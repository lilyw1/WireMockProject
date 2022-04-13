package com.ceshiren2.exercise.po.depart;

import com.ceshiren2.exercise.po.BaseTest;
import com.ceshiren2.exercise.po.DepartmentApi;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author wyl
 * @description
 * @create 2022-04-13 22:12
 */
@DisplayName("获取部门")
public class GetDepartTest extends BaseTest {
    @Test
    @DisplayName("正常参数，jsonSchema校验")
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
}
