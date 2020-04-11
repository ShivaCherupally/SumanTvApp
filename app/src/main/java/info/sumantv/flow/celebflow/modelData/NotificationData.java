package info.sumantv.flow.celebflow.modelData;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Uday Kumay Vurukonda on 14-Jun-19.
 * <p>
 * Mr.Psycho
 */
public class NotificationData implements Parcelable {
    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("memberId")
    @Expose
    public String memberId;
    @SerializedName("notificationFrom")
    @Expose
    public NotificationFrom notificationFrom;
    @SerializedName("feedId")
    @Expose
    public String feedId;
    @SerializedName("startTime")
    @Expose
    public String startTime;
    @SerializedName("endTime")
    @Expose
    public String endTime;
    @SerializedName("updatedBy")
    @Expose
    public String updatedBy;
    @SerializedName("createdBy")
    @Expose
    public String createdBy;
    @SerializedName("updatedAt")
    @Expose
    public String updatedAt;
    @SerializedName("createdAt")
    @Expose
    public String createdAt;
    @SerializedName("notificationType")
    @Expose
    public String notificationType;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("notificationIcon")
    @Expose
    public String notificationIcon;
    @SerializedName("body")
    @Expose
    public String body;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("activity")
    @Expose
    public String activity;
    @SerializedName("scheduleId")
    @Expose
    public String scheduleId;
    public Boolean isItemSelected;


    protected NotificationData(Parcel in) {
        id = in.readString();
        memberId = in.readString();
        notificationFrom = in.readParcelable(NotificationFrom.class.getClassLoader());
        feedId = in.readString();
        startTime = in.readString();
        endTime = in.readString();
        updatedBy = in.readString();
        createdBy = in.readString();
        updatedAt = in.readString();
        createdAt = in.readString();
        notificationType = in.readString();
        status = in.readString();
        notificationIcon = in.readString();
        body = in.readString();
        title = in.readString();
        activity = in.readString();
        scheduleId = in.readString();
        byte tmpIsItemSelected = in.readByte();
        isItemSelected = tmpIsItemSelected == 0 ? null : tmpIsItemSelected == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(memberId);
        dest.writeParcelable(notificationFrom, flags);
        dest.writeString(feedId);
        dest.writeString(startTime);
        dest.writeString(endTime);
        dest.writeString(updatedBy);
        dest.writeString(createdBy);
        dest.writeString(updatedAt);
        dest.writeString(createdAt);
        dest.writeString(notificationType);
        dest.writeString(status);
        dest.writeString(notificationIcon);
        dest.writeString(body);
        dest.writeString(title);
        dest.writeString(activity);
        dest.writeString(scheduleId);
        dest.writeByte((byte) (isItemSelected == null ? 0 : isItemSelected ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NotificationData> CREATOR = new Creator<NotificationData>() {
        @Override
        public NotificationData createFromParcel(Parcel in) {
            return new NotificationData(in);
        }

        @Override
        public NotificationData[] newArray(int size) {
            return new NotificationData[size];
        }
    };
}
