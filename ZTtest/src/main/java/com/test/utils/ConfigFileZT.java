package com.test.utils;

import com.test.model.InterfaceNameZT;

import java.util.Locale;
import java.util.ResourceBundle;

public class ConfigFileZT {
    private static ResourceBundle bundle=ResourceBundle.getBundle("applicationZT", Locale.CHINA);

    public static String getUrl(InterfaceNameZT name){
        String address=bundle.getString("test.url");
        String uri="";
        //最终的测试地址
        String testUrl;
        if(name== InterfaceNameZT.GETTOKEN){
            uri=bundle.getString("getToken.uri");
        }else if(name==InterfaceNameZT.GETUSERINFO){
            uri=bundle.getString("getUserInfo.uri");
        }else if(name==InterfaceNameZT.ADDUSER){
            uri=bundle.getString("addUser.uri");
        }else if(name==InterfaceNameZT.DELETEUSER){
            uri=bundle.getString("deleteUser.uri");
        }else if(name==InterfaceNameZT.UPDATEUSER){
            uri=bundle.getString("updateUser.uri");
        }else if(name==InterfaceNameZT.GETNAMEBYID){
            uri=bundle.getString("getUserNameById.uri");
        }

        testUrl=address+uri;

        return testUrl;

    }
}
