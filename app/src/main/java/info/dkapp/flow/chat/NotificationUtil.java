package info.dkapp.flow.chat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.service.notification.StatusBarNotification;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.util.Log;


import info.dkapp.flow.R;
import info.dkapp.flow.celebflow.constants.Constants;
import info.dkapp.flow.chat.models.ChatDataConvertModel;
import info.dkapp.flow.chat.service.ServiceChatNotificationClick;
import info.dkapp.flow.retrofitcall.SessionManager;
import info.dkapp.flow.userflow.Util.Common;

import org.json.JSONObject;

public class NotificationUtil {
    private final String KEY_NOTIFICATION_GROUP = "KEY_NOTIFICATION_GROUP";
    private final String chatNotificationChannel = "chatNotificationChannel";
    private final Integer GROUP_NOTIFICATION_ID = 123;
    public static final Integer CALL_RUNNING_NOTIFICATION_ID = 111;
    public static final Integer CALL_COMING_NOTIFICATION_ID = 333;
    public static String GroupNotification = "GroupNotification";

    public NotificationUtil() {
    }

    public void sendChatNotification(Context context, final ChatDataConvertModel chatDataConvertModel, String condition) {
        try {
            String userMemberId = Common.getInstance().IsNullReturnValue(SessionManager.getInstance().getKeyValue(SessionManager.KEY_USER_ID, ""), "");
            if (userMemberId == null || userMemberId.isEmpty() || !userMemberId.equals(chatDataConvertModel.senderId)) {
                return;
            }
            if (Common.getInstance().getActivitySingleChat() != null) {
                String otherPersonId = Common.getInstance().getActivitySingleChat().getOtherPersonReceiverID();
                if (otherPersonId.equalsIgnoreCase(chatDataConvertModel.senderId)) {
                    return;//Chatting with same celeb so we are returning
                }
            }
            //!userMemberId.equals()
            //The above line in if condition is will prevent other receiverId messages
            Integer uniqueID = Common.getInstance().getUniqueIDFromMemberID(chatDataConvertModel.receiverId);
            Intent intentMessage = new Intent(context, ServiceChatNotificationClick.class)
                    .putExtra("chatDataConvertModel", chatDataConvertModel)
                    .putExtra("condition", condition)
                    .putExtra(Constants.NOTIFICATION_SERVICE_TYPE, Constants.NOTIFICATION_TYPE_CHAT);
            PendingIntent pendingIntentMessage = PendingIntent.getService(context, uniqueID, intentMessage, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder messageBuilder = createNotificationBuilder(context, chatDataConvertModel.firstName + " " + chatDataConvertModel.lastName, chatDataConvertModel.message, pendingIntentMessage);
            messageBuilder.setGroup(KEY_NOTIFICATION_GROUP);
            //
            Intent intentGroup = new Intent(context, ServiceChatNotificationClick.class)
                    .putExtra("chatDataConvertModel", chatDataConvertModel)
                    .putExtra("condition", GroupNotification)
                    .putExtra(Constants.NOTIFICATION_SERVICE_TYPE, Constants.NOTIFICATION_TYPE_CHAT);
            PendingIntent pendingIntentGroup = PendingIntent.getService(context, GROUP_NOTIFICATION_ID, intentGroup, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder groupBuilder;
            String title = "CelebKonect", messagesCount = null;
            if (chatDataConvertModel.unSeenMessageCount != null && chatDataConvertModel.chatCount != null && chatDataConvertModel.chatCount > 1 && chatDataConvertModel.unSeenMessageCount > 1) {
                messagesCount = chatDataConvertModel.unSeenMessageCount + " messages from " + chatDataConvertModel.chatCount + " chats";
                groupBuilder = createNotificationBuilder(context, title, messagesCount, pendingIntentGroup);
            } else {
                groupBuilder = createNotificationBuilder(context, title, messagesCount, pendingIntentGroup);
            }
            groupBuilder.setStyle(new NotificationCompat.InboxStyle()
                    .setBigContentTitle(title)
                    .setSummaryText(messagesCount));
            groupBuilder.setGroupSummary(true).setGroup(KEY_NOTIFICATION_GROUP);
            //
            showNotification(context, messageBuilder.build(), uniqueID);
            if (chatDataConvertModel.chatCount != null && chatDataConvertModel.chatCount > 1) {
                showNotification(context, groupBuilder.build(), GROUP_NOTIFICATION_ID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (Common.getInstance().getBottomMenuActivity() != null) {
                Common.getInstance().getBottomMenuActivity().updateChatCount(chatDataConvertModel.chatCount,
                        chatDataConvertModel.unSeenMessageCount);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendNormalNotification(Context context, JSONObject jsonObject) {
        try {
            String title = "CelebKonect";
            String messageBody = jsonObject.optString("body", "");
            String serviceType = jsonObject.optString(Constants.NOTIFICATION_SERVICE_TYPE, "");
            if (serviceType != null && serviceType.equalsIgnoreCase(Constants.NOTIFICATION_APP_UPDATE)) {
                title = "CelebKonect Update";
            }
            //
            Intent intent = new Intent(context, ServiceChatNotificationClick.class);
            intent.putExtra(Constants.NOTIFICATION_SERVICE_TYPE, serviceType);
            intent.putExtra(Constants.NOTIFICATION_OBJECT, jsonObject.toString());
            intent.putExtra("isSelf", true);
            //
            int uniqueID = 0;
            PendingIntent pendingIntentMessage = PendingIntent.getService(context, uniqueID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder messageBuilder = createNotificationBuilder(context, title, messageBody, pendingIntentMessage);
            showNotification(context, messageBuilder.build(), uniqueID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private NotificationCompat.Builder createNotificationBuilder(Context context, String title, String message, PendingIntent pendingIntent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, chatNotificationChannel)
                .setSmallIcon(R.drawable.ic_only_logo_white)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setPriority(Notification.PRIORITY_HIGH)//For heads-up notification
                .setVibrate(new long[0])
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setAutoCancel(true);
        if (pendingIntent != null) {
            builder.setContentIntent(pendingIntent);
        }
        return builder;
    }

    private void showNotification(Context context, Notification notification, int id) {
        try {
            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            //NotificationManagerCompat mNotificationManager = NotificationManagerCompat.from(context);
            assert mNotificationManager != null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel(chatNotificationChannel, chatNotificationChannel, NotificationManager.IMPORTANCE_HIGH);
                mNotificationManager.createNotificationChannel(mChannel);
            }
            mNotificationManager.notify(id, notification);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void dismissNotificationsByID(Context context, Integer notifyID) {
        try {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        StatusBarNotification[] activeNotifications = notificationManager.getActiveNotifications();
                        if (activeNotifications != null && activeNotifications.length == 2) {
                            notificationManager.cancel(GROUP_NOTIFICATION_ID);
                        } else {
                            notificationManager.cancel(notifyID);
                        }
                    } else {
                        notificationManager.cancel(GROUP_NOTIFICATION_ID);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    notificationManager.cancel(GROUP_NOTIFICATION_ID);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismissAllNotifications(Context context) {
        try {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            assert notificationManager != null;
            notificationManager.cancelAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismissCallRunningNotification(Context context) {
        try {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.cancel(CALL_RUNNING_NOTIFICATION_ID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*private static int value = 0;
    String GROUP_KEY_WORK_EMAIL = "info.celebkonnect.flow.chat";
    @SuppressLint("NewApi")
    public void buttonClicked(Context context,Integer NOTIFICATION_ID) {
        try {
            value ++;
            //
            Notification notificationMessage =
                    new Notification.Builder(context, chatNotificationChannel)
                            .setSmallIcon(R.drawable.ic_launcher)
                            .setContentTitle("SRIKANTH")
                            .setContentText(value+" Hi, how r u")
                            .setGroup(GROUP_KEY_WORK_EMAIL)
                            .build();
            String title = "CelebKonect";
            String messagesCount = value+" messages from 3 chats";
            Notification summaryNotification =
                    new Notification.Builder(context, chatNotificationChannel)
                            .setContentTitle(title)
                            .setContentText(messagesCount) //set content text to support devices running API level < 24
                            .setSmallIcon(R.drawable.ic_only_logo_white)
                            .setStyle(new Notification.InboxStyle()
                                    .setBigContentTitle(title)
                                    .setSummaryText(messagesCount))
                            .setGroup(GROUP_KEY_WORK_EMAIL)
                            .setGroupSummary(true)
                            .build();
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(value, notificationMessage);
            notificationManager.notify(NOTIFICATION_ID, summaryNotification);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @SuppressLint("NewApi")
    public void groupNotifications(Context context) {
        try {
            int SUMMARY_ID = 0;
            String GROUP_KEY_WORK_EMAIL = "com.android.example.WORK_EMAIL";
            Notification newMessageNotification1 =
                    new NotificationCompat.Builder(context, chatNotificationChannel)
                            .setSmallIcon(R.drawable.ic_launcher)
                            .setContentTitle("emailObject1")
                            .setContentText("You will not believe...")
                            .setGroup(GROUP_KEY_WORK_EMAIL)
                            .build();
            Notification newMessageNotification2 =
                    new NotificationCompat.Builder(context, chatNotificationChannel)
                            .setSmallIcon(R.drawable.ic_launcher)
                            .setContentTitle("emailObject2")
                            .setContentText("Please join us to celebrate the...")
                            .setGroup(GROUP_KEY_WORK_EMAIL)
                            .build();
            Notification summaryNotification =
                    new NotificationCompat.Builder(context, chatNotificationChannel)
                            .setContentTitle("emailObject")
                            //set content text to support devices running API level < 24
                            .setContentText("Two new messages")
                            .setSmallIcon(R.drawable.ic_launcher)
                            //build summary info into InboxStyle template
                            .setStyle(new NotificationCompat.InboxStyle()
                                    .addLine("Alex Faarborg  Check this out")
                                    .addLine("Jeff Chang    Launch Party")
                                    .setBigContentTitle("2 new messages")
                                    .setSummaryText("janedoe@example.com"))
                            //specify which group this notification belongs to
                            .setGroup(GROUP_KEY_WORK_EMAIL)
                            //set this notification as the summary for the group
                            .setGroupSummary(true)
                            .build();
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(1, newMessageNotification1);
            notificationManager.notify(2, newMessageNotification2);
            notificationManager.notify(3, newMessageNotification1);
            notificationManager.notify(4, newMessageNotification2);
            notificationManager.notify(SUMMARY_ID, summaryNotification);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public void setCelebKonectCallLocalNotification(Context context, final JSONObject jsonObject) {
        try {
            Log.e("jsonObjectData", jsonObject.toString() + "");
            String title = "CelebKonect";
            String messageBody = "Hi! " + jsonObject.optString(Constants.SENDER_NAME, "") + " trying to " + " Call you..";
            String serviceType = jsonObject.optString(Constants.SERVICE_TYPE, "");

            Intent intent = new Intent(context, ServiceChatNotificationClick.class);
            intent.putExtra(Constants.NOTIFICATION_SERVICE_TYPE, serviceType);
            intent.putExtra(Constants.NOTIFICATION_OBJECT, jsonObject.toString());
            intent.putExtra("isSelf", true);
            //
            int uniqueID = 0;
            PendingIntent pendingIntentMessage = PendingIntent.getService(context, uniqueID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder messageBuilder = createNotificationBuilder(context, title, messageBody, pendingIntentMessage);
            showNotification(context, messageBuilder.build(), uniqueID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void dismissCallIncomingCallNotification(Context context) {
        try {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.cancel(CALL_COMING_NOTIFICATION_ID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSaveFileNotification(Context context, final JSONObject jsonObject) {
        try {
            Log.e("jsonObjectData", jsonObject.toString() + "");
            String title = "CelebKonect";
            String messageBody = jsonObject.optString("message");
            String fileName = jsonObject.optString("fileName");

            Intent intent = new Intent(context, ServiceChatNotificationClick.class);
            intent.putExtra(Constants.NOTIFICATION_SERVICE_TYPE, "audio");
            intent.putExtra(Constants.NOTIFICATION_OBJECT, jsonObject.toString());
            intent.putExtra("isSelf", true);

            int uniqueID = 0;
            PendingIntent pendingIntentMessage = PendingIntent.getService(context, uniqueID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder messageBuilder = createNotificationBuilder(context, title, messageBody, pendingIntentMessage);
            showNotification(context, messageBuilder.build(), uniqueID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
