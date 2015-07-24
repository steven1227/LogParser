package com.example.rendongliu.logviewer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;

import com.example.rendongliu.fragments.Update;
import com.example.rendongliu.fragments.WelcomePage;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;


public class MainActivity  extends ActionBarActivity implements OnMenuItemLongClickListener,OnMenuItemClickListener{
    public static final String MyPrefs = "MyPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        setContentView(R.layout.activity_main);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.your_placeholder, new WelcomePage(), "welcome");
        ft.commit();

        SharedPreferences sp = getSharedPreferences(MyPrefs, Context.MODE_PRIVATE);
        if (!sp.getBoolean("first", false)) {
            SharedPreferences.Editor editor = sp.edit();
//            editor.putBoolean("first", true);
            editor.commit();
            Intent intent = new Intent(this, Intro_acitivity.class); //call your ViewPager class
            startActivity(intent);
        }
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.swipeback_stack_to_front,
                R.anim.swipeback_stack_right_out);
    }
}
