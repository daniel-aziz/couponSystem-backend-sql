package Beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This is a Class bean. defines the `Company` object
 */
public class Company {

    // FIELDS

    private int companyID; // takes the company ID
    private String name; // takes the company Name
    private String email; // take the company email
    private String password; // take the company password
    private List<Coupon> couponsList = new ArrayList<>(); // take the company related coupons list

    // C'TOR

    /**
     * this CTOR will builds the an instance of the company class
     * default companyID is 0, will auto increment once inserted into the DB and will received back
     * @param name takes in the company name
     * @param email takes in the company email
     * @param password takes in the company password
     */
    public Company(String name, String email, String password) {
        this.companyID = 0; // default id is 0, will auto increment once inserted into the DB and will received back
        this.name = name;
        this.email = email;
        this.password = password;
        this.couponsList = new ArrayList<>();
    }

    /**
     * no arg C'TOR, for empty object creation
     */
    public Company() {}

    // METHODS

    /**
     * returns the object field 'companyID'
     * @return int companyID
     */
    public int getCompanyID() {
        return companyID;
    }

    /**
     * sets the object field `companyID`
     * @param companyID receive an int `companyID`
     */
    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    /**
     * returns the object field 'name'
     * @return String name
     */
    public String getName() {
        return name;
    }

    /**
     * sets the object field `name`
     * @param name receive a String `name`
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * returns the object field `email`
     * @return String `email`
     */
    public String getEmail() {
        return email;
    }

    /**
     * sets the object field `email`
     * @param email receive a String `email`
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * returns the object field `password`
     * @return String `password`
     */
    public String getPassword() {
        return password;
    }

    /**
     * sets the object field `password`
     * @param password receive a String `password`
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * returns the ArrayList field `couponList`
     * @return ArrayList `couponList`
     */
    public List<Coupon> getCouponsList() {
        return couponsList;
    }

    /**
     * sets the ArrayList field `couponsList`
     * @param couponsList receive a List `couponsList`
     */
    public void setCouponsList(List<Coupon> couponsList) {
        this.couponsList = couponsList;
    }

    /**
     * comparing two object of this class, return a boolean answer
     * @param o object compered to this.object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return companyID == company.companyID && Objects.equals(name, company.name) && Objects.equals(email, company.email) && Objects.equals(password, company.password) && Objects.equals(couponsList, company.couponsList);
    }

    /**
     * Uses a default hash code to generate and return a unique int number of an object
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(companyID, name, email, password, couponsList);
    }

    /**
     * format a sting of all object details
     * @return Sting
     */
    @Override
    public String toString() {
        return "\nCompany{" +
                "companyID=" + companyID +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", couponsList=" + couponsList +
                '}';
    }
}
