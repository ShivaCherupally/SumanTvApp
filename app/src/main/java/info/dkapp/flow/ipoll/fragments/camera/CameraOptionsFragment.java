package info.dkapp.flow.ipoll.fragments.camera;


import android.app.Activity;
import android.os.Bundle;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.dkapp.flow.bottommenu.activity.HelperActivity;
import info.dkapp.flow.bottommenu.interfaces.fragments.IFragment;
import info.dkapp.flow.bottommenu.models.IPollOption;

import info.dkapp.flow.R;
import info.dkapp.flow.ipoll.adapters.camera.CameraOptionsAdapter;
import info.dkapp.flow.ipoll.interfaces.other.IOptionsAdapter;
import info.dkapp.flow.utils.Utility;
import info.dkapp.flow.imagepicker.ui.imagepicker.ImagePickerActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CameraOptionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CameraOptionsFragment extends Fragment implements IFragment, IOptionsAdapter {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    List<IPollOption> optionList;
    boolean isFromImagePicker;
    public CameraOptionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @param param2 Parameter 2.
     * @return A new instance of fragment CameraOptionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CameraOptionsFragment newInstance(boolean isFromImagePicker, String param2) {
        CameraOptionsFragment fragment = new CameraOptionsFragment();
        Bundle args = new Bundle();
        args.putBoolean("isFromImagePicker", isFromImagePicker);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isFromImagePicker = getArguments().getBoolean("isFromImagePicker");
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.ipoll_fragment_camera_options, container, false);
        ButterKnife.bind(this,view);

        setUp();
        return view;
    }

    private void setUp()
    {
        optionList = Utility.generateCameraOptions();
        recyclerView.setLayoutManager(new LinearLayoutManager(activity(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(new CameraOptionsAdapter(activity(),optionList,this));
    }

    @Override
    public void changeTitle(String title) {

    }

    @Override
    public void showSnackBar(String snackBarText, int type)
    {
        Utility.showSnackBar(activity(),coordinatorLayout,snackBarText,type);
    }

    @Override
    public Activity activity() {
        return getActivity();
    }

    @Override
    public void optionClick(IPollOption option, int position)
    {
        if (position == 0)
        {
            if (isFromImagePicker){
                ((ImagePickerActivity) activity()).openCameraSnap();
            }else {
                ((HelperActivity) activity()).openCameraSnap();
            }

        }
        else
        {
            if (isFromImagePicker){
                ((ImagePickerActivity) activity()).openCameraVideo();
            }else {
                ((HelperActivity) activity()).openCameraVideo();
            }

        }
    }
}
