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
