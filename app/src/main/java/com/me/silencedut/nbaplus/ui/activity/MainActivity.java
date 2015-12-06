package com.me.silencedut.nbaplus.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.FrameLayout;

import com.me.silencedut.nbaplus.R;
import com.me.silencedut.nbaplus.event.Event;
import com.me.silencedut.nbaplus.ui.fragment.DrawerFragment;
import com.me.silencedut.nbaplus.ui.fragment.MainFragment;
import com.me.silencedut.nbaplus.ui.fragment.NewsFragment;
import com.me.silencedut.nbaplus.utils.AppUtils;

public class MainActivity extends BaseActivity {
    private DrawerFragment mNavigationFragment;
    private Fragment mCurrentFragment;
    private long firstTime;

   // private String test="{\"nextId\": \"116421938\", \"newslist\": [{\"contentType\": \"ARTICLE\", \"description\": \"据传，在两周前76人队主场对阵步行者的比赛时，球队主帅布雷特-布朗被步行者本赛季的小球阵容所启发，想\", \"title\": \"诺尔探花不搭？76人或动刀阵容\", \"putdate\": \"20151205\", \"imgUrlList\": [], \"randomNum\": 1449325765885, \"articleId\": 116422102, \"contentSourceName\": \"3G门户.体育\", \"articleUrl\": \"http://reader.res.meizu.com/reader/articlecontent/20151205/116422102.json\", \"type\": \"IMAGETEXT\", \"topicVoteJson\": null, \"sourceType\": \"ZAKER\"}, {\"contentType\": \"ARTICLE\", \"description\": \"CSN湾区新闻消息，本赛季，勇士悍将伊戈达拉的投篮准星提升许多，他的三分球命中率达到48.1%，这比\", \"title\": \"伊戈1数据nba第3揭秘准星暴涨\", \"putdate\": \"20151205\", \"imgUrlList\": [], \"randomNum\": 1449325652607, \"articleId\": 116422103, \"contentSourceName\": \"3G门户.体育\", \"articleUrl\": \"http://reader.res.meizu.com/reader/articlecontent/20151205/116422103.json\", \"type\": \"IMAGETEXT\", \"topicVoteJson\": null, \"sourceType\": \"ZAKER\"}, {\"contentType\": \"ARTICLE\", \"description\": \"据《达拉斯新闻》报道称，老将贾森-特里自曝本想在休赛期与德安德鲁-乔丹一起加盟达拉斯小牛队，向总冠军\", \"title\": \"特里差点赴小牛，目标打满20季\", \"putdate\": \"20151205\", \"imgUrlList\": [], \"randomNum\": 1449325658028, \"articleId\": 116422104, \"contentSourceName\": \"3G门户.体育\", \"articleUrl\": \"http://reader.res.meizu.com/reader/articlecontent/20151205/116422104.json\", \"type\": \"IMAGETEXT\", \"topicVoteJson\": null, \"sourceType\": \"ZAKER\"}, {\"contentType\": \"ARTICLE\", \"description\": \"ESPN消息，在昨天马刺对阵灰熊的比赛中，阿尔德里奇开局很低迷，前7投全失，但后来及时复苏，最终贡献\", \"title\": \"阿德谢邓肯：他一直在耳边唠叨\", \"putdate\": \"20151205\", \"imgUrlList\": [], \"randomNum\": 1449325687336, \"articleId\": 116422105, \"contentSourceName\": \"3G门户.体育\", \"articleUrl\": \"http://reader.res.meizu.com/reader/articlecontent/20151205/116422105.json\", \"type\": \"IMAGETEXT\", \"topicVoteJson\": null, \"sourceType\": \"ZAKER\"}, {\"contentType\": \"ARTICLE\", \"description\": \"北京时间12月5日，火箭在客场以100-96战胜小牛。在比赛中，哈登表现不俗，贡献25分8个篮板9次\", \"title\": \"临帅：火箭近5战4胜仍处于困境\", \"putdate\": \"20151205\", \"imgUrlList\": [], \"randomNum\": 1449325577850, \"articleId\": 116422106, \"contentSourceName\": \"3G门户.体育\", \"articleUrl\": \"http://reader.res.meizu.com/reader/articlecontent/20151205/116422106.json\", \"type\": \"IMAGETEXT\", \"topicVoteJson\": null, \"sourceType\": \"ZAKER\"}, {\"contentType\": \"ARTICLE\", \"description\": \"如果你想要让篮球水平变得更好，你会怎么做？狂练力量？不断练习投篮？看录像？也许这些都对。不过快船的球\", \"title\": \"扣将转型全能王！格里芬透秘诀\", \"putdate\": \"20151205\", \"imgUrlList\": [\"http://img.res.meizu.com/img/download/reader/2015/1205/120/0c059695/1c664100/93b54160/2bf52fc4/original\"], \"randomNum\": 1449325701115, \"articleId\": 116422107, \"contentSourceName\": \"3G门户.体育\", \"articleUrl\": \"http://reader.res.meizu.com/reader/articlecontent/20151205/116422107.json\", \"type\": \"IMAGETEXT\", \"topicVoteJson\": null, \"sourceType\": \"ZAKER\"}, {\"contentType\": \"ARTICLE\", \"description\": \"据美联社的报道，骑士108比114加时输给了鹈鹕，对于球队今天的表现，主帅布拉特表示詹姆斯需要得到更\", \"title\": \"JR回击布帅：单节15分还不够？\", \"putdate\": \"20151205\", \"imgUrlList\": [], \"randomNum\": 1449325725253, \"articleId\": 116422108, \"contentSourceName\": \"3G门户.体育\", \"articleUrl\": \"http://reader.res.meizu.com/reader/articlecontent/20151205/116422108.json\", \"type\": \"IMAGETEXT\", \"topicVoteJson\": null, \"sourceType\": \"ZAKER\"}, {\"contentType\": \"ARTICLE\", \"description\": \"北京时间12月5日，NBA常规赛继续进行，骑士经过加时苦战108-114不敌鹈鹕。 赛后，对于输\", \"title\": \"詹姆斯：唯有耶稣才能拯救骑士\", \"putdate\": \"20151205\", \"imgUrlList\": [], \"randomNum\": 1449325587034, \"articleId\": 116422109, \"contentSourceName\": \"3G门户.体育\", \"articleUrl\": \"http://reader.res.meizu.com/reader/articlecontent/20151205/116422109.json\", \"type\": \"IMAGETEXT\", \"topicVoteJson\": null, \"sourceType\": \"ZAKER\"}, {\"contentType\": \"ARTICLE\", \"description\": \"当你听到科比-布莱恩特这个名字的时候，第一个想到的词是什么？有些人会说“黑曼巴”，有些人会说“湖人2\", \"title\": \"波什叹科比伟大：81分难以置信\", \"putdate\": \"20151205\", \"imgUrlList\": [], \"randomNum\": 1449325783691, \"articleId\": 116422110, \"contentSourceName\": \"3G门户.体育\", \"articleUrl\": \"http://reader.res.meizu.com/reader/articlecontent/20151205/116422110.json\", \"type\": \"IMAGETEXT\", \"topicVoteJson\": null, \"sourceType\": \"ZAKER\"}, {\"contentType\": \"ARTICLE\", \"description\": \"据《沃斯堡明星电讯报》的报道，科比已经确定将会在赛季结束之后退役，剩余的比赛，就成了他的巡回表演，不\", \"title\": \"小牛错过致敬科比：他还会回来\", \"putdate\": \"20151205\", \"imgUrlList\": [], \"randomNum\": 1449325711016, \"articleId\": 116422111, \"contentSourceName\": \"3G门户.体育\", \"articleUrl\": \"http://reader.res.meizu.com/reader/articlecontent/20151205/116422111.json\", \"type\": \"IMAGETEXT\", \"topicVoteJson\": null, \"sourceType\": \"ZAKER\"}, {\"contentType\": \"ARTICLE\", \"description\": \"据加拿大媒体的报道，现如今，谁能阻止勇士已经成了联盟一大热议的话题，目前他们已经取得了20连胜，明天\", \"title\": \"德罗赞不屑勇士，豪言终结金州\", \"putdate\": \"20151205\", \"imgUrlList\": [], \"randomNum\": 1449325728849, \"articleId\": 116422112, \"contentSourceName\": \"3G门户.体育\", \"articleUrl\": \"http://reader.res.meizu.com/reader/articlecontent/20151205/116422112.json\", \"type\": \"IMAGETEXT\", \"topicVoteJson\": null, \"sourceType\": \"ZAKER\"}, {\"contentType\": \"ARTICLE\", \"description\": \"今天鹈鹕在主场通过加时赛以114-108击败骑士，拿到31分、12个篮板和4次抢断的安东尼-戴维斯居\", \"title\": \"终结者！鹈鹕1人让詹皇17中3图\", \"putdate\": \"20151205\", \"imgUrlList\": [\"http://img.res.meizu.com/img/download/reader/2015/1205/21/34b589af/3af24459/a28a0f7c/9666cb61/original\"], \"randomNum\": 1449325597183, \"articleId\": 116422113, \"contentSourceName\": \"3G门户.体育\", \"articleUrl\": \"http://reader.res.meizu.com/reader/articlecontent/20151205/116422113.json\", \"type\": \"IMAGETEXT\", \"topicVoteJson\": null, \"sourceType\": \"ZAKER\"}]}";

    @Override
    protected void initViews() {
        mNavigationFragment = (DrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationFragment.setUp((FrameLayout) findViewById(R.id.main_content),
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.main_drawer));
        mCurrentFragment= MainFragment.newInstance();
        transactionFragment(mCurrentFragment);


    }

    private void transactionFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, fragment).commit();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    public void onEventMainThread(Event event) {
//        if(event instanceof NewsEvent) {
//            Log.d("onEventMainThread",((NewsEvent) event).getNews().getNextId()+"");
//        }
    }

    @Override
    public void onBackPressed() {
        if (mNavigationFragment.isDrawerOpen()) {
            mNavigationFragment.closeDrawer();
        } else {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                View container;
                if(mCurrentFragment instanceof NewsFragment) {
                    container=((NewsFragment) mCurrentFragment).getContainer();
                }else {
                    container=findViewById(R.id.main_content);
                }
                AppUtils.showSnackBar(container, R.string.exit_prompt);
                firstTime = secondTime;
            } else {
                finish();
            }
        }
    }




}
