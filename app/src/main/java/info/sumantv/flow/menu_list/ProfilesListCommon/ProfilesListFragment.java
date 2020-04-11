package info.sumantv.flow.menu_list.ProfilesListCommon;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.sumantv.flow.bottommenu.constants.RController;
import info.sumantv.flow.bottommenu.generic.EmptyDataAdapter;
import info.sumantv.flow.bottommenu.menuitemsmanager.fragments.MyCelebritiesfragment;
import info.sumantv.flow.bottommenu.menuitemsmanager.fragments.MyFanFollowersFragment;
import info.sumantv.flow.databaseutil.appstart.AppController;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.menu_list.MyContent.MyPostsActivity.MyPostAdapter;
import info.sumantv.flow.retrofitcall.ApiClient;
import info.sumantv.flow.retrofitcall.ApiInterface;
import info.sumantv.flow.retrofitcall.ApiRequestModel;
import info.sumantv.flow.retrofitcall.ApiResponseModel;
import info.sumantv.flow.retrofitcall.EnumConstants;
import info.sumantv.flow.retrofitcall.IApiListener;
import info.sumantv.flow.userflow.Util.Common;
import info.sumantv.flow.utils.RecyclerUtil.CommonRecycler;
import info.sumantv.flow.utils.RecyclerUtil.IRecyclerViewCommon;
import info.sumantv.flow.utils.Utility;
import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilesListFragment extends Fragment implements IApiListener, IRecyclerViewCommon {
    @BindView(R.id.recyclerViewCommon)
    RecyclerView recyclerViewCommon;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinator_layout;

    private ProfileAdapter profileAdapter;
    private String profileId = "";
    private boolean profileStatus = false, isVisibleToUser_ = false;
    private String tabName = "";
    private boolean celebritiesItem, celebProfilePage;
    private Integer totalFanListCount = 0, totalFollowListCount = 0, totalBlockListCount = 0;
    private boolean isLoadMoreApiRunning = false, stopLoading = false;
    private String visibleTabname = "";
    private ArrayList<ProfileInfo> profileInfoList;
    private RecyclerView.LayoutManager layoutManagerOffline;

    public static ProfilesListFragment instance;

    public static ProfilesListFragment getInstance() {
        return instance;
    }

    public ProfilesListFragment() {
    }

    @SuppressLint("ValidFragment")
    public ProfilesListFragment(String profileId, boolean profileStatus, String tabName, boolean celebritiesItem, boolean celebProfilePage, String visibleTabname) {
        this.profileId = profileId;
        this.profileStatus = profileStatus;
        this.tabName = tabName;
        this.celebritiesItem = celebritiesItem;
        this.celebProfilePage = celebProfilePage;
        this.visibleTabname = visibleTabname;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.common_recycler_view_page, container, false);
        ButterKnife.bind(this, view);
        profileInfoList = new ArrayList<>();
        isLoadMoreApiRunning = false;
        setSkelltonAdapter();
        if (  (celebritiesItem && tabName.equals(Constants.ProfilesConstants.FAN_TAB))|| (tabName.equals(Constants.ProfilesConstants.FANS_OF_TAB))) {
            fetchDataFromServer(false);
        }
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
            if (Common.checkInternetConnection(getActivity())) {
                profileAdapter = null;
                setSkelltonAdapter();
                fetchDataFromServer(false);
            } else {
                showSnackBar(Constants.PLEASE_CHECK_INTERNET, 5);
            }
        });
        layoutManagerOffline = new LinearLayoutManager(getActivity());
        recyclerViewCommon.setLayoutManager(layoutManagerOffline);
        //For Pagination purpose
        recyclerViewCommon.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dx == 0 && dy == 0) {
                    return;
                }
                if (!recyclerView.canScrollVertically(1)) {
                    if (dx == 0 && dy == 0) {
                        return;
                    }
                    if (!recyclerView.canScrollVertically(1)) {
                        if (profileInfoList != null && profileInfoList.size() > 0 && !stopLoading) {
                            fetchDataFromServer(true);
                        }
                    }
                }
            }
        });
        return view;
    }

    @Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
        if (condition.equals(Constants.LIST_DATA)) {
            isLoadMoreApiRunning = false;
            try {
                Type type = new TypeToken<List<ProfileInfo>>() {}.getType();
                if (profileAdapter == null) {
                    JSONObject jsonObject = new JSONObject(new Gson().toJson(apiResponseModel.data));
                    profileInfoList = new Gson().fromJson(jsonObject.optJSONArray("dataList").toString(), type);
                    // totalListCount = jsonObject.optInt("count");
                    if (jsonObject.has("blockedCount")) {
                        totalBlockListCount = jsonObject.optInt("blockedCount");
                    }
                    totalFanListCount = jsonObject.optInt("fanCount");
                    totalFollowListCount = jsonObject.optInt("followerCount");
                    stopLoading = false;

                    setTabsList(totalFanListCount, totalFollowListCount, totalBlockListCount);
                    if (profileInfoList.size() > 0) {
                        recyclerViewCommon.setAdapter(profileAdapter = new ProfileAdapter(ProfilesListFragment.this, getContext(),
                                profileInfoList, tabName, false, profileId, profileStatus));
                    } else {
                        nodataList(recyclerViewCommon, "", "", R.drawable.ic_nodata);
                    }
                } else {
                    JSONObject jsonObject = new JSONObject(new Gson().toJson(apiResponseModel.data));
                    ArrayList<ProfileInfo> profileInfoListTemp = new Gson().fromJson(jsonObject.optJSONArray("dataList").toString(), type);
                    if (profileInfoListTemp.size() < Integer.parseInt(Constants.ProfilesConstants.LIMIT_COUNT)) {
                        stopLoading = true;
                    }
                    if (profileInfoListTemp.size() > 0) {
                        profileInfoList.addAll(profileInfoListTemp);
                        profileAdapter.loadmore(profileInfoList);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
        if (condition.equals(Constants.LIST_DATA)) {
            isLoadMoreApiRunning = false;
            CommonRecycler.setNoInternetOrServerDown(activity(), recyclerViewCommon, false);
        }
    }

    private void setSkelltonAdapter() {
        recyclerViewCommon.setAdapter(new MyPostAdapter(RController.LOADING));
    }

    private void setTabsList(int totalFanCount, int totalFollowListCount, int totalBlockListCount) {
        switch (tabName) {
            case Constants.ProfilesConstants.FAN_TAB:
                myCelebTabsCountUpdate(totalFanCount, totalFollowListCount);
                if (totalFanCount == 0) {
                    nodataList(recyclerViewCommon, "", "", R.drawable.ic_nodata);
                }
                break;
            case Constants.ProfilesConstants.FOLLOWING_TAB:
                myCelebTabsCountUpdate(totalFanCount, totalFollowListCount);
                if (totalFollowListCount == 0) {
                    nodataList(recyclerViewCommon, "", "", R.drawable.ic_nodata);
                }
                break;
            case Constants.ProfilesConstants.FANS_OF_TAB:
                myFansFollowersTabsCountUpdate(totalFanCount, totalFollowListCount, totalBlockListCount);
                if (totalFanCount == 0) {
                    nodataList(recyclerViewCommon, "", "", R.drawable.ic_nodata);
                }
                break;
            case Constants.ProfilesConstants.FOLLOWERS_TAB:
                myFansFollowersTabsCountUpdate(totalFanCount, totalFollowListCount, totalBlockListCount);
                if (totalFollowListCount == 0) {
                    nodataList(recyclerViewCommon, "", "", R.drawable.ic_nodata);
                }
                break;
            case Constants.ProfilesConstants.BLOCKS_TAB:
                myFansFollowersTabsCountUpdate(totalFanCount, totalFollowListCount, totalBlockListCount);
                if (totalBlockListCount == 0) {
                    nodataList(recyclerViewCommon, "", "", R.drawable.ic_nodata);
                }
                break;
        }
    }

    private void myFansFollowersTabsCountUpdate(int totalfanCount, int totalFollowListCount, int totalBlockListCount) {
        try {
            if (totalfanCount > 0) {
                MyFanFollowersFragment.tabLayout.getTabAt(0).setText(Constants.ProfilesConstants.FANS_OF_TAB
                        + " " + "(" + totalfanCount + ")");
            } else {
                MyFanFollowersFragment.tabLayout.getTabAt(0).setText(Constants.ProfilesConstants.FANS_OF_TAB);
            }
            if (totalFollowListCount > 0) {
                MyFanFollowersFragment.tabLayout.getTabAt(1).setText(Constants.ProfilesConstants.FOLLOWERS_TAB + " " + "(" + totalFollowListCount + ")");
            } else {
                MyFanFollowersFragment.tabLayout.getTabAt(1).setText(Constants.ProfilesConstants.FOLLOWERS_TAB);
            }
            if (totalBlockListCount > 0) {
                MyFanFollowersFragment.tabLayout.getTabAt(2).setText(Constants.ProfilesConstants.BLOCKS_TAB + " " + "(" + totalBlockListCount + ")");
            } else {
                MyFanFollowersFragment.tabLayout.getTabAt(2).setText(Constants.ProfilesConstants.BLOCKS_TAB);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void myCelebTabsCountUpdate(int totalfanCount, int totalFollowListCount) {
        try {
            if (totalfanCount > 0) {
                MyCelebritiesfragment.tabLayout.getTabAt(0).setText(Constants.ProfilesConstants.FAN_TAB + " " + "(" + totalfanCount + ")");
            } else {
                MyCelebritiesfragment.tabLayout.getTabAt(0).setText(Constants.ProfilesConstants.FAN_TAB);
            }
            if (totalFollowListCount > 0) {
                MyCelebritiesfragment.tabLayout.getTabAt(1).setText(Constants.ProfilesConstants.FOLLOWING_TAB + " " + "(" + totalFollowListCount + ")");
            } else {
                MyCelebritiesfragment.tabLayout.getTabAt(1).setText(Constants.ProfilesConstants.FOLLOWING_TAB);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void setSkelltonView(RecyclerView recyclerViewCommon, boolean firstTime) {
        CommonRecycler.setSkelltonView(activity(), recyclerViewCommon, true, firstTime);
    }

    public void setEmptyDataList() {
        nodataList(recyclerViewCommon, "", "", R.drawable.ic_nodata);
    }

    @Override
    public void nodataList(RecyclerView recyclerViewCommon, String title, String subTitle, int imageResource) {
        switch (tabName) {
            case Constants.ProfilesConstants.FAN_TAB:
                recyclerViewCommon.setAdapter(new EmptyDataAdapter(activity(), Constants.YOU_NOT_FAN, Constants.FAN_YOUR_CELEB, R.drawable.ic_fan_your_favourite_celebrity, 5));
                break;
            case Constants.ProfilesConstants.FOLLOWING_TAB:
                recyclerViewCommon.setAdapter(new EmptyDataAdapter(activity(), Constants.YOU_NOT_FOLLOW, Constants.YOU_NOT_FOLLOW_CELEB, R.drawable.ic_start_browsing, 5));
                break;
            case Constants.ProfilesConstants.FANS_OF_TAB:
                recyclerViewCommon.setAdapter(new EmptyDataAdapter(activity(), Constants.NO_FANS, "", R.drawable.ic_fan_your_favourite_celebrity, 5));
                break;
            case Constants.ProfilesConstants.FOLLOWERS_TAB:
                recyclerViewCommon.setAdapter(new EmptyDataAdapter(activity(), Constants.NO_FOLLOWERS, "", R.drawable.ic_start_browsing, 5));
                break;
            case Constants.ProfilesConstants.BLOCKS_TAB:
                recyclerViewCommon.setAdapter(new EmptyDataAdapter(activity(), Constants.NO_BLOCKS, "", R.drawable.ic_start_browsing, 5));
                break;
        }
    }

    @Override
    public boolean checkInterConnection(RecyclerView recyclerViewCommon) {
        if (Common.checkInternetConnection(getActivity())) {
            return true;
        } else {
            CommonRecycler.setNoInternetOrServerDown(activity(), recyclerViewCommon, true);
            return false;
        }
    }

    @Override
    public void showSnackBar(String snackBarText, int type) {
        Utility.showSnackBar(activity(), coordinator_layout, snackBarText, type);
    }

    public void refreshData() {
        profileAdapter = null;
        profileInfoList = new ArrayList<>();
        fetchDataFromServer(false);
    }

    @Override
    public void fetchDataFromServer(boolean isLoadMore) {
        if (isLoadMoreApiRunning) {
            return;
        }
        IApiListener iApiListener = this;
        Call<ApiResponseModel> call;
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        if (!isLoadMore) {
            profileAdapter = null;
            call = apiInterface.GET(getTabBasedUrl(false));
        } else {
            call = apiInterface.GET(getTabBasedUrl(isLoadMore));
        }
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, Constants.LIST_DATA, false, iApiListener, false));
        isLoadMoreApiRunning = true;
    }

    private Activity activity(){
        if(getActivity() == null){
            return AppController.getInstance().getCurrentRegisteredActivity();
        } else {
            return getActivity();
        }
    }

    private String getTabBasedUrl(boolean isloardMore) {
        String dataFetchUrl = null;
        switch (tabName) {
            //Below Two are Fans & Followers
            case Constants.ProfilesConstants.FAN_TAB:
                if (!isloardMore) {
                    dataFetchUrl = Constants.ProfilesConstants.FAN_LIST + profileId + "/null/" + Constants.ProfilesConstants.LIMIT_COUNT;
                } else {
                    dataFetchUrl = Constants.ProfilesConstants.FAN_LIST + profileId + "/" + profileInfoList.get(profileInfoList.size() - 1).createdAt + "/" + Constants.ProfilesConstants.LIMIT_COUNT;
                }
                break;
            case Constants.ProfilesConstants.FOLLOWING_TAB:
                if (!isloardMore) {
                    dataFetchUrl = Constants.ProfilesConstants.FOLLOWING_LIST + profileId + "/null/" + Constants.ProfilesConstants.LIMIT_COUNT;
                } else {
                    dataFetchUrl = Constants.ProfilesConstants.FOLLOWING_LIST + profileId + "/" + profileInfoList.get(profileInfoList.size() - 1).createdAt + "/" + Constants.ProfilesConstants.LIMIT_COUNT;
                }
                break;

            //Below Two are Fans & Followers
            case Constants.ProfilesConstants.FANS_OF_TAB:
                if (!isloardMore) {
                    dataFetchUrl = Constants.ProfilesConstants.FANSOF_LIST + profileId + "/null/" + Constants.ProfilesConstants.LIMIT_COUNT;
                } else {
                    dataFetchUrl = Constants.ProfilesConstants.FANSOF_LIST + profileId + "/" + profileInfoList.get(profileInfoList.size() - 1).createdAt + "/" + Constants.ProfilesConstants.LIMIT_COUNT;
                }
                break;
            case Constants.ProfilesConstants.FOLLOWERS_TAB:
                if (!isloardMore) {
                    dataFetchUrl = Constants.ProfilesConstants.FOLLOWERS_LIST + profileId + "/null/" + Constants.ProfilesConstants.LIMIT_COUNT;
                } else {
                    dataFetchUrl = Constants.ProfilesConstants.FOLLOWERS_LIST + profileId + "/" + profileInfoList.get(profileInfoList.size() - 1).createdAt + "/" + Constants.ProfilesConstants.LIMIT_COUNT;
                }
                break;
            case Constants.ProfilesConstants.BLOCKS_TAB:
                if (!isloardMore) {
                    dataFetchUrl = Constants.ProfilesConstants.BLOCKS_LIST + profileId + "/0";
                } else {
                    dataFetchUrl = Constants.ProfilesConstants.BLOCKS_LIST + profileId + "/" + profileInfoList.get(profileInfoList.size() - 1).createdAt + "/";
                }
                break;
        }
        return dataFetchUrl;
    }

    public void updateTabTotalCount() {
        switch (tabName) {
            case Constants.ProfilesConstants.FAN_TAB:
            case Constants.ProfilesConstants.FANS_OF_TAB:
                totalFanListCount = totalFanListCount - 1;
                setTabsList(totalFanListCount, totalFollowListCount, totalBlockListCount);
                break;
            case Constants.ProfilesConstants.FOLLOWING_TAB:
            case Constants.ProfilesConstants.FOLLOWERS_TAB:
                totalFollowListCount = totalFollowListCount - 1;
                setTabsList(totalFanListCount, totalFollowListCount, totalBlockListCount);
                break;
            case Constants.ProfilesConstants.BLOCKS_TAB:
                totalBlockListCount = totalBlockListCount - 1;
                setTabsList(totalFanListCount, totalFollowListCount, totalBlockListCount);
                break;
        }
    }

    public void deleteListItem(final int positionValue) {
        switch (tabName) {
            case Constants.ProfilesConstants.FAN_TAB:
                profileInfoList.remove(positionValue);
                profileAdapter.notifyDataSetChanged();
                setTabsList(totalFanListCount, totalFollowListCount, totalBlockListCount);
                break;
            case Constants.ProfilesConstants.FOLLOWING_TAB:
            case Constants.ProfilesConstants.FOLLOWERS_TAB:
                totalFollowListCount = totalFollowListCount - 1;
                profileInfoList.remove(positionValue);
                profileAdapter.notifyDataSetChanged();
                setTabsList(totalFanListCount, totalFollowListCount, totalBlockListCount);
                break;
            case Constants.ProfilesConstants.FANS_OF_TAB:
                totalFanListCount = totalFanListCount - 1;
                profileInfoList.remove(positionValue);
                profileAdapter.notifyDataSetChanged();
                setTabsList(totalFanListCount, totalFollowListCount, totalBlockListCount);
                break;
            case Constants.ProfilesConstants.BLOCKS_TAB:
                totalBlockListCount = totalBlockListCount - 1;
                profileInfoList.remove(positionValue);
                profileAdapter.notifyDataSetChanged();
                setTabsList(totalFanListCount, totalFollowListCount, totalBlockListCount);
                break;
        }
        if(profileInfoList.size() <= 0){
            if(!stopLoading){
                setSkelltonAdapter();
                fetchDataFromServer(false);
            } else {
                nodataList(recyclerViewCommon, "", "", R.drawable.ic_nodata);
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisibleToUser_ = true;
            instance = this;
            if(profileInfoList == null || profileInfoList.size() <= 0){
                if ((celebProfilePage && !tabName.equals(visibleTabname)) || (celebritiesItem && !tabName.equals(Constants.ProfilesConstants.FAN_TAB))|| (!tabName.equals(Constants.ProfilesConstants.FANS_OF_TAB))) {
                    fetchDataFromServer(false);
                }
            }
        }
    }
}
