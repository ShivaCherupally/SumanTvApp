package info.dkapp.flow.bottommenu.menuitemsmanager.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import info.dkapp.flow.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MediaEditProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MediaEditProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MediaEditProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match


    public MediaEditProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MediaEditProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MediaEditProfileFragment newInstance(String param1, String param2) {
        MediaEditProfileFragment fragment = new MediaEditProfileFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_media_edit_profile, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event

}
