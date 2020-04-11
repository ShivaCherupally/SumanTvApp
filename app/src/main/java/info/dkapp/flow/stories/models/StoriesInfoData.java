package info.dkapp.flow.stories.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class StoriesInfoData implements Parcelable {

    @SerializedName("story")
    @Expose
    public ArrayList<StoriesIndividualData> storiesIndividualData;




    protected StoriesInfoData(Parcel in) {
        storiesIndividualData = in.createTypedArrayList(StoriesIndividualData.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(storiesIndividualData);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<StoriesInfoData> CREATOR = new Creator<StoriesInfoData>() {
        @Override
        public StoriesInfoData createFromParcel(Parcel in) {
            return new StoriesInfoData(in);
        }

        @Override
        public StoriesInfoData[] newArray(int size) {
            return new StoriesInfoData[size];
        }
    };
}
