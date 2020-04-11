//package info.celebkonnect.flow.celebflow.services;
//
//import android.app.ActivityManager;
//import android.app.Service;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.os.AsyncTask;
//import android.os.IBinder;
//import android.util.Log;
//import info.celebkonnect.flow.celebflow.DataBaseUtil.AppController;
//import info.celebkonnect.flow.celebflow.constants.Constants;
//import info.celebkonnect.flow.chat.socket.ChatSocket;
//import info.celebkonnect.flow.managermodule.switchingprofiles.SwitchProfileService;
//import info.celebkonnect.flow.userflow.Util.Common;
//import info.celebkonnect.flow.userflow.Util.LockScreenHelper;
//import info.celebkonnect.flow.userflow.Util.SharedPrefsUtil;
//import info.celebkonnect.flow.utils.CommonApis;
//import info.celebkonnect.flow.utils.Logger;
//import org.json.JSONObject;
//
//import java.util.List;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//
///**
// * Created by Shiva on 19-07-2018.
// **/
//
//public class LockScreenService extends Service {
//
//    public static Boolean value = null;
//    public static Context context = null;
//    public static ScheduledExecutorService scheduleTaskExecutorur;
//
//    private String screenLockEmit = "screenLock";
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        context = getApplicationContext();
//
//
//        scheduleTaskExecutorur = Executors.newScheduledThreadPool(5);
//        scheduleTaskExecutorur.scheduleAtFixedRate(new Runnable() {
//            public void run() {
//                Logger.e("LockScreenService", "START");
//                Boolean current = null;
//                try {
//                    current = new ForegroundCheckTask().execute(getApplicationContext()).get();
//
//                    phoneLockStatusService();
//
//
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                }
//                Logger.d("LockScreenService", "RUNNING FOREGROUND " + current);
//
//            }
//        }, 0, Constants.CHECK_PER_SEC, TimeUnit.MILLISECONDS);
//    }
//
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        return Service.START_STICKY;
//    }
//
//    @Override
//    public void onDestroy() {
//        Logger.e("LockScreenService", "STOP");
//        scheduleTaskExecutorur.shutdown();
//        super.onDestroy();
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//
//    public class ForegroundCheckTask extends AsyncTask<Context, Void, Boolean> {
//
//        @Override
//        protected Boolean doInBackground(Context... params) {
//            final Context context = params[0].getApplicationContext();
//            return isAppOnForeground(context);
//        }
//
//        @Override
//        protected void onPostExecute(Boolean aBoolean) {
//            super.onPostExecute(aBoolean);
//        }
//
//        private boolean isAppOnForeground(Context context) {
//            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//            List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
//            if (appProcesses == null) {
//                return false;
//            }
//            final String packageName = context.getPackageName();
//            for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
//                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
//                    return true;
//                }
//            }
//            return false;
//        }
//    }
//
//
//    public void phoneLockStatusService() {
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(Intent.ACTION_SCREEN_OFF);
//        filter.addAction(Intent.ACTION_SCREEN_ON);
//        filter.addAction(Intent.ACTION_USER_PRESENT);
//        registerReceiver(lockScreenReceiver, filter);
//    }
//
//    final BroadcastReceiver lockScreenReceiver = new BroadcastReceiver() {
//        private final static String TAG = "PhoneLockServer";
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            final String action = intent.getAction();
//            if (Intent.ACTION_SCREEN_OFF.equals(action)) {
//                Log.i("PhoneLock", "ACTION_SCREEN_OFF");
//                if (LockScreenHelper.isScreenUnlocked(getBaseContext())) {
//                    // what does this mean?
//                    ChatSocket.getInstance().screenStatusEmit(false);
//                    Log.i(TAG, "screen is unlocked\n\n");
//                } else {
//                    // this means device screen is off, and is locked
//
//                    ChatSocket.getInstance().screenStatusEmit(true);
//
//
//                    Log.i(TAG, "screen is locked\n\n");
//                }
//
//            } else if (Intent.ACTION_SCREEN_ON.equals(action)) {
//                Log.i(TAG, "ACTION_SCREEN_ON");
//                if (LockScreenHelper.isScreenUnlocked(getBaseContext())) {
//                    // this means device screen is on, and is unlocked
//                    ChatSocket.getInstance().screenStatusEmit(false);
//                    Log.i(TAG, "screen is unlocked\n\n");
//                } else {
//                    ChatSocket.getInstance().screenStatusEmit(true);
//                    // this means device screen is on, and is locked
//                    Log.i(TAG, "screen is locked\n\n");
//                }
//            }
//
//            if (Intent.ACTION_USER_PRESENT.equals(action)) {
//                ChatSocket.getInstance().screenStatusEmit(false);
//                Log.i(TAG, "screen user is present - on and unlocked");
//            }
//
//
//        }
//    };
//
////    private void screenStatusEmit(boolean isScreenStatus) {
////        try {
////            JSONObject jsonObject = new JSONObject();
////            if (Common.isCelebStatus(AppController.getInstance().getContext())) {
////                jsonObject.put("userId", Common.isLoginId());
////                jsonObject.put("ScreenLockStatus", isScreenStatus);
////                Common.getInstance().getSocket().emit(screenLockEmit, jsonObject);
////            }
////
////        } catch (Exception e) {
////
////        }
////    }
//
//
//    @Override
//    public boolean stopService(Intent name) {
//        Log.e("LockService", "STOP");
//        unregisterReceiver(lockScreenReceiver);
//        return super.stopService(name);
//    }
//}
