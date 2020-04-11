package info.sumantv.flow.celebflow.constants;

import java.util.Arrays;
import java.util.List;


import info.sumantv.flow.R;
import info.sumantv.flow.retrofitcall.ApiClient;

/**
 * Created by Chenna on 07-07-2018.
 */

public class Constants {


    public static final String FULL_ACCESS = "Full";
    public static final String VIEW_ACCESS = "View";
    public static final String EDIT_ACCESS = "Edit";
    public static final String OFF_ACCESS = "Off";

    public static final String CONNECT_TO_WIFI = "WIFI";
    public static final String CONNECT_TO_MOBILE = "MOBILE";
    public static final String NOT_CONNECT = "NOT_CONNECT";
    public final static String CONNECTIVITY_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

    public static final String AUDITION_PROFILE_ID = "AUDITION_ID";


    public static final String UH_OH = "Uh-oh!";

    public static final String SEARCH_CELEB = "Search a celebrity";


    public static final String SORRY = "Sorry";

    public static final String SEEMS_LIKE_NO_INTRNET = "Seems like your are not connected \n to the internet";

    public static final String PLEASE_CHECK_INTERNET = "No active internet connection!!";

    public static final String DRAG_TO_RETRY = "drag to retry...!!";


    public static final String SERVER_NOT_CONNECTED = "Server not connected, please try again";

    public static final String NO_INTERNET = "No Internet";

    public static final String NO_DATA_AVAILABLE = "No Data Available";

    public static final String NO_FEEDS = "No Feeds";

    public static final String NO_COMMENTS = "Nothing new!";

    public static final String YOU_NO_COMMENTS = "You have no comments";

    public static final String NO_LIKES = "No Likes";

    public static final String CONNECTION_ERROR = "No active internet connection!!";

    public static final String ONLINE = "Online";

    public static final String ENTER_SOMETHING = "Please enter something";

    public static final String SOMETHING_WENT_WRONG = "Something went wrong";

    public static final String SOMETHING_WENT_WRONG_SERVER = "Something went wrong in server";

    public static final String SEARCH_YOUR_CELEB = "Search a Celebrity";

    public static final String MEMBER_NOT_FOUND = "Member not found";

    public static final String USER_ADDRESS = "user_address";

    public static final String NO_REQ = "You have no requests \naccepted";

    public static final String NO_REQUESTS = "You don't have any \n requests";

    public static final String USER_COMPLETE_ADDRESS = "user_complete_address";

    public static final String USER_LOCALITY_ADDRESS = "user_locality_address";

    public static final String USER_ADMIN_AREA = "user_admin_area";

    public static final String USER_COUNTRY = "user_country";

    public static final String USER_PIN_CODE = "user_pin_code";

    public static final String USER_ADDRESS1 = "user_address1";

    public static final String USER_ADDRESS2 = "user_address2";

    public static final int FAILURE_RESULT = 0;

    public static final int SUCCESS_RESULT = 1;

    public static final int SECURE_LOGOUT_RESULT = 2;

    public static final int TOKEN_EXPIRED = 4;

    public static final int TOKEN_AUTHENTICATION_FAIL = 5;

    public static final int FORGOT_EMAIL = 6;

    public static final int INSUFFICIENT_CREDITS = 11;

    public static final String PACKAGE_NAME = "info.celebkonnect.flow.celebflow";

    public static final String RECEIVER = PACKAGE_NAME + ".AddressReceiver";

    public static final String RESULT_DATA_KEY = PACKAGE_NAME + ".RESULT_DATA_KEY";

    public static final String LOCATION_DATA_EXTRA = PACKAGE_NAME + ".LOCATION_DATA_EXTRA";

    public static final int LOCATION_CODE = 10101;

    public static final int PERMISSION_REQUEST_LOCATION_CODE = 18103;

    public static final int LOADER_MORE_VIEW_TYPE = 14039;

    public static final int RECYCLER_MOVE_TO_TOP = 10;

    public static final int RECYCLER_MOVE_TO_TOP_IN_PHOTOS = 20;

    public static final int FEEDS_LOAD_MORE_COUNT = 10;

    public static final int LIKES_LOAD_MORE_COUNT = 50;

    public static final String FRAGMENT_TYPE = "FRAGMENT_TYPE";
    public static final String AUDITION_SEARCH = "AUDITION_SEARCH";
    public static final String TALENT_SEARCH = "TALENT_SEARCH";

    public static final int REQUEST_LOCATION_CHECK_SETTINGS = 100000044  ;

    public static final String normalCall = "normalCall";

    public static final String audioVideoCall = "audioVideoCall";

    public static final String postupdated = "Post updated successfully";

    //Auditions

    public static final String NO_NOTIFICATIONS = "No Recommendations for now.";

    public static final String NO_AUDITION_ARCHIEVES = "No Archives";

    public static final String NO_AUDITIONS = "No Auditions applied";

    public static final String NO_DRAFTS = "No Drafts";

    public static final String NO_POSTS = "No Auditions Posted";


    public static final String NO_AUDITION_SEARCH = "No results for your search criteria";

    public static final String NO_TALENT_SEARCH = "There are no talents";

    public static final String N0_MY_ORDER = "No orders";

    public static final String NO_AUDITION_FAVOURITE = "No Favourite Auditions";

    public static final String NO_TALENT_FAVOURITE = "No Favourite Talents";

    public static final String NO_MEDIA_DATA = "No media uploaded";


    public static final String CELEB_OFFLINE = "is Currently Offline";

    public static final String ARE_YOU_SURE = "Are you sure want to disconnect the call";


    public static final String OFFLINE_STAUS = "offline";

    public static final String ONLINE_STAUS = "online";


    public static final String GET_TYPE = "Get typing";

    public static final String START_TYPE = "Start a chat with your \nfavourite celebrity";

    public static final String NO_CALLS = "No Calls";


    public static final String ONLINE_CELEBS = "GET_ONLINE_CELEBS";

    public static final String LIST_DATA = "LIST_DATA";

    //"Are you sure disconnect "


    //Media Upload
    public static final int VIDEO_SIZE_LIMIT = 50 * 1024;
    public static final long SIZE_LIMIT = 50 * 1024;


    public static final int MULTIPLE_IMAGES = 39016;
    public static final int MULTIPLE_VIDEOS = 39017;

    public static final int MULTIPLE_IMAGES_VIDEOS = 39018;

    public static final int EDIT_QUICK_POST = 98001;
    public static final int EDIT_MEDIA_POST = 98002;

    public static final int CHECK_FOR_ONLINE = 120 * 1000;

    public static final int CHECK_PER_IN_SEC = 5000;

    public static final int CHECK_FOR_DEVICE = 12000;

    public static final String PLATFORM = "android";

    public static final int CHECK_PER_SEC = 1000;


    public static final String TITLE = "Has become your";
    public static final String BODY = "sample title";

//    public static final boolean SELFPROFILE = is;


    // recyclerview -> start with 99000

    public static final int FOOTER_LOADER = 99001;
    public static final int FOOTER_ERROR_RETRY = 99002;

    //FeedDetails
    public static final int FEED = 8801;
    public static final int INVIDULA_FEED = 8802;

    public static final String FORCED_LOGOUT = "Greetings from CelebKonect. We notice " +
            "you are logged in with another device. " +
            "Please confirm to make this device " +
            "active and log out your other device.";

    public static final String FRAGMENT_KEY = "fragment_key";
    public static final String FRAGMENT_TITLE = "title";

    public static final String ON_TASK_REMOVED = "ON_TASK_REMOVED";
    public static final String NOTIFICATION_SERVICE_TYPE = "serviceType";
    public static final String NOTIFICATION_OBJECT = "notificationObject";
    public static final String NOTIFICATION_APP_UPDATE = "AppUpdate";

    public static final String NOTIFICATION_TYPE_FAN = "Fan";
    public static final String NOTIFICATION_TYPE_FOLLOW = "Follow";
    public static final String NOTIFICATION_TYPE_FEED = "Feed";
    public static final String NOTIFICATION_TYPE_SERVICE = "Service";
    public static final String NOTIFICATION_TYPE_CREDIT = "Credit";
    public static final String NOTIFICATION_TYPE_MANAGER = "Manager";
    public static final String NOTIFICATION_TYPE_CHAT = "Chat";

