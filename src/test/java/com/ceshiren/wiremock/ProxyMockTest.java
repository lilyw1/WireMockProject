package com.ceshiren.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.Response;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static java.lang.Thread.sleep;

/**
 * @author wyl
 * @create 2022-03-20 21:52
 */
public class ProxyMockTest {
    @Test
    //wiremock proxy
    //更改response内容为“Mock Java Other Utilities”
    void proxyTest() throws InterruptedException {
        int port = 8089;
        WireMockServer wireMockServer = new WireMockServer(wireMockConfig()
                .port(port)
                .extensions(new ResponseTransformer() {
                    @Override
                    public Response transform(Request request, Response response, FileSource fileSource, Parameters parameters) {
                        return Response.Builder.like(response)
                                .body(
                                        response
                                                .getBodyAsString()
                                                .replace("Other Utilities","Mock Java Other Utilities"))
                                .build();
                    }

                    @Override
                    public String getName() {
                        return "ResponseTransformDemo";
                    }
                })
        );
        wireMockServer.start();
        configureFor(port);
        //业务逻辑
        //添加代理
        stubFor(get(urlMatching(".*"))
                .willReturn(aResponse()
                        .proxiedFrom("https://httpbin.ceshiren.com")
                            )
                );

        sleep(1000000);

        wireMockServer.resetAll();
        wireMockServer.stop();

    }
}
