package info.dkapp.flow.bottommenu.mypaymentactionsfragment.CreditsModelDatas;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreditPrice implements Parcelable {


    @SerializedName("creditPrice")
    @Expose
    public CreditPrice creditPriceAvailableData;

    @SerializedName("totalPrice")
    @Expose
    public String totalPrice;

    @SerializedName("currencySymbol")
    @Expose
    public String currencySymbol;


    protected CreditPrice(Parcel in) {
        totalPrice = in.readString();
        currencySymbol = in.readString();
    }

    public static final Creator<CreditPrice> CREATOR = new Creator<CreditPrice>() {
        @Override
        public CreditPrice createFromParcel(Parcel in) {
            return new CreditPrice(in);
        }

        @Override
        public CreditPrice[] newArray(int size) {
            return new CreditPrice[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(totalPrice);
        dest.writeString(currencySymbol);
    }
}
