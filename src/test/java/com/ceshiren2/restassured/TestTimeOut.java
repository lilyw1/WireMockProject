package com.ceshiren2.restassured;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

/**
 * @author wyl
 * @description 超时处理方法
 * @create 2022-03-24 23:09
 */
public class TestTimeOut {

    @BeforeAll
    static void setupClass(){
        RestAssured.baseURI = "https://httpbin.ceshiren.com";

    }
    @Test
    void case1(){
        given()
                .when()
                .get("/get")
                .then();
    }
    @Test
    void case2(){
        // 自定义HttpClientConfig对象
        // 设置响应超时时长为3秒，单位是毫秒
        HttpClientConfig clientConfig = HttpClientConfig
                .httpClientConfig()
                .setParam("http.socket.timeout", 3000);

        // 定义RestAssuredConfig对象
        // 传入自定义的HttpClientConfig对象
        RestAssuredConfig myTimeout = RestAssuredConfig
                .config()
                .httpClient(clientConfig);
        given()
                .config(myTimeout)
                .when()
                .get("/delay/10")//延迟10s响应
                .then();
    }
    @Test
    void case3(){
        HttpClientConfig httpClientConfig = HttpClientConfig.httpClientConfig().setParam("http.socket.timeout",3000);
        RestAssuredConfig myConfig = RestAssuredConfig.config().httpClient(httpClientConfig);
        
        given()
                .config(myConfig)
                .when()
                .get("/get")
                .then();
    }
}
