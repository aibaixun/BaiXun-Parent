package com.aibaixun.basic.cache;

import com.aibaixun.basic.util.StringUtil;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author wang xiao
 * @date 2022/5/26
 */
public class CacheUtils {
    private CacheService[] cacheServices;
    private static final Duration MAX_WAIT_TIME = Duration.ofSeconds(10L);
    private volatile ConcurrentHashMap<Object, LoaderLock> loaderMap;
    private volatile ConcurrentHashMap<Object, LoaderLock> modifyMap;

    private CacheUtils() {
        this.cacheServices = new CacheService[0];
    }

    private CacheUtils(CacheService... cacheServices) {
        this.cacheServices = new CacheService[0];
        this.cacheServices = cacheServices;
    }

    private CacheUtils(Builder builder) {
        this.cacheServices = new CacheService[0];
        this.cacheServices = builder.cacheServices;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public <R> R get(String prefix, String key, Class<R> returnClass) {

        return get(prefix, key, returnClass,null,false,MAX_WAIT_TIME);
    }

    public <R> R get(String prefix, String key, Class<R> returnClass, Function<String, R> invokeOrigin) {
        return get(prefix, key, returnClass, invokeOrigin, false, MAX_WAIT_TIME);
    }

    public <R> R get(String prefix, String key, Class<R> returnClass, Function<String, R> invokeOrigin, boolean protect, Duration timeout) {
        this.checkParam(prefix, key);
        int i = 0;

        Object value;
        for(value = null; i < this.cacheServices.length; ++i) {
            value = this.cacheServices[i].get(prefix, key, returnClass);
            if (Objects.nonNull(value)) {
                break;
            }
        }

        if (i != this.cacheServices.length) {
            this.putInter(prefix, key, value, i);
            return (R) value;
        } else {
            R oriValue = null;
            if (invokeOrigin == null) {
                return null;
            } else {
                if (protect) {
                    oriValue = this.synchronizedLoad(prefix, key, returnClass, invokeOrigin, timeout);
                } else {
                    oriValue = invokeOrigin.apply(key);
                }

                this.putInter(prefix, key, oriValue, this.cacheServices.length);
                return oriValue;
            }
        }
    }

    ConcurrentHashMap<Object, LoaderLock> initOrGetLoaderMap() {
        if (this.loaderMap == null) {
            synchronized(this) {
                if (this.loaderMap == null) {
                    this.loaderMap = new ConcurrentHashMap(16);
                }
            }
        }

        return this.loaderMap;
    }

    <R> R synchronizedLoad(String prefix, String key, Class<R> returnClass, Function<String, R> invokeOrigin, Duration timeout) {
        ConcurrentHashMap<Object, LoaderLock> loaderMap = this.initOrGetLoaderMap();
        String lockKey = prefix + key;

        LoaderLock ll;
        do {
            boolean[] create = new boolean[1];
            ll = loaderMap.computeIfAbsent(lockKey, (unusedKey) -> {
                create[0] = true;
                LoaderLock loaderLock = new LoaderLock();
                loaderLock.signal = new CountDownLatch(1);
                loaderLock.loaderThread = Thread.currentThread();
                return loaderLock;
            });
            if (create[0] || ll.loaderThread == Thread.currentThread()) {
                Object load;
                try {
                    R loadedValue = invokeOrigin.apply(key);
                    ll.success = true;
                    ll.value = loadedValue;
                    load = loadedValue;
                } finally {
                    if (create[0]) {
                        ll.signal.countDown();
                        loaderMap.remove(lockKey);
                    }

                }

                return (R) load;
            }

            try {
                if (timeout == null) {
                    ll.signal.await();
                } else {
                    boolean ok = ll.signal.await(timeout.toMillis(), TimeUnit.MILLISECONDS);
                    if (!ok) {
                        return invokeOrigin.apply(key);
                    }
                }
            } catch (InterruptedException e) {
                return invokeOrigin.apply(key);
            }
        } while(!ll.success);

        return (R) ll.value;
    }

    ConcurrentHashMap<Object, LoaderLock> initOrGetModifyMap() {
        if (this.modifyMap == null) {
            synchronized(this) {
                if (this.modifyMap == null) {
                    this.modifyMap = new ConcurrentHashMap(16);
                }
            }
        }

        return this.modifyMap;
    }

    void synchronizedModify(String prefix, String key, Consumer<String> operation, Duration timeout) {
        ConcurrentHashMap<Object, LoaderLock> loaderMap = this.initOrGetModifyMap();
        String lockKey = prefix + key;

        while(true) {
            boolean[] create = new boolean[1];
            LoaderLock ll = (LoaderLock)loaderMap.computeIfAbsent(lockKey, (unusedKey) -> {
                create[0] = true;
                LoaderLock loaderLock = new LoaderLock();
                loaderLock.signal = new CountDownLatch(1);
                loaderLock.loaderThread = Thread.currentThread();
                return loaderLock;
            });
            if (create[0] || ll.loaderThread == Thread.currentThread()) {
                try {
                    operation.accept(key);
                    ll.success = true;
                } finally {
                    if (create[0]) {
                        ll.signal.countDown();
                        loaderMap.remove(lockKey);
                    }

                }

                return;
            }

            try {
                if (timeout == null) {
                    ll.signal.await();
                } else {
                    ll.signal.await(timeout.toMillis(), TimeUnit.MILLISECONDS);

                }
            } catch (InterruptedException var12) {
            }

            if (!ll.success) {
            }
        }
    }

    private <R> void putInter(String prefix, String key, R value, int maxLength, Duration... expiredTime) {
        this.checkParam(prefix, key, expiredTime);
        this.synchronizedModify(prefix, key, (index) -> {
            if (!Objects.isNull(value)) {
                for(int i = maxLength - 1; i >= 0; --i) {
                    Duration expireTime = this.cacheServices[i].getExpireTime();
                    if (expiredTime.length != 0) {
                        expireTime = expiredTime[i];
                    }

                    this.cacheServices[i].put(prefix, key, value, expireTime);
                }

            }
        }, MAX_WAIT_TIME);
    }

    public <R> void put(String prefix, String key, R value, Duration... expiredTime) {
        this.putInter(prefix, key, value, this.cacheServices.length, expiredTime);
    }

    public void evict(String prefix, String key) {
        this.checkParam(prefix, key);

        for(int i = 0; i < this.cacheServices.length; ++i) {
            this.cacheServices[i].evict(prefix, key);
        }

    }

    private void checkParam(String prefix, String key) {
        if (StringUtil.isEmpty(prefix) || StringUtil.isEmpty(key)) {
            throw new IllegalArgumentException("前缀与KEY值不能为空");
        }
    }

    private void checkParam(String prefix, String key, Duration... expireTime) {
        this.checkParam(prefix, key);
        if (expireTime.length != 0 && expireTime.length != this.cacheServices.length) {
            throw new IllegalArgumentException("缓存过期时间与缓存级数不一致");
        }
    }

    public static final class Builder {
        private CacheService[] cacheServices;

        private Builder() {
        }

        public Builder setCacheServices(CacheService... val) {
            this.cacheServices = val;
            return this;
        }

        public CacheUtils build() {
            return new CacheUtils(this);
        }
    }

    static class LoaderLock {
        CountDownLatch signal;
        Thread loaderThread;
        volatile boolean success;
        volatile Object value;

        LoaderLock() {
        }
    }
}
