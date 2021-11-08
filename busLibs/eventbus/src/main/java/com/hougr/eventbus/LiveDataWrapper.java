package com.hougr.eventbus;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.HashSet;

public class LiveDataWrapper<T>
//        extends MutableLiveData<T>
        implements ILiveDataWrapper<T>
{
    public int mVersion = -1;
    private MutableLiveData<T> mLiveData = new MutableLiveData<>();
    private HashSet<ObserverWrapper> mObserverWrapperSet;

    @Override
    public void observe(LifecycleOwner owner, ObserverWrapper observerWrapper) {
        isWrapperExisted(observerWrapper, mVersion, ObserverWrapper.ObserveMode.MODE_OBSERVE);
//        super.observe(owner,observerWrapper);
        mLiveData.observe(owner,observerWrapper);
    }

    @Override
    public void observeSticky(LifecycleOwner owner, ObserverWrapper observerWrapper) {
        isWrapperExisted(observerWrapper, mVersion, ObserverWrapper.ObserveMode.MODE_OBSERVE_STICKY);
//        super.observe(owner,observerWrapper);
        mLiveData.observe(owner,observerWrapper);
    }

    @Override
    public void observeForever(ObserverWrapper observerWrapper) {
        isWrapperExisted(observerWrapper, mVersion, ObserverWrapper.ObserveMode.MODE_OBSERVE_FOREVER);
//        super.observeForever(observerWrapper);
        mLiveData.observeForever(observerWrapper);
    }

    private boolean isWrapperExisted(ObserverWrapper<T> wrapper, int version, int mode) {
        if (mObserverWrapperSet == null) {
            mObserverWrapperSet = new HashSet<>();
        } else if (mObserverWrapperSet.contains(wrapper)) {
            return true;
        }
        wrapper.setExtra(version, mode, this);
        mObserverWrapperSet.add(wrapper);
        return false;
    }

    @Override
    public void setValue(T value) {
        mVersion++;
        mLiveData.setValue(value);
    }
}
