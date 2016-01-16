package com.me.silencedut.nbaplus.network;

/**
 * Created by SilenceDut on 2015/11/28.
 */
public class NbaplusFactory {
    private static NbaplusAPI sInstanceInstance=null;
    private static NewsDetileAPI sNewsSetileInStance=null;
    private static final Object WATCH_DOG=new Object();

    private NbaplusFactory(){}

    public static NbaplusAPI getNbaplusInstance() {
        synchronized (WATCH_DOG) {
            if(sInstanceInstance==null){
                NbaplusCilent nbaplusCilent = new NbaplusCilent();
                sInstanceInstance=nbaplusCilent.getCilent();
                sNewsSetileInStance=nbaplusCilent.getNewsDetileClient();
            }
            return sInstanceInstance;
        }
    }

    public static NewsDetileAPI getNewsDetileInstance() {
        synchronized (WATCH_DOG) {
            if(sNewsSetileInStance==null){
                NbaplusCilent nbaplusCilent = new NbaplusCilent();
                sInstanceInstance=nbaplusCilent.getCilent();
                sNewsSetileInStance=nbaplusCilent.getNewsDetileClient();
            }
            return sNewsSetileInStance;
        }
    }
}
