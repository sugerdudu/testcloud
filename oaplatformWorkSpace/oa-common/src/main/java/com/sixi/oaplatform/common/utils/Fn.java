package com.sixi.oaplatform.common.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 公用方法
 *
 * @Author 艾翔
 * @Date 2017/8/28 14:42
 */
public class Fn {
	private final static Pattern intPaddern = Pattern.compile("^[+-]?[0-9]+$");

	/**
	 * 判断字符串是否可以转int
	 *
	 * @param str 字符串
	 * @return
	 */
	public static Boolean isInt(String str) {
		if (str == null) {
			return false;
		}

		return intPaddern.matcher(str).find();
	}

	/**
	 * 判断对象是否可以转int
	 *
	 * @param o
	 * @return
	 */
	public static Boolean isInt(Object o) {
		if (o == null) {
			return false;
		}
		String str = o.toString();

		Matcher mer = intPaddern.matcher(str);
		return mer.find();
	}

	/**
	 * 对象转int(默认是对象的toString())，失败返回 failureVal
	 *
	 * @param o
	 * @param failureVal
	 * @return
	 */
	public static Integer toInt(Object o, int failureVal) {
		if (o == null) {
			return failureVal;
		}
		String str = o.toString();

		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException ex) {
			return failureVal;
		}
	}

	/**
	 * 对象转int(默认是对象的toString())，失败返回 0
	 *
	 * @param o
	 * @return
	 */
	public static Integer toInt(Object o) {
		if (o == null) {
			return 0;
		}
		String str = o.toString();
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException ex) {
			return 0;
		}
	}

	/**
	 * 字符串重新组合 “1,2,a,3,4” -> 1,2,3,4
	 *
	 * @param t
	 * @return
	 */
	public static String clearNoInt(String t) {
		String[] arr = t.split(",");
		List<String> list = new ArrayList<String>();
		for (String val : arr) {
			if (isInt(val)) {
				list.add(val);
			}
		}

		return String.join(",", list);
	}

	/**
	 * 过滤字符串前后空格，支持全角
	 *
	 * @param str
	 * @return 若为null，返回空字符串
	 */
	public static String trim(String str) {
		if (str == null) {
			return "";
		}
		//第1个：全角空格 encodeURI('　')=%E3%80%80
		//第2个：半角空格 encodeURI(' ')=%20
		//第3个：特殊空格 encodeURI(' ')=%C2%A0
		while (str.startsWith("　") || str.startsWith(" ") || str.startsWith(" ")) {// 全角空格
			str = str.substring(1, str.length()).trim();
		}
		while (str.endsWith("　") || str.endsWith(" ") || str.endsWith(" ")) {// 全角空格
			str = str.substring(0, str.length() - 1).trim();
		}
		return str;
	}

	/**
	 * 过滤字符串前后空格，支持全角
	 *
	 * @param o
	 * @return 若为null，返回空字符串
	 */
	public static String trim(Object o) {
		if (o == null) {
			return "";
		}
		return trim(o.toString());
	}

	public static String md5(String s) {
		return DigestUtils.md5Hex(s);
	}

	public static String md5_16(String s) {
		return DigestUtils.md5Hex(s).substring(8, 24);
	}

	/**
	 * 获取当前登录人id
	 *
	 * @return
	 */
	public static Integer getLoginUserId() {
		Integer userid = Session.getSession("userid");
		return Fn.toInt(userid);
	}

	/**
	 * 获取当前登录人一级部门
	 *
	 * @return
	 */
	public static Integer getLoginClass1() {
		Integer class1 = Session.getSession("class1");
		return Fn.toInt(class1);
	}

	/**
	 * 获取当前登录人二级部门
	 *
	 * @return
	 */
	public static Integer getLoginClass2() {
		Integer class2 = Session.getSession("class2");
		return Fn.toInt(class2);
	}

	public static Integer getLoginManager(){
		Integer manager = Session.getSession("manager");
		return Fn.toInt(manager);
	}

	/**
	 * 对象转字符串，当null时，返回空字符串，并去除前后空格
	 *
	 * @param o
	 * @return
	 */
	public static String toString(Object o) {
		if (o == null) {
			return "";
		}
		return o.toString().trim();
	}

	/**
	 * 对象转double，失败返回0
	 *
	 * @param o
	 * @return
	 */
	public static Double toDouble(Object o) {
		if (o == null) {
			return 0d;
		}
		String str = o.toString();

		try {
			return Double.parseDouble(str);
		} catch (NumberFormatException ex) {
			return 0d;
		}
	}

	public static BigDecimal toBigDecimal(Object o){
		if(o == null){
			return new BigDecimal("0");
		}
		String str = o.toString();

		try {
			return new BigDecimal(str);
		}catch(NumberFormatException ex){
			return new BigDecimal("0");
		}
	}

	/**
	 * 判断字符串是否可以转 指定的时间类型
	 *
	 * @param strDate
	 * @param dateFormat 时间格式 yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static Boolean isDateFormat(String strDate, String dateFormat) {
		if (strDate == null || "".equals(strDate)) {
			return false;
		}

//		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
//		format.setLenient(false);
//		try {
//			format.parse(strDate);
//			return true;
//		} catch (ParseException e) {
//			return false;
//		}

		DateFormat formatter = new SimpleDateFormat(dateFormat);
		try{
			Date date = (Date)formatter.parse(strDate);
			return strDate.equals(formatter.format(date));
		}catch(Exception e){
			return false;
		}
	}

	/**
	 * 字符串传时间类型带
	 *
	 * @param datestr 传入时间字符串 如:2016-01-01,2016-01-01 00:00:00
	 * @return 字符串转时间类型, 如果转换成功返回类型为时间类型：2016-01-01 00：00：00，转换失败返回null
	 */
	public static Timestamp toTimestamp(String datestr) {
		datestr = datestr.length() == 10 ? datestr + " 00:00:00" : datestr;
		if (!isDateFormat(datestr, "yyyy-MM-dd HH:mm:ss")) {
			return null;
		}
		return Timestamp.valueOf(datestr);
	}

	/**
	 * 获取当前时间字符串格式 yyyy-MM-dd HH:mm:ss
	 *
	 * @return
	 */
	public static String getNowStr() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date());
	}

	/**
	 * 获取Timestamp 类型当前系统时间
	 * @return
	 */
	public static Timestamp getNowTimestamp(){
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 获取单前时间字符串  格式 yyyy-MM
	 * @return
	 */
	public static String getNowYMStr(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		return format.format(new Date());
	}

	/**
	 * BeanPropertyRowMapper.newInstance(mappedClass);
	 * @param mappedClass
	 * @return
	 */
	public static <T> BeanPropertyRowMapper<T> rowMapper(Class<T> mappedClass){
		return BeanPropertyRowMapper.newInstance(mappedClass);
	}

	/**
	 * 时间转年月
	 * @param date Date
	 * @return
	 */
	public static String toDateStrYM(Date date) {
		return toDateTimeStr(date, "yyyy-MM");
	}

	/**
	 * 转时间字符串格式 （如 年-月-日 时:分:秒 毫秒 格式为 yyyy-MM-dd HH:mm:ss SSS）
	 *
	 * @param date
	 * @param dateFormat
	 *            指定格式
	 * @return 转换失败反回空字符串
	 */
	public static String toDateTimeStr(Date date, String dateFormat) {
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		format.setLenient(false);
		try {
			return format.format(date);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 给时间添加天
	 *
	 * @param d
	 * @param num
	 * @return
	 */
	public static Date addDate(Date d, int num) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.DATE, c.get(Calendar.DATE) + num);
		return c.getTime();
	}

	/**
	 * 给时间添加天
	 *
	 * @param num
	 * @return
	 */
	public static Date addDate(int num) {
		return Fn.addDate(new Date(), num);
	}

	/**
	 * 给时间添加（日，月，年）
	 *
	 * @param d
	 * @param num
	 * @param dateType
	 *            y:年 m：月 d：日
	 * @return
	 */
	public static Date addDate(Date d, int num, String dateType) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		if (dateType.equals("y")) {
			c.set(Calendar.YEAR, c.get(Calendar.YEAR) + num);
		} else if (dateType.equals("m")) {
			c.set(Calendar.MONTH, c.get(Calendar.MONTH) + num);
		} else if (dateType.equals("d")) {
			c.set(Calendar.DATE, c.get(Calendar.DATE) + num);
		}
		return c.getTime();
	}

	public static <T> PageList<T> listToPage(Collection<T> list, int pageIndex, int pageSize) {
		if (pageIndex <= 0) {
			pageIndex = 1;
		}
		if (pageSize <= 0) {
			pageSize = 1;
		}

		int rowTotal = list.size();// 总条数
		int pageTotal = rowTotal / pageSize;// 总页数
		int startIndex = (pageIndex - 1) * pageSize;// 所在页码的开始索引
		int endIndex = startIndex + pageSize;// 所在页码的结束索引

		// 若最后一页不满pageSize条
		if (rowTotal % pageSize > 0) {
			pageTotal++;
		}

		List<T> tmpResult = new ArrayList<>();// 分页后的结果集

		int itIndex = 0;// 迭代器索引
		Iterator<T> it = list.iterator();
		while (it.hasNext()) {
			if (itIndex >= endIndex) {
				break;// 找完后退出
			}

			T val = (T) it.next();
			// 若页码在指的开始索引和结束索引，则加入结果集
			if (startIndex <= itIndex && itIndex < endIndex) {
				tmpResult.add(val);
			}
			itIndex++;
		}

		PageList<T> result = new PageList<>(tmpResult);
		result.setPageSize(pageSize);
		result.setPageIndex(pageIndex);
		result.setTotal(rowTotal);
		return result;
	}

	/**
	 * 转换前端传回的 1,2,3 字符串类型为List集合
	 * @param t
	 * @return
	 */
	public static List<Integer> intStrToList(String t) {
		if(t==null||t==""){
			return new ArrayList<>();
		}
		t=t.replace("，",",");
		String[] arr = t.split(",");
		List<Integer> list = new ArrayList<>();
		for (String val : arr) {
			if (isInt(val)) {
				list.add(Fn.toInt(val));
			}
		}
		return list;
	}

	/**
	 * 获取两个日期间的所有月份集合
	 * @param minDate
	 * @param maxDate
	 * @return
	 * @throws ParseException
	 */
	public static List<String> getMonthBetween(String minDate, String maxDate) {
		ArrayList<String> result = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月

		Calendar min = Calendar.getInstance();
		Calendar max = Calendar.getInstance();

		try {
			min.setTime(sdf.parse(minDate));
		}catch (ParseException pe){
			return new ArrayList<String>();
		}
		min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

		try {
			max.setTime(sdf.parse(maxDate));
		}catch (ParseException pe){
			return new ArrayList<String>();
		}
		max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

		Calendar curr = min;
		while (curr.before(max)) {
			result.add(sdf.format(curr.getTime()));
			curr.add(Calendar.MONTH, 1);
		}

		return result;
	}

	/**
	 * 判断字符串是否为空，去除前后空格
	 *
	 * @param str
	 * @return true:空的 false:非空
	 */
	public static Boolean isStrEmpty(String str) {
		return str == null || trim(str).isEmpty();
	}

	/**
	 * 字符串转Date，若字符串非 dateFormat 格式，则返回null
	 *
	 * @param strDate
	 * @param dateFormat 需要转换的日期格式，如yyyy-MM
	 * @return 转换失败返回null
	 */
	public static Date toDate(String strDate, String dateFormat) {
		return toDate(strDate, dateFormat, null);
	}

	/**
	 * 字符串转Date，若字符串非 dateFormat 格式，则返回defaultValue
	 *
	 * @param strDate
	 * @param dateFormat   需要转换的日期格式，如yyyy-MM
	 * @param defaultValue 转换失败返回的默认值
	 * @return
	 */
	public static Date toDate(String strDate, String dateFormat, Date defaultValue) {
		if (isStrEmpty(strDate)) {
			return defaultValue;
		}
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		format.setLenient(false);//false：比较宽松的日期验证方式
		try {
			return format.parse(strDate);
		} catch (ParseException e) {
			return defaultValue;
		}
	}

}
