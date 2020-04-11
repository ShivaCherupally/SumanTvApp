package info.sumantv.flow.databaseutil.appstart;

/**
 * Created by user on 12/13/2017.
 */

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;

import java.util.ArrayList;
import java.util.List;

import info.sumantv.flow.bottommenu.activity.BottomMenuActivity;
import info.sumantv.flow.chat.socket.ChatSocket;
import info.sumantv.flow.ipoll.fragments.feeds.KeyboardHeightObserver;
import info.sumantv.flow.retrofitcall.ApiClient;
import info.sumantv.flow.userflow.Util.Common;
import io.fabric.sdk.android.Fabric;


public class AppController extends MultiDexApplication implements LifecycleObserver, Application.ActivityLifecycleCallbacks, KeyboardHeightObserver {
    public static Boolean isAppForeground = false;
    private final String TAG = AppController.class.getSimpleName();
    private static AppController mInstance;
    public FirebaseVisionFaceDetectorOptions firebaseVisionFaceDetectorOptions;
    public FirebaseVisionFaceDetector firebaseVisionFaceDetector;
    private boolean isAppInRecentTasks = false;
    private List<Activity> registeredActivities = new ArrayList<>();
    private Integer keyboardHeight = 0;

//    public static int appactive = 0;

//    public static boolean isAppInDestoryed = false;

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                ProcessLifecycleOwner.get().getLifecycle().addObserver(AppController.this);
                registerActivityLifecycleCallbacks(AppController.this);
                AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
//        Foreground.init(this);
//        initImageLoader(getApplicationContext());


                if (!ApiClient.ENVIRONMENT.equals("test")) {
                    Fabric.with(AppController.this, new Crashlytics());
                }

                Fresco.initialize(AppController.this);
                initializeFaceDetector();
//        isAppInDestoryed = false;
                Common.getInstance().createChannel(AppController.this);
//        socketCall();
                StrictMode.enableDefaults();
            }
        });
    }

    public void initializeFaceDetector() {
        try {
            firebaseVisionFaceDetectorOptions =
                    new FirebaseVisionFaceDetectorOptions.Builder()
                            //.setModeType(FirebaseVisionFaceDetectorOptions.ACCURATE_MODE)
                            //  .setLandmarkType(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
                            // .setClassificationType(FirebaseVisionFaceDetectorOptions.NO_CLASSIFICATIONS)
                            .setMinFaceSize(0.1f)
                            //  .setTrackingEnabled(false)
                            .build();
            firebaseVisionFaceDetector = FirebaseVision.getInstance()
                    .getVisionFaceDetector(firebaseVisionFaceDetectorOptions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public Context getContext() {
        return getApplicationContext();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private void onAppForegrounded() {
        isAppForeground = true;
        ChatSocket.getInstance().screenStatusEmit(true);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private void onAppBackgrounded() {
        isAppForeground = false;
        ChatSocket.getInstance().screenStatusEmit(true); //(getContext());
    }

    //ActivityLifecycleCallbacks Start SRIKANTH
    public boolean isIsAppInRecentTasks() {
        return isAppInRecentTasks;
    }

    public List<Activity> getRegisteredActivities() {
        return registeredActivities == null ? new ArrayList<>() : registeredActivities;
    }

    public Activity getCurrentRegisteredActivity() {
        if (registeredActivities == null || registeredActivities.size() == 0) {
            if (getContext() != null) {
                return (Activity) getContext();
            } else {
                return null;
            }
        } else {
            return registeredActivities.get(registeredActivities.size() - 1);
        }
    }

    public FragmentManager getFragmentManager() {
        return ((AppCompatActivity) AppController.getInstance().getCurrentRegisteredActivity()).getSupportFragmentManager();
    }

    public Integer getRegisteredBottomMenuActivityCount() {
        Integer count = 0;
        if (registeredActivities != null && registeredActivities.size() > 0) {
            for (int i = 0; i < registeredActivities.size(); i++) {
                if (registeredActivities.get(i) instanceof BottomMenuActivity) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.d("ActivityLifeCycle", "onActivityCreated" + activity.getLocalClassName());
        if (registeredActivities == null) {
            registeredActivities = new ArrayList<>();
        }
        registeredActivities.add(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        isAppInRecentTasks = true;
//        isAppInDestoryed = false;
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
//        isAppInDestoryed = true;
        if (registeredActivities != null && registeredActivities.size() > 0) {
            registeredActivities.remove(activity);
        }
        if (registeredActivities == null || registeredActivities.size() <= 0) {
            isAppInRecentTasks = false;
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        /*if(activity instanceof CallActivity){
            new NotificationUtil().dismissCallRunningNotification(getApplicationContext());
        }*/
        Log.e("ActivityLifeCycle", "onActivityResumed" + "");

    }

    @Override
    public void onActivityPaused(Activity activity) {
        /*if(Common.getInstance().getCallActivity() != null){
            new NotificationUtil().sendCallRunningNotification(getApplicationContext());
        }*/
    }

    @Override
    public void onActivityStopped(Activity activity) {
//        isAppInDestoryed = true;
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onKeyboardHeightChanged(int height, int orientation) {
        String orientationLabel = orientation == Configuration.ORIENTATION_PORTRAIT ? "portrait" : "landscape";
        keyboardHeight = height;
    }

    public void setKeyboardHeight(Integer keyboardHeight) {
        this.keyboardHeight = keyboardHeight;
    }

    public Integer getKeyboardHeight() {
        return keyboardHeight;
    }

    //ActivityLifecycleCallbacks End SRIKANTH
    /*public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(config.build());
    }*/


}
