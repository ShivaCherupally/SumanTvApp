package info.sumantv.flow.chat.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ChatDataConvertModel implements Parcelable {
    public String _id;//chat receiver info id
    public String firstName;
    public String lastName;
    public String avtar_imgPath;
    public String aboutMe;
    public String profession;
    public String role;
    public Boolean isOnline;
    public Boolean isCeleb;
    public Boolean isFan;
    public String message;
    public String senderId;
    public String receiverId;
    public String createdAt;
    public Integer counter;
    public Boolean putThisAtFirstPosition;
    public String notificationCondition;
    public Integer chatCount;
    public Integer unSeenMessageCount;

    public ChatDataConvertModel(){}

    protected ChatDataConvertModel(Parcel in) {
        _id = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        avtar_imgPath = in.readString();
        aboutMe = in.readString();
        profession = in.readString();
        role = in.readString();
        byte tmpIsOnline = in.readByte();
        isOnline = tmpIsOnline == 0 ? null : tmpIsOnline == 1;
        byte tmpIsCeleb = in.readByte();
        isCeleb = tmpIsCeleb == 0 ? null : tmpIsCeleb == 1;
        byte tmpIsFan = in.readByte();
        isFan = tmpIsFan == 0 ? null : tmpIsFan == 1;
        message = in.readString();
        senderId = in.readString();
        receiverId = in.readString();
        createdAt = in.readString();
        if (in.readByte() == 0) {
            counter = null;
        } else {
            counter = in.readInt();
        }
        byte tmpPutThisAtFirstPosition = in.readByte();
        putThisAtFirstPosition = tmpPutThisAtFirstPosition == 0 ? null : tmpPutThisAtFirstPosition == 1;
        notificationCondition = in.readString();
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
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(avtar_imgPath);
        dest.writeString(aboutMe);
        dest.writeString(profession);
        dest.writeString(role);
        dest.writeByte((byte) (isOnline == null ? 0 : isOnline ? 1 : 2));
        dest.writeByte((byte) (isCeleb == null ? 0 : isCeleb ? 1 : 2));
        dest.writeByte((byte) (isFan == null ? 0 : isFan ? 1 : 2));
        dest.writeString(message);
        dest.writeString(senderId);
        dest.writeString(receiverId);
        dest.writeString(createdAt);
        if (counter == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(counter);
        }
        dest.writeByte((byte) (putThisAtFirstPosition == null ? 0 : putThisAtFirstPosition ? 1 : 2));
        dest.writeString(notificationCondition);
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

    public static final Creator<ChatDataConvertModel> CREATOR = new Creator<ChatDataConvertModel>() {
        @Override
        public ChatDataConvertModel createFromParcel(Parcel in) {
            return new ChatDataConvertModel(in);
        }

        @Override
        public ChatDataConvertModel[] newArray(int size) {
            return new ChatDataConvertModel[size];
        }
    };
}
