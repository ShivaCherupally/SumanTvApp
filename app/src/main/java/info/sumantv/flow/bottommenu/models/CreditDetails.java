package info.sumantv.flow.bottommenu.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Uday Kumay Vurukonda on 12/17/2018.
 * <p>
 * Mr.Psycho
 */
public class CreditDetails implements Parcelable
{

    @SerializedName("creditType")
    @Expose
    private String creditType;
    @SerializedName("creditValue")
    @Expose
    private Double creditValue;
    @SerializedName("cumulativeCreditValue")
    @Expose
    private Double cumulativeCreditValue;
    @SerializedName("referralCreditValue")
    @Expose
    private Integer referralCreditValue;
    @SerializedName("remarks")
    @Expose
    private String remarks;
    @SerializedName("couponCode")
    @Expose
    private String couponCode;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("createdBy")
    @Expose
    private String createdBy;
    @SerializedName("updatedBy")
    @Expose
    private String updatedBy;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("creditRefCartId")
    @Expose
    private String creditRefCartId;
    @SerializedName("memberId")
    @Expose
    private String memberId;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    public final static Parcelable.Creator<CreditDetails> CREATOR = new Creator<CreditDetails>() {


        @SuppressWarnings({
                "unchecked"
        })
        public CreditDetails createFromParcel(Parcel in) {
            return new CreditDetails(in);
        }

        public CreditDetails[] newArray(int size) {
            return (new CreditDetails[size]);
        }

    }
            ;

    protected CreditDetails(Parcel in) {
        this.creditType = ((String) in.readValue((String.class.getClassLoader())));
        this.creditValue = ((Double) in.readValue((Integer.class.getClassLoader())));
        this.cumulativeCreditValue = ((Double) in.readValue((Integer.class.getClassLoader())));
        this.referralCreditValue = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.remarks = ((String) in.readValue((String.class.getClassLoader())));
        this.couponCode = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.createdBy = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedBy = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.creditRefCartId = ((String) in.readValue((String.class.getClassLoader())));
        this.memberId = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
    }

    public CreditDetails() {
    }

    public String getCreditType() {
        return creditType;
    }

    public void setCreditType(String creditType) {
        this.creditType = creditType;
    }

    public Double getCreditValue() {
        return creditValue;
    }

    public void setCreditValue(Double creditValue) {
        this.creditValue = creditValue;
    }

    public Double getCumulativeCreditValue() {
        return cumulativeCreditValue;
    }

    public void setCumulativeCreditValue(Double cumulativeCreditValue) {
        this.cumulativeCreditValue = cumulativeCreditValue;
    }

    public Integer getReferralCreditValue() {
        return referralCreditValue;
    }

    public void setReferralCreditValue(Integer referralCreditValue) {
        this.referralCreditValue = referralCreditValue;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreditRefCartId() {
        return creditRefCartId;
    }

    public void setCreditRefCartId(String creditRefCartId) {
        this.creditRefCartId = creditRefCartId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(creditType);
        dest.writeValue(creditValue);
        dest.writeValue(cumulativeCreditValue);
        dest.writeValue(referralCreditValue);
        dest.writeValue(remarks);
        dest.writeValue(couponCode);
        dest.writeValue(status);
        dest.writeValue(createdBy);
        dest.writeValue(updatedBy);
        dest.writeValue(id);
        dest.writeValue(creditRefCartId);
        dest.writeValue(memberId);
        dest.writeValue(createdAt);
        dest.writeValue(updatedAt);
    }

    public int describeContents() {
        return 0;
    }

}
