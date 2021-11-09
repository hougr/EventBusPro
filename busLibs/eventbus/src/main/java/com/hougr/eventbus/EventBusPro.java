package com.hougr.eventbus;


import java.util.HashMap;


/**
 * 目前全部事件的onChanged可回调。现在需要在某些情况下不执行onChanged即可，有以下情况：
 *      1、observer不是sticky，且在set后。但对新set（新版本的livedata）有效，所以observe需要分两种情况。
 *      2、observerForever在set后。同样道理也是分两种情况。
 *
 *      只需要
 */
public class EventBusPro {
    private static class WrapperOrReflect {
        static final int WRAPPER=0;
        static final int REFLECT=1;
    }

    private int mWrapperOrReflect = WrapperOrReflect.WRAPPER;
    private HashMap<String, LiveDataWrapper<Object>> mEventBusMap = new HashMap<>();

    private static class SingletonHolder {
        static final EventBusPro singleton = new EventBusPro();
    }

    private EventBusPro(){}

    public static EventBusPro get() {
//        public static EventBusPro getSingleton() {
        return SingletonHolder.singleton;
    }

    public synchronized <T extends Object> LiveDataWrapper<T> with(String channelName, Class<T> classObject) {
//        public <T> LiveDataWrapper<T> with(String channelName, T classObject) {
        if (!mEventBusMap.containsKey(channelName)) {
            mEventBusMap.put(channelName, new LiveDataWrapper<>());
//            mEventBusMap.put(channelName, new LiveDataWrapper<T>());
        }
        return (LiveDataWrapper<T>) mEventBusMap.get(channelName);
//        return mEventBusMap.get(channelName);
    }

    public synchronized LiveDataWrapper<Object> with(String channelName) {
        return with(channelName, Object.class);
    }

}
