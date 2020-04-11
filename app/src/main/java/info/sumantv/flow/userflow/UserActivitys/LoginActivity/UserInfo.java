
package info.sumantv.flow.userflow.UserActivitys.LoginActivity;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserInfo implements Serializable, Parcelable
    {

        @SerializedName("avtar_imgPath")
        @Expose
        public String avtarImgPath;
        @SerializedName("avtar_originalname")
        @Expose
        public String avtarOriginalname;
        @SerializedName("imageRatio")
        @Expose
        public String imageRatio;
        @SerializedName("password")
        @Expose
        public String password;
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

        @SerializedName("country")
        @Expose
        public String country;
        @SerializedName("loginType")
        @Expose
        public String loginType;
        @SerializedName("role")
        @Expose
        public String role;
        @SerializedName("gender")
        @Expose
        public String gender;
        @SerializedName("dateOfBirth")
        @Expose
        public String dateOfBirth;
        @SerializedName("address")
        @Expose
        public String address;
        @SerializedName("referralCode")
        @Expose
        public String referralCode;
        @SerializedName("cumulativeSpent")
        @Expose
        public Integer cumulativeSpent;
        @SerializedName("cumulativeEarnings")
        @Expose
        public Integer cumulativeEarnings;
        @SerializedName("lastActivity")
        @Expose
        public String lastActivity;
        @SerializedName("profession")
        @Expose
        public String profession;
        @SerializedName("industry")
        @Expose
        public String industry;
        @SerializedName("userCategory")
        @Expose
        public String userCategory;
        @SerializedName("liveStatus")
        @Expose
        public String liveStatus;
        @SerializedName("status")
        @Expose
        public Boolean status;
        @SerializedName("isCeleb")
        @Expose
        public Boolean isCeleb;
        @SerializedName("isTrending")
        @Expose
        public Boolean isTrending;
        @SerializedName("isOnline")
        @Expose
        public Boolean isOnline;
        @SerializedName("isEditorChoice")
        @Expose
        public Boolean isEditorChoice;
        @SerializedName("isPromoted")
        @Expose
        public Boolean isPromoted;
        @SerializedName("isEmailVerified")
        @Expose
        public Boolean isEmailVerified;
        @SerializedName("callStatus")
        @Expose
        public String callStatus;
        @SerializedName("isMobileVerified")
        @Expose
        public Boolean isMobileVerified;
        @SerializedName("emailVerificationCode")
        @Expose
        public Integer emailVerificationCode;
        @SerializedName("mobileVerificationCode")
        @Expose
        public Integer mobileVerificationCode;
        @SerializedName("celebRecommendations")
        @Expose
        public Boolean celebRecommendations;
        @SerializedName("Dnd")
        @Expose
        public String dnd;
        @SerializedName("celebToManager")
        @Expose
        public List<String> celebToManager = new ArrayList<String>();
        @SerializedName("iosUpdatedAt")
        @Expose
        public String iosUpdatedAt;
        @SerializedName("created_by")
        @Expose
        public String createdBy;
        @SerializedName("updated_by")
        @Expose
        public String updatedBy;
        @SerializedName("IsDeleted")
        @Expose
        public Boolean isDeleted;
        @SerializedName("isPromoter")
        @Expose
        public Boolean isPromoter;
        @SerializedName("isManager")
        @Expose
        public Boolean isManager;
        @SerializedName("managerRefId")
        @Expose
        public String managerRefId;
        @SerializedName("promoterRefId")
        @Expose
        public String promoterRefId;
        @SerializedName("charityRefId")
        @Expose
        public String charityRefId;
        @SerializedName("celebCredits")
        @Expose
        public String celebCredits;
        @SerializedName("alternateEmail")
        @Expose
        public String alternateEmail;
        @SerializedName("alternateMobile")
        @Expose
        public String alternateMobile;
        @SerializedName("areaOfSpecialization")
        @Expose
        public String areaOfSpecialization;
        @SerializedName("managerIndustry")
        @Expose
        public List<String> managerIndustry = new ArrayList<String>();
        @SerializedName("managerCategory")
        @Expose
        public List<String> managerCategory = new ArrayList<String>();
        @SerializedName("managerSubCategory")
        @Expose
        public List<String> managerSubCategory = new ArrayList<String>();
        @SerializedName("experience")
        @Expose
        public Integer experience;
        @SerializedName("languages")
        @Expose
        public List<String> languages = new ArrayList<String>();
        @SerializedName("website")
        @Expose
        public String website;
        @SerializedName("facebookLink")
        @Expose
        public String facebookLink;
        @SerializedName("twitterLink")
        @Expose
        public String twitterLink;
        @SerializedName("_id")
        @Expose
        public String id;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("username")
        @Expose
        public String username;
        @SerializedName("mobileNumber")
        @Expose
        public String mobileNumber;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("isUsernameVerified")
        @Expose
        public boolean isUsernameVerified;
        @SerializedName("celebritiesWorkedFor")
        @Expose
        public List<CelebritiesWorkedFor> celebritiesWorkedFor = new ArrayList<CelebritiesWorkedFor>();
        @SerializedName("pastProfileImages")
        @Expose
        public List<PastProfileImage> pastProfileImages = new ArrayList<PastProfileImage>();

        /*@SerializedName("location")
        @Expose
        public String location;*/

        public UserInfo(){}

        protected UserInfo(Parcel in) {
            avtarImgPath = in.readString();
            avtarOriginalname = in.readString();
            imageRatio = in.readString();
            password = in.readString();
            name = in.readString();
            firstName = in.readString();
            lastName = in.readString();
            prefix = in.readString();
            aboutMe = in.readString();
            country = in.readString();
            loginType = in.readString();
            role = in.readString();
            gender = in.readString();
            dateOfBirth = in.readString();
            address = in.readString();
            referralCode = in.readString();
            if (in.readByte() == 0) {
                cumulativeSpent = null;
            } else {
                cumulativeSpent = in.readInt();
            }
            if (in.readByte() == 0) {
                cumulativeEarnings = null;
            } else {
                cumulativeEarnings = in.readInt();
            }
            lastActivity = in.readString();
            profession = in.readString();
            industry = in.readString();
            userCategory = in.readString();
            liveStatus = in.readString();
            byte tmpStatus = in.readByte();
            status = tmpStatus == 0 ? null : tmpStatus == 1;
            byte tmpIsCeleb = in.readByte();
            isCeleb = tmpIsCeleb == 0 ? null : tmpIsCeleb == 1;
            byte tmpIsTrending = in.readByte();
            isTrending = tmpIsTrending == 0 ? null : tmpIsTrending == 1;
            byte tmpIsOnline = in.readByte();
            isOnline = tmpIsOnline == 0 ? null : tmpIsOnline == 1;
            byte tmpIsEditorChoice = in.readByte();
            isEditorChoice = tmpIsEditorChoice == 0 ? null : tmpIsEditorChoice == 1;
            byte tmpIsPromoted = in.readByte();
            isPromoted = tmpIsPromoted == 0 ? null : tmpIsPromoted == 1;
            byte tmpIsEmailVerified = in.readByte();
            isEmailVerified = tmpIsEmailVerified == 0 ? null : tmpIsEmailVerified == 1;
            callStatus = in.readString();
            byte tmpIsMobileVerified = in.readByte();
            isMobileVerified = tmpIsMobileVerified == 0 ? null : tmpIsMobileVerified == 1;
            if (in.readByte() == 0) {
                emailVerificationCode = null;
            } else {
                emailVerificationCode = in.readInt();
            }
            if (in.readByte() == 0) {
                mobileVerificationCode = null;
            } else {
                mobileVerificationCode = in.readInt();
            }
            byte tmpCelebRecommendations = in.readByte();
            celebRecommendations = tmpCelebRecommendations == 0 ? null : tmpCelebRecommendations == 1;
            dnd = in.readString();
            celebToManager = in.createStringArrayList();
            iosUpdatedAt = in.readString();
            createdBy = in.readString();
            updatedBy = in.readString();
            byte tmpIsDeleted = in.readByte();
            isDeleted = tmpIsDeleted == 0 ? null : tmpIsDeleted == 1;
            byte tmpIsPromoter = in.readByte();
            isPromoter = tmpIsPromoter == 0 ? null : tmpIsPromoter == 1;
            byte tmpIsManager = in.readByte();
            isManager = tmpIsManager == 0 ? null : tmpIsManager == 1;
            managerRefId = in.readString();
            promoterRefId = in.readString();
            charityRefId = in.readString();
            celebCredits = in.readString();
            alternateEmail = in.readString();
            alternateMobile = in.readString();
            areaOfSpecialization = in.readString();
            managerIndustry = in.createStringArrayList();
            managerCategory = in.createStringArrayList();
            managerSubCategory = in.createStringArrayList();
            if (in.readByte() == 0) {
                experience = null;
            } else {
                experience = in.readInt();
            }
            languages = in.createStringArrayList();
            website = in.readString();
            facebookLink = in.readString();
            twitterLink = in.readString();
            id = in.readString();
            updatedAt = in.readString();
            email = in.readString();
            username = in.readString();
            mobileNumber = in.readString();
            createdAt = in.readString();
            isUsernameVerified = in.readByte() != 0;
            celebritiesWorkedFor = in.createTypedArrayList(CelebritiesWorkedFor.CREATOR);
            pastProfileImages = in.createTypedArrayList(PastProfileImage.CREATOR);
        }

        public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
            @Override
            public UserInfo createFromParcel(Parcel in) {
                return new UserInfo(in);
            }

            @Override
            public UserInfo[] newArray(int size) {
                return new UserInfo[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(avtarImgPath);
            parcel.writeString(avtarOriginalname);
            parcel.writeString(imageRatio);
            parcel.writeString(password);
            parcel.writeString(name);
            parcel.writeString(firstName);
            parcel.writeString(lastName);
            parcel.writeString(prefix);
            parcel.writeString(aboutMe);
            parcel.writeString(country);
            parcel.writeString(loginType);
            parcel.writeString(role);
            parcel.writeString(gender);
            parcel.writeString(dateOfBirth);
            parcel.writeString(address);
            parcel.writeString(referralCode);
            if (cumulativeSpent == null) {
                parcel.writeByte((byte) 0);
            } else {
                parcel.writeByte((byte) 1);
                parcel.writeInt(cumulativeSpent);
            }
            if (cumulativeEarnings == null) {
                parcel.writeByte((byte) 0);
            } else {
                parcel.writeByte((byte) 1);
                parcel.writeInt(cumulativeEarnings);
            }
            parcel.writeString(lastActivity);
            parcel.writeString(profession);
            parcel.writeString(industry);
            parcel.writeString(userCategory);
            parcel.writeString(liveStatus);
            parcel.writeByte((byte) (status == null ? 0 : status ? 1 : 2));
            parcel.writeByte((byte) (isCeleb == null ? 0 : isCeleb ? 1 : 2));
            parcel.writeByte((byte) (isTrending == null ? 0 : isTrending ? 1 : 2));
            parcel.writeByte((byte) (isOnline == null ? 0 : isOnline ? 1 : 2));
            parcel.writeByte((byte) (isEditorChoice == null ? 0 : isEditorChoice ? 1 : 2));
            parcel.writeByte((byte) (isPromoted == null ? 0 : isPromoted ? 1 : 2));
            parcel.writeByte((byte) (isEmailVerified == null ? 0 : isEmailVerified ? 1 : 2));
            parcel.writeString(callStatus);
            parcel.writeByte((byte) (isMobileVerified == null ? 0 : isMobileVerified ? 1 : 2));
            if (emailVerificationCode == null) {
                parcel.writeByte((byte) 0);
            } else {
                parcel.writeByte((byte) 1);
                parcel.writeInt(emailVerificationCode);
            }
            if (mobileVerificationCode == null) {
                parcel.writeByte((byte) 0);
            } else {
                parcel.writeByte((byte) 1);
                parcel.writeInt(mobileVerificationCode);
            }
            parcel.writeByte((byte) (celebRecommendations == null ? 0 : celebRecommendations ? 1 : 2));
            parcel.writeString(dnd);
            parcel.writeStringList(celebToManager);
            parcel.writeString(iosUpdatedAt);
            parcel.writeString(createdBy);
            parcel.writeString(updatedBy);
            parcel.writeByte((byte) (isDeleted == null ? 0 : isDeleted ? 1 : 2));
            parcel.writeByte((byte) (isPromoter == null ? 0 : isPromoter ? 1 : 2));
            parcel.writeByte((byte) (isManager == null ? 0 : isManager ? 1 : 2));
            parcel.writeString(managerRefId);
            parcel.writeString(promoterRefId);
            parcel.writeString(charityRefId);
            parcel.writeString(celebCredits);
            parcel.writeString(alternateEmail);
            parcel.writeString(alternateMobile);
            parcel.writeString(areaOfSpecialization);
            parcel.writeStringList(managerIndustry);
            parcel.writeStringList(managerCategory);
            parcel.writeStringList(managerSubCategory);
            if (experience == null) {
                parcel.writeByte((byte) 0);
            } else {
                parcel.writeByte((byte) 1);
                parcel.writeInt(experience);
            }
            parcel.writeStringList(languages);
            parcel.writeString(website);
            parcel.writeString(facebookLink);
            parcel.writeString(twitterLink);
            parcel.writeString(id);
            parcel.writeString(updatedAt);
            parcel.writeString(email);
            parcel.writeString(username);
            parcel.writeString(mobileNumber);
            parcel.writeString(createdAt);
            parcel.writeByte((byte) (isUsernameVerified ? 1 : 0));
            parcel.writeTypedList(celebritiesWorkedFor);
            parcel.writeTypedList(pastProfileImages);
        }
    }
