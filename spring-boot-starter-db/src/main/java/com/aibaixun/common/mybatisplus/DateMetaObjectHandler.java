package com.aibaixun.common.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.time.Clock;
import java.time.Instant;


/**
 * @author wangxiao@aibaixun.com
 * @date 2021/12/30
 */
public class DateMetaObjectHandler implements MetaObjectHandler {

    private final AutoFillProperties autoFillProperties;

    public DateMetaObjectHandler(AutoFillProperties autoFillProperties) {
        this.autoFillProperties = autoFillProperties;
    }

    /**
     * 是否开启了插入填充
     */
    @Override
    public boolean openInsertFill() {
        return autoFillProperties.getEnableInsertFill();
    }

    /**
     * 是否开启了更新填充
     */
    @Override
    public boolean openUpdateFill() {
        return autoFillProperties.getEnableUpdateFill();
    }

    /**
     * 插入填充，字段为空自动填充
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Object createTime = getFieldValByName(autoFillProperties.getCreateTimeField(), metaObject);
        Object updateTime = getFieldValByName(autoFillProperties.getUpdateTimeField(), metaObject);
        long nowTs = Instant.now(Clock.systemDefaultZone()).toEpochMilli();
        if (createTime == null || updateTime == null) {
            if (createTime == null) {
                setFieldValByName(autoFillProperties.getCreateTimeField(), nowTs, metaObject);
            }
            if (updateTime == null) {
                setFieldValByName(autoFillProperties.getUpdateTimeField(), nowTs, metaObject);
            }
        }
    }

    /**
     * 更新填充
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        Object updateTime = getFieldValByName(autoFillProperties.getUpdateTimeField(), metaObject);
        long nowTs = Instant.now(Clock.systemDefaultZone()).toEpochMilli();
        if (updateTime == null) {
            setFieldValByName(autoFillProperties.getUpdateTimeField(), nowTs, metaObject);
        }
    }
}
