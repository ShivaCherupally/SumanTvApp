package info.dkapp.flow.bottommenu.menuitemsmanager.modelclasses;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Uday Kumay Vurukonda on 20-Jun-19.
 * <p>
 * Mr.Psycho
 */
public class CalenderSlot implements Parcelable
{

    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("memberId")
    @Expose
    public String memberId;
    @SerializedName("startTime")
    @Expose
    public String startTime;
    @SerializedName("endTime")
    @Expose
    public String endTime;
    @SerializedName("updatedAt")
    @Expose
    public String updatedAt;
    @SerializedName("createdAt")
    @Expose
    public String createdAt;
    @SerializedName("slotArray")
    @Expose
    public List<ScheduleArray> slotArray = null;
    @SerializedName("serviceType")
    @Expose
    public String  serviceType = null;
    @SerializedName("scheduleStatus")
    @Expose
    public String scheduleStatus;
    @SerializedName("isScheduled")
    @Expose
    public Boolean isScheduled;
    @SerializedName("isDeleted")
    @Expose
    public Boolean isDeleted;
    @SerializedName("updatedBy")
    @Expose
    public String updatedBy;
    @SerializedName("createdBy")
    @Expose
    public String createdBy;
    @SerializedName("creditValue")
    @Expose
    public Integer creditValue;
    @SerializedName("scheduleDuration")
    @Expose
    public Integer scheduleDuration;
    @SerializedName("breakDuration")
    @Expose
    public Integer breakDuration;
    @SerializedName("previousScheduleEndTime")
    @Expose
    public Object previousScheduleEndTime;

    public Boolean isItemSelected;

    protected CalenderSlot(Parcel in) {
        id = in.readString();
        memberId = in.readString();
        startTime = in.readString();
        endTime = in.readString();
        updatedAt = in.readString();
        createdAt = in.readString();
        slotArray = in.createTypedArrayList(ScheduleArray.CREATOR);
        serviceType = in.readString();
        scheduleStatus = in.readString();
        byte tmpIsScheduled = in.readByte();
        isScheduled = tmpIsScheduled == 0 ? null : tmpIsScheduled == 1;
        byte tmpIsDeleted = in.readByte();
        isDeleted = tmpIsDeleted == 0 ? null : tmpIsDeleted == 1;
        updatedBy = in.readString();
        createdBy = in.readString();
        if (in.readByte() == 0) {
            creditValue = null;
        } else {
            creditValue = in.readInt();
        }
        if (in.readByte() == 0) {
            scheduleDuration = null;
        } else {
            scheduleDuration = in.readInt();
        }
        if (in.readByte() == 0) {
            breakDuration = null;
        } else {
            breakDuration = in.readInt();
        }
        byte tmpIsItemSelected = in.readByte();
        isItemSelected = tmpIsItemSelected == 0 ? null : tmpIsItemSelected == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(memberId);
        dest.writeString(startTime);
        dest.writeString(endTime);
        dest.writeString(updatedAt);
        dest.writeString(createdAt);
        dest.writeTypedList(slotArray);
        dest.writeString(serviceType);
        dest.writeString(scheduleStatus);
        dest.writeByte((byte) (isScheduled == null ? 0 : isScheduled ? 1 : 2));
        dest.writeByte((byte) (isDeleted == null ? 0 : isDeleted ? 1 : 2));
        dest.writeString(updatedBy);
        dest.writeString(createdBy);
        if (creditValue == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(creditValue);
        }
        if (scheduleDuration == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(scheduleDuration);
        }
        if (breakDuration == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(breakDuration);
        }
        dest.writeByte((byte) (isItemSelected == null ? 0 : isItemSelected ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CalenderSlot> CREATOR = new Creator<CalenderSlot>() {
        @Override
        public CalenderSlot createFromParcel(Parcel in) {
            return new CalenderSlot(in);
        }

        @Override
        public CalenderSlot[] newArray(int size) {
            return new CalenderSlot[size];
        }
    };
}
