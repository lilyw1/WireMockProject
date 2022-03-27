package com.ceshiren2.restassured.ch15_envs;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

/**
 * @author wyl
 * @description 多环境时通过Yaml文件导入环境配置
 * @create 2022-03-26 10:27
 */
public class TestEnvsYaml {
    @BeforeAll
    static void beforeAll() throws IOException {
        //使用Jackson读取解析yaml文件
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        //定义序列化结构
        TypeReference<HashMap<String, String>> typeReference = new TypeReference<HashMap<String, String>>() {
        };
        //读取yaml文件
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream yamlFile = classLoader.getResourceAsStream("envs.yaml");
        //File yamlFile = new File(classLoader.getResource("envs.yaml").getFile());
        //路径有空格无法正确识别”D:\IntelliJ%20IDEA%202021.3.1\project\WireMockProject\target\test-classes\envs.yaml"
        //所以直接读取stream文件而不是File文件
        System.out.println(yamlFile);

        //解析yaml文件
        HashMap<String, String> envs = mapper.readValue(yamlFile, typeReference);
        //HashMap<String, String> envs = mapper.readValue(new File("src/test/resources/envs.yaml"), typeReference);
        System.out.println(envs);

        RestAssured.baseURI = envs.get(envs.get("default"));
        System.out.println(baseURI);
    }
    @Test
    void testEnvsYaml(){
        //请求体
        given()
                .when()
                .get("/get")
                .then()
                .log().all()
                .statusCode(200);

    }
}
