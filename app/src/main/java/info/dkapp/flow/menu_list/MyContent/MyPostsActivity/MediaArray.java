package info.dkapp.flow.menu_list.MyContent.MyPostsActivity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Chenna on 12-07-2018.
 */

public class MediaArray {

    @SerializedName("media_id")
    @Expose
    private String mediaId;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("mediaStatus")
    @Expose
    private Boolean mediaStatus;
    @SerializedName("mediaRatio")
    @Expose
    private String mediaRatio;
    @SerializedName("mediaDesc")
    @Expose
    private String mediaDesc;
    @SerializedName("mediaCreditValue")
    @Expose
    private String mediaUrlImages;
    @SerializedName("mediaUrl")
    @Expose
    private String mediaUrl;
    @SerializedName("mediaSortOrder")
    @Expose
    private Integer mediaSortOrder;
    @SerializedName("mediaType")
    @Expose
    private String mediaType;
    @SerializedName("mediaName")
    @Expose
    private String mediaName;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getMediaStatus() {
        return mediaStatus;
    }

    public void setMediaStatus(Boolean mediaStatus) {
        this.mediaStatus = mediaStatus;
    }

    public String getMediaRatio() {
        return mediaRatio;
    }

    public void setMediaRatio(String mediaRatio) {
        this.mediaRatio = mediaRatio;
    }

    public String getMediaDesc() {
        return mediaDesc;
    }

    public void setMediaDesc(String mediaDesc) {
        this.mediaDesc = mediaDesc;
    }

    public String getMediaUrlImages() {
        return mediaUrlImages;
    }

    public void setMediaUrlImages(String mediaUrlImages) {
        this.mediaUrlImages = mediaUrlImages;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public Integer getMediaSortOrder() {
        return mediaSortOrder;
    }

    public void setMediaSortOrder(Integer mediaSortOrder) {
        this.mediaSortOrder = mediaSortOrder;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

}
