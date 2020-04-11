package info.sumantv.flow.celebflow;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.*;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.UnderlineSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import com.crashlytics.android.Crashlytics;
import com.facebook.*;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.*;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.twitter.sdk.android.core.*;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.core.services.AccountService;

import info.sumantv.flow.bottommenu.activity.HelperActivity;
import info.sumantv.flow.bottommenu.interfaces.activity.IActivity;
import info.sumantv.flow.celebflow.Adapters.ManageAccountsAdapter;
import info.sumantv.flow.celebflow.Adapters.MultipleAccountsAdapter;
import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.celebflow.interfaces.IManageAccountsAdapter;
import info.sumantv.flow.celebflow.interfaces.IMultipleAccountsAdapter;
import info.sumantv.flow.celebflow.modelData.MultipleAccountModel;
import info.sumantv.flow.celebflow.modelData.SignUpConditions;
import info.sumantv.flow.retrofitcall.SessionManager;
import info.sumantv.flow.retrofitcall.*;
import info.sumantv.flow.userflow.UserActivitys.LoginActivity.LoginData;
import info.sumantv.flow.userflow.UserActivitys.LoginActivity.LoginResponse;
import info.sumantv.flow.userflow.Util.Common;
import info.sumantv.flow.userflow.Util.DateUtil;
import info.sumantv.flow.utils.UtilityNew;
import info.sumantv.flow.utils.internetchecker.InternetSpeedChecker;
import io.fabric.sdk.android.Fabric;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import org.json.JSONArray;
import org.json.JSONObject;

import retrofit2.Call;

import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

import info.sumantv.flow.R;


/**
 * Created by Shiva on 12/6/2017.
 */

public class SignInActivity extends AppCompatActivity implements IActivity, View.OnClickListener, GoogleApiClient.OnConnectionFailedListener,
        IApiListener, IMultipleAccountsAdapter, IManageAccountsAdapter {
    private static final int RC_SIGN_IN = 007;

    ApiInterface apiInterface;
    EditText edtEmail, password_signin, etMAPassword;
    private ProgressDialog progressDialog;
    private TextView forgotpass;
    CheckBox remember_checkbox;
    private Button loginbtn, btnMALogin;
    private ImageView mTwitterLoginBtn, mFacebookLoginBtn, mGplusLoginBtn;
    private ImageView imageViewpasswordhide, ivBack, ivMAProfileImage, ivMAViewPassword;
    private TextView mNewUserSignUp, tvManageAccounts, tvNewAccount, tvBackToMultiple, tvMAUserName, tvMABackToMultiple, tvMAEmail, tvMAForgetPassword;
    String profileImageUrl = "";
    Typeface face;
    private CallbackManager callbackManager;

    private GoogleSignInClient mGoogleSignInClient;
    private TwitterAuthClient client;

    String loginTypeIfSocialLogin = "";
    String loginMailifSocialLogin = "";
    String loginUserNameifSocialLogin = "";
    String lastNameSocial = "", firstNameSocial = "";
    Context mContext;
    private String currenctyType;
    private LinearLayout coordinatorLayout;
    IApiListener iApiListener;
    LinearLayout signLayout, llSignIn, llMultipleAccounts, llManageAccounts, llMultipleAccountLogin;
    RecyclerView rvMultipleAccounts, rvManageAccounts;
    Integer screenWidth = 0, screenHeight = 0;
    IMultipleAccountsAdapter iMultipleAccountsAdapter;
    private IManageAccountsAdapter iManageAccountsAdapter;
    private ManageAccountsAdapter manageAccountsAdapter;
    ArrayList<MultipleAccountModel> multipleAccountModelArrayList = new ArrayList<>();
    LoginManager loginManager = LoginManager.getInstance();
    ImageView logoIconSignLayout;
    String socialMediaType = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);


        if (!ApiClient.ENVIRONMENT.equals("test")) {
            Fabric.with(this, new Crashlytics());
        }


        setContentView(R.layout.activity_signin);
        StrictMode.enableDefaults();
        iApiListener = this;
        iMultipleAccountsAdapter = this;
        iManageAccountsAdapter = this;
        hideStatusBar();
        initializeViews();
        initializeActions();
        socialNetPermissionAccess();
        mContext = SignInActivity.this;
        DateUtil.getCurrentDateAndTimeInUTC();
