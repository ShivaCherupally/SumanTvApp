package info.dkapp.flow.utils.internetchecker;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;
import info.dkapp.flow.R;
import info.dkapp.flow.userflow.Util.Common;

public class InternetSpeedChecker {
    static boolean connectionStatus;


    public static boolean checkInternetAvaialable(Context mContext) {
        boolean netAvailable = false;
        if (Common.checkInternetConnection(mContext)) {
            netAvailable = true;
            Log.e("internetAvailable", true + "");
        } else {
            Common.getInstance().showSweetAlertWarning(mContext, "CelebKonect",
                    mContext.getResources().getString(R.string.no_internet_connect));
            netAvailable = false;
        }
        return netAvailable;
    }


    public static boolean highNetworkState(Context mContext) {
        boolean networkStateok;
        Log.e("bottomnetcheck", "checked");

        connectionStatus = isConnectedFast(mContext);
        if (!connectionStatus) {
            networkStateok = false;
            Common.getInstance().showSweetAlertWarning(mContext, "CelebKonect",
                    mContext.getResources().getString(R.string.poornetworkfound));
        } else {
            networkStateok = true;
        }

        return networkStateok;

    }

    public static String checkNetworkType(Context mContext) {
        String networkState;
        Log.e("bottomnetcheck", "checked");

        connectionStatus = isConnectedFast(mContext);
        if (!connectionStatus) {
            networkState = "Poor Network";
        } else {
//            networkState = "High Network";
            networkState = "";
        }

        return networkState;

    }

    public static boolean isConnectedFast(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected() && isConnectionFast(info.getType(), info.getSubtype(), context));
    }

    public static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    public static boolean isConnectionFast(int type, int subType, Context context) {
        if (type == ConnectivityManager.TYPE_WIFI) {
            return true;

        } else if (type == ConnectivityManager.TYPE_MOBILE) {

            switch (subType) {
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                    return false; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    return false; // ~ 14-64 kbps
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    return false; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    return true; // ~ 400-1000 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    return true; // ~ 600-1400 kbps
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    return false; // ~ 100 kbps
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                    return true; // ~ 2-14 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPA:
                    return true; // ~ 700-1700 kbps
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                    return true; // ~ 1-23 Mbps
                case TelephonyManager.NETWORK_TYPE_UMTS:
                    return true; // ~ 400-7000 kbps

                // Above API level 7, make sure to set android:targetSdkVersion to appropriate level to use these

                case TelephonyManager.NETWORK_TYPE_EHRPD: // API level 11
                    return true; // ~ 1-2 Mbps
                case TelephonyManager.NETWORK_TYPE_EVDO_B: // API level 9
                    return true; // ~ 5 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPAP: // API level 13
                    return true; // ~ 10-20 Mbps
                case TelephonyManager.NETWORK_TYPE_IDEN: // API level 8
                    return false; // ~25 kbps
                case TelephonyManager.NETWORK_TYPE_LTE: // API level 11
                    return true; // ~ 10+ Mbps
                // Unknown
                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                default:
                    return false;
            }
        } else {
            return false;
        }

    }
}
