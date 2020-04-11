package info.dkapp.flow.ModelClass;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

public class FanUnFanData implements Parcelable {
    public Context context;
    public Double serviceCredits;
    public String firstName;
    public String lastName;
    public String avtar_imgPath;
    public String profession;
    public String celebId;

    public FanUnFanData(){}

    public FanUnFanData(Context context, String celebId, Double serviceCredits, String firstName, String lastName, String avtar_imgPath, String profession) {
        this.context = context;
        this.celebId = celebId;
        this.serviceCredits = serviceCredits;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avtar_imgPath = avtar_imgPath;
        this.profession = profession;
    }

    protected FanUnFanData(Parcel in) {
        if (in.readByte() == 0) {
            serviceCredits = null;
        } else {
            serviceCredits = in.readDouble();
        }
        firstName = in.readString();
        lastName = in.readString();
        avtar_imgPath = in.readString();
        profession = in.readString();
        celebId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (serviceCredits == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(serviceCredits);
        }
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(avtar_imgPath);
        dest.writeString(profession);
        dest.writeString(celebId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FanUnFanData> CREATOR = new Creator<FanUnFanData>() {
        @Override
        public FanUnFanData createFromParcel(Parcel in) {
            return new FanUnFanData(in);
        }

        @Override
        public FanUnFanData[] newArray(int size) {
            return new FanUnFanData[size];
        }
    };
}
