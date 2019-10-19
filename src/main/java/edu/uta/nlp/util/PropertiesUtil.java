package edu.uta.nlp.util;

import java.io.*;
import java.util.Properties;

/**
 * @author hxy
 */
public class PropertiesUtil {

    public static final String PROPERTIES_NAME = "config.properties";
    private static Properties prop = new Properties();
    static {
        InputStream is = null;
        try {
            is = PropertiesUtil.class.getClassLoader().getResourceAsStream(PROPERTIES_NAME);
            prop.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getPropery(String key) {
        return prop.getProperty(key);
    }

    public void writeProperty(String key, String value) {
        InputStream is = null;
        OutputStream os = null;
        Properties p = new Properties();
        try {
            is = new FileInputStream(PROPERTIES_NAME);
            p.load(is);
            os = new FileOutputStream(PropertiesUtil.class.getClassLoader().getResource(PROPERTIES_NAME).getFile());
            p.setProperty(key, value);
            p.store(os, key);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != is) {
                    is.close();
                }
                if (null != os) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
