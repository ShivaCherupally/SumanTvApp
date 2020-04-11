package info.sumantv.flow.retrofitcall;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    //Live
    /*public static final String BASE_URL = "https://prodapi.celebkonect.com:4300/";
    public static final String SERVICE_CHAT_SOCKET_PORT_URL = "https://prodapi.celebkonect.com:3000/";
    public static final String WEB_SITE = "https://celebkonect.com/portal/#/";
    public static final String WEB_SITE_FEED_SHARE = "https://celebkonect.com:4200/";

    //Instamojo
    public static final String INSTAMOJO = "https://www.instamojo.com/api/1.1/payment-requests/";
    public static final String INSTAMOJO_TRANSACTION = "https://www.instamojo.com/api/1.1/payment-requests/";
    public static final String XApiKey = "f540ee8448a9d0a56469938f2b281f4d";
    public static final String XAuthToken = "5e2048cf779a9ace89a38d62a8e294ca";
    public static final String ContentType = "application/json";

    public static final String ENVIRONMENT = "live";

    //Paytm
    public static final String MERCHANT_ID = "IENTER56499813066149";
    public static final String WEBSITE = "APPPROD";
    public static final String INDUSTRY_TYPE = "Retail109";
    public static final String PAYTM_CALLBACK_URL = "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID=";
    public static final String WAP = "WAP";*/

    //Demo
//    public static final String BASE_URL = "https://devapi.celebkonect.com:4700/";
//    public static final String SERVICE_CHAT_SOCKET_PORT_URL = "https://devapi.celebkonect.com:3003/";
//    public static final String WEB_SITE = "https://dev.celebkonect.com/portal/#/";
//    public static final String WEB_SITE_FEED_SHARE = "https://dev.celebkonect.com:4100/";

    //Local
