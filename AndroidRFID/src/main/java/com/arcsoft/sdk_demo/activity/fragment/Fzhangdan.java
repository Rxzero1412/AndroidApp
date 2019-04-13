package com.arcsoft.sdk_demo.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.arcsoft.sdk_demo.R;
import com.arcsoft.sdk_demo.set.setdata;


public class Fzhangdan extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        String mBaseUrl=new setdata().Urls;
        View fragment=inflater.inflate(R.layout.fragment_zhangdan, container, false);
        WebView webView = (WebView) fragment.findViewById(R.id.zhangdan_id);
        webView.loadUrl(mBaseUrl+"showhistorical.do");//加载url
        return  fragment;
    }
}
