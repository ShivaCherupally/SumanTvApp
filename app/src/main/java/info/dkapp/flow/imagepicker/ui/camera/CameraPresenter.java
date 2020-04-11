package info.dkapp.flow.imagepicker.ui.camera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


import java.util.List;


import info.dkapp.flow.R;
import info.dkapp.flow.imagepicker.model.Config;
import info.dkapp.flow.imagepicker.model.Image;
import info.dkapp.flow.imagepicker.ui.common.BasePresenter;

/**
 * Created by hoanglam on 8/22/17.
 */

public class CameraPresenter extends BasePresenter<CameraView> {

    private CameraModule cameraModule = new DefaultCameraModule();

    public CameraPresenter() {
    }


    void captureImage(Activity activity, Config config, int requestCode,boolean isImage) {
        Context context = activity.getApplicationContext();
        Intent intent = cameraModule.getCameraIntent(activity, config,true);
        if (intent == null) {
            Toast.makeText(context, context.getString(R.string.imagepicker_error_create_image_file), Toast.LENGTH_LONG).show();
            return;
        }
        activity.startActivityForResult(intent, requestCode);
    }

    public void finishCaptureImage(Context context, Intent data, final Config config) {
        cameraModule.getImage(context, data, new OnImageReadyListener() {
            @Override
            public void onImageReady(List<Image> images) {
                getView().finishPickImages(images);
            }
        });
    }
}
