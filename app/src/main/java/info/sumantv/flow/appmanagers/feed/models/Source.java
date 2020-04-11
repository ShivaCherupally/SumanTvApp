package info.sumantv.flow.appmanagers.feed.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 14-08-2018.
 **/

public class Source implements Parcelable {
    @SerializedName("mediaUrl")
    public String url;

    @SerializedName("thumbnail")
    public String urlthumbnail;

    @SerializedName("mediaName")
    public String mediaName;

    @SerializedName("videoUrl")
    public String videoUrl;

    public String mediaType;
    public float mediaRatio;

    protected Source(Parcel in) {
        url = in.readString();
        urlthumbnail = in.readString();
        mediaName = in.readString();
        videoUrl = in.readString();
        mediaType = in.readString();
        mediaRatio = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(urlthumbnail);
        dest.writeString(mediaName);
        dest.writeString(videoUrl);
        dest.writeString(mediaType);
        dest.writeFloat(mediaRatio);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Source> CREATOR = new Creator<Source>() {
        @Override
        public Source createFromParcel(Parcel in) {
            return new Source(in);
        }

        @Override
        public Source[] newArray(int size) {
            return new Source[size];
        }
    };
}
