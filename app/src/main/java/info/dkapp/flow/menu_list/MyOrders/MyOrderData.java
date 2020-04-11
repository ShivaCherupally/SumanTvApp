package info.dkapp.flow.menu_list.MyOrders;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Shiva on 3/29/2018.
 */

public class MyOrderData implements Parcelable {
    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("celebFirstName")
    @Expose
    public String celebFirstName;
    @SerializedName("celebLastName")
    @Expose
    public String celebLastName;
    @SerializedName("celebProfilepic")
    @Expose
    public String celebProfilepic;
    @SerializedName("serviceType")
    @Expose
    public String serviceType;
    @SerializedName("startTime")
    @Expose
    public String startTime;
    @SerializedName("endTime")
    @Expose
    public String endTime;
    @SerializedName("refCartIds")
    @Expose
    public List<Object> refCartIds = null;
    @SerializedName("paymentAmount")
    @Expose
    public Object paymentAmount;
    @SerializedName("credits")
    @Expose
    public Object credits;
    @SerializedName("paymentMode")
    @Expose
    public String paymentMode;
    @SerializedName("ordersStatus")
    @Expose
    public String ordersStatus;
    @SerializedName("memberId")
    @Expose
    public String memberId;
    @SerializedName("celebId")
    @Expose
    public String celebId;
    @SerializedName("orderType")
    @Expose
    public String orderType;
    @SerializedName("slotId")
    @Expose
    public String slotId;
    @SerializedName("createdAt")
    @Expose
    public String createdAt;
    public final static Parcelable.Creator<MyOrderData> CREATOR = new Creator<MyOrderData>() {


        @SuppressWarnings({
                "unchecked"
        })
        public MyOrderData createFromParcel(Parcel in) {
            return new MyOrderData(in);
        }

        public MyOrderData[] newArray(int size) {
            return (new MyOrderData[size]);
        }

    };

    protected MyOrderData(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.celebFirstName = ((String) in.readValue((String.class.getClassLoader())));
        this.celebLastName = ((String) in.readValue((String.class.getClassLoader())));
        this.celebProfilepic = ((String) in.readValue((String.class.getClassLoader())));
        this.serviceType = ((String) in.readValue((String.class.getClassLoader())));
        this.startTime = ((String) in.readValue((String.class.getClassLoader())));
        this.endTime = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.refCartIds, (java.lang.Object.class.getClassLoader()));
        this.paymentAmount = ((Object) in.readValue((Object.class.getClassLoader())));
        this.credits = ((Object) in.readValue((Object.class.getClassLoader())));
        this.paymentMode = ((String) in.readValue((String.class.getClassLoader())));
        this.ordersStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.memberId = ((String) in.readValue((String.class.getClassLoader())));
        this.celebId = ((String) in.readValue((String.class.getClassLoader())));
        this.orderType = ((String) in.readValue((String.class.getClassLoader())));
        this.slotId = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
    }

    public MyOrderData() {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(celebFirstName);
        dest.writeValue(celebLastName);
        dest.writeValue(celebProfilepic);
        dest.writeValue(serviceType);
        dest.writeValue(startTime);
        dest.writeValue(endTime);
        dest.writeList(refCartIds);
        dest.writeValue(paymentAmount);
        dest.writeValue(credits);
        dest.writeValue(paymentMode);
        dest.writeValue(ordersStatus);
        dest.writeValue(memberId);
        dest.writeValue(celebId);
        dest.writeValue(orderType);
        dest.writeValue(slotId);
        dest.writeValue(createdAt);
    }

    public int describeContents() {
        return 0;
    }
}
