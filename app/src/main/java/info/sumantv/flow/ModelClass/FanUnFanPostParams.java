package info.sumantv.flow.ModelClass;

import android.os.Parcel;
import android.os.Parcelable;

public class FanUnFanPostParams implements Parcelable {
    public String createdBy;
    public String notificationType;
    public Double creditValue;
    public String CelebrityId;
    public String creditType;
    public String memberId;

    public FanUnFanPostParams(){}

    public FanUnFanPostParams(String createdBy, String notificationType, Double creditValue, String CelebrityId, String creditType, String memberId) {
        this.createdBy = createdBy;
        this.notificationType = notificationType;
        this.creditValue = creditValue;
        this.CelebrityId = CelebrityId;
        this.creditType = creditType;
        this.memberId = memberId;
    }

    protected FanUnFanPostParams(Parcel in) {
        createdBy = in.readString();
        notificationType = in.readString();
        if (in.readByte() == 0) {
            creditValue = null;
        } else {
            creditValue = in.readDouble();
        }
        CelebrityId = in.readString();
        creditType = in.readString();
        memberId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(createdBy);
        dest.writeString(notificationType);
        if (creditValue == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(creditValue);
        }
        dest.writeString(CelebrityId);
        dest.writeString(creditType);
        dest.writeString(memberId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FanUnFanPostParams> CREATOR = new Creator<FanUnFanPostParams>() {
        @Override
        public FanUnFanPostParams createFromParcel(Parcel in) {
            return new FanUnFanPostParams(in);
        }

        @Override
        public FanUnFanPostParams[] newArray(int size) {
            return new FanUnFanPostParams[size];
        }
    };
}
