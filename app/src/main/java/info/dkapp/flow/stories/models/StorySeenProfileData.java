package info.dkapp.flow.stories.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StorySeenProfileData implements Parcelable {

    @SerializedName("story")
    @Expose
    public ArrayList<SeenProfileInnerData> StorySeenProfileInfo;

    @SerializedName("count")
    @Expose
    public int seencount;


    protected StorySeenProfileData(Parcel in) {
        StorySeenProfileInfo = in.createTypedArrayList(SeenProfileInnerData.CREATOR);
        seencount = in.readInt();
    }

    public static final Creator<StorySeenProfileData> CREATOR = new Creator<StorySeenProfileData>() {
        @Override
        public StorySeenProfileData createFromParcel(Parcel in) {
            return new StorySeenProfileData(in);
        }

        @Override
        public StorySeenProfileData[] newArray(int size) {
            return new StorySeenProfileData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(StorySeenProfileInfo);
        parcel.writeInt(seencount);
    }
}
