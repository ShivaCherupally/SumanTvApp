package info.dkapp.flow.celebflow.services;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import info.dkapp.flow.celebflow.constants.Constants;
import info.dkapp.flow.retrofitcall.ApiInterface;
import info.dkapp.flow.retrofitcall.SessionManager;
import info.dkapp.flow.utils.Logger;

/**
 * Created by User on 19-07-2018.
 **/

public class GetDeviceTokenService extends Service {

    public static Boolean value = null;
    public static Context context = null;
    ApiInterface apiInterface;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        ScheduledExecutorService scheduleTaskExecutor = Executors.newScheduledThreadPool(5);
        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                //Common.toastMessage(context,"running");
                Boolean current = null;
                try {
                    current = new ForegroundCheckTask().execute(getApplicationContext()).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                Logger.d("SERVICE", "RUNNING FOREGROUND " + current);

                if (SessionManager.getInstance().getKeyValue(SessionManager.KEY_SIGN_IN_ACCESS,"") != null
                        && SessionManager.getInstance().getKeyValue(SessionManager.KEY_SIGN_IN_ACCESS,"").equals("TRUE")) {
//                    CommonApis.getDeviceTokenFromServer(context); // for token implementation
                }
            }
        }, 0, Constants.CHECK_FOR_DEVICE, TimeUnit.MILLISECONDS);

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public class HandlerThread implements Runnable {
        Context context;

        public HandlerThread(Context context) {
            this.context = context;
        }

        @Override
        public void run() {
            Boolean value = null;
            try {
                value = new ForegroundCheckTask().execute(context).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            Logger.d("SERVICE", "RUNNING " + value);
        }
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
