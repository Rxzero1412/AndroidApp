package com.arcsoft.sdk_demo.activity.Sign;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.arcsoft.sdk_demo.set.setdata;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.arcsoft.sdk_demo.R;
import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;

import static java.lang.Thread.sleep;

/**
 * Ccy
 * Created by 83541 on 2018/7/8.
 * 电话号码注册，短信验证，添加用户名，密码
 */
public class Sign extends AppCompatActivity {
    private EditText Ed_MID;
    private EditText Ed_Ps1;
    private EditText Ed_Ps2;
    private EditText Ed_YZM;
    String YZM_True="";
    private String Mid;
    private OkHttpClient okHttpClient=new OkHttpClient();//拿到okhttpClient对象
    //#707070
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        Button signbtn= (Button) findViewById(R.id.SignBtn);
        signbtn.setText(" ");
        RelativeLayout titleA = (RelativeLayout) findViewById(R.id.layout_titlebar);
        titleA.setBackgroundColor(android.graphics.Color.parseColor("#707070"));
        Button toolbarBack= (Button) findViewById(R.id.Title_Back);
        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sign.this.finish();
            }
        });
        ((Button) findViewById(R.id.SignBtn)).setText("");
        Ed_MID= (EditText) findViewById(R.id.sign_YZM);
        Ed_Ps1= (EditText) findViewById(R.id.Sign_Pass1);
        Ed_Ps2= (EditText) findViewById(R.id.Sign_Pass2);
        Ed_YZM= (EditText) findViewById(R.id.sign_YZM_ED);
        findViewById(R.id.Sign_btn_YZM).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mid= String.valueOf(Ed_MID.getText());
                if(!Mid.equals("")){
                    new Thread(new Runnable(){
                        @Override
                        public void run() {
                            SendCode SC=new SendCode();
                            try {
                                YZM_True=SC.SendCode(Mid);
                                System.out.println(YZM_True);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
                else{
                    Toasts("请输入电话号码");
                }
            }});
        findViewById(R.id.sign_btn_zc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p1= String.valueOf(Ed_Ps1.getText());
                String p2= String.valueOf(Ed_Ps2.getText());
                if(!p1.equals("") && !p2.equals("")){
                    if(p1.equals(p2)){
                        while (YZM_True.equals("")) System.out.println("xxx:"+YZM_True);
                        if (YZM_True.equals(Ed_YZM.getText().toString())) {
                            //注册成功
                            doSign(Mid,p1);
                        } else Toasts("验证码错误");
                    }
                    else Toasts("密码不一致,请重新输入");
                }
                else Toasts("请输入密码");
            }});
    }

    private void doSign(String m,String p) {
        String mBaseUrl=new setdata().Urls+"usersign.do?";
        okHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
        Request.Builder builder=new Request.Builder();//构造Request
        System.out.println(mBaseUrl);
        final Request request=builder
                .get()
                .url(mBaseUrl+"username="+m+"&password="+p)
                .build();
        executeRequest(request);
    }

    public void Toasts(String t){
        Toast toast=Toast.makeText(this,t, Toast.LENGTH_SHORT);
        //显示toast信息
        toast.show();
    }

    private void executeRequest(Request request) {
        Call call=okHttpClient.newCall(request);//将Request封装为Call
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Response response) throws IOException {
                final String res=response.body().string();
                System.out.println(res);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(!res.equals("false")){
                            Toasts("注册成功");
                            try {
                                sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Sign.this.finish();
                        }
                    }
                });
            }
        });
    }
}

