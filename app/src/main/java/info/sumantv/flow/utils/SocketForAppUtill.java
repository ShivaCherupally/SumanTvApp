package info.sumantv.flow.utils;

import android.util.Log;

import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.retrofitcall.SessionManager;
import info.sumantv.flow.userflow.Util.Common;

import org.json.JSONObject;

public class SocketForAppUtill {

    public static String storySeenStatus = "storySeenStatus";

    public static SocketForAppUtill socketForAppUtill;

    public static SocketForAppUtill getInstance() {
        if (socketForAppUtill == null) {
            socketForAppUtill = new SocketForAppUtill();
        }
        return socketForAppUtill;
    }


    //To get Online Celebrities List
    public void onlineCelebEmit() {

        if (!Common.getInstance().isSocketConnected()) {
            Common.getInstance().connectSocket();
        }
        try {
            JSONObject jsonObject = new JSONObject();
            if (SessionManager.userLogin.userId != null && !SessionManager.userLogin.userId.isEmpty()) {
                jsonObject.put(Constants.MEMBER_ID, SessionManager.userLogin.userId);
            }
            Log.e("emitToGetOnline", true + "");
            Common.getInstance().getSocket().emit(Constants.ONLINE_CELEB_EMIT, jsonObject);
        } catch (Exception e) {

        }
    }


    //To get Online Celebrities List
    public void liveLogStatusEmit(boolean liveStatus) {

        if (!Common.getInstance().isSocketConnected()) {
            Common.getInstance().connectSocket();
        }
        try {
            JSONObject jsonObject = new JSONObject();
            if (SessionManager.userLogin.userId != null && !SessionManager.userLogin.userId.isEmpty()) {
                jsonObject.put(Constants.MEMBER_ID, SessionManager.userLogin.userId);
            }
            jsonObject.put("liveStatus", liveStatus);

            Log.e("liveLogStatusEmit", jsonObject.toString());

            Common.getInstance().getSocket().emit("liveLogStatus", jsonObject);


        } catch (Exception e) {

        }
    }

    //To get Notification Count
    public void noticationCountEmit() {

        if (!Common.getInstance().isSocketConnected()) {
            Common.getInstance().connectSocket();
        }
        try {
            JSONObject jsonObject = new JSONObject();
            if (SessionManager.userLogin.userId != null &&
                    !SessionManager.userLogin.userId.isEmpty()) {
                jsonObject.put(Constants.MEMBER_ID, SessionManager.userLogin.userId);
            }
            Common.getInstance().getSocket().emit(Constants.NOTIFICATION_COUNT_EMIT, jsonObject);
        } catch (Exception e) {

        }
    }


    public void missedCallCountEmit() {

        if (!Common.getInstance().isSocketConnected()) {
            Common.getInstance().connectSocket();
        }
        try {
            JSONObject jsonObject = new JSONObject();
            if (SessionManager.userLogin.userId != null && !SessionManager.userLogin.userId.isEmpty()) {
                jsonObject.put(Constants.MEMBER_ID, SessionManager.userLogin.userId);
            }
            Log.e("missedCallCountEMit", true + "");
            Common.getInstance().getSocket().emit("missedCallCount", jsonObject);
        } catch (Exception e) {

        }
    }


    public void storySeenStatusEmit(String storyId,String celebId) {
        if (!Common.getInstance().isSocketConnected()) {
            Common.getInstance().connectSocket();
        }
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("memberId", Common.isLoginId());
            jsonObject.put("storyId", storyId);
            jsonObject.put("celebId", celebId);
            Common.getInstance().getSocket().emit(storySeenStatus, jsonObject);
        } catch (Exception e) {

        }
    }

}
