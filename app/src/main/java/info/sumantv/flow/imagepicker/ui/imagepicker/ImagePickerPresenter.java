package info.sumantv.flow.imagepicker.ui.imagepicker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import java.io.File;
import java.util.List;


import info.sumantv.flow.R;
import info.sumantv.flow.imagepicker.listener.OnImageLoaderListener;
import info.sumantv.flow.imagepicker.model.Config;
import info.sumantv.flow.imagepicker.model.Folder;
import info.sumantv.flow.imagepicker.model.Image;
import info.sumantv.flow.imagepicker.ui.camera.CameraModule;
import info.sumantv.flow.imagepicker.ui.camera.DefaultCameraModule;
import info.sumantv.flow.imagepicker.ui.camera.OnImageReadyListener;
import info.sumantv.flow.imagepicker.ui.common.BasePresenter;

/**
 * Created by hoanglam on 8/17/17.
 */

public class ImagePickerPresenter extends BasePresenter<ImagePickerView> {

    private ImageFileLoader imageLoader;
    private CameraModule cameraModule = new DefaultCameraModule();
    private Handler handler = new Handler(Looper.getMainLooper());

    public ImagePickerPresenter(ImageFileLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    public void abortLoading() {
        imageLoader.abortLoadImages();
    }

    public void loadImages(boolean isFolderMode) {
        if (!isViewAttached()) return;

        getView().showLoading(true);
        imageLoader.loadDeviceImages(isFolderMode, new OnImageLoaderListener() {
            @Override
            public void onImageLoaded(final List<Image> images, final List<Folder> folders) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (isViewAttached()) {
                            getView().showFetchCompleted(images, folders);
                            final boolean isEmpty = folders != null ? folders.isEmpty() : images.isEmpty();
                            if (isEmpty) {
                                getView().showEmpty();
                            } else {
                                getView().showLoading(false);
                            }
                        }
                    }
                });
            }

            @Override
            public void onFailed(final Throwable throwable) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (isViewAttached()) {
                            getView().showError(throwable);
                        }
                    }
                });
            }
        });
    }

    void captureImage(Activity activity, Config config, int requestCode) {
        Context context = activity.getApplicationContext();
        Intent intent = cameraModule.getCameraIntent(activity, config,true);
        if (intent == null) {
            Toast.makeText(context, context.getString(R.string.imagepicker_error_create_image_file), Toast.LENGTH_LONG).show();
            return;
        }
        activity.startActivityForResult(intent, requestCode);
    }
    void captureVideo(Activity activity, Config config, int requestCode) {
        Context context = activity.getApplicationContext();
        Intent intent = cameraModule.getCameraIntent(activity, config,false);
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
                if (!config.isMultipleMode()) {
                    getView().finishPickImages(images);
            //    } else {
                    getView().showCapturedImage(images);
                }
            }
        });
    }


    public void onDoneSelectImages(List<Image> selectedImages) {
        if (selectedImages != null && !selectedImages.isEmpty()) {
            for (int i = 0; i < selectedImages.size(); i++) {
                Image image = selectedImages.get(i);
                File file = new File(image.dataFilePath);
                if (!file.exists()) {
                    selectedImages.remove(i);
                    i--;
                }
            }
        }
        getView().finishPickImages(selectedImages);
    }

}
