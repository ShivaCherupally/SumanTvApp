package info.sumantv.flow.bottommenu.search;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Shiva on 21-08-2018.
 */

public class SearchCelebItemModel implements Parcelable {

    @SerializedName("celebrityId")
    @Expose
    public String celebrityId;

    @SerializedName("createdAt")
    @Expose
    public String createdAt;

    @SerializedName("username")
    @Expose
    public String username;

    @SerializedName("mobileNumber")
    @Expose
    public String mobileNumber;

    @SerializedName("avtar_imgPath")
    @Expose
    public String avtar_imgPath;

    @SerializedName("avtar_originalname")
    @Expose
    public String avtar_originalname;

    @SerializedName("imageRatio")
    @Expose
    public String imageRatio;


    @SerializedName("email")
    @Expose
    public String email;


    @SerializedName("name")
    @Expose
    public String name;


    @SerializedName("firstName")
    @Expose
    public String firstName;


    @SerializedName("lastName")
    @Expose
    public String lastName;


    @SerializedName("prefix")
    @Expose
    public String prefix;


    @SerializedName("aboutMe")
    @Expose
    public String aboutMe;


    @SerializedName("cover_imgPath")
    @Expose
    public String cover_imgPath;


    @SerializedName("profession")
    @Expose
    public String profession;

    public SearchCelebItemModel() {
    }

    protected SearchCelebItemModel(Parcel in) {
        celebrityId = in.readString();
        createdAt = in.readString();
        username = in.readString();
        mobileNumber = in.readString();
        avtar_imgPath = in.readString();
        avtar_originalname = in.readString();
        imageRatio = in.readString();
        email = in.readString();
        name = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        prefix = in.readString();
        aboutMe = in.readString();
        cover_imgPath = in.readString();
        profession = in.readString();
    }

    public static final Creator<SearchCelebItemModel> CREATOR = new Creator<SearchCelebItemModel>() {
        @Override
        public SearchCelebItemModel createFromParcel(Parcel in) {
            return new SearchCelebItemModel(in);
        }

        @Override
        public SearchCelebItemModel[] newArray(int size) {
            return new SearchCelebItemModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(celebrityId);
        dest.writeString(createdAt);
        dest.writeString(username);
        dest.writeString(mobileNumber);
        dest.writeString(avtar_imgPath);
        dest.writeString(avtar_originalname);
        dest.writeString(imageRatio);
        dest.writeString(email);
        dest.writeString(name);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(prefix);
        dest.writeString(aboutMe);
        dest.writeString(cover_imgPath);
        dest.writeString(profession);
    }


    public String getCelebrityId() {
        return celebrityId;
    }

    public void setCelebrityId(String celebrityId) {
        this.celebrityId = celebrityId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAvtar_imgPath() {
        return avtar_imgPath;
    }

    public void setAvtar_imgPath(String avtar_imgPath) {
        this.avtar_imgPath = avtar_imgPath;
    }

    public String getAvtar_originalname() {
        return avtar_originalname;
    }

    public void setAvtar_originalname(String avtar_originalname) {
        this.avtar_originalname = avtar_originalname;
    }

    public String getImageRatio() {
        return imageRatio;
    }

    public void setImageRatio(String imageRatio) {
        this.imageRatio = imageRatio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getCover_imgPath() {
        return cover_imgPath;
    }

    public void setCover_imgPath(String cover_imgPath) {
        this.cover_imgPath = cover_imgPath;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }
}
