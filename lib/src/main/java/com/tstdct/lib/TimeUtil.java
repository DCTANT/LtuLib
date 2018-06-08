package com.tstdct.lib;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Dechert on 2017-10-11.
 * Company: www.chisalsoft.co
 */

public class TimeUtil {
	private static final long SECOND = 1000;
	private static final long MINUTE = 60 * SECOND;
	private static final long HOUR = 60 * MINUTE;
	private static final long DAY = 24 * HOUR;
	private static final String TAG=TimeUtil.class.getName();
	/*
     * 将时间转换为时间戳
     */
	public static String dateToStamp(String s){
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = simpleDateFormat.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long ts = date.getTime();
		res = String.valueOf(ts);
		return res;
	}

	public static String timeFormatTransfer(String origin,SimpleDateFormat originSimpleDateFormat,SimpleDateFormat goalSimpleDateFormat){
		Date date= null;
		try {
			date = originSimpleDateFormat.parse(origin);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long timeStamp=date.getTime();
		String result=goalSimpleDateFormat.format(timeStamp);
		return result;
	}

	public static String timeFormatTransfer(String timeWithZone){
		Date date=null;
		SimpleDateFormat simpleDateFormatWithZone=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
		try {
			date=simpleDateFormatWithZone.parse(timeWithZone);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long ts=date.getTime();
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
		String result=simpleDateFormat.format(ts);
		return result;
	}


	public static long dayToStamp(String day){
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = simpleDateFormat.parse(day);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long ts = date.getTime();
		return ts;
	}

	public static long dayZoneToStamp(String day){
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
		Date date = null;
		try {
			date = simpleDateFormat.parse(day);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		DateDetail dateDetail=TimeUtil.stampToDateDetail(date.getTime());
		String dayTemp=dateDetail.getYear()+"-"+dateDetail.getMonth()+"-"+dateDetail.getDay();
		long ts = 0;
		ts = dateToLongStamp(dayTemp,new SimpleDateFormat("yyyy-MM-dd"));
		return ts;
	}

	/*
 * 将时间转换为时间戳
 */
	public static long dateToLongStamp(String s) throws ParseException{
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = simpleDateFormat.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long ts = date.getTime();

		return ts;
	}

	/*
     * 将时间戳转换为时间
     */
	public static String timeStampToDate(long lt){
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(lt);
		res = simpleDateFormat.format(date);
		return res;
	}

	/*
 * 将时间戳转换为时间
 */
	public static String timeStampToDate(long lt,SimpleDateFormat simpleDateFormat){
		String res;
		Date date = new Date(lt);
		res = simpleDateFormat.format(date);
		return res;
	}

	/*
 * 将时间戳转换为时间
 */
	public static DateDetail stampToDateDetail(String s){
		DateDetail dateDetail=new DateDetail();
//		s=s+"000";
		long lt = new Long(s);
		Date date = new Date(lt);
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		dateDetail.setYear(calendar.get(Calendar.YEAR));
		dateDetail.setMonth(calendar.get(Calendar.MONTH)+1);
		dateDetail.setDay(calendar.get(Calendar.DAY_OF_MONTH));
		dateDetail.setHour(calendar.get(Calendar.HOUR));
		dateDetail.setMinute(calendar.get(Calendar.MINUTE));
		dateDetail.setSecond(calendar.get(Calendar.SECOND));
//		dateDetail.setYear(date.getYear());
//		dateDetail.setMonth(date.getMonth()+1);
//		dateDetail.setDay(date.getDay());
//		dateDetail.setHour(date.getHours());
//		dateDetail.setMinute(date.getMinutes());
//		dateDetail.setSecond(date.getSeconds());
		return dateDetail;
	}

	/*
* 将时间戳转换为时间
*/
	public static DateDetail stampToDateDetail(long lt){
		DateDetail dateDetail=new DateDetail();
		Date date = new Date(lt);
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		dateDetail.setYear(calendar.get(Calendar.YEAR));
		dateDetail.setMonth(calendar.get(Calendar.MONTH)+1);
		dateDetail.setDay(calendar.get(Calendar.DAY_OF_MONTH));
		dateDetail.setHour(calendar.get(Calendar.HOUR));
		dateDetail.setMinute(calendar.get(Calendar.MINUTE));
		dateDetail.setSecond(calendar.get(Calendar.SECOND));
//		dateDetail.setYear(date.getYear());
//		dateDetail.setMonth(date.getMonth()+1);
//		dateDetail.setDay(date.getDay());
//		dateDetail.setHour(date.getHours());
//		dateDetail.setMinute(date.getMinutes());
//		dateDetail.setSecond(date.getSeconds());
		return dateDetail;
	}

	public static class DateDetail{
		private int year;
		private int month;
		private int day;
		private int hour;
		private int minute;
		private int second;

		public int getYear() {
			return year;
		}

		public void setYear(int year) {
			this.year = year;
		}

		public int getMonth() {
			return month;
		}

		public void setMonth(int month) {
			this.month = month;
		}

		public int getDay() {
			return day;
		}

		public void setDay(int day) {
			this.day = day;
		}

		public int getHour() {
			return hour;
		}

		public void setHour(int hour) {
			this.hour = hour;
		}

		public int getMinute() {
			return minute;
		}

		public void setMinute(int minute) {
			this.minute = minute;
		}

		public int getSecond() {
			return second;
		}

		public void setSecond(int second) {
			this.second = second;
		}

		@Override
		public String toString() {
			return "DateDetail{" +
					"year=" + year +
					", month=" + month +
					", day=" + day +
					", hour=" + hour +
					", minute=" + minute +
					", second=" + second +
					'}';
		}
	}

	public static long getNowTimeStamp(){
		return System.currentTimeMillis();
	}

	/*
     * 将时间转换为时间戳
     */
	public static String dateToStringStamp(String s) throws ParseException {
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = simpleDateFormat.parse(s);
		long ts = date.getTime();
		res = String.valueOf(ts);
		return res;
	}


	/*
     * 将时间转换为时间戳
     */
	public static long dateToLongStamp(String s,SimpleDateFormat simpleDateFormat)  {
		String res;
		Date date = null;
		try {
			date = simpleDateFormat.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long ts = date.getTime();
//		res = String.valueOf(ts);
		return ts;
	}

	public static long getDayTimeStamp(){
		String now=nowDayFormat();
		return dateToLongStamp(now,new SimpleDateFormat("yyyy-MM-dd"));
	}

	public static String nowDayAndTimeFormat(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String time=df.format(new Date());
		return time;
	}

	public static String nowDayFormat(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		String time=df.format(new Date());
		return time;
	}

	public static String nowTimeFormat(){
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置日期格式
		String time=df.format(new Date());
		return time;
	}

	//出生日期字符串转化成Date对象
	public static  Date parse(String strDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.parse(strDate);
	}

	public static  int getAge(Date birthDay) throws Exception {
		Calendar cal = Calendar.getInstance();

		if (cal.before(birthDay)) {
			throw new IllegalArgumentException(
					"The birthDay is before Now.It's unbelievable!");
		}
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birthDay);

		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth) age--;
			}else{
				age--;
			}
		}
		return age;
	}

