package com.tstdct.lib;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Dechert on 5/22/2018 7:37 PM.
 * Company: www.chisalsoft.com
 * Usage:
 */
public class PackageUtil {
	public static String getVersionName(Context context) {
		PackageManager manager = context.getPackageManager();
		try {
			//第二个参数代表额外的信息，例如获取当前应用中的所有的Activity
			PackageInfo packageInfo = manager.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionName;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}
}
