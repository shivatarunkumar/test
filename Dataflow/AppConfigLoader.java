import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

public class AppConfigLoader {

    public static AppConfig loadFromYaml(String yamlFilePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        return mapper.readValue(new File(yamlFilePath), AppConfig.class);
    }

    public static AppConfig loadFromProperties(String propertiesFilePath) throws IOException {
        AppConfig config = new AppConfig();
        java.util.Properties properties = new java.util.Properties();
        properties.load(new FileInputStream(propertiesFilePath));

        // Set properties if they exist in the file
        if (properties.containsKey("param1")) {
            config.setParam1(properties.getProperty("param1"));
        }
        if (properties.containsKey("param2")) {
            config.setParam2(Integer.parseInt(properties.getProperty("param2")));
        }
        if (properties.containsKey("param3")) {
            config.setParam3(Boolean.parseBoolean(properties.getProperty("param3")));
        }
        // Set more properties as needed

        return config;
    }

    public static void main(String[] args) {
        try {
            // Load from YAML file
            AppConfig yamlConfig = loadFromYaml("config.yaml");
            System.out.println("YAML Config: " + yamlConfig);

            // Load from properties file
            AppConfig propertiesConfig = loadFromProperties("config.properties");
            System.out.println("Properties Config: " + propertiesConfig);
        } catch (IOException e) {
            System.err.println("Error loading configuration: " + e.getMessage());
        }
    }
}















# 2 


    import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AppConfigLoader {

    public static AppConfig loadFromProperties(String propertiesFilePath) throws IOException {
        AppConfig config = new AppConfig();
        Properties properties = new Properties();
        properties.load(new FileInputStream(propertiesFilePath));

        // Set properties if they exist in the file
        if (properties.containsKey("param1")) {
            config.setParam1(properties.getProperty("param1"));
        } else {
            // Handle missing or invalid param1
            throw new IllegalArgumentException("param1 is required in properties file");
        }

        if (properties.containsKey("param2")) {
            try {
                config.setParam2(Integer.parseInt(properties.getProperty("param2")));
            } catch (NumberFormatException e) {
                // Handle invalid param2 format
                throw new IllegalArgumentException("param2 must be an integer in properties file");
            }
        } else {
            // Handle missing or invalid param2
            throw new IllegalArgumentException("param2 is required in properties file");
        }

        // Set default values or handle missing properties
        // config.setParam3(Boolean.parseBoolean(properties.getProperty("param3", "false")));
        // Add more properties as needed

        return config;
    }

    public static void main(String[] args) {
        try {
            // Load from properties file
            AppConfig propertiesConfig = loadFromProperties("config.properties");
            System.out.println("Properties Config: " + propertiesConfig);
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error loading configuration: " + e.getMessage());
        }
    }
}
