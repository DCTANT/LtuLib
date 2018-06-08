package com.tstdct.lib;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

public class ThreadUtil {
	private static Handler mManinHandler;
	private static Object mMainHandlerLock = new Object();

	public static void start(Runnable r) {
		Thread thread = new Thread(r);
		thread.start();
	}

	public static Handler getMainHandler() {
		if (mManinHandler == null) {
			synchronized (mMainHandlerLock) {
				if (mManinHandler == null) {
					mManinHandler = new Handler(Looper.getMainLooper());
				}
			}
		}
		return mManinHandler;
	}

	public static final void toastMessage(final Context context,
										  final String message) {
		((Activity)context).runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
			}
		});
	}
}
