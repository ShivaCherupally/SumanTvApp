package info.dkapp.flow.celebflow.modelData;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Uday Kumay Vurukonda on 17-May-19.
 * <p>
 * Mr.Psycho
 */
public class SignUpConditions implements Parcelable {

    public Boolean isLogin = false;
    public Boolean isForgot = false;
    public Boolean isChangePassword = false;
    public Boolean forOTPVerification = false;
    public Boolean isSocialNetwork = false;
    public Boolean isPasswordVerified = false;
    public Boolean isFromSettings = false;
    public String socialNetworkEmailID = "";
    public String firstNameSocial = "";
    public String lastNameSocial = "";
    public String userName = "";
    public String userId = "";
    public String firstName = "";
    public Boolean isMobile = false;
    public String EmailOrMobile = "";
    public String countryCodeEditProfile = "";

    public String socialMediaType = "";


    public SignUpConditions() {
    }

    protected SignUpConditions(Parcel in) {
        byte tmpIsLogin = in.readByte();
        isLogin = tmpIsLogin == 0 ? null : tmpIsLogin == 1;
        byte tmpIsForgot = in.readByte();
        isForgot = tmpIsForgot == 0 ? null : tmpIsForgot == 1;
        byte tmpIsChangePassword = in.readByte();
        isChangePassword = tmpIsChangePassword == 0 ? null : tmpIsChangePassword == 1;
        byte tmpForOTPVerification = in.readByte();
        forOTPVerification = tmpForOTPVerification == 0 ? null : tmpForOTPVerification == 1;
        byte tmpIsSocialNetwork = in.readByte();
        isSocialNetwork = tmpIsSocialNetwork == 0 ? null : tmpIsSocialNetwork == 1;
        byte tmpIsPasswordVerified = in.readByte();
        isPasswordVerified = tmpIsPasswordVerified == 0 ? null : tmpIsPasswordVerified == 1;
        byte tmpIsFromSettings = in.readByte();
        isFromSettings = tmpIsFromSettings == 0 ? null : tmpIsFromSettings == 1;
        socialNetworkEmailID = in.readString();
        firstNameSocial = in.readString();
        lastNameSocial = in.readString();
        userName = in.readString();
        userId = in.readString();
        firstName = in.readString();
        byte tmpIsMobile = in.readByte();
        isMobile = tmpIsMobile == 0 ? null : tmpIsMobile == 1;
        EmailOrMobile = in.readString();
        countryCodeEditProfile = in.readString();
        socialMediaType = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isLogin == null ? 0 : isLogin ? 1 : 2));
        dest.writeByte((byte) (isForgot == null ? 0 : isForgot ? 1 : 2));
        dest.writeByte((byte) (isChangePassword == null ? 0 : isChangePassword ? 1 : 2));
        dest.writeByte((byte) (forOTPVerification == null ? 0 : forOTPVerification ? 1 : 2));
        dest.writeByte((byte) (isSocialNetwork == null ? 0 : isSocialNetwork ? 1 : 2));
        dest.writeByte((byte) (isPasswordVerified == null ? 0 : isPasswordVerified ? 1 : 2));
        dest.writeByte((byte) (isFromSettings == null ? 0 : isFromSettings ? 1 : 2));
        dest.writeString(socialNetworkEmailID);
        dest.writeString(firstNameSocial);
        dest.writeString(lastNameSocial);
        dest.writeString(userName);
        dest.writeString(userId);
        dest.writeString(firstName);
        dest.writeByte((byte) (isMobile == null ? 0 : isMobile ? 1 : 2));
        dest.writeString(EmailOrMobile);
        dest.writeString(countryCodeEditProfile);
        dest.writeString(socialMediaType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SignUpConditions> CREATOR = new Creator<SignUpConditions>() {
        @Override
        public SignUpConditions createFromParcel(Parcel in) {
            return new SignUpConditions(in);
        }

        @Override
        public SignUpConditions[] newArray(int size) {
            return new SignUpConditions[size];
        }
    };

    public Boolean getLogin() {
        return isLogin;
    }

    public void setLogin(Boolean login) {
        isLogin = login;
    }

    public Boolean getForgot() {
        return isForgot;
    }

    public void setForgot(Boolean forgot) {
        isForgot = forgot;
    }

    public Boolean getChangePassword() {
        return isChangePassword;
    }

    public void setChangePassword(Boolean changePassword) {
        isChangePassword = changePassword;
    }

    public Boolean getForOTPVerification() {
        return forOTPVerification;
    }

    public void setForOTPVerification(Boolean forOTPVerification) {
        this.forOTPVerification = forOTPVerification;
    }

    public Boolean getSocialNetwork() {
        return isSocialNetwork;
    }

    public void setSocialNetwork(Boolean socialNetwork) {
        isSocialNetwork = socialNetwork;
    }

    public Boolean getPasswordVerified() {
        return isPasswordVerified;
    }

    public void setPasswordVerified(Boolean passwordVerified) {
        isPasswordVerified = passwordVerified;
    }

    public Boolean getFromSettings() {
        return isFromSettings;
    }

    public void setFromSettings(Boolean fromSettings) {
        isFromSettings = fromSettings;
    }

    public String getSocialNetworkEmailID() {
        return socialNetworkEmailID;
    }

    public void setSocialNetworkEmailID(String socialNetworkEmailID) {
        this.socialNetworkEmailID = socialNetworkEmailID;
    }

    public String getFirstNameSocial() {
        return firstNameSocial;
    }

    public void setFirstNameSocial(String firstNameSocial) {
        this.firstNameSocial = firstNameSocial;
    }

    public String getLastNameSocial() {
        return lastNameSocial;
    }

    public void setLastNameSocial(String lastNameSocial) {
        this.lastNameSocial = lastNameSocial;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Boolean getMobile() {
        return isMobile;
    }

    public void setMobile(Boolean mobile) {
        isMobile = mobile;
    }

    public String getEmailOrMobile() {
        return EmailOrMobile;
    }

    public void setEmailOrMobile(String emailOrMobile) {
        EmailOrMobile = emailOrMobile;
    }

    public String getCountryCodeEditProfile() {
        return countryCodeEditProfile;
    }

    public void setCountryCodeEditProfile(String countryCodeEditProfile) {
        this.countryCodeEditProfile = countryCodeEditProfile;
    }

    public String getSocialMediaType() {
        return socialMediaType;
    }

    public void setSocialMediaType(String socialMediaType) {
        this.socialMediaType = socialMediaType;
    }
}
