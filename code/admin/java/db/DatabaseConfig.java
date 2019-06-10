package db;

import java.io.Serializable;

public class    DatabaseConfig {

    private String jdbcDriver = "com.mysql.jdbc.Driver";
    private String jdbcHost = "jdbc:mysql://remotemysql.com/geiWbVZPjZ";
    private String username = "geiWbVZPjZ";
    private String password = "uV0o55bynF";

    public String getJdbcDriver() {
        return this.jdbcDriver;
    }

    public void setJdbcDriver(String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
    }

    public String getJdbcHost() {
        return this.jdbcHost;
    }

    public void setJdbcHost() {
        this.jdbcHost = jdbcHost;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String pass) {
        this.password = pass;
    }

}
