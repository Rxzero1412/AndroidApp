package com.arcsoft.sdk_demo.activity.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arcsoft.sdk_demo.R;
import com.arcsoft.sdk_demo.activity.fragment.homeimage.VpAdapter;
import com.arcsoft.sdk_demo.activity.fragment.homeimage.him1;
import com.arcsoft.sdk_demo.activity.fragment.homeimage.him2;
import com.arcsoft.sdk_demo.activity.fragment.homeimage.him3;
import com.arcsoft.sdk_demo.activity.fragment.homeimage.him4;
import com.arcsoft.sdk_demo.activity.fragment.homeimage.him5;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Fhome extends Fragment{
    private List<Fragment> list=new ArrayList<Fragment>();
    private ScheduledExecutorService scheduledExecutorService;
    private int currentItem; //当前页面
    private ViewPager vp;
    private ArrayList<View> dots;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View fragment_Playvideo=inflater.inflate(R.layout.fragment_home, container, false);

        return  fragment_Playvideo;
    }
    /*自动播放*/
    @Override
    public void onStart() {
        super.onStart();
        scheduledExecutorService= Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(new ViewPagerTask(),5,5, TimeUnit.SECONDS);
    }

    private class ViewPagerTask implements Runnable{
        @Override
        public void run() {
            currentItem=(currentItem+1)%5;
            handler.obtainMessage().sendToTarget();
        }
    }
    private Handler handler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            vp.setCurrentItem(currentItem);
        }
    };
    @Override
    public void onStop() {
        super.onStop();
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vp = (ViewPager) getView().findViewById(R.id.homrpage_gg);
        list.add(new him1());
        list.add(new him2());
        list.add(new him3());
        list.add(new him4());
        list.add(new him5());
        VpAdapter adapter = new VpAdapter(getChildFragmentManager(), list);
        vp.setAdapter(adapter);
    }

}
