package info.sumantv.flow.celebflow.EditProfileActivity;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Shiva on 2/22/2018.
 */

public class GetProfileData {
    @SerializedName("_id")
    private String _id;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("username")
    private String username;

    @SerializedName("profession")
    private String profession;

    @SerializedName("industry")
    private String industry;

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("prefix")
    private String prefix;




    @SerializedName("Dnd")
    private String dnd;

    public String getDnd() {
        return dnd;
    }

    public void setDnd(String dnd) {
        this.dnd = dnd;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
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
    //


    @SerializedName("location")
    private String location;


    @SerializedName("dateOfBirth")
    private String dateOfBirth;


    @SerializedName("availableCredits")
    private String availableCredits;


    @SerializedName("gender")
    private String gender;


    @SerializedName("address")
    private String address;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @SerializedName("avtar_imgPath") //avtar_originalname avtar_imgPath
    private String avtar_imgPath;

    @SerializedName("mobileNumber")
    private String mobileNumber;


    @SerializedName("loginType")
    private String loginType;

    @SerializedName("profilePic")
    private String profilePic;


    @SerializedName("isCeleb")
    private Boolean isCeleb;


    @SerializedName("isManager")
    private Boolean isManager;



    @SerializedName("status")
    private String status;


    @SerializedName("liveStatus")
    private String liveStatus;

    public String getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(String liveStatus) {
        this.liveStatus = liveStatus;
    }

    @SerializedName("role")
    private String role;

    @SerializedName("isMobileVerified")
    private Boolean isMobileVerified;


    @SerializedName("isEmailVerified")
    private Boolean isEmailVerified;

    public Boolean getMobileVerified() {
        return isMobileVerified;
    }

    public void setMobileVerified(Boolean mobileVerified) {
        isMobileVerified = mobileVerified;
    }

    public Boolean getEmailVerified() {
        return isEmailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        isEmailVerified = emailVerified;
    }

    @SerializedName("isOnline")
    private Boolean isOnline;

    @SerializedName("aboutMe")
    private String aboutMe;

    @SerializedName("country")
    private String country;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }
    //aboutMe

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAvailableCredits() {
        return availableCredits;
    }

    public void setAvailableCredits(String availableCredits) {
        this.availableCredits = availableCredits;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvtar_imgPath() {
        return avtar_imgPath;
    }

    public void setAvtar_imgPath(String avtar_imgPath) {
        this.avtar_imgPath = avtar_imgPath;
    }

    public String getRole() {
        return role;
    }


    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public Boolean getCeleb() {
        return isCeleb;
    }

    public Boolean getManager() {
        return isManager;
    }

    public void setManager(Boolean manager) {
        isManager = manager;
    }

    public void setCeleb(Boolean celeb) {

        isCeleb = celeb;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public GetProfileData(String _id, String name, String email, String username, String profession, String location, String dateOfBirth, String availableCredits, String gender, String avtar_imgPath, String mobileNumber, String loginType, String profilePic, Boolean isCeleb, Boolean isManager ,String status, String role) {
        this._id = _id;
        this.name = name;
        this.email = email;
        this.username = username;
        this.profession = profession;
        this.location = location;
        this.dateOfBirth = dateOfBirth;
        this.availableCredits = availableCredits;
        this.gender = gender;
        this.avtar_imgPath = avtar_imgPath;
        this.mobileNumber = mobileNumber;
        this.loginType = loginType;
        this.profilePic = profilePic;
        this.isCeleb = isCeleb;
        this.isManager = isManager;
        this.status = status;
        this.role = role;
        Log.e("paramsGetProfileData ", toString() + "");
    }

    //


    @Override
    public String toString() {
        return "GetProfileData{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", profession='" + profession + '\'' +
                ", location='" + location + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", availableCredits='" + availableCredits + '\'' +
                ", gender='" + gender + '\'' +
                ", avtar_imgPath='" + avtar_imgPath + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", loginType='" + loginType + '\'' +
                ", profilePic='" + profilePic + '\'' +
                ", isCeleb='" + isCeleb + '\'' +
                ", isManager='" + isManager + '\'' +
                ", status='" + status + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
