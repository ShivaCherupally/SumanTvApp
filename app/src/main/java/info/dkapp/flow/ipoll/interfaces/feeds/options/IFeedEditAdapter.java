package info.dkapp.flow.ipoll.interfaces.feeds.options;


import info.dkapp.flow.imagepicker.model.Image;

/**
 * Created by User on 06-08-2018.
 **/

public interface IFeedEditAdapter
{
    public void addCaptionGalleryFile(Image galleryFile, String caption, int position);
    public void removeGalleryFile(Image galleryFile, int position);
    public void cropGalleryFile(Image galleryFile, int position);
    public void addMoreMedia();
}
