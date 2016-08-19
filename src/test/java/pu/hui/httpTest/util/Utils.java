package pu.hui.httpTest.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

	private static final char[] HEXDICT = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	private static String ATOM = "[a-z0-9!#$%&'*+/=?^_`{|}~-]";
	private static String DOMAIN = "(" + ATOM + "+(\\." + ATOM + "+)*";
	private static String IP_DOMAIN = "\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\]";
	private static Pattern pattern = Pattern.compile("^" + ATOM + "+(\\."
			+ ATOM + "+)*@" + DOMAIN + "|" + IP_DOMAIN + ")$", 2);

	public static String byteArrayToHexString(byte[] byteArray) {
		String result = "";
		int len = byteArray.length;
		for (int i = 0; i < len; ++i) {
			byte b = byteArray[i];
			result = result + HEXDICT[(b >>> 4 & 0xF)];
			result = result + HEXDICT[(b & 0xF)];
		}
		return result;
	}

	public static byte[] hexStringToByteArray(String str) {
		byte[] byteArray = new byte[str.length() / 2];
		int len = byteArray.length;
		int j = 0;
		for (int i = 0; i < len; ++i) {
			j = i << 1;
			byteArray[i] = 0;
			char c = str.charAt(j);
			if (('0' <= c) && (c <= '9')) {
				int tmp57_55 = i;
				byte[] tmp57_54 = byteArray;
				tmp57_54[tmp57_55] = (byte) (tmp57_54[tmp57_55] | c - '0' << 4);
			} else if (('A' <= c) && (c <= 'F')) {
				int tmp89_87 = i;
				byte[] tmp89_86 = byteArray;
				tmp89_86[tmp89_87] = (byte) (tmp89_86[tmp89_87] | c - 'A' + 10 << 4);
			} else if (('a' <= c) && (c <= 'f')) {
				int tmp124_122 = i;
				byte[] tmp124_121 = byteArray;
				tmp124_121[tmp124_122] = (byte) (tmp124_121[tmp124_122] | c - 'a' + 10 << 4);
			}

			++j;
			c = str.charAt(j);
			if (('0' <= c) && (c <= '9')) {
				int tmp166_164 = i;
				byte[] tmp166_163 = byteArray;
				tmp166_163[tmp166_164] = (byte) (tmp166_163[tmp166_164] | c - '0');
			} else if (('A' <= c) && (c <= 'F')) {
				int tmp196_194 = i;
				byte[] tmp196_193 = byteArray;
				tmp196_193[tmp196_194] = (byte) (tmp196_193[tmp196_194] | c - 'A' + 10);
			} else if (('a' <= c) && (c <= 'f')) {
				int tmp229_227 = i;
				byte[] tmp229_226 = byteArray;
				tmp229_226[tmp229_227] = (byte) (tmp229_226[tmp229_227] | c - 'a' + 10);
			}

		}

		return byteArray;
	}

	public static Date stringToDate(String stringTime, String pattern) {
		if ((stringTime != null) && (!(stringTime.isEmpty()))
				&& (pattern != null) && (!(pattern.isEmpty()))) {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			try {
				Date time = format.parse(stringTime);
				return time;
			} catch (ParseException e) {
			}
		}
		return null;
	}

	public static Date stringToDate(String stringTime) {
		return stringToDate(stringTime, "yyyyMMddHHmmss");
	}

	public static Calendar stringToCalendar(String stringTime, String pattern) {
		if ((stringTime != null) && (!(stringTime.isEmpty()))) {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			try {
				Date time = format.parse(stringTime);
				Calendar c = Calendar.getInstance();
				c.setTime(time);
				return c;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static Calendar stringToCalendar(String stringTime) {
		return stringToCalendar(stringTime, "yyyyMMddHHmmss");
	}

	public static String dateToString(Date date, String pattern) {
		if (date != null) {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			return format.format(date);
		}
		return null;
	}

	public static String dateToString(Date date) {
		return dateToString(date, "yyyyMMddHHmmss");
	}

	public static String calendarToString(Calendar c, String pattern) {
		if (c != null) {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			return format.format(c.getTime());
		}
		return null;
	}

	public static String calendarToString(Calendar c) {
		return calendarToString(c, "yyyyMMddHHmmss");
	}

	public static <T> String nvl(T value, String defValue) {
		return ((value != null) ? String.valueOf(value) : defValue);
	}

	public static <T> String nvl(T value) {
		return nvl(value, "");
	}

	public static String matchStr(String s) {
		if ((s != null) && (!(s.isEmpty())))
			return String.format("%%%1$s%%", new Object[] { s });

		return "%";
	}

	public static String matchString(String s) {
		if ((s != null) && (!(s.isEmpty())))
			return String.format("%%%1$s%%", new Object[] { s });

		return null;
	}

	public static long toLong(String s, long defValue) {
		Long result = toLong(s);
		return ((result != null) ? result.longValue() : defValue);
	}

	public static Long toLong(String s) {
		if (s != null)
			try {
				return Long.valueOf(s);
			} catch (NumberFormatException e) {
			}
		return null;
	}

	public static int toInteger(String s, int defValue) {
		Integer result = toInteger(s);
		return ((result != null) ? result.intValue() : defValue);
	}

	public static Integer toInteger(String s) {
		if (s != null)
			try {
				return Integer.valueOf(s);
			} catch (NumberFormatException e) {
			}
		return null;
	}

	public static void addAttr(StringBuilder params, String name, String value,
			boolean isFilter) {
		if (params.length() > 0)
			params.append(',');

		value = nvl(value, "");
		if ((!(value.equals(""))) && (isFilter)) {
			value = value.replaceAll("\\\\", "\\\\\\\\");
			value = value.replaceAll("'", "\\\\'");
			value = value.replaceAll("\"", "\\\\\"");
		}
		params.append(name).append("=").append("\"").append(value).append("\"");
	}

	public static void addAttr(StringBuilder params, String name, Long value) {
		addAttr(params, name, nvl(value, ""), false);
	}

	public static void addAttr(StringBuilder params, String name, Integer value) {
		addAttr(params, name, nvl(value, ""), false);
	}

	public static Calendar getStartOfDay(Calendar cal) {
		Calendar ret = Calendar.getInstance();
		ret.setTime(cal.getTime());
		ret.set(11, 0);
		ret.set(12, 0);
		ret.set(13, 0);
		ret.set(14, 0);
		return ret;
	}

	public static Calendar getEndOfDay(Calendar cal) {
		Calendar ret = Calendar.getInstance();
		ret.setTime(cal.getTime());
		ret.set(11, 23);
		ret.set(12, 59);
		ret.set(13, 59);
		ret.set(14, 0);
		return ret;
	}

	public static String md5(byte[] input) {
		if (input == null)
			return null;
		try {
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			algorithm.reset();
			algorithm.update(input);
			byte[] messageDigest = algorithm.digest();

			return byteArrayToHexString(messageDigest);
		} catch (NoSuchAlgorithmException e) {
		}
		return null;
	}

	public static boolean isEmail(String str) {
		if (str == null) {
			return false;
		}

		Matcher mat = pattern.matcher(str);
		return mat.find();
	}

	public static void main(String[] args) {
		// String str1 ="timestamp=1390807398098&secret=MKTV@FV.001";
		String str2 = "action=getuserfavorite&curuserid=43&token=3f52a6f33d94e4afdee4509c233a6e4c&start=0&num=10&timestamp=1390807398098&secret=MKTV@FV.001";
		// System.out.println("&signature=" + md5(str1.getBytes()));
		System.out.println("&signature=" + md5(str2.getBytes()));
		// &signature=83c5f81ba4e4ff95ac36a7b789baa153
	}
}
