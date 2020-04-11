/*
 * Created by Nguyen Hoang Lam
 * Date: ${DATE}
 */

package info.sumantv.flow.imagepicker.ui.imagepicker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;

import androidx.fragment.app.Fragment;
import java.util.ArrayList;


import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.imagepicker.model.Config;
import info.sumantv.flow.imagepicker.model.Folder;
import info.sumantv.flow.imagepicker.model.Image;
import info.sumantv.flow.imagepicker.model.SavePath;
import info.sumantv.flow.imagepicker.ui.camera.CameraActivty;

/**
 * Created by hoanglam on 8/4/16.
 */
public class ImagePicker {

    protected Config config;


    public ImagePicker(Builder builder) {
        config = builder.config;
    }

    public static Builder with(Activity activity) {
        return new ActivityBuilder(activity);
    }

    public static Builder with(Fragment fragment) {
        return new FragmentBuilder(fragment);
    }

    static class ActivityBuilder extends Builder {
        private Activity activity;

        public ActivityBuilder(Activity activity) {
            super(activity);
            this.activity = activity;
        }

        @Override
        public void start() {
            Intent intent = getIntent();
            int requestCode = config.getRequestCode() != 0 ? config.getRequestCode() : Config.RC_PICK_IMAGES;
            if (!config.isCameraOnly()) {
                activity.startActivityForResult(intent, requestCode);
            } else {
                activity.overridePendingTransition(0, 0);
                activity.startActivityForResult(intent, requestCode);
            }
        }
        public void start(Folder folder) {
            Intent intent = getIntent();
            int requestCode = config.getRequestCode() != 0 ? config.getRequestCode() : Config.RC_PICK_IMAGES;
            if (!config.isCameraOnly()) {
                activity.startActivityForResult(intent, requestCode);
            } else {
                activity.overridePendingTransition(0, 0);
                activity.startActivityForResult(intent, requestCode);
            }

        }

        @Override
        public Intent getIntent() {
            Intent intent;
            if (!config.isCameraOnly()) {
                intent = new Intent(activity, ImagePickerActivity.class);
                intent.putExtra(Config.EXTRA_CONFIG, config);
            } else {
                intent = new Intent(activity, CameraActivty.class);
                intent.putExtra(Config.EXTRA_CONFIG, config);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            }
            return intent;
        }
    }

    static class FragmentBuilder extends Builder {
        private Fragment fragment;

        public FragmentBuilder(Fragment fragment) {
            super(fragment);
            this.fragment = fragment;
        }

        @Override
        public void start() {
            Intent intent = getIntent();
            int requestCode = config.getRequestCode() != 0 ? config.getRequestCode() : Config.RC_PICK_IMAGES;
            if (!config.isCameraOnly()) {
                fragment.startActivityForResult(intent, requestCode);
            } else {
                fragment.getActivity().overridePendingTransition(0, 0);
                fragment.startActivityForResult(intent, requestCode);
            }
        }

        @Override
        public Intent getIntent() {
            Intent intent;
            if (!config.isCameraOnly()) {
                intent = new Intent(fragment.getActivity(), ImagePickerActivity.class);
                intent.putExtra(Config.EXTRA_CONFIG, config);
            } else {
                intent = new Intent(fragment.getActivity(), CameraActivty.class);
                intent.putExtra(Config.EXTRA_CONFIG, config);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            }
            return intent;
        }
    }

    public static abstract class Builder extends BaseBuilder {

        public Builder(Activity activity) {
            super(activity);
        }

        public Builder(Fragment fragment) {
            super(fragment.getContext());
        }

        public Builder setToolbarColor(String toolbarColor) {
            config.setToolbarColor(toolbarColor);
            return this;
        }

        public Builder setStatusBarColor(String statusBarColor) {
            config.setStatusBarColor(statusBarColor);
            return this;
        }

        public Builder setToolbarTextColor(String toolbarTextColor) {
            config.setToolbarTextColor(toolbarTextColor);
            return this;
        }

