package info.dkapp.flow.menu_list.Settings.ApplicationSettings;

import com.google.gson.annotations.SerializedName;

/**
 */

public class SelectedSettingData {
    @SerializedName("_id")
    private String memberId;

    @SerializedName("nightMode")
    private boolean nightMode;

    @SerializedName("dndDuration")
    private int dndDuration;

    @SerializedName("doNotDisturb")
    private boolean doNotDisturb;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public boolean isNightMode() {
        return nightMode;
    }

    public void setNightMode(boolean nightMode) {
        this.nightMode = nightMode;
    }

    public int getDndDuration() {
        return dndDuration;
    }

    public void setDndDuration(int dndDuration) {
        this.dndDuration = dndDuration;
    }

    public boolean isDoNotDisturb() {
        return doNotDisturb;
    }

    public void setDoNotDisturb(boolean doNotDisturb) {
        this.doNotDisturb = doNotDisturb;
    }
}
