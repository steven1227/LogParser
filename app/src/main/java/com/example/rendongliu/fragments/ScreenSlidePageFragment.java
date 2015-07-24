package com.example.rendongliu.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.rendongliu.logviewer.R;


/**
 * Created by rendong.liu on 21/07/15.
 */
public class ScreenSlidePageFragment extends Fragment{
    private BootstrapButton startbutton;
    private ImageView image_left;
    private ImageView image_right;

    public static ScreenSlidePageFragment newInstance(int i) {
        ScreenSlidePageFragment demo=new ScreenSlidePageFragment();
        Bundle b=new Bundle();
        b.putInt("pageid",i);
        demo.setArguments(b);
        return demo;

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);
        startbutton=(BootstrapButton)rootView.findViewById(R.id.start_button);
        image_left=(ImageView)rootView.findViewById(R.id.imageView);
        image_right=(ImageView)rootView.findViewById(R.id.imageView2);
        TextView textView=(TextView)rootView.findViewById(R.id.text_intro);
//        textView.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "test.ttf"));
        if(getArguments().getInt("pageid",0)==0) {
            startbutton.setVisibility(View.INVISIBLE);
            image_left.setImageDrawable(getResources().getDrawable(R.drawable.x1));
            image_right.setImageDrawable(getResources().getDrawable(R.drawable.x2));
            textView.setText("Choose the log file you want to view. It will be shown in a list which is sorted by timestamp.");
        }else if(getArguments().getInt("pageid",0)==1) {
            startbutton.setVisibility(View.INVISIBLE);
            image_left.setImageDrawable(getResources().getDrawable(R.drawable.x3));
            image_right.setImageDrawable(getResources().getDrawable(R.drawable.x6));
            textView.setText("Pull down to have the amazing refresh( if you want). Click on the item the detail can be seen.");
        }
        else if(getArguments().getInt("pageid",0)==2){
            startbutton.setVisibility(View.VISIBLE);
            image_left.setImageDrawable(getResources().getDrawable(R.drawable.x4));
            image_right.setImageDrawable(getResources().getDrawable(R.drawable.x5));
            textView.setText("The icon in the right corner can let you filter the type of logs you want to view.");
        }

        startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), MainActivity.class); //call your ViewPager class
//                startActivity(intent);
                getActivity().finish();
            }
        });
        return rootView;
    }
}
