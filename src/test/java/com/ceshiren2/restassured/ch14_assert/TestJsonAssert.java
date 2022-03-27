package com.ceshiren2.restassured.ch14_assert;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.from;
import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @author wyl
 * @description Rest-Assured内置解析方式from定位,JsonPath定位响应内容进行断言
 * @create 2022-03-23 22:56
 */
public class TestJsonAssert {
    //方式一：Rest-Assured内置解析方式
    @Test
    void testFrom(){
        String responseText = given()
                .header("Hello","Hogwarts")
        .when()
                .get("https://httpbin.ceshiren.com/get")
        .then()
                .statusCode(200)
                .extract().response().asString();//将响应结果以string格式提取出来
        //Rest-Assured内置解析方式from定位到Hogwarts
        String word = from(responseText).getString("headers.Hello");
        System.out.println(word);
        //断言
        assertEquals("Hogwarts",word);
    }
    //方式二：JsonPath定位方式
    @Test
    void testJsonPath(){
        String responseText = given()
                .header("Hello1","Hogwarts1")
                .when()
                .get("https://httpbin.ceshiren.com/get")
                .then()
                .statusCode(200)
                .extract().response().asString();//将响应结果以string格式提取出来
        //JsonPath定位到Hogwarts
        String word1 = JsonPath.read(responseText,"$.headers.Hello1");
        System.out.println(word1);
        //断言
        assertEquals("Hogwarts1",word1);
    }
    //方式三：Json Schema响应断言

}
