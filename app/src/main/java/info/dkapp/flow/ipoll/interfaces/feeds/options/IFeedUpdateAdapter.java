package info.dkapp.flow.ipoll.interfaces.feeds.options;


import info.dkapp.flow.appmanagers.feed.models.Media;

/**
 * Created by User on 20-08-2018.
 **/

public interface IFeedUpdateAdapter
{
    public void addTitle(String title);
    public void addDescription(String description);
    public void addMediaCaption(Media media, String caption, int position);
    public void removeMedia(Media media, int position);
    public void addMoreMedia();
    public void openPlacesSearch();
}
