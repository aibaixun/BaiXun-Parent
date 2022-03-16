package com.aibaixun.basic.toolkit;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;


/**
 * 数字工具类
 * @author wangxiao@aibaixun.com
 */
public class NumberTool {


	private static final int DEFAULT_DIV_SCALE = 10;

	public static double add(float v1, float v2) {
		return add(Float.toString(v1), Float.toString(v2)).doubleValue();
	}


	public static double add(float v1, double v2) {
		return add(Float.toString(v1), Double.toString(v2)).doubleValue();
	}


	public static double add(double v1, float v2) {
		return add(Double.toString(v1), Float.toString(v2)).doubleValue();
	}


	public static double add(double v1, double v2) {
		return add(Double.toString(v1), Double.toString(v2)).doubleValue();
	}


	public static double add(Double v1, Double v2) {
		return add((Number) v1, (Number) v2).doubleValue();
	}


	public static BigDecimal add(Number v1, Number v2) {
		return add(new Number[]{v1, v2});
	}


	public static BigDecimal add(Number... values) {
		if (ArrayTool.isEmpty(values)) {
			return BigDecimal.ZERO;
		}
		Number value = values[0];
		BigDecimal result = toBigDecimal(value);
		for (int i = 1; i < values.length; i++) {
			value = values[i];
			if (null != value) {
				result = result.add(toBigDecimal(value));
			}
		}
		return result;
	}


	public static BigDecimal add(String... values) {
		if (ArrayTool.isEmpty(values)) {
			return BigDecimal.ZERO;
		}

		String value = values[0];
		BigDecimal result = toBigDecimal(value);
		for (int i = 1; i < values.length; i++) {
			value = values[i];
			if (!StringTool.isBlank(value)) {
				result = result.add(toBigDecimal(value));
			}
		}
		return result;
	}


	public static BigDecimal add(BigDecimal... values) {
		if (ArrayTool.isEmpty(values)) {
			return BigDecimal.ZERO;
		}

		BigDecimal value = values[0];
		BigDecimal result = toBigDecimal(value);
		for (int i = 1; i < values.length; i++) {
			value = values[i];
			if (null != value) {
				result = result.add(value);
			}
		}
		return result;
	}


	public static double sub(float v1, float v2) {
		return sub(Float.toString(v1), Float.toString(v2)).doubleValue();
	}


	public static double sub(float v1, double v2) {
		return sub(Float.toString(v1), Double.toString(v2)).doubleValue();
	}


	public static double sub(double v1, float v2) {
		return sub(Double.toString(v1), Float.toString(v2)).doubleValue();
	}


	public static double sub(double v1, double v2) {
		return sub(Double.toString(v1), Double.toString(v2)).doubleValue();
	}


	public static double sub(Double v1, Double v2) {
		return sub((Number) v1, (Number) v2).doubleValue();
	}


	public static BigDecimal sub(Number v1, Number v2) {
		return sub(new Number[]{v1, v2});
	}


	public static BigDecimal sub(Number... values) {
		if (ArrayTool.isEmpty(values)) {
			return BigDecimal.ZERO;
		}
		Number value = values[0];
		BigDecimal result = toBigDecimal(value);
		for (int i = 1; i < values.length; i++) {
			value = values[i];
			if (null != value) {
				result = result.subtract(toBigDecimal(value));
			}
		}
		return result;
	}


	public static BigDecimal sub(String... values) {
		if (ArrayTool.isEmpty(values)) {
			return BigDecimal.ZERO;
		}

		String value = values[0];
		BigDecimal result = toBigDecimal(value);
		for (int i = 1; i < values.length; i++) {
			value = values[i];
			if (!StringTool.isBlank(value)) {
				result = result.subtract(toBigDecimal(value));
			}
		}
		return result;
	}


	public static BigDecimal sub(BigDecimal... values) {
		if (ArrayTool.isEmpty(values)) {
			return BigDecimal.ZERO;
		}
		BigDecimal value = values[0];
		BigDecimal result = toBigDecimal(value);
		for (int i = 1; i < values.length; i++) {
			value = values[i];
			if (null != value) {
				result = result.subtract(value);
			}
		}
		return result;
	}


	public static double mul(float v1, float v2) {
		return mul(Float.toString(v1), Float.toString(v2)).doubleValue();
	}


	public static double mul(float v1, double v2) {
		return mul(Float.toString(v1), Double.toString(v2)).doubleValue();
	}


	public static double mul(double v1, float v2) {
		return mul(Double.toString(v1), Float.toString(v2)).doubleValue();
	}


	public static double mul(double v1, double v2) {
		return mul(Double.toString(v1), Double.toString(v2)).doubleValue();
	}


