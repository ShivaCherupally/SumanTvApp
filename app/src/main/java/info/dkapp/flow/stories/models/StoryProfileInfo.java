package info.dkapp.flow.stories.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Uday Kumay Vurukonda on 09-Sep-19.
 * <p>
 * Mr.Psycho
 */
public class StoryProfileInfo implements Parcelable {

    @SerializedName("memberId")
    @Expose
    public String memberId;
    @SerializedName("isStorySeen")
    @Expose
    public Boolean isStorySeen;
    @SerializedName("createdAt")
    @Expose
    public String createdAt;
    @SerializedName("storyMemberInfo")
    @Expose

    public StoryMemberInfo storyMemberInfo;
    public StoriesData storiesData;


    public void setStoriesData(StoriesData storiesData) {
        this.storiesData = storiesData;
    }

    protected StoryProfileInfo(Parcel in) {
        memberId = in.readString();
        byte tmpIsStorySeen = in.readByte();
        isStorySeen = tmpIsStorySeen == 0 ? null : tmpIsStorySeen == 1;
        createdAt = in.readString();
        storyMemberInfo = in.readParcelable(StoryMemberInfo.class.getClassLoader());
        storiesData = in.readParcelable(StoriesData.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(memberId);
        dest.writeByte((byte) (isStorySeen == null ? 0 : isStorySeen ? 1 : 2));
        dest.writeString(createdAt);
        dest.writeParcelable(storyMemberInfo, flags);
        dest.writeParcelable(storiesData, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<StoryProfileInfo> CREATOR = new Creator<StoryProfileInfo>() {
        @Override
        public StoryProfileInfo createFromParcel(Parcel in) {
            return new StoryProfileInfo(in);
        }

        @Override
        public StoryProfileInfo[] newArray(int size) {
            return new StoryProfileInfo[size];
        }
    };
}
