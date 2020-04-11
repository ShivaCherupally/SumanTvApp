package info.sumantv.flow.appmanagers.feed.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by User on 11-07-2018.
 **/

public class Feed implements Parcelable, Cloneable {

    @SerializedName("_id")
    public String id;

    @SerializedName("memberId")
    public String memberId;

    @SerializedName("profession")
    public String profession;

    @SerializedName("title")
    public String feedTitle;

    @SerializedName("content")
    public String feedDescription;

    public String timeAgo;

    public String location;

    @SerializedName("media")
    public List<Media> mediaList;

    @SerializedName("feedByMemberDetails")
    public FeedMemberDetails feedMemberDetails;


    @SerializedName("feedLikesCount")
    public int numberOfLikes;

    @SerializedName("celeAudioCharge")
    public int celeAudioCharge;

    @SerializedName("celeFanCharge")
    public int celeFanCharge;

    @SerializedName("celeVideoCharge")
    public int celeVideoCharge;

    @SerializedName("feedCommentsCount")
    public int numberOfComments;

    @SerializedName("isFeedLikedByCurrentUser")
    public int isLike;

    public boolean isBusyLike = false;

    @SerializedName("created_at")
    public String createdDate;

    @SerializedName("updated_at")
    public String updatedDate;


    @SerializedName("createdBy")
    public String createdBy;


    @SerializedName("isHide")
    public boolean isHide;

    @SerializedName("feedSettingsDetails")
    public int feedSettingsDetails;


    public boolean isUpdating = false;
    public Integer type;
    public List<SuggestionsModel> suggestions;
    public FeedAdvertisementModel advertisement;

    public Feed() {}


    protected Feed(Parcel in) {
        id = in.readString();
        memberId = in.readString();
        profession = in.readString();
        feedTitle = in.readString();
        feedDescription = in.readString();
        timeAgo = in.readString();
        location = in.readString();
        mediaList = in.createTypedArrayList(Media.CREATOR);
        feedMemberDetails = in.readParcelable(FeedMemberDetails.class.getClassLoader());
        numberOfLikes = in.readInt();
        celeAudioCharge = in.readInt();
        celeFanCharge = in.readInt();
        celeVideoCharge = in.readInt();
        numberOfComments = in.readInt();
        isLike = in.readInt();
        isBusyLike = in.readByte() != 0;
        createdDate = in.readString();
        updatedDate = in.readString();
        createdBy = in.readString();
        isHide = in.readByte() != 0;
        feedSettingsDetails = in.readInt();
        isUpdating = in.readByte() != 0;
        if (in.readByte() == 0) {
            type = null;
        } else {
            type = in.readInt();
        }
        suggestions = in.createTypedArrayList(SuggestionsModel.CREATOR);
        advertisement = in.readParcelable(FeedAdvertisementModel.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(memberId);
        dest.writeString(profession);
        dest.writeString(feedTitle);
        dest.writeString(feedDescription);
        dest.writeString(timeAgo);
        dest.writeString(location);
        dest.writeTypedList(mediaList);
        dest.writeParcelable(feedMemberDetails, flags);
        dest.writeInt(numberOfLikes);
        dest.writeInt(celeAudioCharge);
        dest.writeInt(celeFanCharge);
        dest.writeInt(celeVideoCharge);
        dest.writeInt(numberOfComments);
        dest.writeInt(isLike);
        dest.writeByte((byte) (isBusyLike ? 1 : 0));
        dest.writeString(createdDate);
        dest.writeString(updatedDate);
        dest.writeString(createdBy);
        dest.writeByte((byte) (isHide ? 1 : 0));
        dest.writeInt(feedSettingsDetails);
        dest.writeByte((byte) (isUpdating ? 1 : 0));
        if (type == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(type);
        }
        dest.writeTypedList(suggestions);
        dest.writeParcelable(advertisement, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Feed> CREATOR = new Creator<Feed>() {
        @Override
        public Feed createFromParcel(Parcel in) {
            return new Feed(in);
        }

        @Override
        public Feed[] newArray(int size) {
            return new Feed[size];
        }
    };

    public Feed getFeedWithType(int type) {
        Feed feed = new Feed();
        feed.type = type;
        return feed;
    }

    public Feed generateClone(Feed feed) {
        try {
            return (Feed) feed.clone();

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

}
