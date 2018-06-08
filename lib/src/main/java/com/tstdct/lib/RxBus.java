package com.tstdct.lib;


import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class RxBus {
	private static volatile RxBus mInstance;

	private final Subject mBus;

	public RxBus() {
		mBus = PublishSubject.create().toSerialized();
	}

	// 单例RxBus
	public static RxBus getInstance() {
		if (mInstance == null) {
			synchronized (RxBus.class) {
				if (mInstance == null) {
					mInstance = new RxBus();
				}
			}
		}
		return mInstance;
	}

	public void post(Object o) {
		mBus.onNext(o);
	}

	// 多个订阅，自己写instanceof过滤
	public Observable<Object> toObservable() {
		return mBus;
	}

}
