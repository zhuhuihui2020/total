package com.test.config;

import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import javax.servlet.http.Cookie;

public class TestConfigZT {
    public static String getTokenUrl;
    public static String getUserInfoUrl;
    public static String addUserUrl;
    public static String deleteUserUrl;
    public static String updateUserUrl;
    public static String getNameByIdUrl;


    //HttpClient对象
    public static DefaultHttpClient defaultHttpClient;

    public static Header header;

    //cookie，登录时把cookie传进去，再访问其他接口的时候，带上cookies信息，判断cookie信息是否正确
//    public static CookieStore store;
//    public static Cookie store;

    //用一个字符串记录token就可以了，不用CookieStore，因为中台返回的就不是CookieStore
    public static String cookieStr;

    //要删除的userId
    public static int userId;

}
