package info.sumantv.flow.bottommenu.menuitemsmanager.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.*;
import android.widget.*;

import info.sumantv.flow.bottommenu.interfaces.fragments.IFragment;

import info.sumantv.flow.R;
import info.sumantv.flow.menu_list.Settings.ChangePassword.ChangePasswordData;
import info.sumantv.flow.retrofitcall.*;
import info.sumantv.flow.userflow.Util.Common;
import info.sumantv.flow.utils.Logger;
import info.sumantv.flow.utils.Utility;
import retrofit2.Call;

import java.util.regex.Pattern;

public class ChangePasswordFragment extends Fragment implements View.OnClickListener, IFragment, IApiListener {
    private androidx.appcompat.widget.Toolbar mToolbar;
    private TextInputEditText mEnterRecoveryCode, edtPassword, edtConfirmPassword;
    private Button mSubmit, change_password_cancel;
    private TextInputLayout mCodeInputLayout;
    public TextView mToolBarTitleTextView;
    private TextInputLayout mPassWordLayout, mConfirmPasswordLayout;
    androidx.appcompat.widget.Toolbar toolbar_back;
    Button backbtn;
    TextView toolbarheadertitle;
    ApiInterface apiInterface;
    EditText oldpassword, newpassword, confirmassword;
    Button submit;
    ImageView imageViewRepasswordhide, imageViewpasswordhide;
    private ProgressDialog progressDialog;
    Typeface face;
    IApiListener iApiListener;

    private ImageView imgEigthCharacter, imgLowerCase, imgUpperCase, imgOneDigit, imgSpecialCharacter;
    private Dialog promoDialog;

