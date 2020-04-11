package info.dkapp.flow.celebflow.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.dkapp.flow.R;
import info.dkapp.flow.appmanagers.feed.models.Media;
import info.dkapp.flow.bottommenu.constants.RController;
import info.dkapp.flow.bottommenu.generic.EmptyDataAdapter;
import info.dkapp.flow.bottommenu.generic.EmptyDataAdapterTop;
import info.dkapp.flow.bottommenu.interfaces.fragments.IFragment;
import info.dkapp.flow.celebflow.Fragments.adapter.PhotosMediaAdapter;
import info.dkapp.flow.celebflow.Fragments.model.CelebUserMedia;
import info.dkapp.flow.celebflow.constants.Constants;
import info.dkapp.flow.retrofitcall.ApiClient;
import info.dkapp.flow.retrofitcall.ApiInterface;
import info.dkapp.flow.retrofitcall.ApiRequestModel;
import info.dkapp.flow.retrofitcall.ApiResponseModel;
import info.dkapp.flow.retrofitcall.EnumConstants;
import info.dkapp.flow.retrofitcall.IApiListener;
import info.dkapp.flow.userflow.Util.Common;
import info.dkapp.flow.utils.CommonSkeletonAdapter;
import info.dkapp.flow.utils.Utility;
import retrofit2.Call;

/**
 * Created by Shiva on 12/13/2017.
 */

