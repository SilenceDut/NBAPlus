package com.me.silencedut.nbaplus.app;

/**
 * Created by SilenceDut on 2015/11/28.
 */
public class NbaplusFactory {
    private static NbaplusAPI sInstance=null;
    private static final Object WATCH_DOG=new Object();

    private NbaplusFactory(){}

    public static NbaplusAPI getNbaplus() {
        synchronized (WATCH_DOG) {
            if(sInstance==null){
                sInstance=new NbaplusCilent().getCilent();
            }
            return sInstance;
        }
    }
}
