package com.me.silencedut.nbaplus.event;

/**
 * Created by SilenceDut on 2015/12/18.
 */
public class StatEvent extends Event {
    private String mStatKind;
    private String[][] mLables;
    private float[][] mStatValues;
    public StatEvent (String statKind,String[][] lables,float[][] statValues) {
        this.mStatKind=statKind;
        this.mLables=lables;
        this.mStatValues=statValues;
    }

    public String getStatKind() {
        return mStatKind;
    }

    public String[][] getLables() {
        return mLables;
    }

    public float[][] getStatValues() {
        return mStatValues;
    }
}