	public static double mul(Double v1, Double v2) {
		return mul((Number) v1, (Number) v2).doubleValue();
	}

	public static BigDecimal mul(Number v1, Number v2) {
		return mul(new Number[]{v1, v2});
	}

	public static BigDecimal mul(Number... values) {
		if (ArrayTool.isEmpty(values) || ArrayTool.hasNull(values)) {
			return BigDecimal.ZERO;
		}
		Number value = values[0];
		BigDecimal result = new BigDecimal(value.toString());
		for (int i = 1; i < values.length; i++) {
			value = values[i];
			result = result.multiply(new BigDecimal(value.toString()));
		}
		return result;
	}


	public static BigDecimal mul(String v1, String v2) {
		return mul(new BigDecimal(v1), new BigDecimal(v2));
	}


	public static BigDecimal mul(String... values) {
		if (ArrayTool.isEmpty(values) || ArrayTool.hasNull(values)) {
			return BigDecimal.ZERO;
		}
		BigDecimal result = new BigDecimal(values[0]);
		for (int i = 1; i < values.length; i++) {
			result = result.multiply(new BigDecimal(values[i]));
		}
		return result;
	}

	public static BigDecimal mul(BigDecimal... values) {
		if (ArrayTool.isEmpty(values) || ArrayTool.hasNull(values)) {
			return BigDecimal.ZERO;
		}
		BigDecimal result = values[0];
		for (int i = 1; i < values.length; i++) {
			result = result.multiply(values[i]);
		}
		return result;
	}


	public static double div(float v1, float v2) {
		return div(v1, v2, DEFAULT_DIV_SCALE);
	}

	public static double div(float v1, double v2) {
		return div(v1, v2, DEFAULT_DIV_SCALE);
	}

	public static double div(double v1, float v2) {
		return div(v1, v2, DEFAULT_DIV_SCALE);
	}


	public static double div(double v1, double v2) {
		return div(v1, v2, DEFAULT_DIV_SCALE);
	}


	public static double div(Double v1, Double v2) {
		return div(v1, v2, DEFAULT_DIV_SCALE);
	}


	public static BigDecimal div(Number v1, Number v2) {
		return div(v1, v2, DEFAULT_DIV_SCALE);
	}


	public static BigDecimal div(String v1, String v2) {
		return div(v1, v2, DEFAULT_DIV_SCALE);
	}


	public static double div(float v1, float v2, int scale) {
		return div(v1, v2, scale, RoundingMode.HALF_UP);
	}

	public static double div(float v1, double v2, int scale) {
		return div(v1, v2, scale, RoundingMode.HALF_UP);
	}


	public static double div(double v1, float v2, int scale) {
		return div(v1, v2, scale, RoundingMode.HALF_UP);
	}


	public static double div(double v1, double v2, int scale) {
		return div(v1, v2, scale, RoundingMode.HALF_UP);
	}


	public static double div(Double v1, Double v2, int scale) {
		return div(v1, v2, scale, RoundingMode.HALF_UP);
	}


	public static BigDecimal div(Number v1, Number v2, int scale) {
		return div(v1, v2, scale, RoundingMode.HALF_UP);
	}


	public static BigDecimal div(String v1, String v2, int scale) {
		return div(v1, v2, scale, RoundingMode.HALF_UP);
	}


	public static double div(float v1, float v2, int scale, RoundingMode roundingMode) {
		return div(Float.toString(v1), Float.toString(v2), scale, roundingMode).doubleValue();
	}


	public static double div(float v1, double v2, int scale, RoundingMode roundingMode) {
		return div(Float.toString(v1), Double.toString(v2), scale, roundingMode).doubleValue();
	}


	public static double div(double v1, float v2, int scale, RoundingMode roundingMode) {
		return div(Double.toString(v1), Float.toString(v2), scale, roundingMode).doubleValue();
	}


	public static double div(double v1, double v2, int scale, RoundingMode roundingMode) {
		return div(Double.toString(v1), Double.toString(v2), scale, roundingMode).doubleValue();
	}


	public static double div(Double v1, Double v2, int scale, RoundingMode roundingMode) {
		return div((Number) v1, (Number) v2, scale, roundingMode).doubleValue();
	}


	public static BigDecimal div(Number v1, Number v2, int scale, RoundingMode roundingMode) {
		return div(v1.toString(), v2.toString(), scale, roundingMode);
	}


	public static BigDecimal div(String v1, String v2, int scale, RoundingMode roundingMode) {
		return div(toBigDecimal(v1), toBigDecimal(v2), scale, roundingMode);
	}


	public static BigDecimal div(BigDecimal v1, BigDecimal v2, int scale, RoundingMode roundingMode) {
		if (null == v1) {
			return BigDecimal.ZERO;
		}
		if (scale < 0) {
			scale = -scale;
		}
		return v1.divide(v2, scale, roundingMode);
	}

