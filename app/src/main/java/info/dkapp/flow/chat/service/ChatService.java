package info.dkapp.flow.chat.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.*;
import androidx.annotation.RequiresApi;
import android.util.Log;
import info.dkapp.flow.celebflow.constants.Constants;
import info.dkapp.flow.chat.NotificationUtil;
import info.dkapp.flow.userflow.Util.Common;
import info.dkapp.flow.utils.Utility;

public class ChatService extends Service {
    String TAG = "ChatService";
    Context context = Utility.getContext();
    public static Integer serviceRunningTime = 10000;


    public ChatService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        //Log.d(TAG, "ChatService onCreate");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "service started");
        /*if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Notification notification = getNotification(context.getString(R.string.app_name), "Updating status");
            startForeground(100, notification);
        }
        ScheduledExecutorService scheduleTaskExecutor = Executors.newScheduledThreadPool(5);
        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                doBackgroundWorkHere();
            }
        }, 0, 2000, TimeUnit.MILLISECONDS);*/
        if (intent != null && intent.getExtras() != null) {
            String condition = "";
            try {
                condition = intent.getExtras().getString("condition", "");
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (condition != null && condition.equalsIgnoreCase(Constants.ON_TASK_REMOVED)) {
                Log.d(TAG, "Starting job service from onTaskRemoved");
                Common.getInstance().doBackgroundWortHere();
                //
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Log.d(TAG, "service stopped");
                        try {
                            stopForeground(true);
                            stopSelf();
                        } catch (Exception e) {

                        }

//                        ChatSocket.getInstance().screenStatusEmit(false,true);


                    }
                }, serviceRunningTime);
            }
        }
        //
        return START_STICKY;
    }

    /*@Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.d(TAG, "onTaskRemoved");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ChatJobIntentService.enqueueWork(context, new Intent());
        } else {
            try {
                Intent restartServiceIntent = new Intent(context, ChatService.class)
                        .putExtra("condition", Constants.ON_TASK_REMOVED);
                restartServiceIntent.setPackage(getPackageName());
                PendingIntent restartServicePendingIntent = PendingIntent.getService(context, 1, restartServiceIntent, PendingIntent.FLAG_ONE_SHOT);
                AlarmManager alarmService = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                assert alarmService != null;
                alarmService.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 1000, restartServicePendingIntent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //new NotificationUtil().sendNormalNotification(context,new JSONObject(),"ChatService","");
        new NotificationUtil().dismissCallRunningNotification(context);
        super.onTaskRemoved(rootIntent);
        stopSelf();
    }*/

  /*  public Notification getNotification(Context context, String title, String message) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            return new NotificationCompat.Builder(context, Common.testChannelID)
                    .setSmallIcon(R.drawable.celebiconnotification)
                    .setChannelId(Common.testChannelID)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .build();
        } else {
            return new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.celebiconnotification)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .build();
        }
    }*/

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.d(TAG, "onTaskRemoved");
        new NotificationUtil().dismissCallRunningNotification(context);
        super.onTaskRemoved(rootIntent);
        stopSelf();
    }


}
