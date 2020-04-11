package info.sumantv.flow.userflow.Util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.KeyguardManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothHeadset;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.ExifInterface;
import android.media.MediaMetadataRetriever;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.os.SystemClock;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.util.UriUtil;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.IndicatorStayLayout;

import org.apache.commons.lang3.StringEscapeUtils;
import org.florescu.android.rangeseekbar.RangeSeekBar;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import info.sumantv.flow.appmanagers.feed.models.Celebrity;
import info.sumantv.flow.appmanagers.feed.models.FacePoint;
import info.sumantv.flow.appmanagers.feed.models.Feed;
import info.sumantv.flow.appmanagers.feed.models.KonectData;
import info.sumantv.flow.appmanagers.feed.models.Media;
import info.sumantv.flow.appmanagers.feed.models.Profile;
import info.sumantv.flow.ModelClass.ContractsSuccess;
import info.sumantv.flow.ModelClass.FanSuccessData;
import info.sumantv.flow.ModelClass.FanUnFanData;
import info.sumantv.flow.ModelClass.FanUnFanPostParams;
import info.sumantv.flow.ModelClass.ProfileDataModel;
import info.sumantv.flow.bottommenu.activity.BottomMenuActivity;
import info.sumantv.flow.bottommenu.activity.HelperActivity;
import info.sumantv.flow.bottommenu.allcelebritiestab.CelebProfileCompleteData;
import info.sumantv.flow.bottommenu.celebritiestab.CelebritiesTabFragment;
import info.sumantv.flow.bottommenu.celebrityprofile.CelebrityProfileFragment;
import info.sumantv.flow.bottommenu.interfaces.fragments.IKonectDailogClick;
import info.sumantv.flow.bottommenu.menuitemsmanager.fragments.MyContentFragment;
import info.sumantv.flow.bottommenu.menuitemsmanager.fragments.my_schedules.ViewScheduleFragment;
import info.sumantv.flow.databaseutil.appstart.AppController;
import info.sumantv.flow.celebflow.Fragments.PhotosFragment;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.SignInActivity;
import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.celebflow.countrydata.CountryData;
import info.sumantv.flow.celebflow.services.SwitchAccountStatus;
import info.sumantv.flow.chat.ActivitySingleChat;
import info.sumantv.flow.chat.Fragment.FragmentCallsList;
import info.sumantv.flow.chat.Fragment.FragmentChatList;
import info.sumantv.flow.chat.Fragment.FragmentNewChatList;
import info.sumantv.flow.chat.Fragment.FragmentTabsChat;
import info.sumantv.flow.chat.NotificationUtil;
import info.sumantv.flow.chat.models.ChatDataConvertModel;
import info.sumantv.flow.chat.models.ChatDataModel;
import info.sumantv.flow.chat.models.ChatSenderReceiverInfo;
import info.sumantv.flow.chat.models.RecentCallsModel;
import info.sumantv.flow.chat.models.SingleChatHistoryModel;
import info.sumantv.flow.chat.socket.ChatSocket;
import info.sumantv.flow.ipoll.dialogs.custom.KonectCelebDailog;
import info.sumantv.flow.ipoll.fragments.feeds.FeedsFragment;
import info.sumantv.flow.ipoll.fragments.feeds.comments.AddCommentParams;
import info.sumantv.flow.ipoll.fragments.videoplayer.VideoPlayerFragment;
import info.sumantv.flow.ipoll.interfaces.feeds.IFeedAdapter;
import info.sumantv.flow.ipoll.viewholders.FeedViewHolder;
import info.sumantv.flow.menu_list.MyCelebrities.MyCelebritiesActivity.FansOfFragment.FansOfFragment;
import info.sumantv.flow.retrofitcall.ApiClient;
import info.sumantv.flow.retrofitcall.ApiInterface;
import info.sumantv.flow.retrofitcall.ApiRequestModel;
import info.sumantv.flow.retrofitcall.ApiRequestModelJSON;
import info.sumantv.flow.retrofitcall.ApiResponseModel;
import info.sumantv.flow.retrofitcall.EnumConstants;
import info.sumantv.flow.retrofitcall.IApiListener;
import info.sumantv.flow.retrofitcall.ProgressRequestBody;
import info.sumantv.flow.retrofitcall.SessionManager;
import info.sumantv.flow.splashandintroscreens.SplashScreenActivity;
import info.sumantv.flow.stories.SeenListDailog;
import info.sumantv.flow.stories.fragments.StoriesFragment;
import info.sumantv.flow.utils.Logger;
import info.sumantv.flow.utils.RecyclerUtil.CommonRecycler;
import info.sumantv.flow.utils.SocketForAppUtill;
import info.sumantv.flow.utils.Utility;
import info.sumantv.flow.utils.facecrop.FaceCenterCrop;
import info.sumantv.flow.utils.waveProgress.SpinKitView;
import info.sumantv.flow.utils.waveProgress.Sprite;
import info.sumantv.flow.utils.waveProgress.SpriteFactory;
import info.sumantv.flow.utils.waveProgress.Style;
import info.sumantv.flow.imagepicker.model.Image;
import info.sumantv.flow.imagepicker.ui.imagepicker.ImagePicker;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Logins on 10-11-2017.
 */

public class Common implements IApiListener {

    IApiListener iApiListener = this;
    private ArrayList<Image> images = new ArrayList<>();
    static ProgressDialog localProgressDialog;
    static ApiInterface apiInterfaceLocal;
    static boolean pushNotificationSuccess = false;
    public static boolean isManagerScreens = true;
    public static Boolean isAuditionMediaIsRunning = false, isFeedSessionStarted = false;
    public static String credits, mReferal_Credits, schdule_refereal_Credits, celeb_referral_credits, celeb_referal_Id, accountBal;
    public static String sharedFeedID = "", sharedProfileID = "", sharedAuditionID = "", sharedRoleID = "";
    static ApiInterface apiInterface;
    public static Bundle bundleFromNotification = null;
    public static ChatDataConvertModel chatDataConvertModelFromNotification = null;
    public static String testChannelID = "Service Notifications", TAG = "Common";
    String celebrityName = "";
    boolean isBlock = false;
    KonectCelebDailog konectCelebDailog;

    static ArrayList<String> _countryCode;
    private static final int MY_PERMISSIONS_REQUEST_ACCOUNTS = 1;
    private static Common common;


    static String memberreason = null;

    private static Double totalCredits = 0.0;
    Toast cusToast = null;
    private static Location currentLocation = null;
    private LocationCallback mLocationCallback;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocationClient;
    ProgressDialog progressDialog;
    //
    static String liveLogStatus = "liveLogStatus";
    public static RecyclerView.ViewHolder clickedHolder;
    public static Boolean isLiveStatusEmitRunning = false;
    private Dialog dialogNetworkError = null, dialogCelebProfile = null;

    private Toast mToastToShow;

    String currentServerTime = "";

    public static Common getInstance() {
        if (common == null) {
            common = new Common();
        }
        return common;
    }


    private Socket mSocket;

    public Socket getSocket() {
        if (!Common.getInstance().isSocketConnected()) {
            Common.getInstance().connectSocket();
        }
        return mSocket;
    }

    public void setSocket(Socket mSocket) {
        this.mSocket = mSocket;
    }

