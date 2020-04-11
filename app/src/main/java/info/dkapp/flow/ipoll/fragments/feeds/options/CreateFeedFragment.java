package info.dkapp.flow.ipoll.fragments.feeds.options;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.*;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.location.places.Place;
import de.hdodenhof.circleimageview.CircleImageView;
import info.dkapp.flow.appmanagers.feed.models.Feed;
import info.dkapp.flow.appmanagers.feed.models.FeedMemberDetails;
import info.dkapp.flow.appmanagers.feed.models.GalleryFile;
import info.dkapp.flow.appmanagers.feed.models.Media;
import info.dkapp.flow.bottommenu.activity.BottomMenuActivity;
import info.dkapp.flow.bottommenu.activity.HelperActivity;
import info.dkapp.flow.bottommenu.adapter.OptionAdapter;
import info.dkapp.flow.bottommenu.customviews.images.CollageView;
import info.dkapp.flow.bottommenu.interfaces.fragments.IFragment;
import info.dkapp.flow.bottommenu.models.IPollOption;

import info.dkapp.flow.R;
import info.dkapp.flow.celebflow.constants.Constants;
import info.dkapp.flow.celebflow.constants.MediaType;
import info.dkapp.flow.ipoll.interfaces.other.IOptionsAdapter;
import info.dkapp.flow.retrofitcall.ApiClient;
import info.dkapp.flow.retrofitcall.SessionManager;
import info.dkapp.flow.userflow.Util.Common;
import info.dkapp.flow.utils.Logger;
import info.dkapp.flow.utils.Utility;
import info.dkapp.flow.imagepicker.model.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateFeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateFeedFragment extends Fragment implements IFragment, IOptionsAdapter {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.etTitle)
    EditText etTitle;

    @BindView(R.id.etDescription)
    EditText etDescription;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.collageView)
    CollageView collageView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.imgUser)
    CircleImageView imgUser;

    @BindView(R.id.tvUserName)
    TextView tvUserName;

    @BindView(R.id.imgOptions)
    ImageView imgOptions;

    @BindView(R.id.tvAddLocation)
    TextView tvAddLocation;

    @BindView(R.id.llLocation)
    LinearLayout llLocation;


    OptionAdapter optionAdapter;

    public ArrayList<Image> gallerySelectedList;

    public List<Media> feedMediaList = null;

    Place place;


    public CreateFeedFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static CreateFeedFragment newInstance(List<GalleryFile> gallerySelectedList) {
        CreateFeedFragment fragment = new CreateFeedFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("GalleryFileList", (ArrayList<? extends Parcelable>) gallerySelectedList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            gallerySelectedList = getArguments().getParcelableArrayList("GalleryFileList");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.ipoll_fragment_create_feed, container, false);
        ButterKnife.bind(this, view);

        setUp();
        return view;
    }

    private void setUp() {

        if (SessionManager.userLogin.profilePic != null
                && !SessionManager.userLogin.profilePic.isEmpty()) {
            Log.e("profilePicNew", SessionManager.userLogin.profilePic + "");
            Glide.with(getActivity()).load(ApiClient.BASE_URL + SessionManager.userLogin.profilePic)

                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_grey_celebkonect_logo)
                    .into(imgUser);
        } else {
            imgUser.setImageResource(R.drawable.ic_grey_celebkonect_logo);
        }

        if (SessionManager.userLogin.firstName != null
                && !SessionManager.userLogin.firstName.isEmpty()) {
            tvUserName.setText(SessionManager.userLogin.firstName);
            if (SessionManager.userLogin.lastName != null && !SessionManager.userLogin.lastName.isEmpty()) {
                tvUserName.setText(SessionManager.userLogin.firstName + " " + SessionManager.userLogin.lastName);
            }
        } else {
            tvUserName.setText("");
        }


