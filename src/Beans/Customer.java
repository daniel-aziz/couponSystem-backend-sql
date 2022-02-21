package Beans;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This is a Class bean. defines the `Customer` object
 */
public class Customer {

    // FIELDS
    private int customerID; // takes the customer ID
    private String firstName; // takes the customer First Name
    private String lastName; // takes the customer Last Name
    private String email; // takes the customer Email
    private String password; // takes the customer Password
    private ArrayList<Coupon> coupons = new ArrayList<>(); // take the customer related coupons list

    // C'TOR

    /**
     * this C'TOR will builds the an instance of the company class
     * default customerID is 0, will auto increment once inserted into the DB and will received back
     * @param firstName takes the first name of a customer
     * @param lastName takes the last name of a customer
     * @param email takes the email of a customer
     * @param password takes the password of a customer
     */
    public Customer(String firstName, String lastName, String email, String password) {
        this.customerID = 0; // default id is 0, will auto increment once inserted into the DB and will received back
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;

    }

    /**
     * no arg C'TOR, for empty object creation
     */
    public Customer() {

    }

    // METHODS

    /**
     * returns the object field 'customerID'
     * @return int customerID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * sets the object field `customerID`
     * @param customerID receive an int `customerID`
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * returns the object field 'firstName'
     * @return String firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * sets the object field `firstName`
     * @param firstName receive an String `firstName`
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * returns the object field 'lastName'
     * @return String lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * sets the object field `lastName`
     * @param lastName receive an String `lastName`
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * returns the object field 'email'
     * @return String email
     */
    public String getEmail() {
        return email;
    }

    /**
     * sets the object field `email`
     * @param email receive an String `email`
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * returns the object field 'password'
     * @return String password
     */
    public String getPassword() {
        return password;
    }

    /**
     * sets the object field `password`
     * @param password receive an String `password`
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * returns the ArrayList field `coupons`
     * @return ArrayList `coupons`
     */
    public ArrayList<Coupon> getCoupons() {
        return coupons;
    }

    /**
     * sets the ArrayList field `coupons`
     * @param coupons receive a List `coupons`
     */
    public void setCoupons(ArrayList<Coupon> coupons) {
        this.coupons = coupons;
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
        Customer customer = (Customer) o;
        return customerID == customer.customerID && Objects.equals(firstName, customer.firstName) && Objects.equals(lastName, customer.lastName) && Objects.equals(email, customer.email) && Objects.equals(password, customer.password) && Objects.equals(coupons, customer.coupons);
    }

    /**
     * Uses a default hash code to generate and return a unique int number of an object
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(customerID, firstName, lastName, email, password, coupons);
    }

    /**
     * format a sting of all object details
     * @return Sting
     */
    @Override
    public String toString() {
        return "\nCustomer{" +
                "customerID=" + customerID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", coupons=" + coupons +
                '}';
    }
}
