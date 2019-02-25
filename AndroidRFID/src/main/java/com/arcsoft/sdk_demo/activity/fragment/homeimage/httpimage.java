package com.arcsoft.sdk_demo.activity.fragment.homeimage;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;

import com.squareup.okhttp.*;
import com.squareup.okhttp.Request;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 83541 on 2017/8/13.
 */

public class httpimage{
    private static Bitmap bitmap = null;
    public Bitmap getImageViewInputStream(String image) {
        String mBaseUrl = "http://192.168.0.7:8080/Imooc_http/image/";
        Request.Builder builder = new Request.Builder();//构造Request
        final Request request = builder
                .get()
                .url(mBaseUrl + image)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();//拿到okhttpClient对象
        Call call = okHttpClient.newCall(request);//将Request封装为Call
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                InputStream is = response.body().byteStream();
                bitmap = BitmapFactory.decodeStream(is);

            }
        });
        while (bitmap == null) ;

        /*
        * save
        * */
        File f = new File("/storage/emulated/0/tencent/MicroMsg/WeiXin",image);
        image=null;
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return bitmap;
    }
    public Bitmap getLoacalBitmap(String url) {
        url="/storage/emulated/0/tencent/MicroMsg/WeiXin"+url;
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis); ///把流转化为Bitmap图片        
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
