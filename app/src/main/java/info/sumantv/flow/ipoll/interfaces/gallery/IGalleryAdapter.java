package info.sumantv.flow.ipoll.interfaces.gallery;


import info.sumantv.flow.appmanagers.feed.models.GalleryFile;

/**
 * Created by User on 30-07-2018.
 **/

public interface IGalleryAdapter
{
    public void click(GalleryFile galleryFile, int position);
    public void longPressed(GalleryFile galleryFile, int position);
}
