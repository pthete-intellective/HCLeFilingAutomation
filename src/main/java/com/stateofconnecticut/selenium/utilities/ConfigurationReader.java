package com.stateofconnecticut.selenium.utilities;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationReader {

    protected static final Log logger = LogFactory.getLog(ConfigurationReader.class);

    private ConfigurationReader() {
        // restrict
    }

    public static Properties readPropertiesFile(String filePath) {
        logger.debug("Reading properties from file " + filePath);
        try {
            Properties p = new Properties();
            if (filePath != null && filePath.length() > 0) {
                InputStream in = null;
                try {
                    in = getStream(filePath);
                    p.load(in);
                    return p;
                } finally {
                    if (in != null) {
                        in.close();
                    }
                }
            } else {
                logger.error("Properties file path not set, please provide.");
            }
        } catch (IOException ex) {
            logger.warn("file " + filePath + " not found");
        } catch (IllegalArgumentException ex) {
            logger.warn(ex.getMessage());
        }
        return null;
    }

    private static InputStream getStream(String name) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
    }

//    public static JSONObject readTestConfig(String filePath) throws IOException, JSONException {
//        URL url = Resources.getResource(filePath);
//        String text = Resources.toString(url, Charsets.UTF_8);
//        return new JSONObject(text);
//    }

}

