package com.ceshiren2.restassured;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.specification.ProxySpecification.host;

/**
 * @author wyl
 * @description 身份认证
 * @create 2022-03-25 22:46
 */
public class TestAuthBasic {
    @Test
    void testAuthBasic(){
        RestAssured.proxy = host("localhost").withPort(7778);
        RestAssured.useRelaxedHTTPSValidation();
        given()
                .auth().basic("hogwarts","123")
                .when()
                .get("https://httpbin.ceshiren.com/basic-auth/hogwarts/123")
                .then()
                .statusCode(200)
                .log().all();
    }
}
