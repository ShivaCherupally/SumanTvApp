package info.sumantv.flow.bottommenu.mypaymentactionsfragment.CreditsModelDatas;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreditPackageInfo implements Parcelable {
    @SerializedName("_id")
    @Expose
    public String _id;

    @SerializedName("createdAt")
    @Expose
    public String createdAt;
    @SerializedName("checkoutCurrencyINR")
    @Expose
    public Double checkoutCurrencyINR;

    @SerializedName("amount")
    @Expose
    public Double amount;

    @SerializedName("packageName")
    @Expose
    public String packageName;

    @SerializedName("packageType")
    @Expose
    public String packageType;

    @SerializedName("countryCode")
    @Expose
    public String countryCode;

    @SerializedName("startColor")
    @Expose
    public String startColor;

    @SerializedName("endColor")
    @Expose
    public String endColor;

    @SerializedName("credits")
    @Expose
    public int credits;


    protected CreditPackageInfo(Parcel in) {
        _id = in.readString();
        createdAt = in.readString();
        if (in.readByte() == 0) {
            checkoutCurrencyINR = null;
        } else {
            checkoutCurrencyINR = in.readDouble();
        }
        if (in.readByte() == 0) {
            amount = null;
        } else {
            amount = in.readDouble();
        }
        packageName = in.readString();
        packageType = in.readString();
        countryCode = in.readString();
        startColor = in.readString();
        endColor = in.readString();
        credits = in.readInt();
    }

    public static final Creator<CreditPackageInfo> CREATOR = new Creator<CreditPackageInfo>() {
        @Override
        public CreditPackageInfo createFromParcel(Parcel in) {
            return new CreditPackageInfo(in);
        }

        @Override
        public CreditPackageInfo[] newArray(int size) {
            return new CreditPackageInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(_id);
        parcel.writeString(createdAt);
        if (checkoutCurrencyINR == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(checkoutCurrencyINR);
        }
        if (amount == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(amount);
        }
        parcel.writeString(packageName);
        parcel.writeString(packageType);
        parcel.writeString(countryCode);
        parcel.writeString(startColor);
        parcel.writeString(endColor);
        parcel.writeInt(credits);
    }
}
