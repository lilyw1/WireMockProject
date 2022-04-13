package com.ceshiren.util;

import java.io.IOException;
import java.util.Properties;

/**
 * @author wyl
 * @description 解析全局配置文件config.properties的工具类
 * @create 2022-04-10 14:07
 */
public class ConfigUtil {
    public Object getProperties(String key){
        Properties properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream("/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties.get(key);
    }
}
