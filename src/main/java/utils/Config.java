package utils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class Config {

    private Config() {

    }

    private static final String LOCAL_PROPERTIES_FILE = "localisedPropertiesFile";
    public static final Properties props = new Properties();
    private static HashMap<String, Properties> propertyObjMap = new HashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(Config.class.getName());

    private static synchronized String getPropertyValue(String property, String propertyFile) {
        String result;
        Properties prop = propertyObjMap.get(propertyFile);
        if (null == prop) {
            prop = loadPropertiesFile(propertyFile);
            propertyObjMap.put(propertyFile, prop);
        }
        result = prop.getProperty(property);
        return result;
    }

    private static Properties loadPropertiesFile(String propertyFile) {
        Properties prop = new Properties();
        FileInputStream input = null;
        try {
            input = new FileInputStream("src/test/resources/" + propertyFile);
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            logger.info("couldn't open propertyfile {}", propertyFile);
        }
        try {
            if (input != null) {
                prop.load(input);
            }

        } catch (Exception e) {
            logger.error("Exception: " + e);
        } finally {

            if (input != null) {

                try {
                    input.close();
                } catch (IOException e) {
                    logger.info("couldn't close input");
                }

            }
        }
        return prop;
    }

    private static void assertMandatoryPropertyValue(String result, String property) {
        if (StringUtils.isEmpty(result)) {
            logger.error("Mandatory property missing: {}", property);
            Assert.fail("Mandatory Property " + property + " either empty or not specified.");
        }
    }

    public static synchronized String getLocalisedMandatoryPropValue(String property) {

        String propFileName = Config.props.getProperty(LOCAL_PROPERTIES_FILE);
        String result = getPropertyValue(property, propFileName);
        assertMandatoryPropertyValue(result, property);
        return result;
    }

    public static String schemaFile(String testCase) {
        String fileName = testCase.replace("Steps", "");
        try (InputStream inputStream = new FileInputStream("src/test/resources/schema/" + fileName + ".json")) {
            return IOUtils.toString(inputStream);
        } catch (IOException e) {
            logger.info("couldn't load schema file {}", fileName);
        }
        return null;
    }
}
