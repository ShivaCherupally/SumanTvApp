package info.sumantv.flow.celebflow.services;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

import info.sumantv.flow.databaseutil.appstart.AppController;
import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.retrofitcall.SessionManager;
import info.sumantv.flow.userflow.Util.Common;
import info.sumantv.flow.utils.CommonApis;
import info.sumantv.flow.utils.Logger;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Shiva on 19-07-2018.
 **/

public class SwitchAccountStatus extends Service {

    public static Boolean value = null;
    public static Context context = null;
    public static ScheduledExecutorService scheduleTaskExecutorur;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();


        scheduleTaskExecutorur = Executors.newScheduledThreadPool(5);
        scheduleTaskExecutorur.scheduleAtFixedRate(new Runnable() {
            public void run() {
                Logger.e("SWITCH_SERVICE", "START");
                Boolean current = null;
                try {
                    current = new ForegroundCheckTask().execute(getApplicationContext()).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                Logger.d("SERVICE", "RUNNING FOREGROUND " + current);
                CommonApis.getCelebGivenPermissions(context);
                boolean isAccess = false;
                if (isAccess) {
                    Logger.d("SHOW_PER", "TRUE");
                } else {
                    Logger.d("SHOW_PER", "FALSE");
                    if (AppController.isAppForeground) {
                        try {
                            String isSwitchUserDialogOpen = SessionManager.getInstance().getKeyValue(SessionManager.KEY_IS_SWITCH_USER_DIALOG_OPEN, "");
                            if (isSwitchUserDialogOpen == null) {
                                isSwitchUserDialogOpen = "";
                            }
                            if (!isSwitchUserDialogOpen.equalsIgnoreCase("true")) {
                                Logger.d("SHOW_PER_MANAGER_SWITCH", "TRUE");
                                SessionManager.getInstance().setKeyValue(SessionManager.KEY_IS_SWITCH_USER_DIALOG_OPEN, "false");
                                Common.getInstance().switchOwnProfile(context, true);

//                                    Intent popup = new Intent(context, CommonNotifyActivity.class);
//                                    popup.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
//                                            Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//                                    startActivity(popup);

//                                    Common.getInstance().switchOwnProfile(context);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Common.getInstance().switchOwnProfile(context, true);
                        }
                    }

                }

            }
        }, 0, Constants.CHECK_PER_IN_SEC, TimeUnit.MILLISECONDS);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        Logger.e("SWITCH_SERVICE", "STOP");
        scheduleTaskExecutorur.shutdown();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public class ForegroundCheckTask extends AsyncTask<Context, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Context... params) {
            final Context context = params[0].getApplicationContext();
            return isAppOnForeground(context);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }

        private boolean isAppOnForeground(Context context) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
            if (appProcesses == null) {
                return false;
            }
            final String packageName = context.getPackageName();
            for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                    return true;
                }
            }
            return false;
        }
    }


}
