
package info.sumantv.flow.bottommenu.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import info.sumantv.flow.userflow.UserActivitys.LoginActivity.PastProfileImage;

import java.util.List;

public class UserDetails implements Parcelable
{

    @SerializedName("avtar_imgPath")
    @Expose
    public String avtarImgPath;
    @SerializedName("cover_imgPath")
    @Expose
    public String cover_imgPath;
    @SerializedName("username")
    @Expose
    public String username;
    @SerializedName("mobileNumber")
    @Expose
    public String mobileNumber;
    @SerializedName("mobile")
    @Expose
    public String mobile;
    @SerializedName("firstName")
    @Expose
    public String firstName;
    @SerializedName("lastName")
    @Expose
    public String lastName;
    @SerializedName("aboutMe")
    @Expose
    public String aboutMe;
    @SerializedName("profession")
    @Expose
    public String profession;

    @SerializedName("dateOfBirth")
    @Expose
    public String dateOfBirth;
    @SerializedName("isCeleb")
    @Expose
    public Boolean isCeleb;
    @SerializedName("isManager")
    @Expose
    public Boolean isManager;
    @SerializedName("Dnd")
    @Expose
    public Boolean Dnd;
    @SerializedName("isOnline")
    @Expose
    public Boolean isOnline;
    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("email")
    @Expose
    public String email;

    @SerializedName("loginType")
    public String loginType;

    @SerializedName("role")
    public String role;

    @SerializedName("industry")
    public String industry;

    @SerializedName("category")
    public String userCategory;

    @SerializedName("isEmailVerified")
    @Expose
    public Boolean isEmailVerified;

    @SerializedName("isMobileVerified")
    @Expose
    public Boolean isMobileVerified;

    @SerializedName("countryDetails")
    @Expose
    public CountryDetails countryDetails;

    @SerializedName("pastProfileImages")
    public List<PastProfileImage> pastProfileImages;

    @SerializedName("activeTime")
    @Expose
    public String activeTime;


    protected UserDetails(Parcel in) {
        avtarImgPath = in.readString();
        cover_imgPath = in.readString();
        username = in.readString();
        mobileNumber = in.readString();
        mobile = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        aboutMe = in.readString();
        profession = in.readString();
        dateOfBirth = in.readString();
        byte tmpIsCeleb = in.readByte();
        isCeleb = tmpIsCeleb == 0 ? null : tmpIsCeleb == 1;
        byte tmpIsManager = in.readByte();
        isManager = tmpIsManager == 0 ? null : tmpIsManager == 1;
        byte tmpDnd = in.readByte();
        Dnd = tmpDnd == 0 ? null : tmpDnd == 1;
        byte tmpIsOnline = in.readByte();
        isOnline = tmpIsOnline == 0 ? null : tmpIsOnline == 1;
        id = in.readString();
        email = in.readString();
        loginType = in.readString();
        role = in.readString();
        industry = in.readString();
        userCategory = in.readString();
        byte tmpIsEmailVerified = in.readByte();
        isEmailVerified = tmpIsEmailVerified == 0 ? null : tmpIsEmailVerified == 1;
        byte tmpIsMobileVerified = in.readByte();
        isMobileVerified = tmpIsMobileVerified == 0 ? null : tmpIsMobileVerified == 1;
        countryDetails = in.readParcelable(CountryDetails.class.getClassLoader());
        pastProfileImages = in.createTypedArrayList(PastProfileImage.CREATOR);
        activeTime = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(avtarImgPath);
        dest.writeString(cover_imgPath);
        dest.writeString(username);
        dest.writeString(mobileNumber);
        dest.writeString(mobile);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(aboutMe);
        dest.writeString(profession);
        dest.writeString(dateOfBirth);
        dest.writeByte((byte) (isCeleb == null ? 0 : isCeleb ? 1 : 2));
        dest.writeByte((byte) (isManager == null ? 0 : isManager ? 1 : 2));
        dest.writeByte((byte) (Dnd == null ? 0 : Dnd ? 1 : 2));
        dest.writeByte((byte) (isOnline == null ? 0 : isOnline ? 1 : 2));
        dest.writeString(id);
        dest.writeString(email);
        dest.writeString(loginType);
        dest.writeString(role);
        dest.writeString(industry);
        dest.writeString(userCategory);
        dest.writeByte((byte) (isEmailVerified == null ? 0 : isEmailVerified ? 1 : 2));
        dest.writeByte((byte) (isMobileVerified == null ? 0 : isMobileVerified ? 1 : 2));
        dest.writeParcelable(countryDetails, flags);
        dest.writeTypedList(pastProfileImages);
        dest.writeString(activeTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserDetails> CREATOR = new Creator<UserDetails>() {
        @Override
        public UserDetails createFromParcel(Parcel in) {
            return new UserDetails(in);
        }

        @Override
        public UserDetails[] newArray(int size) {
            return new UserDetails[size];
        }
    };

    public String getAvtarImgPath() {
        return avtarImgPath;
    }

    public void setAvtarImgPath(String avtarImgPath) {
        this.avtarImgPath = avtarImgPath;
    }

    public String getCover_imgPath() {
        return cover_imgPath;
    }

    public void setCover_imgPath(String cover_imgPath) {
        this.cover_imgPath = cover_imgPath;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Boolean getCeleb() {
        return isCeleb;
    }

    public void setCeleb(Boolean celeb) {
        isCeleb = celeb;
    }

    public Boolean getManager() {
        return isManager;
    }

    public void setManager(Boolean manager) {
        isManager = manager;
    }

    public Boolean getDnd() {
        return Dnd;
    }

    public void setDnd(Boolean dnd) {
        Dnd = dnd;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getUserCategory() {
        return userCategory;
    }

    public void setUserCategory(String userCategory) {
        this.userCategory = userCategory;
    }

    public Boolean getEmailVerified() {
        return isEmailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        isEmailVerified = emailVerified;
    }

    public Boolean getMobileVerified() {
        return isMobileVerified;
    }

    public void setMobileVerified(Boolean mobileVerified) {
        isMobileVerified = mobileVerified;
    }

    public CountryDetails getCountryDetails() {
        return countryDetails;
    }

    public void setCountryDetails(CountryDetails countryDetails) {
        this.countryDetails = countryDetails;
    }

    public List<PastProfileImage> getPastProfileImages() {
        return pastProfileImages;
    }

    public void setPastProfileImages(List<PastProfileImage> pastProfileImages) {
        this.pastProfileImages = pastProfileImages;
    }

    public String getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(String activeTime) {
        this.activeTime = activeTime;
    }

    public static Creator<UserDetails> getCREATOR() {
        return CREATOR;
    }
}
