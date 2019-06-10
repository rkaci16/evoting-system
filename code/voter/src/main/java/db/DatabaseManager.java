package db;

import java.sql.*;

public class DatabaseManager {

    private static DatabaseManager databaseManager = new DatabaseManager();
    private Connection connection = null;
    private Database db = new Database();

    public Connection getConnection() {
        return this.connection;
    }

    public void connectDatabase() throws Exception {
        this.connection = db.getConnection();
        if (this.connection == null) {
            System.out.println("Problem connecting with Database");
        }
    }

    public void closeConnection() {

        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
        }
    }


    public static DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public int getGeneratedID(PreparedStatement stmt) throws Exception {
        ResultSet keyResultSet = stmt.getGeneratedKeys();
        if (keyResultSet.next()) {
            return keyResultSet.getInt(1);
        } else {
            throw new Exception("Next ID has not been generated!");
        }
    }
}
