package info.sumantv.flow.bottommenu.mypaymentactionsfragment.CreditsModelDatas;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateOrderData implements Parcelable {
    @SerializedName("memberId")
    @Expose
    public String memberId;

    @SerializedName("refCreditTransactionId")
    @Expose
    public String refCreditTransactionId;


    @SerializedName("refPaymentTransactionId")
    @Expose
    public String refPaymentTransactionId;


    @SerializedName("orderType")
    @Expose
    public String orderType;

    @SerializedName("paymentMode")
    @Expose
    public String paymentMode;

    @SerializedName("countryCode")
    @Expose
    public String countryCode;

    @SerializedName("paymentAmount")
    @Expose
    public String paymentAmount;

    @SerializedName("credits")
    @Expose
    public String credits;


    protected CreateOrderData(Parcel in) {
        memberId = in.readString();
        refCreditTransactionId = in.readString();
        refPaymentTransactionId = in.readString();
        orderType = in.readString();
        paymentMode = in.readString();
        countryCode = in.readString();
        paymentAmount = in.readString();
        credits = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(memberId);
        dest.writeString(refCreditTransactionId);
        dest.writeString(refPaymentTransactionId);
        dest.writeString(orderType);
        dest.writeString(paymentMode);
        dest.writeString(countryCode);
        dest.writeString(paymentAmount);
        dest.writeString(credits);
    }

    public CreateOrderData(String memberId, String refCreditTransactionId, String refPaymentTransactionId, String orderType, String paymentMode, String countryCode, String paymentAmount, String credits) {
        this.memberId = memberId;
        this.refCreditTransactionId = refCreditTransactionId;
        this.refPaymentTransactionId = refPaymentTransactionId;
        this.orderType = orderType;
        this.paymentMode = paymentMode;
        this.countryCode = countryCode;
        this.paymentAmount = paymentAmount;
        this.credits = credits;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CreateOrderData> CREATOR = new Creator<CreateOrderData>() {
        @Override
        public CreateOrderData createFromParcel(Parcel in) {
            return new CreateOrderData(in);
        }

        @Override
        public CreateOrderData[] newArray(int size) {
            return new CreateOrderData[size];
        }
    };
}
