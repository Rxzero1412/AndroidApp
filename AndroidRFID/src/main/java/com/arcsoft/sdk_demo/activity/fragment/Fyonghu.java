package com.arcsoft.sdk_demo.activity.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arcsoft.sdk_demo.R;
import com.arcsoft.sdk_demo.activity.Activityaddface;
import com.arcsoft.sdk_demo.activity.Application;
import com.arcsoft.sdk_demo.activity.DetecterActivity;
import com.arcsoft.sdk_demo.activity.homeActivity;
import com.arcsoft.sdk_demo.activity.usersignActivity;
import com.arcsoft.sdk_demo.set.setdata;

public class Fyonghu extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View fragment=inflater.inflate(R.layout.fragment_yonghu, container, false);
        fragment.findViewById(R.id.btn_addface).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(getActivity(), Activityaddface.class);
                startActivityForResult(Intent,Activity.RESULT_FIRST_USER);
            }
        });
        TextView f_tv_username= (TextView) fragment.findViewById(R.id.f_tv_username);
        f_tv_username.setText("用户名："+new setdata().username);

        return  fragment;
    }


}
