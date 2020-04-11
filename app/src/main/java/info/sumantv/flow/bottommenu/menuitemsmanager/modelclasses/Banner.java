package info.sumantv.flow.bottommenu.menuitemsmanager.modelclasses;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Banner implements Parcelable
{
    @SerializedName("_id")
    public String id;

    public String contestId;

    public String bannerImage;

    public String bannerTitle;

    public String bannerType;

    public boolean isActive = false;

    public boolean isSubmitted = false;

    protected Banner(Parcel in) {
        id = in.readString();
        contestId = in.readString();
        bannerImage = in.readString();
        bannerTitle = in.readString();
        bannerType = in.readString();
        isActive = in.readByte() != 0;
        isSubmitted = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(contestId);
        dest.writeString(bannerImage);
        dest.writeString(bannerTitle);
        dest.writeString(bannerType);
        dest.writeByte((byte) (isActive ? 1 : 0));
        dest.writeByte((byte) (isSubmitted ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Banner> CREATOR = new Creator<Banner>() {
        @Override
        public Banner createFromParcel(Parcel in) {
            return new Banner(in);
        }

        @Override
        public Banner[] newArray(int size) {
            return new Banner[size];
        }
    };
}
