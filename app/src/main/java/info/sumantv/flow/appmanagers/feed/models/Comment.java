package info.sumantv.flow.appmanagers.feed.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 03-08-2018.
 **/

public class Comment implements Parcelable
{
    @SerializedName("_id")
    public String id;

    @SerializedName("source")
    public String comment;

    @SerializedName("created_at")
    public String timeAgo;

    @SerializedName("memberProfile")
    public Profile profile;

    public boolean isBusy = false;

    public Comment()
    {

    }


    protected Comment(Parcel in) {
        id = in.readString();
        comment = in.readString();
        timeAgo = in.readString();
        profile = in.readParcelable(Profile.class.getClassLoader());
        isBusy = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(comment);
        dest.writeString(timeAgo);
        dest.writeParcelable(profile, flags);
        dest.writeByte((byte) (isBusy ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}