//        imgUser.setImageURI(Uri.parse(Constants.SAMPLE_USER_PROFILE_PIC));
//        etTitle.setText("MB");
//        etDescription.setText("Testing...");
        recyclerView.setLayoutManager(new LinearLayoutManager(activity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(optionAdapter = new OptionAdapter(activity(), Utility.generateFeedTypeOptions(), this, 0));

        llLocation.setVisibility(View.GONE);
        etTitle.setTypeface(Utility.getTypeface(1,activity()), Typeface.BOLD);

        etTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etTitle.getText().toString().startsWith(" ")) {
                    etTitle.setText("");
                }
                updatePostBtn();
            }
        });

        etDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etDescription.getText().toString().startsWith(" ")) {
                    etDescription.setText("");
                }
                updatePostBtn();
            }
        });


        if (gallerySelectedList != null) {
            new AddCollageMedia().execute();
        }
    }

    private void updatePostBtn() {
        String title = Common.getInstance().IsNullReturnValue(etTitle.getText().toString().trim(), "");
        String description = Common.getInstance().IsNullReturnValue(etDescription.getText().toString().trim(), "");
        if (feedMediaList == null) {
            feedMediaList = new ArrayList<>();
        }
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (feedMediaList.size() <= 0 && title.isEmpty() && description.isEmpty()) {
                        ((HelperActivity) activity()).menuShare.setVisible(false);
                    } else {
                        ((HelperActivity) activity()).menuShare.setVisible(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 500);
    }

    public void updateMediaPost(ArrayList<Image> galleryFileList) {
        if (galleryFileList.size() > 0) {
            collageView.removeAllViews();
            this.gallerySelectedList = galleryFileList;
            new AddCollageMedia().execute();
        } else {
            collageView.removeAllViews();
            this.gallerySelectedList = new ArrayList<>();
            feedMediaList = new ArrayList<>();
        }
        updatePostBtn();
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
        if (position == 0) {
            ((HelperActivity) activity()).openCustomGallery();
        } else {

        }
    }


    public class AddCollageMedia extends AsyncTask<Void, Void, List<Media>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (((HelperActivity) activity()).menuShare != null) {
                ((HelperActivity) activity()).menuShare.setVisible(false);
            }
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected List<Media> doInBackground(Void... voids) {
            List<Media> result = new ArrayList<>();
            for (int i = 0; i < gallerySelectedList.size(); i++) {
                Media media = new Media();
                Logger.d("MEDIA TYPE", "" + gallerySelectedList.get(i).mediaType);
                if (gallerySelectedList.get(i).mimeType != null && gallerySelectedList.get(i).mimeType.contains(Constants.MEDIA_TYPE_GIF)) {
                    media.type = Constants.MEDIA_TYPE_GIF;
                } else {
                    media.type = gallerySelectedList.get(i).mediaType == MediaType.IMAGE ? Constants.MEDIA_TYPE_IMAGE : Constants.MEDIA_TYPE_VIDEO;
                }
                media.mimeType = gallerySelectedList.get(i).mimeType;
                media.name = gallerySelectedList.get(i).name;
                media.uri = gallerySelectedList.get(i).uri;
                media.caption = gallerySelectedList.get(i).caption;
                //for folder wise
              //  media.sizeKB = Double.parseDouble(gallerySelectedList.get(i).size) / 1024;
                media.ratio = gallerySelectedList.get(i).mediaType == MediaType.IMAGE ? Utility.getImageRatio(activity(), gallerySelectedList.get(i).uri) : Utility.getVideoRatio(activity(), gallerySelectedList.get(i).uri);
                gallerySelectedList.get(i).ratio = media.ratio;
                media.facePointList = gallerySelectedList.get(i).facePointList;
                Logger.d("Image Ratio", "" + media.ratio);
                result.add(media);
            }
            return result;
        }

        @Override
        protected void onPostExecute(List<Media> list) {
            super.onPostExecute(list);
            progressBar.setVisibility(View.GONE);
            if (activity() instanceof HelperActivity && ((HelperActivity) activity()).menuShare != null) {
                ((HelperActivity) activity()).menuShare.setVisible(true);
            }
            feedMediaList = list;
            collageView.addMedia(list, Utility.getIPollCollageController(list), Utility.getScreenWidth(activity()), false);
            collageView.setCollageItemClickListener((position) -> {
                Intent intent = new Intent(activity(), HelperActivity.class);
                intent.putExtra(Constants.FRAGMENT_KEY, 9038);
                intent.putExtra("Position", position);
                intent.putParcelableArrayListExtra("GalleryFileList", (ArrayList<? extends Parcelable>) gallerySelectedList);
                activity().startActivityForResult(intent, Utility.generateRequestCodes().get("FEED_EDIT"));
            });
            updatePostBtn();
        }
    }

    public void verifyFeedUpload() {
        if (progressBar.getVisibility() == View.VISIBLE) {
            showSnackBar("Try Again", 2);
            return;
        }
        Feed feed = null;
        if (("" + etTitle.getText()).trim().length() > 0 || ("" + etDescription.getText()).trim().length() > 0
                || (feedMediaList != null && feedMediaList.size() > 0)) {
//            if(gallerySelectedList!=null && gallerySelectedList.size()>0)
//            {
            feed = new Feed();
            feed.feedTitle = ("" + etTitle.getText()).trim();
            feed.location = place != null ? "" + place.getAddress() : null;
            feed.feedDescription = ("" + etDescription.getText()).trim();
            feed.mediaList = feedMediaList;


//            }
//            else
//            {
//                showSnackBar(Constants.PLEASE_ADD_MEDIA,2);
//            }
        } else {
            showSnackBar(Constants.PLEASE_FILL_THE_FIELDS, 2);
        }
        FeedMemberDetails feedMemberDetails;
        if (Common.isCelebAndManager(activity())) {
            feedMemberDetails = new FeedMemberDetails(true, false);
        } else if (Common.isCelebStatus(activity())) {
            feedMemberDetails = new FeedMemberDetails(true, false);
        } else {
            feedMemberDetails = new FeedMemberDetails(false, true);
        }


        feed.feedMemberDetails = feedMemberDetails;
        if (Utility.isNetworkAvailable(activity())) {
            createFeed(feed);
        } else {
            showSnackBar(Constants.PLEASE_CHECK_INTERNET, 2);
        }
    }


    public void addPlace(Place place) {
        if (place != null) {
            this.place = place;
            tvAddLocation.setText(place.getAddress());
        }
    }

    private void createFeed(Feed feed) {
        Intent returnData = new Intent(activity(), BottomMenuActivity.class);
        returnData.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        returnData.putExtra("data", 1);
        returnData.putExtra("Feed", feed);
        activity().setResult(Activity.RESULT_OK, returnData);
        activity().finish();
    }
}