//        startService(new Intent(this, MyFirebaseMessagingService.class));
        setDefaultActions();
        SessionManager.getInstance().managerSwitchKeys();
        getAccountsData();
        setMultipleAccountsData();
        coordinatorLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (coordinatorLayout != null) {
                    if (llSignIn.getVisibility() == View.VISIBLE) {
                        llMultipleAccountLogin.setVisibility(View.GONE);
                        if (Common.getInstance().isKeyboardVisible(activity())) {
                            headerLogoHideShow(logoIconSignLayout, 0);
                        } else {
                            headerLogoHideShow(logoIconSignLayout, (int) getResources().getDimension(R.dimen._35sdp));
                        }
                    } else if (llMultipleAccountLogin.getVisibility() == View.VISIBLE) {
                        llSignIn.setVisibility(View.GONE);
                        if (Common.getInstance().isKeyboardVisible(activity())) {
                            headerLogoHideShow(ivMAProfileImage, 0);
                        } else {
                            headerLogoHideShow(ivMAProfileImage, (int) getResources().getDimension(R.dimen._35sdp));
                        }
                    }
                }
            }
        });
        tvNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideAllViews();
                llSignIn.setVisibility(View.VISIBLE);
                signLayout.setVisibility(View.VISIBLE);
                edtEmail.setText("");
                remember_checkbox.setChecked(false);
                llMultipleAccountLogin.setVisibility(View.GONE);
            }
        });
        tvManageAccounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setManageAccountsData();
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setMultipleAccountsData();
            }
        });
        tvBackToMultiple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setMultipleAccountsData();
            }
        });
        tvMABackToMultiple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setMultipleAccountsData();
            }
        });
        tvMAForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgotPasswordClick();
            }
        });
        btnMALogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Common.getInstance().hideKeyboard(new SignInActivity(), (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (TextUtils.isEmpty(etMAPassword.getText().toString().trim())) {
                    showSnackBar("Please enter password", 2);
                    // Common.getInstance().showSweetAlertWarning(mContext, "CelebKonect", "Please enter password");
                } else {
                    datasendserver(false, tvMAEmail.getText().toString().toLowerCase(),
                            etMAPassword.getText().toString());
                }
            }
        });
        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                SessionManager.getInstance().setKeyValue(SessionManager.KEY_EMAIL_ID, edtEmail.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String emailSpace = editable.toString().replaceAll(" ", "");
                if (!editable.toString().equals(emailSpace)) {
                    edtEmail.setText(emailSpace);
                    edtEmail.setSelection(emailSpace.length());
                    SessionManager.getInstance().setKeyValue(SessionManager.KEY_EMAIL_ID, edtEmail.getText().toString());
                }
            }
        });
        // password_signin.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        password_signin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String passSpace = editable.toString().replaceAll(" ", "");
                if (!editable.toString().equals(passSpace)) {
                    password_signin.setText(passSpace);
                    password_signin.setSelection(passSpace.length());
                    // alert the user
                }
            }
        });
    }


    private void setDefaultActions() {
        SessionManager.getInstance().setKeyValue(SessionManager.KEY_SIGN_IN_ACCESS, "");
        SessionManager.getInstance().setKeyValue(SessionManager.KEY_FIRSTTIME_EDIT_PROFILE_ACCESS, "");
        SessionManager.getInstance().setKeyValue(SessionManager.KEY_USER_ID, "");
        SessionManager.getInstance().setKeyValue(SessionManager.KEY_FIRST_EDIT_PROFILE_NOT_COMPLETE, "");
        SessionManager.getInstance().setKeyValue(SessionManager.KEY_IS_MANAGER, false);

        try {
            if (getIntent().getExtras().getString("FROM_SERVICE") != null &&
                    !getIntent().getExtras().getString("FROM_SERVICE").isEmpty()) {
                SessionManager.getInstance().setKeyValue(SessionManager.KEY_SWITCH_ACCOUNT, "");
                Toast.makeText(getApplicationContext(), "Celebrity Permission Denied", Toast.LENGTH_LONG).show();
            } else if (getIntent().getExtras().getString("FROM_MANAGER") != null &&
                    !getIntent().getExtras().getString("FROM_MANAGER").isEmpty()) {
                SessionManager.getInstance().setKeyValue(SessionManager.KEY_SWITCH_ACCOUNT, "");
            }
        } catch (Exception e) {

        }


        //
    }

    private void hideStatusBar() {
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /*if (Build.VERSION.SDK_INT > 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }*/
    }

    private void showStatusBar() {
        //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void initializeViews() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenWidth = displaymetrics.widthPixels;
        screenHeight = displaymetrics.heightPixels;
        //
        llSignIn = findViewById(R.id.llSignIn);
        llMultipleAccounts = findViewById(R.id.llMultipleAccounts);
        llManageAccounts = findViewById(R.id.llManageAccounts);
        tvManageAccounts = findViewById(R.id.tvManageAccounts);
        tvNewAccount = findViewById(R.id.tvNewAccount);
        tvBackToMultiple = findViewById(R.id.tvBackToMultiple);
        rvMultipleAccounts = findViewById(R.id.rvMultipleAccounts);
        ivBack = findViewById(R.id.ivBack);
        rvManageAccounts = findViewById(R.id.rvManageAccounts);
        llMultipleAccountLogin = findViewById(R.id.llMultipleAccountLogin);
        etMAPassword = findViewById(R.id.etMAPassword);
        btnMALogin = findViewById(R.id.btnMALogin);
        ivMAProfileImage = findViewById(R.id.ivMAProfileImage);
        ivMAViewPassword = findViewById(R.id.ivMAViewPassword);
        tvMAUserName = findViewById(R.id.tvMAUserName);
        tvMABackToMultiple = findViewById(R.id.tvMABackToMultiple);
        tvMAForgetPassword = findViewById(R.id.tvMAForgetPassword);
        tvMAEmail = findViewById(R.id.tvMAEmail);
        //
        edtEmail = (EditText) findViewById(R.id.email_signin);
        password_signin = (EditText) findViewById(R.id.password_signin);
//        password_signin.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
//        password_signin.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        face = Typeface.createFromAsset(getAssets(),
                "fonts/sf_compact_display/sf_medium.otf");
        password_signin.setTypeface(face);
        forgotpass = (TextView) findViewById(R.id.forgot_login);
//        forgotpass.setPaintFlags(forgotpass.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        mNewUserSignUp = (TextView) findViewById(R.id.newuser_signup);
        signLayout = (LinearLayout) findViewById(R.id.signLayout);
        coordinatorLayout = (LinearLayout) findViewById(R.id.coordinatorLayout);
