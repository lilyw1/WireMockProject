package com.ceshiren2.exercise.po;

import com.ceshiren.util.ConfigUtil;

import static io.restassured.RestAssured.given;

/**
 * @author wyl
 * @description 封装获取token
 * @create 2022-04-10 15:17
 */
public class GetToken {
    public static String getToken(){
        //优化：将corpid和corpsercret放在配置文件中调用，方便更改
        String param1 = "corpid";
        String param2 = "corpsecret";
        ConfigUtil configUtil = new ConfigUtil();

        String access_token = given()
                .param(param1, configUtil.getProperties(param1))
                .queryParam(param2, configUtil.getProperties(param2))
                .when()
                .get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                .then()
                //.log().all()
                .extract()
                .response().path("access_token").toString();
        System.out.println("access_token = " + access_token);
        return access_token;
    }
}
