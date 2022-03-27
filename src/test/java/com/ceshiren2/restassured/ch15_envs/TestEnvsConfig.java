package com.ceshiren2.restassured.ch15_envs;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

/**
 * @author wyl
 * @description 多环境时设置环境配置
 * @create 2022-03-26 10:15
 */
public class TestEnvsConfig {
    @Test
    void testMultiEnvs(){
        //hashmap存储两个环境域名
        HashMap<String,String> envs = new HashMap<>();

        envs.put("org","http://httpbin.org");
        envs.put("ceshiren","https://httpbin.ceshiren.com");

        //配置默认环境
        //envs.put("default","org");
        envs.put("default","ceshiren");

        //请求体中传入默认环境Url
        RestAssured.baseURI = envs.get(envs.get("default"));

        //请求体
        given()
        .when()
                .get("/get")
        .then()
                .log().all()
                .statusCode(200);

    }
}
