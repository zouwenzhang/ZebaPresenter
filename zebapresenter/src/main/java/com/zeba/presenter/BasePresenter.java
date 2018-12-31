package com.zeba.presenter;

import java.lang.ref.WeakReference;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BasePresenter<T> {
    private WeakReference<T> mt;
    public void init(T t){
        mt=new WeakReference(t);
    }

    public T view(){
        return host();
    }

    public T host(){
        if(mt!=null){
            return mt.get();
        }
        return null;
    }

    public void onCreate(){

    }

    public void onResume() {
    }

    public void onPause() {
    }

    public void onDestroy() {

    }

    public Class<T> getTClass(){
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] ptype = ((ParameterizedType) type).getActualTypeArguments();
            return (Class<T>) ptype[0];
        } else {
            return null;
        }
    }

}
