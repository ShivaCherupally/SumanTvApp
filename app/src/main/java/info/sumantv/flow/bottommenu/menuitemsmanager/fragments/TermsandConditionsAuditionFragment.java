package info.sumantv.flow.bottommenu.menuitemsmanager.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import com.google.gson.reflect.TypeToken;
import info.sumantv.flow.retrofitcall.*;
import org.json.JSONObject;

import info.sumantv.flow.bottommenu.activity.HelperActivity;
import info.sumantv.flow.bottommenu.interfaces.fragments.IFragment;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.userflow.Util.Common;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.lang.reflect.Type;

public class TermsandConditionsAuditionFragment extends Fragment implements View.OnClickListener ,IFragment, IApiListener {
    Button continueBtn;
    CheckBox iagreecheckbox;
    boolean isChecked = false;
    private ProgressDialog progressDialog;
    String text = "", title = "";
    TextView knowtitle;
    TextView contentdatatxt;
    ApiInterface apiInterface;
    IApiListener iApiListener;

    public static TermsandConditionsAuditionFragment newInstance(String param1, String param2) {
        TermsandConditionsAuditionFragment fragment = new TermsandConditionsAuditionFragment();

        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iApiListener = this;
        ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.termsandconditions_audition, null);
        initializeView(root);
        initializeActions();
        getTermsAndConditions();
        return root;
    }
    private void initializeActions() {
        continueBtn.setOnClickListener(this);

        iagreecheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChecked) {
                    isChecked = false;
                    continueBtn.setBackgroundResource(R.drawable.submit_rectangle_grey);
                } else {
                   isChecked = true;
                    continueBtn.setBackgroundResource(R.drawable.submit_rectangle);
                }
            }
        });
    }

    private void initializeView(View root) {
        continueBtn = (Button) root.findViewById(R.id.continueBtn);
        iagreecheckbox = (CheckBox) root.findViewById(R.id.iagreecheckbox);
        contentdatatxt = (TextView) root.findViewById(R.id.contentdatatxt);
        knowtitle = (TextView) root.findViewById(R.id.knowtitle);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.continueBtn) {
            if (isChecked) {
                accessToNavigatingQuiz();
            } else {
//                Toast.makeText(this, "Please Agree the terms & conditions", Toast.LENGTH_SHORT).show();
            }

//            Intent i = new Intent(SuccessSubmittionActivity.this, CompaignActivity.class);
//            startActivity(i);
        }

    }

    
    private void accessToNavigatingQuiz() {
        SessionManager.getInstance().setKeyValue(SessionManager.KEY_TERMS_CONDITION_ALREADY_ACCESS,"TRUE");
        accessIntent(8024, HelperActivity.class,
                "Audition");
    }

    private void getTermsAndConditions() {
     //   progressDialog = Common.showProgressDialog(activity(), progressDialog);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiClient.GET_TERMS_CONDITION)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
     /*   ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<JsonElement> elementCall = apiInterface.getCommonDataCall(ApiClient.GET_TERMS_CONDITION);*/

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.getCommonDataCall(ApiClient.GET_TERMS_CONDITION);
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, ApiClient.GET_TERMS_CONDITION, true, iApiListener, true));


    }
    private void accessIntent(int  str, Class HelperActivity, String headerTitle) {
        Intent profileIntent = new Intent(getActivity(), HelperActivity);
        profileIntent.putExtra("title",headerTitle );
        profileIntent.putExtra(Constants.FRAGMENT_KEY, str);//
        startActivity(profileIntent);


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
        if (condition.equals(ApiClient.GET_TERMS_CONDITION)) {
            try {
                Type type = new TypeToken<JsonElement>() {} .getType();
                JsonElement jsonElement = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                if (jsonElement != null) {
                    if (jsonElement.toString() != null && !jsonElement.toString().isEmpty()) {
                        String responsedata = jsonElement.toString();
                        JSONObject jsonObject = new JSONObject(responsedata);
                        title = jsonObject.getString("title");
                        text = jsonObject.getString("text");
                        Log.e("textTerms", text + "");


                        contentdatatxt.setText(Common.replaceNewlinesWithBreaks(text));





                        if (title != null && !title.isEmpty()) {
                            knowtitle.setText(title);
                        } else {

                        }
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }}
    }

    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {

    }
}
