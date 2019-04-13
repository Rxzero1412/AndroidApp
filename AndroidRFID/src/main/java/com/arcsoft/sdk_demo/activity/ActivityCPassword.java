package com.arcsoft.sdk_demo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.arcsoft.sdk_demo.R;
import com.arcsoft.sdk_demo.activity.Sign.SendCode;
import com.arcsoft.sdk_demo.activity.Sign.Sign;
import com.arcsoft.sdk_demo.set.setdata;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;

import static java.lang.Thread.sleep;

/**
 * Ccy
 * Created by 83541 on 2018/7/8.
 */
public class ActivityCPassword extends AppCompatActivity {
    private EditText Ed_cp;
    private String newpassword="";
    private OkHttpClient okHttpClient=new OkHttpClient();//拿到okhttpClient对象
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpassword);
        Button toolbarBack= (Button) findViewById(R.id.Title_Back);
        Ed_cp= (EditText) findViewById(R.id.ed_cpassword);
        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCPassword.this.finish();
            }
        });
        ((Button) findViewById(R.id.SignBtn)).setText("");
        findViewById(R.id.btn_cpassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newpassword= String.valueOf(Ed_cp.getText());
                if(!newpassword.equals("")){
                    String mBaseUrl=new setdata().Urls+"updatepassword.do?";
                    okHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
                    Request.Builder builder=new Request.Builder();//构造Request
                    System.out.println(mBaseUrl);
                    final Request request=builder
                            .get()
                            .url(mBaseUrl+"userid="+new setdata().userid+"&password="+newpassword)
                            .build();
                    executeRequest(request);
                }
                else{
                    Toasts("请输入新密码");
                }

            }
        });

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
                            Toasts("修改成功");
                            try {
                                sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            ActivityCPassword.this.finish();
                        }
                    }
                });
            }
        });
    }

}

