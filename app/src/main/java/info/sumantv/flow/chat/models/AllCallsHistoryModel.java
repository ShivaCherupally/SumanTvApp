package info.sumantv.flow.chat.models;

import android.os.Parcel;
import android.os.Parcelable;

public class AllCallsHistoryModel implements Parcelable {
    public String _id;
    public String updatedAt;
    public String startTime;
    public CallDurationModel callDuration;
    public ChatSenderReceiverInfo senderId;
    public ChatSenderReceiverInfo receiverId;
    public String createdAt;
    public String liveStatusDate;
    public String endTime;
    public String serviceType;
    public Boolean incoming;
    public Boolean outgoing;

    protected AllCallsHistoryModel(Parcel in) {
        _id = in.readString();
        updatedAt = in.readString();
        startTime = in.readString();
        callDuration = in.readParcelable(CallDurationModel.class.getClassLoader());
        senderId = in.readParcelable(ChatSenderReceiverInfo.class.getClassLoader());
        receiverId = in.readParcelable(ChatSenderReceiverInfo.class.getClassLoader());
        createdAt = in.readString();
        liveStatusDate = in.readString();
        endTime = in.readString();
        serviceType = in.readString();
        byte tmpIncoming = in.readByte();
        incoming = tmpIncoming == 0 ? null : tmpIncoming == 1;
        byte tmpOutgoing = in.readByte();
        outgoing = tmpOutgoing == 0 ? null : tmpOutgoing == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(updatedAt);
        dest.writeString(startTime);
        dest.writeParcelable(callDuration, flags);
        dest.writeParcelable(senderId, flags);
        dest.writeParcelable(receiverId, flags);
        dest.writeString(createdAt);
        dest.writeString(liveStatusDate);
        dest.writeString(endTime);
        dest.writeString(serviceType);
        dest.writeByte((byte) (incoming == null ? 0 : incoming ? 1 : 2));
        dest.writeByte((byte) (outgoing == null ? 0 : outgoing ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AllCallsHistoryModel> CREATOR = new Creator<AllCallsHistoryModel>() {
        @Override
        public AllCallsHistoryModel createFromParcel(Parcel in) {
            return new AllCallsHistoryModel(in);
        }

        @Override
        public AllCallsHistoryModel[] newArray(int size) {
            return new AllCallsHistoryModel[size];
        }
    };
}
