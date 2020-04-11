package info.sumantv.flow.retrofitcall;


import com.google.gson.JsonElement;

import info.sumantv.flow.appmanagers.feed.models.CommentReportOptionPostParams;
import info.sumantv.flow.appmanagers.feed.models.PostReportOptionsParams;
import info.sumantv.flow.ModelClass.FanUnFanPostParams;
import info.sumantv.flow.bottommenu.mypaymentactionsfragment.CreditsModelDatas.CreateTransactionData;
import info.sumantv.flow.bottommenu.mypaymentactionsfragment.CreditsModelDatas.PackageSelectionData;
import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.celebflow.notificationssettingsapp.NotificationSaveData;
import info.sumantv.flow.menu_list.Settings.ApplicationSettings.AppSettingsData;
import info.sumantv.flow.menu_list.Settings.ChangePassword.ChangePasswordData;
import info.sumantv.flow.splashandintroscreens.TutorialScreens.TutorialData;
import info.sumantv.flow.userflow.UserActivitys.LoginActivity.LoginData;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.ArrayList;

/**
 *
 */

/**
 */

public interface ApiInterface {

    @POST(ApiClient.LOGIN_URL)
    Call<ApiResponseModel> getLogInReq(@Body LoginData loginData);

    @GET
    Call<ApiResponseModel> getIndustryProfession(@Url String url);


    @POST(ApiClient.CHANGEPASSWORD_URL)
    Call<ApiResponseModel> changePassword(@Body ChangePasswordData signupData);

    @POST(ApiClient.APP_SETTING_CREATE_URL)
    Call<ApiResponseModel> appSettingsCreateCall(@Body AppSettingsData signupData);

    @GET
    Call<ApiResponseModel> getAppSettinguserId(@Url String url);


    @GET
    Call<ApiResponseModel> getProfiledata(@Url String url);

    @GET
    Call<ApiResponseModel> getManagerProfile(@Url String url);

    @GET
    Call<ArrayList<TutorialData>> getAllSplashScreens(@Url String url);


    @Multipart
    @POST(Constants.FeedConstants.URL_CREATE_FEED)
    Call<ApiResponseModel> createFeed(@Part("feed") RequestBody feed, @Part MultipartBody.Part[] image);

    @Multipart
    @PUT(Constants.FeedConstants.URL_EDIT_FEED + "{path}")
    Call<ApiResponseModel> updateFeed(@Path("path") String _id, @Part("feed") RequestBody feed, @Part MultipartBody.Part[] image);

    @GET
    Call<ApiResponseModel> getNotificationData(@Url String getnotifications);

    @GET
    Call<ApiResponseModel> getCharitySettings(@Url String getCharitysSetiings);

    @GET
    Call<ApiResponseModel> getCommonDataCall(@Url String url);

    @GET
    Call<ApiResponseModel> getCountryList(@Url String url);

    @GET
    Call<ApiResponseModel> getAllNotificationAppList(@Url String url);





    @POST(ApiClient.CREATE_CREDITS)
    Call<ApiResponseModel> CREATE_CREDITS(@Body FanUnFanPostParams fanUnFanPostParams);


    @Multipart
    @POST(Constants.AuditionConstants.GET_AUDITION_CREATE_PROFILE)
    Call<ApiResponseModel> createAuditionProfile(@Part("audition") RequestBody audition,
                                                 @Part MultipartBody.Part profilePicUrl);

    @Multipart
    @PUT(Constants.AuditionConstants.GET_AUDITION_UPDATE_PROFILE + "{path}")
    Call<ApiResponseModel> updateAuditionProfile(
            @Path("path") String _id,
            @Part("audition") RequestBody audition,
            @Part MultipartBody.Part profilePicUrl);

    @Multipart
    @PUT(Constants.AuditionConstants.GET_AUDITION_UPDATE_PROFILE + "{path}")
    Call<ApiResponseModel> updateAuditionProfile(
            @Path("path") String _id,
            @Part("audition") RequestBody audition);

