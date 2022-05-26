package com.aibaixun.basic.util;

import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

/**
 * 数组工具类
 * @author wangxiao@aibaixun.com
 */
public class ArrayUtil {


	public static <T> boolean isEmpty(T[] array) {
		return array == null || array.length == 0;
	}


	public static <T> T[] defaultIfEmpty(T[] array, T[] defaultArray) {
		return isEmpty(array) ? defaultArray : array;
	}

	public static boolean isEmpty(Object array) {
		if (array != null) {
			if (isArray(array)) {
				return 0 == Array.getLength(array);
			}
			return false;
		}
		return true;
	}


	public static <T> boolean isNotEmpty(T[] array) {
		return (null != array && array.length != 0);
	}


	public static boolean isNotEmpty(Object array) {
		return !isEmpty(array);
	}



	@SafeVarargs
	public static <T> boolean hasNull(T... array) {
		if (isNotEmpty(array)) {
			for (T element : array) {
				if (Objectutil.isNull(element)) {
					return true;
				}
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] newArray(Class<?> componentType, int newSize) {
		return (T[]) Array.newInstance(componentType, newSize);
	}


	public static Object[] newArray(int newSize) {
		return new Object[newSize];
	}


	public static Class<?> getComponentType(Object array) {
		return null == array ? null : array.getClass().getComponentType();
	}


	public static Class<?> getComponentType(Class<?> arrayClass) {
		return null == arrayClass ? null : arrayClass.getComponentType();
	}


	public static Class<?> getArrayType(Class<?> componentType) {
		return Array.newInstance(componentType, 0).getClass();
	}


	public static Object[] cast(Class<?> type, Object arrayObj) throws NullPointerException, IllegalArgumentException {
		if (null == arrayObj) {
			throw new NullPointerException("Argument [arrayObj] is null !");
		}
		if (!arrayObj.getClass().isArray()) {
			throw new IllegalArgumentException("Argument [arrayObj] is not array !");
		}
		if (null == type) {
			return (Object[]) arrayObj;
		}

		final Class<?> componentType = type.isArray() ? type.getComponentType() : type;
		final Object[] array = (Object[]) arrayObj;
		final Object[] result = ArrayUtil.newArray(componentType, array.length);
		System.arraycopy(array, 0, result, 0, array.length);
		return result;
	}


	@SafeVarargs
	public static <T> T[] append(T[] buffer, T... newElements) {
		if (isEmpty(buffer)) {
			return newElements;
		}
		return insert(buffer, buffer.length, newElements);
	}


	@SafeVarargs
	public static <T> Object append(Object array, T... newElements) {
		if (isEmpty(array)) {
			return newElements;
		}
		return insert(array, length(array), newElements);
	}


	public static <T> T[] setOrAppend(T[] buffer, int index, T value) {
		if (index < buffer.length) {
			Array.set(buffer, index, value);
			return buffer;
		} else {
			return append(buffer, value);
		}
	}


	public static Object setOrAppend(Object array, int index, Object value) {
		if (index < length(array)) {
			Array.set(array, index, value);
			return array;
		} else {
			return append(array, value);
		}
	}


	@SuppressWarnings("unchecked")
	public static <T> T[] insert(T[] buffer, int index, T... newElements) {
		return (T[]) insert((Object) buffer, index, newElements);
	}


	@SuppressWarnings({"unchecked", "SuspiciousSystemArraycopy"})
	public static <T> Object insert(Object array, int index, T... newElements) {
		if (isEmpty(newElements)) {
			return array;
		}
		if (isEmpty(array)) {
			return newElements;
		}

		final int len = length(array);
		if (index < 0) {
			index = (index % len) + len;
		}
		final T[] result = newArray(array.getClass().getComponentType(), Math.max(len, index) + newElements.length);
		System.arraycopy(array, 0, result, 0, Math.min(len, index));
		System.arraycopy(newElements, 0, result, index, newElements.length);
		if (index < len) {
			System.arraycopy(array, index, result, index + newElements.length, len - index);
		}
		return result;
	}


	public static <T> T[] resize(T[] data, int newSize, Class<?> componentType) {
		if (newSize < 0) {
			return data;
		}

		final T[] newArray = newArray(componentType, newSize);
		if (newSize > 0 && isNotEmpty(data)) {
			System.arraycopy(data, 0, newArray, 0, Math.min(data.length, newSize));
		}
		return newArray;
	}


	public static Object resize(Object array, int newSize) {
		if (newSize < 0) {
			return array;
		}
		if (null == array) {
			return null;
		}
		final int length = length(array);
		final Object newArray = Array.newInstance(array.getClass().getComponentType(), newSize);
		if (newSize > 0 && isNotEmpty(array)) {
			//noinspection SuspiciousSystemArraycopy
			System.arraycopy(array, 0, newArray, 0, Math.min(length, newSize));
		}
		return newArray;
	}


	public static <T> T[] resize(T[] buffer, int newSize) {
		return resize(buffer, newSize, buffer.getClass().getComponentType());
	}


	@SafeVarargs
	public static <T> T[] addAll(T[]... arrays) {
		if (arrays.length == 1) {
			return arrays[0];
		}

		int length = 0;
		for (T[] array : arrays) {
			if (null != array) {
				length += array.length;
			}
		}
		T[] result = newArray(arrays.getClass().getComponentType().getComponentType(), length);

		length = 0;
		for (T[] array : arrays) {
			if (null != array) {
				System.arraycopy(array, 0, result, length, array.length);
				length += array.length;
			}
		}
		return result;
	}


	public static Object copy(Object src, int srcPos, Object dest, int destPos, int length) {
		System.arraycopy(src, srcPos, dest, destPos, length);
		return dest;
	}


	public static Object copy(Object src, Object dest, int length) {
		System.arraycopy(src, 0, dest, 0, length);
		return dest;
	}


	public static <T> T[] clone(T[] array) {
		if (array == null) {
			return null;
		}
		return array.clone();
	}









	public static boolean isArray(Object obj) {
		return null != obj && obj.getClass().isArray();
	}


	@SuppressWarnings("unchecked")
	public static <T> T get(Object array, int index) {
		if (null == array) {
			return null;
		}

		if (index < 0) {
			index += Array.getLength(array);
		}
		try {
			return (T) Array.get(array, index);
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}


	public static <T> T[] getAny(Object array, int... indexes) {
		if (null == array) {
			return null;
		}

		final T[] result = newArray(array.getClass().getComponentType(), indexes.length);
		for (int i : indexes) {
			result[i] = get(array, i);
		}
		return result;
	}


	public static <T> T[] sub(T[] array, int start, int end) {
		int length = length(array);
		if (start < 0) {
			start += length;
		}
		if (end < 0) {
			end += length;
		}
		if (start == length) {
			return newArray(array.getClass().getComponentType(), 0);
		}
		if (start > end) {
			int tmp = start;
			start = end;
			end = tmp;
		}
		if (end > length) {
			if (start >= length) {
				return newArray(array.getClass().getComponentType(), 0);
			}
			end = length;
		}
		return Arrays.copyOfRange(array, start, end);
	}


	public static Object[] sub(Object array, int start, int end) {
		return sub(array, start, end, 1);
	}


	public static Object[] sub(Object array, int start, int end, int step) {
		int length = length(array);
		if (start < 0) {
			start += length;
		}
		if (end < 0) {
			end += length;
		}
		if (start == length) {
			return new Object[0];
		}
		if (start > end) {
			int tmp = start;
			start = end;
			end = tmp;
		}
		if (end > length) {
			if (start >= length) {
				return new Object[0];
			}
			end = length;
		}

		if (step <= 1) {
			step = 1;
		}

		final ArrayList<Object> list = new ArrayList<>();
		for (int i = start; i < end; i += step) {
			list.add(get(array, i));
		}

		return list.toArray();
	}


	public static String toString(Object obj) {
		if (null == obj) {
			return null;
		}

		if (obj instanceof long[]) {
			return Arrays.toString((long[]) obj);
		} else if (obj instanceof int[]) {
			return Arrays.toString((int[]) obj);
		} else if (obj instanceof short[]) {
			return Arrays.toString((short[]) obj);
		} else if (obj instanceof char[]) {
			return Arrays.toString((char[]) obj);
		} else if (obj instanceof byte[]) {
			return Arrays.toString((byte[]) obj);
		} else if (obj instanceof boolean[]) {
			return Arrays.toString((boolean[]) obj);
		} else if (obj instanceof float[]) {
			return Arrays.toString((float[]) obj);
		} else if (obj instanceof double[]) {
			return Arrays.toString((double[]) obj);
		} else if (ArrayUtil.isArray(obj)) {

			try {
				return Arrays.deepToString((Object[]) obj);
			} catch (Exception ignore) {
			}
		}
		return obj.toString();
	}


	public static int length(Object array) throws IllegalArgumentException {
		if (null == array) {
			return 0;
		}
		return Array.getLength(array);
	}



	public static byte[] toArray(ByteBuffer bytebuffer) {
		if (bytebuffer.hasArray()) {
			return Arrays.copyOfRange(bytebuffer.array(), bytebuffer.position(), bytebuffer.limit());
		} else {
			int oldPosition = bytebuffer.position();
			bytebuffer.position(0);
			int size = bytebuffer.limit();
			byte[] buffers = new byte[size];
			bytebuffer.get(buffers);
			bytebuffer.position(oldPosition);
			return buffers;
		}
	}


	public static <T> T[] toArray(Collection<T> collection, Class<T> componentType) {
		return collection.toArray(newArray(componentType, collection.size()));
	}


	public static <T> T[] reverse(T[] array, final int startIndexInclusive, final int endIndexExclusive) {
		if (isEmpty(array)) {
			return array;
		}
		int i = Math.max(startIndexInclusive, 0);
		int j = Math.min(array.length, endIndexExclusive) - 1;
		T tmp;
		while (j > i) {
			tmp = array[j];
			array[j] = array[i];
			array[i] = tmp;
			j--;
			i++;
		}
		return array;
	}

	/**
	 * 反转数组，会变更原数组
	 *
	 * @param <T>   数组元素类型
	 * @param array 数组，会变更
	 * @return 变更后的原数组
	 * @since 3.0.9
	 */
	public static <T> T[] reverse(T[] array) {
		return reverse(array, 0, array.length);
	}




	public static <T> T[] shuffle(T[] array) {
		return shuffle(array, new Random());
	}


	public static <T> T[] shuffle(T[] array, Random random) {
		if (array == null || random == null || array.length <= 1) {
			return array;
		}
		for (int i = array.length; i > 1; i--) {
			swap(array, i - 1, random.nextInt(i));
		}
		return array;
	}


	public static <T> T[] swap(T[] array, int index1, int index2) {
		if (isEmpty(array)) {
			throw new IllegalArgumentException("Array must not empty !");
		}
		T tmp = array[index1];
		array[index1] = array[index2];
		array[index2] = tmp;
		return array;
	}
	public static Object swap(Object array, int index1, int index2) {
		if (isEmpty(array)) {
			throw new IllegalArgumentException("Array must not empty !");
		}
		Object tmp = get(array, index1);
		Array.set(array, index1, Array.get(array, index2));
		Array.set(array, index2, tmp);
		return array;
	}

}
