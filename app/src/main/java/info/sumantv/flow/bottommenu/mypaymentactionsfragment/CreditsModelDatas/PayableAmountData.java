package info.sumantv.flow.bottommenu.mypaymentactionsfragment.CreditsModelDatas;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PayableAmountData implements Parcelable {


    @SerializedName("payableAmount")
    @Expose
    public PayableAmountData payableAmount;


    @SerializedName("totalAmount")
    @Expose
    public Double totalAmount;

    @SerializedName("gstAmount")
    @Expose
    public Double gstAmount;

    @SerializedName("creditAmount")
    @Expose
    public Double creditAmount;

    @SerializedName("creditValue")
    @Expose
    public Integer creditValue;

    @SerializedName("gst")
    @Expose
    public Double gst;

    @SerializedName("currencySymbol")
    @Expose
    public String currencySymbol;

    @SerializedName("countryCode")
    @Expose
    public String countryCode;

    @SerializedName("currencyType")
    @Expose
    public String currencyType;

    @SerializedName("packageRefId")
    @Expose
    public String packageRefId;


    protected PayableAmountData(Parcel in) {
        payableAmount = in.readParcelable(PayableAmountData.class.getClassLoader());
        if (in.readByte() == 0) {
            totalAmount = null;
        } else {
            totalAmount = in.readDouble();
        }
        if (in.readByte() == 0) {
            gstAmount = null;
        } else {
            gstAmount = in.readDouble();
        }
        if (in.readByte() == 0) {
            creditAmount = null;
        } else {
            creditAmount = in.readDouble();
        }
        if (in.readByte() == 0) {
            creditValue = null;
        } else {
            creditValue = in.readInt();
        }
        if (in.readByte() == 0) {
            gst = null;
        } else {
            gst = in.readDouble();
        }
        currencySymbol = in.readString();
        countryCode = in.readString();
        currencyType = in.readString();
        packageRefId = in.readString();
    }

    public static final Creator<PayableAmountData> CREATOR = new Creator<PayableAmountData>() {
        @Override
        public PayableAmountData createFromParcel(Parcel in) {
            return new PayableAmountData(in);
        }

        @Override
        public PayableAmountData[] newArray(int size) {
            return new PayableAmountData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(payableAmount, flags);
        if (totalAmount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(totalAmount);
        }
        if (gstAmount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(gstAmount);
        }
        if (creditAmount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(creditAmount);
        }
        if (creditValue == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(creditValue);
        }
        if (gst == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(gst);
        }
        dest.writeString(currencySymbol);
        dest.writeString(countryCode);
        dest.writeString(currencyType);
        dest.writeString(packageRefId);
    }
}
