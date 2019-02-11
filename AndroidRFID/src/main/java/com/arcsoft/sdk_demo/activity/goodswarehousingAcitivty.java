package com.arcsoft.sdk_demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.arcsoft.sdk_demo.AlertDialog.AlertDialogs;
import com.arcsoft.sdk_demo.R;
import com.arcsoft.sdk_demo.http.httpget;
import com.arcsoft.sdk_demo.set.setdata;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;

/**
 * Created by 83541 on 2018/5/27.
 */

public class goodswarehousingAcitivty extends Activity {

    OkHttpClient okHttpClient=new OkHttpClient();//拿到okhttpClient对象

    private EditText ed_goodsname;
    private EditText ed_commodityprice;
    private TextView tv_RFIDcount;
    private Button btn_getRFID;
    private Button btn_getgoodsRFIDOK;
    private WebView web_rfidAll;
    private String ip;
    private String url;
    private Handler handler=null;
    private Runnable update=null;

    private AlertDialogs ad;

    private View view;
    private String goods_name;
    private String goods_price;

    private boolean b=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_goodswarehousing);
        setdata sd=new setdata();
        ad=new AlertDialogs();
        view=getWindow().getDecorView().findViewById(android.R.id.content);
        ip=sd.ip;
        url=sd.Url;
        url=url.replace("IP",ip);
        okHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
        ed_goodsname= (EditText) findViewById(R.id.ed_goodsname);
        ed_commodityprice= (EditText) findViewById(R.id.ed_commodityprice);
        tv_RFIDcount= (TextView) findViewById(R.id.tv_RFIDcount);
        btn_getRFID= (Button) findViewById(R.id.btn_getRFID);
        btn_getgoodsRFIDOK= (Button) findViewById(R.id.btn_getgoodsRFIDOK);
        web_rfidAll= (WebView) findViewById(R.id.web_rfidAll);



        btn_getRFID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new httpget(url+"readrun");
                b=true;
                handler = new Handler();
                update = new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        if(b){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    web_rfidAll.loadUrl(url + "showrfid");
                                }
                            });
                        }
                        handler.postDelayed(update, 2000); //1秒后再调用
                    }
                };
                handler.post(update); //真正开始的指令；
//                Request.Builder builder=new Request.Builder();//构造Request
//                final Request request=builder
//                        .get()
//                        .url(url+"readrun")
//                        .build();
//                executeRequestrun(request);


            }
        });
        btn_getgoodsRFIDOK.setOnClickListener(new View.OnClickListener() {
            //http://localhost:8080/RFIDWebService/savegoodsdata?goods_name=牛奶&goods_price=5
            @Override
            public void onClick(View v) {
                goods_name= String.valueOf(ed_goodsname.getText());
                goods_price= String.valueOf(ed_commodityprice.getText());
                if (goods_name.equals("")&&goods_price.equals("")){
                    ad.dialog("请填写好内容",v);
                }else{
                    Request.Builder builder=new Request.Builder();//构造Request
                    String data="savegoodsdata?goods_name=A&goods_price=B";
                    data=data.replace("A",goods_name).replace("B",goods_price);
                    final Request request=builder
                            .get()
                            .url(url+data)
                            .build();
                    executeRequestget(request,v);
                    web_rfidAll= (WebView) findViewById(R.id.web_rfidAll);
                }
            }
        });
    }
    private void executeRequestget(Request request, View v) {

        Call call=okHttpClient.newCall(request);//将Request封装为Call
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Response response) throws IOException {
                final String res=response.body().string();
                if(res.equals("true")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //TODO 清空
                            b=false;
                            stopread();
                            ed_goodsname.setText("");
                            ed_commodityprice.setText("");
                            tv_RFIDcount.setText("0");
                            //web_rfidAll.destroy();
                            ad.dialog("提交成功",view);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    web_rfidAll.loadUrl(url + "showrfid");
                                }
                            });
                        }
                    });
                }
            }
        });
    }
    private void stopread(){
        new httpget(url+"readstop");
    }


    @Override
    protected void onStop() {
        super.onStop();
        b=false;
        stopread();
        web_rfidAll.destroy();
    }
}
