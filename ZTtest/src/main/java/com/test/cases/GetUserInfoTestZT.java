package com.test.cases;

import com.test.config.TestConfigZT;
import com.test.model.GetUserInfoCaseZT;
import com.test.utils.DatabaseUtil_local;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class GetUserInfoTestZT {
    SqlSession sessionlocal;

    @BeforeTest
    public void BeforeTest () throws IOException {
//        SqlSession sessionZT= DatabaseUtil_ZT.getSqlSession();
        sessionlocal= DatabaseUtil_local.getSqlSession();
    }

    @Test(dependsOnGroups = "getToken",description = "用例id=1，成功执行的用例，account=18234107311")
    public void getUserInfo() throws IOException, InterruptedException {

        GetUserInfoCaseZT getUserInfoCaseZT=sessionlocal.selectOne("getUserInfoCase",1);
        System.out.println(getUserInfoCaseZT.toString());
        System.out.println(TestConfigZT.getUserInfoUrl);
        String strReturn=getJsonResult(getUserInfoCaseZT);

        Thread.sleep(3000);

        //验证--原本是要跟数据库的数据做对比的，但是中台server返回的就不是user类型，所以就只判断返回结果就行了
//        User user=sessionZT.selectOne(getUserInfoCaseZT.getExpected(),getUserInfoCaseZT);
//        List userList=new ArrayList();
//        userList.add(user);
//        System.out.println("user是啥啊"+user.toString());
//        JSONArray jsonArray=new JSONArray(userList);
        Assert.assertTrue(strReturn.contains(getUserInfoCaseZT.getExpected()));
    }

    @Test(dependsOnGroups = "getToken",description = "用例id=2，账户不存在")
    public void userNotExist() throws IOException, InterruptedException {
        GetUserInfoCaseZT getUserInfoCaseZT=sessionlocal.selectOne("getUserInfoCase",2);
        System.out.println(getUserInfoCaseZT.toString());
        System.out.println(TestConfigZT.getUserInfoUrl);
        String strReturn=getJsonResult(getUserInfoCaseZT);

        Thread.sleep(3000);

        Assert.assertTrue(strReturn.contains(getUserInfoCaseZT.getExpected()));
    }

    private String getJsonResult(GetUserInfoCaseZT getUserInfoCaseZT) throws IOException {
        //get类型的请求直接在请求url中拼接请求参数
        String urlStr=TestConfigZT.getUserInfoUrl+"?"+"account="+getUserInfoCaseZT.getAccount();
        HttpGet get=new HttpGet(urlStr);
        HttpParams httpParams=new BasicHttpParams();
        httpParams.setParameter("account",getUserInfoCaseZT.getAccount());
        get.setParams(httpParams);
        get.setHeader("authorization",TestConfigZT.cookieStr);
        String result;
        HttpResponse response=TestConfigZT.defaultHttpClient.execute(get);

        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println("result:"+result);

        return result;
    }

}