    public static ChangePasswordFragment newInstance(String param1, String param2) {
        ChangePasswordFragment fragment = new ChangePasswordFragment();

        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.changepassword_activity, null);
        iApiListener = this;
        initViews(root);
        intilizeActions();
        return root;
    }

    private void intilizeActions() {
        submit.setOnClickListener(this);
        change_password_cancel.setOnClickListener(this);

    }

    private void initViews(View root) {
//        oldpassword = (Ed)
        oldpassword = (EditText) root.findViewById(R.id.oldpassword);
        newpassword = (EditText) root.findViewById(R.id.newpassword);
        confirmassword = (EditText) root.findViewById(R.id.confirmassword);
        imageViewpasswordhide = (ImageView) root.findViewById(R.id.imageViewpasswordhide);
        imageViewpasswordhide.setBackground(getResources().getDrawable(R.drawable.ic_eye_hide));
        imageViewRepasswordhide = (ImageView) root.findViewById(R.id.imageViewRepasswordhide);
        imageViewRepasswordhide.setBackground(getResources().getDrawable(R.drawable.ic_eye_hide));
        submit = (Button) root.findViewById(R.id.submit);
        change_password_cancel = (Button) root.findViewById(R.id.change_password_cancel);
        face = Utility.getTypeface(10, activity());
        newpassword.setTypeface(face);

        imageViewpasswordhide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int inputTypeValue = newpassword.getInputType();
                int typetest = InputType.TYPE_TEXT_VARIATION_PASSWORD;

                String passworddata = "";
                if (newpassword.getText().toString() != null) {
                    passworddata = newpassword.getText().toString();
                }

                Logger.d("uday", inputTypeValue + " ---  " + typetest + " ---- " + InputType.TYPE_CLASS_TEXT);
                if (inputTypeValue == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                    newpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    newpassword.setInputType(InputType.TYPE_CLASS_TEXT);
                    newpassword.setTypeface(face);
                    imageViewpasswordhide.setBackground(getResources().getDrawable(R.drawable.ic_eye_hide));

                    newpassword.setText("");
                    newpassword.append(passworddata);

                } else {
                    newpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    newpassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    newpassword.setTypeface(face);
                    imageViewpasswordhide.setBackground(getResources().getDrawable(R.drawable.ic_eye_unhide));
                    newpassword.setText("");
                    newpassword.append(passworddata);
                }

            }
        });


        imageViewRepasswordhide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int inputTypeValue = confirmassword.getInputType();
                int typetest = InputType.TYPE_TEXT_VARIATION_PASSWORD;

                String passworddata = "";
                if (confirmassword.getText().toString() != null) {
                    passworddata = confirmassword.getText().toString();
                }

                Logger.d("uday", inputTypeValue + " ---  " + typetest + " ---- " + InputType.TYPE_CLASS_TEXT);
                if (inputTypeValue == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                    confirmassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    confirmassword.setInputType(InputType.TYPE_CLASS_TEXT);
                    confirmassword.setTypeface(face);
                    imageViewRepasswordhide.setBackground(getResources().getDrawable(R.drawable.ic_eye_hide));
                    confirmassword.setText("");
                    confirmassword.append(passworddata);
                } else {
                    confirmassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    confirmassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    confirmassword.setTypeface(face);
                    imageViewRepasswordhide.setBackground(getResources().getDrawable(R.drawable.ic_eye_unhide));
                    confirmassword.setText("");
                    confirmassword.append(passworddata);
                }

            }
        });


        newpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String newpasswordText = editable.toString().replaceAll(" ", "");
                if (!editable.toString().equals(newpasswordText)) {
                    newpassword.setText(newpasswordText);
                    newpassword.setSelection(newpasswordText.length());
                }
            }
        });
        confirmassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String ConpasswordText = editable.toString().replaceAll(" ", "");
                if (!editable.toString().equals(ConpasswordText)) {
                    confirmassword.setText(ConpasswordText);
                    confirmassword.setSelection(ConpasswordText.length());
                }
            }
        });
    }


    private void validateFields() {

        String password = newpassword.getText().toString().trim();
        String confirmPassward = confirmassword.getText().toString().trim();

        if (!password.isEmpty() && newpassword.length() != 0) {
            if (validatePasswordNew()) {
                if (!confirmPassward.isEmpty() && confirmPassward.length() != 0) {
                    if (password.equals(confirmPassward)) {
                        dataSendingServer();
                    } else {
                        Toast.makeText(getActivity(), "Your passwords mismatch", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Please enter confirm password", Toast.LENGTH_SHORT).show();
                }


            }

        } else {
            Toast.makeText(getActivity(), "Please enter new password", Toast.LENGTH_SHORT).show();

            //   showSnackBar("Please enter password",2);
        }


//        if (newpassword.getText().toString().length() != 0) {
//            if (confirmassword.getText().toString().length() != 0) {
//                if (newpassword.getText().toString().equals(confirmassword.getText().toString())) {
//                    dataSendingServer();
//                } else {
//                    //Toast.makeText(activity(), "Password did't match", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(activity(), " New password and Confirm password mismatch", Toast.LENGTH_LONG).show();
//                }
//            } else {
//                Toast.makeText(activity(), "please enter confirm password", Toast.LENGTH_LONG).show();
//            }
//
//        } else {
//            Toast.makeText(activity(), "Please enter password", Toast.LENGTH_LONG).show();
//        }
    }

    private boolean validatePasswordNew() {

        boolean specialCharacter = false, digit = false, upperCase = false, lowerCase = false, eigthCharacter = false;
        boolean returnValue = false;
        /*Pattern regex = Pattern.compile("[.$&+@#^*()%!_{}*]");*/
        Pattern regex = Pattern.compile("[.$&+@#^*()%!_{}*]");
        if (TextUtils.isEmpty(newpassword.getText().toString())) {
            Common.getInstance().showSweetAlertWarning(getActivity(), "CelebKonect",
                    "Please enter new password");
            return false;
        } else {

            String pass = newpassword.getText().toString();
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
                promoDialog = new Dialog(getActivity());
                promoDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                promoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                promoDialog.setContentView(R.layout.password_error);


//                private ImageView imgEigthCharacter, imgLowerCase, imgUpperCase, imgOneDigit, imgSpecialCharacter;
                imgEigthCharacter = (ImageView) promoDialog.findViewById(R.id.imgEigthCharacter);
                imgLowerCase = (ImageView) promoDialog.findViewById(R.id.imgLowerCase);
                imgUpperCase = (ImageView) promoDialog.findViewById(R.id.imgUpperCase);
                imgOneDigit = (ImageView) promoDialog.findViewById(R.id.imgOneDigit);
                imgSpecialCharacter = (ImageView) promoDialog.findViewById(R.id.imgSpecialCharacter);

                promoDialog.show();

                if (!upperCase) {
                    imgUpperCase.setImageResource(R.drawable.password_cross);
                }
                if (!lowerCase) {
                    imgLowerCase.setImageResource(R.drawable.password_cross);
                }
                if (!digit) {
                    imgOneDigit.setImageResource(R.drawable.password_cross);
                }
                if (!specialCharacter) {
                    imgSpecialCharacter.setImageResource(R.drawable.password_cross);
                }
                if (!eigthCharacter) {
                    imgEigthCharacter.setImageResource(R.drawable.password_cross);
                }
                returnValue = false;
            }
        }

        return returnValue;


    }


//    private void validateFields() {
//        if (newpassword.getText().toString().length() != 0) {
//            if (confirmassword.getText().toString().length() != 0) {
//                if (newpassword.getText().toString().equals(confirmassword.getText().toString())) {
//                    dataSendingServer();
//                } else {
//                    //Toast.makeText(activity(), "Password did't match", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(activity(), " New password and Confirm password mismatch", Toast.LENGTH_LONG).show();
//                }
//            } else {
//                Toast.makeText(activity(), "please enter confirm password", Toast.LENGTH_LONG).show();
//            }
//
//        } else {
//            Toast.makeText(activity(), "Please enter password", Toast.LENGTH_LONG).show();
//        }
//    }


    private boolean validateRecoveryCode() {
        if (TextUtils.isEmpty(mEnterRecoveryCode.getText().toString())) {
            mCodeInputLayout.setErrorEnabled(true);
            mCodeInputLayout.setError("Please Enter Recovery Code");
            return false;
        } else {
            mCodeInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePassword() {
        if (TextUtils.isEmpty(edtPassword.getText().toString())) {
            mPassWordLayout.setErrorEnabled(true);
            mPassWordLayout.setError("Please Enter Password");
            return false;
        } else {
            mPassWordLayout.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateConfirmPassword() {
        if (TextUtils.isEmpty(edtConfirmPassword.getText().toString())) {
            mConfirmPasswordLayout.setErrorEnabled(true);
            mConfirmPasswordLayout.setError("Please Enter Confirm Password");
            return false;
        } else {
            mConfirmPasswordLayout.setErrorEnabled(false);
        }
        return true;
    }

    private boolean comparePassword() {
        if (!TextUtils.isEmpty(edtPassword.getText().toString())
                && !TextUtils.isEmpty(edtConfirmPassword.getText().toString())) {
            if (edtPassword.getText().toString().equalsIgnoreCase(edtConfirmPassword.getText().toString()))
                return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                activity().onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void navigateToUserSignInActivity() {
//        Intent intent = new Intent(UserRecoveryPassword.this, UserSignInActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
    }

    private void dataSendingServer() {
        ChangePasswordData changePasswordData = new ChangePasswordData(
                SessionManager.getInstance().getKeyValue(SessionManager.KEY_EMAIL_OR_MOBILE, ""), newpassword.getText().toString());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.changePassword(changePasswordData);
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, ApiClient.CHANGEPASSWORD_URL, true, iApiListener, false));
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submit) {
            validateFields();
        } else if (v.getId() == R.id.change_password_cancel) {
            activity().onBackPressed();
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
        if (condition.equals(ApiClient.CHANGEPASSWORD_URL)) {
            try {
                if (apiResponseModel.message != null) {
                    Toast.makeText(getContext(), apiResponseModel.message
                            , Toast.LENGTH_SHORT).show();
                    //   Common.getInstance().LogOut(activity(),"false");
                    activity().finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
        if (condition.equals(ApiClient.CHANGEPASSWORD_URL)) {
            Toast.makeText(activity(), "Fail to change password", Toast.LENGTH_SHORT).show();
        }
    }


}
