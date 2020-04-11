package info.dkapp.flow.celebflow;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.*;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;
import com.theartofdev.edmodo.cropper.CropImage;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import info.dkapp.flow.bottommenu.generic.EmptyDataAdapter;
import info.dkapp.flow.bottommenu.interfaces.fragments.IFragment;
import info.dkapp.flow.celebflow.constants.Constants;
import info.dkapp.flow.celebflow.countrydata.CountryAdapter;
import info.dkapp.flow.celebflow.countrydata.CountryData;
import info.dkapp.flow.celebflow.interfaces.ICountryAdapter;
import info.dkapp.flow.celebflow.modelData.ForgotUserDetails;
import info.dkapp.flow.celebflow.modelData.SignUpConditions;
import info.dkapp.flow.retrofitcall.*;
import info.dkapp.flow.singup.Available;
import info.dkapp.flow.singup.MemberRegRes;
import info.dkapp.flow.singup.Profile;
import info.dkapp.flow.singup.RegistrationMemberImage;
import info.dkapp.flow.userflow.UserActivitys.LoginActivity.LoginData;
import info.dkapp.flow.userflow.UserActivitys.LoginActivity.LoginResponse;
import info.dkapp.flow.userflow.Util.Common;
import info.dkapp.flow.userflow.Util.DateUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.INPUT_METHOD_SERVICE;
import static info.dkapp.flow.userflow.Util.Common.isValidEmail;

import info.dkapp.flow.R;

/**
 * A simple {@link Fragment} subclass.
 */

public class SignUpFragment extends Fragment implements IFragment, View.OnClickListener, IApiListener, ICountryAdapter, OnOtpCompletionListener {

    LinearLayout linear_phone_number_click, linear_email_id_click, Linear_mobile_number, Linear_email_id, signLayout,
            linear_OTP_CP_thor, linear_old_password_CP_thor, llOldPassword, llOldOTP, llOTP;
    TextView tv_welcome_name, countryCodetv, textview_verifiy_send, email_tv, phone_tv, otp_resend_tv, otp_mobile_number_tv,
            tv_welcome_change_username, tv_profile_pic_skip, tvsentpwdtxt, tv_old_password_thor, tv_OTP_thor, tvSendTo, tv_referralCode_skip,
            tv_haveReferralCode, signup_alreadyhaveaccount, tvChangeProfilePic, tvForgotTitle, tvForgotUserName, tvViaSMSMobileNo, tvViaEmailId, tvChangeViaSMSMobileNo, tvChangeViaEmailId,
            tvCharat, tvUpperCase, tvLowerCase, tvDigit, tvSpecial;
    View phone_tv_view, email_tv_view, tv_old_password_thor_view, tv_OTP_thor_view;
    EditText tv_welcome_username, edt_email_mobile_number, et_oldPasswordChange, et_referralCode;
    ImageView imageview_clear_mobile_number, imageview_clear_email, imageview_back_otp,
            imageView_8charact, imageView_digit, imageView_lowercase, imageView_uppercase, imageView_specialCha,
            imageViewpasswordclose, imageview_refresh, imageview_back_referral, imageview_back_forgot, imageviewViaSMS, imageviewViaEmail, imageview_ForgotDetails, imageviewChangeViaSMS, imageviewChangeViaEmail;
    de.hdodenhof.circleimageview.CircleImageView imageViewForgotUserPic;
    SimpleDraweeView imageview_pick_profile_pic;
    Button next_button, next_button_otp, next_button_profile_pic, next_button_create, next_button_welcome_celebkonect, next_button_name,
            bt_forgotNext, bt_checkOldPassword, bt_changeGetOTP, bt_referralCode, btForgotDetailsNext;
    EditText email_et, mobile_et, et1, et2, et3, et4, et5, et6, et_password_create, et_conf_password_create, et_firstname, et_lasttname;
    RelativeLayout profile_pic_layout, welcome_celebKonect_layout, email_mobile_layout, otp_layout, create_password_layout,
            name_first_last_layout, rlforgotOTP, rlChangePassword, rlReferralCode, rlForgotUserDetails, rlViaSMS, rlViaEmail, rlChangeViaSMS, rlChangeViaEmail;
    private EditText[] editTexts;
    private View viewDivied;
    private Dialog promoDialog;
    private ImageView close_popup;
    TextView take_photo_txt, gallery_txt;
    private static final String DEMO_PHOTO_PATH = "MyDemoPhotoDir";
    private String imageUrl = "";
    private float image_ratio;
    ApiInterface apiInterface;
    IApiListener iApiListener;
    String password = "";
    ImageView imageview_user_available;
    String userId = "null";
    File file;
    private static final int MY_PERMISSIONS_REQUEST_ACCOUNTS = 1;
    ContentBody cbFile;
    String mobileOrEmailID = "";
    boolean isUsingMobile = false;
    String imageFilePath;
    String userName = "";
    boolean isLogin = false;
    boolean isForgot = false;
    boolean isEmailID = false;
    boolean isChangePassword = false;
    boolean IS_EMAIL_VERIFIED = false;
    boolean IS_MOBILE_VERIFIED = false;
    boolean isReferralCodeAvailable = false;
    boolean isReferralCodeAvailableSkip = false;
    boolean forOTPVerification = false;
    boolean isMobile = false;
    String EmailOrMobile = "", countryCodeEditProfile = "";
    String referralCode = "", firstName = "";
    String socialNetworkEmailID = "";
    String searchCode = "+91";
    CountryAdapter countryAdapter;
    ArrayList<CountryData> _countryData = new ArrayList<CountryData>();
    Dialog country_popup_dailogGlobal;
    ProgressBar progressCountry;
    RecyclerView countryRecyList;
    boolean isPopup = false;
    boolean isProfileSkip = false;
    boolean isSocialNetwork = false;
    String socialMediaType = "";
    boolean isReferralCodeVerified = false;
    ForgotUserDetails userDetails;
    public boolean isChangePasswordBack = false, isChangePasswordBackOTP = false, isForgotBack = false, isForgotBackDetails = false, isPasswordVerified = false, isFromSettings = false;
    private static SignUpFragment instance = null;
    double minlenght,
            maxlength;
    SignUpConditions signUpConditions;
    public String firstNameSocial = "";
    public String lastNameSocial = "";
    ICountryAdapter iCountryAdapter;
    private OtpView otpView;

    ImageView logoIconSignupLayout, logoIconForgotLayout, logoIconWelcomeLayout;
    RelativeLayout parentLayout;

    public SignUpFragment() {
        // Required empty public constructor
    }

    public static SignUpFragment getInstance() {
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        iCountryAdapter = this;
        iApiListener = this;
    }

    /*public static SignUpFragment newInstance(boolean isLogin, String userName, String userId, boolean isForgot, boolean isChangePassword,
                                             boolean forOTPVerification, boolean isMobile, String EmailOrMobile, boolean isSocialNetwork,
                                             String socialNetworkEmailID,String  firstName) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        args.putBoolean("isLogin", isLogin);
        args.putString("userName", userName);
        args.putString("userId", userId);
        args.putBoolean("isForgot", isForgot);
        args.putBoolean("isChangePassword", isChangePassword);
        args.putBoolean("forOTPVerification", forOTPVerification);
        args.putBoolean("isMobile", isMobile);
        args.putString("EmailOrMobile", EmailOrMobile);
        args.putBoolean("isSocialNetwork", isSocialNetwork);
        args.putString("socialNetworkEmailID", socialNetworkEmailID);
        args.putString("firstName",firstName);
        fragment.setArguments(args);
        return fragment;
    }*/

