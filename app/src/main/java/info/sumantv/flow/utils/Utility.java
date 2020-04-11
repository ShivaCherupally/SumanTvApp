package info.sumantv.flow.utils;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.facebook.imagepipeline.common.ResizeOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicBoolean;

import id.zelory.compressor.Compressor;
import info.sumantv.flow.appmanagers.feed.models.FacePoint;
import info.sumantv.flow.appmanagers.feed.models.Media;
import info.sumantv.flow.bottommenu.models.IPollOption;
import info.sumantv.flow.databaseutil.appstart.AppController;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.constants.CollageController;
import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.celebflow.constants.Permission;
import info.sumantv.flow.ipoll.dialogs.custom.CustomAlertDialog;
import info.sumantv.flow.ipoll.interfaces.dialogs.custom.ICustomAlertDialog;
import info.sumantv.flow.retrofitcall.SessionManager;

public class Utility {


    public static boolean isNetworkAvailable(Activity activity) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo == null || !networkInfo.isConnected()) {
                return false;
                /* aka, do nothing */
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Context getContext() {
        return AppController.getInstance().getApplicationContext();
    }

    public static Context getActivity() {
        return AppController.getInstance().getApplicationContext();
    }

    public static String getStringResource(Context context, int stringId) {
        return context.getResources().getString(stringId);
    }

    public static int getScreenWidth(Context context) {
//        Log.e("WidthSize", context.getResources().getDisplayMetrics().widthPixels + "");
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getFeedViewPagerHeight(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int dpSize(Context context, double sizeInDp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (sizeInDp * scale + 0.5f);
    }

    public static LinearLayout.LayoutParams getLayoutParams(double ratio, int currentWidth) {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (currentWidth / ratio));
    }

    public static RelativeLayout.LayoutParams getRelativeLayoutParams(double ratio, int currentWidth) {
        return new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int) (currentWidth / ratio));
    }

    public static String makeDateToAgo(String s) {
        try {
            SimpleDateFormat simDf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            simDf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date yourDate = simDf.parse(s);
            Timestamp timestamp = new Timestamp(yourDate.getTime());
            return getTimeAgo(yourDate, timestamp.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return "Long ago";
        }
    }

    public static String timesAgo(String s) {
        try {
            SimpleDateFormat simDf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            simDf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date yourDate = simDf.parse(s);
            Timestamp timestamp = new Timestamp(yourDate.getTime());
            return getTimeAgoShort(yourDate, timestamp.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return "Long ago";
        }
    }

    private static String getTimeAgo(Date finalDate, long time) {
        if (time < 1000000000000L) {
            time *= 1000;
        }
        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return "Just now";
        }
        //
        final long diff = now - time;
        long elapsedDays = diff / Constants.DAY_MILLIS;
        if (diff <= Constants.MINUTE_MILLIS) {
            return "Just now";
        } else if (diff < 2 * Constants.MINUTE_MILLIS) {
            return "1 Minute ago ";
        } else if (diff < 50 * Constants.MINUTE_MILLIS) {
            return diff / Constants.MINUTE_MILLIS + " Minutes ago ";
        } else if (diff < 90 * Constants.MINUTE_MILLIS) {
            return "1 hour ";
        } else if (diff < 24 * Constants.HOUR_MILLIS) {
            return diff / Constants.HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * Constants.HOUR_MILLIS) {
            return "Yesterday";
        } else if (elapsedDays < 7) {
            return elapsedDays + "d ";
        } else {
            SimpleDateFormat formatTime = new SimpleDateFormat("hh:mm a");
            SimpleDateFormat formatDate = new SimpleDateFormat("dd MMM yy");
            return formatDate.format(finalDate) + ", " + formatTime.format(finalDate);
        }
    }

    private static String getTimeAgoShort(Date finalDate, long time) {
        if (time < 1000000000000L) {
            time *= 1000;
        }
        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return "Just now";
        }
        //
        final long diff = now - time;
        long elapsedDays = diff / Constants.DAY_MILLIS;
        if (diff <= Constants.MINUTE_MILLIS) {
            return "Just now";
        } else if (diff < 2 * Constants.MINUTE_MILLIS) {
            return "1m";
        } else if (diff < 50 * Constants.MINUTE_MILLIS) {
            return diff / Constants.MINUTE_MILLIS + "m";
        } else if (diff < 90 * Constants.MINUTE_MILLIS) {
            return "1h";
        } else if (diff < 24 * Constants.HOUR_MILLIS) {
            return diff / Constants.HOUR_MILLIS + "h";
        } else if (diff < 48 * Constants.HOUR_MILLIS) {
            return "Yesterday";
        } else if (elapsedDays < 7) {
            return elapsedDays + "d ";
        } else {
            SimpleDateFormat formatTime = new SimpleDateFormat("hh:mm a");
            SimpleDateFormat formatDate = new SimpleDateFormat("dd MMM yy");
            return formatDate.format(finalDate) + ", " + formatTime.format(finalDate);
        }
    }

    private static String getTimeAgoShortForm(Date finalDate, long time) {
        if (time < 1000000000000L) {
            time *= 1000;
        }
        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return "Just now";
        }
        //
        final long diff = now - time;
        long elapsedDays = diff / Constants.DAY_MILLIS;
        if (diff <= Constants.MINUTE_MILLIS) {
            return "Just now";
        } else if (diff < 2 * Constants.MINUTE_MILLIS) {
            return "1 minute ago";
        } else if (diff < 50 * Constants.MINUTE_MILLIS) {
            return diff / Constants.MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * Constants.MINUTE_MILLIS) {
            return "1 hour ago";
        } else if (diff < 24 * Constants.HOUR_MILLIS) {
            return diff / Constants.HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * Constants.HOUR_MILLIS) {
            return "Yesterday";
        } else if (elapsedDays < 7) {
            return elapsedDays + " days ago";
        } else {
            SimpleDateFormat formatTime = new SimpleDateFormat("hh:mm a");
            SimpleDateFormat formatDate = new SimpleDateFormat("dd MMM yyyy");
            return formatDate.format(finalDate) + ", " + formatTime.format(finalDate);
        }
    }

    public static Typeface getTypeface(int value, Context context) {
        String path = "fonts/sf_compact_display/sf_medium.otf";

        switch (value) {
            // SFCompactDisplay font family
            case 8:
                path = "fonts/sf_compact_display/sf_light.otf";
                break;
            case 9:
                path = "fonts/sf_compact_display/sf_regular.otf";
                break;
            case 10:
                path = "fonts/sf_compact_display/sf_medium.otf";
                ;
                break;
            case 11:
                path = "fonts/sf_compact_display/sf_semibold.otf";
                break;

            case 12:
                path = "fonts/sf_compact_display/sf_bold.otf";
                break;

            case 13:
                path = "fonts/sf_compact_display/sf_black.otf";
                break;


        }
        return Typeface.createFromAsset(context.getAssets(), path);
    }


    public static HashMap<String, Integer> generateRequestCodes() {
        HashMap<String, Integer> hashMap = new HashMap<>();

        // old things
        hashMap.put("READ_STORAGE", 39014);
        hashMap.put("WRITE_STORAGE", 39015);
        hashMap.put("MULTIPLE_IMAGES", 39016);
        hashMap.put("MULTIPLE_VIDEOS", 39017);
        hashMap.put("MULTIPLE_VIDEOS_IMAGES", 39018);

        //permissions
        hashMap.put("LOCATION_REQUEST", 24001);
        hashMap.put("WRITE_SMS_REQUEST", 24002);
        hashMap.put("READ_SMS_REQUEST", 24003);
        hashMap.put("READ_STORAGE_REQUEST", 24004);
        hashMap.put("WRITE_STORAGE_REQUEST", 24005);
        hashMap.put("CAMERA_REQUEST", 24006);
        hashMap.put("RECORD_REQUEST", 24007);

        //snap from camera
        hashMap.put("SNAP_FROM_CAMERA", 20001);
        hashMap.put("VIDEO_FROM_CAMERA", 20002);


        //pick media from gallery
        hashMap.put("MEDIA_FROM_GALLERY", 19001);
        hashMap.put("FEED_EDIT", 19002);
        hashMap.put("ADD_MEDIA_FEED_EDIT", 19003);
        hashMap.put("EXIST_FEED_EDIT", 19004);
        hashMap.put("CONTENT_FEED_EDIT", 19005);
        hashMap.put("ADD_MEDIA_FOR_PROFILE", 19006);
        hashMap.put("ADD_MEDIA_TO_FEED", 19007);
        hashMap.put("ADD_MEDIA_TO_STORE", 19008);
        hashMap.put("ADD_MEDIA_TO_STORY_FOR_SINGLE_IMAGE", 19009);


        //create feed
        hashMap.put("CREATE_FEED", 18001);
        hashMap.put("UPDATE_FEED", 18002);
        hashMap.put("CREATE_SROTE", 40001);


        //social logins
        hashMap.put("GOOGLE_SIGN_IN", 17001);
        hashMap.put("FACEBOOK_SIGN_IN", 17002);
        hashMap.put("TWITTER_SIGN_IN", 17003);

        //google
        hashMap.put("OPEN_PLACES_SEARCH", 4001);

        //online user data update
        hashMap.put("UPDATE_ONLINE_USERS", 3001);

        hashMap.put("UPDATE_FEED_DATA", 8070);

        //profile
        hashMap.put("EDIT_PROFILE_USER", 2001);


        //suspend
        hashMap.put("FEED_BACK_MANAGER_SUSPEND", 1501);

        //reject
        hashMap.put("FEED_BACK_MANAGER_REJECT", 1502);
        hashMap.put("CUSTOM_MEDIA_PICKER", 501);
        hashMap.put("EXISTING_MEDIA_PICKER", 502);
        hashMap.put("MAIN_EXISTING_MEDIA_PICKER", 503);
        hashMap.put("CREATE_AUDITION_FRAGMENT_RESULT", 504);

        //Otp
        hashMap.put("OTP_SUCCESS", 1503);


        //
        hashMap.put("MANAGER_PROFILE_EMPTY", 1201);

        hashMap.put("CHAT_RECENT_LIST", 1202);
        hashMap.put("ADD_MEDIA_TO_STORY", 1203);

        return hashMap;
    }

    public static ProgressDialog generateProgressDialog(Context activity) {
        try {

            ProgressDialog progressDialog = new ProgressDialog(activity);
            progressDialog.setCancelable(false);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.show();
            progressDialog.setContentView(R.layout.progressdialog);
            return progressDialog;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void closeProgressDialog(ProgressDialog progressDialog) {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static boolean checkPermissionRequest(Permission permission, Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            switch (permission) {
                case LOCATION:
                    return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ? false : true;
                case READ_SMS:
                    return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED ? false : true;
                case WRITE_SMS:
                    return ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED ? false : true;
                case READ_STORAGE:
                    return ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ? false : true;
                case WRITE_STORAGE:
                    return ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ? false : true;
                case CAMERA:
                    return ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ? false : true;
                case RECORD:
                    return ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ? false : true;
            }
            return false;
        } else
            return true;
    }

    public static void raisePermissionRequest(Permission permission, Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            switch (permission) {
                case LOCATION:
                    if (!checkPermissionRequest(Permission.LOCATION, activity)) {
                        ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, generateRequestCodes().get("LOCATION_REQUEST"));
                    }
                    break;

                case READ_STORAGE:
                    if (!checkPermissionRequest(Permission.READ_STORAGE, activity)) {
                        ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, generateRequestCodes().get("READ_STORAGE_REQUEST"));
                    }
                    break;

                case WRITE_STORAGE:
                    if (!checkPermissionRequest(Permission.WRITE_STORAGE, activity)) {
                        ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, generateRequestCodes().get("WRITE_STORAGE_REQUEST"));
                    }
                    break;

                case READ_SMS:
                    if (!checkPermissionRequest(Permission.READ_SMS, activity)) {
                        ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.READ_SMS}, generateRequestCodes().get("READ_SMS_REQUEST"));
                    }
                    break;
                case WRITE_SMS:
                    if (!checkPermissionRequest(Permission.WRITE_SMS, activity)) {
                        ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.SEND_SMS}, generateRequestCodes().get("WRITE_SMS_REQUEST"));
                    }
                    break;

                case CAMERA:
                    if (!checkPermissionRequest(Permission.CAMERA, activity)) {
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA},
                                generateRequestCodes().get("CAMERA_REQUEST"));
                    }
                    break;
                case RECORD:
                    if (!checkPermissionRequest(Permission.RECORD, activity)) {
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.RECORD_AUDIO}, generateRequestCodes().get("RECORD_REQUEST"));
                    }
                    break;
            }
        }

    }

    public static String getAppVersionName(Context context) {
        PackageInfo pInfo = null;
        String version = null;
        int version_code = 0;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pInfo.versionName;
            version_code = pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    public static String getAppVersionCode(Context context) {
        PackageInfo pInfo = null;
        String version = null;
        int version_code = 0;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pInfo.versionName;
            version_code = pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version_code + "";
    }


    public static String getPath(final Context context, final Uri uri) {

        // check here to KITKAT or new version
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {

                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/"
                                + split[1];
                    }
                }
                // DownloadsProvider
                else if (isDownloadsDocument(uri)) {

                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"),
                            Long.valueOf(id));

                    return getDataColumn(context, contentUri, null, null);
                }
                // MediaProvider
                else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{split[1]};

                    return getDataColumn(context, contentUri, selection,
                            selectionArgs);
                }
            }
            // MediaStore (and general)
            else if ("content".equalsIgnoreCase(uri.getScheme())) {

                // Return the remote address
                if (isGooglePhotosUri(uri))
                    return uri.getLastPathSegment();

                return getDataColumn(context, uri, null, null);
            }
            // File
            else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = MediaStore.Images.Media.DATA;
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri
                .getAuthority());
    }

    public static long fileSizeInKB(long size) {
        return size / 1024;
    }

    /*collegeview styles
     * same for rectangle and square two images
     *
     * */

    /*double CUT_OFF_ONE = 0.8,CUT_OFF_TWO = 1.2;
    public static CollageController getIPollCollageController(List<Media> mediaList) {
        if (mediaList.size() == 1) {
            return SingleAsPerRatioStyle;
        }
        else if (mediaList.size() == 0)
        {
            return NoImageStyle;
        }

        else if (mediaList.size() == 2)
        {
            for(int i=0;i<mediaList.size();i++){
                if(mediaList.get(i).ratio <= 0.7){
                    return TwoSquareStyle;
                }
            }
            return TwoRectangleStyle;

        }
        else if (mediaList.size() == 3)
        {

            // V H H
            if (mediaList.get(0).ratio >= CUT_OFF_TWO && mediaList.get(1).ratio <= CUT_OFF_ONE && mediaList.get(2).ratio <= CUT_OFF_ONE) {
            return  BottomTwoRectangleStyle;

        }
            //V V H
        
        else if (mediaList.get(0).ratio >= CUT_OFF_TWO && mediaList.get(1).ratio >= CUT_OFF_TWO && mediaList.get(2).ratio <= CUT_OFF_ONE) {
            return RightTwoVerticalRectangleStyle;

        }
            // V V V
        
        else if (mediaList.get(0).ratio >= CUT_OFF_TWO && mediaList.get(1).ratio >= CUT_OFF_TWO && mediaList.get(2).ratio >= CUT_OFF_TWO) {
            return RightTwoVerticalRectangleStyle;

        }


            // V V S
        
        else if (mediaList.get(0).ratio >= CUT_OFF_TWO && mediaList.get(1).ratio >= CUT_OFF_TWO && ( mediaList.get(2).ratio >= CUT_OFF_ONE &&  mediaList.get(2).ratio <=  CUT_OFF_TWO)) {
            return RightTwoVerticalRectangleStyle;

        }


            // V S V
        
        else if (mediaList.get(0).ratio >= CUT_OFF_TWO && ( mediaList.get(1).ratio >= CUT_OFF_ONE &&  mediaList.get(1).ratio <=  CUT_OFF_TWO)&& mediaList.get(2).ratio >= CUT_OFF_TWO) {
            return RightTwoVerticalRectangleStyle;

        }


            // V H V
        
        else if (mediaList.get(0).ratio >= CUT_OFF_TWO && mediaList.get(1).ratio <=  CUT_OFF_ONE && mediaList.get(2).ratio >= CUT_OFF_TWO) {
            return RightTwoVerticalRectangleStyle;

        }
            // S V S
        
        else if (( mediaList.get(0).ratio >= CUT_OFF_ONE &&  mediaList.get(0).ratio <=  CUT_OFF_TWO) &&  mediaList.get(1).ratio >= CUT_OFF_TWO && ( mediaList.get(2).ratio >= CUT_OFF_ONE &&  mediaList.get(2).ratio <=  CUT_OFF_TWO)) {
            return RightTwoVerticalRectangleStyle;

        }



            // H V V
        
        else  if (mediaList.get(0).ratio <= CUT_OFF_ONE && mediaList.get(1).ratio >= CUT_OFF_TWO && mediaList.get(2).ratio >= CUT_OFF_TWO) {
            return RightTwoVerticalRectangleStyle;

        }
            // H S V
        
        else if (mediaList.get(0).ratio <= CUT_OFF_ONE &&( mediaList.get(1).ratio >= CUT_OFF_ONE &&  mediaList.get(1).ratio <=  CUT_OFF_TWO)&& mediaList.get(2).ratio >= CUT_OFF_TWO) {
            return BottomTwoSquareStyle;

        }

            // V H S
        
        else if ( mediaList.get(0).ratio >= CUT_OFF_TWO && mediaList.get(1).ratio <= CUT_OFF_ONE &&( mediaList.get(2).ratio >= CUT_OFF_ONE &&  mediaList.get(2).ratio <=  CUT_OFF_TWO)) {
            return RightTwoSquareStyle;

        }


            // V S S
        
        else  if ( mediaList.get(0).ratio >= CUT_OFF_TWO &&( mediaList.get(1).ratio >= CUT_OFF_ONE &&  mediaList.get(1).ratio <=  CUT_OFF_TWO) &&( mediaList.get(2).ratio >= CUT_OFF_ONE &&  mediaList.get(2).ratio <=  CUT_OFF_TWO)) {
            return  RightTwoSquareStyle;

        }

            // S H V
        
        else if ( ( mediaList.get(0).ratio >= CUT_OFF_ONE &&  mediaList.get(0).ratio <=  CUT_OFF_TWO) &&  mediaList.get(1).ratio <= CUT_OFF_ONE && mediaList.get(2).ratio > CUT_OFF_TWO) {
            return BottomTwoRectangleStyle;

        }

            // S H H
        
        else if ( ( mediaList.get(0).ratio >= CUT_OFF_ONE &&  mediaList.get(0).ratio <=  CUT_OFF_TWO) &&  mediaList.get(1).ratio <= CUT_OFF_ONE && mediaList.get(2).ratio <= CUT_OFF_ONE) {
            return BottomTwoRectangleStyle;

        }

            // S H S
        
        else if (( mediaList.get(0).ratio >= CUT_OFF_ONE &&  mediaList.get(0).ratio <=  CUT_OFF_TWO) && mediaList.get(1).ratio <= CUT_OFF_ONE && ( mediaList.get(2).ratio >= CUT_OFF_ONE &&  mediaList.get(2).ratio <=  CUT_OFF_TWO)) {
            return BottomTwoSquareStyle;

        }

            // S S H
        else  if (( mediaList.get(0).ratio >= CUT_OFF_ONE &&  mediaList.get(0).ratio <=  CUT_OFF_TWO)  && ( mediaList.get(1).ratio >= CUT_OFF_ONE &&  mediaList.get(1).ratio <=  CUT_OFF_TWO) && mediaList.get(2).ratio <= CUT_OFF_ONE ) {
            return BottomTwoRectangleStyle;

        }

            // S S V
        else if (( mediaList.get(0).ratio >= CUT_OFF_ONE &&  mediaList.get(0).ratio <=  CUT_OFF_TWO)  && ( mediaList.get(1).ratio >= CUT_OFF_ONE &&  mediaList.get(1).ratio <=  CUT_OFF_TWO) && mediaList.get(2).ratio >= CUT_OFF_TWO ) {
            return RightTwoVerticalRectangleStyle;

        }

            // H S S
        else if (   mediaList.get(0).ratio <= CUT_OFF_ONE &&  ( mediaList.get(1).ratio >= CUT_OFF_ONE &&  mediaList.get(1).ratio <=  CUT_OFF_TWO)  && ( mediaList.get(2).ratio >= CUT_OFF_ONE &&  mediaList.get(2).ratio <=  CUT_OFF_TWO)  ) {
            return BottomTwoSquareStyle;

        }

            // H S H
        else if (   mediaList.get(0).ratio <= CUT_OFF_ONE &&  ( mediaList.get(1).ratio >= CUT_OFF_ONE &&  mediaList.get(1).ratio <=  CUT_OFF_TWO)  && mediaList.get(2).ratio <= CUT_OFF_ONE  ) {
            return BottomTwoRectangleStyle;

        }


            //H H H
        else if (mediaList.get(0).ratio <= CUT_OFF_ONE && mediaList.get(1).ratio <= CUT_OFF_ONE && mediaList.get(2).ratio <= CUT_OFF_ONE) {

            return BottomTwoRectangleStyle;

        }


            //H V H
        else if (mediaList.get(0).ratio <= CUT_OFF_ONE && mediaList.get(1).ratio >= CUT_OFF_TWO && mediaList.get(2).ratio <= CUT_OFF_ONE) {

            return BottomTwoRectangleStyle;

        }



            //H H V
        else if (mediaList.get(0).ratio <= CUT_OFF_ONE && mediaList.get(1).ratio <= CUT_OFF_ONE && mediaList.get(2).ratio >= CUT_OFF_TWO) {

            return BottomTwoRectangleStyle;
        }



            // H H S
        
        else  if (mediaList.get(0).ratio <= CUT_OFF_ONE && mediaList.get(1).ratio <= CUT_OFF_ONE &&  ( mediaList.get(2).ratio >= CUT_OFF_ONE &&  mediaList.get(2).ratio <=  CUT_OFF_TWO)) {

            return BottomTwoRectangleStyle;
        }


            // S S S
        
        else  if ((mediaList.get(0).ratio >= CUT_OFF_ONE  && mediaList.get(0).ratio <= CUT_OFF_TWO)     &&    (mediaList.get(1).ratio >= CUT_OFF_ONE && mediaList.get(1).ratio <= CUT_OFF_TWO)    &&     (mediaList.get(2).ratio >= CUT_OFF_ONE && mediaList.get(2).ratio <= CUT_OFF_TWO)) {
            return BottomTwoSquareStyle;

        }
        else{
            return BottomTwoSquareStyle;
        }


        }


        else if (mediaList.size() == 4)
        {


            CGFloat oneFraction = mediaList.get(0).ratio;


            // FIRST IMAGE - HORIZONTAL

            if (oneFraction <= CUT_OFF_ONE) {
                return BottomThreeSquareUpOneRectangleStyle;

            }

            // FIRST IMAGE - SQUARE

            else if ((oneFraction >= CUT_OFF_ONE && oneFraction <= CUT_OFF_TWO)) {
                return FourSquareRectangleStyle;

            }

            // FIRST IMAGE - VERTICAL
            else  if (oneFraction >= CUT_OFF_TWO) {
                return RightThreeSquareStyle;

            }
            else{
                return RightThreeSquareStyle;

            }



        }

        else if (mediaList.size() >= 5)
        {

            CGFloat threeFraction = mediaList.get(2).ratio;
            CGFloat fourFraction = mediaList.get(3).ratio;
            CGFloat fiveFraction = mediaList.get(4).ratio;


            if ((threeFraction <= CUT_OFF_ONE && (fourFraction <= CUT_OFF_ONE || fiveFraction <= CUT_OFF_ONE))
                    ||
                    (fourFraction <= CUT_OFF_ONE && fiveFraction <= CUT_OFF_ONE)
                    ||
                    (threeFraction <= CUT_OFF_ONE && fourFraction <= CUT_OFF_ONE && fiveFraction <= CUT_OFF_ONE)) {
                return LeftTwoSquareRightThreeRectangleStyle;
            }
            else{

                return UpTwoSquareStyle;

            }


        }

        return RightTwoVerticalRectangleStyle;
    }*/

    public static CollageController getIPollCollageController(List<Media> mediaList) {

        if (mediaList != null && mediaList.size() > 0) {
            if (mediaList.size() == 1) {
                return CollageController.SINGLE;
            } else if (mediaList.size() == 2) {
                return CollageController.TWO_EQUAL;
                //Srikanth - because face points are not working in horizontal/vertical
                //
                /*if (mediaList.get(0).ratio == 1 && mediaList.get(1).ratio == 1) {
                    return CollageController.TWO_EQUAL;
                } else if (mediaList.get(0).ratio < 1 && mediaList.get(1).ratio < 1) {
                    return CollageController.TWO_HORIZONTAL;
                } else if (mediaList.get(0).ratio > 1 && mediaList.get(1).ratio > 1) {
                    return CollageController.TWO_VERTICAL;
                } else {
                    return CollageController.TWO_EQUAL;
                }*/
            } else if (mediaList.size() == 3) {
                if (mediaList.get(0).ratio == 1 && mediaList.get(1).ratio == 1 && mediaList.get(2).ratio == 1
                        ||
                        insideLimit(mediaList.get(0).ratio) && insideLimit(mediaList.get(1).ratio)
                        ||
                        insideLimit(mediaList.get(0).ratio) && insideLimit(mediaList.get(2).ratio)
                        ||
                        insideLimit(mediaList.get(1).ratio) && insideLimit(mediaList.get(2).ratio)) {
                    return CollageController.THREE_EQUAL;
                } else if (mediaList.get(0).ratio < 1 && mediaList.get(1).ratio < 1 && mediaList.get(2).ratio < 1) {
                    return CollageController.THREE_HORIZONTAL;
                } else if (mediaList.get(0).ratio > 1 && mediaList.get(1).ratio > 1 && mediaList.get(2).ratio > 1) {
                    return CollageController.THREE_VERTICAL;
                } else {
                    if (mediaList.get(0).ratio < Constants.REVERSE_RATIO_HIGH_LIMIT) {
                        if (insideLimit(mediaList.get(1).ratio) && insideLimit(mediaList.get(2).ratio)
                                ||
                                mediaList.get(1).ratio > 1 && mediaList.get(2).ratio > 1) {
                            return CollageController.THREE_1H2S;
                        } else
                            return CollageController.THREE_HORIZONTAL;
                    } else if (mediaList.get(0).ratio > Constants.REVERSE_RATIO_LOW_LIMIT) {
                        if (insideLimit(mediaList.get(1).ratio) && insideLimit(mediaList.get(2).ratio)
                                ||
                                mediaList.get(1).ratio < 1 && mediaList.get(2).ratio < 1) {
                            return CollageController.THREE_1V2S;
                        } else
                            return CollageController.THREE_VERTICAL;
                    } else {
                        return CollageController.THREE_EQUAL;
                    }
                }
            } else if (mediaList.size() == 4) {
                if (mediaList.get(0).ratio == 1 && mediaList.get(1).ratio == 1 && mediaList.get(2).ratio == 1 && mediaList.get(3).ratio == 1
                        ||
                        insideLimit(mediaList.get(0).ratio) && insideLimit(mediaList.get(1).ratio) && insideLimit(mediaList.get(2).ratio)
                        ||
                        insideLimit(mediaList.get(1).ratio) && insideLimit(mediaList.get(2).ratio) && insideLimit(mediaList.get(3).ratio)
                        ||
                        insideLimit(mediaList.get(0).ratio) && insideLimit(mediaList.get(2).ratio) && insideLimit(mediaList.get(3).ratio)
                ) {
                    return CollageController.FOUR_EQUAL;
                } else if (mediaList.get(0).ratio < 1 && mediaList.get(1).ratio < 1 && mediaList.get(2).ratio < 1 && mediaList.get(3).ratio < 1) {
                    return CollageController.FOUR_HORIZONTAL;
                } else if (mediaList.get(0).ratio > 1 && mediaList.get(1).ratio > 1 && mediaList.get(2).ratio > 1 && mediaList.get(3).ratio > 1) {
                    return CollageController.FOUR_VERTICAL;
                } else {
                    if (mediaList.get(0).ratio < 1) {
                        return CollageController.FOUR_HORIZONTAL;
                    } else {
                        return CollageController.FOUR_VERTICAL;
                    }
                }
            } else {
                if (mediaList.get(0).ratio == 1 && mediaList.get(1).ratio == 1 && mediaList.get(2).ratio == 1 && mediaList.get(3).ratio == 1 && mediaList.get(4).ratio == 1
                        ||
                        insideLimit(mediaList.get(0).ratio) && insideLimit(mediaList.get(1).ratio) && insideLimit(mediaList.get(2).ratio)
                        ||
                        insideLimit(mediaList.get(1).ratio) && insideLimit(mediaList.get(2).ratio) && insideLimit(mediaList.get(3).ratio)
                        ||
                        insideLimit(mediaList.get(0).ratio) && insideLimit(mediaList.get(2).ratio) && insideLimit(mediaList.get(3).ratio)) {
                    if (mediaList.size() == 5)
                        return CollageController.FIVE_EQUAL;
                    else {
                        return CollageController.MORE_EQUAL;
                    }
                } else if (mediaList.get(2).ratio < Constants.REVERSE_RATIO_HIGH_LIMIT && mediaList.get(3).ratio < Constants.REVERSE_RATIO_HIGH_LIMIT && mediaList.get(4).ratio < Constants.REVERSE_RATIO_LOW_LIMIT
                        ||
                        mediaList.get(2).ratio < Constants.REVERSE_RATIO_HIGH_LIMIT && mediaList.get(3).ratio < Constants.REVERSE_RATIO_HIGH_LIMIT
                        ||
                        mediaList.get(2).ratio < Constants.REVERSE_RATIO_HIGH_LIMIT && mediaList.get(4).ratio < Constants.REVERSE_RATIO_HIGH_LIMIT
                        ||
                        mediaList.get(3).ratio < Constants.REVERSE_RATIO_HIGH_LIMIT && mediaList.get(4).ratio < Constants.REVERSE_RATIO_HIGH_LIMIT) {
                    if (mediaList.size() == 5)
                        return CollageController.FIVE_2V3H;
                    else {
                        return CollageController.MORE_2V3H;
                    }
                } else if (mediaList.get(0).ratio < Constants.REVERSE_RATIO_HIGH_LIMIT && mediaList.get(1).ratio < Constants.REVERSE_RATIO_HIGH_LIMIT && mediaList.get(2).ratio > Constants.REVERSE_RATIO_LOW_LIMIT
                        ||
                        mediaList.get(0).ratio < Constants.REVERSE_RATIO_HIGH_LIMIT && mediaList.get(1).ratio < Constants.REVERSE_RATIO_HIGH_LIMIT
                        ||
                        mediaList.get(0).ratio < Constants.REVERSE_RATIO_HIGH_LIMIT && mediaList.get(2).ratio < Constants.REVERSE_RATIO_HIGH_LIMIT
                        ||
                        mediaList.get(1).ratio < Constants.REVERSE_RATIO_HIGH_LIMIT && mediaList.get(2).ratio < Constants.REVERSE_RATIO_HIGH_LIMIT) {
                    if (mediaList.size() == 5)
                        return CollageController.FIVE_3H2V;
                    else {
                        return CollageController.MORE_3H2V;
                    }
                } else if (mediaList.get(2).ratio > Constants.REVERSE_RATIO_LOW_LIMIT && mediaList.get(3).ratio > Constants.REVERSE_RATIO_LOW_LIMIT && mediaList.get(4).ratio > Constants.REVERSE_RATIO_LOW_LIMIT
                        ||
                        mediaList.get(2).ratio > Constants.REVERSE_RATIO_LOW_LIMIT && mediaList.get(3).ratio > Constants.REVERSE_RATIO_LOW_LIMIT
                        ||
                        mediaList.get(2).ratio > Constants.REVERSE_RATIO_LOW_LIMIT && mediaList.get(4).ratio > Constants.REVERSE_RATIO_LOW_LIMIT
                        ||
                        mediaList.get(3).ratio > Constants.REVERSE_RATIO_LOW_LIMIT && mediaList.get(4).ratio > Constants.REVERSE_RATIO_LOW_LIMIT) {
                    if (mediaList.size() == 5)
                        return CollageController.FIVE_2HS3V;
                    else {
                        return CollageController.MORE_EQUAL;
                    }
                } else if (mediaList.get(0).ratio > Constants.REVERSE_RATIO_LOW_LIMIT && mediaList.get(1).ratio > Constants.REVERSE_RATIO_LOW_LIMIT && mediaList.get(2).ratio > Constants.REVERSE_RATIO_LOW_LIMIT
                        ||
                        mediaList.get(0).ratio > Constants.REVERSE_RATIO_LOW_LIMIT && mediaList.get(1).ratio > Constants.REVERSE_RATIO_LOW_LIMIT
                        ||
                        mediaList.get(0).ratio > Constants.REVERSE_RATIO_LOW_LIMIT && mediaList.get(2).ratio > Constants.REVERSE_RATIO_LOW_LIMIT
                        ||
                        mediaList.get(1).ratio > Constants.REVERSE_RATIO_LOW_LIMIT && mediaList.get(2).ratio > Constants.REVERSE_RATIO_LOW_LIMIT) {
                    if (mediaList.size() == 5)
                        return CollageController.FIVE_3V2HS;
                    else {
                        return CollageController.MORE_3V2HS;
                    }
                } else if (mediaList.get(0).ratio < 1 && mediaList.get(1).ratio < 1 && mediaList.get(2).ratio < 1 && mediaList.get(3).ratio < 1) {
                    return CollageController.MORE_HORIZONTAL;
                } else if (mediaList.get(0).ratio > 1 && mediaList.get(1).ratio > 1 && mediaList.get(2).ratio > 1 && mediaList.get(3).ratio > 1) {
                    return CollageController.MORE_VERTICAL;
                } else {
                    if (mediaList.get(0).ratio < 1) {
                        return CollageController.MORE_HORIZONTAL;
                    } else {
                        return CollageController.MORE_VERTICAL;

                    }
                }
            }
        }
        return null;
    }

    public static boolean insideLimit(double ratio) {
        ratio = (double) 1 / ratio;
        Logger.d("INSIDE RATIO", "" + ratio);
        return ratio < Constants.RATIO_HIGH_LIMIT ? ratio > Constants.RATIO_LOW_LIMIT ? true : false : false;
    }

    public static int getCompressionQuality(double size) {
        return 100;
        // size in kb
        /*return size < 100 ? 100 :
                size < 200 ? 95 :
                        size < 500 ? 75 :
                                size < 1000 ? 60 :
                                        size < 2000 ? 45 :
                                                size < 5000 ? 30 :
                                                        size < 10000 ? 10 : 5;*/
    }

    public static byte[] getBytesFromFile(Uri uri, String type, double size) {
        File file = new File(getPath(getContext(), uri));
        if (file.exists()) {
            if (type.equals(Constants.MEDIA_TYPE_IMAGE)) {
                try {
                    file = new Compressor(getContext()).setQuality(getCompressionQuality(size)).compressToFile(file);
                    Logger.d("comp image size", "" + file.length());
                    Logger.d("comp image quality", "" + getCompressionQuality(size));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            byte[] bytes = new byte[(int) file.length()];
            try {
                BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
                buf.read(bytes, 0, bytes.length);
                buf.close();
                return bytes;
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

    public static byte[] getBytesFromFile(Uri uri) {

        File file = new File(getPath(getContext(), uri));

        if (file.exists()) {

            byte[] bytes = new byte[(int) file.length()];
            try {
                BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
                buf.read(bytes, 0, bytes.length);
                buf.close();

                return bytes;
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }


    public static byte[] getBytesThumbnail(Uri uri) {
        try {

            String[] filePathColumn = {MediaStore.Images.Media.DATA, MediaStore.MediaColumns._ID};
            Cursor cursor = getContext().getContentResolver().query(uri, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            int mediaID = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            Bitmap bitmap = null;
            if (picturePath.contains(".gif")) {
                //bitmap = ThumbnailUtils.createVideoThumbnail(picturePath, MediaStore.Images.Thumbnails.FULL_SCREEN_KIND);
                bitmap = MediaStore.Images.Thumbnails.getThumbnail(
                        Utility.getContext().getContentResolver(), mediaID,
                        MediaStore.Images.Thumbnails.MINI_KIND,
                        (BitmapFactory.Options) null);
            } else {
                bitmap = ThumbnailUtils.createVideoThumbnail(picturePath, MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
            }
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            return byteArray;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isSuccessful(JSONObject jsonObject) {
        try {
            Logger.d("[Response]", jsonObject.toString());
            if (jsonObject.has("success")) {
                return jsonObject.getInt("success") == 1 ? true : false;
            } else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void showSnackBar(Context activity, CoordinatorLayout coordinatorLayout, String text, int type) {
        if (coordinatorLayout == null || text == null || activity == null) {
            return;
        }
        Snackbar snackBar = Snackbar.make(coordinatorLayout, text, Snackbar.LENGTH_SHORT);
        TextView txtMessage = (TextView) snackBar.getView().findViewById(R.id.snackbar_text);
        txtMessage.setTextColor(ContextCompat.getColor(activity, R.color.white));
        if (type == 2)
            snackBar.getView().setBackgroundColor(ContextCompat.getColor(activity, R.color.black));
        else if (type == 1)
            snackBar.getView().setBackgroundColor(ContextCompat.getColor(activity, R.color.app_snack_bar_true));
        else if (type == 3)
            snackBar.getView().setBackgroundColor(ContextCompat.getColor(activity, R.color.app_snack_bar_true));
        else {
            snackBar.getView().setBackgroundColor(ContextCompat.getColor(activity, R.color.mainColorPrimary));
        }
        txtMessage.setTypeface(getTypeface(1, activity));

        snackBar.show();
    }

    public static String getCamelCase(String name) {
        if (name == null)
            return null;

        int flag = 1439;
        name = name.trim();
        name = name.toLowerCase();

        while (flag == 1439) {
            if (name.contains("  ")) {
                name = name.replaceAll("  ", " ");
            } else {
                flag = 3914;
            }


        }

        StringBuilder sb = new StringBuilder();
        try {
            String[] words = name.toString().split(" ");
            if (words[0].length() > 0) {
                sb.append(Character.toUpperCase(words[0].charAt(0)) + words[0].subSequence(1, words[0].length()).toString());
                for (int i = 1; i < words.length; i++) {
                    sb.append(" ");
                    sb.append(Character.toUpperCase(words[i].charAt(0)) + words[i].subSequence(1, words[i].length()).toString());
                }
            }
        } catch (Exception e) {
            //System.out.println(e.toString());
            e.printStackTrace();
            return name;
        }
        return sb.toString();

    }

    public static void hideKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public static Uri addMediaThumbnailUrl(info.sumantv.flow.appmanagers.feed.models.Media media) {
        if (media.url != null && media.url.length() > 0) {
            return Uri.parse(Constants.MEDIA_BASE_URL + media.url);
        } else if (media.uri != null) {
            return media.uri;
        } else if (media.source != null) {
            if (media.source.urlthumbnail != null && media.source.urlthumbnail.length() > 0 && !SessionManager.getInstance().getKeyValue(SessionManager.KEY_SHOW_HD_IMAGES, false)) {
                return Uri.parse(Constants.MEDIA_BASE_URL + media.source.urlthumbnail);
            } else if (media.source.url != null && media.source.url.length() > 0) {
                return Uri.parse(Constants.MEDIA_BASE_URL + media.source.url);
            } else {
                return Uri.parse("");
            }
        } else {
            return Uri.parse("");
        }
    }

    public static Uri addVideoUrl(info.sumantv.flow.appmanagers.feed.models.Media media) {
        if (media.uri != null) {
            return media.uri;
        } else if (media.source != null) {
            if (media.source.videoUrl != null && media.source.videoUrl.length() > 0) {
                return Uri.parse(Constants.MEDIA_BASE_URL + media.source.videoUrl);
            } else {
                return Uri.parse("");
            }
        } else {
            return Uri.parse("");
        }
    }

    public static Uri addVideoUrl(String mediaURL) {
        if (mediaURL != null) {
            if (mediaURL != null && !mediaURL.isEmpty()) {
                return Uri.parse(Constants.MEDIA_BASE_URL + mediaURL);
            } else {
                return Uri.parse("");
            }
        } else {
            return Uri.parse("");
        }
    }

    public static List<IPollOption> generateCameraOptions() {
        List<IPollOption> list = new ArrayList<>();
        IPollOption i1 = new IPollOption();
        i1.name = "Photo";
        i1.image = R.drawable.ic_image;

        IPollOption i2 = new IPollOption();
        i2.name = "Video";
        i2.image = R.drawable.ic_videocam;

        list.add(i1);
        list.add(i2);

        return list;
    }

    public static List<IPollOption> generateFeedTypeOptions() {
        List<IPollOption> list = new ArrayList<>();
        IPollOption i1 = new IPollOption();
        i1.name = "Photo";
        i1.image = R.drawable.ic_image;

//        IPollOption i2 = new IPollOption();
//        i2.name = "Poll";
//        i2.image = R.drawable.ic_poll;

        list.add(i1);
//        list.add(i2);

        return list;
    }

    public static double getImageRatio(Context context, Uri uri) {
        try {
            String[] filePathColumn = {MediaStore.Images.Media.DATA, MediaStore.Images.Media.ORIENTATION};
            Cursor cursor = context.getContentResolver().query(
                    uri, filePathColumn, null, null, null);
            cursor.moveToFirst();


            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            int orientation = cursor.getInt(1);
            String picturePath = cursor.getString(columnIndex);
            if (cursor != null && !cursor.isClosed())
                cursor.close();

            File file = new File(picturePath);
            Bitmap bmp = null;
            if (file.exists()) {
                bmp = BitmapFactory.decodeFile(file.getAbsolutePath());
            }
            Logger.d("IMAGE ORIENTATION", "" + orientation);
            Logger.d("IMAGE WIDTH", "" + bmp.getWidth());
            Logger.d("IMAGE HEIGHT", "" + bmp.getHeight());
            if (orientation == 90 || orientation == 270) {
                return (double) bmp.getWidth() / (double) bmp.getHeight();
            } else {
                return (double) bmp.getHeight() / (double) bmp.getWidth();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    public static double getVideoRatio(Context context, Uri uri) {
        try {
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            Bitmap bmp = ThumbnailUtils.createVideoThumbnail(picturePath, MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);

            return (double) bmp.getHeight() / (double) bmp.getWidth();
        } catch (Exception e) {
            return 1;
        }
    }

    public static ResizeOptions getResizeOptions(double ratio, Context context) {
        try {
            return new ResizeOptions(getScreenWidth(context), (int) (getScreenWidth(context) * ratio));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResizeOptions(getScreenWidth(context), getScreenWidth(context));
    }

    public static void openCustomAlertDialog(Activity activity, String title, String details, boolean isCancel, ICustomAlertDialog iCustomAlertDialog) {
        CustomAlertDialog dialog = new CustomAlertDialog(activity, iCustomAlertDialog, title, details);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(isCancel);
        dialog.show();
        DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
        int width = (int) (metrics.widthPixels * 0.85);
        dialog.getWindow().setLayout(width, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public static File createImageFile(Activity activity) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IPOLL_" + timeStamp;
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile
                (
                        imageFileName,  /* prefix */
                        ".jpg",         /* suffix */
                        storageDir      /* directory */
                );
        Logger.d("Storage Dir", storageDir.getAbsolutePath());

        // Save a file: path for use with ACTION_VIEW intents
        return image;
    }

    public static void showToast(Context c, String s, boolean duration) {
        if (c == null) return;
        Toast tst = Toast.makeText(c, s, duration ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
        tst.show();
    }

    public static void showToast(Context c, String t, String s, boolean duration) {
        if (c == null) return;
        Toast tst = Toast.makeText(c, t, duration ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
        tst.setText(s);
        tst.show();
    }

    public static void showToast(Context c, String t, String s, boolean duration, String response) {
        if (c == null) return;
        Toast tst = Toast.makeText(c, t, duration ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.has("error")) {
                tst.setText(jsonObject.getString("error").toString());
            } else if (jsonObject.has("message")) {
                tst.setText(jsonObject.getString("message").toString());
            } else {
                tst.setText(s);
            }
            tst.show();
        } catch (Exception e) {
            Logger.d("[Exception]", e.toString());
        }
    }

    public static void notifyMessage(String title, String message) {
        if (getContext() == null) {
            return;
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext(), "CELEBKONECT_NOTFIY_CHANNEL")
                .setSmallIcon(R.drawable.ic_only_logo_white)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        if (title != null) {
            mBuilder.setContentTitle(title);
        } else {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                mBuilder.setContentTitle("CelebKonect");
            }
        }
        if (message != null) {
            mBuilder.setContentText(message);
        }

        mBuilder.setColor(ContextCompat.getColor(getContext(), R.color.skyblueNew));
        mBuilder.setChannelId("CELEBKONECT_NOTFIY_CHANNEL");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel notificationChannel = new NotificationChannel("CELEBKONECT_NOTFIY_CHANNEL", Constants.APP_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
            notificationManager.notify(new Random().nextInt(99999), mBuilder.build());
        } else {
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
            notificationManager.notify(new Random().nextInt(99999), mBuilder.build());
        }

    }

//    public static void notifyMessage(String title, String message) {
//
//        if (getContext() == null) {
//            return;
//        }
//
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext(), "IPOLL_1439")
//                .setSmallIcon(R.drawable.appiconandroid)
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .setAutoCancel(true);
//
//        if (title != null) {
//            mBuilder.setContentTitle(title);
//        }
//        else
//        {
//            mBuilder.setContentTitle("CelebKonect");
//        }
//        if (message != null) {
//            mBuilder.setContentText(message);
//        }
//        mBuilder.setColor(ContextCompat.getColor(getContext(),R.color.skyblueNew));
//
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
//        notificationManager.notify(new Random().nextInt(99999), mBuilder.build());
//    }

    public static Task<List<FirebaseVisionFace>> getFacePoints(Uri uri) {
        if (uri == null) return null;
        Logger.d("Adding face points");
        FirebaseVisionImage image = null;
        try {
            image = FirebaseVisionImage.fromFilePath(getContext(), uri);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        if (image == null)
            return null;
        AppController.getInstance().initializeFaceDetector();
        return AppController.getInstance().firebaseVisionFaceDetector.detectInImage(image);
    }

    public static FacePoint generateFacePoint(FirebaseVisionFace face) {
        FacePoint facePoint = new FacePoint();
        Rect box = face.getBoundingBox();
        facePoint.x = box.left;
        facePoint.y = box.top;
        facePoint.width = box.width();
        facePoint.height = box.height();
        facePoint.faceCenterX = face.getBoundingBox().centerX();
        facePoint.faceCenterY = face.getBoundingBox().centerY();
        return facePoint;
    }

    public static List<IPollOption> generateFeedUserOptions() {
        List<IPollOption> list = new ArrayList<>();
        IPollOption i1 = new IPollOption();
        i1.name = "Edit";
        i1.image = R.drawable.ic_edit;

        IPollOption i2 = new IPollOption();
        i2.name = "Delete";
        i2.image = R.drawable.ic_delete;

//        IPollOption i3 = new IPollOption();
//        i3.name = "Hide";
//        i3.image = R.drawable.ic_visibility_off;

        list.add(i1);
        list.add(i2);
//        list.add(i3);

        return list;
    }

    public static void autoForceShutDown() {
        int[] values = {1, 2};

        for (int i = 0; i < 5; i++) {
            Logger.d("VALUES", "" + i);
        }
    }

    public static String replaceSpecialCharacter(String name, String mimeType) {
        try {
            if (name == null) {
                return mimeType != null ? mimeType.replace("/", ".") : "file";
            } else {
                return name.replaceAll("[^.a-zA-Z0-9]", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return name;
        }
    }

    public static void justify(final TextView textView) {

        final AtomicBoolean isJustify = new AtomicBoolean(false);

        final String textString = textView.getText().toString();

        final TextPaint textPaint = textView.getPaint();

        final SpannableStringBuilder builder = new SpannableStringBuilder();

        textView.post(new Runnable() {
            @Override
            public void run() {

                if (!isJustify.get()) {

                    final int lineCount = textView.getLineCount();
                    final int textViewWidth = textView.getWidth();

                    for (int i = 0; i < lineCount; i++) {

                        int lineStart = textView.getLayout().getLineStart(i);
                        int lineEnd = textView.getLayout().getLineEnd(i);

                        String lineString = textString.substring(lineStart, lineEnd);

                        if (i == lineCount - 1) {
                            builder.append(new SpannableString(lineString));
                            break;
                        }

                        String trimSpaceText = lineString.trim();
                        String removeSpaceText = lineString.replaceAll(" ", "");

                        float removeSpaceWidth = textPaint.measureText(removeSpaceText);
                        float spaceCount = trimSpaceText.length() - removeSpaceText.length();

                        float eachSpaceWidth = (textViewWidth - removeSpaceWidth) / spaceCount;

                        SpannableString spannableString = new SpannableString(lineString);
                        for (int j = 0; j < trimSpaceText.length(); j++) {
                            char c = trimSpaceText.charAt(j);
                            if (c == ' ') {
                                Drawable drawable = new ColorDrawable(0x00ffffff);
                                drawable.setBounds(0, 0, (int) eachSpaceWidth, 0);
                                ImageSpan span = new ImageSpan(drawable);
                                spannableString.setSpan(span, j, j + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            }
                        }

                        builder.append(spannableString);
                    }

                    textView.setText(builder);
                    isJustify.set(true);
                }
            }
        });
    }


    public static String activeAgotime(String s) {
        try {
            SimpleDateFormat simDf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            simDf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date yourDate = simDf.parse(s);
            Timestamp timestamp = new Timestamp(yourDate.getTime());
            return getActiveTimeShort(timestamp.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return "Long ago";
        }
    }

    private static String getActiveTimeShort(long time) {
        if (time < 1000000000000L) {
            time *= 1000;
        }
        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return "Online";
        }
        //
        final long diff = now - time;
        long elapsedDays = diff / Constants.DAY_MILLIS;
        if (diff <= Constants.MINUTE_MILLIS) {
            return diff / Constants.SECOND_MILLIS + " seconds";
        } else if (diff < 2 * Constants.MINUTE_MILLIS) {
            return "1 min";
        } else if (diff < 50 * Constants.MINUTE_MILLIS) {
            return diff / Constants.MINUTE_MILLIS + " mins";
        } else if (diff < 2 * Constants.HOUR_MILLIS) {
            return "1 hr";
        } else if (diff < 24 * Constants.HOUR_MILLIS) {
            return diff / Constants.HOUR_MILLIS + " hrs";
        } else if (diff < 48 * Constants.HOUR_MILLIS) {
            return diff / Constants.DAY_MILLIS + " day";
        } else if (elapsedDays < 7) {
            return elapsedDays + " days ";
        } else {
            return diff / Constants.DAY_MILLIS + " days ";
        }
    }

    public static String timeLastUpdate(String s) {
        try {
            SimpleDateFormat simDf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            simDf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date yourDate = simDf.parse(s);
            Timestamp timestamp = new Timestamp(yourDate.getTime());
            return getLastUpdateTime(timestamp.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return "Long ago";
        }
    }

    private static String getLastUpdateTime(long time) {
        if (time < 1000000000000L) {
            time *= 1000;
        }
        long now = System.currentTimeMillis();

        final long diff = now - time;
        long elapsedDays = diff / Constants.DAY_MILLIS;
        if (diff <= Constants.MINUTE_MILLIS) {
            return "Just now";
        } else if (diff < 2 * Constants.MINUTE_MILLIS) {
            return "1 min";
        } else if (diff < 50 * Constants.MINUTE_MILLIS) {
            return diff / Constants.MINUTE_MILLIS + " mins";
        } else if (diff < 2 * Constants.HOUR_MILLIS) {
            return "1 hr";
        } else if (diff < 24 * Constants.HOUR_MILLIS) {
            return diff / Constants.HOUR_MILLIS + " hrs";
        } else if (diff < 48 * Constants.HOUR_MILLIS) {
            return diff / Constants.DAY_MILLIS + " day";
        } else if (elapsedDays < 7) {
            return elapsedDays + " days ";
        } else {
            return diff / Constants.DAY_MILLIS + " days ";
        }
    }


}
