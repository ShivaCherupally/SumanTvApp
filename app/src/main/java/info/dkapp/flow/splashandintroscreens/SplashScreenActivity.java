package info.dkapp.flow.splashandintroscreens;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import info.dkapp.flow.bottommenu.activity.HelperActivity;

import info.dkapp.flow.R;
import info.dkapp.flow.celebflow.SignInActivity;
import info.dkapp.flow.celebflow.constants.Constants;
import info.dkapp.flow.celebflow.dialogs.UpdateAppDialog;
import info.dkapp.flow.celebflow.interfaces.activities.IUpdateAppDialog;
import info.dkapp.flow.chat.NotificationUtil;
import info.dkapp.flow.retrofitcall.ApiClient;
import info.dkapp.flow.retrofitcall.ApiInterface;
import info.dkapp.flow.retrofitcall.ApiRequestModelJSON;
import info.dkapp.flow.retrofitcall.ApiResponseModel;
import info.dkapp.flow.retrofitcall.EnumConstants;
import info.dkapp.flow.retrofitcall.IApiListener;
import info.dkapp.flow.retrofitcall.SessionManager;
import info.dkapp.flow.splashandintroscreens.TutorialScreens.IntroductionActivityNew;
import info.dkapp.flow.splashandintroscreens.TutorialScreens.TutorialData;
import info.dkapp.flow.userflow.Util.Common;
import info.dkapp.flow.userflow.Util.ImagesData;
import info.dkapp.flow.utils.Logger;
import info.dkapp.flow.utils.Utility;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Shiva on 12/18/2017.
 */

