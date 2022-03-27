package com.ceshiren.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

/**
 * @author wyl
 * @create 2022-03-18 23:36
 */
public class BasicMockDemoTest {
    /*实战一
    创建一个伪服务
    请求本地端口8089
    请求路径为 /wiremock
    页面显示内容为：this is a mock server
    */
    @Test
    void baseMockTest() throws InterruptedException {
        //启动服务的端口号,不指定默认8080
        //No-args constructor will start on port 8080, no HTTPS
        WireMockServer wireMockServer = new WireMockServer(wireMockConfig().port(8089));
        wireMockServer.start();
        configureFor(8089);
        // 做一些业务请求逻辑
        //请求体和返回体
        //浏览器登录http://localhost:8089/wiremock去查看
        stubFor(get(urlEqualTo("/wiremock"))
                .willReturn(aResponse().withBody("this is a mock server"))
        );
        Thread.sleep(100000);
        WireMock.reset();
        // 完成之后
        wireMockServer.stop();
    }

    //实战二：前面要求同，但是状态码为 404
    @Test
    void baseMock404Test() throws InterruptedException {
        //启动服务的端口号,不指定默认8080
        WireMockServer wireMockServer = new WireMockServer(wireMockConfig().port(8089));
        wireMockServer.start();
        configureFor(8089);
        // 做一些业务请求逻辑
        stubFor(get(urlEqualTo("/wiremock"))
                .willReturn(aResponse().withStatus(404).withBody("this is a mock server 404"))
        );
        Thread.sleep(100000);
        WireMock.reset();
        // 完成之后
        wireMockServer.stop();
    }

    /*实战三
    发一个请求
    请求里面匹配：
    header
    “Accept”, containing(“xml”)
    cookie
    “session”, matching(“.12345.”)
    QueryParam
    “search_term”, equalTo(“WireMock”)
    BasicAuth
    RequestBody
    matchingJsonPath(“$.a”,equalTo(“1”))
    */
    @Test
    void requestMockTest() throws InterruptedException {
        WireMockServer wireMockServer = new WireMockServer(wireMockConfig().port(8089));
        wireMockServer.start();
        configureFor(8089);
        stubFor(any(urlEqualTo("/wiremock"))
                .withHeader("Accept", containing("xml"))
                .withCookie("session", matching(".*12345.*"))
                .withQueryParam("search_term", equalTo("WireMock"))
                .withBasicAuth("yaliw@ceshiren.com", "hogwarts123")
                .withRequestBody(matchingJsonPath("$.a", equalTo("1")))
                .willReturn(aResponse().withStatus(200).withBody("request mock server"))
        );
        Thread.sleep(100000);

        wireMockServer.resetAll();
        wireMockServer.stop();
    }

    //实战四：多请求同时发送
    //http://localhost:8089/__admin/   查看
    @Test
    void request2MockTest() throws InterruptedException {
        WireMockServer wireMockServer = new WireMockServer(wireMockConfig().port(8089));
        wireMockServer.start();
        configureFor(8089);
        stubFor(get(urlEqualTo("/wirmock/resp"))
                .willReturn(
                        aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "text/plain")
                                .withBody("Hello WireMock ResponseMath!")));


        //常见的响应缩写
        stubFor(get("/fine-with-body")
                .willReturn(ok("body content")));

        stubFor(get("/json")
                .willReturn(okJson("{ \"message\": \"Hello\" }")));

        stubFor(get("/annother")
                .willReturn(temporaryRedirect("/new/place")));
        stubFor(get("/new/place")
                .willReturn(okJson("{ \"message\": \"302 local\" }")));

        stubFor(get("/sorry-no")
                .willReturn(unauthorized()));

        stubFor(get("/status-only")
                .willReturn(status(418)));
        //重定向到查询天气
        //https://restapi.amap.com/v3/weather/weatherInfo?key=9efd073d2026890392a3aa8ff1b018d3&city=110100
        stubFor(get("/weather")
                .willReturn(temporaryRedirect("https://restapi.amap.com/v3/weather/weatherInfo?key=9efd073d2026890392a3aa8ff1b018d3&city=110100")));
        //带参数的重定向
        //http://localhost:8089/weatherMock?city=110
        //注意是urlPathEqualTo，否则会失败
        stubFor(get(urlPathEqualTo("/weatherMock"))
                .withQueryParam("city",equalTo("110"))
                .willReturn(temporaryRedirect("https://restapi.amap.com/v3/weather/weatherInfo?key=9efd073d2026890392a3aa8ff1b018d3&city=130100")));

        Thread.sleep(100000000);

        wireMockServer.resetAll();
        wireMockServer.stop();

    }
}




