package com.aibaixun.basic.toolkit;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 集合相关工具类
 * @author wangxiao@aibaixun.com
 */
public class CollectionTool {


	public static <T> Set<T> emptyIfNull(Set<T> set) {
		return (null == set) ? Collections.emptySet() : set;
	}


	public static <T> List<T> emptyIfNull(List<T> list) {
		return (null == list) ? Collections.emptyList() : list;
	}



	@SafeVarargs
	public static <T> Collection<T> union(Collection<T> coll1, Collection<T> coll2, Collection<T>... otherColls) {
		Collection<T> union = union(coll1, coll2);
		for (Collection<T> coll : otherColls) {
			union = union(union, coll);
		}
		return union;
	}


	@SafeVarargs
	public static <T> Set<T> unionDistinct(Collection<T> coll1, Collection<T> coll2, Collection<T>... otherColls) {
		final Set<T> result;
		if (isEmpty(coll1)) {
			result = new LinkedHashSet<>();
		} else {
			result = new LinkedHashSet<>(coll1);
		}

		if (isNotEmpty(coll2)) {
			result.addAll(coll2);
		}

		if (ArrayTool.isNotEmpty(otherColls)) {
			for (Collection<T> otherColl : otherColls) {
				result.addAll(otherColl);
			}
		}

		return result;
	}


	@SafeVarargs
	public static <T> List<T> unionAll(Collection<T> coll1, Collection<T> coll2, Collection<T>... otherColls) {
		final List<T> result;
		if (isEmpty(coll1)) {
			result = new ArrayList<>();
		} else {
			result = new ArrayList<>(coll1);
		}

		if (isNotEmpty(coll2)) {
			result.addAll(coll2);
		}

		if (ArrayTool.isNotEmpty(otherColls)) {
			for (Collection<T> otherColl : otherColls) {
				result.addAll(otherColl);
			}
		}

		return result;
	}





	public static boolean contains(Collection<?> collection, Object value) {
		return isNotEmpty(collection) && collection.contains(value);
	}

	public static boolean safeContains(Collection<?> collection, Object value) {

		try {
			return contains(collection, value);
		} catch (ClassCastException | NullPointerException e) {
			return false;
		}
	}



	public static <T> boolean contains(Collection<T> collection, Predicate<? super T> containFunc) {
		if (isEmpty(collection)) {
			return false;
		}
		for (T t : collection) {
			if (containFunc.test(t)) {
				return true;
			}
		}
		return false;
	}


	public static boolean containsAny(Collection<?> coll1, Collection<?> coll2) {
		if (isEmpty(coll1) || isEmpty(coll2)) {
			return false;
		}
		if (coll1.size() < coll2.size()) {
			for (Object object : coll1) {
				if (coll2.contains(object)) {
					return true;
				}
			}
		} else {
			for (Object object : coll2) {
				if (coll1.contains(object)) {
					return true;
				}
			}
		}
		return false;
	}


	public static boolean containsAll(Collection<?> coll1, Collection<?> coll2) {
		if (isEmpty(coll1)) {
			return isEmpty(coll2);
		}
		if (isEmpty(coll2)) {
			return true;
		}
		if (coll1.size() < coll2.size()) {
			return false;
		}
		for (Object object : coll2) {
			if (!coll1.contains(object)) {
				return false;
			}
		}
		return true;
	}


	public static <T, R> List<R> map(Iterable<T> collection, Function<? super T, ? extends R> func, boolean ignoreNull) {
		final List<R> fieldValueList = new ArrayList<>();
		if (null == collection) {
			return fieldValueList;
		}

		R value;
		for (T t : collection) {
			if (null == t && ignoreNull) {
				continue;
			}
			value = func.apply(t);
			if (null == value && ignoreNull) {
				continue;
			}
			fieldValueList.add(value);
		}
		return fieldValueList;
	}


	public static <T> T findOne(Iterable<T> collection, Predicate<T> filter) {
		if (null != collection) {
			for (T t : collection) {
				if (filter.test(t)) {
					return t;
				}
			}
		}
		return null;
	}



	public static <T> int count(Iterable<T> iterable, Predicate<T> matcher) {
		int count = 0;
		if (null != iterable) {
			for (T t : iterable) {
				if (null == matcher || matcher.test(t)) {
					count++;
				}
			}
		}
		return count;
	}


	public static <T> int indexOf(Collection<T> collection, Predicate<T> matcher) {
		if (isNotEmpty(collection)) {
			int index = 0;
			for (T t : collection) {
				if (null == matcher || matcher.test(t)) {
					return index;
				}
				index++;
			}
		}
		return -1;
	}


	public static <T> int lastIndexOf(Collection<T> collection, Predicate<T> matcher) {
		int matchIndex = -1;
		if (isNotEmpty(collection)) {
			int index = collection.size();
			for (T t : collection) {
				if (null == matcher || matcher.test(t)) {
					matchIndex = index;
				}
				index--;
			}
		}
		return matchIndex;
	}





	public static boolean isEmpty(Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}


	public static <T extends Collection<E>, E> T defaultIfEmpty(T collection, T defaultCollection) {
		return isEmpty(collection) ? defaultCollection : collection;
	}


	public static <T extends Collection<E>, E> T defaultIfEmpty(T collection, Supplier<? extends T> supplier) {
		return isEmpty(collection) ? supplier.get() : collection;
	}


