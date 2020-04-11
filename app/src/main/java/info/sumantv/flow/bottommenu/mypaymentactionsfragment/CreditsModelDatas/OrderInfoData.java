package info.sumantv.flow.bottommenu.mypaymentactionsfragment.CreditsModelDatas;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import info.sumantv.flow.bottommenu.models.Credits;

public class OrderInfoData implements Parcelable {

    @SerializedName("orderInfo")
    public OrderInfo orderInfoInner;

    @SerializedName("creditInfo")
    public Credits creditInfo;

    @SerializedName("paymentStatus")
    public String paymentStatus;

    public OrderInfoData() {

    }


    protected OrderInfoData(Parcel in) {
        orderInfoInner = in.readParcelable(OrderInfo.class.getClassLoader());
        creditInfo = in.readParcelable(Credits.class.getClassLoader());
        paymentStatus = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(orderInfoInner, flags);
        dest.writeParcelable(creditInfo, flags);
        dest.writeString(paymentStatus);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderInfoData> CREATOR = new Creator<OrderInfoData>() {
        @Override
        public OrderInfoData createFromParcel(Parcel in) {
            return new OrderInfoData(in);
        }

        @Override
        public OrderInfoData[] newArray(int size) {
            return new OrderInfoData[size];
        }
    };
}
