package info.dkapp.flow.ipoll.fragments.options;


import android.app.Activity;
import android.os.Bundle;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import butterknife.BindView;
import butterknife.ButterKnife;
import info.dkapp.flow.appmanagers.feed.models.Feed;
import info.dkapp.flow.bottommenu.activity.BottomMenuActivity;
import info.dkapp.flow.bottommenu.activity.HelperActivity;
import info.dkapp.flow.bottommenu.adapter.OptionAdapter;
import info.dkapp.flow.bottommenu.interfaces.fragments.IFragment;
import info.dkapp.flow.bottommenu.models.IPollOption;

import info.dkapp.flow.R;
import info.dkapp.flow.ipoll.interfaces.other.IOptionsAdapter;
import info.dkapp.flow.userflow.Util.Common;
import info.dkapp.flow.utils.Utility;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OptionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OptionsFragment extends Fragment implements IFragment, IOptionsAdapter {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    Feed feed;
    int feedPosition, type;

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    public OptionsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static OptionsFragment newInstance(Feed feed, int position, int type) {
        OptionsFragment fragment = new OptionsFragment();
        Bundle args = new Bundle();
        args.putParcelable("Feed", feed);
        args.putInt("Position", position);
        args.putInt("Type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            feed = getArguments().getParcelable("Feed");
            feedPosition = getArguments().getInt("Position");
            type = getArguments().getInt("Type");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_options, container, false);
        ButterKnife.bind(this, view);

        setUp();
        return view;
    }

    private void setUp() {
        recyclerView.setLayoutManager(new LinearLayoutManager(activity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new OptionAdapter(activity(), Utility.generateFeedUserOptions(), this, 1));
    }

    @Override
    public void changeTitle(String title) {

    }

    @Override
    public void showSnackBar(String snackBarText, int type) {
        Utility.showSnackBar(activity(), coordinatorLayout, snackBarText, type);
    }

    @Override
    public Activity activity() {
        return getActivity();
    }

    @Override
    public void optionClick(IPollOption option, int position) {
        activity().onBackPressed();
        switch (position) {
            case 0:
                    if(activity() instanceof BottomMenuActivity) {
                        ((BottomMenuActivity) activity()).editFeed(feed, feedPosition);
                    } else if(activity() instanceof HelperActivity) {
                        //((HelperActivity) activity()).editFeed(feed, feedPosition);
                    }
                break;
            case 1:
                    Common.getInstance().deleteFeed(feed.id, feedPosition, type,"");
                break;
        }

    }
}
