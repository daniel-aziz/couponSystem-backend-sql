package DBDAO;

import Beans.Company;
import DAO.CompaniesDAO;
import SQL.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * this class defines the SQL Query and the exaction related to the companies table in the DB
 */
public class CompaniesDBDAO implements CompaniesDAO {
    // IS_EXIST returns a row from companies table by email and password fields
    private static final String IS_EXISTS = "SELECT * FROM `couponDB`.`companies` WHERE email=? AND BINARY password=?"; // case sensitive
    // private static final String IS_EXISTS = "SELECT count(*) FROM `couponDB`.`companies` WHERE email=? AND password=?"; // case in-sensitive

    // ADD_COMPANY insert a row into the companies table (without id)
    private static final String ADD_COMPANY = "INSERT INTO `couponDB`.`companies` (`name`,`email`,`password`) VALUES (?,?,?)";
    // UPDATE_COMPANY update a row in the companies table by id (can only update, email/password)
    private static final String UPDATE_COMPANY = "UPDATE `couponDB`.`companies` SET `email`=?,`password`=? WHERE id=?";
    // GET_COMPANY_BY_ID return a row from companies table by `id`
    private static final String GET_COMPANY_BY_ID = "SELECT * FROM `couponDB`.`companies` WHERE id=?";
    // DELETE_COMPANY deletes a row in the companies table by `id`
    private static final String DELETE_COMPANY = "DELETE FROM `couponDB`.`companies` WHERE id=?";
    // GET_ALL_COMPANIES return all rows in the companies table
    private static final String GET_ALL_COMPANIES = "SELECT * FROM `couponDB`.`companies` ORDER BY `id`";
    // GET_COMPANY_ID_BY_EMAIL returns an `id` field by `email` field
    private static final String GET_COMPANY_ID_BY_EMAIL = "SELECT `id` FROM `couponDB`.`companies` WHERE `email`=?";

    // a connection object to the class
    private Connection connection;

    /**
     * execute the Query to the DB `IS_EXIST` and return a boolean answer
     * @param email receives an company bean email
     * @param password receives an company bean password
     * @return boolean answer
     * @throws SQLException if failed to return connection back to ConnectionPool
     */
    @Override
    public boolean isCompanyExits(String email, String password) throws SQLException {
        /*
        try {

            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(IS_EXISTS);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return (resultSet.getInt(1) > 0);
        } catch (InterruptedException | SQLException err) {
            System.out.println(err.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return false;
        */ // First method
        /*
        boolean isExist = false;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(IS_EXISTS);
            statement.setString(1, email);
            statement.setString(2, password);
            isExist = statement.execute();
        } catch (InterruptedException | SQLException err) {
            System.out.println(err.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return isExist;
         */ // Third Method
        // second method - working!

        boolean isExist = false;
        try {
            // gets connection from the connection pool
            connection = ConnectionPool.getInstance().getConnection();
            // prepare statement and execute
            PreparedStatement statement = connection.prepareStatement(IS_EXISTS);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                isExist = resultSet.getObject(1, Boolean.class);
            }
        } catch (InterruptedException | SQLException err) {
            System.out.println(err.getMessage());
        } finally {
            // return a connection back to the connection pool
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return isExist;

    }

