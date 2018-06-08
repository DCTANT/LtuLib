package com.tstdct.lib.dialog;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.tstdct.lib.DensityUtil;
import com.tstdct.lib.R;


public class ProgressDialog extends StateDialog {

	public ProgressDialog(Context context) {
		super(context, R.style.loadDialog);
		getTextView_mes().setMaxWidth((int) (DensityUtil.getScreenWidth(getContext()) * 0.6) - DensityUtil.dip2px(context, 30));

		{
			getContainer().setBackgroundResource(R.drawable.shape_circle_toastbg);
			getContainer().setPadding(DensityUtil.dip2px(context, 15), DensityUtil.dip2px(context, 23), DensityUtil.dip2px(context, 15), DensityUtil.dip2px(context, 22));
		}

		{
			LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) getImageView_progress().getLayoutParams();
			params.width = DensityUtil.dip2px(context, 44.67f);
			params.height = DensityUtil.dip2px(context, 44.67f);
			getImageView_progress().setLayoutParams(params);
		}

		{
			LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) getTextView_mes().getLayoutParams();
			params.topMargin = DensityUtil.dip2px(context, 11);
			getTextView_mes().setLayoutParams(params);
		}

		{
			getTextView_mes().setTextColor(Color.WHITE);
			getTextView_mes().setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14.4f);
		}
	}

	public void setMes(String msg){
		changeMes(msg);
	}


	private void showProgress(String msg) {
		show(msg, false, true, R.mipmap.ico_toast_loading, true);
		getImageView_progress().startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.rotate));
	}

	private void showProgressNoCancel(String msg) {
		show(msg, false, false, R.mipmap.ico_toast_loading, true);
		getImageView_progress().startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.rotate));
	}

	private void showStateSuccess(String msg) {
		show(msg, true, true, R.mipmap.ico_toast_success, false);
		getImageView_progress().clearAnimation();
	}

	private void showStateError(String msg) {
		show(msg, true, true, R.mipmap.ico_toast_error, false);
		getImageView_progress().clearAnimation();
	}

	public void showLoading(String msg) {
		getContainer().setMinimumWidth(DensityUtil.dip2px(getContext(), 144));

		showProgress(msg);
	}

	public void showLoadingNoCancel(String msg) {
		getContainer().setMinimumWidth(DensityUtil.dip2px(getContext(), 144));

		showProgressNoCancel(msg);
	}

	public void showSuccess(String msg) {
		getContainer().setMinimumWidth(DensityUtil.dip2px(getContext(), 144));

		showStateSuccess(msg);
	}

	public void showErrorSmallSize(String msg) {
		getContainer().setMinimumWidth(DensityUtil.dip2px(getContext(), 144));

		showStateError(msg);
	}

	public void showErrorLargeSize(String msg) {
		getContainer().setMinimumWidth(DensityUtil.dip2px(getContext(), 201));

		showStateError(msg);
	}

	public void showErrorLargeSizeTwoLines(String msg) {
		getContainer().getLayoutParams().width = DensityUtil.dip2px(getContext(), 201);
		getContainer().setLayoutParams(getContainer().getLayoutParams());

		showStateError(msg);
	}

	public void showNoNetwork() {
		getContainer().setMinimumWidth(DensityUtil.dip2px(getContext(), 201));
//		showStateError("网络错误");
		showStateError("Network Error");
	}

	public void showNetworkFail() {
		getContainer().setMinimumWidth(DensityUtil.dip2px(getContext(), 201));
//		showStateError("服务器繁忙，请稍候重试");
		showStateError("Network Error");
	}
}
