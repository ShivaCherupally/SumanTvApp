
package info.dkapp.flow.bottommenu.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Medium implements Parcelable
{

    @SerializedName("src")
    @Expose
    private Src src;
    @SerializedName("mediaSize")
    @Expose
    private Double mediaSize;
    @SerializedName("mediaRatio")
    @Expose
    private Double mediaRatio;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("mediaType")
    @Expose
    private String mediaType;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    public final static Creator<Medium> CREATOR = new Creator<Medium>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Medium createFromParcel(Parcel in) {
            return new Medium(in);
        }

        public Medium[] newArray(int size) {
            return (new Medium[size]);
        }

    }
    ;

    protected Medium(Parcel in) {
        this.src = ((Src) in.readValue((Src.class.getClassLoader())));
        this.mediaSize = ((Double) in.readValue((Double.class.getClassLoader())));
        this.mediaRatio = ((Double) in.readValue((Double.class.getClassLoader())));
        this.status = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.mediaType = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Medium() {
    }

    public Src getSrc() {
        return src;
    }

    public void setSrc(Src src) {
        this.src = src;
    }

    public Double getMediaSize() {
        return mediaSize;
    }

    public void setMediaSize(Double mediaSize) {
        this.mediaSize = mediaSize;
    }

    public Double getMediaRatio() {
        return mediaRatio;
    }

    public void setMediaRatio(Double mediaRatio) {
        this.mediaRatio = mediaRatio;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(src);
        dest.writeValue(mediaSize);
        dest.writeValue(mediaRatio);
        dest.writeValue(status);
        dest.writeValue(id);
        dest.writeValue(mediaType);
        dest.writeValue(createdAt);
    }

    public int describeContents() {
        return  0;
    }



    public class Src implements Parcelable
    {

        @SerializedName("mediaUrl")
        @Expose
        private String mediaUrl;
        @SerializedName("thumbnail")
        @Expose
        private String thumbnail;
        @SerializedName("mediaName")
        @Expose
        private String mediaName;
        @SerializedName("videoUrl")
        @Expose
        private String videoUrl;
        public  Parcelable.Creator<Src> CREATOR = new Creator<Src>() {


            @SuppressWarnings({
                    "unchecked"
            })
            public Src createFromParcel(Parcel in) {
                return new Src(in);
            }

            public Src[] newArray(int size) {
                return (new Src[size]);
            }

        }
                ;

        protected Src(Parcel in) {
            this.mediaUrl = ((String) in.readValue((String.class.getClassLoader())));
            this.mediaName = ((String) in.readValue((String.class.getClassLoader())));
            this.videoUrl = ((String) in.readValue((String.class.getClassLoader())));
        }

        public Src() {
        }

        public String getMediaUrl() {
            return mediaUrl;
        }

        public void setMediaUrl(String mediaUrl) {
            this.mediaUrl = mediaUrl;
        }

        public String getMediaName() {
            return mediaName;
        }

        public void setMediaName(String mediaName) {
            this.mediaName = mediaName;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(mediaUrl);
            dest.writeValue(mediaName);
            dest.writeValue(videoUrl);
        }

        public int describeContents() {
            return  0;
        }

    }

}
