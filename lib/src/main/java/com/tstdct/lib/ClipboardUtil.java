package com.tstdct.lib;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * Created by Dechert on 5/22/2018 6:07 PM.
 * Company: www.chisalsoft.com
 * Usage:
 */
public class ClipboardUtil {
	public static void addIntoClipboard(Context context, String msg) {
		//获取剪贴板管理器：
		ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
// 创建普通字符型ClipData
		ClipData mClipData = ClipData.newPlainText("Label", msg);
// 将ClipData内容放到系统剪贴板里。
		cm.setPrimaryClip(mClipData);
	}
}
