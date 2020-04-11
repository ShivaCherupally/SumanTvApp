package info.sumantv.flow.bottommenu.mypaymentactionsfragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.sumantv.flow.bottommenu.activity.HelperActivity;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.userflow.Util.Common;

public class OrderFailed extends Fragment implements View.OnClickListener {


    @BindView(R.id.transactionId)
    TextView transactionId;

    @BindView(R.id.amounttxt)
    TextView amounttxt;

    @BindView(R.id.tryagainBtn)
    Button tryagainBtn;

    @BindView(R.id.backhomeBtn)
    Button backhomeBtn;

    public static OrderFailed newInstance() {
        OrderFailed fragment = new OrderFailed();
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.failed_payment, null);

        ButterKnife.bind(this, root);

        Bundle bundle = getArguments();
        try {
            if (bundle.getString("currencyamount") != null && !bundle.getString("currencyamount").isEmpty()) {
                amounttxt.setText(bundle.getString("currencyamount"));
            }
            if (bundle.getString("transactionId") != null && !bundle.getString("transactionId").isEmpty()) {
                transactionId.setText(bundle.getString("transactionId"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        tryagainBtn.setOnClickListener(this::onClick);
        backhomeBtn.setOnClickListener(this::onClick);

        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tryagainBtn:
                backtoCreditpage();
                break;
            case R.id.backhomeBtn:
                Common.getInstance().navigateHome(getActivity());
                break;
        }

    }

    private void backtoCreditpage() {
        Intent intent = new Intent(getActivity(), HelperActivity.class);
        intent.putExtra(Constants.FRAGMENT_TITLE, "Credits");
        intent.putExtra(Constants.FRAGMENT_KEY, 8034);
        startActivity(intent);
        getActivity().finish();
    }
}
