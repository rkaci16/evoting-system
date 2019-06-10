package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database extends DatabaseConfig {

    private DatabaseConfig databaseConfig = new DatabaseConfig();

    private String driver = databaseConfig.getJdbcDriver();
    private String host = databaseConfig.getJdbcHost();
    private String username = databaseConfig.getUsername();
    private String pass = databaseConfig.getPassword();

    public Connection getConnection() throws Exception {

        Connection connection = null;

        Class.forName(driver);
        connection = DriverManager.getConnection(host, username, pass);

        return connection;
    }
}
