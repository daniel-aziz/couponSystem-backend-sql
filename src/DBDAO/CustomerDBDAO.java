package DBDAO;

import Beans.Customer;
import DAO.CustomerDAO;
import SQL.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * this class defines the SQL Query and the exaction related to the customers table in the DB
 */
public class CustomerDBDAO implements CustomerDAO {
    // IS_EXIST returns a row from customers table by email and password fields
    private static final String IS_EXISTS = "SELECT * FROM `couponDB`.`customers` WHERE email=? AND BINARY password=?"; // case sensitive
    // private static final String IS_EXISTS = "SELECT count(*) FROM `couponDB`.`customers` WHERE email=? AND password=?"; // case in-sensitive

    // ADD_CUSTOMER insert a row into the customers table (without id)
    private static final String ADD_CUSTOMER = "INSERT INTO `couponDB`.`customers` (`firstName`,`lastName`,`email`,`password`) VALUES (?,?,?,?)";
    // UPDATE_CUSTOMER update a row in the customers table by id (can only update, firstName/lastName/email/password)
    private static final String UPDATE_CUSTOMER = "UPDATE `couponDB`.`customers` SET `firstName`=?,`lastName`=?,`email`=?,`password`=? WHERE id=?";
    // GET_CUSTOMER_BY_ID return a row from customers table by `id`
    private static final String GET_CUSTOMER_BY_ID = "SELECT * FROM `couponDB`.`customers` WHERE id=?";
    // DELETE_CUSTOMER deletes a row in the customers table by `id`
    private static final String DELETE_CUSTOMER = "DELETE FROM `couponDB`.`customers` WHERE id=?";
    // GET_ALL_CUSTOMER return all rows in the customers table
    private static final String GET_ALL_CUSTOMER = "SELECT * FROM `couponDB`.`customers` ORDER BY `ID`";
    // GET_CUSTOMER_ID_BY_EMAIL returns an `id` field by `email` field
    private static final String GET_CUSTOMER_ID_BY_EMAIL = "SELECT `id` FROM `couponDB`.`customers` WHERE `email`=?";

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
    public boolean isCustomerExist(String email, String password) throws SQLException {
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
     * execute the Query to the DB `ADD_CUSTOMER`
     * @param customer receives the a customer object
     * @throws SQLException if failed to return connection back to ConnectionPool
     */
    @Override
    public void addCustomer(Customer customer) throws SQLException {
        try {
            // gets connection from the connection pool
            connection = ConnectionPool.getInstance().getConnection();
            // prepare statement and execute
            PreparedStatement statement = connection.prepareStatement(ADD_CUSTOMER);
            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getEmail());
            statement.setString(4, customer.getPassword());
            statement.execute();
        } catch (InterruptedException | SQLException err) {
            System.out.println(err.getMessage());
        } finally {
            // return a connection back to the connection pool
            ConnectionPool.getInstance().returnConnection(connection);
        }

    }
    /**
     * execute the Query to the DB `UPDATE_CUSTOMER`
     * @param customer receives the a customer object
     * @throws SQLException if failed to return connection back to ConnectionPool
     */
    @Override
    public void updateCustomer(Customer customer) throws SQLException {
        try {
            // gets connection from the connection pool
            connection = ConnectionPool.getInstance().getConnection();
            // prepare statement and execute
            PreparedStatement statement = connection.prepareStatement(UPDATE_CUSTOMER);
            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getEmail());
            statement.setString(4, customer.getPassword());
            statement.setInt(5, customer.getCustomerID());
            statement.execute();
        } catch (SQLException | InterruptedException err) {
            System.out.println(err.getMessage());
        } finally {
            // return a connection back to the connection pool
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    /**
     * execute the Query to the DB `DELETE_CUSTOMER`
     * @param customerId receives the a customer bean field `customerId`
     * @throws SQLException if failed to return connection back to ConnectionPool
     */
    @Override
    public void deleteCustomer(int customerId) throws SQLException {
        try {
            // gets connection from the connection pool
            connection = ConnectionPool.getInstance().getConnection();
            // prepare statement and execute
            PreparedStatement statement = connection.prepareStatement(DELETE_CUSTOMER);
            statement.setInt(1, customerId);
            statement.execute();
        } catch (SQLException | InterruptedException err) {
            System.out.println(err.getMessage());
        } finally {
            // return a connection back to the connection pool
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    /**
     * execute the Query to the DB `GET_ALL_CUSTOMER` and returns a List of all customers
     * @return ArrayList of customers
     * @throws SQLException if failed to return connection back to ConnectionPool
     */
    @Override
    public ArrayList<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        try {
            // gets connection from the connection pool
            connection = ConnectionPool.getInstance().getConnection();
            // prepare statement and execute
            PreparedStatement statement = connection.prepareStatement(GET_ALL_CUSTOMER);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setCustomerID(resultSet.getInt("id"));
                customer.setFirstName(resultSet.getString("firstName"));
                customer.setLastName(resultSet.getString("lastName"));
                customer.setEmail(resultSet.getString("email"));
                customer.setPassword(resultSet.getString("password"));
                customer.setCoupons(new CouponsDBDAO().getCouponsOfCustomer(customer.getCustomerID()));
                customers.add(customer);
            }
        } catch (SQLException | InterruptedException err) {
            System.out.println(err.getMessage());
        } finally {
            // return a connection back to the connection pool
            ConnectionPool.getInstance().returnConnection(connection);
        }

        return (ArrayList<Customer>) customers;
    }

    /**
     * execute the Query to the DB `GET_CUSTOMER_BY_ID` and returns an object of the required customer
     * @param customerId receives the a customer bean field `customerId`
     * @return Customer object
     * @throws SQLException if failed to return connection back to ConnectionPool
     */
    @Override
    public Customer getOneCustomer(int customerId) throws SQLException {
        Customer customer = new Customer();
        try {
            // gets connection from the connection pool
            connection = ConnectionPool.getInstance().getConnection();
            // prepare statement and execute
            PreparedStatement statement = connection.prepareStatement(GET_CUSTOMER_BY_ID);
            statement.setInt(1, customerId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                customer.setCustomerID(resultSet.getInt("id"));
                customer.setFirstName(resultSet.getString("firstName"));
                customer.setLastName(resultSet.getString("lastName"));
                customer.setEmail(resultSet.getString("email"));
                customer.setPassword(resultSet.getString("password"));
                customer.setCoupons(new CouponsDBDAO().getCouponsOfCustomer(customer.getCustomerID()));
            }
        } catch (SQLException | InterruptedException err) {
            System.out.println(err.getMessage());
        } finally {
            // return a connection back to the connection pool
            ConnectionPool.getInstance().returnConnection(connection);
        }

        return customer;
    }

    /**
     * execute the Query to the DB `GET_CUSTOMER_ID_BY_EMAIL` and returns an int
     * @param email receives the a company bean field `email`
     * @return int field `customer`
     * @throws SQLException if failed to return connection back to ConnectionPool
     */
    @Override
    public int getCustomerID(String email) throws SQLException {
        int customerId = 0;
        try {
            // gets connection from the connection pool
            connection = ConnectionPool.getInstance().getConnection();
            // prepare statement and execute
            PreparedStatement statement = connection.prepareStatement(GET_CUSTOMER_ID_BY_EMAIL);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                customerId = resultSet.getInt("id");
            }
        } catch (SQLException | InterruptedException err) {
            System.out.println(err.getMessage());
        } finally {
            // return a connection back to the connection pool
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return customerId;
    }
}
