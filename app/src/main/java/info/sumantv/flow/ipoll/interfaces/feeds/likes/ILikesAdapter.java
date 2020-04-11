package info.sumantv.flow.ipoll.interfaces.feeds.likes;


import info.sumantv.flow.appmanagers.feed.models.Like;
import info.sumantv.flow.appmanagers.feed.models.Profile;

/**
 * Created by User on 06-08-2018.
 **/

public interface ILikesAdapter
{
    public void onProfileClick(Like like, int position);
    public void retryLoadMore();
    void selfProfile(Profile like);
    void celebUserProfile(Profile profile);
}
