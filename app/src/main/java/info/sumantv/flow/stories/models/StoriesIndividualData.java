package info.sumantv.flow.stories.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StoriesIndividualData implements Parcelable {
    @SerializedName("celebInfo")
    @Expose
    public CelebInfo celebProfileInfo;

    @SerializedName("storyArr")
    @Expose
    public ArrayList<StoriesInnerData> storiesInnerData;

    protected StoriesIndividualData(Parcel in) {
        celebProfileInfo = in.readParcelable(CelebInfo.class.getClassLoader());
        storiesInnerData = in.createTypedArrayList(StoriesInnerData.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(celebProfileInfo, flags);
        dest.writeTypedList(storiesInnerData);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<StoriesIndividualData> CREATOR = new Creator<StoriesIndividualData>() {
        @Override
        public StoriesIndividualData createFromParcel(Parcel in) {
            return new StoriesIndividualData(in);
        }

        @Override
        public StoriesIndividualData[] newArray(int size) {
            return new StoriesIndividualData[size];
        }
    };
}
