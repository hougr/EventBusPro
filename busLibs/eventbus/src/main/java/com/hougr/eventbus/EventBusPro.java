package com.hougr.eventbus;

import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;

public class EventBusPro {
    private HashMap<String, MutableLiveData<Object>> mEventBusMap = new HashMap<>();

    private static class SingletonHolder {
        static final EventBusPro singleton = new EventBusPro();
    }

    private EventBusPro(){}

    public static EventBusPro get() {
//        public static EventBusPro getSingleton() {
        return SingletonHolder.singleton;
    }

    public <T extends Object> MutableLiveData<T> with(String channelName, Class<T> classObject) {
//        public <T> MutableLiveData<T> with(String channelName, T classObject) {
        if (!mEventBusMap.containsKey(channelName)) {
            mEventBusMap.put(channelName, new MutableLiveData<>());
//            mEventBusMap.put(channelName, new MutableLiveData<T>());
        }
        return (MutableLiveData<T>) mEventBusMap.get(channelName);
//        return mEventBusMap.get(channelName);
    }
}
