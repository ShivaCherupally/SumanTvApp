package info.dkapp.flow.menu_list.Settings.Charity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Chenna on 15-05-2018.
 */

public class PayoutSetting {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("ecommerce")
    @Expose
    private Integer ecommerce;
    @SerializedName("chat")
    @Expose
    private Integer chat;
    @SerializedName("audio")
    @Expose
    private Integer audio;
    @SerializedName("video")
    @Expose
    private Integer video;
    @SerializedName("memberId")
    @Expose
    private String memberId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getEcommerce() {
        return ecommerce;
    }

    public void setEcommerce(Integer ecommerce) {
        this.ecommerce = ecommerce;
    }

    public Integer getChat() {
        return chat;
    }

    public void setChat(Integer chat) {
        this.chat = chat;
    }

    public Integer getAudio() {
        return audio;
    }

    public void setAudio(Integer audio) {
        this.audio = audio;
    }

    public Integer getVideo() {
        return video;
    }

    public void setVideo(Integer video) {
        this.video = video;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

}
