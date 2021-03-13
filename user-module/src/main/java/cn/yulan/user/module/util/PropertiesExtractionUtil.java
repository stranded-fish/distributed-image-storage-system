package cn.yulan.user.module.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * 配置文件读取工具
 *
 * @author Yulan Zhou
 * @date 2021/03/13
 */
public class PropertiesExtractionUtil {
    private static Properties properties;

    static {
        properties = new Properties();
        URL url = new PropertiesExtractionUtil().getClass().getClassLoader().getResource("application.properties");
        try{
            properties.load(new FileInputStream(url.getPath()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key){
        return properties.getProperty(key);
    }
}