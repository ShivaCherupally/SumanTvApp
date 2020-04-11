package info.sumantv.flow.appmanagers.feed.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Uday Kumay Vurukonda on 01-Oct-19.
 * <p>
 * Mr.Psycho
 */
public class KonectData implements Parcelable {

    public String _id;
    public String avtar_imgPath;
    public String profession;
    public String lastName;
    public String firstName;
    public Boolean isCeleb;
    public Boolean isScheduleStatus;
    public String  serviceType;

    public KonectData( Boolean isCeleb,String _id, String avtar_imgPath, String profession, String lastName, String firstName, Boolean isScheduleStatus,String  serviceType) {
        this._id = _id;
        this.avtar_imgPath = avtar_imgPath;
        this.profession = profession;
        this.lastName = lastName;
        this.firstName = firstName;
        this.isCeleb = isCeleb;
        this.isScheduleStatus = isScheduleStatus;
        this.serviceType = serviceType;
    }


    protected KonectData(Parcel in) {
        _id = in.readString();
        avtar_imgPath = in.readString();
        profession = in.readString();
        lastName = in.readString();
        firstName = in.readString();
        byte tmpIsCeleb = in.readByte();
        isCeleb = tmpIsCeleb == 0 ? null : tmpIsCeleb == 1;
        byte tmpIsScheduleStatus = in.readByte();
        isScheduleStatus = tmpIsScheduleStatus == 0 ? null : tmpIsScheduleStatus == 1;
        serviceType = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(avtar_imgPath);
        dest.writeString(profession);
        dest.writeString(lastName);
        dest.writeString(firstName);
        dest.writeByte((byte) (isCeleb == null ? 0 : isCeleb ? 1 : 2));
        dest.writeByte((byte) (isScheduleStatus == null ? 0 : isScheduleStatus ? 1 : 2));
        dest.writeString(serviceType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<KonectData> CREATOR = new Creator<KonectData>() {
        @Override
        public KonectData createFromParcel(Parcel in) {
            return new KonectData(in);
        }

        @Override
        public KonectData[] newArray(int size) {
            return new KonectData[size];
        }
    };
}
