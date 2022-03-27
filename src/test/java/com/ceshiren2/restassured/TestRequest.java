package com.ceshiren2.restassured;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * @description get和post请求，以及三种断言
 * @author wyl
 * @create 2022-03-22 21:48
 */
public class TestRequest {
    //get请求
    @Test
    void testGet(){
        given()
                .log().all()//打印请求信息
                // given 设置测试预设（请求头、请求参数、请求体等等）
                .header("Hello", "Hogwarts")
                .param("username","www")
                //https://httpbin.ceshiren.com/get?username=www
        .when()
                // when 所要执行的请求动作
                .get("https://httpbin.ceshiren.com/get")
        .then()
                // then 解析结果、断言
                .log().all()// 打印全部响应信息（响应头、响应体、状态等等）
                .statusCode(200);
    }

    //post请求
    @Test
    void testPost(){
        given()
                .log().all()//打印请求信息
                // given 设置测试预设（请求头、请求参数、请求体等等）
                .formParam("username","www")//表单参数
                //    "form": {"username": "www"}
                .queryParam("user2","www2")//查询参数出现在请求头上
                //https://httpbin.ceshiren.com/post?user2=www2
                .param("user3","www3")
                //Request params:	user3=www3,不会出现在请求头上， "form": {"user3": "www3"}
        .when()
                // when 所要执行的请求动作
                .post("https://httpbin.ceshiren.com/post")
        .then()
                // then 解析结果、断言
                .log().all()// 打印全部响应信息（响应头、响应体、状态等等）
                .statusCode(200);
    }

    @Test
    void testAssert(){
        given()
        .when()
                // when 所要执行的请求动作
                .get("https://httpbin.ceshiren.com/get")
        .then()
                // then 解析结果、断言
                .log().all()// 打印全部响应信息（响应头、响应体、状态等等）
                .statusCode(200)//状态码断言
                .assertThat().header("Content-Length","382")//响应头
                .body("origin",equalTo("172.17.56.110"));//响应体断言
    }

}
