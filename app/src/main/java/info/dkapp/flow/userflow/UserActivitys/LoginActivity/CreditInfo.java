package info.dkapp.flow.userflow.UserActivitys.LoginActivity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Uday Kumay Vurukonda on 2/5/2019.
 * <p>
 * Mr.Psycho
 */
public class CreditInfo implements Parcelable {
    public String id;
    public String creditRefCartId;
    public String memberId;
    public String updatedBy;
    public String createdBy;
    public String updatedAt;
    public String createdAt;
    public String status;
    public String couponCode;
    public String remarks;
    public Double referralCreditValue;
    public Double cumulativeCreditValue;
    public Double creditValue;
    public String creditType;

    protected CreditInfo(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.creditRefCartId = ((String) in.readValue((String.class.getClassLoader())));
        this.memberId = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedBy = ((String) in.readValue((String.class.getClassLoader())));
        this.createdBy = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.couponCode = ((String) in.readValue((String.class.getClassLoader())));
        this.remarks = ((String) in.readValue((String.class.getClassLoader())));
        this.referralCreditValue = ((Double) in.readValue((Integer.class.getClassLoader())));
        this.cumulativeCreditValue = ((Double) in.readValue((Integer.class.getClassLoader())));
        this.creditValue = ((Double) in.readValue((Integer.class.getClassLoader())));
        this.creditType = ((String) in.readValue((String.class.getClassLoader())));
    }



    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(creditRefCartId);
        dest.writeString(memberId);
        dest.writeString(updatedBy);
        dest.writeString(createdBy);
        dest.writeString(updatedAt);
        dest.writeString(createdAt);
        dest.writeString(status);
        dest.writeString(couponCode);
        dest.writeString(remarks);
        if (referralCreditValue == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(referralCreditValue);
        }
        if (cumulativeCreditValue == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(cumulativeCreditValue);
        }
        if (creditValue == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(creditValue);
        }
        dest.writeString(creditType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CreditInfo> CREATOR = new Creator<CreditInfo>() {
        @Override
        public CreditInfo createFromParcel(Parcel in) {
            return new CreditInfo(in);
        }

        @Override
        public CreditInfo[] newArray(int size) {
            return new CreditInfo[size];
        }
    };


}
