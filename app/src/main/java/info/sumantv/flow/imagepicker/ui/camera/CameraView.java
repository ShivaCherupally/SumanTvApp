package info.sumantv.flow.imagepicker.ui.camera;


import java.util.List;

import info.sumantv.flow.imagepicker.model.Image;
import info.sumantv.flow.imagepicker.ui.common.MvpView;

/**
 * Created by hoanglam on 8/22/17.
 */

public interface CameraView extends MvpView {

    void finishPickImages(List<Image> images);
}
