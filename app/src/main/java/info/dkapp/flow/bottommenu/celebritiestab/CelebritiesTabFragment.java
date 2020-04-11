package info.dkapp.flow.bottommenu.celebritiestab;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import info.dkapp.flow.R;
import info.dkapp.flow.appmanagers.feed.models.Celebrity;
import info.dkapp.flow.bottommenu.activity.HelperActivity;
import info.dkapp.flow.bottommenu.constants.RController;
import info.dkapp.flow.bottommenu.generic.EmptyDataNewAdapter;
import info.dkapp.flow.celebflow.constants.Constants;
import info.dkapp.flow.retrofitcall.*;
import info.dkapp.flow.userflow.Util.Common;
import info.dkapp.flow.utils.CommonSkeletonAdapter;
import info.dkapp.flow.utils.RecyclerUtil.CommonRecycler;
import info.dkapp.flow.utils.RecyclerUtil.IRecyclerViewCommon;
import info.dkapp.flow.utils.Utility;
import retrofit2.Call;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Shiva on 8/13/2018.
 */

public class CelebritiesTabFragment extends Fragment implements IApiListener, IRecyclerViewCommon, View.OnClickListener {

    public static CelebritiesTabFragment instance = null;

    @BindView(R.id.recyclerViewOnline)
    RecyclerView recyclerViewOnline;

    @BindView(R.id.recyclerViewTrending)
    RecyclerView recyclerViewTrending;

    @BindView(R.id.recyclerViewEditor)
    public DiscreteScrollView recyclerViewEditor;

