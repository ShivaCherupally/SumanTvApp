package info.sumantv.flow.bottommenu.allcelebritiestab;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Shiva on 8/17/2018.
 */

public class CelebProfileCompleteData implements Parcelable {
    @SerializedName("_id")
    private String _id;

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("role")
    private String role;

    @SerializedName("aboutMe")
    private String aboutMe;

    @SerializedName("avtar_imgPath")
    private String avtar_imgPath;

    @SerializedName("isCeleb")
    private boolean isCeleb;


    @SerializedName("isOnline")
    private boolean isOnline;

    @SerializedName("IsDeleted")
    public boolean IsDeleted;

    @SerializedName("isTrending")
    public boolean trending;

    @SerializedName("isPromoted")
    private boolean isPromoted;

    @SerializedName("isEditorChoice")
    private boolean isEditorChoice;

    @SerializedName("liveStatus")
    private boolean liveStatus;

    @SerializedName("profession")
    private String profession;

    @SerializedName("callStatus")
    public String callStatus;


    @SerializedName("isFan")
    public boolean isFan;


    @SerializedName("isFollower")
    public boolean isFollower;

    @SerializedName("isBlocked")
    public boolean isBlocked;

    public boolean isFanLoading = false;

    public boolean isFollowLoading = false;


    protected CelebProfileCompleteData(Parcel in) {
        _id = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        role = in.readString();
        aboutMe = in.readString();
        avtar_imgPath = in.readString();
        isCeleb = in.readByte() != 0;
        isOnline = in.readByte() != 0;
        IsDeleted = in.readByte() != 0;
        trending = in.readByte() != 0;
        isPromoted = in.readByte() != 0;
        isEditorChoice = in.readByte() != 0;
        liveStatus = in.readByte() != 0;
        profession = in.readString();
        callStatus = in.readString();
        isFan = in.readByte() != 0;
        isFollower = in.readByte() != 0;
        isBlocked = in.readByte() != 0;
        isFanLoading = in.readByte() != 0;
        isFollowLoading = in.readByte() != 0;
    }

    public CelebProfileCompleteData() {}

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(role);
        dest.writeString(aboutMe);
        dest.writeString(avtar_imgPath);
        dest.writeByte((byte) (isCeleb ? 1 : 0));
        dest.writeByte((byte) (isOnline ? 1 : 0));
        dest.writeByte((byte) (IsDeleted ? 1 : 0));
        dest.writeByte((byte) (trending ? 1 : 0));
        dest.writeByte((byte) (isPromoted ? 1 : 0));
        dest.writeByte((byte) (isEditorChoice ? 1 : 0));
        dest.writeByte((byte) (liveStatus ? 1 : 0));
        dest.writeString(profession);
        dest.writeString(callStatus);
        dest.writeByte((byte) (isFan ? 1 : 0));
        dest.writeByte((byte) (isFollower ? 1 : 0));
        dest.writeByte((byte) (isBlocked ? 1 : 0));
        dest.writeByte((byte) (isFanLoading ? 1 : 0));
        dest.writeByte((byte) (isFollowLoading ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CelebProfileCompleteData> CREATOR = new Creator<CelebProfileCompleteData>() {
        @Override
        public CelebProfileCompleteData createFromParcel(Parcel in) {
            return new CelebProfileCompleteData(in);
        }

        @Override
        public CelebProfileCompleteData[] newArray(int size) {
            return new CelebProfileCompleteData[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getAvtar_imgPath() {
        return avtar_imgPath;
    }

    public void setAvtar_imgPath(String avtar_imgPath) {
        this.avtar_imgPath = avtar_imgPath;
    }

    public boolean isCeleb() {
        return isCeleb;
    }

    public void setCeleb(boolean celeb) {
        isCeleb = celeb;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public boolean isDeleted() {
        return IsDeleted;
    }

    public void setDeleted(boolean deleted) {
        IsDeleted = deleted;
    }

    public boolean isTrending() {
        return trending;
    }

    public void setTrending(boolean trending) {
        this.trending = trending;
    }

    public boolean isPromoted() {
        return isPromoted;
    }

    public void setPromoted(boolean promoted) {
        isPromoted = promoted;
    }

    public boolean isEditorChoice() {
        return isEditorChoice;
    }

    public void setEditorChoice(boolean editorChoice) {
        isEditorChoice = editorChoice;
    }

    public boolean isLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(boolean liveStatus) {
        this.liveStatus = liveStatus;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getCallStatus() {
        return callStatus;
    }

    public void setCallStatus(String callStatus) {
        this.callStatus = callStatus;
    }

    public boolean isFan() {
        return isFan;
    }

    public void setFan(boolean fan) {
        isFan = fan;
    }

    public boolean isFollower() {
        return isFollower;
    }

    public void setFollower(boolean follower) {
        isFollower = follower;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public boolean isFanLoading() {
        return isFanLoading;
    }

    public void setFanLoading(boolean fanLoading) {
        isFanLoading = fanLoading;
    }

    public boolean isFollowLoading() {
        return isFollowLoading;
    }

    public void setFollowLoading(boolean followLoading) {
        isFollowLoading = followLoading;
    }
}
