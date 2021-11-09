package com.hougr.eventbus.unused;

import androidx.lifecycle.MutableLiveData;

public class PostValueRunnable<T> implements Runnable {
    private T newValue;
    private MutableLiveData<T> mLiveData;

    public void setNewValue(MutableLiveData<T> liveData, T newValue) {
        this.mLiveData = liveData;
        this.newValue = newValue;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mLiveData.setValue(newValue);
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
