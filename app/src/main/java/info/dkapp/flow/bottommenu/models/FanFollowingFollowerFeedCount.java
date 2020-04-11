
package info.dkapp.flow.bottommenu.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FanFollowingFollowerFeedCount implements Parcelable
{

    @SerializedName("UrFanOf")
    @Expose
    private int urFanOf;
    @SerializedName("Following")
    @Expose
    private int following;
    @SerializedName("fanOfUr")
    @Expose
    private int fanOfUr;
    @SerializedName("Followers")
    @Expose
    private int followers;
    @SerializedName("feedCount")
    @Expose
    private int feedCount;
    @SerializedName("scheduleCount")
    @Expose
    private int scheduleCount;
    @SerializedName("isFan")
    @Expose
    private boolean isFan;
    @SerializedName("isFollower")
    @Expose
    private boolean isFollower;


    protected FanFollowingFollowerFeedCount(Parcel in) {
        urFanOf = in.readInt();
        following = in.readInt();
        fanOfUr = in.readInt();
        followers = in.readInt();
        feedCount = in.readInt();
        scheduleCount = in.readInt();
        isFan = in.readByte() != 0;
        isFollower = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(urFanOf);
        dest.writeInt(following);
        dest.writeInt(fanOfUr);
        dest.writeInt(followers);
        dest.writeInt(feedCount);
        dest.writeInt(scheduleCount);
        dest.writeByte((byte) (isFan ? 1 : 0));
        dest.writeByte((byte) (isFollower ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FanFollowingFollowerFeedCount> CREATOR = new Creator<FanFollowingFollowerFeedCount>() {
        @Override
        public FanFollowingFollowerFeedCount createFromParcel(Parcel in) {
            return new FanFollowingFollowerFeedCount(in);
        }

        @Override
        public FanFollowingFollowerFeedCount[] newArray(int size) {
            return new FanFollowingFollowerFeedCount[size];
        }
    };

    public int getUrFanOf() {
        return urFanOf;
    }

    public void setUrFanOf(int urFanOf) {
        this.urFanOf = urFanOf;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public int getFanOfUr() {
        return fanOfUr;
    }

    public void setFanOfUr(int fanOfUr) {
        this.fanOfUr = fanOfUr;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFeedCount() {
        return feedCount;
    }

    public void setFeedCount(int feedCount) {
        this.feedCount = feedCount;
    }

    public int getScheduleCount() {
        return scheduleCount;
    }

    public void setScheduleCount(int scheduleCount) {
        this.scheduleCount = scheduleCount;
    }

    public boolean isFan() {
        return isFan;
    }

    public void setFan(boolean fan) {
        isFan = fan;
    }

    public boolean isFollower() {
        return isFollower;
    }

    public void setFollower(boolean follower) {
        isFollower = follower;
    }

    public static Creator<FanFollowingFollowerFeedCount> getCREATOR() {
        return CREATOR;
    }
}
