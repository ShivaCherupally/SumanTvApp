//package info.celebkonnect.flow.bottommenu.allcelebritiestab;
//
//import android.annotation.SuppressLint;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import com.google.android.material.tabs.TabLayout;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentStatePagerAdapter;
//import androidx.viewpager.widget.ViewPager;
//import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import info.celebkonnect.flow.bottommenu.activity.BottomMenuActivity;
//import info.celebkonnect.flow.celebflow.R;
//import info.celebkonnect.flow.retrofitcall.*;
//import info.celebkonnect.flow.userflow.Util.Common;
//import retrofit2.Call;
//
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by Shiva on 8/13/2018.
// */
//
//public class CelebrityBaseFragment extends Fragment implements IApiListener {
//    ApiInterface apiInterface;
//    IApiListener iApiListener;
//    SwipeRefreshLayout swipeRefreshLayout;
//    TabLayout tablayout_notification;
//    private ViewPager viewPager;
//    private ViewPagerAdapter mViewPagerAdapter;
//    public String[] tabTitles;
//    public boolean isCelebStatus;
//    List<CelebProfileCompleteData> celebProfileCompleteData = new ArrayList<CelebProfileCompleteData>();
//    LinearLayout netissueLayout;
//    public static CelebrityBaseFragment instance = null;
//
//    public static CelebrityBaseFragment newInstance() {
//        CelebrityBaseFragment fragment = new CelebrityBaseFragment();
//        return fragment;
//    }
//
//    public static CelebrityBaseFragment getInstance() {
//        return instance;
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.celebrities_fragment, container, false);
//        setRetainInstance(false);
//        initializeViews(view);
//        return view;
//    }
//
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        instance = this;
//        iApiListener = this;
//    }
//
//    private void initializeViews(View view) {
//        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
//        netissueLayout = view.findViewById(R.id.netissueLayout);
//        tablayout_notification = view.findViewById(R.id.tablayout_notification);
//
//        viewPager = view.findViewById(R.id.view_pager_v);
//
//        tabTitles = new String[]{getString(R.string.all_celeb_tab),
//                getString(R.string.trending_celeb),
//                getString(R.string.editor_celeb),
//                getString(R.string.recomended_celeb)};
//
//
//        getAllCelebritiesListFormServer();
//
//        tablayout_notification.setupWithViewPager(viewPager);
//        mViewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
//        viewPager.setAdapter(mViewPagerAdapter);
//        viewPager.setOffscreenPageLimit(4);
//
//        tablayout_notification.setTabGravity(TabLayout.GRAVITY_FILL);
//
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                tablayout_notification.getTabAt(position).select();
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//        swipeRefreshLayout.setOnRefreshListener(() -> {
//            swipeRefreshLayout.setRefreshing(false);
//            try {
//                getAllCelebritiesListFormServer();
//                ((BottomMenuActivity) getActivity()).searchClose();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//    }
//
//    public void sendSearchItem(String query) {
//        for (Fragment fragment : getChildFragmentManager().getFragments()) {
//            if (fragment instanceof CategoryCelebsFragment) {
//                CategoryCelebsFragment categoryCelebsFragment = (CategoryCelebsFragment) fragment;
//                categoryCelebsFragment.sendSearchItem(query);
//            }
//        }
//    }
//
//    public void changeGridView(String condition) {
//        for (Fragment fragment : getChildFragmentManager().getFragments()) {
//            if (fragment instanceof CategoryCelebsFragment) {
//                CategoryCelebsFragment categoryCelebsFragment = (CategoryCelebsFragment) fragment;
//                categoryCelebsFragment.threeGridView(condition);
//            }
//        }
//    }
//
//    @Override
//    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
//        if (condition.equals(ApiClient.ALL_CELEBS_NEW_URL)) {
//            try {
//                Type type = new TypeToken<List<CelebProfileCompleteData>>() {
//                }.getType();
//                List<CelebProfileCompleteData> celebProfileCompleteDataList = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
//                if (celebProfileCompleteDataList != null) {
//                    if (celebProfileCompleteDataList.size() != 0) {
//                        celebProfileCompleteData = celebProfileCompleteDataList;
//                    }
//                    mViewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
//                    viewPager.setAdapter(mViewPagerAdapter);
//                    viewPager.setOffscreenPageLimit(4);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @Override
//    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
//
//    }
//
//    public class ViewPagerAdapter extends FragmentStatePagerAdapter {
//        int tabCount;
//
//        public ViewPagerAdapter(FragmentManager fm) {
//            super(fm);
//            this.tabCount = 4;
//        }
//
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return tabTitles[position];
//        }
//
//
//        @Override
//        public Fragment getItem(int position) {
//            CategoryCelebsFragment fragment = null;
//            switch (position) {
//                case 0:
//                    fragment = CategoryCelebsFragment.newInstance(getString(R.string.all_celeb_tab),
//                            celebProfileCompleteData);
//                    return fragment;
//                case 1:
//                    fragment = CategoryCelebsFragment.newInstance(getString(R.string.trending_celeb),
//                            celebProfileCompleteData);
//                    return fragment;
//                case 2:
//                    fragment = CategoryCelebsFragment.newInstance(getString(R.string.editor_celeb),
//                            celebProfileCompleteData);
//                    return fragment;
//                case 3:
//                    fragment = CategoryCelebsFragment.newInstance(getString(R.string.recomended_celeb),
//                            celebProfileCompleteData);
//                    return fragment;
//                default:
//                    return null;
//            }
//        }
//
//        @Override
//        public int getCount() {
//            return tabTitles.length;
//        }
//    }
//
//
//    public List<CelebProfileCompleteData> getCelebProfileCompleteData() {
//        return celebProfileCompleteData;
//    }
//
//    public void getAllCelebritiesListFormServer() {
//
//        apiInterface = ApiClient.getClient().create(ApiInterface.class);
//        Call<ApiResponseModel> call = apiInterface.getAllCelebritiesList(ApiClient.ALL_CELEBS_NEW_URL
//                + SessionManager.userLogin.userId
//        );
//        Common.getInstance().callAPI(new ApiRequestModel().setModel(getActivity(), call, ApiClient.ALL_CELEBS_NEW_URL,
//                false, iApiListener, false));
//    }
//
//    public void updateFanFollowStatus(Boolean fanStatus, Boolean followStatus, String _id) {
//        new updateFanFollowStatus(fanStatus, followStatus, _id).execute();
//    }
//
//    @SuppressLint("StaticFieldLeak")
//    private class updateFanFollowStatus extends AsyncTask<String, Void, String> {
//        Boolean fanStatus, followStatus;
//        String _id;
//
//        public updateFanFollowStatus(Boolean fanStatus, Boolean followStatus, String _id) {
//            this.fanStatus = fanStatus;
//            this.followStatus = followStatus;
//            this._id = _id;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            try {
//                if (celebProfileCompleteData != null) {
//                    for (int i = 0; i < celebProfileCompleteData.size(); i++) {
//                        if (celebProfileCompleteData.get(i).get_id().equalsIgnoreCase(_id)) {
//                            if (fanStatus != null) {
//                                celebProfileCompleteData.get(i).setFan(fanStatus);
//                            }
//                            if (followStatus != null) {
//                                celebProfileCompleteData.get(i).setFollower(followStatus);
//                            }
//                            break;
//                        }
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//        }
//    }
//
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            try {
//                getAllCelebritiesListFormServer();
//                ((BottomMenuActivity) getActivity()).searchClose();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
