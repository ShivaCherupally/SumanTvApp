package info.dkapp.flow.bottommenu.mypaymentactionsfragment.CreditsModelDatas;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateTransactionData implements Parcelable {

    @SerializedName("memberId")
    @Expose
    public String memberId;

    @SerializedName("packageRefId")
    @Expose
    public String packageRefId;


    @SerializedName("refCartId")
    @Expose
    public String refCartId;

    @SerializedName("creditValue")
    @Expose
    public String creditValue;

    @SerializedName("equivalentAmount")
    @Expose
    public Double equivalentAmount;

    @SerializedName("paymentType")
    @Expose
    public String paymentType;

    @SerializedName("createdBy")
    @Expose
    public String createdBy;


    @SerializedName("paymentGateway")
    @Expose
    public String paymentGateway;

    @SerializedName("actualAmount")
    @Expose
    public Double actualAmount;

    @SerializedName("gstAmount")
    @Expose
    public Double gstAmount;


    protected CreateTransactionData(Parcel in) {
        memberId = in.readString();
        packageRefId = in.readString();
        refCartId = in.readString();
        creditValue = in.readString();
        if (in.readByte() == 0) {
            equivalentAmount = null;
        } else {
            equivalentAmount = in.readDouble();
        }
        paymentType = in.readString();
        createdBy = in.readString();
        paymentGateway = in.readString();
        if (in.readByte() == 0) {
            actualAmount = null;
        } else {
            actualAmount = in.readDouble();
        }
        if (in.readByte() == 0) {
            gstAmount = null;
        } else {
            gstAmount = in.readDouble();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(memberId);
        dest.writeString(packageRefId);
        dest.writeString(refCartId);
        dest.writeString(creditValue);
        if (equivalentAmount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(equivalentAmount);
        }
        dest.writeString(paymentType);
        dest.writeString(createdBy);
        dest.writeString(paymentGateway);
        if (actualAmount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(actualAmount);
        }
        if (gstAmount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(gstAmount);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CreateTransactionData> CREATOR = new Creator<CreateTransactionData>() {
        @Override
        public CreateTransactionData createFromParcel(Parcel in) {
            return new CreateTransactionData(in);
        }

        @Override
        public CreateTransactionData[] newArray(int size) {
            return new CreateTransactionData[size];
        }
    };

    public CreateTransactionData(String memberId, String packageRefId, String refCartId, String creditValue, Double equivalentAmount, String paymentType, String createdBy, String paymentGateway, Double actualAmount, Double gstAmount) {
        this.memberId = memberId;
        this.packageRefId = packageRefId;
        this.refCartId = refCartId;
        this.creditValue = creditValue;
        this.equivalentAmount = equivalentAmount;
        this.paymentType = paymentType;
        this.createdBy = createdBy;
        this.paymentGateway = paymentGateway;
        this.actualAmount = actualAmount;
        this.gstAmount = gstAmount;
    }
}
