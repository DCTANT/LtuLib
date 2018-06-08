package com.tstdct.lib.crash;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tstdct.lib.FileUtil;
import com.tstdct.lib.NetworkUtil;
import com.tstdct.lib.TimeUtil;

import java.lang.reflect.Field;

/**
 * Created by Dechert on 2018-02-27.
 * Company: www.chisalsoft.co
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
	private static CrashHandler sInstance = null;
	private Thread.UncaughtExceptionHandler mDefaultHandler;
	private Context mContext;
	// 保存手机信息和异常信息
//	private Map<String, String> mMessage = new HashMap<>();
	private String TAG=CrashHandler.class.getSimpleName();
	private static Gson gson;
	private static Application  application;
	private M_CrashModel crashModel=new M_CrashModel();

	public static CrashHandler getInstance(Gson mGson,Application application) {
		if (sInstance == null) {
			synchronized (CrashHandler.class) {
				if (sInstance == null) {
					synchronized (CrashHandler.class) {
						sInstance = new CrashHandler();
						gson=mGson;
						CrashHandler.application=application;
					}
				}
			}
		}
		return sInstance;
	}

	private CrashHandler() {
	}

	/**
	 * 初始化默认异常捕获
	 *
	 * @param context context
	 */
	public void init(Context context) {
		mContext = context;
		// 获取默认异常处理器
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		// 将此类设为默认异常处理器
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		if (!handleException(e)) {
			// 未经过人为处理,则调用系统默认处理异常,弹出系统强制关闭的对话框
			if (mDefaultHandler != null) {
				mDefaultHandler.uncaughtException(t, e);
			}
		} else {
			// 已经人为处理,系统自己退出
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
//			Process.killProcess(Process.myPid());
//			System.exit(1);

		}
	}

	/**
	 * 是否人为捕获异常
	 *
	 * @param e Throwable
	 * @return true:已处理 false:未处理
	 */
	private boolean handleException(final Throwable e) {
		if (e == null) {// 异常是否为空
			Log.i(TAG,"e == null");
			return false;
		}
		collectErrorMessages(e);
		new Thread() {// 在主线程中弹出提示
			@Override
			public void run() {
				Looper.prepare();
				Toast.makeText(mContext, "很抱歉服务异常，请联系客服人员", Toast.LENGTH_SHORT).show();
				Log.e(TAG,e.getMessage());
				Looper.loop();
			}
		}.start();
		return false;
	}

	/**
	 * 1.收集错误信息
	 * @param e2
	 */
	private void collectErrorMessages(Throwable e2) {
		PackageManager pm = mContext.getPackageManager();
		try {
			PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
			if (pi != null) {
				String versionName = TextUtils.isEmpty(pi.versionName) ? "null" : pi.versionName;
				String versionCode = "" + pi.versionCode;
				crashModel.getEssentialInfo().put("VersionName", versionName);
				crashModel.getEssentialInfo().put("VersionCode", versionCode);
				crashModel.getEssentialInfo().put("AndroidVersion", Build.VERSION.RELEASE+ "(" + Build.MODEL + ")");
				crashModel.getEssentialInfo().put("Exception","message:"+e2.toString());
				crashModel.getEssentialInfo().put("CrashTime", TimeUtil.timeStampToDate(System.currentTimeMillis()));
				crashModel.getEssentialInfo().put("NetworkAvailable", NetworkUtil.isNetworkAvailable(application.getApplicationContext())+"");
			}
			String lines="";
			for(StackTraceElement stackTraceElement:e2.getStackTrace()){
				lines+=stackTraceElement.toString()+"    ";
			}
			crashModel.setCrashLines(lines);
			// 通过反射拿到错误信息
			Field[] fields = Build.class.getFields();
			if (fields != null && fields.length > 0) {
				for (Field field : fields) {
					field.setAccessible(true);
					try {
						crashModel.getOtherInfo().put(field.getName(), field.get(null).toString());
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
			saveErrorMessages(e2);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 2.保存错误信息
	 * @param e2
	 */
	private void saveErrorMessages(Throwable e2) {
		String outputString=gson.toJson(crashModel);
		Log.i(TAG,"outputString:"+outputString);
		String filePath=application.getExternalCacheDir()+"/CrashReport/"+ TimeUtil.timeStampToDate(System.currentTimeMillis())+"_crashLog.txt";
		FileUtil.outputFile(filePath,outputString);

	}

//	/**
//	 * 2.保存错误信息
//	 *
//	 * @param e Throwable
//	 */
//	private void saveErrorMessages(Throwable e) {
//		StringBuilder sb = new StringBuilder();
//		for (Map.Entry<String, String> entry : mMessage.entrySet()) {
//			String key = entry.getKey();
//			String value = entry.getValue();
//			sb.append(key).append("=").append(value).append("\n");
//		}
//		Writer writer = new StringWriter();
//		PrintWriter pw = new PrintWriter(writer);
//		e.printStackTrace(pw);
//		Throwable cause = e.getCause();
//		// 循环取出Cause
//		while (cause != null) {
//			cause.printStackTrace(pw);
//			cause = e.getCause();
//		}
//		pw.close();
//		String result = writer.toString();
//		sb.append(result);
//		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date());
//		String fileName = "crash-" + time + "-" + System.currentTimeMillis() + ".log";
//		// 有无SD卡
//		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//			String path = Environment.getExternalStorageDirectory().getPath() + "crash/";
//			File dir = new File(path);
//			if (!dir.exists()) dir.mkdirs();
//			FileOutputStream fos = null;
//			try {
//				fos = new FileOutputStream(path + fileName);
//				fos.write(sb.toString().getBytes());
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			} finally {
//				if (fos != null) {
//					try {
//						fos.close();
//					} catch (IOException e1) {
//						e1.printStackTrace();
//					}
//				}
//			}
//		}
//	}

}
