package Facade;

import Beans.Company;
import Beans.Coupon;
import Execptions.CouponAlreadyExistsException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * this class includes methods to interact with the program for Company end user, uses all DAO'S for executions
 */
public class CompanyFacade extends ClientFacade {

    // FIELDS

    private int companyId; // the company id to interact with the DB

    // C`TOR

    /**
     * no arg C'TOR, for empty object creation
     */
    public CompanyFacade() {

    }

    // METHODS

    /**
     * sets the object field `companyID`
     * @param companyId receive an int `companyID`
     */
    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    /**
     * returns the object field 'companyID'
     * @return int companyID
     */
    public int getCompanyId() {
        return companyId;
    }

    /**
     * check's if user login details are correct by using companiesDAO
     * if correct sets the user companyID
     * @param email receive a String of email field
     * @param password receive a String of password field
     * @return boolean answer
     */
    public boolean login(String email, String password) {
        try {
            if (companiesDAO.isCompanyExits(email, password)) {
                setCompanyId(companiesDAO.getCompanyID(email));
                return true;
            }

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return false;
    }

    /**
     * add a coupon to the DB by using the couponDAO
     * @param coupon receives a coupon bean
     * @throws CouponAlreadyExistsException if coupons is already exist by the title and companyId
     */
    public void addCoupon(Coupon coupon) throws CouponAlreadyExistsException {
        try {
            if (!couponsDAO.isCouponExists(coupon.getCompanyID(), coupon.getTitle())) {
                coupon.setCompanyID(this.getCompanyId());
                couponsDAO.addCoupon(coupon);
            } else {
                throw new CouponAlreadyExistsException();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * update a coupon to the DB by using the couponDAO
     * @param coupon receives a coupon bean
     */
    public void updateCoupon(Coupon coupon) {
        try {
            couponsDAO.updateCoupon(coupon);
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    /**
     * delete a coupon from the DB by using the couponDAO
     * @param couponId receives a coupon field
     */
    public void deleteCoupon(int couponId) {
        try {
            couponsDAO.deleteCoupon(couponId);
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    /**
     * gets all the (logged) company coupon list from the DB by using the couponDAO
     * @return ArrayList of Coupons
     */
    public ArrayList<Coupon> getCompanyCoupons() {
        List<Coupon> couponList = new ArrayList<>();
        try {
            couponList = couponsDAO.getCouponsOfCompany(this.getCompanyId());
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return (ArrayList<Coupon>) couponList;
    }

    /**
     * gets all the (logged) company coupon list by category from the DB by using the couponDAO
     * @param category receives a coupon enum category
     * @return ArrayList of Coupons
     */
    public ArrayList<Coupon> getCompanyCoupons(Coupon.Category category) {
        List<Coupon> couponList = new ArrayList<>();
        try {
            couponList = couponsDAO.getCouponsOfCompany(this.getCompanyId(), category);
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return (ArrayList<Coupon>) couponList;
    }

    /**
     * gets all the (logged) company coupon list by maxPrice from the DB by using the couponDAO
     * @param maxPrice receives a coupon field maxPrice
     * @return ArrayList of Coupons
     */
    public ArrayList<Coupon> getCompanyCoupons(double maxPrice) {
        List<Coupon> couponList = new ArrayList<>();
        try {
            couponList = couponsDAO.getCouponsOfCompany(this.getCompanyId(), maxPrice);
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return (ArrayList<Coupon>) couponList;
    }

    /**
     * gets the details of the logged company from the DB by using the companiesDAO
     * @return a company bean
     */
    public Company getCompanyDetails() {
        Company company = new Company();
        try {
            company = companiesDAO.getOneCompany(this.getCompanyId());
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return company;
    }

}
