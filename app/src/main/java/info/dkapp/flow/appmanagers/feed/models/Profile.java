package info.dkapp.flow.appmanagers.feed.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 19-08-2018.
 **/

public class Profile implements Parcelable {
    @SerializedName("_id")
    public String id;

    @SerializedName("avtar_imgPath")
    public String profilePic;

    @SerializedName("username")
    public String userName;

    @SerializedName("email")
    public String email;

    @SerializedName("isCeleb")
    public boolean isCeleb=false;

    @SerializedName("aboutMe")
    public String aboutMe;

    @SerializedName("profession")
    public String profession;

    @SerializedName("firstName")
    public String firstName;

    @SerializedName("lastName")
    public String lastName;


    protected Profile(Parcel in) {
        id = in.readString();
        profilePic = in.readString();
        userName = in.readString();
        email = in.readString();
        isCeleb = in.readByte() != 0;
        aboutMe = in.readString();
        profession = in.readString();
        firstName = in.readString();
        lastName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(profilePic);
        dest.writeString(userName);
        dest.writeString(email);
        dest.writeByte((byte) (isCeleb ? 1 : 0));
        dest.writeString(aboutMe);
        dest.writeString(profession);
        dest.writeString(firstName);
        dest.writeString(lastName);
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
}
