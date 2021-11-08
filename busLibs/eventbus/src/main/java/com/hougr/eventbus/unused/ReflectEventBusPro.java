package com.hougr.eventbus.unused;

import java.util.HashMap;

public class ReflectEventBusPro {

//    private HashMap<String, BusMutableLiveData<Object>> mEventBusMap = new HashMap<>();
    private HashMap<String, ReflectMutableLiveData<Object>> mEventBusMap = new HashMap<>();

    private static class SingletonHolder {
        static final ReflectEventBusPro singleton = new ReflectEventBusPro();
    }

    private ReflectEventBusPro(){}

    public static ReflectEventBusPro get() {
//        public static EventBusPro getSingleton() {
        return ReflectEventBusPro.SingletonHolder.singleton;
    }

    public <T extends Object> ReflectMutableLiveData<T> with(String channelName, Class<T> classObject) {
//        public <T> BusMutableLiveData<T> with(String channelName, T classObject) {
        if (!mEventBusMap.containsKey(channelName)) {
            mEventBusMap.put(channelName, new ReflectMutableLiveData<>());
//            mEventBusMap.put(channelName, new BusMutableLiveData<T>());
        }
        return (ReflectMutableLiveData<T>) mEventBusMap.get(channelName);
//        return mEventBusMap.get(channelName);
    }


//    public BusMutableLiveData<Object> with(String key) {
//        return with(key, Object.class);
//    }
//
//    private static class BusMutableLiveData<T> extends MutableLiveData<T> {
//
//        private Map<Observer, Observer> observerMap = new HashMap<>();
//
//        @Override
//        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
//            try {
//                hook(observer);
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                super.observe(owner, observer);
//            }
//        }
//
//        public void observeSticky(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
//            super.observe(owner, observer);
//        }
//
//        @Override
//        public void observeForever(@NonNull Observer<? super T> observer) {
//            if (!observerMap.containsKey(observer)) {
//                observerMap.put(observer, new BusObserver(observer));
//            }
//            super.observeForever(observerMap.get(observer));
//        }
//
//        @Override
//        public void removeObserver(@NonNull Observer<? super T> observer) {
//            Observer realObserver = null;
//            if (observerMap.containsKey(observer)) {
//                realObserver = observerMap.remove(observer);
//            } else {
//                realObserver = observer;
//            }
//            super.removeObserver(realObserver);
//        }
//
//        private void hook(@NonNull Observer<? super T> observer) throws Exception {
//            //get wrapper's version
//            Class<LiveData> classLiveData = LiveData.class;
//            Field fieldObservers = classLiveData.getDeclaredField("mObservers");
//            fieldObservers.setAccessible(true);
//            Object objectObservers = fieldObservers.get(this);
//            Class<?> classObservers = objectObservers.getClass();
//            Method methodGet = classObservers.getDeclaredMethod("get", Object.class);
//            methodGet.setAccessible(true);
//            Object objectWrapperEntry = methodGet.invoke(objectObservers, observer);
//            Object objectWrapper = null;
//            if (objectWrapperEntry instanceof Map.Entry) {
//                objectWrapper = ((Map.Entry) objectWrapperEntry).getValue();
//            }
//            if (objectWrapper == null) {
//                throw new NullPointerException("Wrapper can not be bull!");
//            }
//            Class<?> classObserverWrapper = objectWrapper.getClass().getSuperclass();
//            Field fieldLastVersion = classObserverWrapper.getDeclaredField("mLastVersion");
//            fieldLastVersion.setAccessible(true);
//            //get livedata's version
//            Field fieldVersion = classLiveData.getDeclaredField("mVersion");
//            fieldVersion.setAccessible(true);
//            Object objectVersion = fieldVersion.get(this);
//            //set wrapper's version
//            fieldLastVersion.set(objectWrapper, objectVersion);
//        }
//
//
//    }

//
//    private static class BusObserver<T> implements Observer<T> {
//
//        private Observer<T> observer;
//
//        public BusObserver(Observer<T> observer) {
//            this.observer = observer;
//        }
//
//        @Override
//        public void onChanged(@Nullable T t) {
//            if (observer != null) {
//                if (isCallOnObserve()) {
//                    return;
//                }
//                observer.onChanged(t);
//            }
//        }
//
//        private boolean isCallOnObserve() {
//            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
//            if (stackTrace != null && stackTrace.length > 0) {
//                for (StackTraceElement element : stackTrace) {
//                    if ("android.arch.lifecycle.LiveData".equals(element.getClassName()) &&
//                            "observeForever".equals(element.getMethodName())) {
//                        return true;
//                    }
//                }
//            }
//            return false;
//        }
//    }
}
