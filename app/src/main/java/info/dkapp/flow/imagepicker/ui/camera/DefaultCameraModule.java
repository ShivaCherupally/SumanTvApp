package info.dkapp.flow.imagepicker.ui.camera;

import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.provider.MediaStore;


import java.io.File;
import java.io.Serializable;

import info.dkapp.flow.celebflow.constants.Constants;
import info.dkapp.flow.userflow.Util.Common;
import info.dkapp.flow.imagepicker.helper.ImageHelper;
import info.dkapp.flow.imagepicker.model.Config;

/**
 * Created by hoanglam on 8/18/17.
 */

public class DefaultCameraModule implements CameraModule, Serializable {

    protected String imagePath;


    @Override
    public Intent getCameraIntent(Context context, Config config,boolean isImage) {
        long uniqueNumber = System.currentTimeMillis() / 1000;
        if (isImage) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);Context appContext = context.getApplicationContext();
            String fileName = "IMG_" + uniqueNumber + ".jpg";
            File file = Common.getInstance().CreateSDCardFile(fileName, Constants.CELEB_KONECT_CAMERA_FOLDER);
            if (file != null) {
                Uri uri = Uri.fromFile(file);
                imagePath = file.getAbsolutePath();
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                ImageHelper.grantAppPermission(context, intent, uri);
                return intent;
            }
        } else {
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            String fileName = "VIDEO_" + uniqueNumber + ".mp4";
            File file = Common.getInstance().CreateSDCardFile(fileName,Constants.CELEB_KONECT_CAMERA_FOLDER);
            if (file != null) {
                Uri uri = Uri.fromFile(file);
                imagePath = file.getAbsolutePath();
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                ImageHelper.grantAppPermission(context, intent, uri);
                return intent;
            }
        }
        return null;
    }

   /* @Override
    public Intent getCameraIntent(Context context, Config config,boolean isImage) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File imageFile = new ImageHelper().createImageFile(config.getSavePath());
        if (imageFile != null) {
            Context appContext = context.getApplicationContext();
            String providerName = String.format(Locale.ENGLISH, "%s%s", appContext.getPackageName(), ".provider");
            Uri uri = FileProvider.getUriForFile(appContext, providerName, imageFile);
            imagePath = imageFile.getAbsolutePath();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            ImageHelper.grantAppPermission(context, intent, uri);
            return intent;
        }
        return null;
    }*/

    @Override
    public void getImage(final Context context, Intent intent, final OnImageReadyListener imageReadyListener) {
        if (imageReadyListener == null) {
            throw new IllegalStateException("OnImageReadyListener must not be null");
        }

        if (imagePath == null) {
            imageReadyListener.onImageReady(null);
            return;
        }

        final Uri imageUri = Uri.parse(new File(imagePath).toString());
        if (imageUri != null) {
            MediaScannerConnection.scanFile(context.getApplicationContext(), new String[]{imageUri.getPath()}
                    , null
                    , new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            if (path != null) {
                                path = imagePath;
                            }
                            imageReadyListener.onImageReady(ImageHelper.singleListFromPath(path));
                            ImageHelper.revokeAppPermission(context, imageUri);
                        }
                    });
        }
    }
}
