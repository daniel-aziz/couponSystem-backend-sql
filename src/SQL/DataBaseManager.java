package SQL;

import Beans.Coupon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * this class is the manager for the database, has the defined SQL queries of DB creation, Tables creation, URL of DB, and login details of the DB
 */
public class DataBaseManager {

    /**
     * fields URL, USER_NAME, PASSWORD. Nessery info for connecting to the DataBase
     */
    public static final String URL = "jdbc:mysql://localhost:3306?createDatabaseIfNotExist=FALSE";
    public static final String USER_NAME = "root";
    public static final String PASSWORD = "12345678";

    /**
     * All Fields down, are sql statements to create the DB and tables.
     */
    public static final String CREATE_COUPONDB = "CREATE SCHEMA IF NOT EXISTS `couponDB`";
    public static final String DROP_COUPONDB = "DROP DATABASE `coupondb`";

    /**
     * SQL QUERY that defines the `companies` table
     */
    public static final String CREATE_TABLE_COMPANIES = "CREATE TABLE `couponDB`.`companies` (" +
            "`ID` int NOT NULL AUTO_INCREMENT," +
            "`NAME` varchar(200) DEFAULT NULL," +
            "`EMAIL` varchar(200) DEFAULT NULL," +
            "`PASSWORD` varchar(200) DEFAULT NULL," +
            "PRIMARY KEY (`ID`)," +
            "UNIQUE KEY `EMAIL_UNIQUE` (`EMAIL`)," +
            "UNIQUE KEY `NAME_UNIQUE` (`NAME`)" +
            ");";


    /**
     * SQL QUERY that defines the `customers` table
     */
    public static final String CREATE_TABLE_CUSTOMERS = "CREATE TABLE IF NOT EXISTS `couponDB`.`CUSTOMERS`( " +
            "`ID` INT NOT NULL AUTO_INCREMENT," +
            "`FIRSTNAME` VARCHAR(200)," +
            "`LASTNAME` VARCHAR(200)," +
            "`EMAIL` VARCHAR(200)," +
            "`PASSWORD` varchar(200) DEFAULT NULL," +
            "PRIMARY KEY (`ID`)," +
            "UNIQUE KEY `EMAIL_UNIQUE` (`EMAIL`)" +
            ");" ;


    /**
     * SQL QUERY that defines the `category` table
     */
    public static final String CREATE_TABLE_CATEGORY = "CREATE TABLE IF NOT EXISTS `couponDB`.`categories` (" +
            "`ID` int NOT NULL AUTO_INCREMENT," +
            "`NAME` varchar(200) NOT NULL," +
            "PRIMARY KEY (`ID`)," +
            "UNIQUE KEY `NAME_UNIQUE` (`NAME`)" +
            ");";

    /**
     * SQL QUERY that defines the `coupons` table
     */
    public static final String CREATE_TABLE_COUPONS = "CREATE TABLE IF NOT EXISTS `couponDB`.`coupons` (" +
            "`ID` int NOT NULL AUTO_INCREMENT," +
            "`COMPANY_ID` int DEFAULT NULL," +
            "`CATEGORY_ID` int DEFAULT NULL," +
            "`TITLE` varchar(200) DEFAULT NULL," +
            "`DESCRIPTION` varchar(200) DEFAULT NULL," +
            "`START_DATE` date DEFAULT NULL," +
            "`END_DATE` date DEFAULT NULL," +
            "`AMOUNT` int DEFAULT NULL," +
            "`PRICE` double DEFAULT NULL," +
            "`IMAGE` varchar(200) DEFAULT NULL," +
            "PRIMARY KEY (`ID`)," +
            "KEY `COMPANY_ID_idx` (`COMPANY_ID`)," +
            "KEY `CATEGORY_ID_idx` (`CATEGORY_ID`)," +
            "CONSTRAINT `CATEGORY_ID` FOREIGN KEY (`CATEGORY_ID`) REFERENCES `categories` (`ID`)," +
            "CONSTRAINT `COMPANY_ID` FOREIGN KEY (`COMPANY_ID`) REFERENCES `companies` (`ID`) ON DELETE CASCADE " +
            ");";

    /**
     * SQL QUERY that defines the `customers_vs_coupons` table
     */
    public static final String CREATE_TABLE_CUSTOMERS_VS_COUPONS = "CREATE TABLE IF NOT EXISTS `couponDB`.`customers_vs_coupons` (" +
            "`CUSTOMER_ID` int NOT NULL," +
            "`COUPON_ID` int NOT NULL," +
            "PRIMARY KEY (`CUSTOMER_ID`,`COUPON_ID`)," +
            "KEY `COUPON_ID_idx` (`COUPON_ID`)," +
            "CONSTRAINT `COUPON_ID` FOREIGN KEY (`COUPON_ID`) REFERENCES `coupons` (`ID`) ON DELETE CASCADE," +
            "CONSTRAINT `CUSTOMER_ID` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `customers` (`ID`) ON DELETE CASCADE" +
            ");";





}
