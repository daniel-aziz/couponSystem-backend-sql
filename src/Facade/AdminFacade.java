package Facade;

import Beans.Company;
import Beans.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * this class includes methods to interact with the program for ADMIN end user, uses all DAO'S for executions
 */
public class AdminFacade extends ClientFacade {

    // FIELDS

    private static final String ADMIN_EMAIL = "ADMIN@ADMIN.COM"; // HARD_CODED EMAIL
    private static final String ADMIN_PASSWORD = "admin"; // HARD_CODED PASSWORDS

    /**
     * no arg C'TOR, for empty object creation
     */
    public AdminFacade() {
    }

    // METHODS

    /**
     * check's if user login details are correct
     * @param email receive a String of email field
     * @param password receive a String of password field
     * @return boolean answer
     */
    @Override
    public boolean login(String email, String password) {
        return ((email).equalsIgnoreCase(ADMIN_EMAIL) && password.equals(ADMIN_PASSWORD));
    }

    /**
     * add a company to the DB by using the companiesDAO
     * @param company receives a company bean
     */
    public void addCompany(Company company) {
        try {
            companiesDAO.addCompany(company);
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }

    }

    /**
     * update a company to the DB by using the companiesDAO
     * @param company receives a company bean
     */
    public void updateCompany(Company company) {
        try {
            companiesDAO.updateCompany(company);
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }

    }

    /**
     * update a company to the DB by using the companiesDAO
     * @param companyId receives a company field
     */
    public void deleteCompany(int companyId) {
        try {
            companiesDAO.deleteCompany(companyId);
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    /**
     * gets a companies lists from the DB by using the companiesDAO
     * @return ArrayList of Companies
     */
    public ArrayList<Company> getAllCompanies() {
        List<Company> companyList = new ArrayList<>();
        try {
            companyList = companiesDAO.getAllCompanies();
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return (ArrayList<Company>) companyList;
    }

    /**
     * gets a company from the DB by using the companiesDAO
     * @param companyId receives a company field
     * @return a Company object
     */
    public Company getOneCompany(int companyId) {
        Company company = new Company();
        try {
            company = companiesDAO.getOneCompany(companyId);
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return company;
    }

    /**
     * add a customer to the DB by using the customerDAO
     * @param customer receives a customer bean
     */
    public void addCustomer(Customer customer) {
        try {
            customerDAO.addCustomer(customer);
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    /**
     * update a customer to the DB by using the customerDAO
     * @param customer receives a customer bean
     */
    public void updateCustomer(Customer customer) {
        try {
            customerDAO.updateCustomer(customer);
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    /**
     * delete a customer from the DB by using the customerDAO
     * @param customerId receives a customer object field
     */
    public void deleteCustomer(int customerId) {
        try {
            customerDAO.deleteCustomer(customerId);
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    /**
     * gets a customers list from the DB by using the customerDAO
     * @return ArrayList of Customers
     */
    public ArrayList<Customer> getAllCustomers() {
        List<Customer> customerList = new ArrayList<>();
        try {
            customerList = customerDAO.getAllCustomers();
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return (ArrayList<Customer>) customerList;
    }

    /**
     * gets a customer from the DB by using the customerDAO
     * @param customerId receives a customer field
     * @return a customer object
     */
    public Customer getOneCustomer(int customerId) {
        Customer customer = new Customer();
        try {
            customer = customerDAO.getOneCustomer(customerId);
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return customer;
    }

}
