package info.sumantv.flow.imagepicker.ui.camera;


import java.util.List;

import info.sumantv.flow.imagepicker.model.Image;

public interface OnImageReadyListener {
    void onImageReady(List<Image> images);
}
