package com.example.rendongliu.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableWrapper;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rendongliu.log.TrackEvent;
import com.example.rendongliu.log.TrackInfo;
import com.example.rendongliu.log.TrackLogger;
import com.example.rendongliu.log.TrackPage;
import com.example.rendongliu.logviewer.R;

/**
 * Created by rendong.liu on 16/07/15.
 */
public class Detail extends Fragment {

    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;

    public static Detail newInstance(TrackInfo temp){
        Detail demo =new Detail();
        Bundle args=new Bundle();
        if(temp instanceof TrackPage){
            args.putString("PageName",((TrackPage)temp).getPagename());
            args.putString("Time",temp.getTime());
            args.putBoolean("page", true);
        }else if(temp instanceof TrackEvent){
            args.putString("Action",((TrackEvent) temp).getAction());
            args.putString("Category",((TrackEvent) temp).getCategory());
            args.putString("Label",((TrackEvent) temp).getLabel());
            args.putString("Value", String.valueOf(((TrackEvent) temp).getValue()));
            args.putString("Time",temp.getTime());
            args.putBoolean("page", false);
        }
        demo.setArguments(args);
        return demo;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.detail_item,container,false);
        Bundle args=getArguments();

        textView1=(TextView)view.findViewById(R.id.line1);
        textView2=(TextView)view.findViewById(R.id.line2);
        textView3=(TextView)view.findViewById(R.id.line3);
        textView4=(TextView)view.findViewById(R.id.line4);
        textView5=(TextView)view.findViewById(R.id.line5);

        initialize(args);

        Toolbar mToolbar = (Toolbar)view.findViewById(R.id.toolbar);
        TextView mToolBarTextView = (TextView)view.findViewById(R.id.text_view_toolbar_title);
        mToolBarTextView.setText("Detail");
        ((ActionBarActivity)getActivity()).setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.btn_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        ((TextView)view.findViewById(R.id.log_type)).setText("");
        return view;
    }

    private void initialize(Bundle args){
        if(args.getBoolean("page")){
            textView1.setText("PageName: "+args.getString("PageName"));
            textView2.setText("Time: "+args.getString("Time"));
        }else {
            textView1.setText("Action: "+args.getString("Action"));
            textView2.setText("Category: "+args.getString("Category"));
            textView3.setText("Label: "+args.getString("Label"));
            textView4.setText("Value: "+args.getString("Value"));
            textView5.setText("Time: "+args.getString("Time"));
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
