package info.dkapp.flow.singup;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Uday Kumay Vurukonda on 24-Apr-19.
 * <p>
 * Mr.Psycho
 */
public class Profile implements Parcelable {

    @SerializedName("username")
    @Expose
    public String username;

    @SerializedName("deviceToken")
    @Expose
    public String deviceToken;

    @SerializedName("timezone")
    @Expose
    public String timezone;

    @SerializedName("osType")
    @Expose
    public String osType;

    @SerializedName("lastLoginLocation")
    @Expose
    public String lastLoginLocation;

    @SerializedName("isUsernameVerified")
    @Expose
    public boolean isUsernameVerified;

    @SerializedName("referralCode")
    @Expose
    public String referralCode;

 /*   @SerializedName("location")
    @Expose
    public JSONObject location;*/


    /*public Profile(String username, boolean isUsernameVerified, JSONObject locationinfo) {
        this.username = username;
        this.isUsernameVerified = isUsernameVerified;
        this.location = locationinfo;
        Log.i("ProfileInfo", toString());
    }*/
    public Profile(String username, boolean isUsernameVerified) {
        this.username = username;
        this.isUsernameVerified = isUsernameVerified;
    }

    public Profile(String username, boolean isUsernameVerified, String referralCode) {
        this.username = username;
        this.isUsernameVerified = isUsernameVerified;
        this.referralCode = referralCode;

    }

    public Profile(String username, boolean isUsernameVerified, String deviceToken, String osType) {
        this.username = username;
        this.isUsernameVerified = isUsernameVerified;
        this.deviceToken = deviceToken;
        this.osType = osType;
    }


    protected Profile(Parcel in) {
        username = in.readString();
        deviceToken = in.readString();
        timezone = in.readString();
        osType = in.readString();
        lastLoginLocation = in.readString();
        isUsernameVerified = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(deviceToken);
        dest.writeString(timezone);
        dest.writeString(osType);
        dest.writeString(lastLoginLocation);
        dest.writeByte((byte) (isUsernameVerified ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Profile> CREATOR = new Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };

    /*@Override
    public String toString() {
        return "Profile{" +
                "username='" + username + '\'' +
                ", isUsernameVerified=" + isUsernameVerified +
                ", location=" + location +
                '}';
    }*/
}
