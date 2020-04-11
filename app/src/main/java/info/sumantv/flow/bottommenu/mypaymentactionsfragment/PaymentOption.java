package info.sumantv.flow.bottommenu.mypaymentactionsfragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.braintreepayments.api.BraintreeFragment;
import com.braintreepayments.api.interfaces.BraintreeCancelListener;
import com.braintreepayments.api.interfaces.BraintreeErrorListener;
import com.braintreepayments.api.interfaces.PaymentMethodNonceCreatedListener;
import com.braintreepayments.api.models.PayPalRequest;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.sumantv.flow.bottommenu.activity.HelperActivity;
import info.sumantv.flow.bottommenu.interfaces.fragments.IFragment;
import info.sumantv.flow.bottommenu.mypaymentactionsfragment.CreditsModelDatas.CreateTransactionData;
import info.sumantv.flow.bottommenu.mypaymentactionsfragment.CreditsModelDatas.OrderInfoData;
import info.sumantv.flow.bottommenu.mypaymentactionsfragment.CreditsModelDatas.PackageSelectionData;
import info.sumantv.flow.bottommenu.mypaymentactionsfragment.CreditsModelDatas.PayableAmountData;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.retrofitcall.ApiClient;
import info.sumantv.flow.retrofitcall.ApiInterface;
import info.sumantv.flow.retrofitcall.ApiRequestModel;
import info.sumantv.flow.retrofitcall.ApiRequestModelJSON;
import info.sumantv.flow.retrofitcall.ApiResponseModel;
import info.sumantv.flow.retrofitcall.EnumConstants;
import info.sumantv.flow.retrofitcall.IApiListener;
import info.sumantv.flow.retrofitcall.SessionManager;
import info.sumantv.flow.userflow.Util.Common;
import info.sumantv.flow.utils.Utility;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentOption extends Fragment implements IFragment, PaytmPaymentTransactionCallback,
        IApiListener, View.OnClickListener, PaymentMethodNonceCreatedListener, BraintreeCancelListener, BraintreeErrorListener {

    @BindView(R.id.creditsamount)
    TextView creditsamount;

    @BindView(R.id.gstamount)
    TextView gstamount;

    @BindView(R.id.totalamount)
    TextView totalamount;

    @BindView(R.id.paynowbtn)
    Button paynowbtn;

    @BindView(R.id.instamojoSelection)
    LinearLayout instamojoSelection;

    @BindView(R.id.LLMainLayout)
    public LinearLayout LLMainLayout;

    @BindView(R.id.pleasewaitLayout)
    public LinearLayout pleasewaitLayout;


    @BindView(R.id.paytmSelection)
    LinearLayout paytmSelection;

    @BindView(R.id.paytmCheck)
    ImageView paytmCheck;

    @BindView(R.id.instamojoCheck)
    ImageView instamojoCheck;

    @BindView(R.id.gstpercentageLabel)
    TextView gstpercentageLabel;

    @BindView(R.id.payPalSelection)
    LinearLayout payPalSelection;

    @BindView(R.id.paypalCheck)
    ImageView paypalCheck;

    @BindView(R.id.parentlayout)
    RelativeLayout parentlayout;

    @BindView(R.id.walletlabel)
    TextView walletlabel;

    @BindView(R.id.creditCardLabel)
    TextView creditCardLabel;

    @BindView(R.id.backicon)
    ImageView backicon;

    BraintreeFragment mBraintreeFragment;
    PayPalRequest payPalRequest;

    @BindView(R.id.prefererLabel)
    TextView prefererLabel;

    @BindView(R.id.instamojoWebLayout)
    RelativeLayout instamojoWebLayout;
    //webView

    @BindView(R.id.webViewInstamojo)
    WebView webViewInstamojo;


    @BindView(R.id.progressinstamojo)
    GifImageView progressinstamojo;


    PayableAmountData payableAmountInnerData;


    String paymentGateway = null;

    PackageSelectionData packageSelectionDataObj;

    PayPalConfiguration config = new PayPalConfiguration();

    private static final String TAG = "paypaldata";

    String createTransactionId = "";
    String clientTokenGlobal = "";
    private static PaymentOption instance = null;

    private static final int REQUEST_CODE_PAYPAL_PAYMENT = 1;
    boolean seletedPayment = true;


    public static PaymentOption getInstance() {
        return instance;
    }

    public PaymentOption() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
    }

    public static PaymentOption newInstance(PackageSelectionData packageSelectionDataObj) {
        PaymentOption fragment = new PaymentOption();
        Bundle args = new Bundle();
        args.putParcelable("packageSelectionDataObj", packageSelectionDataObj);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_credit_recharge_payment, container, false);

        ButterKnife.bind(this, root);

        if (getArguments() != null) {
            packageSelectionDataObj = getArguments().getParcelable("packageSelectionDataObj");
            Log.e("packageSelectionDataObj", packageSelectionDataObj.toString());

            calculatetheCreditWithTax(packageSelectionDataObj);
        }

        paynowbtn.setOnClickListener(this::onClick);

        paytmSelection.setOnClickListener(this::onClick);
        instamojoSelection.setOnClickListener(this::onClick);
        payPalSelection.setOnClickListener(this::onClick);
        backicon.setOnClickListener(this::onClick);


        return root;
    }

    private void sendingDatatoServer() {
        CreateTransactionData createTransactionData = null;
        if (payableAmountInnerData != null) {
            if (SessionManager.userLogin.userName != null && !SessionManager.userLogin.userName.isEmpty()) {
                createTransactionData = new CreateTransactionData(Common.isLoginId(), payableAmountInnerData.packageRefId,
                        Common.isLoginId(), String.valueOf(payableAmountInnerData.creditValue), payableAmountInnerData.totalAmount,
                        "Credit Recharge", SessionManager.userLogin.userName, paymentGateway,
                        payableAmountInnerData.creditAmount, payableAmountInnerData.gstAmount);
            } else {
                createTransactionData = new CreateTransactionData(Common.isLoginId(), payableAmountInnerData.packageRefId,
                        Common.isLoginId(), String.valueOf(payableAmountInnerData.creditValue), payableAmountInnerData.totalAmount,
                        "Credit Recharge", "", paymentGateway, payableAmountInnerData.creditAmount, payableAmountInnerData.gstAmount);
            }
        }
        IApiListener iApiListener = this;
        Call<ApiResponseModel> call = null;
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        if (!Utility.isNetworkAvailable(getActivity())) {
            Toast.makeText(getActivity(), Constants.PLEASE_CHECK_INTERNET, Toast.LENGTH_LONG).show();
            return;
        }
        call = apiInterface.createTransactionData(createTransactionData);
        Common.getInstance().callAPI(new ApiRequestModel().setModel(getActivity(),
                call, Constants.CreditConstants.CREATE_TRANSACTION_URL, true,
                iApiListener, true));
    }

    private void generateCheckSum(final String createTransaction) {

        SessionManager.getInstance().setKeyValue(SessionManager.KEY_REF_PAYMENT_TRANSACTION_ID, createTransaction);

        IApiListener iApiListener = this;
        JSONObject checksumObject = new JSONObject();
        try {
            String callbackurl = ApiClient.PAYTM_CALLBACK_URL + createTransaction;

            checksumObject.put("CALLBACK_URL", callbackurl);
            checksumObject.put("CHANNEL_ID", ApiClient.WAP);
            checksumObject.put("CUST_ID", Common.isLoginId());
            checksumObject.put("INDUSTRY_TYPE_ID", ApiClient.INDUSTRY_TYPE);
            checksumObject.put("MID", ApiClient.MERCHANT_ID);
            checksumObject.put("ORDER_ID", createTransaction);
            String totalAmount = String.format("%.2f", payableAmountInnerData.totalAmount);
            checksumObject.put("TXN_AMOUNT", totalAmount);
            checksumObject.put("WEBSITE", ApiClient.WEBSITE);
//            checksumObject.put("EMAIL", "accounts@celebkonect.com");
//            checksumObject.put("MOBILE_NO", "");

            Log.e("paytmDataLoad", checksumObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), checksumObject.toString());

        Call<ApiResponseModel> call = apiInterface.POST(Constants.CreditConstants.GET_CHECKSUM_VALUE, requestBody);

        Common.getInstance().callAPI(new ApiRequestModel().setModel(getActivity(), call, Constants.CreditConstants.GET_CHECKSUM_VALUE,
                true, iApiListener, true));
    }

    private void paytmPaymentGatewayRequest(String getCheckSum, String orderId) {

        HashMap<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("CALLBACK_URL", ApiClient.PAYTM_CALLBACK_URL + orderId);
        paramMap.put("CHANNEL_ID", ApiClient.WAP);
        paramMap.put("CUST_ID", Common.isLoginId());
        paramMap.put("INDUSTRY_TYPE_ID", ApiClient.INDUSTRY_TYPE);
        paramMap.put("MID", ApiClient.MERCHANT_ID);
        paramMap.put("ORDER_ID", orderId);
        String totalAmount = String.format("%.2f", payableAmountInnerData.totalAmount);
        paramMap.put("TXN_AMOUNT", totalAmount);
        paramMap.put("WEBSITE", ApiClient.WEBSITE);
//        paramMap.put("EMAIL", "accounts@celebkonect.com");
//        paramMap.put("MOBILE_NO", "");
        paramMap.put("CHECKSUMHASH", getCheckSum);

        try {

            Log.e("paytmParmas", paramMap.toString() + "");
            PaytmOrder Order = new PaytmOrder(paramMap);

            if (ApiClient.ENVIRONMENT.equals("test")) {
                PaytmPGService.getStagingService().initialize(Order, null);
                PaytmPGService.getStagingService().startPaymentTransaction(activity(), true,
                        true, this);
            } else {
                PaytmPGService.getProductionService().initialize(Order, null);
                PaytmPGService.getProductionService().startPaymentTransaction(activity(), true,
                        true, this);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void instamojoPaymentGatewayRequest(final JSONObject transactionObj) {
        JSONObject instamojoReqParams = new JSONObject();
        try {
            String totalAmount = String.format("%.2f", transactionObj.optDouble("equivalentAmount"));
            instamojoReqParams.put("amount", totalAmount);
            instamojoReqParams.put("purpose", transactionObj.optString("paymentType"));
//            instamojoReqParams.put("buyer_name", SessionManager.userLogin.userName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SessionManager.getInstance().setKeyValue(SessionManager.KEY_REF_PAYMENT_TRANSACTION_ID, transactionObj.optString("_id"));
        IApiListener iApiListener = new IApiListener() {
            @Override
            public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                try {
                    JSONObject response = new JSONObject(new Gson().toJson(apiResponseModel.data));
                    JSONObject instaMojoResponce = new JSONObject(response.toString());
                    Log.d("responseInstamojo", instaMojoResponce.toString() + "");
                    if (instaMojoResponce.getBoolean("success")) {
                        navigateToPaymentGateWayScreen(instaMojoResponce);
                    } else {
                        Toast.makeText(activity(), Constants.SOMETHING_WENT_WRONG_SERVER, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
                Toast.makeText(activity(), "Authentication failed other", Toast.LENGTH_SHORT).show();
            }
        };
        //
        ApiInterface apiInterface = ApiClient.getClientJSON().create(ApiInterface.class);
        Call<JsonElement> call = apiInterface.POST_JSON(ApiClient.INSTAMOJO, RequestBody.create(MediaType.parse("application/json; charset=utf-8"), instamojoReqParams.toString()));
        Common.getInstance().callAPIJSON(new ApiRequestModelJSON().setModel(activity(), call, ApiClient.INSTAMOJO, false, iApiListener, false));
    }

    private void navigateToPaymentGateWayScreen(JSONObject instaMojoResponce) {
        try {
            parentlayout.setVisibility(View.INVISIBLE);
            instamojoWebLayout.setVisibility(View.VISIBLE);

            setInstamojoUrlInWebview(instaMojoResponce.getJSONObject("payment_request").getString("longurl"),
                    instaMojoResponce.getJSONObject("payment_request").getString("id"));
        } catch (Exception e) {

        }

    }

    private void setInstamojoUrlInWebview(String instomjowebUrl, String instamojoId) {

        webViewInstamojo.getSettings().setJavaScriptEnabled(true);

        // enable javascript
        webViewInstamojo.setWebChromeClient(new WebChromeClient() {
            public boolean onConsoleMessage(ConsoleMessage cmsg) {
                cmsg.message();
                return true;

            }
        });
        webViewInstamojo.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.e("Load payment Gateway", description);
                Toast.makeText(getActivity(), "Problem loading. Make sure internet connection is available.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressinstamojo.setVisibility(View.GONE);
                Log.e("InstomojoReturnurl", url + "");
                if (url.indexOf("https://www.instamojo.com/order/status") >= 0) {
                    CreatePaymentTransaction(instamojoId);
                } else if (url.indexOf("https://test.instamojo.com/order/status") >= 0) {
                    CreatePaymentTransaction(instamojoId);
                }
            }
        });

        webViewInstamojo.loadUrl(instomjowebUrl);
    }

    @Override
    public void someUIErrorOccurred(String inErrorMessage) {
        Log.d("someUIErrorOccurred", "UI Error Occur.");
        Toast.makeText(activity(), " UI Error Occur. ", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTransactionResponse(Bundle inResponse) {
        Log.d("onTransactionResponse", "Payment Transaction : " + inResponse);
        if (inResponse.getString("STATUS") != null && !inResponse.getString("STATUS").isEmpty()) {
            try {
                if (inResponse.getString("STATUS").equals("TXN_SUCCESS")) {

                    postTransactionData(inResponse.getString("TXNID"),
                            inResponse.getString("STATUS"),
                            "Success");

                } else {
                    postTransactionData(inResponse.getString("TXNID"), inResponse.getString("STATUS"), "Failed");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void networkNotAvailable() {
        Log.d("networkNotAvailable", "UI Error Occur.");
        Toast.makeText(activity(), " UI Error Occur. ", Toast.LENGTH_LONG).show();

    }

    @SuppressLint("LongLogTag")
    @Override
    public void clientAuthenticationFailed(String inErrorMessage) {
        Log.d("clientAuthenticationFailed", "UI Error Occur.");
        Toast.makeText(activity(), Constants.SOMETHING_WENT_WRONG + inErrorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
        Log.d("onErrorLoadingWebPage", "UI Error Occur.");
        Toast.makeText(activity(), Constants.SOMETHING_WENT_WRONG + inErrorMessage, Toast.LENGTH_LONG).show();
    }

    @Override

    public void onBackPressedCancelTransaction() {
        Log.d("onErrorLoadingWebPage", "onBackPressedCancelTransaction");
    }

    @Override

    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
        Log.d("onTransactionCancel", "Payment Transaction Failed " + inErrorMessage);
        Toast.makeText(activity(), "Payment Transaction Failed ", Toast.LENGTH_LONG).show();
    }

    @Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {

        if (condition.equals(Constants.CreditConstants.CALCULATE_CREDITS_WITH_TAX_URL)) {
            Type type = new TypeToken<PayableAmountData>() {
            }.getType();
            PayableAmountData payableAmountData = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
            if (payableAmountData != null) {
                payableAmountInnerData = payableAmountData.payableAmount;
                if (payableAmountInnerData != null) {

                    creditsDataSet(payableAmountInnerData);

                    //Note : For Braintree Integration needed this dont remove
//                    brainetreeSdkRedirection();

                }
            }
        } else if (condition.equals(Constants.CreditConstants.CREATE_TRANSACTION_URL)) {
            try {
                JSONObject transactionObj = new JSONObject(new Gson().toJson(apiResponseModel.data));
                Log.e("PaymentGatewayData", transactionObj.toString());
                redirectionForPaymentSdk(transactionObj);
            } catch (Exception e) {
                showSnackBar(Constants.SOMETHING_WENT_WRONG, 2);
                e.printStackTrace();
            }
        } else if (condition.equals(Constants.CreditConstants.GET_CHECKSUM_VALUE)) {
            try {
                JSONObject jsonObject = new JSONObject(new Gson().toJson(apiResponseModel.data));
                Log.e("checksumRes", jsonObject.toString());

                paytmPaymentGatewayRequest(jsonObject.getString("CHECKSUMHASH"),
                        jsonObject.getString("ORDER_ID"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (condition.equals(Constants.CreditConstants.GENERATE_BRAINETREE_TOKEN_URL)) {
            try {
                JSONObject jsonObject = new JSONObject(new Gson().toJson(apiResponseModel.data));
                clientTokenGlobal = jsonObject.getString("clientToken");
                mBraintreeFragment = BraintreeFragment.newInstance(PaymentOption.this, clientTokenGlobal);
                mBraintreeFragment.addListener(this);
                Log.e("clientTokenGlobal", clientTokenGlobal + "");
            } catch (Exception e) {
            }
        } else if (condition.equals(Constants.CreditConstants.BRAINETREE_CHECKOUT_URL)) {
            Type type = new TypeToken<OrderInfoData>() {
            }.getType();
            OrderInfoData orderInfoData = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
            redirectingOrderSuccessPage(orderInfoData);
        } else if (condition.equals(Constants.CreditConstants.CHECKOUT_TRANSACTION_STATUS)) {

            Type type = new TypeToken<OrderInfoData>() {
            }.getType();
            OrderInfoData orderInfoData = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
            if (orderInfoData.paymentStatus != null && !orderInfoData.paymentStatus.isEmpty()) {

                if (orderInfoData.paymentStatus.equals("Success")) {
                    redirectingOrderSuccessPage(orderInfoData);
                } else {
                    redirectFailedPage();
                }
            } else {
                redirectFailedPage();
            }
        }
    }

    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
        if (condition.equals(Constants.CreditConstants.CALCULATE_CREDITS_WITH_TAX_URL)) {
            showSnackBar(Constants.SOMETHING_WENT_WRONG, 2);
        } else if (condition.equals(ApiClient.INSERT_PAYMENT_TRANSACTION)) {
            showSnackBar(Constants.SOMETHING_WENT_WRONG, 2);
        } else if (condition.equals(Constants.CreditConstants.GET_CHECKSUM_VALUE)) {
            showSnackBar(Constants.SOMETHING_WENT_WRONG, 2);
        } else if (condition.equals(Constants.CreditConstants.GENERATE_BRAINETREE_TOKEN_URL)) {
            showSnackBar(Constants.SOMETHING_WENT_WRONG, 2);
        } else if (condition.equals(Constants.CreditConstants.BRAINETREE_CHECKOUT_URL)) {
            Toast.makeText(getActivity(), "Payment Failed", Toast.LENGTH_SHORT).show();
            redirectFailedPage();
        } else if (condition.equals(Constants.CreditConstants.CHECKOUT_TRANSACTION_STATUS)) {
            Toast.makeText(getActivity(), "Payment Failed", Toast.LENGTH_SHORT).show();
            redirectFailedPage();
        }
    }

    private void redirectingOrderSuccessPage(OrderInfoData orderInfoData) {
        try {

            Intent intent = new Intent(activity(), HelperActivity.class);

            intent.putExtra(Constants.FRAGMENT_TITLE, "Success");

            intent.putExtra(Constants.FRAGMENT_KEY, 8039);

            intent.putExtra("orderSuccessData", orderInfoData);

            startActivity(intent);

            activity().finish();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void redirectionForPaymentSdk(JSONObject transactionObj) {

        switch (paymentGateway) {
            case Constants.CreditConstants.PAYTM_GATEWAY:
                createTransactionId = transactionObj.optString("_id");
                generateCheckSum(transactionObj.optString("_id"));

                break;
            case Constants.CreditConstants.INSTAMOJO_GATEWAY:
                createTransactionId = transactionObj.optString("_id");
                instamojoPaymentGatewayRequest(transactionObj);
                break;
            case Constants.CreditConstants.PAYPAL_GATEWAY:
                createTransactionId = transactionObj.optString("_id");
                try {
                    viewsUpdated(true);
                    //Note : For Braintree Integration needed this dont remove
//                    PayPal.requestOneTimePayment(mBraintreeFragment, payPalRequest);

                    paypalGatewayRequest(transactionObj);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void brainetreeSdkRedirection() {
        IApiListener iApiListener = this;
        Call<ApiResponseModel> call = null;
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        call = apiInterface.getBrineTreeToken(Constants.CreditConstants.GENERATE_BRAINETREE_TOKEN_URL);
        Common.getInstance().callAPI(new ApiRequestModel().setModel(getActivity(), call, Constants.CreditConstants.GENERATE_BRAINETREE_TOKEN_URL,
                false, iApiListener, false));
    }


    //Old Paypal Version
    private void paypalServerStart() {
        if (ApiClient.ENVIRONMENT.equals("test")) {
            config = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                    .clientId(Constants.CreditConstants.PAYPAL_TEST_CLIENT_ID);
        } else {
            config = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_PRODUCTION)
                    .clientId(Constants.CreditConstants.PAYPAL_LIVE_CLIENT_ID);
        }
        Intent intent = new Intent(getActivity(), PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        getActivity().startService(intent);
    }

    private void paypalGatewayRequest(JSONObject transactionObj) {
        PayPalPayment thingToBuy = getThingToBuy(PayPalPayment.PAYMENT_INTENT_SALE, transactionObj);
        Intent intent = new Intent(getActivity(), PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
        startActivityForResult(intent, REQUEST_CODE_PAYPAL_PAYMENT);
    }

    private PayPalPayment getThingToBuy(String paymentIntent, JSONObject transactionObj) {
        String totalAmount = String.format("%.2f", transactionObj.optDouble("equivalentAmount"));
        String countryCode = "INR";
        Log.e("currencyType", payableAmountInnerData.currencyType + totalAmount);

        if (payableAmountInnerData.currencyType.equals("INR")) {
            countryCode = "INR";
        } else if (payableAmountInnerData.currencyType.equals("AUD")) {
            countryCode = "AUD";
        } else if (payableAmountInnerData.currencyType.equals("USD")) {
            countryCode = "USD";
        }
        return new PayPalPayment(new BigDecimal(totalAmount), countryCode, "Total Credits amount", paymentIntent);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("requestCodeCheck", requestCode + "");
        if (requestCode == REQUEST_CODE_PAYPAL_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        Log.i(TAG, confirm.toJSONObject().toString(4));
                        Log.i(TAG, confirm.getPayment().toJSONObject().toString(4));

                        //ORDER SUCCESS
                        postTransactionData(confirm.toJSONObject().getJSONObject("response").getString("id"),
                                confirm.toJSONObject().getJSONObject("response").getString("state"), "Success");

                    } catch (JSONException e) {
                        Log.e(TAG, "an extremely unlikely failure occurred: ", e);
                        postTransactionData("", "", "Failed");
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                postTransactionData("",
                        "", "Failed");
                Log.i(TAG, "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i(TAG, "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
                postTransactionData("",
                        "", "Failed");
            }
        }
    }


    private void creditsDataSet(PayableAmountData payableAmountInnerData) {
        String creditsAmount = payableAmountInnerData.currencyType + "(" + payableAmountInnerData.currencySymbol + ") " + String.valueOf(payableAmountInnerData.creditAmount);
        if (!creditsAmount.isEmpty()) {
            creditsamount.setText(creditsAmount);
        } else {
            creditsamount.setText("");
        }

        String gstAmount = payableAmountInnerData.currencyType + "(" + payableAmountInnerData.currencySymbol + ") " + String.valueOf(payableAmountInnerData.gstAmount);
        if (!gstAmount.isEmpty()) {
            gstamount.setText(gstAmount);
        } else {
            gstamount.setText("");
        }

        String totalAmount = payableAmountInnerData.currencyType + "(" + payableAmountInnerData.currencySymbol + ") " + String.valueOf(payableAmountInnerData.totalAmount);
        if (!totalAmount.isEmpty()) {
            totalamount.setText(totalAmount);
        } else {
            totalamount.setText("");
        }

        if (payableAmountInnerData.gst != null) {
            gstpercentageLabel.setText("Tax " + "(" + payableAmountInnerData.gst.intValue() + "%)");
        }
        Log.e("packageRefId", payableAmountInnerData.packageRefId + "__CHA");


        if (payableAmountInnerData.countryCode != null && !payableAmountInnerData.countryCode.isEmpty()) {
            Log.d("CountryCode", payableAmountInnerData.countryCode + "");
            if (payableAmountInnerData.countryCode.equals("INR")) {
                walletlabel.setVisibility(View.VISIBLE);
                paytmSelection.setVisibility(View.VISIBLE);
                instamojoSelection.setVisibility(View.GONE);
                payPalSelection.setVisibility(View.GONE);
                prefererLabel.setVisibility(View.VISIBLE);
                creditCardLabel.setVisibility(View.GONE);
            } else {
                walletlabel.setVisibility(View.VISIBLE);
                paytmSelection.setVisibility(View.GONE);
                instamojoSelection.setVisibility(View.GONE);
                payPalSelection.setVisibility(View.VISIBLE);
                creditCardLabel.setVisibility(View.GONE);
                prefererLabel.setVisibility(View.VISIBLE);
            }

            paypalServerStart();

        }
    }


    private void actionContinue(boolean status) {
        paynowbtn.setClickable(status);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            paynowbtn.setBackgroundTintList(null);
            if (status) {
                paynowbtn.setTextColor(getResources().getColor(R.color.white));
                paynowbtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.skyblueNew)));
            } else {
                paynowbtn.setTextColor(getResources().getColor(R.color.gray));
                paynowbtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.grey)));
            }
        } else {
            if (status) {
                paynowbtn.setTextColor(getResources().getColor(R.color.white));
                paynowbtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.skyblueNew)));
            } else {
                paynowbtn.setTextColor(getResources().getColor(R.color.gray));
                paynowbtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.grey)));
            }
        }
    }

    private void calculatetheCreditWithTax(PackageSelectionData packageSelectionDataObj) {
        try {
            IApiListener iApiListener = this;
            Call<ApiResponseModel> call = null;
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

            if (!Utility.isNetworkAvailable(getActivity())) {
                Toast.makeText(getActivity(), Constants.PLEASE_CHECK_INTERNET, Toast.LENGTH_LONG).show();
                return;
            }
            if (packageSelectionDataObj != null) {
                call = apiInterface.checkCalculateCreditswithTax(packageSelectionDataObj);
                Common.getInstance().callAPI(new ApiRequestModel().setModel(getActivity(), call,
                        Constants.CreditConstants.CALCULATE_CREDITS_WITH_TAX_URL, true, iApiListener, true));
            }

        } catch (Exception e) {
            Log.e("Error", "error ");
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.paytmSelection:
                paymentGateway = Constants.CreditConstants.PAYTM_GATEWAY;
//                paytmCheck.setImageDrawable(activity().getResources().getDrawable(R.drawable.green_tick));
                instamojoCheck.setImageDrawable(activity().getResources().getDrawable(R.drawable.manager_settings_checkbox_unselected));
                paypalCheck.setImageDrawable(activity().getResources().getDrawable(R.drawable.manager_settings_checkbox_unselected));
//                actionContinue(true);

                if (seletedPayment) {
                    actionContinue(seletedPayment);
                    seletedPayment = false;
                    paytmCheck.setImageDrawable(activity().getResources().getDrawable(R.drawable.green_tick));
                } else {
                    actionContinue(seletedPayment);
                    seletedPayment = true;
                    paytmCheck.setImageDrawable(activity().getResources().getDrawable(R.drawable.manager_settings_checkbox_unselected));
                }

                break;
            case R.id.instamojoSelection:
                paymentGateway = Constants.CreditConstants.INSTAMOJO_GATEWAY;

                paytmCheck.setImageDrawable(activity().getResources().getDrawable(R.drawable.manager_settings_checkbox_unselected));
                instamojoCheck.setImageDrawable(activity().getResources().getDrawable(R.drawable.green_tick));
                paypalCheck.setImageDrawable(activity().getResources().getDrawable(R.drawable.manager_settings_checkbox_unselected));

                actionContinue(true);
                break;

            case R.id.payPalSelection:

                paymentGateway = Constants.CreditConstants.PAYPAL_GATEWAY;

                paytmCheck.setImageDrawable(activity().getResources().getDrawable(R.drawable.manager_settings_checkbox_unselected));
                instamojoCheck.setImageDrawable(activity().getResources().getDrawable(R.drawable.manager_settings_checkbox_unselected));


                if (seletedPayment) {
                    actionContinue(seletedPayment);
                    seletedPayment = false;
                    paypalCheck.setImageDrawable(activity().getResources().getDrawable(R.drawable.green_tick));
                } else {
                    actionContinue(seletedPayment);
                    seletedPayment = true;
                    paypalCheck.setImageDrawable(activity().getResources().getDrawable(R.drawable.manager_settings_checkbox_unselected));
                }


                //Note : For Braintree Integration needed this dont remove
                /*if (clientTokenGlobal != null && !clientTokenGlobal.isEmpty()) {
                    return;
                } else {
                    brainetreeSdkRedirection();
                }*/

                break;

            case R.id.paynowbtn:
                Log.e("countryCode", payableAmountInnerData.countryCode + "_CR");
                if (paymentGateway != null && !paymentGateway.isEmpty()) {
                    try {
                        if (paymentGateway.equals(Constants.CreditConstants.PAYPAL_GATEWAY)) {
                            payPalRequest = new PayPalRequest(String.valueOf(payableAmountInnerData.totalAmount))
                                    .currencyCode(payableAmountInnerData.countryCode).intent(PayPalRequest.INTENT_AUTHORIZE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    sendingDatatoServer();
                } else {
                    Toast.makeText(activity(), "Please select payment type", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.backicon:
                activity().finish();
                break;
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


    public void postNonceToServer(String nonce) {
        IApiListener iApiListener = this;
        Call<ApiResponseModel> call = null;
        JSONObject jsonData = new JSONObject();
        try {
            jsonData.put("amount", String.valueOf(payableAmountInnerData.totalAmount));
            jsonData.put("payment_method_nonce", nonce);
            jsonData.put("orderId", createTransactionId);
            jsonData.put("customerId", SessionManager.userLogin.userId);
            jsonData.put("countryCode", payableAmountInnerData.countryCode);
            Log.e("nonceData", nonce);
        } catch (Exception e) {
        }

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonData.toString());
        call = apiInterface.POST(Constants.CreditConstants.BRAINETREE_CHECKOUT_URL, requestBody);
        Common.getInstance().callAPI(new ApiRequestModel().setModel(getActivity(), call, Constants.CreditConstants.BRAINETREE_CHECKOUT_URL,
                false,
                iApiListener, false));
    }

    @Override
    public void onPaymentMethodNonceCreated(PaymentMethodNonce paymentMethodNonce) {
        try {
//            String nonce = paymentMethodNonce.getNonce();
//            Log.e("nonce", nonce + "");
            /*if (paymentMethodNonce instanceof PayPalAccountNonce) {
                PayPalAccountNonce payPalAccountNonce = (PayPalAccountNonce) paymentMethodNonce;
                Log.e("paypalEmail", payPalAccountNonce.getEmail() + "");
                // Access additional information
                String email = payPalAccountNonce.getEmail();
                String firstName = payPalAccountNonce.getFirstName();
                String lastName = payPalAccountNonce.getLastName();
                String phone = payPalAccountNonce.getPhone();
                // See PostalAddress.java for details
                PostalAddress billingAddress = payPalAccountNonce.getBillingAddress();
                PostalAddress shippingAddress = payPalAccountNonce.getShippingAddress();
//                payPalAccountNonce.get
            }*/
            if (paymentMethodNonce.getNonce() != null && !paymentMethodNonce.getNonce().isEmpty()) {
                postNonceToServer(paymentMethodNonce.getNonce());
            }
        } catch (Exception e) {

        }


    }

    @Override
    public void onCancel(int requestCode) {
        viewsUpdated(false);
        Toast.makeText(getActivity(), "Payment cancelled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(Exception error) {
        viewsUpdated(false);
        Toast.makeText(getActivity(), "Payment Failed From Paypal", Toast.LENGTH_SHORT).show();
    }

    private void viewsUpdated(boolean visibilityStatus) {
        if (visibilityStatus) {
            pleasewaitLayout.setVisibility(View.VISIBLE);
            parentlayout.setVisibility(View.INVISIBLE);
        } else {
            pleasewaitLayout.setVisibility(View.GONE);
            parentlayout.setVisibility(View.VISIBLE);
        }
    }

    private void redirectFailedPage() {
        Intent intent = new Intent(activity(), HelperActivity.class);
        intent.putExtra("transactionId", createTransactionId);
        intent.putExtra("currencyamount", payableAmountInnerData.currencySymbol + " " + String.valueOf(payableAmountInnerData.totalAmount));
        intent.putExtra(Constants.FRAGMENT_TITLE, "Payment Failed");
        intent.putExtra(Constants.FRAGMENT_KEY, 8036);
        startActivity(intent);
        activity().finish();
    }


    public void postTransactionData(String paymentId, String gatewayResponse, String paymentStatus) {
        IApiListener iApiListener = this;
        Call<ApiResponseModel> call = null;
        JSONObject jsonData = new JSONObject();
        try {
            jsonData.put("orderId", createTransactionId);
            jsonData.put("customerId", SessionManager.userLogin.userId);
            jsonData.put("countryCode", payableAmountInnerData.countryCode);

            jsonData.put("paymentId", paymentId);
            jsonData.put("paymentMode", "WALLET");
            jsonData.put("gatewayResponse", gatewayResponse);
            jsonData.put("paymentStatus", paymentStatus);

        } catch (Exception e) {
        }

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonData.toString());
        call = apiInterface.POST(Constants.CreditConstants.CHECKOUT_TRANSACTION_STATUS, requestBody);
        Common.getInstance().callAPI(new ApiRequestModel().setModel(getActivity(), call, Constants.CreditConstants.CHECKOUT_TRANSACTION_STATUS,
                false,
                iApiListener, false));
    }

    // don't do for token
    private void CreatePaymentTransaction(String instamojoId) {
        String url = ApiClient.INSTAMOJO_TRANSACTION + instamojoId;
        IApiListener iApiListener = new IApiListener() {
            @Override
            public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                try {
                    JSONObject response = new JSONObject(new Gson().toJson(apiResponseModel.data));
                    JSONObject jsonObject1 = response.getJSONObject("payment_request").getJSONArray("payments").getJSONObject(0);
                    String paymentid = response.getJSONObject("payment_request").getJSONArray("payments").getJSONObject(0).getString("payment_id");
                    String paymentresponce = jsonObject1.getString("status");
                    Log.e("paymentresponce", paymentresponce + "");
                    if (paymentresponce.equals("Credit")) {
                        postTransactionData(paymentid, paymentresponce, "Success");
                    } else {
                        Toast.makeText(activity(), "Payment Declined", Toast.LENGTH_SHORT).show();
                        postTransactionData(paymentid, paymentresponce, "Failed");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
                postTransactionData("", "", "Failed");
            }
        };
        //
        ApiInterface apiInterface = ApiClient.getClientJSON().create(ApiInterface.class);
        Call<JsonElement> call = apiInterface.GET_JSON(url);
        Common.getInstance().callAPIJSON(new ApiRequestModelJSON().setModel(activity(), call, url, false, iApiListener, false));
    }


}
