package info.dkapp.flow.stories.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StorySeenProfiles implements Parcelable {

    @SerializedName("avtar_imgPath")
    @Expose
    public String avtar_imgPath;

    protected StorySeenProfiles(Parcel in) {
        avtar_imgPath = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(avtar_imgPath);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<StorySeenProfiles> CREATOR = new Creator<StorySeenProfiles>() {
        @Override
        public StorySeenProfiles createFromParcel(Parcel in) {
            return new StorySeenProfiles(in);
        }

        @Override
        public StorySeenProfiles[] newArray(int size) {
            return new StorySeenProfiles[size];
        }
    };
}
