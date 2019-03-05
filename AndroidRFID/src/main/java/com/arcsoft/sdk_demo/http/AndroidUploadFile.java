package com.arcsoft.sdk_demo.http;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;

public class AndroidUploadFile {
    public static  String uoloadFile(String filePath,String urlServer) throws ClientProtocolException, IOException {
                HttpClient httpClient=new DefaultHttpClient();
                httpClient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION,HttpVersion.HTTP_1_1);
                HttpPost httpPost=new HttpPost(urlServer);
                MultipartEntity multipartEntity=new MultipartEntity();
                File file=new File(filePath);
                ContentBody contentBody=new FileBody(file);
                multipartEntity.addPart("file",contentBody);
                httpPost.setEntity(multipartEntity);
                HttpResponse response=httpClient.execute(httpPost);
                StatusLine statusLine= response.getStatusLine();
                if(statusLine.getStatusCode()==HttpStatus.SC_OK){
                    HttpEntity entity=response.getEntity();
                    String result=EntityUtils.toString(entity);
                    Log.i("TAG","*******"+result);
                }else{
                    Log.i("TAG","请求出了问题");
                }
                return null;
    }


}