public class SplashScreenActivity extends AppCompatActivity implements IUpdateAppDialog, IApiListener {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 500;
    ApiInterface apiInterface;
    ArrayList<TutorialData> _tutorialData = new ArrayList<TutorialData>();
    UpdateAppDialog updateAppDialog = null;
    public static ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();
    List<TutorialData> rowItems = new ArrayList<TutorialData>();
    List<ImagesData> imagesData = new ArrayList<ImagesData>();
    ProgressBar progressBar;
    String currenctyType = "IN";
    IUpdateAppDialog iUpdateAppDialog;
    String previousVersion = null;
    IApiListener iApiListener;
    int RC_APP_UPDATE = 60007;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        iApiListener = this;
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        Common.isFeedSessionStarted = false;
        iUpdateAppDialog = this;

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                SessionManager.getInstance().getUserLoginData();
                Common.getInstance().setSocket(null);
                new NotificationUtil().dismissCallRunningNotification(getApplicationContext());
                new NotificationUtil().dismissCallIncomingCallNotification(getApplicationContext());
                Common.getInstance().callBusyRelatedDataClear();
                Common.chatDataConvertModelFromNotification = null;
                try {
                    int history = (getIntent().getFlags() & Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
                    if (getIntent().getExtras() != null && history == 0) {
                        Common.bundleFromNotification = getIntent().getExtras();
                        Common.chatDataConvertModelFromNotification = getIntent().getExtras().getParcelable("chatDataConvertModel");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Common.getInstance().getLocation(SplashScreenActivity.this, this.getClass().getSimpleName(), false);
                progressBar = (ProgressBar) findViewById(R.id.progressBar1);
                // progressBar.setVisibility(View.VISIBLE);
                progressBar.getIndeterminateDrawable().setColorFilter(
                        getResources().getColor(R.color.white),
                        android.graphics.PorterDuff.Mode.SRC_IN);
                previousVersion = Utility.getAppVersionName(SplashScreenActivity.this);
                //
                hidestatusBar();
                if (Common.checkInternetConnection(getApplicationContext())) {
                    verifyAnyUpdate();
                } else {
                    flowController();
                }
                Log.e("CountryZipCode", Common.GetCountryZipCode(getApplicationContext()) + " CODE");
                SessionManager.getInstance().setKeyValue(SessionManager.KEY_IS_SWITCH_USER_DIALOG_OPEN, "false");
                //
                try {
                    Intent intent = getIntent();
                    String sharedData = Objects.requireNonNull(intent.getData()).toString();
                    String data[] = sharedData.split("/");
                    if (sharedData.contains(ApiClient.SHARE_FEED_IN_SOCIAL_MEDIA)) {
                        Common.sharedFeedID = data[data.length - 1];
                    } else if (sharedData.contains(ApiClient.SHARE_GROUP_TALENT_IN_SOCIAL_MEDIA)) {
                        Common.sharedProfileID = data[data.length - 1];
                    } else if (sharedData.contains(ApiClient.SHARE_AUDITION_IN_SOCIAL_MEDIA)) {
                        Common.sharedAuditionID = data[data.length - 2];
                        Common.sharedRoleID = data[data.length - 1];
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void flowController() {
        if (SessionManager.getInstance().getKeyValue(SessionManager.KEY_FIRST_TIME_SPLASH_LOAD, false)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (SessionManager.getInstance().getKeyValue(SessionManager.KEY_CLEAR_LOCAL_DATA, false)) {
                        navigateToSignInOrFeedActivity();
                    } else {
                        deleteSessionManagerData();
                    }
                }
            }, SPLASH_TIME_OUT);
        } else {
            //If want splash screesn uncomment
//            introSplashScreensGetFromServer();

            continueSignInPage();
        }
    }

    private void deleteSessionManagerData() {
        try {
            SessionManager.getInstance().removeAll();
            SessionManager.getInstance().setKeyValue(SessionManager.KEY_CLEAR_LOCAL_DATA, true);
            navigateToSignInOrFeedActivity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteSessionManagerDataSignIn() {
        try {
            SessionManager.getInstance().removeAll();
            SessionManager.getInstance().setKeyValue(SessionManager.KEY_CLEAR_LOCAL_DATA, true);
            continueSignInPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void navigateToSignInOrFeedActivity() {
        if (SessionManager.getInstance().getKeyValue(SessionManager.KEY_FIRSTTIME_EDIT_PROFILE_ACCESS, "") != null) {
            if (SessionManager.getInstance().getKeyValue(SessionManager.KEY_FIRSTTIME_EDIT_PROFILE_ACCESS, "").equals("TRUE")) {
                navigateToFeedActivity();
            } else {
                if (SessionManager.getInstance().getKeyValue(SessionManager.KEY_FIRST_EDIT_PROFILE_NOT_COMPLETE, "") != null
                        && !SessionManager.getInstance().getKeyValue(SessionManager.KEY_FIRST_EDIT_PROFILE_NOT_COMPLETE, "").isEmpty()) {
                    if (SessionManager.getInstance().getKeyValue(SessionManager.KEY_FIRST_EDIT_PROFILE_NOT_COMPLETE, "").equals("TRUE")) {
                        navigateToEditProfile();
                    } else {
                        navigateToSignIn();
                    }
                } else {
                    navigateToSignIn();
                }
            }
        } else {
            if (SessionManager.getInstance().getKeyValue(SessionManager.KEY_FIRST_EDIT_PROFILE_NOT_COMPLETE, "") != null
                    && !SessionManager.getInstance().getKeyValue(SessionManager.KEY_FIRST_EDIT_PROFILE_NOT_COMPLETE, "").isEmpty()) {
                if (SessionManager.getInstance().getKeyValue(SessionManager.KEY_FIRST_EDIT_PROFILE_NOT_COMPLETE, "").equals("TRUE")) {
                    navigateToEditProfile();
                } else {
                    navigateToSignIn();
                }
            } else {
                navigateToSignIn();
            }
        }
    }

    private void navigateToEditProfile() {
        Intent intent = new Intent(SplashScreenActivity.this, HelperActivity.class);
        intent.putExtra(Constants.FRAGMENT_TITLE, "My Profile");
        intent.putExtra(Constants.FRAGMENT_KEY, 8002);// EditProfileFragment
        intent.putExtra("NEW_REG", "TRUE");
        startActivity(intent);
    }


    private void navigateToFeedActivity() {
        if (Common.checkInternetConnection(getApplicationContext())) {
            //startService(new Intent(getBaseContext(), MyFirebaseMessagingService.class));
            Common.getInstance().navigateToFeedOrBottom(SplashScreenActivity.this);
        } else {
            Toast.makeText(getApplicationContext(), Constants.SEEMS_LIKE_NO_INTRNET, Toast.LENGTH_LONG).show();
        }
    }


    private void navigateToSignIn() {
        startActivity(new Intent(SplashScreenActivity.this, SignInActivity.class));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (updateAppDialog != null && updateAppDialog.isShowing()) {
            if (previousVersion != null) {

                if (previousVersion.equals(Utility.getAppVersionCode(this))) {

                } else {
                    updateAppDialog.dismiss();
                    if (SessionManager.getInstance().getKeyValue(SessionManager.KEY_FIRST_TIME_SPLASH_LOAD, false)) {
                        SPLASH_TIME_OUT = 500;
                    }
                    flowController();
                }
            } else {
                updateAppDialog.dismiss();
            }
        }
    }

    private void introSplashScreensGetFromServer() {
        //   progressBar.setVisibility(View.VISIBLE);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<TutorialData>> call = apiInterface.getAllSplashScreens(ApiClient.GET_ALL_SPLASH_SCREENS + "IN" + "/");
        call.enqueue(new Callback<ArrayList<TutorialData>>() {
            @Override
            public void onResponse(Call<ArrayList<TutorialData>> call, Response<ArrayList<TutorialData>> response) {
                try {
                    if (response.body().size() > 0) {
                        _tutorialData = new ArrayList<TutorialData>();
                        String message = response.body().get(0).getScrn_img_path();
                        if (message != null) {
                            _tutorialData = response.body();
                            if (_tutorialData.size() != 0) {
                                bitmapArray = new ArrayList<>();
                                getBitmapFromFresco(_tutorialData, 0);
                            }
                        } else {
                            continueSignInPage();
                        }
                    } else {
                        if (SessionManager.getInstance().getKeyValue(SessionManager.KEY_CLEAR_LOCAL_DATA, false)) {
                            continueSignInPage();
                        } else {
                            deleteSessionManagerDataSignIn();
                        }
                    }

                } catch (Exception e) {
                    continueSignInPage();
                    Log.e("errorhits", "null");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<TutorialData>> call, Throwable t) {
                if (progressBar != null) {
                    continueSignInPage();
                }
            }
        });
    }

    private void continueSignInPage() {
        Intent i = new Intent(SplashScreenActivity.this, SignInActivity.class);
        startActivity(i);
    }

    private void getBitmapFromFresco(ArrayList<TutorialData> tutorialData, final Integer currentIndex) {
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        ImageRequest imageRequest = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(ApiClient.BASE_URL + _tutorialData.get(currentIndex).getScrn_img_path()))
                .setRequestPriority(Priority.HIGH)
                .setLowestPermittedRequestLevel(com.facebook.imagepipeline.request.ImageRequest.RequestLevel.FULL_FETCH)
                .disableDiskCache()
                .build();
        DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(imageRequest, getApplicationContext());
        try {
            dataSource.subscribe(new BaseBitmapDataSubscriber() {


                @Override
                public void onNewResultImpl(@Nullable Bitmap bitmap) {
                    try {
                        if (bitmap != null && !bitmap.isRecycled()) {
                            bitmapArray.add(Bitmap.createBitmap(bitmap));
                            rowItems.add(new TutorialData(bitmap));
                            imagesData.add(new ImagesData(bitmap));
                            //
                            if (currentIndex == (tutorialData.size() - 1)) {
                                imagesData.addAll(imagesData);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        SessionManager.getInstance().setKeyValue(SessionManager.KEY_FIRST_TIME_SPLASH_LOAD, true);
                                        Intent i = new Intent(SplashScreenActivity.this, IntroductionActivityNew.class);
                                        i.putExtra("IMAGES_SIZE", _tutorialData.size());
                                        startActivity(i);
                                        finish();
                                    }
                                });
                            } else {
                                getBitmapFromFresco(tutorialData, currentIndex + 1);
                            }
                        } else {
                            getBitmapFromFresco(tutorialData, currentIndex);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailureImpl(DataSource dataSource) {
                    Log.d("IntroImageLoadError", dataSource.toString());
                    Intent i = new Intent(SplashScreenActivity.this, SignInActivity.class);
                    startActivity(i);
                }
            }, CallerThreadExecutor.getInstance());
        } finally {

        }
    }

    public ArrayList<Bitmap> splashScreen() {
        return bitmapArray;
    }

    @Override
    public void updateNow() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + getPackageName()));
        startActivity(intent);
        finish();
    }

    @Override
    public void doLater() {
        if (SessionManager.getInstance().getKeyValue(SessionManager.KEY_FIRST_TIME_SPLASH_LOAD, false)) {
            SPLASH_TIME_OUT = 500;
        }
        flowController();
    }

    private void hidestatusBar() {
        if (Build.VERSION.SDK_INT > 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


    public void openDialog(boolean hardUpdate) {
        updateAppDialog = new UpdateAppDialog(SplashScreenActivity.this, iUpdateAppDialog, hardUpdate);

        if (hardUpdate) {
            updateAppDialog.setCancelable(false);
        } else {
            updateAppDialog.setCancelable(true);
        }
        updateAppDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        updateAppDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        updateAppDialog.show();
    }

    // Not needed token
    public void verifyAnyUpdate() {
        JSONObject input = new JSONObject();
        try {
            input.put("platform", Constants.PLATFORM);
            input.put("currentVersion", Utility.getAppVersionCode(SplashScreenActivity.this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        IApiListener iApiListener = new IApiListener() {
            @Override
            public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                try {
                    JSONObject response = new JSONObject(new Gson().toJson(apiResponseModel.data));
                    if (response.optBoolean("update", false)) {
                        openDialog(response.getBoolean("isForce"));
                    } else {
                        flowController();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    flowController();
                }
            }

            @Override
            public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
                flowController();
            }
        };
        //
        ApiInterface apiInterface = ApiClient.getClientJSON().create(ApiInterface.class);
        Call<JsonElement> call = apiInterface.POST_JSON(ApiClient.CHECK_APP_UPDATE, RequestBody.create(MediaType.parse("application/json; charset=utf-8"), input.toString()));
        Common.getInstance().callAPIJSON(new ApiRequestModelJSON().setModel(this, call, ApiClient.CHECK_APP_UPDATE, false, iApiListener, false));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.d("Request Code", "" + requestCode);
        if (requestCode == RC_APP_UPDATE) {
            if (resultCode != RESULT_OK) {
                Logger.d("versionUpdate", "onActivityResult: app download failed");
            }
        }
    }


    @Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
        if (condition.equals(ApiClient.GET_ALL_SPLASH_SCREENS)) {
            try {
                Type type = new TypeToken<ArrayList<TutorialData>>() {
                }.getType();
                ArrayList<TutorialData> tutorialDataArrayList = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                if (tutorialDataArrayList != null) {
                    if (tutorialDataArrayList.size() > 0) {
                        _tutorialData = new ArrayList<TutorialData>();
                        String message = tutorialDataArrayList.get(0).getScrn_img_path();
                        if (message != null) {
                            _tutorialData = tutorialDataArrayList;
                            if (_tutorialData.size() != 0) {
                                bitmapArray = new ArrayList<>();
                                getBitmapFromFresco(_tutorialData, 0);
                            }
                        } else {
                            continueSignInPage();
                        }
                    } else {
                        continueSignInPage();
                    }


                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (condition.equals(ApiClient.CHECK_APP_UPDATE)) {
            try {
                JSONObject response = new JSONObject(new Gson().toJson(apiResponseModel.data));
                if (response != null && response.has("update")) {
                    try {
                        if (response.getBoolean("update"))
                            openDialog(response.getBoolean("isForce"));
                        else {
                            flowController();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
        if (condition.equals(ApiClient.GET_ALL_SPLASH_SCREENS)) {
            continueSignInPage();
        } else if (condition.equals(ApiClient.CHECK_APP_UPDATE)) {

        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i("Service status", "Running");
                return true;
            }
        }
        Log.i("Service status", "Not running");
        return false;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks whether a hardware keyboard is available
        if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {
            Toast.makeText(this, "keyboard visible", Toast.LENGTH_SHORT).show();

        } else if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
            Toast.makeText(this, "keyboard hidden", Toast.LENGTH_SHORT).show();
        }
    }

    /*InstallStateUpdatedListener installStateUpdatedListener = new
            InstallStateUpdatedListener() {
                @Override
                public void onStateUpdate(InstallState state) {
                    if (state.installStatus() == InstallStatus.DOWNLOADED) {
                        popupSnackbarForCompleteUpdate();
                    } else if (state.installStatus() == InstallStatus.INSTALLED) {
                        if (mAppUpdateManager != null) {
                            mAppUpdateManager.unregisterListener(installStateUpdatedListener);
                        }
                    } else {
                        Logger.d("versionUpdate", "InstallStateUpdatedListener: state: " + state.installStatus());
                    }
                }
            };

    private void popupSnackbarForCompleteUpdate() {

        Snackbar snackbar =
                Snackbar.make(
                        findViewById(R.id.relativeLayout),
                        "New app is ready!",
                        Snackbar.LENGTH_INDEFINITE);

        snackbar.setAction("Install", view -> {
            if (mAppUpdateManager != null) {
                mAppUpdateManager.completeUpdate();
            }
        });


        snackbar.setActionTextColor(getResources().getColor(R.color.skyblueNew));
        snackbar.show();
    }*/

}
