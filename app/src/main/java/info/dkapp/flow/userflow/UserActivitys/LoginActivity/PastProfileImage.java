package info.dkapp.flow.userflow.UserActivitys.LoginActivity;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Uday Kumay Vurukonda on 12-Feb-19.
 * <p>
 * Mr.Psycho
 */
public class PastProfileImage implements Serializable, Parcelable
{

    @SerializedName("avtar_imgPath")
    @Expose
    public String avtarImgPath;
    @SerializedName("mimetype")
    @Expose
    public String mimetype;
    @SerializedName("size")
    @Expose
    public Integer size;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    public final static Parcelable.Creator<PastProfileImage> CREATOR = new Creator<PastProfileImage>() {


        @SuppressWarnings({
                "unchecked"
        })
        public PastProfileImage createFromParcel(Parcel in) {
            return new PastProfileImage(in);
        }

        public PastProfileImage[] newArray(int size) {
            return (new PastProfileImage[size]);
        }

    }
            ;
    private final static long serialVersionUID = 8349201199160721568L;

    protected PastProfileImage(Parcel in) {
        this.avtarImgPath = ((String) in.readValue((String.class.getClassLoader())));
        this.mimetype = ((String) in.readValue((String.class.getClassLoader())));
        this.size = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
    }

    public PastProfileImage() {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(avtarImgPath);
        dest.writeValue(mimetype);
        dest.writeValue(size);
        dest.writeValue(updatedAt);
    }

    public int describeContents() {
        return 0;
    }

}
