package info.dkapp.flow.chat.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ChatSenderReceiverInfo implements Parcelable {
    public String _id;
    public String firstName;
    public String lastName;
    public String avtar_imgPath;
    public String aboutMe;
    public String profession;
    public String role;
    public Boolean isCeleb;
    public Boolean isOnline;
    public Boolean isFan;

    protected ChatSenderReceiverInfo(Parcel in) {
        _id = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        avtar_imgPath = in.readString();
        aboutMe = in.readString();
        profession = in.readString();
        role = in.readString();
        byte tmpIsCeleb = in.readByte();
        isCeleb = tmpIsCeleb == 0 ? null : tmpIsCeleb == 1;
        byte tmpIsOnline = in.readByte();
        isOnline = tmpIsOnline == 0 ? null : tmpIsOnline == 1;
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
        dest.writeByte((byte) (isCeleb == null ? 0 : isCeleb ? 1 : 2));
        dest.writeByte((byte) (isOnline == null ? 0 : isOnline ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ChatSenderReceiverInfo> CREATOR = new Creator<ChatSenderReceiverInfo>() {
        @Override
        public ChatSenderReceiverInfo createFromParcel(Parcel in) {
            return new ChatSenderReceiverInfo(in);
        }

        @Override
        public ChatSenderReceiverInfo[] newArray(int size) {
            return new ChatSenderReceiverInfo[size];
        }
    };
}
