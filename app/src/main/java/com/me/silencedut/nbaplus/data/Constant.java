package com.me.silencedut.nbaplus.data;

import com.me.silencedut.nbaplus.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SilenceDut on 2015/11/28.
 */
public class Constant {

    public static final String APP_FIR_IM_URL="http://fir.im/nbaplus";
    public static final String API_TOKEN_FIR="ff55b0c5cb165ec0b04c473cf77c8995";

    public static final String LOADIMAGE = "LOADIMAGE";
    public static final String ACTILEFONTSIZE = "ACTILEFONTSIZE";

    public static final String CSS_STYLE ="<style>* {font-size:%spx;line-height:28px;}p {color:%s;}</style>";

    public final static String[] TEAM_NAMES={"骑士","猛龙","老鹰","步行者","热火","活塞","公牛","魔术"
            ,"黄蜂","凯尔特人","尼克斯","奇才","雄鹿","篮网","76人","勇士","马刺","雷霆","快船","小牛"
            ,"灰熊","火箭","爵士","太阳","掘金","森林狼","国王","开拓者","鹈鹕","湖人"};
    public final static int[] TEAM_ICONS={R.mipmap.cleveland,R.mipmap.toronto,R.mipmap.atlanta,R.mipmap.indiana
            ,R.mipmap.miami,R.mipmap.detroit,R.mipmap.chicago,R.mipmap.orlando,R.mipmap.charlotte,R.mipmap.boston
            ,R.mipmap.newyork,R.mipmap.washington,R.mipmap.milwaukee,R.mipmap.brooklyn,R.mipmap.phila,R.mipmap.goldenstate
            ,R.mipmap.sanantonio,R.mipmap.okc,R.mipmap.laclippers,R.mipmap.dallas,R.mipmap.memphis,R.mipmap.houston
            ,R.mipmap.utah,R.mipmap.phoenix,R.mipmap.denver,R.mipmap.minnesota,R.mipmap.sacramento,R.mipmap.portland
            ,R.mipmap.neworleans,R.mipmap.lalakers};

    private static final Map<String,Integer> sTeamIconMap=new HashMap<>();

    static {
        for (int index=0;index<TEAM_NAMES.length;index++){
            sTeamIconMap.put(TEAM_NAMES[index],TEAM_ICONS[index]);
        }
    }

    public static Map<String,Integer>  getTeamIcons()  {
        return sTeamIconMap;
    }

    public enum NEWSTYPE {
        NEWS("news"),BLOG("blog");
        private String newsType;
        NEWSTYPE(String newsType) {
            this.newsType=newsType;
        }
        public String getNewsType() {
            return newsType;
        }
    }

    public enum GETNEWSWAY {
        INIT,UPDATE,LOADMORE;
    }

    public enum Result {
        SUCCESS,FAIL,NORMAL;
    }

}
