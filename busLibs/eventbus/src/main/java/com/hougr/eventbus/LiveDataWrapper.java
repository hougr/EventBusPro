package com.hougr.eventbus;

import static androidx.lifecycle.Lifecycle.State.DESTROYED;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

//import com.hougr.eventbus.unused.PostValueRunnable;
import com.hougr.eventbus.util.ThreadUtil;

import java.util.HashSet;

public class LiveDataWrapper<T>
//        extends MutableLiveData<T>
        implements ILiveDataWrapper<T>
{
    public int mVersion = -1;
    private long mLastSendTime=-1;
    private MutableLiveData<T> mLiveData = new MutableLiveData<>();
    private HashSet<BusObserver> mBusObserverSet;
//    private LifecycleOwner mOwner;
//    private PostValueRunnable<T> postRunnable;

    @Override
    public void observe(LifecycleOwner owner, BusObserver busObserver) {
        owner.getLifecycle().addObserver(new LifecycleEventObserver() {
            @Override
            public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
                if (source.getLifecycle().getCurrentState() == DESTROYED) {
                    removeForeverObserver(busObserver);
                    return;
                }
            }
        });
        isWrapperExisted(busObserver, mVersion, BusObserver.ObserveMode.MODE_OBSERVE);
//        super.observe(owner,observerWrapper);
        mLiveData.observe(owner, busObserver);
    }

    @Override
    public void observeSticky(LifecycleOwner owner, BusObserver busObserver) {
        isWrapperExisted(busObserver, -1, BusObserver.ObserveMode.MODE_OBSERVE_STICKY);
//        super.observe(owner,observerWrapper);
        mLiveData.observe(owner, busObserver);
    }

    @Override
    public void observeForever(BusObserver busObserver) {
        isWrapperExisted(busObserver, mVersion, BusObserver.ObserveMode.MODE_OBSERVE_FOREVER);
//        super.observeForever(observerWrapper);
        mLiveData.observeForever(busObserver);
    }

    @Override
    public void observeStickyForever(BusObserver busObserver) {
        isWrapperExisted(busObserver, -1, BusObserver.ObserveMode.MODE_OBSERVE_STICKY_FOREVER);
//        super.observeForever(observerWrapper);
        mLiveData.observeForever(busObserver);
    }

    private boolean isWrapperExisted(BusObserver<T> wrapper, int version, int mode) {
        if (mBusObserverSet == null) {
            mBusObserverSet = new HashSet<>();
        } else if (mBusObserverSet.contains(wrapper)) {
            return true;
        }
        wrapper.setExtra(version, mode, this);
        mBusObserverSet.add(wrapper);
        return false;
    }

    @Override
    public void removeForeverObserver(BusObserver<T> busObserver) {
        mLiveData.removeObserver(busObserver);
        mBusObserverSet.remove(busObserver);
    }

    /**
     * 总线的多线程并发安全是伪命题。因为没有读取get方法，而且只关注最新值。
     * 只有with方法有线程安全问题。
     * @param value
     */
    @Override
    public void setValue(T value) {
//    public synchronized void setValue(T value) {
        mVersion++;
        //多线程修改同一个值，谁后发为最新，不是谁后到谁最新。
        if (mLastSendTime != -1 && mLastSendTime>SystemClock.uptimeMillis()) {
            return;
        }
        mLastSendTime = SystemClock.uptimeMillis();
        if(ThreadUtil.isOnMainThread()){
            mLiveData.setValue(value);
        } else {
            mLiveData.postValue(value);
//            new Handler(Looper.getMainLooper()).post(new Runnable() {
//                @Override
//                public void run() {
//                    mLiveData.setValue(value);
//                }
//            });

//            Log.d("HougrLog",ThreadUtil.isOnMainThread()+": "+ value.toString()+":"+Thread.currentThread().toString());
//            if (postRunnable == null) {
//                postRunnable = new PostValueRunnable<>();
//            }
//            postRunnable.setNewValue(mLiveData, value);
//            ThreadUtil.post2MainThread(postRunnable);
        }
    }
}
