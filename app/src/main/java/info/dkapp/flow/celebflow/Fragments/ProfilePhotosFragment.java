package info.dkapp.flow.celebflow.Fragments;


import android.app.Activity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.dkapp.flow.bottommenu.interfaces.fragments.IFragment;

import info.dkapp.flow.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilePhotosFragment extends Fragment implements IFragment{


    public ProfilePhotosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_profile_photos, container, false);

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
}
