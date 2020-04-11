package info.dkapp.flow.menu_list.transactions;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import info.dkapp.flow.R;
import info.dkapp.flow.celebflow.constants.Constants;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    @BindView(R.id.tvError)
    TextView tvError;


    @BindView(R.id.tvNote)
    TextView tvNote;

    @BindView(R.id.imgError)
    ImageView imgError;


    public CartFragment() {
    }

    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.empty_data_item_new, container, false);
        ButterKnife.bind(this, view);
        setUp();
        return view;
    }

    private void NoPremission() {
        tvError.setText(R.string.app_name);
        imgError.setImageResource(R.drawable.ic_oops_cart);
        tvNote.setText(R.string.no_permission_access);
    }

    private void setUp() {

        tvError.setText(Constants.OOPS);
        imgError.setImageResource(R.drawable.ic_oops_cart);
        tvNote.setText(Constants.THERE_NO_CART);
    }


}
