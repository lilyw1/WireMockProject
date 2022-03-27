package com.ceshiren.restassured;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;


/**
 * @author wyl
 * @create 2022-03-19 23:18
 */
public class RequestTest
{
    @Test
    void requestTest(){
        given().log().all()
                .header("Accept","xml")
                .cookie("session","123456")
                .queryParam("search_term","WireMock")
                .auth().preemptive().basic("yaliw@ceshiren.com","hogwarts123")
                .body("a:1,b:90,c:03")
                .when()
                .post("http://localhost:8089/wiremock")
                .then().log().all();


    }
    //直接查询天气
    @Test
    void requestWeatherTest(){
        given().log().all()
                .queryParam("key","9efd073d2026890392a3aa8ff1b018d3")
                .queryParam("city","110100")
                .when()
                .get("https://restapi.amap.com/v3/weather/weatherInfo")
                .then().log().all();


    }
    //测试wiremock重定向到天气查询，带参数
    @Test
    void requestWeatherMockTest(){
        given().log().all()
                .queryParam("city","110")
                .when()
                .get("http://localhost:8089/weatherMock")
                .then().log().all();


    }

}
