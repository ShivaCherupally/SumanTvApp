package info.dkapp.flow.imagepicker.listener;


import java.util.List;

import info.dkapp.flow.imagepicker.model.Folder;
import info.dkapp.flow.imagepicker.model.Image;

/**
 * Created by hoanglam on 8/17/17.
 */

public interface OnImageLoaderListener {
    void onImageLoaded(List<Image> images, List<Folder> folders);

    void onFailed(Throwable throwable);
}
