package info.dkapp.flow.appmanagers.feed.models;

import android.os.Parcel;
import android.os.Parcelable;

public class HideFeed implements Parcelable {


    public String hideById;
    public boolean isHide;


    protected HideFeed(Parcel in) {
        hideById = in.readString();
        isHide = in.readByte() != 0;
    }

    public static final Creator<HideFeed> CREATOR = new Creator<HideFeed>() {
        @Override
        public HideFeed createFromParcel(Parcel in) {
            return new HideFeed(in);
        }

        @Override
        public HideFeed[] newArray(int size) {
            return new HideFeed[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(hideById);
        parcel.writeByte((byte) (isHide ? 1 : 0));
    }
}
