package info.dkapp.flow.ModelClass;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Shiva on 3/17/2018.
 */

public class LiveStatusData implements Parcelable {

    @SerializedName("liveStatus")
    @Expose
    private String liveStatus;


    @SerializedName("callInitiateTime")
    private String callInitiateTime;

    @SerializedName("currentTime")
    private String currentTime;


    @SerializedName("isOnline")
    private Boolean isOnline;


    public String getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(String liveStatus) {
        this.liveStatus = liveStatus;
    }

    public String getCallInitiateTime() {
        return callInitiateTime;
    }

    public void setCallInitiateTime(String callInitiateTime) {
        this.callInitiateTime = callInitiateTime;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }

    protected LiveStatusData(Parcel in) {
        liveStatus = in.readString();
        callInitiateTime = in.readString();
        currentTime = in.readString();
        byte tmpIsOnline = in.readByte();
        isOnline = tmpIsOnline == 0 ? null : tmpIsOnline == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(liveStatus);
        dest.writeString(callInitiateTime);
        dest.writeString(currentTime);
        dest.writeByte((byte) (isOnline == null ? 0 : isOnline ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LiveStatusData> CREATOR = new Creator<LiveStatusData>() {
        @Override
        public LiveStatusData createFromParcel(Parcel in) {
            return new LiveStatusData(in);
        }

        @Override
        public LiveStatusData[] newArray(int size) {
            return new LiveStatusData[size];
        }
    };
}
