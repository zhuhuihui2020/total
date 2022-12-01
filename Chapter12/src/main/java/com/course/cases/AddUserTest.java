package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.AddUserCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class AddUserTest {

    @Test(dependsOnGroups = "loginTrue",description = "添加用户接口测试")
    public void addUser() throws IOException, InterruptedException {
        SqlSession session= DatabaseUtil.getSqlSession();
        AddUserCase addUserCase=session.selectOne("addUserCase",1);
        System.out.println("查询到的用户111："+addUserCase.toString());
//        System.out.println(addUserCase.toString());
        System.out.println(TestConfig.addUserUrl);

        //发请求，获取结果
        String result=getResult(addUserCase);

        //等待上一条语句执行完，再去查询
        Thread.sleep(3000);

        //验证返回结果
        User user = session.selectOne("addUser",addUserCase);

//        System.out.println("返回结果2222："+user.toString());
        System.out.println("调用接口获取用户信息:"+result.toString());
        Assert.assertEquals(addUserCase.getExpected(),result);

    }

    private String getResult(AddUserCase addUserCase) throws IOException {
        //1.创建HttpPost对象，将要请求的URL通过构造方法传入HttpPost对象。
        HttpPost post=new HttpPost(TestConfig.addUserUrl);
        JSONObject param=new JSONObject();
        param.put("userName",addUserCase.getUserName());
        param.put("password",addUserCase.getPassword());
        param.put("sex",addUserCase.getSex());
        param.put("age",addUserCase.getAge());
        param.put("permission",addUserCase.getPermission());
        param.put("isDelete",addUserCase.getIsDelete());

        //设置头信息
        post.setHeader("content-type","application/json");
        StringEntity entity=new StringEntity(param.toString(),"utf-8");
        //调用setEntity(HttpEntity entity)方法来设置请求参数。
        post.setEntity(entity);

        //设置cookies
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);
        String result;//存放返回结果
        HttpResponse response=TestConfig.defaultHttpClient.execute(post);

        result= EntityUtils.toString(response.getEntity(),"utf-8");

        System.out.println(result);
        return result;
    }
}
