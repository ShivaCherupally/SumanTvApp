package info.sumantv.flow.stories.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Uday Kumay Vurukonda on 03-Sep-19.
 * <p>
 * Mr.Psycho
 */
public class CreateStoreMedia implements Parcelable {

    @SerializedName("mediaType")
    @Expose
    public String mediaType;
    @SerializedName("mediaRatio")
    @Expose
    public String mediaRatio;
    @SerializedName("mediaUrl")
    @Expose
    public String mediaUrl;
    @SerializedName("mediaCaption")
    @Expose
    public String mediaCaption;
    @SerializedName("videoDuration")
    @Expose
    public String videoDuration;


    public CreateStoreMedia(String mediaType, String mediaRatio, String mediaUrl, String mediaCaption) {
        this.mediaType = mediaType;
        this.mediaRatio = mediaRatio;
        this.mediaUrl = mediaUrl;
        this.mediaCaption = mediaCaption;
    }
    public CreateStoreMedia(String mediaType, String mediaRatio, String mediaUrl, String mediaCaption,String videoDuration) {
        this.mediaType = mediaType;
        this.mediaRatio = mediaRatio;
        this.mediaUrl = mediaUrl;
        this.mediaCaption = mediaCaption;
        this.videoDuration = videoDuration;
    }

    protected CreateStoreMedia(Parcel in) {
        mediaType = in.readString();
        mediaRatio = in.readString();
        mediaUrl = in.readString();
        mediaCaption = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mediaType);
        dest.writeString(mediaRatio);
        dest.writeString(mediaUrl);
        dest.writeString(mediaCaption);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CreateStoreMedia> CREATOR = new Creator<CreateStoreMedia>() {
        @Override
        public CreateStoreMedia createFromParcel(Parcel in) {
            return new CreateStoreMedia(in);
        }

        @Override
        public CreateStoreMedia[] newArray(int size) {
            return new CreateStoreMedia[size];
        }
    };
}
