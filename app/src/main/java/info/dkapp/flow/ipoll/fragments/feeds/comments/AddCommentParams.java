package info.dkapp.flow.ipoll.fragments.feeds.comments;

import android.os.Parcel;
import android.os.Parcelable;

import info.dkapp.flow.appmanagers.feed.models.Feed;
import info.dkapp.flow.ipoll.viewholders.FeedViewHolder;

public class AddCommentParams implements Parcelable {
    public Feed feed;
    public Boolean isFeed;
    public String commentText;
    public int feedOrMediaPosition;
    public boolean showProgress;
    public FeedViewHolder feedViewHolder;

    public AddCommentParams(){}

    public AddCommentParams newObj(Feed feed, Boolean isFeed, String commentText, int feedOrMediaPosition, boolean showProgress, FeedViewHolder feedViewHolder){
        AddCommentParams addCommentParams = new AddCommentParams();
        addCommentParams.feed = feed;
        addCommentParams.commentText = commentText;
        addCommentParams.isFeed = isFeed;
        addCommentParams.feedOrMediaPosition = feedOrMediaPosition;
        addCommentParams.showProgress = showProgress;
        addCommentParams.feedViewHolder = feedViewHolder;
        return addCommentParams;
    }

    protected AddCommentParams(Parcel in) {
        feed = in.readParcelable(Feed.class.getClassLoader());
        byte tmpIsFeed = in.readByte();
        isFeed = tmpIsFeed == 0 ? null : tmpIsFeed == 1;
        commentText = in.readString();
        feedOrMediaPosition = in.readInt();
        showProgress = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(feed, flags);
        dest.writeByte((byte) (isFeed == null ? 0 : isFeed ? 1 : 2));
        dest.writeString(commentText);
        dest.writeInt(feedOrMediaPosition);
        dest.writeByte((byte) (showProgress ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AddCommentParams> CREATOR = new Creator<AddCommentParams>() {
        @Override
        public AddCommentParams createFromParcel(Parcel in) {
            return new AddCommentParams(in);
        }

        @Override
        public AddCommentParams[] newArray(int size) {
            return new AddCommentParams[size];
        }
    };
}
