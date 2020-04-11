package info.dkapp.flow.ModelClass;

import android.os.Parcel;
import android.os.Parcelable;

public class ProfileDataModel implements Parcelable {
    public String _id;
    public String firstName;
    public String lastName;
    public String role;
    public String aboutMe;
    public String avtar_imgPath;
    public Boolean isCeleb;
    public Boolean isOnline;
    public String profession;
    public String serviceType;
    public Boolean isFan;
    public Integer celeAudioCharge;
    public Integer celeFanCharge;
    public Integer celeVideoCharge;

    public ProfileDataModel(){}

    protected ProfileDataModel(Parcel in) {
        _id = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        role = in.readString();
        aboutMe = in.readString();
        avtar_imgPath = in.readString();
        byte tmpIsCeleb = in.readByte();
        isCeleb = tmpIsCeleb == 0 ? null : tmpIsCeleb == 1;
        byte tmpIsOnline = in.readByte();
        isOnline = tmpIsOnline == 0 ? null : tmpIsOnline == 1;
        profession = in.readString();
        serviceType = in.readString();
        byte tmpIsFan = in.readByte();
        isFan = tmpIsFan == 0 ? null : tmpIsFan == 1;
        if (in.readByte() == 0) {
            celeAudioCharge = null;
        } else {
            celeAudioCharge = in.readInt();
        }
        if (in.readByte() == 0) {
            celeFanCharge = null;
        } else {
            celeFanCharge = in.readInt();
        }
        if (in.readByte() == 0) {
            celeVideoCharge = null;
        } else {
            celeVideoCharge = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(role);
        dest.writeString(aboutMe);
        dest.writeString(avtar_imgPath);
        dest.writeByte((byte) (isCeleb == null ? 0 : isCeleb ? 1 : 2));
        dest.writeByte((byte) (isOnline == null ? 0 : isOnline ? 1 : 2));
        dest.writeString(profession);
        dest.writeString(serviceType);
        dest.writeByte((byte) (isFan == null ? 0 : isFan ? 1 : 2));
        if (celeAudioCharge == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(celeAudioCharge);
        }
        if (celeFanCharge == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(celeFanCharge);
        }
        if (celeVideoCharge == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(celeVideoCharge);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProfileDataModel> CREATOR = new Creator<ProfileDataModel>() {
        @Override
        public ProfileDataModel createFromParcel(Parcel in) {
            return new ProfileDataModel(in);
        }

        @Override
        public ProfileDataModel[] newArray(int size) {
            return new ProfileDataModel[size];
        }
    };
}
