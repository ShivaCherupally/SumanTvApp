package info.dkapp.flow.menu_list.Settings.ApplicationSettings;

import com.google.gson.annotations.SerializedName;

/**
 */

public class AppSettingsData {
    @SerializedName("memberId")
    private String memberId;

    @SerializedName("nightMode")
    private String nightMode;

    @SerializedName("dndDuration")
    private String dndDuration;

    @SerializedName("doNotDisturb")
    private String doNotDisturb;

    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getNightMode() {
        return nightMode;
    }

    public void setNightMode(String nightMode) {
        this.nightMode = nightMode;
    }

    public String getDndDuration() {
        return dndDuration;
    }

    public void setDndDuration(String dndDuration) {
        this.dndDuration = dndDuration;
    }

    public String getDoNotDisturb() {
        return doNotDisturb;
    }

    public void setDoNotDisturb(String doNotDisturb) {
        this.doNotDisturb = doNotDisturb;
    }

    public AppSettingsData(String memberId, String nightMode, String dndDuration, String doNotDisturb) {
        this.memberId = memberId;
        this.nightMode = nightMode;
        this.dndDuration = dndDuration;
        this.doNotDisturb = doNotDisturb;
    }

    public AppSettingsData(String nightMode, String dndDuration, String doNotDisturb) {
        this.nightMode = nightMode;
        this.dndDuration = dndDuration;
        this.doNotDisturb = doNotDisturb;
    }
}
