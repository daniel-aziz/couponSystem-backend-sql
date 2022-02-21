package DAO;

import Beans.Customer;

import java.sql.SQLException;
import java.util.ArrayList;


public interface CustomerDAO {
    boolean isCustomerExist(String email, String password) throws SQLException;
    void addCustomer(Customer customer) throws SQLException;
    void updateCustomer(Customer customer) throws SQLException;
    void deleteCustomer(int customerId) throws SQLException;
    ArrayList<Customer> getAllCustomers() throws SQLException;
    Customer getOneCustomer(int customerId) throws SQLException;
    int getCustomerID(String email) throws SQLException;
}
