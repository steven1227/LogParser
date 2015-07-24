package com.example.rendongliu.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import fr.tvbarthel.lib.blurdialogfragment.SupportBlurDialogFragment;

/**
 * Created by rendong.liu on 20/07/15.
 */
public class DeleteDialog extends SupportBlurDialogFragment {

    private File[] a;
    private ArrayList<Integer> selectedItemsIndexList;
    private String[] filelist;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        File f=new File(Environment.getExternalStorageDirectory()+"/"+"LogFiles");
        selectedItemsIndexList=new ArrayList<>();

        if(f.exists()) {

            a=f.listFiles();
            if(a.length==0){
                Toast.makeText(getActivity(), "No log files", Toast.LENGTH_SHORT).show();
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
        filelist=new String[a.length];
        for(int i=0;i<a.length;i++){
            filelist[i]=a[i].getName();
        }

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose Files to delete")
        .setMultiChoiceItems(filelist, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if(isChecked){
                    selectedItemsIndexList.add(which);
                }else if(selectedItemsIndexList.contains(which)){
                    selectedItemsIndexList.remove(Integer.valueOf(which));
                }
            }
        })
        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getDialog().cancel();
                for(int i :selectedItemsIndexList){
                    File f=new File(Environment.getExternalStorageDirectory()+"/"+"LogFiles"+"/"+filelist[i]);
                    Log.e("testing"," "+f.delete());
                }
            }
        })
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getDialog().cancel();
            }
        });

        return builder.create();
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
