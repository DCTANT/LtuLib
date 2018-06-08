package com.tstdct.lib;

import android.app.Activity;
import android.os.Process;

import java.util.LinkedList;
import java.util.List;

/**
 * @author zhaodongdong
 */
public class ActivityUtils {
    private static ActivityUtils me;
    private static List<Activity> mLists;

    /**
     * 获取已开启所有Activity
     *
     * @return list
     */
    public List<Activity> getLists() {
        return mLists;
    }

    public ActivityUtils() {
        mLists = new LinkedList<>();
    }

    public static ActivityUtils getInstance() {
        if (me == null) {
            synchronized (ActivityUtils.class) {
                if (me == null) {
                    me = new ActivityUtils();
                }
            }

        }
        return me;
    }

    /**
     * 添加Activity
     *
     * @param activity activity
     */
    public void addActivity(Activity activity) {
        mLists.add(activity);
    }

    /**
     * 删除Activity
     *
     * @param activity activity
     */
    public void removeActivity(Activity activity) {
        mLists.remove(activity);
    }

    public void exit() {
        if (mLists != null) {
            for (Activity activity : mLists) {
                activity.finish();
            }
        }
//        LogUtils.getSelf().stop();
        Process.killProcess(Process.myPid());
    }

    /**
     * 获取Activity
     *
     * @param cls 类名
     * @return activity
     */
    public Activity getActivity(Class<?> cls) {
        if (mLists != null) {
            for (Activity activity :
                    mLists) {
                if (activity.getClass().equals(cls)) {
                    return activity;
                }
            }
        }
        return null;
    }

    /**
     * finish指定Activity之外的所有Activity
     *
     * @param cls activity
     */
    public void assignActivityExit(Class<?> cls) {
        if (mLists != null) {
            for (Activity activity : mLists) {
                if (activity.getClass().equals(cls)) {
                    continue;
                }
                activity.finish();
            }
        }
    }

    /**
     * finish所有Activity
     */
    public void ActivityAllExit() {
        if (mLists != null) {
            for (Activity activity : mLists) {
                activity.finish();
            }
        }
    }
}
