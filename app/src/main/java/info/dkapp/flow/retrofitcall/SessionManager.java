package info.dkapp.flow.retrofitcall;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import info.dkapp.flow.databaseutil.appstart.AppController;
import info.dkapp.flow.userflow.UserActivitys.LoginActivity.LoginResponse;
import info.dkapp.flow.userflow.Util.Common;
import info.dkapp.flow.utils.Utility;

public class SessionManager {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;
    public static UserLogin userLogin = new UserLogin();
    //
    public static String KEY_API_TOKEN = "KEY_API_TOKEN", KEY_USER_ID = "KEY_USstorySeenStatusER_ID", KEY_USER_NAME = "KEY_USER_NAME", KEY_FIRST_NAME = "KEY_FIRST_NAME", KEY_LAST_NAME = "KEY_LAST_NAME", KEY_PROFILE_PIC = "KEY_PROFILE_PIC", KEY_COVER_PIC = "KEY_COVER_PIC", KEY_ABOUT = "KEY_ABOUT", KEY_MOBILE_NUMBER = "KEY_MOBILE_NUMBER", KEY_EMAIL = "KEY_EMAIL", KEY_COUNTRY_CODE = "KEY_COUNTRY_CODE", KEY_REFERRAL_CREDITS = "KEY_REFERRAL_CREDITS", KEY_MAIN_CREDITS = "KEY_MAIN_CREDITS", KEY_IS_USERNAME_VERIFIED = "KEY_IS_USERNAME_VERIFIED", KEY_IS_PREFERENCES_SELECTED = "KEY_IS_PREFERENCES_SELECTED", KEY_IS_MOBILE_VERIFIED = "KEY_IS_MOBILE_VERIFIED", KEY_IS_EMAIL_VERIFIED = "KEY_IS_EMAIL_VERIFIED", KEY_IS_ONLINE = "KEY_IS_ONLINE", KEY_IS_MANAGER = "KEY_IS_MANAGER", KEY_IS_CELEB = "KEY_IS_CELEB", KEY_LIVE_STATUS = "KEY_LIVE_STATUS", KEY_PROFESSION = "KEY_PROFESSION", KEY_REFERRAL_CODE = "KEY_REFERRAL_CODE", KEY_LOGIN_TYPE = "KEY_LOGIN_TYPE", KEY_PAUSE_SEARCH_HISTORY = "KEY_PAUSE_SEARCH_HISTORY", KEY_SHOW_HD_IMAGES = "KEY_SHOW_HD_IMAGES", KEY_AUTO_PLAY_GIFS = "KEY_AUTO_PLAY_GIFS", KEY_AUTO_PLAY_VIDEOS = "KEY_AUTO_PLAY_VIDEOS", KEY_IS_MUTE_ENABLED = "KEY_IS_MUTE_ENABLED", KEY_FEED_GRID_STYLE = "KEY_FEED_GRID_STYLE",KEY_EMAIL_OR_MOBILE = "EMAIL_OR_MOBILE",KEY_MOBILE_NO = "MOBILE_NO",KEY_AUDITION_ID = "AUDITION_ID",KEY_EDIT_VALUE = "eidtValue",KEY_MANAGER_OWN_ID = "MANAGER_OWN_ID",KEY_MANAGER_NAME = "MANAGER_NAME",KEY_SWITCHIED_CELEB_USERNAME = "SWITCHIED_CELEB_USERNAME",KEY_SWITCHIED_CELEBID = "SWITCHIED_CELEBID",KEY_IS_SWITCH_USER_DIALOG_OPEN = "isSwitchUserDialogOpen",
                        KEY_SUB_MANAGER_ACCESS = "SUB_MANAGER_ACCESS",KEY_SUB_MANAGER_ID = "SUB_MANAGER_ID",KEY_MANAGE_CELEB_ID = "MANAGE_CELEB_ID",KEY_IS_RECEIVERID = "isReceiverId",KEY_ROLE = "ROLE",KEY_SIGN_IN_ACCESS = "SIGN_IN_ACCESS",KEY_TERMS_CONDITION_ALREADY_ACCESS = "TERMS_CONDITION_ALREADY_ACCESS",KEY_MEMBER_OR_CELEB_PROFILE = "MEMBER_OR_CELEB_PROFILE",KEY_SELF_MEMBER_PROFILE = "SELF_MEMBER_PROFILE",KEY_REF_PAYMENT_TRANSACTION_ID= "refPaymentTransactionId",
            KEY_CREDIT_REACHARGE_Credits = "CreditReachrgeCredits",KEY_REMEMBER_ME = "REMEMBER_ME",KEY_EMAIL_ID = "EMAIL_ID",KEY_FIRSTTIME_EDIT_PROFILE_ACCESS = "FIRSTTIME_EDIT_PROFILE_ACCESS",KEY_FIRST_EDIT_PROFILE_NOT_COMPLETE = "FIRST_EDIT_PROFILE_NOT_COMPLETE",KEY_SWITCH_ACCOUNT = "SwitchAccount",KEY_IS_BUSY_ONCALL_STATUS = "isBusyOnCallStatus",KEY_FIRST_TIME_SPLASH_LOAD = "FIRST_TIME_SPLASH_LOAD",KEY_IS_CALL_INITIATE = "IsCallInitiate",KEY_IS_PASSWORD_VERIFIED = "isPasswordVerified",KEY_PREF_ACCOUNT_NAME = "accountName",KEY_CLEAR_LOCAL_DATA = "LOCAL_DATA_CLEAR";


