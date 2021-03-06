package com.kaisn.utils;

import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;

public class PropertyUtil {

    private static Logger logger = Logger.getLogger(PropertyUtil.class);

    private static Properties props;
    static {
        loadProps();
    }

    synchronized static private void loadProps() {
        logger.info("loading properties file content.......");
        props = new Properties();
        InputStream in = null;
        try {
            // <!--第一种，通过类加载器进行获取properties文件流-->
           // in = new FileInputStream("src/main/resources/properties/config.properties");
            // <!--第二种，通过类进行获取properties文件流-->
             in = PropertyUtil.class.getResourceAsStream("/properties/config.properties");
            props.load(in);
        } catch (FileNotFoundException e) {
            logger.error("path.properties file not found");
        } catch (IOException e) {
            logger.error("occur IOException");
        } finally {
            IOUtils.closeQuietly(in);
        }
        logger.info("loaded properties file content...........");
        logger.info("properties file content：" + props);
    }

    public static String getProperty(String key) {
        if (null == props) {
            loadProps();
        }
        return props.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        if (null == props) {
            loadProps();
        }
        return props.getProperty(key, defaultValue);
    }
}
