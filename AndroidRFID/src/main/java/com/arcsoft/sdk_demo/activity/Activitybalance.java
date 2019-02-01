package com.arcsoft.sdk_demo.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.arcsoft.sdk_demo.R;
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

public class Activitybalance extends Activity {
    private EditText et_balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        et_balance= (EditText) findViewById(R.id.et_balance);
        et_balance.addTextChangedListener(new EditTextJudgeNumberWatcher(et_balance));
        findViewById(R.id.btn_addbalane).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mBaseUrl=new setdata().Urls;
                String b= String.valueOf(et_balance.getText());
                OkHttpClient okHttpClient=new OkHttpClient();//拿到okhttpClient对象
                okHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
                //发送数据
                Request.Builder builder=new Request.Builder();//构造Request
                final Request request=builder
                        .get()
                        .url(mBaseUrl+"updatebalance.do?userid="+new setdata().userid+"&balance="+b)
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
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(res.equals("true")){
                                    try {
                                        Toasts("充值成功！");
                                        sleep(2000);
                                        Activitybalance.this.finish();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });

                    }
                });
            }
        });

    }
    public void Toasts(String t){
        Toast toast=Toast.makeText(Activitybalance.this,t, Toast.LENGTH_SHORT);
        //显示toast信息
        toast.show();
    }
}
/**
 * @Description:EditText内容输入限制最大：小数点前五位，小数点后2位
 * @author: jiawen.gu
 * @CreateDate: 2017/11/6
 */

class EditTextJudgeNumberWatcher implements TextWatcher {
    private EditText editText;

    public EditTextJudgeNumberWatcher(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        judgeNumber(editable,editText);
    }

    /**
     * 金额输入框中的内容限制（最大：小数点前五位，小数点后2位）
     *
     * @param edt
     */
    public static void judgeNumber(Editable edt,EditText editText) {

        String temp = edt.toString();
        int posDot = temp.indexOf(".");//返回指定字符在此字符串中第一次出现处的索引
        int index = editText.getSelectionStart();//获取光标位置
        //  if (posDot == 0) {//必须先输入数字后才能输入小数点
        //  edt.delete(0, temp.length());//删除所有字符
        //  return;
        //  }
        if (posDot < 0) {//不包含小数点
            if (temp.length() <= 5) {
                return;//小于五位数直接返回
            } else {
                edt.delete(index-1, index);//删除光标前的字符
                return;
            }
        }
        if (posDot > 5) {//小数点前大于5位数就删除光标前一位
            edt.delete(index-1, index);//删除光标前的字符
            return;
        }
        if (temp.length() - posDot - 1 > 2)//如果包含小数点
        {
            edt.delete(index-1, index);//删除光标前的字符
            return;
        }
    }
}
