package com.restassured;

import com.restassured.data.CasesDataProvider;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class RunTest {

    @BeforeClass
    public void setUp() {
        //   请求IP
        RestAssured.baseURI = "https://*********";
        RestAssured.basePath = "/";
//        RestAssured.port = 8080;
        //在多数场景下，SSL能顺利运转，这多亏于HTTP Builder和HTTP Client。如果服务端使用了无效的证书，然而有些例子下还是会出错。最简单的方法是使用"relaxed HTTPs validation",也可以为所有的请求静态定义这个配置：
        RestAssured.useRelaxedHTTPSValidation();
        // 通过注册预置的解析器来解析现在不支持的内容类型
        RestAssured.registerParser("text/plain", Parser.JSON);
    }

    @Test(dataProvider = "casesProvider", dataProviderClass = CasesDataProvider.class)
    public void runCases(String caseNo, String testPoit, String preResult, String YorN, String tableCheck, String api, String version,String acctType) {
//    public void runCases(String caseNo, String testPoit, String preResult, String YorN, String tableCheck, String appId, String merchantId, String api, String version, String phone, String bizTransaction, String acctType) {
//
//        String bodyString = "{\n" +
//                "\t\"appId\":\"" + appId + "\",\n" +
//                "\t\"api\":\"" + api + "\",\n" +
//                "\t\"data\":{\n" +
//                "\t\t\"merchantId\":\"" + merchantId + "\",\n" +
//                "\t\t\"bizTransaction\":\"" + bizTransaction + "\",\n" +
//                "\t\t\"phone\":\"" + phone + "\",\n" +
//                "\t\t\"acctType\":\"" + acctType + "\"\n" +
//                "\t\t},\n" +
//                "\t\"version\":\"" + version + "\"\n" +
//                "}\n";
//        System.out.println("bodyString : "+bodyString);
//        Response response = RestAssured.given()
//                .contentType("application/json;charset=UTF-8")
//                .request()
//                .body(bodyString)
//                .post();
        System.out.println("当前测试用例编号是 :  "+caseNo);
        System.out.println("测试点是 :  "+testPoit);
        Response response = RestAssured.given()
//                .contentType("application/json;charset=UTF-8")
                .when()
                .get(api);
        System.out.println("下面是格式化后的响应报文 :  ");
        response.prettyPrint();  //格式化响应报文

        //断言
        String json = response.asString();
        JsonPath jp = new JsonPath(json);

//        if(response.statusCode() == 200){ //请求成功
        if(jp.getInt("code")==1){
            System.out.println("请求成功，下面进行判断");
            Assert.assertEquals(jp.get("success").toString(),preResult);
        }else{
            System.out.println("请求失败，下面进行判断");
            Assert.assertEquals(jp.get("success").toString(),preResult);
        }
        //  为了与下一个用例隔开，输出下面的空行
        System.out.println();
    }
}
