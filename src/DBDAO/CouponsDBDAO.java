package DBDAO;


import Beans.Coupon;
import DAO.CouponsDAO;
import SQL.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * this class defines the SQL Query and the exaction related to the coupons and customers_vs_coupons tables in the DB
 */
public class CouponsDBDAO implements CouponsDAO {
    // ADD_COUPON insert a row into the coupons table (without id)
    private static final String ADD_COUPON = "INSERT INTO `couponDB`.`coupons` " +
            "(`COMPANY_ID`,`CATEGORY_ID`,`TITLE`,`DESCRIPTION`,`START_DATE`,`END_DATE`,`AMOUNT`,`PRICE`,`IMAGE`) " +
            "VALUES (?,?,?,?,?,?,?,?,?)";
    // UPDATE_COUPON update a row in the coupons table by id (cannot update `couponId` and `companyId` )
    private static final String UPDATE_COUPON = "UPDATE `couponDB`.`coupons` SET" +
            "`CATEGORY_ID`=?,`TITLE`=?,`DESCRIPTION`=?,`START_DATE`=?,`END_DATE`=?,`AMOUNT`=?,`PRICE`=?,`IMAGE`=? " +
            "WHERE ID=?";
    // GET_COUPON_BY_ID return a row from coupons table by `id`
    private static final String GET_COUPON_BY_ID = "SELECT * FROM `couponDB`.`coupons` WHERE ID=?";
    // DELETE_COUPON deletes a row in the coupons table by `id`
    private static final String DELETE_COUPON = "DELETE FROM `couponDB`.`coupons` WHERE ID=?";
    // GET_ALL_COUPONS return all rows in the coupons table
    private static final String GET_ALL_COUPONS = "SELECT * FROM `couponDB`.`coupons` ORDER BY `ID`";
    // ADD_COUPON_PURCHASE insert a row in the customers_vs_coupons table
    private static final String ADD_COUPON_PURCHASE = "INSERT INTO `couponDB`.`customers_vs_coupons` (`CUSTOMER_ID`,`COUPON_ID`) VALUES (?,?)";
    // DELETE_COUPON_PURCHASE delete a row in the customers_vs_coupons table
    private static final String DELETE_COUPON_PURCHASE = "DELETE FROM `couponDB`.`customers_vs_coupons` WHERE `CUSTOMER_ID`=? AND `COUPON_ID`=?";
    // GET_ALL_COUPONS_OF_COMPANY get all company coupons by companyId
    private static final String GET_ALL_COUPONS_OF_COMPANY = "SELECT * FROM `couponDB`.`coupons` WHERE `COMPANY_ID`=? ORDER BY `ID`";
    // GET_ALL_COUPONS_OF_COMPANY_AND_CATEGORY get all company coupons by companyId and category
    private static final String GET_ALL_COUPONS_OF_COMPANY_AND_CATEGORY = "SELECT * FROM `couponDB`.`coupons` WHERE `COMPANY_ID`=? AND `CATEGORY_ID`=? ORDER BY `ID`";
    // GET_ALL_COUPONS_OF_COMPANY_BY_MAX_PRICE get all company coupons by companyId and maxPrice
    private static final String GET_ALL_COUPONS_OF_COMPANY_BY_MAX_PRICE = "SELECT * FROM `couponDB`.`coupons` WHERE `COMPANY_ID`=? AND `PRICE` <= ? ORDER BY `PRICE`";
    // DELETE_EXPIRED_COUPONS deletes all coupons by endDate
    private static final String DELETE_EXPIRED_COUPONS = "DELETE FROM `couponDB`.`coupons` WHERE `END_DATE` < CURRENT_TIMESTAMP";
    // IS_EXIST returns a row from coupons table by companyId and title fields
    private static final String IS_COUPON_EXISTS = "SELECT * FROM `couponDB`.`coupons` WHERE `COMPANY_ID`=? AND `TITLE`=?";
    // IS_COUPON_PURCHASED returns a row returns a row from customers_vs_coupons table by customerId and title couponId
    private static final String IS_COUPON_PURCHASED = "SELECT * FROM `couponDB`.`customers_vs_coupons` WHERE `CUSTOMER_ID`=? AND `COUPON_ID`=?";
    // GET_ALL_COUPONS_OF_CUSTOMER gets all coupons of customer by customerId
    private static final String GET_ALL_COUPONS_OF_CUSTOMER = "SELECT `couponDB`.`coupons`.* FROM `couponDB`.`coupons` " +
            "JOIN `couponDB`.`customers_vs_coupons` ON (`couponDB`.`coupons`.`id` = `couponDB`.`customers_vs_coupons`.`COUPON_ID`) " +
            "WHERE `couponDB`.`customers_vs_coupons`.`CUSTOMER_ID` = ?";
    // GET_ALL_COUPONS_OF_CUSTOMER_AND_CATEGORY gets all coupons of customer by customerId and Category
    private static final String GET_ALL_COUPONS_OF_CUSTOMER_AND_CATEGORY = "SELECT `couponDB`.`coupons`.* FROM `couponDB`.`coupons` " +
            "JOIN `couponDB`.`customers_vs_coupons` ON (`couponDB`.`coupons`.`id` = `couponDB`.`customers_vs_coupons`.`COUPON_ID`) " +
            "WHERE `couponDB`.`customers_vs_coupons`.`CUSTOMER_ID` = ? AND `couponDB`.`coupons`.`CATEGORY_ID` = ?";
    // GET_ALL_COUPONS_OF_CUSTOMER_AND_CATEGORY gets all coupons of customer by customerId and maxPrice
    private static final String GET_ALL_COUPONS_OF_CUSTOMER_BY_MAX_PRICE = "SELECT `couponDB`.`coupons`.* FROM `couponDB`.`coupons` " +
            "JOIN `couponDB`.`customers_vs_coupons` ON (`couponDB`.`coupons`.`id` = `couponDB`.`customers_vs_coupons`.`COUPON_ID`) " +
            "WHERE `couponDB`.`customers_vs_coupons`.`CUSTOMER_ID` = ? AND `couponDB`.`coupons`.`Price` <= ?";

