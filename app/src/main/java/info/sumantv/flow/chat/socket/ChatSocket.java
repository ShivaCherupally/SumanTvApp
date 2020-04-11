package info.sumantv.flow.chat.socket;

import android.content.Context;
import android.os.Looper;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import info.sumantv.flow.databaseutil.appstart.AppController;
import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.chat.NotificationUtil;
import info.sumantv.flow.chat.models.ChatDataConvertModel;
import info.sumantv.flow.chat.models.ChatDataModel;
import info.sumantv.flow.chat.models.SingleChatHistoryModel;
import info.sumantv.flow.retrofitcall.ApiResponseModel;
import info.sumantv.flow.retrofitcall.SessionManager;
import info.sumantv.flow.userflow.Util.Common;
import info.sumantv.flow.utils.SocketForAppUtill;
import info.sumantv.flow.utils.Utility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ChatSocket {

    private String TAG = "ChatSocket", newUserEmit = "new user", exitUserEmit = "exit user", joinEmit = "join", leaveEmit = "leave",
            joinCallback = "joinSucceed", leaveCallback = "leaveSucceed", onlineUsersCallback = "onlineUsers",
            isOnlineCallback = "isOnline", messageCallback = "new message", typingCallback = "typing", updateChatMessageSatausCallBack = "updateChatMessageSataus",
            recentChatsCallBack = "recentChats", userOnline = "userOnline", userOffline = "userOffline", tokenVerified = "tokenVerified",
            unSeenMessageCountCallBack = "unSeenMessageCount", numberOfUnSeenMessageEmit = "numberOfUnSeenMessage", unSeenNotification = "notifyCount",
            liveLogStatusCallBack = "liveLogStatus", liftedBackgroundCallCallBack = "liftedBackgroundCall",
            liftedBackgroundCallDisconnectedCallBack = "liftedBackgroundCallDisconnected", liftedOnAnotherCallCallBack = "liftedOnAnotherCall",
            liftedCallDisconnectedCallBack = "liftedCallDisconnected",
            broadcastOnlineCelebrities = "onlineCelebrities"; //"broadcastOnlineCelebrities"; //"onlineCelebrities";//";

    private static ChatSocket chatSocket;

    private String screenLockEmit = "screenLock", screenLockStatusList = "screenLockStatus";


    public static ChatSocket getInstance() {
        if (chatSocket == null) {
            chatSocket = new ChatSocket();
        }
        return chatSocket;
    }

    public void onSocketListeners() {
        if (!Common.getInstance().isLoginInApp()) {
            Log.e("notlogged", true + "");
            return;
        }
        if (!Common.getInstance().isSocketConnected()) {
            Common.getInstance().connectSocket();
        }
        joinRecentChats(Utility.getContext());
        try {
            if (!Common.getInstance().getSocket().hasListeners(joinCallback)) {
                Common.getInstance().getSocket().on(joinCallback, emitJoinListener);
            }
            if (!Common.getInstance().getSocket().hasListeners(leaveCallback)) {
                Common.getInstance().getSocket().on(leaveCallback, emitLeaveListener);
            }
            if (!Common.getInstance().getSocket().hasListeners(onlineUsersCallback)) {
                Common.getInstance().getSocket().on(onlineUsersCallback, emitOnlineUsersListener);
            }
            if (!Common.getInstance().getSocket().hasListeners(isOnlineCallback)) {
                Common.getInstance().getSocket().on(isOnlineCallback, emitIsOnlineListener);
            }
            if (!Common.getInstance().getSocket().hasListeners(messageCallback)) {
                Common.getInstance().getSocket().on(messageCallback, emitMessageListener);
            }
            if (!Common.getInstance().getSocket().hasListeners(typingCallback)) {
                Common.getInstance().getSocket().on(typingCallback, emitTypingListener);
            }
            if (!Common.getInstance().getSocket().hasListeners(updateChatMessageSatausCallBack)) {
                Common.getInstance().getSocket().on(updateChatMessageSatausCallBack, emitUpdateChatMessageStatusListener);
            }
            if (!Common.getInstance().getSocket().hasListeners(recentChatsCallBack)) {
                Common.getInstance().getSocket().on(recentChatsCallBack, emitRecentChatsListener);
            }
            if (!Common.getInstance().getSocket().hasListeners(userOnline)) {
                Common.getInstance().getSocket().on(userOnline, emitUserOnline);
            }
            if (!Common.getInstance().getSocket().hasListeners(userOffline)) {
                Common.getInstance().getSocket().on(userOffline, emitUserOffline);
            }
            if (!Common.getInstance().getSocket().hasListeners(unSeenMessageCountCallBack)) {
                Common.getInstance().getSocket().on(unSeenMessageCountCallBack, emitUnSeenMessageCountListener);
            }
            if (!Common.getInstance().getSocket().hasListeners(unSeenNotification)) {
                Common.getInstance().getSocket().on(unSeenNotification, unSeenNotificationCountListener);
            }
            if (!Common.getInstance().getSocket().hasListeners(broadcastOnlineCelebrities)) {
                Common.getInstance().getSocket().on(broadcastOnlineCelebrities, ONLINE_CELEBS);
            }
            if (!Common.getInstance().getSocket().hasListeners(tokenVerified)) {
                Common.getInstance().getSocket().on(tokenVerified, tokenActiveListener);
            }
            if (!Common.getInstance().getSocket().hasListeners(liveLogStatusCallBack)) {
                Common.getInstance().getSocket().on(liveLogStatusCallBack, liveLogStatusListener);
            }
            if (!Common.getInstance().getSocket().hasListeners(liftedBackgroundCallCallBack)) {
                Common.getInstance().getSocket().on(liftedBackgroundCallCallBack, liftedBackgroundCallListener);
            }
            if (!Common.getInstance().getSocket().hasListeners(liftedBackgroundCallDisconnectedCallBack)) {
                Common.getInstance().getSocket().on(liftedBackgroundCallDisconnectedCallBack, liftedBackgroundCallDisconnectedListener);
            }
            if (!Common.getInstance().getSocket().hasListeners(liftedOnAnotherCallCallBack)) {
                Common.getInstance().getSocket().on(liftedOnAnotherCallCallBack, liftedOnAnotherCallListener);
            }
            if (!Common.getInstance().getSocket().hasListeners(liftedCallDisconnectedCallBack)) {
                Common.getInstance().getSocket().on(liftedCallDisconnectedCallBack, liftedCallDisconnectedListener);
            }
            if (!Common.getInstance().getSocket().hasListeners(screenLockStatusList)) {
                Common.getInstance().getSocket().on(screenLockStatusList, screenLockStatusListener);
            }

            Log.e("allCallList", true + "");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void offSocketListeners() {
        if (!Common.getInstance().isSocketConnected()) {
            Common.getInstance().connectSocket();
        }
        leaveRecentChats(Utility.getContext());
        try {
            Common.getInstance().getSocket().off(joinCallback, emitJoinListener);
            Common.getInstance().getSocket().off(leaveCallback, emitLeaveListener);
            Common.getInstance().getSocket().off(onlineUsersCallback, emitOnlineUsersListener);
            Common.getInstance().getSocket().off(isOnlineCallback, emitIsOnlineListener);
            Common.getInstance().getSocket().off(messageCallback, emitMessageListener);
            Common.getInstance().getSocket().off(typingCallback, emitTypingListener);
            Common.getInstance().getSocket().off(updateChatMessageSatausCallBack, emitUpdateChatMessageStatusListener);
            Common.getInstance().getSocket().off(recentChatsCallBack, emitRecentChatsListener);
            Common.getInstance().getSocket().off(userOnline, emitUserOnline);
            Common.getInstance().getSocket().off(userOffline, emitUserOffline);
            Common.getInstance().getSocket().off(unSeenMessageCountCallBack, emitUnSeenMessageCountListener);
            Common.getInstance().getSocket().off(unSeenNotification, unSeenNotificationCountListener);
            Common.getInstance().getSocket().off(broadcastOnlineCelebrities, ONLINE_CELEBS);
            Common.getInstance().getSocket().off(tokenVerified, tokenActiveListener);
            Common.getInstance().getSocket().off(liveLogStatusCallBack, liveLogStatusListener);
            Common.getInstance().getSocket().off(liftedBackgroundCallCallBack, liftedBackgroundCallListener);
            Common.getInstance().getSocket().off(liftedBackgroundCallDisconnectedCallBack, liftedBackgroundCallDisconnectedListener);
            Common.getInstance().getSocket().off(liftedOnAnotherCallCallBack, liftedOnAnotherCallListener);
            Common.getInstance().getSocket().off(liftedCallDisconnectedCallBack, liftedCallDisconnectedListener);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void emitOnline(Context context) {
        if (!Common.getInstance().isSocketConnected()) {
            Common.getInstance().connectSocket();
        }
        if (Common.getInstance().getBottomMenuActivity() == null) {
            Log.e("checkCame", true + "");
            return;
        }
        try {
            String userMemberId = SessionManager.userLogin.userId;
            String userName = Common.getInstance().IsNullReturnValue(SessionManager.userLogin.userName, "");
            String email_id = Common.getInstance().IsNullReturnValue(SessionManager.getInstance().getKeyValue(SessionManager.KEY_EMAIL_OR_MOBILE, ""), "");
            Boolean liveLogStatus = Common.getInstance().isOnlineStatus();
            if (!Common.isCelebStatus(context)) {
                liveLogStatus = true;
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", userMemberId);
            jsonObject.put("username", userName);
            jsonObject.put("email", email_id);
            jsonObject.put("liveLogStatus", liveLogStatus);


            if (userMemberId != null && !userMemberId.isEmpty()) {

                Common.getInstance().getSocket().emit(newUserEmit, jsonObject);

                screenStatusEmit(false); // For new Offline call
            }
            SessionManager.getInstance().setKeyValue(SessionManager.KEY_IS_ONLINE, true);

            //Online Celeb Access
            SocketForAppUtill.getInstance().noticationCountEmit();
            SocketForAppUtill.getInstance().onlineCelebEmit();
            SocketForAppUtill.getInstance().missedCallCountEmit();

            Log.v(TAG, "ZZZZZZ : " + jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exitUserFromApp(Context context) {
        if (!Common.getInstance().isSocketConnected()) {
            Common.getInstance().connectSocket();
        }
        try {

            String userMemberId = SessionManager.userLogin.userId;
            String userName = Common.getInstance().IsNullReturnValue(SessionManager.userLogin.userName, "");

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("userId", userMemberId);
            jsonObject.put("username", userName);

            if (userMemberId != null && !userMemberId.isEmpty()) {
                Log.e("exitUser", jsonObject.toString());
                Common.getInstance().getSocket().emit(exitUserEmit, jsonObject);
            }

            SessionManager.getInstance().setKeyValue(SessionManager.KEY_IS_ONLINE, false);
            Log.v(TAG, "ZZZZZZ : " + jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void joinRecentChats(Context context) {
        if (!Common.getInstance().isSocketConnected()) {
            Common.getInstance().connectSocket();
        }
        try {
            String userName = Common.getInstance().IsNullReturnValue(SessionManager.userLogin.userName, "");
            String userEmail = Common.getInstance().IsNullReturnValue(SessionManager.getInstance().getKeyValue(SessionManager.KEY_EMAIL_OR_MOBILE, ""), "");
            String userMemberId = Common.getInstance().IsNullReturnValue(SessionManager.userLogin.userId, "");

            //Uday
            String newUserID = "CK" + userMemberId;

            //
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", userName);
            jsonObject.put("email", userEmail);
            jsonObject.put("uuid", userMemberId);
            jsonObject.put("chatRoomID", newUserID);
            Common.getInstance().getSocket().emit(joinEmit, jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void leaveRecentChats(Context context) {
        if (!Common.getInstance().isSocketConnected()) {
            Common.getInstance().connectSocket();
        }
        try {
            String userName = Common.getInstance().IsNullReturnValue(SessionManager.userLogin.userName, "");
            String userEmail = Common.getInstance().IsNullReturnValue(SessionManager.getInstance().getKeyValue(SessionManager.KEY_EMAIL_OR_MOBILE, ""), "");
            String userMemberId = Common.getInstance().IsNullReturnValue(SessionManager.userLogin.userId, "");
            //
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", userName);
            jsonObject.put("email", userEmail);
            jsonObject.put("uuid", userMemberId);
            jsonObject.put("chatRoomID", userMemberId);
            Common.getInstance().getSocket().emit(leaveEmit, jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void numberOfUnSeenMessageEmit() {
        try {
            String userMemberId = Common.getInstance().IsNullReturnValue(SessionManager.userLogin.userId, "");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("memberId", userMemberId);
            Common.getInstance().getSocket().emit(numberOfUnSeenMessageEmit, jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Emit Listeners
    private Emitter.Listener emitJoinListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.v(TAG, "emitJoinListener : " + args[0].toString());
                        JSONObject jsonObject = (JSONObject) args[0];
                        if (jsonObject.length() > 0 && Common.getInstance().checkResponseStatus(jsonObject)) {
                            //
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
    private Emitter.Listener emitLeaveListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.v(TAG, "emitLeaveListener : " + args[0].toString());
                        JSONObject jsonObject = (JSONObject) args[0];
                        if (jsonObject.length() > 0 && Common.getInstance().checkResponseStatus(jsonObject)) {
                            //
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
    private Emitter.Listener emitOnlineUsersListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            new android.os.Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        //Log.v(TAG, "emitOnlineUsersListener : " + args[0].toString());
                        JSONArray jsonArray = (JSONArray) args[0];
                        if (jsonArray != null && jsonArray.length() > 0) {
                            if (Common.getInstance().getActivitySingleChat() != null) {
                                Common.getInstance().getActivitySingleChat().updateOnlineStatus(jsonArray);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 100);
        }
    };
    private Emitter.Listener emitIsOnlineListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            new android.os.Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.v(TAG, "emitIsOnlineListener : " + args[0].toString());
                        JSONObject jsonObject = new JSONObject(args[0].toString());
                        if (jsonObject.length() > 0) {
                            if (Common.getInstance().getActivitySingleChat() != null) {
                                Common.getInstance().getActivitySingleChat().updateOnlineStatus(jsonObject);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 100);
        }
    };
    private Emitter.Listener emitTypingListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.v(TAG, "emitTypingListener : " + args[0].toString());
                        JSONObject jsonObject = (JSONObject) args[0];
                        if (Common.getInstance().getActivitySingleChat() != null) {
                            Common.getInstance().getActivitySingleChat().updateTyping(jsonObject);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
    private Emitter.Listener emitUpdateChatMessageStatusListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.v(TAG, "emitUpdateChatMessageStatusListener : " + args[0].toString());
                        JSONObject jsonObject = (JSONObject) args[0];
                        if (jsonObject.length() > 0 && Common.getInstance().checkResponseStatus(jsonObject)) {
                            //
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
    private Emitter.Listener emitMessageListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.v(TAG, "emitMessageListener : " + args[0].toString());
                        JSONObject jsonObject = (JSONObject) args[0];
                        Gson gson = new Gson();
                        Type type = new TypeToken<SingleChatHistoryModel>() {
                        }.getType();
                        SingleChatHistoryModel singleChatHistoryModel = gson.fromJson(jsonObject.optJSONObject("data").optJSONObject("data").toString(), type);
                        if (Common.getInstance().getActivitySingleChat() != null) {
                            Common.getInstance().getActivitySingleChat().updateChatMessages(jsonObject, singleChatHistoryModel);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
    private Emitter.Listener emitRecentChatsListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            try {
                new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.v(TAG, "emitRecentChatsListener : " + args[0].toString());
                            JSONObject jsonObject = (JSONObject) args[0];
                            if (jsonObject.length() > 0 && Common.getInstance().checkResponseStatus(jsonObject)) {
                                String userMemberId = Common.getInstance().IsNullReturnValue(SessionManager.userLogin.userId, "");
                                if (userMemberId == null || userMemberId.isEmpty()) {
                                    return;
                                }
                                Gson gson = new Gson();
                                Type type = new TypeToken<ChatDataModel>() {
                                }.getType();

                                ChatDataModel chatDataModel = gson.fromJson(jsonObject.optString("data"), type);

                                if (chatDataModel != null) {
                                    Boolean canSendNotification = true;
                                    ChatDataConvertModel chatDataConvertModel = Common.getInstance().convertToChatDataConvertModel(chatDataModel, userMemberId);
                                    chatDataConvertModel.chatCount = jsonObject.optJSONObject("data").optInt("chatCount", 0);
                                    chatDataConvertModel.unSeenMessageCount = jsonObject.optJSONObject("data").optInt("unSeenMessageCount", 0);
                                    if (Common.getInstance().getActivitySingleChat() != null) {
                                        String otherPersonId = Common.getInstance().getActivitySingleChat().getOtherPersonReceiverID();
                                        if (otherPersonId.equalsIgnoreCase(chatDataModel.senderId)) {
                                            canSendNotification = false;
                                        }
                                        if (!chatDataConvertModel._id.equalsIgnoreCase(otherPersonId)) {
                                            Common.getInstance().addRecentChatDataConvertModel(chatDataConvertModel, true);
                                        }
                                    } else if (Common.getInstance().getFragmentChatList() != null && Common.getInstance().getFragmentChatList().getFragVisibility()) {
                                        ArrayList<ChatDataConvertModel> recentChatDataConvertModelList = new ArrayList<>();
                                        recentChatDataConvertModelList.add(chatDataConvertModel);
                                        Common.getInstance().getFragmentChatList().updateRecentChatListAdapter(recentChatDataConvertModelList);
                                    } else {
                                        Common.getInstance().addRecentChatDataConvertModel(chatDataConvertModel, true);
                                    }
                                    if (Common.getInstance().getBottomMenuActivity() != null) {
                                        Common.getInstance().getBottomMenuActivity().updateChatCount(chatDataConvertModel.chatCount, chatDataConvertModel.unSeenMessageCount);
                                    }
                                    //
                                    Log.d("SendChatNotify", "" + canSendNotification);
                                    if (canSendNotification && userMemberId.equals(chatDataConvertModel.senderId)) {
                                        new NotificationUtil().sendChatNotification(AppController.getInstance().getContext(), chatDataConvertModel, "No");
                                        //SendChatNotification(AppController.getInstance().getContext(),chatDataConvertModel);
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    private Emitter.Listener emitUserOnline = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.v(TAG, "emitUserOnline : " + args[0].toString());
                        JSONObject jsonObject = (JSONObject) args[0];
                        if (jsonObject.length() > 0 && Common.getInstance().checkResponseStatus(jsonObject)) {
                            //
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
    private Emitter.Listener emitUserOffline = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.v(TAG, "emitUserOffline : " + args[0].toString());
                        JSONObject jsonObject = (JSONObject) args[0];
                        if (jsonObject.length() > 0 && Common.getInstance().checkResponseStatus(jsonObject)) {
                            //
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
    private Emitter.Listener emitUnSeenMessageCountListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.v(TAG, "emitUnSeenMessageCountListener : " + args[0].toString());
                        JSONObject jsonObject = (JSONObject) args[0];
                        if (jsonObject.length() > 0 && Common.getInstance().checkResponseStatus(jsonObject)) {
                            if (Common.getInstance().getBottomMenuActivity() != null) {
                                Common.getInstance().getBottomMenuActivity().updateChatCount(
                                        jsonObject.optJSONObject("data").optInt("chatCount", 0), jsonObject.optJSONObject("data").optInt("unSeenMessageCount", 0));
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
    private Emitter.Listener liveLogStatusListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.v(TAG, "liveLogStatusListener : " + args[0].toString());
                        JSONObject jsonObject = (JSONObject) args[0];
                        if (jsonObject.length() > 0 && Common.getInstance().checkResponseStatus(jsonObject)) {
                            /*Boolean liveStatus = jsonObject.optBoolean("liveStatus", false);
                            Common.getInstance().updateLiveStatusData(liveStatus);*/
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
    private Emitter.Listener unSeenNotificationCountListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.v("emitUnSeenNtn", args[0].toString() + "");
                        JSONObject jsonObject = (JSONObject) args[0];
                        if (jsonObject.length() > 0) {
                            if (Common.getInstance().getBottomMenuActivity() != null) {
                                if (jsonObject.getString("memberId").equals(Common.isLoginId())) {
                                    Common.getInstance().getBottomMenuActivity().updateNotificationCount(
                                            jsonObject.getInt("count"));
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
    private Emitter.Listener ONLINE_CELEBS = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    try {
//                        Log.v("onlineCelebs", "celebList : " + args[0].toString());
                        JSONObject jsonObject = (JSONObject) args[0];
                        Log.v("onlineCelebsSize", "celebList-ChatSocket : " + jsonObject.length());
                        if (jsonObject.length() > 0) {
                            Common.getInstance().setOnlineCelebList(jsonObject);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
    private Emitter.Listener tokenActiveListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject jsonObject = (JSONObject) args[0];
                        Type type = new TypeToken<ApiResponseModel>() {
                        }.getType();

                        String userID = jsonObject.optString("userID", "");
                        if (Common.getInstance().isSwicthed()) {
                            if (Common.isBeingManager(userID)) {
                                tokenExpired(type, jsonObject);
                            }
                        } else {
                            if (userID.equals(Common.isLoginId())) {
                                tokenExpired(type, jsonObject);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    private void tokenExpired(Type type, JSONObject jsonObject) {
        Log.e("tokenExpire", jsonObject.toString() + "");
        ApiResponseModel apiResponseModel = new Gson().fromJson(jsonObject.toString(), type);
        if (apiResponseModel.success.equals(Constants.TOKEN_AUTHENTICATION_FAIL)) {
            if (!SessionManager.getInstance().getKeyValue(SessionManager.KEY_API_TOKEN, "").equals(apiResponseModel.token)) {
                Common.getInstance().LogOut(Utility.getContext(), "false", apiResponseModel.success);
                if (apiResponseModel.message != null && !apiResponseModel.message.isEmpty()) {
                    Common.getInstance().cusToast(Utility.getContext(), apiResponseModel.message);
                } else {
                    //Checking -6-6-19

                    Common.getInstance().cusToast(Utility.getContext(), "Looks like you have logged in on other device");
                }
            }
        }
    }

    private Emitter.Listener liftedBackgroundCallListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject jsonObject = (JSONObject) args[0];
                        if (jsonObject != null && jsonObject.length() > 0) {
                            String serviceType = jsonObject.optJSONObject("data").optString("serviceType", "");
                            Log.e("onPauseState", jsonObject.toString() + "");

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    private Emitter.Listener liftedBackgroundCallDisconnectedListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject jsonObject = (JSONObject) args[0];
                        if (jsonObject != null && jsonObject.length() > 0) {
                            String serviceType = jsonObject.optJSONObject("data").optString("serviceType", "");
                            Log.e("onPauseStateRelease", jsonObject.toString() + "");

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
    private Emitter.Listener liftedOnAnotherCallListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject jsonObject = (JSONObject) args[0];
                        if (jsonObject != null && jsonObject.length() > 0) {
                            //Common.getInstance().cusToast(Utility.getContext(),jsonObject.optString("message",""));

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    private Emitter.Listener liftedCallDisconnectedListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject jsonObject = (JSONObject) args[0];
                        if (jsonObject != null && jsonObject.length() > 0) {

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    public void screenStatusEmit(boolean isScreenStatus) {
        try {

            if (Common.getInstance().isLoginInApp()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("userId", Common.isLoginId());
                jsonObject.put("ScreenLockStatus", isScreenStatus);
                jsonObject.put("osType", Constants.ANDROID);


                if (Common.isCelebStatus(AppController.getInstance().getContext())) {
                    jsonObject.put("isCeleb", true);
                } else {
                    jsonObject.put("isCeleb", false);
                }


                Log.e("screenLockEmit", jsonObject.toString() + "");

                if (Common.checkInternetConnection(Utility.getContext())) {
                    if (!Common.getInstance().isSocketConnected()) {
                        Common.getInstance().connectSocket();
                    }
                    Common.getInstance().getSocket().emit(screenLockEmit, jsonObject);


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Emitter.Listener screenLockStatusListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.v("screenLockStatusList : ", args[0].toString());
                        JSONObject jsonObject = (JSONObject) args[0];

                    } catch (Exception e) {
                        Log.v("screenLockStatusList : ", false + "");
                        e.printStackTrace();
                    }
                }
            });
        }
    };
}