    @Multipart
    @PUT(Constants.AuditionConstants.GET_AUDITION_UPDATE_PROFILE + "{path}")
    Call<ApiResponseModel> updateAuditionProfile(
            @Path("path") String _id,
            @Part("audition") RequestBody audition,
            @Part MultipartBody.Part[] image);

    @GET
    Call<ApiResponseModel> getAuditionProfile(@Url String url);

    @GET
    Call<ApiResponseModel> getAllCelebritiesList(@Url String url);


    @GET
    Call<ApiResponseModel> getPackages(@Url String url);

    @GET
    Call<ApiResponseModel> getContracts(@Url String url);




    @GET
    Call<ApiResponseModel> getRoles(@Url String url);

    @GET
    Call<ApiResponseModel> getAgeRange(@Url String url);


    @GET
    Call<ApiResponseModel> getFilterByRoleType(@Url String url);

    @GET
    Call<ApiResponseModel> getAllTalents(@Url String url);

    @GET
    Call<ApiResponseModel> getAllTalentFevorites(@Url String url);


    @GET
    Call<ApiResponseModel> getAllAuditionFevorites(@Url String url);



    @GET
    Call<ApiResponseModel> getNotificationDeatils(@Url String url);

    @GET
    Call<ApiResponseModel> getSubTalents(@Url String url);


    @GET
    Call<ApiResponseModel> getEthnicity(@Url String url);

    @GET
    Call<ApiResponseModel> getMediaRequired(@Url String url);

    @GET
    Call<ApiResponseModel> getBodyType(@Url String url);

    @GET
    Call<ApiResponseModel> getHairColor(@Url String url);


    @GET
    Call<ApiResponseModel> getEyeColor(@Url String url);

    @GET
    Call<ApiResponseModel> getLanguages(@Url String url);

    @GET
    Call<ApiResponseModel> getBrineTreeToken(@Url String url);

    @POST(ApiClient.SAVE_NOTIFICATION)
    Call<ApiResponseModel> saveNotificationData(@Body NotificationSaveData notificationSaveData);






    @GET
    Call<ApiResponseModel> getCurrentMemberChat(@Url String url);

    @GET
    Call<ApiResponseModel> getAllChatMessages(@Url String url);

    @GET
    Call<ApiResponseModel> getOnlineOfflineUser(@Url String url);

    @GET
    Call<ApiResponseModel> newGetCallHistoryByMemberId(@Url String url);

    @GET
    Call<ApiResponseModel> getAllFeedbackItems(@Url String url);

    @POST(Constants.FeedConstants.postFeedbackOnComment)
    Call<ApiResponseModel> postFeedbackOnComment(@Body CommentReportOptionPostParams commentReportOptionPostParams);

    @POST(Constants.FeedConstants.POST_REPORT_FEEDBACK_ON_POST_OPTIONS)
    Call<ApiResponseModel> reportFeedPost(@Body PostReportOptionsParams commentReportOptionPostParams);


    @GET
    Call<ApiResponseModel> getAllPreferences(@Url String url);


    @GET
    Call<ApiResponseModel> getManagerDetails(@Url String url);


    @GET
    Call<ApiResponseModel> getIsAccess(@Url String url);

    @GET
    Call<ApiResponseModel> getManagerPermissionOptions(@Url String url);


    @GET
    Call<ApiResponseModel> getMyclient(@Url String url);

    @GET
    Call<ApiResponseModel> getSubManagers(@Url String url);



    @POST(ApiClient.SEND_OTP)
    Call<ApiResponseModel> otpVerifyManager(@Body OTPVerifyManager otpVerifyManager);

    @POST(ApiClient.SUBMIT_OTP)
    Call<ApiResponseModel> otpSendManager(@Body OTPSendManager otpSendManager);


    @POST(Constants.CreditConstants.CALCULATE_CREDITS_WITH_TAX_URL)
    Call<ApiResponseModel> checkCalculateCreditswithTax(@Body PackageSelectionData packageSelectionDataObj);


