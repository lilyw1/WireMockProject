package com.ceshiren2.exercise;

import com.ceshiren.util.ConfigUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

/**
 * @author wyl
 * @description 企业微信base文件
 * @create 2022-03-28 21:47
 */
public class WeworkInterfaceBase {
    static String access_token;
    static List<Integer> list = new ArrayList<>();
    @BeforeAll
    static void getToken(){
        //优化：将corpid和corpsercret放在配置文件中调用，方便更改
        String param1 = "corpid";
        String param2 = "corpsecret";
        ConfigUtil configUtil = new ConfigUtil();

        access_token = given()
                .param(param1, configUtil.getProperties(param1))
                .queryParam(param2, configUtil.getProperties(param2))
                .when()
                .get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                .then()
                //.log().all()
                .extract()
                .response().path("access_token").toString();
        System.out.println("access_token = " + access_token);
    }

    @AfterAll
    static void afterAll() {

    }
}
