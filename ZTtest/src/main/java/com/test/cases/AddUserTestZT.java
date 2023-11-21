package com.test.cases;

import com.test.config.TestConfigZT;
import com.test.model.AddUserCaseZT;
import com.test.utils.DatabaseUtil_ZT;
import com.test.utils.DatabaseUtil_local;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class AddUserTestZT {

    @Test(dependsOnGroups = "getToken",description = "添加用户")
    public void addUser() throws IOException, InterruptedException {
        SqlSession sessionZT= DatabaseUtil_ZT.getSqlSession();
        SqlSession sessionlocal= DatabaseUtil_local.getSqlSession();
        AddUserCaseZT addUserCaseZT=sessionlocal.selectOne("addUserCase",1);
        System.out.println(addUserCaseZT.toString());
        System.out.println(TestConfigZT.addUserUrl);

        String strReturn=getJsonResult(addUserCaseZT);

        Thread.sleep(3000);

        //验证
        Assert.assertTrue(strReturn.contains(addUserCaseZT.getExpected()));
    }

    private String getJsonResult(AddUserCaseZT addUserCaseZT) throws IOException {
        HttpPost post=new HttpPost(TestConfigZT.addUserUrl);
        JSONObject param=new JSONObject();
        param.put("name",addUserCaseZT.getName());
        param.put("password",addUserCaseZT.getPassword());
        param.put("phone",addUserCaseZT.getPhone());
        param.put("photoUrl",addUserCaseZT.getPhotoUrl());

        //设置头信息
        post.setHeader("content-type","application/json");
        post.setHeader("authorization",TestConfigZT.cookieStr);
        StringEntity entity=new StringEntity(param.toString(),"utf-8");
        //调用setEntity(HttpEntity entity)方法来设置请求参数。
        post.setEntity(entity);

        String result;
        HttpResponse response=TestConfigZT.defaultHttpClient.execute(post);

        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println("result:"+result);
        JSONObject jsonObject= new JSONObject(result);

        //取一下返回值中的userId
        JSONObject jsonObject1= (JSONObject) jsonObject.get("data");
        String userIdStr=jsonObject1.get("id").toString();

//        TestConfigZT.userId=Integer.parseInt(userIdStr);
        TestConfigZT.userId=Integer.parseInt(userIdStr);

        return result;
    }

}
