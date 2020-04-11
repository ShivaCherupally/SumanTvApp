package info.dkapp.flow.ipoll.interfaces.feeds.likes;


import info.dkapp.flow.appmanagers.feed.models.Like;
import info.dkapp.flow.appmanagers.feed.models.Profile;

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