    public static final String NOTIFICATION_ACTIVITY_FAN = "FAN";
    public static final String NOTIFICATION_ACTIVITY_FOLLOW = "FOLLOW";
    public static final String NOTIFICATION_ACTIVITY_FEED = "FEED";
    public static final String NOTIFICATION_ACTIVITY_REQUEST_FROM_ADMIN = "REQUESTFROMADMIN";
    public static final String NOTIFICATION_ACTIVITY_SUSPEND_MANAGER = "SUSPENDMANAGER";
    public static final String NOTIFICATION_ACTIVITY_CELEB_REJECT = "CELEBREJECT";
    public static final String NOTIFICATION_ACTIVITY_REQUEST_TO_MANAGER = "REQUESTTOMANAGER";
    public static final String NOTIFICATION_ACTIVITY_SUSPEND_SUB_MANAGER = "SUSPENDSUBMANAGER";
    public static final String NOTIFICATION_ACTIVITY_MANAGER_ACCEPTED = "MANAGERACCEPTED";
    public static final String NOTIFICATION_ACTIVITY_MANAGER_REQUESTED = "MANAGERREQUESTED";
    public static final String NOTIFICATION_ACTIVITY_MANAGER_REJECT = "MANAGERREJECT";
    public static final String NOTIFICATION_ACTIVITY_CELEB_ACCEPTED = "CELEBACCEPTED";
    public static final String NOTIFICATION_ACTIVITY_MANAGER_REQ_CANCEL = "MANAGERREQCANCEL";
    public static final String NOTIFICATION_ACTIVITY_EARNED_CREDIT = "EARNEDCREDIT";
    public static final String NOTIFICATION_ACTIVITY_SPENT_CREDIT = "SPENTCREDIT";
    public static final String NOTIFICATION_ACTIVITY_SCHEDULE = "SCHEDULE";
    public static final String NOTIFICATION_ACTIVITY_CALL_REMINDER = "CALLREMINDER";
    public static final String NOTIFICATION_ACTIVITY_CANCEL_CALL_CELEBRITY = "CANCELCALLCELEBRITY";
    public static final String NOTIFICATION_ACTIVITY_REJECT_CALL_CELEBRITY = "REJECTCALLCELEBRITY";
    public static final String NOTIFICATION_ACTIVITY_CALL_MISSED_CALL = "MISSEDCALL";
    public static final String NOTIFICATION_ACTIVITY_PURCHASED_CREDITS = "PURCHASEDCREDITS";

    public static final String SCHEDULE_CALL = "scheduleCall";

    public static final String CHAT = "chat";
    public static final String AUDIO_CALL = "audio";
    public static final String VIDEO_CALL = "video";

    public static final String MEMBER_CALLING = "membercalling";


    public static final String MEMBER_ID = "memberId";  //receiverid


    public static final String DEVICE_ID = "deviceId"; //senderId senderid

    //CelebProfile Details

    public static final String SENDER_ID = "senderId"; //senderId senderid

    public static final String SLOT_DURATION = "slotDuration"; //senderId senderid



    public static final String RECEIVER_ID = "receiverId";  //receiverid

    public static final String IS_PHONECALLID = "isPhoneCallId";  //receiverid


    public static final String TOKEN = "token"; //senderId senderid

    public static final String LIVE_STATUS = "liveStatus";  //receiverid

    public static final String CELEB_KONECT_CAMERA_FOLDER = "Celebkonect/CelebKonect camera";

    public static final String CELEB_KONECT_IMAGES_FOLDER = "Celebkonect/CelebKonect images";

    public static final String CELEB_KONECT_VIDEOS_FOLDER = "Celebkonect/CelebKonect videos";

    //
    //String receiverid = responsedata.getString(Constants.RECEIVER_ID);


    public static final String SENDER_NAME = "sendername";

    public static final String RECEIVER_NAME = "receivername";

    public static final String SENDER_IMAGE = "senderimage";

    public static final String RECEIVER_IMAGE = "receiverimage"; //receiverimage

    public static final String CHAT_ROOM_ID = "chatRoomID";

    public static final String OS = "os";

    public static final String ANDROID = "android";


    public static final String SERVICE_TYPE = "serviceType";

    public static final String SCREEN_LOCK_STATUS = "screenLockStatus";

    public static final String SERVICE_CREDITS = "ServiceCredits";

    public static final String CALL_DURATION = "2";

    public static final String TOTAL_CREDITS = "totalCredits";

    public static final String SERVICE_TRANSACTION_ID = "sTransactionId";

    public static final String INFO_FOR_SERVICE = "CelebinfoService";

    public static final String SENDER_STATUS = "senderStatus"; //senderId senderid

    public static final String CELEB = "celeb"; //senderId senderid

    public static final String MEMBER = "member"; //senderId senderid

    public static final String inNormalCall = "inNormalCall";


    public static final String other_reason = "Other"; //senderId senderid


    public static final String CELEB_ID = "celebId"; //senderId senderid

    public static final String CALL_INFO = "callinfo";

    public static final String IS_SCHEDULE_CALL = "false";


    //Member Side Status Updates

    //Emit

    public static final String SENDER_REJECTED = "memberRejected";


    public static final String UPDATE_CALL_START_TIME = "updateCallStartTime";

    public static final String IS_ANOTHER_CALL = "isAnotherCall";

    public static final String IS_CALL_DISCONNECTED = "isCallDisconnected";


    public static final String isBackgroundCall = "isBackgroundCall";

    public static final String isBackgroundCallDisconnected = "isBackgroundCallDisconnected";


    public static final String NOTIFICATION_COUNT_EMIT = "notificationCount";


    //Online Celeb Emit
    public static final String ONLINE_CELEB_EMIT = "emitToGetOnlineCelebrities";


    //Celeb Side Status Updates

    //Emit
    public static final String RECEIVER_REJECTED = "celebRejected";

    public static final String BAD_NETWORK = "Bad network";


    public static final String RECEIVER_DISCONNECTED = "celebdisconnected";


    //on
//    public static final String RECEIVER_REJECTION = "celebRejection"; //memberRejection

//    public static final String CALL_DISCONNECTED = "callDisconnected";
    //

//    public static final String CELEB_LOG_STATUS = "celebLogStatus";


    public static final String CELEB_ONLINE_STATUS = "celebOnlineStatus";

    //celebLogStatus


    //celebNotResponded


    //SERVICE JOIN
    public static final String ROOM_ID = "roomID";


    public static final String CELEB_LIFTED = "celebLifted";


    // Access Permisson Swicth Account

   /* public static final String PROFILE_UPDATES = "Profile Updates";

    public static final String FEED_UPDATES = "Feed Updates";

    public static final String CREDIT_UPDATES = "Credits Updates";

    public static final String SERVICE_UPDATES = "Service Updates";

    public static final String FAN_FOLLOW_UPDATES = "Fan Follow Updates";

    public static final String PREFERENCE_UPDATES = "Preferences Updates";

    public static final String SCHEDULE_UPDATES = "Schedule Updates";

    public static final String CART_UPDATES = "Cart Updates";

    public static final String ORDER_UPDATES = "Order Updates";

    public static final String APP_PROMOTION_UPDATES = "App Promotions Updates";

    public static final String AUDITION_UPDATES = "Auditions Updates";

    public static final String REPORTS_UPDATES = "Reports Updates";

    public static final String Notification_UPDATES = "Notification Updates";*/

    public static final String PROFILE_UPDATES = "Profile";

    public static final String FEED_UPDATES = "Feed";

    public static final String CREDIT_UPDATES = "Credits";

    public static final String SERVICE_UPDATES = "Service";

    public static final String FAN_FOLLOW_UPDATES = "Fan / Follow";

    public static final String PREFERENCE_UPDATES = "Preferences";

    public static final String SCHEDULE_UPDATES = "Schedule";

    public static final String CART_UPDATES = "Cart";

    public static final String ORDER_UPDATES = "Order";

    public static final String APP_PROMOTION_UPDATES = "App Promotions";

    public static final String AUDITION_UPDATES = "Auditions";

    public static final String REPORTS_UPDATES = "Reports";

    public static final String Notification_UPDATES = "Notification";

    public static final String VERIFY = "Verify";

    public static final String VERIFIED = "Verified";


    //Call Intiate

    public static final String CREATE_CALL_TRANSACTION = "createCallTransaction";

    public static final String INTIATE_TRANSACTION = "initiatedTransaction";


