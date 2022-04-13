package com.ceshiren2.exercise.po;

import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.RepeatedTest.*;

/**
 * @author wyl
 * @description 调用封装的department接口，进行测试。
 * @create 2022-04-13 21:19
 */
@DisplayName("企业微信部门相关测试")
//指定执行顺序
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DepartmentTest extends BaseTest{
    @RepeatedTest(value = 3, name = DISPLAY_NAME_PLACEHOLDER + " " + CURRENT_REPETITION_PLACEHOLDER + "/" + TOTAL_REPETITIONS_PLACEHOLDER)
    @Order(11)
    void add(){
        Response addResponse = DepartmentApi.addDepartFromFilter("src/test/resources/addDepart.json","departName" , "orderId");

        Integer errcode = addResponse.path("errcode");
        String errmsg = addResponse.path("errmsg");
        //多断言
        assertAll(
                () -> assertEquals(0, errcode, "添加部门错误码不匹配"),
                () -> assertEquals("created", errmsg, "添加部门提示信息不匹配")
        );
    }
    @Test
    @Order(22)
    void get(){
        Response getResponse = DepartmentApi.getDepartFromFilter("depart.json");
        Integer errcode = getResponse.path("errcode");
        String errmsg = getResponse.path("errmsg");
        //多断言
        assertAll(
                () -> assertEquals(0, errcode, "获取部门错误码不匹配"),
                () -> assertEquals("ok", errmsg, "获取部门提示信息不匹配")
        );

    }
    @ParameterizedTest(name = "删除部门的ID：{0}")
    @MethodSource
    @Order(33)
    void delete(int id){
        Response deleteResponse = DepartmentApi.deleteDepartFromFilter(id);
        Integer errcode = deleteResponse.path("errcode");
        String errmsg = deleteResponse.path("errmsg");
        //多断言
        assertAll(
                () -> assertEquals(0, errcode, "删除部门错误码不匹配"),
                () -> assertEquals("deleted", errmsg, "删除部门提示信息不匹配")
        );

    }
    static Stream<Arguments> delete(){
        Response getResponse = DepartmentApi.getDepartFromFilter("depart.json");
        //获取部门id的请求
        //获取order小于1000的部门列表
        //通过order来确定部门id
        //gpath验证 https://onecompiler.com/groovy/3xxb42jsb
        List<Arguments> idList = getResponse.path("department.findAll{it.order < 1000}.id");
        System.out.println(idList);
        return idList.stream();
    }

}
