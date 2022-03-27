package com.ceshiren.util;

import java.util.Locale;

/**
 * @author wyl
 * @description javafaker用于生成随机数据
 * @create 2022-03-27 17:29
 */
public class FakerUtil{
    com.github.javafaker.Faker faker = new com.github.javafaker.Faker(Locale.CHINA);

    //部门名

    public String get_teamName(){
        String teamName = faker.company().suffix();
        return teamName;
    }

    //名字
    public String get_name(){
        String name = faker.name().fullName();
        //System.out.println(name);
        return name;
    }


    //句子
    public String get_sentence(){
        return faker.lorem().sentence(6);
    }


    //随机数
    public int get_num(int max){
        return faker.number().numberBetween(50,max);
    }

    public String getTimeStamp(){
        return  String.valueOf(System.currentTimeMillis());
    }

}