	public static boolean isNotEmpty(Collection<?> collection) {
		return !isEmpty(collection);
	}


	public static <T> Collection<T> addAll(Collection<T> collection, Iterator<T> iterator) {
		if (null != collection && null != iterator) {
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}


	public static <T> Collection<T> addAll(Collection<T> collection, Iterable<T> iterable) {
		if (iterable == null) {
			return collection;
		}
		return addAll(collection, iterable.iterator());
	}


	public static <T> Collection<T> addAll(Collection<T> collection, Enumeration<T> enumeration) {
		if (null != collection && null != enumeration) {
			while (enumeration.hasMoreElements()) {
				collection.add(enumeration.nextElement());
			}
		}
		return collection;
	}


	public static <T> Collection<T> addAll(Collection<T> collection, T[] values) {
		if (null != collection && null != values) {
			Collections.addAll(collection, values);
		}
		return collection;
	}


	public static <T> List<T> addAllIfNotContains(List<T> list, List<T> otherList) {
		for (T t : otherList) {
			if (!list.contains(t)) {
				list.add(t);
			}
		}
		return list;
	}


	public static <T> T get(Collection<T> collection, int index) {
		if (null == collection) {
			return null;
		}
		final int size = collection.size();
		if (0 == size) {
			return null;
		}

		if (index < 0) {
			index += size;
		}
		if (index >= size || index < 0) {
			return null;
		}
		if (collection instanceof List) {
			final List<T> list = ((List<T>) collection);
			return list.get(index);
		} else {
			int i = 0;
			for (T t : collection) {
				if (i > index) {
					break;
				} else if (i == index) {
					return t;
				}
				i++;
			}
		}
		return null;
	}


	@SuppressWarnings("unchecked")
	public static <T> List<T> getAny(Collection<T> collection, int... indexes) {
		final int size = collection.size();
		final ArrayList<T> result = new ArrayList<>();
		if (collection instanceof List) {
			final List<T> list = ((List<T>) collection);
			for (int index : indexes) {
				if (index < 0) {
					index += size;
				}
				result.add(list.get(index));
			}
		} else {
			final Object[] array = collection.toArray();
			for (int index : indexes) {
				if (index < 0) {
					index += size;
				}
				result.add((T) array[index]);
			}
		}
		return result;
	}



	public static <T> List<T> sort(Collection<T> collection, Comparator<? super T> comparator) {
		List<T> list = new ArrayList<>(collection);
		list.sort(comparator);
		return list;
	}


	public static <K, V> TreeMap<K, V> sort(Map<K, V> map, Comparator<? super K> comparator) {
		final TreeMap<K, V> result = new TreeMap<>(comparator);
		result.putAll(map);
		return result;
	}


	public static <K, V> LinkedHashMap<K, V> sortToMap(Collection<Entry<K, V>> entryCollection, Comparator<Entry<K, V>> comparator) {
		List<Entry<K, V>> list = new LinkedList<>(entryCollection);
		list.sort(comparator);

		LinkedHashMap<K, V> result = new LinkedHashMap<>();
		for (Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}


	public static <K, V> LinkedHashMap<K, V> sortByEntry(Map<K, V> map, Comparator<Entry<K, V>> comparator) {
		return sortToMap(map.entrySet(), comparator);
	}


	@SuppressWarnings({"unchecked", "rawtypes"})
	public static <K, V> List<Entry<K, V>> sortEntryToList(Collection<Entry<K, V>> collection) {
		List<Entry<K, V>> list = new LinkedList<>(collection);
		list.sort((o1, o2) -> {
			V v1 = o1.getValue();
			V v2 = o2.getValue();

			if (v1 instanceof Comparable) {
				return ((Comparable) v1).compareTo(v2);
			} else {
				return v1.toString().compareTo(v2.toString());
			}
		});
		return list;
	}


	public static <V> List<V> values(Collection<Map<?, V>> mapCollection) {
		final List<V> values = new ArrayList<>();
		for (Map<?, V> map : mapCollection) {
			values.addAll(map.values());
		}

		return values;
	}


	public static <T extends Comparable<? super T>> T max(Collection<T> coll) {
		return Collections.max(coll);
	}


	public static <T extends Comparable<? super T>> T min(Collection<T> coll) {
		return Collections.min(coll);
	}


	public static <T> Collection<T> unmodifiable(Collection<? extends T> c) {
		return Collections.unmodifiableCollection(c);
	}


	@SuppressWarnings("unchecked")
	public static <E, T extends Collection<E>> T empty(Class<?> collectionClass) {
		if (null == collectionClass) {
			return (T) Collections.emptyList();
		}
		if (Set.class.isAssignableFrom(collectionClass)) {
			if (NavigableSet.class == collectionClass) {
				return (T) Collections.emptyNavigableSet();
			} else if (SortedSet.class == collectionClass) {
				return (T) Collections.emptySortedSet();
			} else {
				return (T) Collections.emptySet();
			}
		} else if (List.class.isAssignableFrom(collectionClass)) {
			return (T) Collections.emptyList();
		}

		throw new IllegalArgumentException("Collection is not support to get empty!");
	}


	public static void clear(Collection<?>... collections) {
		for (Collection<?> collection : collections) {
			if (isNotEmpty(collection)) {
				collection.clear();
			}
		}
	}




}
