package DAO;

import Beans.Coupon;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;



public interface CouponsDAO {
    void addCoupon(Coupon coupon) throws SQLException;
    void updateCoupon(Coupon coupon) throws SQLException;
    void deleteCoupon(int couponID) throws SQLException;
    ArrayList<Coupon> getAllCoupons() throws SQLException;
    Coupon getOneCoupon(int couponID) throws SQLException;
    void addCouponPurchase(int customerID, int couponID) throws SQLException;
    void deleteCouponPurchase(int customerID, int couponID) throws SQLException;
    ArrayList<Coupon> getCouponsOfCompany(int companyID) throws SQLException;
    ArrayList<Coupon> getCouponsOfCompany(int companyID, Coupon.Category category) throws SQLException;
    ArrayList<Coupon> getCouponsOfCompany(int companyID, double maxPrice) throws SQLException;
    void deleteExpiredCoupons() throws SQLException;
    boolean isCouponExists(int companyId, String title) throws SQLException;
    boolean isCouponPurchased(int customerId, int couponId) throws SQLException;
    ArrayList<Coupon> getCouponsOfCustomer(int customerID) throws SQLException;
    ArrayList<Coupon> getCouponsOfCustomer(int customerID, Coupon.Category category) throws SQLException;
    ArrayList<Coupon> getCouponsOfCustomer(int customerID, double maxPrice) throws SQLException;

}
