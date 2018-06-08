package com.tstdct.lib;

import java.util.regex.Pattern;

public class ValidateUtil
{
	/**
	 * 验证密码
	 * 由6-20位数字、字母、一般符号组成的
	 * */
	public static boolean validateOfPassword(String password){
		String regex="[!@#$%\\^&*()/\\+\\[\\]\\{\\}, .?;:<>|\\w]{6,20}";
		boolean result=Pattern.matches(regex, password);
		return result;
	}
	
	/**
	 * 验证手机
	 * 由1开始11位手机号码
	 * */
	public static boolean validateOfPhone(String phone){
		String regex="(\\+86)?[1]\\d{2}\\d{8}";
		boolean result=Pattern.matches(regex, phone);
		return result;
	}
	
	/**
	 * 验证QQ
	 * 由1-9开始5位以上的QQ号码
	 * */
	public static boolean validateOfQQ(String qqNumber){
		String regex="[1-9][0-9]{4,}";
		boolean result=Pattern.matches(regex, qqNumber);
		return result;
	}
	
	/**
	 * 验证邮箱
	 * 基本格式为“内容@内容.内容”
	 * */
	public static boolean validateOfEmail(String email){
		String regex="\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		boolean result=Pattern.matches(regex, email);
		return result;
	}
	
	/**
	 * 验证网址
	 * 可验证http或https或没有开头的网址
	 * */
	public static boolean validateOfWebUrl(String url){
		String regex="^(([hH][tT]{2}[pP][sS]?)|([fF][tT][pP]))\\:\\/\\/[wW]{3}\\.[\\w-]+\\.\\w{2,4}(\\/.*)?$";
		boolean result=Pattern.matches(regex, url);
		return result;
	}

	public static boolean validateOfIdCard(String idCardNumber){
//		String regex="^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";
//		boolean result = Pattern.matches(regex,idCardNumber);

		return IdCardValidateUtil.getInstance().isIDCard(idCardNumber);
	}

	public static boolean validateNumber(String validate){
		String regex="\\d+";
		boolean result= Pattern.matches(regex, validate);
		return result;
	}
}
