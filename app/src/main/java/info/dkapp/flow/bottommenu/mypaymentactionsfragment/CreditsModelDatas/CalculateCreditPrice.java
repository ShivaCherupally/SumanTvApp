package info.dkapp.flow.bottommenu.mypaymentactionsfragment.CreditsModelDatas;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CalculateCreditPrice implements Parcelable {

    @SerializedName("creditValue")
    @Expose
    public long creditValue;

    @SerializedName("country")
    @Expose
    public String country;


    public CalculateCreditPrice(long creditValue, String country) {
        this.creditValue = creditValue;
        this.country = country;
    }


    protected CalculateCreditPrice(Parcel in) {
        creditValue = in.readLong();
        country = in.readString();
    }

    public static final Creator<CalculateCreditPrice> CREATOR = new Creator<CalculateCreditPrice>() {
        @Override
        public CalculateCreditPrice createFromParcel(Parcel in) {
            return new CalculateCreditPrice(in);
        }

        @Override
        public CalculateCreditPrice[] newArray(int size) {
            return new CalculateCreditPrice[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(creditValue);
        dest.writeString(country);
    }
}
