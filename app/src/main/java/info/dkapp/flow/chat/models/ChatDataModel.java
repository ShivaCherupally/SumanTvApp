package info.dkapp.flow.chat.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ChatDataModel implements Parcelable {
    public String _id;
    public String message;
    public String chatStatus;
    public String senderId;
    public String receiverId;
    public String createdAt;
    public ChatSenderReceiverInfo receiverInfo;
    public ChatSenderReceiverInfo senderInfo;
    public String chatRoomId;
    public Boolean isRead = false;
    public Integer counter;
    public Integer chatCount;
    public Integer unSeenMessageCount;

    protected ChatDataModel(Parcel in) {
        _id = in.readString();
        message = in.readString();
        chatStatus = in.readString();
        senderId = in.readString();
        receiverId = in.readString();
        createdAt = in.readString();
        receiverInfo = in.readParcelable(ChatSenderReceiverInfo.class.getClassLoader());
        senderInfo = in.readParcelable(ChatSenderReceiverInfo.class.getClassLoader());
        chatRoomId = in.readString();
        byte tmpIsRead = in.readByte();
        isRead = tmpIsRead == 0 ? null : tmpIsRead == 1;
        if (in.readByte() == 0) {
            counter = null;
        } else {
            counter = in.readInt();
        }
        if (in.readByte() == 0) {
            chatCount = null;
        } else {
            chatCount = in.readInt();
        }
        if (in.readByte() == 0) {
            unSeenMessageCount = null;
        } else {
            unSeenMessageCount = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(message);
        dest.writeString(chatStatus);
        dest.writeString(senderId);
        dest.writeString(receiverId);
        dest.writeString(createdAt);
        dest.writeParcelable(receiverInfo, flags);
        dest.writeParcelable(senderInfo, flags);
        dest.writeString(chatRoomId);
        dest.writeByte((byte) (isRead == null ? 0 : isRead ? 1 : 2));
        if (counter == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(counter);
        }
        if (chatCount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(chatCount);
        }
        if (unSeenMessageCount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(unSeenMessageCount);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ChatDataModel> CREATOR = new Creator<ChatDataModel>() {
        @Override
        public ChatDataModel createFromParcel(Parcel in) {
            return new ChatDataModel(in);
        }

        @Override
        public ChatDataModel[] newArray(int size) {
            return new ChatDataModel[size];
        }
    };
}
