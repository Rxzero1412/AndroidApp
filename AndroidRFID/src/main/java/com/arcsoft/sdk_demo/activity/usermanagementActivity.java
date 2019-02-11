package com.arcsoft.sdk_demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;

import com.arcsoft.sdk_demo.AlertDialog.AlertDialogs;
import com.arcsoft.sdk_demo.R;
import com.arcsoft.sdk_demo.http.httpget;
import com.arcsoft.sdk_demo.set.setdata;

/**
 * Created by 83541 on 2018/5/27.
 */

public class usermanagementActivity extends Activity {

    private WebView webView;
    private EditText ed_usermanagement_num;
    private EditText ed_usermanagement_Rechargemoney;
    private EditText ed_usermanagement_password;
    private setdata sd;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_usermanagement);
        ed_usermanagement_num= (EditText) findViewById(R.id.ed_usermanagement_num);
        ed_usermanagement_Rechargemoney= (EditText) findViewById(R.id.ed_usermanagement_Rechargemoney);
        ed_usermanagement_password= (EditText) findViewById(R.id.ed_usermanagement_password);
        webView= (WebView) findViewById(R.id.web_usermanagement);
        sd=new setdata();
        url=sd.Urls+"showuser";
        webView.loadUrl(url);
        findViewById(R.id.ed_usermanagement_sign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(usermanagementActivity.this, usersignActivity.class));
            }
        });
        findViewById(R.id.ed_usermanagement_Determine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ed_usermanagement_password.getText().toString().equals("123456")){
                    String n=ed_usermanagement_num.getText().toString();
                    String b=ed_usermanagement_Rechargemoney.getText().toString();
                    String userupdate=sd.Urls+"uesrbalanceupdate?num="+n+"&balance="+b;
                    new httpget(userupdate);
                    new AlertDialogs().dialog("充值成功",v);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ed_usermanagement_num.setText("");
                            ed_usermanagement_Rechargemoney.setText("");
                            ed_usermanagement_password.setText("");
                            webView.loadUrl(url);
                        }
                    });
                }else {
                    new AlertDialogs().dialog("密码错误，请重新输入",v);
                }
            }
        });
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        webView.loadUrl(url);
    }
}