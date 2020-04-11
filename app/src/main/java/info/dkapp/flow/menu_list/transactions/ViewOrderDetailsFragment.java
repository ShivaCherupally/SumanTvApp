package info.dkapp.flow.menu_list.transactions;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import info.dkapp.flow.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewOrderDetailsFragment extends Fragment {

    @BindView(R.id.tvOrderDate)
    TextView tvOrderDate;

    @BindView(R.id.tvOrderNumber)
    TextView tvOrderNumber;

    @BindView(R.id.tvOrderTotal)
    TextView tvOrderTotal;

    @BindView(R.id.tvTransactionDate)
    TextView tvTransactionDate;

    @BindView(R.id.tvTransactionTime)
    TextView tvTransactionTime;

    @BindView(R.id.tvCallType)
    TextView tvCallType;

    @BindView(R.id.tvCallCharges)
    TextView tvCallCharges;

    @BindView(R.id.tvOrdersubTotal)
    TextView tvOrdersubTotal;

    public ViewOrderDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_view_order_details, container, false);
        ButterKnife.bind(this, root);

        return  root;
    }

}
