package LoginManger;

import Execptions.InvalidUserTypeException;
import Execptions.InvalidLoginCredentialsException;
import Facade.AdminFacade;
import Facade.ClientFacade;
import Facade.CompanyFacade;
import Facade.CustomerFaced;

/**
 * this Singleton class used for login into the system
 */
public class LoginManager {

    /**
     * defines the type of user that can use/log into the system
     */
    public enum ClientType {
        Administrator, Company, Customer
    }

    // FIELDS

    private static LoginManager instance = null; // instance for this class

    // C'TOR

    /**
     * C'TOR will be created if instance was not been made
     */
    private LoginManager() {
    }

    /**
     * gets the Class instance
     * @return One LoginManager instance
     */
    public static LoginManager getInstance() {
        if (instance == null) {
            synchronized (LoginManager.class) {
                if (instance == null) {
                    instance = new LoginManager();
                }
            }
        }
        return instance;
    }

    /**
     *  this login method checks, users entered type,email,password. uses switch to locate how to check login and to return to correct facade
     *  will return null if user credentials or type are incorrect
     * @param email receives user entered email field
     * @param password receives user entered password field
     * @param clientType receives user entered email enum
     * @return ClientFaced (inherited facades or null object)
     * @throws InvalidUserTypeException thrown if user entered type is incorrect
     * @throws InvalidLoginCredentialsException thrown if user entered credentials (email/password) is incorrect
     */
    public ClientFacade login(String email, String password, ClientType clientType) throws InvalidUserTypeException, InvalidLoginCredentialsException {
        switch (clientType) {
            case Administrator:
                ClientFacade adminFaced = new AdminFacade();
                if (adminFaced.login(email, password)) {
                    return adminFaced;
                } else {
                    throw new InvalidLoginCredentialsException();
                }
            case Company:
                ClientFacade companyFaced = new CompanyFacade();
                if (companyFaced.login(email, password)) {
                    return companyFaced;
                } else {
                    throw new InvalidLoginCredentialsException();
                }
            case Customer:
                ClientFacade customerFaced = new CustomerFaced();
                if (customerFaced.login(email, password)) {
                    return customerFaced;
                } else {
                    throw new InvalidLoginCredentialsException();
                }
            default:
                throw new InvalidUserTypeException();
        }
    }

}
