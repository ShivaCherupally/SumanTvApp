package info.dkapp.flow.bottommenu.celebrityprofile;

import android.os.Parcel;
import android.os.Parcelable;

public class CelebScheduleModel implements Parcelable {
    public String _id;
    public Integer scheduleDuration;
    public String startTime;
    public String endTime;
    public String creditValue;
    public String slotStatus;

    protected CelebScheduleModel(Parcel in) {
        _id = in.readString();
        if (in.readByte() == 0) {
            scheduleDuration = null;
        } else {
            scheduleDuration = in.readInt();
        }
        startTime = in.readString();
        endTime = in.readString();
        creditValue = in.readString();
        slotStatus = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        if (scheduleDuration == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(scheduleDuration);
        }
        dest.writeString(startTime);
        dest.writeString(endTime);
        dest.writeString(creditValue);
        dest.writeString(slotStatus);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CelebScheduleModel> CREATOR = new Creator<CelebScheduleModel>() {
        @Override
        public CelebScheduleModel createFromParcel(Parcel in) {
            return new CelebScheduleModel(in);
        }

        @Override
        public CelebScheduleModel[] newArray(int size) {
            return new CelebScheduleModel[size];
        }
    };
}