	public static int ceilDiv(int v1, int v2) {
		return (int) Math.ceil((double) v1 / v2);
	}




	public static BigDecimal round(double v, int scale) {
		return round(v, scale, RoundingMode.HALF_UP);
	}


	public static String roundStr(double v, int scale) {
		return round(v, scale).toString();
	}


	public static BigDecimal round(String numberStr, int scale) {
		return round(numberStr, scale, RoundingMode.HALF_UP);
	}


	public static BigDecimal round(BigDecimal number, int scale) {
		return round(number, scale, RoundingMode.HALF_UP);
	}


	public static String roundStr(String numberStr, int scale) {
		return round(numberStr, scale).toString();
	}


	public static BigDecimal round(double v, int scale, RoundingMode roundingMode) {
		return round(Double.toString(v), scale, roundingMode);
	}


	public static String roundStr(double v, int scale, RoundingMode roundingMode) {
		return round(v, scale, roundingMode).toString();
	}


	public static BigDecimal round(String numberStr, int scale, RoundingMode roundingMode) {
		if (scale < 0) {
			scale = 0;
		}
		return round(toBigDecimal(numberStr), scale, roundingMode);
	}


	public static BigDecimal round(BigDecimal number, int scale, RoundingMode roundingMode) {
		if (null == number) {
			number = BigDecimal.ZERO;
		}
		if (scale < 0) {
			scale = 0;
		}
		if (null == roundingMode) {
			roundingMode = RoundingMode.HALF_UP;
		}

		return number.setScale(scale, roundingMode);
	}


	public static String roundStr(String numberStr, int scale, RoundingMode roundingMode) {
		return round(numberStr, scale, roundingMode).toString();
	}


	public static BigDecimal roundHalfEven(Number number, int scale) {
		return roundHalfEven(toBigDecimal(number), scale);
	}


	public static BigDecimal roundHalfEven(BigDecimal value, int scale) {
		return round(value, scale, RoundingMode.HALF_EVEN);
	}


	public static BigDecimal roundDown(Number number, int scale) {
		return roundDown(toBigDecimal(number), scale);
	}


	public static BigDecimal roundDown(BigDecimal value, int scale) {
		return round(value, scale, RoundingMode.DOWN);
	}



	public static String decimalFormat(String pattern, double value) {
		return new DecimalFormat(pattern).format(value);
	}


	public static String decimalFormat(String pattern, long value) {
		return new DecimalFormat(pattern).format(value);
	}


	public static String decimalFormat(String pattern, Object value) {
		return decimalFormat(pattern, value, null);
	}


	public static String decimalFormat(String pattern, Object value, RoundingMode roundingMode) {
		final DecimalFormat decimalFormat = new DecimalFormat(pattern);
		if (null != roundingMode) {
			decimalFormat.setRoundingMode(roundingMode);
		}
		return decimalFormat.format(value);
	}






	public static int compare(char x, char y) {
		return Character.compare(x, y);
	}


	public static int compare(double x, double y) {
		return Double.compare(x, y);
	}


	public static int compare(int x, int y) {
		return Integer.compare(x, y);
	}


	public static int compare(long x, long y) {
		return Long.compare(x, y);
	}


	public static int compare(short x, short y) {
		return Short.compare(x, y);
	}


	public static int compare(byte x, byte y) {
		return Byte.compare(x, y);
	}


	public static boolean equals(BigDecimal bigNum1, BigDecimal bigNum2) {
		if (bigNum1 == null || bigNum2 == null) {
			return false;
		}
		return 0 == bigNum1.compareTo(bigNum2);
	}


	public static boolean equals(char c1, char c2, boolean ignoreCase) {
		return CharTool.equals(c1, c2, ignoreCase);
	}





	public static String toString(Number number, String defaultValue) {
		return (null == number) ? defaultValue : toString(number);
	}


	public static String toString(Number number) {
		return toString(number, true);
	}


	public static String toString(Number number, boolean isStripTrailingZeros) {
		if (number instanceof BigDecimal) {
			return toString((BigDecimal) number, isStripTrailingZeros);
		}
		String string = number.toString();
		if (isStripTrailingZeros) {
			if (string.indexOf('.') > 0 && string.indexOf('e') < 0 && string.indexOf('E') < 0) {
				while (string.endsWith("0")) {
					string = string.substring(0, string.length() - 1);
				}
				if (string.endsWith(".")) {
					string = string.substring(0, string.length() - 1);
				}
			}
		}
		return string;
	}


	public static String toString(BigDecimal bigDecimal) {
		return toString(bigDecimal, true);
	}


