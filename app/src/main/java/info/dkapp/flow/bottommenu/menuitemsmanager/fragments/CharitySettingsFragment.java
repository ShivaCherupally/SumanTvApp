package info.dkapp.flow.bottommenu.menuitemsmanager.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import info.dkapp.flow.bottommenu.activity.HelperActivity;
import info.dkapp.flow.retrofitcall.*;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import info.dkapp.flow.bottommenu.interfaces.fragments.IFragment;

import info.dkapp.flow.R;
import info.dkapp.flow.menu_list.Settings.Charity.CharityModelClass;
import info.dkapp.flow.menu_list.Settings.Charity.PayoutSetting;
import info.dkapp.flow.userflow.Util.Common;
import retrofit2.Call;

public class CharitySettingsFragment extends Fragment implements View.OnClickListener ,IFragment,IApiListener {
    RelativeLayout charitylayout;
    androidx.appcompat.widget.Toolbar toolbar_back;
    Button backbtn;
    TextView toolbarheadertitle;
    private EditText mCharity_Name, mCharity_Address, mCharity_Registration_Code, mCharity_About;
    private Spinner mCharity_Spinner;
    private ProgressDialog progressDialog;
    private ApiInterface apiInterface;
    private ArrayList<CharityModelClass> charityModelClassArrayList = new ArrayList<>();
    private ArrayList<PayoutSetting> charityPayoutSettings = new ArrayList<>();
    private ArrayList<String> contacts = new ArrayList<>();
    private EditText mAudio, mVideo, mEcommerce, mChat;
    CharityModelClass charityModelClass1 = new CharityModelClass();
    PayoutSetting payoutSetting1 = new PayoutSetting();
    private Button update_charity_button, contactus_button;
    private String audio_text = "", video_text = "", chat_text = "", ecommerece_text = "";
    String mCharitY_id;
    private String mCharity_type;
    private int spinner_selection;
    IApiListener iApiListener;

    public static CharitySettingsFragment newInstance(String param1, String param2) {
        CharitySettingsFragment fragment = new CharitySettingsFragment();

        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_charity_settings, null);
      //  Common.headerScreenatAllScreens(activity(), activity().getIntent().getExtras().getString("HEADER_TITLE"), activity());
        iApiListener = this;
        initializeViews(root);
        getCharitySettings();

