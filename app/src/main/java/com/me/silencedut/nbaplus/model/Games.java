package com.me.silencedut.nbaplus.model;

import java.util.List;

/**
 * Created by SilenceDut on 2015/12/26.
 */
public class Games {
    /**
     * status :
     * stateUrl :
     * rightTeam :
     * statusText :
     * statusUrl :
     * leftTeam :
     * rightScore :
     * stateText :
     * date : 12-26
     * type : 0
     */

    private List<GamesEntity> games;

    public void setGames(List<GamesEntity> games) {
        this.games = games;
    }

    public List<GamesEntity> getGames() {
        return games;
    }

    public static class GamesEntity {
        private String status;
        private String stateUrl;
        private String rightTeam;
        private String statusText;
        private String statusUrl;
        private String leftTeam;
        private String leftScore;
        private String rightScore;
        private String stateText;
        private String date;
        private int type;

        public void setStatus(String status) {
            this.status = status;
        }

        public void setStateUrl(String stateUrl) {
            this.stateUrl = stateUrl;
        }

        public void setRightTeam(String rightTeam) {
            this.rightTeam = rightTeam;
        }

        public void setStatusText(String statusText) {
            this.statusText = statusText;
        }

        public void setStatusUrl(String statusUrl) {
            this.statusUrl = statusUrl;
        }

        public void setLeftTeam(String leftTeam) {
            this.leftTeam = leftTeam;
        }

        public void setLeftScore(String leftScore) {
            this.leftScore = leftScore;
        }

        public void setRightScore(String rightScore) {
            this.rightScore = rightScore;
        }

        public void setStateText(String stateText) {
            this.stateText = stateText;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getStatus() {
            return status;
        }

        public String getStateUrl() {
            return stateUrl;
        }

        public String getRightTeam() {
            return rightTeam;
        }

        public String getStatusText() {
            return statusText;
        }

        public String getStatusUrl() {
            return statusUrl;
        }

        public String getLeftTeam() {
            return leftTeam;
        }

        public String getLeftScore() {
            return leftScore;
        }

        public String getRightScore() {
            return rightScore;
        }

        public String getStateText() {
            return stateText;
        }

        public String getDate() {
            return date;
        }

        public int getType() {
            return type;
        }
    }
}
