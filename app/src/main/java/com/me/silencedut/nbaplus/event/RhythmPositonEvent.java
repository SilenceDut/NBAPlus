package com.me.silencedut.nbaplus.event;

/**
 * Created by SilenceDut on 2015/12/17.
 */
public class RhythmPositonEvent extends Event{
    private int mPosition;
    public RhythmPositonEvent(int position) {
        this.mPosition=position;
    }

    public int getPosition() {
        return mPosition;
    }
}
