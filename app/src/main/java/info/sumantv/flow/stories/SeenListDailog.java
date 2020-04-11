package info.sumantv.flow.stories;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.sumantv.flow.bottommenu.constants.RController;
import info.sumantv.flow.bottommenu.generic.EmptyDataNewAdapter;
import info.sumantv.flow.bottommenu.interfaces.fragments.IFragment;
import info.sumantv.flow.bottommenu.search.ISearchAdapter;
import info.sumantv.flow.bottommenu.search.Search;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.retrofitcall.ApiClient;
import info.sumantv.flow.retrofitcall.ApiInterface;
import info.sumantv.flow.retrofitcall.ApiRequestModel;
import info.sumantv.flow.retrofitcall.ApiResponseModel;
import info.sumantv.flow.retrofitcall.EnumConstants;
import info.sumantv.flow.retrofitcall.IApiListener;
import info.sumantv.flow.stories.fragments.StoriesFragment;
import info.sumantv.flow.stories.models.SeenProfileInnerData;
import info.sumantv.flow.stories.models.StorySeenProfileData;
import info.sumantv.flow.userflow.Util.Common;
import info.sumantv.flow.utils.RecyclerUtil.CommonRecycler;
import info.sumantv.flow.utils.RecyclerUtil.IRecyclerViewCommon;
import retrofit2.Call;

public class SeenListDailog extends BottomSheetDialogFragment implements View.OnClickListener, IRecyclerViewCommon, IApiListener, IFragment {

    @BindView(R.id.closeicon)
    ImageView closeicon;

    @BindView(R.id.seencount)
    TextView seencount;

    @BindView(R.id.recyclerSeenList)
    RecyclerView recyclerSeenList;
    Context context;


    private SeenProfilesAdapter searchAdapter;
    private ISearchAdapter iSearchAdapter;


    private List<Search> searchList;

    String storyId;

    public SeenListDailog() {
        // Required empty public constructor
    }

    private static SeenListDailog instance = null;

    boolean isDailogToUser = false;


    public static SeenListDailog getInstance() {
        return instance;
    }


    public void setData(String storyId) {
        this.storyId = storyId;
    }


    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {

        super.setupDialog(dialog, style);

        View contentView = View.inflate(getContext(), R.layout.fragment_seen_list_bottom, null);
        dialog.setContentView(contentView);

        ((View) contentView.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));

        ButterKnife.bind(this, contentView);


        Common.getInstance().setSeenListDailog(this);

        context = getContext();
        instance = this;
        isDailogToUser = true;


        closeicon.setOnClickListener(this::onClick);

