package info.dkapp.flow.bottommenu.mypaymentactionsfragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import info.dkapp.flow.bottommenu.interfaces.fragments.IFragment;
import info.dkapp.flow.bottommenu.models.Credits;
import info.dkapp.flow.bottommenu.mypaymentactionsfragment.CreditsModelDatas.OrderInfo;
import info.dkapp.flow.bottommenu.mypaymentactionsfragment.CreditsModelDatas.OrderInfoData;

import info.dkapp.flow.R;
import info.dkapp.flow.userflow.Util.Common;
import info.dkapp.flow.userflow.Util.DateUtil;

public class OrderSuccess extends Fragment implements IFragment, View.OnClickListener {

    @BindView(R.id.orderId)
    TextView orderId;

    @BindView(R.id.transactionId)
    TextView transactionId;

    @BindView(R.id.dateandtime)
    TextView dateandtime;

    @BindView(R.id.amountcountry)
    TextView amountcountry;

    @BindView(R.id.credits)
    TextView credits;

    @BindView(R.id.itemprice)
    TextView itemprice;

    @BindView(R.id.totalbeforetax)
    TextView totalbeforetax;

    @BindView(R.id.paymentmode)
    TextView paymentmode;

    @BindView(R.id.total)
    TextView total;


    @BindView(R.id.taxamount)
    TextView taxamount;


    @BindView(R.id.ordertotal)
    TextView ordertotal;

    @BindView(R.id.taxlabel)
    TextView taxlabel;


    @BindView(R.id.donebtn)
    Button donebtn;

    OrderInfoData orderInfoData;

    public OrderSuccess() {
        // Required empty public constructor
    }


    public static OrderSuccess newInstance(OrderInfoData orderSuccessData) {
        OrderSuccess fragment = new OrderSuccess();
        Bundle args = new Bundle();
        args.putParcelable("orderInfo", orderSuccessData);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.order_success_page, null);

        ButterKnife.bind(this, root);

        try {
            if (getArguments() != null) {

                orderInfoData = getArguments().getParcelable("orderInfo");

                setdatatoViews(orderInfoData.orderInfoInner);

                updateCredits(orderInfoData.creditInfo);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        donebtn.setOnClickListener(this::onClick);

        return root;
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



    private void updateCredits(Credits creditInfo) {
        Double credits = creditInfo.cumulativeCreditValue;
        Common.getInstance().setAvailableBalance(credits);

        Double refferalCredits = creditInfo.referralCreditValue;
        Common.getInstance().setRefferalCredits(refferalCredits);
    }

    private void setdatatoViews(OrderInfo orderInfoInner) {

        if (orderInfoInner.orderId != null && !orderInfoInner.orderId.isEmpty()) {
            orderId.setText(": " + orderInfoInner.orderId + "");
        }

        if (orderInfoInner.transactionId != null && !orderInfoInner.transactionId.isEmpty()) {
            transactionId.setText(": " + orderInfoInner.transactionId + "");
        }

        if (orderInfoInner.createdAt != null && !orderInfoInner.createdAt.isEmpty()) {
            dateandtime.setText(": " + DateUtil.getFeedDateAndTme(orderInfoInner.createdAt) + "");
        }

        credits.setText(String.valueOf(orderInfoInner.credits) + " Credit(s)");

        if (orderInfoInner.currencySymbol != null) {
            amountcountry.setText("(" + orderInfoInner.currencyType + ")" + String.valueOf(orderInfoInner.actualAmount) + "");
        }

        if (orderInfoInner.paymentMode != null) {
            paymentmode.setText(orderInfoInner.paymentMode);
        }

        itemprice.setText(": " + "(" + orderInfoInner.currencyType + ") " + String.valueOf(orderInfoInner.totalAmount) + "");

        totalbeforetax.setText(": " + "(" + orderInfoInner.currencyType + ") " + String.valueOf(orderInfoInner.actualAmount) + "");

        taxamount.setText(": " + "(" + orderInfoInner.currencyType + ") " + String.valueOf(orderInfoInner.gstAmount) + "");

        total.setText(": " + "(" + orderInfoInner.currencyType + ") " + String.valueOf(orderInfoInner.totalAmount) + "");

        ordertotal.setText(": " + "(" + orderInfoInner.currencyType + ") " + String.valueOf(orderInfoInner.totalAmount) + "");

        taxlabel.setText("Tax (" + String.valueOf(orderInfoInner.gst) + "%)" + "");

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.donebtn) {
            Common.getInstance().navigateToFeedOrBottomWithContext(getContext());
        }

    }
}
