package com.ceshiren2.exercise.po;

import org.junit.jupiter.api.AfterAll;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author wyl
 * @description
 * @create 2022-04-10 16:29
 */
public class BaseTest {
//  @BeforeAll
    @AfterAll
    //删除以.txt结尾的log日志
    static void cleanLogTest(){
        System.out.println(System.getProperty("user.dir"));
        File file = new File(System.getProperty("user.dir"));
        File[] files = file.listFiles();
        //将文件名toString,过滤出.txt结尾的
        //放在集合里，一层层删除
        Arrays.stream(files).filter(file1 -> file1.toString().endsWith(".txt"))
                .collect(Collectors.toList())
                .forEach(file1 -> file1.delete());
    }
}