//        signLayout.setOnClickListener(this); //newly comment

        SpannableString content = new SpannableString("Sign Up");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        mNewUserSignUp.setText(content);
        mNewUserSignUp.setOnClickListener(this); //new write
        imageViewpasswordhide = (ImageView) findViewById(R.id.imageViewpasswordhide);
        imageViewpasswordhide.setBackground(getResources().getDrawable(R.drawable.ic_eye_hide));
        mTwitterLoginBtn = (ImageView) findViewById(R.id.twitter_icon);
        mFacebookLoginBtn = (ImageView) findViewById(R.id.facebook_icon);
        mGplusLoginBtn = (ImageView) findViewById(R.id.google_plus_icon);
        remember_checkbox = (CheckBox) findViewById(R.id.remember_checkbox);
        loginbtn = (Button) findViewById(R.id.loginbtn);
        progressDialog = new ProgressDialog(SignInActivity.this, R.style.AppCompatAlertDialogStyle);
        callbackManager = CallbackManager.Factory.create();
        //
        rvMultipleAccounts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvManageAccounts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        tvNewAccount.setPaintFlags(tvNewAccount.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        /*Drawable tick_drawable = VectorDrawableCompat.create(getResources(), R.drawable.ic_mail_outline_white,
                null);
        if (tick_drawable != null) {
            tick_drawable.setBounds(0, 0, tick_drawable.getIntrinsicWidth(),
                    tick_drawable.getIntrinsicHeight());
        }

        Drawable password_drawable = VectorDrawableCompat.create(getResources(), R.drawable.ic_lock_outline_white,
                null);
        if (password_drawable != null) {
            password_drawable.setBounds(0, 0, password_drawable.getIntrinsicWidth(),
                    password_drawable.getIntrinsicHeight());
        }

        edtEmail.setCompoundDrawables(tick_drawable, null, null, null);
        password_signin.setCompoundDrawables(password_drawable, null, null, null);

        setVectorForPreLollipop(R.drawable.ic_mail_outline_white,getApplicationContext());*/

        logoIconSignLayout = (ImageView) findViewById(R.id.logoIconSignLayout);

    }

    private void initializeActions() {
        mTwitterLoginBtn.setOnClickListener(this);
        mFacebookLoginBtn.setOnClickListener(this);
        mGplusLoginBtn.setOnClickListener(this);
        remember_checkbox.setOnClickListener(this);
        if (SessionManager.getInstance().getKeyValue(SessionManager.KEY_REMEMBER_ME, "") != null && !SessionManager.getInstance().getKeyValue(SessionManager.KEY_REMEMBER_ME, "").isEmpty()) {
            if (SessionManager.getInstance().getKeyValue(SessionManager.KEY_REMEMBER_ME, "").equals("TRUE")) {
                remember_checkbox.setChecked(true);

                password_signin.setTransformationMethod(PasswordTransformationMethod.getInstance());
                password_signin.setTypeface(face);
                //        password_signin.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                imageViewpasswordhide.setVisibility(View.GONE);
                if (SessionManager.getInstance().getKeyValue(SessionManager.KEY_EMAIL_ID, "") != null && !SessionManager.getInstance().getKeyValue(SessionManager.KEY_EMAIL_ID, "").isEmpty()) {
                    edtEmail.setText(SessionManager.getInstance().getKeyValue(SessionManager.KEY_EMAIL_ID, ""));
                    edtEmail.requestFocus();
//                    password_signin.setText(SharedPrefsUtil.getStringPreference(getApplicationContext(), "PASSWORD"));
                }
            } else {

                password_signin.setTransformationMethod(PasswordTransformationMethod.getInstance());
                password_signin.setTypeface(face);
                remember_checkbox.setChecked(false);
                imageViewpasswordhide.setVisibility(View.VISIBLE);
                edtEmail.setText("");
                password_signin.setText("");
            }
        } else {

            password_signin.setTransformationMethod(PasswordTransformationMethod.getInstance());
            password_signin.setTypeface(face);
            remember_checkbox.setChecked(false);
            imageViewpasswordhide.setVisibility(View.VISIBLE);
        }

        forgotpass.setOnClickListener(this);
        mNewUserSignUp.setOnClickListener(this);
        loginbtn.setOnClickListener(this);
        imageViewpasswordhide.setOnClickListener(this);
        ivMAViewPassword.setOnClickListener(this);
    }

    private void hideAllViews() {
        try {
            Common.getInstance().hideKeyboard(new SignInActivity(), (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        llSignIn.setVisibility(View.GONE);
        llMultipleAccounts.setVisibility(View.GONE);
        llMultipleAccountLogin.setVisibility(View.GONE);
        llManageAccounts.setVisibility(View.GONE);
        signLayout.setVisibility(View.GONE);
        ivMAViewPassword.setBackground(getResources().getDrawable(R.drawable.ic_eye_hide));
        hideStatusBar();
        if (multipleAccountModelArrayList != null && multipleAccountModelArrayList.size() > 0) {
            tvBackToMultiple.setVisibility(View.VISIBLE);
        } else {
            tvBackToMultiple.setVisibility(View.GONE);
        }
    }

    private void getAccountsData() {
        try {
            LocalDB dataBase = new LocalDB(this);
            dataBase.open();
            JSONArray jsonArray = dataBase.getAllLogins();
            dataBase.close();
            Type type = new TypeToken<ArrayList<MultipleAccountModel>>() {
            }.getType();
            multipleAccountModelArrayList = new Gson().fromJson(jsonArray.toString(), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setMultipleAccountsData() {
        hideAllViews();
        try {
            if (multipleAccountModelArrayList != null && multipleAccountModelArrayList.size() > 0) {
                MultipleAccountsAdapter multipleAccountsAdapter = new MultipleAccountsAdapter(multipleAccountModelArrayList, screenWidth, screenHeight, this, iMultipleAccountsAdapter);
                rvMultipleAccounts.setAdapter(multipleAccountsAdapter);
                llMultipleAccounts.setVisibility(View.VISIBLE);
            } else {
                llSignIn.setVisibility(View.VISIBLE);
                llMultipleAccountLogin.setVisibility(View.GONE);
            }
            signLayout.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setManageAccountsData() {
        hideAllViews();
        try {
            if (multipleAccountModelArrayList != null && multipleAccountModelArrayList.size() > 0) {
                manageAccountsAdapter = new ManageAccountsAdapter(multipleAccountModelArrayList, this, iManageAccountsAdapter);
                rvManageAccounts.setAdapter(manageAccountsAdapter);
                llManageAccounts.setVisibility(View.VISIBLE);
                showStatusBar();
            } else {
                Common.getInstance().cusToast(this, "Data not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Common.getInstance().cusToast(this, Constants.SOMETHING_WENT_WRONG);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        /*new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    Common.getInstance().hideKeyboard(new SignInActivity(),(ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },500);*/
    }

    @Override
    public void onBackPressed() {
        if (llMultipleAccountLogin.getVisibility() == View.VISIBLE || llManageAccounts.getVisibility() == View.VISIBLE || (llSignIn.getVisibility() == View.VISIBLE && multipleAccountModelArrayList != null && multipleAccountModelArrayList.size() > 0)) {
            setMultipleAccountsData();
        } else {
            super.onBackPressed();
        }
    }


//    private void submitForm() {
//        if (!Common.isMobileOrEmailvalidation(this, edtEmail.getText().toString())) {
//        } else if (TextUtils.isEmpty(password_signin.getText().toString())) {
//            mPasswordLayout.setErrorEnabled(true);
//            mPasswordLayout.setError("Please enter password_signin");
//        } else if (!validatePassword()) {
//            return;
//        } else if (Utility.isNetworkAvailable(SignInActivity.this)) {
//            getDevicetokennFromSever(null, null, null);
//        } else {
//            Common.getInstance().showSweetAlertWarning(mContext, "CelebKonect", "Internet Connection Unavailable, Retry");
//        }
//
//        validatingEntryData();
//    }

    private void validatingEntryData() {
//        if (Common.isMobileOrEmailvalidation(this, edtEmail.getText().toString())) {
        if (validateEmail()) {
            if (validatePassword()) {

                // New Code on 05-02-2019 by Uday Kumar


                if (InternetSpeedChecker.checkInternetAvaialable(SignInActivity.this)) {
                    // Commented on 05-02-2019 by Uday Kumar
                    //getDevicetokennFromSever(null, null, null);
                    CheckDevicetoken(null, null, null);
                }
            } else {

            }
        }
    }

    private void CheckDevicetoken(final String loginMailifSocialLogin,
                                  final String loginTypeIfSocialLogin, final String username) {
        // Commented on 05-02-2019 by Uday Kumar
       /* if (deviceToken != null &&
                !deviceToken.isEmpty()
                && !deviceToken.equals("null")) {
            String localDeviceid = FirebaseInstanceId.getInstance().getToken();
            if (localDeviceid.equals(deviceToken)) {
                if (!((Activity) mContext).isFinishing()) {
                    if (loginMailifSocialLogin != null && !loginMailifSocialLogin.isEmpty()) {
                        socialLoginWithToken(loginMailifSocialLogin);
                    } else {
                        datasendserver();
                    }
                    return;
                }
            } else {
                if (!((Activity) mContext).isFinishing()) {
                    if (loginMailifSocialLogin != null && !loginMailifSocialLogin.isEmpty()) {
                        forceclose_popup(msgForWrongDeviceToken,
                                loginMailifSocialLogin, loginTypeIfSocialLogin, username);
                    } else {
                        forceclose_popup(msgForWrongDeviceToken,
                                edtEmail.getText().toString(), loginTypeIfSocialLogin, username);
                    }
                    return;
                }
            }
        } else {
            if (!((Activity) mContext).isFinishing()) {
                if (loginMailifSocialLogin != null && !loginMailifSocialLogin.isEmpty()) {
                    socialLoginWithToken(loginMailifSocialLogin);
                } else {
                    datasendserver();
                }
                return;
            }
        }*/

        if (!((Activity) mContext).isFinishing()) {
            if (loginMailifSocialLogin != null && !loginMailifSocialLogin.isEmpty()) {
                //  socialLoginWithToken(loginMailifSocialLogin);
            } else {
                datasendserver(false, edtEmail.getText().toString().toLowerCase(),
                        password_signin.getText().toString());
            }
            return;
        }
    }

    private void datasendserver(boolean secureLogin, String email, String password) {
        if (!Common.checkInternetConnection(getApplicationContext())) {
            showSnackBar(mContext.getResources().getString(R.string.no_internet_connect), 0);
           /* Common.getInstance().showSweetAlertWarning(mContext, "CelebKonect",
                    mContext.getResources().getString(R.string.no_internet_connect));*/
        } else {
//            progressDialog = Common.showProgressDialog(SignInActivity.this, progressDialog);
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();

            String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);

            Log.e("DeviceToken", android_id + "");
            Log.e("FirebaseToken", refreshedToken + "");

            currenctyType = Common.getCoutryCode(getApplicationContext());
            if (currenctyType.equals("IN")) {
                currenctyType = "IN";
            } else if (currenctyType.equals("AU")) {
                currenctyType = "AU";
            } else if (currenctyType.equals("US")) {
                currenctyType = "US";
            }

            LoginData loginData = new LoginData(email, password, currenctyType,
                    refreshedToken, DateUtil.getCurrentLocalTime(), "Android", secureLogin);

            apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<ApiResponseModel> call = apiInterface.getLogInReq(loginData);
            Common.getInstance().callAPI(new ApiRequestModel().setModel(this, call, ApiClient.LOGIN_URL, true, iApiListener, false));
        }
    }


    private void navigateToEditProfile() {
        if (loginTypeIfSocialLogin != null && !loginTypeIfSocialLogin.isEmpty()) {
            SessionManager.getInstance().setKeyValue(SessionManager.KEY_EMAIL_OR_MOBILE, loginMailifSocialLogin);
            SessionManager.getInstance().setKeyValue(SessionManager.KEY_SIGN_IN_ACCESS, "TRUE");
            /*Intent intent = new Intent(SignInActivity.this, EditProfileActivity.class);
            startActivity(intent);*/

            Intent intent = new Intent(SignInActivity.this, HelperActivity.class);
            intent.putExtra(Constants.FRAGMENT_TITLE, "My Profile");
            intent.putExtra(Constants.FRAGMENT_KEY, 8002);// EditProfileFragment
            intent.putExtra("NEW_REG", "TRUE");
            startActivity(intent);

        } else {
            SessionManager.getInstance().setKeyValue(SessionManager.KEY_SIGN_IN_ACCESS, "TRUE");
//            Intent intent = new Intent(SignInActivity.this, EditProfileActivity.class);
//            startActivity(intent);
            Intent intent = new Intent(SignInActivity.this, HelperActivity.class);
            intent.putExtra(Constants.FRAGMENT_TITLE, "My Profile");
            intent.putExtra(Constants.FRAGMENT_KEY, 8002);// EditProfileFragment
            intent.putExtra("NEW_REG", "TRUE");
            startActivity(intent);
        }

    }


    private boolean validateEmail() {
        boolean isAccess = true;
        if (edtEmail.getText().toString().trim().length() == 0) {

            // edtEmail.setError("Please enter email id");
            showSnackBar("Please enter Email id / Phone Number", 0);
           /* Common.getInstance().showSweetAlertWarning(mContext, "CelebKonect",
                    "Please enter Email id / \n Phone Number");*/
            isAccess = false;
        } else {
            isAccess = true;
          /*  if (TextUtils.isEmpty(edtEmail.getText().toString()) ||
                    !Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString().trim()).matches()) {
                Common.getInstance().showSweetAlertWarning(mContext, "CelebKonect",
                        "Invalid / email does \nnot exist");
                //  edtEmail.setError("Please enter valid email id");
                isAccess = false;
            } else {
                isAccess = true;
            }*/
        }

        return isAccess;
    }

    private boolean validatePassword() {
        boolean specialCharacter = false, digit = false, upperCase = false, lowerCase = false, eigthCharacter = false;
        boolean returnValue = false;
        Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]");

        if (TextUtils.isEmpty(password_signin.getText().toString())) {
            showSnackBar("Please enter password", 0);
            /*Common.getInstance().showSweetAlertWarning(mContext, "CelebKonect",
                    "Please enter password");*/
            return false;
        } else {
            returnValue = true;


            /*String pass = password_signin.getText().toString();
            for (int i = 0; i < pass.length(); i++) {
                if (Character.isUpperCase(pass.charAt(i))) {
                    upperCase = true;
                }
                if (Character.isLowerCase(pass.charAt(i))) {
                    lowerCase = true;
                }
            }

            String number = pass.replaceAll("[^0-9]", "");

            if (!number.isEmpty() && number != null) {
                digit = true;
            }

            if (regex.matcher(pass).find()) {
                specialCharacter = true;
            }

            if (pass.length() >= 8) {
                eigthCharacter = true;
            }

            if (upperCase && lowerCase && specialCharacter && digit && eigthCharacter) {
                returnValue = true;
            } else {
                Common.getInstance().showSweetAlertWarning(mContext, "CelebKonect",
                        "Please enter valid password");
                returnValue = false;
            }*/
        }
        return returnValue;
    }


    private void showProgressDialog() {
        progressDialog.setMessage("Loading..."); // Setting Message
//        progressDialog.setTitle("ProgressDialog"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
    }

    private void dismissProgressDialog() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }


    @Override
    public void onClick(View v) {
        try {
            Common.getInstance().hideKeyboard(new SignInActivity(), (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (v.getId() == R.id.twitter_icon) {
            if (Common.checkInternetConnection(getApplicationContext())) {
                twitterCall();
            } else {
                Common.offlinePopupDailog(getApplicationContext(), SignInActivity.this, "", "", 0);
            }

        } else if (v.getId() == R.id.facebook_icon) {
            faceBookCall();
        } else if (v.getId() == R.id.google_plus_icon) {
            gmailCall();
        } else if (v.getId() == R.id.forgot_login) {
            forgotPasswordClick();
        } else if (v.getId() == R.id.newuser_signup) {
            if (InternetSpeedChecker.checkInternetAvaialable(this)) {

                SignUpConditions signUpConditions = new SignUpConditions();
                signUpConditions.setLogin(false);
                signUpConditions.setUserName("");
                signUpConditions.setForgot(false);
                signUpConditions.setChangePassword(false);
                signUpConditions.setForOTPVerification(false);
                signUpConditions.setSocialNetwork(false);

                signUpConditions.setSocialMediaType(socialMediaType);


                Intent intent = new Intent(this, HelperActivity.class);
                intent.putExtra(Constants.FRAGMENT_TITLE, "");
                intent.putExtra(Constants.FRAGMENT_KEY, 1001);// SignUpFragment
                intent.putExtra("signUpConditions", signUpConditions);
                startActivity(intent);
            }
        } else if (v.getId() == R.id.remember_checkbox) {
            if (remember_checkbox.isChecked()) {
//                imageViewpasswordhide.setVisibility(View.GONE);
                SessionManager.getInstance().setKeyValue(SessionManager.KEY_REMEMBER_ME, "TRUE");
                SessionManager.getInstance().setKeyValue(SessionManager.KEY_EMAIL_ID, edtEmail.getText().toString());
            } else {
                imageViewpasswordhide.setVisibility(View.VISIBLE);
                SessionManager.getInstance().setKeyValue(SessionManager.KEY_REMEMBER_ME, "FALSE");
            }
        } else if (v.getId() == R.id.loginbtn) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(loginbtn.getWindowToken(), 0);
            loginbtn.setEnabled(false);
//            submitForm();
            validatingEntryData();
            loginbtn.setEnabled(true);
        } else if (v.getId() == R.id.imageViewpasswordhide) {
            showHidePassword(password_signin, imageViewpasswordhide);
        } else if (v.getId() == R.id.ivMAViewPassword) {
            showHidePassword(etMAPassword, ivMAViewPassword);
        }
    }

    private void showHidePassword(EditText editText, ImageView imageView) {
        int inputTypeValue = editText.getInputType();
        String passworddata = "";
        if (editText.getText().toString() != null) {
            passworddata = editText.getText().toString();
        }
        if (inputTypeValue == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            editText.setInputType(InputType.TYPE_CLASS_TEXT);
            editText.setTypeface(face);
            imageView.setBackground(getResources().getDrawable(R.drawable.ic_eye_hide));
            editText.setText("");
            editText.append(passworddata);
        } else {
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            editText.setTypeface(face);
            imageView.setBackground(getResources().getDrawable(R.drawable.ic_eye_unhide));
            editText.setText("");
            editText.append(passworddata);
        }
    }

    private void forgotPasswordClick() {
        if (InternetSpeedChecker.checkInternetAvaialable(this)) {

            SignUpConditions signUpConditions = new SignUpConditions();
            signUpConditions.setLogin(false);
            signUpConditions.setUserName("");
            signUpConditions.setForgot(true);
            signUpConditions.setChangePassword(false);
            signUpConditions.setForOTPVerification(false);
            signUpConditions.setSocialNetwork(false);

            signUpConditions.setSocialMediaType(socialMediaType);

            Intent intent = new Intent(this, HelperActivity.class);
            intent.putExtra(Constants.FRAGMENT_TITLE, "");
            intent.putExtra(Constants.FRAGMENT_KEY, 1001);// SignUpFragment
            intent.putExtra("signUpConditions", signUpConditions);
            startActivity(intent);
        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }


    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            assert acct != null;
            String email = Common.getInstance().IsNullReturnValue(acct.getEmail(), "");
            if (email.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Email id not available", Toast.LENGTH_SHORT).show();
                return;
            }
            String firstName = Common.getInstance().IsNullReturnValue(acct.getGivenName(), "");
            String lastName = Common.getInstance().IsNullReturnValue(acct.getFamilyName(), "");
            String fullName = Common.getInstance().IsNullReturnValue(acct.getDisplayName(), "");
            String afterReplaceUsername = email.replace("@gmail.com", "");
            signUpOrInSocialNetWork(email, "socialLogin",
                    afterReplaceUsername, profileImageUrl, false, firstName, lastName, "gmail");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data) {
        super.onActivityResult(requestCode, responseCode, data);
        if (requestCode == RC_SIGN_IN) {
            Log.v("resultcode", "" + responseCode);
//            if (responseCode == -1) {
//                Common.getInstance().showSweetAlertWarning(mContext, "CelebKonect", "Unable to get the gmail data");
//                return;
//            }
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        } else if (requestCode == TwitterAuthConfig.DEFAULT_AUTH_REQUEST_CODE) {
            client.onActivityResult(requestCode, responseCode, data);
            //  twitter related handling
        } else {
            // facebook related handling
            callbackManager.onActivityResult(requestCode, responseCode, data);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    //Gmail Call
    private void gmailCall() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //
                    }
                });
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    //Facebook Call
    private void faceBookCall() {
        loginManager.logOut();
        loginManager.logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
        loginManager.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        graphRequest(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d("FbError", error.toString());
                    }
                });
    }

    //Twitter Call
    private void twitterCall() {
        client = new TwitterAuthClient();
        client.authorize(SignInActivity.this, new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> twitterSessionResult) {
                Log.d("TAG", "twitterLogin:success" + twitterSessionResult);
                TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
                AccountService accountService = twitterApiClient.getAccountService();
                Call<User> call = accountService.verifyCredentials(true,
                        true, true);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void success(Result<User> result) {
                        User user = result.data;
                        if (user.email != null && !user.email.isEmpty()) {
                            if (user.name != null && !user.name.isEmpty()) {
                                String personPhotoUrl = "";
                                signUpOrInSocialNetWork(user.email, "socialLogin", user.name, personPhotoUrl,
                                        false, user.name, "", "twitter");
                            } else {
                                showSnackBar("Unable to access profile data", 0);
                                //  Common.getInstance().showSweetAlertWarning(mContext, "CelebKonect", "Unable to access profile data");
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Email id not available", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        exception.printStackTrace();
                    }
                });
            }

            @Override
            public void failure(TwitterException e) {
                Log.w("TAG", "twitterLogin:failure", e);
//                        updateUI(null);
            }
        });
    }

    public void graphRequest(AccessToken token) {
        GraphRequest request = GraphRequest.newMeRequest(token, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    Log.e("allData", object.toString());
                    String facebook_email = object.optString("email", "");
                    String first_name = object.optString("first_name", "");
                    String last_name = object.optString("last_name", "");
                    if (facebook_email.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Email id not available", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    profileImageUrl = "";
                    String afterReplaceUsername = "";
                    if (facebook_email.contains("@gmail.com")) {
                        afterReplaceUsername = facebook_email.replace("@gmail.com", "");
                    } else if (facebook_email.contains("@facebook.com")) {
                        afterReplaceUsername = facebook_email.replace("@fb.com", "");
                    } else {
                        afterReplaceUsername = Common.getUserName(facebook_email);
                    }
                    signUpOrInSocialNetWork(facebook_email, "socialLogin", afterReplaceUsername, profileImageUrl,
                            false, first_name, last_name, "facebook");
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Email id not available", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void socialNetPermissionAccess() {
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(getString(R.string.com_twitter_sdk_android_CONSUMER_KEY),
                        getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET)))
                .debug(true)
                .build();
        Twitter.initialize(config);
        FacebookSdk.sdkInitialize(getApplicationContext());


        AppEventsLogger.activateApp(getApplication());
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //Getting Facebook hash Key
        try {
            PackageInfo info = getPackageManager().getPackageInfo("info.celebkonnect.flow.celebflow",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                loginManager.logOut();
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private void signUpOrInSocialNetWork(final String emailId, final String loginType, final String username,
                                         String imageUrldd, boolean secureNewLogin, String firstName, String lastName, String socialMediaTypeLocal) {
        // showProgressDialog();
        loginMailifSocialLogin = emailId;
        loginTypeIfSocialLogin = loginType;
        loginUserNameifSocialLogin = username;
        firstNameSocial = firstName;
        lastNameSocial = lastName;

        socialMediaType = socialMediaTypeLocal;

        String refreshedToken = "";
        if (FirebaseInstanceId.getInstance().getToken() != null && !FirebaseInstanceId.getInstance().getToken().isEmpty()) {
            refreshedToken = FirebaseInstanceId.getInstance().getToken();
        } else {
            refreshedToken = FirebaseInstanceId.getInstance().getToken();
        }

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("email", emailId);
            jsonObject.put("loginType", loginType);
            jsonObject.put("deviceToken", refreshedToken);
            jsonObject.put("osType", "Android");
            jsonObject.put("secureNewLogin", secureNewLogin);

            Log.e("deviceTokenData", refreshedToken);

        } catch (Exception e) {
            e.printStackTrace();
        }

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.memberRegistrationNewSingUpJSON("null", RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                jsonObject.toString()));
        Common.getInstance().callAPI(new ApiRequestModel().setModel(this, call, "goLoginSocial", true, iApiListener, true));
    }

    private void forceclose_popup(final String forceMsg, final Boolean isSocialLogin) {
        final Dialog forceclose_popup;
        TextView take_photo_txt;
        Button yesBtn, noBtn;
        forceclose_popup = new Dialog(this);
        forceclose_popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        forceclose_popup.requestWindowFeature(Window.FEATURE_NO_TITLE);
        forceclose_popup.setCancelable(false);
        forceclose_popup.setContentView(R.layout.force_to_close_popup);

        take_photo_txt = (TextView) forceclose_popup.findViewById(R.id.greetingmsgtxt);
        if (forceMsg != null && !forceMsg.isEmpty()) {
            take_photo_txt.setText(Constants.FORCED_LOGOUT + "");
//            take_photo_txt.setText(forceMsg + "");
        } else {
            take_photo_txt.setText("");
        }


        noBtn = (Button) forceclose_popup.findViewById(R.id.noBtn);
        yesBtn = (Button) forceclose_popup.findViewById(R.id.yesBtn);
        forceclose_popup.show();

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forceclose_popup.dismiss();
            }
        });
        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forceclose_popup.dismiss();
                if (isSocialLogin) {
                    signUpOrInSocialNetWork(loginMailifSocialLogin, loginTypeIfSocialLogin,
                            loginUserNameifSocialLogin, null, true,
                            firstNameSocial, lastNameSocial, "");
                } else {
                    String email;
                    if (llMultipleAccountLogin.getVisibility() == View.VISIBLE) {
                        email = tvMAEmail.getText().toString();
                    } else {
                        email = edtEmail.getText().toString();
                    }
                    String password;
                    if (llMultipleAccountLogin.getVisibility() == View.VISIBLE) {
                        password = etMAPassword.getText().toString();
                    } else {
                        password = password_signin.getText().toString();
                    }
                    datasendserver(true, email, password);
                }
            }
        });
    }

    @Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
        if (condition.equals(ApiClient.LOGIN_URL)) {
            try {
                Type type = new TypeToken<LoginResponse>() {
                }.getType();

                LoginResponse loginResponse = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                if (loginResponse != null) {
                    if (!loginResponse.userInfo.isUsernameVerified) {
                        if (InternetSpeedChecker.checkInternetAvaialable(this)) {
                            SignUpConditions signUpConditions = new SignUpConditions();
                            signUpConditions.setLogin(true);
                            signUpConditions.setUserName(loginResponse.userInfo.username);
                            signUpConditions.setUserId(loginResponse.userInfo.id);
                            signUpConditions.setFirstName(loginResponse.userInfo.firstName);

                            Intent intent = new Intent(this, HelperActivity.class);
                            intent.putExtra(Constants.FRAGMENT_TITLE, "");
                            intent.putExtra(Constants.FRAGMENT_KEY, 1001);// SignUpFragment
                            intent.putExtra("signUpConditions", signUpConditions);
                            startActivity(intent);
                        }
                    } else {
                        appendUserDetails(loginResponse, false);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (condition.equals("goLoginSocial")) {
            try {
                Type type = new TypeToken<LoginResponse>() {
                }.getType();
                try {

                    JSONObject jsonObject1 = new JSONObject(new Gson().toJson(apiResponseModel.data));
                    Boolean isExist = null;
                    if (jsonObject1.has("exist")) {
                        isExist = jsonObject1.getBoolean("exist");
                    }
                    if (isExist != null && !isExist) {
                        SignUpConditions signUpConditions = new SignUpConditions();
                        signUpConditions.setLogin(false);
                        signUpConditions.setUserName("");
                        signUpConditions.setForgot(false);
                        signUpConditions.setChangePassword(false);
                        signUpConditions.setForOTPVerification(false);

                        signUpConditions.setSocialNetwork(true);

                        signUpConditions.setFirstNameSocial(firstNameSocial);
                        signUpConditions.setLastNameSocial(lastNameSocial);
                        signUpConditions.setSocialNetworkEmailID(loginMailifSocialLogin);

                        signUpConditions.setSocialMediaType(socialMediaType);

                        if (InternetSpeedChecker.checkInternetAvaialable(this)) {
                            Intent intent = new Intent(this, HelperActivity.class);
                            intent.putExtra(Constants.FRAGMENT_TITLE, "");
                            intent.putExtra(Constants.FRAGMENT_KEY, 1001);// SignUpFragment
                            intent.putExtra("signUpConditions", signUpConditions);
                            startActivity(intent);
                        }
                    } else {
                        LoginResponse loginResponse = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                        if (loginResponse != null) {
                            if (!loginResponse.userInfo.isUsernameVerified) {
                                if (InternetSpeedChecker.checkInternetAvaialable(this)) {
                                    SignUpConditions signUpConditions = new SignUpConditions();
                                    signUpConditions.setLogin(true);
                                    signUpConditions.setUserName(loginResponse.userInfo.username);
                                    signUpConditions.setForgot(false);
                                    signUpConditions.setChangePassword(false);
                                    signUpConditions.setForOTPVerification(false);
                                    signUpConditions.setSocialNetwork(false);
                                    signUpConditions.setFirstNameSocial(firstNameSocial);
                                    signUpConditions.setLastNameSocial(lastNameSocial);
                                    signUpConditions.setSocialNetworkEmailID(loginMailifSocialLogin);
                                    signUpConditions.setUserId(loginResponse.userInfo.id);
                                    signUpConditions.setFirstName(loginResponse.userInfo.firstName);


                                    signUpConditions.setSocialMediaType(socialMediaType);

                                    Intent intent = new Intent(this, HelperActivity.class);
                                    intent.putExtra(Constants.FRAGMENT_TITLE, "");
                                    intent.putExtra(Constants.FRAGMENT_KEY, 1001);// SignUpFragment
                                    intent.putExtra("signUpConditions", signUpConditions);
                                    startActivity(intent);
                                }
                            } else {
                                appendUserDetails(loginResponse, true);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
        if (condition.equals(ApiClient.LOGIN_URL) || condition.equals("goLoginSocial")) {
            if (enumConstants.equals(EnumConstants.SERVER_ERROR)) {
                showSnackBar(Constants.SOMETHING_WENT_WRONG_SERVER, 0);
                // Common.getInstance().showSweetAlertWarning(SignInActivity.this, "CelebKonect", Constants.SOMETHING_WENT_WRONG_SERVER);
            } else {
                if (apiResponseModel.success.equals(Constants.SECURE_LOGOUT_RESULT)) {
                    forceclose_popup(apiResponseModel.message, condition.equals("goLoginSocial"));
                } else if (apiResponseModel.success.equals(Constants.FORGOT_EMAIL)) {
                    Toast.makeText(getApplicationContext(), apiResponseModel.message, Toast.LENGTH_LONG).show();
                } else if (apiResponseModel.success.equals(Constants.FAILURE_RESULT)) {
                    showSnackBar(apiResponseModel.message, 0);
                    //  Common.getInstance().showSweetAlertWarning(SignInActivity.this, "CelebKonect", apiResponseModel.message);
                }
            }
        }
    }

    private void appendUserDetails(LoginResponse loginResponse, Boolean isSocialLogin) {
        SessionManager.getInstance().appendUserDetails(loginResponse, isSocialLogin);
        try {
            String loginType = Common.getInstance().IsNullReturnValue(loginResponse.userInfo.loginType, "");
            if (remember_checkbox.isChecked() && !isSocialLogin) {
                LocalDB dataBase = new LocalDB(this);
                dataBase.open();
                long result = dataBase.createLogin(loginResponse.userInfo.id, loginResponse.userInfo.firstName,
                        loginResponse.userInfo.lastName, loginResponse.userInfo.avtarImgPath, edtEmail.getText().toString());
                dataBase.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void accountClick(MultipleAccountModel multipleAccountModel) {
        try {
            hideAllViews();
            llMultipleAccountLogin.setVisibility(View.VISIBLE);
            llSignIn.setVisibility(View.GONE);
            tvMAEmail.setText(multipleAccountModel.emailOrMobile);
            if (!Common.getInstance().IsNull(multipleAccountModel.firstName)) {
                tvMAUserName.setText(multipleAccountModel.firstName + " " + multipleAccountModel.lastName);
            } else {
                tvMAUserName.setText("");
            }
            if (!Common.getInstance().IsNull(multipleAccountModel.image)) {
                Common.getInstance().setGlideImage(this, ApiClient.BASE_URL + multipleAccountModel.image, ivMAProfileImage);
            } else {
                ivMAProfileImage.setImageResource(R.drawable.ic_profile_square_pleace_holder);
            }
            //
            etMAPassword.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeAccount(MultipleAccountModel multipleAccountModel, int position) {
        new AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage("Are you sure, you want to remove this account?")
                .setCancelable(true)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            dialogInterface.cancel();
                            LocalDB dataBase = new LocalDB(getApplicationContext());
                            dataBase.open();
                            dataBase.DeleteLoginRowByID(multipleAccountModel.PrimID);
                            dataBase.close();
                            //
                            if (manageAccountsAdapter != null) {
                                multipleAccountModelArrayList.remove(position);
                                manageAccountsAdapter.notifyItemRemoved(position);
                                manageAccountsAdapter.notifyDataSetChanged();
                            }
                            if (multipleAccountModelArrayList.size() <= 0) {
                                setMultipleAccountsData();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .create()
                .show();
    }

    @Override
    public void changeTitle(String title) {

    }

    @Override
    public void showSnackBar(String snackBarText, int type) {
        UtilityNew.showSnackBar(activity(), coordinatorLayout, snackBarText, type);
    }

    @Override
    public Activity activity() {
        return this;
    }

    /*@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks whether a hardware keyboard is available
        if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {
            Toast.makeText(this, "keyboard visible", Toast.LENGTH_SHORT).show();
            logoIcon.setVisibility(View.VISIBLE);

        } else if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
            Toast.makeText(this, "keyboard hidden", Toast.LENGTH_SHORT).show();
            logoIcon.setVisibility(View.GONE);
        }
    }*/

    private void headerLogoHideShow(ImageView headerIcon, int marginTop) {
        if (Common.getInstance().isKeyboardVisible(activity())) {
            headerIcon.getLayoutParams().height = (int) getResources().getDimension(R.dimen._50sdp);
            headerIcon.getLayoutParams().width = (int) getResources().getDimension(R.dimen._50sdp);
            Common.getInstance().setMargins(headerIcon, 0, 0, 0, 0);
        } else {
            headerIcon.getLayoutParams().width = (int) getResources().getDimension(R.dimen._90sdp);
            headerIcon.getLayoutParams().height = (int) getResources().getDimension(R.dimen._130sdp);
            Common.getInstance().setMargins(headerIcon, 0, marginTop, 0, 0);
        }
    }


}
