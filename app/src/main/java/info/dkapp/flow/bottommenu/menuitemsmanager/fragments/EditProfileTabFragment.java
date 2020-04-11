//package info.celebkonnect.flow.bottommenu.menuitemsmanager.fragments;
//
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.graphics.PorterDuff;
//import android.graphics.drawable.ColorDrawable;
//import android.location.Location;
//import android.net.Uri;
//import android.os.Bundle;
//import androidx.annotation.Nullable;
//import androidx.coordinatorlayout.widget.CoordinatorLayout;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.DefaultItemAnimator;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import android.text.Editable;
//import android.text.SpannableString;
//import android.text.TextUtils;
//import android.text.TextWatcher;
//import android.text.style.UnderlineSpan;
//import android.util.Log;
//import android.view.*;
//import android.widget.*;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import info.celebkonnect.flow.bottommenu.activity.HelperActivity;
//import info.celebkonnect.flow.bottommenu.generic.EmptyDataAdapter;
//import info.celebkonnect.flow.bottommenu.interfaces.fragments.IFragment;
//import info.celebkonnect.flow.bottommenu.models.EditProfileUserDetails;
//import info.celebkonnect.flow.celebflow.EditProfileActivity.GetProfileData;
//import info.celebkonnect.flow.celebflow.EditProfileActivity.IndustryAndProfessionData;
//import info.celebkonnect.flow.celebflow.R;
//import info.celebkonnect.flow.celebflow.constants.Constants;
//import info.celebkonnect.flow.celebflow.countrydata.CountryAdapter;
//import info.celebkonnect.flow.celebflow.countrydata.CountryData;
//import info.celebkonnect.flow.celebflow.interfaces.ICountryAdapter;
//import info.celebkonnect.flow.celebflow.modelData.SignUpConditions;
//import info.celebkonnect.flow.managermodule.switchingprofiles.CommonAccessPermissionOfCeleb;
//import info.celebkonnect.flow.retrofitcall.*;
//import info.celebkonnect.flow.userflow.Util.Common;
//import info.celebkonnect.flow.utils.UtilityNew;
//import info.celebkonnect.flow.utils.internetchecker.InternetSpeedChecker;
//import okhttp3.MediaType;
//import okhttp3.MultipartBody;
//import okhttp3.RequestBody;
//import org.json.JSONException;
//import org.json.JSONObject;
//import retrofit2.Call;
//
//import java.io.File;
//import java.lang.reflect.Type;
//import java.text.DecimalFormat;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import static info.celebkonnect.flow.retrofitcall.SessionManager.KEY_PROFILE_PIC;
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class EditProfileTabFragment extends Fragment implements IFragment, View.OnClickListener, IApiListener, ICountryAdapter {
//
//    private CoordinatorLayout coordinatorLayout;
//    private TextView textViewManagerProfile, mCredits_Display;
//    private EditText user_name, et_Firstname, et_lastname, mEmail,
//            mAboutMe, edt_phone;
//    private Button edit_add_credits;
//    private Spinner proffesionspinner, industrySpn;
//    private LinearLayout industryProfessionLayout;
//    ArrayList<IndustryAndProfessionData> _industryAndAll;
//    ArrayList<String> _industryAndAllData;
//    ArrayList<String> _professiondata;
//    HashMap<String, List<String>> professiondata;
//    boolean industrySelected = false;
//    boolean professionSelection = false;
//    boolean directProfession = false;
//    String userTitleSelectionitem = "";
//    String userIndustrySelectionitem = "";
//    String userProfessionSelectionitem = "";
//    String aboutMeStr = "";
//    String firstNameInServer = "";
//    String lastNameInServer = "";
//    String aboutMeInServer = "";
//    LinearLayout profileLayout, creditBalanceLayout;
//    private float image_ratio;
//    View view_pro, view_industry;
//
//    ApiInterface apiInterface;
//    String genderSelection = "";
//    // private ProgressDialog progressDialog;
//    String selectioAddress = "";
//    String isCeleb = "";
//    private static final int MY_PERMISSIONS_REQUEST_ACCOUNTS = 1;
//    IApiListener iApiListener;
//    //Camera thing
//    private static final int IMG_SELECT = 777;
//    //    ImageView iv_user_pic;
//
//    private static final int TIME_DELAY = 2000;
//    private static long back_pressed;
//
//    private static final String DEMO_PHOTO_PATH = "MyDemoPhotoDir";
//    String headertitle;
//    boolean isEmailVerified = false, isMobileVerified = false;
//    String EmailID = "", MobileNumber = "";
//    ICountryAdapter iCountryAdapter;
//    String loginType = "", role = "";
//
//
////    private String chatserver = ApiClient.SERVICE_CALL_SOCKET_PORT; //ApiClient.chaturl;// "http://192.168.3.29:3000"; //192.168.3.29/"; ApiClient.chaturl;
//
//
//    boolean directAccess = false;
//    boolean genderdirectAccess = false;
//    boolean loadingDirect = false;
//
//
//    private String credits, referal_credits;
//    Context mContext;
//
//    private TextView mReferal_Credits;
//    private LinearLayout mReferal_Layout;
//
//    //    private LinearLayout mIsIndustry_Layout, mIsProfession_Layout;
////    private String isIndustry = "";
//
//    EditProfileUserDetails editProfileUserDetails;
//    ProgressBar progfileProgress;
//    private String dndisChecked = "false";
//    Button verifyemailBtn, verifyMobileBtn;
//    ImageView emailVerifiedimg, verify_mblimage, imageview_arrow_spinner_industry, imageview_arrow_spinner_proff;
//    boolean verifyButtonClick = false;
//    Bundle arguments;
//    private Uri uri;
//    Boolean reload = false;
//    Double creditBalUpdated, refCreditsUpdated;
//    TextView countryCodetv;
//    boolean donotActionPerform = false;
//    boolean isUsingMobile = false;
//    String mobileOrEmailID = "";
//    ProgressBar progressBarLocation;
//    Location currentLocation = null;
//    String searchCode = "+91";
//    CountryAdapter countryAdapter;
//    ArrayList<CountryData> _countryData = new ArrayList<CountryData>();
//    Dialog country_popup_dailogGlobal;
//    ProgressBar progressCountry;
//    RecyclerView countryRecyList;
//    boolean isPopup = false;
//    private static EditProfileTabFragment instance = null;
//    Double creditBal, refCredits;
//    int minlenght,
//            maxlength;
//
//
//    public static EditProfileTabFragment getInstance() {
//        return instance;
//    }
//
//
//    public EditProfileTabFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        instance = this;
//        iApiListener = this;
//        iCountryAdapter = this;
//    }
//
//    public static EditProfileTabFragment newInstance(EditProfileUserDetails editProfileUserDetails, String title, String newReg, boolean reload) {
//        EditProfileTabFragment fragment = new EditProfileTabFragment();
//        Bundle args = new Bundle();
//        args.putString("HEADER_TITLE", title);
//        args.putString("NEW_REG", newReg);
//        args.putBoolean("reload", reload);
//        args.putParcelable("editProfileUserDetails", editProfileUserDetails);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View root = inflater.inflate(R.layout.fragment_edit_profile_tab, container, false);
//        initializeViews(root);
//
//
//        try {
//            if (Common.isCelebAndManager(activity())) {
//                industryProfessionLayout.setVisibility(View.VISIBLE);
//                textViewManagerProfile.setVisibility(View.VISIBLE);
//                getIndustrydata();
//
////                mSave.setText("Proceed");
//            } else if (Common.isCelebStatus(activity())) {
//                industryProfessionLayout.setVisibility(View.VISIBLE);
//                textViewManagerProfile.setVisibility(View.GONE);
//                getIndustrydata();
////                mSave.setText("Proceed");
//            } else if (Common.isManagerStatus(activity())) {
//                industryProfessionLayout.setVisibility(View.GONE);
//                textViewManagerProfile.setVisibility(View.VISIBLE);
//                //getprofileDataWithEmailOrMobile();
//                appenddata(editProfileUserDetails);
//            } else {
//                textViewManagerProfile.setVisibility(View.GONE);
//                industryProfessionLayout.setVisibility(View.GONE);
//                //  getprofileDataWithEmailOrMobile();
//                appenddata(editProfileUserDetails);
//            }
//        } catch (Exception e) {
//            //getprofileDataWithEmailOrMobile();
//            appenddata(editProfileUserDetails);
//        }
//
//        et_Firstname.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                et_Firstname.getBackground().setColorFilter(getResources().getColor(R.color.editTexttint_color),
//                        PorterDuff.Mode.SRC_ATOP);
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
////                et_Firstname.getBackground().setColorFilter(getResources().getColor(R.color.skyblueNew),
////                        PorterDuff.Mode.SRC_ATOP);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if (et_Firstname.getText().toString().startsWith(" ")) {
//                    et_Firstname.setText("");
//                }
//                String firstName = editable.toString();
//                if (firstName.length() > 23) {
//                    Toast.makeText(activity(), "Your First Name has reached maximum limit of 24 characters", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//        });
//        et_lastname.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
////                et_lastname.getBackground().setColorFilter(getResources().getColor(R.color.skyblueNew),
////                        PorterDuff.Mode.SRC_ATOP);
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                et_Firstname.getBackground().setColorFilter(getResources().getColor(R.color.editTexttint_color),
//                        PorterDuff.Mode.SRC_ATOP);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if (et_lastname.getText().toString().startsWith(" ")) {
//                    et_lastname.setText("");
//                }
//                String lastName = editable.toString();
//                if (lastName.length() > 23) {
//                    Toast.makeText(activity(), "Your Last Name has reached maximum limit of 24 characters", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
//        edt_phone.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                String number = editable.toString();
//                if (!MobileNumber.equals(number)) {
//                    verifyMobileBtn.setVisibility(View.VISIBLE);
//                    verify_mblimage.setVisibility(View.GONE);
//                }else {
//                    verifyMobileBtn.setVisibility(View.GONE);
//                    verify_mblimage.setVisibility(View.VISIBLE);
//                }
//            }
//        });
//        mEmail.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                String email = editable.toString();
//                if (!EmailID.equals(email)) {
//                    verifyemailBtn.setVisibility(View.VISIBLE);
//                    emailVerifiedimg.setVisibility(View.GONE);
//                }else {
//                    verifyemailBtn.setVisibility(View.GONE);
//                    emailVerifiedimg.setVisibility(View.VISIBLE);
//                }
//            }
//        });
//
//        return root;
//    }
//
//    private void initializeViews(View root) {
//
//        editProfileUserDetails = getArguments().getParcelable("editProfileUserDetails");
//        coordinatorLayout = root.findViewById(R.id.coordinatorLayout);
//        textViewManagerProfile = root.findViewById(R.id.textViewManagerProfile);
//        SpannableString content = new SpannableString("Manager Profile");
//        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
//        textViewManagerProfile.setText(content);
//        profileLayout = root.findViewById(R.id.profileLayout);
//        creditBalanceLayout = root.findViewById(R.id.creditBalanceLayout);
//        mCredits_Display = root.findViewById(R.id.credits_display);
//        mReferal_Credits = root.findViewById(R.id.profile_referal_credits);
//        user_name = root.findViewById(R.id.user_name);
//        et_Firstname = root.findViewById(R.id.et_firstname);
//        et_lastname = root.findViewById(R.id.et_lastname);
//        mEmail = root.findViewById(R.id.edt_email);
//        edt_phone = root.findViewById(R.id.edt_phone);
//        mAboutMe = root.findViewById(R.id.edt_aboutme);
//        imageview_arrow_spinner_proff = root.findViewById(R.id.imageview_arrow_spinner_proff);
//        imageview_arrow_spinner_industry = root.findViewById(R.id.imageview_arrow_spinner_industry);
//        edit_add_credits = root.findViewById(R.id.edit_profile_add_credits);
//
//        proffesionspinner = root.findViewById(R.id.proffesionspinner);
//        industrySpn = root.findViewById(R.id.industrySpn);
//        industryProfessionLayout = root.findViewById(R.id.industryProfessionLayout);
//        verifyemailBtn = root.findViewById(R.id.verify_btn);
//        verifyMobileBtn = root.findViewById(R.id.verifyMobileBtn);
//        emailVerifiedimg = root.findViewById(R.id.verify_image);
//        verify_mblimage = root.findViewById(R.id.verify_mblimage);
//        view_industry = root.findViewById(R.id.view_industry);
//        view_pro = root.findViewById(R.id.view_pro);
//        countryCodetv = root.findViewById(R.id.countryCode);
//
//
//        //Check new
////        appenddata(editProfileUserDetails);
//
//        disableViews();
//
//        initializeActions();
//        edit_add_credits.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (CommonAccessPermissionOfCeleb.creditsPermissonAvailabilty(activity(),
//                        false, false)) {
//                    if (mandatatoryValidation()) {
//                        navigateToCreditRechargePage();
//                    }
//                }
//
//            }
//        });
//
//
//        verifyemailBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                resendEmailData(true);
//            }
//        });
//        verifyMobileBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!edt_phone.getText().toString().trim().isEmpty()) {
//                    if (Common.getInstance().isValidMobilelenght(edt_phone.getText().toString(),minlenght,maxlength)) {
//                        resendEmailData(false);
//                    } else {
//                        Toast.makeText(activity(), "Looks like your phone number may be incorrect", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//            }
//        });
//        //set country code dialog box
//        countryCodetv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                countryListPopup();
//            }
//        });
//        getAllCountryListFromServer(false);
//
//
//    }
//
//
//    @SuppressLint("NewApi")
//    private void initializeActions() {
//
//        textViewManagerProfile.setOnClickListener(this);
//
////        for (int i = 0; i < profileLayout.getChildCount(); i++) {
////            View child = profileLayout.getChildAt(i);
////            child.setEnabled(false);
////
////            proffesionspinner.setEnabled(false);
////            industrySpn.setEnabled(false);
////
////        }
//
//        //AIzaSyD_0hBk0O_ugFOv_gODKZo8Njq-k6Gzj3I
//        arguments = getArguments();
//        if (arguments.getString("HEADER_TITLE") != null) {
//            if (arguments.getString("HEADER_TITLE").equals("Profile")) {
//
//            }
//        } else {
//            //   subheaderTitle.setText("My Profile");
//        }
//
//        if (arguments.getString("NEW_REG") != null && !arguments.getString("NEW_REG").isEmpty()) {
//            if (arguments.getString("NEW_REG").equals("TRUE")) {
//                editClick();
//            }
//        }
//
//        reload = arguments.getBoolean("reload");
//
//
//    }
//
//
//    private void editClick() {
////        industrySpn.getBackground().setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.ADD);
////        proffesionspinner.getBackground().setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.ADD);
////        view_pro.setVisibility(View.GONE);
////        view_industry.setVisibility(View.GONE);
//        creditBalanceLayout.setVisibility(View.GONE);
//        mAboutMe.setEnabled(true);
//        verifyMobileBtn.setEnabled(true);
//        verifyemailBtn.setEnabled(true);
//        mEmail.setEnabled(true);
//        edt_phone.setEnabled(true);
//        countryCodetv.setEnabled(true);
//        Common.getInstance().enableDisableView(profileLayout, true, getActivity());
//        imageview_arrow_spinner_proff.setVisibility(View.VISIBLE);
//        imageview_arrow_spinner_industry.setVisibility(View.VISIBLE);
//        if (arguments.getString("NEW_REG") != null && !arguments.getString("NEW_REG").isEmpty()) {
//            if (arguments.getString("NEW_REG").equals("TRUE")) {
//
//            }
//
//        }
//        if (isMobileVerified && isEmailVerified) {
//            /*mEmail.setEnabled(false);
//            edt_phone.setEnabled(false);
//            countryCodetv.setEnabled(false);*/
//            countryCodetv.setTextColor(activity().getResources().getColor(R.color.approved));
//        } else if (isEmailVerified) {
///*            mEmail.setEnabled(false);
//            edt_phone.setEnabled(true);
//            countryCodetv.setEnabled(true);*/
//            countryCodetv.setTextColor(activity().getResources().getColor(R.color.black));
//        } else if (isMobileVerified) {
///*            mEmail.setEnabled(true);
//            edt_phone.setEnabled(false);
//            countryCodetv.setEnabled(false);*/
//            countryCodetv.setTextColor(activity().getResources().getColor(R.color.approved));
//        } else {
///*            mEmail.setEnabled(false);
//            edt_phone.setEnabled(false);
//            countryCodetv.setEnabled(false);*/
//            countryCodetv.setTextColor(activity().getResources().getColor(R.color.approved));
//        }
//
//
//    }
//
//
//    private void resendEmailData(boolean isEmail) {
//        //Uday new sign up
//       /* try {
//            JSONObject entryData = new JSONObject();
//            entryData.put("mode_ids", "email");
//            entryData.put("event", "resendEmail");
//            entryData.put("from_addr", "admin@celebkonect.com");
//            entryData.put("to_addr", mEmail.getText().toString());
//            resendOtpViaEmailInServer(entryData);
//        } catch (Exception e) {
//        }*/
//        JSONObject jsonObject = new JSONObject();
//        if (isEmail) {
//            if (!Common.isValidEmail(mEmail.getText().toString())) {
//                Common.getInstance().showSweetAlertWarning(activity(), "CelebKonect", "Please enter valid \nEmail ID");
//                return;
//            }
//
//            isUsingMobile = false;
//            mobileOrEmailID = mEmail.getText().toString().trim();
//
//            try {
//                jsonObject.put("medium", "email");
//                jsonObject.put("email", mEmail.getText().toString().trim());
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//
//        } else {
//            if (!Common.isValidMobile(edt_phone.getText().toString())) {
//                Common.getInstance().showSweetAlertWarning(activity(), "CelebKonect", "Please enter valid \nPhone Number");
//                return;
//            }
//
//            isUsingMobile = true;
//            mobileOrEmailID = edt_phone.getText().toString().trim();
//
//            try {
//                jsonObject.put("medium", "mobile");
//                jsonObject.put("mobileNumber", edt_phone.getText().toString().trim());
//                jsonObject.put("country", searchCode);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//        try {
//            jsonObject.put("mode", "getOTP");
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        getVerifyRegisterOTPJSON(jsonObject, "getOTP", "null");
//    }
//
//    private void resendOtpViaEmailInServer(JSONObject jsonObject) {
//        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
//        Call<ApiResponseModel> call = apiInterface.POST(ApiClient.RESEND_EMAIL, requestBody);
//        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, ApiClient.RESEND_EMAIL, true, iApiListener, false));
//    }
//
//    private void cancelUpdate() {
//        creditBalanceLayout.setVisibility(View.VISIBLE);
//        mAboutMe.setEnabled(false);
//        verifyMobileBtn.setEnabled(false);
//        verifyemailBtn.setEnabled(false);
//        //  Common.getInstance().enableDisableView(profileLayout,false,activity());
////         user_name.setTextColor(activity().getResources().getColor(R.color.black));
////        edt_phone.setTextColor(activity().getResources().getColor(R.color.black));
////        edt_phone.setTextColor(activity().getResources().getColor(R.color.black));
//
////        et_Firstname.setClickable(false);
////        et_Firstname.setEnabled(false);
////        et_lastname.setClickable(false);
////        et_lastname.setEnabled(false);
////        proffesionspinner.setEnabled(false);
////        industrySpn.setEnabled(false);
////        mAboutMe.setClickable(false);
////        mAboutMe.setEnabled(false);
////        professiontxt.setEnabled(false);
////        professiontxt.setClickable(false);
//
////        ((HelperActivity) getGlobalActivity()).showUpdate("Edit");
//    }
//
//
//    private boolean mandatatoryValidation() {
//        boolean isValidate = true;
//
//        if (et_Firstname.getText().toString().length() == 0) {
//            isValidate = false;
//            Toast.makeText(getActivity(), "Please update your profile information to continue", Toast.LENGTH_SHORT).show();
//            return isValidate;
//        } else if (et_lastname.getText().toString().length() == 0) {
//            Toast.makeText(getActivity(), "Please update your profile information to continue", Toast.LENGTH_SHORT).show();
//            isValidate = false;
//            return isValidate;
//        } else if (firstNameInServer.length() == 0) {
//            Toast.makeText(getActivity(), "Please update your profile information to continue", Toast.LENGTH_SHORT).show();
//            isValidate = false;
//            return isValidate;
//        } else if (lastNameInServer.length() == 0) {
//            Toast.makeText(getActivity(), "Please update your profile information to continue", Toast.LENGTH_SHORT).show();
//            isValidate = false;
//            return isValidate;
//        }
//
//        return isValidate;
//    }
//
//    private boolean mandatatoryValidationForLogin() {
//        boolean isValidate = true;
//
//        if (et_Firstname.getText().toString().length() == 0) {
//            isValidate = false;
//            Toast.makeText(getActivity(), "Please update your profile information to continue", Toast.LENGTH_SHORT).show();
//            return isValidate;
//        } else if (et_lastname.getText().toString().length() == 0) {
//            Toast.makeText(getActivity(), "Please update your profile information to continue", Toast.LENGTH_SHORT).show();
//            isValidate = false;
//            return isValidate;
//        }
//        if (Common.isCelebAndManager(activity())) {
//            if (userIndustrySelectionitem != null && !userIndustrySelectionitem.isEmpty() &&
//                    !userIndustrySelectionitem.equals("Select Industry")) {
//
//            } else {
//                Toast.makeText(getActivity(), "Pick a favorite Industry", Toast.LENGTH_SHORT).show();
//                isValidate = false;
//                return isValidate;
//            }
//
//            if (userProfessionSelectionitem == null && userProfessionSelectionitem.isEmpty() &&
//                    userProfessionSelectionitem.equals("Select Industry")) {
//                Toast.makeText(getActivity(), "Pick a favorite Industry", Toast.LENGTH_SHORT).show();
//                isValidate = false;
//                return isValidate;
//            }
//
//        }
//        if (Common.isCelebStatus(activity())) {
//            if (userIndustrySelectionitem == null && userIndustrySelectionitem.isEmpty() &&
//                    userIndustrySelectionitem.equals("Select Industry")) {
//                Toast.makeText(getActivity(), "Pick a favorite Industry", Toast.LENGTH_SHORT).show();
//                isValidate = false;
//                return isValidate;
//            } else if (userProfessionSelectionitem == null && userProfessionSelectionitem.isEmpty() &&
//                    userProfessionSelectionitem.equals("Select Industry")) {
//                Toast.makeText(getActivity(), "Pick a favorite Industry", Toast.LENGTH_SHORT).show();
//                isValidate = false;
//                return isValidate;
//            }
//        }
//
//        return isValidate;
//    }
//
//    private void navigateToCreditRechargePage() {
//
////        Intent intent = new Intent(getGlobalActivity(), CreditRechargeActvity.class);
////        intent.putExtra("className", "EditProfile");
////        startActivity(intent);
//
//        Intent intent = new Intent(activity(), HelperActivity.class);
//        intent.putExtra(Constants.FRAGMENT_TITLE, "Credits");
//        intent.putExtra("className", "EditProfile");
//        intent.putExtra(Constants.FRAGMENT_KEY, 8034);// CreditsRecharge
//        startActivity(intent);
//    }
//
//    private void disableViews() {
//        mAboutMe.setEnabled(false);
//        verifyMobileBtn.setEnabled(false);
//        verifyemailBtn.setEnabled(false);
//        user_name.setEnabled(false);
//        mEmail.setEnabled(false);
//        edt_phone.setEnabled(false);
//        countryCodetv.setEnabled(false);
//        countryCodetv.setTextColor(activity().getResources().getColor(R.color.approved));
//        user_name.setTextColor(getResources().getColor(R.color.black));
//        //user_name.setHintTextColor(getResources().getColor(R.color.black));
//        edt_phone.setTextColor(getResources().getColor(R.color.black));
//
//        mEmail.setTextColor(getResources().getColor(R.color.black));
//        et_Firstname.isCursorVisible();
//        imageview_arrow_spinner_proff.setVisibility(View.GONE);
//        imageview_arrow_spinner_industry.setVisibility(View.GONE);
//        Common.getInstance().enableDisableView(profileLayout, false, activity());
////        industrySpn.getBackground().setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
////        proffesionspinner.getBackground().setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
////        view_pro.setVisibility(View.VISIBLE);
////        view_industry.setVisibility(View.VISIBLE);
////        mEmail.setEnabled(false);
////        mEmail.setClickable(false);
////        mEmail.setCursorVisible(false);
////
////        edt_phone.setEnabled(false);
////        edt_phone.setClickable(false);
////        edt_phone.setCursorVisible(false);
//
//    }
//
//    private void getIndustrydata() {
//        _industryAndAll = new ArrayList<>();
//        _industryAndAllData = new ArrayList<>();
//        professiondata = new HashMap<>();
//        _professiondata = new ArrayList<>();
//        apiInterface = ApiClient.getClient().create(ApiInterface.class);
//        Call<ApiResponseModel> call = apiInterface.getIndustryProfession(ApiClient.GET_INDUSRTY_PROFESSION);
//        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, ApiClient.GET_INDUSRTY_PROFESSION, false, iApiListener, true));
//    }
//
//    private void settingToAdapter(final ArrayList<String> industryAndAll) {
//        try {
//
//            ArrayAdapter<String> industryAdapter = new ArrayAdapter<String>(getActivity(),
//                    R.layout.simple_spinner_dropdown_item, industryAndAll);
//            industryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            industrySpn.setAdapter(industryAdapter);
//            industrySpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    try {
//                        if (industrySelected) {
//                            if (position == 0) {
//                                userIndustrySelectionitem = "Select Industry";
//                                proffesionspinner.setVisibility(View.GONE);
////                            professiontxt.setVisibility(View.VISIBLE);
////                            professiontxt.setText("Select Profession");
//                            } else {
//                                userIndustrySelectionitem = industryAndAll.get(position);
////                            professiontxt.setVisibility(View.GONE);
//                                proffesionspinner.setVisibility(View.VISIBLE);
//                                //getProfessiondata(userIndustrySelectionitem);
//                                setProfessiondata(position - 1, false, "");
//                            }
//                        }
//                    } catch (Exception e) {
//                    }
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//                }
//            });
//
//            industrySpn.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    industrySelected = true;
//                    professionSelection = true;
//                    return false;
//                }
//            });
//
//      /*  professiontxt.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
////                industrySelected = false;
////                userIndustrySelectionitem = userIndustrySelectionitem
//                if (industrySelected) {
//                } else {
//                    professionSelection = true;
//                    professiontxt.setVisibility(View.GONE);
//                    proffesionspinner.setVisibility(View.VISIBLE);
//                    getProfessiondata(userIndustrySelectionitem);
////                    proffesionspinner.performClick();
//                }
//
//                return false;
//            }
//        });*/
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void setProfessiondata(Integer position, boolean isDataget, String professionName) {
//        if (_industryAndAll != null && _industryAndAll.size() > 0) {
//            settingProfessiondata(_industryAndAll.get(position).getProfessions(), isDataget, professionName);
//        } else {
//            Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void settingProfessiondata(final ArrayList<String> professiondata, boolean isDataget, String professionName) {
//        /* proffesionspinner.setAdapter(null);*/
//        ArrayAdapter<String> designationAdapter = new ArrayAdapter<String>(getActivity(),
//                R.layout.simple_spinner_dropdown_item, professiondata);
//        designationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        proffesionspinner.setAdapter(designationAdapter);
//
//        Integer indexprofession = -1;
//        if (professiondata != null && professiondata.size() > 0) {
//            indexprofession = professiondata.indexOf(professionName);
//        }
//
//        if (isDataget) {
//            if (indexprofession > -1) {
//                proffesionspinner.setSelection(indexprofession);
//                userProfessionSelectionitem = professiondata.get(indexprofession);
//            }
//        }
//        proffesionspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (directProfession) {
//                    userProfessionSelectionitem = professiondata.get(position);
//                } else {
//                    directProfession = true;
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//    }
//
//    private void creditAndReffferalCreditsShow(Double credits, Double referal_credits) {
//        if (referal_credits != null) {
//            mReferal_Credits.setText(referal_credits + "");
//        } else {
//            mReferal_Layout.setVisibility(View.GONE);
//        }
//        if (credits != null) {
////            double credit_double = Double.parseDouble(credits);
//            DecimalFormat twoDecimal = new DecimalFormat("0.00");
//            mCredits_Display.setText(twoDecimal.format(credits) + "");
//        } else {
//            mCredits_Display.setText("0");
//        }
//    }
//
//
//    @Override
//    public void changeTitle(String title) {
//
//    }
//
//    @Override
//    public void showSnackBar(String snackBarText, int type) {
//
//    }
//
//    @Override
//    public Activity activity() {
//        return getActivity();
//    }
//
//    @Override
//    public void onClick(View v) {
//        if (v.getId() == R.id.textViewManagerProfile) {
//            Intent intent = new Intent(activity(), HelperActivity.class);
//            intent.putExtra(Constants.FRAGMENT_TITLE, "Manager Profile");
//            intent.putExtra(Constants.FRAGMENT_KEY, 8059);// ManagerAdditionalDetails
//            startActivity(intent);
//        }
//    }
//
//    public void updateProfile(int type, String imageUrl, float image_ratio) {
//        switch (type) {
//            case 0:
//                editClick();
//                break;
//            case 1:
//                if (mandatatoryValidationForLogin()) {
//                    profileDataUploadInServer(imageUrl, image_ratio);
//                }
//                break;
//            case 2:
//                cancelUpdate();
//                break;
//            case 3:
//                Common.getInstance().showSweetAlertWarning(getContext(),
//                        "CelebKonect", getContext().getResources().getString(R.string.no_edit_per));
//                break;
//        }
//    }
//
//    private void profileDataUploadInServer(String imageUrl, float image_ratio) {
//        if (!TextUtils.isEmpty(et_Firstname.getText().toString())) {
//            if (!TextUtils.isEmpty(et_lastname.getText().toString())) {
//                if (UtilityNew.isNetworkAvailable(activity())) {
//                    if (SessionManager.userLogin.isCeleb != null) {
//                        if (SessionManager.userLogin.isCeleb) {
//                            if (userIndustrySelectionitem != null && !userIndustrySelectionitem.isEmpty() &&
//                                    !userIndustrySelectionitem.equals("Select Industry")) {
//
//                                updateCelebProfileInServer(imageUrl, image_ratio);
//
//                            } else {
//                                Toast.makeText(getActivity(), "Pick a favorite Industry", Toast.LENGTH_SHORT).show();
//                            }
//                        } else {
//                            updateMemberProfileInServer(imageUrl, image_ratio);
//                        }
//                    } else {
//                        updateMemberProfileInServer(imageUrl, image_ratio);
//                        ;
//                    }
//                } else {
//                    showSnackBar(Constants.PLEASE_CHECK_INTERNET, 2);
//                }
//            } else {
//                Common.getInstance().showSweetAlertWarning(mContext, "CelebKonect", "Please update your profile information to continue !!!");
//            }
//        } else {
////            Toast.makeText(getGlobalActivity(), "Please enter firstname", Toast.LENGTH_SHORT).show();
//            Common.getInstance().showSweetAlertWarning(mContext, "CelebKonect", "Please update your profile information to continue !!!");
//
//        }
//    }
//
//
//    private void updateMemberProfileInServer(String imageUrl, float image_ratio) {
//
//            if (!mEmail.getText().toString().isEmpty()) {
//                if (verifyemailBtn.getVisibility() == View.VISIBLE) {
//                    Toast.makeText(activity(), "Please verify Email", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//            }
//
//
//            if (!edt_phone.getText().toString().isEmpty()) {
//                if (verifyMobileBtn.getVisibility() == View.VISIBLE) {
//                    Toast.makeText(activity(), "Please verify Phone Number", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//            }
//        File fileMedia;
//        MultipartBody.Part body = null;
//       /* if (imageUrl != null && !imageUrl.equals("null") && !imageUrl.equals("imageAvailable")) {
//            fileMedia = new File(imageUrl);
//            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), fileMedia);
//            body = MultipartBody.Part.createFormData("profilePic", fileMedia.getName(), requestFile);*/
//        if(!imageUrl.isEmpty() && imageUrl != null) {
//            try {
//                fileMedia = new File(imageUrl);
//                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), fileMedia);
//                body = MultipartBody.Part.createFormData("image", fileMedia
//                        .getName(), requestFile);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("loginType", "app");
//            jsonObject.put("location", "");
//            jsonObject.put("id", SessionManager.userLogin.userId);
//            jsonObject.put("dateOfBirth", "");
//            jsonObject.put("selectionaddress", selectioAddress);
//            jsonObject.put("availableCredits", mCredits_Display.getText());
//
//            if (!isEmailVerified) {
//                if (!mEmail.getText().toString().isEmpty()) {
//                    jsonObject.put("email", mEmail.getText().toString());
//                }
//
//            }
//            if (!isMobileVerified) {
//                if (!edt_phone.getText().toString().isEmpty()) {
//                    jsonObject.put("mobileNumber", edt_phone.getText().toString());
//                    jsonObject.put("country", searchCode);
//                }
//            }
//            jsonObject.put("profession", userProfessionSelectionitem);
//            jsonObject.put("genderselection", "");
//            jsonObject.put("role", SessionManager.getInstance().getKeyValue(SessionManager.KEY_ROLE,""));
//            //  jsonObject.put("Dnd", "true");
//            jsonObject.put("industry", userIndustrySelectionitem);
//            jsonObject.put("firstName", Common.getInstance().convertFirstLetterToCapital(et_Firstname.getText().toString()));
//            jsonObject.put("lastName", Common.getInstance().convertFirstLetterToCapital(et_lastname.getText().toString()));
//            jsonObject.put("prefix", userTitleSelectionitem);
//            jsonObject.put("aboutMe", Common.convertEmojiFormat(mAboutMe));
//            String ration = String.valueOf(image_ratio);
//            jsonObject.put("imageRatio", ration);
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        String userId = SessionManager.userLogin.userId;
//        Call<ApiResponseModel> call = apiInterface.editProfileUpdate(userId, RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
//                jsonObject.toString()), body,body);
//
//        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, "UpdateProfile", true, iApiListener, true));
//    }
//
//
//    private void updateCelebProfileInServer(String imageUrl, float image_ratio) {
//        SessionManager.getInstance().setKeyValue(SessionManager.KEY_FIRST_NAME,Common.getInstance().convertFirstLetterToCapital(et_Firstname.getText().toString()));
//        SessionManager.getInstance().setKeyValue(SessionManager.KEY_LAST_NAME,Common.getInstance().convertFirstLetterToCapital(et_lastname.getText().toString()));
//
//
//        if (!isEmailVerified) {
//            if (!mEmail.getText().toString().isEmpty()) {
//                if (verifyemailBtn.getVisibility() == View.VISIBLE) {
//                    Toast.makeText(activity(), "Please verify Email", Toast.LENGTH_SHORT).show();
//                }
//                return;
//            }
//        }
//        if (!isMobileVerified) {
//            if (!edt_phone.getText().toString().isEmpty()) {
//                if (verifyMobileBtn.getVisibility() == View.VISIBLE) {
//                    Toast.makeText(activity(), "Please verify Mobile", Toast.LENGTH_SHORT).show();
//                }
//                return;
//            }
//        }
//        File fileMedia;
//        MultipartBody.Part body = null;
//      /*  if (imageUrl != null && !imageUrl.equals("null") && !imageUrl.equals("imageAvailable")) {
//            fileMedia = new File(imageUrl);
//            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), fileMedia);
//            body = MultipartBody.Part.createFormData("profilePic", fileMedia.getName(), requestFile);*/
//        if(!imageUrl.isEmpty() && imageUrl != null) {
//            try {
//                fileMedia = new File(imageUrl);
//                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), fileMedia);
//                body = MultipartBody.Part.createFormData("image", fileMedia.getName(), requestFile);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("loginType", "app");
//            jsonObject.put("location", "");
//            jsonObject.put("id", SessionManager.userLogin.userId);
//            jsonObject.put("dateOfBirth", "");
//            jsonObject.put("selectionaddress", selectioAddress);
//            jsonObject.put("availableCredits", creditBalUpdated + "");
//            if (!isEmailVerified) {
//                if (!mEmail.getText().toString().isEmpty()) {
//                    jsonObject.put("email", mEmail.getText().toString());
//                }
//            }
//            if (!isMobileVerified) {
//                if (!edt_phone.getText().toString().isEmpty()) {
//                    jsonObject.put("mobileNumber", edt_phone.getText().toString());
//                    jsonObject.put("country", searchCode);
//                }
//            }
//            jsonObject.put("profession", userProfessionSelectionitem);
//            jsonObject.put("genderselection", "");
//            jsonObject.put("role",  SessionManager.getInstance().getKeyValue(SessionManager.KEY_ROLE,""));
//            //    jsonObject.put("Dnd", "true");
//            jsonObject.put("industry", userIndustrySelectionitem);
//            jsonObject.put("firstName", Common.getInstance().convertFirstLetterToCapital(et_Firstname.getText().toString()));
//            jsonObject.put("lastName", Common.getInstance().convertFirstLetterToCapital(et_lastname.getText().toString()));
//            jsonObject.put("prefix", userTitleSelectionitem);
//            jsonObject.put("aboutMe", Common.convertEmojiFormat(mAboutMe));
//            String ration = String.valueOf(image_ratio);
//            jsonObject.put("imageRatio", ration);
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        String userId = SessionManager.userLogin.userId;
//        Call<ApiResponseModel> call = apiInterface.editProfileUpdate(userId, RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
//                jsonObject.toString()), body,body);
//
//        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, "UpdateProfile", true, iApiListener, true));
//
//
//    }
//
//    @Override
//    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
//        if (condition.equals(ApiClient.LOGINDETAILS_URL)) {
//            try {
//                Type type = new TypeToken<GetProfileData>() {
//                }.getType();
//                GetProfileData profileData = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else if (condition.equals(ApiClient.GET_INDUSRTY_PROFESSION)) {
//            try {
//                Type type = new TypeToken<List<IndustryAndProfessionData>>() {
//                }.getType();
//                _industryAndAll = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
//                if (_industryAndAll != null) {
//
//                    _industryAndAllData.add("Select Industry");
//                    // IndustryAndProfessionData industryAndProfessionData = new IndustryAndProfessionData();
//                    // industryAndProfessionData.setPreferenceName("Select Industry");
//                    //   industryAndProfessionData.setProfessions(new ArrayList<>());
//
//                    for (int i = 0; i < _industryAndAll.size(); i++) {
////                        JSONObject jsonObject = null;
////                        try {
////                            jsonObject = response.getJSONObject(i);
////                        } catch (JSONException e) {
////                            e.printStackTrace();
////                        }
////                        if (jsonObject != null && jsonObject.length() > 0) {
////                            IndustryAndProfessionData industryAndProfessionDataInner = new IndustryAndProfessionData();
////                            industryAndProfessionDataInner.setPreferenceName(jsonObject.optString("preferenceName", ""));
////                            ArrayList<String> arrayList = new ArrayList<>();
////                            JSONArray jsonArrayProfessions = jsonObject.optJSONArray("professions");
////                            for (int j = 0; j < jsonArrayProfessions.length(); j++) {
////                                try {
////                                    arrayList.add(jsonArrayProfessions.getString(j));
////                                } catch (Exception e) {
////                                    e.printStackTrace();
////                                }
////                            }
////                            industryAndProfessionDataInner.setProfessions(arrayList);
////                            _industryAndAll.add(industryAndProfessionDataInner);
////                        }
//                        _industryAndAllData.add(_industryAndAll.get(i).getPreferenceName());
//                    }
//                    settingToAdapter(_industryAndAllData);
//                    //   getprofileDataWithEmailOrMobile();
//
//                    appenddata(editProfileUserDetails);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else if (condition.equals(ApiClient.RESEND_EMAIL)) {
//            try {
//                JSONObject response = new JSONObject(new Gson().toJson(apiResponseModel.data));
//                if (response.has("error")) {
//                    Toast.makeText(getActivity(), "" + response.getString("error"), Toast.LENGTH_LONG).show();
//                    if (response.getString("error").equals("Already verified!!")) {
//                        verifyemailBtn.setVisibility(View.GONE);
//                        emailVerifiedimg.setVisibility(View.VISIBLE);
//                    } else {
//                        Toast.makeText(getActivity(), "fail to resend", Toast.LENGTH_SHORT).show();
//                    }
//                } else if (response.has("message")) {
////                                        if (response.getString("message").equals("comLog saved sucessfully")) {
//                    if (response.getString("message").equals("Verification email sent successfully!")) {
//                        Toast.makeText(getActivity(), "" + "Verification email sent successfully.", Toast.LENGTH_LONG).show();
////                                            navigateToLoginActivity();
//                    } else {
//                        Toast.makeText(getActivity(), "fail to resend", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else if (condition.equals("getOTP")) {
//            try {
//
//                Toast.makeText(activity(), apiResponseModel.message, Toast.LENGTH_SHORT).show();
//                if (InternetSpeedChecker.checkInternetAvaialable(activity())) {
//
//
//                    SignUpConditions signUpConditions = new SignUpConditions();
//                    signUpConditions.setLogin(false);
//                    signUpConditions.setForgot(false);
//                    signUpConditions.setChangePassword(false);
//                    signUpConditions.setForOTPVerification(true);
//                    signUpConditions.setSocialNetwork(false);
//
//                    Intent intent = new Intent(activity(), HelperActivity.class);
//                    intent.putExtra(Constants.FRAGMENT_TITLE, "");
//                    intent.putExtra(Constants.FRAGMENT_KEY, 1001);// SignUpFragment
//
//                    if (isUsingMobile) {
//                        signUpConditions.setMobile(true);
//                        signUpConditions.setEmailOrMobile(edt_phone.getText().toString());
//                        signUpConditions.setCountryCodeEditProfile(searchCode);
//
//                    } else {
//                        signUpConditions.setMobile(false);
//                        signUpConditions.setEmailOrMobile(mEmail.getText().toString());
//                    }
//
//                    intent.putExtra("signUpConditions", signUpConditions);
//                    startActivity(intent);
//                    activity().finish();
//                }
//
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else if (condition.equals(ApiClient.GET_COUNTRY_LIST)) {
//            try {
//                Type type = new TypeToken<ArrayList<CountryData>>() {
//                }.getType();
//                _countryData = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
//                if (_countryData != null) {
//                    if (isPopup) {
//                        progressCountry.setVisibility(View.GONE);
//
//                        countryAdapter = new CountryAdapter(activity(), _countryData, this, iCountryAdapter);
//                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager
//                                (activity());
//                        countryRecyList.setLayoutManager(mLayoutManager);
//                        countryRecyList.setItemAnimator(new DefaultItemAnimator());
//                        countryRecyList.setAdapter(countryAdapter);
//                    } else {
//                        for (int i = 0; i < _countryData.size(); i++) {
//                            if (_countryData.get(i).getDial_code().equalsIgnoreCase(searchCode)) {
//                                minlenght = _countryData.get(i).getMinLength();
//                                maxlength = _countryData.get(i).getMaxlength();
//                                break;
//                            }
//                        }
//                    }
//                }
//            } catch (Exception e) {
//                if (isPopup) {
//                    progressCountry.setVisibility(View.GONE);
//                }
//                e.printStackTrace();
//            }
//        } else if (condition.equals("UpdateProfile")) {
//            try {
//                SessionManager.getInstance().setKeyValue(SessionManager.KEY_FIRSTTIME_EDIT_PROFILE_ACCESS,"TRUE");
//
//                Toast.makeText(activity(), apiResponseModel.message, Toast.LENGTH_SHORT).show();
//
//
//                Common.getprofileDataWithEmailOrMobile(activity().getApplicationContext(), activity(), "NEW_REG");
//
//            } catch (Exception e) {
//                Toast.makeText(activity(), "Fail to Update", Toast.LENGTH_SHORT).show();
//                SessionManager.getInstance().setKeyValue(SessionManager.KEY_FIRSTTIME_EDIT_PROFILE_ACCESS,dndisChecked);
//                Log.e("errorhits", "null");
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @Override
//    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
//        if (condition.equals(ApiClient.LOGINDETAILS_URL)) {
//        } else if (condition.equals(ApiClient.RESEND_EMAIL)) {
//            Toast.makeText(getActivity(), "fail to resend", Toast.LENGTH_SHORT).show();
//        } else if (condition.equals("getOTP")) {
//
//        } else if (condition.equals("UpdateProfile")) {
//
//        }
//    }
//
//    public void creditsUpdate(Double creditBal, Double refCredits) {
//        creditBalUpdated = creditBal;
//        refCreditsUpdated = refCredits;
//        if (CommonAccessPermissionOfCeleb.creditsPermissonAvailabilty(activity(),
//                true,
//                false, true)) {
//            creditAndReffferalCreditsShow(creditBal, refCredits);
//        } else {
//            mReferal_Credits.setText("N/A");
////                                mCredits_Display.setText("0");
//            mCredits_Display.setText("N/A");
//        }
//    }
//
//    public void appenddata(EditProfileUserDetails editProfileUserDetails) {
//        if (editProfileUserDetails != null) {
//            if (editProfileUserDetails.getUserDetails().getUsername() != null && !editProfileUserDetails.getUserDetails().getUsername().isEmpty()) {
//                user_name.setText(Character.toUpperCase(editProfileUserDetails.getUserDetails().getUsername().charAt(0)) + editProfileUserDetails.getUserDetails().getUsername().substring(1));
//                SessionManager.getInstance().setKeyValue(SessionManager.KEY_USER_NAME, editProfileUserDetails.getUserDetails().getUsername());
//            } else {
//                user_name.setText("");
//            }
//            if (editProfileUserDetails.getUserDetails().getId() != null && !editProfileUserDetails.getUserDetails().getId().isEmpty()) {
//                SessionManager.getInstance().setKeyValue(SessionManager.KEY_USER_ID,editProfileUserDetails.getUserDetails().getId());
//            } else {
//                SessionManager.getInstance().setKeyValue(SessionManager.KEY_USER_ID,"");
//            }
//
//            if (editProfileUserDetails.getUserDetails().getMobile() != null && !editProfileUserDetails.getUserDetails().getMobile().isEmpty()) {
//                SessionManager.getInstance().setKeyValue(SessionManager.KEY_MOBILE_NO,editProfileUserDetails.getUserDetails().getMobile());
//                edt_phone.setText("+" + editProfileUserDetails.getUserDetails().getMobile() + "");
//            } else {
//                SessionManager.getInstance().setKeyValue(SessionManager.KEY_MOBILE_NO,"");
//            }
//
//            if (editProfileUserDetails.getUserDetails().getMobile() != null && !editProfileUserDetails.getUserDetails().getMobile().isEmpty()) {
//                SessionManager.getInstance().setKeyValue(SessionManager.KEY_MOBILE_NO,editProfileUserDetails.getUserDetails().getMobile());
//                edt_phone.setText(editProfileUserDetails.getUserDetails().getMobile() + "");
//            }
//
//          /*  try {
//                if (editProfileUserDetails.getUserDetails().getCountry() != null && !editProfileUserDetails.getUserDetails().getCountry().isEmpty()) {
//                    edt_phone.setText("+" + editProfileUserDetails.getUserDetails().getMobileNumber() + "");
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }*/
//            if (editProfileUserDetails.getUserDetails().getFirstName() != null && !editProfileUserDetails.getUserDetails().getFirstName().isEmpty()) {
//                SessionManager.getInstance().setKeyValue(SessionManager.KEY_FIRST_NAME, editProfileUserDetails.getUserDetails().getFirstName());
//                et_Firstname.setText(Character.toUpperCase(editProfileUserDetails.getUserDetails().getFirstName().charAt(0))
//                        + editProfileUserDetails.getUserDetails().getFirstName().substring(1));
//                firstNameInServer = editProfileUserDetails.getUserDetails().getFirstName();
//            } else {
//                firstNameInServer = "";
//                et_Firstname.setText("");
//            }
//
//            if (editProfileUserDetails.getUserDetails().getLastName() != null && !editProfileUserDetails.getUserDetails().getLastName().isEmpty()) {
//                SessionManager.getInstance().setKeyValue(SessionManager.KEY_LAST_NAME,editProfileUserDetails.getUserDetails().getLastName());
////                            et_lastname.setText(profileData.getLastName());
//                et_lastname.setText(Character.toUpperCase(editProfileUserDetails.getUserDetails().getLastName().charAt(0)) + editProfileUserDetails.getUserDetails().getLastName().substring(1));
//                lastNameInServer = editProfileUserDetails.getUserDetails().getLastName();
//            } else {
//                lastNameInServer = "";
//                et_lastname.setText("");
//            }
//
////                            if (profileData.getAboutMe() != null && !profileData.getAboutMe().isEmpty()) {
////
////                                mAboutMe.setText(Character.toUpperCase(profileData.getLastName().charAt(0)) + profileData.getLastName().substring(1));
////                                aboutMeInServer = profileData.getAboutMe();
////                            } else {
////                                aboutMeInServer = "";
////                                mAboutMe.setText("");
////                            }
//
//            if (editProfileUserDetails.getUserDetails().getEmail() != null && !editProfileUserDetails.getUserDetails().getEmail().isEmpty()) {
//                mEmail.setText(editProfileUserDetails.getUserDetails().getEmail() + "");
//                SessionManager.getInstance().setKeyValue(SessionManager.KEY_EMAIL_OR_MOBILE, editProfileUserDetails.getUserDetails().getEmail());
//            }
//
//
//           /* if (profileData.getLocation() != null && !profileData.getLocation().isEmpty()) {
////                            mLocation.setText(profileData.getLocation() + "");
//                //mLocation.setText(Character.toUpperCase(profileData.getLocation().charAt(0)) + profileData.getLocation().substring(1));
//            } else if (profileData.getAddress() != null && !profileData.getAddress().isEmpty()) {
//                //  mLocation.setText(Character.toUpperCase(profileData.getAddress().charAt(0)) + profileData.getAddress().substring(1));
//            }
//
//            if (profileData.getDateOfBirth() != null && !profileData.getDateOfBirth().isEmpty()) {
//                // mDob.setText(profileData.getDateOfBirth() + "");
//            }
//            if (profileData.getPrefix() != null && profileData.getPrefix() != null) {
//                if (profileData.getPrefix().equals("Mr")) {
//                    //   userTitleSpn.setSelection(1);
//                    userTitleSelectionitem = profileData.getPrefix();
//                } else if (profileData.getPrefix().equals("Ms")) {
//                    //  userTitleSpn.setSelection(2);
//                    userTitleSelectionitem = profileData.getPrefix();
//                } else if (profileData.getPrefix().equals("Mrs")) {
//                    //  userTitleSpn.setSelection(3);
//                } else if (profileData.getPrefix().equals("Dr")) {
//                    //   userTitleSpn.setSelection(4);
//                    userTitleSelectionitem = profileData.getPrefix();
//                } else if (profileData.getPrefix().equals("None")) {
//                    // userTitleSpn.setSelection(5);
//                    userTitleSelectionitem = profileData.getPrefix();
//                }
//            } else {
//            }
//
//            if (profileData.getGender() != null && profileData.getGender() != null) {
//                if (profileData.getGender().equals("Male") ||
//                        profileData.getGender().equals("male")) {
//                    //   mMale.setChecked(true);
//                    genderSelection = "Male";
//                    //  genderSpn.setSelection(1);
//                    directAccess = false;
//                } else if (profileData.getGender().equals("Female")
//                        || profileData.getGender().equals("female")) {
//                    //   mFemale.setChecked(true);
//                    genderSelection = "Female";
//                    //  genderSpn.setSelection(2);
//                    directAccess = false;
////                                userTitleSelectionitem = "Miss";
//                } else if (profileData.getGender().equals("other")) {
//                    //  mFemale.setChecked(true);
//                    genderSelection = "other";
//                    // genderSpn.setSelection(3);
//                    directAccess = false;
//                }
//            }*/
//
//            if (editProfileUserDetails.getUserDetails().getLoginType() != null && !editProfileUserDetails.getUserDetails().getLoginType().isEmpty()) {
//                loginType = editProfileUserDetails.getUserDetails().getLoginType();
//
//            } else {
//                loginType = "";
//            }
//
//
//            try {
//                if (editProfileUserDetails.getUserDetails().getAvtarImgPath() != null && !editProfileUserDetails.getUserDetails().getAvtarImgPath().isEmpty()) {
//                    String profileImg = ApiClient.BASE_URL + editProfileUserDetails.getUserDetails().getAvtarImgPath();
//
//                    SessionManager.getInstance().setKeyValue(KEY_PROFILE_PIC, editProfileUserDetails.getUserDetails().getAvtarImgPath());
//
//                    try {
////                                        Glide.with(getGlobalActivity())
////                                                .load(profileImg)
////                                                .diskCacheStrategy(DiskCacheStrategy.ALL)
////                                                .listener(new RequestListener<String, GlideDrawable>() {
////                                                    @Override
////                                                    public boolean onException(Exception e, String model,
////                                                                               Target<GlideDrawable> target,
////                                                                               boolean isFirstResource) {
////                                                        progfileProgress.setVisibility(View.GONE);
////                                                     //   iv_user_pic.setVisibility(View.VISIBLE);
////                                                        return false;
////                                                    }
////
////                                                    @Override
////                                                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
////                                                        progfileProgress.setVisibility(View.GONE);
////                                                      //  iv_user_pic.setVisibility(View.VISIBLE);
////                                                        return false;
////                                                    }
////                                                }).into(iv_user_pic);
//
//                        progfileProgress.setVisibility(View.GONE);
//                        // iv_user_pic.setVisibility(View.VISIBLE);
//                    } catch (Exception e) {
//                        progfileProgress.setVisibility(View.GONE);
//                    }
//
//                } else {
//                    progfileProgress.setVisibility(View.GONE);
//                    //iv_user_pic.setVisibility(View.VISIBLE);
//                    SessionManager.getInstance().setKeyValue(KEY_PROFILE_PIC, "");
//                }
//
//                if (editProfileUserDetails.getUserDetails().getDnd() != null) {
//                    if (!editProfileUserDetails.getUserDetails().getDnd()) {
////                                    mSave.setText("Update");
//                        dndisChecked = "true";
//                    } else {
//                        dndisChecked = "false";
//                    }
//                }
//
//
//            } catch (Exception e) {
//            }
//            //
//            try {
//                if (editProfileUserDetails.getUserDetails().getCeleb()) {
//                    String industry = editProfileUserDetails.getUserDetails().getIndustry();
//                    Integer indexIndustry = -1;
//                    if (_industryAndAllData != null && _industryAndAllData.size() > 0) {
//                        indexIndustry = _industryAndAllData.indexOf(industry);
//                    }
//                    if (indexIndustry > -1) {
//                        userIndustrySelectionitem = industry;
//                        industrySpn.setSelection(indexIndustry);
//                        //
//                        String profession = editProfileUserDetails.getUserDetails().getProfession();
//
//                        setProfessiondata(indexIndustry - 1, true, profession);
//
//                               /* if (!profession.isEmpty()) {
//                                    Integer indexProfession = -1;
//                                    if (_professiondata != null && _professiondata.size() > 0) {
//                                       indexProfession = _professiondata.indexOf(profession);
//                                    }
//                                    if (indexProfession > -1) {
////                                        professiontxt.setText(profession);
//                                        Log.e("profession", profession + "");
//                                        setProfessiondata(indexIndustry,false);
//                                        proffesionspinner.setSelection(indexProfession);
//                                        proffesionspinner.setVisibility(View.VISIBLE);
//                                        userProfessionSelectionitem = profession;
//                                    }
//                                }*/
//                    }
//                } else {
//
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            if (editProfileUserDetails.getUserDetails().getAboutMe() != null && !editProfileUserDetails.getUserDetails().getAboutMe().isEmpty()) {
////                mAboutMe.setText(Character.toUpperCase(editProfileUserDetails.getUserDetails().getAboutMe().charAt(0))
////                        + editProfileUserDetails.getUserDetails().getAboutMe().substring(1));
//
//                mAboutMe.setText(Common.decodeMessage(Common.convertCaseSensitive(editProfileUserDetails.getUserDetails().getAboutMe())));
//
//                aboutMeStr = mAboutMe.getText().toString();
//                SessionManager.getInstance().setKeyValue(SessionManager.KEY_ABOUT,aboutMeStr);
//            } else {
//                SessionManager.getInstance().setKeyValue(SessionManager.KEY_ABOUT,"");
//            }
//
//            if (editProfileUserDetails.getUserDetails().getCeleb()) {
//                SessionManager.getInstance().setKeyValue(SessionManager.KEY_IS_CELEB,true);
//                //  isCelebVerifiedImage.setVisibility(View.VISIBLE);
//                industryProfessionLayout.setVisibility(View.VISIBLE);
//                isCeleb = "true";
//                if (editProfileUserDetails.getUserDetails().getRole() != null && !editProfileUserDetails.getUserDetails().getRole().isEmpty() &&
//                        !editProfileUserDetails.getUserDetails().getRole().equals("null")) {
//                    SessionManager.getInstance().setKeyValue(SessionManager.KEY_ROLE,editProfileUserDetails.getUserDetails().getRole());
//
//                    role = editProfileUserDetails.getUserDetails().getRole();
//                } else {
//                    role = "";
//                    SessionManager.getInstance().setKeyValue(SessionManager.KEY_ROLE,"");
//                }
//            } else {
//                SessionManager.getInstance().setKeyValue(SessionManager.KEY_IS_CELEB,false);
//                //   isCelebVerifiedImage.setVisibility(View.GONE);
//                industryProfessionLayout.setVisibility(View.GONE);
//                isCeleb = "false";
//                if (editProfileUserDetails.getUserDetails().getRole() != null && !editProfileUserDetails.getUserDetails().getRole().isEmpty() &&
//                        !editProfileUserDetails.getUserDetails().getRole().equals("null")) {
//                    SessionManager.getInstance().setKeyValue(SessionManager.KEY_ROLE,editProfileUserDetails.getUserDetails().getRole());
//                    role = editProfileUserDetails.getUserDetails().getRole();
//                } else {
//                    role = "";
//                    SessionManager.getInstance().setKeyValue(SessionManager.KEY_ROLE,"");
//                }
//            }
//
//            if (editProfileUserDetails.getUserDetails().getEmailVerified() != null) {
//                if (editProfileUserDetails.getUserDetails().getEmailVerified() && editProfileUserDetails.getUserDetails().getMobileVerified()) {
//                    isEmailVerified = true;
//                    isMobileVerified = true;
//                    verifyemailBtn.setVisibility(View.GONE);
//                    verifyMobileBtn.setVisibility(View.GONE);
//                    emailVerifiedimg.setVisibility(View.VISIBLE);
//                    verify_mblimage.setVisibility(View.VISIBLE);
//                    EmailID = editProfileUserDetails.getUserDetails().getEmail();
//                    MobileNumber = editProfileUserDetails.getUserDetails().getMobileNumber();
//                } else if (editProfileUserDetails.getUserDetails().getEmailVerified()) {
//                    isEmailVerified = true;
//                    isMobileVerified = false;
//                    verifyemailBtn.setVisibility(View.GONE);
//                    verifyMobileBtn.setVisibility(View.VISIBLE);
//                    emailVerifiedimg.setVisibility(View.VISIBLE);
//                    verify_mblimage.setVisibility(View.GONE);
//                    EmailID = editProfileUserDetails.getUserDetails().getEmail();
//                } else if (editProfileUserDetails.getUserDetails().getMobileVerified()) {
//                    isEmailVerified = false;
//                    isMobileVerified = true;
//                    verifyemailBtn.setVisibility(View.VISIBLE);
//                    verifyMobileBtn.setVisibility(View.GONE);
//                    emailVerifiedimg.setVisibility(View.GONE);
//                    verify_mblimage.setVisibility(View.VISIBLE);
//                    MobileNumber = editProfileUserDetails.getUserDetails().getMobileNumber();
//                }
//            }
//                if (editProfileUserDetails.getUserDetails().getCountryDetails() != null) {
//                    if (editProfileUserDetails.getUserDetails().getCountryDetails().countryCode != null &&
//                            editProfileUserDetails.getUserDetails().getCountryDetails().dialCode != null) {
//                        countryCodetv.setText(editProfileUserDetails.getUserDetails().getCountryDetails().countryCode + " " +
//                                editProfileUserDetails.getUserDetails().getCountryDetails().dialCode);
//                    }
//                }else {
//                    String countryZipCode = Common.GetCountryZipCode(activity());
//                    if (countryZipCode != null && !countryZipCode.isEmpty()) {
//                        String countryCodeLocal = "+" + countryZipCode;
//                        String countryCodeWithPrefix = "IN +91";
//                        searchCode = countryCodeLocal;
//                        countryCodeWithPrefix = Common.getCoutryCode(activity()) + " " + countryCodeLocal;
//                        countryCodetv.setText(countryCodeWithPrefix);
//                    }
//                }
//
//        }
//    }
//
//    public void getVerifyRegisterOTPJSON(JSONObject jsonObject, String condition, String userId) {
//        apiInterface = ApiClient.getClient().create(ApiInterface.class);
//        Call<ApiResponseModel> call = apiInterface.memberRegistrationNewSingUpJSON(userId,
//                RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
//                        jsonObject.toString()));
//
//        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, condition, true, iApiListener, true));
//
//    }
//    ///ConutryPopup
//
//    private void countryListPopup() {
//        final Dialog country_popup_dailog;
//        country_popup_dailog = new Dialog(activity());
//        country_popup_dailog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        country_popup_dailog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        country_popup_dailog.setCancelable(true);
//        country_popup_dailog.setContentView(R.layout.country_popup);
//        countryRecyList = (RecyclerView) country_popup_dailog.findViewById(R.id.countryRecyList);
//        country_popup_dailog.show();
//        country_popup_dailogGlobal = new Dialog(activity());
//        country_popup_dailogGlobal = country_popup_dailog;
//
//        EditText searchView = (EditText) country_popup_dailog.findViewById(R.id.searchView);
//        Common.hideKeyboard(activity());
//        progressCountry = (ProgressBar) country_popup_dailog.findViewById(R.id.progressCountry);
//        progressCountry.setVisibility(View.GONE);
//
////        getAllCountryListFromServer(countryRecyList, progressCountry);
//
//        if (_countryData.size() == 0) {
//            getAllCountryListFromServer(true);
//        } else {
//            if (countryAdapter != null) {
////                countryRecyList.setAdapter(countryAdapter);
//
//                countryAdapter = new CountryAdapter(activity(), _countryData, EditProfileTabFragment.this, iCountryAdapter);
//                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager
//                        (activity());
//                countryRecyList.setLayoutManager(mLayoutManager);
//                countryRecyList.setItemAnimator(new DefaultItemAnimator());
//                countryRecyList.setAdapter(countryAdapter);
//
//            } else {
//                getAllCountryListFromServer(true);
//            }
//        }
//
//
//        searchView.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (s.toString().length() > 0) {
//                    ArrayList<CountryData> _searchedData = (ArrayList<CountryData>) (ArrayList<?>) Common.getInstance().getFilteredListOfSearchObject(_countryData, s.toString().trim(), 3);
//                    if (_searchedData == null || _searchedData.size() == 0) {
//                        countryRecyList.setAdapter(new EmptyDataAdapter(activity(), Constants.SORRY,
//                                Constants.THERE_IS_NO_DATA, R.drawable.ic_no_results_to_show, 6));
//                    } else {
//                        countryRecyList.setAdapter(countryAdapter = new CountryAdapter(activity(), _searchedData, EditProfileTabFragment.this, iCountryAdapter));
//                    }
//                } else {
//                    countryRecyList.setAdapter(countryAdapter = new CountryAdapter(activity(), _countryData, EditProfileTabFragment.this, iCountryAdapter));
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (searchView.getText().toString().startsWith(" ")) {
//                    searchView.setText("");
//                }
//
//
//            }
//        });
//    }
//
//    private void getAllCountryListFromServer(boolean _isPopup) {
//        isPopup = _isPopup;
//        if (isPopup) {
//            progressCountry.setVisibility(View.VISIBLE);
//        }
//        apiInterface = ApiClient.getClient().create(ApiInterface.class);
//        Call<ApiResponseModel> call = apiInterface.getCountryList(ApiClient.GET_COUNTRY_LIST);
//        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, ApiClient.GET_COUNTRY_LIST,
//                false, iApiListener, true));
//    }
//
//
//
//    @Override
//    public void onClickCountry(CountryData countryData, int position) {
//        minlenght = countryData.getMinLength();
//        maxlength = countryData.getMaxlength();
//    }
//
//    public void searchCountryCode(String searchCodeLocal,
//                                  String countryCodeLocal) {
//        if (country_popup_dailogGlobal != null) {
//            country_popup_dailogGlobal.dismiss();
//            searchCode = countryCodeLocal;
//            countryCodetv.setText("" + searchCodeLocal);
//        } else {
//
//        }
//    }
//
//}
