
package info.sumantv.flow.menu_list.ProfilesListCommon;

import java.io.Serializable;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileInfo implements Serializable, Parcelable {

    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("username")
    @Expose
    public String username;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("dua")
    @Expose
    public Boolean dua;
    @SerializedName("pastProfileImages")
    @Expose
    public List<Object> pastProfileImages = null;
    @SerializedName("twitterLink")
    @Expose
    public String twitterLink;
    @SerializedName("facebookLink")
    @Expose
    public String facebookLink;
    @SerializedName("website")
    @Expose
    public String website;
    @SerializedName("celebritiesWorkedFor")
    @Expose
    public List<Object> celebritiesWorkedFor = null;
    @SerializedName("languages")
    @Expose
    public List<Object> languages = null;
    @SerializedName("experience")
    @Expose
    public Integer experience;
    @SerializedName("managerSubCategory")
    @Expose
    public List<Object> managerSubCategory = null;
    @SerializedName("managerCategory")
    @Expose
    public List<Object> managerCategory = null;
    @SerializedName("managerIndustry")
    @Expose
    public List<Object> managerIndustry = null;
    @SerializedName("areaOfSpecialization")
    @Expose
    public String areaOfSpecialization;
    @SerializedName("alternateMobile")
    @Expose
    public String alternateMobile;
    @SerializedName("alternateEmail")
    @Expose
    public String alternateEmail;
    @SerializedName("celebCredits")
    @Expose
    public String celebCredits;
    @SerializedName("charityRefId")
    @Expose
    public Object charityRefId;
    @SerializedName("promoterRefId")
    @Expose
    public Object promoterRefId;
    @SerializedName("managerRefId")
    @Expose
    public Object managerRefId;
    @SerializedName("isManager")
    @Expose
    public Boolean isManager;
    @SerializedName("isPromoter")
    @Expose
    public Boolean isPromoter;
    @SerializedName("IsDeleted")
    @Expose
    public Boolean isDeleted;
    @SerializedName("updated_by")
    @Expose
    public String updatedBy;
    @SerializedName("created_by")
    @Expose
    public String createdBy;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("iosUpdatedAt")
    @Expose
    public String iosUpdatedAt;
    @SerializedName("celebToManager")
    @Expose
    public List<Object> celebToManager = null;
    @SerializedName("Dnd")
    @Expose
    public String dnd;
    @SerializedName("celebRecommendations")
    @Expose
    public Boolean celebRecommendations;
    @SerializedName("mobileVerificationCode")
    @Expose
    public Integer mobileVerificationCode;
    @SerializedName("emailVerificationCode")
    @Expose
    public Integer emailVerificationCode;
    @SerializedName("isMobileVerified")
    @Expose
    public Boolean isMobileVerified;
    @SerializedName("callStatus")
    @Expose
    public String callStatus;
    @SerializedName("isEmailVerified")
    @Expose
    public Boolean isEmailVerified;
    @SerializedName("isPromoted")
    @Expose
    public Boolean isPromoted;
    @SerializedName("isEditorChoice")
    @Expose
    public Boolean isEditorChoice;
    @SerializedName("isOnline")
    @Expose
    public Boolean isOnline;
    @SerializedName("isTrending")
    @Expose
    public Boolean isTrending;
    @SerializedName("isCeleb")
    @Expose
    public Boolean isCeleb;
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("liveStatus")
    @Expose
    public String liveStatus;
    @SerializedName("userCategory")
    @Expose
    public String userCategory;
    @SerializedName("category")
    @Expose
    public String category;
    @SerializedName("industry")
    @Expose
    public String industry;
    @SerializedName("profession")
    @Expose
    public String profession;
    @SerializedName("lastActivity")
    @Expose
    public String lastActivity;
    @SerializedName("cumulativeEarnings")
    @Expose
    public Integer cumulativeEarnings;
    @SerializedName("cumulativeSpent")
    @Expose
    public Integer cumulativeSpent;
    @SerializedName("referralCode")
    @Expose
    public String referralCode;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("dateOfBirth")
    @Expose
    public String dateOfBirth;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("role")
    @Expose
    public String role;
    @SerializedName("loginType")
    @Expose
    public String loginType;
    @SerializedName("country")
    @Expose
    public String country;
    @SerializedName("location")
    @Expose
    public String location;
    @SerializedName("aboutMe")
    @Expose
    public String aboutMe;
    @SerializedName("prefix")
    @Expose
    public String prefix;
    @SerializedName("lastName")
    @Expose
    public String lastName;
    @SerializedName("firstName")
    @Expose
    public String firstName;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("imageRatio")
    @Expose
    public String imageRatio;
    @SerializedName("avtar_originalname")
    @Expose
    public String avtarOriginalname;
    @SerializedName("avtar_imgPath")
    @Expose
    public String avtarImgPath;
    @SerializedName("isUsernameVerified")
    @Expose
    public Boolean isUsernameVerified;
    @SerializedName("osType")
    @Expose
    public String osType;
    @SerializedName("mobile")
    @Expose
    public String mobile;
    @SerializedName("mobileNumber")
    @Expose
    public String mobileNumber;
    @SerializedName("createdAt")
    @Expose
    public String createdAt;

    @SerializedName("blockedDate")
    @Expose
    public String blockedDate;

    @SerializedName("feedback")
    @Expose
    public String feedback;

    @SerializedName("total")
    @Expose
    public Integer total;


    protected ProfileInfo(Parcel in) {
        id = in.readString();
        username = in.readString();
        email = in.readString();
        byte tmpDua = in.readByte();
        dua = tmpDua == 0 ? null : tmpDua == 1;
        twitterLink = in.readString();
        facebookLink = in.readString();
        website = in.readString();
        if (in.readByte() == 0) {
            experience = null;
        } else {
            experience = in.readInt();
        }
        areaOfSpecialization = in.readString();
        alternateMobile = in.readString();
        alternateEmail = in.readString();
        celebCredits = in.readString();
        byte tmpIsManager = in.readByte();
        isManager = tmpIsManager == 0 ? null : tmpIsManager == 1;
        byte tmpIsPromoter = in.readByte();
        isPromoter = tmpIsPromoter == 0 ? null : tmpIsPromoter == 1;
        byte tmpIsDeleted = in.readByte();
        isDeleted = tmpIsDeleted == 0 ? null : tmpIsDeleted == 1;
        updatedBy = in.readString();
        createdBy = in.readString();
        updatedAt = in.readString();
        iosUpdatedAt = in.readString();
        dnd = in.readString();
        byte tmpCelebRecommendations = in.readByte();
        celebRecommendations = tmpCelebRecommendations == 0 ? null : tmpCelebRecommendations == 1;
        if (in.readByte() == 0) {
            mobileVerificationCode = null;
        } else {
            mobileVerificationCode = in.readInt();
        }
        if (in.readByte() == 0) {
            emailVerificationCode = null;
        } else {
            emailVerificationCode = in.readInt();
        }
        byte tmpIsMobileVerified = in.readByte();
        isMobileVerified = tmpIsMobileVerified == 0 ? null : tmpIsMobileVerified == 1;
        callStatus = in.readString();
        byte tmpIsEmailVerified = in.readByte();
        isEmailVerified = tmpIsEmailVerified == 0 ? null : tmpIsEmailVerified == 1;
        byte tmpIsPromoted = in.readByte();
        isPromoted = tmpIsPromoted == 0 ? null : tmpIsPromoted == 1;
        byte tmpIsEditorChoice = in.readByte();
        isEditorChoice = tmpIsEditorChoice == 0 ? null : tmpIsEditorChoice == 1;
        byte tmpIsOnline = in.readByte();
        isOnline = tmpIsOnline == 0 ? null : tmpIsOnline == 1;
        byte tmpIsTrending = in.readByte();
        isTrending = tmpIsTrending == 0 ? null : tmpIsTrending == 1;
        byte tmpIsCeleb = in.readByte();
        isCeleb = tmpIsCeleb == 0 ? null : tmpIsCeleb == 1;
        byte tmpStatus = in.readByte();
        status = tmpStatus == 0 ? null : tmpStatus == 1;
        liveStatus = in.readString();
        userCategory = in.readString();
        category = in.readString();
        industry = in.readString();
        profession = in.readString();
        lastActivity = in.readString();
        if (in.readByte() == 0) {
            cumulativeEarnings = null;
        } else {
            cumulativeEarnings = in.readInt();
        }
        if (in.readByte() == 0) {
            cumulativeSpent = null;
        } else {
            cumulativeSpent = in.readInt();
        }
        referralCode = in.readString();
        address = in.readString();
        dateOfBirth = in.readString();
        gender = in.readString();
        role = in.readString();
        loginType = in.readString();
        country = in.readString();
        location = in.readString();
        aboutMe = in.readString();
        prefix = in.readString();
        lastName = in.readString();
        firstName = in.readString();
        name = in.readString();
        password = in.readString();
        imageRatio = in.readString();
        avtarOriginalname = in.readString();
        avtarImgPath = in.readString();
        byte tmpIsUsernameVerified = in.readByte();
        isUsernameVerified = tmpIsUsernameVerified == 0 ? null : tmpIsUsernameVerified == 1;
        osType = in.readString();
        mobile = in.readString();
        mobileNumber = in.readString();
        createdAt = in.readString();
        blockedDate = in.readString();
        feedback = in.readString();
        if (in.readByte() == 0) {
            total = null;
        } else {
            total = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(username);
        dest.writeString(email);
        dest.writeByte((byte) (dua == null ? 0 : dua ? 1 : 2));
        dest.writeString(twitterLink);
        dest.writeString(facebookLink);
        dest.writeString(website);
        if (experience == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(experience);
        }
        dest.writeString(areaOfSpecialization);
        dest.writeString(alternateMobile);
        dest.writeString(alternateEmail);
        dest.writeString(celebCredits);
        dest.writeByte((byte) (isManager == null ? 0 : isManager ? 1 : 2));
        dest.writeByte((byte) (isPromoter == null ? 0 : isPromoter ? 1 : 2));
        dest.writeByte((byte) (isDeleted == null ? 0 : isDeleted ? 1 : 2));
        dest.writeString(updatedBy);
        dest.writeString(createdBy);
        dest.writeString(updatedAt);
        dest.writeString(iosUpdatedAt);
        dest.writeString(dnd);
        dest.writeByte((byte) (celebRecommendations == null ? 0 : celebRecommendations ? 1 : 2));
        if (mobileVerificationCode == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(mobileVerificationCode);
        }
        if (emailVerificationCode == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(emailVerificationCode);
        }
        dest.writeByte((byte) (isMobileVerified == null ? 0 : isMobileVerified ? 1 : 2));
        dest.writeString(callStatus);
        dest.writeByte((byte) (isEmailVerified == null ? 0 : isEmailVerified ? 1 : 2));
        dest.writeByte((byte) (isPromoted == null ? 0 : isPromoted ? 1 : 2));
        dest.writeByte((byte) (isEditorChoice == null ? 0 : isEditorChoice ? 1 : 2));
        dest.writeByte((byte) (isOnline == null ? 0 : isOnline ? 1 : 2));
        dest.writeByte((byte) (isTrending == null ? 0 : isTrending ? 1 : 2));
        dest.writeByte((byte) (isCeleb == null ? 0 : isCeleb ? 1 : 2));
        dest.writeByte((byte) (status == null ? 0 : status ? 1 : 2));
        dest.writeString(liveStatus);
        dest.writeString(userCategory);
        dest.writeString(category);
        dest.writeString(industry);
        dest.writeString(profession);
        dest.writeString(lastActivity);
        if (cumulativeEarnings == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(cumulativeEarnings);
        }
        if (cumulativeSpent == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(cumulativeSpent);
        }
        dest.writeString(referralCode);
        dest.writeString(address);
        dest.writeString(dateOfBirth);
        dest.writeString(gender);
        dest.writeString(role);
        dest.writeString(loginType);
        dest.writeString(country);
        dest.writeString(location);
        dest.writeString(aboutMe);
        dest.writeString(prefix);
        dest.writeString(lastName);
        dest.writeString(firstName);
        dest.writeString(name);
        dest.writeString(password);
        dest.writeString(imageRatio);
        dest.writeString(avtarOriginalname);
        dest.writeString(avtarImgPath);
        dest.writeByte((byte) (isUsernameVerified == null ? 0 : isUsernameVerified ? 1 : 2));
        dest.writeString(osType);
        dest.writeString(mobile);
        dest.writeString(mobileNumber);
        dest.writeString(createdAt);
        dest.writeString(blockedDate);
        dest.writeString(feedback);
        if (total == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(total);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProfileInfo> CREATOR = new Creator<ProfileInfo>() {
        @Override
        public ProfileInfo createFromParcel(Parcel in) {
            return new ProfileInfo(in);
        }

        @Override
        public ProfileInfo[] newArray(int size) {
            return new ProfileInfo[size];
        }
    };
}
