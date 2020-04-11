package info.sumantv.flow.ipoll.interfaces.feeds;

import android.net.Uri;

import java.util.List;

import info.sumantv.flow.appmanagers.feed.models.Celebrity;
import info.sumantv.flow.appmanagers.feed.models.Feed;
import info.sumantv.flow.appmanagers.feed.models.Media;
import info.sumantv.flow.ipoll.viewholders.FeedViewHolder;
import info.sumantv.flow.ipoll.viewholders.OnlineCelebritiesViewHolder;


/**
 * Created by User on 11-07-2018.
 **/

public interface IFeedAdapter {

    public void addMedia();

    public void retryLoadMore();

    public void showAlert(String content, int type);

    public void viewCelebDetails(List<Feed> feeds, int position, boolean isSelf);

    public void viewFeedDetails(Feed feed, int position, int mediaPosition);

    public void likeAction(Feed feed, int position, FeedViewHolder feedViewHolder);

    public void shareToOther(Feed feed, int position);

    public void userLikes(Feed feed, int position);

    public void addComment(Feed feed, int position);

    public void userComments(Feed feed, int position);

    public void createFeed();

    public void openOptionsFeed(Feed feed, int position);

    public void openVideoPlayer(Feed feed,Uri uri, Media media,int position,int mediaPosition);

    public void deleteFeed(int position);

    public void updateFeed(Feed feed, int position);

    public void openImage(Feed feed, Media media, int position);

    public void navigatetoCelebProfile(int postion, List<Celebrity> list, OnlineCelebritiesViewHolder onlineCelebritiesViewHolder);

//    public void navigatetoCelebProfile(int postion, List<Celebrity> list, CreateStoriesViewHolder onlineCelebritiesViewHolder);

    public void feedContentViewLess(int position);

    public void openKonect(Feed feed, int position);

    public void openOptionDailog(Feed feed, int position);

    public void hideFeed(int position, boolean hideFeed);
}
