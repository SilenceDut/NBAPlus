package com.me.silencedut.nbaplus.model;

import java.util.List;

/**
 * Created by Administrator on 2015/12/17.
 */
public class Statistics {
    /**
     * place : 1
     * name : 布雷克-格里芬
     * statdata : 34
     * team : 快船
     */

    private List<StatEntity> dailyStat;
    /**
     * place : 1
     * name : 斯蒂芬-库里
     * statdata : 32.3
     * team : 勇士
     */

    private List<StatEntity> everageStat;

    public void setDailyStat(List<StatEntity> dailyStat) {
        this.dailyStat = dailyStat;
    }

    public void setEverageStat(List<StatEntity> everageStat) {
        this.everageStat = everageStat;
    }

    public List<StatEntity> getDailyStat() {
        return dailyStat;
    }

    public List<StatEntity> getEverageStat() {
        return everageStat;
    }

    public static class StatEntity {
        private String place;
        private String name;
        private String statdata;
        private String team;

        public void setPlace(String place) {
            this.place = place;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setStatdata(String statdata) {
            this.statdata = statdata;
        }

        public void setTeam(String team) {
            this.team = team;
        }

        public String getPlace() {
            return place;
        }

        public String getName() {
            return name;
        }

        public String getStatdata() {
            return statdata;
        }

        public String getTeam() {
            return team;
        }
    }

}
