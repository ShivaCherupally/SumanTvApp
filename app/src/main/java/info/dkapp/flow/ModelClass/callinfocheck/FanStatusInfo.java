package info.dkapp.flow.ModelClass.callinfocheck;

import com.google.gson.annotations.SerializedName;

public class FanStatusInfo {

    @SerializedName("CelebrityId")
    private String celebrityId;


    @SerializedName("isFan")
    private boolean isFan;

    public String getCelebrityId() {
        return celebrityId;
    }

    public void setCelebrityId(String celebrityId) {
        this.celebrityId = celebrityId;
    }

    public boolean isFan() {
        return isFan;
    }

    public void setFan(boolean fan) {
        isFan = fan;
    }
}
