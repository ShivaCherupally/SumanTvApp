package info.dkapp.flow.bottommenu.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Uday Kumay Vurukonda on 11/26/2018.
 * <p>
 * Mr.Psycho
 */
public class CreditAmountTransfer implements Parcelable {

    @SerializedName("memberId")
    @Expose
    private String memberId;

    @SerializedName("packageRefId")
    @Expose
    private String packageRefId;

    @SerializedName("refCartId")
    @Expose
    private String refCartId;

    @SerializedName("creditValue")
    @Expose
    private String creditValue;

    @SerializedName("equivalentAmount")
    @Expose
    private double equivalentAmount;

    @SerializedName("paymentType")
    @Expose
    private String paymentType;

    @SerializedName("createdBy")
    @Expose
    private String createdBy;


    private String  card_type;

    private String  cart_id;

    private double  sendingAmount;


    public CreditAmountTransfer(Parcel in) {
        memberId = in.readString();
        packageRefId = in.readString();
        refCartId = in.readString();
        creditValue = in.readString();
        equivalentAmount = in.readDouble();
        paymentType = in.readString();
        createdBy = in.readString();
        card_type = in.readString();
        cart_id = in.readString();
        sendingAmount = in.readDouble();
    }

    public CreditAmountTransfer() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(memberId);
        dest.writeString(packageRefId);
        dest.writeString(refCartId);
        dest.writeString(creditValue);
        dest.writeDouble(equivalentAmount);
        dest.writeString(paymentType);
        dest.writeString(createdBy);
        dest.writeString(card_type);
        dest.writeString(cart_id);
        dest.writeDouble(sendingAmount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CreditAmountTransfer> CREATOR = new Creator<CreditAmountTransfer>() {
        @Override
        public CreditAmountTransfer createFromParcel(Parcel in) {
            return new CreditAmountTransfer(in);
        }

        @Override
        public CreditAmountTransfer[] newArray(int size) {
            return new CreditAmountTransfer[size];
        }
    };

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public double getSendingAmount() {
        return sendingAmount;
    }

    public void setSendingAmount(double sendingAmount) {
        this.sendingAmount = sendingAmount;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getPackageRefId() {
        return packageRefId;
    }

    public void setPackageRefId(String packageRefId) {
        this.packageRefId = packageRefId;
    }

    public String getRefCartId() {
        return refCartId;
    }

    public void setRefCartId(String refCartId) {
        this.refCartId = refCartId;
    }

    public String getCreditValue() {
        return creditValue;
    }

    public void setCreditValue(String creditValue) {
        this.creditValue = creditValue;
    }

    public double getEquivalentAmount() {
        return equivalentAmount;
    }

    public void setEquivalentAmount(double equivalentAmount) {
        this.equivalentAmount = equivalentAmount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
