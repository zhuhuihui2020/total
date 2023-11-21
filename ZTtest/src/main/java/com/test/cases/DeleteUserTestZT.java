package com.test.cases;

import com.test.config.TestConfigZT;
import com.test.model.DeleteUserCaseZT;
import com.test.utils.DatabaseUtil_ZT;
import com.test.utils.DatabaseUtil_local;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class DeleteUserTestZT {

    @Test(dependsOnGroups = "getToken",description = "删除新添加的用户")
    public void deleteUser() throws IOException, InterruptedException {
        SqlSession sessionZT= DatabaseUtil_ZT.getSqlSession();
        SqlSession sessionlocal= DatabaseUtil_local.getSqlSession();
        DeleteUserCaseZT deleteUserCaseZT=sessionlocal.selectOne("deleteUserCase",1);
        System.out.println(deleteUserCaseZT.toString());
        System.out.println(TestConfigZT.deleteUserUrl);

        String strReturn=getJsonResult(deleteUserCaseZT);

        Thread.sleep(3000);

        //验证
        Assert.assertTrue(strReturn.contains(deleteUserCaseZT.getExpected()));
    }

    private String getJsonResult(DeleteUserCaseZT deleteUserCaseZT) throws IOException {
        String urlStr=TestConfigZT.deleteUserUrl+"/"+TestConfigZT.userId;
        System.out.println("urlStr"+urlStr);
        HttpDelete delete=new HttpDelete(urlStr);
        delete.setHeader("authorization",TestConfigZT.cookieStr);
        String result;
        HttpResponse response=TestConfigZT.defaultHttpClient.execute(delete);

        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println("result:"+result);

        return result;

    }

}
