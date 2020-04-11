package info.sumantv.flow.otp;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import info.sumantv.flow.bottommenu.interfaces.fragments.IFragment;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.retrofitcall.*;
import info.sumantv.flow.userflow.Util.Common;
import info.sumantv.flow.utils.Utility;
import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 */

public class OTPFragment extends Fragment implements IFragment, IApiListener {

    private ImageView ivImage;
    private TextView tvVerifyText, tvCountDown;
    private RadioGroup card_radiogroup;
    private Button otpButton, buttonTryAgain;
    private EditText etOTP;
    private LinearLayout llTryagain, llOtpBox, llTryagain_button;
    private RadioButton rbMobile, rbEmail;
    private String medium, reason = "";

    private ProgressBar progress_bar;
    Bundle ar;
    private Runnable myRunnable1;
    Handler handler1;
    int delay;
    private int countDown = 30;
    private ProgressDialog progressDialog;
    CoordinatorLayout coordinator_layout;
    ApiInterface apiInterface;
    IApiListener iApiListener;

    public OTPFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iApiListener = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ot, container, false);
        ar = getArguments();
        setUp(view);

        return view;
    }

    private void setUp(View view) {

        tvCountDown = view.findViewById(R.id.tvCountDown);
        progress_bar = view.findViewById(R.id.progress_bar);
        rbMobile = view.findViewById(R.id.rbMobile);
        rbEmail = view.findViewById(R.id.rbEmail);
        llOtpBox = view.findViewById(R.id.llOtpBox);
        llTryagain = view.findViewById(R.id.llTryagain);
        llTryagain_button = view.findViewById(R.id.llTryagain_button);
        etOTP = view.findViewById(R.id.etOTP);
        otpButton = view.findViewById(R.id.otpButton);
        buttonTryAgain = view.findViewById(R.id.buttonTryAgain);
        card_radiogroup = view.findViewById(R.id.card_radiogroup);
        ivImage = view.findViewById(R.id.ivImage);
        tvVerifyText = view.findViewById(R.id.tvVerifyText);
        coordinator_layout = view.findViewById(R.id.coordinator_layout);

        if (SessionManager.userLogin.isEmailVerified) {
            if (SessionManager.getInstance().getKeyValue(SessionManager.KEY_EMAIL_OR_MOBILE,"") != null && !SessionManager.getInstance().getKeyValue(SessionManager.KEY_EMAIL_OR_MOBILE,"").isEmpty()) {
                rbEmail.setText(SessionManager.getInstance().getKeyValue(SessionManager.KEY_EMAIL_OR_MOBILE,""));
                rbEmail.setVisibility(View.VISIBLE);
            }
        }else {
            rbEmail.setVisibility(View.GONE);
        }
        if (SessionManager.userLogin.isMobileVerified) {
            if (SessionManager.getInstance().getKeyValue(SessionManager.KEY_MOBILE_NO,"") != null && !SessionManager.getInstance().getKeyValue(SessionManager.KEY_MOBILE_NO,"").isEmpty()) {
//            rbEmail.setText(SessionManager.getInstance().getKeyValue(SessionManager.KEY_EMAIL_OR_MOBILE,""));
                rbMobile.setText(SessionManager.getInstance().getKeyValue(SessionManager.KEY_MOBILE_NO,""));
                rbMobile.setVisibility(View.VISIBLE);
            }
        }  else {
            rbMobile.setVisibility(View.GONE);
        }

        tvVerifyText.setText(Constants.MOBILE_OTP_MESSAGE);

        llTryagain_button.setOnClickListener(v -> {
            //llTryagain.setVisibility(View.GONE);
            //   tvCountDown.setVisibility(View.GONE);
            sendOTP(view);

        });

        otpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (otpButton.getText().toString().equals("Send Verification Code")) {
                    sendOTP(view);
                } else if (otpButton.getText().toString().equals("Submit")) {
                    submitOTP(view);
                }
            }
        });

        buttonTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOTP(view);
            }
        });
        card_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.rbEmail) {

                    medium = "email";

                } else if (checkedId == R.id.rbMobile) {

                    medium = "mobile";
                }
            }
        });

    }

    private void submitOTP(View view) {

        if (etOTP.getText().toString().length() > 0) {

            if (!Utility.isNetworkAvailable(activity())) {
                showSnackBar(Constants.PLEASE_CHECK_INTERNET, 2);
                return;
            }
            String userid = null;
            apiInterface = ApiClient.getClient().create(ApiInterface.class);
            if (SessionManager.userLogin.userId != null
                    && !SessionManager.userLogin.userId.isEmpty()) {
                userid = SessionManager.userLogin.userId;
            }
            OTPSendManager opOtpVerifyManager = new OTPSendManager(userid, medium, etOTP.getText().toString());
            Call<ApiResponseModel> call = apiInterface.otpSendManager(opOtpVerifyManager);
            Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, ApiClient.SUBMIT_OTP,
                    true, iApiListener, true));


           /* progress_bar.setVisibility(View.VISIBLE);
            if (otpManager == null)
                otpManager = new OTPManager();
            Response.ErrorListener errorListener = error -> {
                progress_bar.setVisibility(View.GONE);
                showSnackBar(Constants.CONNECTION_ERROR, 2);
            };
            Response.Listener<JSONObject> successListener = response ->
            {
                Logger.d("[Response]", response.toString());
                try {

                    progress_bar.setVisibility(View.GONE);
                    Toast.makeText(activity(), response.getString("message"), Toast.LENGTH_SHORT).show();
                    if (response.get("message").equals("OTP verified successfully")) {

                        getManagerList();


                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    progress_bar.setVisibility(View.GONE);
                    showSnackBar(Constants.SOMETHING_WENT_WRONG, 2);
                }
            };
            otpManager.submitManagerOTP(medium, etOTP.getText().toString(), successListener, errorListener);
        } else {
            showSnackBar("Plese enter OTP", 2);
        }*/
        }
        else {
            //showSnackBar("Plese enter OTP", 2);
            Toast.makeText(getActivity(),"Plese enter OTP",Toast.LENGTH_SHORT).show();
        }
    }

    private void sendOTP(View view) {
        if (!Utility.isNetworkAvailable(activity())) {
            showSnackBar(Constants.PLEASE_CHECK_INTERNET, 2);
            return;
        }

        //progress_bar.setVisibility(View.VISIBLE);
        String userid = null;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        if (SessionManager.userLogin.userId != null
                && !SessionManager.userLogin.userId.isEmpty()) {
            userid = SessionManager.userLogin.userId;
        }
        OTPVerifyManager opOtpVerifyManager= new OTPVerifyManager(userid,medium, reason);
        Call<ApiResponseModel> call = apiInterface.otpVerifyManager( opOtpVerifyManager);
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call,ApiClient.SEND_OTP, true, iApiListener, true));

      /*  if (otpManager == null)
            otpManager = new OTPManager();
        Response.ErrorListener errorListener = error -> {
            progress_bar.setVisibility(View.GONE);
            showSnackBar(Constants.CONNECTION_ERROR, 2);
        };
        Response.Listener<JSONObject> successListener = response ->
        {
            progress_bar.setVisibility(View.GONE);

            try {
                Logger.d("[Response]", response.toString());

                Toast.makeText(activity(), response.getString("message"), Toast.LENGTH_SHORT).show();
                if (response.getString("message").equals("OTP sent successfully")) {
                    otpButton.setText("Submit");
                    llOtpBox.setVisibility(View.VISIBLE);
                    card_radiogroup.setVisibility(View.GONE);
                    llTryagain.setVisibility(View.VISIBLE);
                    //   startTimer();
                } else {

                }

            } catch (Exception e) {
                e.printStackTrace();
                progress_bar.setVisibility(View.GONE);
                showSnackBar(Constants.SOMETHING_WENT_WRONG, 2);
            }
        };
        otpManager.sendManagerOTP(medium, reason, successListener, errorListener);*/

    }

    private void startTimer() {

        //llTryagain.setClickable(false);
        handler1 = new Handler();
        delay = 1000; //milliseconds
        handler1.postDelayed(myRunnable1 = new Runnable() {
            public void run() {
                if (countDown <= 0) {
                    //  llTryagain.setClickable(true);
                    handler1.removeCallbacks(myRunnable1);
                } else {
                    handler1.postDelayed(this, delay);
                    countDown = countDown - 1;
                    tvCountDown.setText(String.valueOf(countDown));
                }
            }
        }, delay);

    }

    public static Fragment newInstance(String o, String o1) {

        OTPFragment otpFragment = new OTPFragment();
        return otpFragment;

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
        if (condition.equalsIgnoreCase("getMyClient")) {

        }else if (condition.equalsIgnoreCase(ApiClient.SEND_OTP)){
            Toast.makeText(activity(), apiResponseModel.message, Toast.LENGTH_LONG).show();
            otpButton.setText("Submit");
            llOtpBox.setVisibility(View.VISIBLE);
            card_radiogroup.setVisibility(View.GONE);
            llTryagain.setVisibility(View.VISIBLE);
        }else if (condition.equalsIgnoreCase( ApiClient.SUBMIT_OTP)){
            Toast.makeText(activity(), apiResponseModel.message, Toast.LENGTH_LONG).show();
//            getManagerList();
        }


    }

        @Override
        public void apiErrorResponse (String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel){
            if (condition.equalsIgnoreCase(ApiClient.SEND_OTP)){

            }else if (condition.equalsIgnoreCase( ApiClient.SUBMIT_OTP)){

            }
        }
    }
