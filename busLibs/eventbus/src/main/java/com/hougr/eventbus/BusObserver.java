package com.hougr.eventbus;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;

public abstract class BusObserver<T> implements Observer<T> {
//public abstract class ObserverWrapper<T>{
//    Observer<T> mObserver;//接口方法重写后必须是public，故改继承为组合。不对，必须用继承。
    int mVersion = -1;
    int mMode = ObserveMode.MODE_OBSERVE_STICKY;
    LiveDataWrapper<T> mLiveDataWrapper;

    void setExtra(int version, int mode, LiveDataWrapper<T> wrapper){
        mVersion = version;
        mMode = mode;
        mLiveDataWrapper = wrapper;
    }

    /**
     * 不能final这个方法是挺遗憾的。我错了，public和final可以公用。
     * @param t
     */
    @Override
    public final void onChanged(@NonNull T t) {
        if (mVersion >= mLiveDataWrapper.mVersion) {
            return;
        }
        mVersion = mLiveDataWrapper.mVersion;
        onValueChanged(t);
    }

    public abstract void onValueChanged(T t);

//    public ObserverWrapper(){}

//    public abstract void onChanged(T value);

    /**
     * 这个Mode目前没有实际用处，只用mVersion就能实现区分Sticky的功能。
     */
    public static interface ObserveMode {
        public static final int MODE_OBSERVE_STICKY = 0;
        public static final int MODE_OBSERVE = 1;
        public static final int MODE_OBSERVE_STICKY_FOREVER = 2;
        public static final int MODE_OBSERVE_FOREVER = 3;
    }
}
