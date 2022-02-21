package Execptions;

/**
 * this Exception is for when the coupon/user doesn't meet the requirements for purchase
 */
public class RestrictCouponPurchaseException extends Exception {

    public RestrictCouponPurchaseException() {
        super("Cannot Purchase Coupon, Not enough amount / Already been purchase / Coupon expired");
    }

    public RestrictCouponPurchaseException(String message) {
        super(message);
    }
}
