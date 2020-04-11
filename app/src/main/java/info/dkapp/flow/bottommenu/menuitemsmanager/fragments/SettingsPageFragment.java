package info.dkapp.flow.bottommenu.menuitemsmanager.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import info.dkapp.flow.bottommenu.activity.HelperActivity;
import info.dkapp.flow.bottommenu.interfaces.fragments.IFragment;

import info.dkapp.flow.R;
import info.dkapp.flow.celebflow.constants.Constants;
import info.dkapp.flow.celebflow.modelData.SignUpConditions;
import info.dkapp.flow.retrofitcall.*;
import info.dkapp.flow.userflow.Util.Common;
import info.dkapp.flow.utils.Logger;
import info.dkapp.flow.utils.internetchecker.InternetSpeedChecker;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.json.JSONObject;
import retrofit2.Call;


import static info.dkapp.flow.userflow.Util.Common.isManagerScreens;


public class SettingsPageFragment extends Fragment implements View.OnClickListener, IFragment, IApiListener, CompoundButton.OnCheckedChangeListener {
    LinearLayout iLFeedSettings, iLCharitySettings, iLActivityLog, iLManagerSettings, iLNotioficationSettings, iLLogout, iLChangePassword, iLNightMode, iLSearchHistory;
    Switch swSearchHistory, swNightMode;
    Boolean isActive = false;
    ProgressDialog progressDialog;
    CoordinatorLayout coordinatorLayout;
    ApiInterface apiInterface;
    IApiListener iApiListener;
    Boolean isPasswordVerified;
    String loginType = "";

    //    Context mContext;
    public static SettingsPageFragment newInstance(String param1, String param2) {
        SettingsPageFragment fragment = new SettingsPageFragment();
        return fragment;

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iApiListener = this;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.settings_activity, null);
        coordinatorLayout = root.findViewById(R.id.coordinatorLayout);
        iLCharitySettings = (LinearLayout) root.findViewById(R.id.iLCharitySettings);
        iLFeedSettings = (LinearLayout) root.findViewById(R.id.iLFeedSettings);
        iLActivityLog = (LinearLayout) root.findViewById(R.id.iLActivityLog);
        iLManagerSettings = (LinearLayout) root.findViewById(R.id.iLManagerSettings);
        iLNotioficationSettings = (LinearLayout) root.findViewById(R.id.iLNotioficationSettings);
        iLLogout = (LinearLayout) root.findViewById(R.id.iLLogout);
        iLChangePassword = (LinearLayout) root.findViewById(R.id.iLChangePassword);
        iLNightMode = (LinearLayout) root.findViewById(R.id.iLNightMode);
        iLSearchHistory = (LinearLayout) root.findViewById(R.id.iLSearchHistory);
        swNightMode = (Switch) root.findViewById(R.id.swNightMode);
        swSearchHistory = (Switch) root.findViewById(R.id.swSearchHistory);

        if (Common.isCelebAndManager(activity())) {
            iLCharitySettings.setVisibility(View.VISIBLE);
            if (isManagerScreens) {
                iLManagerSettings.setVisibility(View.VISIBLE);
            } else {
                iLManagerSettings.setVisibility(View.GONE);
            }
        } else if (Common.isCelebStatus(activity())) {
            iLCharitySettings.setVisibility(View.VISIBLE);
            if (isManagerScreens) {
                iLManagerSettings.setVisibility(View.VISIBLE);
            } else {
                iLManagerSettings.setVisibility(View.GONE);
            }
        } else if (Common.isManagerStatus(activity())) {
            iLCharitySettings.setVisibility(View.VISIBLE);
            if (isManagerScreens) {
                iLManagerSettings.setVisibility(View.VISIBLE);
            } else {
                iLManagerSettings.setVisibility(View.GONE);
            }
        }

        iLCharitySettings.setOnClickListener(this);
        iLActivityLog.setOnClickListener(this);
        iLManagerSettings.setOnClickListener(this);
        iLNotioficationSettings.setOnClickListener(this);
        iLLogout.setOnClickListener(this);
        iLChangePassword.setOnClickListener(this);
        iLNightMode.setOnClickListener(this);
        iLSearchHistory.setOnClickListener(this);
        iLFeedSettings.setOnClickListener(this);
        swNightMode.setOnCheckedChangeListener(this);
        swSearchHistory.setOnCheckedChangeListener(this);
        if(SessionManager.getInstance().getKeyValue(SessionManager.KEY_PAUSE_SEARCH_HISTORY,false)){
            swSearchHistory.setChecked(true);
        }

