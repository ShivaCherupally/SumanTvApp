package info.dkapp.flow.userflow.Util;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.util.HashMap;

public class Alerts {
    public static void register(Activity activity) {
        AlertReceiver.register(activity);
    }

    public static void unregister(Activity activity) {
        AlertReceiver.unregister(activity);
    }

    public static void displayError(Context context, String title, String msg) {
        Intent intent = new Intent("MyApplication.INTENT_DISPLAY_ERROR");
        intent.putExtra("title", title);
        intent.putExtra("msg", msg);
        context.sendOrderedBroadcast(intent, null);
    }

   /* private static void displayErrorInternal(Context context, String title, String msg) {
        try {
            new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText(title)
                    .setContentText(msg)
                    .setConfirmClickListener(sweetAlertDialog -> {
                        try {
                            sweetAlertDialog.dismiss();
                            sweetAlertDialog.cancel();
                            //openSignInActivity(context);
                            Common.getInstance().managerOwnProfileBack(context);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    })
                    .show();
        } catch (Exception e) {
            e.printStackTrace();
            Common.getInstance().managerOwnProfileBack(context);
        }
    }*/

    /*private static void displayErrorCancelOrOk(Context context, String title, String msg) {
        try {
            new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText(title).setCancelText("Cancel")
                    .setContentText(msg).showCancelButton(true)
                    .setConfirmClickListener(sweetAlertDialog -> {
                        try {
                            sweetAlertDialog.dismiss();
                            sweetAlertDialog.cancel();
                            //openSignInActivity(context);

                            Common.getInstance().managerOwnProfileBack(context);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }).setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sDialog) {
                    sDialog.cancel();
//                    context.fi
                    if (context != null) {
//                        if ((CommonNotifyActivity) context != null) {
//                            ((CommonNotifyActivity) context).finish();
//                        }

                    }

                }
            }).show();
        } catch (Exception e) {
            e.printStackTrace();
            Common.getInstance().managerOwnProfileBack(context);
        }
    }*/

//    private static void openSignInActivity(Context context) {
//        Common.getInstance().getCommonNotifyActivity().finish();
//        Common.getInstance().callSignInActivity(context);
//    }

    private static class AlertReceiver extends BroadcastReceiver {
        private static HashMap<Activity, AlertReceiver> registrations;

        static {
            registrations = new HashMap<Activity, AlertReceiver>();
        }

        private Context activityContext;

        private AlertReceiver(Activity activity) {
            activityContext = activity;
        }

        static void register(Activity activity) {
            AlertReceiver receiver = new AlertReceiver(activity);
            activity.registerReceiver(receiver, new IntentFilter("MyApplication.INTENT_DISPLAY_ERROR"));
            registrations.put(activity, receiver);
        }

        static void unregister(Activity activity) {
            AlertReceiver receiver = registrations.get(activity);
            if (receiver != null) {
                activity.unregisterReceiver(receiver);
                registrations.remove(activity);
            }
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            abortBroadcast();
            //String msg = intent.getStringExtra(Intent.EXTRA_TEXT);
//            displayErrorCancelOrOk(activityContext, intent.getStringExtra("title"), intent.getStringExtra("msg"));
//            displayErrorInternal(activityContext, intent.getStringExtra("title"), intent.getStringExtra("msg"));
        }
    }
}
