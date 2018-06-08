package com.tstdct.lib.dialog;

import android.content.Context;

public class StateDialogUtil {
	public static StateDialog show(Context context, String mes, boolean canceledOnTouchOutside, boolean cancelable, int drawable) {
		StateDialog p = new StateDialog(context);
		p.show(mes, canceledOnTouchOutside, cancelable, drawable, true);
		return p;
	}
	
	public static StateDialog show(Context context) {
		return show(context, "loading…", false, true, -1);
	}
	
	public static StateDialog show(Context context, String msg) {
		return show(context, msg, false, true, -1);
	}
	
	public static StateDialog show(Context context, String msg, int drawable) {
		return show(context, msg, false, true, -1);
	}
}
