package com.ceshiren2.exercise;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author wyl
 * @description
 * @create 2022-04-11 23:14
 */
public class GetDepart extends WeworkInterfaceBase{
    //获取部门列表
    //请求方式：GET（HTTPS）
    //请求地址：https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=ACCESS_TOKEN&id=ID
    @Test
    void getDepart(){
        Response response = given()
                .queryParam("access_token", WeworkInterfaceBase.access_token)
                .when()
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/list")
                .then()
                .log().all()
                .statusCode(200)
                //json schema断言
                .body(matchesJsonSchemaInClasspath("depart.json"))
                .extract().response();
        Integer errcode = response.path("errcode");
        String errmsg = response.path("errmsg");
        //多断言
        assertAll(
                () -> assertEquals(0, errcode, "获取部门错误码不匹配"),
                () -> assertEquals("ok", errmsg, "获取部门提示信息不匹配")
        );
    }
}
