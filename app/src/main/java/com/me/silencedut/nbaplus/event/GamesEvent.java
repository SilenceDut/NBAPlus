package com.me.silencedut.nbaplus.event;

import com.me.silencedut.nbaplus.data.Constant;
import com.me.silencedut.nbaplus.model.Games;

/**
 * Created by SilenceDut on 2015/12/26.
 */
public class GamesEvent extends Event {
    private Games mAllGames;
    public GamesEvent(Games games,Constant.Result result) {
        this.mAllGames=games;
        this.mEventResult=result;
    }

    public Games getAllGames() {
        return mAllGames;
    }
}
