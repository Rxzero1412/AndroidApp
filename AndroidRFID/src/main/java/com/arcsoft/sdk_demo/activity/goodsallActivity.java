package com.arcsoft.sdk_demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.arcsoft.sdk_demo.R;
import com.arcsoft.sdk_demo.set.setdata;

/**
 * Created by 83541 on 2018/5/27.
 */

public class goodsallActivity extends Activity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_goodsall);
        webView= (WebView) findViewById(R.id.web_goodsall);
        setdata sd=new setdata();
        String url=sd.Urls+"showallgoodlist";
        webView.loadUrl(url);

    }
}
