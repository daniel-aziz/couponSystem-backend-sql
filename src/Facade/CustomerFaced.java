package Facade;

import Beans.Coupon;
import Beans.Customer;
import Execptions.RestrictCouponPurchaseException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * this class includes methods to interact with the program for Customer end user, uses all DAO'S for executions
 */
public class CustomerFaced extends ClientFacade {

    // FIELDS

    private int customerId; // the customer id to interact with the DB

    // C`TOR

    /**
     * no arg C'TOR, for empty object creation
     */
    public CustomerFaced() {

    }

    // METHODS

    /**
     * sets the object field `customerId`
     * @param customerId receive an int `customerId`
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * returns the object field 'customerId'
     * @return int customerId
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * check's if user login details are correct by using customerDAO
     * if correct sets the user customerId
     * @param email receive a String of email field
     * @param password receive a String of password field
     * @return boolean answer
     */
    public boolean login(String email, String password) {
        try {
            if (customerDAO.isCustomerExist(email, password)) {
                setCustomerId(customerDAO.getCustomerID(email));
                return true;
            }

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return false;
    }

    /**
     * purchase coupon for the customer, and insert it into the DB by using couponDAO
     * will execute if , amount of coupon is higher than 0, is not expired, the customer hasn't purchased the coupon already
     * will lower the amount of the coupon by 1, add the purchase to DB, and update the coupon int the DB
     * @param coupon receives a coupon object
     * @throws RestrictCouponPurchaseException if failed to meet the requirements of coupon purchases
     */
    public void purchaseCoupon(Coupon coupon) throws RestrictCouponPurchaseException {
        try {
            if (coupon.getAmount() > 0 && (LocalDateTime.now().isBefore(coupon.getEndDate())) && !couponsDAO.isCouponPurchased(this.getCustomerId(), coupon.getCouponID())) {
                    couponsDAO.addCouponPurchase(this.getCustomerId(), coupon.getCouponID());
                    coupon.setAmount(coupon.getAmount() - 1);
                    couponsDAO.updateCoupon(coupon);
            } else {
                throw new RestrictCouponPurchaseException();
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    /**
     * gets all the (logged) customer coupon list from the DB by using the couponDAO
     * @return ArrayList of Coupons
     */
    public ArrayList<Coupon> getCustomerCoupons() {
        List<Coupon> couponList = new ArrayList<>();
        try {
            couponsDAO.getCouponsOfCustomer(this.getCustomerId());
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return (ArrayList<Coupon>) couponList;
    }

    /**
     * gets all the (logged) customer coupon list by category from the DB by using the couponDAO
     * @param category receives a coupon enum category
     * @return ArrayList of Coupons
     */
    public ArrayList<Coupon> getCustomerCoupons(Coupon.Category category) {
        List<Coupon> couponList = new ArrayList<>();
        try {
            couponsDAO.getCouponsOfCustomer(this.getCustomerId(), category);
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return (ArrayList<Coupon>) couponList;
    }

    /**
     * gets all the (logged) customer coupon list by maxPrice from the DB by using the couponDAO
     * @param maxPrice receives a coupon field maxPrice
     * @return ArrayList of Coupons
     */
    public ArrayList<Coupon> getCustomerCoupons(double maxPrice) {
        List<Coupon> couponList = new ArrayList<>();
        try {
            couponsDAO.getCouponsOfCustomer(this.getCustomerId(), maxPrice);
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return (ArrayList<Coupon>) couponList;
    }

    /**
     * gets the details of the logged customer from the DB by using the customerDAO
     * @return a customer bean
     */
    public Customer getCustomerDetails() {
        Customer customer = new Customer();
        try {
            customer = customerDAO.getOneCustomer(this.getCustomerId());
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return customer;
    }
}