    /**
     * execute the Query to the DB `ADD_COMPANY `
     * @param company receives the a company object
     * @throws SQLException if failed to return connection back to ConnectionPool
     */
    @Override
    public void addCompany(Company company) throws SQLException {
        try {
            // gets connection from the connection pool
            connection = ConnectionPool.getInstance().getConnection();
            // prepare statement and execute
            PreparedStatement statement = connection.prepareStatement(ADD_COMPANY);
            statement.setString(1, company.getName());
            statement.setString(2, company.getEmail());
            statement.setString(3, company.getPassword());
            statement.execute();
        } catch (InterruptedException | SQLException err) {
            System.out.println(err.getMessage());
        } finally {
            // return a connection back to the connection pool
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    /**
     * execute the Query to the DB `UPDATE_COMPANY`
     * @param company receives the a company object
     * @throws SQLException if failed to return connection back to ConnectionPool
     */
    @Override
    public void updateCompany(Company company) throws SQLException {
        try {
            // gets connection from the connection pool
            connection = ConnectionPool.getInstance().getConnection();
            // prepare statement and execute
            PreparedStatement statement = connection.prepareStatement(UPDATE_COMPANY);
            statement.setString(1, company.getEmail());
            statement.setString(2, company.getPassword());
            statement.setInt(3, company.getCompanyID());
            statement.execute();
        } catch (SQLException | InterruptedException err) {
            System.out.println(err.getMessage());
        } finally {
            // return a connection back to the connection pool
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    /**
     * execute the Query to the DB `DELETE_COMPANY `
     * @param companyId receives the a company bean field `companyId`
     * @throws SQLException if failed to return connection back to ConnectionPool
     */
    @Override
    public void deleteCompany(int companyId) throws SQLException {
        try {
            // gets connection from the connection pool
            connection = ConnectionPool.getInstance().getConnection();
            // prepare statement and execute
            PreparedStatement statement = connection.prepareStatement(DELETE_COMPANY);
            statement.setInt(1, companyId);
            statement.execute();
        } catch (SQLException | InterruptedException err) {
            System.out.println(err.getMessage());
        } finally {
            // return a connection back to the connection pool
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    /**
     * execute the Query to the DB `GET_ALL_COMPANIES` and returns a List of all companies
     * @return ArrayList of companies
     * @throws SQLException if failed to return connection back to ConnectionPool
     */
    @Override
    public ArrayList<Company> getAllCompanies() throws SQLException {
        List<Company> companies = new ArrayList<>();
        try {
            // gets connection from the connection pool
            connection = ConnectionPool.getInstance().getConnection();
            // prepare statement and execute
            PreparedStatement statement = connection.prepareStatement(GET_ALL_COMPANIES);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Company company = new Company();
                company.setCompanyID(resultSet.getInt("id"));
                company.setName(resultSet.getString("name"));
                company.setEmail(resultSet.getString("email"));
                company.setPassword(resultSet.getString("password"));
                company.setCouponsList(new CouponsDBDAO().getCouponsOfCompany(company.getCompanyID()));
                companies.add(company);
            }
        } catch (SQLException | InterruptedException err) {
            System.out.println(err.getMessage());
        } finally {
            // return a connection back to the connection pool
            ConnectionPool.getInstance().returnConnection(connection);
        }

        return (ArrayList<Company>) companies;
    }

    /**
     * execute the Query to the DB `GET_COMPANY_BY_ID` and returns an object of the required company
     * @param companyId receives the a company bean field `companyId`
     * @return Company object
     * @throws SQLException if failed to return connection back to ConnectionPool
     */
    @Override
    public Company getOneCompany(int companyId) throws SQLException {
        Company company = new Company();
        try {
            // gets connection from the connection pool
            connection = ConnectionPool.getInstance().getConnection();
            // prepare statement and execute
            PreparedStatement statement = connection.prepareStatement(GET_COMPANY_BY_ID);
            statement.setInt(1, companyId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                company.setCompanyID(resultSet.getInt("id"));
                company.setName(resultSet.getString("name"));
                company.setEmail(resultSet.getString("email"));
                company.setPassword(resultSet.getString("password"));
                company.setCouponsList(new CouponsDBDAO().getCouponsOfCompany(company.getCompanyID()));
            }
        } catch (SQLException | InterruptedException err) {
            System.out.println(err.getMessage());
        } finally {
            // return a connection back to the connection pool
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return company;
    }

    /**
     * execute the Query to the DB `GET_COMPANY_ID_BY_EMAIL` and returns an int
     * @param email receives the a company bean field `email`
     * @return int field `companyId`
     * @throws SQLException if failed to return connection back to ConnectionPool
     */
    @Override
    public int getCompanyID(String email) throws SQLException {
        int companyId = 0;
        try {
            // gets connection from the connection pool
            connection = ConnectionPool.getInstance().getConnection();
            // prepare statement and execute
            PreparedStatement statement = connection.prepareStatement(GET_COMPANY_ID_BY_EMAIL);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                companyId = resultSet.getInt("id");
            }
        } catch (SQLException | InterruptedException err) {
            System.out.println(err.getMessage());
        } finally {
            // return a connection back to the connection pool
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return companyId;
    }


}