    // a connection object to the class
    private Connection connection;

    /**
     * execute the Query to the DB `ADD_COUPON`
     *
     * @param coupon receives the a coupon object
     * @throws SQLException if failed to return connection back to ConnectionPool
     */
    @Override
    public void addCoupon(Coupon coupon) throws SQLException {
        Timestamp timestampStart = Timestamp.valueOf(coupon.getStartDate());
        Timestamp timestampEnd = Timestamp.valueOf(coupon.getEndDate());
        try {
            // gets connection from the connection pool
            connection = ConnectionPool.getInstance().getConnection();
            // prepare statement and execute
            PreparedStatement statement = connection.prepareStatement(ADD_COUPON);
            statement.setInt(1, coupon.getCompanyID());
            statement.setInt(2, coupon.getCategory().ordinal());
            statement.setString(3, coupon.getTitle());
            statement.setString(4, coupon.getDescription());
            statement.setTimestamp(5, timestampStart);
            statement.setTimestamp(6, timestampEnd);
            statement.setInt(7, coupon.getAmount());
            statement.setDouble(8, coupon.getPrice());
            statement.setString(9, coupon.getImage());
            statement.execute();
        } catch (SQLException | InterruptedException err) {
            System.out.println(err.getMessage());
        } finally {
            // return a connection back to the connection pool
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    /**
     * execute the Query to the DB `UPDATE_COUPON`
     *
     * @param coupon receives the a coupon object
     * @throws SQLException if failed to return connection back to ConnectionPool
     */
    @Override
    public void updateCoupon(Coupon coupon) throws SQLException {
        Timestamp timestampStart = Timestamp.valueOf(coupon.getStartDate());
        Timestamp timestampEnd = Timestamp.valueOf(coupon.getEndDate());
        try {
            // gets connection from the connection pool
            connection = ConnectionPool.getInstance().getConnection();
            // prepare statement and execute
            PreparedStatement statement = connection.prepareStatement(UPDATE_COUPON);
            statement.setInt(1, coupon.getCategory().ordinal());
            statement.setString(2, coupon.getTitle());
            statement.setString(3, coupon.getDescription());
            statement.setTimestamp(4, timestampStart);
            statement.setTimestamp(5, timestampEnd);
            statement.setInt(6, coupon.getAmount());
            statement.setDouble(7, coupon.getPrice());
            statement.setString(8, coupon.getImage());
            statement.setInt(9, coupon.getCouponID());
            statement.execute();
        } catch (SQLException | InterruptedException err) {
            System.out.println(err.getMessage());
        } finally {
            // return a connection back to the connection pool
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    /**
     * execute the Query to the DB `DELETE_COUPON`
     *
     * @param couponID receives the a coupon bean field `couponID`
     * @throws SQLException if failed to return connection back to ConnectionPool
     */
    @Override
    public void deleteCoupon(int couponID) throws SQLException {
        try {
            // gets connection from the connection pool
            connection = ConnectionPool.getInstance().getConnection();
            // prepare statement and execute
            PreparedStatement statement = connection.prepareStatement(DELETE_COUPON);
            statement.setInt(1, couponID);
            statement.execute();
        } catch (SQLException | InterruptedException err) {
            System.out.println(err.getMessage());
        } finally {
            // return a connection back to the connection pool
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    /**
     * execute the Query to the DB `GET_ALL_COUPONS` and returns a List of all coupons
     *
     * @return ArrayList of coupons
     * @throws SQLException if failed to return connection back to ConnectionPool
     */
    @Override
    public ArrayList<Coupon> getAllCoupons() throws SQLException {
        List<Coupon> coupons = new ArrayList<>();
        try {
            // gets connection from the connection pool
            connection = ConnectionPool.getInstance().getConnection();
            // prepare statement and execute
            PreparedStatement statement = connection.prepareStatement(GET_ALL_COUPONS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Coupon coupon = new Coupon();
                coupon.setCouponID(resultSet.getInt("ID"));
                coupon.setCompanyID(resultSet.getInt("COMPANY_ID"));
                int ordinal = resultSet.getInt("CATEGORY_ID");
                coupon.setCategory(Coupon.Category.values()[ordinal]);
                coupon.setTitle(resultSet.getString("TITLE"));
                coupon.setDescription(resultSet.getString("DESCRIPTION"));
                Timestamp timestampStart = resultSet.getTimestamp("START_DATE");
                Timestamp timestampEnd = resultSet.getTimestamp("END_DATE");
                coupon.setStartDate(timestampStart.toLocalDateTime());
                coupon.setEndDate(timestampEnd.toLocalDateTime());
                coupon.setAmount(resultSet.getInt("AMOUNT"));
                coupon.setPrice(resultSet.getDouble("PRICE"));
                coupon.setImage(resultSet.getString("IMAGE"));
                coupons.add(coupon);
            }
        } catch (SQLException | InterruptedException err) {
            System.out.println(err.getMessage());
        } finally {
            // return a connection back to the connection pool
            ConnectionPool.getInstance().returnConnection(connection);
        }

        return (ArrayList<Coupon>) coupons;
    }

    /**
     * execute the Query to the DB `GET_COUPON_BY_ID` and returns an object of the required coupon
     *
     * @param couponID receives the a coupon bean field `couponID`
     * @return Coupon object
     * @throws SQLException if failed to return connection back to ConnectionPool
     */
    @Override
    public Coupon getOneCoupon(int couponID) throws SQLException {
        Coupon coupon = new Coupon();
        try {
            // gets connection from the connection pool
            connection = ConnectionPool.getInstance().getConnection();
            // prepare statement and execute
            PreparedStatement statement = connection.prepareStatement(GET_COUPON_BY_ID);
            statement.setInt(1, couponID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                coupon.setCouponID(resultSet.getInt("ID"));
                coupon.setCompanyID(resultSet.getInt("COMPANY_ID"));
                int ordinal = resultSet.getInt("CATEGORY_ID");
                coupon.setCategory(Coupon.Category.values()[ordinal]);
                coupon.setTitle(resultSet.getString("TITLE"));
                coupon.setDescription(resultSet.getString("DESCRIPTION"));
                Timestamp timestampStart = resultSet.getTimestamp("START_DATE");
                Timestamp timestampEnd = resultSet.getTimestamp("END_DATE");
                coupon.setStartDate(timestampStart.toLocalDateTime());
                coupon.setEndDate(timestampEnd.toLocalDateTime());
                coupon.setAmount(resultSet.getInt("AMOUNT"));
                coupon.setPrice(resultSet.getDouble("PRICE"));
                coupon.setImage(resultSet.getString("IMAGE"));
            }
        } catch (SQLException | InterruptedException err) {
            System.out.println(err.getMessage());
        } finally {
            // return a connection back to the connection pool
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return coupon;
    }

    /**
     * execute the query `ADD_COUPON_PURCHASE` to add a row to the table customers_vs_coupons
     *
     * @param customerID receives the a customer bean field `customerID`
     * @param couponID   receives the a coupon bean field `couponID`
     * @throws SQLException if failed to return connection back to ConnectionPool
     */
    @Override
    public void addCouponPurchase(int customerID, int couponID) throws SQLException {
        try {
            // gets connection from the connection pool
            connection = ConnectionPool.getInstance().getConnection();
            // prepare statement and execute
            PreparedStatement statement = connection.prepareStatement(ADD_COUPON_PURCHASE);
            statement.setInt(1, customerID);
            statement.setInt(2, couponID);
            statement.execute();
        } catch (SQLException | InterruptedException err) {
            System.out.println(err.getMessage());
        } finally {
            // return a connection back to the connection pool
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    /**
     * execute the query `DELETE_COUPON_PURCHASE` to delete a row from the table customers_vs_coupons
     *
     * @param customerID receives the a customer bean field `customerID`
     * @param couponID receives the a coupon bean field `couponID`
     * @throws SQLException if failed to return connection back to ConnectionPool
     */
    @Override
    public void deleteCouponPurchase(int customerID, int couponID) throws SQLException {
        try {
            // gets connection from the connection pool
            connection = ConnectionPool.getInstance().getConnection();
            // prepare statement and execute
            PreparedStatement statement = connection.prepareStatement(DELETE_COUPON_PURCHASE);
            statement.setInt(1, customerID);
            statement.setInt(2, couponID);
            statement.execute();
        } catch (SQLException | InterruptedException err) {
            System.out.println(err.getMessage());
        } finally {
            // return a connection back to the connection pool
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    /**
     * execute the query `GET_ALL_COUPONS_OF_COMPANY`and returns a list of coupons of the required company
     * @param companyID receives the a company bean field `companyID`
     * @return ArrayList of coupons
     * @throws SQLException if failed to return connection back to ConnectionPool
     */
    @Override
    public ArrayList<Coupon> getCouponsOfCompany(int companyID) throws SQLException {
        List<Coupon> coupons = new ArrayList<>();
        try {
            // gets connection from the connection pool
            connection = ConnectionPool.getInstance().getConnection();
            // prepare statement and execute
            PreparedStatement statement = connection.prepareStatement(GET_ALL_COUPONS_OF_COMPANY);
            statement.setInt(1, companyID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Coupon coupon = new Coupon();
                coupon.setCouponID(resultSet.getInt("ID"));
                coupon.setCompanyID(resultSet.getInt("COMPANY_ID"));
                int ordinal = resultSet.getInt("CATEGORY_ID");
                coupon.setCategory(Coupon.Category.values()[ordinal]);
                coupon.setTitle(resultSet.getString("TITLE"));
                coupon.setDescription(resultSet.getString("DESCRIPTION"));
                Timestamp timestampStart = resultSet.getTimestamp("START_DATE");
                Timestamp timestampEnd = resultSet.getTimestamp("END_DATE");
                coupon.setStartDate(timestampStart.toLocalDateTime());
                coupon.setEndDate(timestampEnd.toLocalDateTime());
                coupon.setAmount(resultSet.getInt("AMOUNT"));
                coupon.setPrice(resultSet.getDouble("PRICE"));
                coupon.setImage(resultSet.getString("IMAGE"));
                coupons.add(coupon);
            }
        } catch (SQLException | InterruptedException err) {
            System.out.println(err.getMessage());
        } finally {
            // return a connection back to the connection pool
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return (ArrayList<Coupon>) coupons;
    }

    /**
     * execute the query `GET_ALL_COUPONS_OF_COMPANY_AND_CATEGORY`and returns a list of coupons of the required company
     * @param companyID receives the a company bean field `companyID`
     * @param category receives the a coupon bean enum `category`
     * @return ArrayList of coupons
     * @throws SQLException if failed to return connection back to ConnectionPool
     */
    @Override
    public ArrayList<Coupon> getCouponsOfCompany(int companyID, Coupon.Category category) throws SQLException {
        List<Coupon> coupons = new ArrayList<>();
        try {
            // gets connection from the connection pool
            connection = ConnectionPool.getInstance().getConnection();
            // prepare statement and execute
            PreparedStatement statement = connection.prepareStatement(GET_ALL_COUPONS_OF_COMPANY_AND_CATEGORY);
            statement.setInt(1, companyID);
            statement.setInt(2, category.ordinal());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Coupon coupon = new Coupon();
                coupon.setCouponID(resultSet.getInt("ID"));
                coupon.setCompanyID(resultSet.getInt("COMPANY_ID"));
                int ordinal = resultSet.getInt("CATEGORY_ID");
                coupon.setCategory(Coupon.Category.values()[ordinal]);
                coupon.setTitle(resultSet.getString("TITLE"));
                coupon.setDescription(resultSet.getString("DESCRIPTION"));
                Timestamp timestampStart = resultSet.getTimestamp("START_DATE");
                Timestamp timestampEnd = resultSet.getTimestamp("END_DATE");
                coupon.setStartDate(timestampStart.toLocalDateTime());
                coupon.setEndDate(timestampEnd.toLocalDateTime());
                coupon.setAmount(resultSet.getInt("AMOUNT"));
                coupon.setPrice(resultSet.getDouble("PRICE"));
                coupon.setImage(resultSet.getString("IMAGE"));
                coupons.add(coupon);
            }
        } catch (SQLException | InterruptedException err) {
            System.out.println(err.getMessage());
        } finally {
            // return a connection back to the connection pool
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return (ArrayList<Coupon>) coupons;
    }

    /**
     * execute the query `GET_ALL_COUPONS_OF_COMPANY_BY_MAX_PRICE`and returns a list of coupons of the required company
     * @param companyID receives the a company bean field `companyID`
     * @param maxPrice receives the a coupon bean field `maxPrice`
     * @return ArrayList of coupons
     * @throws SQLException if failed to return connection back to ConnectionPool
     */
    @Override
    public ArrayList<Coupon> getCouponsOfCompany(int companyID, double maxPrice) throws SQLException {
        List<Coupon> coupons = new ArrayList<>();
        try {
            // gets connection from the connection pool
            connection = ConnectionPool.getInstance().getConnection();
            // prepare statement and execute
            PreparedStatement statement = connection.prepareStatement(GET_ALL_COUPONS_OF_COMPANY_BY_MAX_PRICE);
            statement.setInt(1, companyID);
            statement.setDouble(2, maxPrice);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Coupon coupon = new Coupon();
                coupon.setCouponID(resultSet.getInt("ID"));
                coupon.setCompanyID(resultSet.getInt("COMPANY_ID"));
                int ordinal = resultSet.getInt("CATEGORY_ID");
                coupon.setCategory(Coupon.Category.values()[ordinal]);
                coupon.setTitle(resultSet.getString("TITLE"));
                coupon.setDescription(resultSet.getString("DESCRIPTION"));
                Timestamp timestampStart = resultSet.getTimestamp("START_DATE");
                Timestamp timestampEnd = resultSet.getTimestamp("END_DATE");
                coupon.setStartDate(timestampStart.toLocalDateTime());
                coupon.setEndDate(timestampEnd.toLocalDateTime());
                coupon.setAmount(resultSet.getInt("AMOUNT"));
                coupon.setPrice(resultSet.getDouble("PRICE"));
                coupon.setImage(resultSet.getString("IMAGE"));
                coupons.add(coupon);
            }
        } catch (SQLException | InterruptedException err) {
            System.out.println(err.getMessage());
        } finally {
            // return a connection back to the connection pool
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return (ArrayList<Coupon>) coupons;
    }

    /**
     * execute the query `DELETE_EXPIRED_COUPONS` deletes coupon by endDate and CURRENT_TIMESTAMP
     * @throws SQLException if failed to return connection back to ConnectionPool
     */
    @Override
    public void deleteExpiredCoupons() throws SQLException {
        try {
            // gets connection from the connection pool
            connection = ConnectionPool.getInstance().getConnection();
            // prepare statement and execute
            PreparedStatement statement = connection.prepareStatement(DELETE_EXPIRED_COUPONS);
            statement.execute();
        } catch (SQLException | InterruptedException err) {
            System.out.println(err.getMessage());
        } finally {
            // return a connection back to the connection pool
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    /**
     * execute the Query to the DB `IS_COUPON_EXISTS` and return a boolean answer
     * @param companyId receives an coupon bean field companyId
     * @param title receives an company bean field title
     * @return boolean answer
     * @throws SQLException if failed to return connection back to ConnectionPool
     */
    @Override
    public boolean isCouponExists(int companyId, String title) throws SQLException {
        boolean isExist = false;
        try {
            // gets connection from the connection pool
            connection = ConnectionPool.getInstance().getConnection();
            // prepare statement and execute
            PreparedStatement statement = connection.prepareStatement(IS_COUPON_EXISTS);
            statement.setInt(1, companyId);
            statement.setString(2, title);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                isExist = resultSet.getObject(1, Boolean.class);
            }
        } catch (InterruptedException | SQLException err) {
            System.out.println(err.getMessage());
        } finally {
            // return a connection back to the connection pool
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return isExist;
    }

    /**
     * execute the Query to the DB `IS_COUPON_PURCHASED` and return a boolean answer
     * @param customerId receives an customer bean field customerId
     * @param couponId receives an coupon bean field couponId
     * @return a boolean answer
     * @throws SQLException if failed to return connection back to ConnectionPool
     */
    @Override
    public boolean isCouponPurchased(int customerId, int couponId) throws SQLException {
        boolean isExist = false;
        try {
            // gets connection from the connection pool
            connection = ConnectionPool.getInstance().getConnection();
            // prepare statement and execute
            PreparedStatement statement = connection.prepareStatement(IS_COUPON_PURCHASED);
            statement.setInt(1, customerId);
            statement.setInt(2, couponId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                isExist = resultSet.getObject(1, Boolean.class);
            }
        } catch (InterruptedException | SQLException err) {
            System.out.println(err.getMessage());
        } finally {
            // return a connection back to the connection pool
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return isExist;
    }

    /**
     * execute the query `GET_ALL_COUPONS_OF_CUSTOMER`and returns a list of coupons of the required customer
     * @param customerID receives the a customer bean field `companyID`
     * @return ArrayList of coupons
     * @throws SQLException if failed to return connection back to ConnectionPool
     */
    @Override
    public ArrayList<Coupon> getCouponsOfCustomer(int customerID) throws SQLException {
        List<Coupon> coupons = new ArrayList<>();
        try {
            // gets connection from the connection pool
            connection = ConnectionPool.getInstance().getConnection();
            // prepare statement and execute
            PreparedStatement statement = connection.prepareStatement(GET_ALL_COUPONS_OF_CUSTOMER);
            statement.setInt(1, customerID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Coupon coupon = new Coupon();
                coupon.setCouponID(resultSet.getInt("ID"));
                coupon.setCompanyID(resultSet.getInt("COMPANY_ID"));
                int ordinal = resultSet.getInt("CATEGORY_ID");
                coupon.setCategory(Coupon.Category.values()[ordinal]);
                coupon.setTitle(resultSet.getString("TITLE"));
                coupon.setDescription(resultSet.getString("DESCRIPTION"));
                Timestamp timestampStart = resultSet.getTimestamp("START_DATE");
                Timestamp timestampEnd = resultSet.getTimestamp("END_DATE");
                coupon.setStartDate(timestampStart.toLocalDateTime());
                coupon.setEndDate(timestampEnd.toLocalDateTime());
                coupon.setAmount(resultSet.getInt("AMOUNT"));
                coupon.setPrice(resultSet.getDouble("PRICE"));
                coupon.setImage(resultSet.getString("IMAGE"));
                coupons.add(coupon);
            }
        } catch (SQLException | InterruptedException err) {
            System.out.println(err.getMessage());
        } finally {
            // return a connection back to the connection pool
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return (ArrayList<Coupon>) coupons;
    }

    /**
     * execute the query `GET_ALL_COUPONS_OF_CUSTOMER_AND_CATEGORY`and returns a list of coupons of the required customer
     * @param customerID receives the a customer bean field `customerID`
     * @param category receives the a coupon bean enum `category`
     * @return ArrayList of coupons
     * @throws SQLException if failed to return connection back to ConnectionPool
     */
    @Override
    public ArrayList<Coupon> getCouponsOfCustomer(int customerID, Coupon.Category category) throws SQLException {
        List<Coupon> coupons = new ArrayList<>();
        try {
            // gets connection from the connection pool
            connection = ConnectionPool.getInstance().getConnection();
            // prepare statement and execute
            PreparedStatement statement = connection.prepareStatement(GET_ALL_COUPONS_OF_CUSTOMER_AND_CATEGORY);
            statement.setInt(1, customerID);
            statement.setInt(2, category.ordinal());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Coupon coupon = new Coupon();
                coupon.setCouponID(resultSet.getInt("ID"));
                coupon.setCompanyID(resultSet.getInt("COMPANY_ID"));
                int ordinal = resultSet.getInt("CATEGORY_ID");
                coupon.setCategory(Coupon.Category.values()[ordinal]);
                coupon.setTitle(resultSet.getString("TITLE"));
                coupon.setDescription(resultSet.getString("DESCRIPTION"));
                Timestamp timestampStart = resultSet.getTimestamp("START_DATE");
                Timestamp timestampEnd = resultSet.getTimestamp("END_DATE");
                coupon.setStartDate(timestampStart.toLocalDateTime());
                coupon.setEndDate(timestampEnd.toLocalDateTime());
                coupon.setAmount(resultSet.getInt("AMOUNT"));
                coupon.setPrice(resultSet.getDouble("PRICE"));
                coupon.setImage(resultSet.getString("IMAGE"));
                coupons.add(coupon);
            }
        } catch (SQLException | InterruptedException err) {
            System.out.println(err.getMessage());
        } finally {
            // return a connection back to the connection pool
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return (ArrayList<Coupon>) coupons;
    }

    /**
     * execute the query `GET_ALL_COUPONS_OF_CUSTOMER_BY_MAX_PRICE` and returns a list of coupons of the required customer
     * @param customerID receives the a customer bean field `customerID`
     * @param maxPrice receives the a coupon bean field `maxPrice`
     * @return ArrayList of coupons
     * @throws SQLException if failed to return connection back to ConnectionPool
     */
    @Override
    public ArrayList<Coupon> getCouponsOfCustomer(int customerID, double maxPrice) throws SQLException {
        List<Coupon> coupons = new ArrayList<>();
        try {
            // gets connection from the connection pool
            connection = ConnectionPool.getInstance().getConnection();
            // prepare statement and execute
            PreparedStatement statement = connection.prepareStatement(GET_ALL_COUPONS_OF_CUSTOMER_BY_MAX_PRICE);
            statement.setInt(1, customerID);
            statement.setDouble(2, maxPrice);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Coupon coupon = new Coupon();
                coupon.setCouponID(resultSet.getInt("ID"));
                coupon.setCompanyID(resultSet.getInt("COMPANY_ID"));
                int ordinal = resultSet.getInt("CATEGORY_ID");
                coupon.setCategory(Coupon.Category.values()[ordinal]);
                coupon.setTitle(resultSet.getString("TITLE"));
                coupon.setDescription(resultSet.getString("DESCRIPTION"));
                Timestamp timestampStart = resultSet.getTimestamp("START_DATE");
                Timestamp timestampEnd = resultSet.getTimestamp("END_DATE");
                coupon.setStartDate(timestampStart.toLocalDateTime());
                coupon.setEndDate(timestampEnd.toLocalDateTime());
                coupon.setAmount(resultSet.getInt("AMOUNT"));
                coupon.setPrice(resultSet.getDouble("PRICE"));
                coupon.setImage(resultSet.getString("IMAGE"));
                coupons.add(coupon);
            }
        } catch (SQLException | InterruptedException err) {
            System.out.println(err.getMessage());
        } finally {
            // return a connection back to the connection pool
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return (ArrayList<Coupon>) coupons;
    }
}
