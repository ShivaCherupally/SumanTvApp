package info.dkapp.flow.menu_list.MyContent.MyPostsActivity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Shiva on 3/17/2018.
 */

public class MyPostData implements Serializable,Parcelable {

    @SerializedName("_id")
    private String _id;

    @SerializedName("title")
    private String title;

    protected MyPostData(Parcel in) {
        _id = in.readString();
        title = in.readString();
        content = in.readString();
        mediaSrc = in.readString();
        profilePicPath = in.readString();
        service_type = in.readString();
        likesCount = in.readInt();
        sharesCount = in.readInt();
        commentsCount = in.readInt();
        followCount = in.readInt();
        created_at = in.readString();
        like_status = in.readString();
        view_status = in.readString();
        bookmark_status = in.readString();
    }

    public static final Creator<MyPostData> CREATOR = new Creator<MyPostData>() {
        @Override
        public MyPostData createFromParcel(Parcel in) {
            return new MyPostData(in);
        }

        @Override
        public MyPostData[] newArray(int size) {
            return new MyPostData[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @SerializedName("content")
    private String content;

    @SerializedName("mediaSrc")
    private String mediaSrc;

    @SerializedName("profilePicPath")
    private String profilePicPath;

    @SerializedName("service_type")
    private String service_type;

    @SerializedName("likesCount")
    private int likesCount;

    @SerializedName("sharesCount")
    private int sharesCount;


    @SerializedName("commentsCount")
    private int commentsCount;

    @SerializedName("followCount")
    private int followCount;

    @SerializedName("created_at")
    private String created_at;

    @SerializedName("likeStatus")
    private String like_status;

    @SerializedName("viewStatus")
    private String view_status;

    @SerializedName("bookmarkStatus")
    private String bookmark_status;

    @SerializedName("mediaArray")
    private List<MediaArray> mediaArray = null;

    public List<MediaArray> getMediaArray() {
        return mediaArray;
    }

    public void setMediaArray(List<MediaArray> mediaArray) {
        this.mediaArray = mediaArray;
    }

    public String getLike_status() {
        return like_status;
    }

    public void setLike_status(String like_status) {
        this.like_status = like_status;
    }

    public String getView_status() {
        return view_status;
    }

    public void setView_status(String view_status) {
        this.view_status = view_status;
    }

    public String getBookmark_status() {
        return bookmark_status;
    }

    public void setBookmark_status(String bookmark_status) {
        this.bookmark_status = bookmark_status;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getSharesCount() {
        return sharesCount;
    }

    public void setSharesCount(int sharesCount) {
        this.sharesCount = sharesCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getFollowCount() {
        return followCount;
    }

    public void setFollowCount(int followCount) {
        this.followCount = followCount;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMediaSrc() {
        return mediaSrc;
    }

    public void setMediaSrc(String mediaSrc) {
        this.mediaSrc = mediaSrc;
    }

    public String getProfilePicPath() {
        return profilePicPath;
    }

    public void setProfilePicPath(String profilePicPath) {
        this.profilePicPath = profilePicPath;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(_id);
        parcel.writeString(title);
        parcel.writeString(content);
        parcel.writeString(mediaSrc);
        parcel.writeString(profilePicPath);
        parcel.writeString(service_type);
        parcel.writeInt(likesCount);
        parcel.writeInt(sharesCount);
        parcel.writeInt(commentsCount);
        parcel.writeInt(followCount);
        parcel.writeString(created_at);
        parcel.writeString(like_status);
        parcel.writeString(view_status);
        parcel.writeString(bookmark_status);
    }

  /*  public MyPostData(String _id, String title, String content, String mediaSrc, String profilePicPath, String service_type, String created_at) {
        this._id = _id;
        this.title = title;
        this.content = content;
        this.mediaSrc = mediaSrc;
        this.profilePicPath = profilePicPath;
        this.service_type = service_type;
        this.created_at = created_at;
    }*/
}
