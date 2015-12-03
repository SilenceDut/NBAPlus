package com.greendao;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class DaoGeneration {
    public static void main(String[] args) throws Exception {

        Schema schema = new Schema(1, "com.me.silencedut.greendao");
        addNews(schema);
        new DaoGenerator().generateAll(schema, "/AndroidCode/NBAPlus/app/src/main/java-gen");
    }

    public static void addNews(Schema schema) {
        Entity news = schema.addEntity("GreenNews");
        news.addIdProperty();
        news.addStringProperty("newslist");
        news.addStringProperty("type");
    }
}
