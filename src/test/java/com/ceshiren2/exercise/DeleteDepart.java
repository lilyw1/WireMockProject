package com.ceshiren2.exercise;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author wyl
 * @description
 * @create 2022-04-10 16:11
 */
public class DeleteDepart extends WeworkInterfaceBase{
    //删除部门
    @Test
    void deleteDepart(){
        //先获取部门和部门id
        Response response = given()
                .queryParam("access_token", WeworkInterfaceBase.access_token)
                .when()
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/list")
                .then()
                .statusCode(200)
                //json schema断言
                .body(matchesJsonSchemaInClasspath("depart.json"))
                .extract().response();
        //获取order小于1000的部门列表
        //通过order来确定部门id
        //gpath验证 https://onecompiler.com/groovy/3xxb42jsb
        List<Integer> idList = response.path("department.findAll{it.order < 1000}.id");
        System.out.println(idList);

        //    删除部门
        //    请求方式：GET（HTTPS）
        //    请求地址：https://qyapi.weixin.qq.com/cgi-bin/department/delete?access_token=ACCESS_TOKEN&id=ID
        //必须需要传入access_token和部门id
        idList.forEach(id ->
        {
            Response resDel =
                given()
                    .queryParam("access_token", access_token)
                    .queryParam("id", id)
                .when()
                    .get("https://qyapi.weixin.qq.com/cgi-bin/department/delete")
                .then()
                    .statusCode(200)
                    .log().all()
                    .extract().response();
            Integer errcode = resDel.path("errcode");
            String errmsg = resDel.path("errmsg");
            //多断言
            assertAll(
                    () -> assertEquals(0, errcode, "删除部门错误码不匹配"),
                    () -> assertEquals("deleted", errmsg, "删除部门提示信息不匹配")

            );

        });
    }
    //上述方式无法看到删除的部门id
    //优化：将部门id作为参数化传入
    @ParameterizedTest(name = "删除部门的ID：{0}")
    @MethodSource//通过MethodSource传参
    void deleteDepartParameter(int id){
        Response resDel =
                given()
                        .queryParam("access_token", access_token)
                        .queryParam("id", id)
                        .when()
                        .get("https://qyapi.weixin.qq.com/cgi-bin/department/delete")
                        .then()
                        .statusCode(200)
                        .log().all()
                        .extract().response();
        Integer errcode = resDel.path("errcode");
        String errmsg = resDel.path("errmsg");
        //多断言
        assertAll(
                () -> assertEquals(0, errcode, "删除部门错误码不匹配"),
                () -> assertEquals("deleted", errmsg, "删除部门提示信息不匹配")

                );

    }
    static Stream<Arguments> deleteDepartParameter(){
        //获取部门id的请求
        Response response = given()
                .queryParam("access_token", WeworkInterfaceBase.access_token)
                .when()
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/list")
                .then()
                .statusCode(200)
                //json schema断言
                .body(matchesJsonSchemaInClasspath("depart.json"))
                .extract().response();
        //获取order小于1000的部门列表
        //通过order来确定部门id
        //gpath验证 https://onecompiler.com/groovy/3xxb42jsb
        List<Arguments> idList = response.path("department.findAll{it.order < 1000}.id");
        System.out.println(idList);
        return idList.stream();
    }
}
