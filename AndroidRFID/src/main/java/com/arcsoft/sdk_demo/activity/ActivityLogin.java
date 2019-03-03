package com.arcsoft.sdk_demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.arcsoft.sdk_demo.R;
import com.arcsoft.sdk_demo.Via.CircleImageView;
import com.arcsoft.sdk_demo.activity.Sign.Sign;
import com.arcsoft.sdk_demo.set.setdata;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;


/*
* Ccy 2019.02.11
* 登陆功能
* */
public class ActivityLogin extends Activity {
    int viacount=-2;
    String subjectcolor="default";
    private String Username;
    private String Password;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

        Changestyle(findViewById(R.id.Login_Activity));
        Button toolbarBack= (Button) findViewById(R.id.Title_Back);
        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityLogin.this.finish();
            }
        });
        findViewById(R.id.SignBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityLogin.this, Sign.class));
            }
        });
        findViewById(R.id.Login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Username="";
                EditText editText1 =(EditText)findViewById(R.id.Login_UserText_UserText);
                Username=editText1.getText().toString();
                Password="";
                EditText editText2 =(EditText)findViewById(R.id.Login_PasswordText_PasswordText);
                Password=editText2.getText().toString();
                if(Username.equals("")){
                    Toasts("请输入用户名");
                    return;
                }
                else {
                    if(Password.equals("")){
                        Toasts("请输入密码");
                        return;
                    }
                }
                Loginjudge(Username,Password);
            }
        });
    }
    public void Changestyle(View view){
        //#1296db    #ed4255
        viacount++;
        style(viacount);
    }
    public void style(int menu){
        RelativeLayout titleA = (RelativeLayout) findViewById(R.id.layout_titlebar);
        CircleImageView CIA = (CircleImageView) findViewById(R.id.Via_image);
        ImageView ivuser= (ImageView) findViewById(R.id.Login_UserText_UserImage);
        ImageView ivpassword= (ImageView) findViewById(R.id.Login_PasswordText_PasswordImage);
        Button btn= (Button) findViewById(R.id.Login_button);
        if(menu%2==0){
            titleA.setBackgroundColor(android.graphics.Color.parseColor("#1296db"));
            CIA.setBorderColor(android.graphics.Color.parseColor("#1296db"));
            CIA.setImageResource(R.drawable.mvia);
            ivuser.setImageResource(R.drawable.usertextm);
            ivpassword.setImageResource(R.drawable.passtextm);
            btn.setBackgroundColor(android.graphics.Color.parseColor("#1296db"));
            subjectcolor="M";
        }
        else if(menu==-1){
            titleA.setBackgroundColor(android.graphics.Color.parseColor("#707070"));
            CIA.setBorderColor(android.graphics.Color.parseColor("#707070"));
            CIA.setImageResource(R.drawable.via);
            ivuser.setImageResource(R.drawable.usertext);
            ivpassword.setImageResource(R.drawable.passtext);
            btn.setBackgroundColor(android.graphics.Color.parseColor("#707070"));
            subjectcolor="default";
        }
        else{
            titleA.setBackgroundColor(android.graphics.Color.parseColor("#ed4255"));
            CIA.setBorderColor(android.graphics.Color.parseColor("#ed4255"));
            CIA.setImageResource(R.drawable.fvia);
            ivuser.setImageResource(R.drawable.usertextf);
            ivpassword.setImageResource(R.drawable.passtextf);
            btn.setBackgroundColor(android.graphics.Color.parseColor("#ed4255"));
            subjectcolor="F";
        }
    }
    public void Loginjudge(String Username,String Password){
        String mBaseUrl=new setdata().Urls;
        OkHttpClient okHttpClient=new OkHttpClient();//拿到okhttpClient对象
        okHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
        //发送数据
        Request.Builder builder=new Request.Builder();//构造Request
        final Request request=builder
                .get()
                .url(mBaseUrl+"userlogin.do?username="+Username+"&password="+Password)
                .build();
        Call call=okHttpClient.newCall(request);//将Request封装为Call
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Response response) throws IOException {
                final String res=response.body().string();
                if(!res.equals("false")){
                    startneAty(res);
                }
                else {
                    Looper.prepare();
                    Toasts("用户名或密码错误，请重新输入");
                    //显示toast信息
                    Looper.loop();
                }
            }
        });
    }
    public void Toasts(String t){
        Toast toast=Toast.makeText(this,t, Toast.LENGTH_SHORT);
        //显示toast信息
        toast.show();
    }
    /*
    * 启动首页
    * */
    public void startneAty(String userid){
        Intent i=new Intent(ActivityLogin.this,homesActivity.class);
        i.putExtra("subjectcolor",subjectcolor);
        new setdata().userid=userid;
        new setdata().username=Username;
        startActivity(i);
        ActivityLogin.this.finish();
    }
}
