package info.sumantv.flow.celebflow.modelData;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SelfProfile implements Parcelable
{

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
    @SerializedName("email")
    @Expose
    public String email;
    public final static Parcelable.Creator<SelfProfile> CREATOR = new Creator<SelfProfile>() {


        @SuppressWarnings({
                "unchecked"
        })
        public SelfProfile createFromParcel(Parcel in) {
            return new SelfProfile(in);
        }

        public SelfProfile[] newArray(int size) {
            return (new SelfProfile[size]);
        }

    }
            ;

    protected SelfProfile(Parcel in) {
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
        this.email = ((String) in.readValue((String.class.getClassLoader())));
    }

    public SelfProfile() {
    }

    public void writeToParcel(Parcel dest, int flags) {
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
        dest.writeValue(email);
    }

    public int describeContents() {
        return 0;
    }

}