    @POST(Constants.CreditConstants.CREATE_TRANSACTION_URL)
    Call<ApiResponseModel> createTransactionData(@Body CreateTransactionData createTransactionData);





    @PUT(Constants.StoriesConstants.DELETE_STORY + "{path}")
    Call<ApiResponseModel> deleteStory(@Path("path") String storyId);

    @GET
    Call<ApiResponseModel> getIndustriesListForSearch(@Url String url);

    @GET
    Call<ApiResponseModel> getManagerLIst(@Url String url);

    @GET
    Call<ApiResponseModel> getCategoryListForSearch(@Url String url);

    @GET
    Call<ApiResponseModel> getCountriesAll(@Url String url);




    @GET
    Call<ApiResponseModel> getUserCompleteDate(@Url String url);


    @GET
    Call<ApiResponseModel> getCelebrityCompleteData(@Url String url);


    @GET()
    Call<ApiResponseModel> getBlockStatus(@Url String url);


    @PUT(Constants.AuditionConstants.UPDATE_AUDITION_DRAFT + "{path}")
    Call<ApiResponseModel> UPDATE_AUDITION_DRAFT(@Path("path") String url, @Body RequestBody requestBody);

    @PUT(ApiClient.NOTIFICATION_UPDATE + "{path}")
    Call<ApiResponseModel> NOTIFICATION_UPDATE(@Path("path") String url, @Body RequestBody requestBody);

    @POST(Constants.AuditionConstants.APPLY_AUDITION_ROLE)
    Call<ApiResponseModel> APPLY_AUDITION_ROLE(@Body RequestBody requestBody);

    //

    @GET
    Call<ApiResponseModel> GET(@Url String url);

    @POST
    Call<ApiResponseModel> POST(@Url String url, @Body RequestBody requestBody);

    @PUT
    Call<ApiResponseModel> PUT(@Url String url, @Body RequestBody requestBody);

    @DELETE
    Call<ApiResponseModel> DELETE(@Url String url);

    //

    @GET
    Call<JsonElement> GET_JSON(@Url String url);

    @POST
    Call<JsonElement> POST_JSON(@Url String url, @Body RequestBody requestBody);

    @PUT
    Call<JsonElement> PUT_JSON(@Url String url, @Body RequestBody requestBody);

    @DELETE
    Call<JsonElement> DELETE_JSON(@Url String url);

    //




    @POST(ApiClient.FORGOT_PASSWORD_GET_OTP)
    Call<ApiResponseModel> forgotGetOTP(@Body RequestBody requestBody);



    @PUT(ApiClient.CHANGE_PASSWORD + "{path}")
    Call<ApiResponseModel> changePassword(@Path("path") String _id, @Body RequestBody requestBody);

    @PUT(ApiClient.MEMBER_REGISTRATION_NEW_SINGUP + "{path}")
    Call<ApiResponseModel> memberRegistrationNewSingUpJSON(@Path("path") String _id, @Body RequestBody requestBody);

//    @PUT(ApiClient.MEMBER_REGISTRATION_NEW_SINGUP + "{path}")
//    Call<ApiResponseModel> memberRegistrationNewSingUpJSONNew(@Path("path") String _id, @Body SignupData requestBody);

    @Multipart
    @PUT(ApiClient.MEMBER_REGISTRATION_NEW_SINGUP + "{path}")
    Call<ApiResponseModel> memberRegistrationNewSingUpImage(@Path("path") String _id,
                                                            @Part("profile") RequestBody requestBody, @Part MultipartBody.Part profilePicUrl);

    @Multipart
    @POST(ApiClient.CREATE_STORE)
    Call<ApiResponseModel> createStore(
            @Part("story") RequestBody createStore,
            @Part MultipartBody.Part[] image);

    @Multipart
    @PUT(ApiClient.MANAGER_ADITIONAL_PROFILE_UPDATE + "{path}")
    Call<ApiResponseModel> editProfileUpdate(@Path("path") String _id,
                                             @Part("profile") RequestBody requestBody, @Part MultipartBody.Part profilePicUrl, @Part MultipartBody.Part coverPic);
}
