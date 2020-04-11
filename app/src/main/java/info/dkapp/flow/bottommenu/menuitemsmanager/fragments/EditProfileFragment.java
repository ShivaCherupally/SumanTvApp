package info.dkapp.flow.bottommenu.menuitemsmanager.fragments;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.*;
import android.widget.*;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.theartofdev.edmodo.cropper.CropImage;

import de.hdodenhof.circleimageview.CircleImageView;
import info.dkapp.flow.bottommenu.activity.HelperActivity;
import info.dkapp.flow.bottommenu.celebrityprofile.CelebrityProfileFragment;
import info.dkapp.flow.bottommenu.generic.EmptyDataAdapter;
import info.dkapp.flow.bottommenu.interfaces.fragments.IFragment;
import info.dkapp.flow.bottommenu.models.EditProfileUserDetails;
import info.dkapp.flow.celebflow.EditProfileActivity.CategoriesPreference;
import info.dkapp.flow.celebflow.EditProfileActivity.IndustryAndProfessionData;
import info.dkapp.flow.celebflow.LocalDB;

import info.dkapp.flow.R;
import info.dkapp.flow.celebflow.constants.Constants;
import info.dkapp.flow.celebflow.countrydata.CountryAdapter;
import info.dkapp.flow.celebflow.countrydata.CountryData;
import info.dkapp.flow.celebflow.interfaces.ICountryAdapter;
import info.dkapp.flow.celebflow.modelData.SignUpConditions;
import info.dkapp.flow.retrofitcall.*;
import info.dkapp.flow.userflow.Util.Common;
import info.dkapp.flow.utils.Utility;
import info.dkapp.flow.utils.UtilityNew;
import info.dkapp.flow.utils.internetchecker.InternetSpeedChecker;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static info.dkapp.flow.retrofitcall.SessionManager.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment implements IFragment, IApiListener, View.OnClickListener, ICountryAdapter {

    @BindView(R.id.imageViewCoverPic)
    ImageView imageViewCoverPic;

    @BindView(R.id.imageViewProfilePic)
    CircleImageView imageViewProfilePic;

    @BindView(R.id.etUser_name)
    EditText etUser_name;

    @BindView(R.id.et_firstname)
    EditText et_firstname;

    @BindView(R.id.et_lastname)
    EditText et_lastname;

    @BindView(R.id.edt_email)
    EditText edt_email;

    @BindView(R.id.edt_phone)
    EditText edt_phone;

    @BindView(R.id.edt_aboutme)
    EditText edt_aboutme;

    @BindView(R.id.proffesionspinner)
    Spinner proffesionspinner;

    @BindView(R.id.industrySpn)
    Spinner industrySpn;

    @BindView(R.id.categorySpinner)
    Spinner categorySpinner;

    @BindView(R.id.textViewPhoneVerify)
    TextView textViewPhoneVerify;

    @BindView(R.id.textViewEmailVerify)
    TextView textViewEmailVerify;

    @BindView(R.id.tvCountryCode)
    TextView tvCountryCode;

    @BindView(R.id.imageViewUpdate)
    ImageView imageViewUpdate;

    @BindView(R.id.imageViewBack)
    ImageView imageViewBack;

    @BindView(R.id.imageviewVerifyMobileNumber)
    ImageView imageviewVerifyMobileNumber;

    @BindView(R.id.imageviewVerifyEmail)
    ImageView imageviewVerifyEmail;

    @BindView(R.id.industryProfessionLayout)
    LinearLayout industryProfessionLayout;

    @BindView(R.id.iLProfileLayout)
    LinearLayout iLProfileLayout;

    @BindView(R.id.textViewManagerProfile)
    TextView textViewManagerProfile;
    String imageFilePath;

    private String celebID;
    ApiInterface apiInterface;
    IApiListener iApiListener;
    EditProfileUserDetails editProfileUserDetails;
    ArrayList<IndustryAndProfessionData> _industryAndAll;
    ArrayList<String> _industryAndAllData;
    ArrayList<String> _professiondata;
    ArrayList<String> _categorydata;
    HashMap<String, List<String>> professiondata;
    HashMap<String, List<String>> categorydata;
    boolean industrySelected = false, professionSelection = false, categorySelection = false, isProfilePic;
    boolean directProfession = false, directcategory = false;
    String userIndustrySelectionitem = "";
    String userProfessionSelectionitem = "";
    String userCategorySelectionitem = "";
    String userCategorySelectionitemID = "";
    boolean isEmailVerified = false, isMobileVerified = false;
    String EmailID = "", MobileNumber = "";
    ICountryAdapter iCountryAdapter;
    boolean isUsingMobile = false;
    String mobileOrEmailID = "";
    String searchCode = "+91", toastMsg = "";
    CountryAdapter countryAdapter;
    ArrayList<CountryData> _countryData = new ArrayList<CountryData>();
    Dialog country_popup_dailogGlobal;
    ProgressBar progressCountry;
    RecyclerView countryRecyList;
    boolean isPopup = false;
    int minlenght, maxlength;
    private Dialog promoDialog;
    private ImageView close_popup;
    TextView take_photo_txt, gallery_txt;
    private static final String DEMO_PHOTO_PATH = "MyDemoPhotoDir";
    private String imageUrlProfilePic = "", imageUrlCoverPic = "";
    private float ratioProfilePic, ratioCoverPic;
    String selectioAddress = "";
    boolean isUpdate = false;
    private static final int MY_PERMISSIONS_REQUEST_ACCOUNTS = 1;


    public static EditProfileFragment newInstance(String pram1, String param2) {
        EditProfileFragment fragment = new EditProfileFragment();
    /*    Bundle args = new Bundle();
        args.putParcelable("signUpConditions", signUpConditions);
        fragment.setArguments(args);*/
        return fragment;
    }

    public EditProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iApiListener = this;
        iCountryAdapter = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        ButterKnife.bind(this, root);
        disableViews();
        Utility.hideKeyboard(activity());
        if (SessionManager.userLogin.userId != null && !SessionManager.userLogin.userId.isEmpty()) {
            celebID = SessionManager.userLogin.userId;
        }
        try {
            if (Common.isCelebAndManager(activity())) {
                industryProfessionLayout.setVisibility(View.VISIBLE);
                textViewManagerProfile.setVisibility(View.VISIBLE);
                getIndustrydata();
            } else if (Common.isCelebStatus(activity())) {
                industryProfessionLayout.setVisibility(View.VISIBLE);
                textViewManagerProfile.setVisibility(View.GONE);
                getIndustrydata();
            } else if (Common.isManagerStatus(activity())) {
                industryProfessionLayout.setVisibility(View.GONE);
                textViewManagerProfile.setVisibility(View.VISIBLE);
                getUserCompleteDetails();
            } else {
                industryProfessionLayout.setVisibility(View.GONE);
                textViewManagerProfile.setVisibility(View.GONE);
                getUserCompleteDetails();
            }
        } catch (Exception e) {
            getUserCompleteDetails();
        }

        textViewPhoneVerify.setOnClickListener(this);
        textViewEmailVerify.setOnClickListener(this);
        imageViewCoverPic.setOnClickListener(this);
        imageViewProfilePic.setOnClickListener(this);
        imageViewBack.setOnClickListener(this);
        imageViewUpdate.setOnClickListener(this);
        tvCountryCode.setOnClickListener(this);
        textViewManagerProfile.setOnClickListener(this);
        SpannableString content = new SpannableString("Manager Profile");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        textViewManagerProfile.setText(content);

        et_firstname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                et_firstname.getBackground().setColorFilter(getResources().getColor(R.color.editTexttint_color),
                        PorterDuff.Mode.SRC_ATOP);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (et_firstname.getText().toString().startsWith(" ")) {
                    et_firstname.setText("");
                }
                String firstName = editable.toString();
            }

        });
        et_lastname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                et_lastname.getBackground().setColorFilter(getResources().getColor(R.color.editTexttint_color),
                        PorterDuff.Mode.SRC_ATOP);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (et_lastname.getText().toString().startsWith(" ")) {
                    et_lastname.setText("");
                }
                String lastName = editable.toString();

            }
        });
        edt_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String number = editable.toString();
                if (!MobileNumber.equals(number)) {
                    textViewPhoneVerify.setVisibility(View.VISIBLE);
                    imageviewVerifyMobileNumber.setVisibility(View.GONE);
                } else {
                    if (number.length() > 0) {
                        textViewPhoneVerify.setVisibility(View.GONE);
                        imageviewVerifyMobileNumber.setVisibility(View.VISIBLE);
                    } else {
                        textViewPhoneVerify.setVisibility(View.VISIBLE);
                        imageviewVerifyMobileNumber.setVisibility(View.GONE);
                    }
                }
                if (number.length() > 13) {
                    Toast.makeText(activity(), "Your Phone Number has reached maximum limit of 13 characters", Toast.LENGTH_SHORT).show();
                }
            }
        });
        edt_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String email = editable.toString();
                if (!EmailID.equals(email)) {
                    textViewEmailVerify.setVisibility(View.VISIBLE);
                    imageviewVerifyEmail.setVisibility(View.GONE);
                } else {
                    if (email.length() > 0) {
                        textViewEmailVerify.setVisibility(View.GONE);
                        imageviewVerifyEmail.setVisibility(View.VISIBLE);
                    } else {
                        textViewEmailVerify.setVisibility(View.VISIBLE);
                        imageviewVerifyEmail.setVisibility(View.GONE);
                    }
                }
                if (email.length() > 254) {
                    Toast.makeText(activity(), "Your Email has reached maximum limit of 254 characters", Toast.LENGTH_SHORT).show();
                }
            }
        });

        getAllCountryListFromServer(false);
        return root;
    }

    private void disableViews() {
        etUser_name.setEnabled(false);
    }

    private void getUserCompleteDetails() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.getUserCompleteDate(ApiClient.GET_USER_COMPLETE_DATA + celebID);
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, ApiClient.GET_USER_COMPLETE_DATA, true, iApiListener, true));
    }

    private void getIndustrydata() {
        _industryAndAll = new ArrayList<>();
        _industryAndAllData = new ArrayList<>();
        professiondata = new HashMap<>();
        _professiondata = new ArrayList<>();
        categorydata = new HashMap<>();
        _categorydata = new ArrayList<>();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.getIndustryProfession(ApiClient.GET_INDUSRTY_PROFESSION);
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, ApiClient.GET_INDUSRTY_PROFESSION, true, iApiListener, true));
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
        if (condition.equals(ApiClient.GET_USER_COMPLETE_DATA)) {
            try {
                Type type = new TypeToken<EditProfileUserDetails>() {
                }.getType();
                editProfileUserDetails = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                if (editProfileUserDetails != null) {
                    //  SessionManager.getInstance().appendUserDetails(editProfileUserDetails,false);
                    try {
                        LocalDB dataBase = new LocalDB(activity());
                        dataBase.open();
                        long result = dataBase.updateLogin(editProfileUserDetails.getUserDetails().getId(), editProfileUserDetails.getUserDetails().getFirstName(), editProfileUserDetails.getUserDetails().getLastName(), editProfileUserDetails.getUserDetails().getAvtarImgPath());
                        dataBase.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (editProfileUserDetails.userDetails.username != null && !editProfileUserDetails.userDetails.username.isEmpty()) {
                        etUser_name.setText(editProfileUserDetails.userDetails.username.trim());
                    }
                    if (editProfileUserDetails.userDetails.firstName != null && !editProfileUserDetails.userDetails.firstName.isEmpty()) {
                        SessionManager.getInstance().setKeyValue(KEY_FIRST_NAME, Common.getInstance().IsNullReturnValue(editProfileUserDetails.userDetails.firstName, ""));
                        et_firstname.setText(Common.getInstance().convertFirstLetterToCapital(editProfileUserDetails.userDetails.firstName.trim()));
                    } else {
                        SessionManager.getInstance().setKeyValue(KEY_FIRST_NAME, "");
                        et_firstname.setText(null);
                    }
                    if (editProfileUserDetails.userDetails.lastName != null && !editProfileUserDetails.userDetails.lastName.isEmpty()) {
                        SessionManager.getInstance().setKeyValue(KEY_LAST_NAME, Common.getInstance().IsNullReturnValue(editProfileUserDetails.userDetails.lastName, ""));
                        et_lastname.setText(Common.getInstance().convertFirstLetterToCapital(editProfileUserDetails.userDetails.lastName.trim()));
                    } else {
                        SessionManager.getInstance().setKeyValue(SessionManager.KEY_LAST_NAME, "");
                        et_lastname.setText("");
                    }

                    if (editProfileUserDetails.userDetails.email != null && !editProfileUserDetails.userDetails.email.isEmpty()) {
                        edt_email.setText(editProfileUserDetails.userDetails.email.trim());
                    } else {
                        edt_email.setText("");
                    }
                    if (editProfileUserDetails.userDetails.mobile != null && !editProfileUserDetails.userDetails.mobile.isEmpty()) {
                        edt_phone.setText(editProfileUserDetails.userDetails.mobile.trim());
                    } else {
                        edt_phone.setText("");
                    }
                    if (editProfileUserDetails.userDetails.avtarImgPath != null && !editProfileUserDetails.userDetails.avtarImgPath.isEmpty()) {
                        String PROFILE_PIC_PATH = editProfileUserDetails.userDetails.avtarImgPath;
                        if (PROFILE_PIC_PATH.length() < 5) {
                            PROFILE_PIC_PATH = "";
                        }
                        if (!PROFILE_PIC_PATH.isEmpty()) {
                            Glide.with(getContext())
                                    .load(ApiClient.BASE_URL + PROFILE_PIC_PATH)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .error(R.drawable.profile_picture_placeholder)
                                    .into(imageViewProfilePic);
                        } else {
                            imageViewProfilePic.setImageResource(R.drawable.profile_picture_placeholder);
                        }
                    }
                    if (editProfileUserDetails.userDetails.cover_imgPath != null && !editProfileUserDetails.userDetails.cover_imgPath.isEmpty()) {
                        String COVER_PIC_PATH = editProfileUserDetails.userDetails.cover_imgPath;
                        if (COVER_PIC_PATH.length() < 5) {
                            COVER_PIC_PATH = "";
                        }
                        if (!COVER_PIC_PATH.isEmpty()) {
                            Glide.with(getContext())
                                    .load(ApiClient.BASE_URL + COVER_PIC_PATH)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .error(R.drawable.cover_picture_place_holder)
                                    .into(imageViewCoverPic);
                        } else {
                            imageViewCoverPic.setImageResource(R.drawable.cover_picture_place_holder);
                        }
                    }
                    try {
                        if (editProfileUserDetails.userDetails.avtarImgPath != null && !editProfileUserDetails.userDetails.avtarImgPath.isEmpty()) {
                            String profileImg = ApiClient.BASE_URL + editProfileUserDetails.userDetails.avtarImgPath;
                            SessionManager.getInstance().setKeyValue(SessionManager.KEY_PROFILE_PIC, editProfileUserDetails.userDetails.avtarImgPath);
                            SessionManager.getInstance().setKeyValue(KEY_PROFILE_PIC, Common.getInstance().IsNullReturnValue(editProfileUserDetails.userDetails.avtarImgPath, ""));
                        } else {
                            SessionManager.getInstance().setKeyValue(SessionManager.KEY_PROFILE_PIC, "");

                        }
                    } catch (Exception e) {
                    }

                    try {
                        if (editProfileUserDetails.getUserDetails().getCeleb()) {
                            String industry = editProfileUserDetails.getUserDetails().getIndustry();
                            Integer indexIndustry = -1;
                            if (_industryAndAllData != null && _industryAndAllData.size() > 0) {
                                indexIndustry = _industryAndAllData.indexOf(industry);
                            }
                            if (indexIndustry > -1) {
                                userIndustrySelectionitem = industry;
                                industrySpn.setSelection(indexIndustry);
                                //
                                String profession = editProfileUserDetails.getUserDetails().getProfession();
                                String category = editProfileUserDetails.getUserDetails().getUserCategory();

                                setProfessiondata(indexIndustry - 1, true, profession);
                                setCategorydata(indexIndustry - 1, true, category);

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (editProfileUserDetails.userDetails.aboutMe != null && !editProfileUserDetails.userDetails.aboutMe.isEmpty()) {
                        edt_aboutme.setText(Common.decodeMessage(Common.convertCaseSensitive(editProfileUserDetails.userDetails.aboutMe)));
                        String aboutMeStr = edt_aboutme.getText().toString();
                        SessionManager.getInstance().setKeyValue(SessionManager.KEY_ABOUT, aboutMeStr);
                    } else {
                        SessionManager.getInstance().setKeyValue(SessionManager.KEY_ABOUT, "");
                    }
                    if (editProfileUserDetails.userDetails.isEmailVerified != null) {
                        if (editProfileUserDetails.userDetails.isEmailVerified && editProfileUserDetails.userDetails.isMobileVerified) {
                            isEmailVerified = true;
                            isMobileVerified = true;
                            EmailID = editProfileUserDetails.userDetails.email;
                            MobileNumber = editProfileUserDetails.userDetails.mobile;
                            textViewEmailVerify.setVisibility(View.GONE);
                            imageviewVerifyEmail.setVisibility(View.VISIBLE);
                            textViewPhoneVerify.setVisibility(View.GONE);
                            imageviewVerifyMobileNumber.setVisibility(View.VISIBLE);
                        } else if (editProfileUserDetails.userDetails.isEmailVerified) {
                            isEmailVerified = true;
                            isMobileVerified = false;
                            textViewEmailVerify.setVisibility(View.GONE);
                            imageviewVerifyEmail.setVisibility(View.VISIBLE);
                            textViewPhoneVerify.setVisibility(View.VISIBLE);
                            imageviewVerifyMobileNumber.setVisibility(View.GONE);
                            EmailID = editProfileUserDetails.userDetails.email;
                        } else if (editProfileUserDetails.userDetails.isMobileVerified) {
                            isEmailVerified = false;
                            isMobileVerified = true;
                            MobileNumber = editProfileUserDetails.userDetails.mobile;
                            textViewEmailVerify.setVisibility(View.VISIBLE);
                            imageviewVerifyEmail.setVisibility(View.GONE);
                            textViewPhoneVerify.setVisibility(View.GONE);
                            imageviewVerifyMobileNumber.setVisibility(View.VISIBLE);
                        }
                    }
                    if (editProfileUserDetails.userDetails.countryDetails != null) {
                        if (editProfileUserDetails.userDetails.countryDetails.countryCode != null &&
                                editProfileUserDetails.userDetails.countryDetails.dialCode != null) {
                            tvCountryCode.setText(editProfileUserDetails.userDetails.countryDetails.countryCode + " " +
                                    editProfileUserDetails.userDetails.countryDetails.dialCode);
                        }
                    } else {
                        String countryZipCode = Common.GetCountryZipCode(activity());
                        if (countryZipCode != null && !countryZipCode.isEmpty()) {
                            String countryCodeLocal = "+" + countryZipCode;
                            String countryCodeWithPrefix = "IN +91";
                            searchCode = countryCodeLocal;
                            countryCodeWithPrefix = Common.getCoutryCode(activity()) + " " + countryCodeLocal;

                            tvCountryCode.setText(countryCodeWithPrefix);
                        }
                    }

                    if (HomeMenuFragment.getInstance() != null) {
                        HomeMenuFragment.getInstance().loadData();
                    }

                    if (isUpdate) {
                        Toast.makeText(activity(), toastMsg, Toast.LENGTH_SHORT).show();
                        activity().finish();
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (condition.equals(ApiClient.GET_INDUSRTY_PROFESSION)) {
            try {
                Type type = new TypeToken<List<IndustryAndProfessionData>>() {
                }.getType();
                _industryAndAll = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                if (_industryAndAll != null) {

                    _industryAndAllData.add("Select Industry");
                    _professiondata.add("Select Profession");
                    _categorydata.add("Select Category");

                    for (int i = 0; i < _industryAndAll.size(); i++) {
                        _industryAndAllData.add(_industryAndAll.get(i).getPreferenceName());
                    }
                    settingToAdapter(_industryAndAllData);

                    getUserCompleteDetails();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (condition.equals("getOTP")) {
            try {

                Toast.makeText(activity(), apiResponseModel.message, Toast.LENGTH_SHORT).show();
                if (InternetSpeedChecker.checkInternetAvaialable(activity())) {

                    SignUpConditions signUpConditions = new SignUpConditions();
                    signUpConditions.setLogin(false);
                    signUpConditions.setForgot(false);
                    signUpConditions.setChangePassword(false);
                    signUpConditions.setForOTPVerification(true);
                    signUpConditions.setSocialNetwork(false);

                    Intent intent = new Intent(activity(), HelperActivity.class);
                    intent.putExtra(Constants.FRAGMENT_TITLE, "");
                    intent.putExtra(Constants.FRAGMENT_KEY, 1001);// SignUpFragment

                    if (isUsingMobile) {
                        signUpConditions.setMobile(true);
                        signUpConditions.setEmailOrMobile(edt_phone.getText().toString());
                        signUpConditions.setCountryCodeEditProfile(searchCode);

                    } else {
                        signUpConditions.setMobile(false);
                        signUpConditions.setEmailOrMobile(edt_email.getText().toString());
                    }

                    intent.putExtra("signUpConditions", signUpConditions);
                    startActivity(intent);
                    activity().finish();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (condition.equals(ApiClient.GET_COUNTRY_LIST)) {
            try {
                Type type = new TypeToken<ArrayList<CountryData>>() {
                }.getType();
                _countryData = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                if (_countryData != null) {
                    if (isPopup) {
                        progressCountry.setVisibility(View.GONE);

                        countryAdapter = new CountryAdapter(activity(), _countryData, this, iCountryAdapter);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager
                                (activity());
                        countryRecyList.setLayoutManager(mLayoutManager);
                        countryRecyList.setItemAnimator(new DefaultItemAnimator());
                        countryRecyList.setAdapter(countryAdapter);
                    } else {
                        for (int i = 0; i < _countryData.size(); i++) {
                            if (_countryData.get(i).getDial_code().equalsIgnoreCase(searchCode)) {
                                minlenght = _countryData.get(i).getMinLength();
                                maxlength = _countryData.get(i).getMaxlength();
                                break;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                if (isPopup) {
                    progressCountry.setVisibility(View.GONE);
                }
                e.printStackTrace();
            }
        } else if (condition.equals("UpdateProfile")) {
            isUpdate = true;
            try {
                if (activity() instanceof HelperActivity) {
                    if (CelebrityProfileFragment.getInstance() != null) {
                        CelebrityProfileFragment.getInstance().getProfileDetails(celebID, true);
                    }
                }
                SessionManager.getInstance().setKeyValue(SessionManager.KEY_FIRSTTIME_EDIT_PROFILE_ACCESS, "TRUE");
                toastMsg = apiResponseModel.message;
                getUserCompleteDetails();
            } catch (Exception e) {
                Toast.makeText(activity(), "Fail to Update", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    private void setCategorydata(int position, boolean isDataget, String category) {
        if (_industryAndAll != null && _industryAndAll.size() > 0) {
            settingCategotydata(_industryAndAll.get(position).getCategories(), isDataget, category);
        } else {
            Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
        if (condition.equals(ApiClient.GET_USER_COMPLETE_DATA)) {

        }
    }

    private void setProfessiondata(Integer position, boolean isDataget, String professionName) {
        if (_industryAndAll != null && _industryAndAll.size() > 0) {
            settingProfessiondata(_industryAndAll.get(position).getProfessions(), isDataget, professionName);
        } else {
            Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
        }
    }

    private void settingCategotydata(final ArrayList<CategoriesPreference> categorydata, boolean isDataget, String category) {

        ArrayList<String> categoryPreNameList = new ArrayList<>();
        categoryPreNameList.add(0, "Select Category");
        for (int i = 0; i < categorydata.size(); i++) {
            categoryPreNameList.add(categorydata.get(i).preferenceName);
        }

        ArrayAdapter<String> designationAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_dropdown_item, categoryPreNameList);
        designationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(designationAdapter);

        ArrayList<String> categoryIDTemp = new ArrayList<>();
        categoryIDTemp.add(0, "No Id");

        for (int i = 0; i < categorydata.size(); i++) {
            categoryIDTemp.add(categorydata.get(i)._id);
        }

        Integer indexprofession = -1;
        for (int i = 0; i < categoryPreNameList.size(); i++) {
            if (categoryPreNameList.get(i).equals(category)) {
                indexprofession = i;
            }
        }

        if (isDataget) {
            if (indexprofession > -1) {
                categorySpinner.setSelection(indexprofession);
                userCategorySelectionitem = categoryPreNameList.get(indexprofession);
                userCategorySelectionitemID = categoryIDTemp.get(indexprofession);
            }
        }
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("positionNo", position + "");
                if (position == 0) {
                    userCategorySelectionitem = "Select Category";
                    userCategorySelectionitemID = "0";
                } else {
                    userCategorySelectionitem = categoryPreNameList.get(position);
                    userCategorySelectionitemID = categoryIDTemp.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void settingProfessiondata(final ArrayList<String> professiondataAvail, boolean isDataget, String professionName) {
        ArrayList<String> professiondata = new ArrayList<>();
        professiondata.add(0, "Select Profession");
        professiondata.addAll(professiondataAvail);

        ArrayAdapter<String> designationAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_dropdown_item, professiondata);
        designationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        proffesionspinner.setAdapter(designationAdapter);

        Integer indexprofession = -1;
        if (professiondata.size() > 0) {
            indexprofession = professiondata.indexOf(professionName);
        }

        if (isDataget) {
            if (indexprofession > -1) {
                proffesionspinner.setSelection(indexprofession);
                userProfessionSelectionitem = professiondata.get(indexprofession);
            }
        }
        proffesionspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    userProfessionSelectionitem = "Select Profession";
                } else {
                    userProfessionSelectionitem = professiondata.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void settingToAdapter(final ArrayList<String> industryAndAll) {
        try {

            ArrayAdapter<String> industryAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_dropdown_item, industryAndAll);
            industryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            industrySpn.setAdapter(industryAdapter);

            industrySpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        if (industrySelected) {
                            if (position == 0) {
                                userIndustrySelectionitem = "Select Industry";
                                proffesionspinner.setVisibility(View.GONE);
                                categorySpinner.setVisibility(View.GONE);
                            } else {
                                userIndustrySelectionitem = industryAndAll.get(position);
                                proffesionspinner.setVisibility(View.VISIBLE);
                                categorySpinner.setVisibility(View.VISIBLE);
                                setProfessiondata(position - 1, false, "");
                                setCategorydata(position - 1, false, "");
                            }
                        }
                    } catch (Exception e) {
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            industrySpn.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    industrySelected = true;
                    professionSelection = true;
                    return false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textViewPhoneVerify:
                if (!edt_phone.getText().toString().trim().isEmpty()) {
                    if (Common.getInstance().isValidMobilelenght(edt_phone.getText().toString(), minlenght, maxlength)) {
                        verify(false);
                    } else {
                        Toast.makeText(activity(), "Looks like your phone number may be incorrect", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Common.getInstance().cusToast(activity(), "Please enter Phone Number");
                }
                break;
            case R.id.textViewEmailVerify:
                verify(true);
                break;
            case R.id.imageViewCoverPic:
                isProfilePic = false;
                selectImage();
                break;
            case R.id.imageViewProfilePic:
                isProfilePic = true;
                selectImage();
                break;
            case R.id.imageViewBack:
                activity().finish();
                break;
            case R.id.imageViewUpdate:
//                update();
                if (mandatatoryValidationForLogin()) {
                    profileDataUploadInServer();
                }
                break;
            case R.id.tvCountryCode:
                countryListPopup();
                break;
            case R.id.textViewManagerProfile:
                Intent intent = new Intent(activity(), HelperActivity.class);
                intent.putExtra(Constants.FRAGMENT_TITLE, "Manager Profile");
                intent.putExtra(Constants.FRAGMENT_KEY, 8059);// ManagerAdditionalDetails
                startActivity(intent);
                break;
        }
    }

    private void selectImage() {
        promoDialog = new Dialog(getActivity());
        promoDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        promoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        promoDialog.setCancelable(true);
        promoDialog.setContentView(R.layout.camera_capture_dailog);
        take_photo_txt = (TextView) promoDialog.findViewById(R.id.take_photo_txt);
        gallery_txt = (TextView) promoDialog.findViewById(R.id.gallery_txt);
//        premiumCont = (TextView) promoDialog.root.findViewById(R.id.premiumCont);

        close_popup = (ImageView) promoDialog.findViewById(R.id.close_popup);
        promoDialog.show();

        take_photo_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promoDialog.dismiss();
                openCamera();

            }
        });
        gallery_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promoDialog.dismiss();
                galleryIntent();
            }
        });


        close_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                promoDialog.dismiss();
            }

        });

    }

    private void openCamera() {
        if (checkAndRequestPermissions()) {
            Intent pictureIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
            if (pictureIntent.resolveActivity(activity().getPackageManager()) != null) {
                //Create a file to store the image
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    // Error occurred while creating the File
                }
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(activity(), "info.celebkonnect.flow.celebflow.provider", photoFile);
                    pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            photoURI);
                    startActivityForResult(pictureIntent,
                            1);
                }
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                activity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        imageFilePath = image.getAbsolutePath();
        return image;
    }

    private boolean checkAndRequestPermissions() {
        int permissionCAMERA = ContextCompat.checkSelfPermission(activity(), Manifest.permission.CAMERA);
        int storagePermission = ContextCompat.checkSelfPermission(activity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (storagePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (permissionCAMERA != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }

        /*if (ACCESS_FINE_LOCATION != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ACCESS_COARSE_LOCATION != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }*/
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(activity(), listPermissionsNeeded.toArray(new
                    String[listPermissionsNeeded.size()]), MY_PERMISSIONS_REQUEST_ACCOUNTS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 264: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    private void galleryIntent() {
        if (checkAndRequestPermissions()) {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 2);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(imageFilePath);

                Intent intent = null;
                if (isProfilePic) {
                    intent = CropImage.activity(Uri.fromFile(f)).setAllowFlipping(false).setAspectRatio(300, 300)
                            .getIntent(getContext());
                } else {
                    intent = CropImage.activity(Uri.fromFile(f)).setAllowFlipping(false).setAspectRatio(100, 45)
                            .getIntent(getContext());
                }
                startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
            } else if (requestCode == 2) {
                Intent intent = null;
                if (isProfilePic) {
                    intent = CropImage.activity(data.getData()).setAllowFlipping(false).setAspectRatio(300, 300)
                            .getIntent(getContext());
                } else {
                    intent = CropImage.activity(data.getData()).setAllowFlipping(false).setAspectRatio(100, 45)
                            .getIntent(getContext());
                }
                startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    Uri resultUri = result.getUri();
                    if (isProfilePic) {
                        imageViewProfilePic.setImageURI(resultUri);
                    } else {
                        imageViewCoverPic.setImageURI(resultUri);
                    }
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(new File(resultUri.getPath()).getAbsolutePath(), options);
                    if (isProfilePic) {
                        imageUrlProfilePic = new File(resultUri.getPath()).getAbsolutePath();
                        ratioProfilePic = Common.imageResizeInRatioCaptureOrGallery(getActivity(),
                                options.outHeight, options.outWidth);
                    } else {
                        imageUrlCoverPic = new File(resultUri.getPath()).getAbsolutePath();
                        ratioCoverPic = Common.imageResizeInRatioCaptureOrGallery(getActivity(),
                                options.outHeight, options.outWidth);
                    }


                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }
            }
        }
    }


    private void verify(boolean isEmail) {
        JSONObject jsonObject = new JSONObject();
        if (isEmail) {
            if (!Common.isValidEmail(edt_email.getText().toString())) {
                Toast.makeText(activity(), "Please enter Email ID", Toast.LENGTH_SHORT).show();
                return;
            }

            isUsingMobile = false;
            mobileOrEmailID = edt_email.getText().toString().trim();

            try {
                jsonObject.put("medium", "email");
                jsonObject.put("email", edt_email.getText().toString().trim());

            } catch (JSONException e) {
                e.printStackTrace();
            }


        } else {
            if (!Common.isValidMobile(edt_phone.getText().toString())) {
                Common.getInstance().cusToast(activity(), "Please enter valid Phone Number");
                return;
            }

            isUsingMobile = true;
            mobileOrEmailID = edt_phone.getText().toString().trim();

            try {
                jsonObject.put("medium", "mobile");
                jsonObject.put("mobileNumber", edt_phone.getText().toString().trim());
                jsonObject.put("country", searchCode);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {
            jsonObject.put("mode", "getOTP");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        getVerifyRegisterOTPJSON(jsonObject, "getOTP", "null");
    }

    public void getVerifyRegisterOTPJSON(JSONObject jsonObject, String condition, String userId) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.memberRegistrationNewSingUpJSON(userId,
                RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                        jsonObject.toString()));

        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, condition, true, iApiListener, true));

    }

    ///ConutryPopup
    private void countryListPopup() {
        final Dialog country_popup_dailog;
        country_popup_dailog = new Dialog(activity());
        country_popup_dailog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        country_popup_dailog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        country_popup_dailog.setCancelable(true);
        country_popup_dailog.setContentView(R.layout.country_popup);
        countryRecyList = (RecyclerView) country_popup_dailog.findViewById(R.id.countryRecyList);
        country_popup_dailog.show();
        country_popup_dailogGlobal = new Dialog(activity());
        country_popup_dailogGlobal = country_popup_dailog;

        EditText searchView = (EditText) country_popup_dailog.findViewById(R.id.searchView);
        Common.hideKeyboard(activity());
        progressCountry = (ProgressBar) country_popup_dailog.findViewById(R.id.progressCountry);
        progressCountry.setVisibility(View.GONE);


        if (_countryData.size() == 0) {
            getAllCountryListFromServer(true);
        } else {
            if (countryAdapter != null) {
                countryAdapter = new CountryAdapter(activity(), _countryData, EditProfileFragment.this, iCountryAdapter);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager
                        (activity());
                countryRecyList.setLayoutManager(mLayoutManager);
                countryRecyList.setItemAnimator(new DefaultItemAnimator());
                countryRecyList.setAdapter(countryAdapter);

            } else {
                getAllCountryListFromServer(true);
            }
        }
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0) {
                    ArrayList<CountryData> _searchedData = (ArrayList<CountryData>) (ArrayList<?>) Common.getInstance().getFilteredListOfSearchObject(_countryData, s.toString().trim(), 3);
                    if (_searchedData == null || _searchedData.size() == 0) {
                        countryRecyList.setAdapter(new EmptyDataAdapter(activity(), Constants.SORRY,
                                Constants.THERE_IS_NO_DATA, R.drawable.ic_no_results_to_show, 6));
                    } else {
                        countryRecyList.setAdapter(countryAdapter = new CountryAdapter(activity(), _searchedData, EditProfileFragment.this, iCountryAdapter));
                    }
                } else {
                    countryRecyList.setAdapter(countryAdapter = new CountryAdapter(activity(), _countryData, EditProfileFragment.this, iCountryAdapter));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (searchView.getText().toString().startsWith(" ")) {
                    searchView.setText("");
                }
            }
        });
    }

    private void getAllCountryListFromServer(boolean _isPopup) {
        isPopup = _isPopup;
        if (isPopup) {
            progressCountry.setVisibility(View.VISIBLE);
        }
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.getCountryList(ApiClient.GET_COUNTRY_LIST);
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, ApiClient.GET_COUNTRY_LIST,
                false, iApiListener, true));
    }

    @Override
    public void onClickCountry(CountryData countryData, int position) {
        minlenght = countryData.getMinLength();
        maxlength = countryData.getMaxlength();
    }

    public void searchCountryCode(String searchCodeLocal,
                                  String countryCodeLocal) {
        if (country_popup_dailogGlobal != null) {
            country_popup_dailogGlobal.dismiss();
            searchCode = countryCodeLocal;
            tvCountryCode.setText("" + searchCodeLocal);
        } else {

        }
    }

    private boolean mandatatoryValidationForLogin() {
        boolean isValidate = true;
        if (et_firstname.getText().toString().length() == 0) {
            isValidate = false;
            Toast.makeText(getActivity(), "Please update your profile information to continue", Toast.LENGTH_SHORT).show();
            return isValidate;
        } else if (et_lastname.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), "Please update your profile information to continue", Toast.LENGTH_SHORT).show();
            isValidate = false;
            return isValidate;
        }
        if (Common.isCelebAndManager(activity())) {
            if (userIndustrySelectionitem != null && !userIndustrySelectionitem.isEmpty() &&
                    !userIndustrySelectionitem.equals("Select Industry")) {

            } else {
                Toast.makeText(getActivity(), "Pick a favorite Industry", Toast.LENGTH_SHORT).show();
                isValidate = false;
                return isValidate;
            }

            if (userProfessionSelectionitem == null && userProfessionSelectionitem.isEmpty() &&
                    userProfessionSelectionitem.equals("Select Industry")) {
                Toast.makeText(getActivity(), "Pick a favorite Industry", Toast.LENGTH_SHORT).show();
                isValidate = false;
                return isValidate;
            }

        }
        if (Common.isCelebStatus(activity())) {
            if (userIndustrySelectionitem == null && userIndustrySelectionitem.isEmpty() &&
                    userIndustrySelectionitem.equals("Select Industry")) {
                Toast.makeText(getActivity(), "Pick a favorite Industry", Toast.LENGTH_SHORT).show();
                isValidate = false;
                return isValidate;
            } else if (userProfessionSelectionitem == null && userProfessionSelectionitem.isEmpty() &&
                    userProfessionSelectionitem.equals("Select Industry")) {
                Toast.makeText(getActivity(), "Pick a favorite Industry", Toast.LENGTH_SHORT).show();
                isValidate = false;
                return isValidate;
            }
        }

        return isValidate;
    }

    private void profileDataUploadInServer() {
        if (!TextUtils.isEmpty(et_firstname.getText().toString())) {
            if (!TextUtils.isEmpty(et_lastname.getText().toString())) {
                if (UtilityNew.isNetworkAvailable(activity())) {
                    if (SessionManager.userLogin.isCeleb != null) {
                        if (SessionManager.userLogin.isCeleb) {
                            if (userIndustrySelectionitem != null && !userIndustrySelectionitem.isEmpty() &&
                                    !userIndustrySelectionitem.equals("Select Industry")) {
                                if (!userCategorySelectionitem.equals("Select Category")) {
                                    if (!userProfessionSelectionitem.equals("Select Profession")) {

                                        updateCelebProfileInServer();
                                    } else {
                                        Toast.makeText(getActivity(), "Pick a favorite Profession", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getActivity(), "Pick a favorite Category", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getActivity(), "Pick a favorite Industry", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            updateMemberProfileInServer();
                        }
                    } else {
                        updateMemberProfileInServer();
                        ;
                    }
                } else {
                    showSnackBar(Constants.PLEASE_CHECK_INTERNET, 2);
                }
            } else {
                Common.getInstance().cusToast(activity(), "Please update your profile information to continue !!!");
                return;
            }
        } else {
            Common.getInstance().cusToast(activity(), "Please update your profile information to continue !!!");
            return;

        }
    }

    private void updateMemberProfileInServer() {
        if (!isEmailVerified && !edt_email.getText().toString().isEmpty() && textViewEmailVerify.getText().toString().equals(Constants.VERIFY)) {
            Toast.makeText(activity(), "Please verify Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isMobileVerified && !edt_phone.getText().toString().isEmpty() && textViewPhoneVerify.getText().toString().equals(Constants.VERIFY)) {
            Toast.makeText(activity(), "Please verify Phone Number", Toast.LENGTH_SHORT).show();
            return;
        }
        File fileMediaProfilePic;
        MultipartBody.Part bodyProfilePic = null;
        if (!imageUrlProfilePic.isEmpty()) {
            try {
                fileMediaProfilePic = new File(imageUrlProfilePic);
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), fileMediaProfilePic);
                bodyProfilePic = MultipartBody.Part.createFormData("profilePic", fileMediaProfilePic
                        .getName(), requestFile);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        File fileMediaCoverPic;
        MultipartBody.Part bodyCoverPic = null;
        if (!imageUrlCoverPic.isEmpty()) {
            try {
                fileMediaCoverPic = new File(imageUrlCoverPic);
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), fileMediaCoverPic);
                bodyCoverPic = MultipartBody.Part.createFormData("coverPic", fileMediaCoverPic
                        .getName(), requestFile);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("loginType", "app");
            jsonObject.put("location", "");
            jsonObject.put("id", SessionManager.userLogin.userId);
            jsonObject.put("dateOfBirth", "");
            jsonObject.put("selectionaddress", selectioAddress);

            if (!isEmailVerified) {
                if (!edt_email.getText().toString().isEmpty()) {
                    jsonObject.put("email", edt_email.getText().toString().trim());
                }
            }
            if (!isMobileVerified) {
                if (!edt_phone.getText().toString().isEmpty()) {
                    jsonObject.put("mobileNumber", edt_phone.getText().toString());
                    jsonObject.put("country", searchCode);
                }
            }
            jsonObject.put("profession", userProfessionSelectionitem);
            jsonObject.put("category", userCategorySelectionitem);
            jsonObject.put("genderselection", "");
            jsonObject.put("role", SessionManager.getInstance().getKeyValue(KEY_ROLE, ""));
            jsonObject.put("industry", userIndustrySelectionitem);
            jsonObject.put("firstName", Common.getInstance().convertFirstLetterToCapital(et_firstname.getText().toString().trim()));
            jsonObject.put("lastName", Common.getInstance().convertFirstLetterToCapital(et_lastname.getText().toString().trim()));
            jsonObject.put("aboutMe", Common.convertEmojiFormat(edt_aboutme));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String userId = SessionManager.userLogin.userId;
        Call<ApiResponseModel> call = apiInterface.editProfileUpdate(userId, RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                jsonObject.toString()), bodyProfilePic, bodyCoverPic);

        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, "UpdateProfile", true, iApiListener, true));
    }


    private void updateCelebProfileInServer() {
        SessionManager.getInstance().setKeyValue(KEY_FIRST_NAME, Common.getInstance().convertFirstLetterToCapital(et_firstname.getText().toString()));
        SessionManager.getInstance().setKeyValue(SessionManager.KEY_LAST_NAME, Common.getInstance().convertFirstLetterToCapital(et_lastname.getText().toString()));

        if (!isEmailVerified) {
            if (!edt_email.getText().toString().isEmpty()) {
                if (textViewEmailVerify.getText().toString().equals(Constants.VERIFY)) {
                    Toast.makeText(activity(), "Please verify Email", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
        if (!isMobileVerified) {
            if (!edt_phone.getText().toString().isEmpty()) {
                if (textViewPhoneVerify.getText().toString().equals(Constants.VERIFY)) {
                    Toast.makeText(activity(), "Please verify Mobile", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
        File fileMediaProfilePic;
        MultipartBody.Part bodyProfilePic = null;
        if (!imageUrlProfilePic.isEmpty() && imageUrlProfilePic != null) {
            try {
                fileMediaProfilePic = new File(imageUrlProfilePic);
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), fileMediaProfilePic);
                bodyProfilePic = MultipartBody.Part.createFormData("profilePic", fileMediaProfilePic
                        .getName(), requestFile);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        File fileMediaCoverPic;
        MultipartBody.Part bodyCoverPic = null;
        if (!imageUrlCoverPic.isEmpty() && imageUrlCoverPic != null) {
            try {
                fileMediaCoverPic = new File(imageUrlCoverPic);
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), fileMediaCoverPic);
                bodyCoverPic = MultipartBody.Part.createFormData("coverPic", fileMediaCoverPic
                        .getName(), requestFile);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("loginType", "app");
            jsonObject.put("location", "");
            jsonObject.put("id", SessionManager.userLogin.userId);
            jsonObject.put("dateOfBirth", "");
            jsonObject.put("selectionaddress", selectioAddress);
            if (!isEmailVerified) {
                if (!edt_email.getText().toString().isEmpty()) {
                    jsonObject.put("email", edt_email.getText().toString());
                }
            }
            if (!isMobileVerified) {
                if (!edt_phone.getText().toString().isEmpty()) {
                    jsonObject.put("mobileNumber", edt_phone.getText().toString());
                    jsonObject.put("country", searchCode);
                }
            }
            jsonObject.put("profession", userProfessionSelectionitem);
            jsonObject.put("category", userCategorySelectionitem);
            Log.d("categoryItemSer", userCategorySelectionitem + "");

            jsonObject.put("categoryId", userCategorySelectionitemID); //5aba19c8d84c9f352f7647fa

            jsonObject.put("genderselection", "");
            jsonObject.put("role", SessionManager.getInstance().getKeyValue(KEY_ROLE, ""));
            //    jsonObject.put("Dnd", "true");
            jsonObject.put("industry", userIndustrySelectionitem);
            jsonObject.put("firstName", Common.getInstance().convertFirstLetterToCapital(et_firstname.getText().toString()));
            jsonObject.put("lastName", Common.getInstance().convertFirstLetterToCapital(et_lastname.getText().toString()));
            jsonObject.put("aboutMe", Common.convertEmojiFormat(edt_aboutme));
            /*String ration = String.valueOf(image_ratio);
            jsonObject.put("imageRatio", ration);*/


        } catch (JSONException e) {
            e.printStackTrace();
        }

        String userId = SessionManager.userLogin.userId;
        Call<ApiResponseModel> call = apiInterface.editProfileUpdate(userId, RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                jsonObject.toString()), bodyProfilePic, bodyCoverPic);
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, "UpdateProfile", true, iApiListener, true));


    }

}
