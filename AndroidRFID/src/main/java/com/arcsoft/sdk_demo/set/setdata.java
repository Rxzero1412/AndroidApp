package com.arcsoft.sdk_demo.set;

/**
 * Created by 83541 on 2018/5/27.
 */

public class setdata {
    static public String ip="47.107.160.19";
    static public String Url="http://IP:8080/ServerCloud/";
    static public String Urls;
    static public String username;
    static public String userid;
    static public String balance;
    static public String subjectcolor;
    public setdata(){
        Urls=new String();
        Urls=Url.replace("IP",ip);
    }


}
