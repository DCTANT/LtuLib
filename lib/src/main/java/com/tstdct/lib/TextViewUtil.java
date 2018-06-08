package com.tstdct.lib;

import android.widget.TextView;

/**
 * Created by Dechert on 2017-09-19.
 */

public class TextViewUtil{
    public static<T extends TextView> void show99Plus(T textView,String text){
        int num=0;
        try{
            num=Integer.valueOf(text);
        }catch (Exception e){
            e.printStackTrace();
        }
        String settingString="";
        if(num>99){
            settingString="99+";
        }else{
            settingString=num+"";
        }
        textView.setText(settingString);
    }

    public static<T extends TextView> void show99Plus(T textView,int num){
        String settingString="";
        if(num>99){
            settingString="99+";
        }else{
            settingString=num+"";
        }
        textView.setText(settingString);
    }
}
