package info.sumantv.flow.stories.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Uday Kumay Vurukonda on 09-Sep-19.
 * <p>
 * Mr.Psycho
 */
public class ShowCelebStoriesData implements Parcelable
{

    @SerializedName("storyProfileInfo")
    @Expose
    public List<StoryProfileInfo> storyProfileInfo = null;

    public final static Parcelable.Creator<ShowCelebStoriesData> CREATOR = new Creator<ShowCelebStoriesData>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ShowCelebStoriesData createFromParcel(Parcel in) {
            return new ShowCelebStoriesData(in);
        }

        public ShowCelebStoriesData[] newArray(int size) {
            return (new ShowCelebStoriesData[size]);
        }

    }
            ;

    protected ShowCelebStoriesData(Parcel in) {
        in.readList(this.storyProfileInfo, (StoryProfileInfo.class.getClassLoader()));
    }

    public ShowCelebStoriesData() {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(storyProfileInfo);
    }

    public int describeContents() {
        return 0;
    }

}
