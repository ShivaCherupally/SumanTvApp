package info.dkapp.flow.bottommenu.mypaymentactionsfragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.*;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import info.dkapp.flow.bottommenu.activity.HelperActivity;
import info.dkapp.flow.bottommenu.generic.EmptyDataNewAdapter;
import info.dkapp.flow.bottommenu.mypaymentactionsfragment.CreditsModelDatas.CreditPackageInfo;
import info.dkapp.flow.bottommenu.mypaymentactionsfragment.CreditsModelDatas.CreditPageInfo;
import info.dkapp.flow.bottommenu.mypaymentactionsfragment.CreditsModelDatas.PackageSelectionData;

import info.dkapp.flow.R;
import info.dkapp.flow.celebflow.constants.Constants;
import info.dkapp.flow.menu_list.MyCart.MyCartActivity.CreditPackage;
import info.dkapp.flow.menu_list.MyCart.MyCartActivity.CreditRechargeAdapter;
import info.dkapp.flow.retrofitcall.*;
import info.dkapp.flow.userflow.Util.Common;
import info.dkapp.flow.utils.RecyclerUtil.CommonRecycler;
import info.dkapp.flow.utils.RecyclerUtil.IRecyclerViewCommon;
import retrofit2.Call;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CLIPBOARD_SERVICE;

public class CreditsRecharge extends Fragment implements CreditPackage, IApiListener, IRecyclerViewCommon, View.OnClickListener {

    private String countryCode = "INR";

    private Integer currencyValue = 0;

    IApiListener iApiListener;

    @BindView(R.id.imageViewBack)
    ImageView imageViewBack;

    @BindView(R.id.credits_recycler)
    RecyclerView credits_recycler;

    @BindView(R.id.credit_recharge_btn)
    Button credit_recharge_btn;

    @BindView(R.id.cancel_payment)
    Button cancel_payment;

    @BindView(R.id.credit_bal)
    TextView credit_bal;


    @BindView(R.id.reffercodetxt)
    TextView reffercodetxt;


    @BindView(R.id.refferalcreditstxt)
    TextView refferalcreditstxt;


    @BindView(R.id.entercreditEt)
    EditText entercreditEt;


    @BindView(R.id.amounttxt)
    TextView amounttxt;

    @BindView(R.id.notetxt)
    TextView notetxt;


    @BindView(R.id.currencySpn)
    Spinner currencySpn;

    @BindView(R.id.bottomLayout)
    LinearLayout bottomLayout;

    @BindView(R.id.sharelayout)
    LinearLayout sharelayout;


    @BindView(R.id.copyLayout)
    LinearLayout copyLayout;

    private ClipboardManager myClipboard;
    private ClipData myClip;


    boolean serverIssueOccure = false;
    String currencySymbol = "₹";
    boolean isSelectedItem = false;

    double convertedEnterdAmount = 0.0;

    CreditRechargeAdapter creditRechargeAdapter;
    int selectedPosition = 0;

    ArrayList<CreditPackageInfo> creditPackageInfo;


    public static CreditsRecharge newInstance(String param1, String param2) {
        CreditsRecharge fragment = new CreditsRecharge();
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_credti_recharge_actvity, null);

        ButterKnife.bind(this, root);

        initializeAvailableData();

        credit_recharge_btn.setOnClickListener(this::onClick);

        cancel_payment.setOnClickListener(this::onClick);

        imageViewBack.setOnClickListener(this::onClick);

        sharelayout.setOnClickListener(this::onClick);

        copyLayout.setOnClickListener(this::onClick);

        entercreditEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                actionContinue(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String credisEntered = editable.toString();
                if (credisEntered.startsWith("0")) {
                    entercreditEt.setText("");
                    amounttxt.setText(currencySymbol + "0");
                    convertedEnterdAmount = 0.0;
                } else {
                    if (credisEntered.length() > 0) {
                        if (isSelectedItem) {
                            isSelectedItem = false;
                            if (creditRechargeAdapter != null) {
                                creditRechargeAdapter.refreshAdapter(-1);
                            }
                        }
                        try {
                            //If required with server
//                            getCalculateValue(currenctyType, Long.parseLong(credisEntered));

                            if (countryCode.equals("INR")) {
                                amounttxt.setText("₹" + credisEntered);

                                convertedEnterdAmount = Double.parseDouble(credisEntered);

                            } else {
                                if (currencyValue != null && currencyValue != 0) {

                                    DecimalFormat twoDecimal = new DecimalFormat("0.00");

                                    double convertionCurrency = Double.parseDouble(credisEntered) / currencyValue;

                                    convertedEnterdAmount = Double.parseDouble(twoDecimal.format(convertionCurrency));

                                    amounttxt.setText("$" + String.valueOf(twoDecimal.format(convertionCurrency)));
                                } else {
                                    convertedEnterdAmount = 0.0;
                                    amounttxt.setText("₹" + credisEntered);
                                }
                            }

                        } catch (Exception e) {

                        }
                        actionContinue(true);
                    } else {
                        amounttxt.setText(currencySymbol + "0");
                        convertedEnterdAmount = 0.0;
                        if (!isSelectedItem) {
                            actionContinue(false);
                        }
                    }
                }


            }
        });

        return root;
    }


    private void actionContinue(boolean enable) {
        if (enable) {
            credit_recharge_btn.setClickable(enable);
            int colorInt = getResources().getColor(R.color.skyblueNew);
            credit_recharge_btn.setTextColor(colorInt);
        } else {
            credit_recharge_btn.setClickable(enable);
            int colorInt = getResources().getColor(R.color.light_gray);
            credit_recharge_btn.setTextColor(colorInt);
        }
    }


    private void initializeAvailableData() {
        iApiListener = this;
        countryCode = Common.getCoutryCode(getActivity());
        setCurrencyData();
        myClipboard = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);


    }

    private void setCurrencyData() {
        List<String> reasonsList;
        reasonsList = new ArrayList<String>();

        reasonsList.add(getActivity().getResources().getString(R.string.inrcurrency));
        reasonsList.add(getActivity().getResources().getString(R.string.auscurrency));
        reasonsList.add(getActivity().getResources().getString(R.string.uscurrency));


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, reasonsList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencySpn.setAdapter(dataAdapter);

        currencySpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    getPackagesData("INR");
                } else if (position == 1) {
                    getPackagesData("AUD");
                } else if (position == 2) {
                    getPackagesData("USD");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
    }

    private void rechargeScreenNavigate() {
        try {

            if (isSelectedItem) {
                SessionManager.getInstance().setKeyValue(SessionManager.KEY_CREDIT_REACHARGE_Credits,
                        String.valueOf(creditPackageInfo.get(selectedPosition).credits));

            } else {
                SessionManager.getInstance().setKeyValue(SessionManager.KEY_CREDIT_REACHARGE_Credits,
                        entercreditEt.getText().toString());
            }


            PackageSelectionData packageSelectionData;
            if (isSelectedItem) {
                packageSelectionData = new PackageSelectionData(true, creditPackageInfo.get(selectedPosition)._id, countryCode,
                        0.0,
                        0.0);
            } else {
                packageSelectionData = new PackageSelectionData(false, "",
                        countryCode, Double.parseDouble(entercreditEt.getText().toString()),
                        convertedEnterdAmount);
            }


            Intent intent = new Intent(getActivity(), HelperActivity.class);
            intent.putExtra(Constants.FRAGMENT_TITLE, "Payment Option");
            intent.putExtra("packageSelectionDataObj", packageSelectionData);
            intent.putExtra(Constants.FRAGMENT_KEY, 8064);
            startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void getSelectedPackagePos(int selectedPositionLocal) {

        amounttxt.setText(currencySymbol + "0");
        entercreditEt.setText("");
        isSelectedItem = true;

        actionContinue(true);

        selectedPosition = selectedPositionLocal;


    }


    @Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
        if (condition.equals(Constants.CreditConstants.CREDITS_PACKAGE_COLLECTION)) {
            try {
                Type type = new TypeToken<CreditPageInfo>() {
                }.getType();
                CreditPageInfo creditPageInfo = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);

                if (creditPageInfo != null) {

                    setInfo(creditPageInfo);

                    if (creditPageInfo.creditPackageInfo.size() > 0) {
                        creditPackageInfo = creditPageInfo.creditPackageInfo;
                        credits_recycler.setAdapter(creditRechargeAdapter = new CreditRechargeAdapter(creditPageInfo.creditPackageInfo,
                                creditPageInfo.currencyType, CreditsRecharge.this));

                    } else {
                        nodataList(credits_recycler, Constants.WHOOPS, Constants.NO_PACKAGES, R.drawable.ic_nodata);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
        if (condition.equals(Constants.CreditConstants.CREDITS_PACKAGE_COLLECTION)) {
            serverIssueOccure = true;
            credits_recycler.setAdapter(new EmptyDataNewAdapter(getActivity(), Constants.SOMETHING_WENT_WRONG_SERVER, Constants.DRAG_TO_RETRY, R.drawable.ic_no_internet));
        } else if (condition.equals(Constants.CreditConstants.CHECK_CURRENCY_CALCULATER)) {
            entercreditEt.requestFocus();
            if (entercreditEt.requestFocus()) {
                ((HelperActivity) getContext()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
            Toast.makeText(getActivity(), "Server issue", Toast.LENGTH_SHORT).show();

            actionContinue(false);
        }
    }

    private void setInfo(CreditPageInfo creditPageInfo) {


        if (creditPageInfo.creditInfo.getCumulativeCreditValue() != null) {
            credit_bal.setText(String.valueOf(creditPageInfo.creditInfo.getCumulativeCreditValue().intValue()));
            try {
                Double availableCredits = creditPageInfo.creditInfo.getCumulativeCreditValue() + creditPageInfo.creditInfo.getMemberReferCreditValue();
                SessionManager.getInstance().setKeyValue(SessionManager.KEY_MAIN_CREDITS, availableCredits);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (creditPageInfo.creditInfo.getReferralCreditValue() != null && creditPageInfo.creditInfo.getReferralCreditValue() != 0.0) {
            refferalcreditstxt.setText("REFERRAL CREDITS :" + String.valueOf(creditPageInfo.creditInfo.getReferralCreditValue()));
            SessionManager.getInstance().setKeyValue(SessionManager.KEY_REFERRAL_CREDITS,
                    creditPageInfo.creditInfo.getReferralCreditValue());
        } else {
            refferalcreditstxt.setText("REFERRAL CREDITS : 0");
            SessionManager.getInstance().setKeyValue(SessionManager.KEY_REFERRAL_CREDITS,
                    creditPageInfo.creditInfo.getReferralCreditValue());
        }

        if (creditPageInfo.referalCodeInfo.memberCode != null && !creditPageInfo.referalCodeInfo.memberCode.isEmpty()) {
            reffercodetxt.setText(creditPageInfo.referalCodeInfo.memberCode);
        } else {
            reffercodetxt.setVisibility(View.GONE);
        }

        if (creditPageInfo.currencyType.description != null) {
            notetxt.setText(creditPageInfo.currencyType.description);
        }

        if (creditPageInfo.currencyType.currencyType != null) {

            amounttxt.setText(creditPageInfo.currencyType.currencySymbol + "0");

            countryCode = creditPageInfo.currencyType.countryCode;

            currencyValue = creditPageInfo.currencyType.currencyValue;

            currencySymbol = creditPageInfo.currencyType.currencySymbol;
        }

        entercreditEt.setText("");

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.credit_recharge_btn:
                rechargeScreenNavigate();
                break;

            case R.id.cancel_payment:
                getActivity().finish();
                break;
            case R.id.imageViewBack:
                getActivity().finish();
                break;

            case R.id.sharelayout:
                    if (reffercodetxt.getText().toString() != null && !reffercodetxt.getText().toString().isEmpty()) {
                        Common.shareSocialNetwork(getActivity(), reffercodetxt.getText().toString(), "APP_PROMOTION");
                    } else {
                        Toast.makeText(getActivity(), "Please Generate Code", Toast.LENGTH_SHORT).show();
                    }

                break;

            case R.id.copyLayout:
                if (reffercodetxt.getText().toString() != null && !reffercodetxt.getText().toString().isEmpty()) {
                    String text = reffercodetxt.getText().toString();
                    myClip = ClipData.newPlainText("text", text);
                    myClipboard.setPrimaryClip(myClip);
                    Toast.makeText(getActivity(), "Copied to Clipboard!", Toast.LENGTH_SHORT).show();
                }

                break;

        }
    }

    @Override
    public void setSkelltonView(RecyclerView recyclerView, boolean firstTime) {
        CommonRecycler.setSkelltonViewOther(getActivity(), recyclerView, false, firstTime, true);
    }

    @Override
    public void nodataList(RecyclerView recyclerView, String title, String subTitle, int imageResource) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new EmptyDataNewAdapter(getActivity(), title, subTitle, imageResource));
    }

    @Override
    public boolean checkInterConnection(RecyclerView recyclerView) {
        if (Common.checkInternetConnection(getActivity())) {
            return true;
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            CommonRecycler.setNoInternetOrServerDown(getActivity(), recyclerView, false);
            return false;
        }
    }

    @Override
    public void showSnackBar(String snackBarText, int type) {

    }

    @Override
    public void fetchDataFromServer(boolean firstTime) {
    }

    public void getPackagesData(String countryCode) {
        setSkelltonView(credits_recycler, true);
        if (checkInterConnection(credits_recycler)) {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<ApiResponseModel> call = apiInterface.getPackages(Constants.CreditConstants.CREDITS_PACKAGE_COLLECTION
                    + Common.isLoginId() + "/" + countryCode);
            Common.getInstance().callAPI(new ApiRequestModel().setModel(getActivity(), call,
                    Constants.CreditConstants.CREDITS_PACKAGE_COLLECTION,
                    false, iApiListener, false));
        }
    }


}
