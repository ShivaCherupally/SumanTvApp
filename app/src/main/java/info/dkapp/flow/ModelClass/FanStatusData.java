package info.dkapp.flow.ModelClass;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Shiva on 3/17/2018.
 */

public class FanStatusData implements Parcelable {

    @SerializedName("error")
    @Expose
    private String error;


    @SerializedName("memberId")
    private String memberId;


    @SerializedName("CelebrityId")
    private String CelebrityId;


    @SerializedName("creditRefCartId")
    private String creditRefCartId;


    @SerializedName("creditType")
    private String creditType;


    @SerializedName("creditValue")
    private String creditValue;


    @SerializedName("cumulativeCreditValue")
    private String cumulativeCreditValue;

    @SerializedName("remarks")
    private String remarks;

    @SerializedName("couponCode")
    private String couponCode;

    @SerializedName("createdBy")
    private String createdBy;



    @SerializedName("isFan")
    private Boolean isFan;


    @SerializedName("notificationType")
    private String notificationType;

    public FanStatusData(String memberId, String celebrityId, String creditRefCartId, String creditType, String creditValue, String cumulativeCreditValue, String remarks, String couponCode, String createdBy, Boolean isFan, String notificationType) {
        this.memberId = memberId;
        CelebrityId = celebrityId;
        this.creditRefCartId = creditRefCartId;
        this.creditType = creditType;
        this.creditValue = creditValue;
        this.cumulativeCreditValue = cumulativeCreditValue;
        this.remarks = remarks;
        this.couponCode = couponCode;
        this.createdBy = createdBy;
        this.isFan = isFan;
        this.notificationType = notificationType;
    }

    protected FanStatusData(Parcel in) {
        error = in.readString();
        memberId = in.readString();
        CelebrityId = in.readString();
        creditRefCartId = in.readString();
        creditType = in.readString();
        creditValue = in.readString();
        cumulativeCreditValue = in.readString();
        remarks = in.readString();
        couponCode = in.readString();
        createdBy = in.readString();
        byte tmpIsFan = in.readByte();
        isFan = tmpIsFan == 0 ? null : tmpIsFan == 1;
        notificationType = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(error);
        dest.writeString(memberId);
        dest.writeString(CelebrityId);
        dest.writeString(creditRefCartId);
        dest.writeString(creditType);
        dest.writeString(creditValue);
        dest.writeString(cumulativeCreditValue);
        dest.writeString(remarks);
        dest.writeString(couponCode);
        dest.writeString(createdBy);
        dest.writeByte((byte) (isFan == null ? 0 : isFan ? 1 : 2));
        dest.writeString(notificationType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FanStatusData> CREATOR = new Creator<FanStatusData>() {
        @Override
        public FanStatusData createFromParcel(Parcel in) {
            return new FanStatusData(in);
        }

        @Override
        public FanStatusData[] newArray(int size) {
            return new FanStatusData[size];
        }
    };

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getCelebrityId() {
        return CelebrityId;
    }

    public void setCelebrityId(String celebrityId) {
        CelebrityId = celebrityId;
    }

    public String getCreditRefCartId() {
        return creditRefCartId;
    }

    public void setCreditRefCartId(String creditRefCartId) {
        this.creditRefCartId = creditRefCartId;
    }

    public String getCreditType() {
        return creditType;
    }

    public void setCreditType(String creditType) {
        this.creditType = creditType;
    }

    public String getCreditValue() {
        return creditValue;
    }

    public void setCreditValue(String creditValue) {
        this.creditValue = creditValue;
    }

    public String getCumulativeCreditValue() {
        return cumulativeCreditValue;
    }

    public void setCumulativeCreditValue(String cumulativeCreditValue) {
        this.cumulativeCreditValue = cumulativeCreditValue;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Boolean getFan() {
        return isFan;
    }

    public void setFan(Boolean fan) {
        isFan = fan;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }
}
