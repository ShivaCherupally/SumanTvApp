//package info.celebkonnect.flow.celebflow.ForgotpasswordActivity;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Build;
//import android.os.Bundle;
//import androidx.annotation.Nullable;
//import androidx.coordinatorlayout.widget.CoordinatorLayout;
//import com.google.android.material.textfield.TextInputLayout;
//import androidx.appcompat.app.AppCompatActivity;
//import android.text.Editable;
//import android.text.TextUtils;
//import android.text.TextWatcher;
//import android.util.Log;
//import android.util.Patterns;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.WindowManager;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import info.celebkonnect.flow.retrofitcall.*;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import info.celebkonnect.flow.celebflow.R;
//import info.celebkonnect.flow.celebflow.SignInActivity;
//import info.celebkonnect.flow.userflow.Util.Common;
//import okhttp3.MediaType;
//import okhttp3.RequestBody;
//import retrofit2.Call;
//
///**
// * Created by Shiva on 5/31/2018.
// */
//
//public class ForgotPasswordActivityy extends AppCompatActivity implements IApiListener {
//    private EditText mEmailEditText;
//    private Button mSubmit, cancel, navigateBackBtn;
//    private TextInputLayout mEmailInputLayout;
////    private ProgressDialog progressDialog;
//    String username, emailid, logintype;
//    CoordinatorLayout coordinatorLayout;
//    String EMAIL_ID;
//    ApiInterface apiInterface;
//    TextView sentpwdtxt;
//    Context mContext;
//    //PopUpDialog
//    IApiListener iApiListener;
////    View mView;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_user_forgot_password);
//        iApiListener = this;
//        initializeViews();
//        hidestatusBar();
//
//
//        mEmailEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//                String firstnameSpace = editable.toString().replaceAll(" ", "");
//                if (!editable.toString().equals(firstnameSpace)) {
//                    mEmailEditText.setText(firstnameSpace);
//                    mEmailEditText.setSelection(firstnameSpace.length());
//                    // alert the user
//                }
//            }
//        });
//
//        mSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
//
//                if (mEmailEditText.getText().toString().length() != 0) {
//                    if (isValidEmail(mEmailEditText.getText().toString())) {
//                        mEmailInputLayout.setErrorEnabled(false);
//                        if (!TextUtils.isEmpty(mEmailEditText.getText().toString())) {
//                            getOTP(true);
//                        }
//                    } else if (mEmailEditText.getText().toString().matches("[0-9]+")) {
//                        getOTP(false);
//                    } else {
//                        Common.getInstance().showSweetAlertWarning(mContext, "CelebKonect", "Invalid / email/phone number does \nnot exist");
//                    }
//                } else {
//                    Common.getInstance().showSweetAlertWarning(mContext, "CelebKonect", "Please enter \nregistered email id");
//
//                }
//
////                if (Common.isMobileOrEmailvalidation(ForgotPasswordActivityy.this,
////                        mEmailEditText.getText().toString())) {
////                    makeJsonObjReq();
////                }
//
//            }
//        });
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        navigateBackBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//    }
//
//    private void getOTP(boolean isEmailID) {
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("emailOrMobileNumber", mEmailEditText.getText().toString());
//            if (isEmailID) {
//                jsonObject.put("medium", "email");
//            } else {
//                jsonObject.put("medium", "mobile");
//            }
//            jsonObject.put("mode", "getOTP");
//            apiInterface = ApiClient.getClient().create(ApiInterface.class);
//            Call<ApiResponseModel> call = apiInterface.forgotGetOTP(RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
//                    jsonObject.toString()));
//
//            Common.getInstance().callAPI(new ApiRequestModel().setModel(this, call, "getOTP", true, iApiListener, true));
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//    private void initializeViews() {
//
////        progressDialog = new ProgressDialog(ForgotPasswordActivityy.this, R.style.AppCompatAlertDialogStyle);
//        mEmailEditText = (EditText) findViewById(R.id.edt_email);
//        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
//        mSubmit = (Button) findViewById(R.id.submit);
//        navigateBackBtn = (Button) findViewById(R.id.navigateBackBtn);
//        cancel = (Button) findViewById(R.id.cancel);
//        sentpwdtxt = (TextView) findViewById(R.id.sentpwdtxt);
//        sentpwdtxt.setVisibility(View.GONE);
//        mEmailInputLayout = (TextInputLayout) findViewById(R.id.text_input_mail);
//
//        mContext = ForgotPasswordActivityy.this;
//
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                onBackPressed();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    public final static boolean isValidEmail(CharSequence target) {
//        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
//    }
//
//    private void navigateToUserRecoveryPassword() {
////        Intent intent = new Intent(ForgotPasswordActivityy.this, UserRecoveryPassword.class);
////        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        startActivity(intent);
//    }
//
//
//
//
//    private void hidestatusBar() {
//        //How to hide status bar in android in just one activity
//        if (Build.VERSION.SDK_INT > 19) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        } else {
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
//    }
//
//
//    @Override
//    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
//        if (condition.equals(ApiClient.POST_COM_LOG)) {
//            try {
////                Type type = new TypeToken<CommunicationLogData>() {} .getType();
////                CommunicationLogData communicationLogData = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
//                if (apiResponseModel.message != null) {
//
//                    String message = apiResponseModel.message;
//                    Log.v("message", message);
//                    if (message != null) {
//                        Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
//                        startActivity(intent);
//
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Communication call error", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else if (condition.equals("getOTP")) {
//
//        }
//    }
//
//    @Override
//    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
//        if (condition.equals(ApiClient.POST_COM_LOG)) {
//        } else if (condition.equals("getOTP")) {
//        }
//    }
//}
