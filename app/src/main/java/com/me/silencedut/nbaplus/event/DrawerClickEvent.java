package com.me.silencedut.nbaplus.event;

/**
 * Created by asan on 2015/11/28.
 */
public class DrawerClickEvent extends Event {
    private int drawId;

    public DrawerClickEvent(int drawId) {
        this.drawId = drawId;
    }

    public int getDrawId() {
        return drawId;
    }

}
