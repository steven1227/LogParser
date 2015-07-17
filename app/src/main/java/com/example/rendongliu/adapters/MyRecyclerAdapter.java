package com.example.rendongliu.adapters;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rendongliu.fragments.Detail;
import com.example.rendongliu.fragments.FileFinder;
import com.example.rendongliu.fragments.WelcomePage;
import com.example.rendongliu.log.TrackInfo;
import com.example.rendongliu.log.TrackLogger;
import com.example.rendongliu.log.TrackPage;
import com.example.rendongliu.logviewer.R;

import org.json.JSONException;

import java.util.List;

/**
 * Created by rendong.liu on 13/07/15.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.textViewHolder>{
    private FragmentManager ft;
    private List<TrackInfo> filtedList;
    private TrackLogger trackLogger;
    private FileFinder caller;


    public MyRecyclerAdapter(TrackLogger trackLogger,FragmentManager ft,FileFinder a) {
        this.filtedList = trackLogger.getTrackInfoList();
        this.trackLogger=trackLogger;
        this.ft=ft;
        this.caller=a;
    }

    public void Update(int type){
        if (type==0){
            this.filtedList=this.trackLogger.getTrackInfoList();
        }
        else if(type==1){
            this.filtedList=this.trackLogger.getEventLogger().getTrackInfoList();
        }else if(type==2){
            this.filtedList=this.trackLogger.getPageLogger().getTrackInfoList();
        }

    }
    @Override
    public textViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item,parent,false);
        textViewHolder pvh=new textViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(textViewHolder holder, int position) {
        TrackInfo temp=filtedList.get(position);
        holder.textMain.setText(temp.gettitle());
        holder.textTime.setText(temp.getTime());

        if(temp instanceof TrackPage){
            holder.label.setText("page");
            holder.label.setBackgroundColor(Color.BLUE);
        }else if(temp instanceof TrackInfo){
            holder.label.setText("event");
            holder.label.setBackgroundColor(Color.RED);
        }

        holder.textMain.setOnClickListener(clickListener);
        holder.textTime.setOnClickListener(clickListener);
        holder.label.setOnClickListener(clickListener);
        holder.textMain.setTag(holder);
        holder.textTime.setTag(holder);
        holder.label.setTag(holder);
    }

    @Override
    public int getItemCount() {
        return filtedList.size();
    }

    class textViewHolder extends RecyclerView.ViewHolder {
        TextView textMain;
        TextView textTime;
        TextView label;
        public textViewHolder(View itemView) {
            super(itemView);
            textTime=(TextView)itemView.findViewById(R.id.text_time);
            textMain=(TextView)itemView.findViewById(R.id.text_main);
            label=(TextView)itemView.findViewById(R.id.label);
        }
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            textViewHolder holder = (textViewHolder) view.getTag();
            int position = holder.getPosition();
            Log.e("test tag", filtedList.get(position).gettitle());
            FragmentTransaction task=ft.beginTransaction();
            task.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_pop_in, R.anim.slide_pop_out);
            task.add(R.id.your_placeholder, Detail.newInstance(filtedList.get(position)));
//            task.replace(R.id.your_placeholder, Detail.newInstance(filtedList.get(position)));
            task.hide(caller);
            task.addToBackStack(null);
            task.commit();
        }
    };

}