package com.tstdct.lib;

import android.content.Context;

public class DensityUtil {     
    
    public static int dip2px(Context context, float dpValue) { 
        final float scale = context.getResources().getDisplayMetrics().density;     
        int value=(int) (dpValue * scale + 0.5f);     
        return value;     
    }     
     
    public static int px2dip(Context context, float pxValue) {     
        final float scale = context.getResources().getDisplayMetrics().density;     
        int value=(int)(pxValue / scale + 0.5f);    
        return value;     
    }
    
    public static int getScreenWidth(Context context) {     
    	return (int)((float)context.getResources().getDisplayMetrics().widthPixels + 0.5f);     
    }
    
    public static int getScreenHeight(Context context) {     
        return (int)((float)context.getResources().getDisplayMetrics().heightPixels + 0.5f);   
    }
    
    public static float getDensity(Context context) {     
        return context.getResources().getDisplayMetrics().density;   
    }
} 