    public void connectSocket() {
        try {
            if (mSocket != null && mSocket.connected()) {
                return;
            }
            mSocket = IO.socket(ApiClient.SERVICE_CHAT_SOCKET_PORT_URL);
            mSocket.connect();
            //
            mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    try {
                        if (AppController.getInstance().isIsAppInRecentTasks()) {
                            Log.v("SocketConnected", "true");
                        } else {
                            Log.v("AppNotInRecentstate", "true");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            mSocket.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    try {
                        Log.v("SocketError", "true");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            mSocket.on(Socket.EVENT_ERROR, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    try {
                        Log.v("SocketEventError", "true");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            mSocket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.v("SocketDisconnect", "true");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void disconnectSocket() {
        try {
            if (mSocket != null) {
                mSocket.disconnect();
                Log.e("SocketConnected", "TRUE");
            }
        } catch (Exception e) {
        }
    }


    public Boolean isSocketConnected() {
        try {
            if (mSocket != null) {
                return mSocket.connected();
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

    }

    public static void switchOwnProfile(Context context, boolean isBackGround) {

    }

    public void enableDisableView(View view, boolean enabled, Activity activity) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.setEnabled(enabled);
                try {
                    if (view instanceof ViewGroup) {
                        ViewGroup group = (ViewGroup) view;
                        for (int idx = 0; idx < group.getChildCount(); idx++) {
                            enableDisableView(group.getChildAt(idx), enabled, activity);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void applyTouchClickListener(final View view, final Activity activity, final CoordinatorLayout coordinator_layout, final String message, final Boolean applyForAllViews) {
        activity.runOnUiThread(() -> {
            try {
                if (view instanceof EditText || view instanceof Spinner || view instanceof ImageView || view instanceof Button || view instanceof CheckBox || view instanceof RangeSeekBar || view instanceof IndicatorStayLayout || view instanceof IndicatorSeekBar || applyForAllViews) {
                    Log.d("viewType", view.toString());
                    view.setOnTouchListener(new View.OnTouchListener() {
                        int CLICK_ACTION_THRESHOLD = 200;
                        float startX = 0, startY = 0;

                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            switch (event.getAction()) {
                                case MotionEvent.ACTION_DOWN:
                                    startX = event.getX();
                                    startY = event.getY();
                                    break;
                                case MotionEvent.ACTION_UP:
                                    float endX = event.getX();
                                    float endY = event.getY();
                                    if (isAClick(startX, endX, startY, endY)) {
                                        Utility.showSnackBar(activity, coordinator_layout, message, 2);
                                        return true;
                                    }
                                    break;
                            }
                            return true;
                        }

                        private boolean isAClick(float startX, float endX, float startY, float endY) {
                            float differenceX = Math.abs(startX - endX);
                            float differenceY = Math.abs(startY - endY);
                            return !(differenceX > CLICK_ACTION_THRESHOLD || differenceY > CLICK_ACTION_THRESHOLD);
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (view instanceof ViewGroup) {
                    ViewGroup group = (ViewGroup) view;
                    for (int idx = 0; idx < group.getChildCount(); idx++) {
                        applyTouchClickListener(group.getChildAt(idx), activity, coordinator_layout, message, applyForAllViews);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void showSweetAlertSuccess(Context context, String title, String message) {
        try {
            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText(title)
                    .setContentText(message);
            sweetAlertDialog.setOnShowListener(dialogInterface -> {
                try {
                    SweetAlertDialog alertDialog = (SweetAlertDialog) dialogInterface;
                    TextView text = (TextView) alertDialog.findViewById(R.id.title_text);
                    text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    text.setSingleLine(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            sweetAlertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showSweetAlertWarning(Context context, String title, String message) {
        try {
            if (context != null) {
                if (title != null) {
                    new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText(title)
                            .setContentText(message)
                            .show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showSweetAlertWarningFan(Context context, String title, String message, ProfileDataModel profileDataModel) {
        try {
            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText(title)
                    .setContentText(message);
            sweetAlertDialog.setOnShowListener(dialogInterface -> {
                try {
                    SweetAlertDialog alertDialog = (SweetAlertDialog) dialogInterface;
                    TextView text = (TextView) alertDialog.findViewById(R.id.title_text);
                    text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    text.setSingleLine(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            if (profileDataModel.isCeleb) {
                sweetAlertDialog.setConfirmClickListener(sweetAlertDialog1 -> {
                    sweetAlertDialog1.dismiss();
                    getContracts(context, "fan", profileDataModel);

                });

            }
            sweetAlertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //SRIKANTH Code START
    public void notificationRedirection(Context context, Bundle bundle) {
        try {
            String serviceType = "", memberId = "", celebId = "", activityType = "";
            ChatDataConvertModel chatDataConvertModel = null;
            JSONObject notificationObject = new JSONObject();
            if (bundle != null && bundle.size() > 0) {
                chatDataConvertModel = bundle.getParcelable("chatDataConvertModel");
                serviceType = bundle.getString(Constants.NOTIFICATION_SERVICE_TYPE, "");
                notificationObject = new JSONObject(Common.getInstance().IsNullReturnValue(bundle.getString(Constants.NOTIFICATION_OBJECT), "{}"));
                memberId = notificationObject.optString("memberId", "");
                celebId = notificationObject.optString("celebId", "");
                activityType = notificationObject.optString("activity", "");
            }
            if (serviceType != null && !serviceType.isEmpty()) {
                if (serviceType.equalsIgnoreCase(Constants.NOTIFICATION_ACTIVITY_FEED)) {
                    String feedId = notificationObject.optString("feedId", "");
                    Common.getInstance().getSingleFeedData(feedId);
                } else if (serviceType.equalsIgnoreCase(Constants.NOTIFICATION_ACTIVITY_FAN)) {
                    openProfileScreen(context, memberId);
                } else if (serviceType.equalsIgnoreCase(Constants.NOTIFICATION_ACTIVITY_SCHEDULE) || serviceType.equalsIgnoreCase(Constants.NOTIFICATION_ACTIVITY_CALL_REMINDER)) {
                    openCelebSchedulesScreen(context, celebId
                            , activityType);
                } else if (serviceType.equalsIgnoreCase(Constants.NOTIFICATION_ACTIVITY_REQUEST_FROM_ADMIN) || serviceType.equalsIgnoreCase(Constants.NOTIFICATION_ACTIVITY_MANAGER_ACCEPTED)) {
                    openCelebSettingsScreen(context);
                } else if (serviceType.equalsIgnoreCase(Constants.NOTIFICATION_ACTIVITY_REQUEST_TO_MANAGER)) {
                    openManagerSettingsScreen(context);
                } else if (serviceType.equalsIgnoreCase(Constants.NOTIFICATION_ACTIVITY_PURCHASED_CREDITS)) {
                    Common.getInstance().openTransactionScreen(context, "Orders");
                } else if (serviceType.equalsIgnoreCase(Constants.NOTIFICATION_TYPE_CHAT)) {
                    if (chatDataConvertModel == null) {
                        return;
                    } else {
                        chatDataConvertModel.notificationCondition = bundle.getString("condition", "");
                    }
                    String notificationCondition = Common.getInstance().IsNullReturnValue(chatDataConvertModel.notificationCondition, "");
                    if (notificationCondition.equalsIgnoreCase(NotificationUtil.GroupNotification) && Common.getInstance().getFragmentTabsChat() == null) {
                        Common.getInstance().openChatTabsActivity(context, chatDataConvertModel);
                    } else if (Common.getInstance().getFragmentNewChatList() != null || Common.getInstance().getFragmentTabsChat() != null || Common.getInstance().getActivitySingleChat() != null) {
                        Common.getInstance().openSingleChatActivity(null, context, chatDataConvertModel);
                    } else if (Common.getInstance().getFragmentTabsChat() == null) {
                        Common.getInstance().openChatTabsActivity(context, chatDataConvertModel);
                    } else {
                        Common.getInstance().openSingleChatActivity(null, context, chatDataConvertModel);
                    }
                } else {
                    openNotificationIntent(context, bundle, true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void bringBottomMenuActivityToFront(Context context) {
        Intent intent = new Intent(context, BottomMenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    public void hideKeyboard(Context context, View v) {
        try {
            if (v != null) {
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String requestBodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

    public void callAPI(final ApiRequestModel apiRequestModel) {
        try {
            if (!checkInternetConnection(apiRequestModel.context)) {
                apiRequestModel.iApiListener.apiErrorResponse(apiRequestModel.condition, EnumConstants.NO_NETWORK, null);
                showErrorDialog(apiRequestModel, EnumConstants.NO_NETWORK, null);
                return;
            }
            if (apiRequestModel.showProgress) {
                showProgressDialog(apiRequestModel.context);
            }
            Log.d("CallAPIMethod", apiRequestModel.call.request() + "");
            Log.d("CallAPIMethodToken", SessionManager.getInstance().getKeyValue(SessionManager.KEY_API_TOKEN, ""));
            Log.d("CallAPIMethodBody", requestBodyToString(apiRequestModel.call.request().body()) + "");
            apiRequestModel.call.clone().enqueue(new Callback<ApiResponseModel>() {
                @Override
                public void onResponse(@NonNull Call<ApiResponseModel> call, @NonNull Response<ApiResponseModel> response) {
                    //Log.d("CallAPIMethodOnResponse", response.body().toString());
                    if (apiRequestModel.showProgress) {
                        closeProgressDialog();
                    }
                    ApiResponseModel apiResponseModel;
                    if (response.body() == null) {
                        apiResponseModel = new ApiResponseModel();
                    } else {
                        apiResponseModel = response.body();
                    }
                    try {
                        if (apiResponseModel.success.equals(Constants.TOKEN_EXPIRED) ||
                                apiResponseModel.success.equals(Constants.TOKEN_AUTHENTICATION_FAIL)) {
                            LogOut(apiRequestModel.context, "false", apiResponseModel.success);
                            showErrorDialog(apiRequestModel, EnumConstants.SHOW_CUSTOM_ERROR,
                                    apiResponseModel.message);
                        } else if (response.isSuccessful() && checkAPIResponse(apiResponseModel)) {
                            apiRequestModel.iApiListener.apiSuccessResponse(apiRequestModel.condition,
                                    apiResponseModel);
                        } else {
                            apiRequestModel.iApiListener.apiErrorResponse(apiRequestModel.condition,
                                    EnumConstants.SHOW_CUSTOM_ERROR, apiResponseModel);
                            if (apiResponseModel.success.equals(Constants.FAILURE_RESULT)) {
                                showErrorDialog(apiRequestModel, EnumConstants.SHOW_CUSTOM_ERROR, apiResponseModel.message);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ApiResponseModel> call, @NonNull Throwable t) {
                    //Log.d("CallAPIMethodOnFailure", t.getMessage());
                    if (apiRequestModel.showProgress) {
                        closeProgressDialog();
                    }
                    try {
                        apiRequestModel.iApiListener.apiErrorResponse(apiRequestModel.condition, EnumConstants.SERVER_ERROR, null);
                        showErrorDialog(apiRequestModel, EnumConstants.SERVER_ERROR, null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("CallAPIMethodERROR", e.getMessage());
            closeProgressDialog();
        }
    }

    public void callAPIJSON(final ApiRequestModelJSON apiRequestModel) {
        try {
            if (!checkInternetConnection(apiRequestModel.context)) {
                apiRequestModel.iApiListener.apiErrorResponse(apiRequestModel.condition, EnumConstants.NO_NETWORK, null);
                return;
            }
            if (apiRequestModel.showProgress) {
                showProgressDialog(apiRequestModel.context);
            }
            Log.d("CallAPIMethod", apiRequestModel.call.request() + "");
            Log.d("CallAPIMethodToken", SessionManager.getInstance().getKeyValue(SessionManager.KEY_API_TOKEN, ""));
            Log.d("CallAPIMethodBody", requestBodyToString(apiRequestModel.call.request().body()) + "");
            apiRequestModel.call.clone().enqueue(new Callback<JsonElement>() {
                @Override
                public void onResponse(@NonNull Call<JsonElement> call, @NonNull Response<JsonElement> response) {
                    if (apiRequestModel.showProgress) {
                        closeProgressDialog();
                    }
                    ApiResponseModel apiResponseModel = new ApiResponseModel();
                    apiResponseModel.data = response.body();
                    try {
                        apiRequestModel.iApiListener.apiSuccessResponse(apiRequestModel.condition, apiResponseModel);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonElement> call, @NonNull Throwable t) {
                    if (apiRequestModel.showProgress) {
                        closeProgressDialog();
                    }
                    try {
                        apiRequestModel.iApiListener.apiErrorResponse(apiRequestModel.condition, EnumConstants.SERVER_ERROR, null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("CallAPIMethodERROR", e.getMessage());
            closeProgressDialog();
        }
    }

    public void showErrorDialog(ApiRequestModel apiRequestModel, EnumConstants enumConstants, String message) {
        Common.getInstance().callBusyRelatedDataClear();
        if (!apiRequestModel.showError) {
            return;
        }
        try {
            if (dialogNetworkError != null && dialogNetworkError.isShowing()) {
                dialogNetworkError.dismiss();
                dialogNetworkError = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            dialogNetworkError = new Dialog(apiRequestModel.context);
            dialogNetworkError.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogNetworkError.setCancelable(true);
            dialogNetworkError.setContentView(R.layout.dialog_error);
            //
            TextView tvTitle = dialogNetworkError.findViewById(R.id.tvTitle);
            TextView tvMessage = dialogNetworkError.findViewById(R.id.tvMessage);
            Button btnRetry = dialogNetworkError.findViewById(R.id.btnRetry);
            Button btnOK = dialogNetworkError.findViewById(R.id.btnOK);
            //
            if (message == null) {
                message = "";
            }
            switch (enumConstants) {
                case NO_NETWORK:
                    tvTitle.setText(Constants.UH_OH);
                    tvMessage.setText(Constants.PLEASE_CHECK_INTERNET);
                    break;
                case SERVER_ERROR:
                    tvTitle.setText(apiRequestModel.context.getResources().getString(R.string.app_name));
                    tvMessage.setText(Constants.SOMETHING_WENT_WRONG_SERVER);
                    break;
                case SHOW_CUSTOM_ERROR:
                    btnRetry.setVisibility(View.GONE);
                    tvTitle.setText(apiRequestModel.context.getResources().getString(R.string.app_name));
                    tvMessage.setText(message);
                    break;
                default:
                    tvTitle.setText(apiRequestModel.context.getResources().getString(R.string.app_name));
                    tvMessage.setText(Constants.SOMETHING_WENT_WRONG);
                    break;
            }
            //
            btnRetry.setOnClickListener(view -> {
                callAPI(apiRequestModel);
                dialogNetworkError.dismiss();
            });
            btnOK.setOnClickListener(v -> dialogNetworkError.dismiss());
            dialogNetworkError.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Boolean checkAPIResponse(ApiResponseModel apiResponseModel) {
        try {
            if (apiResponseModel != null) {
                if (!IsNullReturnValue(apiResponseModel.token, "").isEmpty()) {
                    //  SessionManager.getInstance().setAPI_TOKEN(apiResponseModel.token);
                }
                if (apiResponseModel.success.equals(Constants.SUCCESS_RESULT)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    Handler handlerProgressDialog = new Handler(Looper.getMainLooper());
    Runnable runnableProgressDialog = new Runnable() {
        @Override
        public void run() {
            try {
                closeProgressDialog();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public void showProgressDialog(Context activity) {
        try {
            if (progressDialog == null || !progressDialog.isShowing()) {
                progressDialog = new ProgressDialog(activity);
                progressDialog.setCancelable(false);
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                progressDialog.show();
                progressDialog.setContentView(R.layout.progressdialog);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        handlerProgressDialog.removeCallbacks(runnableProgressDialog);
        handlerProgressDialog.postDelayed(runnableProgressDialog, 30000);
    }

    public void closeProgressDialog() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void StoriesshowProgressDialog(Context activity) {
        try {
            if (progressDialog == null || !progressDialog.isShowing()) {
                progressDialog = new ProgressDialog(activity);
                progressDialog.setCancelable(false);
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                progressDialog.show();
                progressDialog.setContentView(R.layout.stories_progressdialog);
                Style style = Style.WAVE;
                SpinKitView spin_kit = (SpinKitView) progressDialog.findViewById(R.id.spin_kit);
                Sprite drawable = SpriteFactory.create(style);
                spin_kit.setIndeterminateDrawable(drawable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        handlerProgressDialog.removeCallbacks(runnableProgressDialog);
        handlerProgressDialog.postDelayed(runnableProgressDialog, 30000);
    }

    public void StoriescloseProgressDialog() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openNotificationIntent(Context context, Bundle bundle, Boolean isFromPushNotification) {
        try {
            Intent intent = new Intent(context, HelperActivity.class);
            intent.putExtras(bundle);
            intent.putExtra(Constants.FRAGMENT_TITLE, "Notifications");
            intent.putExtra(Constants.FRAGMENT_KEY, 8019);// NotificationsFragment
            intent.putExtra("isFromPushNotification", isFromPushNotification);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openFeedDetailsFrag(Activity activity, Feed feed, int position, int mediaPosition) {
        Intent intent = new Intent(activity, HelperActivity.class);
        intent.putExtra(Constants.FRAGMENT_TITLE, "");
        intent.putExtra(Constants.FRAGMENT_KEY, 8069);
        intent.putExtra("feed", feed);
        intent.putExtra("feedPosition", position);
        intent.putExtra("mediaPosition", mediaPosition);
        activity.startActivityForResult(intent, Utility.generateRequestCodes().get("UPDATE_FEED_DATA"));
    }

    public void addMediaDirectly(Activity activity, String isFrom) {

        /*Intent intent = new Intent(activity, HelperActivity.class);
        intent.putExtra(Constants.FRAGMENT_KEY, 9039);
        intent.putExtra("isFrom", isFrom);
        activity.startActivityForResult(intent, Utility.generateRequestCodes().get("ADD_MEDIA_TO_FEED"));*/
        ImagePicker.with(activity)
                .setFolderMode(true)
                .setCameraOnly(false)
                .setFolderTitle("Album")
                .setMultipleMode(true)
                .setSelectedImages(images)
                .setMaxSize(20)
                .setBackgroundColor("#212121")
                .setAlwaysShowDoneButton(false)
                .setRequestCode(Utility.generateRequestCodes().get("ADD_MEDIA_TO_FEED"))
                .setKeepScreenOn(true)
                .setIsFeed(true)
                .start();
    }

    public void openCelebFeeds(Context context, String CELEB_ID, boolean isFanFollow) {
        Intent intent = new Intent(context, HelperActivity.class);
        intent.putExtra(Constants.FRAGMENT_TITLE, "");
        intent.putExtra(Constants.FRAGMENT_KEY, 8032);
        intent.putExtra("CELEB_ID", CELEB_ID);
        intent.putExtra("isFanFollow", isFanFollow);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void openFeedComments(Feed feed, String id, boolean isFeed, int feedPosition, int mediaPosition, boolean isDetails, boolean isFromMediaDetailsFragment) {
        Intent intent = new Intent(AppController.getInstance().getCurrentRegisteredActivity(), HelperActivity.class);
        intent.putExtra(Constants.FRAGMENT_TITLE, "Comments");
        intent.putExtra(Constants.FRAGMENT_KEY, 8070);
        intent.putExtra("feed", feed);
        intent.putExtra("id", id);
        intent.putExtra("isFeed", isFeed);
        intent.putExtra("feedPosition", feedPosition);
        intent.putExtra("mediaPosition", mediaPosition);
        intent.putExtra("isDetails", isDetails);
        intent.putExtra("isFromMediaDetailsFragment", isFromMediaDetailsFragment);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppController.getInstance().getCurrentRegisteredActivity().startActivityForResult(intent, Utility.generateRequestCodes().get("UPDATE_FEED_DATA"));
    }

    public void getSingleFeedData(final String feedId) {
        try {
            Feed feed = new Feed();
            feed.id = feedId;
            if (AppController.getInstance().getCurrentRegisteredActivity() != null) {
                openFeedDetailsFrag(AppController.getInstance().getCurrentRegisteredActivity(), feed, -1, -1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openProfileScreen(Context context, String profileId) {
        Intent intent = new Intent(context, HelperActivity.class);
        intent.putExtra(Constants.FRAGMENT_TITLE, "");
        intent.putExtra(Constants.FRAGMENT_KEY, 8029);
        intent.putExtra("CelebId", profileId);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void openCelebSchedulesScreen(Context context, String celebId, String activityType) {
        //  if (activityType.equalsIgnoreCase(Constants.NOTIFICATION_ACTIVITY_SCHEDULE)) {
        Intent intent = new Intent(context, HelperActivity.class);
        intent.putExtra(Constants.FRAGMENT_TITLE, "Calendar");
        intent.putExtra("celebID", celebId);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Constants.FRAGMENT_KEY, 8009);// ScheduleListfragment
        context.startActivity(intent);
        /*} else {
            Intent intent = new Intent(context, HelperActivity.class);
            intent.putExtra(Constants.FRAGMENT_KEY, 8066);
            intent.putExtra(Constants.FRAGMENT_TITLE, "Schedules");
            intent.putExtra("CELEB_ID", celebId);
            //
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }*/
    }

    public void openViewScheduleScreen(Context context, String scheduleId, String celebId) {
        Intent intent = new Intent(context, HelperActivity.class);
        intent.putExtra(Constants.FRAGMENT_TITLE, "View Schedule");
        intent.putExtra(Constants.FRAGMENT_KEY, 9044);// ViewScheduleFragment
        intent.putExtra("scheduleID", scheduleId);
        intent.putExtra("celebID", celebId);
        context.startActivity(intent);
    }

    public void openCelebSettingsScreen(Context context) {
        if (Common.isCelebAndManager(context)) {
            Intent intent = new Intent(context, HelperActivity.class);
            intent.putExtra(Constants.FRAGMENT_TITLE, "Manager Settings");
            intent.putExtra(Constants.FRAGMENT_KEY, 8057);// CelebManagerSettingFragment
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else if (Common.isCelebStatus(context)) {
            Logger.d("isManager", "isCeleb");
            Intent intent = new Intent(context, HelperActivity.class);
            intent.putExtra(Constants.FRAGMENT_TITLE, "Manager Settings");
            intent.putExtra(Constants.FRAGMENT_KEY, 8046);// ManagerSettingsList
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    public void openManagerSettingsScreen(Context context) {
        Intent intent = new Intent(context, HelperActivity.class);
        intent.putExtra(Constants.FRAGMENT_TITLE, "Manager Settings");
        intent.putExtra(Constants.FRAGMENT_KEY, 8047);// Managing_celeb_or_sub_manger_list
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void openTransactionScreen(Context context, String condition) {
        Intent intent = new Intent(context, HelperActivity.class);
        intent.putExtra(Constants.FRAGMENT_TITLE, "Transactions");
        intent.putExtra(Constants.FRAGMENT_KEY, 8205);
        intent.putExtra("MyCartfragment", "Transactions");
        intent.putExtra("condition", condition);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public String convertFirstLetterToCapital(String value) {
        value = IsNullReturnValue(value, "");
        if (value.isEmpty()) {
            return "";
        }
        if (value.length() == 1) {
            return value.toUpperCase();
        }
        return Character.toUpperCase(value.charAt(0)) + value.substring(1);
    }

    public void selfProfile(Context context, Profile profile) {
        Intent intent = new Intent(context, HelperActivity.class);
        intent.putExtra(Constants.FRAGMENT_KEY, 8002);
        intent.putExtra(Constants.FRAGMENT_TITLE, "My Profile");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("celeb_status", "offline");
        intent.putExtra("CelebId", profile.id);
        intent.putExtra("PROFILE_NAME", profile.firstName + " " + profile.lastName);
        //CelebId
        intent.putExtra("Imaage", ApiClient.BASE_URL + profile.profilePic);
        intent.putExtra("proffession", profile.profession);
        if (profile.aboutMe != null) {
            intent.putExtra("ABOUT", profile.aboutMe);
        } else {
            intent.putExtra("ABOUT", "");
        }
        context.startActivity(intent);
    }

    public void celebUserProfile(Context context, Profile profile) {
        Intent intent = new Intent(context, HelperActivity.class);
        if (profile.isCeleb) {
            ProfileDataModel profileDataModel = new ProfileDataModel();
            profileDataModel._id = profile.id;
            profileDataModel.firstName = profile.firstName;
            profileDataModel.lastName = profile.lastName;
            profileDataModel.isCeleb = true;
            profileDataModel.role = "";
            profileDataModel.avtar_imgPath = profile.profilePic;
            profileDataModel.isOnline = false;
            profileDataModel.profession = profile.profession;
            profileDataModel.aboutMe = "";
            profileDataModel.isFan = true;//Need to get from Backend - IS_FAN
//            Common.getInstance().viewCelebProfileDialog(context, profileDataModel, progressDialog);
            //checking with customize class
            viewCelebProfileDialog(context, profileDataModel, progressDialog);
        } else {
            intent.putExtra(Constants.FRAGMENT_TITLE, "Member Profile");
            intent.putExtra(Constants.FRAGMENT_KEY, 8029);
            intent.putExtra("fromSearchResult", false);
            intent.putExtra("isManagerProfile", false);
            intent.putExtra("isUserProfile", true);
            intent.putExtra("celeb_status", "online");
            intent.putExtra("isCeleb", profile.isCeleb);
            intent.putExtra("CelebId", profile.id);
            intent.putExtra("PROFILE_NAME", profile.firstName + " " + profile.lastName);
            intent.putExtra("Imaage", ApiClient.BASE_URL + profile.profilePic);
            intent.putExtra("ABOUT", profile.aboutMe);
            intent.putExtra("proffession", profile.profession);
            context.startActivity(intent);
        }
    }

    public void insertFeedComment(Context context, AddCommentParams object, IApiListener iApiListener) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        JSONObject input = new JSONObject();
        try {
            input.put("celebId", object.feed.feedMemberDetails._id);
            input.put("memberId", SessionManager.userLogin.userId);
            input.put("feedId", object.feed.id);
            if (!object.isFeed) {
                input.put("mediaId", object.feed.mediaList.get(object.feedOrMediaPosition).mediaId);
            }
            input.put("source", object.commentText);
            input.put("activities", Constants.ACTION_TYPE_COMMENT);
            input.put("status", "Active");
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), input.toString());
        Call<ApiResponseModel> call = apiInterface.POST(Constants.FeedConstants.URL_ADD_COMMENT, requestBody);
        callAPI(new ApiRequestModel().setModel(context, call, Constants.FeedConstants.URL_ADD_COMMENT, object.showProgress, iApiListener, false));
    }

    public void openScheduleListFragment(Context context, String celebId) {
        Intent intent = new Intent(context, HelperActivity.class);
        intent.putExtra(Constants.FRAGMENT_TITLE, "Calendar");
        intent.putExtra("celebID", celebId);
        intent.putExtra(Constants.FRAGMENT_KEY, 8009);// ScheduleListfragment
        context.startActivity(intent);
    }


    public void updateLiveStatusAPI(Context context, IApiListener iApiListener) {
        //SOCKET EMIT
        /*try {
            Common.getInstance().showProgressDialog(context);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("memberId", SessionManager.userLogin.userId);
            Common.getInstance().getSocket().emit(liveLogStatus, jsonObject);//Data will be handle in chatSocket class (liveLogStatus - on listener)
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        //API CALL
        Boolean previousLiveStatus = Common.getInstance().isOnlineStatus();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("memberId", SessionManager.userLogin.userId);
            if (previousLiveStatus) {
                jsonObject.put("liveStatus", "offline");
            } else {
                jsonObject.put("liveStatus", "online");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        Call<ApiResponseModel> call = apiInterface.POST(ApiClient.PASS_LOG_STATUS, requestBody);
        callAPI(new ApiRequestModel().setModel(context, call, ApiClient.PASS_LOG_STATUS, true, iApiListener, true));
    }

    public void getLocation(Activity activity, String fromWhichClass, Boolean requestLocationSettings) {
        try {//To get last known location
            LocationManager manager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
            Location utilLocation = null;
            List<String> providers = manager.getProviders(true);
            for (String provider : providers) {
                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity,
                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    utilLocation = manager.getLastKnownLocation(provider);
                    if (utilLocation != null) {
                        Common.currentLocation = utilLocation;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
            mSettingsClient = LocationServices.getSettingsClient(activity);
            mLocationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    //Log.d("onLocationResult",locationResult.getLastLocation().getLatitude()+", "+locationResult.getLastLocation().getLongitude());
                    Common.currentLocation = locationResult.getLastLocation();
                    if (Common.currentLocation != null && mFusedLocationClient != null) {
                        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
                    }
                }
            };
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(60 * 1000);
            mLocationRequest.setFastestInterval(30 * 1000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mLocationRequest.setNumUpdates(5);//need to declare number, how many times you want to get location updates
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
            builder.addLocationRequest(mLocationRequest);
            LocationSettingsRequest mLocationSettingsRequest = builder.build();
            //
            mSettingsClient
                    .checkLocationSettings(mLocationSettingsRequest)
                    .addOnSuccessListener(activity, new OnSuccessListener<LocationSettingsResponse>() {
                        @SuppressLint("MissingPermission")
                        @Override
                        public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                        }
                    })
                    .addOnFailureListener(activity, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            int statusCode = ((ApiException) e).getStatusCode();
                            switch (statusCode) {
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                    try {
                                        if (fromWhichClass.isEmpty()) {
                                        }
                                        if (requestLocationSettings) {
                                            ResolvableApiException rae = (ResolvableApiException) e;
                                            rae.startResolutionForResult(activity, Constants.REQUEST_LOCATION_CHECK_SETTINGS);
                                        }
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    String errorMessage = "Location settings are inadequate, and cannot be fixed here. Fix in Settings.";
                                    break;
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCountryCode() {
        String countryCode = "0";
        if (currentLocation != null) {
            try {
                Geocoder geocoder = new Geocoder(Utility.getContext(), Locale.getDefault());
                List<Address> addresses = geocoder.getFromLocation(currentLocation.getLatitude(),
                        currentLocation.getLongitude(), 1);
                countryCode = addresses.get(0).getCountryCode();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Log.d("countryCode", countryCode.toUpperCase());
        return countryCode.toUpperCase();
    }

    public String getStateName() {
        String state = "0";
        if (currentLocation != null) {
            try {
                Geocoder geocoder = new Geocoder(Utility.getContext(), Locale.getDefault());
                List<Address> addresses = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1);
                state = addresses.get(0).getAdminArea();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return state.toUpperCase();
    }

    public Integer getUniqueIDFromMemberID(String memberID) {
        try {
            Integer uniqueID = new Random().nextInt();
            try {
                uniqueID = Integer.parseInt(memberID.replaceAll("[^\\d.]", "").substring(0, 7));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return uniqueID;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public Uri getImageURL(String baseURl, Media media, Boolean showThumbnail) {
        if (media.url != null && media.url.length() > 0) {
            return Uri.parse(baseURl + media.url);
        } else if (media.uri != null) {
            return media.uri;
        } else if (media.source != null) {
            if (showThumbnail && media.source.urlthumbnail != null && media.source.urlthumbnail.length() > 0 && !SessionManager.getInstance().getKeyValue(SessionManager.KEY_SHOW_HD_IMAGES, false)) {
                return Uri.parse(baseURl + media.source.urlthumbnail);
            } else if (!showThumbnail && media.source.videoUrl != null && media.source.videoUrl.length() > 0) {
                return Uri.parse(baseURl + media.source.videoUrl);
            } else if (media.source.url != null && media.source.url.length() > 0) {
                return Uri.parse(baseURl + media.source.url);
            } else {
                return Uri.parse("");
            }
        } else {
            return Uri.parse("");
        }
    }

    public void setGIFImageToFresco(SimpleDraweeView simpleDraweeView, ImageRequest request, Media media, Boolean showThumbnail) {
        try {
            PipelineDraweeControllerBuilder pipelineDraweeControllerBuilder = Fresco.newDraweeControllerBuilder();
            if (!showThumbnail) {
                pipelineDraweeControllerBuilder.setLowResImageRequest(ImageRequest.fromUri(Common.getInstance().getImageURL(ApiClient.BASE_URL,
                        media, true)));
            }
            if (request != null) {
                pipelineDraweeControllerBuilder.setImageRequest(request);
            } else {
                pipelineDraweeControllerBuilder.setImageRequest(ImageRequest.fromUri(Common.getInstance().getImageURL(ApiClient.BASE_URL, media, showThumbnail)));
            }
            pipelineDraweeControllerBuilder.setOldController(simpleDraweeView.getController()).setAutoPlayAnimations(!showThumbnail);
            pipelineDraweeControllerBuilder.setControllerListener(new BaseControllerListener<ImageInfo>() {
                @Override
                public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                    super.onFinalImageSet(id, imageInfo, animatable);
                }
            });
            simpleDraweeView.setController(pipelineDraweeControllerBuilder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void getBitmapFromFresco(Uri imageURL, SimpleDraweeView simpleDraweeView) {
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        ImageRequest imageRequest = ImageRequestBuilder
                .newBuilderWithSource(imageURL)
                .build();
        DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(imageRequest, AppController.getInstance().getCurrentRegisteredActivity());
        try {
            dataSource.subscribe(new BaseBitmapDataSubscriber() {
                @Override
                public void onNewResultImpl(@Nullable final Bitmap bitmap) {
                    try {
                        if (dataSource.isFinished() && bitmap != null) {
                            Bitmap bmp = Bitmap.createBitmap(bitmap);
                            dataSource.close();
                            AppController.getInstance().getCurrentRegisteredActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        simpleDraweeView.setImageBitmap(bmp);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailureImpl(DataSource dataSource) {
                    if (dataSource != null) {
                        dataSource.close();
                    }
                }
            }, CallerThreadExecutor.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setGlideLowHDImage(ImageView imageView, Media media, Boolean showThumbnail) {
        try {
            Uri finalImageUri = null;
            if (showThumbnail == null) {
                showThumbnail = false;
            }
            try {
                if (media.url != null && media.url.length() > 0) {
                    finalImageUri = Uri.parse(Constants.MEDIA_BASE_URL + media.url);
                } else if (media.uri != null) {
                    finalImageUri = media.uri;
                } else if (media.source != null) {
                    if (showThumbnail) {
                        finalImageUri = Uri.parse(Constants.MEDIA_BASE_URL + media.source.urlthumbnail);
                    } else {
                        if (media.type.equals(Constants.MEDIA_TYPE_GIF)) {
                            finalImageUri = Uri.parse(Constants.MEDIA_BASE_URL + media.source.videoUrl);
                        } else {
                            finalImageUri = Uri.parse(Constants.MEDIA_BASE_URL + media.source.url);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (finalImageUri == null) {
                return;
            }
            if (!showThumbnail && media.source.urlthumbnail != null && media.source.urlthumbnail.length() > 0) {
                RequestBuilder<Drawable> thumbnailRequest = Glide
                        .with(AppController.getInstance().getCurrentRegisteredActivity())
                        .load(media.source.urlthumbnail);

                Glide.with(AppController.getInstance().getCurrentRegisteredActivity())
                        .load(finalImageUri)
                        .thumbnail(thumbnailRequest)
                        .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(imageView);
            } else {
                Glide.with(AppController.getInstance().getCurrentRegisteredActivity())
                        .load(finalImageUri)
                        .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(imageView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isNormalCallActive(Context context) {
        AudioManager manager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        assert manager != null;
        if (manager.getMode() == AudioManager.MODE_IN_CALL) {
            return true;
        }
        return false;
    }

    public void cusToast(Context context, String message) {
        try {
            if (cusToast != null) {
                cusToast.cancel();
            }
            cusToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            cusToast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cusToastLong(Context context, String message) {
        try {
            if (cusToast != null) {
                cusToast.cancel();
            }
            cusToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            cusToast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    BottomMenuActivity bottomMenuActivity;

    public void setBottomMenuActivity(BottomMenuActivity bottomMenuActivity) {
        this.bottomMenuActivity = bottomMenuActivity;
    }

    public BottomMenuActivity getBottomMenuActivity() {
        return bottomMenuActivity;
    }

    //FeedsFragment

    FeedsFragment feedsFragment;

    public void setFeedsFragment(FeedsFragment feedsFragment) {
        this.feedsFragment = feedsFragment;
    }

    public FeedsFragment getFeedsFragment() {
        return feedsFragment;
    }


    CelebritiesTabFragment celebritiesTabFragment;

    public void setCelebritiesTabFragment(CelebritiesTabFragment celebritiesTabFragment) {
        this.celebritiesTabFragment = celebritiesTabFragment;
    }

    public CelebritiesTabFragment getCelebritiesTabFragment() {
        return celebritiesTabFragment;
    }

    HelperActivity helperActivity;

    public void setHelperActivity(HelperActivity helperActivity) {
        this.helperActivity = helperActivity;
    }

    public HelperActivity getHelperActivity() {
        return helperActivity;
    }

    private ActivitySingleChat activitySingleChat;

    public ActivitySingleChat getActivitySingleChat() {
        return activitySingleChat;
    }

    public void setActivitySingleChat(ActivitySingleChat activitySingleChat) {
        this.activitySingleChat = activitySingleChat;
    }

    private FragmentTabsChat fragmentTabsChat;

    public FragmentTabsChat getFragmentTabsChat() {
        return fragmentTabsChat;
    }

    public void setFragmentTabsChat(FragmentTabsChat fragmentTabsChat) {
        this.fragmentTabsChat = fragmentTabsChat;
    }

    private FragmentChatList fragmentChatList;

    public FragmentChatList getFragmentChatList() {
        return fragmentChatList;
    }

    public void setFragmentChatList(FragmentChatList fragmentChatList) {
        this.fragmentChatList = fragmentChatList;
    }


    private StoriesFragment storiesFragment;

    public void setStoriesFragment(StoriesFragment storiesFragment) {
        this.storiesFragment = storiesFragment;
    }

    public StoriesFragment getStoriesFragment() {
        return storiesFragment;
    }


    private FragmentCallsList fragmentCallsList;

    public FragmentCallsList getFragmentCallsList() {
        return fragmentCallsList;
    }

    public void setFragmentCallsList(FragmentCallsList fragmentCallsList) {
        this.fragmentCallsList = fragmentCallsList;
    }

    private SeenListDailog seenListDailog;

    public void setSeenListDailog(SeenListDailog seenListDailog) {
        this.seenListDailog = seenListDailog;
    }

    public SeenListDailog getSeenListDailog() {
        return seenListDailog;
    }

    private FragmentNewChatList fragmentNewChatList;

    public FragmentNewChatList getFragmentNewChatList() {
        return fragmentNewChatList;
    }

    public void setFragmentNewChatList(FragmentNewChatList fragmentNewChatList) {
        this.fragmentNewChatList = fragmentNewChatList;
    }

    private CelebrityProfileFragment celebrityProfileFragment;

    public CelebrityProfileFragment getCelebrityProfileFragment() {
        return celebrityProfileFragment;
    }

    public void setCelebrityProfileFragment(CelebrityProfileFragment celebrityProfileFragment) {
        this.celebrityProfileFragment = celebrityProfileFragment;
    }


    private PhotosFragment photosFragment;

    public PhotosFragment getPhotosFragment() {
        return photosFragment;
    }

    public void setPhotosFragment(PhotosFragment photosFragment) {
        this.photosFragment = photosFragment;
    }

    private VideoPlayerFragment videoPlayerFragment;

    public VideoPlayerFragment getVideoPlayerFragment() {
        return videoPlayerFragment;
    }

    public void setVideoPlayerFragment(VideoPlayerFragment videoPlayerFragment) {
        this.videoPlayerFragment = videoPlayerFragment;
    }

    public String getChatRoomID(Context context, String recevierID) {
        String chatRoomID;
        String userMemberId = Common.getInstance().IsNullReturnValue(SessionManager.userLogin.userId, "");
        if (userMemberId.compareTo(recevierID) < 0) {
            chatRoomID = userMemberId + recevierID;
        } else {
            chatRoomID = recevierID + userMemberId;
        }
        Log.e("getChatRoomID", chatRoomID);
        return chatRoomID;
    }

    public void setMenuItemColor(int colorId, MenuItem menuItem) {
        SpannableString spanString = new SpannableString(menuItem.getTitle().toString());
        spanString.setSpan(new ForegroundColorSpan(colorId), 0, spanString.length(), 0); //fix the color to white
        menuItem.setTitle(spanString);
    }

    public boolean isMyServiceRunning(Class<?> serviceClass, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        assert manager != null;
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                //Log.i ("isMyServiceRunning?", true+"");
                return true;
            }
        }
        //Log.i ("isMyServiceRunning?", false+"");
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public boolean isJobServiceOn(Context context, Integer jobRequestCode) {
        JobScheduler scheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        boolean hasBeenScheduled = false;
        assert scheduler != null;
        for (JobInfo jobInfo : scheduler.getAllPendingJobs()) {
            if (jobInfo.getId() == jobRequestCode) {
                hasBeenScheduled = true;
                break;
            }
        }
        return hasBeenScheduled;
    }

    public void doBackgroundWortHere() {
        if (!isLoginInApp()) {
            return;
        }
        Log.d(TAG, "Running doBackgroundWork");
        new NotificationUtil().dismissCallRunningNotification(AppController.getInstance().getCurrentRegisteredActivity());

    }


    public boolean isAppForeground(Context context) {
        //shiva aa
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        assert activityManager != null;
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null || appProcesses.size() <= 0) {
            return false;
        }
        final String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    public Boolean IsNull(String value) {
        if (value == null) {
            return true;
        } else if (value.trim().equalsIgnoreCase("") || value.equalsIgnoreCase("null")) {
            return true;
        } else {
            return false;
        }
    }

    public String IsNullReturnValue(String value, String returnValue) {
        if (value == null) {
            return returnValue;
        } else if (value.trim().isEmpty() || value.equalsIgnoreCase("null")) {
            return returnValue;
        } else {
            return value;
        }
    }

    public String concatStringArrayList(ArrayList<String> arrayList, String concatRegix) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < arrayList.size(); i++) {
            if (i == 0) {
                result = new StringBuilder(arrayList.get(i));
            } else {
                result.append(concatRegix).append(arrayList.get(i));
            }
        }
        return result.toString();
    }

    public int ArrayListStringIndex(ArrayList<String> arrayList, String value) {
        if (arrayList != null) {
            return arrayList.indexOf(value);
        } else {
            return -1;
        }
    }

    public int ArrayListIntIndex(ArrayList<Integer> arrayList, Integer value) {
        if (arrayList != null) {
            return arrayList.indexOf(value);
        } else {
            return -1;
        }
    }

    public Integer findIndexJsonArray(String value, String keyName, JSONArray jsonArray) {
        Integer index = -1;
        try {
            if (jsonArray != null && jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    String data = jsonArray.optJSONObject(i).optString(keyName);
                    if (value.equals(data)) {
                        return i;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return index;
    }

    public Integer roundToNearestInt(double doubleValue) {
        Integer intValue = (int) doubleValue;
        if (intValue < doubleValue) {
            return intValue + 1;
        } else {
            return intValue;
        }
    }

    public String roundTwoDecimalPlaces(Double value) {
        return new DecimalFormat("0.00").format(value);
    }

    public Boolean getBooleanFromInt(Integer value) {
        return value != null && value != 0;
    }

    public Boolean checkResponseStatus(JSONObject jsonObject) {
        try {
            if (jsonObject != null && jsonObject.length() > 0) {
                if (jsonObject.optString("success", "").equalsIgnoreCase("1")) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setFrescoImage(final Uri uri, final SimpleDraweeView imageView, final int singleItemWidth, final int singleItemHeight, final List<FacePoint> facePointList) {
        ImageRequest request;
        if (singleItemWidth > 0 && singleItemHeight > 0 && facePointList != null && facePointList.size() > 0) {
            request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .setPostprocessor(new FaceCenterCrop(singleItemWidth, singleItemHeight, facePointList))
                    .build();
        } else {
            request = ImageRequestBuilder.newBuilderWithSource(uri)
                    //.setPostprocessor(new FaceCenterCrop(singleItemWidth, singleItemHeight, facePointList))
                    .build();
        }
        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(imageView.getController())
                .build();
        imageView.setController(controller);
    }

    public void setGlideImage(final Context context, final String imagePath, final ImageView imageView) {
        try {
            Glide.with(context)
                    .load(imagePath)
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.ic_placeholder)
                            .error(R.drawable.appiconandroid)
                            .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setStoryImage(final Context context, final String imagePath, final ImageView imageView) {
        try {
            Glide.with(context)
                    .load(imagePath)
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.corner_radius_dark_transparent_3)
                            .error(R.drawable.corner_radius_dark_transparent_3)
                            .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setGlideImageFaceDetector(final Context context, final String imagePath, final ImageView imageView, final List<FacePoint> facePointList) {
        try {
            Glide.with(context)
                    .load(imagePath)
                    .transform(new GlideFaceCenterCrop(facePointList))
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.ic_placeholder)
                            .error(R.drawable.appiconandroid)
                            .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ChatDataConvertModel convertToChatDataConvertModel(ChatDataModel chatDataModel, String userMemberId) {
        try {
            ChatDataConvertModel chatDataConvertModel = new ChatDataConvertModel();
            ChatSenderReceiverInfo chatSenderReceiverInfo;
            if (userMemberId.equalsIgnoreCase(chatDataModel.receiverId)) {
                chatSenderReceiverInfo = chatDataModel.senderInfo;
            } else {
                chatSenderReceiverInfo = chatDataModel.receiverInfo;
            }
            chatDataConvertModel._id = chatSenderReceiverInfo._id;
            chatDataConvertModel.firstName = chatSenderReceiverInfo.firstName;
            chatDataConvertModel.lastName = chatSenderReceiverInfo.lastName;
            chatDataConvertModel.avtar_imgPath = chatSenderReceiverInfo.avtar_imgPath;
            chatDataConvertModel.profession = chatSenderReceiverInfo.profession;
            chatDataConvertModel.aboutMe = chatSenderReceiverInfo.aboutMe;
            chatDataConvertModel.role = chatSenderReceiverInfo.role;
            chatDataConvertModel.isOnline = chatSenderReceiverInfo.isOnline;
            chatDataConvertModel.isCeleb = chatSenderReceiverInfo.isCeleb;
            chatDataConvertModel.isFan = chatSenderReceiverInfo.isFan;
            chatDataConvertModel.message = chatDataModel.message;
            //chatDataConvertModel.senderId = userMemberId;
            chatDataConvertModel.senderId = chatDataModel.receiverId;
            chatDataConvertModel.receiverId = chatSenderReceiverInfo._id;
            chatDataConvertModel.createdAt = chatDataModel.createdAt;
            chatDataConvertModel.counter = chatDataModel.counter;
            return chatDataConvertModel;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ChatDataConvertModel convertToChatDataConvertModel(RecentCallsModel recentCallsModel, String userMemberId) {
        try {
            ChatDataConvertModel chatDataConvertModel = new ChatDataConvertModel();
            ChatSenderReceiverInfo chatSenderReceiverInfo;
            if (userMemberId.equalsIgnoreCase(recentCallsModel.lastCallStatus.receiverId._id)) {
                chatSenderReceiverInfo = recentCallsModel.lastCallStatus.senderId;
            } else {
                chatSenderReceiverInfo = recentCallsModel.lastCallStatus.receiverId;
            }
            chatDataConvertModel._id = chatSenderReceiverInfo._id;
            chatDataConvertModel.firstName = chatSenderReceiverInfo.firstName;
            chatDataConvertModel.lastName = chatSenderReceiverInfo.lastName;
            chatDataConvertModel.avtar_imgPath = chatSenderReceiverInfo.avtar_imgPath;
            chatDataConvertModel.profession = chatSenderReceiverInfo.profession;
            chatDataConvertModel.aboutMe = chatSenderReceiverInfo.aboutMe;
            chatDataConvertModel.role = chatSenderReceiverInfo.role;
            chatDataConvertModel.isOnline = chatSenderReceiverInfo.isOnline;
            chatDataConvertModel.isCeleb = chatSenderReceiverInfo.isCeleb;
            chatDataConvertModel.isFan = chatSenderReceiverInfo.isFan;
            chatDataConvertModel.message = "";
            chatDataConvertModel.senderId = recentCallsModel.lastCallStatus.receiverId._id;
            chatDataConvertModel.receiverId = chatSenderReceiverInfo._id;
            chatDataConvertModel.createdAt = recentCallsModel.lastCallStatus.createdAt;
            chatDataConvertModel.counter = 0;
            return chatDataConvertModel;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ChatDataConvertModel convertToChatDataConvertModel(SingleChatHistoryModel singleChatHistoryModel, ChatDataConvertModel chatDataConvertModelParent, String userMemberId) {
        try {
            ChatDataConvertModel chatDataConvertModel = new ChatDataConvertModel();
            if (userMemberId.equalsIgnoreCase(singleChatHistoryModel.receiverId)) {
                chatDataConvertModel._id = singleChatHistoryModel.senderId;
                chatDataConvertModel.receiverId = singleChatHistoryModel.senderId;
            } else {
                chatDataConvertModel._id = singleChatHistoryModel.receiverId;
                chatDataConvertModel.receiverId = singleChatHistoryModel.receiverId;
            }
            chatDataConvertModel.firstName = chatDataConvertModelParent.firstName;
            chatDataConvertModel.lastName = chatDataConvertModelParent.lastName;
            chatDataConvertModel.avtar_imgPath = chatDataConvertModelParent.avtar_imgPath;
            chatDataConvertModel.profession = chatDataConvertModelParent.profession;
            chatDataConvertModel.aboutMe = chatDataConvertModelParent.aboutMe;
            chatDataConvertModel.role = chatDataConvertModelParent.role;
            chatDataConvertModel.isOnline = chatDataConvertModelParent.isOnline;
            chatDataConvertModel.isCeleb = chatDataConvertModelParent.isCeleb;
            chatDataConvertModel.isFan = chatDataConvertModelParent.isFan;
            chatDataConvertModel.message = singleChatHistoryModel.message;
            chatDataConvertModel.senderId = singleChatHistoryModel.receiverId;
            chatDataConvertModel.createdAt = singleChatHistoryModel.createdAt;
            chatDataConvertModel.counter = 0;
            return chatDataConvertModel;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ProfileDataModel convertToViewProfile(ChatDataConvertModel chatDataConvertModel) {
        try {
            ProfileDataModel profileDataModel = new ProfileDataModel();
            profileDataModel._id = chatDataConvertModel._id;
            profileDataModel.firstName = chatDataConvertModel.firstName;
            profileDataModel.lastName = chatDataConvertModel.lastName;
            profileDataModel.isCeleb = chatDataConvertModel.isCeleb;
            profileDataModel.role = chatDataConvertModel.role;
            profileDataModel.avtar_imgPath = chatDataConvertModel.avtar_imgPath;
            profileDataModel.isOnline = chatDataConvertModel.isOnline;
            profileDataModel.profession = chatDataConvertModel.profession;
            profileDataModel.aboutMe = chatDataConvertModel.aboutMe;
            profileDataModel.isFan = chatDataConvertModel.isFan;
            return profileDataModel;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ProfileDataModel convertToViewProfile(ChatSenderReceiverInfo chatSenderReceiverInfo) {
        try {
            ProfileDataModel profileDataModel = new ProfileDataModel();
            profileDataModel._id = chatSenderReceiverInfo._id;
            profileDataModel.firstName = chatSenderReceiverInfo.firstName;
            profileDataModel.lastName = chatSenderReceiverInfo.lastName;
            profileDataModel.isCeleb = chatSenderReceiverInfo.isCeleb;
            profileDataModel.role = chatSenderReceiverInfo.role;
            profileDataModel.avtar_imgPath = chatSenderReceiverInfo.avtar_imgPath;
            profileDataModel.isOnline = chatSenderReceiverInfo.isOnline;
            profileDataModel.profession = chatSenderReceiverInfo.profession;
            profileDataModel.aboutMe = chatSenderReceiverInfo.aboutMe;
            profileDataModel.isFan = chatSenderReceiverInfo.isFan;
            return profileDataModel;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //To update online celebrities data
    private List<Celebrity> onlineCelebList
            = new ArrayList<>();

    private asyncTaskOnlineCelebList asyncTaskOnlineCelebList;

    public List<Celebrity> getOnlineCelebList() {
        return onlineCelebList;
    }

    public void setOnlineCelebList(JSONObject response) {
        if (asyncTaskOnlineCelebList != null && asyncTaskOnlineCelebList.getStatus() == AsyncTask.Status.RUNNING) {
            asyncTaskOnlineCelebList.cancel(true);
        }
        asyncTaskOnlineCelebList = new asyncTaskOnlineCelebList(response);
        asyncTaskOnlineCelebList.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class asyncTaskOnlineCelebList extends AsyncTask<String, Void, List<Celebrity>> {
        JSONObject response;

        public asyncTaskOnlineCelebList(JSONObject response) {
            this.response = response;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Celebrity> doInBackground(String... params) {
            List<Celebrity> celebrityListFinalResult = new ArrayList<>();
            try {
                Gson gson = new Gson();
                Type type = new TypeToken<List<Celebrity>>() {
                }.getType();
                List<Celebrity> onlineCelebListTemp = gson.fromJson(response.getString("onlineCelebrities"), type);
                boolean isSwitched = false;
                for (int i = 0; i < onlineCelebListTemp.size(); i++) {
                    if (findDuplicateOnlineCeleb(onlineCelebListTemp.get(i).id, onlineCelebList) < 0 && !onlineCelebListTemp.get(i).id.equals(Common.isLoginId())) {
                        if (isSwitched && !onlineCelebListTemp.get(i).id.equals(Common.isMangerSwitchCarryingId())) {
                            onlineCelebList.add(0, onlineCelebListTemp.get(i));
                        } else {
                            onlineCelebList.add(0, onlineCelebListTemp.get(i));
                        }
                    }
                }
                for (int i = 0; i < onlineCelebList.size(); i++) {
                    if (findDuplicateOnlineCeleb(onlineCelebList.get(i).id, onlineCelebListTemp) >= 0 && findDuplicateOnlineCeleb(onlineCelebList.get(i).id, celebrityListFinalResult) < 0) {
                        celebrityListFinalResult.add(onlineCelebListTemp.get(findDuplicateOnlineCeleb(onlineCelebList.get(i).id, onlineCelebListTemp)));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return celebrityListFinalResult;
        }

        @Override
        protected void onPostExecute(List<Celebrity> result) {
            super.onPostExecute(result);
            try {
                onlineCelebList = result;
                /*if (Common.getInstance().getFeedsFragment() != null) {
                    Common.getInstance().getFeedsFragment().updateOnlineCelebAdapter(onlineCelebList);
                }*/
                if (Common.getInstance().getCelebritiesTabFragment() != null) {
                    Common.getInstance().getCelebritiesTabFragment().updateOnlineCelebAdapter(onlineCelebList);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private int findDuplicateOnlineCeleb(String id, List<Celebrity> onlineCelebList) {
        for (int i = 0; i < onlineCelebList.size(); i++) {
            if (onlineCelebList.get(i).id.equals(id)) {
                return i;
            }
        }
        return -1;
    }

    //To update recent chats data
    private ArrayList<ChatDataConvertModel> recentChatDataConvertModelList;

    public void addRecentChatDataConvertModel(ChatDataConvertModel chatDataConvertModel, Boolean putItemAtFirst) {
        new addRecentChatDataConvertModel(chatDataConvertModel, putItemAtFirst).execute();
    }

    public ArrayList<ChatDataConvertModel> getRecentChatDataConvertModelList() {
        return recentChatDataConvertModelList;
    }

    public void setRecentChatDataConvertModelList(ArrayList<ChatDataConvertModel> recentChatDataConvertModelList) {
        this.recentChatDataConvertModelList = recentChatDataConvertModelList;
    }

    @SuppressLint("StaticFieldLeak")
    private class addRecentChatDataConvertModel extends AsyncTask<String, Void, Integer> {
        ChatDataConvertModel chatDataConvertModel;
        Boolean putItemAtFirst;

        public addRecentChatDataConvertModel(ChatDataConvertModel chatDataConvertModel, Boolean putItemAtFirst) {
            this.chatDataConvertModel = chatDataConvertModel;
            this.putItemAtFirst = putItemAtFirst;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... params) {
            try {
                if (recentChatDataConvertModelList != null && recentChatDataConvertModelList.size() > 0) {
                    for (int i = 0; i < recentChatDataConvertModelList.size(); i++) {
                        if (recentChatDataConvertModelList.get(i)._id.equalsIgnoreCase(chatDataConvertModel._id) && recentChatDataConvertModelList.get(i).senderId.equalsIgnoreCase(chatDataConvertModel.senderId)) {
                            return i;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return -1;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if (recentChatDataConvertModelList == null) {
                recentChatDataConvertModelList = new ArrayList<>();
            }
            if (chatDataConvertModel.message != null && !chatDataConvertModel.message.isEmpty()) {//if you don't check message empty then it will automatically add new chat in chat list fragment
                chatDataConvertModel.putThisAtFirstPosition = putItemAtFirst;
                if (result > -1) {
                    recentChatDataConvertModelList.remove((int) result);//(int) is mandatory
                }
                recentChatDataConvertModelList.add(0, chatDataConvertModel);
            }
        }
    }

    //To update fan unFan data

    private ArrayList<ChatDataConvertModel> fanUnFanChatDataConvertModelList;

    public void addFanUnFanChatDataConvertModel(ChatDataConvertModel chatDataConvertModel) {
        new addFanUnFanChatDataConvertModel(chatDataConvertModel).execute();
        if (getActivitySingleChat() != null) {
            getActivitySingleChat().updateIsFanStatus(chatDataConvertModel.isFan);
        }
    }

    public ArrayList<ChatDataConvertModel> getFanUnFanChatDataConvertModelList() {
        return fanUnFanChatDataConvertModelList;
    }

    public void setFanUnFanChatDataConvertModelList(ArrayList<ChatDataConvertModel> fanUnFanChatDataConvertModelList) {
        this.fanUnFanChatDataConvertModelList = fanUnFanChatDataConvertModelList;
    }

    @SuppressLint("StaticFieldLeak")
    private class addFanUnFanChatDataConvertModel extends AsyncTask<String, Void, Integer> {
        ChatDataConvertModel chatDataConvertModel;

        public addFanUnFanChatDataConvertModel(ChatDataConvertModel chatDataConvertModel) {
            this.chatDataConvertModel = chatDataConvertModel;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... params) {
            try {
                if (fanUnFanChatDataConvertModelList != null && fanUnFanChatDataConvertModelList.size() > 0) {
                    for (int i = 0; i < fanUnFanChatDataConvertModelList.size(); i++) {
                        if (fanUnFanChatDataConvertModelList.get(i)._id.equalsIgnoreCase(chatDataConvertModel._id)) {
                            return i;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return -1;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if (fanUnFanChatDataConvertModelList == null) {
                fanUnFanChatDataConvertModelList = new ArrayList<>();
            }
            if (result > -1) {
                fanUnFanChatDataConvertModelList.set(result, chatDataConvertModel);
            } else {
                fanUnFanChatDataConvertModelList.add(0, chatDataConvertModel);
            }
        }
    }

    //

    public void createChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel mChannel = new NotificationChannel(Common.testChannelID, Common.testChannelID, NotificationManager.IMPORTANCE_DEFAULT);
            mChannel.enableLights(true);
            mChannel.setShowBadge(true);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(mChannel);
        }
    }

    public void openChatTabsActivity(Context context, ChatDataConvertModel chatDataConvertModel) {
        /*if (SwitchProfileService.switchUserStatus(context)) {
            Common.getInstance().showSweetAlertWarning(context, "CelebKonect", context.getResources().getString(R.string.donthavepermission));
            return;
        }*/
        if (getFragmentTabsChat() == null) {
            Intent intent = new Intent(context, HelperActivity.class);
            intent.putExtra(Constants.FRAGMENT_KEY, 8100);
            intent.putExtra(Constants.FRAGMENT_TITLE, "Konect");
            intent.putExtra("chatDataConvertModel", chatDataConvertModel);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else {
            openSingleChatActivity(null, context, chatDataConvertModel);
        }
    }

    public void openSingleChatActivity(Activity activity, Context context, ChatDataConvertModel chatDataConvertModel) {
        /*if (SwitchProfileService.switchUserStatus(context)) {
            Common.getInstance().showSweetAlertWarning(context, "CelebKonect", context.getResources().getString(R.string.donthavepermission));
            return;
        }*/
        if (chatDataConvertModel == null) {
            return;
        }
        String notificationCondition = Common.getInstance().IsNullReturnValue(chatDataConvertModel.notificationCondition, "");
        if (chatDataConvertModel._id != null && !chatDataConvertModel._id.isEmpty() && !notificationCondition.equalsIgnoreCase(NotificationUtil.GroupNotification)) {
            if (Common.getInstance().getActivitySingleChat() != null) {
                Common.getInstance().getActivitySingleChat().finish();
            }
            Intent intent = new Intent(context, ActivitySingleChat.class);
            intent.putExtra("ChatDataConvertModel", chatDataConvertModel);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    public void openCallDetailsActivity(Context context, RecentCallsModel recentCallsModel) {
        Intent intent = new Intent(context, HelperActivity.class);
        intent.putExtra(Constants.FRAGMENT_KEY, 8102);
        intent.putExtra(Constants.FRAGMENT_TITLE, "Call info");
        intent.putExtra("recentCallsModel", recentCallsModel);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void openMediaDetailsFragment(String mediaType, String CelelbID, Activity activity, Feed feed, List<Media> media,
                                         int mediaPosition, Boolean isSelf, Boolean isMember, int feedPosition, String isFromWhich, Boolean showBottomLikes) {
        Intent intent = new Intent(activity, HelperActivity.class);
        intent.putExtra(Constants.FRAGMENT_TITLE, "");
        intent.putExtra(Constants.FRAGMENT_KEY, 8071);
        intent.putExtra("feed", feed);
        intent.putExtra("mediaType", mediaType);
        intent.putExtra("CelelbID", CelelbID);
        intent.putExtra("feed", feed);
        intent.putParcelableArrayListExtra("media", (ArrayList<? extends Parcelable>) media);
        intent.putExtra("mediaPosition", mediaPosition);
        intent.putExtra("isSelf", isSelf);
        intent.putExtra("isMember", isMember);
        intent.putExtra("feedPosition", feedPosition);
        intent.putExtra("isFromWhich", isFromWhich);
        intent.putExtra("showBottomLikes", showBottomLikes);
        activity.startActivityForResult(intent, Utility.generateRequestCodes().get("UPDATE_FEED_DATA"));
    }

    public void openVideoPlayer(Context context, Uri uri, String feedId, String mediaUnderScoreId, int feedPosition, int mediaPosition, boolean isFeedEdit, String isFromWhich) {
        Intent intent = new Intent(context, HelperActivity.class);
        intent.putExtra(Constants.FRAGMENT_KEY, 8080);
        intent.putExtra(Constants.FRAGMENT_TITLE, "");
        intent.putExtra("uri", uri);
        intent.putExtra("feedId", feedId);
        intent.putExtra("mediaUnderScoreId", mediaUnderScoreId);
        intent.putExtra("feedPosition", feedPosition);
        intent.putExtra("mediaPosition", mediaPosition);
        intent.putExtra("isFeedEdit", isFeedEdit);
        intent.putExtra("isFromWhich", isFromWhich);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @SuppressLint({"DefaultLocale", "SimpleDateFormat"})
    public String getDateORTime(String dateUTC) {
        Date finalDate = null, todayDate = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            finalDate = format.parse(dateUTC);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            todayDate = format.parse(DateUtil.getCurrentDateAndTimeInUTC());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (finalDate == null || todayDate == null) {
            return "NA";
        }
        //milliseconds
        long different = todayDate.getTime() - finalDate.getTime();
        /*System.out.println("startDate : " + startDate);
        System.out.println("endDate : "+ endDate);
        System.out.println("different : " + different);*/
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;
        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;
        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;
        long elapsedSeconds = different / secondsInMilli;
        //System.out.printf("%d days, %d hours, %d minutes, %d seconds%n",elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);
        //String.format("%d months", elapsedDays/30)
        Calendar yesterdayCalendar = Calendar.getInstance(); // today
        yesterdayCalendar.add(Calendar.DAY_OF_YEAR, -1); // yesterday
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(finalDate);
        Boolean isYesterday = false;
        if (yesterdayCalendar.get(Calendar.YEAR) == endCalendar.get(Calendar.YEAR) && yesterdayCalendar.get(Calendar.MONTH) == endCalendar.get(Calendar.MONTH) && yesterdayCalendar.get(Calendar.DAY_OF_YEAR) == endCalendar.get(Calendar.DAY_OF_YEAR)) {
            isYesterday = true;
        }
        if (isYesterday) {
            return "Yesterday";
        } else if (elapsedDays > 0) {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            return simpleDateFormat.format(finalDate);
        } else {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
            return simpleDateFormat.format(finalDate);
        }
    }

    @SuppressLint({"DefaultLocale", "SimpleDateFormat"})
    public String getDateANDTime(String dateUTC) {
        Date finalDate = null, todayDate = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            finalDate = format.parse(dateUTC);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            todayDate = format.parse(DateUtil.getCurrentDateAndTimeInUTC());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (finalDate == null || todayDate == null) {
            return "NA";
        }
        //milliseconds
        long different = todayDate.getTime() - finalDate.getTime();
        /*System.out.println("startDate : " + startDate);
        System.out.println("endDate : "+ endDate);
        System.out.println("different : " + different);*/
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;
        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;
        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;
        long elapsedSeconds = different / secondsInMilli;
        //System.out.printf("%d days, %d hours, %d minutes, %d seconds%n",elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);
        //String.format("%d months", elapsedDays/30)
        Calendar yesterdayCalendar = Calendar.getInstance(); // today
        yesterdayCalendar.add(Calendar.DAY_OF_YEAR, -1); // yesterday
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(finalDate);
        Boolean isYesterday = false;
        if (yesterdayCalendar.get(Calendar.YEAR) == endCalendar.get(Calendar.YEAR) && yesterdayCalendar.get(Calendar.MONTH) == endCalendar.get(Calendar.MONTH) && yesterdayCalendar.get(Calendar.DAY_OF_YEAR) == endCalendar.get(Calendar.DAY_OF_YEAR)) {
            isYesterday = true;
        }
        SimpleDateFormat formatTime = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat formatDate = new SimpleDateFormat("dd MMM yyyy");
        if (isYesterday) {
            return "Yesterday" + ", " + formatTime.format(finalDate);
        } else if (elapsedDays > 0) {
            return formatDate.format(finalDate) + ", " + formatTime.format(finalDate);
        } else if (elapsedDays == 0) {
            return "Today, " + formatTime.format(finalDate);
        } else {
            return formatTime.format(finalDate);
        }
    }

    @SuppressLint({"DefaultLocale", "SimpleDateFormat"})
    public String getDate(String dateUTC) {
        Date finalDate = null, todayDate = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            finalDate = format.parse(dateUTC);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            todayDate = format.parse(DateUtil.getCurrentDateAndTimeInUTC());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (finalDate == null || todayDate == null) {
            return "NA";
        }
        //milliseconds
        long different = todayDate.getTime() - finalDate.getTime();
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
        long elapsedDays = different / daysInMilli;
        //
        Calendar yesterdayCalendar = Calendar.getInstance(); // today
        yesterdayCalendar.add(Calendar.DAY_OF_YEAR, -1); // yesterday
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(finalDate);
        Boolean isYesterday = false;
        if (yesterdayCalendar.get(Calendar.YEAR) == endCalendar.get(Calendar.YEAR) && yesterdayCalendar.get(Calendar.MONTH) == endCalendar.get(Calendar.MONTH) && yesterdayCalendar.get(Calendar.DAY_OF_YEAR) == endCalendar.get(Calendar.DAY_OF_YEAR)) {
            isYesterday = true;
        }
        SimpleDateFormat formatDate = new SimpleDateFormat("dd MMM yyyy");
        if (isYesterday) {
            return "Yesterday";
        } else if (elapsedDays == 0) {
            return "Today";
        } else {
            return formatDate.format(finalDate);
        }
    }

    @SuppressLint("DefaultLocale")
    public String get12HrsTime(String dateUTC) {
        Date finalDate = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            finalDate = format.parse(dateUTC);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (finalDate == null) {
            return "NA";
        }
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        return simpleDateFormat.format(finalDate);
    }

    @SuppressLint("DefaultLocale")
    public String getDateSection(String dateUTC) {
        Date finalDate = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            finalDate = format.parse(dateUTC);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (finalDate == null) {
            return "NA";
        }
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
        return simpleDateFormat.format(finalDate);
    }

    @SuppressLint("DefaultLocale")
    public String getDateSection(String dateUTC, String dateFormat) {
        Date finalDate = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            finalDate = format.parse(dateUTC);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (finalDate == null) {
            return "NA";
        }
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        return simpleDateFormat.format(finalDate);
    }


    public void getFanCredits(Context context, Boolean isFanRequest, FanUnFanData fanUnFanData, IApiListener iApiListener) {
        try {
            String serviceType = "fan";
            if (isFanRequest) {
                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                IApiListener iApiListenerLocal = new IApiListener() {
                    @Override
                    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                        if (condition.equals(Constants.CONTRACTS_FOR_CELEB)) {
                            ArrayList<ContractsSuccess> contractsSuccess;
                            try {
                                Type type = new TypeToken<ArrayList<ContractsSuccess>>() {
                                }.getType();
                                contractsSuccess = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                                if (contractsSuccess.size() != 0 && contractsSuccess.get(0).getServiceType() != null && contractsSuccess.get(0).getServiceType().equals(serviceType)) {
                                    fanUnFanData.serviceCredits = contractsSuccess.get(0).getServiceCredits();
                                    Common.getInstance().fanUnFanConfirmationDialog(isFanRequest, fanUnFanData, iApiListener);
                                } else {
                                    Common.getInstance().showSweetAlertWarning(context, "CelebKonect", context.getResources().getString(R.string.no_contracts_avail));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
                        Common.getInstance().cusToast(context, Constants.SOMETHING_WENT_WRONG);
                    }
                };
                Call<ApiResponseModel> call = apiInterface.getBlockStatus(ApiClient.GET_CONTRACTS + fanUnFanData.celebId + "/" + serviceType);
                callAPI(new ApiRequestModel().setModel(context, call, Constants.CONTRACTS_FOR_CELEB, true, iApiListenerLocal, true));
            } else {
                Common.getInstance().fanUnFanConfirmationDialog(isFanRequest, fanUnFanData, iApiListener);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SetTextI18n")
    public void fanUnFanConfirmationDialog(Boolean isFanRequest, FanUnFanData fanUnFanData, IApiListener iApiListener) {
        try {
            if (fanUnFanData.serviceCredits == null) {
                Common.getInstance().cusToast(Utility.getContext(), "This celebrity doesn't have any contracts");
                return;
            }
            //
            final Dialog fancreditsshoDailog;
            TextView take_photo_txt;
            Button yesBtn, noBtn;
            fancreditsshoDailog = new Dialog(fanUnFanData.context);
            fancreditsshoDailog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            fancreditsshoDailog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            fancreditsshoDailog.setCancelable(false);
            fancreditsshoDailog.setContentView(R.layout.conformation_popup);
            TextView fan_profile_role = (TextView) fancreditsshoDailog.findViewById(R.id.fan_profile_role);
            TextView mCeleb_Name = (TextView) fancreditsshoDailog.findViewById(R.id.fan_profile_name);
            CircleImageView profile_pic = (CircleImageView) fancreditsshoDailog.findViewById(R.id.fan_profile_pic);
            noBtn = (Button) fancreditsshoDailog.findViewById(R.id.noBtn);
            yesBtn = (Button) fancreditsshoDailog.findViewById(R.id.yesBtn);
            TextView avail_creditstxt = (TextView) fancreditsshoDailog.findViewById(R.id.avail_creditstxt);
            //
            take_photo_txt = (TextView) fancreditsshoDailog.findViewById(R.id.take_photo_txt);
            if (isFanRequest) {
                take_photo_txt.setText("You will be charged with " + fanUnFanData.serviceCredits + " Credit(S) to become a fan." +
                        " Please 'Confirm' to proceed.");
                Double availableCredits = SessionManager.userLogin.mainCredits;// Common.getInstance().getAvailableBalance();
                //If Main credits 0 Checking for Refferal Credits for Fan (10-14-19)
                /*if (availableCredits <= 0) {
                    availableCredits = SessionManager.userLogin.referralCredits; //Common.getInstance().getRefferalCreditsBalance();
                }*/
                if (availableCredits <= 0 || fanUnFanData.serviceCredits > availableCredits) {
                    yesBtn.setText("Buy Credits");
                    if (fanUnFanData.serviceCredits == 0.0) {
                        yesBtn.setText("Confirm");
                    }
                } else {
                    yesBtn.setText("Confirm");
                }
                noBtn.setText("Cancel");
            } else {
                take_photo_txt.setText("Are you sure, you want to cancel the fan subscription for " + fanUnFanData.firstName + "?");
                yesBtn.setText("Yes");
                noBtn.setText("No");
            }
            if (fanUnFanData.firstName != null && !fanUnFanData.firstName.isEmpty()) {
                mCeleb_Name.setText(convertFirstLetterToCapital(fanUnFanData.firstName.trim()) + " " + fanUnFanData.lastName);
            } else {
                mCeleb_Name.setText("");
            }
            if (fanUnFanData.profession != null && !fanUnFanData.profession.isEmpty()) {
                fan_profile_role.setText(Character.toUpperCase(fanUnFanData.profession.charAt(0))
                        + fanUnFanData.profession.substring(1));
            } else {
                fan_profile_role.setText("");
            }
            if (fanUnFanData.avtar_imgPath != null && !fanUnFanData.avtar_imgPath.isEmpty()) {
                setGlideImage(fanUnFanData.context, ApiClient.BASE_URL + fanUnFanData.avtar_imgPath, profile_pic);
            } else {
                profile_pic.setImageResource(R.drawable.appiconandroid);
            }
            avail_creditstxt.setVisibility(View.VISIBLE);
            if (SessionManager.userLogin.mainCredits != null) {
                DecimalFormat twoDecimal = new DecimalFormat("0.00");
                double credit_double = SessionManager.userLogin.mainCredits;
//                avail_creditstxt.setText("Available Credits : " + twoDecimal.format(credit_double));
//                avail_creditstxt.setText("Available Credits : " + twoDecimal.format(credit_double));

                avail_creditstxt.setText(Common.getInstance().getCreditBalancetoShowInLabel());
                //
            } else {
                avail_creditstxt.setVisibility(View.GONE);
            }
            yesBtn.setOnClickListener(v -> {
                fancreditsshoDailog.dismiss();
                if (isFanRequest) {
                    double credits = SessionManager.userLogin.mainCredits;

                    //If Main credits 0 Checking for Refferal Credits for Fan (10-14-19)
                    if (credits <= 0) {
                        credits = SessionManager.userLogin.referralCredits; // Common.getInstance().getRefferalCreditsBalance();
                    }

                    if (credits >= fanUnFanData.serviceCredits) {
                        FanUnFanPostParams fanUnFanPostParams = new FanUnFanPostParams(fanUnFanData.firstName
                                + " " + fanUnFanData.lastName, "Fan", fanUnFanData.serviceCredits,
                                fanUnFanData.celebId, "debit", isLoginId());
                        fanCallAPI(fanUnFanData.context, fanUnFanPostParams, iApiListener);
                    } else {
                        Intent intent = new Intent(fanUnFanData.context, HelperActivity.class);
                        intent.putExtra("refCartId", fanUnFanData.celebId);
                        intent.putExtra(Constants.FRAGMENT_TITLE, "Credit Recharge");
                        intent.putExtra("className", "UserBookAppointment");
                        intent.putExtra(Constants.FRAGMENT_KEY, 8034);
                        fanUnFanData.context.startActivity(intent);
                    }
                } else {
                    feedBackDialog(fanUnFanData, iApiListener);
                }
            });
            noBtn.setOnClickListener(v -> fancreditsshoDailog.dismiss());
            fancreditsshoDailog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void feedBackDialog(FanUnFanData fanUnFanData, IApiListener iApiListener) {
        try {
            final Dialog promoDialog = new Dialog(fanUnFanData.context);
            promoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            promoDialog.setCancelable(false);
            promoDialog.setContentView(R.layout.feed_back_dialog);
            LinearLayout linear_layout_submit = (LinearLayout) promoDialog.findViewById(R.id.linear_layout_submit);
            TextView textTitle = (TextView) promoDialog.findViewById(R.id.textTitle);
            textTitle.setText("Reason to UnFan");
            TextView textSubTitle = (TextView) promoDialog.findViewById(R.id.textSubTitle);
//            textSubTitle.setText("");
            textSubTitle.setVisibility(View.GONE);
            ImageView close_dialog = (ImageView) promoDialog.findViewById(R.id.close_dialog);
            Spinner spinner = (Spinner) promoDialog.findViewById(R.id.spinner);
            EditText editText_reason = (EditText) promoDialog.findViewById(R.id.editText_reason);
            editText_reason.setVisibility(View.GONE);
            List<String> reasonsList;
            reasonsList = new ArrayList<String>();
            reasonsList.add("Select Reason");
            reasonsList.add(fanUnFanData.context.getResources().getString(R.string.not_intrest));
            reasonsList.add(fanUnFanData.context.getResources().getString(R.string.celebrude));
            reasonsList.add("Other");
            //
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(fanUnFanData.context, android.R.layout.simple_spinner_item, reasonsList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);
            //

            close_dialog.setVisibility(View.VISIBLE);
            close_dialog.setOnClickListener(v -> {
                promoDialog.dismiss();
//                unFanCallAPI(fanUnFanData, String.valueOf(spinner.getSelectedItem()), iApiListener);
            });
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("other")) {
                        editText_reason.setVisibility(View.VISIBLE);
                    } else {
                        editText_reason.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            linear_layout_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (String.valueOf(spinner.getSelectedItem()).equalsIgnoreCase(reasonsList.get(0))) {
                        cusToast(fanUnFanData.context, "Please select reason");
                    } else if (String.valueOf(spinner.getSelectedItem()).equalsIgnoreCase("Other")) {
                        if (editText_reason.getText().toString().isEmpty()) {
                            editText_reason.setError("Write your reason");
                        } else {
                            promoDialog.dismiss();
                            unFanCallAPI(fanUnFanData, editText_reason.getText().toString(), iApiListener);
                        }
                    } else {
                        promoDialog.dismiss();
                        unFanCallAPI(fanUnFanData, reasonsList.get(1), iApiListener);
                    }
                }
            });
            promoDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fanCallAPI(Context context, FanUnFanPostParams fanUnFanPostParams, IApiListener iApiListener) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.CREATE_CREDITS(fanUnFanPostParams);
        IApiListener apiListener = new IApiListener() {
            @Override
            public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                if (iApiListener != null) {
                    iApiListener.apiSuccessResponse(condition, apiResponseModel);
                }
                try {
                    Type type = new TypeToken<FanSuccessData>() {
                    }.getType();
                    FanSuccessData fanSuccessData = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                    if (fanSuccessData != null) {
                        becomeFanSuccess(context, fanSuccessData, fanUnFanPostParams.CelebrityId);
                    }

                    //If required will uncomment
                    /*CategoryCelebsFragment categoryCelebsFragment = CategoryCelebsFragment.getInstance();
                    if (categoryCelebsFragment != null) {
                        categoryCelebsFragment.updateAdapterFanStatus(true);
                    }
                    CelebrityBaseFragment celebrityBaseFragment = CelebrityBaseFragment.getInstance();
                    if (celebrityBaseFragment != null) {
                        celebrityBaseFragment.updateFanFollowStatus(true, null,fanUnFanPostParams.CelebrityId);
                    }*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
                if (iApiListener != null) {
                    iApiListener.apiErrorResponse(condition, enumConstants, apiResponseModel);
                }
            }
        };
        callAPI(new ApiRequestModel().setModel(context, call, Constants.FAN_STATUS, true, apiListener, true));
    }

    public void unFanCallAPI(FanUnFanData fanUnFanData, String remarkReason, IApiListener iApiListener) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        JSONObject params = new JSONObject();
        try {
            params.put("userId", SessionManager.userLogin.userId);
            params.put("CelebrityId", fanUnFanData.celebId);
            params.put("reason", remarkReason);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), params.toString());
        Call<ApiResponseModel> call = apiInterface.PUT(ApiClient.UNFAN_CELEB +
                SessionManager.userLogin.userId, requestBody);
        IApiListener apiListener = new IApiListener() {
            @Override
            public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                if (iApiListener != null) {
                    iApiListener.apiSuccessResponse(condition, apiResponseModel);
                }
                try {
                    Common.getInstance().showSweetAlertSuccess(fanUnFanData.context, "CelebKonect",
                            fanUnFanData.context.getResources().getString(R.string.unfan_status_message)
                                    + "\n" + fanUnFanData.firstName + " " + fanUnFanData.lastName);

                    FansOfFragment fansOfFragment = FansOfFragment.getInstance();
                    if (fansOfFragment != null) {
                        fansOfFragment.deleteListItem();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
                if (iApiListener != null) {
                    iApiListener.apiErrorResponse(condition, enumConstants, apiResponseModel);
                }
                Toast.makeText(fanUnFanData.context, "please check your network and try again", Toast.LENGTH_SHORT).show();
            }
        };
        callAPI(new ApiRequestModel().setModel(fanUnFanData.context, call, Constants.UN_FAN_STATUS,
                true, apiListener, true));
    }

    public void followUnFollow(Context context, Boolean isFollowRequest, String celebId, Boolean showProgress, IApiListener iApiListener) {
        try {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            JSONObject params = new JSONObject();
            try {
                params.put("userId", SessionManager.userLogin.userId);
                params.put("CelebrityId", celebId);
                params.put("isFollower", isFollowRequest);
                params.put("notificationType", Constants.NOTIFICATION_ACTIVITY_FOLLOW);
                params.put("createdBy", SessionManager.userLogin.userName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), params.toString());
            if (isFollowRequest) {
                Call<ApiResponseModel> call = apiInterface.PUT(ApiClient.FOLLOW_CELEB + SessionManager.userLogin.userId, requestBody);
                callAPI(new ApiRequestModel().setModel(context, call, ApiClient.FOLLOW_CELEB, showProgress, iApiListener, true));
            } else {
                Call<ApiResponseModel> call = apiInterface.PUT(ApiClient.UNFOLLOW_CELEB + SessionManager.userLogin.userId, requestBody);
                callAPI(new ApiRequestModel().setModel(context, call, ApiClient.UNFOLLOW_CELEB, showProgress, iApiListener, true));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkBlockStatus(Context context, String celebId, IApiListener iApiListener) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.getBlockStatus(ApiClient.GET_CREDIT_BALANCE_BLOCK
                + SessionManager.userLogin.userId + "/" + celebId);
        callAPI(new ApiRequestModel().setModel(context, call, Constants.BLOCK_STATUS,
                true, iApiListener, true));
    }


    public void viewCelebProfileDialog(Context context, ProfileDataModel profileDataModel,
                                       ProgressDialog progressDialog) {
        if (profileDataModel.isCeleb == null || !profileDataModel.isCeleb) {
            navigatingToProfile(context, profileDataModel);
            return;
        }
        navigatingToProfile(context, profileDataModel);
        /*try {
            if (dialogCelebProfile != null && dialogCelebProfile.isShowing()) {
                dialogCelebProfile.dismiss();
                dialogCelebProfile = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            dialogCelebProfile = new Dialog(context, R.style.PauseDialog);
            dialogCelebProfile.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogCelebProfile.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogCelebProfile.setContentView(R.layout.profile_popup_action_items);
            dialogCelebProfile.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            Button yesBtn, noBtn;
            TextView take_photo_txt, textViewProf, textViewFullProfile, mCeleb_Name;
            ImageView profileIcon, messageIcon, callIcon, videoCallIcon, imageViewProfilePic, close_popup;
            noBtn = dialogCelebProfile.findViewById(R.id.profile_follow);
            yesBtn = dialogCelebProfile.findViewById(R.id.profile_view);
            take_photo_txt = dialogCelebProfile.findViewById(R.id.profile_role);
            mCeleb_Name = dialogCelebProfile.findViewById(R.id.profile_name);
            close_popup = dialogCelebProfile.findViewById(R.id.close_popup);
            textViewFullProfile = dialogCelebProfile.findViewById(R.id.textViewFullProfile);
            textViewProf = dialogCelebProfile.findViewById(R.id.textViewProf);
            profileIcon = dialogCelebProfile.findViewById(R.id.profileIcon);
            messageIcon = dialogCelebProfile.findViewById(R.id.messageIcon);
            callIcon = dialogCelebProfile.findViewById(R.id.callIcon);

            videoCallIcon = dialogCelebProfile.findViewById(R.id.videoCallIcon);
            imageViewProfilePic = dialogCelebProfile.findViewById(R.id.imageViewProfilePic);
            //
            if (profileDataModel.firstName != null && !profileDataModel.firstName.isEmpty()) {
                mCeleb_Name.setText(convertFirstLetterToCapital(profileDataModel.firstName) + " " + IsNullReturnValue(profileDataModel.lastName, ""));
            } else {
                mCeleb_Name.setText("");
            }

            if (profileDataModel.profession != null && !profileDataModel.profession.isEmpty()) {
                textViewProf.setText(profileDataModel.profession);

                textViewProf.setBackgroundColor(context.getResources().getColor(R.color.skyblueNew));
            } else {
                textViewProf.setBackgroundColor(context.getResources().getColor(R.color.transparent));
            }
            take_photo_txt.setText(profileDataModel.role);
            if (profileDataModel.avtar_imgPath != null && !profileDataModel.avtar_imgPath.isEmpty()) {
                setGlideImage(context, ApiClient.BASE_URL + profileDataModel.avtar_imgPath, imageViewProfilePic);
            } else {
                imageViewProfilePic.setImageResource(R.drawable.ic_grey_celebkonect_logo);
            }

            if (!dialogCelebProfile.isShowing()) {
                dialogCelebProfile.show();
            }

            profileIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogCelebProfile.dismiss();
                    navigatingToProfile(context, profileDataModel);
                }
            });
            textViewFullProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogCelebProfile.dismiss();
                    navigatingToProfile(context, profileDataModel);
                }
            });
            messageIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogCelebProfile.dismiss();
                    //
                    ChatDataConvertModel chatDataConvertModel = new ChatDataConvertModel();
                    try {
                        chatDataConvertModel._id = profileDataModel._id;
                        chatDataConvertModel.firstName = profileDataModel.firstName;
                        chatDataConvertModel.lastName = profileDataModel.lastName;
                        chatDataConvertModel.avtar_imgPath = profileDataModel.avtar_imgPath;
                        chatDataConvertModel.isCeleb = profileDataModel.isCeleb;
                        chatDataConvertModel.isFan = profileDataModel.isFan;
                        chatDataConvertModel.message = "";
                        chatDataConvertModel.receiverId = profileDataModel._id;
                        chatDataConvertModel.senderId = Common.getInstance().IsNullReturnValue(SessionManager.userLogin.userId, "");
                        chatDataConvertModel.createdAt = "";
                        chatDataConvertModel.counter = 0;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    openChatTabsActivity(context, chatDataConvertModel);
                    if (Common.getInstance().getFragmentNewChatList() != null) {
                        Common.getInstance().getFragmentNewChatList().activity().finish();
                    }
                }
            });
            callIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AudioVideoApiCalls.getInstance().checkCelebAvaialabiltyForCall(context, profileDataModel._id, Constants.AUDIO_CALL);
                }
            });
            videoCallIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AudioVideoApiCalls.getInstance().checkCelebAvaialabiltyForCall(context, profileDataModel._id, Constants.VIDEO_CALL);
                }
            });
            yesBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogCelebProfile.dismiss();
                    navigatingToProfile(context, profileDataModel);
                }
            });
            noBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogCelebProfile.dismiss();
                }
            });
            close_popup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogCelebProfile.dismiss();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    public void navigatingToProfile(Context context, ProfileDataModel profileDataModel) {
        try {
            if (profileDataModel.firstName != null && !profileDataModel.firstName.isEmpty()) {
                Intent profileIntent = new Intent(context, HelperActivity.class);
                profileIntent.putExtra(Constants.FRAGMENT_KEY, 8029);
                profileIntent.putExtra(Constants.FRAGMENT_TITLE, "Celeb Profile");
                profileIntent.putExtra("ClASS_NAME", "OFFLINE");
                profileIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (profileDataModel._id != null && !profileDataModel._id.isEmpty()) {
                    profileIntent.putExtra("CelebId", profileDataModel._id);
                } else {
                    profileIntent.putExtra("CelebId", "");
                }
                if (profileDataModel.isOnline != null && profileDataModel.isOnline) {
                    profileIntent.putExtra("celeb_status", "online");
                } else {
                    profileIntent.putExtra("celeb_status", "offline");
                }
                if (profileDataModel.firstName != null && !profileDataModel.firstName.isEmpty()) {
                    profileIntent.putExtra("PROFILE_NAME", profileDataModel.firstName + " " + profileDataModel.lastName);
                } else {
                    profileIntent.putExtra("PROFILE_NAME", "null");
                }
                if (profileDataModel.avtar_imgPath != null && !profileDataModel.avtar_imgPath.isEmpty()) {
                    profileIntent.putExtra("Imaage", ApiClient.BASE_URL + profileDataModel.avtar_imgPath);
                } else {
                    profileIntent.putExtra("Imaage", "");
                }
                if (profileDataModel.profession != null && !profileDataModel.profession.isEmpty()) {
                    profileIntent.putExtra("proffession", profileDataModel.profession);
                } else {
                    profileIntent.putExtra("proffession", "");
                }
                if (profileDataModel.aboutMe != null) {
                    profileIntent.putExtra("ABOUT", profileDataModel.aboutMe);
                } else {

                }
                if (profileDataModel.isCeleb != null && !profileDataModel.isCeleb) {
                    profileIntent.putExtra("isUserProfile", true);
                }
                context.startActivity(profileIntent);
            } else {
                cusToast(context, "Profile Data Not Available");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Object> getFilteredListOfSearchObject(List<?> orgList, String searchString, Integer conditionValue) {
        try {
            //Condition values
            //0 - CelebProfileCompleteData
            //1 - ChatDataConvertModel
            //2 - RecentCallsModel
            //3 - CountryData
            List<Object> filteredList = new ArrayList<>();
            ArrayList<Integer> filteredIndexes = new ArrayList<>();
            for (int i = 0; i < orgList.size(); i++) {
                String firstName = "";
                if (conditionValue.equals(0)) {
                    firstName = Common.getInstance().IsNullReturnValue(((CelebProfileCompleteData) orgList.get(i)).getFirstName(), "");
                } else if (conditionValue.equals(1)) {
                    firstName = Common.getInstance().IsNullReturnValue(((ChatDataConvertModel) orgList.get(i)).firstName, "");
                } else if (conditionValue.equals(2)) {
                    firstName = Common.getInstance().IsNullReturnValue(getChatSenderReceiverInfo((RecentCallsModel) orgList.get(i)).firstName, "");
                } else if (conditionValue.equals(3)) {
                    firstName = Common.getInstance().IsNullReturnValue(((CountryData) orgList.get(i)).getCountryName(), "");
                }
                if (firstName.toLowerCase().startsWith(searchString.toLowerCase()) && filteredIndexes.indexOf(i) <= -1) {
                    filteredIndexes.add(i);
                    filteredList.add(orgList.get(i));
                }
            }
            for (int i = 0; i < orgList.size(); i++) {
                String firstName = "";
                if (conditionValue.equals(0)) {
                    firstName = Common.getInstance().IsNullReturnValue(((CelebProfileCompleteData) orgList.get(i)).getFirstName(), "");
                } else if (conditionValue.equals(1)) {
                    firstName = Common.getInstance().IsNullReturnValue(((ChatDataConvertModel) orgList.get(i)).firstName, "");
                } else if (conditionValue.equals(2)) {
                    firstName = Common.getInstance().IsNullReturnValue(getChatSenderReceiverInfo((RecentCallsModel) orgList.get(i)).firstName, "");
                } else if (conditionValue.equals(3)) {
                    firstName = Common.getInstance().IsNullReturnValue(((CountryData) orgList.get(i)).getCountryName(), "");
                }
                if (firstName.contains(" ")) {
                    String[] strings = firstName.split(" ");
                    if (strings.length > 0) {
                        for (String string : strings) {
                            if (string.toLowerCase().startsWith(searchString.toLowerCase()) && filteredIndexes.indexOf(i) <= -1) {
                                filteredIndexes.add(i);
                                filteredList.add(orgList.get(i));
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < orgList.size(); i++) {
                String lastName = "";
                if (conditionValue.equals(0)) {
                    lastName = Common.getInstance().IsNullReturnValue(((CelebProfileCompleteData) orgList.get(i)).getLastName(), "");
                } else if (conditionValue.equals(1)) {
                    lastName = Common.getInstance().IsNullReturnValue(((ChatDataConvertModel) orgList.get(i)).lastName, "");
                } else if (conditionValue.equals(2)) {
                    lastName = Common.getInstance().IsNullReturnValue(getChatSenderReceiverInfo((RecentCallsModel) orgList.get(i)).lastName, "");
                } else if (conditionValue.equals(3)) {
                    lastName = Common.getInstance().IsNullReturnValue(((CountryData) orgList.get(i)).getCountryName(), "");
                }
                if (lastName.toLowerCase().startsWith(searchString.toLowerCase()) && filteredIndexes.indexOf(i) <= -1) {
                    filteredIndexes.add(i);
                    filteredList.add(orgList.get(i));
                }
            }
            for (int i = 0; i < orgList.size(); i++) {
                String lastName = "";
                if (conditionValue.equals(0)) {
                    lastName = Common.getInstance().IsNullReturnValue(((CelebProfileCompleteData) orgList.get(i)).getLastName(), "");
                } else if (conditionValue.equals(1)) {
                    lastName = Common.getInstance().IsNullReturnValue(((ChatDataConvertModel) orgList.get(i)).lastName, "");
                } else if (conditionValue.equals(2)) {
                    lastName = Common.getInstance().IsNullReturnValue(getChatSenderReceiverInfo((RecentCallsModel) orgList.get(i)).lastName, "");
                } else if (conditionValue.equals(3)) {
                    lastName = Common.getInstance().IsNullReturnValue(((CountryData) orgList.get(i)).getCountryName(), "");
                }
                if (lastName.contains(" ")) {
                    String[] strings = lastName.split(" ");
                    if (strings.length > 0) {
                        for (String string : strings) {
                            if (string.toLowerCase().startsWith(searchString.toLowerCase()) && filteredIndexes.indexOf(i) <= -1) {
                                filteredIndexes.add(i);
                                filteredList.add(orgList.get(i));
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < orgList.size(); i++) {
                String value = "";
                if (conditionValue.equals(0)) {
                    value = Common.getInstance().IsNullReturnValue(((CelebProfileCompleteData) orgList.get(i)).getFirstName(), "") + " " + Common.getInstance().IsNullReturnValue(((CelebProfileCompleteData) orgList.get(i)).getLastName(), "");
                } else if (conditionValue.equals(1)) {
                    value = Common.getInstance().IsNullReturnValue(((ChatDataConvertModel) orgList.get(i)).firstName, "") + " " + Common.getInstance().IsNullReturnValue(((ChatDataConvertModel) orgList.get(i)).lastName, "");
                } else if (conditionValue.equals(2)) {
                    value = Common.getInstance().IsNullReturnValue(getChatSenderReceiverInfo((RecentCallsModel) orgList.get(i)).firstName, "") + " " + Common.getInstance().IsNullReturnValue(getChatSenderReceiverInfo((RecentCallsModel) orgList.get(i)).lastName, "");
                } else if (conditionValue.equals(3)) {
                    value = Common.getInstance().IsNullReturnValue(((CountryData) orgList.get(i)).getCountryName(), "");
                }
                if (value.toLowerCase().startsWith(searchString.toLowerCase()) && filteredIndexes.indexOf(i) <= -1) {
                    filteredIndexes.add(i);
                    filteredList.add(orgList.get(i));
                }
            }
            return filteredList;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private ChatSenderReceiverInfo getChatSenderReceiverInfo(RecentCallsModel recentCallsModel) {
        ChatSenderReceiverInfo chatSenderReceiverInfo;
        String userMemberId = Common.getInstance().IsNullReturnValue(SessionManager.userLogin.userId, "");
        if (userMemberId.equalsIgnoreCase(recentCallsModel.lastCallStatus.receiverId._id)) {
            chatSenderReceiverInfo = recentCallsModel.lastCallStatus.senderId;
        } else {
            chatSenderReceiverInfo = recentCallsModel.lastCallStatus.receiverId;
        }
        return chatSenderReceiverInfo;
    }

    @SuppressLint("SetTextI18n")
    public void setFeedLikeUnLike(FeedViewHolder feedViewHolder, Feed feed, boolean showLikeAnimation) {
        try {
            feedViewHolder.imgLike.setImageResource(Common.getInstance().getBooleanFromInt(feed.isLike) ? R.drawable.ic_like_fill : R.drawable.ic_like_stroke);
            //feedViewHolder.progressBar.setVisibility(feed.isBusyLike ? View.VISIBLE : View.GONE);
            //feedViewHolder.imgLike.setVisibility(feed.isBusyLike ? View.GONE : View.VISIBLE);
            feedViewHolder.llLikeComment.setVisibility(View.VISIBLE);
            feedViewHolder.tvDot.setVisibility(View.GONE);
            /*if(Common.getInstance().getBooleanFromInt(feed.isLike) && showLikeAnimation){
                feedViewHolder.imgLike.setImageResource(R.color.transparent);
                setLikeAnimation(feedViewHolder.ivLikeAnimation);
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        feedViewHolder.ivLikeAnimation.setVisibility(View.GONE);
                        feedViewHolder.imgLike.setImageResource(Common.getInstance().getBooleanFromInt(feed.isLike) ? R.drawable.ic_like_fill : R.drawable.ic_like_stroke);
                    }
                }, 1400);
            }*/
            // feedViewHolder.imgLike.setColorFilter(ContextCompat.getColor(context, feed.isLike ? R.color.skyblueNew : R.color.hash_text_color));
            if (feed.numberOfLikes > 0) {
                if (feed.numberOfLikes == 1) {
                    feedViewHolder.tvLikeCount.setText(Common.getInstance().coolFormat(feed.numberOfLikes, 0) + " like");
                } else {
                    feedViewHolder.tvLikeCount.setText(Common.getInstance().coolFormat(feed.numberOfLikes, 0) + " likes");
                }
                feedViewHolder.tvLikeCount.setVisibility(View.VISIBLE);
            } else {
                feedViewHolder.tvLikeCount.setVisibility(View.GONE);
                feedViewHolder.tvLikeCount.setText("");
            }
            if (feed.numberOfComments > 0) {
                if (feed.numberOfComments == 1) {
                    feedViewHolder.tvCommentCount.setText(Common.getInstance().coolFormat(feed.numberOfComments, 0) + " comment");
                } else {
                    feedViewHolder.tvCommentCount.setText(Common.getInstance().coolFormat(feed.numberOfComments, 0) + " comments");
                }
                feedViewHolder.tvCommentCount.setVisibility(View.VISIBLE);
            } else {
                feedViewHolder.tvCommentCount.setVisibility(View.VISIBLE);
                feedViewHolder.tvCommentCount.setText("");
            }
            if (feed.numberOfLikes <= 0 && feed.numberOfComments <= 0) {
                feedViewHolder.llLikeComment.setVisibility(View.GONE);
            } else if (feed.numberOfLikes > 0 && feed.numberOfComments > 0) {
                feedViewHolder.tvDot.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setLikeAnimation(SimpleDraweeView ivLikeAnimation) {
        try {
            PipelineDraweeControllerBuilder pipelineDraweeControllerBuilder = Fresco.newDraweeControllerBuilder();
            Uri uri = new Uri.Builder()
                    .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                    .path(String.valueOf(R.drawable.like_animation_gif))
                    .build();
            pipelineDraweeControllerBuilder.setImageRequest(ImageRequest.fromUri(uri));
            pipelineDraweeControllerBuilder.setOldController(ivLikeAnimation.getController()).setAutoPlayAnimations(true);
            pipelineDraweeControllerBuilder.setControllerListener(new BaseControllerListener<ImageInfo>() {
                @Override
                public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                    super.onFinalImageSet(id, imageInfo, animatable);
                }
            });
            ivLikeAnimation.setController(pipelineDraweeControllerBuilder.build());
            ivLikeAnimation.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SetTextI18n")
    public void setFeedMediaLikeUnLike(Feed feed, int mediaPosition, LinearLayout llLikeComment, TextView tvDot, TextView tvLikeCount, TextView tvCommentCount) {
        try {
            llLikeComment.setVisibility(View.VISIBLE);
            tvDot.setVisibility(View.GONE);
            boolean isSingleMediaFeed = feed.mediaList.size() == 1;
            int numberOfLikes, numberOfComments;
            if (isSingleMediaFeed) {
                numberOfLikes = feed.numberOfLikes;
                numberOfComments = feed.numberOfComments;
            } else {
                numberOfLikes = feed.mediaList.get(mediaPosition).numberOfLikes;
                numberOfComments = feed.mediaList.get(mediaPosition).numberOfComments;
            }
            if (numberOfLikes > 0) {
                if (numberOfLikes == 1) {
                    tvLikeCount.setText(Common.getInstance().coolFormat(numberOfLikes, 0) + " like");
                } else {
                    tvLikeCount.setText(Common.getInstance().coolFormat(numberOfLikes, 0) + " likes");
                }
                tvLikeCount.setVisibility(View.VISIBLE);
            } else {
                tvLikeCount.setVisibility(View.GONE);
                tvLikeCount.setText("");
            }
            if (numberOfComments > 0) {
                if (numberOfComments == 1) {
                    tvCommentCount.setText(Common.getInstance().coolFormat(numberOfComments, 0) + " comment");
                } else {
                    tvCommentCount.setText(Common.getInstance().coolFormat(numberOfComments, 0) + " comments");
                }
                tvCommentCount.setVisibility(View.VISIBLE);
            } else {
                tvCommentCount.setVisibility(View.VISIBLE);
                tvCommentCount.setText("");
            }
            if (numberOfLikes <= 0 && numberOfComments <= 0) {
                llLikeComment.setVisibility(View.GONE);
            } else if (numberOfLikes > 0 && numberOfComments > 0) {
                tvDot.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setFeedContent(TextView tvFeedContent, TextView tvFeedDescription, TextView view_more, int position, Feed feed, IFeedAdapter iFeedAdapter) {
        tvFeedContent.setText(feed.feedTitle);
        tvFeedContent.setVisibility(feed.feedTitle != null && feed.feedTitle.length() > 0 ? View.VISIBLE : View.GONE);
        view_more.setVisibility(View.GONE);
        String description = "";
        if (feed.feedDescription != null && feed.feedDescription.length() > 0) {
            tvFeedDescription.setVisibility(View.VISIBLE);
            description = feed.feedDescription.trim();
            /*tvFeedDescription.setText(description);
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    int lineCount = tvFeedDescription.getLineCount();
                    Log.e("lineCount", lineCount + "");
                    if (lineCount > 5) {
                        tvFeedDescription.setMaxLines(5);
                        view_more.setVisibility(View.VISIBLE);
                        view_more.setText("more");
                    }
                }
            }, 300);*/
            int newLineCount = description.split("\n").length;
            if (description.length() > 70 || newLineCount > 2) {
                expandableTextView(tvFeedDescription, description);
            } else {
                tvFeedDescription.setText(description);
            }
        } else {
            view_more.setVisibility(View.GONE);
            tvFeedDescription.setVisibility(View.GONE);
        }
        String finalDescription = description;
        view_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFeedDescViewMoreBtn(tvFeedDescription, view_more, finalDescription, position, iFeedAdapter);
            }
        });
    }

    private void expandableTextView(TextView tvFeedDescription, String inputText) {
        int textLength = inputText.length();
        if (inputText.length() > 70) {
            textLength = 70;
        }
        String[] splitCount = inputText.split("\n");
        if (splitCount.length > 2) {
            textLength = splitCount[0].length() + splitCount[1].length();
        }
        Activity activity = AppController.getInstance().getCurrentRegisteredActivity();
        String text = inputText.substring(0, textLength) + "... ";
        final String fulltext = inputText;
        final SpannableString moreTxt = new SpannableString(text + "more");
        ClickableSpan span1 = new ClickableSpan() {
            @Override
            public void onClick(@NotNull View textView) {
                String fulltextAdd = fulltext + " ";
                SpannableString lessTxt = new SpannableString(fulltextAdd + "less");
                ClickableSpan span2 = new ClickableSpan() {
                    @Override
                    public void onClick(@NotNull View textView) {
                        tvFeedDescription.setText(moreTxt);
                        tvFeedDescription.setMovementMethod(LinkMovementMethod.getInstance());
                    }
                };
                lessTxt.setSpan(span2, fulltextAdd.length(), lessTxt.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                lessTxt.setSpan(new RelativeSizeSpan(0.9f), fulltextAdd.length(), lessTxt.length(), 0);
                lessTxt.setSpan(new ForegroundColorSpan(activity.getResources().getColor(R.color.feed_des_color)), fulltextAdd.length(), lessTxt.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvFeedDescription.setText(lessTxt);
                tvFeedDescription.setMovementMethod(LinkMovementMethod.getInstance());
            }
        };
        moreTxt.setSpan(span1, textLength + 4, textLength + 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        moreTxt.setSpan(new RelativeSizeSpan(0.9f), textLength + 4, textLength + 8, 0);
        moreTxt.setSpan(new ForegroundColorSpan(activity.getResources().getColor(R.color.feed_des_color)), textLength + 4, textLength + 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvFeedDescription.setText(moreTxt);
        tvFeedDescription.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void setFeedDescViewMoreBtn(TextView tvFeedDescription, TextView view_more, String feedDescription,
                                        int itemPosition, IFeedAdapter iFeedAdapter) {
        if (view_more.getText().toString().equals("more")) {
            tvFeedDescription.setMaxLines(100);
            tvFeedDescription.setText(feedDescription);
            view_more.setText("less");
        } else {
            tvFeedDescription.setMaxLines(5);
            tvFeedDescription.setText(feedDescription);
            view_more.setText("more");
            if (iFeedAdapter != null) {
                iFeedAdapter.feedContentViewLess(itemPosition);
            }
        }
    }

    //SRIKANTH Code END

    public static void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    int lineEndIndex = tv.getLayout().getLineEnd(0);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else {
                    int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                }
            }
        });

    }

    private static SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                            final int maxLine, final String spanableText,
                                                                            final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {
            ssb.setSpan(new ClickableSpan() {

                @Override
                public void onClick(View widget) {
                    if (viewMore) {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, -1, "View Less", false);
                    } else {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, 3, "View More", true);
                    }

                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;

    }


    public static String formatString(String name) {
        String s = name;
        Log.e("datalength", s.length() + "");
        if (s.length() >= 220) {
            s = s.substring(0, 215) + " ...";
        }
        return s;
    }

    public static boolean toastMessage(Context mContext, String name) {
        Toast.makeText(mContext, name, Toast.LENGTH_SHORT).show();
        return false;
    }

    public static boolean toastMessageLong(Context mContext, String name) {
        Toast.makeText(mContext, name, Toast.LENGTH_SHORT).show();
        return false;
    }

    public static boolean editTextErrorCall(Context mContext, String name, EditText editText) {
        editText.setError(name);
        editText.requestFocus();
        return false;
    }

    public static String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public static Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
                    encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public static void setKeyboardFocus(final EditText primaryTextField) {
        (new Handler()).postDelayed(new Runnable() {
            public void run() {
                primaryTextField.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0, 0));
                primaryTextField.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 0, 0, 0));
            }
        }, 100);
    }


    /*//Like Event
    public static void likeAndUnLikeEvent(Context context, final ImageButton ib_like,
                                          final String userId, String entityId) {
        if (SharedPrefsUtil.getStringPreference(context, "IS_LOGGED_IN").equals("NOT_LOGGED_IN")) {
            Toast.makeText(context, "You need to first Login.", Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (ib_like.getDrawable().getConstantState().equals
                    (context.getResources().getDrawable(R.drawable.ic_likepink).getConstantState())) {
                Log.e("LikeEvent", "PINK_COLOR");
                likeImageChangeAndpostRequest(ib_like, "LIKED", userId, entityId, context);
            } else {
                Log.e("LikeEvent", "NORMAL_COLOR");
                likeImageChangeAndpostRequest(ib_like, "UNLIKED", userId, entityId, context);
            }
        }
    }*/


//    private static void likeImageChangeAndpostRequest(ImageButton ib_like,
//                                                      String likeStr, String userId,
//                                                      String entityId, Context context) {
//        if (SharedPrefsUtil.getStringPreference(context, "IS_LOGGED_IN").equals("NOT_LOGGED_IN")) {
//            Toast.makeText(context, "You need to first Login.", Toast.LENGTH_SHORT).show();
//            return;
//        } else {
//            ib_like.setImageResource(0);
//            if (likeStr.equals("LIKED")) {
//                ib_like.setImageResource(R.drawable.ic_like);
//                Toast.makeText(context, "UnLiked", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(context, "Liked", Toast.LENGTH_SHORT).show();
//                ib_like.setImageResource(R.drawable.ic_like);
//            }
////            new PostRetrofit().postRetrofitMethod("like", userId,
////                    entityId, ib_like, context);
//        }
//
//    }


    public void feedShare(Context context, Feed feed) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, ApiClient.SHARE_FEED_IN_SOCIAL_MEDIA + feed.id);
        context.startActivity(Intent.createChooser(intent, "Share"));
    }

    private static void shareAudioOrVideoLink(String shareableLink, Context context) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Flikster");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareableLink + "\n\n\n" + "Download **Flikster**" + "\n" +
                "https://play.google.com/store/apps/details?id=com.flikster&hl=en" +
                " and don't miss anything from movie industry. Stay connected to the world of Illusion.\n");
        shareIntent.setType("text/plain");
        context.startActivity(Intent.createChooser(shareIntent, "Complete action using ...."));
    }

    public static void getBitmapForShare(Bitmap bitmap, Context context) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "CelebKonnect");
        //shareIntent.putExtra(Intent.EXTRA_TEXT, shareableLink);
        shareIntent.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap, context));
        shareIntent.setType("text/plain");
        context.startActivity(Intent.createChooser(shareIntent, "Complete action using ...."));
    }

    public static Uri getLocalBitmapUri(Bitmap bmp, Context context) {
        Uri bmpUri = null;
        try {
            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                    "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            /*if (!file.exists() && !file.isDirectory()) {
                Log.d("deletePictreDir", true + "");
                file.mkdirs();
            }else {
                Log.d("deletePictreDir", true + "");
            }*/
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmpUri;
    }


    public static boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }


   /* public static void openCommonDialog(Context context, String commontxtStr, String headertxtStr) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_common_menuitems);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        //WebView commontxt = (WebView) dialog.findViewById(R.id.commontxt);
        TextView commontxt = (TextView) dialog.findViewById(R.id.commontxt);
        ImageButton closebtn = (ImageButton) dialog.findViewById(R.id.closebtn);
        TextView headertxt = (TextView) dialog.findViewById(R.id.headertxt);
        if (headertxtStr != null && !headertxtStr.isEmpty()) {
            headertxt.setText(headertxtStr);
        } else {
            headertxt.setText("Flikster");
        }
        String htmlText = " %s ";
        String myData = "What do we do with your information?\n" +
                "When you purchase something from our store, as part of the buying and selling process, we collect the personal information you give us such as your name, address and email address.When you browse our store If you would like to: access, correct, amend or delete any personal information we have about you, register a complaint, or simply want more information contact our Privacy Compliance Officer at support@flikster.com";
        //commontxt.loadData(String.format(htmlText, myData), "text/html", "utf-8");
        commontxt.setText("\"Flikster - PreferenceNew & Fashion\" is extremely useful to get you the most popular and latest news of your favorite celebrities, Bollywood, Fashion trends. Manage your data feed cleaner and stay tuned with every piece of news from different sources in just few seconds under a single umbrella of \"Flikster - PreferenceNew & Fashion\". ");
//        commontxt.setText(String.format(htmlText, myData));
         *//*if (commontxtStr != null && !commontxtStr.isEmpty()) {
            commontxt.setText(commontxtStr + "");
            commontxt.setText(Html.fromHtml("first<br><b>second</b>"));
        } else {
            commontxt.setText("No Data Available" + "");
            Html.fromHtml("first<br><b>second</b>");
        }*//*

        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        window.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.translucent)));
        dialog.show();
    }*/

    public static void popBackStack(FragmentManager manager) {
        FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
        manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public static ProgressDialog showProgressDialog(Context mContext, ProgressDialog progressDialog) {
//        if(progressDialog == null){
//            return null;
//        }
        try {
            progressDialog = new ProgressDialog(mContext, R.style.AppCompatAlertDialogStyle);
            progressDialog.setMessage("Loading..."); // Setting Message
//        progressDialog.setTitle("ProgressDialog"); // Setting Title
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
            progressDialog.show(); // Display Progress Dialog
            progressDialog.setCancelable(false);
            return progressDialog;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void dismissProgressDialog(Context mContext, ProgressDialog progressDialog) {
        try {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkInternetConnection(Context mContext) {
        ConnectivityManager connMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return connMgr.getActiveNetworkInfo() != null;
    }

    public boolean checkInterConnection(RecyclerView recyclerView, Context context) {
        if (Common.checkInternetConnection(context)) {
            return true;
        } else {
            CommonRecycler.setNoInternetOrServerDown(context, recyclerView, true);
            return false;
        }
    }


    public static void shareClick(String shareableLink, Context context) {
        if (shareableLink != null && !shareableLink.isEmpty()) {
            Log.e("shareLink", shareableLink);
            if (shareableLink.contains("https://www.youtube.com/embed/")) {
                shareAudioOrVideoLink(shareableLink, context);
            } else {
                BitmapLoadingInBack bitmapLoadingInBack = new BitmapLoadingInBack(shareableLink, context);
                bitmapLoadingInBack.execute();
            }
        } else {
            Log.e("shareLink", shareableLink);
            Toast.makeText(context, "Image Link not available", Toast.LENGTH_SHORT).show();
        }
    }

    public static String getCoutryCode(Context mContext) {
        String countryCode = "";
        try {
            TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            countryCode = tm.getSimCountryIso();
            if (countryCode == null || countryCode.isEmpty()) {
                countryCode = "IN";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return countryCode.toUpperCase();
    }

    public static void shareSocialNetwork(Context mContext, String referalCode, String pageAccess) {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        if (pageAccess != null && !pageAccess.isEmpty()) {
            if (pageAccess.equals("APP_PROMOTION")) {
                if (isCelebAndManager(Utility.getContext())) {
                    intent.putExtra(Intent.EXTRA_TEXT,
                            "Hi, I'm inviting you to join CelebKonect - a simple and interactive Celebrity engagement app." +
                                    "Use my referral code " + referalCode + " during sign-up, and get free credits of up to 250 to Konect with me." +
                                    "So what are you waiting for - Get, Set, Konect." + " visit https://celebkonect.com/ to download the app now.");

                } else if (isCelebStatus(Utility.getContext())) {
                    intent.putExtra(Intent.EXTRA_TEXT,
                            "Hi, I'm inviting you to join CelebKonect - a simple and interactive Celebrity engagement app." +
                                    "Use my referral code " + referalCode + " during sign-up, and get free credits of up to 250 to Konect with me." +
                                    "So what are you waiting for - Get, Set, Konect." + " visit https://celebkonect.com/ to download the app now.");
                } else if (isManagerStatus(Utility.getContext())) {
                    intent.putExtra(Intent.EXTRA_TEXT,
                            "Hi, I'm inviting you to join CelebKonect - a simple and interactive Celebrity engagement app." +
                                    "Use my referral code " + referalCode + " during sign-up, and get free credits of up to 150 to Konect with a celebrity of your choice." +
                                    "So what are you waiting for - Get, Set, Konect." + " visit https://celebkonect.com/ to download the app now.");
                } else {
                    intent.putExtra(Intent.EXTRA_TEXT,
                            "Hi, I'm inviting you to join CelebKonect - a simple and interactive Celebrity engagement app." +
                                    "Use my referral code " + referalCode + " during sign-up, and get free credits of up to 150 to Konect with a celebrity of your choice." +
                                    "So what are you waiting for - Get, Set, Konect." + " visit https://celebkonect.com/ to download the app now.");
                }

            }

        } else {
            intent.putExtra(Intent.EXTRA_TEXT,
                    "Hi, I'm inviting you to join CelebKonect - a simple and interactive Celebrity engagement app." +
                            "Use my referral code " + referalCode + " during sign-up, and get free credits of up to 250 to Konect with me." +
                            "So what are you waiting for - Get, Set, Konect." + " visit celebkonect.com to download the app now.");
        }

        mContext.startActivity(Intent.createChooser(intent, "Share"));
    }


    public static float imageResizeInRatioCaptureOrGallery(Context mContext, int imgHeight, int imgWidth) {
        return (float) imgHeight / (float) imgWidth;
    }

    public static ArrayList<String> removeDuplicatesInArraylist(ArrayList<String> duplicateArrayList) {
        HashSet<String> hashSet = new HashSet<String>();
        hashSet.addAll(duplicateArrayList);
        duplicateArrayList.clear();
        duplicateArrayList.addAll(hashSet);
        return duplicateArrayList;
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void headerScreenatAllScreens(final Activity mActivity, final String headertitle, final Context mContext) {
        androidx.appcompat.widget.Toolbar toolbar_back = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            toolbar_back = (androidx.appcompat.widget.Toolbar) mActivity.findViewById(R.id.toolbar_back_main);
        }
        ((AppCompatActivity) mActivity).setSupportActionBar(toolbar_back);
        TextView toolbarheadertitle = (TextView) mActivity.findViewById(R.id.toolbar_back_title);
        toolbarheadertitle.setText(headertitle + "");
        ImageButton notificationicon = (ImageButton) mActivity.findViewById(R.id.notificationicon);

        CircleImageView usersmallimg = (CircleImageView) mActivity.findViewById(R.id.usersmallimg);
        usersmallimg.setVisibility(View.VISIBLE);

        LinearLayout title_bar_layout = (LinearLayout) mActivity.findViewById(R.id.title_bar_layout);

        LinearLayout toolbar_creditslayout = (LinearLayout) mActivity.findViewById(R.id.toolbar_creditslayout);
        toolbar_creditslayout.setVisibility(View.GONE);

        TextView toolbar_credits_display = (TextView) mActivity.findViewById(R.id.toolbar_credits_display);
        Button toolbar_edit_profile_add_credits = (Button) mActivity.findViewById(R.id.toolbar_edit_profile_add_credits);


        try {
            if (SessionManager.userLogin.profilePic != null
                    && !SessionManager.userLogin.profilePic.isEmpty()) {
                Common.getInstance().setGlideImage(mContext, ApiClient.BASE_URL + SessionManager.userLogin.profilePic, usersmallimg);
            } else {
                usersmallimg.setImageResource(R.drawable.ic_grey_celebkonect_logo);
            }

        } catch (Exception e) {

        }

        if (SessionManager.userLogin.mainCredits != null) {
            toolbar_credits_display.setText("Available Credits: " + SessionManager.userLogin.mainCredits + "");
        }
        try {
            ImageView backbtn = (ImageView) mActivity.findViewById(R.id.backbtntollbar);
            backbtn.setClickable(true);
            ImageView help_icon = (ImageView) mActivity.findViewById(R.id.help_icon);

            if (headertitle != null && !headertitle.isEmpty()) {
                if (headertitle.equals("Notification Settings")) {
                    backbtn.setVisibility(View.VISIBLE);
                    notificationicon.setVisibility(View.GONE);
                } else if (headertitle.equals("Charity Settings")) {
                    backbtn.setVisibility(View.VISIBLE);
                } else if (headertitle.equals("Change Password")) {
                    backbtn.setVisibility(View.VISIBLE);
                } else if (headertitle.equals("Others")) {
                    backbtn.setVisibility(View.VISIBLE);
                    notificationicon.setVisibility(View.GONE);
                } else if (headertitle.equals("My Celebrities")) {
                    notificationicon.setVisibility(View.VISIBLE);
                    backbtn.setVisibility(View.INVISIBLE);
                    Button allCelebBtn = (Button) mActivity.findViewById(R.id.allCelebBtn);
                    allCelebBtn.setVisibility(View.VISIBLE);
                    allCelebBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            Intent intent = new Intent(mActivity, RecommdedAvailableCeleb.class);
//                            intent.putExtra("FORM_MYCELEB", "TRUE");
//                            mActivity.startActivity(intent);

                        }
                    });
                } else if (headertitle.equals("Feed Details")) {
                    title_bar_layout.setVisibility(View.GONE);
                    toolbarheadertitle.setVisibility(View.GONE);
                    backbtn.setVisibility(View.VISIBLE);
                    notificationicon.setVisibility(View.VISIBLE);
                    //SuccessActivity
                } else if (headertitle.equals("SuccessActivity")) {
                    title_bar_layout.setVisibility(View.GONE);
                    toolbarheadertitle.setVisibility(View.GONE);
//                    backbtn.setVisibility(View.VISIBLE);
                    notificationicon.setVisibility(View.GONE);
                    title_bar_layout.setVisibility(View.GONE);
                    backbtn.setVisibility(View.GONE);
                } else if (headertitle.equals("New Slot Schedule")) {
                    title_bar_layout.setVisibility(View.GONE);
                    toolbarheadertitle.setVisibility(View.GONE);
//                    backbtn.setVisibility(View.VISIBLE);
                    notificationicon.setVisibility(View.VISIBLE);
                    title_bar_layout.setVisibility(View.GONE);
                    backbtn.setVisibility(View.VISIBLE);
                } else if (headertitle.equals("My Orders")) {
                    title_bar_layout.setVisibility(View.VISIBLE);
                    toolbarheadertitle.setVisibility(View.VISIBLE);
                    notificationicon.setVisibility(View.VISIBLE);
                    title_bar_layout.setVisibility(View.VISIBLE);
                    backbtn.setVisibility(View.INVISIBLE);
                    toolbar_creditslayout.setVisibility(View.VISIBLE);
                } else if (headertitle.equals("My Cart")) {
                    title_bar_layout.setVisibility(View.VISIBLE);
                    toolbarheadertitle.setVisibility(View.VISIBLE);
                    notificationicon.setVisibility(View.VISIBLE);
                    title_bar_layout.setVisibility(View.VISIBLE);
                    backbtn.setVisibility(View.INVISIBLE);
                    toolbar_creditslayout.setVisibility(View.VISIBLE);
                } else if (headertitle.equals("Credit Recharge")) {
                    backbtn.setVisibility(View.VISIBLE);
                } else if (headertitle.equals("Credit Recharge")) {
                    backbtn.setVisibility(View.VISIBLE);
                } else if (headertitle.equals("Payment")) {
                    backbtn.setVisibility(View.VISIBLE);
                } else if (headertitle.equals("Quick Post")) {
                    backbtn.setVisibility(View.VISIBLE);
                } else if (headertitle.equals("Media Post")) {
                    backbtn.setVisibility(View.VISIBLE);
                } else if (headertitle.equals("Premium Post")) {
                    backbtn.setVisibility(View.VISIBLE);
                } else if (headertitle.equals("My Reports")) {
                    backbtn.setVisibility(View.INVISIBLE);
                    help_icon.setVisibility(View.VISIBLE);
                } else if (headertitle.equals("FAQ")) {
                    backbtn.setVisibility(View.INVISIBLE);
                    help_icon.setVisibility(View.VISIBLE);
                } else {
                    backbtn.setVisibility(View.INVISIBLE);
                }
            }
            backbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.finish();
                }
            });
            notificationicon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    mActivity.startActivity(new Intent(mActivity, NotificationsActivity.class));
                }
            });

            usersmallimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent i = new Intent(mActivity, SelfProfileActivity.class);
//                    //ABOUT
//                    //SharedPrefsUtil.setStringPreference(mContext, "ABOUT", aboutMeStr);
//                    if (SharedPrefsUtil.getStringPreference(mActivity, "ABOUT") != null && !SharedPrefsUtil.getStringPreference(mActivity, "ABOUT").isEmpty()) {
//                        i.putExtra("ABOUT", SharedPrefsUtil.getStringPreference(mActivity, "ABOUT"));
//                    } else {
//                        i.putExtra("ABOUT", "");
//                    }
//
//                    i.putExtra("CelebId", SharedPrefsUtil.getStringPreference(mActivity, "USER_ID"));
//                    mActivity.startActivity(i);
                }
            });

            toolbar_edit_profile_add_credits.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent intent = new Intent(mActivity, CreditRechargeActvity.class);
//                    intent.putExtra("className", "tollbar");
//                    mActivity.startActivity(intent);


                    Intent intent = new Intent(mActivity, HelperActivity.class);
                    intent.putExtra(Constants.FRAGMENT_TITLE, "Credits");
                    intent.putExtra("className", "tollbar");
                    intent.putExtra(Constants.FRAGMENT_KEY, 8034);// CreditsRecharge
                    mActivity.startActivity(intent);
                }
            });
        } catch (Exception e) {

        }
    }

    public static boolean isValidEmail(String email) {
        boolean isValid = false;
        String ex = "^([a-zA-Z0-9_\\.\\-])+([+_a-zA-Z0-9])+\\@(([a-zA-Z0-9\\-])+\\.)([a-zA-Z0-9]{2,3})([a-zA-Z0-9.]{3})?$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(ex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        if (email.contains(".com.com") || email.contains("com.co") || email.contains(".com.com.com")) {
            isValid = false;
        }
        return isValid;

    }

    //Patterns.WEB_URL.matcher(
    public static boolean isValidWebUrl(Context mContext, String givenUrl) {
        boolean isValidUrl = true;
        if (Patterns.WEB_URL.matcher(givenUrl).matches()) {
            if (givenUrl.startsWith("https://") || givenUrl.startsWith("http://")) {
                isValidUrl = true;
            } else {
                isValidUrl = false;
                Toast.makeText(mContext, "Please Enter Valid Url", Toast.LENGTH_SHORT).show();
            }
        } else {
            isValidUrl = false;
            Toast.makeText(mContext, "Please Enter Valid Url", Toast.LENGTH_SHORT).show();
        }
        return isValidUrl;
    }

    /*if (addEt.getText().toString().endsWith("png") || addEt.getText().toString().endsWith("PNG")) {
                                addingPortfolioImagetoAdapter();
                            } else if (addEt.getText().toString().endsWith("jpg") || addEt.getText().toString().endsWith("JPG")) {
                                addingPortfolioImagetoAdapter();
                            } else if (addEt.getText().toString().endsWith("bmp") || addEt.getText().toString().endsWith("bmp")) {
                                addingPortfolioImagetoAdapter();
                            } else if (addEt.getText().toString().endsWith("jpeg") || addEt.getText().toString().endsWith("JPEG")) {
                                addingPortfolioImagetoAdapter();
                            } */

    public static boolean checkImageExtension(Context mContext, String givenUrl) {
        boolean isValidUrl = true;

        if (givenUrl.endsWith("png") || givenUrl.endsWith("PNG")) {
            isValidUrl = true;
        } else if (givenUrl.endsWith("jpg") || givenUrl.endsWith("JPG")) {
            isValidUrl = true;
        } else if (givenUrl.endsWith("bmp") || givenUrl.endsWith("bmp")) {
            isValidUrl = true;
        } else if (givenUrl.endsWith("jpeg") || givenUrl.endsWith("JPEG")) {
            isValidUrl = true;
        } else {
            isValidUrl = false;
            Toast.makeText(mContext, "Please Enter Valid Image Url", Toast.LENGTH_SHORT).show();
        }
        return isValidUrl;
    }

    public static boolean checkVideoExtension(Context mContext, String givenUrl) {
        boolean isValidUrl = true;
        if (givenUrl.endsWith("3gp") || givenUrl.endsWith("3GP")) {
            isValidUrl = true;
        } else if (givenUrl.endsWith("avi") || givenUrl.endsWith("AVI")) {
            isValidUrl = true;
        } else if (givenUrl.endsWith("mp4") || givenUrl.endsWith("MP4")) {
            isValidUrl = true;
        } else if (givenUrl.endsWith("wmv") || givenUrl.endsWith("WMV")) {
            isValidUrl = true;
        } else {
            isValidUrl = false;
            Toast.makeText(mContext, "Please Enter Valid Video Url", Toast.LENGTH_SHORT).show();
        }
        return isValidUrl;
    }

    public static boolean checkImageOrVideoExtension(Context mContext, String givenUrl) {
        boolean isValidUrl = true;
        if (givenUrl.endsWith("3gp") || givenUrl.endsWith("3GP")) {
            isValidUrl = true;
        } else if (givenUrl.endsWith("avi") || givenUrl.endsWith("AVI")) {
            isValidUrl = true;
        } else if (givenUrl.endsWith("mp4") || givenUrl.endsWith("MP4")) {
            isValidUrl = true;
        } else if (givenUrl.endsWith("wmv") || givenUrl.endsWith("WMV")) {
            isValidUrl = true;
        } else {
            isValidUrl = false;
            Toast.makeText(mContext, "Please Enter Valid Video Url", Toast.LENGTH_SHORT).show();
        }
        return isValidUrl;
    }

    public static double heigthConvertInCm(double heigthInFeet) {
        double convertsInCm;
        double heigthinFloat = heigthInFeet;
        convertsInCm = heigthinFloat * 30.48;
        return convertsInCm;
    }

    public static double weighConvertInKgs(double weigthInkg) {
        double weigthInPounds;
        double heigthinFloat = weigthInkg;
        weigthInPounds = heigthinFloat * 2.20462;
        return weigthInPounds;
    }

    public void showKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static void hideKeyboard(Activity activity) {
        if (isKeyboardVisible(activity)) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        }

    }


    public static boolean isKeyboardVisible(Activity activity) {
        ///This method is based on the one described at http://stackoverflow.com/questions/4745988/
        // how-do-i-detect-if-software-keyboard-is-visible-on-android-device
        Rect r = new Rect();
        View contentView = activity.findViewById(android.R.id.content);
        contentView.getWindowVisibleDisplayFrame(r);
        int screenHeight = contentView.getRootView().getHeight();

        int keypadHeight = screenHeight - r.bottom;

        return
                (keypadHeight > screenHeight * 0.15);
    }

    public static String replaceNewlinesWithBreaks(String source) {
        return source != null ? source.replaceAll("(?:\n|\r\n)", "\n") : "";
    }

    public static void offlinePopupDailog(final Context mContext, final Activity ComingActivity,
                                          String title, String heading, final int type) {
        Boolean status;
        final Dialog promoDialog = new Dialog(ComingActivity);
        promoDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        promoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        promoDialog.setCancelable(type == 0 ? true : false);
        promoDialog.setContentView(R.layout.internet_error_popup);
        Button retryBtn = (Button) promoDialog.findViewById(R.id.retryBtn);
        TextView mCeleb_name = (TextView) promoDialog.findViewById(R.id.offlinetxt);
        promoDialog.show();
        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.checkInternetConnection(ComingActivity)) {
                    if (type == 1) {
                        promoDialog.dismiss();
                        ((SplashScreenActivity) ComingActivity).verifyAnyUpdate();

                        return;
                    }
//                    Common.getInstance().navigateToFeedOrBottom(ComingActivity);
                    Common.getInstance().navigateHome(ComingActivity);
                    promoDialog.dismiss();
                } else {
//                    Toast.makeText(co)

                }
            }
        });

    }

   /* public static void countrypopData(final Context mContext, final Activity ComingActivity,
                                      String title, String heading) {
        Boolean status;
        final Dialog promoDialog = new Dialog(ComingActivity);
        promoDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        promoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        promoDialog.setCancelable(true);
        promoDialog.setContentView(R.layout.country_popup);
        Button retryBtn = (Button) promoDialog.findViewById(R.id.countryRecyList);
        TextView mCeleb_name = (TextView) promoDialog.findViewById(R.id.offlinetxt);
        promoDialog.show();
        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.checkInternetConnection(ComingActivity)) {
//                    ComingActivity.startActivity(new Intent(ComingActivity, MyFeedsActivity.class));
//                    ComingActivity.Common.getInstance().navigateToFeedOrBottom(ComingActivity));
                    Common.getInstance().navigateToFeedOrBottom(ComingActivity);
                    promoDialog.dismiss();
                } else {
//                    Toast.makeText(co)

                }
            }
        });

    }*/

    /*public static void interNetSpeed(Context mContext, Activity activity) {
        ConnectivityManager connectivity = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivity.getNetworkInfo(activity);
        if (info.getType() == ConnectivityManager.TYPE_WIFI) {
            // do something
        } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            // check NetworkInfo subtype
            if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_GPRS) {
                // Bandwidth between 100 kbps and below
            } else if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_EDGE) {
                // Bandwidth between 50-100 kbps
            } else if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_EVDO_0) {
                // Bandwidth between 400-1000 kbps
            } else if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_EVDO_A) {
                // Bandwidth between 600-1400 kbps
            }
        }
    }*/

    public static String getReadableFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    public static Long getSizeofImage(List<File> fileList) {

        long totalKbs = 0;
        if (fileList != null && fileList.size() > 0) {
            for (File file1 : fileList) {
                totalKbs += Utility.fileSizeInKB(file1.length());
            }
        }
        return totalKbs;
    }

    public static SpannableStringBuilder setMandatoryHintData(Context mContext, String hintData) {
        String simple = hintData;
        String colored = " *";
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(simple);
        int start = builder.length();
        builder.append(colored);
        int end = builder.length();
        builder.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.appcolor)),
                start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;

    }

    public static boolean isValidMobile(String phone) {
        boolean check = false;
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            if (phone.length() < 9 || phone.length() > 13) {
                // if(phone.length() != 10) {
                check = false;
                // txtPhone.setError("Not Valid Number");
            } else {
                check = Patterns.PHONE.matcher(phone).matches();
            }
        } else {
            check = false;
        }
        return check;
    }

    public static boolean isBlank(String value) {
        return (value == null || value.equals("") || value.equals("null") || value.trim().equals(""));
    }


    public static boolean isOnlyNumber(String value) {
        boolean ret = false;
        if (!isBlank(value)) {
            ret = value.matches("^[0-9]+$");
        }
        return ret;
    }


    public static String GetCountryZipCode(final Context mContext) {
        String CountryID = "";
        String CountryZipCode = "91";
        try {
            TelephonyManager manager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            //getNetworkCountryIso
            CountryID = manager.getSimCountryIso().toUpperCase();
            String[] rl = mContext.getResources().getStringArray(R.array.CountryCodes);
            for (int i = 0; i < rl.length; i++) {
                String[] g = rl[i].split(",");
                if (g[1].trim().equals(CountryID.trim())) {
                    CountryZipCode = g[0];
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CountryZipCode;


    }

    public static String GetCountryCode(final Context mContext) {
        String CountryID = "";
        String CountryZipCode = "91";
        try {
            TelephonyManager manager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            //getNetworkCountryIso
            CountryID = manager.getSimCountryIso().toUpperCase();
            String[] rl = mContext.getResources().getStringArray(R.array.CountryCodes);
            for (int i = 0; i < rl.length; i++) {
                String[] g = rl[i].split(",");
                if (g[1].trim().equals(CountryID.trim())) {
                    CountryZipCode = g[0];
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CountryZipCode;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void toolbarFixHeader(Activity mActivity, String headerTitle) {
        try {
            androidx.appcompat.widget.Toolbar toolbar = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                toolbar = (androidx.appcompat.widget.Toolbar) mActivity.findViewById(R.id.common_toolbar);
            }
            ((AppCompatActivity) mActivity).setSupportActionBar(toolbar);
            mActivity.setTitle(headerTitle);
            ((AppCompatActivity) mActivity).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) mActivity).getSupportActionBar().setDisplayShowHomeEnabled(true);
        } catch (Exception e) {

        }

    }

    public static void screenShotRestrict(Activity mActivity) {
        if (ApiClient.ENVIRONMENT.equals("live")) {
            mActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE);
        }
    }

    public static boolean checkAndRequestPermissions(Activity mActivity) {
        int permissionCAMERA = ContextCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionCAMERA != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(mActivity,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MY_PERMISSIONS_REQUEST_ACCOUNTS);
            return false;
        }
        return true;
    }

    public static boolean checkAndRequestGalleryPermissions(Activity mActivity) {
        int storagePermission = ContextCompat.checkSelfPermission(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (storagePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(mActivity,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MY_PERMISSIONS_REQUEST_ACCOUNTS);
            return false;
        }
        return true;
    }

    public static String getYouTubeId(String youTubeUrl) {
        String pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(youTubeUrl);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return "error";
        }
    }

    public static boolean createReminderEventInGmail(String startdateAndTime,
                                                     String enddateAndTime, Activity mActivity, String callWithPerson) {
        long calID = 3;
        long startTimeMillis = 0;
        long endTimeMillis = 0;

        try {
            startTimeMillis = DateUtil.selcteddateAndTimeEpoic(startdateAndTime);
            endTimeMillis = DateUtil.selcteddateAndTimeEpoic(enddateAndTime);

            Log.v("startTimeMillis", startTimeMillis + "");
            Log.v("endTimeMillis", endTimeMillis + "");

        } catch (Exception e) {

        }


        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, startTimeMillis);
        values.put(CalendarContract.Events.DTEND, endTimeMillis);
        values.put(CalendarContract.Events.TITLE, "CelebKonect Remainder");
        values.put(CalendarContract.Events.DESCRIPTION, callWithPerson + " call ");
        values.put(CalendarContract.Events.CALENDAR_ID, calID);
        values.put(CalendarContract.Events.HAS_ALARM, true);
//        values.put(CalendarContract.Events.ALLOWED_REMINDERS, true);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
//        values.put(CalendarContract.Reminders.MINUTES, 10);


        ContentResolver cr = mActivity.getContentResolver();
        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_CALENDAR)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(mActivity, "Calender permission denied", Toast.LENGTH_LONG).show();
        } else {
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
            long eventID = Long.parseLong(uri.getLastPathSegment());
/*
            int calenderId = -1;
            String calenderEmaillAddress = "shivashiv080@gmail.com";
            String[] projection = new String[]{
                    CalendarContract.Calendars._ID,
                    CalendarContract.Calendars.ACCOUNT_NAME};
            ContentResolver cr = CalendarActivity.this.getContentResolver();
            Cursor cursor = cr.query(Uri.parse("content://com.android.calendar/calendars"), projection,
                    CalendarContract.Calendars.ACCOUNT_NAME + "=? and (" +
                            CalendarContract.Calendars.NAME + "=? or " +
                            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME + "=?)",
                    new String[]{calenderEmaillAddress, calenderEmaillAddress,
                            calenderEmaillAddress}, null);

            if (cursor.moveToFirst()) {

                if (cursor.getString(1).equals(calenderEmaillAddress))
                    calenderId = cursor.getInt(0); //youre calender id to be insered in above 2 answer


            }*/

            Toast.makeText(mActivity, "Event Created", Toast.LENGTH_LONG).show();
        }


        return true;
    }

    /*public static void feedshareInFacebook(ShareDialog shareDialog) {
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
//                    .setContentTitle("Z2P")
                    .setContentTitle("Check")
                    .setContentDescription("CelebKonect")
//                    .setContentUrl(Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()))
                    .setContentUrl(Uri.parse("http://prodapi.celebkonect.com:4300/uploads/feeds/ck_pr2_2018-8-10_1533907021771_IMG-20180806-WA0004.jpg"))
                    .build();
            shareDialog.show(linkContent);
        }
    }*/

    public void navigateToFeedPageHome(Activity currentActivity) {
        SessionManager.getInstance().setKeyValue(SessionManager.KEY_FIRSTTIME_EDIT_PROFILE_ACCESS, "TRUE");
        SessionManager.getInstance().setKeyValue(SessionManager.KEY_SIGN_IN_ACCESS, "TRUE");
        navigateToFeedOrBottom(currentActivity);
    }

    public void navigateToFeedOrBottom(Activity currentActivity) {
        Intent i = new Intent(currentActivity, BottomMenuActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        currentActivity.startActivity(i);
        currentActivity.finish();
    }

    public void navigateHome(Activity currentActivity) {
        Intent intent = new Intent(currentActivity, BottomMenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        currentActivity.startActivity(intent);
    }

    public void openPreferencesFrag(Activity activity, Boolean isFromRegister) {
        Intent intent = new Intent(activity, HelperActivity.class);
        intent.putExtra(Constants.FRAGMENT_TITLE, "Preferences");
        intent.putExtra(Constants.FRAGMENT_KEY, 8072);
        intent.putExtra("isFromRegister", isFromRegister);
        activity.startActivity(intent);
        activity.finish();
    }

    public void navigateToFeedOrBottomWithContext(Context context) {
        Intent i = new Intent(context, BottomMenuActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(i);
    }


    public static void switchFromMangerProfile(Context context, Boolean isSwitchingToCeleb) {
//        ChatSocket.getInstance().emitOffline(context);

        ChatSocket.getInstance().screenStatusEmit(false);

        Log.e("switchAcc", "false");
        Intent intent = new Intent(context, SwitchAccountStatus.class);
        context.stopService(intent);

        Intent i = new Intent(context, BottomMenuActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(i);
    }


    public static String unescapeJava(String escaped) {
        String processed = "";
        try {
            if (escaped.indexOf("\\u") == -1)
                return escaped;
            int position = escaped.indexOf("\\u");
            while (position != -1) {
                if (position != 0)
                    processed += escaped.substring(0, position);
                String token = escaped.substring(position + 2, position + 6);
                escaped = escaped.substring(position + 6);
                processed += (char) Integer.parseInt(token, 16);
                position = escaped.indexOf("\\u");
            }
            processed += escaped;
        } catch (Exception e) {

        }
        return processed;
    }

    public static String convertEmojiFormat(EditText titleEt) {
        return StringEscapeUtils.escapeJava(titleEt.getText().toString());
    }

    public static String getEmojiByUnicode(int unicode) {
        return new String(Character.toChars(unicode));
    }

    public static String getUserName(String emailId) {
        if (emailId == null)
            return null;
        String[] fields = emailId.split("@");
        if (fields.length == 2) {
            return fields[0];
        }
        return emailId;
    }

    public static String convertCaseSensitive(String dataName) {
        String convertName = Character.toUpperCase(dataName.charAt(0))
                + dataName.substring(1);
        return convertName;
    }

    public static String decodeEmoji(String message) {
        String myString = null;
        try {
            return URLDecoder.decode(message, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return message;
        }
    }

    public static String decodeMessage(String message) {
        message = message.replaceAll(":and:", "&");
        message = message.replaceAll(":plus:", "+");
        return StringEscapeUtils.unescapeJava(message);
    }


    private static void reloadBottomNavigation(Activity activity) {
      /*Intent returnData = new Intent(activity, BottomMenuActivity.class);
        returnData.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        returnData.putExtra("data", "1");
        activity.setResult(Activity.RESULT_OK, returnData);*/
        activity.finish();
        return;
    }

    public static boolean isCelebAndManager(Context mContext) {
        boolean isCelebManagerStatus = false;
        boolean isCeleb = false;
        boolean isManager = false;
        isCelebManagerStatus = Common.isCelebStatus(mContext) && Common.isManagerStatus(mContext);
        return isCelebManagerStatus;
    }


    public static boolean isCelebStatus(Context mContext) {
        boolean isCeleb = false;
        if (SessionManager.userLogin.isCeleb != null) {
            isCeleb = SessionManager.userLogin.isCeleb;
        }
        return isCeleb;
    }

    public static boolean isManagerStatus(Context mContext) {
        boolean isManager = false;
        if (SessionManager.userLogin.isManager != null) {
            isManager = SessionManager.userLogin.isManager;
        }
        return isManager;
    }

    public void celebOfflinePopup(Context mContext, String celebName) {
        final Dialog dialog = new Dialog(mContext);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.buycredits_chat);
        TextView contenttxt = (TextView) dialog.findViewById(R.id.contenttxt);
        Button chatbutton = (Button) dialog.findViewById(R.id.ok_chat);

        contenttxt.setText(celebName + " " + Constants.CELEB_OFFLINE + "");
        chatbutton.setText("Ok");
        dialog.show();

        chatbutton.setOnClickListener(v -> dialog.dismiss());

    }


    public static void getTermsAndConditions(Context mContext) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.getCommonDataCall(ApiClient.SERVICE_TERMS_CONDITION);

        IApiListener iApiListener = new IApiListener() {
            @Override
            public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {

                if (condition.equals(ApiClient.SERVICE_TERMS_CONDITION)) {
                    try {
                        Type type = new TypeToken<JsonElement>() {
                        }.getType();
                        JsonElement jsonElement = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                        if (jsonElement != null) {
                            String responsedata = jsonElement.toString();
                            JSONObject jsonObject = new JSONObject(responsedata);
                            String title = jsonObject.getString("title");
                            String text = jsonObject.getString("text");
                            Log.e("textTerms", text + "");

                            Common.termsAndConditionPopup(mContext, title, Common.replaceNewlinesWithBreaks(text));

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {

            }
        };
        Common.getInstance().callAPI(new ApiRequestModel().setModel(mContext, call, ApiClient.SERVICE_TERMS_CONDITION, true, iApiListener, true));
    }

    public static void termsAndConditionPopup(Context mcontext, String title, String termsdata) {
        Dialog promoDialog = new Dialog(mcontext);
//        promoDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        promoDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        promoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        promoDialog.setCancelable(false);
        promoDialog.setContentView(R.layout.termsandconditions_service);

        RelativeLayout proceedLayout = (RelativeLayout) promoDialog.findViewById(R.id.proceedLayout);
        proceedLayout.setVisibility(View.GONE);
        ImageView imageViewClose = (ImageView) promoDialog.findViewById(R.id.imageViewClose);
        Button button_accept = (Button) promoDialog.findViewById(R.id.button_accept);
        TextView knowtitle = (TextView) promoDialog.findViewById(R.id.knowtitle);
        knowtitle.setText(title + "");

        TextView contentdatatxt = (TextView) promoDialog.findViewById(R.id.contentdatatxt);
        contentdatatxt.setText(termsdata + "");
        promoDialog.show();
        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promoDialog.dismiss();
            }
        });
        button_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promoDialog.dismiss();
            }
        });

    }


    private void getContracts(Context context, String servicetype, ProfileDataModel profileDataModel) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        IApiListener iApiListenerLocal = new IApiListener() {
            @Override
            public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                if (condition.equals(Constants.CONTRACTS_FOR_CELEB)) {
                    ArrayList<ContractsSuccess> contractsSuccess = new ArrayList<>();
                    try {
                        Type type = new TypeToken<ArrayList<ContractsSuccess>>() {
                        }.getType();
                        contractsSuccess = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                        if (contractsSuccess.size() != 0) {
                            if (contractsSuccess.get(0).getServiceType() != null) {
                                if (contractsSuccess.get(0).getServiceType().equals(servicetype)) {
                                    profileDataModel.celeFanCharge = contractsSuccess.get(0).getServiceCredits().intValue();
                                    FanUnFanData fanUnFanData = new FanUnFanData(context, profileDataModel._id,
                                            contractsSuccess.get(0).getServiceCredits(),
                                            profileDataModel.firstName, profileDataModel.lastName,
                                            profileDataModel.avtar_imgPath, profileDataModel.profession);
                                    Common.getInstance().fanUnFanConfirmationDialog(true, fanUnFanData,
                                            iApiListener);

                                } else {
                                    Common.getInstance().showSweetAlertWarning(context, "CelebKonect",
                                            context.getResources().getString(R.string.no_contracts_avail));
                                }
                            } else {
                                Common.getInstance().showSweetAlertWarning(context, "CelebKonect",
                                        context.getResources().getString(R.string.no_contracts_avail));
                            }
                        } else {
                            Common.getInstance().showSweetAlertWarning(context, "CelebKonect",
                                    context.getResources().getString(R.string.no_contracts_avail));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
                //
            }
        };
        Call<ApiResponseModel> call = apiInterface.getBlockStatus(ApiClient.GET_CONTRACTS + profileDataModel._id
                + "/" + servicetype);
        callAPI(new ApiRequestModel().setModel(context, call, Constants.CONTRACTS_FOR_CELEB, true, iApiListenerLocal, true));
    }


    public Boolean isOnlineStatus() {
        boolean isOnline = false;
        if (SessionManager.userLogin.liveStatus != null) {
            if (SessionManager.userLogin.liveStatus.equals("TRUE")) {
                isOnline = true;
            } else {
                isOnline = false;
            }
        }
        return isOnline;
    }


    public static String isLoginId() {
        SessionManager.getInstance().getUserLoginData();
        String isLoginId = "";
        if (SessionManager.userLogin.userId != null
                && !SessionManager.userLogin.userId.isEmpty()) {
            isLoginId = SessionManager.userLogin.userId;
            //Log.e("loginId", isLoginId);
        }
        return isLoginId;

    }


    public static String loginFirstNameAndLastName() {
        String profileName = null;
        profileName = Common.getInstance().IsNullReturnValue(SessionManager.userLogin.firstName, "");
        if (profileName != null) {
            profileName = profileName + " " + Common.getInstance().IsNullReturnValue(SessionManager.userLogin.lastName, "");
        } else {
            profileName = "";
        }
        return profileName;
    }

    public static String loginProfilePic() {
        String profilePic = null;
        if (SessionManager.userLogin.profilePic != null && !SessionManager.userLogin.profilePic.isEmpty()) {
            profilePic = ApiClient.BASE_URL + SessionManager.userLogin.profilePic;
        } else {
            profilePic = null;
        }
        return profilePic;
    }


    public static String isLoginIdManger() {
        String isLoginId = "";
        if (SessionManager.userLogin.userId != null
                && !SessionManager.userLogin.userId.isEmpty()) {
            isLoginId = SessionManager.userLogin.userId;
        }
        return isLoginId;

    }


    public static String isMangerSwitchCarryingId() {
        String isMangerOwnId = "";
        if (SessionManager.getInstance().getKeyValue(SessionManager.KEY_MANAGER_OWN_ID, "") != null
                && !SessionManager.getInstance().getKeyValue(SessionManager.KEY_MANAGER_OWN_ID, "").isEmpty()) {
            isMangerOwnId = SessionManager.getInstance().getKeyValue(SessionManager.KEY_MANAGER_OWN_ID, "");
        }
        return isMangerOwnId;

    }

    public static String managingCelebId() {
        String managingCelebId = "";
        if (SessionManager.getInstance().getKeyValue(SessionManager.KEY_MANAGE_CELEB_ID, "") != null
                && !SessionManager.getInstance().getKeyValue(SessionManager.KEY_MANAGE_CELEB_ID, "").isEmpty()) {
            managingCelebId = SessionManager.getInstance().getKeyValue(SessionManager.KEY_MANAGE_CELEB_ID, "");
        }
        return managingCelebId;

    }


    public static boolean isBeingManager(String currentUserId) {
        boolean beingManger = false;
        String isMangerOwnId = "";
        if (SessionManager.getInstance().getKeyValue(SessionManager.KEY_MANAGER_OWN_ID, "") != null
                && !SessionManager.getInstance().getKeyValue(SessionManager.KEY_MANAGER_OWN_ID, "").isEmpty()) {
            isMangerOwnId = SessionManager.getInstance().getKeyValue(SessionManager.KEY_MANAGER_OWN_ID, "");
            if (isMangerOwnId.equals(currentUserId)) {
                beingManger = true;
            } else {
                beingManger = false;
            }

        }

        return beingManger;

    }

    public static boolean isSwicthed() {

        return false;

    }

    @Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {

    }

    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {

    }

    public void LogOut(Context context, String isLogoutpage, Integer successStatus) {
        try {
            new NotificationUtil().dismissAllNotifications(context);
            new NotificationUtil().dismissCallRunningNotification(context);
            if (!successStatus.equals(Constants.TOKEN_AUTHENTICATION_FAIL)) {
//                ChatSocket.getInstance().emitOffline(context);
                ChatSocket.getInstance().screenStatusEmit(false);
            }
            ChatSocket.getInstance().offSocketListeners();


            clearLocalData(context, isLogoutpage);
            SessionManager.getInstance().removeAll();
            SessionManager.getInstance().setKeyValue(SessionManager.KEY_CLEAR_LOCAL_DATA, true);
            Common.isFeedSessionStarted = false;

            ChatSocket.getInstance().exitUserFromApp(Utility.getContext());
            SocketForAppUtill.getInstance().onlineCelebEmit();
            Common.getInstance().disconnectSocket();


            Intent intent = new Intent(context, SignInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearLocalData(Context context, String islogout) {

        if (islogout.equals("true")) {
            Toast.makeText(context, "Logout.", Toast.LENGTH_SHORT).show();
        }
        SessionManager.getInstance().setKeyValue(SessionManager.KEY_FIRSTTIME_EDIT_PROFILE_ACCESS, "FALSE");
        SessionManager.getInstance().setKeyValue(SessionManager.KEY_SIGN_IN_ACCESS, "FALSE");
        SessionManager.getInstance().setKeyValue(SessionManager.KEY_PROFILE_PIC, "");
        SessionManager.getInstance().setKeyValue(SessionManager.KEY_FIRSTTIME_EDIT_PROFILE_ACCESS, "FALSE");
        SessionManager.getInstance().setKeyValue(SessionManager.KEY_IS_CELEB, false);
        SessionManager.getInstance().setKeyValue(SessionManager.KEY_FIRST_EDIT_PROFILE_NOT_COMPLETE, "FALSE");
        SessionManager.getInstance().setKeyValue(SessionManager.KEY_IS_MANAGER, false);
        SessionManager.getInstance().setKeyValue(SessionManager.KEY_FIRST_NAME, "");
        SessionManager.getInstance().setKeyValue(SessionManager.KEY_LAST_NAME, "");
        SessionManager.getInstance().setKeyValue(SessionManager.KEY_USER_ID, "");
        SessionManager.getInstance().setKeyValue(SessionManager.KEY_EMAIL_OR_MOBILE, "");
        SessionManager.getInstance().setKeyValue(SessionManager.KEY_LIVE_STATUS, "FALSE");
        SessionManager.getInstance().setKeyValue(SessionManager.KEY_LIVE_STATUS, "FALSE");
        SessionManager.getInstance().setKeyValue(SessionManager.KEY_MAIN_CREDITS, 0.0);
        SessionManager.getInstance().setKeyValue(SessionManager.KEY_REFERRAL_CREDITS, 0.0);
        SessionManager.getInstance().setKeyValue(SessionManager.KEY_MANAGER_OWN_ID, "");
        SessionManager.getInstance().setKeyValue(SessionManager.KEY_SWITCHIED_CELEBID, "");
        SessionManager.getInstance().setKeyValue(SessionManager.KEY_SWITCHIED_CELEB_USERNAME, "");
        SessionManager.getInstance().setKeyValue(SessionManager.KEY_AUDITION_ID, "");
        Common.getInstance().callBusyRelatedDataClear();
    }


    public void becomeFanSuccess(Context mContext, FanSuccessData fanSuccessData, String celebrityId) {
        try {
            ChatDataConvertModel chatDataConvertModel = new ChatDataConvertModel();
            chatDataConvertModel._id = fanSuccessData.getCelebInfo().get_id();
            chatDataConvertModel.isFan = true;
            Common.getInstance().addFanUnFanChatDataConvertModel(chatDataConvertModel);
            //
            try {
                SessionManager.getInstance().setKeyValue(SessionManager.KEY_MAIN_CREDITS, fanSuccessData.getCreditInfo().getCumulativeCreditValue());
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (CelebrityProfileFragment.getInstance() != null) {
                CelebrityProfileFragment.getInstance().getProfileDetails(chatDataConvertModel._id, true);
            }


            DecimalFormat twoDecimal = new DecimalFormat("0.00");
            double credit_double = Double.parseDouble(String.valueOf(fanSuccessData.creditInfo.getCumulativeCreditValue()));
            Common.getInstance().showSweetAlertSuccess(mContext, "CelebKonect",
                    mContext.getResources().getString(R.string.fan_status_message) + "\n" +
                            fanSuccessData.getCelebInfo().getFirstName() + ",\n Remaining balance : \n" +
                            twoDecimal.format(credit_double));

            Common.getInstance().setAvailableBalance(fanSuccessData.creditInfo.getCumulativeCreditValue());

            Common.getInstance().setRefferalCredits(fanSuccessData.creditInfo.getReferralCreditValue());
            ViewScheduleFragment viewScheduleFragment = ViewScheduleFragment.getInstance();
            if (viewScheduleFragment != null) {
                viewScheduleFragment.getScheduleDetails();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCreditBalance() {
        String creditsBalance = "";
        if (SessionManager.userLogin.mainCredits != null) {
            DecimalFormat twoDecimal = new DecimalFormat("0.00");
            double credit_double = SessionManager.userLogin.mainCredits;
            creditsBalance = String.valueOf(twoDecimal.format(credit_double));
        }
        return creditsBalance;
    }

    public String getCreditBalancetoShowInLabel() {
        String creditsBalance = "";
        boolean isSwitched = Common.isSwicthed();
        if (isSwitched) {
            if (SessionManager.userLogin.mainCredits != null) {
                DecimalFormat twoDecimal = new DecimalFormat("0.00");
                double credit_double = SessionManager.userLogin.mainCredits;
                creditsBalance = "Available Credits : " + String.valueOf(twoDecimal.format(credit_double));
            } else {
                creditsBalance = "Available Credits : " + "0.0";
            }

        } else {
            if (SessionManager.userLogin.mainCredits != null) {
                DecimalFormat twoDecimal = new DecimalFormat("0.00");
                double credit_double = SessionManager.userLogin.mainCredits;
                creditsBalance = "Available Credits : " + String.valueOf(twoDecimal.format(credit_double));
            } else {
                creditsBalance = "Available Credits : " + "0.0";
            }
        }
        return creditsBalance;
    }

    public double getCreditBalanceWithReferralCredits() {
        boolean isSwitched = Common.isSwicthed();
        if (SessionManager.userLogin.mainCredits != null) {
            return SessionManager.userLogin.mainCredits;
        }

        return 0.0;
    }

    public void setAvailableBalance(Double creditBal) {
        if (creditBal != null) {
            Log.e("availableBal", creditBal + "");
            SessionManager.getInstance().setKeyValue(SessionManager.KEY_MAIN_CREDITS, creditBal);
        } else {
            Log.e("availableBal", "no" + "");
            SessionManager.getInstance().setKeyValue(SessionManager.KEY_MAIN_CREDITS, 0.0);
        }

    }

    public void setRefferalCredits(Double creditBal) {
        if (creditBal != null) {
            SessionManager.getInstance().setKeyValue(SessionManager.KEY_REFERRAL_CREDITS, creditBal);
        } else {
            SessionManager.getInstance().setKeyValue(SessionManager.KEY_REFERRAL_CREDITS, 0.0);
        }

    }

    public void creditBalance(Context context, Boolean showProgress) {
        String myCartBalance = ApiClient.GET_CREDIT_BALANCE + SessionManager.userLogin.userId.toString();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.GET(myCartBalance);

        IApiListener iApiListenerFollow = new IApiListener() {
            @Override
            public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                try {
                    JSONObject jsonObject = new JSONObject(new Gson().toJson(apiResponseModel.data));
                    try {
                        Double credits = jsonObject.optDouble("cumulativeCreditValue", 0.0);
                        setAvailableBalance(credits);

                        Double referralCreditValue = jsonObject.optDouble("referralCreditValue", 0.0);
                        setRefferalCredits(referralCreditValue);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {

            }
        };

        callAPI(new ApiRequestModel().setModel(context, call, ApiClient.GET_CREDIT_BALANCE, showProgress,
                iApiListenerFollow, false));

    }


    public boolean findDuplicateMedia(Media mediaMain, List<Media> newAddedList) {
        try {
            if (mediaMain.mediaId != null && !mediaMain.mediaId.isEmpty()) {
                for (Media mediaInner : newAddedList) {
                    if (mediaMain.mediaId.equalsIgnoreCase(mediaInner.mediaId)) {
                        return true;
                    }
                }
            } else {
                for (Media mediaInner : newAddedList) {
                    if (mediaMain.type.equals(mediaInner.type) && mediaMain.mimeType.equals(mediaInner.mimeType) && mediaMain.name.equals(mediaInner.name)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public int findDuplicateMediaIndex(Media mediaMain, List<Media> newAddedList) {
        try {
            int index = -1;
            if (mediaMain.mediaId != null && !mediaMain.mediaId.isEmpty()) {
                for (Media mediaInner : newAddedList) {
                    index++;
                    if (mediaMain.mediaId.equalsIgnoreCase(mediaInner.mediaId)) {
                        return index;
                    }
                }
            } else {
                for (Media mediaInner : newAddedList) {
                    index++;
                    if (mediaMain.type.equals(mediaInner.type) && mediaMain.mimeType.equals(mediaInner.mimeType) && mediaMain.name.equals(mediaInner.name)) {
                        return index;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }


    @SuppressLint("StaticFieldLeak")
    public void createUpdateFeed(Feed feed, int position, boolean isFeedUpdating) {
        Utility.hideKeyboard(AppController.getInstance().getCurrentRegisteredActivity());
        if (FeedsFragment.getInstance() != null) {
            if (isFeedUpdating) {
                FeedsFragment.getInstance().showFeedUpdate(true, position);
                FeedsFragment.getInstance().isFeedUpdateRunning(true);
            } else {
                FeedsFragment.getInstance().showFeedProgress(true, 0);
            }
        }
        new AsyncTask<Void, Void, List<Media>>() {
            ProgressDialog progressDialog = null;
            MultipartBody.Part[] mediaMultiParts;
            int unique_id = 0;
            JSONObject postParams = new JSONObject();
            JSONArray mediaArray = new JSONArray();
            String folderName = "FeedThumbnail";

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Activity currentActivity = AppController.getInstance().getCurrentRegisteredActivity();
                if (currentActivity instanceof HelperActivity) {
                    progressDialog = Utility.generateProgressDialog(currentActivity);
                }
            }

            @Override
            protected List<Media> doInBackground(Void... voids) {
                if (feed.mediaList != null && feed.mediaList.size() > 0) {
                    FaceDetector detector = new FaceDetector.Builder(Utility.getContext())
                            .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                            .setMode(FaceDetector.ACCURATE_MODE)
                            .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                            .setTrackingEnabled(false)
                            .build();
                    for (int i = 0; i < feed.mediaList.size(); i++) {
                        if (feed.mediaList.get(i).type.equals(Constants.MEDIA_TYPE_IMAGE) && feed.mediaList.get(i).uri != null && (feed.mediaList.get(i).facePointList == null || feed.mediaList.get(i).facePointList.size() <= 0)) {
                            try {
                                File file = new File(Objects.requireNonNull(Utility.getPath(Utility.getContext(), feed.mediaList.get(i).uri)));
                                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                                if (detector.isOperational() && bitmap != null) {
                                    Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                                    SparseArray<Face> faces = detector.detect(frame);
                                    List<FacePoint> facePointList = new ArrayList<>();
                                    for (int j = 0; j < faces.size(); ++j) {
                                        Face face = faces.valueAt(j);
                                        FacePoint facePoint = new FacePoint();
                                        facePoint.x = face.getPosition().x;
                                        facePoint.y = face.getPosition().y;
                                        facePoint.width = face.getPosition().x + face.getWidth();
                                        facePoint.height = face.getPosition().y + face.getHeight();
                                        facePoint.faceCenterX = 0;
                                        facePoint.faceCenterY = 0;
                                        facePointList.add(facePoint);
                                    }
                                    feed.mediaList.get(i).facePointList = facePointList;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        try {
                            JSONObject mediaJson = new JSONObject();
                            if (feed.mediaList.get(i).mediaId != null) {
                                mediaJson.put("mediaId", feed.mediaList.get(i).mediaId);
                            }
                            mediaJson.put("mediaCaption", feed.mediaList.get(i).caption == null ? "" : feed.mediaList.get(i).caption);
                            mediaJson.put("mediaRatio", feed.mediaList.get(i).ratio);
                            mediaJson.put("mediaType", feed.mediaList.get(i).type == null ? "image" : feed.mediaList.get(i).type);
                            mediaJson.put("mediaSize", feed.mediaList.get(i).sizeKB);
                            JSONArray faceFeatures = new JSONArray();
                            if (feed.mediaList.get(i).facePointList != null && feed.mediaList.get(i).facePointList.size() > 0) {
                                Type type = new TypeToken<List<FacePoint>>() {
                                }.getType();
                                faceFeatures = new JSONArray(new Gson().toJson(feed.mediaList.get(i).facePointList, type));
                            }
                            mediaJson.put("faceFeatures", faceFeatures);
                            mediaArray.put(mediaJson);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        detector.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //
                    ArrayList<MultipartBody.Part> partArrayList = new ArrayList<>();
                    for (int index = 0; index < feed.mediaList.size(); index++) {
                        unique_id++;
                        try {
                            if (feed.mediaList.get(index).uri != null) {
                                File file;
                                if (feed.mediaList.get(index).type.equals(Constants.MEDIA_TYPE_DOC)) {
                                    file = new File(Objects.requireNonNull(feed.mediaList.get(index).dataFilePath));
                                } else {
                                    file = new File(Objects.requireNonNull(Utility.getPath(Utility.getContext(), feed.mediaList.get(index).uri)));
                                }
                                ProgressRequestBody fileBody = new ProgressRequestBody(file,
                                        feed.mediaList.get(index).mimeType, partArrayList.size(), new ProgressRequestBody.UploadCallbacks() {
                                    @Override
                                    public void onProgressUpdate(int percentage, int currentIndex) {
                                        Log.d("ProgressRequestBody", percentage + " @ " + currentIndex);
                                    }

                                    @Override
                                    public void onError() {

                                    }

                                    @Override
                                    public void onFinish() {
                                        Log.d("ProgressRequestBody", "onFinish");
                                    }
                                });
                                //RequestBody requestFile = RequestBody.create(MediaType.parse(feed.mediaList.get(index).mimeType), file);
                                MultipartBody.Part multiPart = MultipartBody.Part.createFormData("image", ("AND_" + unique_id + Utility.replaceSpecialCharacter(feed.mediaList.get(index).name, feed.mediaList.get(index).mimeType)).toLowerCase(), fileBody);
                                partArrayList.add(multiPart);
                                //
                                if (feed.mediaList.get(index).type.equals(Constants.MEDIA_TYPE_VIDEO) ||
                                        feed.mediaList.get(index).type.equals(Constants.MEDIA_TYPE_GIF)) {
                                    byte[] bytes = Objects.requireNonNull(Utility.getBytesThumbnail(feed.mediaList.get(index).uri));
                                    String fileName = ("AND_THUMBNAIL_" + unique_id + ".jpg").toLowerCase();
                                    File file1Thumbnail = createSDFile(fileName, folderName);
                                    try {
                                        if (!file1Thumbnail.exists()) {
                                            file1Thumbnail.createNewFile();
                                        }
                                        FileOutputStream fos = new FileOutputStream(file1Thumbnail);
                                        fos.write(bytes);
                                        fos.close();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    ProgressRequestBody fileBodyThumbnail = new ProgressRequestBody(file1Thumbnail, "image/jpeg", partArrayList.size(), new ProgressRequestBody.UploadCallbacks() {
                                        @Override
                                        public void onProgressUpdate(int percentage, int currentIndex) {
                                            Log.d("ProgressRequestBodyTN", percentage + " @ " + currentIndex);
                                        }

                                        @Override
                                        public void onError() {

                                        }

                                        @Override
                                        public void onFinish() {
                                            Log.d("ProgressRequestBodyTN", "onFinish");
                                        }
                                    });
                                    //RequestBody requestFileThumbnail = RequestBody.create(MediaType.parse("image/jpeg"), file1Thumbnail);
                                    MultipartBody.Part multiPartThumbnail = MultipartBody.Part.createFormData("image", fileName, fileBodyThumbnail);
                                    partArrayList.add(multiPartThumbnail);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (partArrayList.size() > 0) {
                        mediaMultiParts = new MultipartBody.Part[partArrayList.size()];
                        for (int index = 0; index < partArrayList.size(); index++) {
                            mediaMultiParts[index] = partArrayList.get(index);
                        }
                    }
                }
                try {
                    postParams.put("memberId", SessionManager.userLogin.userId);
                    postParams.put("title", feed.feedTitle);
                    postParams.put("content", feed.feedDescription);
                    postParams.put("location", feed.location == null ? "" : feed.location);
                    postParams.put("status", "Active");
                    /*postParams.put("countryCode", Common.getInstance().getCountryCode());
                    postParams.put("state", Common.getInstance().getStateName());*/
                    postParams.put("media", mediaArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return feed.mediaList;
            }

            @Override
            protected void onPostExecute(List<Media> result) {
                super.onPostExecute(result);
                //feedManager.createUserFeed(feed, successListener, errorListener);
                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                IApiListener apiListener = new IApiListener() {
                    @Override
                    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                        deleteSDFolder(folderName);
                        if (FeedsFragment.getInstance() != null) {
                            if (isFeedUpdating) {
                                FeedsFragment.getInstance().showFeedUpdate(false, position);
                                FeedsFragment.getInstance().isFeedUpdateRunning(false);
                                FeedsFragment.getInstance().swipeRefreshLayout.setEnabled(true);
                            } else {
                                FeedsFragment.getInstance().showFeedProgress(false, 0);
                            }
                        }
                        if (progressDialog != null) {
                            Utility.closeProgressDialog(progressDialog);
                        }
                        Activity currentActivity = AppController.getInstance().getCurrentRegisteredActivity();
                        Utility.notifyMessage(null, apiResponseModel.message);
                        if (currentActivity instanceof BottomMenuActivity) {
                            ((BottomMenuActivity) currentActivity).showSnackBar(apiResponseModel.message, 1);
                        } else if (currentActivity instanceof HelperActivity) {
                            ((HelperActivity) currentActivity).showSnackBar(apiResponseModel.message, 1);
                        }
                        //
                        Type type = new TypeToken<Feed>() {
                        }.getType();
                        Feed feedNew = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                        if (FeedsFragment.getInstance() != null) {
                            if (isFeedUpdating) {
                                if (currentActivity instanceof BottomMenuActivity) {
                                    ((BottomMenuActivity) currentActivity).updateFeed(feedNew, position);
                                } else if (currentActivity instanceof HelperActivity) {
                                    ((HelperActivity) currentActivity).updateFeed(feedNew, position);
                                    if (MyContentFragment.getInstance() != null) {
                                        MyContentFragment.getInstance().updateFeed(feedNew, position);
                                    }
                                }
                            } else {
                                FeedsFragment.getInstance().addFeed(feedNew);
                            }
                        }
                    }

                    @Override
                    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
                        deleteSDFolder(folderName);
                        if (FeedsFragment.getInstance() != null) {
                            if (isFeedUpdating) {
                                FeedsFragment.getInstance().showFeedUpdate(false, position);
                                FeedsFragment.getInstance().isFeedUpdateRunning(false);
                                FeedsFragment.getInstance().swipeRefreshLayout.setEnabled(true);
                            } else {
                                FeedsFragment.getInstance().showFeedProgress(false, 0);
                            }
                        }
                        if (progressDialog != null) {
                            Utility.closeProgressDialog(progressDialog);
                        }
                        Activity currentActivity = AppController.getInstance().getCurrentRegisteredActivity();
                        if (currentActivity instanceof BottomMenuActivity) {
                            ((BottomMenuActivity) currentActivity).showSnackBar(Constants.CONNECTION_ERROR, 2);
                        } else if (currentActivity instanceof HelperActivity) {
                            ((HelperActivity) currentActivity).showSnackBar(Constants.CONNECTION_ERROR, 2);
                        }
                    }
                };
                if (isFeedUpdating) {
                    Call<ApiResponseModel> call = apiInterface.updateFeed(feed.id, RequestBody.create(MediaType.parse("multipart/form-data"), postParams.toString()), mediaMultiParts);
                    callAPI(new ApiRequestModel().setModel(Utility.getContext(), call, Constants.FeedConstants.URL_EDIT_FEED, false, apiListener, false));
                } else {
                    Call<ApiResponseModel> call = apiInterface.createFeed(RequestBody.create(MediaType.parse("multipart/form-data"), postParams.toString()), mediaMultiParts);
                    callAPI(new ApiRequestModel().setModel(Utility.getContext(), call, Constants.FeedConstants.URL_CREATE_FEED, false, apiListener, false));
                }
            }
        }.execute();
    }

    public void deleteFeed(String id, int position, int type, String isFromWhich) {
        Activity currentActivity = AppController.getInstance().getCurrentRegisteredActivity();
        Utility.hideKeyboard(currentActivity);
        ProgressDialog progressDialog = Utility.generateProgressDialog(currentActivity);
        IApiListener iApiListener = new IApiListener() {
            @Override
            public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                if (progressDialog != null) {
                    Utility.closeProgressDialog(progressDialog);
                }
                if (currentActivity instanceof BottomMenuActivity) {
                    ((BottomMenuActivity) currentActivity).showSnackBar(apiResponseModel.message, 1);
                    if (type == 1) {
                        currentActivity.onBackPressed();
                    }
                } else if (currentActivity instanceof HelperActivity) {
                    ((HelperActivity) currentActivity).showSnackBar(apiResponseModel.message, 1);
                }
                if (FeedsFragment.getInstance() != null && !isFromWhich.equalsIgnoreCase("MyContent")) {
                    FeedsFragment.getInstance().deleteFeed(position);
                }
                if (MyContentFragment.getInstance() != null) {
                    MyContentFragment.getInstance().deleteFeed(position);
                }
            }

            @Override
            public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
                if (progressDialog != null) {
                    Utility.closeProgressDialog(progressDialog);
                }
                if (currentActivity instanceof BottomMenuActivity) {
                    ((BottomMenuActivity) currentActivity).showSnackBar(Constants.CONNECTION_ERROR, 2);
                } else if (currentActivity instanceof HelperActivity) {
                    ((HelperActivity) currentActivity).showSnackBar(Constants.CONNECTION_ERROR, 2);
                }
            }
        };
        //
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.DELETE(Constants.FeedConstants.URL_DELETE_FEED + id);
        callAPI(new ApiRequestModel().setModel(currentActivity, call, Constants.FeedConstants.URL_DELETE_FEED, false, iApiListener, false));
    }

    public void addLike(String feedOrMediaId, String celebId, String feedId, int isLike,
                        boolean isFeed, IApiListener iApiListener) {
        JSONObject input = new JSONObject();
        try {
            input.put("memberId", SessionManager.userLogin.userId);
            input.put("feedId", feedId);
            input.put("celebId", celebId);
            input.put(isFeed ? "feedId" : "mediaId", feedOrMediaId);
            input.put("isLike", Common.getInstance().getBooleanFromInt(isLike) ? 0 : 1);

            input.put("activities", Constants.ACTION_TYPE_LIKE);
            input.put("status", "Active");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.POST(Constants.FeedConstants.URL_ADD_LIKE, RequestBody.create(MediaType.parse("application/json; charset=utf-8"), input.toString()));
        callAPI(new ApiRequestModel().setModel(AppController.getInstance().getCurrentRegisteredActivity(), call, Constants.FeedConstants.URL_ADD_LIKE, false, iApiListener, false));
    }


    public File createSDFile(String FileName, String FolderName) {
        File dir = new File(Environment.getExternalStorageDirectory(), FolderName);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
        }
        File Destination = new File(dir, FileName);
        if (Destination.exists()) {
            Destination.delete();
        }
        try {
            Destination.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Destination;
    }

    public File createInternalFile(Context context, String FileName) {
        String appPath = context.getFilesDir().getAbsolutePath();
        File dir = new File(appPath, "Temp");
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
        }
        File Destination = new File(dir, FileName);
        if (Destination.exists()) {
            Destination.delete();
        }
        try {
            Destination.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Destination;
    }

    public void deleteInternalFolder(Context context) {
        try {
            String appPath = context.getFilesDir().getAbsolutePath();
            File dir = new File(appPath, "Temp");
            if (dir.exists() && dir.isDirectory()) {
                String[] children = dir.list();
                assert children != null;
                for (String child : children) {
                    new File(dir, child).delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteSDFolder(String FolderName) {
        try {
            String SDPath = Environment.getExternalStorageDirectory().toString();
            File dir = new File(SDPath, FolderName);
            if (dir.exists() && dir.isDirectory()) {
                String[] children = dir.list();
                assert children != null;
                for (String child : children) {
                    new File(dir, child).delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getRealPathFromURI(Context context, Uri contentURI) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is DropBox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }


    public static String isReceiverId() {
        String isReceiverId = "";
        if (SessionManager.getInstance().getKeyValue(SessionManager.KEY_IS_RECEIVERID, "") != null && !SessionManager.getInstance().getKeyValue(SessionManager.KEY_IS_RECEIVERID, "").isEmpty()) {
            isReceiverId = SessionManager.getInstance().getKeyValue(SessionManager.KEY_IS_RECEIVERID, "");
            Log.e("isReceiverId", isReceiverId + "_OnCall");
        }
        return isReceiverId;

    }


    public static void callBusyRelatedDataClear() {
        SessionManager.getInstance().setKeyValue(SessionManager.KEY_IS_CALL_INITIATE, false);
        SessionManager.getInstance().setKeyValue(SessionManager.KEY_IS_RECEIVERID, false);
    }


    public boolean isLoginInApp() {
        SessionManager.getInstance().getUserLoginData();
        boolean isLoginApp = false;
        if (SessionManager.userLogin.userId != null && !SessionManager.userLogin.userId.isEmpty()) {
            isLoginApp = true;
        }
        return isLoginApp;
    }


    public static char[] c = new char[]{'k', 'm', 'b', 't'};

    public String coolFormat(double n, int iteration) {

        if (n < 1000) {
            return (int) n + "";
        }
        double d = ((long) n / 100) / 10.0;
        boolean isRound = (d * 10) % 10 == 0;//true if the decimal part is equal to 0 (then it's trimmed anyway)
        return (d < 1000 ? //this determines the class, i.e. 'k', 'm' etc
                ((d > 99.9 || isRound || (!isRound && d > 9.99) ? //this decides whether to trim the decimals
                        (int) d * 10 / 10 : d + "" // (int) d * 10 / 10 drops the decimal
                ) + "" + c[iteration])
                : coolFormat(d, iteration + 1));

    }


    public static String convertCentimeterToHeight(double d) {
        int feetPart = 0;
        int inchesPart = 0;
        if (String.valueOf(d) != null && String.valueOf(d).trim().length() != 0) {
            feetPart = (int) Math.floor((d / 2.54) / 12);
            inchesPart = (int) Math.floor((d / 2.54) - (feetPart * 12));
        }
        String inFeets = String.valueOf(feetPart) + " Ft " + String.valueOf(inchesPart) + " Inches";
        return inFeets;
    }


    public static String convertKgsToPounds(double d) {
        String inPounds = "";
        double poundsWeight = 0;
        poundsWeight = d / 0.45359237;

        DecimalFormat twoDecimal = new DecimalFormat("0.00");
        inPounds = String.valueOf(twoDecimal.format(poundsWeight));

        return inPounds;
    }

    /**
     * Checks if the application is being sent in the background (i.e behind
     * another application's Activity).
     *
     * @param context the context
     * @return <code>true</code> if another application will be above this one.
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static boolean isApplicationSentToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                topActivity = tasks.get(0).topActivity;
            }
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                Log.e("AppIsBackgroundState", true + "");
                return true;
            }
        } else {
            Log.e("AppIsBackgroundState", false + "");
        }

        return false;
    }

    public boolean isValidMobilelenght(String phone, int minlenght, int maxlength) {
        boolean check = false;
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            if (phone.length() < minlenght || phone.length() > maxlength) {
                // if(phone.length() != 10) {
                check = false;
                // txtPhone.setError("Not Valid Number");
            } else {
                check = Patterns.PHONE.matcher(phone).matches();
            }
        } else {
            check = false;
        }
        return check;
    }


    public void backgroundStateCall() {
        if (!isLoginInApp()) {
            return;
        }
        Log.d(TAG, "Running screen locked");
        IApiListener iApiListener = new IApiListener() {
            @Override
            public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                Log.d(TAG, "Response : success");

            }

            @Override
            public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
                Log.d(TAG, "Response : failure ");
            }
        };
        Boolean previousLiveStatus = Common.getInstance().isOnlineStatus();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("memberId", SessionManager.userLogin.userId);
            if (previousLiveStatus) {
                jsonObject.put("liveStatus", "offline");
            } else {
                jsonObject.put("liveStatus", "online");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        Call<ApiResponseModel> call = apiInterface.POST(ApiClient.PASS_LOG_STATUS, requestBody);
        callAPI(new ApiRequestModel().setModel(Utility.getContext(), call, ApiClient.PASS_LOG_STATUS, false, iApiListener, false));
    }

    public boolean screenLockcheck() {
        boolean isLocked = false;
        KeyguardManager myKM = (KeyguardManager) Utility.getContext().getSystemService(Context.KEYGUARD_SERVICE);
        if (myKM.inKeyguardRestrictedInputMode()) {
            //it is locked
            Log.e("it is locked", "true");
            isLocked = true;

        } else {
            //it is not locked
            Log.e("it is locked", "false");
            isLocked = false;
        }
        return isLocked;
    }

    public void setMargins(View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }

    public void showToastDuration(String contentData) {

        // Set the toast and duration
        int toastDurationInMilliSeconds = 50000;
        mToastToShow = Toast.makeText(Utility.getContext(), "" + contentData, Toast.LENGTH_LONG);

        // Set the countdown to display the toast
        CountDownTimer toastCountDown;
        toastCountDown = new CountDownTimer(toastDurationInMilliSeconds, 1000 /*Tick duration*/) {
            public void onTick(long millisUntilFinished) {
                mToastToShow.show();
            }

            public void onFinish() {
                mToastToShow.cancel();
            }
        };

        // Show the toast and starts the countdown
        mToastToShow.show();
        toastCountDown.start();
    }


    public void setSingleImage(SimpleDraweeView simpleDraweeView, String imageUrl) {
        try {
            Log.e("imageUrl", imageUrl + "");
            PipelineDraweeControllerBuilder pipelineDraweeControllerBuilder = Fresco.newDraweeControllerBuilder();
            pipelineDraweeControllerBuilder.setImageRequest(ImageRequest.fromUri(ApiClient.BASE_URL + Uri.parse(imageUrl)));
            pipelineDraweeControllerBuilder.setControllerListener(new BaseControllerListener<ImageInfo>() {
                @Override
                public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                    super.onFinalImageSet(id, imageInfo, animatable);
                }
            });
            simpleDraweeView.setController(pipelineDraweeControllerBuilder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public String getRealPathFromUriIma(Context context, Uri contentUri) {
        String filePath;
        Cursor cursor = context.getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            filePath = contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            filePath = cursor.getString(idx);
            cursor.close();
        }
        return filePath;
    }


    public boolean isBluetoothHeadsetConnected() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()
                && mBluetoothAdapter.getProfileConnectionState(BluetoothHeadset.HEADSET) == BluetoothHeadset.STATE_CONNECTED;
    }

    public boolean isHeadPhoneConnect(Context context) {
        AudioManager am1 = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        Log.i("WiredHeadsetOn = ", am1.isWiredHeadsetOn() + "");
        return am1.isWiredHeadsetOn();
    }

    public boolean stopOtherBackgroundMusic(Context context) {
        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int result = am.requestAudioFocus(focusChange -> {
            switch (focusChange) {
                case (AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK):
                    break;
                case (AudioManager.AUDIOFOCUS_LOSS_TRANSIENT):
                    break;
                case (AudioManager.AUDIOFOCUS_LOSS):
                    break;
                case (AudioManager.AUDIOFOCUS_GAIN):
                    break;
                default:
                    break;
            }
        }, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        return result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
    }


    public void openKonectPopup(Context context, KonectData konectData, IKonectDailogClick
            iKonectDailogClick) {
        androidx.fragment.app.FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        konectCelebDailog = new KonectCelebDailog();
        konectCelebDailog.setData(konectData, iKonectDailogClick);
        konectCelebDailog.show(fragmentManager, "KonectCelebDailog");
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public Bitmap addWatermark(Bitmap source) {


        int sourceWidth = source.getWidth();
        int sourceHeight = source.getHeight();
        Bitmap sourcebmp = Bitmap.createBitmap(sourceWidth, sourceHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(sourcebmp);
        canvas.drawBitmap(source, 0, 0, null);
        //Watermark Image Added
        Bitmap waterMark = BitmapFactory.decodeResource(Utility.getActivity().getResources(), R.drawable.watermark_png);
        int maxSize = (int) (sourceWidth * 0.235);

        waterMark = getResizedBitmap(waterMark, maxSize);
        int topDistance = (int) (sourceHeight - (waterMark.getHeight()));
        int rightDistance = (int) (sourceWidth - (waterMark.getWidth()));

        canvas.drawBitmap(waterMark, rightDistance, topDistance, null);
        return sourcebmp;
    }

    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public String getSizeName(Context context) {
        int screenLayout = context.getResources().getConfiguration().screenLayout;
        screenLayout &= Configuration.SCREENLAYOUT_SIZE_MASK;

        switch (screenLayout) {
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                return "small";
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                return "normal";
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                return "large";
            case 4: // Configuration.SCREENLAYOUT_SIZE_XLARGE is API >= 9
                return "xlarge";
            default:
                return "undefined";
        }
    }

    public String getLoginStatus(Context context) {
        String loginType = "member";
        if (Common.isCelebAndManager(context)) {
            loginType = "manager";
        } else if (Common.isCelebStatus(context)) {
            loginType = "celeb";
        } else {
            loginType = "member";
        }
        return loginType;
    }

    private DownloadFile mDownloadFile;
    private String downloadFileName = "";

    public void downloadFileFromUrl(String fileName, String fileType, ProgressBar
            progressBarDown, ImageView imgDownload) {
        if (mDownloadFile != null && mDownloadFile.getStatus() == AsyncTask.Status.RUNNING) {
            if (downloadFileName.equals(fileName)) {
                Toast.makeText(Utility.getContext(), "In Progress please wait..", Toast.LENGTH_LONG).show();
            } else {
                progressBarDown.setVisibility(View.VISIBLE);
                imgDownload.setVisibility(View.GONE);
                downloadFileName = fileName;

                mDownloadFile = new DownloadFile(fileType, fileName, progressBarDown, imgDownload);
                mDownloadFile.execute(fileName);
            }
        } else {
            progressBarDown.setVisibility(View.VISIBLE);
            imgDownload.setVisibility(View.GONE);
            downloadFileName = fileName;
            mDownloadFile = new DownloadFile(fileType, fileName, progressBarDown, imgDownload);
            mDownloadFile.execute(fileName);
        }
    }

    @SuppressLint("StaticFieldLeak,SyntheticAccessor")
    private class DownloadFile extends AsyncTask<String, Integer, String> {
        String fileType, fileNameWaterMark;
        ProgressBar progressBarDown;
        ImageView imgDownload;
        File fileOrg, fileWaterMark;
        int videoWidth = 0, videoHeight = 0, videoDuration = 0;

        public DownloadFile(String fileType, String fileName, ProgressBar progressBarDown, ImageView imgDownload) {
            this.fileType = fileType;
            this.fileNameWaterMark = fileName;
            this.progressBarDown = progressBarDown;
            this.imgDownload = imgDownload;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String StorageFile = "";
            try {
                //For image Save with WaterMark Image
                if (fileType.equals("image")) {
                    StorageFile = saveImageToExternalStorage(addWatermark(getBitmapFromURL(fileNameWaterMark)));

                } else if (fileType.equals("video")) {
                    Integer count;
                    URL url = new URL(params[0]);
                    URLConnection connection = url.openConnection();
                    connection.connect();
                    InputStream input = new BufferedInputStream(url.openStream(), 8192);
                    //
                    long uniqueNumber = System.currentTimeMillis() / 1000;
                    String fileName = "videoOrg-" + uniqueNumber + ".mp4";
                    fileNameWaterMark = "video-" + uniqueNumber + ".mp4";
                    fileOrg = CreateInternalFile(fileName, "Temp");
//                    fileWaterMark = CreateSDCardFile(fileNameWaterMark, Constants.CELEB_KONECT_VIDEOS_FOLDER);
                    //
                    String root = Environment.getExternalStoragePublicDirectory("Celebkonect").toString();
                    File myDir = new File(root + "/CelebKonect videos");
                    myDir.mkdirs();
                    fileWaterMark = new File(myDir, fileNameWaterMark);
                    //
                    new SingleMediaScanner(Utility.getContext(), fileOrg);
                    OutputStream output = new FileOutputStream(fileOrg);
                    Double sentData = 0.0;
                    Double fileLength = Double.parseDouble(String.valueOf(connection.getContentLength()));
                    byte data[] = new byte[1024];
                    while ((count = input.read(data)) != -1) {
                        output.write(data, 0, count);
                        sentData += count;
                        if (fileLength > 0) {
                            int progress = (int) ((sentData / fileLength) * 100);
                            publishProgress(progress);
                        }
                    }
                    output.flush();
                    output.close();
                    input.close();
                    //
                    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                    retriever.setDataSource(fileOrg.getAbsolutePath());
                    videoDuration = Integer.valueOf(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
                    videoWidth = Integer.valueOf(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
                    videoHeight = Integer.valueOf(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
                    retriever.release();
                    //
                    return "Downloaded at: " + fileWaterMark.getAbsolutePath();
                }
            } catch (Exception e) {
                Log.e("errorOccur", "TRy" + "");
                e.printStackTrace();
            }
            return StorageFile;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.v("videoDownloadProgress", "" + values[0]);
            if (Common.getInstance().getVideoPlayerFragment() != null) {
                // Common.getInstance().getVideoPlayerFragment().showProgress(values[0]);
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (fileType.equals("image")) {
                hideProgressDeleteFile(result, fileType, progressBarDown, imgDownload, fileOrg, true);
            } else if (fileType.equals("video")) {
                FFmpeg ffmpeg = FFmpeg.getInstance(AppController.getInstance().getCurrentRegisteredActivity());
                if (ffmpeg == null) {
                    Common.getInstance().cusToast(AppController.getInstance().getCurrentRegisteredActivity(), "FFmpeg initial failed");
                    return;
                }
                if (fileOrg == null || fileWaterMark == null) {
                    hideProgressDeleteFile(result, fileType, progressBarDown, imgDownload, fileOrg, false);
                    Common.getInstance().cusToast(AppController.getInstance().getCurrentRegisteredActivity(), "Files not found");
                    return;
                }
                try {
                    ffmpeg.loadBinary(new LoadBinaryResponseHandler() {
                        @Override
                        public void onStart() {
                        }

                        @Override
                        public void onFailure() {
                            hideProgressDeleteFile(result, fileType, progressBarDown, imgDownload, fileOrg, false);
                        }

                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onFinish() {
                        }
                    });
                } catch (Exception e) {
                    hideProgressDeleteFile(result, fileType, progressBarDown, imgDownload, fileOrg, false);
                }
                try {
                    int startMs = 0, endMs = 2;
                    if (videoWidth <= 0) {
                        videoWidth = Utility.getScreenWidth(AppController.getInstance().getCurrentRegisteredActivity());
                    }
                    int videoHeightDiff = (int) (videoHeight * 0.04);
                    String[] trimCommand = new String[]{"-ss", "" + startMs, "-y", "-i", fileOrg.getAbsolutePath(), "-t", "" + endMs, "-vcodec", "mpeg4", "-b:v", "2097152", "-b:a", "48000", "-ac", "2", "-ar", "22050", fileWaterMark.getAbsolutePath()};
                    Bitmap waterMark = getBitmap(AppController.getInstance().getCurrentRegisteredActivity(), R.drawable.watermark_png);
                    int maxSize = 1;
                    if (videoWidth > videoHeight) {
                        maxSize = (int) (videoHeight * 0.235);
                    } else {
                        maxSize = (int) (videoWidth * 0.235);
                    }

                    waterMark = getResizedBitmap(waterMark, maxSize);
                    String waterMarkImagePath = saveImageToInternalStorage(waterMark);
                    String[] waterMarkCommand = new String[]{"-i", fileOrg.getAbsolutePath(), "-i", waterMarkImagePath, "-filter_complex", "overlay=(main_w-overlay_w):(main_h-overlay_h)", "-strict", "-2", "-b:a", "32k",
                            "-r", "15", "-preset", "ultrafast", fileWaterMark.getAbsolutePath()};
                    String finalCommand = Arrays.toString(waterMarkCommand);
                    Log.v("ffmpeg command", finalCommand);
                    ffmpeg.execute(waterMarkCommand, new ExecuteBinaryResponseHandler() {

                        @Override
                        public void onStart() {
                        }

                        @Override
                        public void onProgress(String message) {
                            Log.v("videoDownloadFFMPEG", message);
                            Pattern timePattern = Pattern.compile("(?<=time=)[\\d:.]*");
                            Scanner sc = new Scanner(message);
                            String match = sc.findWithinHorizon(timePattern, 0);
                            if (match != null) {
                                String[] matchSplit = match.split(":");
                                if (videoDuration > 0) {
                                    float progress = (Integer.parseInt(matchSplit[0]) * 3600 +
                                            Integer.parseInt(matchSplit[1]) * 60 +
                                            Float.parseFloat(matchSplit[2])) / videoDuration;
                                    float showProgress = (progress * 100) * 1000;
                                    Log.d("videoDownloadFFMPEG_Per", "" + (int) showProgress);
                                    if (Common.getInstance().getVideoPlayerFragment() != null) {
                                        Common.getInstance().getVideoPlayerFragment().showProgress((int) showProgress);
                                    }
                                }
                            }

                        }

                        @Override
                        public void onFailure(String message) {
                            hideProgressDeleteFile(result, fileType, progressBarDown, imgDownload, fileOrg, false);
                        }

                        @Override
                        public void onSuccess(String message) {
                            new SingleMediaScanner(Utility.getContext(), fileWaterMark);
                        }

                        @Override
                        public void onFinish() {
                            hideProgressDeleteFile(result, fileType, progressBarDown, imgDownload, fileOrg, true);
                        }
                    });
                } catch (Exception e) {
                    hideProgressDeleteFile(result, fileType, progressBarDown, imgDownload, fileOrg, false);
                    Common.getInstance().cusToast(AppController.getInstance().getCurrentRegisteredActivity(), Constants.SOMETHING_WENT_WRONG);
                }
            }
            super.onPostExecute(result);
        }
    }

    private void hideProgressDeleteFile(String result, String fileType, ProgressBar
            progressBarDown, ImageView imgDownload, File file, boolean isDownloadCompleted) {
        progressBarDown.setVisibility(View.GONE);
        imgDownload.setVisibility(View.VISIBLE);
        if (file != null) {
            file.delete();
        }
        if (isDownloadCompleted) {
            try {
                if (result != null && !result.isEmpty()) {
                    JSONObject objdata = new JSONObject();
                    objdata.put("fileName", result);
                    objdata.put("message", Common.convertCaseSensitive(fileType) + " Saved Successfully");
                    new NotificationUtil().setSaveFileNotification(Utility.getContext(), objdata);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Common.getInstance().cusToast(AppController.getInstance().getCurrentRegisteredActivity(), "Download has failed, please try again");
        }
        String appPath = AppController.getInstance().getCurrentRegisteredActivity().getFilesDir().getAbsolutePath();
        File dir = new File(appPath, "Temp");
        deleteRecursive(dir);
        /*if (dir.isDirectory()) {
            String[] children = dir.list();
            assert children != null;
            for (int i = 0; i < children.length; i++) {
                new File(dir, children[i]).delete();
            }
            dir.delete();
        }*/
    }

    public void deleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory()) {
            for (File child : fileOrDirectory.listFiles()) {
                deleteRecursive(child);
            }
        }
        fileOrDirectory.delete();
    }

    private String saveImageToInternalStorage(Bitmap finalBitmap) {
        File file = CreateInternalFile("WaterMark.png", "Temp");
        try {
            new SingleMediaScanner(Utility.getContext(), file);
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    public File CreateSDCardFile(String fileName, String folderName) {
        String appPath = Environment.getExternalStoragePublicDirectory(folderName).toString();
        File dir = new File(appPath);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
        }
        File Destination = new File(dir, fileName);
        if (Destination.exists()) {
            Destination.delete();
        }
        try {
            Destination.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Destination;
    }

    public File CreateInternalFile(String FileName, String folderName) {
        String appPath = AppController.getInstance().getCurrentRegisteredActivity().getFilesDir().getAbsolutePath();
        File dir = new File(appPath, "/" + folderName);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
        }
        File Destination = new File(dir, FileName);
        if (Destination.exists()) {
            Destination.delete();
        }
        try {
            Destination.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Destination;
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();

            Bitmap myBitmap = BitmapFactory.decodeStream(input);

            ExifInterface exif = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                exif = new ExifInterface(input);
            }
            int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            int rotationInDegrees = exifToDegrees(rotation);
            Log.e("rotation", rotation + "");

            Matrix matrix = new Matrix();
            if (rotation != 0f) {
                matrix.preRotate(rotationInDegrees);
                myBitmap = Bitmap.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(), myBitmap.getHeight(), matrix, true);
            }

            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public JSONObject getCompleteAddress(Context mContext) {
        //Address[addressLines=[0:"1251, Rd Number 62, Jubilee Hills, Hyderabad, Telangana 500033, India"],feature=1251,admin=Telangana,sub-admin=Hyderabad,locality=Hyderabad,thoroughfare=Road Number 62,postalCode=500033,countryCode=IN,countryName=India,hasLatitude=true,latitude=17.4274499,hasLongitude=true,longitude=78.4113112,phone=null,url=null,extras=null]
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        JSONObject locationObj = new JSONObject();
        String address = "Could not find location :(";
        try {
            List<Address> listAddresses = geocoder.getFromLocation(currentLocation.getLatitude(),
                    currentLocation.getLongitude(), 1);

            if (listAddresses != null && listAddresses.size() > 0) {
                /*if (listAddresses.get(0).getThoroughfare() != null) {
                    address = listAddresses.get(0).getThoroughfare() + " ";
                }*/

                if (listAddresses.get(0).getSubLocality() != null) {
                    address = listAddresses.get(0).getSubLocality();
                }

                if (listAddresses.get(0).getLocality() != null) {
                    address += listAddresses.get(0).getLocality() + " ";
                }

                if (listAddresses.get(0).getPostalCode() != null) {
                    address += listAddresses.get(0).getPostalCode() + " ";
                }

                /*if (listAddresses.get(0).getSubAdminArea() != null) {
                    address += listAddresses.get(0).getSubAdminArea();
                }*/


                locationObj.put("latitude", currentLocation.getLatitude());
                locationObj.put("longitude", currentLocation.getLatitude());
                locationObj.put("areaName", address);

            }
            Log.i("CompleteAddress", locationObj.toString());

        } catch (Exception e) {
            e.printStackTrace();
            locationObj = new JSONObject();
        }

        return locationObj;
    }

    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static Bitmap overlay(Bitmap bmp1, Bitmap bmp2) {

        Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());

        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(bmp1, new Matrix(), null);
        canvas.drawBitmap(bmp2, 0, 0, null);

        return bmOverlay;
    }

    class SingleMediaScanner implements MediaScannerConnection.MediaScannerConnectionClient {

        private MediaScannerConnection mMs;
        private File mFile;

        public SingleMediaScanner(Context context, File f) {
            mFile = f;
            mMs = new MediaScannerConnection(context, this);
            mMs.connect();
        }

        public void onMediaScannerConnected() {
            mMs.scanFile(mFile.getAbsolutePath(), null);
        }

        public void onScanCompleted(String path, Uri uri) {
            mMs.disconnect();
        }
    }

    public String saveImageToExternalStorage(Bitmap finalBitmap) {
        long uniqueNumber = System.currentTimeMillis() / 1000;
        String fileName = "IMAGE_" + uniqueNumber + ".jpg";
        File file = Common.getInstance().CreateSDCardFile(fileName, Constants.CELEB_KONECT_IMAGES_FOLDER);
        try {
            new SingleMediaScanner(Utility.getContext(), file);
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file.toString();
    }

    public static Bitmap getBitmap(Context context, @DrawableRes int drawableResId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableResId);
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof VectorDrawableCompat) {
            return getBitmap((VectorDrawableCompat) drawable);
        } else if (drawable instanceof VectorDrawable) {
            return getBitmap((VectorDrawable) drawable);
        } else {
            throw new IllegalArgumentException("Unsupported drawable type");
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static Bitmap getBitmap(VectorDrawable vectorDrawable) {
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return bitmap;
    }

    private static Bitmap getBitmap(VectorDrawableCompat vectorDrawable) {
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return bitmap;
    }


   /* public String checkServerTimer(IServerTimer iServerTimer) {
        currentServerTime = "";
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.GET(ApiClient.GET_SERVER_TIME);
        Common.getInstance().callAPI(new ApiRequestModel().setModel(Utility.getContext(), call, ApiClient.GET_SERVER_TIME,
                true, new IApiListener() {
                    @Override
                    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                        if (condition.equals(ApiClient.GET_SERVER_TIME)) {
                            try {
                                Type type = new TypeToken<ServerTime>() {
                                }.getType();
                                ServerTime serverTime = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                                currentServerTime = serverTime.currentTime;
                                Log.e("serverTime", currentServerTime);
                                iServerTimer.getCurrentTime(currentServerTime);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
                        iServerTimer.getCurrentTime("");
                    }
                }, true));

        return currentServerTime;
    }*/


//    public Bitmap rotateImage(Bitmap bitmap, String filePath) {
//        Bitmap resultBitmap = bitmap;
//
//        try {
//            ExifInterface exifInterface = new ExifInterface(filePath);
//            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
//
//            Matrix matrix = new Matrix();
//
//            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
//                matrix.postRotate(ExifInterface.ORIENTATION_ROTATE_90);
//            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
//                matrix.postRotate(ExifInterface.ORIENTATION_ROTATE_180);
//            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
//                matrix.postRotate(ExifInterface.ORIENTATION_ROTATE_270);
//            }
//
//            // Rotate the bitmap
//            resultBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
//        } catch (Exception exception) {
//            Logger.d("Could not rotate the image");
//        }
//        return resultBitmap;
//    }

    private static int exifToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

}
