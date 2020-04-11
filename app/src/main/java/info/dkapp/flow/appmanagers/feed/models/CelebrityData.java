package info.dkapp.flow.appmanagers.feed.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 28-08-2018.
 **/

public class CelebrityData implements Parcelable
{

    @SerializedName("isFan")
    @Expose
    public Boolean isFan;
    @SerializedName("isFollower")
    @Expose
    public Boolean isFollower;
    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("username")
    @Expose
    public String username;
    @SerializedName("isCeleb")
    @Expose
    public Boolean isCeleb;
    @SerializedName("isOnline")
    @Expose
    public Boolean isOnline;
    @SerializedName("lastName")
    @Expose
    public String lastName;
    @SerializedName("firstName")
    @Expose
    public String firstName;
    @SerializedName("imageRatio")
    @Expose
    public String imageRatio;
    @SerializedName("avtar_imgPath")
    @Expose
    public String avatarImgPath;
    @SerializedName("aboutMe")
    @Expose
    public String aboutMe;
    @SerializedName("profession")
    @Expose
    public String profession;
    @SerializedName("role")
    @Expose
    public String role;

    @SerializedName("custom_imgPath")
    @Expose
    public String custom_imgPath;

    protected CelebrityData(Parcel in) {
        byte tmpIsFan = in.readByte();
        isFan = tmpIsFan == 0 ? null : tmpIsFan == 1;
        byte tmpIsFollower = in.readByte();
        isFollower = tmpIsFollower == 0 ? null : tmpIsFollower == 1;
        id = in.readString();
        username = in.readString();
        byte tmpIsCeleb = in.readByte();
        isCeleb = tmpIsCeleb == 0 ? null : tmpIsCeleb == 1;
        byte tmpIsOnline = in.readByte();
        isOnline = tmpIsOnline == 0 ? null : tmpIsOnline == 1;
        lastName = in.readString();
        firstName = in.readString();
        imageRatio = in.readString();
        avatarImgPath = in.readString();
        aboutMe = in.readString();
        profession = in.readString();
        role = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isFan == null ? 0 : isFan ? 1 : 2));
        dest.writeByte((byte) (isFollower == null ? 0 : isFollower ? 1 : 2));
        dest.writeString(id);
        dest.writeString(username);
        dest.writeByte((byte) (isCeleb == null ? 0 : isCeleb ? 1 : 2));
        dest.writeByte((byte) (isOnline == null ? 0 : isOnline ? 1 : 2));
        dest.writeString(lastName);
        dest.writeString(firstName);
        dest.writeString(imageRatio);
        dest.writeString(avatarImgPath);
        dest.writeString(aboutMe);
        dest.writeString(profession);
        dest.writeString(role);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CelebrityData> CREATOR = new Creator<CelebrityData>() {
        @Override
        public CelebrityData createFromParcel(Parcel in) {
            return new CelebrityData(in);
        }

        @Override
        public CelebrityData[] newArray(int size) {
            return new CelebrityData[size];
        }
    };
}
