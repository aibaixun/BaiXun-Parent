package com.aibaixun.basic.toolkit;

import java.io.StringReader;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;


/**
 * 字符串工具类
 * @author wangxiao@aibaixun.com
 */
public class StringTool {


	public static boolean isBlank(Object obj) {
		if (null == obj) {
			return true;
		} else if (obj instanceof CharSequence) {
			return isBlank((CharSequence) obj);
		}
		return false;
	}


	public static boolean isBlank(CharSequence str) {
		int length;
		if ((str == null) || ((length = str.length()) == 0)) {
			return true;
		}
		for (int i = 0; i < length; i++) {
			if (!CharTool.isBlankChar(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static void trim(String ... strArray) {
		if (null == strArray) {
			return;
		}
		String str;
		for (int i = 0; i < strArray.length; i++) {
			str = strArray[i];
			if (null != str) {
				strArray[i] = str.trim();
			}
		}
	}


	public static String utf8Str(Object obj) {
		return str(obj, StandardCharsets.UTF_8);
	}


	public static String str(Object obj, String charsetName) {
		return str(obj, Charset.forName(charsetName));
	}


	public static String str(Object obj, Charset charset) {
		if (null == obj) {
			return null;
		}
		if (obj instanceof String) {
			return (String) obj;
		} else if (obj instanceof byte[]) {
			return str((byte[]) obj, charset);
		} else if (obj instanceof Byte[]) {
			return str((Byte[]) obj, charset);
		} else if (obj instanceof ByteBuffer) {
			return str((ByteBuffer) obj, charset);
		} else if (ArrayTool.isArray(obj)) {
			return ArrayTool.toString(obj);
		}
		return obj.toString();
	}

	public static String str(byte[] bytes, String charset) {
		return str(bytes, isBlank(charset) ? Charset.defaultCharset() : Charset.forName(charset));
	}


	public static String str(byte[] data, Charset charset) {
		if (data == null) {
			return null;
		}
		if (null == charset) {
			return new String(data);
		}
		return new String(data, charset);
	}


	public static String str(Byte[] bytes, String charset) {
		return str(bytes, isBlank(charset) ? Charset.defaultCharset() : Charset.forName(charset));
	}


	public static String str(Byte[] data, Charset charset) {
		if (data == null) {
			return null;
		}
		byte[] bytes = new byte[data.length];
		Byte dataByte;
		for (int i = 0; i < data.length; i++) {
			dataByte = data[i];
			bytes[i] = (null == dataByte) ? -1 : dataByte;
		}

		return str(bytes, charset);
	}


	public static String str(ByteBuffer data, String charset) {
		if (data == null) {
			return null;
		}
		return str(data, Charset.forName(charset));
	}


	public static String str(ByteBuffer data, Charset charset) {
		if (null == charset) {
			charset = Charset.defaultCharset();
		}
		return charset.decode(data).toString();
	}


	public static String toString(Object obj) {
		return String.valueOf(obj);
	}




	public static StringReader getReader(CharSequence str) {
		if (null == str) {
			return null;
		}
		return new StringReader(str.toString());
	}


	public static StringWriter getWriter() {
		return new StringWriter();
	}


	public static String reverse(String str) {
		return new StringBuilder(str).reverse().toString();
	}


	public static byte []  bytes(String str) {
		return  str.getBytes(StandardCharsets.UTF_8);
	}


	public static byte []  bytes(String str,Charset charset) {
		return  str.getBytes(charset);
	}

	public static String cleanBlank(CharSequence str) {
		return filter(str, c -> !CharTool.isBlankChar(c));
	}

	public static String filter(CharSequence str, final Function<Character,Boolean> filter) {
		if (str == null || filter == null) {
			return null;
		}
		int len = str.length();
		final StringBuilder sb = new StringBuilder(len);
		char c;
		for (int i = 0; i < len; i++) {
			c = str.charAt(i);
			if (filter.apply(c)) {
				sb.append(c);
			}
		}
		return sb.toString();
	}


}
