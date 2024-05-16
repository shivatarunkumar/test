import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Map;

public class Config {
    private String databaseHost;
    private int databasePort;
    private String databaseUsername;
    private String databasePassword;
    private boolean appDebug;
    private int appMaxConnections;

    public Config(String configFile) {
        loadConfig(configFile);
    }

    private void loadConfig(String configFile) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(configFile)) {
            Yaml yaml = new Yaml();
            Map<String, Object> yamlData = yaml.load(inputStream);

            if (yamlData != null) {
                for (Method method : getClass().getDeclaredMethods()) {
                    if (isGetter(method)) {
                        String propertyName = getPropertyName(method.getName());
                        Object value = yamlData.get(propertyName);
                        if (value instanceof String) {
                            String strValue = (String) value;
                            if (strValue.startsWith("${") && strValue.endsWith("}")) {
                                String envVarName = strValue.substring(2, strValue.length() - 1);
                                String envVarValue = System.getenv(envVarName);
                                if (envVarValue != null) {
                                    method.invoke(this, convertToCorrectType(method, envVarValue));
                                }
                            } else {
                                method.invoke(this, convertToCorrectType(method, strValue));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isGetter(Method method) {
        return method.getName().startsWith("get") &&
               method.getParameterCount() == 0 &&
               !method.getReturnType().equals(void.class);
    }

    private String getPropertyName(String methodName) {
        return Character.toLowerCase(methodName.charAt(3)) + methodName.substring(4);
    }

    private Object convertToCorrectType(Method method, String value) {
        Class<?> returnType = method.getReturnType();
        if (returnType == String.class) {
            return value;
        } else if (returnType == int.class || returnType == Integer.class) {
            return Integer.parseInt(value);
        } else if (returnType == boolean.class || returnType == Boolean.class) {
            return Boolean.parseBoolean(value);
        } else {
            return value; // Handle other types as needed
        }
    }

    // Define getter methods for properties (omitted for brevity)

    public String getDatabaseHost() {
        return databaseHost;
    }

    public int getDatabasePort() {
        return databasePort;
    }

    public String getDatabaseUsername() {
        return databaseUsername;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public boolean isAppDebug() {
        return appDebug;
    }

    public int getAppMaxConnections() {
        return appMaxConnections;
    }

    public static void main(String[] args) {
        Config config = new Config("config.yml");

        System.out.println("Database Configuration:");
        System.out.println("Host: " + config.getDatabaseHost());
        System.out.println("Port: " + config.getDatabasePort());
        System.out.println("Username: " + config.getDatabaseUsername());
        System.out.println("Password: " + config.getDatabasePassword());

        System.out.println("\nApp Configuration:");
        System.out.println("Debug Mode: " + config.isAppDebug());
        System.out.println("Max Connections: " + config.getAppMaxConnections());
    }
}
