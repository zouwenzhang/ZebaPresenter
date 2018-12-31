package com.zeba.presenter;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import com.zeba.presenter.annotation.Presenter;

public class ZebaPresenter {

    public static void inject(Object host){
        inject(host,host);
    }

    public static void inject(Object host,Object obj){
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field f : declaredFields) {
            if (f.isAnnotationPresent(Presenter.class)) {
                Presenter annotation = f.getAnnotation(Presenter.class);
                if (annotation != null)
                {
                    f.setAccessible(true);
                    try {
                        Object ob=f.getType().newInstance();
                        if(ob instanceof BasePresenter){
                            BasePresenter bp=(BasePresenter) ob;
                            if(bp.getTClass().isInstance(host)){
                                bp.init(host);
                            }
                            bp.onCreate();
                        }
                        f.set(obj, ob);
                        inject(host,ob);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static WeakHashMap<Object,List<WeakReference<BasePresenter>>> viewMap=new WeakHashMap<>();

    private static void addView(Object host,BasePresenter presenter){
        List<WeakReference<BasePresenter>> list=viewMap.get(host);
        if(list==null){
            list=new ArrayList<>();
            viewMap.put(host,list);
        }
        list.add(new WeakReference<>(presenter));
    }

    public static void onResume(Object host) {
        List<WeakReference<BasePresenter>> list=viewMap.get(host);
        if(list==null){
            return;
        }
        for(WeakReference<BasePresenter> wf:list){
            if(wf.get()!=null){
                wf.get().onResume();
            }
        }
    }

    public static void onPause(Object host) {
        List<WeakReference<BasePresenter>> list=viewMap.get(host);
        if(list==null){
            return;
        }
        for(WeakReference<BasePresenter> wf:list){
            if(wf.get()!=null){
                wf.get().onPause();
            }
        }
    }

    public static void onDestroy(Object host) {
        List<WeakReference<BasePresenter>> list=viewMap.get(host);
        if(list==null){
            return;
        }
        for(WeakReference<BasePresenter> wf:list){
            if(wf.get()!=null){
                wf.get().onDestroy();
                wf.clear();
            }
        }
        list.clear();
        viewMap.remove(host);
    }
}
