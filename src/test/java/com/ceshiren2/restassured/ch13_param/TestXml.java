package com.ceshiren2.restassured.ch13_param;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * @description XML格式数据传参及其XPath方式响应断言
 * @author wyl
 * @create 2022-03-23 22:07
 */
public class TestXml {
    @Test
    void testXml() throws IOException {
        File file = new File("src/test/resources/add.xml");
        FileInputStream fis = new FileInputStream(file);
        String requestBody = IOUtils.toString(fis,"UTF-8");

        given()
                .contentType("text/xml")//定义请求内容媒体信息
                .body(requestBody)
                .log().headers()
                .log().body()
        .when()
                .post("http://dneonline.com/calculator.asmx")
        .then()
                .log().all()
                .statusCode(200)
                //添加body中的结果断言,使用xpath定位
                //注意为字符串“2”
                .body("//AddResult.text()",equalTo("2"));
    }
}
