package com.hougr.eventbus;

import androidx.lifecycle.LifecycleOwner;

public interface ILiveDataWrapper<T> {
    public void observe (LifecycleOwner owner, BusObserver<T> busObserver);
    public void observeSticky (LifecycleOwner owner, BusObserver<T> busObserver);
    public void observeForever (BusObserver<T> busObserver);
    public void observeStickyForever (BusObserver<T> busObserver);
    public void removeForeverObserver(BusObserver<T> busObserver);
    public void setValue(T value);
}
