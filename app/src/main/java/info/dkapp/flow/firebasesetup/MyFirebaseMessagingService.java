package info.dkapp.flow.firebasesetup;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import info.dkapp.flow.databaseutil.appstart.AppController;
import info.dkapp.flow.celebflow.constants.Constants;
import info.dkapp.flow.chat.NotificationUtil;
import info.dkapp.flow.chat.models.ChatDataConvertModel;
import info.dkapp.flow.retrofitcall.SessionManager;
import info.dkapp.flow.userflow.Util.Common;
import info.dkapp.flow.utils.SocketForAppUtill;

import org.json.JSONObject;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    String title = "", serviceType = "";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());
//        ServiceSockets.getInstance().noticationCountEmit();

        SocketForAppUtill.getInstance().noticationCountEmit();
        SocketForAppUtill.getInstance().missedCallCountEmit();

        // Check if message contains a notification payload.
        Map<String, String> params = remoteMessage.getData();
        if (params.size() > 0) {
            JSONObject finalJsonObject = new JSONObject(params);
            if (finalJsonObject.length() > 0) {
                serviceType = finalJsonObject.optString(Constants.NOTIFICATION_SERVICE_TYPE, "");
                title = finalJsonObject.optString("title", "");
                SessionManager.getInstance().getUserLoginData();
                if (SessionManager.userLogin == null || SessionManager.userLogin.userId.isEmpty()) {
                    return;
                }
                Log.e("notificationObj", finalJsonObject.toString());

                if (serviceType.equalsIgnoreCase("chat")) {
                    try {
                        ChatDataConvertModel chatDataConvertModel = new ChatDataConvertModel();
                        try {
                            chatDataConvertModel._id = finalJsonObject.optString("memberId", "");
                            chatDataConvertModel.firstName = finalJsonObject.optString("senderFirstName", "");
                            chatDataConvertModel.lastName = finalJsonObject.optString("senderLastName", "");
                            chatDataConvertModel.avtar_imgPath = finalJsonObject.optString("senderAvatar", "");
                            chatDataConvertModel.message = finalJsonObject.optString("body", "");
                            chatDataConvertModel.receiverId = finalJsonObject.optString("memberId", "");
                            chatDataConvertModel.isCeleb = finalJsonObject.optBoolean("isCeleb", false);
                            chatDataConvertModel.isFan = finalJsonObject.optBoolean("isFan", false);
                            chatDataConvertModel.senderId = Common.getInstance().IsNullReturnValue(SessionManager.getInstance().getKeyValue(SessionManager.KEY_USER_ID, ""), "");
                            chatDataConvertModel.createdAt = "";
                            chatDataConvertModel.counter = 0;
                            chatDataConvertModel.chatCount = finalJsonObject.optInt("chatCount", 0);
                            chatDataConvertModel.unSeenMessageCount = finalJsonObject.optInt("unSeenMessageCount", 0);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Boolean canSendNotification = true;
                        if (Common.getInstance().getActivitySingleChat() != null &&
                                Common.getInstance().getActivitySingleChat().getOtherPersonReceiverID().equalsIgnoreCase(chatDataConvertModel._id)
                                && Common.getInstance().getActivitySingleChat().getIsActivityVisible()) {
                            canSendNotification = false;
                        }
                        Log.d("SendChatNotify", "" + canSendNotification);
                        if (canSendNotification) {
                            new NotificationUtil().sendChatNotification(AppController.getInstance().getContext(), chatDataConvertModel, "FCMNotification");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if ((serviceType.equals("video") || serviceType.equals("audio"))) {
                    boolean isScheduleCall = finalJsonObject.optBoolean(Constants.SCHEDULE_CALL, false);
                    Log.e("isScheduleCall", isScheduleCall + "");
                } else {
                    new NotificationUtil().sendNormalNotification(AppController.getInstance().getContext(), finalJsonObject);
                }
            }
        } else if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Title: " + remoteMessage.getNotification().getTitle() + "Message Notification Body: "
                    + remoteMessage.getNotification().getBody());
        }

    }

}