        update_charity_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCharity_type != null) {
                    if (mCharity_type.equals("Select Charity")) {
                        Toast.makeText(activity(), "Please Select Charity", Toast.LENGTH_SHORT).show();
                    } else {
                        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                        JSONObject params = new JSONObject();
                        try {
                            params.put("video", mVideo.getText().toString());
                            params.put("audio", mAudio.getText().toString());
                            params.put("chat", mChat.getText().toString());
                            params.put("ecommerce", mEcommerce.getText().toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        String url = ApiClient.UPDATE_CHARITY + mCharitY_id + "/"
                                + SessionManager.userLogin.userId;
                        Call<ApiResponseModel> call = apiInterface.PUT(url,RequestBody.create(MediaType.parse("application/json; charset=utf-8"),params.toString()));
                        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(),call,ApiClient.UPDATE_CHARITY,true,iApiListener,false));
                    }
                } else {
//                    Toast.makeText(activity(), "Please Select Charity", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mCharity_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                showBtnSave(false);
                if (position != 0) {
                    ((HelperActivity) activity()).showBtnEdit(true);
                    spinner_selection = position;
                    charityModelClass1 = charityModelClassArrayList.get(position - 1);
                    payoutSetting1 = charityPayoutSettings.get(position - 1);
                    String mem = payoutSetting1.getMemberId();
                    String userid = SessionManager.userLogin.userId;
                    mCharitY_id = charityModelClass1.getId();
                    mCharity_type = charityModelClass1.getCharityName();
                    mCharity_Name.setText(charityModelClass1.getCharityName());
                    mCharity_Address.setText(charityModelClass1.getAddress());
                    mCharity_Registration_Code.setText(charityModelClass1.getCharityRegistrationID());
                    mCharity_About.setText(charityModelClass1.getCharityTrustDesc());

                    if (mem != null) {
                        if (mem.equals(userid)) {
                            if (payoutSetting1.getVideo() != null) {
                                mVideo.setText(String.valueOf(payoutSetting1.getVideo()));
                                video_text = String.valueOf(payoutSetting1.getVideo());
                            }
                            if (payoutSetting1.getChat() != null) {
                                mChat.setText(String.valueOf(payoutSetting1.getChat()));
                                chat_text = String.valueOf(payoutSetting1.getChat());
                            }
                            if (payoutSetting1.getEcommerce() != null) {
                                mEcommerce.setText(String.valueOf(payoutSetting1.getEcommerce()));
                                ecommerece_text = String.valueOf(payoutSetting1.getEcommerce());
                            }
                            if (payoutSetting1.getAudio() != null) {
                                mAudio.setText(String.valueOf(payoutSetting1.getAudio()));
                                audio_text = String.valueOf(payoutSetting1.getAudio());
                            }
                        }
                    } else {
                        mVideo.setText("0");
                        mChat.setText("0");
                        mEcommerce.setText("0");
                        mAudio.setText("0");
                        video_text = "";
                        chat_text = "";
                        ecommerece_text = "";
                        audio_text = "";

                    }
                } else {
//                    Toast.makeText(activity(), "Please Select Charity", Toast.LENGTH_SHORT).show();
                    ((HelperActivity) activity()).showBtnEdit(false);
                    mCharity_type = "Select Charity Organization";
                    mVideo.setText("0");
                    mChat.setText("0");
                    mEcommerce.setText("0");
                    mAudio.setText("0");
                    video_text = "";
                    chat_text = "";
                    ecommerece_text = "";
                    audio_text = "";
                    mCharity_Name.setText("");
                    mCharity_Address.setText("");
                    mCharity_Registration_Code.setText("");
                    mCharity_About.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        return root;
    }

    private void enableEditText(Boolean show){
        mChat.setEnabled(show);
        mVideo.setEnabled(show);
        mEcommerce.setEnabled(show);
        mAudio.setEnabled(show);
    }

    public void showBtnSave(Boolean show){
        enableEditText(show);
        if(show){
            update_charity_button.setVisibility(View.VISIBLE);
        } else {
            update_charity_button.setVisibility(View.GONE);
        }
    }

    public Boolean isBtnSaveEnabled(){
        return update_charity_button.getVisibility() == View.VISIBLE;
    }

    private void getCharitySettings() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.getCharitySettings(ApiClient.GET_CHARITY + "/");
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(),call,ApiClient.GET_CHARITY,true,iApiListener,false));
    }

    private void UpdatedData() {
        charityModelClassArrayList.clear();
        charityPayoutSettings.clear();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.getCharitySettings(ApiClient.GET_CHARITY + "/");
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(),call,ApiClient.GET_CHARITY+"1",true,iApiListener,false));
    }


    private void initializeViews(View root) {

        mCharity_Spinner = (Spinner) root.findViewById(R.id.charity_spinner);
        mCharity_Name = (EditText) root.findViewById(R.id.charity_name);
        mCharity_Address = (EditText) root.findViewById(R.id.charity_address);
        mCharity_Registration_Code = (EditText) root.findViewById(R.id.charity_registration_code);
        mCharity_About = (EditText) root.findViewById(R.id.charity_about);
        mAudio = (EditText) root.findViewById(R.id.audio_calls);
        mChat = (EditText) root.findViewById(R.id.chat_msg);
        mVideo = (EditText) root.findViewById(R.id.video_call);
        mEcommerce = (EditText) root.findViewById(R.id.eCommerce_text);
        update_charity_button = (Button) root.findViewById(R.id.update_charity_button);
        contactus_button = (Button) root.findViewById(R.id.contactus_button);

        update_charity_button.setVisibility(View.GONE);
        mCharity_Name.setEnabled(false);
        mCharity_Address.setEnabled(false);
        mCharity_Registration_Code.setEnabled(false);
        mCharity_About.setEnabled(false);

        contactus_button.setOnClickListener(this);


    }