    public static final String IS_ONLINE_STATUS_LIS = "onlineStatus";

    public static final String IS_ONLINE_EMIT = "isOnlineStatus";


    //Services


    //Receiver Status Update
    public static final String CELEB_LIFTED_KEY = "celebLifted";

    public static final String CELEB_ON_CALL = "onCall";


    public static final String VERSION_CODE = "Alpha(0.0.1)";

    public static final String VERSION_DETAILS = "Version: " + VERSION_CODE;

    public static final String POWERED_BY_INDOZ = "Powered by: INDOZ";

    public static final String APP_NAME = "CELEBKONECT";

    public static final String PACKAGE = "info.celebkonnect.flow.celebflow";

    public static final String PREFS = PACKAGE;

    public static final String FILE_PROVIDER_AUTHORITY = PACKAGE + ".provider";

    public static final String PLEASE_FILL_THE_FIELDS = "Please fill the fields";

    public static final String PLEASE_ADD_MEDIA = "Please add media";

    public static final String ERROR = "Error";

    public static final String REJECTED = "Rejected";

    public static final String ACCEPTED = "Accepted";

    public static final String MEDIA_DATA = "MEDIA DATA";

    public static final String BUSY_ON_CALL = "Busy on other call";

    public static final String INVALID_OPERATION = "Invalid Operation";

    public static final int SECOND_MILLIS = 1000;

    public static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;

    public static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;

    public static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    public static final String COLOR_CODES[] = {"#BA4141", "#E07C5C", "#00B3FF", "#0069C9"};

    public static final String TAG_COLOR_CODES[] = {"#000000", "#8B4513", "#990000", "#0069C9"};

    public static final String COLOR_START_CODES[] = {"#6ACAD3", "#816CD4", "#BEE05C", "#f07C5C", "#E8DF16"};

    public static final String COLOR_END_CODES[] = {"#4791BE", "#BA4198", "#41BA63", "#BA4141", "#F9D006"};

    public static final String PARKING_COLOR_START_CODES[] = {"#00B2FF", "#816CD4", "#BEE05C", "#f07C5C", "#E8DF16"};

    public static final String PARKING_COLOR_END_CODES[] = {"#0069C9", "#BA4198", "#41BA63", "#BA4141", "#F9D006"};

    public static final List<String> FB_PERMISSIONS = Arrays.asList("public_profile", "email");

    public static final String MOBILE_OTP_MESSAGE = "Please select the number we can send your verification code to";

    public static final String MAIL_OTP_MESSAGE = "Please select the mail we can send your verification code to";


    //Audition Shiva

    public static final String AUDITION_PROFILE_STATUS = "auditionprofilestatus";

    public static final String NO_FAVOURITE_TALENT = "Seems like you have no \nfavourite talent"; //senderId senderid

    public static final String NO_FAVOURITE_CASTING = "Seems like you have no \nfavourite casting"; //senderId senderid


    //party color

    public static final String PARTY_COLOR_PRIMARY = "party_color_primary";

    public static final String PARTY_COLOR_PRIMARY_DARK = "party_color_primary_dark";

    public static final String PARTY_ACCENT_COLOR = "party_accent_color";

    public static final String PARTY_TOOLBAR_TEXT_COLOR = "party_toolbar_text_color";

    public static final String PARTY_TOOLBAR_MENU_COLOR = "party_toolbar_menu_color";

    public static final String PARTY_TEXT_COLOR = "party_text_color";


    //user details

    public static final String USER_ID = "user_id";

    public static final String ACCESS_TOKEN = "access_token";

    public static final String EXPIRES_IN = "expires_in";

    public static final String TOKEN_TYPE = "token_type";

    public static final String LAST_NAME = "last_name";

    public static final String FIRST_NAME = "first_name";

    public static final String USER_EMAIL = "user_email";

    public static final String USER_NAME = "user_name";

    public static final String USER_PHONE_NUMBER = "user_phone_number";

    public static final String USER_PHOTO_URL = "user_photo_url";

    public static final String USER_FCM_TOKEN = "user_fcm_token";

    public static final String AVATAR_IMAGE = "https://ssl.gstatic.com/images/branding/product/1x/avatar_circle_blue_512dp.png";

    public static final String GIVE_ALL_PERMISSIONS = "Give all the permissions in order to access the application";

    public static final int MEDIA_PICK_LIMIT = 20;

    public static final int MEDIA_PICK_LIMIT_CREATE = 1;

    public static final long MEDIA_SIZE_LIMIT_IN_BYES = 50 * 1024 * 1024;

    public static final int AUDITION_MEDIA_PICK_LIMIT = 20;

    public static final int RECYCLER_MOVE_TO_TOP_LIMIT = 20;

    public static final long AUDITION_MEDIA_SIZE_LIMIT_IN_BYES = 30 * 1024 * 1024;

    public static final String SAMPLE_FILE_SIZE = "51200";

    public static final String MEDIA_TYPE_IMAGE = "image";

    public static final String MEDIA_TYPE_GIF = "gif";

    public static final String MEDIA_TYPE_VIDEO = "video";

    public static final String MEDIA_TYPE_AUDIO = "audio";

    public static final String MEDIA_TYPE_DOC = "doc";

    public static final String MEDIA_IMAGE_MIME_TYPE = "image/jpeg";


    // permissions -> start with 24001

    public static final int LOCATION_REQUEST = 24001;

    public static final int WRITE_SMS_REQUEST = 24002;

    public static final int READ_SMS_REQUEST = 24003;

    public static final int READ_STORAGE_REQUEST = 24004;

    public static final int WRITE_STORAGE_REQUEST = 24005;

    public static final String CALL_STATUS = "CALL_STATUS";

    public static final String CALL_STATUS_NEW = "CALL_STATUS_NEW";


    // social sign in -> starts with 17000

    public static final int GOOGLE_SIGN_IN = 17001;

    public static final int FACEBOOK_SIGN_IN = 17002;

    public static final int TWITTER_SIGN_IN = 17003;


    // recyclerview -> start with 99000

    public static final int FOOTER_BOTTOM = 99003;

    public static final int HEADER_VIEW = 99004;

    // fragment key -> start with 9000

    public static final List<String> LEADER_OVERVIEW_TITLES = Arrays.asList("Profile", "Recent");

    // adapter key -> 14000;
    public static final int LEADER_OVERVIEW = 14001;


    public static final String[] YEAR_MONTH = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "July",
            "Aug", "Sept", "Oct", "Nov", "Dec"};


    public static final String URL_TERMS_AND_CONDITIONS = "https://www.google.com/";

    public static final int RETROFIT_CONNECTION_TIME_OUT = 10;

    public static final double REVERSE_RATIO_HIGH_LIMIT = 0.9;

    public static final double REVERSE_RATIO_LOW_LIMIT = 1.1;

    public static final double RATIO_HIGH_LIMIT = 1.1;

    public static final double RATIO_LOW_LIMIT = 0.9;


    // sample ids


    public static final String ACTION_TYPE_LIKE = "views";

    public static final String ACTION_TYPE_COMMENT = "comment";

//    public static final String SAMPLE_MEMBER_ID = "5a9ccfe641249d75a4d62828";

