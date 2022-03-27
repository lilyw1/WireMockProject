package com.ceshiren.restassured;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

/**
 * @author wyl
 * @create 2022-03-20 22:17
 */
public class WeatherProxyTest {
    //测试 经过wiremock替换后的天气页面
    @Test
    void weatherProxyTest(){
            given().log().all()
                    .queryParam("key","9efd073d2026890392a3aa8ff1b018d3")
                    .queryParam("city","120000")
                    .when()
                    .get("http://localhost:8089/v3/weather/weatherInfo")
                    .then().log().all();


        }
}