    @SuppressLint("StaticFieldLeak")
    private static SessionManager objInstance;

    public static SessionManager getInstance() {
        if (objInstance == null) {
            objInstance = new SessionManager();
        }
        return objInstance;
    }

    @SuppressLint("CommitPrefEdits")
    private SessionManager() {
        this.context = Utility.getContext();
        pref = context.getSharedPreferences("CelebKonect", Context.MODE_PRIVATE);
        editor = pref.edit();
    }
    //

    public void setUserLoginData(LoginResponse loginResponse) {
        try {
            setKeyValue(KEY_API_TOKEN, Common.getInstance().IsNullReturnValue(loginResponse.loginInfo.token,""));
            setKeyValue(KEY_USER_ID,Common.getInstance().IsNullReturnValue(loginResponse.userInfo.id,""));
            setKeyValue(KEY_USER_NAME,Common.getInstance().IsNullReturnValue(loginResponse.userInfo.username,""));
            setKeyValue(KEY_FIRST_NAME,Common.getInstance().IsNullReturnValue(loginResponse.userInfo.firstName,""));
            setKeyValue(KEY_PROFILE_PIC,Common.getInstance().IsNullReturnValue(loginResponse.userInfo.avtarImgPath,""));
            setKeyValue(KEY_LAST_NAME,Common.getInstance().IsNullReturnValue(loginResponse.userInfo.lastName,""));
            setKeyValue(KEY_COVER_PIC,Common.getInstance().IsNullReturnValue("",""));
            setKeyValue(KEY_ABOUT,Common.getInstance().IsNullReturnValue(loginResponse.userInfo.aboutMe,""));
            setKeyValue(KEY_MOBILE_NUMBER,Common.getInstance().IsNullReturnValue(loginResponse.userInfo.mobileNumber,""));
            setKeyValue(KEY_EMAIL,Common.getInstance().IsNullReturnValue(loginResponse.userInfo.email,""));
            setKeyValue(KEY_COUNTRY_CODE,Common.getInstance().IsNullReturnValue(loginResponse.userInfo.country,""));
            setKeyValue(KEY_REFERRAL_CREDITS,loginResponse.creditInfo.referralCreditValue);
            setKeyValue(KEY_MAIN_CREDITS,loginResponse.creditInfo.cumulativeCreditValue);
            setKeyValue(KEY_IS_USERNAME_VERIFIED,loginResponse.userInfo.isUsernameVerified);
            setKeyValue(KEY_IS_PREFERENCES_SELECTED,false);
            setKeyValue(KEY_IS_MOBILE_VERIFIED,loginResponse.userInfo.isMobileVerified);
            setKeyValue(KEY_IS_EMAIL_VERIFIED,loginResponse.userInfo.isEmailVerified);
            setKeyValue(KEY_IS_ONLINE,loginResponse.userInfo.isOnline);
            setKeyValue(KEY_IS_CELEB,loginResponse.userInfo.isCeleb);
            setKeyValue(KEY_IS_MANAGER,loginResponse.userInfo.isManager);
            setKeyValue(KEY_LIVE_STATUS,Common.getInstance().IsNullReturnValue(loginResponse.userInfo.liveStatus,""));
            setKeyValue(KEY_PROFESSION,Common.getInstance().IsNullReturnValue(loginResponse.userInfo.profession,""));
            setKeyValue(KEY_REFERRAL_CODE,Common.getInstance().IsNullReturnValue(loginResponse.userInfo.referralCode,""));
            setKeyValue(KEY_LOGIN_TYPE,Common.getInstance().IsNullReturnValue(loginResponse.userInfo.loginType,""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //
        getUserLoginData();
    }

    public void getUserLoginData(){
        userLogin = new UserLogin();
        userLogin.token = getKeyValue(KEY_API_TOKEN,"");
        userLogin.userId = getKeyValue(KEY_USER_ID,"");
        userLogin.userName = getKeyValue(KEY_USER_NAME,"");
        userLogin.firstName = getKeyValue(KEY_FIRST_NAME,"");
        userLogin.lastName = getKeyValue(KEY_LAST_NAME,"");
        userLogin.profilePic = getKeyValue(KEY_PROFILE_PIC,"");
        userLogin.coverPic = getKeyValue(KEY_COVER_PIC,"");
        userLogin.about = getKeyValue(KEY_ABOUT,"");
        userLogin.mobileNumber = getKeyValue(KEY_MOBILE_NUMBER,"");
        userLogin.email = getKeyValue(KEY_EMAIL,"");
        userLogin.countryCode = getKeyValue(KEY_COUNTRY_CODE,"");
        userLogin.referralCredits = getKeyValue(KEY_REFERRAL_CREDITS,0.0);
        userLogin.mainCredits = getKeyValue(KEY_MAIN_CREDITS,0.0);
        userLogin.isUserNameVerified = getKeyValue(KEY_IS_USERNAME_VERIFIED,false);
        userLogin.isPreferencesSelected = getKeyValue(KEY_IS_PREFERENCES_SELECTED,false);
        userLogin.isMobileVerified = getKeyValue(KEY_IS_MOBILE_VERIFIED,false);
        userLogin.isEmailVerified = getKeyValue(KEY_IS_EMAIL_VERIFIED,false);
        userLogin.isOnline = getKeyValue(KEY_IS_ONLINE,false);
        userLogin.isCeleb = getKeyValue(KEY_IS_CELEB,false);
        userLogin.isManager = getKeyValue(KEY_IS_MANAGER,false);
        userLogin.liveStatus = getKeyValue(KEY_LIVE_STATUS,"");
        userLogin.profession = getKeyValue(KEY_PROFESSION,"");
        userLogin.referralCode = getKeyValue(KEY_REFERRAL_CODE,"");
        userLogin.loginType = getKeyValue(KEY_LOGIN_TYPE,"");
    }

    //
    public boolean checkKeyExist(String key){
        return pref.contains(key);
    }

    public void setKeyValue(String key, String value) {
        if (value != null && !value.isEmpty()) {
            editor.putString(key, value);
            editor.commit();
        }
        getUserLoginData();
    }

    public void setKeyValue(String key, Boolean value) {
        if (value != null) {
            editor.putBoolean(key, value);
            editor.commit();
        }
        getUserLoginData();
    }

    public void setKeyValue(String key, Integer value) {
        if (value != null) {
            editor.putInt(key, value);
            editor.commit();
        }
        getUserLoginData();
    }

    public void setKeyValue(String key, Float value) {
        if (value != null) {
            editor.putFloat(key, value);
            editor.commit();
        }
        getUserLoginData();
    }

    public void setKeyValue(String key, Double value) {
        if (value != null) {
            editor.putLong(key, Double.doubleToRawLongBits(value));
            editor.commit();
        }
        getUserLoginData();
    }

    public String getKeyValue(String key, String defaultValue) {
        return pref.getString(key, defaultValue);
    }

    public Boolean getKeyValue(String key, Boolean defaultValue) {
        return pref.getBoolean(key, defaultValue);
    }

    public Integer getKeyValue(String key, Integer defaultValue) {
        return pref.getInt(key, defaultValue);
    }

    public Float getKeyValue(String key, Float defaultValue) {
        return pref.getFloat(key, defaultValue);
    }

    public Double getKeyValue(String key, Double defaultValue) {
        try {
            return  Double.longBitsToDouble(pref.getLong(key, Double.doubleToLongBits(defaultValue)));
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0.0;
    }

    //
    public void removeKey(String key) {
        editor.remove(key);
        editor.commit();
        //
        getUserLoginData();
    }

    public void removeAll() {
        editor.clear();
        editor.commit();
        //
        getUserLoginData();
    }
    //

    public void appendUserDetails(LoginResponse loginResponse, Boolean isSocialLogin) {
        setUserLoginData(loginResponse);
        //
        if (loginResponse.userInfo.email != null && !loginResponse.userInfo.email.isEmpty()) {
            //EMAIL_ID
            SessionManager.getInstance().setKeyValue(SessionManager.KEY_EMAIL_OR_MOBILE, loginResponse.userInfo.email);

        } else {
            SessionManager.getInstance().setKeyValue(SessionManager.KEY_EMAIL_OR_MOBILE, "");
        }
        if (loginResponse.userInfo.username != null && !loginResponse.userInfo.username.isEmpty()) {
            SessionManager.getInstance().setKeyValue(SessionManager.KEY_USER_NAME, loginResponse.userInfo.username);
            Log.e("USERId", loginResponse.userInfo.id + "_INMYSIDe");
            SessionManager.getInstance().setKeyValue(SessionManager.KEY_USER_ID,loginResponse.userInfo.id);
            SessionManager.getInstance().setKeyValue(KEY_ROLE,loginResponse.userInfo.role);

            if (loginResponse.userInfo.avtarOriginalname != null && !loginResponse.userInfo.avtarOriginalname.isEmpty()) {
                String profileImg = ApiClient.BASE_URL + loginResponse.userInfo.avtarOriginalname;   // iv_user_pic
                SessionManager.getInstance().setKeyValue(SessionManager.KEY_PROFILE_PIC, loginResponse.userInfo.avtarOriginalname);

            } else {
                SessionManager.getInstance().setKeyValue(SessionManager.KEY_PROFILE_PIC, "");
            }

            String country = Common.getInstance().IsNullReturnValue(loginResponse.userInfo.country, "");
            if (!country.isEmpty()) {
                SessionManager.getInstance().setKeyValue(SessionManager.KEY_COUNTRY_CODE,country);
            }
            if (loginResponse.userInfo.loginType != null && !loginResponse.userInfo.loginType.isEmpty()) {
                SessionManager.getInstance().setKeyValue(SessionManager.KEY_LOGIN_TYPE,
                        loginResponse.userInfo.loginType);
            } else {
                SessionManager.getInstance().setKeyValue(SessionManager.KEY_LOGIN_TYPE,
                        "");
            }

            if (loginResponse.userInfo.firstName != null && !loginResponse.userInfo.firstName.isEmpty()) {
                SessionManager.getInstance().setKeyValue(SessionManager.KEY_FIRST_NAME,loginResponse.userInfo.firstName);
            } else {
                SessionManager.getInstance().setKeyValue(SessionManager.KEY_FIRST_NAME,"");
            }

            if (loginResponse.userInfo.lastName != null && !loginResponse.userInfo.lastName.isEmpty()) {
                SessionManager.getInstance().setKeyValue(SessionManager.KEY_LAST_NAME,loginResponse.userInfo.lastName);
            } else {
                SessionManager.getInstance().setKeyValue(SessionManager.KEY_LAST_NAME,"");
            }


            if (loginResponse.userInfo.isCeleb) {
                SessionManager.getInstance().setKeyValue(SessionManager.KEY_IS_CELEB,true);
            } else {
                SessionManager.getInstance().setKeyValue(SessionManager.KEY_IS_CELEB,false);
            }

            if (loginResponse.userInfo.role != null && !loginResponse.userInfo.role.isEmpty() && !loginResponse.userInfo.role.equals("null")) {
                SessionManager.getInstance().setKeyValue(KEY_ROLE,loginResponse.userInfo.role);
            } else {
                SessionManager.getInstance().setKeyValue(KEY_ROLE,"");
            }

            if (loginResponse.userInfo.liveStatus.equals("online")) {
                SessionManager.getInstance().setKeyValue(SessionManager.KEY_LIVE_STATUS,"TRUE");
            } else {
                SessionManager.getInstance().setKeyValue(SessionManager.KEY_LIVE_STATUS,"FALSE");
            }


            if (loginResponse.userInfo.avtarImgPath != null && !loginResponse.userInfo.avtarImgPath.isEmpty()) {
                String profileImg = ApiClient.BASE_URL + loginResponse.userInfo.avtarImgPath;
                SessionManager.getInstance().setKeyValue(SessionManager.KEY_PROFILE_PIC, loginResponse.userInfo.avtarImgPath);
                Log.e("profileImg", profileImg + "_my");
            } else {
                SessionManager.getInstance().setKeyValue(SessionManager.KEY_PROFILE_PIC, "");
            }

            if (loginResponse.userInfo.mobileNumber != null && !loginResponse.userInfo.mobileNumber.isEmpty()) {
                SessionManager.getInstance().setKeyValue(SessionManager.KEY_MOBILE_NO,loginResponse.userInfo.mobileNumber);
            } else {
                //MOBILE_NO
                SessionManager.getInstance().setKeyValue(SessionManager.KEY_MOBILE_NO,"");
            }


            if (loginResponse.userInfo.aboutMe != null && !loginResponse.userInfo.aboutMe.isEmpty()) {
                SessionManager.getInstance().setKeyValue(SessionManager.KEY_ABOUT,loginResponse.userInfo.aboutMe);
            } else {
                SessionManager.getInstance().setKeyValue(SessionManager.KEY_ABOUT,"");
            }

            if (loginResponse.creditInfo != null) {

                Common.getInstance().setAvailableBalance(loginResponse.creditInfo.cumulativeCreditValue);
                Common.getInstance().setRefferalCredits(loginResponse.creditInfo.referralCreditValue);

            }
            if (loginResponse.userInfo.isManager) {
                SessionManager.getInstance().setKeyValue(SessionManager.KEY_IS_MANAGER,true);
            } else {
                SessionManager.getInstance().setKeyValue(SessionManager.KEY_IS_MANAGER,false);
            }

            if (loginResponse.userInfo.isEmailVerified != null) {
                SessionManager.getInstance().setKeyValue(SessionManager.KEY_IS_EMAIL_VERIFIED,loginResponse.userInfo.isEmailVerified);
            }
            if (loginResponse.userInfo.isMobileVerified != null) {
                SessionManager.getInstance().setKeyValue(SessionManager.KEY_IS_MOBILE_VERIFIED,loginResponse.userInfo.isMobileVerified);            }
            managerSwitchKeys();
            dndNotEnableAvailable(loginResponse);
        } else {
            //Common.getInstance().navigateToFeedPageHome(this);
        }
    }

    private void dndNotEnableAvailable(LoginResponse loginResponse) {
        if (loginResponse.userInfo.isEmailVerified == null) {
            loginResponse.userInfo.isEmailVerified = false;
        }
        if (loginResponse.userInfo.isMobileVerified == null) {
            loginResponse.userInfo.isMobileVerified = false;
        }
        if (loginResponse.userInfo.dnd == null) {
            loginResponse.userInfo.dnd = "";
        }
        if (loginResponse.userInfo.firstName == null) {
            loginResponse.userInfo.firstName = "";
        }
        if (loginResponse.isPreferencesSelected == null || !loginResponse.isPreferencesSelected) {
            Common.getInstance().openPreferencesFrag(AppController.getInstance().getCurrentRegisteredActivity(), true);
        } else if ((loginResponse.userInfo.isEmailVerified || loginResponse.userInfo.isMobileVerified) && loginResponse.userInfo.dnd.equals("true") && !loginResponse.userInfo.firstName.isEmpty()) {
            Common.getInstance().navigateToFeedPageHome(AppController.getInstance().getCurrentRegisteredActivity());
        } else {
            Common.getInstance().navigateToFeedPageHome(AppController.getInstance().getCurrentRegisteredActivity());
        }
    }

    public void managerSwitchKeys() {
        SessionManager.getInstance().setKeyValue(SessionManager.KEY_MANAGER_OWN_ID, "");
        SessionManager.getInstance().setKeyValue(SessionManager.KEY_SWITCHIED_CELEBID, "");
        SessionManager.getInstance().setKeyValue(SessionManager.KEY_SWITCHIED_CELEB_USERNAME, "");
    }
}
