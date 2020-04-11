package info.dkapp.flow.otp;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Uday Kumay Vurukonda on 12-Feb-19.
 * <p>
 * Mr.Psycho
 */
public class OTPVerify implements Parcelable {


    @SerializedName("type")
    @Expose
    public String type;

    @SerializedName("mobileNumber")
    @Expose
    public String mobileNumber;

    @SerializedName("verificationcode")
    @Expose
    public String verificationcode;



    public OTPVerify(String type, String mobileNumber, String verificationcode) {
        this.type = type;
        this.mobileNumber = mobileNumber;
        this.verificationcode = verificationcode;
    }

    public OTPVerify(String type, String mobileNumber) {
        this.type = type;
        this.mobileNumber = mobileNumber;
    }

    protected OTPVerify(Parcel in) {
        type = in.readString();
        mobileNumber = in.readString();
        verificationcode = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(mobileNumber);
        dest.writeString(verificationcode);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OTPVerify> CREATOR = new Creator<OTPVerify>() {
        @Override
        public OTPVerify createFromParcel(Parcel in) {
            return new OTPVerify(in);
        }

        @Override
        public OTPVerify[] newArray(int size) {
            return new OTPVerify[size];
        }
    };
}
