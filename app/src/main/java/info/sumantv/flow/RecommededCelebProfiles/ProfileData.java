package info.sumantv.flow.RecommededCelebProfiles;

/**
 * Created by Shiva on 2/24/2018.
 */

public class ProfileData {

    //username

    private String username;

    private String name;
    private String profession;
    private String aboutMe;

    private Boolean isCeleb;
    private Boolean isManager;


    private Boolean isOnline;
    private Boolean isEditorChoice;
    private Boolean isPromoted;
    private Boolean isTrending;

    private int cumulativeCreditValue;
    private String lastActivity;

    private String follow_id;
    private String follow_status;
    private String lastName;

    private String liveStatus;

    public String getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(String liveStatus) {
        this.liveStatus = liveStatus;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFollow_id() {
        return follow_id;
    }

    public void setFollow_id(String follow_id) {
        this.follow_id = follow_id;
    }

    public String getFollow_status() {
        return follow_status;
    }

    public void setFollow_status(String follow_status) {
        this.follow_status = follow_status;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public int getCumulativeCreditValue() {
        return cumulativeCreditValue;
    }

    public void setCumulativeCreditValue(int cumulativeCreditValue) {
        this.cumulativeCreditValue = cumulativeCreditValue;
    }

    public String getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(String lastActivity) {
        this.lastActivity = lastActivity;
    }

    private String memberId;


    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }

    public Boolean getEditorChoice() {
        return isEditorChoice;
    }

    public void setEditorChoice(Boolean editorChoice) {
        isEditorChoice = editorChoice;
    }

    public Boolean getPromoted() {
        return isPromoted;
    }

    public void setPromoted(Boolean promoted) {
        isPromoted = promoted;
    }

    public Boolean getTrending() {
        return isTrending;
    }

    public void setTrending(Boolean trending) {
        isTrending = trending;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    private String _id;

    private Boolean status;

    private String avtar_imgPath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getAvtar_imgPath() {
        return avtar_imgPath;
    }

    public void setAvtar_imgPath(String avtar_imgPath) {
        this.avtar_imgPath = avtar_imgPath;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Boolean getCeleb() {
        return isCeleb;
    }

    public void setCeleb(Boolean celeb) {
        isCeleb = celeb;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getManager() {
        return isManager;
    }

    public void setManager(Boolean manager) {
        isManager = manager;
    }
}
