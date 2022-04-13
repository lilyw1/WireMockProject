package com.ceshiren2.exercise.po.depart;

import com.ceshiren2.exercise.po.BaseTest;
import com.ceshiren2.exercise.po.DepartmentApi;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.RepeatedTest.*;

/**
 * @author wyl
 * @description
 * @create 2022-04-13 22:12
 */
@DisplayName("添加部门")
public class AddDepartTest extends BaseTest{
    @DisplayName("正常参数")
    @RepeatedTest(value = 100, name = DISPLAY_NAME_PLACEHOLDER + " " + CURRENT_REPETITION_PLACEHOLDER + "/" + TOTAL_REPETITIONS_PLACEHOLDER)
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
}
