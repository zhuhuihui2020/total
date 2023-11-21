package com.test.cases;

import com.test.config.TestConfigZT;
import com.test.model.InterfaceNameZT;
import com.test.utils.ConfigFileZT;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;


/**
 * 使用HttpClient发送请求、接收响应很简单，通常须要以下几步便可。
 *
 * 1. 建立HttpClient对象。
 *
 * 2. 建立请求方法的实例，并指定请求URL。若是须要发送GET请求，建立HttpGet对象；若是须要发送POST请求，建立HttpPost对象。
 *
 * 3. 若是须要发送请求参数，可调用HttpGet、HttpPost共同的setParams(HetpParams params)方法来添加请求参数；对于HttpPost对象而言，也可调用setEntity(HttpEntity entity)方法来设置请求参数。
 *
 * 4. 调用HttpClient对象的execute(HttpUriRequest request)发送请求，该方法返回一个HttpResponse。
 *
 * 5. 调用HttpResponse的getAllHeaders()、getHeaders(String name)等方法可获取服务器的响应头；调用HttpResponse的getEntity()方法可获取HttpEntity对象，该对象包装了服务器的响应内容。程序可经过该对象获取服务器的响应内容。
 *
 * 6. 释放链接。不管执行方法是否成功，都必须释放链接
 */
public class GetTokenTestZT {

    @BeforeTest(groups = "getToken",description = "测试准备工作,获取HttpClient对象")
    public void beforeTest(){
        TestConfigZT.defaultHttpClient = new DefaultHttpClient();
        TestConfigZT.getUserInfoUrl = ConfigFileZT.getUrl(InterfaceNameZT.GETUSERINFO);
        TestConfigZT.getTokenUrl = ConfigFileZT.getUrl(InterfaceNameZT.GETTOKEN);
        TestConfigZT.addUserUrl=ConfigFileZT.getUrl(InterfaceNameZT.ADDUSER);
        TestConfigZT.deleteUserUrl=ConfigFileZT.getUrl(InterfaceNameZT.DELETEUSER);
        TestConfigZT.updateUserUrl=ConfigFileZT.getUrl(InterfaceNameZT.UPDATEUSER);
        TestConfigZT.getNameByIdUrl=ConfigFileZT.getUrl(InterfaceNameZT.GETNAMEBYID);
    }

    @Test(groups = "getToken",description = "设置token信息")
    public void getToken() throws IOException {
        //1.创建HttpPost对象，将要请求的URL通过构造方法传入HttpPost对象。
        HttpGet get=new HttpGet(TestConfigZT.getTokenUrl);

        String result;//存放返回结果
        HttpResponse response=TestConfigZT.defaultHttpClient.execute(get);

        result= EntityUtils.toString(response.getEntity(),"utf-8");
        //正则表达式取值--太麻烦了，返回的是json，不用正则了
//        Pattern p=Pattern.compile("(?<=data\":\").*?(?=\"})");
//        Matcher m=p.matcher(result);
//        String str="";
//        if(m.find()){
//            str=m.group(0);
//        }

        //使用json相关的方法取字符串
        JSONObject jsonObject=new JSONObject(result);
        System.out.println("json提取"+jsonObject.get("data"));

        System.out.println("返回结果是："+result);

//        设置token
        //访问其他接口的时候带上
        TestConfigZT.cookieStr=jsonObject.get("data").toString();

    }

}
