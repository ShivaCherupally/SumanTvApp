package info.dkapp.flow.imagepicker.ui.imagepicker;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;


import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import info.dkapp.flow.celebflow.constants.MediaType;
import info.dkapp.flow.utils.Logger;
import info.dkapp.flow.imagepicker.listener.OnImageLoaderListener;
import info.dkapp.flow.imagepicker.model.Folder;
import info.dkapp.flow.imagepicker.model.Image;

/**
 * Created by hoanglam on 8/17/17.
 */

public class ImageFileLoader {
    String fetchQuery = null;
    public boolean isSizeLimitExceed = false;
    public boolean isGifVisible = false;
    int existingMedia = 0;
    boolean isMultipleImage;

    public boolean isCountLimitExceed = false;
    private final String[] projection = new String[]{MediaStore.Files.FileColumns._ID
            , MediaStore.Files.FileColumns.DISPLAY_NAME
            , MediaStore.Files.FileColumns.DATA
            , MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME,
            MediaStore.Files.FileColumns.MIME_TYPE,
            MediaStore.Files.FileColumns.SIZE,
            MediaStore.Files.FileColumns.MEDIA_TYPE};

    private Context context;
    private ExecutorService executorService;

    public ImageFileLoader(Context context, boolean isGifVisible,boolean isMultipleImage) {
        this.context = context;
        this.isGifVisible = isGifVisible;
        this.isMultipleImage = isMultipleImage;
        if (isMultipleImage){
            addPickerType(1);
        }else {
            addPickerType(3);
        }

    }

    private void addPickerType(int value) {
        switch (value) {
            case 1: // only images
                fetchQuery = MediaStore.Files.FileColumns.MEDIA_TYPE + " = 1";
                break;

            case 2: // only videos
                fetchQuery = MediaStore.Files.FileColumns.MEDIA_TYPE + " = 3";
                break;

            case 3: // both images and videos
                fetchQuery = MediaStore.Files.FileColumns.MEDIA_TYPE + " = 1 OR " + MediaStore.Files.FileColumns.MEDIA_TYPE + " = 3";
                break;
        }

    }

    private static File makeSafeFile(String path) {
        if (path == null || path.isEmpty()) {
            return null;
        }
        try {
            return new File(path);
        } catch (Exception ignored) {
            return null;
        }
    }

    public void loadDeviceImages(boolean isFolderMode, OnImageLoaderListener listener) {
        getExecutorService().execute(new ImageLoadRunnable(isFolderMode, listener));
    }

    public void abortLoadImages() {
        if (executorService != null) {
            executorService.shutdown();
            executorService = null;
        }
    }

    private ExecutorService getExecutorService() {
        if (executorService == null) {
            executorService = Executors.newSingleThreadExecutor();
        }
        return executorService;
    }

    private class ImageLoadRunnable implements Runnable {

        private boolean isFolderMode;
        private OnImageLoaderListener listener;

        public ImageLoadRunnable(boolean isFolderMode, OnImageLoaderListener listener) {
            this.isFolderMode = isFolderMode;
            this.listener = listener;
        }

        @Override
        public void run() {
            /*Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
                    null, null, MediaStore.Images.Media.DATE_ADDED);*/
            Cursor cursor = context.getContentResolver().query(MediaStore.Files.getContentUri("external"),
                    projection,
                    fetchQuery,
                    null,
                    MediaStore.Files.FileColumns.DATE_ADDED);

            if (cursor == null) {
                listener.onFailed(new NullPointerException());
                return;
            }

            List<Image> images = new ArrayList<>(cursor.getCount());
            Map<String, Folder> folderMap = isFolderMode ? new LinkedHashMap<String, Folder>() : null;

            if (cursor.moveToLast()) {
                do {
                    long id = cursor.getLong(cursor.getColumnIndex(projection[0]));
                    String name = cursor.getString(cursor.getColumnIndex(projection[1]));
                    String path = cursor.getString(cursor.getColumnIndex(projection[2]));
                    String bucket = cursor.getString(cursor.getColumnIndex(projection[3]));
                    String size = cursor.getString(cursor.getColumnIndex(projection[5])) == null ? "100000" : cursor.getString(cursor.getColumnIndex(projection[5]));
                    MediaType media_type = cursor.getString(cursor.getColumnIndex(projection[6])).equals("1") ? MediaType.IMAGE : MediaType.VIDEO;
                    String min_type = cursor.getString(cursor.getColumnIndex(projection[4]));
                    Uri uri = null;
                    Uri thumbnailUri = null;
                    if (cursor.getString(cursor.getColumnIndex(projection[6])).equals("1")) {
                        uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + cursor.getInt(0));
                        thumbnailUri = Uri.withAppendedPath(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, "" + cursor.getInt(0));
                    } else {
                        uri = Uri.withAppendedPath(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, "" + cursor.getInt(0));
                        thumbnailUri = Uri.withAppendedPath(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI, "" + cursor.getInt(0));
                    }
                    File file = makeSafeFile(path);
                    if (file != null && file.exists()) {
                        if (!isGifVisible) {
                            Image image = new Image(id, name, path, media_type, min_type, size, uri, thumbnailUri);
                            images.add(image);
                            Logger.d("mimeType", image.mimeType);
                            if (folderMap != null) {
                                Folder folder = folderMap.get(bucket);
                                if (folder == null) {
                                    folder = new Folder(bucket);
                                    folderMap.put(bucket, folder);
                                }
                                if (!image.mimeType.equals("image/gif")) {
                                    folder.getImages().add(image);
                                }
                            }
                        } else {
                            Image image = new Image(id, name, path, media_type, min_type, size, uri, thumbnailUri);
                            images.add(image);
                            Logger.d("mimeType", image.mimeType);
                            if (folderMap != null) {
                                Folder folder = folderMap.get(bucket);
                                if (folder == null) {
                                    folder = new Folder(bucket);
                                    folderMap.put(bucket, folder);
                                }
                                folder.getImages().add(image);

                            }
                        }
                    }

                } while (cursor.moveToPrevious());
            }
            cursor.close();


            /* Convert HashMap to ArrayList if not null */
            List<Folder> folders = null;
            if (folderMap != null) {
                folders = new ArrayList<>(folderMap.values());
            }
            listener.onImageLoaded(images, folders);
        }
    }
}
