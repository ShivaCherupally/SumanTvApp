package info.sumantv.flow.retrofitcall;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Uday Kumay Vurukonda on 12-Feb-19.
 * <p>
 * Mr.Psycho
 */
public class OTPSendManager implements Parcelable {

    @SerializedName("memberId")
    @Expose
    public String memberId;

    @SerializedName("medium")
    @Expose
    public String medium;

    @SerializedName("reason")
    @Expose
    public String reason;

    @SerializedName("OTP")
    @Expose
    public String OTP;



    public OTPSendManager(String memberId, String medium, String OTP) {
        this.memberId = memberId;
        this.medium = medium;
        this.OTP = OTP;
    }

    protected OTPSendManager(Parcel in) {
        memberId = in.readString();
        medium = in.readString();
        reason = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(memberId);
        dest.writeString(medium);
        dest.writeString(reason);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OTPVerifyManager> CREATOR = new Creator<OTPVerifyManager>() {
        @Override
        public OTPVerifyManager createFromParcel(Parcel in) {
            return new OTPVerifyManager(in);
        }

        @Override
        public OTPVerifyManager[] newArray(int size) {
            return new OTPVerifyManager[size];
        }
    };
}
