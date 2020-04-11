package info.dkapp.flow.menu_list.Settings.NotificationSettings;

import com.google.gson.annotations.SerializedName;

/**
 */

public class SelectedNotificationSettingsData {
    @SerializedName("_id")
    private String _id;

    @SerializedName("memberId")
    private String memberId;

    @SerializedName("profileUpdates")
    private boolean profileupdate;

    @SerializedName("passwordUpdates")
    private boolean passwordupdate;

    @SerializedName("paymentUpdates")
    private boolean paymentupdate;

    @SerializedName("creditUpdates")
    private boolean creditupdate;

    @SerializedName("serviceUpdates")
    private boolean serviceupdate;

    @SerializedName("orderUpdates")
    private boolean orderupdate;

    @SerializedName("cartUpdates")
    private boolean cartupdate;

    @SerializedName("marketingUpdates")
    private boolean marketing;


    @SerializedName("error")
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    //

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public boolean isProfileupdate() {
        return profileupdate;
    }

    public void setProfileupdate(boolean profileupdate) {
        this.profileupdate = profileupdate;
    }

    public boolean isPasswordupdate() {
        return passwordupdate;
    }

    public void setPasswordupdate(boolean passwordupdate) {
        this.passwordupdate = passwordupdate;
    }

    public boolean isPaymentupdate() {
        return paymentupdate;
    }

    public void setPaymentupdate(boolean paymentupdate) {
        this.paymentupdate = paymentupdate;
    }

    public boolean isCreditupdate() {
        return creditupdate;
    }

    public void setCreditupdate(boolean creditupdate) {
        this.creditupdate = creditupdate;
    }

    public boolean isServiceupdate() {
        return serviceupdate;
    }

    public void setServiceupdate(boolean serviceupdate) {
        this.serviceupdate = serviceupdate;
    }

    public boolean isOrderupdate() {
        return orderupdate;
    }

    public void setOrderupdate(boolean orderupdate) {
        this.orderupdate = orderupdate;
    }

    public boolean isCartupdate() {
        return cartupdate;
    }

    public void setCartupdate(boolean cartupdate) {
        this.cartupdate = cartupdate;
    }

    public boolean isMarketing() {
        return marketing;
    }

    public void setMarketing(boolean marketing) {
        this.marketing = marketing;
    }
}
