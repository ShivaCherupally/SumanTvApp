package info.dkapp.flow.bottommenu.mypaymentactionsfragment.CreditsModelDatas;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrencyType implements Parcelable {
    @SerializedName("_id")
    @Expose
    public String _id;

    @SerializedName("countryName")
    @Expose
    public String countryName;


    @SerializedName("countryCode")
    @Expose
    public String countryCode;


    @SerializedName("currencyType")
    @Expose
    public String currencyType;

    @SerializedName("currencySymbol")
    @Expose
    public String currencySymbol;

    @SerializedName("currencyValue")
    @Expose
    public Integer currencyValue;

    @SerializedName("description")
    @Expose
    public String description;


    protected CurrencyType(Parcel in) {
        _id = in.readString();
        countryName = in.readString();
        countryCode = in.readString();
        currencyType = in.readString();
        if (in.readByte() == 0) {
            currencyValue = null;
        } else {
            currencyValue = in.readInt();
        }
        description = in.readString();
    }

    public static final Creator<CurrencyType> CREATOR = new Creator<CurrencyType>() {
        @Override
        public CurrencyType createFromParcel(Parcel in) {
            return new CurrencyType(in);
        }

        @Override
        public CurrencyType[] newArray(int size) {
            return new CurrencyType[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(countryName);
        dest.writeString(countryCode);
        dest.writeString(currencyType);
        if (currencyValue == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(currencyValue);
        }
        dest.writeString(description);
    }
}
