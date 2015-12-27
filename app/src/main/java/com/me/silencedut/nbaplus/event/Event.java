package com.me.silencedut.nbaplus.event;
import com.me.silencedut.nbaplus.data.Constant.Result;
/**
 * Created by SilenceDut on 2015/11/28.
 */
public class Event {
    protected Result mEventResult;

    public void setEventResult(Result eventResult) {
        this.mEventResult=eventResult;
    }

    public Result getEventResult() {
        return mEventResult;
    }
}
