package com.aibaixun.basic.util;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 *  16 进制转换
 * @author wangxiao@aibaixun.com
 */
public class HexTool {

	/**
	 * 用于建立十六进制字符的输出的小写字符数组
	 */
	private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	/**
	 * 用于建立十六进制字符的输出的大写字符数组
	 */
	private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

	private static final String LOWER_START = "0x";

	private static final String UPPER_START = "0X";


	public static boolean isHexNumber(String value) {
		final int index = (value.startsWith("-") ? 1 : 0);
		if (value.startsWith(LOWER_START, index) || value.startsWith(UPPER_START, index) || value.startsWith("#", index)) {
			try {
				Long.decode(value);
			} catch (NumberFormatException e) {
				return false;
			}
			return true;
		}
		return false;
	}




	public static char[] encodeHex(byte[] data) {
		return encodeHex(data, true);
	}


	public static char[] encodeHex(String str) {
		return encodeHex(StringUtil.bytes(str), true);
	}


	public static char[] encodeHex(byte[] data, boolean toLowerCase) {
		return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
	}


	public static String encodeHexStr(byte[] data) {
		return encodeHexStr(data, true);
	}


	public static String encodeHexStr(String data, Charset charset) {
		return encodeHexStr(StringUtil.bytes(data, charset), true);
	}


	public static String encodeHexStr(String data) {
		return encodeHexStr(data, StandardCharsets.UTF_8);
	}


	public static String encodeHexStr(byte[] data, boolean toLowerCase) {
		return encodeHexStr(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
	}




	public static String decodeHexStr(String hexStr) {
		return decodeHexStr(hexStr, StandardCharsets.UTF_8);
	}


	public static String decodeHexStr(String hexStr, Charset charset) {
		if (StringUtil.isBlank(hexStr)) {
			return hexStr;
		}
		return StringUtil.str(decodeHex(hexStr), charset);
	}


	public static String decodeHexStr(char[] hexData, Charset charset) {
		return StringUtil.str(decodeHex(hexData), charset);
	}


	public static byte[] decodeHex(String hexStr) {
		return decodeHex((CharSequence) hexStr);
	}


	public static byte[] decodeHex(char[] hexData) {
		return decodeHex(String.valueOf(hexData));
	}


	public static byte[] decodeHex(CharSequence hexData) {
		if (StringUtil.isBlank(hexData)) {
			return null;
		}
		hexData = StringUtil.cleanBlank(hexData);
		int len = hexData.length();

		if ((len & 0x01) != 0) {
			hexData = "0" + hexData;
			len = hexData.length();
		}
		final byte[] out = new byte[len >> 1];
		for (int i = 0, j = 0; j < len; i++) {
			int f = toDigit(hexData.charAt(j)) << 4;
			j++;
			f = f | toDigit(hexData.charAt(j));
			j++;
			out[i] = (byte) (f & 0xFF);
		}
		return out;
	}



	public static String toUnicodeHex(char ch) {
		return "\\u" +
				DIGITS_LOWER[(ch >> 12) & 15] +
				DIGITS_LOWER[(ch >> 8) & 15] +
				DIGITS_LOWER[(ch >> 4) & 15] +
				DIGITS_LOWER[(ch) & 15];
	}


	public static String toHex(int value) {
		return Integer.toHexString(value);
	}


	public static int hexToInt(String value) {
		return Integer.parseInt(value, 16);
	}



	public static String toHex(long value) {
		return Long.toHexString(value);
	}


	public static long hexToLong(String value) {
		return Long.parseLong(value, 16);
	}


	public static void appendHex(StringBuilder builder, byte b, boolean toLowerCase) {
		final char[] toDigits = toLowerCase ? DIGITS_LOWER : DIGITS_UPPER;
		int high = (b & 0xf0) >>> 4;
		int low = b & 0x0f;
		builder.append(toDigits[high]);
		builder.append(toDigits[low]);
	}


	public static BigInteger toBigInteger(String hexStr) {
		if (null == hexStr) {
			return null;
		}
		return new BigInteger(hexStr, 16);
	}


	public static String format(String hexStr) {
		final int length = hexStr.length();
		final StringBuilder builder = new StringBuilder(length + length / 2);
		builder.append(hexStr.charAt(0)).append(hexStr.charAt(1));
		for (int i = 2; i < length - 1; i += 2) {
			builder.append(CharUtil.SPACE).append(hexStr.charAt(i)).append(hexStr.charAt(i + 1));
		}
		return builder.toString();
	}




	private static String encodeHexStr(byte[] data, char[] toDigits) {
		return new String(encodeHex(data, toDigits));
	}


	private static char[] encodeHex(byte[] data, char[] toDigits) {
		final int len = data.length;
		final char[] out = new char[len << 1];
		for (int i = 0, j = 0; i < len; i++) {
			out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
			out[j++] = toDigits[0x0F & data[i]];
		}
		return out;
	}

	private static int toDigit(char ch) {
		return Character.digit(ch, 16);
	}

}
