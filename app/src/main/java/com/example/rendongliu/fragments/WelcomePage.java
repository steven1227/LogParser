package com.example.rendongliu.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.rendongliu.logviewer.R;

/**
 * Created by rendong.liu on 08/07/15.
 */
public class WelcomePage extends Fragment{
    private BootstrapButton button;
    private BootstrapButton button2;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.welcome_page,container,false);
        button = (BootstrapButton)view.findViewById(R.id.button);
        button2=(BootstrapButton)view.findViewById(R.id.button2);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                SimpleDialog dialog = new SimpleDialog();
                ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_pop_in, R.anim.slide_pop_out);
                dialog.show(getActivity().getSupportFragmentManager(), "dialog");
                ft.commit();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
    }

}

