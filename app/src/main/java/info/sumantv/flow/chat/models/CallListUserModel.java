package info.sumantv.flow.chat.models;

import android.os.Parcel;
import android.os.Parcelable;

public class CallListUserModel implements Parcelable {
    public String serviceType;
    public ChatSenderReceiverInfo senderId;
    public ChatSenderReceiverInfo receiverId;
    public Integer month;
    public Integer day;
    public Integer year;
    public Boolean isFan;

    protected CallListUserModel(Parcel in) {
        serviceType = in.readString();
        senderId = in.readParcelable(ChatSenderReceiverInfo.class.getClassLoader());
        receiverId = in.readParcelable(ChatSenderReceiverInfo.class.getClassLoader());
        if (in.readByte() == 0) {
            month = null;
        } else {
            month = in.readInt();
        }
        if (in.readByte() == 0) {
            day = null;
        } else {
            day = in.readInt();
        }
        if (in.readByte() == 0) {
            year = null;
        } else {
            year = in.readInt();
        }
        byte tmpIsFan = in.readByte();
        isFan = tmpIsFan == 0 ? null : tmpIsFan == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(serviceType);
        dest.writeParcelable(senderId, flags);
        dest.writeParcelable(receiverId, flags);
        if (month == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(month);
        }
        if (day == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(day);
        }
        if (year == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(year);
        }
        dest.writeByte((byte) (isFan == null ? 0 : isFan ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CallListUserModel> CREATOR = new Creator<CallListUserModel>() {
        @Override
        public CallListUserModel createFromParcel(Parcel in) {
            return new CallListUserModel(in);
        }

        @Override
        public CallListUserModel[] newArray(int size) {
            return new CallListUserModel[size];
        }
    };
}
