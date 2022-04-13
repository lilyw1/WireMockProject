package com.ceshiren2.exercise;

import com.ceshiren.util.FakerUtil;
import com.ceshiren.util.MapperUtil;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author wyl
 * @description
 * @create 2022-03-27 16:49
 */
public class TestWeworkAPI {
    static String access_token;
    static List<Integer> list = new ArrayList<>();
    //获取企业微信access_token
    //corpid:ww0b599ee738db5500
    //corpsecret:ETIpLP70MLEULV0-htsamX02tSIheVIpB5gE4DIxPl4
    @BeforeAll
    static void getToken(){
        access_token = given()
                .param("corpid", "ww0b599ee738db5500")
                .queryParam("corpsecret", "ETIpLP70MLEULV0-htsamX02tSIheVIpB5gE4DIxPl4")
                .when()
                .get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                .then()
                //.log().all()
                .extract()
                .response().path("access_token").toString();
        System.out.println("access_token = " + access_token);
    }

    //创建部门
    //请求方式：POST（HTTPS）
    //请求地址：https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token=ACCESS_TOKEN
    @RepeatedTest(9)
    void addDepart(){
        FakerUtil fakerUtil = new FakerUtil();
        //读取addDepart.json文件
        MapperUtil<HashMap<String,Object>> mapperUtil = new MapperUtil<>();
        HashMap<String,Object> originMap = mapperUtil.getReadValue("src/test/resources/addDepart.json");
        //赋值addDepart.json文件中的变量，并存入depart中
        HashMap<String,Object> depart = new HashMap<>();
        originMap.forEach((s, o) -> {
            String s1 = o.toString();
            if(s1.startsWith("${")&&s1.endsWith("}")){
                //提取变量名称
                String s2 = StringUtils.stripStart(s1,"${");
                String s3 = StringUtils.stripEnd(s2, "}");

                if(s3.equals("departName")){
                    o = fakerUtil.get_name()+fakerUtil.getTimeStamp();
                }
                if(s3.equals("orderId")){
                    o = fakerUtil.get_num(1000);
                }
            }
            depart.put(s,o);
        });
        /*
        body体内容
        String depart = "{\n" +
                "   \"name\": \"运营部\",\n" +
                "   \"name_en\": \"YYBZ\",\n" +
                "   \"parentid\": 1,\n" +
                "   \"order\": 3986,\n" +
                "   \"id\": 9991\n" +
                "}";
         */
        Response response = given().log().all()
                .contentType("application/json;charset = utf-8")
                .queryParam("access_token", TestWeworkAPI.access_token)
                .body(depart)
                .when()
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create")
                .then().log().all().statusCode(200)
                .extract().response();
        Integer id = response.path("id");
        list.add(id);

        Integer errcode = response.path("errcode");
        String errmsg = response.path("errmsg");
        assertAll(
                ()-> assertEquals(0,errcode, "错误码不匹配"),
                ()-> assertEquals("created",errmsg,"添加信息不匹配")
        );

    }
    //id：部门id，32位整型，指定时必须大于1。若不填该参数，将自动生成id
    @AfterAll
    static void afterAll(){
        //输出部门id
        System.out.println(list);
    }

}