	public static int daysBetween(Date now, Date returnDate) {
		Calendar cNow = Calendar.getInstance();
		Calendar cReturnDate = Calendar.getInstance();
		cNow.setTime(now);
		cReturnDate.setTime(returnDate);
		setTimeToMidnight(cNow);
		setTimeToMidnight(cReturnDate);
		long todayMs = cNow.getTimeInMillis();
		long returnMs = cReturnDate.getTimeInMillis();
		long intervalMs = todayMs - returnMs;
		return millisecondsToDays(intervalMs);
	}
	private static int millisecondsToDays(long intervalMs) {
		return (int) (intervalMs / (1000 * 86400));
	}
	private static void setTimeToMidnight(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
	}

	public static Date dateDetailToDate(DateDetail dateDetail){
		String time=dateDetail.getYear()+"-"+(dateDetail.getMonth()-1)+"-"+dateDetail.getDay()+" "+dateDetail.getHour()+":"+dateDetail.getMinute()+":"+dateDetail.getSecond();
		Log.i(TAG,"timeString:"+time);
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return simpleDateFormat.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static long dateDetailToTimeStamp(DateDetail dateDetail){
		Date date=dateDetailToDate(dateDetail);
		Log.i(TAG,"dateDetail:"+dateDetail.toString());
//		Calendar calendar=Calendar.getInstance();
//		calendar.setTime(date);
//		calendar.
		return date.getTime();
	}

	public static String timeDaysAgo(String dateString) {

		if (dateString == null){
			return "未知";
		}
		long publishTimeStamp = Long.valueOf(dateToStamp(dateString));
		long nowTimeStamp = getNowTimeStamp();
		String moreDay=timeStampToDate(publishTimeStamp,new SimpleDateFormat("MM-dd HH:mm"));
//		DateDetail publishDateDetail = stampToDateDetail(publishTimeStamp);
//		DateDetail nowDataDetail = stampToDateDetail(getNowTimeStamp());
//		if (nowDataDetail.getDay() - publishDateDetail.getDay() >= 1) {//2天前或者更多天前
//			if(nowDataDetail.getYear()!=publishDateDetail.getYear()){
//				return publishDateDetail.getMonth()+"-"+publishDateDetail.getDay()+" "+publishDateDetail.getHour()+":"+publishDateDetail.getMinute();
//			}else{
//				return publishDateDetail.getMonth()+"-"+publishDateDetail.getDay();
//			}
//		} else {
		long differTs = nowTimeStamp - publishTimeStamp;
		if (differTs > DAY) {
			return moreDay;
		} else {
			if (differTs / HOUR > 0) {
				return (differTs / HOUR) + "小时前";
			} else {
				if (differTs / MINUTE > 0) {
					return (differTs / MINUTE) + "分钟前";
				} else {
					return "1分钟前";
				}
			}
		}
//		}
	}
}
