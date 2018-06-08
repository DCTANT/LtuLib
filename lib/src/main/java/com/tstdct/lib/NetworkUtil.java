package com.tstdct.lib;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static android.content.Context.WIFI_SERVICE;

public class NetworkUtil {
	private static final String TAG=NetworkUtil.class.getName();
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null) {

		} else {
			//如果仅仅是用来判断网络连接 
			//则可以使用 cm.getActiveNetworkInfo().isAvailable();
			NetworkInfo[] info = cm.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean isWifiOpen(Context context){
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm != null) {
			NetworkInfo  wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (wifiInfo != null && wifiInfo.isConnected()) {
				return true;
			}
		}
		return false;
	}

	public static String getCurrentWifiSSID(Context context){
		WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = manager.getConnectionInfo();
		if (info == null){
			return null;
		}
		String ssid = info.getSSID();
		if (ssid.indexOf("\"")> -1){
			ssid = ssid.replaceAll("\"","");
		}
		return ssid;
	}

	public static int getCurrentWifiNetworkId(Context context) {
		WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = manager.getConnectionInfo();
		if (info == null) {
			return -1;
		}
		int networkId = info.getNetworkId();
		return networkId;
	}

	public static String getIPAddress(Context context) {
		NetworkInfo info = ((ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
		if (info != null && info.isConnected()) {
			if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
				try {
					//Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
					for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
						NetworkInterface intf = en.nextElement();
						for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
							InetAddress inetAddress = enumIpAddr.nextElement();
							if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
								return inetAddress.getHostAddress();
							}
						}
					}
				} catch (SocketException e) {
					e.printStackTrace();
				}

			} else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
				WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
				WifiInfo wifiInfo = wifiManager.getConnectionInfo();
				String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
				return ipAddress;
			}
		} else {
			//当前无网络连接,请在设置中打开网络
		}
		return null;
	}

	public static void pingAllDevicesInLan(Context context, final OnPingFinishListener onPingFinishListener) {
		final ArrayList<String> arrayList = new ArrayList();//用于储存局域网中所有可用的ip地址
		String myIP = getIPAddress(context);
		String ipHead = myIP.substring(0, myIP.lastIndexOf(".") + 1);
		final ExecutorService executorService = Executors.newFixedThreadPool(20);
		for (int i = 1; i < 255; i++) {
			final String pingHost = ipHead + i;
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					if (ping(pingHost, 1, new StringBuffer())) {
//						Log.e(TAG,"有效IP:"+pingHost);
						arrayList.add(pingHost);
					} else {

					}
				}
			});
		}
		executorService.shutdown();
		ExecutorService executorService1 = Executors.newSingleThreadExecutor();
		executorService1.execute(new Runnable() {
			@Override
			public void run() {
				watch:
				while (true) {
					if (executorService.isTerminated()) {
//						Log.e(TAG,"已经结束");
						onPingFinishListener.onFinish(arrayList);
						break watch;
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	public static void getIpAndMac(Context context, final OnArpGetListener onArpGetListener) {
		final HashMap<String, String> hashMap = new HashMap<>();
		pingAllDevicesInLan(context, new OnPingFinishListener() {
			@Override
			public void onFinish(ArrayList<String> arrayList) {
//				Log.e(TAG,"进入onFinish");
				StringBuffer stringBuffer = new StringBuffer();
				if (showArp(stringBuffer)) {
//					Log.e(TAG,"stringbuffer:"+stringBuffer);
					String wholeString = stringBuffer.toString();
					String[] items = wholeString.split("\n");
					for (String string : items) {
						boolean isRightMac = true;
						String[] lines = string.split(" ");
						String ip = null;
						String mac = null;
						for (String str : lines) {
							if (str.length() > 0) {
								if (str.contains(".")) {
									ip = str;
								}
								if (str.contains(":")) {
									mac = str;
								}
								if (str.equals("00:00:00:00:00:00")) {
									isRightMac = false;
								}
							}
						}
						if (isRightMac) {
							if (ip != null && mac != null) {
								mac = mac.replaceAll(":","");
								hashMap.put(mac, ip);
							}
						}
					}
					onArpGetListener.onFinish(hashMap);
				}
			}
		});
	}

	/**
	 * 将得到的int类型的IP转换为String类型
	 *
	 * @param ip
	 * @return
	 */
	public static String intIP2StringIP(int ip) {
		return (ip & 0xFF) + "." +
				((ip >> 8) & 0xFF) + "." +
				((ip >> 16) & 0xFF) + "." +
				(ip >> 24 & 0xFF);
	}

	public static boolean ping(String host, int pingCount, StringBuffer stringBuffer) {
		//        String command = "ping -c " + pingCount + " -w 5 " + host;
		String command = "ping -c " + pingCount + " " + host;
		return executeLinuxBash(command, stringBuffer);
	}

	public static boolean executeLinuxBash(String command, StringBuffer stringBuffer) {
		String line = null;
		Process process = null;
		BufferedReader successReader = null;
		boolean isSuccess = false;
		try {
			process = Runtime.getRuntime().exec(command);
			if (process == null) {
				return false;
			}
			successReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			while ((line = successReader.readLine()) != null) {
				append(stringBuffer, line);
			}
			int status = process.waitFor();
			if (status == 0) {
				isSuccess = true;
			} else {
				isSuccess = false;
			}
		} catch (IOException e) {

		} catch (InterruptedException e) {

		} finally {
			if (process != null) {
				process.destroy();
			}
			if (successReader != null) {
				try {
					successReader.close();
				} catch (IOException e) {

				}
			}
		}
		return isSuccess;
	}

	public static boolean showArp(StringBuffer stringBuffer) {
		String command = "cat /proc/net/arp";
//		Log.e(TAG,"进入showArp");
		return executeLinuxBash(command, stringBuffer);
	}

	private static void append(StringBuffer stringBuffer, String text) {
		if (stringBuffer != null) {
			stringBuffer.append(text + "\n");
		}
	}

	public interface OnPingFinishListener {
		void onFinish(ArrayList<String> arrayList);
	}

	public interface OnArpGetListener {
		void onFinish(HashMap<String, String> hashMap);
	}

	public static boolean connectWifiByNetworkId(WifiManager manager, int networkId) {
		boolean b = manager.enableNetwork(networkId, true);
		System.out.println("enableNetwork:" + b + ";networkId:" + networkId+";ssid:"+manager.getConnectionInfo().getSSID());
		if (!b) {
			b = manager.reconnect();
			System.out.println("reconnect:" + b + ";networkId:" + manager.getConnectionInfo().getNetworkId()
					+";ssid:"+manager.getConnectionInfo().getSSID());
		}
		return b;
	}


	public static boolean connectWifi(Context context, @NonNull String ssId, @Nullable String password){
		WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

		WifiConfiguration tempConfig = isExist(ssId, manager);
		if (tempConfig != null) {
			manager.removeNetwork(tempConfig.networkId);
		}

		WifiConfiguration config = new WifiConfiguration();

		config.allowedAuthAlgorithms.clear();
		config.allowedGroupCiphers.clear();
		config.allowedKeyManagement.clear();
		config.allowedPairwiseCiphers.clear();
		config.allowedProtocols.clear();

		config.SSID = "\"" + ssId + "\"";
//		config.SSID = "\u0022" + ssId + "\u0022";
		System.out.println("ssId:"+ssId);
		if (password != null){
			config.preSharedKey = "\"" + password + "\"";
//			config.preSharedKey = "\u0022" + password + "\u0022";
		}else{
			config.preSharedKey = null;//非加密wifi
		}
		config.hiddenSSID = true;
		config.status = WifiConfiguration.Status.ENABLED;
		config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
		config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
		config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
		config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
		if (password != null){
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);//WPA_PSK2  NONE（加密）
		}else{
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);//WPA_PSK  NONE（非加密）
		}
		config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
		int netId = manager.addNetwork(config);
		boolean b = manager.enableNetwork(netId, true);
		if (!b){
			config.SSID = ssId;
			if (password != null){
				config.preSharedKey = password;
			}else{
				config.preSharedKey = null;//非加密wifi
			}
			netId = manager.addNetwork(config);
			b = manager.enableNetwork(netId,true);
		}
		return b;
	}
	//wifi控制代码
	private static final int WIFICIPHER_NOPASS = 0;
	private static final int WIFICIPHER_WEP = 1;
	private static final int WIFICIPHER_WPA = 2;
	public static boolean connectThisWifi(Context context,String SSID,String password) {
		WifiManager wifiManager=(WifiManager) context.getSystemService(WIFI_SERVICE);
		int netId = wifiManager.addNetwork(createWifiConfig(wifiManager,SSID,password,WIFICIPHER_WEP));
		Log.i(TAG,"netid:"+netId);
		boolean enable = wifiManager.enableNetwork(netId, true);
		Log.d(TAG, "enable: " + enable);
		boolean reconnect = wifiManager.reconnect();
		return reconnect;
	}

	private static WifiConfiguration createWifiConfig(WifiManager wifiManager,String ssid, String password, int type) {
		WifiConfiguration config = new WifiConfiguration();
		config.allowedAuthAlgorithms.clear();
		config.allowedGroupCiphers.clear();
		config.allowedKeyManagement.clear();
		config.allowedPairwiseCiphers.clear();
		config.allowedProtocols.clear();
		config.SSID = "\"" + ssid + "\"";

		WifiConfiguration tempConfig = isExist(ssid,wifiManager);
		if (tempConfig != null) {
			Log.i(TAG,"tempConfig不是null");
			wifiManager.removeNetwork(tempConfig.networkId);
		}

		if (type == WIFICIPHER_NOPASS) {
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
		} else if (type == WIFICIPHER_WEP) {
			config.hiddenSSID = true;
			config.wepKeys[0] = "\"" + password + "\"";
			config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
			config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
			config.wepTxKeyIndex = 0;
		} else if (type == WIFICIPHER_WPA) {
			config.preSharedKey = "\"" + password + "\"";
			config.hiddenSSID = true;
			Log.i(TAG,"ssid:"+config.SSID+",password"+config.wepKeys);
			config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
			config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
			config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
			config.status = WifiConfiguration.Status.ENABLED;
		}
		return config;
	}

	public static WifiConfiguration isExist(String ssid, WifiManager wifiManager) {
		List<WifiConfiguration> configs = wifiManager.getConfiguredNetworks();
		for (WifiConfiguration config : configs) {
			if (config.SSID.equals("\"" + ssid + "\"") || config.SSID.equals(ssid)) {
				return config;
			}
		}
		return null;
	}
}
