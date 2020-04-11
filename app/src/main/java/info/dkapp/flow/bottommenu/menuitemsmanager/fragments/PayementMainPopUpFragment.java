package info.dkapp.flow.bottommenu.menuitemsmanager.fragments;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import info.dkapp.flow.bottommenu.interfaces.fragments.IFragment;

import info.dkapp.flow.R;
import info.dkapp.flow.userflow.Util.Common;

public class PayementMainPopUpFragment extends Fragment implements IFragment,View.OnClickListener {

    Button saveBtn;



    public static PayementMainPopUpFragment newInstance(String param1, String param2) {
        PayementMainPopUpFragment fragment = new PayementMainPopUpFragment();
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.paymentgateway_maintainance_popup, null);

        initializeView(root);
        initializeActions();

    return root;
    }


    private void initializeActions() {
        saveBtn.setOnClickListener(this);
    }

    private void initializeView(View root) {
        saveBtn = (Button) root.findViewById(R.id.saveBtn);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.saveBtn) {
            accessToNavigatingQuiz();
        }

    }

    private void accessToNavigatingQuiz() {
//        Intent i = new Intent(activity(), BottomMenuActivity.class);
//        startActivity(i);
        Common.getInstance().navigateToFeedOrBottomWithContext(getContext());
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
}