//    private void commonHeader(String headertitle) {
//        toolbar_back = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_back_main);
//        setSupportActionBar(toolbar_back);
//        try {
//            backbtn = (Button) root.findViewById(R.id.backbtntollbar);
//            backbtn.setVisibility(View.VISIBLE);
//            toolbarheadertitle = (TextView) root.findViewById(R.id.toolbar_back_title);
//            toolbarheadertitle.setText(headertitle + "");
//            backbtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    finish();
//                }
//            });
//        } catch (Exception e) {
//
//        }
//    }

   
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.contactus_button) {
            Intent mSeocndintent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.celebkonect.com/"));
            startActivity(mSeocndintent);
        }
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
        if(condition.equals(ApiClient.GET_CHARITY)) {
            try {
                /*Type type = new TypeToken<ChangePasswordData>() {}.getType();
                ChangePasswordData changePasswordData = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);*/
                contacts.add("Select Charity");
                JSONArray notificationsArray = new JSONArray(new Gson().toJson(apiResponseModel.data));
                for (int i = 0; i < notificationsArray.length(); i++) {
                    JSONObject getCharitySettings = notificationsArray.getJSONObject(i);
                    CharityModelClass charityModelClass = new CharityModelClass();
                    PayoutSetting payoutSetting = new PayoutSetting();
                    charityModelClass.setId(getCharitySettings.getString("_id"));
                    charityModelClass.setCharityName(getCharitySettings.getString("charityName"));
                    charityModelClass.setCreatedAt(getCharitySettings.getString("createdAt"));
                    charityModelClass.setAddress(getCharitySettings.getString("address"));
                    charityModelClass.setCharityRegistrationID(getCharitySettings.getString("charityRegistrationID"));
                    charityModelClass.setCharityTrustDesc(getCharitySettings.getString("charityTrustDesc"));

                    contacts.add(getCharitySettings.getString("charityName"));

                    JSONArray payoutSettings = getCharitySettings.getJSONArray("payoutSettings");
                    for (int j = 0; j < payoutSettings.length(); j++) {
                        JSONObject getCharityPay = payoutSettings.getJSONObject(j);
                        if (getCharityPay.has("memberId")) {
                            if (SessionManager.userLogin.userId.equals(getCharityPay.getString("memberId"))) {
                                payoutSetting.setMemberId(getCharityPay.getString("memberId"));
                                if (getCharityPay.has("audio")) {
                                    if (getCharityPay.getString("audio") != null && !getCharityPay.getString("audio").isEmpty()) {
                                        payoutSetting.setAudio(getCharityPay.optInt("audio",0));
                                    }
                                }
                                if (getCharityPay.has("video")) {
                                    if (getCharityPay.getString("video") != null && !getCharityPay.getString("video").isEmpty()) {
                                        payoutSetting.setVideo(getCharityPay.optInt("video",0));
                                    }
                                }
                                if (getCharityPay.has("chat")) {
                                    if (getCharityPay.getString("chat") != null && !getCharityPay.getString("chat").isEmpty()) {
                                        payoutSetting.setChat(getCharityPay.optInt("chat",0));
                                    }
                                }
                                if (getCharityPay.has("ecommerce")) {
                                    if (getCharityPay.getString("ecommerce") != null && !getCharityPay.getString("ecommerce").isEmpty()) {
                                        payoutSetting.setEcommerce(getCharityPay.optInt("ecommerce",0));
                                    }
                                }

                            }
                        }
                    }
                    charityModelClassArrayList.add(charityModelClass);
                    charityPayoutSettings.add(payoutSetting);

                }
                ArrayAdapter<String> languageAdapter = new ArrayAdapter<String>(activity(),
                        R.layout.simple_spinner_dropdown_item, contacts);
                languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mCharity_Spinner.setAdapter(languageAdapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if(condition.equals(ApiClient.GET_CHARITY+"1")) {
            try {
                /*Type type = new TypeToken<ChangePasswordData>() {}.getType();
                ChangePasswordData changePasswordData = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);*/
                JSONArray notificationsArray = new JSONArray(new Gson().toJson(apiResponseModel.data));
                for (int i = 0; i < notificationsArray.length(); i++) {
                    JSONObject getCharitySettings = notificationsArray.getJSONObject(i);
                    CharityModelClass charityModelClass = new CharityModelClass();
                    PayoutSetting payoutSetting = new PayoutSetting();
                    charityModelClass.setId(getCharitySettings.getString("_id"));
                    charityModelClass.setCharityName(getCharitySettings.getString("charityName"));
                    charityModelClass.setCreatedAt(getCharitySettings.getString("createdAt"));
                    charityModelClass.setAddress(getCharitySettings.getString("address"));
                    charityModelClass.setCharityRegistrationID(getCharitySettings.getString("charityRegistrationID"));
                    charityModelClass.setCharityTrustDesc(getCharitySettings.getString("charityTrustDesc"));

                    JSONArray payoutSettings = getCharitySettings.getJSONArray("payoutSettings");
                    for (int j = 0; j < payoutSettings.length(); j++) {
                        JSONObject getCharityPay = payoutSettings.getJSONObject(j);
                        if (getCharityPay.has("memberId")) {
                            if (SessionManager.userLogin.userId.equals(getCharityPay.getString("memberId"))) {
                                payoutSetting.setMemberId(getCharityPay.getString("memberId"));
                                if (getCharityPay.has("audio")) {
                                    if (getCharityPay.getString("audio") != null && !getCharityPay.getString("audio").isEmpty()) {
                                        payoutSetting.setAudio(getCharityPay.optInt("audio",0));
                                    }
                                }
                                if (getCharityPay.has("video")) {
                                    if (getCharityPay.getString("video") != null && !getCharityPay.getString("video").isEmpty()) {
                                        payoutSetting.setVideo(getCharityPay.optInt("video",0));
                                    }
                                }
                                if (getCharityPay.has("chat")) {
                                    if (getCharityPay.getString("chat") != null && !getCharityPay.getString("chat").isEmpty()) {
                                        payoutSetting.setChat(getCharityPay.optInt("chat",0));
                                    }
                                }
                                if (getCharityPay.has("ecommerce")) {
                                    if (getCharityPay.getString("ecommerce") != null && !getCharityPay.getString("ecommerce").isEmpty()) {
                                        payoutSetting.setEcommerce(getCharityPay.optInt("ecommerce",0));
                                    }
                                }

                            }
                        }
                    }
                    charityModelClassArrayList.add(charityModelClass);
                    charityPayoutSettings.add(payoutSetting);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if(condition.equals(ApiClient.UPDATE_CHARITY)){
            Toast.makeText(activity(), apiResponseModel.message, Toast.LENGTH_SHORT).show();
            //UpdatedData();
            activity().finish();
        }
    }

    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
        if(condition.equals(ApiClient.GET_CHARITY)){
            //Toast.makeText(activity(), "Fail to change password", Toast.LENGTH_SHORT).show();
        } else if(condition.equals(ApiClient.GET_CHARITY+"1")){
            //Toast.makeText(activity(), "Fail to change password", Toast.LENGTH_SHORT).show();
        } else if(condition.equals(ApiClient.UPDATE_CHARITY)){
            Toast.makeText(activity(), "Fail to reach server", Toast.LENGTH_SHORT).show();
        }
    }
    
}
