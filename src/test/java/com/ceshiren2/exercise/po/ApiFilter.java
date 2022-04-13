package com.ceshiren2.exercise.po;

import com.ceshiren.util.FakerUtil;
import com.ceshiren.util.TimeUtil;
import io.restassured.builder.ResponseBuilder;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.filter.log.LogDetail;
import io.restassured.internal.print.RequestPrinter;
import io.restassured.internal.print.ResponsePrinter;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import static io.qameta.allure.Allure.addAttachment;
import static io.restassured.config.LogConfig.logConfig;

/**
 * @author wyl
 * @description 过滤器，给每个请求添加access_token参数
 * @create 2022-04-10 15:26
 */
//1.请求的log日志存到allure(请求log存到日志文件内，以时间戳命名文件)
//2.allure动态添加到报告里
public class ApiFilter implements Filter {

    @Override
    public Response filter(FilterableRequestSpecification filterableRequestSpecification, FilterableResponseSpecification filterableResponseSpecification, FilterContext filterContext) {
        //log日志文件的名称生成
        //String apiLogPath = "target/log/" + TimeUtil.getFormat() + new FakerUtil().get_num(2000) + ".txt";
        //或者生成在根目录下，afterall进行清除
        String apiLogPath = TimeUtil.getFormat() + new FakerUtil().get_num(2000) + ".txt";
        //修改请求
        //动态添加全局参数
        filterableRequestSpecification.queryParam("access_token", GetToken.getToken());
        //print(FilterableRequestSpecification requestSpec, String requestMethod, String completeRequestUri, LogDetail logDetail, Set<String> blacklistedHeaders, PrintStream stream, boolean shouldPrettyPrint)
        //接口请求打印输出到文件路径里
        try {
            RequestPrinter.print(
                    filterableRequestSpecification,
                    filterableRequestSpecification.getMethod(),
                    filterableRequestSpecification.getURI(),
                    LogDetail.ALL,
                    logConfig().blacklistedHeaders(),
                    new PrintStream(new FileOutputStream(apiLogPath, false)),
                    true);
            //allure 报告动态添加接口请求日志
            addAttachment("接口请求日志",new FileInputStream(apiLogPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Response responseOrigin = filterContext.next(filterableRequestSpecification, filterableResponseSpecification);
        //修改响应
        Response responseAction = new ResponseBuilder().clone(responseOrigin).build();
        //接口响应打印输出到文件路径里
        //print(ResponseOptions responseOptions, ResponseBody responseBody, PrintStream stream, LogDetail logDetail, boolean shouldPrettyPrint, Set<String> blacklistedHeaders)
        try {
            ResponsePrinter.print(
                    responseAction,
                    responseAction.getBody(),
                    new PrintStream(new FileOutputStream(apiLogPath, false)),
                    LogDetail.ALL,
                    true,
                    logConfig().blacklistedHeaders());
            addAttachment("接口响应日志", new FileInputStream(apiLogPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return  responseAction;


    }
}
