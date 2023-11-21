package com.test.cases;

import com.test.config.TestConfigZT;
import com.test.model.GetNameByIdCaseZT;
import com.test.utils.DatabaseUtil_local;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;

public class GetNameByIdTestZT {
    SqlSession sessionlocal;

    @BeforeTest
    public void BeforeTest () throws IOException {
//        SqlSession sessionZT= DatabaseUtil_ZT.getSqlSession();
        sessionlocal= DatabaseUtil_local.getSqlSession();
    }

    @Test(dependsOnGroups = "getToken",description = "批量通过id查询用户名，需要先遍历表，查出一共有多少条，再挨个遍历查询")
    public void getNameById() throws IOException, InterruptedException {
        Integer count=sessionlocal.selectOne("getNameByIdNum");
        System.out.println(TestConfigZT.getNameByIdUrl);
        System.out.println("一共有多少条数据:"+count);
        GetNameByIdCaseZT getNameByIdCaseZT=new GetNameByIdCaseZT();
        int i=1;
        GetNameByIdCaseZT[] getNameByIdCaseZTS=new GetNameByIdCaseZT[count];
        StringBuilder stringBuilder=new StringBuilder();
        while (i<=count){
            getNameByIdCaseZT=sessionlocal.selectOne("getNameById",i);
            getNameByIdCaseZTS[i-1]=getNameByIdCaseZT;
            //数据库中取的数据
            stringBuilder.append(getNameByIdCaseZT.getExpected());
            i++;
        }
        System.out.println(getNameByIdCaseZT);
        String strReturn=getJsonResult(getNameByIdCaseZTS);

        Thread.sleep(3000);

        //验证--原本是要跟数据库的数据做对比的，但是中台server返回的就不是user类型，所以就只判断返回结果就行了
//        User user=sessionZT.selectOne(getUserInfoCaseZT.getExpected(),getUserInfoCaseZT);
//        List userList=new ArrayList();
//        userList.add(user);
//        System.out.println("user是啥啊"+user.toString());
//        JSONArray jsonArray=new JSONArray(userList);
        Assert.assertEquals(strReturn,stringBuilder.toString());
    }

    private String getJsonResult(GetNameByIdCaseZT[] getUserInfoCaseZTS) throws IOException {
        HttpPost post=new HttpPost(TestConfigZT.getNameByIdUrl);

        int length=getUserInfoCaseZTS.length;
        JSONArray jsonArray=new JSONArray();
        int i=0;
        while (i<length){
            jsonArray.put(getUserInfoCaseZTS[i].getUid());
            i++;
        }

        //设置头信息
        post.setHeader("content-type","application/json");
        post.setHeader("authorization",TestConfigZT.cookieStr);
        StringEntity entity=new StringEntity(jsonArray.toString(),"utf-8");
        //调用setEntity(HttpEntity entity)方法来设置请求参数。
        post.setEntity(entity);

        String result;
        HttpResponse response=TestConfigZT.defaultHttpClient.execute(post);

        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println("result:"+result);

        JSONObject jsonObject= new JSONObject(result);

        //data
        JSONArray jsonObject2=(JSONArray) jsonObject.get("data");
        String data=jsonObject2.toString();
        System.out.println("data:"+data);

        JSONArray jsonArray1= new JSONArray(data);

        //循环取一下返回值中的name
        i=0;
        StringBuilder stringBuilder=new StringBuilder();
        while (i<length){
            JSONObject jsonObject1= (JSONObject) jsonArray1.get(i);
            String name=jsonObject1.get("name").toString();
            stringBuilder.append(name);
            i++;
        }

        return stringBuilder.toString();
    }
}
