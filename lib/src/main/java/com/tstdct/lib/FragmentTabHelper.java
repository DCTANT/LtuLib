package com.tstdct.lib;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.List;

public class FragmentTabHelper implements RadioGroup.OnCheckedChangeListener {
	private List<Fragment> fragments;
	private RadioGroup rgs;
	private FragmentActivity fragmentActivity;
	private int fragmentContentId;
	private int currentTab;
	private OnRgsExtraCheckedChangedListener onRgsExtraCheckedChangedListener;
	private Fragment currentFragment;
	private boolean isShowAnim = true;

	public FragmentTabHelper(FragmentActivity fragmentActivity, List<Fragment> fragments, int fragmentContentId,
							 RadioGroup rgs) {
		this.fragments = fragments;
		this.rgs = rgs;
		this.fragmentActivity = fragmentActivity;
		this.fragmentContentId = fragmentContentId;

		FragmentTransaction ft = fragmentActivity.getSupportFragmentManager().beginTransaction();
		ft.replace(fragmentContentId, fragments.get(0));
		ft.commit();
		currentFragment = fragments.get(0);

		rgs.setOnCheckedChangeListener(this);
	}

	public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
		int current = 0;
		for(int i = 0; i < rgs.getChildCount(); i++) {
			if(!(rgs.getChildAt(i) instanceof RadioButton)) {
				continue;
			}
			if(rgs.getChildAt(i).getId() == checkedId) {

				Fragment fragment = fragments.get(current);
				FragmentTransaction ft = obtainFragmentTransaction(i);

				getCurrentFragment().onPause();
				if(fragment.isAdded()) {
					fragment.onResume();
				}else{
					ft.add(fragmentContentId, fragment);
				}
				showTab(current);
				ft.commit();

				if(null != onRgsExtraCheckedChangedListener) {
					onRgsExtraCheckedChangedListener.OnRgsExtraCheckedChanged(radioGroup, checkedId, current);
				}
				currentFragment = fragments.get(current);
			}
			current++;
		}
	}

	private void showTab(int idx) {
		for (int i = 0; i < fragments.size(); i++) {
			Fragment fragment = fragments.get(i);
			FragmentTransaction ft = obtainFragmentTransaction(idx);

			if (idx == i) {
				ft.show(fragment);
			} else {
				ft.hide(fragment);
			}
			ft.commitAllowingStateLoss();
		}
		currentTab = idx;
	}

	private FragmentTransaction obtainFragmentTransaction(int index) {
		FragmentTransaction ft = fragmentActivity.getSupportFragmentManager().beginTransaction();

		if (isShowAnim) {
			if (index > currentTab) {
				ft.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out);
			} else {
				ft.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_right_out);
			}
		}
		return ft;
	}

	public int getCurrentTab() {
		return currentTab;
	}

	public Fragment getCurrentFragment() {
		return currentFragment;
	}

	public OnRgsExtraCheckedChangedListener getOnRgsExtraCheckedChangedListener() {
		return onRgsExtraCheckedChangedListener;
	}

	public void setOnRgsExtraCheckedChangedListener(OnRgsExtraCheckedChangedListener onRgsExtraCheckedChangedListener) {
		this.onRgsExtraCheckedChangedListener = onRgsExtraCheckedChangedListener;
	}

	public boolean isShowAnim() {
		return isShowAnim;
	}

	public void setIsShowAnim(boolean isShowAnim) {
		this.isShowAnim = isShowAnim;
	}

	public static class OnRgsExtraCheckedChangedListener {
		public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index) {

		}
	}

}