//    public static final String SAMPLE_MEMBER_ID_CB = "5a9ccfe641249d75a4d62828";

    public static final String SAMPLE_LOCATION = "Hyderabad";

    public static final String SAMPLE_USER_PROFILE_PIC = "https://lh5.googleusercontent.com/-nqHdVgQyBl4/AAAAAAAAAAI/AAAAAAAAAZQ/Zh8U9kHXWwM/il/photo.jpg";


    public static final String CALL_INITIATE = "callintiate";

    public static final String CHECK_BALANCE = "checkbal";


    public static final String CHECK_COMPLETE_DATA = "checkcmptdata";

    //Audio Video Call
    public static final String BLOCK_STATUS = "BlockStatus";

    public static final String BLOCK_STATUS_CELEB_FAN = "BlockStatusFan";

    public static final String BLOCK_STATUS_CELEB_UNFAN = "BlockStatusUnFan";

    public static final String BLOCK_STATUS_CELEB_FOLLOW = "BlockStatusFollow";

    public static final String BLOCK_STATUS_CELEB_UNFOLLOW = "BlockStatusUnFollow";


    public static final String BLOCK_STATUS_CALL = "BlockStatusforCall";


    public static final String BECOME_FAN = "BecomeFan";


    public static final String CONTRACTS_FOR_CELEB = "contracts";


    public static final String CALL_DURATION_STATIC = "50";

    // urls
    public static final String BASE_URL = ApiClient.BASE_URL;

    public static final String MEDIA_BASE_URL = ApiClient.BASE_URL;


    public static final String NO_FOLLOWERS = "No followers here yet!!!";

    public static final String NO_BLOCKS = "No blocks here yet!!!";

    public static final String NO_FANS = "No fans here yet!!!";

    public static final String YOU_NOT_FAN = "Get your hands on exclusive insights!";

    public static final String FAN_YOUR_CELEB = "Fan your favourite celebrity";

    public static final String YOU_NOT_FOLLOW = "Start browsing ";

    public static final String YOU_NOT_FOLLOW_CELEB = "Follow your favourite celebrity ";


    public static final String FAN_STATUS = "fan";
    public static final String UN_FAN_STATUS = "Fanned";


    //Notifications
    public static final String NO_NOTIFICATIONS_AVAILALE = "No new notifications";

    public static final String NO_CELEBS = "Currently no celebrities \navailable ";

    public static final String NO_PACKAGES = "No packages available";

    public static final String NO_VIEWS = "No Views";


    public static final String NO_NOTIFICATIONS_AVAILALE_DES = "Check this section for exciting \nupdates and activities";

    public static final String NO_ARCHIVE_NOTIFICATIONS_AVAILALE = "Nothing here!";

    public static final String NO_ARCHIVE_NOTIFICATIONS_DATA = "You have no archived \n notifications ";

    public static final String NO_RECOMMENDATIONS = "No recommendations";

    public static final String NO_SCHEDULES = "There are no schedules \ncreated";


    public static final String NO_CONETENTS = "You have not posted any \nstories";


    //Audition New Titles
    public static final String NOTHING_HERE = "Nothing here";

    public static final String NO_DRAFTS_AUDITION = "You haven't saved any \ndrafts";


    public static final String OOPS = "Oops";

    public static final String THERE_NO_CART = "There seems to be nothing \nin your cart";

    public static final String WHOOPS = "Whoops!";

    public static final String AUDIO_FILES = "Audio files";

    public static final String THERE_NO_ORDERS = "You don't have any \norders yet";

    public static final String UPCOMING_REPORTS = "Up Coming reports";

    public static final String NO_IMAGES = "You have no images\n saved";

    public static final String NO_VIDEOS = "You have no videos\n saved";

    public static final String NO_AUDIOS = "You have no audio files saved";

    public static final String NO_VIDEOS_AUDITION = "No videos saved";

    public static final String NO_AUDIOS_AUDITION = "No audios saved";

    public static final String NO_IMAGES_AUDITION = "No images saved";

    public static final String NO_DOCS_AUDITION = "No documents saved";

    public static final String NO_DOCS = "You have no documents saved";

    public static final String NO_BRANDS = "You have no videos\n saved";

    public static final String COMING_UP = "Coming up";

    public static final String SEARCH = "Search";

    public static final String YOU_ARE_OFFLINE = "Turn ON live status to stay konected";


    public static final String THERE_IS_NO_DATA = "There are no results \n to show";

    public static final String NO_SCHEDULES_AVAILABLE = "There are no upcoming \nschedules";

    //@BindView(R.id.tvNote)
    //    TextView tvNote;

    public static class NotificationConstants {
        public static final String ALL = "General";
        public static final String FAN_FOLLOW = "FanFollow";
        public static final String SERVICE = "Call";
        public static final String MANAGER = "Manager";
    }

    public static class FeedConstants {

        public static final String URL_CREATE_FEED = BASE_URL + "feeddata/createFeed_PK";

        public static final String URL_GET_FEED_NEW = BASE_URL + "feed/getFeedsNew/";

        public static final String URL_DELETE_FEED = BASE_URL + "feeddata/deleteFeed/";

        public static final String URL_EDIT_FEED = BASE_URL + "feeddata/editFeedById_PK/";

        public static final String URL_ADD_LIKE = BASE_URL + "mediaTracking/setLike";

        public static final String URL_ADD_COMMENT = BASE_URL + "mediaTracking/setComments";

        public static final String URL_GET_FEED_LIKES = BASE_URL + "feeddata/getFeedLikesById_PK/";

        public static final String URL_GET_FEED_MEDIA_LIKES = BASE_URL + "feed/getMediaLikedProfilesById/";

        public static final String URL_GET_FEED_COMMENTS = BASE_URL + "feeddata/getFeedCommentsById_PK/";

        //Update Coment
        public static final String UPDATE_COMMENT = BASE_URL + "mediaTracking/updateCommentsById/";

        //Delete Coment
        public static final String DELETE_COMMENT = BASE_URL + "mediaTracking/deleteCommentsById/";

        public static final String URL_GET_FEED_MEDIA_COMMENTS = BASE_URL + "feeddata/getMediaCommentsById_PK/";

        public static final String getAllFeedbackItems = BASE_URL + "commentFeedbackRouter/getAllFeedbackItems/";

        public static final String postFeedbackOnComment = BASE_URL + "feedCommentFeedback/postFeedbackOnComment/";

        public static final String getFeedById = BASE_URL + "feed/getFeedById/";


        public static final String GET_REPORT_POST_OPTIONS = BASE_URL + "reportFeedbackItems/getAllFeedbackItems/";

        public static final String POST_REPORT_FEEDBACK_ON_POST_OPTIONS = BASE_URL + "report/createReport/";

        public static final String HIDE_FEED = BASE_URL + "feed/hideAndUnhideFeed/"; //type delete

        public static final String NOTIFICSTION_ON_OFF = BASE_URL + "feedsettings/setFeedSettings";

        public static final int suggestionsOnWelcomeFeed = 0;
        public static final int suggestionsInFeed = 1;
        public static final int AdvertisementFeed = 2;
        public static final int allCaughtUpFeed = 3;

    }

    public static class AuditionConstants {

        public static final String POST_AUDITION = BASE_URL + "auditionRouter/create/"; //post Audition

        public static final String UPDATE_AUDITION_DRAFT = BASE_URL + "auditionRouter/update/";

        public static final String GET_PRODUCTION_PARENT = BASE_URL + "productionType/getParents/"; //getParentProductions

        public static final String GET_PRODUCTION_CHILD = BASE_URL + "productionType/findProductionTypeId/"; //getChildProductins

        public static final String GET_ROLE_TYPES = BASE_URL + "RoleType/getAll/"; //getRoleTypes

        public static final String GET_AWARD_TYPES = BASE_URL + "awardTpeRouter/getAllAwardType"; //getAwards

        public static final String GET_AGE_RANGE = BASE_URL + "agerange/getAgeRangeForAuditionProfile"; //getAwards

        public static final String GET_ALL_DEGREE_TYPES = BASE_URL + "degreeTypeRouter/getAllDegreeType"; //getDegreeTypes

        public static final String GET_FILTER_BY_ROLETYPE = "RoleType/getFiltersByRoleType/";

        public static final String GET_ALL_TALENT_DETAILS = "tilent/getAll";

        public static final String GET_ALL_TALENT_FEVERITE = "favoritesRouter/getAllFavouriteTalent/";

        public static final String GET_ALL_AUDITION_FEVOURITE = "favoritesRouter/getAllFavouritedAuditions/";


        public static final String GET_ALL_FEVERITE_TALENT = BASE_URL + "favoritesRouter/getAllFavouriteTalent/";

        public static final String GET_SUB_TALENT_DETAILS = "tilent/getById/";

        public static final String GET_NOTIFICATION_DETAILS = "auditionRouter/getAuditionAndRolesById/";

        public static final String GET_ETHNICITY = BASE_URL + "ethnicity/getAll/"; //getEthnicity

        public static final String GET_MEDIA_REQUIRED = BASE_URL + "mediaRequired/getAll/"; //getMEdiaRequired

        public static final String GET_BODY_TYPE = BASE_URL + "bodyType/getAll/"; //getBodyType

        public static final String GET_EY_COLOR = BASE_URL + "eyeColour/getAll/"; //getEyeColor

        public static final String AUDITION_SEARCH_BY_NAME = BASE_URL + "auditionRouter/getRoleByString/"; //getEyeColor

        public static final String SEARCH_BY_ROLE = BASE_URL + "auditionRouter/getRoleByString/"; //Search By Role

        public static final String SEARCH_BY_FILTER = "auditionRouter/getSearchByString/";

        public static final String GET_AUDITION_DRAFTS = BASE_URL + "auditionRouter/getDrafts/";

        public static final String PUBLISH_DRAFTS = BASE_URL + "auditionRouter/update/{id}";

        public static final String GET_SKIN_TONE = BASE_URL + "skinTone/getAll/"; //getSkinTone

        public static final String GET_HAIR_COLOR = BASE_URL + "hairColor/getAll/"; //getHairColor

        public static final String GET_LANGUAGES = BASE_URL + "languages/getAll/"; //getLanguages

        public static final String APPLY_AUDITION_ROLE = BASE_URL + "applyAuditionsRouter/create"; //Apply Audition

        public static final String MY_AUDITION_POSTS = BASE_URL + "auditionRouter/getAuditionByMemberId/";

        public static final String AUDITION_APPLIED_MEMBERS_LIST = BASE_URL + "applyAuditionsRouter/getAllMembersForAuditionById/";

        public static final String GET_AUDITION_PROFILE_BY_ID = BASE_URL + "auditionsProfiles/getAuditionsProfilesByMemberId/";

        public static final String GET_MY_AUDITIONS = BASE_URL + "applyAuditionsRouter/getAllApplyAuditionByMemberId/";

        public static final String GET_AUDITION_ROLE_FAVORITE = BASE_URL + "favoritesRouter/checkFavorite/";

        public static final String AUDITION_ROLE_FAVORITE = BASE_URL + "favoritesRouter/create/";

        public static final String UPDATE_AUDITION_ROLE_FAVORITE = BASE_URL + "favoritesRouter/update/";

        public static final String GET_AUDITION_CREATE_PROFILE = BASE_URL + "auditionsProfiles/create/"; //createAuditionProfile

        public static final String GET_AUDITION_UPDATE_PROFILE = BASE_URL + "auditionsProfiles/update/"; //updateAuditionProfile

        public static final String GET_MY_INTERSTS = BASE_URL + "interests/getInterests/"; //myIntrests

        public static final String INSERT_INTRESTS = BASE_URL + "myInterests/createMyInterests/"; //updateInrests

        public static final String createTalentFavorite = BASE_URL + "favoritesRouter/createTalentFavorite/"; //updateInrests

        public static final String CreateAuditionFavorite = "favoritesRouter/create";

        public static final String GET_ALL_ARCHIEVED_NOTIFICATION = BASE_URL + "notification/getNotificationsByMemberIdIsArchievedGeneral/";

        public static final String AUDITION_NOTIFICATIONS = BASE_URL + "notification/getNotificationsByMemberIdAudition/";
        //auditionNotifications

        public static final String AUDITION_ARCHIVE_NOTIFICATIONS = BASE_URL + "notification/getNotificationsByMemberIdIsArchieved/"; //auditionArchiveNotifications

        public static final String TALENT_SEARCH_BY_FILTERS = BASE_URL + "auditionsProfiles/getSearchByString/";//Talent Search filters
    }


    public static class ProfilesConstants {

        public static final String LIMIT_COUNT = "10";

        public static final String FAN_TAB = "Fanned";

        public static final String FOLLOWING_TAB = "Following";

        public static final String FANS_OF_TAB = "Fans";

        public static final String FOLLOWERS_TAB = "Followers";

        public static final String BLOCKS_TAB = "Block";

        public static final String FAN_LIST = BASE_URL + "memberpreferences/fanCelebritiesbyMember/";

        public static final String FOLLOWING_LIST = BASE_URL + "memberpreferences/followingCelebritiesByMember/";

        public static final String FANSOF_LIST = BASE_URL + "memberpreferences/fanMembersbyCelebrity/";

        public static final String FOLLOWERS_LIST = BASE_URL + "memberpreferences/followingMembersbyCelebrity/";

        public static final String BLOCKS_LIST = BASE_URL + "memberpreferences/getBlockUserList/";

        public static final String UNBLOCK = BASE_URL + "memberpreferences/unblockMember";



    }


    public static class BannerConstants {
        public static final String URL_CHECK_BANNER_STATUS = BASE_URL + "banner/getCurrentContest/";

        public static final String URL_SUBMIT_BANNER = BASE_URL + "contestEntry/create";
    }


    public static class ChatConstants {
        public static final String getCurrentMemberChat = BASE_URL + "chatRouter/getCurrentMemberChat/";
        public static final String getAllChatMessages = BASE_URL + "chatRouter/getAllChatMessages/";
        public static final String getOnlineOfflineUser = BASE_URL + "users/getOnlineAndOfflineCelebs/";
        public static final String newGetCallHistoryByMemberId = BASE_URL + "serviceTransaction/newGetCallHistoryByMemberId/";
    }


    public static class AudioVideoCallConstants {
        public static final String CALL_STATUS_URL = BASE_URL + "users/edit/";
    }


    //Local Images
    public static final String[] IMAGES = new String[]{
            // Heavy images
            "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg",
            "https://lh4.googleusercontent.com/--dq8niRp7W4/URquVgmXvgI/AAAAAAAAAbs/-gnuLQfNnBA/s1024/A%252520Song%252520of%252520Ice%252520and%252520Fire.jpg",
            "https://lh5.googleusercontent.com/-7qZeDtRKFKc/URquWZT1gOI/AAAAAAAAAbs/hqWgteyNXsg/s1024/Another%252520Rockaway%252520Sunset.jpg",
            "https://lh3.googleusercontent.com/--L0Km39l5J8/URquXHGcdNI/AAAAAAAAAbs/3ZrSJNrSomQ/s1024/Antelope%252520Butte.jpg",
            "https://lh6.googleusercontent.com/-8HO-4vIFnlw/URquZnsFgtI/AAAAAAAAAbs/WT8jViTF7vw/s1024/Antelope%252520Hallway.jpg",
            "https://lh4.googleusercontent.com/-WIuWgVcU3Qw/URqubRVcj4I/AAAAAAAAAbs/YvbwgGjwdIQ/s1024/Antelope%252520Walls.jpg",
            "https://lh6.googleusercontent.com/-UBmLbPELvoQ/URqucCdv0kI/AAAAAAAAAbs/IdNhr2VQoQs/s1024/Apre%2525CC%252580s%252520la%252520Pluie.jpg",
            "https://lh3.googleusercontent.com/-s-AFpvgSeew/URquc6dF-JI/AAAAAAAAAbs/Mt3xNGRUd68/s1024/Backlit%252520Cloud.jpg",
            "https://lh5.googleusercontent.com/-bvmif9a9YOQ/URquea3heHI/AAAAAAAAAbs/rcr6wyeQtAo/s1024/Bee%252520and%252520Flower.jpg",
            "https://lh5.googleusercontent.com/-n7mdm7I7FGs/URqueT_BT-I/AAAAAAAAAbs/9MYmXlmpSAo/s1024/Bonzai%252520Rock%252520Sunset.jpg",
            "https://lh6.googleusercontent.com/-4CN4X4t0M1k/URqufPozWzI/AAAAAAAAAbs/8wK41lg1KPs/s1024/Caterpillar.jpg",
            "https://lh3.googleusercontent.com/-rrFnVC8xQEg/URqufdrLBaI/AAAAAAAAAbs/s69WYy_fl1E/s1024/Chess.jpg",
            "https://lh5.googleusercontent.com/-WVpRptWH8Yw/URqugh-QmDI/AAAAAAAAAbs/E-MgBgtlUWU/s1024/Chihuly.jpg",
            "https://lh5.googleusercontent.com/-0BDXkYmckbo/URquhKFW84I/AAAAAAAAAbs/ogQtHCTk2JQ/s1024/Closed%252520Door.jpg",
            "https://lh3.googleusercontent.com/-PyggXXZRykM/URquh-kVvoI/AAAAAAAAAbs/hFtDwhtrHHQ/s1024/Colorado%252520River%252520Sunset.jpg",
            "https://lh3.googleusercontent.com/-ZAs4dNZtALc/URquikvOCWI/AAAAAAAAAbs/DXz4h3dll1Y/s1024/Colors%252520of%252520Autumn.jpg",
            "https://lh4.googleusercontent.com/-GztnWEIiMz8/URqukVCU7bI/AAAAAAAAAbs/jo2Hjv6MZ6M/s1024/Countryside.jpg",
            "https://lh4.googleusercontent.com/-bEg9EZ9QoiM/URquklz3FGI/AAAAAAAAAbs/UUuv8Ac2BaE/s1024/Death%252520Valley%252520-%252520Dunes.jpg",
            "https://lh6.googleusercontent.com/-ijQJ8W68tEE/URqulGkvFEI/AAAAAAAAAbs/zPXvIwi_rFw/s1024/Delicate%252520Arch.jpg",
            "https://lh5.googleusercontent.com/-Oh8mMy2ieng/URqullDwehI/AAAAAAAAAbs/TbdeEfsaIZY/s1024/Despair.jpg",
            "https://lh5.googleusercontent.com/-gl0y4UiAOlk/URqumC_KjBI/AAAAAAAAAbs/PM1eT7dn4oo/s1024/Eagle%252520Fall%252520Sunrise.jpg",
            "https://lh3.googleusercontent.com/-hYYHd2_vXPQ/URqumtJa9eI/AAAAAAAAAbs/wAalXVkbSh0/s1024/Electric%252520Storm.jpg",
            "https://lh5.googleusercontent.com/-PyY_yiyjPTo/URqunUOhHFI/AAAAAAAAAbs/azZoULNuJXc/s1024/False%252520Kiva.jpg",
            "https://lh6.googleusercontent.com/-PYvLVdvXywk/URqunwd8hfI/AAAAAAAAAbs/qiMwgkFvf6I/s1024/Fitzgerald%252520Streaks.jpg",
            "https://lh4.googleusercontent.com/-KIR_UobIIqY/URquoCZ9SlI/AAAAAAAAAbs/Y4d4q8sXu4c/s1024/Foggy%252520Sunset.jpg",
            "https://lh6.googleusercontent.com/-9lzOk_OWZH0/URquoo4xYoI/AAAAAAAAAbs/AwgzHtNVCwU/s1024/Frantic.jpg",
            "https://lh3.googleusercontent.com/-0X3JNaKaz48/URqupH78wpI/AAAAAAAAAbs/lHXxu_zbH8s/s1024/Golden%252520Gate%252520Afternoon.jpg",
            "https://lh6.googleusercontent.com/-95sb5ag7ABc/URqupl95RDI/AAAAAAAAAbs/g73R20iVTRA/s1024/Golden%252520Gate%252520Fog.jpg",
            "https://lh3.googleusercontent.com/-JB9v6rtgHhk/URqup21F-zI/AAAAAAAAAbs/64Fb8qMZWXk/s1024/Golden%252520Grass.jpg",
            "https://lh4.googleusercontent.com/-EIBGfnuLtII/URquqVHwaRI/AAAAAAAAAbs/FA4McV2u8VE/s1024/Grand%252520Teton.jpg",
            "https://lh4.googleusercontent.com/-WoMxZvmN9nY/URquq1v2AoI/AAAAAAAAAbs/grj5uMhL6NA/s1024/Grass%252520Closeup.jpg",
            "https://lh3.googleusercontent.com/-6hZiEHXx64Q/URqurxvNdqI/AAAAAAAAAbs/kWMXM3o5OVI/s1024/Green%252520Grass.jpg",
            "https://lh5.googleusercontent.com/-6LVb9OXtQ60/URquteBFuKI/AAAAAAAAAbs/4F4kRgecwFs/s1024/Hanging%252520Leaf.jpg",
            "https://lh4.googleusercontent.com/-zAvf__52ONk/URqutT_IuxI/AAAAAAAAAbs/D_bcuc0thoU/s1024/Highway%2525201.jpg",
            "https://lh6.googleusercontent.com/-H4SrUg615rA/URquuL27fXI/AAAAAAAAAbs/4aEqJfiMsOU/s1024/Horseshoe%252520Bend%252520Sunset.jpg",
            "https://lh4.googleusercontent.com/-JhFi4fb_Pqw/URquuX-QXbI/AAAAAAAAAbs/IXpYUxuweYM/s1024/Horseshoe%252520Bend.jpg",
            "https://lh5.googleusercontent.com/-UGgssvFRJ7g/URquueyJzGI/AAAAAAAAAbs/yYIBlLT0toM/s1024/Into%252520the%252520Blue.jpg",
            "https://lh3.googleusercontent.com/-CH7KoupI7uI/URquu0FF__I/AAAAAAAAAbs/R7GDmI7v_G0/s1024/Jelly%252520Fish%2525202.jpg",
            "https://lh4.googleusercontent.com/-pwuuw6yhg8U/URquvPxR3FI/AAAAAAAAAbs/VNGk6f-tsGE/s1024/Jelly%252520Fish%2525203.jpg",
            "https://lh5.googleusercontent.com/-GoUQVw1fnFw/URquv6xbC0I/AAAAAAAAAbs/zEUVTQQ43Zc/s1024/Kauai.jpg",
            "https://lh6.googleusercontent.com/-8QdYYQEpYjw/URquwvdh88I/AAAAAAAAAbs/cktDy-ysfHo/s1024/Kyoto%252520Sunset.jpg",
            "https://lh4.googleusercontent.com/-vPeekyDjOE0/URquwzJ28qI/AAAAAAAAAbs/qxcyXULsZrg/s1024/Lake%252520Tahoe%252520Colors.jpg",
            "https://lh4.googleusercontent.com/-xBPxWpD4yxU/URquxWHk8AI/AAAAAAAAAbs/ARDPeDYPiMY/s1024/Lava%252520from%252520the%252520Sky.jpg",
            "https://lh3.googleusercontent.com/-897VXrJB6RE/URquxxxd-5I/AAAAAAAAAbs/j-Cz4T4YvIw/s1024/Leica%25252050mm%252520Summilux.jpg",
            "https://lh5.googleusercontent.com/-qSJ4D4iXzGo/URquyDWiJ1I/AAAAAAAAAbs/k2pBXeWehOA/s1024/Leica%25252050mm%252520Summilux.jpg",
            "https://lh6.googleusercontent.com/-dwlPg83vzLg/URquylTVuFI/AAAAAAAAAbs/G6SyQ8b4YsI/s1024/Leica%252520M8%252520%252528Front%252529.jpg",
            "https://lh3.googleusercontent.com/-R3_EYAyJvfk/URquzQBv8eI/AAAAAAAAAbs/b9xhpUM3pEI/s1024/Light%252520to%252520Sand.jpg",
            "https://lh3.googleusercontent.com/-fHY5h67QPi0/URqu0Cp4J1I/AAAAAAAAAbs/0lG6m94Z6vM/s1024/Little%252520Bit%252520of%252520Paradise.jpg",
            "https://lh5.googleusercontent.com/-TzF_LwrCnRM/URqu0RddPOI/AAAAAAAAAbs/gaj2dLiuX0s/s1024/Lone%252520Pine%252520Sunset.jpg",
            "https://lh3.googleusercontent.com/-4HdpJ4_DXU4/URqu046dJ9I/AAAAAAAAAbs/eBOodtk2_uk/s1024/Lonely%252520Rock.jpg",
            "https://lh6.googleusercontent.com/-erbF--z-W4s/URqu1ajSLkI/AAAAAAAAAbs/xjDCDO1INzM/s1024/Longue%252520Vue.jpg",
            "https://lh6.googleusercontent.com/-0CXJRdJaqvc/URqu1opNZNI/AAAAAAAAAbs/PFB2oPUU7Lk/s1024/Look%252520Me%252520in%252520the%252520Eye.jpg",
            "https://lh3.googleusercontent.com/-D_5lNxnDN6g/URqu2Tk7HVI/AAAAAAAAAbs/p0ddca9W__Y/s1024/Lost%252520in%252520a%252520Field.jpg",
            "https://lh6.googleusercontent.com/-flsqwMrIk2Q/URqu24PcmjI/AAAAAAAAAbs/5ocIH85XofM/s1024/Marshall%252520Beach%252520Sunset.jpg",
            "https://lh4.googleusercontent.com/-Y4lgryEVTmU/URqu28kG3gI/AAAAAAAAAbs/OjXpekqtbJ4/s1024/Mono%252520Lake%252520Blue.jpg",
            "https://lh4.googleusercontent.com/-AaHAJPmcGYA/URqu3PIldHI/AAAAAAAAAbs/lcTqk1SIcRs/s1024/Monument%252520Valley%252520Overlook.jpg",
            "https://lh4.googleusercontent.com/-vKxfdQ83dQA/URqu31Yq_BI/AAAAAAAAAbs/OUoGk_2AyfM/s1024/Moving%252520Rock.jpg",
            "https://lh5.googleusercontent.com/-CG62QiPpWXg/URqu4ia4vRI/AAAAAAAAAbs/0YOdqLAlcAc/s1024/Napali%252520Coast.jpg",
            "https://lh6.googleusercontent.com/-wdGrP5PMmJQ/URqu5PZvn7I/AAAAAAAAAbs/m0abEcdPXe4/s1024/One%252520Wheel.jpg",
            "https://lh6.googleusercontent.com/-6WS5DoCGuOA/URqu5qx1UgI/AAAAAAAAAbs/giMw2ixPvrY/s1024/Open%252520Sky.jpg",
            "https://lh6.googleusercontent.com/-u8EHKj8G8GQ/URqu55sM6yI/AAAAAAAAAbs/lIXX_GlTdmI/s1024/Orange%252520Sunset.jpg",
            "https://lh6.googleusercontent.com/-74Z5qj4bTDE/URqu6LSrJrI/AAAAAAAAAbs/XzmVkw90szQ/s1024/Orchid.jpg",
            "https://lh6.googleusercontent.com/-lEQE4h6TePE/URqu6t_lSkI/AAAAAAAAAbs/zvGYKOea_qY/s1024/Over%252520there.jpg",
            "https://lh5.googleusercontent.com/-cauH-53JH2M/URqu66v_USI/AAAAAAAAAbs/EucwwqclfKQ/s1024/Plumes.jpg",
            "https://lh3.googleusercontent.com/-eDLT2jHDoy4/URqu7axzkAI/AAAAAAAAAbs/iVZE-xJ7lZs/s1024/Rainbokeh.jpg",
            "https://lh5.googleusercontent.com/-j1NLqEFIyco/URqu8L1CGcI/AAAAAAAAAbs/aqZkgX66zlI/s1024/Rainbow.jpg",
            "https://lh5.googleusercontent.com/-DRnqmK0t4VU/URqu8XYN9yI/AAAAAAAAAbs/LgvF_592WLU/s1024/Rice%252520Fields.jpg",
            "https://lh3.googleusercontent.com/-hwh1v3EOGcQ/URqu8qOaKwI/AAAAAAAAAbs/IljRJRnbJGw/s1024/Rockaway%252520Fire%252520Sky.jpg",
            "https://lh5.googleusercontent.com/-wjV6FQk7tlk/URqu9jCQ8sI/AAAAAAAAAbs/RyYUpdo-c9o/s1024/Rockaway%252520Flow.jpg",
            "https://lh6.googleusercontent.com/-6cAXNfo7D20/URqu-BdzgPI/AAAAAAAAAbs/OmsYllzJqwo/s1024/Rockaway%252520Sunset%252520Sky.jpg",
            "https://lh3.googleusercontent.com/-sl8fpGPS-RE/URqu_BOkfgI/AAAAAAAAAbs/Dg2Fv-JxOeg/s1024/Russian%252520Ridge%252520Sunset.jpg",
            "https://lh6.googleusercontent.com/-gVtY36mMBIg/URqu_q91lkI/AAAAAAAAAbs/3CiFMBcy5MA/s1024/Rust%252520Knot.jpg",
            "https://lh6.googleusercontent.com/-GHeImuHqJBE/URqu_FKfVLI/AAAAAAAAAbs/axuEJeqam7Q/s1024/Sailing%252520Stones.jpg",
            "https://lh3.googleusercontent.com/-hBbYZjTOwGc/URqu_ycpIrI/AAAAAAAAAbs/nAdJUXnGJYE/s1024/Seahorse.jpg",
            "https://lh3.googleusercontent.com/-Iwi6-i6IexY/URqvAYZHsVI/AAAAAAAAAbs/5ETWl4qXsFE/s1024/Shinjuku%252520Street.jpg",
            "https://lh6.googleusercontent.com/-amhnySTM_MY/URqvAlb5KoI/AAAAAAAAAbs/pFCFgzlKsn0/s1024/Sierra%252520Heavens.jpg",
            "https://lh5.googleusercontent.com/-dJgjepFrYSo/URqvBVJZrAI/AAAAAAAAAbs/v-F5QWpYO6s/s1024/Sierra%252520Sunset.jpg",
            "https://lh4.googleusercontent.com/-Z4zGiC5nWdc/URqvBdEwivI/AAAAAAAAAbs/ZRZR1VJ84QA/s1024/Sin%252520Lights.jpg",
            "https://lh4.googleusercontent.com/-_0cYiWW8ccY/URqvBz3iM4I/AAAAAAAAAbs/9N_Wq8MhLTY/s1024/Starry%252520Lake.jpg",
            "https://lh3.googleusercontent.com/-A9LMoRyuQUA/URqvCYx_JoI/AAAAAAAAAbs/s7sde1Bz9cI/s1024/Starry%252520Night.jpg",
            "https://lh3.googleusercontent.com/-KtLJ3k858eY/URqvC_2h_bI/AAAAAAAAAbs/zzEBImwDA_g/s1024/Stream.jpg",
            "https://lh5.googleusercontent.com/-dFB7Lad6RcA/URqvDUftwWI/AAAAAAAAAbs/BrhoUtXTN7o/s1024/Strip%252520Sunset.jpg",
            "https://lh5.googleusercontent.com/-at6apgFiN20/URqvDyffUZI/AAAAAAAAAbs/clABCx171bE/s1024/Sunset%252520Hills.jpg",
            "https://lh4.googleusercontent.com/-7-EHhtQthII/URqvEYTk4vI/AAAAAAAAAbs/QSJZoB3YjVg/s1024/Tenaya%252520Lake%2525202.jpg",
            "https://lh6.googleusercontent.com/-8MrjV_a-Pok/URqvFC5repI/AAAAAAAAAbs/9inKTg9fbCE/s1024/Tenaya%252520Lake.jpg",
            "https://lh5.googleusercontent.com/-B1HW-z4zwao/URqvFWYRwUI/AAAAAAAAAbs/8Peli53Bs8I/s1024/The%252520Cave%252520BW.jpg",
            "https://lh3.googleusercontent.com/-PO4E-xZKAnQ/URqvGRqjYkI/AAAAAAAAAbs/42nyADFsXag/s1024/The%252520Fisherman.jpg",
            "https://lh4.googleusercontent.com/-iLyZlzfdy7s/URqvG0YScdI/AAAAAAAAAbs/1J9eDKmkXtk/s1024/The%252520Night%252520is%252520Coming.jpg",
            "https://lh6.googleusercontent.com/-G-k7YkkUco0/URqvHhah6fI/AAAAAAAAAbs/_taQQG7t0vo/s1024/The%252520Road.jpg",
            "https://lh6.googleusercontent.com/-h-ALJt7kSus/URqvIThqYfI/AAAAAAAAAbs/ejiv35olWS8/s1024/Tokyo%252520Heights.jpg",
            "https://lh5.googleusercontent.com/-Hy9k-TbS7xg/URqvIjQMOxI/AAAAAAAAAbs/RSpmmOATSkg/s1024/Tokyo%252520Highway.jpg",
            "https://lh6.googleusercontent.com/-83oOvMb4OZs/URqvJL0T7lI/AAAAAAAAAbs/c5TECZ6RONM/s1024/Tokyo%252520Smog.jpg",
            "https://lh3.googleusercontent.com/-FB-jfgREEfI/URqvJI3EXAI/AAAAAAAAAbs/XfyweiRF4v8/s1024/Tufa%252520at%252520Night.jpg",
            "https://lh4.googleusercontent.com/-vngKD5Z1U8w/URqvJUCEgPI/AAAAAAAAAbs/ulxCMVcU6EU/s1024/Valley%252520Sunset.jpg",
            "https://lh6.googleusercontent.com/-DOz5I2E2oMQ/URqvKMND1kI/AAAAAAAAAbs/Iqf0IsInleo/s1024/Windmill%252520Sunrise.jpg",
            "https://lh5.googleusercontent.com/-biyiyWcJ9MU/URqvKculiAI/AAAAAAAAAbs/jyPsCplJOpE/s1024/Windmill.jpg",
            "https://lh4.googleusercontent.com/-PDT167_xRdA/URqvK36mLcI/AAAAAAAAAbs/oi2ik9QseMI/s1024/Windmills.jpg",
            "https://lh5.googleusercontent.com/-kI_QdYx7VlU/URqvLXCB6gI/AAAAAAAAAbs/N31vlZ6u89o/s1024/Yet%252520Another%252520Rockaway%252520Sunset.jpg",
            "https://lh4.googleusercontent.com/-e9NHZ5k5MSs/URqvMIBZjtI/AAAAAAAAAbs/1fV810rDNfQ/s1024/Yosemite%252520Tree.jpg",
            // Light images
            "http://tabletpcssource.com/wp-content/uploads/2011/05/android-logo.png",
            "http://simpozia.com/pages/images/stories/windows-icon.png",
            "http://radiotray.sourceforge.net/radio.png",
            "http://www.bandwidthblog.com/wp-content/uploads/2011/11/twitter-logo.png",
            "http://weloveicons.s3.amazonaws.com/icons/100907_itunes1.png",
            "http://weloveicons.s3.amazonaws.com/icons/100929_applications.png",
            "http://www.idyllicmusic.com/index_files/get_apple-iphone.png",
            "http://www.frenchrevolutionfood.com/wp-content/uploads/2009/04/Twitter-Bird.png",
            "http://3.bp.blogspot.com/-ka5MiRGJ_S4/TdD9OoF6bmI/AAAAAAAAE8k/7ydKtptUtSg/s1600/Google_Sky%2BMaps_Android.png",
            "http://www.desiredsoft.com/images/icon_webhosting.png",
            "http://goodereader.com/apps/wp-content/uploads/downloads/thumbnails/2012/01/hi-256-0-99dda8c730196ab93c67f0659d5b8489abdeb977.png",
            "http://1.bp.blogspot.com/-mlaJ4p_3rBU/TdD9OWxN8II/AAAAAAAAE8U/xyynWwr3_4Q/s1600/antivitus_free.png",
            "http://cdn3.iconfinder.com/data/icons/transformers/computer.png",
            "http://cdn.geekwire.com/wp-content/uploads/2011/04/firefox.png?7794fe",
            "https://ssl.gstatic.com/android/market/com.rovio.angrybirdsseasons/hi-256-9-347dae230614238a639d21508ae492302340b2ba",
            "http://androidblaze.com/wp-content/uploads/2011/12/tablet-pc-256x256.jpg",
            "http://www.theblaze.com/wp-content/uploads/2011/08/Apple.png",
            "http://1.bp.blogspot.com/-y-HQwQ4Kuu0/TdD9_iKIY7I/AAAAAAAAE88/3G4xiclDZD0/s1600/Twitter_Android.png",
            "http://3.bp.blogspot.com/-nAf4IMJGpc8/TdD9OGNUHHI/AAAAAAAAE8E/VM9yU_lIgZ4/s1600/Adobe%2BReader_Android.png",
            "http://cdn.geekwire.com/wp-content/uploads/2011/05/oovoo-android.png?7794fe",
            "http://icons.iconarchive.com/icons/kocco/ndroid/128/android-market-2-icon.png",
            "http://thecustomizewindows.com/wp-content/uploads/2011/11/Nicest-Android-Live-Wallpapers.png",
            "http://c.wrzuta.pl/wm16596/a32f1a47002ab3a949afeb4f",
            "http://macprovid.vo.llnwd.net/o43/hub/media/1090/6882/01_headline_Muse.jpg",
            // Special cases
            "http://cdn.urbanislandz.com/wp-content/uploads/2011/10/MMSposter-large.jpg", // Very large image
            "http://www.ioncannon.net/wp-content/uploads/2011/06/test9.webp", // WebP image
            "http://4.bp.blogspot.com/-LEvwF87bbyU/Uicaskm-g6I/AAAAAAAAZ2c/V-WZZAvFg5I/s800/Pesto+Guacamole+500w+0268.jpg", // Image with "Mark has been invalidated" problem
            "file:///sdcard/Universal Image Loader @#&=+-_.,!()~'%20.png", // Image from SD card with encoded symbols
            "assets://Living Things @#&=+-_.,!()~'%20.jpg", // Image from assets
            "drawable://" + R.drawable.celebicon_iogo, // Image from drawables
            "http://upload.wikimedia.org/wikipedia/ru/b/b6/Как_кот_с_мышами_воевал.png", // Link with UTF-8
            "https://www.eff.org/sites/default/files/chrome150_0.jpg", // Image from HTTPS
            "http://bit.ly/soBiXr", // Redirect link
            "http://img001.us.expono.com/100001/100001-1bc30-2d736f_m.jpg", // EXIF
            "", // Empty link
            "http://wrong.site.com/corruptedLink", // Wrong link
    };


    public static class SearchConstants {

        public static final String SEARCH_CELEB = BASE_URL + "users/getCelebSearch/";

        public static final String SAVE_SEARCH = BASE_URL + "searchHistory/saveSearchHistory/";

        public static final String GET_ALL_HISTORY = BASE_URL + "searchHistory/getSearchHistoryByMemberId/";

        public static final String DELETE_INDIVIDUAL_SEARCH = BASE_URL + "searchHistory/deleteSearchHistoryByCelebrityId/";

        public static final String CLEAR_ALL_SEARCH = BASE_URL + "searchHistory/clearAllSearchHistory/";

    }


    public static class CreditConstants {

        public static final String PAYTM_GATEWAY = "Paytm";

        public static final String INSTAMOJO_GATEWAY = "Instamojo";

        public static final String PAYPAL_GATEWAY = "Paypal";


//        public static final String BRAINE_TREE_TOKEN = "token";

//        public static final String BRAINE_TRANSACTIONPAGE = "transactionpage";


        public static final String PAYPAL_TEST_CLIENT_ID = "Ae4sS0wlUvDJL5c19PdynBwCXJaDrP8NzoeeQKoxuz8ohwE3m_0d5-KcXZNfv-FGCpHyQMUmL0Xh2WK6";

        public static final String PAYPAL_LIVE_CLIENT_ID = "AbPsy6MOjjXTr3tw-sU5pW3kPw6lixeuJZMLhV1RlhGUO4mMM8hEnASdc1kp_q168Z5cmFjUrBsQ7nlW";

        public static final String CREDITS_PACKAGE_COLLECTION = BASE_URL + "packageCollection/getDefaultCreditPackage/";

        public static final String CHECK_CURRENCY_CALCULATER = BASE_URL + "packageCollection/calculateCreditPrice";

        public static final String CALCULATE_CREDITS_WITH_TAX_URL = BASE_URL + "packageCollection/calculateFinalCreditPriceWithGST";

        //   Instamojo
//        Step 1
        public static final String CREATE_TRANSACTION_URL = BASE_URL + "paymentTransaction/createPaymentTransaction";

        //        Paytm Gateway Only 1.1
        public static final String GET_CHECKSUM_VALUE = BASE_URL + "payments/generate_checksum";


        //BraineTree Authentication url

        public static final String GENERATE_BRAINETREE_TOKEN_URL = BASE_URL + "paymentTransaction/generateClientToken/";

        public static final String BRAINETREE_CHECKOUT_URL = BASE_URL + "paymentTransaction/checkout/";


        //For Paytm and Instamojo only
        public static final String CHECKOUT_TRANSACTION_STATUS = BASE_URL + "paymentTransaction/generalCheckout/";

    }


    /*public static class PaytmPageConstants {

        public static final String MERCHANT_ID = "IENTER35085996277002";
        public static final String WEBSITE = "APPSTAGING";
        public static final String INDUSTRY_TYPE = "Retail";
        public static final String PAYTM_CALLBACK_URL = "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID=";
        public static final String WAP = "WAP";

        public static final String INSTAMOJO = "https://test.instamojo.com/api/1.1/payment-requests/";
        public static final String PAYMENTTRANSACTION = "https://test.instamojo.com/api/1.1/payment-requests/";


    }*/

    public static class StoriesConstants {

        public static final String GET_CELEB_INDIVIDUAL_STORIES = BASE_URL + "story/getIndividualStory/";

        public static final String GET_INDIVIDUAL_STORIES = BASE_URL + "story/getStory/";

        public static final String DELETE_STORY = BASE_URL + "story/deleteStoryById/";

        public static final String GET_STORY_SEEN_PROFILES = BASE_URL + "story/getStorySeenStatus/";

    }

}