    public static SignUpFragment newInstance(SignUpConditions signUpConditions) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        args.putParcelable("signUpConditions", signUpConditions);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_sign_up, container, false);
        if (Build.VERSION.SDK_INT < 23) {
        } else {
            if (checkAndRequestPermissions()) {
            }
        }

        if (getArguments() != null) {
            signUpConditions = getArguments().getParcelable("signUpConditions");
            isLogin = signUpConditions.getLogin();
            userName = signUpConditions.getUserName();
            userId = signUpConditions.getUserId();
            firstName = signUpConditions.getFirstName();
            isForgot = signUpConditions.getForgot();
            isChangePassword = signUpConditions.getChangePassword();
            forOTPVerification = signUpConditions.getForOTPVerification();
            isMobile = signUpConditions.getMobile();
            EmailOrMobile = signUpConditions.getEmailOrMobile();
            isSocialNetwork = signUpConditions.getSocialNetwork();
            socialNetworkEmailID = signUpConditions.getSocialNetworkEmailID();
            firstNameSocial = signUpConditions.getFirstNameSocial();
            lastNameSocial = signUpConditions.getLastNameSocial();
            countryCodeEditProfile = signUpConditions.getCountryCodeEditProfile();
            isPasswordVerified = signUpConditions.getPasswordVerified();
            isFromSettings = signUpConditions.getFromSettings();

            socialMediaType = signUpConditions.getSocialMediaType();
        }

        if (SessionManager.userLogin.isMobileVerified) {
            IS_MOBILE_VERIFIED = true;
        }
        if (SessionManager.userLogin.isEmailVerified) {
            IS_EMAIL_VERIFIED = true;
        }
        setUp(root);
        getAllCountryListFromServer(false);
        String countryZipCode = Common.GetCountryZipCode(activity());
        if (countryZipCode != null && !countryZipCode.isEmpty()) {
            String countryCodeLocal = "+" + countryZipCode;
            String countryCodeWithPrefix = "IN +91";
            searchCode = countryCodeLocal;
            countryCodeWithPrefix = Common.getCoutryCode(activity()) + " " + countryCodeLocal;
            countryCodetv.setText(countryCodeWithPrefix);
        }

        Common.getInstance().getLocation(getActivity(), this.getClass().getSimpleName(), true);


        return root;
    }

    private void setUp(View root) {
        signLayout = (LinearLayout) root.findViewById(R.id.signLayout);
        SpannableString content = new SpannableString("Log In");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        signup_alreadyhaveaccount = (TextView) root.findViewById(R.id.signup_alreadyhaveaccount);
        signup_alreadyhaveaccount.setText(content);

        signLayout.setOnClickListener(this);

        //Phone_email_layout
        linear_phone_number_click = (LinearLayout) root.findViewById(R.id.linear_phone_number_click);
        linear_email_id_click = (LinearLayout) root.findViewById(R.id.linear_email_id_click);
        Linear_mobile_number = (LinearLayout) root.findViewById(R.id.Linear_mobile_number);
        Linear_email_id = (LinearLayout) root.findViewById(R.id.Linear_email_id);
        countryCodetv = (TextView) root.findViewById(R.id.countryCode);
        textview_verifiy_send = (TextView) root.findViewById(R.id.textview_verifiy_send);
        email_tv = (TextView) root.findViewById(R.id.email_tv);
        phone_tv = (TextView) root.findViewById(R.id.phone_tv);
        phone_tv_view = (View) root.findViewById(R.id.phone_tv_view);
        email_tv_view = (View) root.findViewById(R.id.email_tv_view);
        next_button = (Button) root.findViewById(R.id.next_button);
        imageview_clear_email = (ImageView) root.findViewById(R.id.imageview_clear_email);
        imageview_clear_mobile_number = (ImageView) root.findViewById(R.id.imageview_clear_mobile_number);
        email_et = (EditText) root.findViewById(R.id.email_et);
        mobile_et = (EditText) root.findViewById(R.id.mobile_et);
        email_mobile_layout = (RelativeLayout) root.findViewById(R.id.email_mobile_layout);
        textview_verifiy_send.setText("You will receive a verification code on this Number");
        next_button.setEnabled(false);
        next_button.setBackgroundColor(activity().getResources().getColor(R.color.new_light));

        logoIconSignupLayout = (ImageView) root.findViewById(R.id.logoIconSignupLayout); //new icon
        logoIconForgotLayout = (ImageView) root.findViewById(R.id.logoIconForgotLayout); //new icon
        logoIconWelcomeLayout = (ImageView) root.findViewById(R.id.logoIconWelcomeLayout); //new icon
        parentLayout = (RelativeLayout) root.findViewById(R.id.parentLayout); //new icon

        parentLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (parentLayout != null) {
                    if (email_mobile_layout.getVisibility() == View.VISIBLE) {
                        if (Common.getInstance().isKeyboardVisible(activity())) {
                            headerLogoHideShow(logoIconSignupLayout, 0, 0);
                        } else {
                            headerLogoHideShow(logoIconSignupLayout, (int) getResources().getDimension(R.dimen._35sdp), (int) getResources().getDimension(R.dimen._25sdp));
                        }
                    } else if (welcome_celebKonect_layout.getVisibility() == View.VISIBLE) {
                        if (Common.getInstance().isKeyboardVisible(activity())) {
                            headerLogoHideShow(logoIconWelcomeLayout, 0, 0);
                        } else {
                            headerLogoHideShow(logoIconWelcomeLayout, (int) getResources().getDimension(R.dimen._35sdp), 0);
                        }
                    } else if (rlforgotOTP.getVisibility() == View.VISIBLE) {
                        if (Common.getInstance().isKeyboardVisible(activity())) {
                            headerLogoHideShow(logoIconForgotLayout, 0, 0);
                        } else {
                            headerLogoHideShow(logoIconForgotLayout, (int) getResources().getDimension(R.dimen._50sdp), 0);
                        }
                    }
                }
            }
        });


        email_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String email_et = s.toString();
                if (email_et.length() == 0) {
                    imageview_clear_email.setVisibility(View.GONE);
                } else {
                    imageview_clear_email.setVisibility(View.VISIBLE);
                }
                enableViews("emailScreen");
            }
        });

        mobile_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String mobile_et = s.toString();
                if (mobile_et.length() == 0) {
                    imageview_clear_mobile_number.setVisibility(View.GONE);
                } else {
                    imageview_clear_mobile_number.setVisibility(View.VISIBLE);
                }

                enableViews("mobileScreen");
            }
        });

        // otp layput
        otp_resend_tv = (TextView) root.findViewById(R.id.otp_resend_tv);
        otp_mobile_number_tv = (TextView) root.findViewById(R.id.otp_mobile_number_tv);
        next_button_otp = (Button) root.findViewById(R.id.next_button_otp);
        imageview_back_otp = (ImageView) root.findViewById(R.id.imageview_back_otp);
        otpView = root.findViewById(R.id.otp_view);

        otp_layout = (RelativeLayout) root.findViewById(R.id.otp_layout);
        et1 = root.findViewById(R.id.et1);
        et2 = root.findViewById(R.id.et2);
        et3 = root.findViewById(R.id.et3);
        et4 = root.findViewById(R.id.et4);
        et5 = root.findViewById(R.id.et5);
        et6 = root.findViewById(R.id.et6);
        //
        editTexts = new EditText[]{et1, et2, et3, et4, et5, et6};

        et1.addTextChangedListener(new PinTextWatcher(0));
        et2.addTextChangedListener(new PinTextWatcher(1));
        et3.addTextChangedListener(new PinTextWatcher(2));
        et4.addTextChangedListener(new PinTextWatcher(3));
        et5.addTextChangedListener(new PinTextWatcher(4));
        et6.addTextChangedListener(new PinTextWatcher(5));

        et1.setOnKeyListener(new PinOnKeyListener(0));
        et2.setOnKeyListener(new PinOnKeyListener(1));
        et3.setOnKeyListener(new PinOnKeyListener(2));
        et4.setOnKeyListener(new PinOnKeyListener(3));
        et5.setOnKeyListener(new PinOnKeyListener(4));
        et6.setOnKeyListener(new PinOnKeyListener(5));

        otp_resend_tv.setOnClickListener(this);
        otpView.setOtpCompletionListener(this);

        //enter name layout
        name_first_last_layout = (RelativeLayout) root.findViewById(R.id.name_first_last_layout);
        et_firstname = (EditText) root.findViewById(R.id.et_firstname);
        et_lasttname = (EditText) root.findViewById(R.id.et_lasttname);
        next_button_name = (Button) root.findViewById(R.id.next_button_name);

        next_button_name.setOnClickListener(this);
        next_button_name.setEnabled(false);
        next_button_name.setBackgroundColor(activity().getResources().getColor(R.color.new_light));


        et_firstname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                enableViews("nameScreen");
                if (et_firstname.getText().toString().startsWith(" ")) {
                    et_firstname.setText("");
                }
                String firstName = editable.toString();
                if (firstName.length() > 23) {
                    Common.getInstance().cusToast(activity(), "Your First Name has reached maximum limit of 24 characters");
                }
            }

        });
        et_lasttname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                enableViews("nameScreen");
                if (et_lasttname.getText().toString().startsWith(" ")) {
                    et_lasttname.setText("");
                }
                String lastName = editable.toString();
                if (lastName.length() > 23) {
                    Common.getInstance().cusToast(activity(), "Your Last Name has reached maximum limit of 24 characters");
                }
            }

        });


        // Create Password layout
        create_password_layout = (RelativeLayout) root.findViewById(R.id.create_password_layout);
        et_password_create = (EditText) root.findViewById(R.id.et_password_create);
        et_conf_password_create = (EditText) root.findViewById(R.id.et_conf_password_create);
        next_button_create = (Button) root.findViewById(R.id.next_button_create);
        imageView_digit = (ImageView) root.findViewById(R.id.imageView_digit);
        imageView_8charact = (ImageView) root.findViewById(R.id.imageView_8charact);
        imageView_lowercase = (ImageView) root.findViewById(R.id.imageView_lowercase);
        imageView_uppercase = (ImageView) root.findViewById(R.id.imageView_uppercase);
        imageView_specialCha = (ImageView) root.findViewById(R.id.imageView_specialCha);
        imageViewpasswordclose = (ImageView) root.findViewById(R.id.imageViewpasswordclose);
        tvCharat = (TextView) root.findViewById(R.id.tvCharat);
        tvUpperCase = (TextView) root.findViewById(R.id.tvUpperCase);
        tvLowerCase = (TextView) root.findViewById(R.id.tvLowerCase);
        tvDigit = (TextView) root.findViewById(R.id.tvDigit);
        tvSpecial = (TextView) root.findViewById(R.id.tvSpecial);

        imageView_digit.setVisibility(View.GONE);
        imageView_lowercase.setVisibility(View.GONE);
        imageView_8charact.setVisibility(View.GONE);
        imageView_uppercase.setVisibility(View.GONE);
        imageView_specialCha.setVisibility(View.GONE);
        Linear_mobile_number.setVisibility(View.VISIBLE);
        Linear_email_id.setVisibility(View.GONE);

        linear_phone_number_click.setOnClickListener(this);
        linear_email_id_click.setOnClickListener(this);
        next_button.setOnClickListener(this);
        imageview_clear_email.setOnClickListener(this);
        imageview_clear_mobile_number.setOnClickListener(this);
        next_button_otp.setOnClickListener(this);
        imageview_back_otp.setOnClickListener(this);
        next_button_create.setOnClickListener(this);
        imageViewpasswordclose.setOnClickListener(this);

        next_button_create.setEnabled(false);
        next_button_create.setBackgroundColor(activity().getResources().getColor(R.color.new_light));

        //welcome_celebKonect_layout

        welcome_celebKonect_layout = (RelativeLayout) root.findViewById(R.id.welcome_celebKonect_layout);
        next_button_welcome_celebkonect = (Button) root.findViewById(R.id.next_button_welcome_celebkonect);
        tv_welcome_username = (EditText) root.findViewById(R.id.tv_welcome_username);
        tv_welcome_change_username = (TextView) root.findViewById(R.id.tv_welcome_change_username);
        imageview_user_available = (ImageView) root.findViewById(R.id.imageview_user_available);
        imageview_refresh = (ImageView) root.findViewById(R.id.imageview_refresh);
        tv_welcome_name = (TextView) root.findViewById(R.id.tv_welcome_name);
        tv_haveReferralCode = (TextView) root.findViewById(R.id.tv_haveReferralCode);

        next_button_welcome_celebkonect.setOnClickListener(this);
        tv_welcome_change_username.setOnClickListener(this);
        imageview_refresh.setOnClickListener(this);
        tv_haveReferralCode.setOnClickListener(this);


        tv_welcome_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String userName_ = s.toString();
                if (userName_.equalsIgnoreCase(userName)) {
                    imageview_user_available.setImageDrawable(activity().getResources().getDrawable(R.drawable.check));
                    next_button_welcome_celebkonect.setEnabled(true);
                    next_button_welcome_celebkonect.setBackgroundColor(activity().getResources().getColor(R.color.skyblueNew));
                    return;
                }
                if (userName_.length() > 15) {
                    Common.getInstance().cusToast(activity(), "Your Username has reached maximum limit of 16 characters");
                }
                if (userName_.length() > 5) {
                    imageview_user_available.setVisibility(View.VISIBLE);
                    next_button_welcome_celebkonect.setEnabled(true);
                    next_button_welcome_celebkonect.setBackgroundColor(activity().getResources().getColor(R.color.skyblueNew));

                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("username", userName_);
                        jsonObject.put("mode", "userNameStatus");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    getVerifyRegisterOTPJSON(jsonObject, "userNameStatus", "null");

                } else {
                    imageview_user_available.setVisibility(View.GONE);
                    next_button_welcome_celebkonect.setEnabled(false);
                    next_button_welcome_celebkonect.setBackgroundColor(activity().getResources().getColor(R.color.new_light));
                }


            }
        });

        //Profile pic layout
        profile_pic_layout = (RelativeLayout) root.findViewById(R.id.profile_pic_layout);
        next_button_profile_pic = (Button) root.findViewById(R.id.next_button_profile_pic);
        tv_profile_pic_skip = (TextView) root.findViewById(R.id.tv_profile_pic_skip);
        imageview_pick_profile_pic = (SimpleDraweeView) root.findViewById(R.id.imageview_pick_profile_pic);
        imageview_pick_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        next_button_profile_pic.setOnClickListener(this);
        tv_profile_pic_skip.setOnClickListener(this);

        // Forgot Layout
        rlforgotOTP = (RelativeLayout) root.findViewById(R.id.rlforgotOTP);
        edt_email_mobile_number = (EditText) root.findViewById(R.id.edt_email_mobile_number);
        bt_forgotNext = (Button) root.findViewById(R.id.bt_forgotNext);
        tvsentpwdtxt = (TextView) root.findViewById(R.id.tvsentpwdtxt);
        tvForgotTitle = (TextView) root.findViewById(R.id.tvForgotTitle);
        imageview_back_forgot = (ImageView) root.findViewById(R.id.imageview_back_forgot);

        bt_forgotNext.setOnClickListener(this);
        imageview_back_forgot.setOnClickListener(this);
        bt_forgotNext.setEnabled(false);
        bt_forgotNext.setBackgroundColor(activity().getResources().getColor(R.color.new_light));
        edt_email_mobile_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                enableViews("forgotScreen");
            }
        });

        //Change Password
        rlChangePassword = (RelativeLayout) root.findViewById(R.id.rlChangePassword);
        linear_OTP_CP_thor = (LinearLayout) root.findViewById(R.id.linear_OTP_CP_thor);
        linear_old_password_CP_thor = (LinearLayout) root.findViewById(R.id.linear_old_password_CP_thor);
        llOldPassword = (LinearLayout) root.findViewById(R.id.llOldPassword);
        llOldOTP = (LinearLayout) root.findViewById(R.id.llOldOTP);
        llOTP = (LinearLayout) root.findViewById(R.id.llOTP);
        tv_old_password_thor = (TextView) root.findViewById(R.id.tv_old_password_thor);
        tv_OTP_thor = (TextView) root.findViewById(R.id.tv_OTP_thor);
        tv_old_password_thor_view = (View) root.findViewById(R.id.tv_old_password_thor_view);
        tv_OTP_thor_view = (View) root.findViewById(R.id.tv_OTP_thor_view);
        bt_checkOldPassword = (Button) root.findViewById(R.id.bt_checkOldPassword);
        bt_changeGetOTP = (Button) root.findViewById(R.id.bt_changeGetOTP);
        tvSendTo = (TextView) root.findViewById(R.id.tvSendTo);
        rlChangeViaSMS = (RelativeLayout) root.findViewById(R.id.rlChangeViaSMS);
        rlChangeViaEmail = (RelativeLayout) root.findViewById(R.id.rlChangeViaEmail);
        imageviewChangeViaSMS = (ImageView) root.findViewById(R.id.imageviewChangeViaSMS);
        imageviewChangeViaEmail = (ImageView) root.findViewById(R.id.imageviewChangeViaEmail);
        tvChangeViaSMSMobileNo = (TextView) root.findViewById(R.id.tvChangeViaSMSMobileNo);
        tvChangeViaEmailId = (TextView) root.findViewById(R.id.tvChangeViaEmailId);
        et_oldPasswordChange = (EditText) root.findViewById(R.id.et_oldPasswordChange);
        viewDivied = (View) root.findViewById(R.id.viewDivied);
        tv_OTP_thor.setTextColor(activity().getResources().getColor(R.color.dark_gray));
        tv_old_password_thor.setTextColor(activity().getResources().getColor(R.color.skyblueNew));
        tv_OTP_thor_view.setVisibility(View.GONE);
        tv_old_password_thor_view.setBackgroundColor(activity().getResources().getColor(R.color.skyblueNew));
        tv_old_password_thor_view.setVisibility(View.VISIBLE);
        llOldPassword.setVisibility(View.VISIBLE);
        bt_checkOldPassword.setOnClickListener(this);
        bt_changeGetOTP.setOnClickListener(this);
        rlChangeViaSMS.setOnClickListener(this);
        rlChangeViaEmail.setOnClickListener(this);

        linear_OTP_CP_thor.setOnClickListener(this);
        linear_old_password_CP_thor.setOnClickListener(this);
        bt_checkOldPassword.setEnabled(false);
        bt_checkOldPassword.setBackgroundColor(activity().getResources().getColor(R.color.new_light));
        if (isFromSettings) {
            if (!isPasswordVerified) {
                linear_OTP_CP_thor.setVisibility(View.VISIBLE);
                tv_OTP_thor.setTextColor(activity().getResources().getColor(R.color.skyblueNew));
                tv_old_password_thor.setTextColor(activity().getResources().getColor(R.color.dark_gray));
                tv_OTP_thor_view.setVisibility(View.VISIBLE);
                linear_old_password_CP_thor.setVisibility(View.GONE);
                viewDivied.setVisibility(View.GONE);
                llOldPassword.setVisibility(View.GONE);
                setWhichisVerified();
                llOTP.setVisibility(View.VISIBLE);

            } else {

                linear_OTP_CP_thor.setVisibility(View.VISIBLE);
                linear_old_password_CP_thor.setVisibility(View.VISIBLE);
                viewDivied.setVisibility(View.VISIBLE);
                llOldOTP.setVisibility(View.VISIBLE);
                llOldPassword.setVisibility(View.VISIBLE);
                llOTP.setVisibility(View.GONE);
            }
        }
        et_oldPasswordChange.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    bt_checkOldPassword.setEnabled(true);
                    bt_checkOldPassword.setBackgroundColor(activity().getResources().getColor(R.color.skyblueNew));

                } else {
                    bt_checkOldPassword.setEnabled(false);
                    bt_checkOldPassword.setBackgroundColor(activity().getResources().getColor(R.color.new_light));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //  Referral Code layout
        rlReferralCode = (RelativeLayout) root.findViewById(R.id.rlReferralCode);
        et_referralCode = (EditText) root.findViewById(R.id.et_referralCode);
        bt_referralCode = (Button) root.findViewById(R.id.bt_referralCode);
        tv_referralCode_skip = (TextView) root.findViewById(R.id.tv_referralCode_skip);
        imageview_back_referral = (ImageView) root.findViewById(R.id.imageview_back_referral);
        tvChangeProfilePic = (TextView) root.findViewById(R.id.tvChangeProfilePic);

        imageview_back_referral.setOnClickListener(this);
        bt_referralCode.setOnClickListener(this);
        tv_referralCode_skip.setOnClickListener(this);
        tvChangeProfilePic.setOnClickListener(this);
        bt_referralCode.setEnabled(false);
        bt_referralCode.setBackgroundColor(activity().getResources().getColor(R.color.new_light));

        et_referralCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                enableViews("referralScreen");
            }
        });

        //rlForgotUserDetails layout

        rlForgotUserDetails = (RelativeLayout) root.findViewById(R.id.rlForgotUserDetails);
        imageViewForgotUserPic = (CircleImageView) root.findViewById(R.id.imageViewForgotUserPic);
        tvForgotUserName = (TextView) root.findViewById(R.id.tvForgotUserName);
        imageviewViaSMS = (ImageView) root.findViewById(R.id.imageviewViaSMS);
        tvViaSMSMobileNo = (TextView) root.findViewById(R.id.tvViaSMSMobileNo);
        tvViaEmailId = (TextView) root.findViewById(R.id.tvViaEmailId);
        imageviewViaEmail = (ImageView) root.findViewById(R.id.imageviewViaEmail);
        rlViaEmail = (RelativeLayout) root.findViewById(R.id.rlViaEmail);
        rlViaSMS = (RelativeLayout) root.findViewById(R.id.rlViaSMS);
        btForgotDetailsNext = (Button) root.findViewById(R.id.btForgotDetailsNext);
        imageview_ForgotDetails = (ImageView) root.findViewById(R.id.imageview_ForgotDetails);


        btForgotDetailsNext.setOnClickListener(this);
        rlViaEmail.setOnClickListener(this);
        rlViaSMS.setOnClickListener(this);
        imageview_ForgotDetails.setOnClickListener(this);


        //set country code dialog box
        countryCodetv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countryListPopup();
            }
        });

        et_password_create.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.length() > 0) {
                    imageViewpasswordclose.setVisibility(View.VISIBLE);
                } else {
                    imageViewpasswordclose.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String password = editable.toString();
                char ch;
                Boolean isNumberVerify = false, isUpperCaseVerify = false, isLowercaseVerify = false, isSpeciVerify = false, isCharact = false;
                for (int i = 0; i < password.length(); i++) {
                    ch = password.charAt(i);
                    isNumberVerify = !isNumberVerify ? Character.isDigit(ch) : isNumberVerify;
                    isUpperCaseVerify = !isUpperCaseVerify ? Character.isUpperCase(ch) : isUpperCaseVerify;
                    isLowercaseVerify = !isLowercaseVerify ? Character.isLowerCase(ch) : isLowercaseVerify;

                }

                Pattern regex = Pattern.compile("[.$&+@#^*()%!_{}*]");
                if (regex.matcher(password).find()) {
                    isSpeciVerify = true;
                } else {
                    isSpeciVerify = false;
                }

                if (password.length() > 7) {
                    isCharact = true;
                } else {
                    isCharact = false;
                }

                imageView_digit.setVisibility(isNumberVerify ? View.VISIBLE : View.GONE);
                imageView_lowercase.setVisibility(isLowercaseVerify ? View.VISIBLE : View.GONE);
                imageView_uppercase.setVisibility(isUpperCaseVerify ? View.VISIBLE : View.GONE);
                imageView_specialCha.setVisibility(isSpeciVerify ? View.VISIBLE : View.GONE);
                imageView_8charact.setVisibility(isCharact ? View.VISIBLE : View.GONE);
                if (imageView_8charact.getVisibility() == View.VISIBLE && imageView_lowercase.getVisibility() == View.VISIBLE && imageView_uppercase.getVisibility() == View.VISIBLE && imageView_digit.getVisibility() == View.VISIBLE && imageView_specialCha.getVisibility() == View.VISIBLE) {
                    next_button_create.setEnabled(true);
                    next_button_create.setBackgroundColor(activity().getResources().getColor(R.color.skyblueNew));
                } else {
                    next_button_create.setEnabled(false);
                    next_button_create.setBackgroundColor(activity().getResources().getColor(R.color.new_light));
                }


                tvCharat.setTextColor(isCharact ? getResources().getColor(R.color.black) : getResources().getColor(R.color.gray));
                tvUpperCase.setTextColor(isUpperCaseVerify ? getResources().getColor(R.color.black) : getResources().getColor(R.color.gray));
                tvLowerCase.setTextColor(isLowercaseVerify ? getResources().getColor(R.color.black) : getResources().getColor(R.color.gray));
                tvDigit.setTextColor(isNumberVerify ? getResources().getColor(R.color.black) : getResources().getColor(R.color.gray));
                tvSpecial.setTextColor(isSpeciVerify ? getResources().getColor(R.color.black) : getResources().getColor(R.color.gray));


            }
        });

        if (isLogin) {
            setVisibilityForAllLayout(4);
        } else if (isForgot) {
            setVisibilityForAllLayout(6);
        } else if (isChangePassword) {
            setVisibilityForAllLayout(7);
        } else if (forOTPVerification) {
            setVisibilityForAllLayout(1);
        } else if (isSocialNetwork) {
            setVisibilityForAllLayout(2);
        } else {
            setVisibilityForAllLayout(0);
        }


    }

    public void setVisibilityForAllLayout(int i) {

        switch (i) {

            case 0: // Email Phone Layout
                email_mobile_layout.setVisibility(View.VISIBLE);
                otp_layout.setVisibility(View.GONE);
                signLayout.setVisibility(View.VISIBLE);
                create_password_layout.setVisibility(View.GONE);
                welcome_celebKonect_layout.setVisibility(View.GONE);
                rlforgotOTP.setVisibility(View.GONE);
                rlChangePassword.setVisibility(View.GONE);
                rlReferralCode.setVisibility(View.GONE);
                rlForgotUserDetails.setVisibility(View.GONE);
                Common.getInstance().hideKeyboard(activity());
                break;
            case 1: // OTP Layout

                email_mobile_layout.setVisibility(View.GONE);
                Animation slideleft = AnimationUtils.loadAnimation(activity(),
                        R.anim.slide_from_right);
                otp_layout.startAnimation(slideleft);
                otp_layout.setVisibility(View.VISIBLE);
                signLayout.setVisibility(View.GONE);
                create_password_layout.setVisibility(View.GONE);
                welcome_celebKonect_layout.setVisibility(View.GONE);
                profile_pic_layout.setVisibility(View.GONE);
                name_first_last_layout.setVisibility(View.GONE);

                if (isLogin) {
                    emailOrMobileHide(true);
                } else if (isForgot) {
                    emailOrMobileHide(true);
                } else if (isChangePassword) {
                    emailOrMobileHide(true);
                } else if (forOTPVerification) {
                    emailOrMobileHide(true);
                } else if (isSocialNetwork) {
                    emailOrMobileHide(true);
                } else {
                    emailOrMobileHide(false);
                }
                rlforgotOTP.setVisibility(View.GONE);
                rlChangePassword.setVisibility(View.GONE);
                rlReferralCode.setVisibility(View.GONE);
                rlForgotUserDetails.setVisibility(View.GONE);
                otpView.setText("");
                otpView.requestFocus();
                et1.setText("");
                et2.setText("");
                et3.setText("");
                et4.setText("");
                et5.setText("");
                et6.setText("");
                otpView.requestFocus();
                Common.getInstance().showKeyboard(activity());
                break;
            case 2: // enter name layout
                email_mobile_layout.setVisibility(View.GONE);
                slideleft = AnimationUtils.loadAnimation(activity(),
                        R.anim.slide_from_right);
                name_first_last_layout.startAnimation(slideleft);
                name_first_last_layout.setVisibility(View.VISIBLE);
                otp_layout.setVisibility(View.GONE);
                signLayout.setVisibility(View.VISIBLE);
                create_password_layout.setVisibility(View.GONE);
                welcome_celebKonect_layout.setVisibility(View.GONE);
                profile_pic_layout.setVisibility(View.GONE);
                otp_mobile_number_tv.setText(mobileOrEmailID);
                rlforgotOTP.setVisibility(View.GONE);
                rlChangePassword.setVisibility(View.GONE);
                rlReferralCode.setVisibility(View.GONE);
                rlForgotUserDetails.setVisibility(View.GONE);
                if (isSocialNetwork) {
                    et_firstname.append(firstNameSocial);
                    et_lasttname.append(lastNameSocial);
                }
                Common.getInstance().hideKeyboard(activity());
                break;
            case 3: // Create Password Layout
                et_password_create.setText("");
                Common.hideKeyboard(activity());
                email_mobile_layout.setVisibility(View.GONE);
                otp_layout.setVisibility(View.GONE);
                name_first_last_layout.setVisibility(View.GONE);

                slideleft = AnimationUtils.loadAnimation(activity(),
                        R.anim.slide_from_right);
                create_password_layout.startAnimation(slideleft);
                create_password_layout.setVisibility(View.VISIBLE);
                et_password_create.setFocusable(true);
                welcome_celebKonect_layout.setVisibility(View.GONE);
                profile_pic_layout.setVisibility(View.GONE);
                rlforgotOTP.setVisibility(View.GONE);
                rlChangePassword.setVisibility(View.GONE);
                rlReferralCode.setVisibility(View.GONE);
                rlForgotUserDetails.setVisibility(View.GONE);
                if (isChangePassword) {
                    signLayout.setVisibility(View.GONE);
                } else {
                    signLayout.setVisibility(View.VISIBLE);
                }
                Common.getInstance().hideKeyboard(activity());
                break;
            case 4: // welcome_celebKonect_layout
                Common.hideKeyboard(activity());
                email_mobile_layout.setVisibility(View.GONE);
                otp_layout.setVisibility(View.GONE);
                name_first_last_layout.setVisibility(View.GONE);
                signLayout.setVisibility(View.VISIBLE);
                create_password_layout.setVisibility(View.GONE);
                slideleft = AnimationUtils.loadAnimation(activity(),
                        R.anim.slide_from_right);
                welcome_celebKonect_layout.startAnimation(slideleft);
                welcome_celebKonect_layout.setVisibility(View.VISIBLE);
                profile_pic_layout.setVisibility(View.GONE);
                tv_welcome_username.setText("");
                tv_welcome_username.append(userName);
                tv_welcome_username.setEnabled(false);
                if (isLogin) {
                    tv_welcome_name.setText("Welcome , " + firstName);

                } else {
                    tv_welcome_name.setText("Welcome , " + et_firstname.getText().toString());
                }
                rlforgotOTP.setVisibility(View.GONE);
                rlChangePassword.setVisibility(View.GONE);
                rlReferralCode.setVisibility(View.GONE);
                rlForgotUserDetails.setVisibility(View.GONE);
                Common.getInstance().hideKeyboard(activity());
                break;
            case 5: // profile pic layout
                email_mobile_layout.setVisibility(View.GONE);
                otp_layout.setVisibility(View.GONE);
                signLayout.setVisibility(View.GONE);
                name_first_last_layout.setVisibility(View.GONE);
                create_password_layout.setVisibility(View.GONE);
                welcome_celebKonect_layout.setVisibility(View.GONE);
                slideleft = AnimationUtils.loadAnimation(activity(),
                        R.anim.slide_from_right);
                profile_pic_layout.startAnimation(slideleft);
                profile_pic_layout.setVisibility(View.VISIBLE);
                rlforgotOTP.setVisibility(View.GONE);
                rlChangePassword.setVisibility(View.GONE);
                rlReferralCode.setVisibility(View.GONE);
                rlForgotUserDetails.setVisibility(View.GONE);
                Common.hideKeyboard(activity());
                break;
            case 6: // Forgotlayout
                email_mobile_layout.setVisibility(View.GONE);
                otp_layout.setVisibility(View.GONE);
                signLayout.setVisibility(View.GONE);
                name_first_last_layout.setVisibility(View.GONE);
                create_password_layout.setVisibility(View.GONE);
                welcome_celebKonect_layout.setVisibility(View.GONE);
                slideleft = AnimationUtils.loadAnimation(activity(),
                        R.anim.slide_from_right);
                rlforgotOTP.startAnimation(slideleft);
                profile_pic_layout.setVisibility(View.GONE);
                rlforgotOTP.setVisibility(View.VISIBLE);
                rlChangePassword.setVisibility(View.GONE);
                rlReferralCode.setVisibility(View.GONE);
                rlForgotUserDetails.setVisibility(View.GONE);
                Common.getInstance().hideKeyboard(activity());
                break;
            case 7: // Change Password layout
                email_mobile_layout.setVisibility(View.GONE);
                otp_layout.setVisibility(View.GONE);
                signLayout.setVisibility(View.GONE);
                name_first_last_layout.setVisibility(View.GONE);
                create_password_layout.setVisibility(View.GONE);
                welcome_celebKonect_layout.setVisibility(View.GONE);
                slideleft = AnimationUtils.loadAnimation(activity(),
                        R.anim.slide_from_right);
                rlChangePassword.startAnimation(slideleft);
                profile_pic_layout.setVisibility(View.GONE);
                rlforgotOTP.setVisibility(View.GONE);
                rlChangePassword.setVisibility(View.VISIBLE);
                rlReferralCode.setVisibility(View.GONE);
                rlForgotUserDetails.setVisibility(View.GONE);
                Common.getInstance().hideKeyboard(activity());
                break;

            case 8: // Referral Code layout
                email_mobile_layout.setVisibility(View.GONE);
                otp_layout.setVisibility(View.GONE);
                signLayout.setVisibility(View.GONE);
                name_first_last_layout.setVisibility(View.GONE);
                create_password_layout.setVisibility(View.GONE);
                welcome_celebKonect_layout.setVisibility(View.GONE);
                slideleft = AnimationUtils.loadAnimation(activity(),
                        R.anim.slide_from_right);
                rlReferralCode.startAnimation(slideleft);
                profile_pic_layout.setVisibility(View.GONE);
                rlforgotOTP.setVisibility(View.GONE);
                rlChangePassword.setVisibility(View.GONE);
                rlReferralCode.setVisibility(View.VISIBLE);
                rlForgotUserDetails.setVisibility(View.GONE);
                Common.getInstance().hideKeyboard(activity());
                break;
            case 9: // rlForgotUserDetails layout
                email_mobile_layout.setVisibility(View.GONE);
                otp_layout.setVisibility(View.GONE);
                signLayout.setVisibility(View.GONE);
                name_first_last_layout.setVisibility(View.GONE);
                create_password_layout.setVisibility(View.GONE);
                welcome_celebKonect_layout.setVisibility(View.GONE);
                slideleft = AnimationUtils.loadAnimation(activity(),
                        R.anim.slide_from_right);
                rlForgotUserDetails.startAnimation(slideleft);
                profile_pic_layout.setVisibility(View.GONE);
                rlforgotOTP.setVisibility(View.GONE);
                rlChangePassword.setVisibility(View.GONE);
                rlReferralCode.setVisibility(View.GONE);
                rlForgotUserDetails.setVisibility(View.VISIBLE);
                Common.getInstance().hideKeyboard(activity());
                break;
        }
    }

    @Override
    public void onClickCountry(CountryData countryData, int position) {

        minlenght = countryData.getMinLength();
        maxlength = countryData.getMaxlength();
    }

    @Override
    public void onOtpCompleted(String otp) {
        verifyOTP(isForgot, isChangePassword, forOTPVerification, otp);
    }


    public class PinTextWatcher implements TextWatcher {

        private int currentIndex;
        private boolean isFirst = false, isLast = false;
        private String newTypedString = "";

        PinTextWatcher(int currentIndex) {
            this.currentIndex = currentIndex;

            if (currentIndex == 0)
                this.isFirst = true;
            else if (currentIndex == editTexts.length - 1)
                this.isLast = true;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            newTypedString = s.subSequence(start, start + count).toString().trim();
        }

        @Override
        public void afterTextChanged(Editable s) {
            String text = newTypedString;
            /* Detect paste event and set first char */
            if (text.length() > 1)
                text = String.valueOf(text.charAt(0));

            editTexts[currentIndex].removeTextChangedListener(this);
            editTexts[currentIndex].setText(text);
            editTexts[currentIndex].setSelection(text.length());
            editTexts[currentIndex].addTextChangedListener(this);
            if (text.length() == 1)
                moveToNext();
            else if (text.length() == 0)
                moveToPrevious();
        }

        private void moveToNext() {
            if (!isLast)
                editTexts[currentIndex + 1].requestFocus();

            if (isAllEditTextsFilled() && isLast) {
                editTexts[currentIndex].clearFocus();
                hideKeyboard();
                //  verifyOTP(isForgot, isChangePassword, forOTPVerification);
            }
        }

        private void moveToPrevious() {
            if (!isFirst)
                editTexts[currentIndex - 1].requestFocus();
        }

        private boolean isAllEditTextsFilled() {
            for (EditText editText : editTexts)
                if (editText.getText().toString().trim().length() == 0)
                    return false;
            return true;
        }

        private void hideKeyboard() {
            if (getActivity().getCurrentFocus() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
            }
        }

    }

    public class PinOnKeyListener implements View.OnKeyListener {

        private int currentIndex;

        PinOnKeyListener(int currentIndex) {
            this.currentIndex = currentIndex;
        }

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                if (editTexts[currentIndex].getText().toString().isEmpty() && currentIndex != 0)
                    editTexts[currentIndex - 1].requestFocus();
            }
            return false;
        }

    }

    private void forceClosePopup(final String forceMsg, final Boolean isSocialLogin) {
        final Dialog dialogForceClose;
        TextView take_photo_txt;
        Button yesBtn, noBtn;
        dialogForceClose = new Dialog(activity());
        dialogForceClose.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogForceClose.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogForceClose.setCancelable(false);
        dialogForceClose.setContentView(R.layout.force_to_close_popup);
        take_photo_txt = (TextView) dialogForceClose.findViewById(R.id.greetingmsgtxt);
        if (forceMsg != null && !forceMsg.isEmpty()) {
            take_photo_txt.setText(Constants.FORCED_LOGOUT + "");
        } else {
            take_photo_txt.setText("");
        }
        noBtn = (Button) dialogForceClose.findViewById(R.id.noBtn);
        yesBtn = (Button) dialogForceClose.findViewById(R.id.yesBtn);
        dialogForceClose.show();
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogForceClose.dismiss();
            }
        });
        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogForceClose.dismiss();
                login(true);
            }
        });
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signLayout:
                Intent intent = new Intent(activity(), SignInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.linear_phone_number_click:
                email_et.setText("");
                Common.hideKeyboard(activity());
                textview_verifiy_send.setText("You will receive a verification code on this Number");
                Animation slideright = AnimationUtils.loadAnimation(activity(),
                        R.anim.slide_from_left);
                Linear_mobile_number.startAnimation(slideright);
                Linear_mobile_number.setVisibility(View.VISIBLE);
                Linear_email_id.setVisibility(View.GONE);
                email_tv.setTextColor(activity().getResources().getColor(R.color.dark_gray));
                phone_tv.setTextColor(activity().getResources().getColor(R.color.skyblueNew));
                email_tv_view.setVisibility(View.GONE);
                phone_tv_view.setBackgroundColor(activity().getResources().getColor(R.color.skyblueNew));
                phone_tv_view.setVisibility(View.VISIBLE);
                break;
            case R.id.linear_email_id_click:
                mobile_et.setText("");
                Common.hideKeyboard(activity());
                textview_verifiy_send.setText("You will receive a verification code on this Email");
                Linear_mobile_number.setVisibility(View.GONE);
                Animation slideleft = AnimationUtils.loadAnimation(activity(),
                        R.anim.slide_from_right);
                Linear_email_id.startAnimation(slideleft);
                Linear_email_id.setVisibility(View.VISIBLE);
                email_tv.setTextColor(activity().getResources().getColor(R.color.skyblueNew));
                phone_tv.setTextColor(activity().getResources().getColor(R.color.dark_gray));
                email_tv_view.setBackgroundColor(activity().getResources().getColor(R.color.skyblueNew));
                phone_tv_view.setVisibility(View.GONE);
                email_tv_view.setVisibility(View.VISIBLE);
                break;
            case R.id.next_button:
                if (!mobile_et.getText().toString().trim().isEmpty()) {
                    if (isValidMobilelenght(mobile_et.getText().toString())) {
                        getOTP();
                    } else {
                        Common.getInstance().cusToast(activity(), "Looks like your phone number may be incorrect");
                    }
                } else {
                    getOTP();
                }


                break;
            case R.id.imageview_clear_mobile_number:
                mobile_et.setText("");
                break;
            case R.id.imageview_clear_email:
                email_et.setText("");
                break;
            case R.id.otp_resend_tv:
                otpView.setText("");
                otpView.requestFocus();
                et1.requestFocus();
                et1.setText("");
                et2.setText("");
                et3.setText("");
                et4.setText("");
                et5.setText("");
                et6.setText("");

                if (isForgot) {
                    getOTPForgot();
                } else {
                    getOTP();
                }
                break;
            case R.id.next_button_otp:
                //  verifyOTP(isForgot, isChangePassword, forOTPVerification);
                break;
            case R.id.imageview_back_otp:
                otpView.setText("");
                otpView.requestFocus();
                et1.requestFocus();
                et1.setText("");
                et2.setText("");
                et3.setText("");
                et4.setText("");
                et5.setText("");
                et6.setText("");
                if (isForgot) {
                    setVisibilityForAllLayout(9);
                } else if (isChangePassword) {
                    setVisibilityForAllLayout(7);
                } else if (forOTPVerification) {
                    activity().finish();
                } else {
                    setVisibilityForAllLayout(0);
                }
                break;
            case R.id.next_button_name:

                if (!et_firstname.getText().toString().isEmpty()) {
                    if (!et_lasttname.getText().toString().isEmpty()) {
                        if (isSocialNetwork) {
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("medium", "email");
                                jsonObject.put("loginType", "socialLogin");
                                jsonObject.put("email", socialNetworkEmailID);
                                jsonObject.put("firstName", Common.getInstance().convertFirstLetterToCapital(et_firstname.getText().toString().trim()));
                                jsonObject.put("lastName", Common.getInstance().convertFirstLetterToCapital(et_lasttname.getText().toString().trim()));
                                jsonObject.put("mode", "memberRegister");

                                jsonObject.put("socialMediaType", socialMediaType);
                                //Added Location Info
                                jsonObject.put("location", new JSONObject(Common.getInstance()
                                        .getCompleteAddress(getContext()).toString()));


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            getVerifyRegisterOTPJSON(jsonObject, "socialNetworkReg", "null");

                        } else {
                            setVisibilityForAllLayout(3);
                        }

                    } else {
                        Common.getInstance().showSweetAlertWarning(activity(), "CelebKonect",
                                "Please enter Last Name ");
                    }
                } else {
                    Common.getInstance().showSweetAlertWarning(activity(), "CelebKonect",
                            "Please enter First Name");
                }

                break;
            case R.id.next_button_create:
                if (!et_password_create.getText().toString().isEmpty()) {
                    if (et_password_create.getText().toString().length() < 8) {
                        Common.getInstance().cusToast(activity(), "Password length should 8");
                        return;
                    }
                    if (imageView_8charact.getVisibility() == View.VISIBLE && imageView_lowercase.getVisibility() == View.VISIBLE && imageView_uppercase.getVisibility() == View.VISIBLE && imageView_digit.getVisibility() == View.VISIBLE && imageView_specialCha.getVisibility() == View.VISIBLE) {
                        Common.hideKeyboard(activity());
                        tv_welcome_username.setEnabled(false);
                        password = et_password_create.getText().toString().trim();
                        if (isForgot) {
                            JSONObject jsonObject = new JSONObject();
                            try {
                                if (imageviewViaEmail.getVisibility() == View.VISIBLE) {
                                    jsonObject.put("emailOrMobileNumber", userDetails.email);
                                    mobileOrEmailID = userDetails.email;
                                } else if (imageviewViaSMS.getVisibility() == View.VISIBLE) {
                                    jsonObject.put("emailOrMobileNumber", userDetails.mobile);
                                    mobileOrEmailID = userDetails.mobile;
                                }
                                jsonObject.put("newPassword", password);
                                jsonObject.put("mode", "setPassword");
                                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                                Call<ApiResponseModel> call = apiInterface.forgotGetOTP(RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                                        jsonObject.toString()));

                                Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, "passwordCondition", true, iApiListener, true));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else if (isChangePassword) {
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("newPassword", password);
                                jsonObject.put("byOtp", false);
                                jsonObject.put("mode", "setPassword");
                                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                                Call<ApiResponseModel> call = apiInterface.changePassword(SessionManager.userLogin.userId,
                                        RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString()));
                                Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call,
                                        "passwordCondition", true, iApiListener, true));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            next_button_welcome_celebkonect.setEnabled(false);
                            next_button_welcome_celebkonect.setBackgroundColor(activity().getResources().getColor(R.color.new_light));
                            //   setVisibilityForAllLayout(4);
                            if (isUsingMobile) {
                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonObject.put("medium", "mobile");
                                    jsonObject.put("mobileNumber", mobile_et.getText().toString().trim());
                                    jsonObject.put("country", searchCode);
                                    jsonObject.put("firstName", Common.getInstance().convertFirstLetterToCapital(et_firstname.getText().toString()));
                                    jsonObject.put("lastName", Common.getInstance().convertFirstLetterToCapital(et_lasttname.getText().toString()));
                                    jsonObject.put("password", password);
                                    jsonObject.put("loginType", "app");
                                    jsonObject.put("mode", "memberRegister");

                                    //Added Location Info
                                    jsonObject.put("location", new JSONObject(Common.getInstance()
                                            .getCompleteAddress(getContext()).toString()));

                                    Log.e("sentData", jsonObject.toString());

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                getVerifyRegisterOTPJSON(jsonObject, "passwordCondition", "null"); //Shiva

                                 /* SignupData signupData = new SignupData("mobile",
                                        mobile_et.getText().toString().trim(), searchCode,
                                        Common.getInstance().convertFirstLetterToCapital(et_firstname.getText().toString()),
                                        Common.getInstance().convertFirstLetterToCapital(et_lasttname.getText().toString()),
                                        password, "app", "memberRegister", Common.getInstance()
                                        .getCompleteAddress(getContext()));*/
//                                getVerifyRegisterOTPJSONNew(signupData, "passwordCondition", "null"); //Shiva


                            } else {
                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonObject.put("medium", "email");
                                    jsonObject.put("email", email_et.getText().toString().trim());
                                    jsonObject.put("firstName", Common.getInstance().convertFirstLetterToCapital(et_firstname.getText().toString()));
                                    jsonObject.put("lastName", Common.getInstance().convertFirstLetterToCapital(et_lasttname.getText().toString()));
                                    jsonObject.put("password", password);
                                    jsonObject.put("loginType", "app");
                                    jsonObject.put("mode", "memberRegister");

                                    //Added Location Info
                                    jsonObject.put("location", new JSONObject(Common.getInstance()
                                            .getCompleteAddress(getContext()).toString()));
                                    Log.e("sentData", jsonObject.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                getVerifyRegisterOTPJSON(jsonObject, "passwordCondition", "null"); //Shiva

                               /* SignupData signupData = new SignupData("email",
                                        email_et.getText().toString().trim(), searchCode,
                                        Common.getInstance().convertFirstLetterToCapital(et_firstname.getText().toString()),
                                        Common.getInstance().convertFirstLetterToCapital(et_lasttname.getText().toString()),
                                        password, "app", "memberRegister", Common.getInstance()
                                        .getCompleteAddress(getContext()), true);


                                getVerifyRegisterOTPJSONNew(signupData, "passwordCondition", "null");*/
                            }
                        }
                    } else {
                        Common.getInstance().cusToast(activity(), "Please enter valid password !");
                    }
                } else {
                    Common.getInstance().cusToast(activity(), "Please enter password");
                }
                break;
            case R.id.imageViewpasswordclose:
                et_password_create.setText("");
                imageView_digit.setVisibility(View.GONE);
                imageView_specialCha.setVisibility(View.GONE);
                imageView_lowercase.setVisibility(View.GONE);
                imageView_8charact.setVisibility(View.GONE);
                imageView_uppercase.setVisibility(View.GONE);
                break;
            case R.id.next_button_welcome_celebkonect:
                /*if (isUsingMobile) {
                    RegistrationMember registrationMember = new RegistrationMember("mobile", mobile_et.getText().toString().trim(), countryCode, ""
                            , tv_welcome_username.getText().toString().trim(), "memberRegister", password,et_firstname.getText().toString(),et_lasttname.getText().toString());
                    getVerifyRegisterOTP(registrationMember, "memberRegister", userId);
                } else {
                    RegistrationMember registrationMember = new RegistrationMember("email", email_et.getText().toString().trim(), "", tv_welcome_username.getText().toString().trim(), "memberRegister", password,et_firstname.getText().toString(),et_lasttname.getText().toString());
                    getVerifyRegisterOTP(registrationMember, "memberRegister", userId);
                }*/

                //change flow
              /*  if (!tv_welcome_username.getText().toString().isEmpty()) {
                    userName = tv_welcome_username.getText().toString();
                    setVisibilityForAllLayout(8);
                }*/

                if (!tv_welcome_username.getText().toString().isEmpty()) {
                    userName = tv_welcome_username.getText().toString();
                } else {
                    Common.getInstance().cusToast(activity(), "Enter Username");
                    return;
                }

                Profile profile = new Profile(userName, true);

