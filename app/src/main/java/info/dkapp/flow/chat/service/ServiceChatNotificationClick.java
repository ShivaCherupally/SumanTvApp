package info.dkapp.flow.chat.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import info.dkapp.flow.databaseutil.appstart.AppController;
import info.dkapp.flow.celebflow.constants.Constants;
import info.dkapp.flow.chat.NotificationUtil;
import info.dkapp.flow.retrofitcall.SessionManager;
import info.dkapp.flow.splashandintroscreens.SplashScreenActivity;
import info.dkapp.flow.userflow.Util.Common;
import info.dkapp.flow.utils.Utility;

public class ServiceChatNotificationClick extends IntentService {
    Context context;

    public ServiceChatNotificationClick() {
        super("ServiceChatNotificationClick");
    }

    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intentBundle) {
        SessionManager.getInstance().getUserLoginData();
        if (intentBundle != null) {
            try {
                context = Utility.getContext();
                //
                String serviceType = "", condition = "";
                Bundle bundle = intentBundle.getExtras();
                if (bundle != null && bundle.size() > 0) {
                    serviceType = bundle.getString(Constants.NOTIFICATION_SERVICE_TYPE, "");
                    condition = bundle.getString("condition", "");
                }
                if (serviceType != null && serviceType.equalsIgnoreCase(Constants.NOTIFICATION_APP_UPDATE)) {
                    final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                    Intent intent;
                    try {
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName));
                    } catch (Exception e) {
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName));
                    }
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    return;
                } else if (serviceType != null && serviceType.equals("audio") || serviceType.equals("video")) {
                    Log.e("serviceType", serviceType + "For Call");
                    return;
                }
                if (condition.equalsIgnoreCase(String.valueOf(NotificationUtil.CALL_RUNNING_NOTIFICATION_ID))) {
                    new NotificationUtil().dismissCallRunningNotification(context);
                } else if (AppController.getInstance().isIsAppInRecentTasks()/* || Common.getInstance().isAppForeground(context)*/) {
                    Common.getInstance().notificationRedirection(context, bundle);

                } else {
                    Intent intent = new Intent(this, SplashScreenActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        stopSelf();
    }
}
