package com.ceshiren2.restassured;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.specification.ProxySpecification.host;

/**
 * @author wyl
 * @description multiPart文件上传
 * @create 2022-03-25 22:06
 */
public class TestMultiPart {
    @Test
    void testMultiPart(){
        File file = new File("src/test/resources/hogwarts.txt");
        RestAssured.proxy = host("127.0.0.1").withPort(7778);
        //忽略Https校验
        RestAssured.useRelaxedHTTPSValidation();

        given()
                .log().headers()
                .multiPart("hogwarts111",file)
                .multiPart("ceshiren","{\"hogwarts\":666}","application/json")
        .when()
                .post("https://httpbin.ceshiren.com/post")
        .then()
                .statusCode(200);
    }
}
