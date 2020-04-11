package info.sumantv.flow.celebflow.modelData;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Uday Kumay Vurukonda on 24-Jul-19.
 * <p>
 * Mr.Psycho
 */
public class ActivityOnProfile implements Parcelable
{

    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("username")
    @Expose
    public String username;
    @SerializedName("mobileNumber")
    @Expose
    public String mobileNumber;
    @SerializedName("aboutMe")
    @Expose
    public String aboutMe;
    @SerializedName("prefix")
    @Expose
    public String prefix;
    @SerializedName("lastName")
    @Expose
    public String lastName;
    @SerializedName("firstName")
    @Expose
    public String firstName;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("imageRatio")
    @Expose
    public String imageRatio;
    @SerializedName("avtar_originalname")
    @Expose
    public String avtarOriginalname;
    @SerializedName("avtar_imgPath")
    @Expose
    public String avtarImgPath;
    public final static Parcelable.Creator<ActivityOnProfile> CREATOR = new Creator<ActivityOnProfile>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ActivityOnProfile createFromParcel(Parcel in) {
            return new ActivityOnProfile(in);
        }

        public ActivityOnProfile[] newArray(int size) {
            return (new ActivityOnProfile[size]);
        }

    }
            ;

    protected ActivityOnProfile(Parcel in) {
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.username = ((String) in.readValue((String.class.getClassLoader())));
        this.mobileNumber = ((String) in.readValue((String.class.getClassLoader())));
        this.aboutMe = ((String) in.readValue((String.class.getClassLoader())));
        this.prefix = ((String) in.readValue((String.class.getClassLoader())));
        this.lastName = ((String) in.readValue((String.class.getClassLoader())));
        this.firstName = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.imageRatio = ((String) in.readValue((String.class.getClassLoader())));
        this.avtarOriginalname = ((String) in.readValue((String.class.getClassLoader())));
        this.avtarImgPath = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ActivityOnProfile() {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(email);
        dest.writeValue(username);
        dest.writeValue(mobileNumber);
        dest.writeValue(aboutMe);
        dest.writeValue(prefix);
        dest.writeValue(lastName);
        dest.writeValue(firstName);
        dest.writeValue(name);
        dest.writeValue(imageRatio);
        dest.writeValue(avtarOriginalname);
        dest.writeValue(avtarImgPath);
    }

    public int describeContents() {
        return 0;
    }

}
