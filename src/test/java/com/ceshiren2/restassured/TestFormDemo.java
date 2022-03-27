package com.ceshiren2.restassured;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

/**
 * @author wyl
 * @description form表单传入数据
 * @create 2022-03-24 22:58
 */
public class TestFormDemo {
    @Test
    void testForm(){
        given().log().all()
                //.formParam("my_param","hogwarts")
                .formParams("username","www","pwd","123")
                .when()
                .post("https://httpbin.ceshiren.com/post")  // 发送请求
                .then()
                .log().all()  // 打印完整响应信息
                .statusCode(200);  // 响应断言
    }

}
