package com.example.rendongliu.fragments;

/**
 * Created by rendong.liu on 08/07/15.
 */

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rendongliu.logviewer.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import fr.tvbarthel.lib.blurdialogfragment.BlurDialogFragment;
import fr.tvbarthel.lib.blurdialogfragment.SupportBlurDialogFragment;

public class SimpleDialog extends SupportBlurDialogFragment
{

    private ListView listview;
    private File[] a;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dialog_list,container,false);
        listview=(ListView)view.findViewById(R.id.list);
        File f=new File(Environment.getExternalStorageDirectory()+"/"+"LogFiles");

        if(f.exists()) {

            a=f.listFiles();
            if(a.length==0){
                Toast.makeText(getActivity(),"No log files",Toast.LENGTH_SHORT).show();
            }
            Arrays.sort(a, new Comparator<File>() {
                @Override
                public int compare(File lhs, File rhs) {
                    if (lhs.lastModified() > rhs.lastModified())
                        return 1;
                    else if (lhs.lastModified() < rhs.lastModified())
                        return -1;
                    else
                        return 0;
                }
            });

        }else{
            a=new File[0];
        }
        listview.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return a.length;
            }

            @Override
            public Object getItem(int position) {
                return a[position];
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View row=convertView;
                SimpleDateFormat sdf = new SimpleDateFormat("h:mm, MMM dd");
                LayoutInflater inflater = getActivity().getLayoutInflater();
                row=inflater.inflate(R.layout.listview_item_row,parent,false);
                ((TextView)row.findViewById(R.id.title)).setText(a[position].getName());
                ((TextView)row.findViewById(R.id.time)).setText("Last Modified "+sdf.format(new Date(a[position].lastModified())));
                return row ;
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right,R.anim.slide_pop_in,R.anim.slide_pop_out);
                ft.replace(R.id.your_placeholder, FileFinder.newInstance(a[position].getName()),"filefinder");
                ft.addToBackStack(null);
                ft.commit();
                Dialog temp=getDialog();
                temp.dismiss();
            }
        });

        return view;
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Dialog a=super.onCreateDialog(savedInstanceState);
        a.setTitle("Choose the Log file ");
        a.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        return a;
    }

    @Override
    protected float getDownScaleFactor() {
        // Allow to customize the down scale factor.
        return 5;
    }

    @Override
    protected int getBlurRadius() {
        // Allow to customize the blur radius factor.
        return 7;
    }

    @Override
    protected boolean isActionBarBlurred() {
        // Enable or disable the blur effect on the action bar.
        // Disabled by default.
        return true;
    }

    @Override
    protected boolean isDimmingEnable() {
        // Enable or disable the dimming effect.
        // Disabled by default.
        return true;
    }

    @Override
    protected boolean isRenderScriptEnable() {
        // Enable or disable the use of RenderScript for blurring effect
        // Disabled by default.
        return true;
    }


}