        public Builder setToolbarIconColor(String toolbarIconColor) {
            config.setToolbarIconColor(toolbarIconColor);
            return this;
        }

        public Builder setProgressBarColor(String progressBarColor) {
            config.setProgressBarColor(progressBarColor);
            return this;
        }

        public Builder setBackgroundColor(String backgroundColor) {
            config.setBackgroundColor(backgroundColor);
            return this;
        }

        public Builder setCameraOnly(boolean isCameraOnly) {
            config.setCameraOnly(isCameraOnly);
            return this;
        }

        public Builder setMultipleMode(boolean isMultipleMode) {
            config.setMultipleMode(isMultipleMode);
            return this;
        }

        public Builder setFolderMode(boolean isFolderMode) {
            config.setFolderMode(isFolderMode);
            return this;
        }

        public Builder setGif(boolean isGif) {
            config.setGifVisible(isGif);
            return this;
        }
        public Builder setIsFeed(boolean isFeed) {
            config.setFeed(isFeed);
            return this;
        }

        public Builder setShowCamera(boolean isShowCamera) {
            config.setShowCamera(isShowCamera);
            return this;
        }

        public Builder setMaxSize(int maxSize) {
            config.setMaxSize(maxSize);
            return this;
        }
        public Builder setTotalFileSize(long maxSize) {
            config.setTotalFileSize(maxSize);
            return this;
        }

        public Builder setDoneTitle(String doneTitle) {
            config.setDoneTitle(doneTitle);
            return this;
        }

        public Builder setFolderTitle(String folderTitle) {
            config.setFolderTitle(folderTitle);
            return this;
        }

        public Builder setMultipleImages(boolean isMultipleImage) {
            config.setMultipleImages(isMultipleImage);
            return this;
        }

        public Builder setImageTitle(String imageTitle) {
            config.setImageTitle(imageTitle);
            return this;
        }

        public Builder setLimitMessage(String message) {
            config.setLimitMessage(message);
            return this;
        }


        public Builder setSavePath(String path) {
            config.setSavePath(new SavePath(path, false));
            return this;
        }

        public Builder setAlwaysShowDoneButton(boolean isAlwaysShowDoneButton) {
            config.setAlwaysShowDoneButton(isAlwaysShowDoneButton);
            return this;
        }

        public Builder setKeepScreenOn(boolean keepScreenOn) {
            config.setKeepScreenOn(keepScreenOn);
            return this;
        }

        public Builder setSelectedImages(ArrayList<Image> selectedImages) {
            config.setSelectedImages(selectedImages);
            return this;
        }

        public Builder setFolder(Folder folder) {
            config.setFolder(folder);
            return this;
        }

        public Builder setRequestCode(int requestCode) {
            config.setRequestCode(requestCode);
            return this;
        }

        public abstract void start();

        public abstract Intent getIntent();

    }

    public static abstract class BaseBuilder {

        protected Config config;

        public BaseBuilder(Context context) {
            this.config = new Config();

            Resources resources = context.getResources();
            config.setCameraOnly(false);
            config.setMultipleMode(true);
            config.setFolderMode(true);
            config.setGifVisible(true);
            config.setShowCamera(true);
            config.setMaxSize(Config.MAX_SIZE);
            config.setTotalFileSize(Constants.MEDIA_SIZE_LIMIT_IN_BYES);
            config.setDoneTitle(resources.getString(R.string.imagepicker_action_done));
            config.setFolderTitle(resources.getString(R.string.imagepicker_title_folder));
            config.setImageTitle(resources.getString(R.string.imagepicker_title_image));
            config.setLimitMessage(resources.getString(R.string.imagepicker_msg_limit_images));
            config.setSavePath(SavePath.DEFAULT);
            config.setAlwaysShowDoneButton(false);
            config.setKeepScreenOn(false);
            config.setSelectedImages(new ArrayList<Image>());
            config.setMultipleImages(false);
            config.setFeed(false);
        }
    }

}


