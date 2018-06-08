package com.tstdct.lib;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Dechert on 2017-09-20.
 */

public class ToastUtil {
    public static void showShortToast(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }
}
