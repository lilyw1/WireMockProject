package com.ceshiren.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

/**
 * @author wyl
 * @create 2022-03-20 15:37
 */
public class RequestPriorityMockTest {
    //优先级设定
    @Test
    void testPriority() throws InterruptedException {
        WireMockServer wireMockServer = new WireMockServer(wireMockConfig().port(8089));
        wireMockServer.start();
        configureFor(8089);
        //多个请求匹配可能会有重叠的时候，需要设置优先级才能匹配到前面俩个
        //atPriority(10)指定优先级，数字越小优先级越高
        stubFor(get(urlMatching("/api/.*")).atPriority(10)
                .willReturn(aResponse().withStatus(200).withBody("api demo"))
        );
        stubFor(get(urlEqualTo("/api/resources")).atPriority(5)
                .willReturn(aResponse().withStatus(200).withBody("response resources sate"))
        );
        //匹配任何URL
        stubFor(any(anyUrl()).atPriority(100)
                .willReturn(aResponse().withStatus(401).withBody("no match"))
        );


        Thread.sleep(10000000);

        wireMockServer.resetAll();
        wireMockServer.stop();
    }



}

