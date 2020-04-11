
package info.dkapp.flow.bottommenu.models;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import info.dkapp.flow.appmanagers.feed.models.Media;

public class MemberMedium implements Parcelable
{

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("media")
    @Expose
    private ArrayList<Media> media = null;
    public final static Creator<MemberMedium> CREATOR = new Creator<MemberMedium>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MemberMedium createFromParcel(Parcel in) {
            return new MemberMedium(in);
        }

        public MemberMedium[] newArray(int size) {
            return (new MemberMedium[size]);
        }

    }
    ;

    protected MemberMedium(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.media, (Medium.class.getClassLoader()));
    }

    public MemberMedium() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Media> getMedia() {
        return media;
    }

    public void setMedia(ArrayList<Media> media) {
        this.media = media;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeList(media);
    }

    public int describeContents() {
        return  0;
    }

}
