package com.me.silencedut.nbaplus.ui.adapter.RecycleAdapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.me.silencedut.nbaplus.R;
import com.me.silencedut.nbaplus.model.Teams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by SilenceDut on 2015/12/23.
 */
public class TeamSortAdapter extends RecyclerView.Adapter<TeamSortAdapter.TeamHolder> {

    private final static String[] TEAM_NAMES={"骑士","猛龙","老鹰","步行者","热火","活塞","公牛","魔术"
            ,"黄蜂","凯尔特人","尼克斯","奇才","雄鹿","篮网","76人","勇士","马刺","雷霆","快船","小牛"
            ,"灰熊","火箭","爵士","太阳","掘金","森林狼","国王","开拓者","鹈鹕","湖人"};
    private final static int[] TEAM_ICONS={R.mipmap.cleveland,R.mipmap.toronto,R.mipmap.atlanta,R.mipmap.indiana
            ,R.mipmap.miami,R.mipmap.detroit,R.mipmap.chicago,R.mipmap.orlando,R.mipmap.charlotte,R.mipmap.boston
            ,R.mipmap.newyork,R.mipmap.houston,R.mipmap.milwaukee,R.mipmap.brooklyn,R.mipmap.phila,R.mipmap.goldenstate
            ,R.mipmap.sanantonio,R.mipmap.okc,R.mipmap.laclippers,R.mipmap.dallas,R.mipmap.memphis,R.mipmap.houston
            ,R.mipmap.utah,R.mipmap.phoenix,R.mipmap.denver,R.mipmap.minnesota,R.mipmap.sacramento,R.mipmap.portland
            ,R.mipmap.neworleans,R.mipmap.lalakers};
    private final Map<String,Integer> teamIconMap=new HashMap<>();
    private List<Teams.TeamsortEntity> mTeams;
    protected Context mContext;
    protected LayoutInflater mInflater;
    private static final int TEAMS_TITLE=0;
    private static final int TEAMS_ENTITY=1;
    public TeamSortAdapter(Context context,List<Teams.TeamsortEntity> teams) {
        super();
        this.mContext = context;
        this.mTeams=teams;
        mInflater = LayoutInflater.from(context);
        for (int index=0;index<TEAM_NAMES.length;index++){
            teamIconMap.put(TEAM_NAMES[index],TEAM_ICONS[index]);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position%17==0) {
            return TEAMS_TITLE;
        }else {
            return TEAMS_ENTITY;
        }
    }

    @Override
    public TeamHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TeamHolder teamHolder;
        if(viewType==TEAMS_TITLE) {
            teamHolder=new TeamTitle(mInflater.inflate(R.layout.item_fragment_teamsort_title,parent,false));
        }else {
            teamHolder=new TeamEntity(mInflater.inflate(R.layout.item_fragment_teamsort_entity,parent,false));
        }

        return teamHolder;
    }

    @Override
    public void onBindViewHolder(TeamHolder holder, int position) {
        holder.updateItem(position);
    }

    @Override
    public int getItemCount() {
        int count=0;
        if(mTeams!=null) {
            count=mTeams.size()+2;
        }
        return count;
    }



    abstract class TeamHolder extends RecyclerView.ViewHolder {

        public TeamHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        abstract void updateItem(int position);
    }

    class TeamTitle extends TeamHolder {
        @Bind(R.id.teams_title)
        TextView mTeamTitle_tv;
        public TeamTitle(View itemView) {
            super(itemView);
        }

        @Override
        void updateItem(int position) {
            if(mTeams==null||mTeams.size()==0){
                return;
            }
            if(position==0) {
                mTeamTitle_tv.setText("东部球队");
            }else {
                mTeamTitle_tv.setText("西部球队");
            }

        }
    }

    class TeamEntity extends TeamHolder {
        @Bind(R.id.place)
        TextView mTeamPlace_tv;
        @Bind(R.id.team_icon)
        ImageView mTeamicon_IV;
        @Bind(R.id.team_name)
        TextView mTeamName_tv;
        @Bind(R.id.win)
        TextView mTeamWin_tv;
        @Bind(R.id.lose)
        TextView mTeamLose_tv;
        @Bind(R.id.win_percent)
        TextView mTeamWinPer_tv;
        @Bind(R.id.gap)
        TextView mTeamGap_tv;
        @Bind(R.id.divider)
        View divider;
        View mItemView;
        public TeamEntity(View itemView) {
            super(itemView);
            this.mItemView=itemView;
        }
        @Override
        void updateItem(int position) {
            if(mTeams==null||mTeams.size()==0){
                return;
            }
            int index=0;
            if(position>17) {
                index=position-2;
            }else if(position>0){
                index=position-1;
            }
            Teams.TeamsortEntity team=mTeams.get(index);
            mTeamPlace_tv.setText(team.getSort());

            mTeamName_tv.setText(team.getTeam());
            mTeamWin_tv.setText(team.getWin());
            mTeamLose_tv.setText(team.getLose());
            mTeamWinPer_tv.setText(team.getWinPercent());
            mTeamGap_tv.setText(team.getGap());
            if(index==0||index==16) {
                mTeamPlace_tv.setText("");
                divider.setVisibility(View.VISIBLE);
                mItemView.setClickable(false);
                mTeamicon_IV.setVisibility(View.INVISIBLE);

            }else {
                mTeamicon_IV.setVisibility(View.VISIBLE);
                mTeamicon_IV.setImageResource(teamIconMap.get(team.getTeam()));
                divider.setVisibility(View.GONE);
                mItemView.setClickable(true);
            }

            if((index>0&&index<9)||(index>16&&index<25)) {
                mTeamPlace_tv.setTextColor(Color.parseColor("#f44336"));
            }else {
                mTeamPlace_tv.setTextColor(Color.parseColor("#212121"));
            }
        }
    }

}
