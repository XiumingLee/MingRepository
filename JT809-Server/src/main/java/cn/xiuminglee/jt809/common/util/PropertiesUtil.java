package cn.xiuminglee.jt809.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;
public class PropertiesUtil {
    private static Logger log = LoggerFactory.getLogger(PropertiesUtil.class);
    private static Properties props;
    static {
        String fileName = "application.properties";
        props = new Properties();
        try {
            props.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName),"UTF-8"));
        } catch (IOException e) {
            log.error("load configuration file failure!",e);
        }
    }
    public static String getProperty(String key){
        String value = props.getProperty(key.trim());
        if(value.isEmpty()){
            return null;
        }
        return value.trim();
    }

    public static String getProperty(String key,String defaultValue){
        String value = props.getProperty(key.trim());
        if(value.isEmpty()){
            value = defaultValue;
        }
        return value.trim();
    }
}
