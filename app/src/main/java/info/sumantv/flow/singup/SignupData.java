package info.sumantv.flow.singup;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

/**
 * Created by Uday Kumay Vurukonda on 24-Apr-19.
 * <p>
 * Mr.Psycho
 */
public class SignupData implements Parcelable {

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

    @SerializedName("firstName")
    @Expose
    public String firstName;

    @SerializedName("lastName")
    @Expose
    public String lastName;

    @SerializedName("password")
    @Expose
    public String password;

    @SerializedName("loginType")
    @Expose
    public String loginType;

    @SerializedName("mode")
    @Expose
    public String mode;


    @SerializedName("location")
    @Expose
    public JSONObject location;

    public SignupData(String medium, String mobileNumber, String country, String firstName, String lastName,
                      String password, String loginType, String mode, JSONObject location) {
        this.medium = medium;
        this.mobileNumber = mobileNumber;
        this.country = country;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.loginType = loginType;
        this.mode = mode;
        this.location = location;

        Log.e("signupInfo", toString() + "");
    }

    public SignupData(String medium, String email, String country, String firstName, String lastName,
                      String password, String loginType, String mode, JSONObject location, boolean emailAccess) {
        this.medium = medium;
        this.email = email;
        this.country = country;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.loginType = loginType;
        this.mode = mode;
        this.location = location;
        Log.e("signupInfo", toStringEmail() + "");
    }


    protected SignupData(Parcel in) {
        medium = in.readString();
        mobileNumber = in.readString();
        email = in.readString();
        country = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        password = in.readString();
        loginType = in.readString();
        mode = in.readString();
    }

    public static final Creator<SignupData> CREATOR = new Creator<SignupData>() {
        @Override
        public SignupData createFromParcel(Parcel in) {
            return new SignupData(in);
        }

        @Override
        public SignupData[] newArray(int size) {
            return new SignupData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(medium);
        parcel.writeString(mobileNumber);
        parcel.writeString(email);
        parcel.writeString(country);
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(password);
        parcel.writeString(loginType);
        parcel.writeString(mode);
    }

    @Override
    public String toString() {
        return "SignupData{" +
                "medium='" + medium + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", country='" + country + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", loginType='" + loginType + '\'' +
                ", mode='" + mode + '\'' +
                ", location=" + location +
                '}';
    }

    public String toStringEmail() {
        return "SignupData{" +
                "medium='" + medium + '\'' +
                ", email='" + email + '\'' +
                ", country='" + country + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", loginType='" + loginType + '\'' +
                ", mode='" + mode + '\'' +
                ", location=" + location +
                '}';
    }


}
