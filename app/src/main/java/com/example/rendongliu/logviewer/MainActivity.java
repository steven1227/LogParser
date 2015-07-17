package com.example.rendongliu.logviewer;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import com.example.rendongliu.fragments.Update;
import com.example.rendongliu.fragments.WelcomePage;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import java.io.File;
import java.util.ArrayList;


public class MainActivity  extends ActionBarActivity implements OnMenuItemLongClickListener,OnMenuItemClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.your_placeholder, new WelcomePage(), "welcome");
        ft.commit();
    }

    @Override
    public void onMenuItemClick(View view, int i) {
        Update instance=(Update)getSupportFragmentManager().findFragmentByTag("filefinder");
        instance.updateItem(i);
    }

    @Override
    public void onMenuItemLongClick(View view, int i) {
        Toast.makeText(this, "Clicked on position: " +i, Toast.LENGTH_SHORT).show();
    }

}
