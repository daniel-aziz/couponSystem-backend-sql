package SQL;

import Beans.Company;
import Beans.Coupon;
import Beans.Customer;
import DBDAO.CompaniesDBDAO;
import DBDAO.CouponsDBDAO;
import DBDAO.CustomerDBDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

/**
 * this class hold programmer methods for randon entries to the DB, and methods to create those
 */
public class DBUtils {

    /**
     * execute queries to the DB without values
     *
     * @param sqlStatement
     */
    public static void runQuery(String sqlStatement) throws SQLException {
        Connection connection = null;
        try {
            // gets connection from the connection pool
            connection = ConnectionPool.getInstance().getConnection();
            // prepare statement and execute
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.execute();
        } catch (SQLException | InterruptedException err) {
            System.out.println(err.getMessage());
        } finally {
            // return a connection back to the connection pool
            ConnectionPool.getInstance().returnConnection(connection);

        }
    }

    /**
     * creates the `couponDB` database
     */
    public static void createDB() {
        try {
            runQuery(DataBaseManager.CREATE_COUPONDB);
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    /**
     * creates all tables in the `couponDB` database
     */
    public static void createAllTables() {
        try {
            runQuery(DataBaseManager.CREATE_TABLE_CUSTOMERS);
            runQuery(DataBaseManager.CREATE_TABLE_CATEGORY);
            runQuery(DataBaseManager.CREATE_TABLE_COMPANIES);
            runQuery(DataBaseManager.CREATE_TABLE_COUPONS);
            runQuery(DataBaseManager.CREATE_TABLE_CUSTOMERS_VS_COUPONS);
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }

    }

    /**
     * creates all coupon categories in the `couponDB` database
     */
    public static void createALlCategories() {
        String add_category = "INSERT INTO `couponDB`.`categories` (`name`) VALUES (?)";
        Connection connection = null;
        for (Enum item : Coupon.Category.values()) {
            try {
                // gets connection from the connection pool
                connection = ConnectionPool.getInstance().getConnection();
                // prepare statement and execute
                PreparedStatement statement = connection.prepareStatement(add_category);
                statement.setString(1, item.toString());
                statement.execute();
            } catch (SQLException | InterruptedException err) {
                System.out.println(err.getMessage());
            } finally {
                try {
                    // return a connection back to the connection pool
                    ConnectionPool.getInstance().returnConnection(connection);
                } catch (SQLException err) {
                    System.out.println(err.getMessage());
                }
            }
        }
    }

    /**
     * drop schema `couponDB` from the sql server
     */
    public static void dropCouponDB() {
        try {
            runQuery(DataBaseManager.DROP_COUPONDB);
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    /**
     * create and insert all prepared entries into the DB
     */
    public static void createEntriesToDB() {
        createAllCompanies();
        createAllCustomers();
        createAllCoupons();
        createAllPurchases();
    }

    /**
     * create the prepared companies list the in DB
     */
    private static void createAllCompanies() {
        Iterator itr = entriesLists.listOfCompanies().iterator();
        CompaniesDBDAO companiesDBDAO = new CompaniesDBDAO();
        while (itr.hasNext()) {
            try {
                companiesDBDAO.addCompany((Company) itr.next());
            } catch (SQLException throwables) {
                System.out.println(throwables.getMessage());
            }
        }
    }

    /**
     * create the prepared coupons list the in DB
     */
    private static void createAllCoupons() {
        Iterator itr = entriesLists.listAllCoupons().iterator();
        CouponsDBDAO couponsDBDAO = new CouponsDBDAO();
        while (itr.hasNext()) {
            try {
                couponsDBDAO.addCoupon((Coupon) itr.next());
            } catch (SQLException throwables) {
                System.out.println(throwables.getMessage());
            }
        }
    }

    /**
     * create the prepared customers list the in DB
     */
    private static void createAllCustomers() {
        Iterator itr = entriesLists.listOfCustomers().iterator();
        CustomerDBDAO customerDBDAO = new CustomerDBDAO();
        while (itr.hasNext()) {
            try {
                customerDBDAO.addCustomer((Customer) itr.next());
            } catch (SQLException throwables) {
                System.out.println(throwables.getMessage());
            }
        }
    }

    /**
     * holds list of purchases and create them in DB
     */
    private static void createAllPurchases() {
        CouponsDBDAO couponsDBDAO = new CouponsDBDAO();
        try {
            couponsDBDAO.addCouponPurchase(1, 1);
            couponsDBDAO.addCouponPurchase(1, 2);
            couponsDBDAO.addCouponPurchase(1, 3);
            couponsDBDAO.addCouponPurchase(2, 3);
            couponsDBDAO.addCouponPurchase(2, 1);
            couponsDBDAO.addCouponPurchase(3, 7);
            couponsDBDAO.addCouponPurchase(3, 8);
            couponsDBDAO.addCouponPurchase(4, 5);
            couponsDBDAO.addCouponPurchase(4, 6);
            couponsDBDAO.addCouponPurchase(5, 1);
            couponsDBDAO.addCouponPurchase(10, 6);
            couponsDBDAO.addCouponPurchase(11, 9);
            couponsDBDAO.addCouponPurchase(12, 10);

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }


    }

    /**
     * prepared lists on entries
     */
    protected static class entriesLists {

        /**
         * List of
         *
         * @return ArrayList of Companies
         */
        private static ArrayList<Company> listOfCompanies() {
            List<Company> companies = new ArrayList<>() {{
                add(new Company("JohnBryce", "office@jb.com", "ad561xop6"));
                add(new Company("IBM", "corp@ibm.com", "Wpdn3@!"));
                add(new Company("Check Point", "ceo@cp.com", "12345678"));
                add(new Company("Apple", "AppleOne@apple.com", "microsoft"));
                add(new Company("Tesla", "m.elon@tesla.com", "bitcoin"));
                add(new Company("BMW", "bmw.head@WV.com", "carsgovroomvroom"));
                add(new Company("Medellín Cartel", "e.pablo@drugs.com", "ilovemoney"));
                add(new Company("Goldstar", "beers@goldstar.co.il", "b00ze"));
            }};
            return (ArrayList<Company>) companies;
        }

        /**
         * List of
         *
         * @return ArrayList of Customers
         */
        private static ArrayList<Customer> listOfCustomers() {
            List<Customer> customers = new ArrayList<>() {{
                add(new Customer("Erik", "Einstein", "ss@gmail.com", "mezizim"));
                add(new Customer("Yasmin", "Aladdin", "yanda@gmail.comn", "magiclamp"));
                add(new Customer("Eric", "Clapton", "ec@yahoo.com", "8812313"));
                add(new Customer("Sami", "FireFighter", "fire@down.com", "water"));
                add(new Customer("Haim", "Haaaaim", "cg@walla.com", "curlyhair"));
                add(new Customer("Jon", "Doe", "mjd1000@fbi.com", "notfound"));
                add(new Customer("Jane", "Doe", "fjd2005@fbi.com", "notfound"));
                add(new Customer("Michael", "Douglas", "michael@holywood.cali", "movies"));
                add(new Customer("Danny", "Din", "danny@gmail.com", "nopassword"));
                add(new Customer("Ben", "Gurion", "negev@israel.com", "first"));
                add(new Customer("Shaquille ", "O'Neil", "shaq@NBA.com", "basketball"));
                add(new Customer("Bill", "Gates", "b.gates@microsoft.com", "stevejobs"));

            }};
            return (ArrayList<Customer>) customers;
        }

        /**
         * List of
         *
         * @return ArrayList of Coupons
         */
        private static ArrayList<Coupon> listAllCoupons() {
            List<Coupon> coupons = new ArrayList<>() {{
                add(new Coupon(1, Coupon.Category.Electricity, "Lamps for cheap", "40% off lamps", LocalDateTime.now(), LocalDateTime.of(2022, 10, 12, 10, 30), 20, 50.5, "image"));
                add(new Coupon(2, Coupon.Category.Electricity, "Lamps Discount", "40% off lamps", LocalDateTime.now(), LocalDateTime.of(2022, 10, 12, 10, 30), 20, 50.5, "image"));
                add(new Coupon(1, Coupon.Category.Computers, "Free Cables", "All Cables for free", LocalDateTime.now(), LocalDateTime.of(2021, 6, 19, 00, 00), 200, 45.0, "image"));
                add(new Coupon(3, Coupon.Category.Concert, "Eyal Golan Zappa", "20 ILS OFF for leumi bank", LocalDateTime.now(), LocalDateTime.of(2021, 10, 10, 00, 00), 200, 250, "image"));
                add(new Coupon(4, Coupon.Category.Movie, "30 ILS ticket on weekend", "only at rav hen Eilat", LocalDateTime.now(), LocalDateTime.of(2021, 6, 20, 00, 00), 2500, 30, "image"));
                add(new Coupon(6, Coupon.Category.Restaurant, "1+1 Cafe Cafe", "1+1 breakfest meals", LocalDateTime.now(), LocalDateTime.of(2021, 06, 25, 10, 30), 250, 100, "image"));
                add(new Coupon(7, Coupon.Category.Vacations, "Suites of by 10% per night", "Hilton hotels for the summer", LocalDateTime.now(), LocalDateTime.of(2021, 9, 1, 1, 30), 10000, 800, "image"));
                add(new Coupon(7, Coupon.Category.Vacations, "Suites Deluxe of by 10% per night", "Hilton hotels for the summer", LocalDateTime.now(), LocalDateTime.of(2021, 9, 1, 1, 30), 10000, 800, "image"));
                add(new Coupon(8, Coupon.Category.Movie, "Fast and Furious 8", "1+1 sunday to monday", LocalDateTime.now(), LocalDateTime.of(2022, 6, 1, 1, 30), 10000, 60, "image"));
            }};
            return (ArrayList<Coupon>) coupons;
        }


    }

}
