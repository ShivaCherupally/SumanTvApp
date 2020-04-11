package info.dkapp.flow.appmanagers.feed.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 03-08-2018.
 **/

public class Like implements Parcelable
{
    @SerializedName("_id")
    public String id;

    @SerializedName("created_at")
    public String timeAgo;

    @SerializedName("updated_at")
    public String updated_at;

    @SerializedName("memberProfile")
    public Profile profile;


    public Like()
    {

    }


    protected Like(Parcel in) {
        id = in.readString();
        timeAgo = in.readString();
        updated_at = in.readString();
        profile = in.readParcelable(Profile.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(timeAgo);
        dest.writeString(updated_at);
        dest.writeParcelable(profile, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Like> CREATOR = new Creator<Like>() {
        @Override
        public Like createFromParcel(Parcel in) {
            return new Like(in);
        }

        @Override
        public Like[] newArray(int size) {
            return new Like[size];
        }
    };
}
