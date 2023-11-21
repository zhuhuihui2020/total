package com.test.cases;

import com.test.config.TestConfigZT;
import com.test.model.UpdateUserCaseZT;
import com.test.utils.DatabaseUtil_local;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class UpdateUserTestZT {

    @Test(dependsOnGroups = "getToken",description = "修改用户信息")
    public void updateUserInfo() throws IOException, InterruptedException {
        SqlSession sessionlocal= DatabaseUtil_local.getSqlSession();
        UpdateUserCaseZT updateUserCaseZT=sessionlocal.selectOne("updateUserCase",1);
        System.out.println(updateUserCaseZT.toString());
        System.out.println(TestConfigZT.updateUserUrl);

        String strReturn=getJsonResult(updateUserCaseZT);

        Thread.sleep(3000);

        Assert.assertEquals(strReturn,updateUserCaseZT.getExpected());
    }

    private String getJsonResult(UpdateUserCaseZT updateUserCaseZT) throws IOException {
        String urlStr=TestConfigZT.updateUserUrl+"/"+updateUserCaseZT.getUserId();
        System.out.println("urlStr："+urlStr);
        HttpPut put=new HttpPut(urlStr);

        JSONObject param=new JSONObject();
        param.put("name",updateUserCaseZT.getName());
        param.put("password",updateUserCaseZT.getPassword());
        param.put("phone",updateUserCaseZT.getPhone());
        param.put("photoUrl",updateUserCaseZT.getPhotoUrl());

        //设置头信息
        put.setHeader("content-type","application/json");
        put.setHeader("authorization",TestConfigZT.cookieStr);
        StringEntity entity=new StringEntity(param.toString(),"utf-8");
        //调用setEntity(HttpEntity entity)方法来设置请求参数。
        put.setEntity(entity);

        put.setHeader("authorization",TestConfigZT.cookieStr);
        String result;

        HttpResponse response=TestConfigZT.defaultHttpClient.execute(put);

        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println("result:"+result);

        return result;

    }

}
