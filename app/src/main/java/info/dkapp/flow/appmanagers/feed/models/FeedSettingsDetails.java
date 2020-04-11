package info.dkapp.flow.appmanagers.feed.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Uday Kumay Vurukonda on 18-Nov-19.
 * <p>
 * Mr.Psycho
 */
public class FeedSettingsDetails implements Parcelable
{

    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("isEnabled")
    @Expose
    public Boolean isEnabled;
    @SerializedName("createdBy")
    @Expose
    public String createdBy;
    @SerializedName("updatedBy")
    @Expose
    public String updatedBy;
    @SerializedName("memberId")
    @Expose
    public String memberId;
    @SerializedName("celebId")
    @Expose
    public String celebId;
    @SerializedName("feedId")
    @Expose
    public String feedId;
    @SerializedName("createdAt")
    @Expose
    public String createdAt;
    @SerializedName("updatedAt")
    @Expose
    public String updatedAt;
    public final static Parcelable.Creator<FeedSettingsDetails> CREATOR = new Creator<FeedSettingsDetails>() {


        @SuppressWarnings({
                "unchecked"
        })
        public FeedSettingsDetails createFromParcel(Parcel in) {
            return new FeedSettingsDetails(in);
        }

        public FeedSettingsDetails[] newArray(int size) {
            return (new FeedSettingsDetails[size]);
        }

    }
            ;

    protected FeedSettingsDetails(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.isEnabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.createdBy = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedBy = ((String) in.readValue((String.class.getClassLoader())));
        this.memberId = ((String) in.readValue((String.class.getClassLoader())));
        this.celebId = ((String) in.readValue((String.class.getClassLoader())));
        this.feedId = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
    }

    public FeedSettingsDetails() {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(isEnabled);
        dest.writeValue(createdBy);
        dest.writeValue(updatedBy);
        dest.writeValue(memberId);
        dest.writeValue(celebId);
        dest.writeValue(feedId);
        dest.writeValue(createdAt);
        dest.writeValue(updatedAt);
    }

    public int describeContents() {
        return 0;
    }

}
