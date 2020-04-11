package info.sumantv.flow.ipoll.fragments.feeds.options;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.gms.location.places.Place;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.sumantv.flow.appmanagers.feed.models.Feed;
import info.sumantv.flow.appmanagers.feed.models.Media;
import info.sumantv.flow.bottommenu.activity.BottomMenuActivity;
import info.sumantv.flow.bottommenu.activity.HelperActivity;
import info.sumantv.flow.bottommenu.interfaces.fragments.IFragment;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.celebflow.constants.MediaType;
import info.sumantv.flow.ipoll.adapters.feeds.options.FeedUpdateAdapter;
import info.sumantv.flow.ipoll.interfaces.feeds.options.IFeedUpdateAdapter;
import info.sumantv.flow.utils.Logger;
import info.sumantv.flow.utils.Utility;
import info.sumantv.flow.imagepicker.model.Image;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateFeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateFeedFragment extends Fragment implements IFragment, IFeedUpdateAdapter {

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

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    public ArrayList<Image> galleryFileList;

    Feed feed, originalFeed;

    public List<Media> mediaList = new ArrayList<>();
    int feedPosition;
    FeedUpdateAdapter feedUpdateAdapter;

    Place place;


    public UpdateFeedFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static UpdateFeedFragment newInstance(Feed feed, int position) {
        UpdateFeedFragment fragment = new UpdateFeedFragment();
        Bundle args = new Bundle();
        args.putParcelable("Feed", feed);
        args.putInt("FeedPosition", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            feed = getArguments().getParcelable("Feed");
            originalFeed = feed.generateClone(feed);
            feedPosition = getArguments().getInt("FeedPosition");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.ipoll_fragment_update_feed, container, false);
        ButterKnife.bind(this, view);

        setUp();
        return view;
    }

    private void setUp() {
        if (feed == null)
            return;

        mediaList = new ArrayList<>();

        if (originalFeed.mediaList != null)
            mediaList.addAll(originalFeed.mediaList);

        recyclerView.setLayoutManager(new LinearLayoutManager(activity()));
        recyclerView.setAdapter(feedUpdateAdapter = new FeedUpdateAdapter(feed, activity(), this));
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
    public void addTitle(String title) {
        feed.feedTitle = title;
        addMenuUpdate();
    }

    @Override
    public void addDescription(String description) {
        feed.feedDescription = description;
        addMenuUpdate();
    }

    @Override
    public void addMediaCaption(Media media, String caption, int position) {
        Logger.d("CAPTION", caption);
        feed.mediaList.get(position).caption = caption;
        if (mediaList != null) {
            if (position < mediaList.size())
                mediaList.get(position).caption = caption;
            else {
                if (galleryFileList != null)
                    galleryFileList.get(position - mediaList.size()).caption = caption;
            }
        } else {
            if (galleryFileList != null)
                galleryFileList.get(position).caption = caption;
        }

        addMenuUpdate();
    }

    private void addMenuUpdate() {
        ((HelperActivity) activity()).showUpdateFeed();
    }

    private void hideMenuUpdate() {
        ((HelperActivity) activity()).hideUpdateFeed();
    }

    public void addPlace(Place place) {
        if (place != null) {
            this.place = place;
            feed.location = "" + place.getAddress();
            feedUpdateAdapter.notifyDataSetChanged();
            addMenuUpdate();
        }
    }

    @Override
    public void removeMedia(Media media, int position) {
        if (feedUpdateAdapter == null) return;

        feed.mediaList.remove(position);

        if (mediaList != null) {

            if (mediaList.size() - 1 >= position) {
                mediaList.remove(position);
            } else {
                if (galleryFileList != null) {
                    galleryFileList.remove(position - mediaList.size());
                }
            }
        } else {
            if (galleryFileList != null) {
                galleryFileList.remove(position - mediaList.size());
            }
        }
        feedUpdateAdapter.notifyDataSetChanged();
        addMenuUpdate();
    }

    @Override
    public void addMoreMedia() {

    }

    @Override
    public void openPlacesSearch() {

    }


    public void updateMediaPost(ArrayList<Image> galleryFileList) {
        if (galleryFileList != null && galleryFileList.size() > 0) {
            showSnackBar("Selected " + galleryFileList.size() + " items", 2);
            this.galleryFileList = galleryFileList;
            new AddMedia().execute();
        }
    }


    public class AddMedia extends AsyncTask<Void, Void, List<Media>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Media> doInBackground(Void... voids) {
            List<Media> result = new ArrayList<>();
            for (int i = 0; i < galleryFileList.size(); i++) {
                Media media = new Media();
                Logger.d("MEDIA TYPE", "" + galleryFileList.get(i).mediaType);
                if (galleryFileList.get(i).mimeType != null && galleryFileList.get(i).mimeType.contains(Constants.MEDIA_TYPE_GIF)) {
                    media.type = Constants.MEDIA_TYPE_GIF;
                } else {
                    media.type = galleryFileList.get(i).mediaType == MediaType.IMAGE ? Constants.MEDIA_TYPE_IMAGE : Constants.MEDIA_TYPE_VIDEO;
                }
                media.mimeType = galleryFileList.get(i).mimeType;
                media.name = galleryFileList.get(i).name;
                media.uri = galleryFileList.get(i).uri;
                media.caption = galleryFileList.get(i).caption;
                //for folder wise
            //    media.sizeKB = Double.parseDouble(galleryFileList.get(i).size) / 1024;
                media.ratio = galleryFileList.get(i).mediaType == MediaType.IMAGE ? Utility.getImageRatio(activity(), media.uri) : Utility.getVideoRatio(activity(), media.uri);
                galleryFileList.get(i).ratio = media.ratio;
                media.facePointList = galleryFileList.get(i).facePointList;
                Logger.d("Image Ratio", "" + media.ratio);
                result.add(media);
            }
            return result;
        }

        @Override
        protected void onPostExecute(List<Media> result) {
            super.onPostExecute(result);
            progressBar.setVisibility(View.GONE);


            feed.mediaList = new ArrayList<>();

            if (mediaList != null) {
                feed.mediaList.addAll(mediaList);
            }

            feed.mediaList.addAll(result);
            feedUpdateAdapter.notifyDataSetChanged();

            addMenuUpdate();

            if (mediaList != null) {
                Logger.d("Original Feed Size", "" + mediaList.size());
            }

        }
    }
    public void verifyFeed() {
        if (progressBar.getVisibility() == View.VISIBLE) {
            showSnackBar("Try Again", 2);
            return;
        }
        if (!Utility.isNetworkAvailable(activity())) {
            showSnackBar(Constants.PLEASE_CHECK_INTERNET, 2);
            return;
        }
        if (feed != null) {
            if ((feed.feedTitle != null && feed.feedTitle.trim().length() > 0) || (feed.feedDescription != null && feed.feedDescription.trim().length() > 0)) {
                updatedFeed();
            } else if (feed.mediaList.size() > 0) {
                updatedFeed();
            } else {
                showSnackBar("Please add media/title", 2);
            }
        } else {
            showSnackBar(Constants.SOMETHING_WENT_WRONG, 2);
        }

    }

    private void updatedFeed() {
        Intent returnData = new Intent(activity(), BottomMenuActivity.class);
        returnData.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        returnData.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (activity().getIntent() != null) {
            if (activity().getIntent().getBooleanExtra("Mine", false)) {
                returnData = new Intent(activity(), HelperActivity.class);
            }
        }
        returnData.putExtra("data", 1);
        returnData.putExtra("Feed", feed);
        returnData.putExtra("FeedPosition", feedPosition);
        activity().setResult(Activity.RESULT_OK, returnData);
        activity().finish();
    }
}
