//
//package info.celebkonnect.flow.bottommenu.allcelebritiestab;
//
//import java.util.List;
//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;
//
//public class CelebrityInnerProfileDetails {
//
//    @SerializedName("_id")
//    @Expose
//    private String id;
//    @SerializedName("avtar_imgPath")
//    @Expose
//    private String avtarImgPath;
//    @SerializedName("avtar_originalname")
//    @Expose
//    private String avtarOriginalname;
//    @SerializedName("imageRatio")
//    @Expose
//    private String imageRatio;
//    @SerializedName("name")
//    @Expose
//    private String name;
//    @SerializedName("firstName")
//    @Expose
//    private String firstName;
//    @SerializedName("lastName")
//    @Expose
//    private String lastName;
//    @SerializedName("prefix")
//    @Expose
//    private String prefix;
//    @SerializedName("aboutMe")
//    @Expose
//    private String aboutMe;
//    @SerializedName("location")
//    @Expose
//    private String location;
//    @SerializedName("country")
//    @Expose
//    private String country;
//    @SerializedName("loginType")
//    @Expose
//    private String loginType;
//    @SerializedName("role")
//    @Expose
//    private String role;
//    @SerializedName("gender")
//    @Expose
//    private String gender;
//    @SerializedName("dateOfBirth")
//    @Expose
//    private String dateOfBirth;
//    @SerializedName("address")
//    @Expose
//    private String address;
//    @SerializedName("referralCode")
//    @Expose
//    private String referralCode;
//    @SerializedName("cumulativeSpent")
//    @Expose
//    private Integer cumulativeSpent;
//    @SerializedName("cumulativeEarnings")
//    @Expose
//    private Integer cumulativeEarnings;
//    @SerializedName("lastActivity")
//    @Expose
//    private String lastActivity;
//    @SerializedName("profession")
//    @Expose
//    private String profession;
//    @SerializedName("industry")
//    @Expose
//    private String industry;
//    @SerializedName("userCategory")
//    @Expose
//    private String userCategory;
//    @SerializedName("liveStatus")
//    @Expose
//    private String liveStatus;
//    @SerializedName("status")
//    @Expose
//    private Boolean status;
//    @SerializedName("isCeleb")
//    @Expose
//    private Boolean isCeleb;
//    @SerializedName("isTrending")
//    @Expose
//    private Boolean isTrending;
//    @SerializedName("isOnline")
//    @Expose
//    private Boolean isOnline;
//    @SerializedName("isEditorChoice")
//    @Expose
//    private Boolean isEditorChoice;
//    @SerializedName("isPromoted")
//    @Expose
//    private Boolean isPromoted;
//    @SerializedName("isEmailVerified")
//    @Expose
//    private Boolean isEmailVerified;
//    @SerializedName("isMobileVerified")
//    @Expose
//    private Boolean isMobileVerified;
//    @SerializedName("emailVerificationCode")
//    @Expose
//    private Integer emailVerificationCode;
//    @SerializedName("mobileVerificationCode")
//    @Expose
//    private Integer mobileVerificationCode;
//    @SerializedName("celebRecommendations")
//    @Expose
//    private Boolean celebRecommendations;
//    @SerializedName("Dnd")
//    @Expose
//    private String dnd;
//    @SerializedName("celebToManager")
//    @Expose
//    private List<String> celebToManager = null;
//    @SerializedName("iosUpdatedAt")
//    @Expose
//    private String iosUpdatedAt;
//    @SerializedName("updated_at")
//    @Expose
//    private String updatedAt;
//    @SerializedName("created_by")
//    @Expose
//    private String createdBy;
//    @SerializedName("updated_by")
//    @Expose
//    private String updatedBy;
//    @SerializedName("IsDeleted")
//    @Expose
//    private Boolean isDeleted;
//    @SerializedName("isPromoter")
//    @Expose
//    private Boolean isPromoter;
//    @SerializedName("isManager")
//    @Expose
//    private Boolean isManager;
//    @SerializedName("managerRefId")
//    @Expose
//    private String managerRefId;
//    @SerializedName("promoterRefId")
//    @Expose
//    private String promoterRefId;
//    @SerializedName("charityRefId")
//    @Expose
//    private String charityRefId;
//    @SerializedName("celebCredits")
//    @Expose
//    private String celebCredits;
//    @SerializedName("email")
//    @Expose
//    private String email;
//    @SerializedName("username")
//    @Expose
//    private String username;
//    @SerializedName("mobileNumber")
//    @Expose
//    private String mobileNumber;
//    @SerializedName("created_at")
//    @Expose
//    private String createdAt;
//    @SerializedName("callStatus")
//    @Expose
//    private String callStatus;
//    @SerializedName("isFan")
//    @Expose
//    private Boolean isFan;
//    @SerializedName("isFollower")
//    @Expose
//    private Boolean isFollower;
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getAvtarImgPath() {
//        return avtarImgPath;
//    }
//
//    public void setAvtarImgPath(String avtarImgPath) {
//        this.avtarImgPath = avtarImgPath;
//    }
//
//    public String getAvtarOriginalname() {
//        return avtarOriginalname;
//    }
//
//    public void setAvtarOriginalname(String avtarOriginalname) {
//        this.avtarOriginalname = avtarOriginalname;
//    }
//
//    public String getImageRatio() {
//        return imageRatio;
//    }
//
//    public void setImageRatio(String imageRatio) {
//        this.imageRatio = imageRatio;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public String getPrefix() {
//        return prefix;
//    }
//
//    public void setPrefix(String prefix) {
//        this.prefix = prefix;
//    }
//
//    public String getAboutMe() {
//        return aboutMe;
//    }
//
//    public void setAboutMe(String aboutMe) {
//        this.aboutMe = aboutMe;
//    }
//
//    public String getLocation() {
//        return location;
//    }
//
//    public void setLocation(String location) {
//        this.location = location;
//    }
//
//    public String getCountry() {
//        return country;
//    }
//
//    public void setCountry(String country) {
//        this.country = country;
//    }
//
//    public String getLoginType() {
//        return loginType;
//    }
//
//    public void setLoginType(String loginType) {
//        this.loginType = loginType;
//    }
//
//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }
//
//    public String getGender() {
//        return gender;
//    }
//
//    public void setGender(String gender) {
//        this.gender = gender;
//    }
//
//    public String getDateOfBirth() {
//        return dateOfBirth;
//    }
//
//    public void setDateOfBirth(String dateOfBirth) {
//        this.dateOfBirth = dateOfBirth;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getReferralCode() {
//        return referralCode;
//    }
//
//    public void setReferralCode(String referralCode) {
//        this.referralCode = referralCode;
//    }
//
//    public Integer getCumulativeSpent() {
//        return cumulativeSpent;
//    }
//
//    public void setCumulativeSpent(Integer cumulativeSpent) {
//        this.cumulativeSpent = cumulativeSpent;
//    }
//
//    public Integer getCumulativeEarnings() {
//        return cumulativeEarnings;
//    }
//
//    public void setCumulativeEarnings(Integer cumulativeEarnings) {
//        this.cumulativeEarnings = cumulativeEarnings;
//    }
//
//    public String getLastActivity() {
//        return lastActivity;
//    }
//
//    public void setLastActivity(String lastActivity) {
//        this.lastActivity = lastActivity;
//    }
//
//    public String getProfession() {
//        return profession;
//    }
//
//    public void setProfession(String profession) {
//        this.profession = profession;
//    }
//
//    public String getIndustry() {
//        return industry;
//    }
//
//    public void setIndustry(String industry) {
//        this.industry = industry;
//    }
//
//    public String getUserCategory() {
//        return userCategory;
//    }
//
//    public void setUserCategory(String userCategory) {
//        this.userCategory = userCategory;
//    }
//
//    public String getLiveStatus() {
//        return liveStatus;
//    }
//
//    public void setLiveStatus(String liveStatus) {
//        this.liveStatus = liveStatus;
//    }
//
//    public Boolean getStatus() {
//        return status;
//    }
//
//    public void setStatus(Boolean status) {
//        this.status = status;
//    }
//
//    public Boolean getIsCeleb() {
//        return isCeleb;
//    }
//
//    public void setIsCeleb(Boolean isCeleb) {
//        this.isCeleb = isCeleb;
//    }
//
//    public Boolean getIsTrending() {
//        return isTrending;
//    }
//
//    public void setIsTrending(Boolean isTrending) {
//        this.isTrending = isTrending;
//    }
//
//    public Boolean getIsOnline() {
//        return isOnline;
//    }
//
//    public void setIsOnline(Boolean isOnline) {
//        this.isOnline = isOnline;
//    }
//
//    public Boolean getIsEditorChoice() {
//        return isEditorChoice;
//    }
//
//    public void setIsEditorChoice(Boolean isEditorChoice) {
//        this.isEditorChoice = isEditorChoice;
//    }
//
//    public Boolean getIsPromoted() {
//        return isPromoted;
//    }
//
//    public void setIsPromoted(Boolean isPromoted) {
//        this.isPromoted = isPromoted;
//    }
//
//    public Boolean getIsEmailVerified() {
//        return isEmailVerified;
//    }
//
//    public void setIsEmailVerified(Boolean isEmailVerified) {
//        this.isEmailVerified = isEmailVerified;
//    }
//
//    public Boolean getIsMobileVerified() {
//        return isMobileVerified;
//    }
//
//    public void setIsMobileVerified(Boolean isMobileVerified) {
//        this.isMobileVerified = isMobileVerified;
//    }
//
//    public Integer getEmailVerificationCode() {
//        return emailVerificationCode;
//    }
//
//    public void setEmailVerificationCode(Integer emailVerificationCode) {
//        this.emailVerificationCode = emailVerificationCode;
//    }
//
//    public Integer getMobileVerificationCode() {
//        return mobileVerificationCode;
//    }
//
//    public void setMobileVerificationCode(Integer mobileVerificationCode) {
//        this.mobileVerificationCode = mobileVerificationCode;
//    }
//
//    public Boolean getCelebRecommendations() {
//        return celebRecommendations;
//    }
//
//    public void setCelebRecommendations(Boolean celebRecommendations) {
//        this.celebRecommendations = celebRecommendations;
//    }
//
//    public String getDnd() {
//        return dnd;
//    }
//
//    public void setDnd(String dnd) {
//        this.dnd = dnd;
//    }
//
//    public List<String> getCelebToManager() {
//        return celebToManager;
//    }
//
//    public void setCelebToManager(List<String> celebToManager) {
//        this.celebToManager = celebToManager;
//    }
//
//    public String getIosUpdatedAt() {
//        return iosUpdatedAt;
//    }
//
//    public void setIosUpdatedAt(String iosUpdatedAt) {
//        this.iosUpdatedAt = iosUpdatedAt;
//    }
//
//    public String getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(String updatedAt) {
//        this.updatedAt = updatedAt;
//    }
//
//    public String getCreatedBy() {
//        return createdBy;
//    }
//
//    public void setCreatedBy(String createdBy) {
//        this.createdBy = createdBy;
//    }
//
//    public String getUpdatedBy() {
//        return updatedBy;
//    }
//
//    public void setUpdatedBy(String updatedBy) {
//        this.updatedBy = updatedBy;
//    }
//
//    public Boolean getIsDeleted() {
//        return isDeleted;
//    }
//
//    public void setIsDeleted(Boolean isDeleted) {
//        this.isDeleted = isDeleted;
//    }
//
//    public Boolean getIsPromoter() {
//        return isPromoter;
//    }
//
//    public void setIsPromoter(Boolean isPromoter) {
//        this.isPromoter = isPromoter;
//    }
//
//    public Boolean getIsManager() {
//        return isManager;
//    }
//
//    public void setIsManager(Boolean isManager) {
//        this.isManager = isManager;
//    }
//
//    public String getManagerRefId() {
//        return managerRefId;
//    }
//
//    public void setManagerRefId(String managerRefId) {
//        this.managerRefId = managerRefId;
//    }
//
//    public String getPromoterRefId() {
//        return promoterRefId;
//    }
//
//    public void setPromoterRefId(String promoterRefId) {
//        this.promoterRefId = promoterRefId;
//    }
//
//    public String getCharityRefId() {
//        return charityRefId;
//    }
//
//    public void setCharityRefId(String charityRefId) {
//        this.charityRefId = charityRefId;
//    }
//
//    public String getCelebCredits() {
//        return celebCredits;
//    }
//
//    public void setCelebCredits(String celebCredits) {
//        this.celebCredits = celebCredits;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getMobileNumber() {
//        return mobileNumber;
//    }
//
//    public void setMobileNumber(String mobileNumber) {
//        this.mobileNumber = mobileNumber;
//    }
//
//    public String getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(String createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public String getCallStatus() {
//        return callStatus;
//    }
//
//    public void setCallStatus(String callStatus) {
//        this.callStatus = callStatus;
//    }
//
//    public Boolean getIsFan() {
//        return isFan;
//    }
//
//    public void setIsFan(Boolean isFan) {
//        this.isFan = isFan;
//    }
//
//    public Boolean getIsFollower() {
//        return isFollower;
//    }
//
//    public void setIsFollower(Boolean isFollower) {
//        this.isFollower = isFollower;
//    }
//
//}
