package info.sumantv.flow.celebflow.notificationssettingsapp;


import com.google.gson.annotations.SerializedName;

/**
 * Created by Shiva on 7/17/2018.
 */

public class NotificationSaveData  {

    @SerializedName("memberId")
    private String memberId;

    @SerializedName("notificationSettingId")
    private String notificationMasterId;


    @SerializedName("isEnabled")
    private boolean isEnable;

    @SerializedName("message")
    private String message;



    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getNotificationMasterId() {
        return notificationMasterId;
    }

    public void setNotificationMasterId(String notificationMasterId) {
        this.notificationMasterId = notificationMasterId;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationSaveData(String memberId, String notificationMasterId, boolean isEnable) {
        this.memberId = memberId;
        this.notificationMasterId = notificationMasterId;
        this.isEnable = isEnable;
    }
}
