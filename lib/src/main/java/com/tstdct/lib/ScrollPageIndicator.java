/*
 * Copyright (C) 2011 The Android Open Source Project
 * Copyright (C) 2011 Jake Wharton
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tstdct.lib;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * This widget implements the dynamic action bar tab behavior that can change
 * across different configurations or circumstances.
 */
public class ScrollPageIndicator extends HorizontalScrollView implements PageIndicator {
    /** Title text used when no title is provided by the adapter. */
    private static final CharSequence EMPTY_TITLE = "";

    /**
     * Interface for a callback when the selected tab has been reselected.
     */
    public interface OnTabReselectedListener {
        /**
         * Callback when the selected tab has been reselected.
         *
         * @param position Position of the current center item.
         */
        void onTabReselected(int position);
    }

    private Runnable mTabSelector;

    private final OnClickListener mTabClickListener = new OnClickListener() {
        public void onClick(View view) {
            TabView tabView = (TabView)view;
            final int oldSelected = mViewPager.getCurrentItem();
            final int newSelected = tabView.getIndex();
            mViewPager.setCurrentItem(newSelected);
            if (oldSelected == newSelected && mTabReselectedListener != null) {
                mTabReselectedListener.onTabReselected(newSelected);
            }
        }
    };
    private RelativeLayout container;
    private final IcsLinearLayout mTabLayout;
    private final TextView fg;

    private ViewPager mViewPager;
    private OnPageChangeListener mListener;

    private int mMaxTabWidth;
    private int mSelectedTabIndex;

    private OnTabReselectedListener mTabReselectedListener;

    public ScrollPageIndicator(Context context) {
        this(context, null);
    }

    public ScrollPageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
//        setBackgroundColor(Color.rgb(0xed, 0xf0, 0xf5));
        setHorizontalScrollBarEnabled(false);

        container = new RelativeLayout(context);
        mTabLayout = new IcsLinearLayout(context, R.attr.vpiTabPageIndicatorStyle);
        fg = new TextView(context, null, R.attr.vpiTabPageIndicatorStyle);
        
        container.addView(fg);
        container.addView(mTabLayout);
//        fg.setBackgroundColor(Color.argb(0x7f, 0xff, 0x00, 0x00));
//        fg.setBackgroundResource(R.drawable.ico_mycollect_screen);
//        fg.setPadding(DensityUtil.dip2px(context, 18), DensityUtil.dip2px(context, 12), DensityUtil.dip2px(context, 18), DensityUtil.dip2px(context, 12));
        
