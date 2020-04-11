package info.sumantv.flow.bottommenu.menuitemsmanager.modelclasses;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Uday Kumay Vurukonda on 20-Jun-19.
 * <p>
 * Mr.Psycho
 */
public class ScheduleArray  implements Parcelable
{

    @SerializedName("memberId")
    @Expose
    public String memberId;
    @SerializedName("slotStartTime")
    @Expose
    public String slotStartTime;
    @SerializedName("slotEndTime")
    @Expose
    public String slotEndTime;
    @SerializedName("slotStatus")
    @Expose
    public String slotStatus;
    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("createdAt")
    @Expose
    public String createdAt;
    @SerializedName("creditValue")
    @Expose
    public Integer creditValue;
    @SerializedName("slotDuration")
    @Expose
    public Integer slotDuration;
    @SerializedName("serviceType")
    @Expose
    public String  serviceType = null;


    protected ScheduleArray(Parcel in) {
        memberId = in.readString();
        slotStartTime = in.readString();
        slotEndTime = in.readString();
        slotStatus = in.readString();
        id = in.readString();
        createdAt = in.readString();
        if (in.readByte() == 0) {
            creditValue = null;
        } else {
            creditValue = in.readInt();
        }
        if (in.readByte() == 0) {
            slotDuration = null;
        } else {
            slotDuration = in.readInt();
        }
        serviceType = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(memberId);
        dest.writeString(slotStartTime);
        dest.writeString(slotEndTime);
        dest.writeString(slotStatus);
        dest.writeString(id);
        dest.writeString(createdAt);
        if (creditValue == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(creditValue);
        }
        if (slotDuration == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(slotDuration);
        }
        dest.writeString(serviceType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ScheduleArray> CREATOR = new Creator<ScheduleArray>() {
        @Override
        public ScheduleArray createFromParcel(Parcel in) {
            return new ScheduleArray(in);
        }

        @Override
        public ScheduleArray[] newArray(int size) {
            return new ScheduleArray[size];
        }
    };
}
