package com.ceshiren2.exercise.po;

import com.ceshiren.util.FakerUtil;
import com.ceshiren.util.MapperUtil;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.RepeatedTest.*;

/**
 * @author wyl
 * @description 通过filter添加Access token参数。打印请求和响应到文件，allure动态添加
 * @create 2022-04-12 23:37
 */
@DisplayName("添加部门")
public class AddDepartFilterTest extends BaseTest{
    @DisplayName("正常参数随机添加")
    //正常参数随机添加 1/10
    @RepeatedTest(value = 10, name = DISPLAY_NAME_PLACEHOLDER + " " + CURRENT_REPETITION_PLACEHOLDER + "/" + TOTAL_REPETITIONS_PLACEHOLDER)
    void addFromFilter(){
        //获取body体内容
        HashMap<String, Object> depart = getBodyDepart();
        System.out.println("depart:" + depart);
        //given（）后添加filter
        Response response = given().filter(new ApiFilter())
                .log().all()
                .contentType("application/json;charset = utf-8")//否则中文字符会报错
                .body(depart)
                .when()
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create")
                .then().log().all().statusCode(200)
                .extract().response();

        Integer errcode = response.path("errcode");
        String errmsg = response.path("errmsg");
        //多断言
        assertAll(
                () -> assertEquals(0, errcode, "添加部门错误码不匹配"),
                () -> assertEquals("created", errmsg, "添加部门提示信息不匹配")
        );
    }

    //获取body体，提取成方法
    private HashMap<String, Object> getBodyDepart() {
        FakerUtil fakerUtil = new FakerUtil();
        //读取addDepart.json文件
        MapperUtil<HashMap<String, Object>> mapperUtil = new MapperUtil<>();
        HashMap<String, Object> originMap = mapperUtil.getReadValue("src/test/resources/addDepart.json");
        //赋值addDepart.json文件中的变量，并存入depart中
        HashMap<String, Object> depart = new HashMap<>();
        originMap.forEach((s, o) -> {
            String value = o.toString();
            if (value.startsWith("${") && value.endsWith("}")) {
                //提取变量名称
                String v1 = StringUtils.stripStart(value, "${");
                String v2 = StringUtils.stripEnd(v1, "}");

                if ("departName".equals(v2)) {
                    o = fakerUtil.get_name() + fakerUtil.getTimeStamp();
                }
                if ("orderId".equals(v2)) {
                    o = fakerUtil.get_num(1000);
                }
            }
            //组成新map传递给body
            depart.put(s, o);
        });
        return depart;

    }

}
