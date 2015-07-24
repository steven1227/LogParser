package com.example.rendongliu.fragments;


import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rendongliu.adapters.MyRecyclerAdapter;
import com.example.rendongliu.log.TrackEvent;
import com.example.rendongliu.log.TrackInfo;
import com.example.rendongliu.log.TrackLogger;
import com.example.rendongliu.log.TrackPage;
import com.example.rendongliu.logviewer.R;
import com.google.gson.Gson;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.phoenix.PullToRefreshView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;

/**
 * Created by rendong.liu on 08/07/15.
 */
public class FileFinder extends Fragment implements AdapterView.OnItemSelectedListener , Update{
    private boolean loading = true;
//    private TextView textView;
    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter listadapter;
//    private Spinner spinner;
    private PullToRefreshView mPullToRefreshView;
    private ScaleInAnimationAdapter scaleInAnimationAdapter;

    private TextView logtype;

    private DialogFragment mMenuDialogFragment;

    private JSONArray array;
    private String[] text={"stamp1","stamp2","stamp3"};
    private List<String> text1= Arrays.asList(text);

    private ArrayList<String> al = new ArrayList<String>(text1);
    private List<TrackInfo> logList;

    private TrackLogger trackLogger;
    private String[] choice;

    public static FileFinder  newInstance (String filename){
        FileFinder demo=new FileFinder();
        Bundle args=new Bundle();
        args.putString("filename", filename);
        demo.setArguments(args);
        return demo;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
        initMenuFragment();
        Log.e("1 st", "Oncreate");
        trackLogger = new TrackLogger();
        new AsyncFiletask().execute(getArguments().getString("filename"));

    }

    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(false);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
    }

    private List<MenuObject> getMenuObjects() {
        List<MenuObject> menuObjects = new ArrayList<>();

        MenuObject close = new MenuObject();
        close.setResource(R.drawable.icn_close);

        MenuObject send = new MenuObject("All logs");
        send.setResource(R.drawable.icn_1);

        MenuObject like = new MenuObject("Event logs");
        like.setResource(R.drawable.icn_1);


        MenuObject addFr = new MenuObject("Page logs");
        addFr.setResource(R.drawable.icn_1);

        menuObjects.add(close);
        menuObjects.add(send);
        menuObjects.add(like);
        menuObjects.add(addFr);
        return menuObjects;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.file_finder,container,false);

        Log.e("2 st","Oncreateview");
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv);
        choice=getResources().getStringArray(R.array.choice_array);
        mPullToRefreshView= (PullToRefreshView) view.findViewById(R.id.swipeRefreshLayout);
        logtype=(TextView)view.findViewById(R.id.log_type);
        Toolbar mToolbar = (Toolbar)view.findViewById(R.id.toolbar);
        TextView mToolBarTextView = (TextView)view.findViewById(R.id.text_view_toolbar_title);
        mToolBarTextView.setText(getArguments().getString("filename"));
        mToolBarTextView.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "galderglynn_titling_bd.ttf"));
        ((ActionBarActivity)getActivity()).setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.btn_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("logtype", String.valueOf(logtype.getText()));
        Log.e("logtype",String.valueOf(logtype.getText()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu:
                if (getActivity().getSupportFragmentManager().findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                    mMenuDialogFragment.show(getActivity().getSupportFragmentManager(), ContextMenuDialogFragment.TAG);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                trackLogger = new TrackLogger();
                new AsyncFiletask().execute(getArguments().getString("filename"));
                mPullToRefreshView.setRefreshing(false);

            }
        });
        Log.e("Text", "how it works");
        logtype.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "leagueGothic-Regular.otf"));
        mRecyclerView.setAdapter(scaleInAnimationAdapter);
        Log.e("3 st", "finsh?");
        if(savedInstanceState!=null){
            logtype.setText(savedInstanceState.getString("logtype"));
        }
//        mRecyclerView.setOnScrollListener(new OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                int visibleItemCount = layoutManager.getChildCount();
//                int totalItemCount = layoutManager.getItemCount();
//                int  pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();
//                if(loading)
//            }
//        });
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position==1||position==2){
            mPullToRefreshView.setEnabled(false);
        }else {
            mPullToRefreshView.setEnabled(true);
        }
        Toast.makeText(getActivity(),"Show "+choice[position],Toast.LENGTH_SHORT).show();
        listadapter.Update(position);
        scaleInAnimationAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(getActivity(),"nothing choose",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateItem(int position) {
        if(position!=0) {
            if (position == 2 || position == 3) {
                mPullToRefreshView.setEnabled(false);
            } else {
                mPullToRefreshView.setEnabled(true);
            }
            logtype.setText(choice[position-1]);
            Toast.makeText(getActivity(), "Show " + choice[position - 1], Toast.LENGTH_SHORT).show();
            listadapter.Update(position - 1);
            scaleInAnimationAdapter.notifyDataSetChanged();
        }
    }


    public class AsyncFiletask extends AsyncTask<String,Void,Integer>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... params) {
            File f=new File(Environment.getExternalStorageDirectory()+"/"+"LogFiles"+"/"+params[0]);
            FileInputStream stream = null;
            try {
                stream = new FileInputStream(f);
                String jsonStr = null;
                FileChannel fc = stream.getChannel();
                MappedByteBuffer bb = null;
                bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
                jsonStr = Charset.defaultCharset().decode(bb).toString();
                if(jsonStr.length()!=0) {
                    Gson gson = new Gson();
                    JSONArray jarray = new JSONArray(jsonStr);

                    for (int i = 0; i < jarray.length(); i ++) {
                        JSONObject object = jarray.getJSONObject(i);
                        TrackInfo info = null;
                        if (object.length() > 3) {
                            info = gson.fromJson(object.toString(), TrackEvent.class);
                        } else {
                            info = gson.fromJson(object.toString(), TrackPage.class);
                        }
                        trackLogger.add(info);
                    }
                }

            }catch (IOException e) {
                e.printStackTrace();
            }catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            listadapter=new MyRecyclerAdapter(trackLogger,getActivity().getSupportFragmentManager(),FileFinder.this);
            AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(listadapter);
            alphaAdapter.setDuration(1000);
            alphaAdapter.setInterpolator(new OvershootInterpolator());
            scaleInAnimationAdapter=new ScaleInAnimationAdapter(alphaAdapter);
            if(mRecyclerView!=null) {
                mRecyclerView.setAdapter(scaleInAnimationAdapter);
            }
            Log.e("3 st", "can you find mRecyclerview?");
        }
    }

}


