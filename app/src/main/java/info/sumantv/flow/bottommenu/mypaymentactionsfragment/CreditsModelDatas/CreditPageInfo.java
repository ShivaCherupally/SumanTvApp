package info.sumantv.flow.bottommenu.mypaymentactionsfragment.CreditsModelDatas;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import info.sumantv.flow.ModelClass.CreditInfoData;

import java.util.ArrayList;

public class CreditPageInfo implements Parcelable {
    @SerializedName("creditInfo")
    @Expose
    public CreditInfoData creditInfo;

    @SerializedName("referalCodeInfo")
    @Expose
    public ReferalCodeInfo referalCodeInfo;

    @SerializedName("currencyType")
    @Expose
    public CurrencyType currencyType;

    @SerializedName("creditPackageInfo")
    @Expose
    public ArrayList<CreditPackageInfo> creditPackageInfo;


    protected CreditPageInfo(Parcel in) {
        creditInfo = in.readParcelable(CreditInfoData.class.getClassLoader());
        referalCodeInfo = in.readParcelable(CurrencyType.class.getClassLoader());
        currencyType = in.readParcelable(CurrencyType.class.getClassLoader());
        creditPackageInfo = in.createTypedArrayList(CreditPackageInfo.CREATOR);
    }

    public static final Creator<CreditPageInfo> CREATOR = new Creator<CreditPageInfo>() {
        @Override
        public CreditPageInfo createFromParcel(Parcel in) {
            return new CreditPageInfo(in);
        }

        @Override
        public CreditPageInfo[] newArray(int size) {
            return new CreditPageInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(creditInfo, flags);
        dest.writeParcelable(referalCodeInfo, flags);
        dest.writeParcelable(currencyType, flags);
        dest.writeTypedList(creditPackageInfo);
    }
}
