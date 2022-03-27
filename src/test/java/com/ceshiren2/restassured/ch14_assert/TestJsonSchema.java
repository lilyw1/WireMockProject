package com.ceshiren2.restassured.ch14_assert;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

/**
 * @author wyl
 * @description json schema断言
 * @create 2022-03-23 23:24
 */
public class TestJsonSchema {
    @Test
    void testJsonSchema(){
        given()
        .when()
                .get("https://httpbin.ceshiren.com/get")//get请求
        .then()
                .log().all()//打印响应内容
                //json schema断言
                .assertThat().body(matchesJsonSchemaInClasspath("schema.json"));
    }
}
