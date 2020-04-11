package info.dkapp.flow.stories.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StoriesInnerData implements Parcelable {
    @SerializedName("_id")
    @Expose
    public String _id;

    @SerializedName("memberId")
    @Expose
    public String memberId;

    @SerializedName("mediaType")
    @Expose
    public String mediaType;

    @SerializedName("mediaName")
    @Expose
    public String mediaName;

    @SerializedName("mediaCaption")
    @Expose
    public String mediaCaption;

    @SerializedName("isSeen")
    @Expose
    public int isSeen;

    @SerializedName("createdAt")
    @Expose
    public String createdAt;


    @SerializedName("isDeleted")
    @Expose
    public boolean isDeleted;

    @SerializedName("seenCount")
    @Expose
    public int seenCount;

    @SerializedName("src")
    @Expose
    public ImageDataInfo imageDataInfo;

    @SerializedName("storySeenProfiles")
    @Expose
    public ArrayList<StorySeenProfiles> seenprofilesList;

    @SerializedName("videoDuration")
    @Expose
    public double videoDuration;



    public class ImageDataInfo implements Parcelable {
        @SerializedName("mediaUrl")
        @Expose
        public String mediaUrl;

        @SerializedName("thumbnailUrl")
        @Expose
        public String thumbnailUrl;


        protected ImageDataInfo(Parcel in) {
            mediaUrl = in.readString();
            thumbnailUrl = in.readString();
        }

        protected final Creator<ImageDataInfo> CREATOR = new Creator<ImageDataInfo>() {
            @Override
            public ImageDataInfo createFromParcel(Parcel in) {
                return new ImageDataInfo(in);
            }

            @Override
            public ImageDataInfo[] newArray(int size) {
                return new ImageDataInfo[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(mediaUrl);
            parcel.writeString(thumbnailUrl);
        }
    }


    protected StoriesInnerData(Parcel in) {
        _id = in.readString();
        memberId = in.readString();
        mediaType = in.readString();
        mediaName = in.readString();
        mediaCaption = in.readString();
        isSeen = in.readInt();
        createdAt = in.readString();
        isDeleted = in.readByte() != 0;
        seenCount = in.readInt();
        imageDataInfo = in.readParcelable(ImageDataInfo.class.getClassLoader());
        seenprofilesList = in.createTypedArrayList(StorySeenProfiles.CREATOR);
        videoDuration = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(memberId);
        dest.writeString(mediaType);
        dest.writeString(mediaName);
        dest.writeString(mediaCaption);
        dest.writeInt(isSeen);
        dest.writeString(createdAt);
        dest.writeByte((byte) (isDeleted ? 1 : 0));
        dest.writeInt(seenCount);
        dest.writeParcelable(imageDataInfo, flags);
        dest.writeTypedList(seenprofilesList);
        dest.writeDouble(videoDuration);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<StoriesInnerData> CREATOR = new Creator<StoriesInnerData>() {
        @Override
        public StoriesInnerData createFromParcel(Parcel in) {
            return new StoriesInnerData(in);
        }

        @Override
        public StoriesInnerData[] newArray(int size) {
            return new StoriesInnerData[size];
        }
    };
}
