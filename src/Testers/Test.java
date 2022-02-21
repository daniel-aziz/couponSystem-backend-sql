package Testers;

import Beans.Company;
import Beans.Coupon;
import Beans.Customer;
import Execptions.InvalidUserTypeException;
import Execptions.InvalidLoginCredentialsException;
import Execptions.RestrictCouponPurchaseException;
import Facade.AdminFacade;
import Facade.CompanyFacade;
import Facade.CustomerFaced;
import LoginManger.LoginManager;
import SQL.ConnectionPool;
import SQL.DBUtils;
import SQL.DataBaseManager;
import Threads.CouponExpirationDailyJob;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class Test {
    /**
     * Creates CouponDB in the DB, Tables, Categories
     */
    public static void dBStart() {
        DBUtils.createDB();
        DBUtils.createAllTables();
        DBUtils.createALlCategories();
    }

    /**
     * Start the thread of daily job, Tests all Facades method, Stop thread daily job, close all connection
     */
    public static void testAll() {
        // Create & Start the Coupon Expiration Daily job (Currently on 5 sec interval)
        CouponExpirationDailyJob couponExpirationDailyJob = new CouponExpirationDailyJob();
        couponExpirationDailyJob.startDailyJob();

        try {
            //Admin Login
            AdminFacade adminFacade = (AdminFacade) LoginManager.getInstance().login("admin@admin.com", "admin", LoginManager.ClientType.Administrator);
            // Add rows (Companies) to DB
            Company company1 = new Company("dunder mifflin", "reception@dundermiffilin.com", "12345678");
            Company company2 = new Company("Intel", "ceo@intel.com", "12345678");
            adminFacade.addCompany(company1);
            adminFacade.addCompany(company2);
            // Get rows (Companies) from DB
            Company getCompany = adminFacade.getOneCompany(1);
            // Update row (Company) in the DB
            getCompany.setPassword("0000");
            adminFacade.updateCompany(getCompany);
            // Delete row (Company) from the DB
            adminFacade.deleteCompany(2);
            // Get all the companies from the DB
            List<Company> companies = adminFacade.getAllCompanies();
            // Add Customers to DB
            Customer customer1 = new Customer("Mario", "no last name", "Mario@nintendo.com", "12345678");
            Customer customer2 = new Customer("Luigi", "no last name", "Luigi@nintendo.com", "12345678");
            adminFacade.addCustomer(customer1);
            adminFacade.addCustomer(customer2);
            // Get row (Customer) from DB
            Customer getCustomer = adminFacade.getOneCustomer(1);
            // Update row (Customer) in the DB
            getCustomer.setPassword("0000");
            adminFacade.updateCustomer(getCustomer);
            // Delete row (Customer) from the DB
            adminFacade.deleteCustomer(2);
            // Get all Customers from the DB
            List<Customer> getCustomers = adminFacade.getAllCustomers();

            // -------------------------------------------------------------------------------

            // Company Login
            CompanyFacade companyFacade = (CompanyFacade) LoginManager.getInstance().login("reception@dundermiffilin.com", "0000", LoginManager.ClientType.Company);
            // Add Coupons of the company to the DB
            Coupon coupon1 = new Coupon(companyFacade.getCompanyId(), Coupon.Category.Electricity, "Lamps for cheap", "40% off lamps", LocalDateTime.now(), LocalDateTime.of(2022, 10, 12, 10, 30), 1000, 50, "image");
            Coupon coupon2 = new Coupon(companyFacade.getCompanyId(), Coupon.Category.Electricity, "Phones 50% off", "only in Eilat", LocalDateTime.now(), LocalDateTime.of(2022, 10, 12, 10, 30), 2000, 1000, "image");
            companyFacade.addCoupon(coupon1);
            companyFacade.addCoupon(coupon2);
            // Get all company coupons from the DB
            List<Coupon> getAllCoupons = companyFacade.getCompanyCoupons();
            // Update row (Coupon) in the DB
            getAllCoupons.get(0).setAmount(1500);
            companyFacade.updateCoupon(getAllCoupons.get(0));
            // Delete (Row) Coupon from the DB
            companyFacade.deleteCoupon(getAllCoupons.get(1).getCouponID());
            // Get all company coupons by Category
            List<Coupon> getAllCouponsCategory = companyFacade.getCompanyCoupons(Coupon.Category.Electricity);
            // Get all company coupons by Max Price
            List<Coupon> getAllCouponsPrice = companyFacade.getCompanyCoupons(2000);
            // Get Company Details
            Company getCompanyDetails = companyFacade.getCompanyDetails();

            // ------------------------------------------------------------------------------

            // Customer Login
            CustomerFaced customerFaced = (CustomerFaced) LoginManager.getInstance().login("Mario@nintendo.com", "0000", LoginManager.ClientType.Customer);
            // Customer Coupon Purchase (With returned coupons above)
            customerFaced.purchaseCoupon(getAllCoupons.get(0));
            // Get Customer Coupons
            List<Coupon> getCustomerCoupons = customerFaced.getCustomerCoupons();
            // Get Customer Coupons BY CATEGORY
            List<Coupon> getCustomerCouponsByCategory = customerFaced.getCustomerCoupons(Coupon.Category.Electricity);
            // Get Customer Coupons BY MAX PRICE
            List<Coupon> getCustomerCouponsByMaxPrice = customerFaced.getCustomerCoupons(2000);
            // Get Customer Details
            Customer getCustomerDetails = customerFaced.getCustomerDetails();
        } catch (InvalidLoginCredentialsException | InvalidUserTypeException | RestrictCouponPurchaseException e) {
            System.out.println("Failed to continue, Issue: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("General Exception: " + e.getMessage());
        } finally {
            // Stop Coupon Expiration Daily job
            couponExpirationDailyJob.stopDailyJob();
            try {
                // Close all connection to the DB
                ConnectionPool.getInstance().closeAllConnections();
            } catch (InterruptedException | SQLException e) {
                System.out.println(e.getMessage());
            }
        }


    }

    /**
     * Drop CouponDB from the DB
     */
    public static void dBDrop() {
        DBUtils.dropCouponDB();
    }

    /**
     * creates pre listed entries to the DB
     */
    public static void createEntriesToDB() {
        DBUtils.createEntriesToDB();
    }


}

