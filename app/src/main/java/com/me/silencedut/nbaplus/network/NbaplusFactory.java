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
                NbaplusClient nbaplusClient = new NbaplusClient();
                sInstanceInstance= nbaplusClient.getCilent();
                sNewsSetileInStance= nbaplusClient.getNewsDetileClient();
            }
            return sInstanceInstance;
        }
    }

    public static NewsDetileAPI getNewsDetileInstance() {
        synchronized (WATCH_DOG) {
            if(sNewsSetileInStance==null){
                NbaplusClient nbaplusClient = new NbaplusClient();
                sInstanceInstance= nbaplusClient.getCilent();
                sNewsSetileInStance= nbaplusClient.getNewsDetileClient();
            }
            return sNewsSetileInStance;
        }
    }
}
