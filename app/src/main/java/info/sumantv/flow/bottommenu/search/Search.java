package info.sumantv.flow.bottommenu.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Chenna on 21-08-2018.
 */

public class Search implements Serializable {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("isCeleb")
    @Expose
    private Boolean isCeleb;
    @SerializedName("isPromoted")
    @Expose
    private Boolean isPromoted;
    @SerializedName("isEditorChoice")
    @Expose
    private Boolean isEditorChoice;
    @SerializedName("isOnline")
    @Expose
    private Boolean isOnline;
    @SerializedName("isTrending")
    @Expose
    private Boolean isTrending;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("avtar_imgPath")
    @Expose
    private String avtarImgPath;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("profession")
    @Expose
    private String profession;
    @SerializedName("aboutMe")
    @Expose
    private String aboutMe;
    @SerializedName("isManager")
    @Expose
    private Boolean isManager;
    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("reqID")
    @Expose
    private String reqID;

    @SerializedName("isCelebAccepted")
    @Expose
    private Boolean isCelebAccepted;
    @SerializedName("isManagerAccepted")
    @Expose
    private Boolean isManagerAccepted;
    @SerializedName("isCelebReqNew")
    @Expose
    private Boolean isCelebReqNew;
    @SerializedName("isManagerReqNew")
    @Expose
    private Boolean isManagerReqNew;
    @SerializedName("createdAt")
    @Expose
    private String  createdAt;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getCeleb() {
        return isCeleb;
    }

    public void setCeleb(Boolean celeb) {
        isCeleb = celeb;
    }

    public Boolean getPromoted() {
        return isPromoted;
    }

    public void setPromoted(Boolean promoted) {
        isPromoted = promoted;
    }

    public Boolean getEditorChoice() {
        return isEditorChoice;
    }

    public void setEditorChoice(Boolean editorChoice) {
        isEditorChoice = editorChoice;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }

    public Boolean getTrending() {
        return isTrending;
    }

    public void setTrending(Boolean trending) {
        isTrending = trending;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAvtarImgPath() {
        return avtarImgPath;
    }

    public void setAvtarImgPath(String avtarImgPath) {
        this.avtarImgPath = avtarImgPath;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }


    public Boolean getManager() {
        return isManager;
    }

    public void setManager(Boolean manager) {
        isManager = manager;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReqID() {
        return reqID;
    }

    public void setReqID(String reqID) {
        this.reqID = reqID;
    }

    public Boolean getCelebAccepted() {
        return isCelebAccepted;
    }

    public void setCelebAccepted(Boolean celebAccepted) {
        isCelebAccepted = celebAccepted;
    }

    public Boolean getManagerAccepted() {
        return isManagerAccepted;
    }

    public void setManagerAccepted(Boolean managerAccepted) {
        isManagerAccepted = managerAccepted;
    }

    public Boolean getCelebReqNew() {
        return isCelebReqNew;
    }

    public void setCelebReqNew(Boolean celebReqNew) {
        isCelebReqNew = celebReqNew;
    }

    public Boolean getManagerReqNew() {
        return isManagerReqNew;
    }

    public void setManagerReqNew(Boolean managerReqNew) {
        isManagerReqNew = managerReqNew;
    }
}
