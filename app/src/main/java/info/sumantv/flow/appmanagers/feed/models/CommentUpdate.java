package info.sumantv.flow.appmanagers.feed.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommentUpdate implements Parcelable, Cloneable {

    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("feedId")
    @Expose
    public String feedId;
    @SerializedName("memberId")
    @Expose
    public String memberId;
    @SerializedName("activities")
    @Expose
    public String activities;
    @SerializedName("source")
    @Expose
    public String source;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("created_at")
    @Expose
    public String createdAt;

    protected CommentUpdate(Parcel in) {
        id = in.readString();
        feedId = in.readString();
        memberId = in.readString();
        activities = in.readString();
        source = in.readString();
        status = in.readString();
        createdAt = in.readString();
    }

    public static final Creator<CommentUpdate> CREATOR = new Creator<CommentUpdate>() {
        @Override
        public CommentUpdate createFromParcel(Parcel in) {
            return new CommentUpdate(in);
        }

        @Override
        public CommentUpdate[] newArray(int size) {
            return new CommentUpdate[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(feedId);
        parcel.writeString(memberId);
        parcel.writeString(activities);
        parcel.writeString(source);
        parcel.writeString(status);
        parcel.writeString(createdAt);
    }
}
