import java.lang.reflect.Method;

public class SetterGenerator {
    public static void main(String[] args) throws Exception {
        Person person = new Person();

        // Define and invoke setters dynamically
        setProperty(person, "name", "John Doe");
        setProperty(person, "age", 30);
        setProperty(person, "address", "123 Main St");

        // Display the updated person object
        System.out.println("Updated Person:");
        System.out.println(person);
    }

    // Method to dynamically set a property value using a setter method
    private static void setProperty(Object obj, String propertyName, Object value) throws Exception {
        // Capitalize the first letter of the property name to form the setter method name
        String setterMethodName = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);

        // Get the class object of the object
        Class<?> clazz = obj.getClass();

        // Get the method object corresponding to the setter method
        Method setterMethod = clazz.getMethod(setterMethodName, value.getClass());

        // Invoke the setter method to set the property value
        setterMethod.invoke(obj, value);
    }
}

// Example class representing a Person
class Person {
    private String name;
    private int age;
    private String address;

    // Default constructor
    public Person() {
    }

    // Getters and setters (omitted for brevity)

    // Setter methods for name, age, and address
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Override toString method for displaying Person details
    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                '}';
    }
}
