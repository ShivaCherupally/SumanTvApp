package info.dkapp.flow.imagepicker.ui.camera;

import android.content.Context;
import android.content.Intent;

import info.dkapp.flow.imagepicker.model.Config;

/**
 * Created by hoanglam on 8/18/17.
 */

public interface CameraModule {
    Intent getCameraIntent(Context context, Config config,boolean isImage);

    void getImage(Context context, Intent intent, OnImageReadyListener imageReadyListener);
}
