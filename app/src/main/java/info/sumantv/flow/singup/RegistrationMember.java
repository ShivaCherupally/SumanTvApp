package info.sumantv.flow.singup;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.http.entity.mime.content.ContentBody;

/**
 * Created by Uday Kumay Vurukonda on 23-Apr-19.
 * <p>
 * Mr.Psycho
 */
public class RegistrationMember implements Parcelable {

    @SerializedName("medium")
    @Expose
    public String medium;
    @SerializedName("mobileNumber")
    @Expose
    public String mobileNumber;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("country")
    @Expose
    public String country;

    @SerializedName("OTP")
    @Expose
    public String oTP;

    @SerializedName("username")
    @Expose
    public String username;
    @SerializedName("mode")
    @Expose
    public String mode;
    @SerializedName("password")
    @Expose
    public String password;

    @SerializedName("imageRatio")
    @Expose
    public float imageRatio;

    @SerializedName("image")
    @Expose
    public ContentBody image;

    @SerializedName("firstName")
    @Expose
    public String firstName;

    @SerializedName("lastName")
    @Expose
    public String lastName;



    public RegistrationMember(String medium, String email, String oTP, String username, String mode, String password,String firstName,String lastName) {
        this.medium = medium;
        this.email = email;
        this.oTP = oTP;
        this.username = username;
        this.mode = mode;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public RegistrationMember(String medium, String mobileNumber, String country, String oTP, String username, String mode, String password,String firstName,String lastName) {
        this.medium = medium;
        this.mobileNumber = mobileNumber;
        this.country = country;
        this.oTP = oTP;
        this.username = username;
        this.mode = mode;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public RegistrationMember( String username, String mode) {

        this.username = username;
        this.mode = mode;

    }

    protected RegistrationMember(Parcel in) {
        medium = in.readString();
        mobileNumber = in.readString();
        email = in.readString();
        country = in.readString();
        oTP = in.readString();
        username = in.readString();
        mode = in.readString();
        password = in.readString();
        imageRatio = in.readFloat();
        firstName = in.readString();
        lastName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(medium);
        dest.writeString(mobileNumber);
        dest.writeString(email);
        dest.writeString(country);
        dest.writeString(oTP);
        dest.writeString(username);
        dest.writeString(mode);
        dest.writeString(password);
        dest.writeFloat(imageRatio);
        dest.writeString(firstName);
        dest.writeString(lastName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RegistrationMember> CREATOR = new Creator<RegistrationMember>() {
        @Override
        public RegistrationMember createFromParcel(Parcel in) {
            return new RegistrationMember(in);
        }

        @Override
        public RegistrationMember[] newArray(int size) {
            return new RegistrationMember[size];
        }
    };
}
