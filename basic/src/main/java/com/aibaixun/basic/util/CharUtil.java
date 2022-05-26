package com.aibaixun.basic.util;



/**
 * @author wangxiao@aibaixun.com
 * @since 4.0.1
 */
public class CharUtil {


	public static char SPACE = ' ';
	public static boolean isAscii(char ch) {
		return ch < 128;
	}


	public static boolean isAsciiPrintable(char ch) {
		return ch >= 32 && ch < 127;
	}


	public static boolean isAsciiControl(final char ch) {
		return ch < 32 || ch == 127;
	}

	/**
	 * 字母包括A~Z和a~z
	 */
	public static boolean isLetter(char ch) {
		return isLetterUpper(ch) || isLetterLower(ch);
	}


	public static boolean isLetterUpper(final char ch) {
		return ch >= 'A' && ch <= 'Z';
	}


	public static boolean isLetterLower(final char ch) {
		return ch >= 'a' && ch <= 'z';
	}


	public static boolean isNumber(char ch) {
		return ch >= '0' && ch <= '9';
	}


	public static boolean isHexChar(char c) {
		return isNumber(c) || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F');
	}


	public static boolean isLetterOrNumber(final char ch) {
		return isLetter(ch) || isNumber(ch);
	}


	public static boolean isCharClass(Class<?> clazz) {
		return clazz == Character.class || clazz == char.class;
	}


	public static boolean isChar(Object value) {
		return value instanceof Character;
	}


	public static boolean isBlankChar(char c) {
		return isBlankChar((int) c);
	}


	public static boolean isBlankChar(int c) {
		return Character.isWhitespace(c)
				|| Character.isSpaceChar(c)
				|| c == '\ufeff'
				|| c == '\u202a'
				|| c == '\u0000';
	}


	public static boolean isEmoji(char c) {
		return !(c == 0x0 || c == 0x9 || c == 0xA || c == 0xD || c >= 0x20 && c <= 0xD7FF || c >= 0xE000 && c <= 0xFFFD);
	}


	public static boolean equals(char c1, char c2, boolean caseInsensitive) {
		if (caseInsensitive) {
			return Character.toLowerCase(c1) == Character.toLowerCase(c2);
		}
		return c1 == c2;
	}


	public static int getType(int c) {
		return Character.getType(c);
	}


	public static int digitHex(int b) {
		return Character.digit(b, 16);
	}


}