        addView(container, new ViewGroup.LayoutParams(WRAP_CONTENT, MATCH_PARENT));
    }

    public void setOnTabReselectedListener(OnTabReselectedListener listener) {
        mTabReselectedListener = listener;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final boolean lockedExpanded = widthMode == MeasureSpec.EXACTLY;
        setFillViewport(lockedExpanded);

        final int childCount = mTabLayout.getChildCount();
        if (childCount > 1 && (widthMode == MeasureSpec.EXACTLY || widthMode == MeasureSpec.AT_MOST)) {
            if (childCount > 2) {
                mMaxTabWidth = (int)(MeasureSpec.getSize(widthMeasureSpec) * 0.4f);
            } else {
                mMaxTabWidth = MeasureSpec.getSize(widthMeasureSpec) / 2;
            }
        } else {
            mMaxTabWidth = -1;
        }

        final int oldWidth = getMeasuredWidth();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int newWidth = getMeasuredWidth();

        if (childCount>1&&lockedExpanded && oldWidth != newWidth) {
            // Recenter the tab display if we're at a new (scrollable) size.
            setCurrentItem(mSelectedTabIndex);
	          RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)fg.getLayoutParams();
	          params.width = mTabLayout.getChildAt(mSelectedTabIndex).getMeasuredWidth();
	          params.setMargins(mTabLayout.getChildAt(mSelectedTabIndex).getLeft(), 0, 0, 0);
	          fg.setLayoutParams(params);
        }
        if(childCount==0) {
        	RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)fg.getLayoutParams();
	          params.width = 0;
	          fg.setLayoutParams(params);
        }
    }

    private void animateToTab(final int position) {
    	System.out.println("animateToTab");
        final View tabView = mTabLayout.getChildAt(position);
        if (mTabSelector != null) {
            removeCallbacks(mTabSelector);
        }
        mTabSelector = new Runnable() {
            public void run() {
                final int scrollPos = tabView.getLeft() - (getWidth() - tabView.getWidth()) / 2;
                smoothScrollTo(scrollPos, 0);
                mTabSelector = null;
            }
        };
        post(mTabSelector);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mTabSelector != null) {
            // Re-post the selector we saved
            post(mTabSelector);
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mTabSelector != null) {
            removeCallbacks(mTabSelector);
        }
    }

    private void addTab(int index, CharSequence text, int iconResId) {
        final TabView tabView = new TabView(getContext());

        tabView.mIndex = index;
        tabView.setFocusable(true);
        tabView.setOnClickListener(mTabClickListener);
        tabView.setText(text);

        int paddingLandscape = dip2px(getContext(),8);
        int paddingPortrait = dip2px(getContext(),3);
        tabView.setPadding(paddingLandscape,paddingPortrait,paddingLandscape,paddingPortrait);

        tabView.setBackgroundResource(R.drawable.selector_title_bg);
        tabView.setTextColor(getResources().getColorStateList(R.color.selector_title_color));
        if (iconResId != 0) {
            tabView.setCompoundDrawablesWithIntrinsicBounds(iconResId, 0, 0, 0);
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(WRAP_CONTENT,MATCH_PARENT);
        int margins = dip2px(getContext(),10);
        params.setMargins(margins,margins,margins,margins);

        mTabLayout.addView(tabView, params);
    }



    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        int value=(int) (dpValue * scale + 0.5f);
        return value;
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        if (mListener != null) {
            mListener.onPageScrollStateChanged(arg0);
        }
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    	if(mTabLayout.getChildCount()==0) return;
    	if(arg1!=0.0f) {
    		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)fg.getLayoutParams();
            params.width = (int)(mTabLayout.getChildAt(arg0).getWidth()*(1-arg1)+ arg1*(mTabLayout.getChildAt(arg0+1).getWidth()));
            params.setMargins((int)(mTabLayout.getChildAt(arg0).getLeft()+ arg1*(mTabLayout.getChildAt(arg0).getWidth())), 0, 0, 0);
            fg.setLayoutParams(params);
    	} else {
    		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)fg.getLayoutParams();
            params.width = mTabLayout.getChildAt(arg0).getMeasuredWidth();
            params.setMargins(mTabLayout.getChildAt(arg0).getLeft(), 0, 0, 0);
            fg.setLayoutParams(params);
            
            final int tabCount = mTabLayout.getChildCount();
            for (int i = 0; i < tabCount; i++) {
                final View child = mTabLayout.getChildAt(i);
                final boolean isSelected = (i == arg0);
                child.setSelected(isSelected);
                if (isSelected) {
                    animateToTab(arg0);
                }
            }
    	}
        
        if (mListener != null) {
            mListener.onPageScrolled(arg0, arg1, arg2);
        }
    }

    @Override
    public void onPageSelected(int arg0) {
        setCurrentItem(arg0);
        if (mListener != null) {
            mListener.onPageSelected(arg0);
        }
    }

    @Override
    public void setViewPager(ViewPager view) {
        if (mViewPager == view) {
            return;
        }
        if (mViewPager != null) {
            mViewPager.setOnPageChangeListener(null);
        }
        final PagerAdapter adapter = view.getAdapter();
        if (adapter == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        mViewPager = view;
        view.setOnPageChangeListener(this);
        notifyDataSetChanged();
    }

    public void notifyDataSetChanged() {
        mTabLayout.removeAllViews();
        PagerAdapter adapter = mViewPager.getAdapter();
        IconPagerAdapter iconAdapter = null;
        if (adapter instanceof IconPagerAdapter) {
            iconAdapter = (IconPagerAdapter)adapter;
        }
        final int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            CharSequence title = adapter.getPageTitle(i);
            if (title == null) {
                title = EMPTY_TITLE;
            }
            int iconResId = 0;
            if (iconAdapter != null) {
                iconResId = iconAdapter.getIconResId(i);
            }
            addTab(i, title, iconResId);
        }
        if (mSelectedTabIndex > count) {
            mSelectedTabIndex = count - 1;
        }
        setCurrentItem(mSelectedTabIndex);
        
        System.out.println("mSelectedTabIndex="+mSelectedTabIndex);
        if (mTabLayout.getChildCount()!=0) {
        	container.measure(MeasureSpec.EXACTLY, MeasureSpec.EXACTLY);
	          RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)fg.getLayoutParams();
	          params.width = mTabLayout.getChildAt(mSelectedTabIndex).getMeasuredWidth();
	          params.setMargins(mTabLayout.getChildAt(mSelectedTabIndex).getLeft(), 0, 0, 0);
	          fg.setLayoutParams(params);
        } else {
        	RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)fg.getLayoutParams();
	          params.width = 0;
	          fg.setLayoutParams(params);
        }
        
        requestLayout();
    }

    @Override
    public void setViewPager(ViewPager view, int initialPosition) {
        setViewPager(view);
        setCurrentItem(initialPosition);
    }

    @Override
    public void setCurrentItem(int item) {
        if (mViewPager == null) {
            throw new IllegalStateException("ViewPager has not been bound.");
        }
        mSelectedTabIndex = item;
        mViewPager.setCurrentItem(item);
        
        final int tabCount = mTabLayout.getChildCount();
        for (int i = 0; i < tabCount; i++) {
            final View child = mTabLayout.getChildAt(i);
            final boolean isSelected = (i == mSelectedTabIndex);
            child.setSelected(isSelected);
        }
    }

    @Override
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        mListener = listener;
    }

    private class TabView extends android.support.v7.widget.AppCompatTextView {
        private int mIndex;

        public TabView(Context context) {
            super(context, null, R.attr.vpiTabPageIndicatorStyle);
        }

        @Override
        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);

            // Re-measure if we went beyond our maximum size.
            if (mMaxTabWidth > 0 && getMeasuredWidth() > mMaxTabWidth) {
                super.onMeasure(MeasureSpec.makeMeasureSpec(mMaxTabWidth, MeasureSpec.EXACTLY),
                        heightMeasureSpec);
            }
        }

        public int getIndex() {
            return mIndex;
        }
    }
}
