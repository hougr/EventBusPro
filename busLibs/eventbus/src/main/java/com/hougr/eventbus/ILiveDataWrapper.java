package com.hougr.eventbus;

import androidx.lifecycle.LifecycleOwner;

public interface ILiveDataWrapper<T> {
    public void observe (LifecycleOwner owner, ObserverWrapper<T> observerWrapper);
    public void observeSticky (LifecycleOwner owner, ObserverWrapper<T> observerWrapper);
    public void observeForever (ObserverWrapper<T> observerWrapper);
    public void observeStickyForever (ObserverWrapper<T> observerWrapper);
//    public void removeForeverObserver(ObserverWrapper<T> observerWrapper);
    public void setValue(T value);
}
