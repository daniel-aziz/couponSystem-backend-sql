package Facade;

import DAO.CompaniesDAO;
import DAO.CouponsDAO;
import DAO.CustomerDAO;
import DBDAO.CompaniesDBDAO;
import DBDAO.CouponsDBDAO;
import DBDAO.CustomerDBDAO;

/**
 * this is and abstract class, defines the abstract methods and fields to inherit to all facades.
 */
public abstract class ClientFacade {

    // FIELDS

    // initialized all DAO'S for inherited classes to use
    protected CompaniesDAO companiesDAO = new CompaniesDBDAO();
    protected CustomerDAO customerDAO = new CustomerDBDAO();
    protected CouponsDAO couponsDAO = new CouponsDBDAO();

    // METHODS

    /**
     * defines how login method signature
     * @param email receive a String of email field
     * @param password receive a String of password field
     * @return a boolean answer
     */
    public abstract boolean login(String email, String password);

}
