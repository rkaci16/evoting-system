package middleware;

import db.DatabaseManager;
import db.UserDB;
import filter.CitizienFilter;
import model.Citizen;

import java.util.List;

public class UserMiddleware {

    private UserDB userDB;

    public UserMiddleware() throws Exception{ // TODO po e lej kshu iher po duhet mendu a tbohet kshu
        this.userDB = new UserDB();
        userDB.connectDatabase(); // vtm kur krijo middleware lidhet me db pstj bon veprimet
    }

    public void execute() {

        try {
            userDB.connectDatabase();
//            userDB.getSurnameFromAuthor();
//            userDB.doesCitizienExist();
            DatabaseManager.getDatabaseManager().closeConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Citizen> getAllCitiziens() {
        try{

            return userDB.getCitiziens(new CitizienFilter(true)); // isFullBody = true for all records in the table
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            userDB.closeConnection();
        }
        return null;
    }

    public Citizen getCitizienById(int id) {
        try{
            CitizienFilter filter = new CitizienFilter();
            filter.setId(id);
            return userDB.getCitiziens(filter).get(0); // returns null if citizien with specified ID does not exist
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            userDB.closeConnection();
        }
        return null;
    }
    // tashi gjith kjoesht bo mos me shkrujt disa query po t perdoresh vetem ate qe bone ti?
    // po kshu nuk bon kod kot dhe ke me pak mundsi tkesh buge se e shkru vec iher
    // nca kuptimi e shkru vec i her se ket pjes se kam t qart
    // 1. shkrun vec i query jo disa
    // 2. nqs ka bug e ke vetem ke i metod vtm me ate do meresh
    // po mir e kuptoj po edhe n ket rast ti 1 query ke po do bosh shum filtrime kurse n at rastin tj thjesht ke disa query dhe do i thrrasesh
    // po si esht me e thjesht dhe e shpejt me bo disa metoda me query result sets prepared statements apo me shut nje if aty me filter?
    // ehe po e marr vesh se aty n fakt do perserisesh shum kod po bone disa query po ket metoden se kisha pa najher n fakt rrsp :p
    // haah tkurse shum koh kjo po e shof e kam kuptu tn thjesht kodi kshu specifikisht do pa tamom psh

    public List<Citizen> getCitiziensByName(String name) {
        try{
            CitizienFilter filter = new CitizienFilter();
            filter.setName(name);
            return userDB.getCitiziens(filter);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            userDB.closeConnection();
        }
        return null;
    }

    public boolean doesCitizenExist(String passId) {
        try{
            return userDB.doesCitizienExist(passId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            userDB.closeConnection();
        }
        return false;
    }
}
