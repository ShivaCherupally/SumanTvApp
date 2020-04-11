package info.sumantv.flow.ipoll.interfaces.feeds.details;

import android.net.Uri;

import java.util.List;

import info.sumantv.flow.appmanagers.feed.models.Feed;
import info.sumantv.flow.appmanagers.feed.models.Media;


/**
 * Created by User on 03-08-2018.
 **/

public interface IFeedDetailsAdapter
{
    public void likeAction(Feed feed, int position);
    public void shareToOther(Media media, int position);
    public void shareToOther(Feed feed, int position);
    public void userLikes(Feed feed, int position);
    public void addComment(Feed feed, int position);
    public void userComments(Feed feed, int position);
    public void likeActionMedia(Media media, int position);
    public void userLikesMedia(Media media, int position);
    public void userCommentsMedia(Media media, int position);
    public void openFeedOptions(Feed feed, int position);
    public void openVideoPlayer(Feed feed,Uri uri, Media media,int position);
    public void openPhotosMedia(Feed feed,List<Media> mediaList, int position);
    public void viewCelebDetails(Feed feeds, int position, boolean isSelf);
    public void openKonect(Feed feed, int position);
}
