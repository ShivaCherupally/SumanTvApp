package info.sumantv.flow.celebflow.modelData;

import android.os.Parcel;
import android.os.Parcelable;
import info.sumantv.flow.celebflow.Notifications.NotificationsAdapter;
import org.json.JSONArray;

public class NotificationDeleteBottomModel implements Parcelable {
    public String profileImage;
    public String title;
    public String id;
    public JSONArray _idList;
    public int itemPosition;
    public NotificationsAdapter notificationsAdapter;

    public NotificationDeleteBottomModel(){}

    protected NotificationDeleteBottomModel(Parcel in) {
        profileImage = in.readString();
        title = in.readString();
        id = in.readString();
        itemPosition = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(profileImage);
        dest.writeString(title);
        dest.writeString(id);
        dest.writeInt(itemPosition);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NotificationDeleteBottomModel> CREATOR = new Creator<NotificationDeleteBottomModel>() {
        @Override
        public NotificationDeleteBottomModel createFromParcel(Parcel in) {
            return new NotificationDeleteBottomModel(in);
        }

        @Override
        public NotificationDeleteBottomModel[] newArray(int size) {
            return new NotificationDeleteBottomModel[size];
        }
    };
}
