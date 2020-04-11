package info.sumantv.flow.stories.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CelebInfo implements Parcelable {
    @SerializedName("avtar_imgPath")
    @Expose
    public String avtar_imgPath;

    @SerializedName("firstName")
    @Expose
    public String firstName;

    @SerializedName("lastName")
    @Expose
    public String lastName;

    @SerializedName("_id")
    @Expose
    public String _id;


    protected CelebInfo(Parcel in) {
        avtar_imgPath = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        _id = in.readString();
    }

    public static final Creator<CelebInfo> CREATOR = new Creator<CelebInfo>() {
        @Override
        public CelebInfo createFromParcel(Parcel in) {
            return new CelebInfo(in);
        }

        @Override
        public CelebInfo[] newArray(int size) {
            return new CelebInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(avtar_imgPath);
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(_id);
    }
}
