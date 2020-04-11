package info.sumantv.flow.appmanagers.feed.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by User on 11-07-2018.
 **/

public class Media implements Parcelable, Cloneable
{
    @SerializedName("mediaId")
    public String mediaId;

    @SerializedName("_id")
    public String _id;

    public String feedId;

    @SerializedName("mediaCreditValue")
    public String mediaCreditValue;

    @SerializedName("mediaType")
    public String type = "image";

    @SerializedName("createdAt")
    @Expose
    public String createdAt;

    public String url;

    @SerializedName("mediaRatio")
    public Double ratio = 0.00;

    @SerializedName("mediaSize")
    public double sizeKB;

    public Uri uri;

    public Uri thumbnailUri;

    public String mimeType;

    public String name;

    public String dataFilePath;

    @SerializedName("mediaLikesCount")
    public int numberOfLikes;

    @SerializedName("mediaCommentsCount")
    public int numberOfComments;

    @SerializedName("isMediaLikedByCurrentUser")
    public int isLike;

    public boolean isBusyLike = false;

    @SerializedName("mediaCaption")
    public String caption = "";

    @SerializedName("faceFeatures")
    public List<FacePoint> facePointList;

    @SerializedName("src")
    public Source source;

    public Boolean isSelected = false;
    public Feed feedMediaDetails;

    public Media(){}


    protected Media(Parcel in) {
        mediaId = in.readString();
        _id = in.readString();
        feedId = in.readString();
        mediaCreditValue = in.readString();
        type = in.readString();
        createdAt = in.readString();
        url = in.readString();
        if (in.readByte() == 0) {
            ratio = null;
        } else {
            ratio = in.readDouble();
        }
        sizeKB = in.readDouble();
        uri = in.readParcelable(Uri.class.getClassLoader());
        thumbnailUri = in.readParcelable(Uri.class.getClassLoader());
        mimeType = in.readString();
        name = in.readString();
        dataFilePath = in.readString();
        numberOfLikes = in.readInt();
        numberOfComments = in.readInt();
        isLike = in.readInt();
        isBusyLike = in.readByte() != 0;
        caption = in.readString();
        facePointList = in.createTypedArrayList(FacePoint.CREATOR);
        source = in.readParcelable(Source.class.getClassLoader());
        byte tmpIsSelected = in.readByte();
        isSelected = tmpIsSelected == 0 ? null : tmpIsSelected == 1;
        feedMediaDetails = in.readParcelable(Feed.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mediaId);
        dest.writeString(_id);
        dest.writeString(feedId);
        dest.writeString(mediaCreditValue);
        dest.writeString(type);
        dest.writeString(createdAt);
        dest.writeString(url);
        if (ratio == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(ratio);
        }
        dest.writeDouble(sizeKB);
        dest.writeParcelable(uri, flags);
        dest.writeParcelable(thumbnailUri, flags);
        dest.writeString(mimeType);
        dest.writeString(name);
        dest.writeString(dataFilePath);
        dest.writeInt(numberOfLikes);
        dest.writeInt(numberOfComments);
        dest.writeInt(isLike);
        dest.writeByte((byte) (isBusyLike ? 1 : 0));
        dest.writeString(caption);
        dest.writeTypedList(facePointList);
        dest.writeParcelable(source, flags);
        dest.writeByte((byte) (isSelected == null ? 0 : isSelected ? 1 : 2));
        dest.writeParcelable(feedMediaDetails, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Media> CREATOR = new Creator<Media>() {
        @Override
        public Media createFromParcel(Parcel in) {
            return new Media(in);
        }

        @Override
        public Media[] newArray(int size) {
            return new Media[size];
        }
    };
}
