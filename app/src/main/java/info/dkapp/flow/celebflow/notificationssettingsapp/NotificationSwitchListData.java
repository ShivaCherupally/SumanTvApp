package info.dkapp.flow.celebflow.notificationssettingsapp;


import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by Shiva on 7/17/2018.
 */

public class NotificationSwitchListData implements Parcelable {
    public String _id;
    public String notificationType;
    public String notificationName;
    public NotificationStats notificationStats;

    protected NotificationSwitchListData(Parcel in) {
        _id = in.readString();
        notificationType = in.readString();
        notificationName = in.readString();
        notificationStats = in.readParcelable(NotificationStats.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(notificationType);
        dest.writeString(notificationName);
        dest.writeParcelable(notificationStats, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NotificationSwitchListData> CREATOR = new Creator<NotificationSwitchListData>() {
        @Override
        public NotificationSwitchListData createFromParcel(Parcel in) {
            return new NotificationSwitchListData(in);
        }

        @Override
        public NotificationSwitchListData[] newArray(int size) {
            return new NotificationSwitchListData[size];
        }
    };
}
