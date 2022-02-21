package Threads;

import DAO.CouponsDAO;
import DBDAO.CouponsDBDAO;

import java.sql.SQLException;

/**
 * this thread class used for fixed interval of deletion of coupons from the DB system
 */
public class CouponExpirationDailyJob implements Runnable {

    //FIELDS
    private CouponsDAO couponsDAO = new CouponsDBDAO();  // initialize DAO for thread to use
    private Thread dailyJob;
    private final int INTERVAL_SECOND = 5; // sets the thread interval seconds 1-60
    private final int INTERVAL_MINUTE = 1; // sets the thread interval minutes 1-60
    private final int INTERVAL_HOUR = 1; // sets the thread interval hours 1-24
    private boolean quit; // sets the while loop in .run() boolean

    // C'TOR

    /**
     * no arg C'TOR, for empty object creation
     */
    public CouponExpirationDailyJob() {
    }

    // METHODS

    /**
     * returns the object field 'quit'
     * @return boolean quit
     */
    public boolean isQuit() {
        return quit;
    }

    /**
     * sets the object field `quit`
     * @param quit receive an boolean `quit`
     */
    public void setQuit(boolean quit) {
        this.quit = quit;
    }


    /**
     * insert the Runnable object into usable Thread and start the Thread object by .start()
     */
    public void startDailyJob() {
        dailyJob = new Thread(this);
        dailyJob.start();
    }

    /**
     * this method will delete expired coupons by endDate and Current_TIMESTAMP using couponsDAO, will sleep interval by fixed fields choosing using while loop
     */
    @Override
    public void run() {
        while (!quit) {
            try {
                couponsDAO.deleteExpiredCoupons();
            } catch (SQLException err) {
                System.out.println(err.getMessage());
            }
            try {
                Thread.sleep(1000 * INTERVAL_SECOND * INTERVAL_MINUTE * INTERVAL_HOUR);
            } catch (InterruptedException err) {

            }
        }
    }

    /**
     * stops the thread by setting the thread loop to true and interrupting the sleeping thread
     */
    public void stopDailyJob() {
        this.setQuit(true);
        dailyJob.interrupt();
    }





}
