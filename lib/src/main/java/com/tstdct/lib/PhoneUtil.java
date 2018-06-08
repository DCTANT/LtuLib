package com.tstdct.lib;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.UUID;

/**
 * Created by @cola .
 */
public class PhoneUtil {
	public static String getVersionName(Context context) {
		try {
			String pkName = context.getPackageName();
			String versionName = context.getPackageManager().getPackageInfo(
					pkName, 0).versionName;
			return versionName;
		} catch (Exception e) {
		}
		return null;
	}

	public static int getVersionCode(Context context) {
		try {
			String pkName = context.getPackageName();
			int versionCode = context.getPackageManager().getPackageInfo(
					pkName, 0).versionCode;
			return versionCode;
		} catch (Exception e) {
		}
		return -1;
	}

	/**
	 * 取得当前设备的唯一编号。首先尝试取得DeviceId，如果取不到则尝试取得AndroidId，如果还取不到则用UUID生成一个随机id。 最后，将生成的唯一编号用MD5处理成32位字符串。
	 *
	 * @param ctx
	 * @return
	 */
	public static String getUniqueId(Context ctx) {
		String uniqueId = null;
		TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
		try {
			uniqueId = tm.getDeviceId();
			if (uniqueId == null || uniqueId.length() == 0) {
				uniqueId = Settings.Secure.getString(ctx.getContentResolver(), Settings.Secure.ANDROID_ID);
			}
		} catch (Throwable t) {
		}
		if (uniqueId == null || uniqueId.length() == 0) {
			uniqueId = UUID.randomUUID().toString();
		}
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(uniqueId.getBytes("UTF-8"));
		} catch (Throwable t) {
		}
		if (md != null) {
			byte[] byteArray = md.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteArray.length; i++) {
				if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
					sb.append('0').append(Integer.toHexString(0xFF & byteArray[i]));
				} else {
					sb.append(Integer.toHexString(0xFF & byteArray[i]));
				}
			}
			uniqueId = sb.toString();
		}
		return uniqueId;
	}


//	public static String getDeviceId(Context context) {
//		StringBuilder deviceId = new StringBuilder();
//		// 渠道标志
//		deviceId.append("a");
//		try {
//			//wifi mac地址
//			WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//			WifiInfo info = wifi.getConnectionInfo();
//			String wifiMac = info.getMacAddress();
//			if(!isEmpty(wifiMac)){
//				deviceId.append("wifi");
//				deviceId.append(wifiMac);
//				Log.e("getDeviceId : ", deviceId.toString());
//				return deviceId.toString();
//			}
//			//IMEI（imei）
//			TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//			String imei = tm.getDeviceId();
//			if(!isEmpty(imei)){
//				deviceId.append("imei");
//				deviceId.append(imei);
//				Log.e("getDeviceId : ", deviceId.toString());
//				return deviceId.toString();
//			}
//			//序列号（sn）
//			String sn = tm.getSimSerialNumber();
//			if(!isEmpty(sn)){
//				deviceId.append("sn");
//				deviceId.append(sn);
//				Log.e("getDeviceId : ", deviceId.toString());
//				return deviceId.toString();
//			}
//			//如果上面都没有， 则生成一个id：随机码
//			String uuid = getUUID(context);
//			if(!isEmpty(uuid)){
//				deviceId.append("id");
//				deviceId.append(uuid);
//				Log.e("getDeviceId : ", deviceId.toString());
//				return deviceId.toString();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			deviceId.append("id").append(getUUID(context));
//		}
//		Log.e("getDeviceId : ", deviceId.toString());
//		return deviceId.toString();
//	}
//
//	private static boolean isEmpty(String wifiMac) {
//		if(wifiMac.length()>0){
//			return false;
//		}else{
//			return true;
//		}
//	}
//
//	/**
//	 * 得到全局唯一UUID
//	 */
//	public static String getUUID(Context context){
//		SharedPreferences mShare = getSysShare(context, "sysCacheMap");
//		if(mShare != null){
//			uuid = mShare.getString("uuid", "");
//		}
//		if(isEmpty(uuid)){
//			uuid = UUID.randomUUID().toString();
//			saveSysMap(context, "sysCacheMap", "uuid", uuid);
//		}
//		Log.e(tag, "getUUID : " + uuid);
//		return uuid;
//	}


	/**
	 * 获取设备SN码
	 *
	 * @param key
	 * @return
	 */
	private static String getAndroidOsSystemProperties(String key) {
		String ret;
		try {
			Method systemProperties_get = Class.forName("android.os.SystemProperties").getMethod("get", String.class);
			if ((ret = (String) systemProperties_get.invoke(null, key)) != null)
				return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return "";
	}

	public static String getPhoneSN() {
		String[] propertys = {"ro.boot.serialno", "ro.serialno"};
		String sn = "";
		for (String key : propertys) {
//          String v = android.os.SystemProperties.get(key);
			if(sn.trim().length()==0){
				sn = getAndroidOsSystemProperties(key);
				break;
			}else{
				break;
			}

		}
		return sn;
	}
}
