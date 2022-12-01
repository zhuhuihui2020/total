package com.course.config;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.DefaultHttpClient;

public class TestConfig {
    public static String loginUrl;
    public static String updateUserInfoUrl;
    public static String getUserListUrl;
    public static String getUserInfoUrl;
    public static String addUserUrl;

    //HttpClient对象
    public static DefaultHttpClient defaultHttpClient;

    //cookie，登录时把cookie传进去，再访问其他接口的时候，带上cookies信息，判断cookie信息是否正确
    public static CookieStore store;
}
