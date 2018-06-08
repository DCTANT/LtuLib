package com.tstdct.lib;

import android.util.Log;

/**
 * Created by Dechert on 2017-09-22.
 */

public class LogUtil {
	private String TAG;

	public void i(String message) {
		Log.i(TAG, message);
	}

	public void d(String message) {
		Log.d(TAG, message);
	}

	public void e(String message) {
		Log.e(TAG, message);
	}

	public void v(String message) {
		Log.v(TAG, message);
	}

	public LogUtil(String TAG) {
		this.TAG = TAG;
	}
}
