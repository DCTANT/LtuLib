package com.tstdct.lib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.tstdct.lib.R;

import java.lang.ref.WeakReference;

public class StateDialog extends Dialog {
	public final static int PROGRESSDRAWABLE = -1; 
	private final static long DELAY_STATEDISMISS = 2000;
	protected long dismissDelay = DELAY_STATEDISMISS;
	
	protected Context context;
	protected View container;
	protected ImageView imageView_progress;
	protected TextView textView_mes;
	
	protected String mes = "loading…";
	protected boolean canceledOnTouchOutside = true;
	protected boolean cancelable = true;
	public StateDialog(Context context) {
		super(context, R.style.MMTheme_DataSheet_Progress);
		this.context = context;
		init();
	}

	public StateDialog(Context context, int theme)
	{
		super(context, theme);
		this.context = context;
		init();
	}

	private void init() {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		container = (View) inflater.inflate(R.layout.progressdialog, null);
		imageView_progress = (ImageView) container.findViewById(R.id.progress);
		textView_mes = (TextView) container.findViewById(R.id.mes);
		Window w = getWindow();
		WindowManager.LayoutParams lp = w.getAttributes();
		lp.x = 0;
		lp.y = 0;
		lp.gravity = Gravity.CENTER;
		onWindowAttributesChanged(lp);
		setContentView(container);

		dismissHandler = new DismissHandler(this);
	}
	public void show(String mes, boolean canceledOnTouchOutside, boolean cancelable, int drawable, boolean isAnim) {
		this.mes = mes;
		this.canceledOnTouchOutside = canceledOnTouchOutside;
		this.cancelable = cancelable;
		if(!isAnim) {
			imageView_progress.setBackgroundResource(drawable);
			
			dismissHandler.removeMessages(1);
			dismissHandler.sendEmptyMessageDelayed(1, dismissDelay);
		}
		else
		{
			imageView_progress.setBackgroundDrawable(null);
			imageView_progress.setBackgroundResource(drawable);
		}
		show();
	}
	
	public void show(String mes, int drawable) {
		this.mes = mes;
		this.canceledOnTouchOutside = false;
		this.cancelable = true;
		if(drawable!=-1) {
			imageView_progress.setBackgroundResource(drawable);
			
			dismissHandler.removeMessages(1);
			dismissHandler.sendEmptyMessageDelayed(1, dismissDelay);
		}
		else
		{
			imageView_progress.setBackgroundDrawable(null);
		}
		show();
	}
	
	public void show(String mes) {
		this.mes = mes;
		show();
	}

	public void changeMes(String mes){
		textView_mes.setText(mes);
	}


	static class DismissHandler extends Handler {
		private WeakReference<StateDialog> weakReference;
		public DismissHandler(StateDialog stateDialog) {
			weakReference = new WeakReference<>(stateDialog);
		}
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (weakReference != null && weakReference.get() != null) {
				weakReference.get().dismiss();
			}
		}
	}
	private DismissHandler dismissHandler;
	
	@Override
	public void show() {
		textView_mes.setText(mes);
		setCanceledOnTouchOutside(canceledOnTouchOutside);
		setCancelable(cancelable);
		super.show();
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public View getContainer()
	{
		return container;
	}

	public void setContainer(View container)
	{
		this.container = container;
	}

	public ImageView getImageView_progress()
	{
		return imageView_progress;
	}

	public void setImageView_progress(ImageView imageView_progress)
	{
		this.imageView_progress = imageView_progress;
	}

	public TextView getTextView_mes()
	{
		return textView_mes;
	}

	public void setTextView_mes(TextView textView_mes)
	{
		this.textView_mes = textView_mes;
	}

	public long getDismissDelay()
	{
		return dismissDelay;
	}

	public void setDismissDelay(long dismissDelay)
	{
		this.dismissDelay = dismissDelay;
	}

	@Override
	public void dismiss() {
		System.out.println("loading^dismiss");
		super.dismiss();
	}
}
