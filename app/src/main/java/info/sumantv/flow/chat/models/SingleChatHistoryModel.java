package info.sumantv.flow.chat.models;

import android.os.Parcel;
import android.os.Parcelable;

public class SingleChatHistoryModel implements Parcelable {
    public String chatRoomId;
    public String message;
    public Boolean isRead;
    public String _id;
    public Boolean isWelcomeMessage;
    public String receiverId;
    public String createdAt;
    public String credits;
    public String senderId;


    protected SingleChatHistoryModel(Parcel in) {
        chatRoomId = in.readString();
        message = in.readString();
        byte tmpIsRead = in.readByte();
        isRead = tmpIsRead == 0 ? null : tmpIsRead == 1;
        _id = in.readString();
        byte tmpIsWelcomeMessage = in.readByte();
        isWelcomeMessage = tmpIsWelcomeMessage == 0 ? null : tmpIsWelcomeMessage == 1;
        receiverId = in.readString();
        createdAt = in.readString();
        credits = in.readString();
        senderId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(chatRoomId);
        dest.writeString(message);
        dest.writeByte((byte) (isRead == null ? 0 : isRead ? 1 : 2));
        dest.writeString(_id);
        dest.writeByte((byte) (isWelcomeMessage == null ? 0 : isWelcomeMessage ? 1 : 2));
        dest.writeString(receiverId);
        dest.writeString(createdAt);
        dest.writeString(credits);
        dest.writeString(senderId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SingleChatHistoryModel> CREATOR = new Creator<SingleChatHistoryModel>() {
        @Override
        public SingleChatHistoryModel createFromParcel(Parcel in) {
            return new SingleChatHistoryModel(in);
        }

        @Override
        public SingleChatHistoryModel[] newArray(int size) {
            return new SingleChatHistoryModel[size];
        }
    };
}
