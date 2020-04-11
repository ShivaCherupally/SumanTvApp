package info.dkapp.flow.bottommenu.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Uday Kumay Vurukonda on 12-Feb-19.
 * <p>
 * Mr.Psycho
 */
public class Credits implements Parcelable {

    @SerializedName("cumulativeCreditValue")
    public Double cumulativeCreditValue;

    @SerializedName("referralCreditValue")
    public Double referralCreditValue;


    public Double getCumulativeCreditValue() {
        return cumulativeCreditValue;
    }

    public void setCumulativeCreditValue(Double cumulativeCreditValue) {
        this.cumulativeCreditValue = cumulativeCreditValue;
    }

    public Double getReferralCreditValue() {
        return referralCreditValue;
    }

    public void setReferralCreditValue(Double referralCreditValue) {
        this.referralCreditValue = referralCreditValue;
    }

    public static Creator<Credits> getCREATOR() {
        return CREATOR;
    }

    protected Credits(Parcel in) {
        cumulativeCreditValue = in.readDouble();
        referralCreditValue = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(cumulativeCreditValue);
        dest.writeDouble(referralCreditValue);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Credits> CREATOR = new Creator<Credits>() {
        @Override
        public Credits createFromParcel(Parcel in) {
            return new Credits(in);
        }

        @Override
        public Credits[] newArray(int size) {
            return new Credits[size];
        }
    };
}
