package com.es.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringUtil {
	private static String[] units = { "", "十", "百", "千", "万", "十万", "百万", "千万", "亿", "十亿", "百亿", "千亿", "万亿" };
	private static char[] numArray = {' ','一', '二', '三', '四', '五', '六', '七', '八', '九' };
	private static final char[] NUM_ARRAY_CHINA = {'零', '一', '二', '三', '四', '五', '六', '七', '八', '九'};
	private static final Logger LOG = LoggerFactory.getLogger(StringUtil.class);
	private static long sequenceId = System.currentTimeMillis();
	private static String addrIp;
	//定义HTML标签的正则表达式
	private static final String regEx_html = "<[^>]+>";
	// 72个字符集
	private static char[] chars = { '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
			'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
			'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
			'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
			'Y', 'Z', '!', '@', '#', '$', '%', '^', '&', '*', '~', '|' };

	/**
	 * 数字校验正则，支持小数正负数
	 */
	private static final Pattern DIGITAL_PATTERN = Pattern.compile("^(-?\\d+)(\\.)?(\\d+)?$");
	/**
	 * 数字，减号/连接线，空格
	 */
	private static Pattern NUMBER_SPACE_MINUS = Pattern.compile("^[0-9][0-9\\ \\-]+[0-9]$");
	/**
	 * 数字
	 */
	private static  Pattern NUMBER_PATTERN = Pattern.compile("^0$|(^(\\+)?[1-9]([0-9]*))");
	/**
	 * 换行符、空格等字符
	 */
	private static  Pattern SPACE_PATTERN  = Pattern.compile("\\s*|\t|\r|\n");

  	private static Pattern isDouble = Pattern.compile("\\d+\\.\\d+$|-\\d+\\.\\d+$");

	/**
	 * 客户传入时间格式正则
	 */
	private static Pattern isDate=Pattern.compile("(20|19)[0-9]{2}(-|/|.|)[0-1][0-9](-|/|.|)[0-3][0-9]((( )[0-9][0-9](:)[0-9][0-9])|)");

 	private static Random random = new Random();
	/**
	 * 获得随机密码数组
	 *
	 * @param type
	 *            随机密码类型 type=1, 数字; type=2, 非数字; type=3, 所有字符, type=4 数字和英文小写字母
	 * @param passLength
	 *            随机密码长度
	 * @param count
	 *            随机密码个数
	 * @return 随机密码数组
	 */
	public static String[] getRandomPasswords(int type, int passLength,
											  int count) {
		String[] passwords = new String[count];// 新建一个长度为指定需要密码个数的字符串数组
		if (type == 1) {
			for (int i = 0; i < count; i++) {// 外循环 从0开始为密码数组赋值 也就是开始一个一个的生成密码
				StringBuilder password = new StringBuilder("");// 保存生成密码的变量
				for (int m = 1; m <= passLength; m++) {// 内循环 从1开始到密码长度 正式开始生成密码
					password.append(chars[random.nextInt(10)]);// 为密码变量随机增加上面字符中的一个
				}
				passwords[i] = password.toString();// 将生成出来的密码赋值给密码数组
			}
		} else if (type == 2) {
			for (int i = 0; i < count; i++) {// 外循环 从0开始为密码数组赋值 也就是开始一个一个的生成密码
				StringBuilder password = new StringBuilder("");// 保存生成密码的变量
				for (int m = 1; m <= passLength; m++) {// 内循环 从1开始到密码长度 正式开始生成密码
					password.append(chars[random.nextInt(53) + 10]);// 为密码变量随机增加上面字符中的一个
				}
				passwords[i] = password.toString();// 将生成出来的密码赋值给密码数组
			}
		} else if (type == 3) {
			for (int i = 0; i < count; i++) {// 外循环 从0开始为密码数组赋值 也就是开始一个一个的生成密码
				StringBuilder password = new StringBuilder("");// 保存生成密码的变量
				for (int m = 1; m <= passLength; m++) {// 内循环 从1开始到密码长度 正式开始生成密码
					password.append(chars[random.nextInt(72)]);// 为密码变量随机增加上面字符中的一个
				}
				passwords[i] = password.toString();// 将生成出来的密码赋值给密码数组
			}
		} else if (type == 4) {
			for (int i = 0; i < count; i++) {// 外循环 从0开始为密码数组赋值 也就是开始一个一个的生成密码
				StringBuilder password = new StringBuilder("");// 保存生成密码的变量
				for (int m = 1; m <= passLength; m++) {// 内循环 从1开始到密码长度 正式开始生成密码
					password.append(chars[random.nextInt(36)]);// 为密码变量随机增加上面字符中的一个
				}
				passwords[i] = password.toString();// 将生成出来的密码赋值给密码数组
			}
		} else {
			LOG.error("StringUtil.getRandomPasswords type参数输入错误!");
		}
		return passwords;
	}

	/**
	 * 获得单个随机密码
	 *
	 * @param type
	 *            随机密码类型 type=1, 数字; type=2, 非数字; type=3, 所有字符; type=4,
	 *            数字和英文小写字母
	 * @param passLength
	 *            随机密码长度
	 * @return 随机密码
	 *
	 */
	public static String getRandomPassword(int type, int passLength) {
		return getRandomPasswords(type, passLength, 1)[0];
	}

	/**
	 * 获得单个随机密码
	 *
	 * type = 3
	 *            随机密码类型 type=1, 数字; type=2, 非数字; type=3, 所有字符; type=4,
	 *            数字和英文小写字母
	 * @param passLength
	 *            随机密码长度
	 * @return 随机密码
	 */
	public static String getRandomPassword(int passLength) {
		return getRandomPassword(3, passLength);
	}

	public static synchronized String getNextId() {
		sequenceId++;
		if (addrIp == null) {
			try {
				InetAddress addr = InetAddress.getLocalHost();
				addrIp = addr.getHostAddress().toString().replace(".", "");

			} catch (UnknownHostException e) {

				e.printStackTrace();
				addrIp = ((Double) Math.random()).toString().substring(2, 14);
			}
		}
		System.out.println("sequenceId=" + addrIp + sequenceId);
		return "ID" + addrIp + sequenceId;
	}

	/**
	 * 将list转为以split分割的字符串，首尾都包含分割符，比如：|a|b|c|d|
	 * @param list 元数据
	 * @param split 分隔符
	 * @param <T> 数据类型
	 * @return 若列表为空或无数据返回空字符串，否则返回 将list转为以split分割的字符串
	 */
	public static <T> String listToString(List<T> list, String split) {
		if (list == null || list.size()==0) {
			return "";
		}
		StringBuilder buf = new StringBuilder();
		buf.append(split);
		for (T t : list) {
			buf.append(t.toString());
			buf.append(split);
		}
		return buf.toString();
	}

	public static String getUtf8(String str) {
		return getUtf8(str, null);
	}

	public static String getUtf8(String str, String encoding) {
		if (isEmpty(str)) {
			return null;
		}
		String retStr = null;
		try {
			if(!StringUtil.isEmpty(encoding)){
				retStr= new String(str.getBytes(encoding), "UTF-8");
				return retStr;
			}
			if(new String(str.getBytes("ISO-8859-1"),"ISO-8859-1").equals(str)){
				retStr = new String(str.getBytes("ISO-8859-1"), "UTF-8");
			}else if(new String(str.getBytes("UTF-8"),"UTF-8").equals(str)){
				retStr = str;
			}else if(new String(str.getBytes("GBK"),"GBK").equals(str)){
				retStr = new String(str.getBytes("GBK"), "UTF-8");
			}else if(new String(str.getBytes("GB2312"),"GB2312").equals(str)){
				retStr = new String(str.getBytes("GB2312"), "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
		return retStr;
	}

	/**
	 *
	 * @param str
	 * @param len
	 * @return 取str的最多len位，若str的长度小于len，返回全部
	 */
	public static String getSummary(String str, Integer len) {
		if (str == null || str.length() <= len) {
			return str;
		}
		return str.substring(0, len - 3) + "...";
	}

	public static String getSummary(String str, Integer len,String fix) {
		if (str == null || str.length() <= len) {
			return str;
		}

		return str.substring(0, len - fix.length()) + fix;

	}

	/* 将array转为以split分割的字符串，首尾都包含分割符，比如：|a|b|c|d| */
	public static <T> String arrayToString(T[] array, String split) {
		if(array == null ) {
            return "";
        }
		StringBuilder buf = new StringBuilder();
		buf.append(split);
		for (T t : array) {
			buf.append(t.toString());
			buf.append(split);
		}
		return buf.toString();
	}
	/**
	 * 将roleIds转为以List<String>
	 * @param roleIds 格式为|1|2|3|4|
	 * @return
	 */
	public static List<String> roleIdToList(String roleIds) {
		if (isEmpty(roleIds)) {
			return null;
		}

		String roleIdArr [] = roleIds.substring(1, roleIds.length() - 1).split("\\|");
		//获取不重复的roleId ID列表
		Set<String> roleIdsSet = new LinkedHashSet<String>();//按顺序
		for (String roleIdStr : roleIdArr) {
			roleIdsSet.add(roleIdStr);
		}

		List<String> roleIdList = new ArrayList<String>(roleIdsSet);

		return roleIdList;
	}

	/**
	 * 将roleIds转为以List<Integer>
	 * @param roleIds 格式为|1|2|3|4|
	 * @return
	 */
	public static List<Integer> roleIdToListInt(String roleIds) {
		if (isEmpty(roleIds)) {
			return null;
		}

		String roleIdArr [] = roleIds.substring(1, roleIds.length() - 1).split("\\|");

		//获取不重复的roleId ID列表
		Set<Integer> roleIdsSet = new LinkedHashSet<Integer>();//按顺序
		for (String roleIdStr : roleIdArr) {
			roleIdsSet.add(Integer.valueOf(roleIdStr));
		}

		List<Integer> roleIdList = new ArrayList<Integer>(roleIdsSet);

		return roleIdList;
	}

	/**
	 *
	 * @param   |1|2|3|4| 字符串 转化为list<Integer>格式
	 * @return  strings 格式为|1|2|3|4|
	 * @author chenhb
	 * @time 2016-7-14 下午3:50:27
	 */
	public static List<Integer> toList(String strings) {

		List<Integer> list = new ArrayList<Integer>();
		list=toList(strings,"|");
		return list;
	}
	/**
	 * @param  |1|2|3|4| 字符串 转化为list<Integer>格式
	 * @return  strings 格式为|1|2|3|4|
	 * @author chenkaiwei
	 * @time 2016-7-22 下午3:50:27
	 */
	public static List<Integer> toList(String strings,String spliteStr) {
		if (isEmpty(strings)) {
			return null;
		}
		switch(spliteStr){
			case "|":spliteStr="\\|";break;
			case "*":spliteStr="\\*";break;
			case "+":spliteStr="\\+";break;
			default:break;
		}
		List<Integer> list = new ArrayList<Integer>();
		String stringArr [] =   strings.split(spliteStr);
		for (String item : stringArr) {
			if(isEmpty(item)){//如果为空，则跳过
				continue;
			}
			list.add(Integer.valueOf(item));
		}
		return list;
	}

	/**
	 * @param   |1|2|3|4| 字符串 转化为list<String>格式
	 * @return  strings 格式为|1|2|3|4|
	 * @author zhengchuxiao
	 * @time 2016-7-22 下午3:50:27
	 */
	public static List<String> toStringList(String strings,String spliteStr) {
		if (isEmpty(strings)) {
			return null;
		}
		switch(spliteStr){
			case "|":spliteStr="\\|";break;
			case "*":spliteStr="\\*";break;
			case "+":spliteStr="\\+";break;
			default:break;
		}
		List<String> list = new ArrayList<>();
        String[] stringArr = strings.split(spliteStr);
		for (String item : stringArr) {
			if(isEmpty(item)){
			    //如果为空，则跳过
				continue;
			}
			list.add(item);
		}
		return list;
	}

    /**
     * |1|2|3|4| 字符串 转化为list<Long>格式
     * @param strings 格式为|1|2|3|4|
     * @param spliteStr
     * @return
     */
	public static List<Long> toLongList(String strings,String spliteStr) {
		if (isEmpty(strings)) {
			return new ArrayList<>();
		}
		switch(spliteStr){
			case "|":spliteStr="\\|";break;
			case "*":spliteStr="\\*";break;
			case "+":spliteStr="\\+";break;
			default:break;
		}
		List<Long> list = new ArrayList<>();
		String stringArr [] =   strings.split(spliteStr);
		for (String item : stringArr) {
			if(isEmpty(item)){//如果为空，则跳过
				continue;
			}
			list.add(Long.valueOf(item));
		}
		return list;
	}


	public static Double toDouble(String str ){
		return toDouble(str,0d);
	}
	public static Double toDouble(String str,Double defaultValue ){
		if (StringUtil.isEmpty(str)) {
            return defaultValue;
        }

		try {
			return Double.valueOf(str.trim());
		} catch (Exception e) {
			// e.printStackTrace();
//			LOG.warn("todefaultValue canot deal with arg:[" + str
//					+ "] ,and will renturn defaultValue " + defaultValue);
			return defaultValue;
		}
	}

	public static Float toFloat(String str ){
		return toFloat(str,0f);
	}
	public static Float toFloat(String str,Float defaultValue ){
		if (StringUtil.isEmpty(str)) {
            return defaultValue;
        }

		try {
			return Float.valueOf(str.trim());
		} catch (Exception e) {
			// e.printStackTrace();
//			LOG.warn("todefaultValue canot deal with arg:[" + str
//					+ "] ,and will renturn defaultValue " + defaultValue);
			return defaultValue;
		}
	}

	/**
	 * 将sex 性别和int的对应
	 * 性别的中文字 男 ，女，未知等
	 * @return ，0未知，1男，2女，3其他
	 */
	public static Integer getSexInt(String sex ){
		if(StringUtil.isEmpty(sex)){
			return 0;
		}else if("男".equals(sex) || "男性".equals(sex) ){
			return 1;
		}else if("女".equals(sex) || "女性".equals(sex)){
			return 2;
		}else {
			return 3;
		}

	}
	public static String getSexStr(Integer sex ){
		if(sex == null || sex ==0){
			return "未知";
		}else if(sex == 1  ){
			return "男";
		}else if(sex == 2 ){
			return "女";
		}else {
			return "其他";
		}
	}


	/* 将字符串转为Integer，作空指针和首尾空检查,如果异常，返回缺省值defaultValue */
	public static Integer toInt(String str, Integer defaultValue){
		return stringToInteger(  str,   defaultValue);
	}
	public static Integer toInt(String str ){
		return stringToInteger(  str,   null);
	}
	public static Integer stringToInteger(String str) {
		return stringToInteger(str, null);
	}

	public static Integer stringToInteger(String str, Integer defaultValue) {
		if (StringUtil.isEmpty(str)) {
			return defaultValue;
		}
		try {
			return Integer.valueOf(str.trim());
		} catch (Exception e) {
//			LOG.warn("StringToInteger canot deal with arg:[" + str
//					+ "] ,and will renturn defaultValue " + defaultValue);
			return defaultValue;
		}

	}


	/**
	 * 将字符串转为Integer，作空指针和首尾空检查,如果异常，返回缺省值defaultValue
	 * @param str
	 * @param defaultValue
	 * @return
	 */
	public static Long StringToLong(String str, Long defaultValue) {
		if (StringUtil.isEmpty(str)) {
            return defaultValue;
        }

		try {
			return Long.valueOf(str.trim());
		} catch (Exception e) {
			// e.printStackTrace();
//			LOG.warn("StringToInteger canot deal with arg:[" + str
//					+ "] ,and will renturn defaultValue " + defaultValue);
			return defaultValue;
		}
	}

	public static Long StringToLong(String str) {
		return StringToLong(str, null);
	}

	public static boolean isEmpty(String value, boolean trim, char... trimChars) {
		if (trim) {
			return value == null || trim(value, trimChars).length() <= 0;
		}
		return value == null || value.length() <= 0;
	}

	public static boolean isEmpty(String value, boolean trim) {
		return isEmpty(value, trim, ' ');
	}

	public static boolean isEmpty(String value) {
		return isEmpty(value, true);
	}

	public static boolean isNotEmpty(String value, boolean trim) {
		return !isEmpty(value, trim, ' ');
	}

	public static boolean isNotEmpty(String value) {
		return isNotEmpty(value, true);
	}

	public static String nullSafeString(String value) {
		return value == null ? "" : value;
	}

	public static String trim(String value) {
		value = trim(3, value, ' ');//英文
		value = trim(3, value, '　');//中文全角空格
		return value;
	}
	//去除字符串中所有的换行符
	public static String trimNewLines(String value){
		while (value.contains("\n")){
			value = value.replace('\n','/').replaceAll("/","");
		}
		while (value.contains("\\n")){
			value = value.replaceAll("\\\\n","");
		}
		return  value;
	}
	public static String trim(String value, char... chars) {
		return trim(3, value, chars);
	}

	public static String trimStart(String value, char... chars) {
		return trim(1, value, chars);
	}

	public static String trimEnd(String value, char... chars) {
		return trim(2, value, chars);
	}

	private static String trim(int mode, String value, char... chars) {
		if (value == null || value.length() <= 0) {
            return value;
        }
		value = value.trim();
		int startIndex = 0, endIndex = value.length(), index = 0;
		if (mode == 1 || mode == 3) {
			// trim头部
			while (index < endIndex) {
				if (contains(chars, value.charAt(index++))) {
					startIndex++;
					continue;
				}
				break;
			}
		}

		if (startIndex >= endIndex) {
            return "";
        }

		if (mode == 2 || mode == 3) {
			// trim尾部
			index = endIndex - 1;
			while (index >= 0) {
				if (contains(chars, value.charAt(index--))) {
					endIndex--;
					continue;
				}
				break;
			}
		}

		if (startIndex >= endIndex) {
            return "";
        }

		return value.substring(startIndex, endIndex);
	}
	
	private static boolean contains(char[] chars, char chr) {
		if (chars == null || chars.length <= 0) {
            return false;
        }
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == chr) {
                return true;
            }
		}
		return false;
	}

	public static String URLDecode(String str) {
		if (str == null) {
            return "";
        }
		try {
			return URLDecoder.decode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
			LOG.warn("URLDecode UnsupportedEncodingException" + str);
			return str;
		} catch (Exception e) {
			e.printStackTrace();
			LOG.warn("URLDecode Exception" + str);
			return str;
		}
	}

	/**
	 * 判断输入的字符串是否为自然数
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		if(str == null || "".equals(str)) {
			return false;
		}
		Matcher isNum = NUMBER_PATTERN.matcher(str);
		return isNum.matches();
	}

	/**
	 * 判断是否为数字，不在乎小数后位数
	 * 重构方法
	 */
	public static boolean isDigital(String str) {
		return isDigital(str, null);
	}

	/**
	 * 判断输入的字符串是否为数字（包含正负数字）
	 *
	 * @param str 匹配字符串
     * @param limitNum 小数位限制 （null则不限制小数位 ，2表示最多保留小数点后两位）
	 * @return
	 * @author 郑初晓
	 * 创建时间： 2018/1/26  17:34
	 * 修改时间： 2018/3/15
	 * @version
	 * @since v3.17
	 */
    public static boolean isDigital(String str, Integer limitNum) {
        if (str == null || "".equals(str)) {
            return false;
        }

		Matcher isNum;
		if (limitNum == null) {
			isNum = DIGITAL_PATTERN.matcher(str);
		} else {
			isNum = Pattern.compile("^(-?\\d+)(\\.)?(\\d{0," + limitNum + "})$").matcher(str);
		}

        return isNum.matches();
    }


	/**
	 * 验证所有电话格式 总结起来就是第一位必定为1，第二位必定为3或4或5或7或8,6,9，其他位置的可以为0-9, 区号为86可不填
	 */
	public static boolean isMobile(String mobiles) {
    /*
	    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
	    联通：130、131、132、152、155、156、185、186
	    电信：133、153、180、189、（1349卫通）新增号段：190、192、196、197(5G)
	    网络电话: 17
	    总结起来就是第一位必定为1，第二位必定为3或5或7或8，其他位置的可以为0-9, 区号为86可不填
	
	    香港号码：区号为00852或852 香港手机号码是5/6/9开头的8位数字的号码,座机是2/3开头的8位数的号码
	
	    台湾号码:区号为00886或886 台湾手机10位数，皆以09起头，拨打台湾手机，先拨台湾的国际区码00886，接着拨去起头0的手机号码，譬如0960XXXXXX，则拨00886-960XXXXXX
    */
		String telRegex = "(^(86)?[1][3456789]\\d{9}$)|(^(00852|852)?[569]\\d{7}$)|(^(00886|886)?0?[9]\\d{8}$)";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		if (isEmpty(mobiles)) {
            return false;
        } else {
            return mobiles.matches(telRegex);
        }
	}

	/**
	 * 验证手机号格式     type为"china"
	 */
	public static boolean isMobile(String mobiles,String type) {
    /*
	    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
	    联通：130、131、132、152、155、156、185、186
	    电信：133、153、180、189、（1349卫通）新增：190、192、196、197(5G)
    */
		String telRegex = "";
		if("china".equals(type)){
			telRegex = "(^(86)?[1][3456789]\\d{9}$)";
		}

		if (isEmpty(mobiles)) {
            return false;
        } else {
            return mobiles.matches(telRegex);
        }
	}

	/**
	 * 验证手机格式
	 */
	public static boolean isPhone(String phone) {
		if (isEmpty(phone)) {
			return false;
		} else {
			phone = trim(phone);
			Matcher matcher = NUMBER_SPACE_MINUS.matcher(phone);
			return matcher.matches();
		}
	}

	public static boolean isEmail(String str){
		if(str == null || "".equals(str)) {
			return false;
		}
		String regExp = "[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+";
		return str.matches(regExp);
	}

	public static boolean isIdcard(String str){
		if(str == null || "".equals(str)) {
			return false;
		}
		String regExp15="^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
		String regExp18 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";

		//定义判别用户身份证号的正则表达式（要么是15位，要么是18位，最后一位可以为字母） 
		Matcher idNumMatcher=null;
		if(str.length()==15){
			Pattern idNumPattern = Pattern.compile(regExp15);
			idNumMatcher = idNumPattern.matcher(str);
		}else{
			Pattern idNumPattern = Pattern.compile(regExp18);
			idNumMatcher = idNumPattern.matcher(str);
		}


		return idNumMatcher.matches();
	}

	public static String ifEmptyThenDefault(String str, String defaultStr) {
		if(isEmpty(str)) {
			return defaultStr;
		}
		return str;
	}
	/**
	 * 把content中的html<...>替换为“” ，但是不能替换urlencode之后的结果，
	 * @param content
	 * @return
	 * kaka
	 * 2017年4月24日 下午3:09:43
	 */
	public static String replaceHtml(String content)
	{
		if (isEmpty(content)) {
			return "";
		}
		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(content);
		content = m_html.replaceAll("");
		content = content.replaceAll("&nbsp;", "");
		return content;
	}


	/**
	 * 把content中的html标签左括号 < %3C 都替换为“”，这个只用于破坏脚本注入攻击
	 * @param content
	 * @return
	 * kaka
	 * 2017年4月24日 下午3:08:33
	 */
	public static String replaceHtmlTag(String content)
	{
		if (isEmpty(content)) {
			return "";
		}

		content = content.replaceAll("<", "");
		content = content.replaceAll("%3C", "");
		return content;
	}

	/**
	 * 输出内容保留空格，换行等
	 * @param content
	 * @return
	 */
	public static String outHtml(String content) {
		if (isEmpty(content)) {
			return "";
		}

		content = ((content.replaceAll("<(.+?)>","&lt;$1&gt;")).replaceAll(" ","&nbsp;")).replaceAll("\n","<br>");

		return content;
	}


	/**
	 * 过滤sql 注入的语法 , = '
	 * @param content
	 * @return
	 */
	public static String filterSql(String content) {
		if (isEmpty(content)) {
			return "";
		}

		content = content.replaceAll("'","").replaceAll(",","").replaceAll("=","");

		return content;
	}


	/**
	 * 判断一个图片路径是否为http开头，或则/开头，或则ID开头，然后返回一阁url路径
	 *
	 * @param image
	 */
	public static String getImgUrl(String image) {
		return getImgUrl(image, "");
	}

	/**
	 * 判断一个图片路径是否为http开头，或则/开头，或则ID开头，然后返回一阁url路径
	 *
	 * @param image
	 * @param baseUrl
	 */

	public static String getImgUrl(String image, String baseUrl) {
		if (isEmpty(baseUrl)) {
			baseUrl = "";
		}
		if(isEmpty(image)){
			return "";
		}
		if (image.startsWith("http")) {
			return image;
		} else if (image.startsWith("/")  ) {
			return baseUrl + image;
		} else if ( image.startsWith("API") ) {
			return baseUrl +"/"+ image;
		} else {
			return baseUrl + "/API/image/get.do?imageId=" + image;
		}
	}
	/**
	 * 判断一个文本是否包换屏蔽词，包含则用*替换
	 *
	 * @param text
	 * @param keyword []
	 */
	public static String keywordsShield(String text, String []keyword) {
		if(keyword.length==0){
			return text;
		}
		for(int i=0;i<keyword.length;i++){
			text=text.replaceAll(keyword[i], "*");
		}
		return text;
	}

	/**
	 * 隐藏手机号中间4位
	 * @param phone
	 * @return
	 */
	public static String phoneHideMid(String phone) {
		if (isEmpty(phone)) {
			return "";
		}
		char[] temp = phone.toCharArray();
		String ret = "";
		for (int i = 0; i < temp.length; ++i) {
			if (i==3 || i==4 || i==5 || i==6) {
				temp[i] = '*';
			}
			ret += temp[i];
		}

		return ret;
	}
	/**
	 * 判断字符串分割后是否含有目标的字符串
	 * @param str 源字符串
	 * @param split 分隔符
	 * @param tarStr 目标字符串
	 * @return
	 */
	public static boolean contains(String str, String split, String tarStr) {
		if (isEmpty(str) || isEmpty(split) || isEmpty(tarStr)) {
			return false;
		}
		String[] splitedStr = str.split(split);
		for (String singleStr : splitedStr) {
			if (tarStr.equals(singleStr)) {
				return true;
			}
		}
		return false;
	}

	/**
	 *
	 * @param doub
	 * @param formatStr 转换格式  默认为钱币格式(#,##0.00) 如:123,456,789.12   
	 * 					还可以是###0.0或###0.00等
	 * @return
	 */
	public static String  formatDouble (Object doub, String formatStr){
		if (doub == null) {
			return "0.00";
		}
		if (isEmpty(formatStr)) {
			formatStr = "#,##0.00";
		}

		//格式化设置
		DecimalFormat decimalFormat = new DecimalFormat(formatStr);
		return decimalFormat.format(doub);
	}
	public static String  formatDouble (Object doub){
		if (doub == null) {
			return "0.00";
		}
		return formatDouble(doub, "#,##0.00");
	}
	
	public static String formatDouble(Double doub, int decimalDigit, int divisor){
		if (doub == null) {
			return "0.00";
		}
		if(Double.isNaN(doub)) {
			return "0.00";
		}
		if (divisor == 0) {
			// 除数不能为0
			divisor = 1;
		}

		return BigDecimal.valueOf(doub).divide(new BigDecimal(divisor)).setScale(decimalDigit, BigDecimal.ROUND_HALF_UP).toPlainString();
	}

	/**
	 * 格式化数据，返回金额，两位小数
	 * @param doub 数字
	 * @return 2位小数纯数字
	 */
	public static String formatDecimal(Double doub){
		return formatDouble(doub,2,1);
	}


	/**
	 * 分割字符串
     *
     * 替代方法{@link StringUtil#splitByMark}
	 */
	@Deprecated
	public static List<String> getStringList(String strs){
		return splitByMark(strs, ",", "，");
	}

    /**
     * 分割字符串
     *
     * 替代方法{@link StringUtil#splitByMark}
     */
    @Deprecated
    public static List<String> getListByCommaStr(String strs){
        return splitByMark(strs, ",", "，");
    }
    
    public static List<String> splitByMark(String ready2SplitText, String mark1, String mark2) {
        List<String> resultList = new ArrayList<>();
        if (ready2SplitText == null) {
            return resultList;
        }
        String[] splitArray = ready2SplitText.split(mark1);
        for (String item : splitArray) {
            String[] itemArray = item.split(mark2);
            Collections.addAll(resultList, itemArray);
        }
        return resultList;
    }

	public static Integer indexof(String str, String searchvalue){
		return str.indexOf(searchvalue);
	}
	
	/**
	 * 过滤特殊字符表情方法
	 * @param codePoint
	 * @return
	 */
	public static boolean isNotEmojiCharacter(char codePoint){
		return (codePoint == 0x0) ||
				(codePoint == 0x9) ||
				(codePoint == 0xA) ||
				(codePoint == 0xD) ||
				((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
				((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
				((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
	}
	
	public static boolean hasEmojiCharacter(String source){
		if (StringUtil.isEmpty(source)) {
			return false;
		}
		int len = source.length();
		for (int i = 0; i < len; i++) {
			if(!isNotEmojiCharacter( source.charAt(i) )){//找到emoji符号
				return true;
			}
		}

		return false;
	}

	/**
	 * 过滤emoji 或者 其他非文字类型的字符
	 * @param source
	 * @return
	 */
	public static String filterEmoji(String source){
		if (StringUtil.isEmpty(source)) {
			return "";
		}
		//优化性能，如果不含emoji，则直接返回
		if(!hasEmojiCharacter(source)){
			return source;
		}
		int len = source.length();
		StringBuilder buf = new StringBuilder(len);
		for (int i = 0; i < len; i++)
		{
			char codePoint = source.charAt(i);
			if (isNotEmojiCharacter(codePoint))
			{
				buf.append(codePoint);
			}else {
				buf.append("*");
			}
		}
		return buf.toString();
	}

	public static String intToChineseNum(int num) {
		char[] val = String.valueOf(num).toCharArray();
		int len = val.length;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			String m = val[i] + "";
			int n = Integer.valueOf(m);
			boolean isZero = n == 0;
			String unit = units[(len - 1) - i];
			if (isZero) {
				if ('0' == val[i - 1]) {
					// not need process if the last digital bits is 0
					continue;
				} else {
					// no unit for 0
					sb.append(numArray[n]);
				}
			} else {
				if(len < 3 && len > 1){
					if(i != 0 || n != 1){
						sb.append(numArray[n]);
					}
				}else{
					sb.append(numArray[n]);
				}

				sb.append(unit);
			}
		}
		return sb.toString();
	}

	/**
	 * 将银行号除了后4位变为*号
	 * @param bankCode
	 * @return
	 */
	public static String hideBankCode(String bankCode) {
		if (isEmpty(bankCode)) {
			return "";
		}
		if (bankCode.length() < 4 ) {
			return bankCode;
		}

		String beforeCode = bankCode.substring(0, bankCode.length()-4);//除了后4位
		String lastCode = bankCode.substring(bankCode.length()-4);//后4位

		String star = "";
		for (int i = 0; i < beforeCode.length(); i++) {
			star += "*";
		}

		return star + lastCode;
	}
	
	public static String repeat(String src, int num) {
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < num; i++) {
            s.append(src);
        }
		return s.toString();
	}

	/**
	 * 返回byte的数据大小对应的文本
	 * @param size
	 * @return
	 */
	public static String getDataSize(long size){
		DecimalFormat formater = new DecimalFormat("####.00");
		if(size<1024){
			return size+"bytes";
		}else if(size<1024*1024){
			float kbsize = size/1024f;
			return formater.format(kbsize)+"KB";
		}else if(size<1024*1024*1024){
			float mbsize = size/1024f/1024f;
			return formater.format(mbsize)+"MB";
		}else if(size<1024*1024*1024*1024){
			float gbsize = size/1024f/1024f/1024f;
			return formater.format(gbsize)+"GB";
		}else{
			float gbsize = size/1024f/1024f/1024f;
			return formater.format(gbsize)+"GB";
		}
	}
	
	public static String camelToUnderscore(String source) {
		String answer;

		if (source == null) {
			answer = null;
		} else if (source.length() < 2) {
			answer = source.toLowerCase(Locale.US);
		} else {
			if (Character.isUpperCase(source.charAt(0))
					&& !Character.isUpperCase(source.charAt(1))) {
				answer = source.substring(0, 1).toLowerCase(Locale.US)
						+ source.substring(1);
			} else {
				answer = source;
			}
		}

		return answer;
	}

	/**
	 * 将首字母转换为大写
	 * @param str
	 * @return
	 */
	public static String upperFirstChar(String str) {
		char[] chars = new char[1];
		chars[0] = str.charAt(0);
		String tempStr = new String(chars);
		if (chars[0] >= 'a' && chars[0] <= 'z') {
			str = str.replaceFirst(tempStr, tempStr.toUpperCase());
		}
		return str;
	}

	/**
	 * 将首字母转换为小写
	 * @param str
	 * @return
	 */
	public static String lowerFirstChar(String str) {
		char[] chars = new char[1];
		chars[0] = str.charAt(0);
		String tempStr = new String(chars);
		if (chars[0] >= 'A' && chars[0] <= 'Z') {
			str = str.replaceFirst(tempStr, tempStr.toLowerCase());
		}
		return str;
	}

	/**
	 * 查重队列中的重复部分，返回干净的队列
	 * @param list 需要处理的队列
	 * @return 排重处理后的队列
	 */
	public static List<String> duplicateChecking(List<String> list) {
		if (list == null || list.size() == 0) {
			return new ArrayList<>();
		}
		Set tempSet = new HashSet();
		tempSet.addAll(list);
		list.clear();
		list.addAll(tempSet);
		return list;
	}
	
	public static String replaceBlank(String str) {
		String dest = "";
		if (str!=null) {
			Matcher m = SPACE_PATTERN.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
	
}
