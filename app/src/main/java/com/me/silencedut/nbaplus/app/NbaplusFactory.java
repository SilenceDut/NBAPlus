package com.me.silencedut.nbaplus.app;

/**
 * Created by SilenceDut on 2015/11/28.
 */
public class NbaplusFactory {
    private static NbaplusAPI sInstance=null;
    private static NewsDetileAPI sNewsSetileInStance=null;
    private static final Object WATCH_DOG=new Object();

    private NbaplusFactory(){}

    public static NbaplusAPI getNbaplus() {
        synchronized (WATCH_DOG) {
            if(sInstance==null){
                NbaplusCilent nbaplusCilent = new NbaplusCilent();
                sInstance=nbaplusCilent.getCilent();
                sNewsSetileInStance=nbaplusCilent.getNewsDetileClient();
            }
            return sInstance;
        }
    }

    public static NewsDetileAPI getNewsDetileInstance() {
        synchronized (WATCH_DOG) {
            if(sNewsSetileInStance==null){
                NbaplusCilent nbaplusCilent = new NbaplusCilent();
                sInstance=nbaplusCilent.getCilent();
                sNewsSetileInStance=nbaplusCilent.getNewsDetileClient();
            }
            return sNewsSetileInStance;
        }
    }
}
