package info.sumantv.flow.bottommenu.menuitemsmanager.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import info.sumantv.flow.bottommenu.interfaces.fragments.IFragment;

import info.sumantv.flow.R;
import info.sumantv.flow.menu_list.Settings.ApplicationSettings.AppSettingsData;
import info.sumantv.flow.menu_list.Settings.ApplicationSettings.SelectedSettingData;
import info.sumantv.flow.retrofitcall.*;
import info.sumantv.flow.userflow.Util.Common;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.json.JSONObject;
import retrofit2.Call;

public class ApplicationSettingsFragment extends Fragment implements View.OnClickListener, IFragment,IApiListener {
    RelativeLayout aplicationlayout;

    androidx.appcompat.widget.Toolbar toolbar_back;
    Button backbtn;
    TextView toolbarheadertitle;

    ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    Button save;
    SeekBar seekbar;

    Switch nigthmodeBtn, donnotmode;
    String nigthMode;
    String donotdisturb;
    TextView tvMin;
    public static String MemeberId = "";
    int seekBarProgress;
    String seekbarvalue = "25";

    private LinearLayout mDonotdistrubLayout;
    IApiListener iApiListener;

    public static ApplicationSettingsFragment newInstance(String param1, String param2) {
        ApplicationSettingsFragment fragment = new ApplicationSettingsFragment();

        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_application_settings, null);
//        Common.headerScreenatAllScreens(getGlobalActivity(),
//                getGlobalActivity().getIntent().getExtras().getString("HEADER_TITLE"), getGlobalActivity());
        iApiListener = this;
        initializeViews(root);
        setRangeSeekbar1();

        initilizeActions();
        getAppSettingAvailableData();
        return root;
    }

    private void getAppSettingAvailableData() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.getAppSettinguserId(ApiClient.GETSETTINGSDATA_BY_USERID
                + SessionManager.userLogin.userId + "/");
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(),call,ApiClient.GETSETTINGSDATA_BY_USERID,true,iApiListener,false));
    }

    private void initilizeActions() {

        save.setOnClickListener(this);
        /*nigthmodeBtn.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                if (on) {
                    nigthMode = "true";
                } else {
                    nigthMode = "false";
                }

            }
        });*/


        nigthmodeBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    nigthMode = "true";
                } else {
                    nigthMode = "false";
                }
            }
        });

        donnotmode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    donotdisturb = "true";
                    mDonotdistrubLayout.setVisibility(View.VISIBLE);
                } else {
                    donotdisturb = "false";
                    mDonotdistrubLayout.setVisibility(View.GONE);
                }
            }
        });


    }

    private void initializeViews(View root) {
        nigthmodeBtn = (Switch) root.findViewById(R.id.nigthmodeBtn);
        donnotmode = (Switch) root.findViewById(R.id.donnotmode);
//        donnotmode.setToggleOff();

        save = (Button) root.findViewById(R.id.save);

        seekbar = (SeekBar) root.findViewById(R.id.rangeSeekbar1);
        tvMin = (TextView) root.findViewById(R.id.textMin1);
        mDonotdistrubLayout = (LinearLayout) root.findViewById(R.id.donotdistrub_layout);
    }


    private void setRangeSeekbar1() {
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int seekBarProgress = 25;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarProgress = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                tvMin.setText(String.valueOf(seekBarProgress));
                seekbarvalue = String.valueOf(seekBarProgress);
            }
        });
    }


    private void dataSendingServer() {
        AppSettingsData changePasswordData = new AppSettingsData(
                SessionManager.userLogin.userId,
                nigthMode, tvMin.getText().toString(), donotdisturb);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.appSettingsCreateCall(changePasswordData);
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(),call,ApiClient.APP_SETTING_CREATE_URL,true,iApiListener,false));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.save) {
            if (save.getText().toString().equals("Update")) {
//                updateAppSettingdata();
                updateAppSettings();
            } else {
                dataSendingServer();
            }

        }

    }

    private void updateAppSettings() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        JSONObject params = new JSONObject();
        try {
            params.put("nightMode", nigthMode);
            params.put("doNotDisturb", donotdisturb);
            params.put("dndDuration", tvMin.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),params.toString());
        Call<ApiResponseModel> call = apiInterface.PUT(ApiClient.UPDATE_APP_SETTINGS+MemeberId,requestBody);
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(),call,ApiClient.UPDATE_APP_SETTINGS,true,iApiListener,false));
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
        if(condition.equals(ApiClient.GETSETTINGSDATA_BY_USERID)){
            try {
                Type type = new TypeToken<SelectedSettingData>(){}.getType();
                SelectedSettingData settingData = new Gson().fromJson(new Gson().toJson(apiResponseModel.data),type);
                if (settingData.getMemberId() != null) {
                    MemeberId = settingData.getMemberId();
                    if (settingData.isNightMode()) {
                        nigthMode = "true";
//                            nigthmodeBtn.setToggleOn();
                        donnotmode.setChecked(true);
                    } else {
                        nigthMode = "false";
//                            nigthmodeBtn.setToggleOff();
                        donnotmode.setChecked(false);
                    }

                    if (settingData.isDoNotDisturb()) {
                        donotdisturb = "true";
//                            donnotmode.setToggleOn();
                        donnotmode.setChecked(true);
                        mDonotdistrubLayout.setVisibility(View.VISIBLE);
                    } else {
                        donotdisturb = "false";
                        donnotmode.setChecked(false);
                        mDonotdistrubLayout.setVisibility(View.GONE);
                    }

                    if (settingData.getDndDuration() != 0) {
                        tvMin.setText(String.valueOf(settingData.getDndDuration()));
                        seekBarProgress = settingData.getDndDuration();
                        seekbar.setProgress(seekBarProgress);
//                            seekbar.setPosition(response.body().getDndDuration());
                    } else {

                    }
                    save.setText("Update");
                } else {
                    save.setText("Save");
//                        Toast.makeText(activity(), "failed to get selected settings data", Toast.LENGTH_SHORT).show();
//                        activity().finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if(condition.equals(ApiClient.APP_SETTING_CREATE_URL)) {
            try {
                Type type = new TypeToken<AppSettingsData>() {}.getType();
                AppSettingsData appSettingsData = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                String message = appSettingsData.getMessage();
                Log.v("response", message);


                if (message != null) {
                    if (message.equals("AppSettings saved sucessfully")) {
                        Toast.makeText(activity(), "AppSettings saved sucessfully", Toast.LENGTH_SHORT).show();
                        activity().finish();
                    } else {
                        Toast.makeText(activity(), message, Toast.LENGTH_SHORT).show();
                        activity().finish();
                    }
                } else {
                    Toast.makeText(activity(), "AppSettings saved failed", Toast.LENGTH_SHORT).show();
                    activity().finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if(condition.equals(ApiClient.UPDATE_APP_SETTINGS)){
            Toast.makeText(activity(), "AppSettings Updated Successfully", Toast.LENGTH_SHORT).show();
            activity().finish();
        }
    }

    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
        if(condition.equals(ApiClient.GETSETTINGSDATA_BY_USERID)){
            //
        } else if(condition.equals(ApiClient.APP_SETTING_CREATE_URL)){
            //showSnackBar(apiResponseModel.message,2);
            Toast.makeText(activity(), "AppSettings saved failed", Toast.LENGTH_SHORT).show();
        } else if(condition.equals(ApiClient.UPDATE_APP_SETTINGS)){

        }
    }
}
