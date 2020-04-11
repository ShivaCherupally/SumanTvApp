package info.dkapp.flow.menu_list.Settings.NotificationSettings;

import com.google.gson.annotations.SerializedName;

/**
 */

public class NotificationSettingsData {

    @SerializedName("memberId")
    private String memberId;

    @SerializedName("profileUpdates")
    private String profileUpdate;

    @SerializedName("passwordUpdates")
    private String passwordUpdate;

    @SerializedName("paymentUpdates")
    private String paymentUpdate;

    @SerializedName("creditUpdates")
    private String creditUpdates;

    @SerializedName("serviceUpdates")
    private String serviceUpdates;

    @SerializedName("orderUpdates")
    private String orderUpdates;

    @SerializedName("cartUpdates")
    private String cartUpdates;

    @SerializedName("marketingUpdates")
    private String marketingUpdates;

    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationSettingsData(String mMemberId, String mprofileUpdates, String mpasswordUpdates,
                                    String mpaymentUpdates
            , String mcreditUpdates, String mserviceUpdates, String morderUpdates, String mcartUpdates,
                                    String mmarketingUpdates) {
        this.memberId = mMemberId;
        this.profileUpdate = mprofileUpdates;
        this.passwordUpdate = mpasswordUpdates;
        this.paymentUpdate = mpaymentUpdates;
        this.creditUpdates = mcreditUpdates;
        this.serviceUpdates = mserviceUpdates;
        this.orderUpdates = morderUpdates;
        this.cartUpdates = mcartUpdates;
        this.marketingUpdates = mmarketingUpdates;
    }

    /*public NotificationSettingsData(String mprofileUpdates, String mpasswordUpdates, String mpaymentUpdates
            , String mcreditUpdates, String mserviceUpdates, String morderUpdates, String mcartUpdates, String mmarketingUpdates) {
        this.profileUpdate = mprofileUpdates;
        this.passwordUpdate = mpasswordUpdates;
        this.paymentUpdate = mpaymentUpdates;
        this.profileUpdate = mcreditUpdates;
        this.profileUpdate = mserviceUpdates;
        this.profileUpdate = morderUpdates;
        this.profileUpdate = mcartUpdates;
        this.profileUpdate = mmarketingUpdates;
    }*/

//    public NotificationSettingsData(String audio, String video, String chat, String schedules, String orders, String ecommerce, String content, String cart) {
//        this.audio = audio;
//        this.video = video;
//        this.chat = chat;
//        this.schedules = schedules;
//        this.orders = orders;
//        this.ecommerce = ecommerce;
//        this.content = content;
//        this.cart = cart;
//    }


    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getProfileUpdate() {
        return profileUpdate;
    }

    public void setProfileUpdate(String profileUpdate) {
        this.profileUpdate = profileUpdate;
    }

    public String getPasswordUpdate() {
        return passwordUpdate;
    }

    public void setPasswordUpdate(String passwordUpdate) {
        this.passwordUpdate = passwordUpdate;
    }

    public String getPaymentUpdate() {
        return paymentUpdate;
    }

    public void setPaymentUpdate(String paymentUpdate) {
        this.paymentUpdate = paymentUpdate;
    }

    public String getCreditUpdates() {
        return creditUpdates;
    }

    public void setCreditUpdates(String creditUpdates) {
        this.creditUpdates = creditUpdates;
    }

    public String getServiceUpdates() {
        return serviceUpdates;
    }

    public void setServiceUpdates(String serviceUpdates) {
        this.serviceUpdates = serviceUpdates;
    }

    public String getOrderUpdates() {
        return orderUpdates;
    }

    public void setOrderUpdates(String orderUpdates) {
        this.orderUpdates = orderUpdates;
    }

    public String getCartUpdates() {
        return cartUpdates;
    }

    public void setCartUpdates(String cartUpdates) {
        this.cartUpdates = cartUpdates;
    }

    public String getMarketingUpdates() {
        return marketingUpdates;
    }

    public void setMarketingUpdates(String marketingUpdates) {
        this.marketingUpdates = marketingUpdates;
    }
}
