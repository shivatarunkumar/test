import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLServerExample {

    public static void main(String[] args) {

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            System.err.println("Failed to load SQL Server JDBC driver.");
            e.printStackTrace();
            return;
        }
        
        // JDBC URL for SQL Server
        String jdbcUrl = "jdbc:sqlserver://your_server:1433;databaseName=your_database";
        String username = "your_username";
        String password = "your_password";

        // SQL query to execute
        String sqlQuery = "SELECT * FROM your_table";

        // Establishing connection
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            System.out.println("Connected to SQL Server.");

            // Creating statement
            try (Statement statement = connection.createStatement()) {
                // Executing query
                try (ResultSet resultSet = statement.executeQuery(sqlQuery)) {
                    // Processing query results
                    while (resultSet.next()) {
                        // Example: Retrieve values by column index (1-based)
                        int column1Value = resultSet.getInt(1);
                        String column2Value = resultSet.getString(2);
                        // Example: Retrieve values by column name
                        // String column2Value = resultSet.getString("column2");

                        // Process retrieved values
                        System.out.println("Column1: " + column1Value + ", Column2: " + column2Value);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
