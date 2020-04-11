package info.sumantv.flow.bottommenu.fragment;


import android.app.Activity;
import android.os.Bundle;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import info.sumantv.flow.appmanagers.feed.models.Feed;
import info.sumantv.flow.bottommenu.activity.HelperActivity;
import info.sumantv.flow.bottommenu.adapter.OptionAdapter;
import info.sumantv.flow.bottommenu.interfaces.fragments.IFragment;
import info.sumantv.flow.bottommenu.models.IPollOption;

import info.sumantv.flow.R;
import info.sumantv.flow.ipoll.interfaces.other.IOptionsAdapter;
import info.sumantv.flow.menu_list.MyContent.MyPostsActivity.MyPostData;
import info.sumantv.flow.userflow.Util.Common;
import info.sumantv.flow.utils.UtilityNew;

import java.util.ArrayList;

public class OptionsFragment extends Fragment implements IFragment, IOptionsAdapter {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    ArrayList<MyPostData> myPostData;
    Feed feed;
    private String myContentData;
    int feedPosition;
    CoordinatorLayout coordinatorLayout;
    RecyclerView recyclerView;

    public OptionsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static OptionsFragment newInstance(String myPostData, int position, Feed feed) {
        OptionsFragment fragment = new OptionsFragment();
        Bundle args = new Bundle();
        args.putString("myPostData", myPostData);
        args.putInt("Position", position);
        args.putParcelable("Feed", feed);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            myContentData = getArguments().getString("myPostData");
            feedPosition = getArguments().getInt("Position");
            feed = getArguments().getParcelable("Feed");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_options, container, false);

        setUp(view);
        return view;
    }

    private void setUp(View view) {
        coordinatorLayout = view.findViewById(R.id.coordinatorLayout);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new OptionAdapter(activity(), UtilityNew.generateFeedUserOptions(), this, 1));
    }

    @Override
    public void changeTitle(String title) {

    }

    @Override
    public void showSnackBar(String snackBarText, int type) {
        UtilityNew.showSnackBar(activity(), coordinatorLayout, snackBarText, type);
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

                ((HelperActivity) activity()).editFeed(feed, feedPosition);
                break;
            case 1:
                    Common.getInstance().deleteFeed(myContentData, feedPosition,0,"");

                break;
        }

    }
}
