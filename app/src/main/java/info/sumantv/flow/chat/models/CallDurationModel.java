package info.sumantv.flow.chat.models;

import android.os.Parcel;
import android.os.Parcelable;

public class CallDurationModel implements Parcelable {
    public Integer minutes;
    public Integer seconds;
    public Integer hours;

    protected CallDurationModel(Parcel in) {
        if (in.readByte() == 0) {
            minutes = null;
        } else {
            minutes = in.readInt();
        }
        if (in.readByte() == 0) {
            seconds = null;
        } else {
            seconds = in.readInt();
        }
        if (in.readByte() == 0) {
            hours = null;
        } else {
            hours = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (minutes == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(minutes);
        }
        if (seconds == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(seconds);
        }
        if (hours == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(hours);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CallDurationModel> CREATOR = new Creator<CallDurationModel>() {
        @Override
        public CallDurationModel createFromParcel(Parcel in) {
            return new CallDurationModel(in);
        }

        @Override
        public CallDurationModel[] newArray(int size) {
            return new CallDurationModel[size];
        }
    };
}
