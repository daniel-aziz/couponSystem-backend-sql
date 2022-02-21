package Execptions;

/**
 * this Exception is for when the coupon already exist in the system
 */
public class CouponAlreadyExistsException extends Exception {

    public CouponAlreadyExistsException() {
        super("Coupon Already Exists for this Company");
    }

    public CouponAlreadyExistsException(String message) {
        super(message);
    }
}