        if (SessionManager.getInstance().getKeyValue(SessionManager.KEY_LOGIN_TYPE,"") != null && !SessionManager.getInstance().getKeyValue(SessionManager.KEY_LOGIN_TYPE,"").isEmpty()) {
            loginType = SessionManager.getInstance().getKeyValue(SessionManager.KEY_LOGIN_TYPE,"");
        }

        isPasswordVerified = SessionManager.getInstance().getKeyValue(SessionManager.KEY_IS_PASSWORD_VERIFIED,false);
        return root;

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iLFeedSettings:
                Intent intent = new Intent(activity(), HelperActivity.class);
                intent.putExtra(Constants.FRAGMENT_TITLE, "Feed Settings");
                intent.putExtra(Constants.FRAGMENT_KEY, 8074);// FeedSettingsFragment
                startActivity(intent);
                break;
            case R.id.iLCharitySettings:
                intent = new Intent(activity(), HelperActivity.class);
                intent.putExtra(Constants.FRAGMENT_TITLE, "Charity Settings");
                intent.putExtra(Constants.FRAGMENT_KEY, 8017);// CharitySettingsFragment
                startActivity(intent);
                break;
            case R.id.iLActivityLog:
                intent = new Intent(activity(), HelperActivity.class);
                intent.putExtra(Constants.FRAGMENT_TITLE, "Activity Log");
                intent.putExtra(Constants.FRAGMENT_KEY, 8079);// ActivityLogFragment
                startActivity(intent);
                break;
            case R.id.iLManagerSettings:
                if (Common.isCelebAndManager(activity())) {
                    intent = new Intent(activity(), HelperActivity.class);
                    intent.putExtra(Constants.FRAGMENT_TITLE, "Manager Settings");
                    intent.putExtra(Constants.FRAGMENT_KEY, 8057);// CelebManagerSettingFragment
                    startActivity(intent);
                } else if (Common.isCelebStatus(activity())) {
                    Logger.d("isManager", "isCeleb");
                    intent = new Intent(activity(), HelperActivity.class);
                    intent.putExtra(Constants.FRAGMENT_TITLE, "Manager Settings");
                    intent.putExtra(Constants.FRAGMENT_KEY, 8046);// ManagerSettingsList
                    startActivity(intent);
                } else if (Common.isManagerStatus(activity())) {
                    Logger.d("isManager", "isManager");
                    intent = new Intent(activity(), HelperActivity.class);
                    intent.putExtra(Constants.FRAGMENT_TITLE, "Manager Settings");
                    intent.putExtra(Constants.FRAGMENT_KEY, 8047);// Managing_celeb_or_sub_manger_list
                    startActivity(intent);
                }
                break;
            case R.id.iLNotioficationSettings:
                intent = new Intent(activity(), HelperActivity.class);
                intent.putExtra(Constants.FRAGMENT_TITLE, "Notifications Settings");
                intent.putExtra(Constants.FRAGMENT_KEY, 8020);// NotificationSettingsFragment
                startActivity(intent);
                break;
            case R.id.iLLogout:
                try {
//                    activity().stopService(new Intent(activity(), MyFirebaseMessagingService.class));
                } catch (Exception e) {
                }

