package db;

import filter.AdminFilter;
import filter.CitizienFilter;
import model.*;
import sample.UserController;

import javax.management.monitor.Monitor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDB extends DatabaseManager {



    /**
     * @param filter conditions for where clause
     * @return list of citiziens satisfying the applied filters
     * @throws SQLException in case a SQL error occurs
     *                      kjo funksionon kshu tsh:
     *                      ky filteri esht i klas qe ka si atribute element
     *                      qe ti do i perdoresh per te selektuar disa qytetar
     *                      qe plotsojn i kush te caktum. Psh nqs do me mar
     *                      qytetaret me emrin Patrik do vendoesh ke filteri
     *                      filter.setName("Patrik") pstj kur tvi filteri ke kjo metoda
     *                      do boje SELECT * FORM citizien WHERE  2 sec e kuptoj thjesht ku do percaktohet filteri
     *                      ? ke middleware ta boj? bo
     */
    public List <Citizen> getCitiziens(CitizienFilter filter) throws SQLException {
        List <Citizen> citiziens = new ArrayList <>();

        String query = "SELECT *" +
                "\n FROM citizien" +
                "\n WHERE ";
        if (filter.isFullBody()) {
            query += "1 = 1"; //tani ti boj kshu komit dhe push? po ja i sek
        } else if (filter.hasIdFilter()) {
            query += "citizien.id = " + filter.getId();
        } else if (filter.hasNameFilter()) {
            query += "citizien.name = " + filter.getName();
        } else if (filter.hasSurnameFilter()) {
            query += "citizien.surname = " + filter.getSurname();
        }

        Statement stmt = getConnection().createStatement();
        ResultSet rs = stmt.executeQuery(query);
        Citizen citizen = null;
        while (rs.next()) {
            citizen = new Citizen(rs.getInt("id"),rs.getInt("hasVoted"));
            citizen.setPassportId(rs.getString("passport_id"));
            citizen.setName(rs.getString("name"));
            citizen.setSurname(rs.getString("surname"));

            citiziens.add(citizen);
        }
        return citiziens;
    }

    public void getNameFromAuthor(int id) throws Exception {

        String query1 = "SELECT * FROM author WHERE id = 1"; // boje me ID pstj

        Statement statement1 = super.getConnection().createStatement();
        ResultSet resultSet1 = statement1.executeQuery(query1);
        if (resultSet1.next()) {
            String firstName = resultSet1.getString("name");
            System.out.println(firstName);
        } else {
            System.out.println("This author doesn't exist!");
        }
    }

    public void getAllUsers() throws Exception {

        String query4 = "SELECT * FROM author";

        Statement statement4 = super.getConnection().createStatement();
        ResultSet resultSet4 = statement4.executeQuery(query4);

    }

    public void getSurnameFromAuthor() throws Exception {

        String query1 = "SELECT surname FROM author WHERE id = 1";

        Statement statement2 = super.getConnection().createStatement();
        ResultSet resultSet2 = statement2.executeQuery(query1);
        if (resultSet2.next()) {
            String lastName = resultSet2.getString("surname");
            System.out.println(lastName);
        }
    }

    public void insertIntoAuthor() throws Exception {

        String query3 = "INSERT INTO author(id, name, surname) VALUES ('6','John','Doe')";

        Statement statement3 = super.getConnection().createStatement();
        statement3.executeUpdate(query3);
    }

    public boolean doesCitizienExist(String passId) throws Exception {
        UserController userController = new UserController();

        String queryCheck = "SELECT * FROM author where id = '" + userController.getPassId() + "'";

        PreparedStatement preparedStatement = super.getConnection().prepareStatement(queryCheck);
        ResultSet resultSetCheck = preparedStatement.executeQuery();

        if (resultSetCheck.next()) {
            return true;
        }
        return false;
    }

    public void saveCitizen(Citizen citizen) throws Exception {
        PreparedStatement stmt = getConnection().prepareStatement("INSERT INTO citizen " +
                "(id, " +
                "passportId," +
                "name," +
                "surname," +
                "VALUES ( ?,?,?,?);");
        int i = 1;
        stmt.setInt(i++, citizen.getId());
        stmt.setString(i++, citizen.getPassportId());
        stmt.setString(i++, citizen.getName());
        stmt.setString(i++, citizen.getSurname());


        stmt.executeUpdate();
        citizen.setId(getGeneratedID(stmt));
        stmt.close();
    }
    public void saveCandidate(Candidate candidate) throws Exception {
        connectDatabase();
        PreparedStatement stmt = getConnection().prepareStatement(/*"INSERT INTO candidate " +
                "(id, " +
                "name," +
                "leader," +
                "VALUES (?,?,?);");*/
        "INSERT INTO Candidate"
                + "(id,name,leader) "
                + "VALUES "
                + "(?,?,?)", Statement.RETURN_GENERATED_KEYS);
        int i = 1;
        stmt.setInt(i++, candidate.getID());
        stmt.setString(i++, candidate.getName());
        stmt.setString(i++, candidate.getLeader());

        stmt.executeUpdate();
        candidate.setID(getGeneratedID(stmt));
        stmt.close();
        closeConnection();
    }
    public void saveVotingCenter(VotingCenter votingCenter) throws Exception {
        connectDatabase();
        PreparedStatement stmt = getConnection().prepareStatement(/*"INSERT INTO voting_center " +
                "(id, " +
                "city," +
                "centerNum " +
                "VALUES (?,?,?);");*/
                "INSERT INTO voting_center"
                        + "(id,city,centerNum) "
                        + "VALUES "
                        + "(?,?,?)", Statement.RETURN_GENERATED_KEYS);
        int i = 1;
        stmt.setInt(i++, votingCenter.getID());
        stmt.setString(i++, votingCenter.getCity());
        stmt.setInt(i++, votingCenter.getUnitCenterNo());

        stmt.executeUpdate();
        votingCenter.setID(getGeneratedID(stmt));
        stmt.close();
        closeConnection();
    }




    public void updateCitizen(Citizen user) throws Exception {
        connectDatabase();
            String query = "UPDATE citizien SET " +
                    "hasVoted = ? " +
                    "WHERE id = ?;  ";


            PreparedStatement stmt = getConnection().prepareStatement(query);
            int i = 1;
            stmt.setInt(i++, user.getHasVoted());
            stmt.setInt(i++, user.getId());

            stmt.executeUpdate();
            stmt.close();
            closeConnection();
    }
    public void updateCandidate(Candidate candidate) throws Exception {
        connectDatabase();
        String query = "UPDATE Candidate SET " +
                "name = ?," +
                "leader = ?"+
                "WHERE id = ?;  ";

        PreparedStatement stmt = getConnection().prepareStatement(query);
        int i = 1;
        stmt.setString(i++, candidate.getName());
        stmt.setString(i++, candidate.getLeader());
        stmt.setInt(i++, candidate.getID());

        stmt.executeUpdate();
        stmt.close();
        closeConnection();
    }
    public void updateVotingCenter(VotingCenter votingCenter) throws Exception {
        String query = "UPDATE voting_center SET " +
                "city = ?, " +
                "centerNum = ? " +
                "WHERE id = ?;";

        connectDatabase();

        PreparedStatement stmt = getConnection().prepareStatement(query);
        int i = 1;
        stmt.setString(i++, votingCenter.getCity());
        stmt.setInt(i++, votingCenter.getUnitCenterNo());
        stmt.setInt(i++, votingCenter.getID());

        stmt.executeUpdate();
        stmt.close();

        closeConnection();
    }

    public void deleteCitizen(int id) throws Exception {
        String query = "DELETE FROM citizen WHERE ID = ?";

        PreparedStatement stmt = getConnection().prepareStatement(query);
        stmt.setInt(1, id);
        stmt.executeUpdate();
        stmt.close();
    }
    public void deleteCandidate(int id) throws Exception {
        String query = "DELETE FROM Candidate WHERE ID ='" + id + "'";

        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }

        String url = "jdbc:mysql://remotemysql.com/geiWbVZPjZ";
        try {
            conn = DriverManager.getConnection(url,"geiWbVZPjZ", "uV0o55bynF");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.executeUpdate();
        stmt.close();
    }

    public void deleteVotingCenter(int id) throws SQLException {
        String query = "DELETE FROM voting_center WHERE ID ='" + id + "'";

        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }

        String url = "jdbc:mysql://remotemysql.com/geiWbVZPjZ";
        try {
            conn = DriverManager.getConnection(url,"geiWbVZPjZ", "uV0o55bynF");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.executeUpdate();
        stmt.close();
    }
    public List <Admin> getAdmin(AdminFilter filter) throws SQLException {
        List <Admin> admins = new ArrayList <>();

        String query = "SELECT *" +
                "\n FROM admin" +
                "\n WHERE ";
        if (filter.isFullBody()) {
            query += "1 = 1"; //tani ti boj kshu komit dhe push? po ja i sek
        } else if (filter.hasIdFilter()) {
            query += "admin.id = " + filter.getId();
        } else if (filter.hasNameFilter()) {
            query += "admin.full_name = " + filter.getFullname();
        } else if (filter.hasUsernameFilter()) {
            query += "admin.username = " + filter.getUsername();
        } else if (filter.hasPasswordFilter()) {
            query += "admin.password = " + filter.getPassword();
        }
        Statement stmt = getConnection().createStatement();
        ResultSet rs = stmt.executeQuery(query);
        Admin admin = null;
        while (rs.next()) {
            admin = new Admin(rs.getInt("id"));
            admin.setFullName(rs.getString("full_name"));
            admin.setUsername(rs.getString("username"));
            admin.setPassword(rs.getString("password"));

            admins.add(admin);
        }
        return admins;
    }

    public void updateVotingStatus(VotingStatus votingStatus) throws Exception {
        connectDatabase();
        String query = "UPDATE votingStatus SET " +
                "hasStarted = ?;  ";


        PreparedStatement stmt = getConnection().prepareStatement(query);
        int i = 1;
        stmt.setInt(i++, votingStatus.getHasStarted());

        stmt.executeUpdate();
        stmt.close();
        closeConnection();
    }

    public void updateSupervisor(Supervisor supervisor) throws Exception {
        connectDatabase();
        String query = "UPDATE supervisor SET " +
                "full_name = ?," +
                "voting_center = ? " +
                "WHERE id = ?;  ";


        PreparedStatement stmt = getConnection().prepareStatement(query);
        int i = 1;
        stmt.setString(i++, supervisor.getFullName());
        stmt.setInt(i++, supervisor.getVotingCenter());
        stmt.setInt(i++, supervisor.getId());

        stmt.executeUpdate();
        stmt.close();
        closeConnection();
    }

    public void saveSupervisor(Supervisor supervisor) throws Exception {
        connectDatabase();
        PreparedStatement stmt = getConnection().prepareStatement(/*"INSERT INTO supervisor " +
                "(id, " +
                "full_name," +
                "voting_center," +
                "VALUES (?,?,?);");*/
        "INSERT INTO supervisor"
                + "(id,full_name,voting_center) "
                + "VALUES "
                + "(?,?,?)", Statement.RETURN_GENERATED_KEYS);
        int i = 1;
        stmt.setInt(i++, supervisor.getId());
        stmt.setString(i++, supervisor.getFullName());
        stmt.setInt(i++, supervisor.getVotingCenter());

        stmt.executeUpdate();
        supervisor.setId(getGeneratedID(stmt));
        stmt.close();
        closeConnection();
    }

    public void deleteSupervisor(int id) throws SQLException {
        String query = "DELETE FROM supervisor WHERE ID ='" + id + "'";

        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }

        String url = "jdbc:mysql://remotemysql.com/geiWbVZPjZ";
        try {
            conn = DriverManager.getConnection(url,"geiWbVZPjZ", "uV0o55bynF");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.executeUpdate();
        stmt.close();
    }
}