    @BindView(R.id.recyclerViewRecommended)
    RecyclerView recyclerViewRecommended;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.otherLayout)
    LinearLayout otherLayout;

    @BindView(R.id.eSearchBarNew)
    EditText eSearchBarNew;

    @BindView(R.id.viewallonlinetxt)
    TextView viewallonlinetxt;

    @BindView(R.id.nowonlinetxt)
    TextView nowonlinetxt;

    @BindView(R.id.onlineLayout)
    LinearLayout onlineLayout;

    CelebProfilesCKAdapter onlineCelebrityViewAllAdapter;
    CelebProfilesCKAdapter trendingAdapter;
    CelebProfilesCKAdapter editorsAdapter;
    CelebProfilesCKAdapter recommendedAdapter;
    CelebritiesList celebritiesList = null;


    public static CelebritiesTabFragment newInstance() {
        CelebritiesTabFragment fragment = new CelebritiesTabFragment();
        return fragment;
    }

    public static CelebritiesTabFragment getInstance() {
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.celebrities_tab_ck, container, false);
        ButterKnife.bind(this, view);
        initializeViews();
        Common.getInstance().setCelebritiesTabFragment(this);
        setLayoutMargin();
        updateOnlineCelebAdapter(Common.getInstance().getOnlineCelebList());
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
    }

    private void initializeViews() {
        recyclerViewOnline.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
            fetchDataFromServer(true);
        });
        eSearchBarNew.setOnClickListener(this::onClick);
        viewallonlinetxt.setOnClickListener(this::onClick);
    }

    @Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
        if (condition.equals(ApiClient.GET_ALL_CELEBS)) {
            otherLayout.setVisibility(View.VISIBLE);
            Type type = new TypeToken<CelebritiesList>() {}.getType();
            celebritiesList = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
            if (celebritiesList != null) {
                if (celebritiesList.trendingCelebrities.size() > 0) {
                    recyclerViewTrending.setAdapter(trendingAdapter = new CelebProfilesCKAdapter(getActivity(),
                            celebritiesList.trendingCelebrities, getResources().getString(R.string.trending_celeb)));
                } else {
                    nodataList(recyclerViewTrending, Constants.WHOOPS, Constants.NO_DATA_AVAILABLE, R.drawable.ic_nodata);
                }
                if (celebritiesList.editorChoiceCelebrities.size() > 0) {
                    recyclerViewEditor.setOrientation(DSVOrientation.HORIZONTAL);
                    recyclerViewEditor.setAdapter(editorsAdapter = new CelebProfilesCKAdapter(getActivity(),
                            celebritiesList.editorChoiceCelebrities, getResources().getString(R.string.editor_celeb)));
                    recyclerViewEditor.getCurrentItem();
                    recyclerViewEditor.setItemTransitionTimeMillis(2400);
                    recyclerViewEditor.setSlideOnFling(true);
                    recyclerViewEditor.setSlideOnFlingThreshold(150);
                    recyclerViewEditor.setItemTransformer(new ScaleTransformer.Builder().setMinScale(0.8f).build());

//                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewEditor.getContext(),
//                    DividerItemDecoration.HORIZONTAL);
//                    recyclerViewEditor.addItemDecoration(dividerItemDecoration);
                }
                if (celebritiesList.recommendedCelebrities.size() > 0) {
                    recyclerViewRecommended.setAdapter(recommendedAdapter = new CelebProfilesCKAdapter(getActivity(),
                            celebritiesList.recommendedCelebrities, getResources().getString(R.string.recomended_celeb)));
                } else {
                    nodataList(recyclerViewRecommended, Constants.WHOOPS, Constants.NO_DATA_AVAILABLE, R.drawable.ic_nodata);
                }
            } else {
                nodataList(recyclerViewTrending, Constants.WHOOPS, Constants.NO_CELEBS, R.drawable.ic_nodata);
                nodataList(recyclerViewRecommended, Constants.WHOOPS, Constants.NO_CELEBS, R.drawable.ic_nodata);
            }
        }
    }

    private void setonlineCount(List<Celebrity> onlineCelebList) {
        try {
            nowonlinetxt.setVisibility(View.GONE);
            nowonlinetxt.setText("Now Online");
            viewallonlinetxt.setVisibility(View.GONE);
            if (onlineCelebList != null && onlineCelebList.size() > 0) {
                nowonlinetxt.setVisibility(View.VISIBLE);
                if (onlineCelebList.size() > 4) {
                    nowonlinetxt.setText("Now Online (" + String.valueOf(onlineCelebList.size()) + ")");
                    viewallonlinetxt.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
        if (condition.equals(ApiClient.GET_ALL_CELEBS)) {
            CommonRecycler.setNoInternetOrServerDown(getActivity(), recyclerViewTrending, false);
            CommonRecycler.setNoInternetOrServerDown(getActivity(), recyclerViewRecommended, false);
        }
    }

    private void setLayoutMargin() {
        LinearLayout.LayoutParams layoutParamsLeft = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsLeft.setMargins(-((Utility.getScreenWidth(Utility.getContext()) / 15) * 8), 0, 0, 0);
        recyclerViewEditor.setLayoutParams(layoutParamsLeft);
    }


    @Override
    public void setSkelltonView(RecyclerView recyclerView, boolean firstTime) {
        CommonRecycler.setSkelltonViewOther(getActivity(), recyclerView, false, firstTime, true);
        recyclerViewEditor.setAdapter(new CommonSkeletonAdapter(RController.LOADING, true));
    }

    @Override
    public void nodataList(RecyclerView recyclerView, String title, String subTitle, int imageResource) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new EmptyDataNewAdapter(getActivity(), title, subTitle, imageResource));
    }

    @Override
    public boolean checkInterConnection(RecyclerView recyclerView) {
        if (Common.checkInternetConnection(getActivity())) {
            return true;
        } else {
            /*nowonlinetxt.setText("Now Online");
            nowonlinetxt.setVisibility(View.GONE);
            viewallonlinetxt.setVisibility(View.GONE);
            otherLayout.setVisibility(View.GONE);
            recyclerViewOnline.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            CommonRecycler.setNoInternetOrServerDown(getActivity(), recyclerViewOnline, false);*/
            return false;
        }
    }

    @Override
    public void showSnackBar(String snackBarText, int type) {

    }

    @Override
    public void fetchDataFromServer(boolean firstTime) {
        setSkelltonView(recyclerViewTrending, firstTime);
        setSkelltonView(recyclerViewRecommended, firstTime);
        //
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.getAllCelebritiesList(ApiClient.GET_ALL_CELEBS + Common.isLoginId());
        Common.getInstance().callAPI(new ApiRequestModel().setModel(getActivity(), call, ApiClient.GET_ALL_CELEBS, false, this, false));
    }


    public void updateOnlineCelebAdapter(List<Celebrity> onlineCelebList) {
        if (onlineCelebList != null && onlineCelebList.size() > 0) {
            onlineLayout.setVisibility(View.VISIBLE);
            setonlineCount(Common.getInstance().getOnlineCelebList());
            if(onlineCelebrityViewAllAdapter == null) {
                recyclerViewOnline.setAdapter(onlineCelebrityViewAllAdapter = new CelebProfilesCKAdapter(getActivity(), Common.getInstance().getOnlineCelebList(), getResources().getString(R.string.nowonline)));
            } else {
                onlineCelebrityViewAllAdapter.updateOnlineCelebrityList(onlineCelebList);
                onlineCelebrityViewAllAdapter.notifyDataSetChanged();
            }
        } else {
            onlineLayout.setVisibility(View.GONE);
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            updateOnlineCelebAdapter(Common.getInstance().getOnlineCelebList());
            if(celebritiesList == null) {
                fetchDataFromServer(true);
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.eSearchBarNew:
                Intent intent = new Intent(getActivity(), HelperActivity.class);
                intent.putExtra(Constants.FRAGMENT_KEY, 8001);
                startActivity(intent);
                break;
            case R.id.viewallonlinetxt:
                Intent intentt = new Intent(getActivity(), HelperActivity.class);
                intentt.putExtra(Constants.FRAGMENT_TITLE, "Now Online");
                intentt.putExtra(Constants.FRAGMENT_KEY, 8065);
                getActivity().startActivity(intentt);
                break;
        }

    }


}