        fetchDataFromServer(true);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.closeicon:
                dimiss_dailog();
                break;
        }
    }

    @Override
    public void setSkelltonView(RecyclerView recyclerView, boolean firstTime) {
        CommonRecycler.setSkelltonViewOther(getActivity(), recyclerView, true, firstTime, true);
    }

    @Override
    public void nodataList(RecyclerView recyclerView, String title, String subTitle, int imageResource) {
        recyclerView.setLayoutManager(new LinearLayoutManager(activity()));
        recyclerView.setAdapter(new EmptyDataNewAdapter(activity(), title, subTitle, imageResource));
    }

    @Override
    public boolean checkInterConnection(RecyclerView recyclerView) {
        if (Common.checkInternetConnection(activity())) {
            return true;
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(activity(), LinearLayoutManager.HORIZONTAL, false));
            CommonRecycler.setNoInternetOrServerDown(activity(), recyclerView, false);
            return false;
        }
    }

    public Boolean getSeenListDailogVisibility() {
        return isDailogToUser;
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

    @Override
    public void fetchDataFromServer(boolean firstTime) {
        IApiListener iApiListener = this;
        Call<ApiResponseModel> call = null;
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        setSkelltonView(recyclerSeenList, firstTime);

        if (firstTime) {
            if (Common.getInstance().checkInterConnection(recyclerSeenList, activity())) {
                searchAdapter = null;
                searchList = new ArrayList<>();
                String url = Constants.StoriesConstants.GET_STORY_SEEN_PROFILES + storyId + "/" + Constants.LIKES_LOAD_MORE_COUNT + "/0";
                call = apiInterface.GET(url);
            }
        } else {
            if (Common.checkInternetConnection(activity())) {
                String url = Constants.StoriesConstants.GET_STORY_SEEN_PROFILES + storyId + "/" + Constants.LIKES_LOAD_MORE_COUNT + "/0";
                call = apiInterface.GET(url);
            } else {
                showSnackBar(Constants.PLEASE_CHECK_INTERNET, 5);
                return;
            }
        }
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, Constants.StoriesConstants.GET_STORY_SEEN_PROFILES, false, iApiListener, false));
    }

    @Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
        if (condition.equals(Constants.StoriesConstants.GET_STORY_SEEN_PROFILES)) {
            try {
                Type type = new TypeToken<StorySeenProfileData>() {
                }.getType();

                StorySeenProfileData searchData = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                Log.e("seenCount", searchData.seencount + "");
                if (searchData.seencount <= 1) {
                    seencount.setText(String.valueOf(searchData.seencount) + " Viewer");
                } else {
                    seencount.setText(String.valueOf(searchData.seencount) + " Viewers");
                }


                List<SeenProfileInnerData> searchListComplete = searchData.StorySeenProfileInfo;
//                    paginationDate = searchData.paginationDate;

                if (searchListComplete.size() > 0) {
                    searchAdapter = new SeenProfilesAdapter(searchListComplete, activity(), RController.LOADED, iSearchAdapter);
                    recyclerSeenList.setAdapter(searchAdapter);
                    Log.e("listSize", searchListComplete.size() + "");
                } else {
                    nodataList(recyclerSeenList, Constants.SORRY, Constants.NO_VIEWS, R.drawable.ic_nodata);
                }
                   /* if (!isLoadMoreApiRunning) {
                        recyclerSeenList.setVisibility(View.VISIBLE);
                        if (searchListComplete.size() > 0) {
                            if ("R".length() != 0) {
                                searchList = new ArrayList<>();
                                searchList = searchListComplete;

                                searchAdapter = new SearchAdapter(searchListComplete, activity(), RController.LOADED, iSearchAdapter);
                                recyclerSeenList.setAdapter(searchAdapter);
                            } else {
                                nodataList(recyclerSeenList, Constants.WHOOPS, Constants.NO_VIEWS, R.drawable.ic_nodata);
                            }
                        } else {
                            Log.e("nodataSearch", "yes");
                            nodataList(recyclerSeenList, Constants.SORRY, Constants.NO_VIEWS, R.drawable.ic_nodata);
                        }
                    } else {
                        ArrayList<Search> searchListTemp = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                        if (searchListTemp.size() < LIMIT_COUNT) {
                            stopLoading = true;
                        }
                        if (searchListTemp.size() > 0) {
                            searchList.addAll(searchListTemp);
                            searchAdapter.loadmore(searchList);
                        }
                    }*/
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
        if (condition.equals(Constants.StoriesConstants.GET_STORY_SEEN_PROFILES)) {
            Toast.makeText(context, Constants.SOMETHING_WENT_WRONG, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                if ((keyCode == android.view.KeyEvent.KEYCODE_BACK)) {
                    dimiss_dailog();
                    return true;
                }
                // Otherwise, do nothing else
                else return false;
            }
        });
        getDialog().setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dimiss_dailog();
            }
        });
    }

    private void dimiss_dailog() {
        if (StoriesFragment.getInstance() != null) {
            StoriesFragment.getInstance().resumeVideo();
        }
        dismiss();
    }

    @Override
    public void onDestroy() {
        Common.getInstance().setSeenListDailog(null);
        super.onDestroy();
    }
}
