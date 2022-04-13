package com.ceshiren2.exercise.po;

import com.ceshiren.util.FakerUtil;
import com.ceshiren.util.MapperUtil;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

/**
 * @author wyl
 * @description 将一些参数提取成变量，暴露出来方便更改--->将部门功能封装起来
 * @create 2022-04-13 21:14
 */
public class DepartmentApi{
    public static Response addDepartFromFilter(String pathName, String param1, String param2){
        //获取body体内容
        HashMap<String, Object> depart = getBodyDepart(pathName,param1,param2);
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
        return response;
    }

    //获取body体，提取成方法
    private static HashMap<String, Object> getBodyDepart(String pathName, String param1, String param2) {
        FakerUtil fakerUtil = new FakerUtil();
        //读取addDepart.json文件
        MapperUtil<HashMap<String, Object>> mapperUtil = new MapperUtil<>();
        HashMap<String, Object> originMap = mapperUtil.getReadValue(pathName);
        //赋值addDepart.json文件中的变量，并存入depart中
        HashMap<String, Object> depart = new HashMap<>();
        originMap.forEach((s, o) -> {
            String value = o.toString();
            if (value.startsWith("${") && value.endsWith("}")) {
                //提取变量名称
                String v1 = StringUtils.stripStart(value, "${");
                String v2 = StringUtils.stripEnd(v1, "}");

                if (param1.equals(v2)) {
                    o = fakerUtil.get_name() + fakerUtil.getTimeStamp();
                }
                if (param2.equals(v2)) {
                    o = fakerUtil.get_num(1000);
                }
            }
            //组成新map传递给body
            depart.put(s, o);
        });
        return depart;

    }

    //获取部门
    public static Response getDepartFromFilter(String jsonPath) {
        Response response = given()
                .filter(new ApiFilter())
                .when()
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/list")
                .then()
                .log().all()
                .statusCode(200)
                //json schema断言
                .body(matchesJsonSchemaInClasspath(jsonPath))
                .extract().response();
        return response;
    }
    //删除部门

    public static Response deleteDepartFromFilter(int id){
        Response response =
                given()
                        .filter(new ApiFilter())
                        .queryParam("id", id)
                        .when()
                        .get("https://qyapi.weixin.qq.com/cgi-bin/department/delete")
                        .then()
                        .statusCode(200)
                        .log().all()
                        .extract().response();
        return response;
    }
}