	public static String toString(BigDecimal bigDecimal, boolean isStripTrailingZeros) {
		if (isStripTrailingZeros) {
			bigDecimal = bigDecimal.stripTrailingZeros();
		}
		return bigDecimal.toPlainString();
	}


	public static BigDecimal toBigDecimal(Number number) {
		if (null == number) {
			return BigDecimal.ZERO;
		}

		if (number instanceof BigDecimal) {
			return (BigDecimal) number;
		} else if (number instanceof Long) {
			return new BigDecimal((Long) number);
		} else if (number instanceof Integer) {
			return new BigDecimal((Integer) number);
		} else if (number instanceof BigInteger) {
			return new BigDecimal((BigInteger) number);
		}
		return toBigDecimal(number.toString());
	}


	public static BigDecimal toBigDecimal(String numberStr) {
		if(StringTool.isBlank(numberStr)){
			return BigDecimal.ZERO;
		}
		try {
			final Number number = parseNumber(numberStr);
			if(number instanceof BigDecimal){
				return (BigDecimal) number;
			} else {
				return new BigDecimal(number.toString());
			}
		} catch (Exception ignore) {
		}
		return new BigDecimal(numberStr);
	}

	public static BigInteger toBigInteger(Number number) {
		if (null == number) {
			return BigInteger.ZERO;
		}

		if (number instanceof BigInteger) {
			return (BigInteger) number;
		} else if (number instanceof Long) {
			return BigInteger.valueOf((Long) number);
		}

		return toBigInteger(number.longValue());
	}


	public static BigInteger toBigInteger(String number) {
		return StringTool.isBlank(number) ? BigInteger.ZERO : new BigInteger(number);
	}

	public static Number parseNumber(String numberStr) throws NumberFormatException {
		try {
			final NumberFormat format = NumberFormat.getInstance();
			if(format instanceof DecimalFormat){
				((DecimalFormat) format).setParseBigDecimal(true);
			}
			return format.parse(numberStr);
		} catch (ParseException e) {
			final NumberFormatException nfe = new NumberFormatException(e.getMessage());
			nfe.initCause(e);
			throw nfe;
		}
	}

	public static byte[] toBytes(int value) {
		final byte[] result = new byte[4];
		result[0] = (byte) (value >> 24);
		result[1] = (byte) (value >> 16);
		result[2] = (byte) (value >> 8);
		result[3] = (byte) (value);
		return result;
	}


	public static int toInt(byte[] bytes) {
		return (bytes[0] & 0xff) << 24
				| (bytes[1] & 0xff) << 16
				| (bytes[2] & 0xff) << 8
				| (bytes[3] & 0xff);
	}

	public static byte[] toUnsignedByteArray(BigInteger value) {
		byte[] bytes = value.toByteArray();
		if (bytes[0] == 0) {
			byte[] tmp = new byte[bytes.length - 1];
			System.arraycopy(bytes, 1, tmp, 0, tmp.length);

			return tmp;
		}

		return bytes;
	}


	public static byte[] toUnsignedByteArray(int length, BigInteger value) {
		byte[] bytes = value.toByteArray();
		if (bytes.length == length) {
			return bytes;
		}
		int start = bytes[0] == 0 ? 1 : 0;
		int count = bytes.length - start;
		if (count > length) {
			throw new IllegalArgumentException("standard length exceeded for value");
		}

		byte[] tmp = new byte[length];
		System.arraycopy(bytes, start, tmp, tmp.length - count, count);
		return tmp;
	}


	public static BigInteger fromUnsignedByteArray(byte[] buf) {
		return new BigInteger(1, buf);
	}


	public static BigInteger fromUnsignedByteArray(byte[] buf, int off, int length) {
		byte[] mag = buf;
		if (off != 0 || length != buf.length) {
			mag = new byte[length];
			System.arraycopy(buf, off, mag, 0, length);
		}
		return new BigInteger(1, mag);
	}

	/**
	 * 检查是否为有效的数字<br>
	 * 检查Double和Float是否为无限大，或者Not a Number<br>
	 * 非数字类型和Null将返回true
	 *
	 * @param number 被检查类型
	 * @return 检查结果，非数字类型和Null将返回true
	 * @since 4.6.7
	 */
	public static boolean isValidNumber(Number number) {
		if (number instanceof Double) {
			return (!((Double) number).isInfinite()) && (!((Double) number).isNaN());
		} else if (number instanceof Float) {
			return (!((Float) number).isInfinite()) && (!((Float) number).isNaN());
		}
		return true;
	}


	public static boolean isValid(double number) {
		return !(Double.isNaN(number) || Double.isInfinite(number));
	}


	public static boolean isValid(float number) {
		return !(Float.isNaN(number) || Float.isInfinite(number));
	}


}
