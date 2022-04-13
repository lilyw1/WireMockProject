package com.ceshiren2.exercise.po.depart;

import com.ceshiren2.exercise.po.BaseTest;
import com.ceshiren2.exercise.po.DepartmentApi;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author wyl
 * @description
 * @create 2022-04-13 22:13
 */
@DisplayName("删除部门")
public class DeleteDepartTest extends BaseTest {

    @ParameterizedTest(name = "删除部门的ID：{0}")
    @MethodSource
    @DisplayName("正常参数")
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
