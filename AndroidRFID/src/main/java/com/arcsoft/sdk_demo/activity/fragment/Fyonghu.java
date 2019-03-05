package com.arcsoft.sdk_demo.activity.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arcsoft.sdk_demo.R;
import com.arcsoft.sdk_demo.Via.CircleImageView;
import com.arcsoft.sdk_demo.activity.Activityaddface;
import com.arcsoft.sdk_demo.activity.Activitybalance;
import com.arcsoft.sdk_demo.set.setdata;

public class Fyonghu extends Fragment {
    private TextView f_tv_username;
    private TextView f_tv_balance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View fragment=inflater.inflate(R.layout.fragment_yonghu, container, false);
        CircleImageView CIA = (CircleImageView) fragment.findViewById(R.id.Via_image);
        String subjectcolor=new setdata().subjectcolor;
        if(subjectcolor.equals("M")){
            CIA.setBorderColor(android.graphics.Color.parseColor("#1296db"));
            CIA.setImageResource(R.drawable.mvia);
        }
        else if(subjectcolor.equals("F")){
            CIA.setBorderColor(android.graphics.Color.parseColor("#ed4255"));
            CIA.setImageResource(R.drawable.fvia);
        }
        else{
            CIA.setBorderColor(android.graphics.Color.parseColor("#707070"));
            CIA.setImageResource(R.drawable.via);
        }
        fragment.findViewById(R.id.btn_addface).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(getActivity(), Activityaddface.class);
                startActivityForResult(Intent,Activity.RESULT_FIRST_USER);
            }
        });
        fragment.findViewById(R.id.btn_balance).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(getActivity(), Activitybalance.class);
                startActivityForResult(Intent,Activity.RESULT_FIRST_USER);
            }
        });
        f_tv_balance=(TextView)fragment.findViewById(R.id.f_tv_balance);
        f_tv_username= (TextView) fragment.findViewById(R.id.f_tv_username);
        f_tv_username.setText("用户名："+new setdata().username);
        return  fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        f_tv_balance.setText("余额："+new setdata().balance);
    }
}