@SuppressLint("ValidFragment")
public class PhotosFragment extends Fragment implements IFragment, IApiListener, View.OnClickListener {

    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinator_layout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.llGrid)
    LinearLayout llGrid;
    @BindView(R.id.iv_One)
    ImageView iv_One;
    @BindView(R.id.iv_Two)
    ImageView iv_Two;
    @BindView(R.id.iv_Three)
    ImageView iv_Three;
    @BindView(R.id.iv_GRID)
    ImageView iv_GRID;
    StaggeredGridLayoutManager layoutManager;
    CelebUserMedia celebUserMedia;
    PhotosMediaAdapter photosMediaAdapter;
    Boolean isSelfProfile = false;
    private String CELEB_ID = null;
    private static PhotosFragment instance = null;
    IApiListener iApiListener;
    List<Media> mediaList = new ArrayList<>();
    List<Media> mediaListMember = new ArrayList<>();
    Boolean isMember;
    boolean videopage;
    String type;
    CommonSkeletonAdapter commonSkeletonAdapter;
    private boolean conditionLoadMore = false;
    boolean fromAuditionPage, stopLoadmore = false;
    int Limit = 20;
    Boolean isFragVisibleToUser = false;
    String celebId = "";

    public PhotosFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        iApiListener = this;
        Common.getInstance().setPhotosFragment(this);
    }

    public static PhotosFragment getInstance() {
        return instance;
    }


    public PhotosFragment(String celebId, Boolean isSelfProfile, List<Media> mediaList, Boolean isMember, boolean videoPage) {
        this.CELEB_ID = celebId;
        this.isSelfProfile = isSelfProfile;
        this.mediaListMember = mediaList;
        this.isMember = isMember;
        this.videopage = videoPage;
        if (isSelfProfile) {
            this.celebId = Common.isLoginId();
        } else {
            this.celebId = celebId;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.photos_fragment, container, false);
        ButterKnife.bind(this, view);
        initializeSetup();
        //If Audition Talent Search profile fromAuditionPage its become true
        if (!fromAuditionPage) {
            getPhotosOrVideoFiles();
        }
        iv_One.setOnClickListener(this::onClick);
        iv_Two.setOnClickListener(this::onClick);
        iv_Three.setOnClickListener(this::onClick);
        iv_GRID.setOnClickListener(this::onClick);
        return view;
    }

    public void recyclerLoadMore() {
        if (!fromAuditionPage && !stopLoadmore) {
            loadMore(true);
        }
    }

    private void loadMore(boolean status) {
        conditionLoadMore = status;
        if (mediaList != null && mediaList.size() > 0) {
            String type;
            if (videopage) {
                type = "video/";
            } else {
                type = "image/";
            }
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<ApiResponseModel> call = apiInterface.GET(ApiClient.GET_MEDIA_DATA + CELEB_ID + "/" + type
                    + mediaList.get(mediaList.size() - 1).createdAt + "/");
            Common.getInstance().callAPI(new ApiRequestModel().setModel(getActivity(), call, Constants.MEDIA_DATA, false, iApiListener, false));
        }
    }

    private void initializeSetup() {
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
    }

    public Boolean getFragVisibleToUser() {
        return isFragVisibleToUser;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isFragVisibleToUser = isVisibleToUser;
    }

    public void getPhotosOrVideoFiles() {
        setSkelltonAdapter();
        if (!Common.checkInternetConnection(getActivity())) {
            recyclerView.removeAllViews();
            recyclerView.setAdapter(new EmptyDataAdapter(getActivity(), Constants.UH_OH, Constants.PLEASE_CHECK_INTERNET,
                    R.drawable.ic_network, 0));
            return;
        }
        type = "image/";
        if (videopage) {
            type = "video/";
        } else {
            type = "image/";
        }
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.GET(ApiClient.GET_MEDIA_DATA + CELEB_ID + "/" + type + "0" + "/");
        Common.getInstance().callAPI(new ApiRequestModel().setModel(getActivity(), call, Constants.MEDIA_DATA,
                false,
                iApiListener, false));
    }

    private void emptyDataList() {
        layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        if (!fromAuditionPage) {

            if (videopage) {
                String noVideos = Constants.NO_VIDEOS_AUDITION;
                if (CELEB_ID != null && !CELEB_ID.isEmpty()) {
                    if (CELEB_ID.equals(Common.isLoginId())) {
                        noVideos = Constants.NO_VIDEOS;
                    } else {
                        noVideos = Constants.NO_VIDEOS_AUDITION;
                    }
                }
                recyclerView.setAdapter(new EmptyDataAdapterTop(activity(), noVideos, "", R.drawable.ic_no_videos_saved, 1));
            } else {
                String noPhotos = Constants.NO_IMAGES_AUDITION;
                if (CELEB_ID != null && !CELEB_ID.isEmpty()) {
                    if (CELEB_ID.equals(Common.isLoginId())) {
                        noPhotos = Constants.NO_IMAGES;
                    } else {
                        noPhotos = Constants.NO_IMAGES_AUDITION;
                    }
                }
                recyclerView.setAdapter(new EmptyDataAdapterTop(activity(), noPhotos, "", R.drawable.ic_no_images_to_saved, 1));
            }
        } else {
            if (videopage) {
                String noVideos = Constants.NO_VIDEOS_AUDITION;
                if (CELEB_ID != null && !CELEB_ID.isEmpty()) {
                    if (CELEB_ID.equals(Common.isLoginId())) {
                        noVideos = Constants.NO_VIDEOS;
                    } else {
                        noVideos = Constants.NO_VIDEOS_AUDITION;
                    }
                }
                recyclerView.setAdapter(new EmptyDataAdapterTop(activity(),noVideos, "", R.drawable.ic_no_videos_saved, 1));

            } else {
                String noPhotos = Constants.NO_IMAGES_AUDITION;
                if (CELEB_ID != null && !CELEB_ID.isEmpty()) {

                    if (CELEB_ID.equals(Common.isLoginId())) {
                        noPhotos = Constants.NO_IMAGES;
                    } else {
                        noPhotos = Constants.NO_IMAGES_AUDITION;
                    }
                }
                recyclerView.setAdapter(new EmptyDataAdapterTop(activity(), noPhotos, "", R.drawable.ic_no_images_to_saved, 1));
            }
        }
    }

    private void setAdapter(int grid) {
        if (mediaList != null && mediaList.size() > 0) {
            layoutManager = new StaggeredGridLayoutManager(grid, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.getLayoutManager().setAutoMeasureEnabled(true);
            recyclerView.setAdapter(photosMediaAdapter = new PhotosMediaAdapter(activity(),
                    mediaList, grid, isSelfProfile, isMember,
                    fromAuditionPage, celebId));
        } else {
            emptyDataList();
        }
        llGrid.setVisibility(View.GONE);
        iv_GRID.setVisibility(View.VISIBLE);
    }

    @Override
    public void changeTitle(String title) {

    }

    @Override
    public void showSnackBar(String snackBarText, int type) {
        Utility.showSnackBar(activity(), coordinator_layout, snackBarText, type);
    }

    @Override
    public Activity activity() {
        return getActivity();
    }




    @Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
        if (condition.equals(Constants.MEDIA_DATA)) {
            try {
                Type type = new TypeToken<CelebUserMedia>() {
                }.getType();
                celebUserMedia = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                if (!isMember) {
                    if (celebUserMedia != null && celebUserMedia.memberMedia.getMedia() != null && celebUserMedia.memberMedia.getMedia().size() > 0) {
                        if (celebUserMedia.memberMedia.getMedia().size() < Limit) {
                            stopLoadmore = true;
                        }
                        if (conditionLoadMore) {
                            mediaList.addAll(celebUserMedia.memberMedia.getMedia());
                            photosMediaAdapter.loadmore(celebUserMedia.memberMedia.getMedia());
                        } else {
                            mediaList = celebUserMedia.memberMedia.getMedia();
                            setAdapter(2);
                        }
                    }
                } else {
                    if (celebUserMedia != null && celebUserMedia.userDetails.getPastProfileImages() != null && celebUserMedia.userDetails.getPastProfileImages().size() > 0) {

                        List<Media> mediaListTemop = new ArrayList<>();
                        if (conditionLoadMore) {
                            for (int i = 0; i < celebUserMedia.userDetails.getPastProfileImages().size(); i++) {
                                Media media = new Media();
                                media.url = celebUserMedia.userDetails.getPastProfileImages().get(i).avtarImgPath;
                                mediaListTemop.add(media);
                            }
                            if (mediaListTemop.size() < Limit) {
                                stopLoadmore = true;
                            }
                            mediaList.addAll(mediaListTemop);
                            photosMediaAdapter.loadmore(celebUserMedia.memberMedia.getMedia());
                        } else {
                            for (int i = 0; i < celebUserMedia.userDetails.getPastProfileImages().size(); i++) {
                                Media media = new Media();
                                media.url = celebUserMedia.userDetails.getPastProfileImages().get(i).avtarImgPath;
                                mediaList.add(media);
                            }
                            if (mediaList.size() < Limit) {
                                stopLoadmore = true;
                            }
                            setAdapter(2);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (mediaList == null) {
                mediaList = new ArrayList<>();
            }
            if (!conditionLoadMore && mediaList.size() <= 0) {
                emptyDataList();
            }
        }
    }

    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
        if (condition.equals(Constants.MEDIA_DATA)) {
            //emptyDataList();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_One:
                gridType(1);
                break;
            case R.id.iv_Two:
                gridType(2);
                break;
            case R.id.iv_Three:
                gridType(3);
                break;
            case R.id.iv_GRID:
                Animation slideleft = AnimationUtils.loadAnimation(activity(), R.anim.slide_from_right);
                llGrid.setVisibility(View.VISIBLE);
                llGrid.startAnimation(slideleft);
                iv_GRID.setVisibility(View.GONE);
                break;
        }
    }

    private void gridType(int type) {
        if (type == 1) {
            iv_GRID.setImageResource(R.drawable.ic_grid_view_1);
        } else if (type == 2) {
            iv_GRID.setImageResource(R.drawable.ic_grid_view);
        } else if (type == 3) {
            iv_GRID.setImageResource(R.drawable.ic_grid_view_3);
        }
        iv_GRID.setColorFilter(getContext().getResources().getColor(R.color.white));
        setAdapter(type);
    }

    private void setSkelltonAdapter() {
        commonSkeletonAdapter = new CommonSkeletonAdapter(RController.LOADING, true);
        recyclerView.setAdapter(commonSkeletonAdapter);
    }
}