//    public static final String BASE_URL = "http://192.168.1.44:4300/";
//    public static final String SERVICE_CHAT_SOCKET_PORT_URL = "http://192.168.1.44:3000/";
//    public static final String WEB_SITE = "https://test.celebkonect.com/portal/#/";
//    public static final String WEB_SITE_FEED_SHARE = "https://test.celebkonect.com:4100/";

    //Test
    public static final String BASE_URL = "https://testapi.celebkonect.com:4300/";
    public static final String SERVICE_CHAT_SOCKET_PORT_URL = "https://testapi.celebkonect.com:3000/";
    public static final String WEB_SITE = "https://test.celsebkonect.com/portal/#/";
    public static final String WEB_SITE_FEED_SHARE = "https://test.celebkonect.com:4100/";

    //Test or Demo  Environment APIS
    public static final String ENVIRONMENT = "test";
    public static final String MERCHANT_ID = "JLOTGY48867147844550";
    public static final String WEBSITE = "APPSTAGING";
    public static final String INDUSTRY_TYPE = "Retail";

    public static final String PAYTM_CALLBACK_URL = "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID=";
    public static final String WAP = "WAP";
    public static final String XApiKey = "test_b9cea638b817efe3f5818251640";
    public static final String XAuthToken = "test_fcc8a3b611c9f7277a2443c22e0";

    //Instamojo
    public static final String INSTAMOJO = "https://test.instamojo.com/api/1.1/payment-requests/";
    public static final String INSTAMOJO_TRANSACTION = "https://test.instamojo.com/api/1.1/payment-requests/";
    public static final String ContentType = "application/json";

    public static final String CHANGEPASSWORD_URL = BASE_URL + "admin/resetPasswordByEmail/";

    public static final String LOGIN_URL = BASE_URL + "logininfo/login";

    public static final String LOGINDETAILS_URL = BASE_URL + "users/getMemberByEmail/";

    public static final String GET_MANAGER_PROFILE = BASE_URL + "celebManagers/getManagerProfile/";

    public static final String GET_ALL_SPLASH_SCREENS = BASE_URL + "splashscreen/splashscreenbycountry/";

    public static final String APP_SETTING_CREATE_URL = BASE_URL + "appsettings/create/";

    public static final String GETSETTINGSDATA_BY_USERID = BASE_URL + "appsettings/getByUserID/";

    public static final String UPDATE_APP_SETTINGS = BASE_URL + "appsettings/edit/";

    public static final String GET_SLOTS_LIST = BASE_URL + "slotMaster/getSlotsByServiceMemberId/";

    public static final String GET_SLOT_DETAILS = BASE_URL + "slotMaster/getScheduleInfo/";

    public static final String FOLLOW_CELEB = BASE_URL + "memberpreferences/setMemberCelebrityAsFollower/";

    public static final String GET_ONLINE_CELEBS = BASE_URL + "users/getOnlineCelebrities/";

    public static final String GET_STORIES_CELEBS = BASE_URL + "story/getStoryProfile/";

    public static final String GET_CONTRACTS = BASE_URL + "celebritycontract/getContractsByMemberIdByService/";

    public static final String GET_SERVER_TIME = BASE_URL + "slotMaster/currentTime";

    public static final String FAN_CELB_BY_MEMBER = BASE_URL + "memberpreferences/fanCelebritiesbyMember/";

    public static final String FOLLOWING_CELB_BY_MEMBER = BASE_URL + "memberpreferences/followingCelebritiesByMember/";

    public static final String FANS_MEMBER_BY_CELB = BASE_URL + "memberpreferences/fanMembersbyCelebrity/";

    public static final String FOLLOWING_MEMBER_BY_CELB = BASE_URL + "memberpreferences/followingMembersbyCelebrity/";

    public static final String GET_REFFERAL_CODE = BASE_URL + "referralCode/getByMemberId/";

    public static final String GET_MEDIA_DATA = BASE_URL + "membermedia/getMemberMedia/";

    public static final String GET_MEDIA_NEXT_PREVIOUS = BASE_URL + "membermedia/getMemberMediaWithPreAndNext/";

    public static final String GET_INDUSRTY_PROFESSION = BASE_URL + "preferences/getPreferencesByParentlist/";

    public static final String DELETE_SELF_MEDIA = BASE_URL + "memberMedia/deleteMedia/";

    public static final String MY_ORDERS_LIST = BASE_URL + "orders/getOrdersByMemberId/";

    public static final String MY_CONTENTS = BASE_URL + "feeddata/getFeedByMemberId/";

    public static final String GET_ACTIVITY_LOG = BASE_URL + "activityLog/getActivityLogByMemberId/";

    public static final String UNFAN_CELEB = BASE_URL + "memberpreferences/unFan/";

    public static final String UNFOLLOW_CELEB = BASE_URL + "memberpreferences/unFollow/";

    public static final String SCHEDULE_DELETE = BASE_URL + "slotMaster/deleteSchedules/";
    //
    public static final String PASS_LOG_STATUS = BASE_URL + "livetimelog/create";

    public static final String INSERT_PAYMENT_TRANSACTION = BASE_URL + "paymentTransaction/createPaymentTransaction";

    public static final String LOGOUT_USER = BASE_URL + "logininfo/logout/";

    public static final String CHECK_APP_UPDATE = BASE_URL + "appupdate/appupdateinfo";

    public static final String SET_MEMBER_PREFERENCE = BASE_URL + "memberpreferences/setMemberPreferences";

    public static final String CREATE_CREDITS = BASE_URL + "credits/createCredits";

    public static final String GET_CREDIT_BALANCE_BLOCK = BASE_URL + "credits/getBlockCheck/";

    public static final String GET_CREDIT_BALANCE = BASE_URL + "credits/getCreditBalanceByMemberID/";

    public static final String GET_CHECK_BAL = BASE_URL + "credits/getCheckBalance/";

    public static final String GET_NOTIFICATIONS = BASE_URL + "notification/getNotificationsByServiceTypeAndMemberID/";

    public static final String GET_ARCHIVE_NOTIFICATIONS = BASE_URL + "notification/getArchivedNotificationsByServiceTypeAndMemberID/";

    public static final String GET_ARCHIVE_NOTIFICATIONS_ALL = BASE_URL + "notification/getArchivedNotificationsByMemberID/";

    public static final String GET_NOTIFICATIONS_ALL = BASE_URL + "notification/getNotificationsByMemberID/";

    public static final String NOTIFICATION_UPDATE = BASE_URL + "notification/updateNotification";

    public static final String NOTIFICATION_DELETE = BASE_URL + "notification/deleteMultipleNotification/";

    public static final String GET_CHARITY = BASE_URL + "charitySettings/getAll";

    public static final String UPDATE_CHARITY = BASE_URL + "charitySettings/edit/";

    public static final String GET_TERMS_CONDITION = BASE_URL + "appCms/getContent/auditions/termsAndConditions/";

    public static final String SERVICE_TERMS_CONDITION = BASE_URL + "appCms/getContent/celebkonect/termsAndConditionsAPP/";

    public static final String SHARE_FEED_IN_SOCIAL_MEDIA = WEB_SITE_FEED_SHARE + "feed/";

    public static final String SHARE_AUDITION_IN_SOCIAL_MEDIA = WEB_SITE + "auditionrole/";

    public static final String SHARE_GROUP_TALENT_IN_SOCIAL_MEDIA = WEB_SITE + "auditionprofile/";

    public static final String GET_ALL_PREFERENCES = BASE_URL + "preferences/getAllPreferances/";

    public static final String GET_COUNTRY_LIST = BASE_URL + "countries/getAll/";

    public static final String FAQ_WEB_LINK = BASE_URL + "appCms/getfaqs";

    public static final String GET_NOTIFICATION_SWTTCH_LIST = BASE_URL + "notificationMaster/getAll/";

    public static final String SAVE_NOTIFICATION = BASE_URL + "notificationSettings/setNotificationSettings/";

    public static final String CONTACT_US_WEB = BASE_URL + "appCms/contactuspage";

    public static final String ABOUT_US_WEB = BASE_URL + "appCms/aboutuspage";

    public static final String SEND_OTP = BASE_URL + "notification/getOTP";

    public static final String SUBMIT_OTP = BASE_URL + "notification/verifyOTP";

    public static final String GET_USER_COMPLETE_DATA = BASE_URL + "users/getAllDetailsOfCelebrity/";

    public static final String GET_CELEBRITY_COMPLETE_DATA = BASE_URL + "users/getAllDetailsOfCelebrityForMemberId/";

    public static final String GET_COUNTRIES_ALL = BASE_URL + "countries/getAll";

    public static final String BLOCK_USER = BASE_URL + "memberpreferences/blockUser/";

    public static final String CALL_INITIATE_URL = BASE_URL + "serviceTransaction/createServiceTransaction/";

    public static final String CREATE_SHEDULE = BASE_URL + "slotMaster/createSchedule";

    public static final String BOOK_SLOT = BASE_URL + "serviceSchedule/bookSchedule";

    public static final String GET_CELEB_SCHEDULES = BASE_URL + "slotMaster/getSlotsByServiceMemberId";

    public static final String SHARE_AUDITION = BASE_URL + "shareRouter/shareAudition/";

    public static final String getMemberPreferancesCount = BASE_URL + "memberpreferences/getMemberPreferancesCount/";

    //New Signup
    public static final String MEMBER_REGISTRATION_NEW_SINGUP = BASE_URL + "users/memberRegistrationAndProfileUpdate/";

    public static final String FORGOT_PASSWORD_GET_OTP = BASE_URL + "logininfo/forgotPassword";

    public static final String CHANGE_PASSWORD = BASE_URL + "logininfo/resetPassword/";

    public static final String IS_PASSWORD_VERIFIED = BASE_URL + "users/isPasswordverified/";

    public static final String GET_ALL_LANGUAGES = BASE_URL + "languages/getAll";

    //New Screens API
    public static final String GET_ALL_CELEBS = BASE_URL + "users/getAllCelebrityListForMember/";

    public static final String GET_CONTRACTS_BY_MEMBER_ID = BASE_URL + "celebritycontract/getContractsByMemberID/";

    //Create Store
    public static final String CREATE_STORE = BASE_URL + "story/createStory";

    public static final String STORY_IMAGES = BASE_URL + "story/storyImages";

    public static final String MANAGER_ADITIONAL_PROFILE_UPDATE = BASE_URL + "users/editUser/";


    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(new OkHttpClient.Builder()
                            .connectTimeout(30L, TimeUnit.SECONDS)
                            .readTimeout(30L, TimeUnit.SECONDS)
                            .retryOnConnectionFailure(true)
                            .addInterceptor(new Interceptor() {
                                @Override
                                public Response intercept(Chain chain) throws IOException {
                                    Request request = chain.request().newBuilder()
                                            .addHeader("x-access-token", SessionManager.getInstance().getKeyValue(SessionManager.KEY_API_TOKEN, ""))
                                            .addHeader("Connection", "close")
                                            .build();
                                    return chain.proceed(request);
                                }
                            })
                            .build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static ApiInterface getApiInterface() {
        return getClient().create(ApiInterface.class);
    }

    public static Retrofit getClientJSON() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(new OkHttpClient.Builder()
                            .connectTimeout(30L, TimeUnit.SECONDS)
                            .readTimeout(30L, TimeUnit.SECONDS)
                            .retryOnConnectionFailure(true)
                            .addInterceptor(new Interceptor() {
                                @Override
                                public Response intercept(Chain chain) throws IOException {
                                    Request request = chain.request().newBuilder()
                                            .addHeader("x-access-token", SessionManager.getInstance().getKeyValue(SessionManager.KEY_API_TOKEN, ""))
                                            .addHeader("Connection", "close")
                                            .addHeader("X-Api-Key", ApiClient.XApiKey)
                                            .addHeader("X-Auth-Token", ApiClient.XAuthToken)
                                            .addHeader("Content-Type", ApiClient.ContentType)
                                            .build();
                                    return chain.proceed(request);
                                }
                            })
                            .build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
