package Beans;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * This is a Class bean. defines the `Coupon` object
 */
public class Coupon {

    // ENUM

    /**
     * Enum for coupon category options
     */
    public enum Category {
        Food, Electricity, Restaurant, Vacations, Concert, Movie, Computers;
    }

    // FIELDS

    private int couponID; // takes the Coupon Id
    private int companyID; // takes the company Id related to the coupon in the Company Object
    private Category category; // takes the coupon Category
    private String title; // takes the coupon head title
    private String description; // takes the coupon description
    private LocalDateTime startDate; // takes the coupon activation date (Start date)
    private LocalDateTime endDate; // takes the coupon expiration date (End date)
    private int amount; // take in the amount of the coupon availability
    private double price; // take in the coupon price
    private String image; // will be used later, currently takes in a random String

    // C'TOR

    /**
     * this CTOR will builds the an instance of the coupon class
     * default couponID is 0, will auto increment once inserted into the DB and will received back
     * @param companyID takes in the companyID for a Company bean
     * @param category takes enum for categories of the coupon
     * @param title takes the title of the coupon
     * @param description takes the description of the coupon
     * @param startDate takes the start activation date of the coupon
     * @param endDate takes the end of life date of the coupon
     * @param amount takes the of amount to each coupon has
     * @param price takes the price of the coupon
     * @param image string for information of image .. will be added later
     */
    public Coupon(int companyID, Category category, String title, String description, LocalDateTime startDate, LocalDateTime endDate, int amount, double price, String image) {
        this.couponID = 0;  // default couponID is 0, will auto increment once inserted into the DB and will received back
        this.companyID = companyID;
        this.category = category;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }

    /**
     * no arg C'TOR, for empty object creation
     */
    public Coupon() {

    }

    // METHODS

    /**
     * returns the object field 'couponId'
     * @return int couponId
     */
    public int getCouponID() {
        return couponID;
    }

    /**
     * sets the object field `couponId`
     * @param couponID receive an int `couponId`
     */
    public void setCouponID(int couponID) {
        this.couponID = couponID;
    }

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
     * returns the object enum 'category'
     * @return enum category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * sets the object enum `category`
     * @param category receive an enum `category`
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * returns the object field 'title'
     * @return String companyID
     */
    public String getTitle() {
        return title;
    }

    /**
     * sets the object field `title`
     * @param title receive an int `title`
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * returns the object field 'description'
     * @return String description
     */
    public String getDescription() {
        return description;
    }

    /**
     * sets the object field `description`
     * @param description receive an int `description`
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * returns the object field 'startDate'
     * @return LocalDateTime startDate
     */
    public LocalDateTime getStartDate() {
        return startDate;
    }

    /**
     * sets the object field `startDate`
     * @param startDate receive an LocalDateTime `startDate`
     */
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    /**
     * returns the object field 'endDate'
     * @return LocalDateTime endDate
     */
    public LocalDateTime getEndDate() {
        return endDate;
    }

    /**
     * sets the object field `endDate`
     * @param endDate receive an LocalDateTime `endDate`
     */
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    /**
     * returns the object field 'amount'
     * @return int amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * sets the object field `amount`
     * @param amount receive an int `amount`
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * returns the object field 'price'
     * @return double price
     */
    public double getPrice() {
        return price;
    }

    /**
     * sets the object field `price`
     * @param price receive a double `price`
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * returns the object field 'image'
     * @return String image
     */
    public String getImage() {
        return image;
    }

    /**
     * sets the object field `image`
     * @param image receive an int `image`
     */
    public void setImage(String image) {
        this.image = image;
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
        Coupon coupon = (Coupon) o;
        return couponID == coupon.couponID && companyID == coupon.companyID && amount == coupon.amount && Double.compare(coupon.price, price) == 0 && category == coupon.category && Objects.equals(title, coupon.title) && Objects.equals(description, coupon.description) && Objects.equals(startDate, coupon.startDate) && Objects.equals(endDate, coupon.endDate) && Objects.equals(image, coupon.image);
    }

    /**
     * Uses a default hash code to generate and return a unique int number of an object
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(couponID, companyID, category, title, description, startDate, endDate, amount, price, image);
    }

    /**
     * format a sting of all object details
     * @return Sting
     */
    @Override
    public String toString() {
        return "\nCoupon{" +
                "couponID=" + couponID +
                ", companyID=" + companyID +
                ", category=" + category +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", amount=" + amount +
                ", price=" + price +
                ", image='" + image + '\'' +
                '}';
    }
}
