package info.sumantv.flow.bottommenu.mypaymentactionsfragment.CreditsModelDatas;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderInfo implements Parcelable {

    public String memberId;
    public String orderId;
    public String transactionId;
    public String createdAt;
    public int credits;
    public Double totalAmount;
    public Double actualAmount;
    public Double gstAmount;
    public String currencySymbol;
    public String currencyType;
    public Double cumulativeCreditValue;
    public int gst;

    public String paymentGateway;
    public String gatewayResponse;
    public String ordersStatus;
    public String status;
    public String paymentMode;

    protected OrderInfo(Parcel in) {
        memberId = in.readString();
        orderId = in.readString();
        transactionId = in.readString();
        createdAt = in.readString();
        credits = in.readInt();
        if (in.readByte() == 0) {
            totalAmount = null;
        } else {
            totalAmount = in.readDouble();
        }
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
        currencySymbol = in.readString();
        currencyType = in.readString();
        if (in.readByte() == 0) {
            cumulativeCreditValue = null;
        } else {
            cumulativeCreditValue = in.readDouble();
        }
        gst = in.readInt();
        paymentGateway = in.readString();
        gatewayResponse = in.readString();
        ordersStatus = in.readString();
        status = in.readString();
        paymentMode = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(memberId);
        dest.writeString(orderId);
        dest.writeString(transactionId);
        dest.writeString(createdAt);
        dest.writeInt(credits);
        if (totalAmount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(totalAmount);
        }
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
        dest.writeString(currencySymbol);
        dest.writeString(currencyType);
        if (cumulativeCreditValue == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(cumulativeCreditValue);
        }
        dest.writeInt(gst);
        dest.writeString(paymentGateway);
        dest.writeString(gatewayResponse);
        dest.writeString(ordersStatus);
        dest.writeString(status);
        dest.writeString(paymentMode);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderInfo> CREATOR = new Creator<OrderInfo>() {
        @Override
        public OrderInfo createFromParcel(Parcel in) {
            return new OrderInfo(in);
        }

        @Override
        public OrderInfo[] newArray(int size) {
            return new OrderInfo[size];
        }
    };
}
