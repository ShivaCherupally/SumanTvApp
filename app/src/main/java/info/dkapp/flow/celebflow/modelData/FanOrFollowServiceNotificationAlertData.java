package info.dkapp.flow.celebflow.modelData;

import com.google.gson.annotations.SerializedName;


/**
 * Created by Shiva on 3/17/2018.
 */

public class FanOrFollowServiceNotificationAlertData {

    @SerializedName("memberId")
    private String memberId;

    @SerializedName("celebrityId")
    private String celebrityId;

    @SerializedName("title")
    private String title;

    @SerializedName("body")
    private String body;

    @SerializedName("notificationType")
    private String notificationType;

    @SerializedName("createdBy")
    private String createdBy;


    @SerializedName("message")
    private String message;

    public String getCelebrityId() {
        return celebrityId;
    }

    public void setCelebrityId(String celebrityId) {
        this.celebrityId = celebrityId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FanOrFollowServiceNotificationAlertData(String memberId, String celebrityId, String title, String body, String notificationType, String createdBy) {
        this.memberId = memberId;
        this.title = title;
        this.body = body;
        this.notificationType = notificationType;
        this.createdBy = createdBy;
        this.celebrityId = celebrityId;
    }

    public FanOrFollowServiceNotificationAlertData(String memberId, String celebrityId,
                                                   String notificationType, String createdBy) {
        this.memberId = memberId;
        this.notificationType = notificationType;
        this.createdBy = createdBy;
        this.celebrityId = celebrityId;
    }
}