                if (InternetSpeedChecker.checkInternetAvaialable(getContext())) {
                    logoutUserInApp();
                }
                break;
            case R.id.iLChangePassword:
                if (loginType.equalsIgnoreCase("socialLogin") && !isPasswordVerified) {
                    apiInterface = ApiClient.getClient().create(ApiInterface.class);
                    Call<ApiResponseModel> call = apiInterface.GET(ApiClient.IS_PASSWORD_VERIFIED
                            + SessionManager.userLogin.userId);
                    Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call,
                            ApiClient.IS_PASSWORD_VERIFIED, true, iApiListener, true));
                } else {
                    if (InternetSpeedChecker.checkInternetAvaialable(activity())) {
                        SignUpConditions signUpConditions = new SignUpConditions();
                        signUpConditions.setLogin(false);
                        signUpConditions.setForgot(false);
                        signUpConditions.setChangePassword(true);
                        signUpConditions.setForOTPVerification(false);
                        signUpConditions.setSocialNetwork(false);
                        signUpConditions.setPasswordVerified(true);
                        signUpConditions.setFromSettings(true);
                        signUpConditions.setPasswordVerified(true);


                        intent = new Intent(activity(), HelperActivity.class);
                        intent.putExtra(Constants.FRAGMENT_TITLE, "");
                        intent.putExtra(Constants.FRAGMENT_KEY, 1001);// SignUpFragment
                        intent.putExtra("signUpConditions", signUpConditions);
                        startActivity(intent);
                    }
                }
                break;
            case R.id.iLNightMode:
                break;
            case R.id.iLSearchHistory:
                break;
        }
    }

    private void logoutUserInApp() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        JSONObject jsonObject = new JSONObject();
        try {
            if (SessionManager.userLogin.userId != null
                    && !SessionManager.userLogin.userId.isEmpty()) {
                jsonObject.put("userId", SessionManager.userLogin.userId);
            } else {
                jsonObject.put("userId", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        Call<ApiResponseModel> call = apiInterface.POST(ApiClient.LOGOUT_USER, requestBody);
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, ApiClient.LOGOUT_USER, true, iApiListener, false));
    }

    @Override
    public void changeTitle(String title) {

    }

    @Override
    public void showSnackBar(String snackBarText, int type) {

    }

    @Override
    public Activity activity() {
        return getActivity();
    }






    @Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
        if (condition.equals(ApiClient.LOGOUT_USER)) {
            try {
                Common.getInstance().LogOut(activity(), "true", apiResponseModel.success);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (condition.equals(ApiClient.IS_PASSWORD_VERIFIED)) {
            try {
                JSONObject jsonObject1 = new JSONObject(new Gson().toJson(apiResponseModel.data));
                Boolean isPasswordVerified_ = null;
                if (jsonObject1.has("isPasswordVerified")) {
                    isPasswordVerified_ = jsonObject1.getBoolean("isPasswordVerified");
                }

                if (isPasswordVerified_ != null) {
                    Logger.e("isPasswordVerified", isPasswordVerified_ + "");
                    isPasswordVerified = isPasswordVerified_;
                    SessionManager.getInstance().setKeyValue(SessionManager.KEY_IS_PASSWORD_VERIFIED,isPasswordVerified_);
                }
                SignUpConditions signUpConditions = new SignUpConditions();
                signUpConditions.setLogin(false);
                signUpConditions.setForgot(false);
                signUpConditions.setChangePassword(true);
                signUpConditions.setForOTPVerification(false);
                signUpConditions.setSocialNetwork(false);
                signUpConditions.setFromSettings(true);

                if (!isPasswordVerified) {
                    signUpConditions.setPasswordVerified(false);
                } else {
                    signUpConditions.setPasswordVerified(true);
                }
                if (InternetSpeedChecker.checkInternetAvaialable(activity())) {
                    Intent intent = new Intent(activity(), HelperActivity.class);
                    intent.putExtra(Constants.FRAGMENT_TITLE, "");
                    intent.putExtra(Constants.FRAGMENT_KEY, 1001);// SignUpFragment
                    intent.putExtra("signUpConditions", signUpConditions);
                    startActivity(intent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
        if (condition.equals(ApiClient.LOGOUT_USER)) {
            Toast.makeText(getActivity(), Constants.SOMETHING_WENT_WRONG_SERVER, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.swSearchHistory:
                if(isChecked){
                    SessionManager.getInstance().setKeyValue(SessionManager.KEY_PAUSE_SEARCH_HISTORY,true);
                    swSearchHistory.setChecked(true);
                } else {
                    SessionManager.getInstance().setKeyValue(SessionManager.KEY_PAUSE_SEARCH_HISTORY,false);
                    swSearchHistory.setChecked(false);
                }
                break;
            case R.id.swNightMode:
                Common.getInstance().cusToast(getActivity(),isChecked+"  swNightMode");
                break;

        }
    }
}

