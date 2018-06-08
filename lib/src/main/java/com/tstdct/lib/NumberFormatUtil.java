package com.tstdct.lib;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class NumberFormatUtil {
	private static DecimalFormat decimalFormat = new DecimalFormat("0.00");
	private static DecimalFormat decimalDecimalFormat = new DecimalFormat("0.000");
	private static DecimalFormat decimalIntFormat = new DecimalFormat("0");
	private static DecimalFormat decimalPriceFormat = new DecimalFormat("###,###");
	private static DecimalFormat decimalTimeFormat = new DecimalFormat("00");
	private static DecimalFormat decimalOneFormat = new DecimalFormat("0.0");

	public static String getFormatStr(double value) {
		return decimalFormat.format(value);
	}

	public static String getFormatStr(float value) {
		return decimalFormat.format(value);
	}

	public static String getFormatDecimalStr(double value) {
		return decimalDecimalFormat.format(value);
	}

	public static String getFormatDecimalStr(float value) {
		return decimalDecimalFormat.format(value);
	}

	public static String getFormatDecimalOneStr(double value){
		return decimalOneFormat.format(value);
	}

	public static String getFormatIntStr(double value) {
		return decimalIntFormat.format(value);
	}

	public static String getFormatIntStr(float value) {
		return decimalIntFormat.format(value);
	}

	public static String getFormatPriceStr(double value){
		return decimalPriceFormat.format(value);
	}

	public static String getFormatTimeStr(int value){
		return decimalTimeFormat.format(value);
	}

	public static String oneDecimal(double number){
		BigDecimal bigDecimal=new BigDecimal(number);
		BigDecimal bigDecimal1=bigDecimal.setScale(1,BigDecimal.ROUND_HALF_UP);
		return bigDecimal1.toString();
	}

	public static String twoDecimal(double number){
		BigDecimal bigDecimal=new BigDecimal(number);
		BigDecimal bigDecimal1=bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP);
		return bigDecimal1.toString();
	}

	public static String customDecimal(double number,int scale){
		BigDecimal bigDecimal=new BigDecimal(number);
		BigDecimal bigDecimal1=bigDecimal.setScale(scale,BigDecimal.ROUND_HALF_UP);
		return bigDecimal1.toString();
	}
}
