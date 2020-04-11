package info.dkapp.flow.stories.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class StoriesData implements Parcelable {

    @SerializedName("story")
    @Expose
    public ArrayList<StoriesInnerData> storiesInnerData;

    @SerializedName("celebInfo")
    @Expose
    public CelebInfo celebProfileInfo;


    @SerializedName("statusSeenCount")
    @Expose
    public int statusSeenCount;


    protected StoriesData(Parcel in) {
        storiesInnerData = in.createTypedArrayList(StoriesInnerData.CREATOR);
        celebProfileInfo = in.readParcelable(CelebInfo.class.getClassLoader());
        statusSeenCount = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(storiesInnerData);
        dest.writeParcelable(celebProfileInfo, flags);
        dest.writeInt(statusSeenCount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<StoriesData> CREATOR = new Creator<StoriesData>() {
        @Override
        public StoriesData createFromParcel(Parcel in) {
            return new StoriesData(in);
        }

        @Override
        public StoriesData[] newArray(int size) {
            return new StoriesData[size];
        }
    };

    public int getStatusSeenCount() {
        return statusSeenCount;
    }

    public void setStatusSeenCount(int statusSeenCount) {
        this.statusSeenCount = statusSeenCount;
    }
}
