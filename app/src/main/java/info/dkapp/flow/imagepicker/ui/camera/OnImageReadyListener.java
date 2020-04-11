package info.dkapp.flow.imagepicker.ui.camera;


import java.util.List;

import info.dkapp.flow.imagepicker.model.Image;

public interface OnImageReadyListener {
    void onImageReady(List<Image> images);
}
