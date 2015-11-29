package com.me.silencedut.nbaplus.utils;

import android.text.TextUtils;

import org.joda.time.DateTime;

import java.util.Date;


/**
 * Created by asan on 2015/8/8.
 */
public class DateFormatter {

    public static String formatDate(String format) {
        if(TextUtils.isEmpty(format)) {
            format="yyyy/MM/dd hh:mm:ss";
        }
        DateTime data = new DateTime();

        return data.toString(format);

    }

    private static final long minute = 60 * 1000; //分钟
    private static final long hour = 60 * minute; //小时
    private static final long day = 24 * hour;    //天
    private static final long week = 7 * day;     //周
    private static final long month = 31 * day;   //月
    private static final long year = 12 * month;  //年

    public static String getRecentlyTimeFormatText(DateTime date) {
        if (date == null) {
            return null;
        }
        long diff = new Date().getTime() - date.getMillis();
        long r = 0;
        if (diff > year) {
            r = (diff / year);
            return r + "年前";
        }
        if (diff > month) {
            r = (diff / month);
            return r + "个月前";
        }
        if (diff > week) {
            r = (diff / week);
            return r + "周前";
        }
        if (diff > day) {
            r = (diff / day);
            return r + "天前";
        }
        if (diff > hour) {
            r = (diff / hour);
            return r + "小时前";
        }
        if (diff > minute) {
            r = (diff / minute);
            return r + "分钟前";
        }
        return "刚刚";
    }

}