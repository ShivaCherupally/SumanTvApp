package info.dkapp.flow.celebflow.notificationssettingsapp;

import android.os.Parcel;
import android.os.Parcelable;

public class NotificationStats implements Parcelable {
    public String _id;
    public String memberId;
    public String notificationSettingId;
    public String updatedBy;
    public String createdBy;
    public String updatedAt;
    public String createdAt;
    public boolean isEnabled;

    protected NotificationStats(Parcel in) {
        _id = in.readString();
        memberId = in.readString();
        notificationSettingId = in.readString();
        updatedBy = in.readString();
        createdBy = in.readString();
        updatedAt = in.readString();
        createdAt = in.readString();
        isEnabled = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(memberId);
        dest.writeString(notificationSettingId);
        dest.writeString(updatedBy);
        dest.writeString(createdBy);
        dest.writeString(updatedAt);
        dest.writeString(createdAt);
        dest.writeByte((byte) (isEnabled ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NotificationStats> CREATOR = new Creator<NotificationStats>() {
        @Override
        public NotificationStats createFromParcel(Parcel in) {
            return new NotificationStats(in);
        }

        @Override
        public NotificationStats[] newArray(int size) {
            return new NotificationStats[size];
        }
    };
}
