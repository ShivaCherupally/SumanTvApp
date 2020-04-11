package info.sumantv.flow.ModelClass;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class CreditInfoData  implements Parcelable {

    @SerializedName("cumulativeCreditValue")
    private Double cumulativeCreditValue;

    @SerializedName("referralCreditValue")
    private Double referralCreditValue;

    @SerializedName("memberReferCreditValue")
    private Double memberReferCreditValue;


    @SerializedName("couponCode")
    private String couponCode;

    @SerializedName("creditType")
    private String creditType;


    protected CreditInfoData(Parcel in) {
        if (in.readByte() == 0) {
            cumulativeCreditValue = null;
        } else {
            cumulativeCreditValue = in.readDouble();
        }
        if (in.readByte() == 0) {
            referralCreditValue = null;
        } else {
            referralCreditValue = in.readDouble();
        }
        if (in.readByte() == 0) {
            memberReferCreditValue = null;
        } else {
            memberReferCreditValue = in.readDouble();
        }
        couponCode = in.readString();
        creditType = in.readString();
    }

    public static final Creator<CreditInfoData> CREATOR = new Creator<CreditInfoData>() {
        @Override
        public CreditInfoData createFromParcel(Parcel in) {
            return new CreditInfoData(in);
        }

        @Override
        public CreditInfoData[] newArray(int size) {
            return new CreditInfoData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (cumulativeCreditValue == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(cumulativeCreditValue);
        }
        if (referralCreditValue == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(referralCreditValue);
        }
        if (memberReferCreditValue == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(memberReferCreditValue);
        }
        parcel.writeString(couponCode);
        parcel.writeString(creditType);
    }

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

    public Double getMemberReferCreditValue() {
        return memberReferCreditValue;
    }

    public void setMemberReferCreditValue(Double memberReferCreditValue) {
        this.memberReferCreditValue = memberReferCreditValue;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getCreditType() {
        return creditType;
    }

    public void setCreditType(String creditType) {
        this.creditType = creditType;
    }

    public static Creator<CreditInfoData> getCREATOR() {
        return CREATOR;
    }
}
