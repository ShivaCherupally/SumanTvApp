package info.dkapp.flow.retrofitcall;

import android.os.Parcel;
import android.os.Parcelable;

public class UserLogin implements Parcelable {

    public String token;
    public String userId;
    public String userName;
    public String firstName;
    public String lastName;
    public String profilePic;
    public String coverPic;
    public String about;
    public String mobileNumber;
    public String email;
    public String countryCode;
    public Double referralCredits;
    public Double mainCredits;
    public Boolean isUserNameVerified;
    public Boolean isPreferencesSelected;
    public Boolean isMobileVerified;
    public Boolean isEmailVerified;
    public Boolean isOnline;
    public Boolean isCeleb;
    public Boolean isManager;
    public String liveStatus;
    public String profession;
    public String referralCode;
    public String loginType;

    public UserLogin() {
    }


    protected UserLogin(Parcel in) {
        token = in.readString();
        userId = in.readString();
        userName = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        profilePic = in.readString();
        coverPic = in.readString();
        about = in.readString();
        mobileNumber = in.readString();
        email = in.readString();
        countryCode = in.readString();
        if (in.readByte() == 0) {
            referralCredits = null;
        } else {
            referralCredits = in.readDouble();
        }
        if (in.readByte() == 0) {
            mainCredits = null;
        } else {
            mainCredits = in.readDouble();
        }
        byte tmpIsUserNameVerified = in.readByte();
        isUserNameVerified = tmpIsUserNameVerified == 0 ? null : tmpIsUserNameVerified == 1;
        byte tmpIsPreferencesSelected = in.readByte();
        isPreferencesSelected = tmpIsPreferencesSelected == 0 ? null : tmpIsPreferencesSelected == 1;
        byte tmpIsMobileVerified = in.readByte();
        isMobileVerified = tmpIsMobileVerified == 0 ? null : tmpIsMobileVerified == 1;
        byte tmpIsEmailVerified = in.readByte();
        isEmailVerified = tmpIsEmailVerified == 0 ? null : tmpIsEmailVerified == 1;
        byte tmpIsOnline = in.readByte();
        isOnline = tmpIsOnline == 0 ? null : tmpIsOnline == 1;
        byte tmpIsCeleb = in.readByte();
        isCeleb = tmpIsCeleb == 0 ? null : tmpIsCeleb == 1;
        byte tmpIsManager = in.readByte();
        isManager = tmpIsManager == 0 ? null : tmpIsManager == 1;
        liveStatus = in.readString();
        profession = in.readString();
        referralCode = in.readString();
        loginType = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(token);
        dest.writeString(userId);
        dest.writeString(userName);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(profilePic);
        dest.writeString(coverPic);
        dest.writeString(about);
        dest.writeString(mobileNumber);
        dest.writeString(email);
        dest.writeString(countryCode);
        if (referralCredits == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(referralCredits);
        }
        if (mainCredits == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(mainCredits);
        }
        dest.writeByte((byte) (isUserNameVerified == null ? 0 : isUserNameVerified ? 1 : 2));
        dest.writeByte((byte) (isPreferencesSelected == null ? 0 : isPreferencesSelected ? 1 : 2));
        dest.writeByte((byte) (isMobileVerified == null ? 0 : isMobileVerified ? 1 : 2));
        dest.writeByte((byte) (isEmailVerified == null ? 0 : isEmailVerified ? 1 : 2));
        dest.writeByte((byte) (isOnline == null ? 0 : isOnline ? 1 : 2));
        dest.writeByte((byte) (isCeleb == null ? 0 : isCeleb ? 1 : 2));
        dest.writeByte((byte) (isManager == null ? 0 : isManager ? 1 : 2));
        dest.writeString(liveStatus);
        dest.writeString(profession);
        dest.writeString(referralCode);
        dest.writeString(loginType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserLogin> CREATOR = new Creator<UserLogin>() {
        @Override
        public UserLogin createFromParcel(Parcel in) {
            return new UserLogin(in);
        }

        @Override
        public UserLogin[] newArray(int size) {
            return new UserLogin[size];
        }
    };
}
