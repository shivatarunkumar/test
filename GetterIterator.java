import java.lang.reflect.Method;

public class GetterIterator {
    public static void main(String[] args) {
        Person person = new Person("John Doe", 30, "123 Main St");

        // Get the class object corresponding to the Person class
        Class<?> personClass = person.getClass();

        // Get all methods declared in the Person class
        Method[] methods = personClass.getDeclaredMethods();

        // Iterate over each method
        for (Method method : methods) {
            // Check if the method is a getter (starts with "get" and has no parameters)
            if (isGetter(method)) {
                try {
                    // Invoke the getter method to get the property value
                    Object value = method.invoke(person);

                    // Extract the property name from the method name
                    String propertyName = getPropertyName(method.getName());

                    // Print property name and value
                    System.out.println(propertyName + ": " + value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Helper method to check if a method is a getter
    private static boolean isGetter(Method method) {
        return method.getName().startsWith("get") &&
               method.getParameterCount() == 0 &&
               !method.getReturnType().equals(void.class);
    }

    // Helper method to extract property name from getter method name
    private static String getPropertyName(String methodName) {
        return methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
    }
}
