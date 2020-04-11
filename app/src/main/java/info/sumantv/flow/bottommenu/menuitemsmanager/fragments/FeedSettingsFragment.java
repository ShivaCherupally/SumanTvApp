package info.sumantv.flow.bottommenu.menuitemsmanager.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import butterknife.BindView;
import butterknife.ButterKnife;

import info.sumantv.flow.R;
import info.sumantv.flow.ipoll.fragments.feeds.FeedsFragment;
import info.sumantv.flow.retrofitcall.SessionManager;

public class FeedSettingsFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    @BindView(R.id.swHdImages)
    public Switch swHdImages;
    @BindView(R.id.swAutoPlayGIF)
    public Switch swAutoPlayGIF;
    @BindView(R.id.swAutoPlayVIDEOS)
    public Switch swAutoPlayVIDEOS;
    @BindView(R.id.rbCollage)
    public RadioButton rbCollage;
    @BindView(R.id.rbGrid)
    public RadioButton rbGrid;
    @BindView(R.id.llCollage)
    public LinearLayout llCollage;
    @BindView(R.id.llGrid)
    public LinearLayout llGrid;

    public FeedSettingsFragment() {
        // Required empty public constructor
    }

    public static FeedSettingsFragment newInstance(String param1, String param2) {
        FeedSettingsFragment fragment = new FeedSettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
        View root = inflater.inflate(R.layout.fragment_feed_settings, container, false);
        ButterKnife.bind(this, root);
        swHdImages.setOnCheckedChangeListener(this);
        swAutoPlayGIF.setOnCheckedChangeListener(this);
        swAutoPlayVIDEOS.setOnCheckedChangeListener(this);
        if(SessionManager.getInstance().getKeyValue(SessionManager.KEY_SHOW_HD_IMAGES,false)){
            swHdImages.setChecked(true);
        }
        if(SessionManager.getInstance().getKeyValue(SessionManager.KEY_AUTO_PLAY_GIFS,false)){
            swAutoPlayGIF.setChecked(true);
        }
        if(SessionManager.getInstance().getKeyValue(SessionManager.KEY_AUTO_PLAY_VIDEOS,false)){
            swAutoPlayVIDEOS.setChecked(true);
        }
        setGridSelected(SessionManager.getInstance().getKeyValue(SessionManager.KEY_FEED_GRID_STYLE,false));
        llCollage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setGridSelected(false);
            }
        });
        llGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setGridSelected(true);
            }
        });
        return root;
    }
    private void setGridSelected(Boolean isSelected){
        SessionManager.getInstance().setKeyValue(SessionManager.KEY_FEED_GRID_STYLE,isSelected);
        rbCollage.setChecked(!isSelected);
        rbGrid.setChecked(isSelected);
        if (FeedsFragment.getInstance() != null) {
            FeedsFragment.getInstance().setReloadAdapterChanges(true);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.swHdImages:
                if(isChecked){
                    SessionManager.getInstance().setKeyValue(SessionManager.KEY_SHOW_HD_IMAGES,true);
                    swHdImages.setChecked(true);
                } else {
                    SessionManager.getInstance().setKeyValue(SessionManager.KEY_SHOW_HD_IMAGES,false);
                    swHdImages.setChecked(false);
                }
                if (FeedsFragment.getInstance() != null) {
                    FeedsFragment.getInstance().setReloadAdapterChanges(true);
                }
                break;
            case R.id.swAutoPlayGIF:
                if(isChecked){
                    SessionManager.getInstance().setKeyValue(SessionManager.KEY_AUTO_PLAY_GIFS,true);
                    swAutoPlayGIF.setChecked(true);
                } else {
                    SessionManager.getInstance().setKeyValue(SessionManager.KEY_AUTO_PLAY_GIFS,false);
                    swAutoPlayGIF.setChecked(false);
                }
                if (FeedsFragment.getInstance() != null) {
                    FeedsFragment.getInstance().setReloadAdapterChanges(true);
                }
                break;
            case R.id.swAutoPlayVIDEOS:
                if(isChecked){
                    SessionManager.getInstance().setKeyValue(SessionManager.KEY_AUTO_PLAY_VIDEOS,true);
                    swAutoPlayVIDEOS.setChecked(true);
                } else {
                    SessionManager.getInstance().setKeyValue(SessionManager.KEY_AUTO_PLAY_VIDEOS,false);
                    swAutoPlayVIDEOS.setChecked(false);
                }
                if (FeedsFragment.getInstance() != null) {
                    FeedsFragment.getInstance().setReloadAdapterChanges(true);
                }
                break;
        }
    }
}
