package info.dkapp.flow.chat;

import android.content.Context;
import android.util.Log;

import java.util.Date;

import info.dkapp.flow.userflow.Util.Common;

public class CallReceiver extends PhoneCallReceiver {
    String TAG = "CallReceiver";
    public static Boolean normalCallRunning = false;

    //created new constructer
//    public CallReceiver(Context mContext) {
//        super(mContext);
//    }

    /*private static CallReceiver callReceiver;
    public static CallReceiver getInstance() {
        if (callReceiver == null) {
            callReceiver = new CallReceiver();
        }
        return callReceiver;
    }*/
    @Override
    protected void onIncomingCallStarted(Context ctx, String number, Date start) {
        Log.d(TAG,"onIncomingCallStarted "+number);

        normalCallRunning = true;
    }

    @Override
    protected void onOutgoingCallStarted(Context ctx, String number, Date start) {
        Log.d(TAG,"onOutgoingCallStarted "+number);

        normalCallRunning = true;
    }

    @Override
    protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end) {
        Log.d(TAG,"onIncomingCallEnded "+number);

        normalCallRunning = false;
    }

    @Override
    protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end) {
        Log.d(TAG,"onOutgoingCallEnded "+number);

        normalCallRunning = false;
    }

    @Override
    protected void onMissedCall(Context ctx, String number, Date start) {
        Log.d(TAG,"onMissedCall "+number);

        normalCallRunning = false;
    }

    public static Boolean getNormalCallRunning() {
        return normalCallRunning;
    }

    public static void setNormalCallRunning(Boolean value) {
        normalCallRunning = value;
    }
}
