package info.sumantv.flow.utils.internetchecker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import info.sumantv.flow.bottommenu.activity.BottomMenuActivity;
import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.chat.socket.ChatSocket;
import info.sumantv.flow.utils.SocketForAppUtill;
import info.sumantv.flow.utils.Utility;

public class ConnectivityReceiver extends BroadcastReceiver {
    Context mContext;
    private boolean isConnected;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        String status = NetworkUtil.getConnectivityStatusString(context);

        Log.e("Receiver ", "" + status);

        if (status.equals(Constants.NOT_CONNECT)) {
            Log.e("Receiver ", "not connection");// your code when internet lost
//            ChatSocket.getInstance().emitOffline(context);
        } else {
            Log.e("Receiver ", "connected to internet");//your code when internet connection come back
            ChatSocket.getInstance().emitOnline(context);
        }
        BottomMenuActivity.addLogText(status);

        if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
            Log.i("Boot Status: ", "BootCompleted");
            ChatSocket.getInstance().screenStatusEmit(true);
            SocketForAppUtill.getInstance().onlineCelebEmit();
        } else if (intent.getAction().equalsIgnoreCase(Intent.ACTION_SHUTDOWN)) {
            Log.i("Boot Status: ", "ShutdownPhone");
            ChatSocket.getInstance().exitUserFromApp(Utility.getContext());

//            ChatSocket.getInstance().screenStatusEmit(false);
//            SocketForAppUtill.getInstance().onlineCelebEmit();


        }


//        switch (intent.getAction()) {
//            case WifiManager.NETWORK_STATE_CHANGED_ACTION:
//                if (!isConnected && isOnline(context)) {
//                    isConnected = true;
//                    // do stuff when connected
//                    Log.i("Network status: ", "Connected");
//                } else {
//                    isConnected = isOnline(context);
//                    Log.i("Network status: ", "Disconnected");
//                }
//            case WifiManager.WIFI_STATE_CHANGED_ACTION:
//                if (!isConnected && isOnline(context)) {
//                    isConnected = true;
//                    // do stuff when connected
//                    Log.i("Network status: ", "Connected");
//                } else {
//                    isConnected = isOnline(context);
//                    Log.i("Network status: ", "Disconnected");
//                }
//                break;
//        }


    }

//    public static boolean isOnline(Context ctx) {
//        ConnectivityManager cm = (ConnectivityManager) ctx
//                .getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo netInfo = cm != null
//                ? cm.getActiveNetworkInfo()
//                : null;
//        return netInfo != null && netInfo.isConnectedOrConnecting();
//    }
}
