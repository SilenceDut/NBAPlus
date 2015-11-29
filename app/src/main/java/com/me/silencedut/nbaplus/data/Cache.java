package com.me.silencedut.nbaplus.data;

import android.os.Environment;
import com.me.silencedut.nbaplus.app.App;
import com.snappydb.DB;
import com.snappydb.SnappyDB;
import com.snappydb.SnappydbException;

/**
 * Created by SilenceDut on 2015/11/28.
 */
public class Cache {
    public enum CACHEKEY{NEWSFEED,BLOGSFEED};
    private static final Cache INSTANCE = new Cache();
    private DB snappydb;

    private Cache() {}

    public static Cache getInstance() {
        return INSTANCE;
    }
    
    public void initSnappyDB() {
        try  {
             snappydb = new SnappyDB.Builder(App.getContext())
                    .directory(Environment.getExternalStorageDirectory().getAbsolutePath())
                    .name("nbaplus")
                    .build();
        } catch(SnappydbException e){

        }
    }

    public <T> T get(String key, Class<T> returnType)  {
        T cache=null;
        try  {
            if(snappydb==null) {
                return null;
            }
            cache = snappydb.getObject(key, returnType);
        } catch(Exception e){
            if(e instanceof SnappydbException) {

            }else if(e instanceof IllegalAccessException) {

            }else if(e instanceof InstantiationException) {

            }
        }

        return cache;
    }

    public void put(String key, Object value) {
        try  {
            if(snappydb==null) {
                return ;
            }
            snappydb.put(key, value);
        } catch(SnappydbException e){
        }
    }

    public void closeDB() {
        try {
            if(snappydb==null) {
                return ;
            }
            snappydb.close();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }


}
