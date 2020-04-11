package info.dkapp.flow.imagepicker.ui.imagepicker;


import java.util.List;

import info.dkapp.flow.imagepicker.model.Folder;
import info.dkapp.flow.imagepicker.model.Image;
import info.dkapp.flow.imagepicker.ui.common.MvpView;

/**
 * Created by hoanglam on 8/17/17.
 */

public interface ImagePickerView extends MvpView {

    void showLoading(boolean isLoading);

    void showFetchCompleted(List<Image> images, List<Folder> folders);

    void showError(Throwable throwable);

    void showEmpty();

    void showCapturedImage(List<Image> images);

    void finishPickImages(List<Image> images);

}