//                //Adding Location Key
//                Profile profile = new Profile(userName, true, Common.getInstance().getCompleteAddress(getContext()));

                RegistrationMemberImage registrationMemberimage = new RegistrationMemberImage(profile, true);
                getVerifyRegisterOTP(registrationMemberimage, "userNameCondition", userId);
                break;
            case R.id.tv_welcome_change_username:
                tv_welcome_username.setBackgroundTintList(activity().getResources().getColorStateList(R.color.skyblueNew));
                tv_welcome_change_username.setVisibility(View.GONE);
                tv_welcome_username.setEnabled(true);
                tv_welcome_username.requestFocus();
                imageview_refresh.setVisibility(View.VISIBLE);
                break;
            case R.id.imageview_refresh:

                tv_welcome_username.setText("");
                tv_welcome_username.append(userName);
                tv_welcome_username.setEnabled(false);
                tv_welcome_change_username.setVisibility(View.VISIBLE);
                break;
            case R.id.next_button_profile_pic:

                if (next_button_profile_pic.getText().toString().equalsIgnoreCase("Next")) {
                    if (imageUrl != null && !imageUrl.isEmpty()) {
                        try {
                            file = new File(imageUrl);
                            File compressedImageFile = new Compressor(activity()).compressToFile(file);
                            cbFile = new FileBody(compressedImageFile, "image/jpeg");
                            String size = String.format("Size : %s", Common.getReadableFileSize(compressedImageFile.length()));
                            Log.d("filesize", size);

                            profile = new Profile(userName, false);

//                            //Adding Location Key
//                            profile = new Profile(userName, false, Common.getInstance().getCompleteAddress(getContext()));

                            registrationMemberimage = new RegistrationMemberImage(profile, compressedImageFile, image_ratio);
                            getVerifyRegisterOTP(registrationMemberimage, "profilePic", userId);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Common.getInstance().cusToast(getContext(), "Please Select Image");
                        return;
                    }
                } else {
                    selectImage();
                }
                break;
            case R.id.tv_profile_pic_skip: //Check For New
                isProfileSkip = true;

                login(false);

                break;
            case R.id.imageview_pick_profile_pic:
                selectImage();
                break;
            case R.id.bt_forgotNext:
                Common.hideKeyboard(activity());

                if (edt_email_mobile_number.getText().toString().length() != 0) {
                    if (isValidEmail(edt_email_mobile_number.getText().toString())) {
                        if (!TextUtils.isEmpty(edt_email_mobile_number.getText().toString())) {
                            getForgotDetails();
                            isEmailID = true;
                        }
                    } else if (edt_email_mobile_number.getText().toString().matches("[0-9]+")) {
                        if (edt_email_mobile_number.getText().toString().length() > 6) {
                            getForgotDetails();
                            isEmailID = false;
                        } else {
                            Common.getInstance().cusToast(activity(), "Invalid  Email/Phone Number/User name does not exist");
                        }
                    } else {
                        getForgotDetails();
                    }
                } else {

                    Common.getInstance().cusToast(activity(), "Please enter registered Email id / Mobile Number /Username");
//                    Common.getInstance().showSweetAlertWarning(activity(), "CelebKonect", "Please enter \nregistered email id");

                }
                break;
            case R.id.imageview_back_forgot:

                intent = new Intent(activity(), SignInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                activity().finish();

                break;
            case R.id.linear_OTP_CP_thor:
                if (!isPasswordVerified) {
                    return;
                }
                llOldPassword.setVisibility(View.GONE);
                slideright = AnimationUtils.loadAnimation(activity(),
                        R.anim.slide_from_left);
                llOTP.startAnimation(slideright);
                llOTP.setVisibility(View.VISIBLE);
                tv_OTP_thor.setTextColor(activity().getResources().getColor(R.color.skyblueNew));
                tv_old_password_thor.setTextColor(activity().getResources().getColor(R.color.dark_gray));
                tv_OTP_thor_view.setVisibility(View.VISIBLE);
                tv_OTP_thor_view.setBackgroundColor(activity().getResources().getColor(R.color.skyblueNew));
                tv_old_password_thor_view.setVisibility(View.GONE);

                setWhichisVerified();

                break;
            case R.id.linear_old_password_CP_thor:

                llOTP.setVisibility(View.GONE);
                slideright = AnimationUtils.loadAnimation(activity(),
                        R.anim.slide_from_right);
                llOldPassword.startAnimation(slideright);
                llOldPassword.setVisibility(View.VISIBLE);

                tv_OTP_thor.setTextColor(activity().getResources().getColor(R.color.dark_gray));
                tv_old_password_thor.setTextColor(activity().getResources().getColor(R.color.skyblueNew));
                tv_OTP_thor_view.setVisibility(View.GONE);
                tv_old_password_thor_view.setBackgroundColor(activity().getResources().getColor(R.color.skyblueNew));
                tv_old_password_thor_view.setVisibility(View.VISIBLE);
                break;

            case R.id.bt_checkOldPassword:

                if (!et_oldPasswordChange.getText().toString().isEmpty()) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("oldPassword", et_oldPasswordChange.getText().toString());
                        jsonObject.put("byOtp", false);
                        jsonObject.put("mode", "");
                        apiInterface = ApiClient.getClient().create(ApiInterface.class);
                        Call<ApiResponseModel> call = apiInterface.changePassword(SessionManager.userLogin.userId, RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                                jsonObject.toString()));

                        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, "checkPassword", true, iApiListener, true));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Common.getInstance().cusToast(activity(), "Please Enter Current Password");
                }

                break;
            case R.id.bt_changeGetOTP:

                JSONObject jsonObject = new JSONObject();
                try {

                    if (imageviewChangeViaEmail.getVisibility() == View.VISIBLE) {
                        jsonObject.put("medium", "email");
                        mobileOrEmailID = SessionManager.getInstance().getKeyValue(SessionManager.KEY_EMAIL_OR_MOBILE, "");
                    } else {
                        jsonObject.put("medium", "mobile");
                        mobileOrEmailID = SessionManager.getInstance().getKeyValue(SessionManager.KEY_MOBILE_NO, "");
                    }
                    jsonObject.put("byOtp", true);
                    jsonObject.put("mode", "getOTP");
                    apiInterface = ApiClient.getClient().create(ApiInterface.class);
                    Call<ApiResponseModel> call = apiInterface.changePassword(SessionManager.userLogin.userId, RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                            jsonObject.toString()));

                    Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, "getOTP", true, iApiListener, true));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.bt_referralCode:

                if (!tv_welcome_username.getText().toString().isEmpty()) {
                    userName = tv_welcome_username.getText().toString();
                }
                if (!et_referralCode.getText().toString().isEmpty()) {
                    isReferralCodeAvailable = true;
                    referralCode = et_referralCode.getText().toString();
                    userName = tv_welcome_username.getText().toString();


                    profile = new Profile(userName, true);

//                    //Adding Location Key
//                    profile = new Profile(userName, true, Common.getInstance().getCompleteAddress(getContext()));


                    registrationMemberimage = new RegistrationMemberImage(profile, true);
                    getVerifyRegisterOTP(registrationMemberimage, "referralCodeCondition", userId);
                } else {
                    Common.getInstance().showSweetAlertWarning(activity(), "CelebKonect", "Enter Referral Code");
                }
                break;
            case R.id.tv_referralCode_skip:
                isReferralCodeAvailable = false;
                setVisibilityForAllLayout(5);
                Common.hideKeyboard(activity());
                break;
            case R.id.imageview_back_referral:
                isReferralCodeAvailable = false;
                setVisibilityForAllLayout(4);
                break;
            case R.id.tv_haveReferralCode:

                // setVisibilityForAllLayout(8);
                break;
            case R.id.tvChangeProfilePic:
                selectImage();
                break;
            case R.id.btForgotDetailsNext:

                getOTPForgot();

                break;
            case R.id.rlViaEmail:

                imageviewViaSMS.setVisibility(View.GONE);
                imageviewViaEmail.setVisibility(View.VISIBLE);

                break;
            case R.id.rlViaSMS:
                imageviewViaSMS.setVisibility(View.VISIBLE);
                imageviewViaEmail.setVisibility(View.GONE);
                break;
            case R.id.rlChangeViaEmail:

                imageviewChangeViaSMS.setVisibility(View.GONE);
                imageviewChangeViaEmail.setVisibility(View.VISIBLE);

                break;
            case R.id.rlChangeViaSMS:
                imageviewChangeViaSMS.setVisibility(View.VISIBLE);
                imageviewChangeViaEmail.setVisibility(View.GONE);
                break;
            case R.id.imageview_ForgotDetails:
                tvViaEmailId.setText("");
                tvViaSMSMobileNo.setText("");
                tvForgotUserName.setText("");
                imageViewForgotUserPic.setImageDrawable(getResources().getDrawable(R.drawable.ic_profile_circle_pleace_holder));
                setVisibilityForAllLayout(6);
                break;


        }
    }

    private void setWhichisVerified() {

        if (IS_EMAIL_VERIFIED && IS_MOBILE_VERIFIED) {
            rlChangeViaSMS.setVisibility(View.VISIBLE);
            rlChangeViaEmail.setVisibility(View.VISIBLE);
            imageviewChangeViaSMS.setVisibility(View.VISIBLE);
            imageviewChangeViaEmail.setVisibility(View.GONE);
            String string = "";
            string = SessionManager.getInstance().getKeyValue(SessionManager.KEY_EMAIL_OR_MOBILE, "");
            String emailStarting = string;
            String emailEnding = string;
            if (string.length() > 3) {
                emailStarting = string.substring(0, 3);
                emailEnding = string.substring(string.length() - 3, string.length());
            }
            String stringMobile = "";
            stringMobile = SessionManager.getInstance().getKeyValue(SessionManager.KEY_MOBILE_NO, "");
            String mobileNumberStarting = stringMobile;
            String mobileNumberEnding = stringMobile;
            if (stringMobile.length() > 3) {
                mobileNumberStarting = stringMobile.substring(0, 3);
                mobileNumberEnding = stringMobile.substring(stringMobile.length() - 3, stringMobile.length());
            }

            tvChangeViaEmailId.setText("Send OTP to " + emailStarting + "*****" + emailEnding);
            tvChangeViaSMSMobileNo.setText("Send OTP to " + mobileNumberStarting + "*****" + mobileNumberEnding);

        } else if (IS_EMAIL_VERIFIED) {

            rlChangeViaSMS.setVisibility(View.GONE);
            rlChangeViaEmail.setVisibility(View.VISIBLE);
            imageviewChangeViaSMS.setVisibility(View.GONE);
            imageviewChangeViaEmail.setVisibility(View.VISIBLE);
            String string = " ";
            string = SessionManager.getInstance().getKeyValue(SessionManager.KEY_EMAIL_OR_MOBILE, "");
            String mobileNumberStarting = string;
            String mobileNumberEnding = string;
            if (string.length() != 0) {
                if (string.length() > 3) {
                    mobileNumberStarting = string.substring(0, 3);
                    mobileNumberEnding = string.substring(string.length() - 3, string.length());
                }
            }

            tvChangeViaEmailId.setText("Send OTP to " + mobileNumberStarting + "*****" + mobileNumberEnding);
        } else if (IS_MOBILE_VERIFIED) {
            rlChangeViaSMS.setVisibility(View.VISIBLE);
            rlChangeViaEmail.setVisibility(View.GONE);
            imageviewChangeViaSMS.setVisibility(View.VISIBLE);
            imageviewChangeViaEmail.setVisibility(View.GONE);
            String string = " ";
            string = SessionManager.getInstance().getKeyValue(SessionManager.KEY_MOBILE_NO, "");
            String mobileNumberStarting = string;
            String mobileNumberEnding = string;
            if (string.length() != 0) {
                if (string.length() > 3) {
                    mobileNumberStarting = string.substring(0, 3);
                    mobileNumberEnding = string.substring(string.length() - 3, string.length());
                }
            }
            tvChangeViaSMSMobileNo.setText("Send OTP to " + mobileNumberStarting + "*****" + mobileNumberEnding);
        }
    }

    private void getForgotDetails() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("emailOrMobileNumber", edt_email_mobile_number.getText().toString());
            jsonObject.put("mode", "getUserDetails");
            apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<ApiResponseModel> call = apiInterface.forgotGetOTP(RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    jsonObject.toString()));

            Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, "getUserDetails", true, iApiListener, true));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void verifyOTP(boolean isForgot, boolean isChangePassword, boolean forOTPVerification, String otp) {
     /*   String otp = et1.getText().toString().trim() + et2.getText().toString().trim() +
                et3.getText().toString().trim() + et4.getText().toString().trim() +
                et5.getText().toString().trim() + et6.getText().toString().trim();*/
        if (isForgot) {
            JSONObject jsonObject = new JSONObject();

            try {
                if (imageviewViaEmail.getVisibility() == View.VISIBLE) {
                    jsonObject.put("emailOrMobileNumber", userDetails.email);
                    jsonObject.put("medium", "email");
                } else if (imageviewViaSMS.getVisibility() == View.VISIBLE) {
                    jsonObject.put("emailOrMobileNumber", userDetails.mobile);
                    jsonObject.put("medium", "mobile");
                }
                jsonObject.put("mode", "verifyOTP");
                jsonObject.put("OTP", otp);
                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<ApiResponseModel> call = apiInterface.forgotGetOTP(RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                        jsonObject.toString()));

                Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, "verifyOTPForgot", true, iApiListener, true));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else if (isChangePassword) {

            JSONObject jsonObject = new JSONObject();
            try {

                if (imageviewChangeViaEmail.getVisibility() == View.VISIBLE) {
                    jsonObject.put("medium", "email");
                } else {
                    jsonObject.put("medium", "mobile");
                }

                jsonObject.put("byOtp", true);
                jsonObject.put("mode", "verifyOTP");
                jsonObject.put("OTP", otp);
                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<ApiResponseModel> call = apiInterface.changePassword(SessionManager.userLogin.userId, RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                        jsonObject.toString()));

                Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, "verifyOTP", true, iApiListener, true));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else if (forOTPVerification) {
            JSONObject jsonObject = new JSONObject();
            try {
                if (isMobile) {
                    jsonObject.put("mobileNumber", EmailOrMobile);
                    jsonObject.put("medium", "mobile");
                    jsonObject.put("country", countryCodeEditProfile);
                } else {
                    jsonObject.put("email", EmailOrMobile);
                    jsonObject.put("medium", "email");
                }
                jsonObject.put("mode", "verifyOTP");

                jsonObject.put("OTP", otp);
                getVerifyRegisterOTPJSON(jsonObject, "verifyOTPEditProfile", "null");

            } catch (JSONException e) {
                e.printStackTrace();
            }


        } else {
            if (isUsingMobile) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("medium", "mobile");
                    jsonObject.put("mobileNumber", mobile_et.getText().toString().trim());
                    jsonObject.put("mode", "verifyOTP");
                    jsonObject.put("OTP", otp);
                    jsonObject.put("country", searchCode);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getVerifyRegisterOTPJSON(jsonObject, "verifyOTP", "null");

            } else {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("medium", "email");
                    jsonObject.put("email", email_et.getText().toString().trim());
                    jsonObject.put("mode", "verifyOTP");
                    jsonObject.put("OTP", otp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getVerifyRegisterOTPJSON(jsonObject, "verifyOTP", "null");

            }
        }
    }

    private void getOTP() {
        if (isChangePassword) {
            JSONObject jsonObject = new JSONObject();
            try {

                if (imageviewChangeViaEmail.getVisibility() == View.VISIBLE) {
                    jsonObject.put("medium", "email");
                    mobileOrEmailID = SessionManager.getInstance().getKeyValue(SessionManager.KEY_EMAIL_OR_MOBILE, "");
                } else {
                    jsonObject.put("medium", "mobile");
                    mobileOrEmailID = SessionManager.getInstance().getKeyValue(SessionManager.KEY_MOBILE_NO, "");
                }
                jsonObject.put("byOtp", true);
                jsonObject.put("mode", "getOTP");
                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<ApiResponseModel> call = apiInterface.changePassword(SessionManager.userLogin.userId, RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                        jsonObject.toString()));

                Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, "getOTP", true, iApiListener, true));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (forOTPVerification) {
            JSONObject jsonObject = new JSONObject();
            try {
                if (isMobile) {
                    jsonObject.put("mobileNumber", EmailOrMobile);
                    jsonObject.put("medium", "mobile");
                    jsonObject.put("country", countryCodeEditProfile);
                } else {
                    jsonObject.put("email", EmailOrMobile);
                    jsonObject.put("medium", "email");
                }
                jsonObject.put("mode", "getOTP");

                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<ApiResponseModel> call = apiInterface.memberRegistrationNewSingUpJSON("null", RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                        jsonObject.toString()));

                Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, "getOTP", true, iApiListener, true));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            if (!mobile_et.getText().toString().trim().isEmpty()) {
                isUsingMobile = true;
                mobileOrEmailID = searchCode + " " + mobile_et.getText().toString().trim();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("medium", "mobile");
                    jsonObject.put("mobileNumber", mobile_et.getText().toString().trim());
                    jsonObject.put("mode", "getOTP");
                    jsonObject.put("country", searchCode);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getVerifyRegisterOTPJSON(jsonObject, "getOTP", "null");

            } else if (!email_et.getText().toString().trim().isEmpty()) {
                if (!Common.isValidEmail(email_et.getText().toString())) {
                    Common.getInstance().showSweetAlertWarning(activity(), "CelebKonect", "Please enter valid \nEmail ID");
                    return;
                }
                isUsingMobile = false;
                mobileOrEmailID = email_et.getText().toString().trim();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("medium", "email");
                    jsonObject.put("email", email_et.getText().toString().trim());
                    jsonObject.put("mode", "getOTP");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getVerifyRegisterOTPJSON(jsonObject, "getOTP", "null");

           /* RegistrationMember registrationMember = new RegistrationMember("email", email_et.getText().toString().trim(), "", "", "getOTP", "", "", "");
            getVerifyRegisterOTP(registrationMember, "getOTP", userId);*/
            } else {
                isUsingMobile = false;
                Common.getInstance().cusToast(activity(), "Please enter mobile / email id");
                return;
            }
        }
    }


    private void selectImage() {
        promoDialog = new Dialog(getActivity());
        promoDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        promoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        promoDialog.setCancelable(false);
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
                if (promoDialog != null) {
                    promoDialog.dismiss();
                } else {
                    promoDialog.dismiss();
                }
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

        int locationPermission = ContextCompat.checkSelfPermission(activity(), Manifest.permission.ACCESS_FINE_LOCATION);
        int locationPermissionCore = ContextCompat.checkSelfPermission(activity(), Manifest.permission.ACCESS_COARSE_LOCATION);

        List<String> listPermissionsNeeded = new ArrayList<>();
        if (storagePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (permissionCAMERA != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }

        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (locationPermissionCore != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(activity(), listPermissionsNeeded.toArray(new
                    String[listPermissionsNeeded.size()]), MY_PERMISSIONS_REQUEST_ACCOUNTS);
            return false;
        }
        return true;
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

                Intent intent = CropImage.activity(Uri.fromFile(f)).setAllowFlipping(false).setAspectRatio(300, 300)
                        .getIntent(getContext());

                startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
            } else if (requestCode == 2) {
                Intent intent = CropImage.activity(data.getData()).setAllowFlipping(false).setAspectRatio(300, 300)
                        .getIntent(getContext());
                startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    Uri resultUri = result.getUri();
                    imageview_pick_profile_pic.setBackground(null);
                    imageview_pick_profile_pic.setImageURI(resultUri);
                    next_button_profile_pic.setText("Next");
                    tvChangeProfilePic.setVisibility(View.VISIBLE);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(new File(resultUri.getPath()).getAbsolutePath(), options);
                    imageUrl = new File(resultUri.getPath()).getAbsolutePath();
                    image_ratio = Common.imageResizeInRatioCaptureOrGallery(getActivity(),
                            options.outHeight, options.outWidth);
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }
            }
        }
    }

    public void getVerifyRegisterOTPJSON(JSONObject jsonObject, String condition, String userId) {
        Log.e("signupData", jsonObject.toString());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.memberRegistrationNewSingUpJSON(userId, RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString()));
        if (condition.equals("userNameStatus")) {
            Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, condition, true, iApiListener, false));
        } else {
            Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, condition, true, iApiListener, true));
        }
    }


    /*public void getVerifyRegisterOTPJSONNew(SignupData signupData, String condition, String userId) {
        Log.e("SignupDatInfo", signupData.toStringEmail());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.memberRegistrationNewSingUpJSONNew(userId, signupData);
        if (condition.equals("userNameStatus")) {
            Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, condition,
                    true, iApiListener, false));
        } else {
            Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, condition,
                    true, iApiListener, true));
        }
    }*/


    public void getVerifyRegisterOTP(RegistrationMemberImage registrationMemberImage, String condition, String userId) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        String refreshedToken = "";
        if (FirebaseInstanceId.getInstance().getToken() != null && !FirebaseInstanceId.getInstance().getToken().isEmpty()) {
            refreshedToken = FirebaseInstanceId.getInstance().getToken();
        } else {
            refreshedToken = FirebaseInstanceId.getInstance().getToken();
        }


        if (!registrationMemberImage.isUserName) {
            File fileMedia;
            MultipartBody.Part body = null;
            if (imageUrl != null && !imageUrl.equals("null") && !imageUrl.equals("imageAvailable")) {
                fileMedia = new File(imageUrl);
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), fileMedia);
                body = MultipartBody.Part.createFormData("profilePic", fileMedia.getName(), requestFile);

                JSONObject jsonObject = new JSONObject();
                userName = tv_welcome_username.getText().toString();
                try {
                    jsonObject.put("username", userName);
                    jsonObject.put("deviceToken", refreshedToken);
                    jsonObject.put("osType", "Android");
                    jsonObject.put("Dnd", true);
                    if (isReferralCodeAvailable) {
                        jsonObject.put("referralCode", referralCode);
                    }
                    jsonObject.put("isUsernameVerified", true);
                    jsonObject.put("mode", "goLogin");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Call<ApiResponseModel> call;
                if (isProfileSkip) {
                    call = apiInterface.memberRegistrationNewSingUpImage(userId,
                            RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString()), null);
                } else {
                    call = apiInterface.memberRegistrationNewSingUpImage(userId, RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString()), body);
                }
                Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, condition, true, iApiListener, true));
            }
        } else {
            try {
                JSONObject jsonObject = new JSONObject();
                userName = tv_welcome_username.getText().toString();

                if (isReferralCodeAvailable) {
                    jsonObject.put("referralCode", referralCode);
                    jsonObject.put("mode", "goLogin");
                } else {
                    jsonObject.put("username", userName);
                    jsonObject.put("isUsernameVerified", true);
                }
                Call<ApiResponseModel> call = apiInterface.memberRegistrationNewSingUpImage(userId, RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                        jsonObject.toString()), null);
                Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, condition, true, iApiListener, true));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
        if (condition.equals("getOTP")) {
            try {

                Common.getInstance().cusToast(activity(), apiResponseModel.message);
                if (isChangePassword) {
                    isChangePasswordBackOTP = true;
                }
                setVisibilityForAllLayout(1);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (condition.equals("verifyOTP")) {
            try {
                if (isChangePassword) {
                    Common.getInstance().cusToast(activity(), apiResponseModel.message);
                    setVisibilityForAllLayout(3);
                } else {
                    Common.getInstance().cusToast(activity(), apiResponseModel.message);
                    setVisibilityForAllLayout(2);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (condition.equals("userNameStatus")) {
            try {

                Type type = new TypeToken<Available>() {
                }.getType();
                Available available = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);

                boolean isAva = available.available;
                imageview_user_available.setVisibility(View.VISIBLE);
                if (isAva) {
                    next_button_welcome_celebkonect.setEnabled(true);
                    next_button_welcome_celebkonect.setBackgroundColor(activity().getResources().getColor(R.color.skyblueNew));
                    imageview_user_available.setImageDrawable(activity().getResources().getDrawable(R.drawable.ic_tick_icon_1));
                } else {
                    imageview_user_available.setImageDrawable(activity().getResources().getDrawable(R.drawable.ic_close_circular_button_symbol));
                    next_button_welcome_celebkonect.setEnabled(false);
                    next_button_welcome_celebkonect.setBackgroundColor(activity().getResources().getColor(R.color.new_light));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (condition.equals("passwordCondition")) {
            try {
                if (isForgot) {
                    /*Common.getInstance().cusToast(activity(), apiResponseModel.message);
                    Intent intent = new Intent(activity(), SignInActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);*/
                    login(false);
                } else if (isChangePassword) {
                    Common.getInstance().cusToast(activity(), apiResponseModel.message);
                    activity().finish();
                 /*   Intent intent = new Intent(activity(), SignInActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);*/
                } else {
                    Type type = new TypeToken<MemberRegRes>() {
                    }.getType();
                    MemberRegRes memberRegRes = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                    userId = memberRegRes.userId;
                    userName = memberRegRes.username;
                    tv_welcome_username.setText("");
                    tv_welcome_username.append(userName + "");
                    setVisibilityForAllLayout(4);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (condition.equals("userNameCondition")) {
            try {
                setVisibilityForAllLayout(8);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (condition.equals("profilePic")) {

            try {

                try {
                    Type type = new TypeToken<LoginResponse>() {
                    }.getType();
                    LoginResponse loginResponse = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                    if (loginResponse != null) {

                        appendUserDetails(loginResponse);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (condition.equals("goLogin")) {
            try {

                try {
                    Type type = new TypeToken<LoginResponse>() {
                    }.getType();
                    LoginResponse loginResponse = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                    if (loginResponse != null) {
                        appendUserDetails(loginResponse);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (condition.equals("getOTPForgot")) {
            try {
                Common.getInstance().cusToast(activity(), apiResponseModel.message);
                isForgotBack = false;
                isForgotBackDetails = true;
                setVisibilityForAllLayout(1);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (condition.equals("verifyOTPForgot")) {
            Common.getInstance().cusToast(activity(), apiResponseModel.message);
            setVisibilityForAllLayout(3);
        } else if (condition.equals("checkPassword")) {
            //   Common.getInstance().cusToast(activity(), apiResponseModel.message);
            isChangePasswordBack = true;
            et_oldPasswordChange.setText("");
            setVisibilityForAllLayout(3);

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
        } else if (condition.equals("verifyOTPEditProfile")) {

            try {
                Common.getInstance().cusToast(activity(), apiResponseModel.message);
                JSONObject jsonObject = new JSONObject();
                if (isMobile) {
                    jsonObject.put("mobileNumber", EmailOrMobile);
                    jsonObject.put("isMobileVerified", true);
                    jsonObject.put("country", countryCodeEditProfile);
                } else {
                    jsonObject.put("email", EmailOrMobile);
                    jsonObject.put("isEmailVerified", true);
                }

                userId = SessionManager.userLogin.userId;
                getVerifyRegisterOTPVerifyTrue(jsonObject, "mobileOrEmailVerify", userId);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (condition.equals("mobileOrEmailVerify")) {

           /* Intent intent = new Intent(activity(), HelperActivity.class);
            intent.putExtra("title", "My Profile");
            intent.putExtra(Constants.FRAGMENT_KEY, 8002);// EditProfileFragment
            activity().startActivity(intent);*/
            activity().finish();

        } else if (condition.equals("socialNetworkReg")) {
            Type type = new TypeToken<MemberRegRes>() {
            }.getType();
            MemberRegRes memberRegRes = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
            userId = memberRegRes.userId;
            userName = memberRegRes.username;
            tv_welcome_username.setText("");
            tv_welcome_username.append(userName + "");
            setVisibilityForAllLayout(4);

        } else if (condition.equals("referralCodeCondition")) {
            isReferralCodeAvailable = false;
            isReferralCodeVerified = true;
            setVisibilityForAllLayout(5);
            Common.hideKeyboard(activity());
        } else if (condition.equals("getUserDetails")) {
            try {
                Type type = new TypeToken<ForgotUserDetails>() {
                }.getType();
                userDetails = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                isForgotBack = true;
                setForgotDetails(userDetails);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    public void getVerifyRegisterOTPVerifyTrue(JSONObject jsonObject, String condition, String userId) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        try {
            Call<ApiResponseModel> call = apiInterface.memberRegistrationNewSingUpImage(userId, RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    jsonObject.toString()), null);
            Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, condition, true, iApiListener, true));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setForgotDetails(ForgotUserDetails userDetails) {

        if (userDetails.avtarImgPath != null && !userDetails.avtarImgPath.isEmpty()) {
            Glide.with(activity())
                    .load(ApiClient.BASE_URL + userDetails.avtarImgPath)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_profile_square_pleace_holder)
                    .into(imageViewForgotUserPic);
        } else {
//                Glide.with(activity).load(R.drawable.ic_profile_square_pleace_holder).into(mUserProfileImage);
            imageViewForgotUserPic.setImageResource(R.drawable.ic_profile_square_pleace_holder);
        }
        if (userDetails.firstName != null && !userDetails.firstName.isEmpty()) {
            if (userDetails.lastName != null && !userDetails.lastName.isEmpty()) {
                tvForgotUserName.setText(userDetails.firstName + " " + userDetails.lastName);
            }
        }
        if (userDetails.isMobileVerified && userDetails.isEmailVerified) {

            rlViaSMS.setVisibility(View.VISIBLE);
            rlViaEmail.setVisibility(View.VISIBLE);
            imageviewViaSMS.setVisibility(View.VISIBLE);
            imageviewViaEmail.setVisibility(View.GONE);

            String string = "";
            if (userDetails.email != null && !userDetails.email.isEmpty()) {
                string = userDetails.email;
            }
            String emailStarting = string;
            String emailEnding = string;
            if (string.length() > 3) {
                emailStarting = string.substring(0, 3);
                emailEnding = string.substring(string.length() - 3, string.length());
            }
            String stringMobile = "";
            if (userDetails.mobile != null && !userDetails.mobile.isEmpty()) {
                stringMobile = userDetails.mobile;
            }
            String mobileNumberStarting = stringMobile;
            String mobileNumberEnding = stringMobile;
            if (stringMobile.length() > 3) {
                mobileNumberStarting = stringMobile.substring(0, 3);
                mobileNumberEnding = stringMobile.substring(stringMobile.length() - 3, stringMobile.length());
            }


            tvViaEmailId.setText("Send OTP to " + emailStarting + "*****" + emailEnding);
            tvViaSMSMobileNo.setText("Send OTP to " + mobileNumberStarting + "*****" + mobileNumberEnding);
        } else if (userDetails.isMobileVerified) {
            imageviewViaSMS.setVisibility(View.VISIBLE);
            imageviewViaEmail.setVisibility(View.GONE);
            String string = " ";
            if (userDetails.mobile != null && !userDetails.mobile.isEmpty()) {
                string = userDetails.mobile;
            }
            String mobileNumberStarting = string;
            String mobileNumberEnding = string;
            if (string.length() != 0) {
                if (string.length() > 3) {
                    mobileNumberStarting = string.substring(0, 3);
                    mobileNumberEnding = string.substring(string.length() - 3, string.length());
                }
            }

            tvViaSMSMobileNo.setText("Send OTP to " + mobileNumberStarting + "*****" + mobileNumberEnding);
            rlViaSMS.setVisibility(View.VISIBLE);
            rlViaEmail.setVisibility(View.GONE);

        } else if (userDetails.isEmailVerified) {
            imageviewViaSMS.setVisibility(View.GONE);
            imageviewViaEmail.setVisibility(View.VISIBLE);
            rlViaSMS.setVisibility(View.GONE);
            rlViaEmail.setVisibility(View.VISIBLE);
            String string = " ";
            if (userDetails.email != null && !userDetails.email.isEmpty()) {
                string = userDetails.email;
            }
            String mobileNumberStarting = string;
            String mobileNumberEnding = string;
            if (string.length() != 0) {
                if (string.length() > 3) {
                    mobileNumberStarting = string.substring(0, 3);
                    mobileNumberEnding = string.substring(string.length() - 3, string.length());
                }
            }

            tvViaEmailId.setText("Send OTP to " + mobileNumberStarting + "*****" + mobileNumberEnding);
        }

        setVisibilityForAllLayout(9);

    }

    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
        if (condition.equals("getOTP")) {

        } else if (condition.equals("verifyOTP")) {

            // Common.getInstance().cusToast(activity(), "Incorrect OTP");

        } else if (condition.equals("userNameStatus")) {
            try {

                Type type = new TypeToken<Available>() {
                }.getType();
                Available available = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);

                boolean isAva = available.available;
                imageview_user_available.setVisibility(View.VISIBLE);
                if (isAva) {
                    next_button_welcome_celebkonect.setEnabled(true);
                    next_button_welcome_celebkonect.setBackgroundColor(activity().getResources().getColor(R.color.skyblueNew));
                    imageview_user_available.setImageDrawable(activity().getResources().getDrawable(R.drawable.ic_tick_icon_1));
                } else {
                    next_button_welcome_celebkonect.setEnabled(false);
                    next_button_welcome_celebkonect.setBackgroundColor(activity().getResources().getColor(R.color.new_light));
                    imageview_user_available.setImageDrawable(activity().getResources().getDrawable(R.drawable.ic_close_circular_button_symbol));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (condition.equals("passwordCondition")) {

        } else if (condition.equals("memberRegister")) {

        } else if (condition.equals("userNameCondition")) {

            //  Common.getInstance().cusToast(activity(), apiResponseModel.message);

        } else if (condition.equals("profilePic")) {

        } else if (condition.equals("goLogin")) {
            if (enumConstants.equals(EnumConstants.SERVER_ERROR)) {
                Common.getInstance().cusToast(activity(), Constants.SOMETHING_WENT_WRONG_SERVER);
                // Common.getInstance().showSweetAlertWarning(SignInActivity.this, "CelebKonect", Constants.SOMETHING_WENT_WRONG_SERVER);
            } else {
                if (apiResponseModel.success.equals(Constants.SECURE_LOGOUT_RESULT)) {
                    forceClosePopup(apiResponseModel.message, condition.equals("goLoginSocial"));
                } else if (apiResponseModel.success.equals(Constants.FORGOT_EMAIL)) {
                    Common.getInstance().cusToast(activity(), apiResponseModel.message);
                } else if (apiResponseModel.success.equals(Constants.FAILURE_RESULT)) {
                    Common.getInstance().cusToast(activity(), apiResponseModel.message);
                }
            }
        } else if (condition.equals("getOTPForgot")) {

        } else if (condition.equals("verifyOTPForgot")) {

        } else if (condition.equals("checkPassword")) {

        } else if (condition.equals("verifyOTPEditProfile")) {

        } else if (condition.equals("mobileOrEmailVerify")) {

        } else if (condition.equals("socialNetworkReg")) {

        } else if (condition.equals("referralCodeCondition")) {

        } else if (condition.equals("getUserDetails")) {

        }
    }

    public void login(final boolean secureLogin) {
        if (isForgot) {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();

            String android_id = Settings.Secure.getString(activity().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            Log.e("DeviceToken", android_id + "");
            Log.e("FirebaseToken", refreshedToken + "");
            String currenctyType;
            currenctyType = Common.getCoutryCode(activity());
            if (currenctyType.equals("IN")) {
                currenctyType = "IN";
            } else if (currenctyType.equals("AU")) {
                currenctyType = "AU";
            } else if (currenctyType.equals("US")) {
                currenctyType = "US";
            }

            LoginData loginData = new LoginData(mobileOrEmailID, password, currenctyType,
                    refreshedToken, DateUtil.getCurrentLocalTime(), "Android", secureLogin);
/*            Call<ApiResponseModel>
                call = apiInterface.memberRegistrationNewSingUpImage("null", RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                        jsonObject.toString()), null);
            Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, "goLogin", true, iApiListener, true));*/
            Call<ApiResponseModel> call = apiInterface.getLogInReq(loginData);
            Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, "goLogin", true,
                    iApiListener, false));


        } else {
            String refreshedToken = "";
            if (FirebaseInstanceId.getInstance().getToken() != null && !FirebaseInstanceId.getInstance().getToken().isEmpty()) {
                refreshedToken = FirebaseInstanceId.getInstance().getToken();
            } else {
                refreshedToken = FirebaseInstanceId.getInstance().getToken();
            }

            Profile profile = new Profile(userName, true, refreshedToken, "Android");
            userName = tv_welcome_username.getText().toString();

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("deviceToken", refreshedToken);
                if (isReferralCodeAvailable) {
                    jsonObject.put("referralCode", referralCode);
                }
                jsonObject.put("osType", "Android");
                jsonObject.put("username", userName);
                jsonObject.put("isUsernameVerified", true);
                jsonObject.put("mode", "goLogin");


            } catch (JSONException e) {
                e.printStackTrace();
            }
            RegistrationMemberImage registrationMemberimage = new RegistrationMemberImage(profile, "goLogin");
            getVerifyRegisterOTP(registrationMemberimage, "goLogin", userId);
        }
    }


    //Login response appending
    private void appendUserDetails(LoginResponse loginResponse) {
        SessionManager.getInstance().appendUserDetails(loginResponse, false);
    }


    private void getOTPForgot() {

        JSONObject jsonObject = new JSONObject();
        try {

            if (imageviewViaEmail.getVisibility() == View.VISIBLE) {
                jsonObject.put("emailOrMobileNumber", userDetails.email);
                jsonObject.put("medium", "email");
                mobileOrEmailID = userDetails.email;
            } else if (imageviewViaSMS.getVisibility() == View.VISIBLE) {
                jsonObject.put("emailOrMobileNumber", userDetails.mobile);
                jsonObject.put("medium", "mobile");
                mobileOrEmailID = userDetails.mobile;
            }
            jsonObject.put("mode", "getOTP");
            apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<ApiResponseModel> call = apiInterface.forgotGetOTP(RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    jsonObject.toString()));

            Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, "getOTPForgot", true, iApiListener, true));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void searchCountryCode(String searchCodeLocal,
                                  String countryCodeLocal) {
        if (country_popup_dailogGlobal != null) {
            country_popup_dailogGlobal.dismiss();
            searchCode = countryCodeLocal;
            countryCodetv.setText("" + searchCodeLocal);
        } else {

        }
    }
    //Conutry Popup

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

//        getAllCountryListFromServer(countryRecyList, progressCountry);

        if (_countryData.size() == 0) {
            getAllCountryListFromServer(true);
        } else {
            if (countryAdapter != null) {
//                countryRecyList.setAdapter(countryAdapter);

                countryAdapter = new CountryAdapter(activity(), _countryData, SignUpFragment.this, iCountryAdapter);
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
                        countryRecyList.setAdapter(countryAdapter = new CountryAdapter(activity(), _searchedData, SignUpFragment.this, iCountryAdapter));
                    }
                } else {
                    countryRecyList.setAdapter(countryAdapter = new CountryAdapter(activity(), _countryData, SignUpFragment.this, iCountryAdapter));
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

    public boolean isValidMobilelenght(String phone) {
        boolean check = false;
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            if (phone.length() < minlenght || phone.length() > maxlength) {
                // if(phone.length() != 10) {
                check = false;
                // txtPhone.setError("Not Valid Number");
            } else {
                check = android.util.Patterns.PHONE.matcher(phone).matches();
            }
        } else {
            check = false;
        }
        return check;
    }


    public void enableViews(String condition) {
        switch (condition) {
            case "nameScreen":
                if (et_firstname.getText().toString().trim().isEmpty() || et_lasttname.getText().toString().trim().isEmpty()) {
                    next_button_name.setEnabled(false);
                    next_button_name.setBackgroundColor(activity().getResources().getColor(R.color.new_light));
                } else {
                    next_button_name.setEnabled(true);
                    next_button_name.setBackgroundColor(activity().getResources().getColor(R.color.skyblueNew));
                }
                break;
            case "referralScreen":
                if (et_referralCode.getText().toString().trim().isEmpty()) {
                    bt_referralCode.setEnabled(false);
                    bt_referralCode.setBackgroundColor(activity().getResources().getColor(R.color.new_light));
                } else {
                    bt_referralCode.setEnabled(true);
                    bt_referralCode.setBackgroundColor(activity().getResources().getColor(R.color.skyblueNew));
                }
                break;
            case "emailScreen":
                if (email_et.getText().toString().trim().isEmpty()) {
                    next_button.setEnabled(false);
                    next_button.setBackgroundColor(activity().getResources().getColor(R.color.new_light));
                } else {
                    next_button.setEnabled(true);
                    next_button.setBackgroundColor(activity().getResources().getColor(R.color.skyblueNew));
                }
                break;
            case "mobileScreen":
                if (mobile_et.getText().toString().trim().isEmpty()) {
                    next_button.setEnabled(false);
                    next_button.setBackgroundColor(activity().getResources().getColor(R.color.new_light));
                } else {
                    next_button.setEnabled(true);
                    next_button.setBackgroundColor(activity().getResources().getColor(R.color.skyblueNew));
                }
                break;
            case "forgotScreen":
                if (edt_email_mobile_number.getText().toString().trim().isEmpty()) {
                    bt_forgotNext.setEnabled(false);
                    bt_forgotNext.setBackgroundColor(activity().getResources().getColor(R.color.new_light));
                } else {
                    bt_forgotNext.setEnabled(true);
                    bt_forgotNext.setBackgroundColor(activity().getResources().getColor(R.color.skyblueNew));
                }
                break;
        }
    }

    public void emailOrMobileHide(boolean isHide) {
        if (isHide) {
            if (forOTPVerification) {
                String stringMobile = EmailOrMobile;
                String mobileNumberStarting = stringMobile;
                String mobileNumberEnding = stringMobile;
                if (stringMobile.length() > 5) {
                    mobileNumberStarting = stringMobile.substring(0, 6);
                    mobileNumberEnding = stringMobile.substring(stringMobile.length() - 3, stringMobile.length());
                }

                otp_mobile_number_tv.setText(mobileNumberStarting + "*****" + mobileNumberEnding);

            } else {

                String stringMobile = mobileOrEmailID;
                String mobileNumberStarting = stringMobile;
                String mobileNumberEnding = stringMobile;
                if (stringMobile.length() > 5) {
                    mobileNumberStarting = stringMobile.substring(0, 6);
                    mobileNumberEnding = stringMobile.substring(stringMobile.length() - 3, stringMobile.length());
                }

                otp_mobile_number_tv.setText(mobileNumberStarting + "*****" + mobileNumberEnding);

            }
        } else {
            if (forOTPVerification) {
                otp_mobile_number_tv.setText(EmailOrMobile);

            } else {
                otp_mobile_number_tv.setText(mobileOrEmailID);

            }
        }
    }

    private void headerLogoHideShow(ImageView headerIcon, int marginTop, int marginBottom) {
        if (Common.getInstance().isKeyboardVisible(activity())) {
            headerIcon.getLayoutParams().height = (int) getResources().getDimension(R.dimen._50sdp);
            headerIcon.getLayoutParams().width = (int) getResources().getDimension(R.dimen._50sdp);
            Common.getInstance().setMargins(headerIcon, 0, 0, 0, 0);
        } else {
            headerIcon.getLayoutParams().width = (int) getResources().getDimension(R.dimen._90sdp);
            headerIcon.getLayoutParams().height = (int) getResources().getDimension(R.dimen._130sdp);
            Common.getInstance().setMargins(headerIcon, 0, marginTop, 0, marginBottom);
        }
    }
}
