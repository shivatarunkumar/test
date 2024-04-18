import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLServerConnection {
    public static void main(String[] args) {
        // Database connection parameters
        String url = "jdbc:sqlserver://localhost:1433;databaseName=mydatabase"; // Replace with your SQL Server URL and database name
        String username = "your_username";
        String password = "your_password";

        // JDBC driver name
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

        // Attempt to establish database connection
        try {
            // Load the JDBC driver
            Class.forName(driver);

            // Connect to the database
            Connection conn = DriverManager.getConnection(url, username, password);

            // Print a success message if connected
            System.out.println("Connected to the database!");

            // Perform database operations...

            // Close the connection
            conn.close();

        } catch (ClassNotFoundException e) {
            // Handle ClassNotFoundException (driver not found)
            System.err.println("Error loading JDBC driver: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            // Handle SQLException (connection error)
            System.err.println("Database connection error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
