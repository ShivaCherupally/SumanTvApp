package info.sumantv.flow.celebflow.modelData;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Uday Kumay Vurukonda on 22-Jul-19.
 * <p>
 * Mr.Psycho
 */
public class ActivityLog  implements Parcelable
{

    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("memberId")
    @Expose
    public String memberId;
    @SerializedName("activityOn")
    @Expose
    public String activityOn;
    @SerializedName("activityLogTypeId")
    @Expose
    public String activityLogTypeId;
    @SerializedName("mediaId")
    @Expose
    public String mediaId;
    @SerializedName("feedId")
    @Expose
    public String feedId;
    @SerializedName("activityType")
    @Expose
    public String activityType;
    @SerializedName("likeId")
    @Expose
    public String likeId;
    @SerializedName("updatedAt")
    @Expose
    public String updatedAt;
    @SerializedName("createdAt")
    @Expose
    public String createdAt;
    @SerializedName("selfProfile")
    @Expose
    public SelfProfile selfProfile;
    @SerializedName("activityOnProfile")
    @Expose
    public ActivityOnProfile activityOnProfile;
    @SerializedName("activityTypeInfo")
    @Expose
    public ActivityTypeInfo activityTypeInfo;

    protected ActivityLog(Parcel in) {
        id = in.readString();
        memberId = in.readString();
        activityOn = in.readString();
        activityLogTypeId = in.readString();
        mediaId = in.readString();
        feedId = in.readString();
        activityType = in.readString();
        likeId = in.readString();
        updatedAt = in.readString();
        createdAt = in.readString();
        selfProfile = in.readParcelable(SelfProfile.class.getClassLoader());
        activityOnProfile = in.readParcelable(ActivityOnProfile.class.getClassLoader());
        activityTypeInfo = in.readParcelable(ActivityTypeInfo.class.getClassLoader());
    }

    public static final Creator<ActivityLog> CREATOR = new Creator<ActivityLog>() {
        @Override
        public ActivityLog createFromParcel(Parcel in) {
            return new ActivityLog(in);
        }

        @Override
        public ActivityLog[] newArray(int size) {
            return new ActivityLog[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(memberId);
        dest.writeString(activityOn);
        dest.writeString(activityLogTypeId);
        dest.writeString(mediaId);
        dest.writeString(feedId);
        dest.writeString(activityType);
        dest.writeString(likeId);
        dest.writeString(updatedAt);
        dest.writeString(createdAt);
        dest.writeParcelable(selfProfile, flags);
        dest.writeParcelable(activityOnProfile, flags);
        dest.writeParcelable(activityTypeInfo, flags);
    }
}
