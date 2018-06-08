package com.tstdct.lib;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by Dechert on 2017-09-20.
 */

public class PermissionCheckUtil {
    public static String[] demoPermissions = new String[]{Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.ACCESS_COARSE_LOCATION};
    public static void checkPermission(Context context, String[] permissions, PermissionCheckListener permissionCheckListener) {
        boolean isGranted=true;
        if (Build.VERSION.SDK_INT >= 23) {
            for (int i = 0; i < permissions.length; i++) {
                int check = ContextCompat.checkSelfPermission(context, permissions[i]);
                if (check == PackageManager.PERMISSION_GRANTED) {
//                    permissionCheckListener.permissonGranted();
                } else {
                    isGranted=false;
//                    permissionCheckListener.permissionDenied();
                }
            }
            if(isGranted){
                permissionCheckListener.permissonGranted();
            }else{
                permissionCheckListener.permissionDenied();
            }

        } else {
            permissionCheckListener.underSDK23();
        }
    }

    public interface PermissionCheckListener {
        void permissonGranted();

        void permissionDenied();

        void underSDK23();
    }

    public static void requestPermissions(Activity activity,String[] permissions,int requestCode){
        ActivityCompat.requestPermissions(activity,permissions,requestCode);
    }


}
