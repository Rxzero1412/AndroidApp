package com.arcsoft.sdk_demo.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arcsoft.sdk_demo.R;

public class Fyonghu extends Fragment {
    RelativeLayout titleA;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View fragment_voicechat=inflater.inflate(R.layout.fragment_yonghu, container, false);
        titleA = (RelativeLayout) fragment_voicechat.findViewById(R.id.layout_titlebar);
        return  fragment_voicechat;
    }

}
