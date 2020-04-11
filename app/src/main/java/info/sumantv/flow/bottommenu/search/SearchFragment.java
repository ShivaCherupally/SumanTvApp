package info.sumantv.flow.bottommenu.search;


import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.*;
import android.widget.*;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import info.sumantv.flow.bottommenu.activity.HelperActivity;
import info.sumantv.flow.bottommenu.constants.RController;
import info.sumantv.flow.bottommenu.generic.EmptyDataAdapter;
import info.sumantv.flow.bottommenu.interfaces.fragments.IFragment;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.retrofitcall.*;
import info.sumantv.flow.userflow.Util.Common;
import info.sumantv.flow.utils.RecyclerUtil.CommonRecycler;
import info.sumantv.flow.utils.RecyclerUtil.IRecyclerViewCommon;
import info.sumantv.flow.utils.Utility;
import info.sumantv.flow.utils.UtilityNew;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import org.json.JSONObject;

import retrofit2.Call;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements IFragment, ISearchAdapter, IApiListener, IRecyclerViewCommon, View.OnClickListener {

    private LinearLayoutManager linearLayoutManager;

    private List<Search> searchList;

    private SearchAdapter searchAdapter;
    private SearchCelebAdapter searchCelebAdapter;

    private ISearchAdapter iSearchAdapter;

    IApiListener iApiListener;

    boolean isLoadMoreApiRunning = false, stopLoading = false;
    int LIMIT_COUNT = 20;

    @BindView(R.id.backimg)
    ImageView backimg;

    @BindView(R.id.recyclerCelebs)
    RecyclerView recyclerCelebs;

    @BindView(R.id.iClearText)
    ImageView iClearText;

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.etSearch)
    EditText etSearch;

    @BindView(R.id.recentLayout)
    LinearLayout recentLayout;

    @BindView(R.id.recyclerhistoryCelebs)
    RecyclerView recyclerhistoryCelebs;

    @BindView(R.id.clearAll)
    TextView clearAll;


    boolean loadMore = false;

    private List<SearchCelebItemModel> searchCelebItemModel = new ArrayList<>();


    int positionGlobal = 0;
    boolean redirectProfilePage = false;

    String paginationDate = "";


    public SearchFragment() {
    }

    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iApiListener = this;
        iSearchAdapter = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_celeb, container, false);

        ButterKnife.bind(this, view);

        setUp(view);

        backimg.setOnClickListener(this::onClick);
        iClearText.setOnClickListener(this::onClick);
        clearAll.setOnClickListener(this::onClick);


        searchCelebEmpty(Constants.SEARCH_CELEB, "");

        fetchDataFromServer(true);

        return view;
    }

    private void setUp(View view) {

        linearLayoutManager = new LinearLayoutManager(activity(), LinearLayoutManager.VERTICAL, false);

        recyclerCelebs.setLayoutManager(linearLayoutManager);

        progressBar.setVisibility(View.GONE);
        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.dark_gray), android.graphics.PorterDuff.Mode.SRC_IN);


        etSearch.requestFocus();
        if (etSearch.requestFocus()) {
            ((HelperActivity) getContext()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.toString().length() > 0) {
                    iClearText.setVisibility(View.VISIBLE);
//                    Toast.makeText(getActivity(), "key " + editable.toString().length(), Toast.LENGTH_SHORT).show();
                    performSearch(false);
                } else {
                    iClearText.setVisibility(View.GONE);
                    if (searchList != null && searchList.size() > 0) {
                        recentLayout.setVisibility(View.GONE);
                        searchList.clear();
                        if (searchAdapter != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    searchAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                        searchCelebEmpty(Constants.SEARCH_CELEB, "");

                        progressBar.setVisibility(View.GONE);
                    } else {
                        searchCelebEmpty(Constants.SEARCH_CELEB, "");
                    }
                }
            }
        });

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return false;
            }
        });

        recyclerCelebs.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                    if (etSearch.getText().toString().length() != 0) {
                       /* if (searchList != null && searchList.size() > 0 && !stopLoading) {
                            performSearch(true);
                        }*/
                        if (searchList != null && searchList.size() > 0 && !stopLoading) {
                            performSearch(true);
                        }

                    }
                }
            }
        });

        recyclerhistoryCelebs.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                    if (searchCelebItemModel != null && searchCelebItemModel.size() > 0 && !stopLoading) {
                        fetchDataFromServer(false);
                    }
                }
            }
        });
    }


    private void performSearch(Boolean isLoadmore) {

//        if (isLoadMoreApiRunning) {
//            return;
//        }
        progressBar.setVisibility(View.VISIBLE);
        Call<ApiResponseModel> call = null;
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        if (!isLoadmore) {
            if (Common.getInstance().checkInterConnection(recyclerCelebs, activity())) {
                searchAdapter = null;
                searchList = new ArrayList<>();

                String url = Constants.SearchConstants.SEARCH_CELEB + Common.isLoginId() + "/" + etSearch.getText().toString() + "/" + "0";
//                String url = Constants.SearchConstants.SEARCH_CELEB_NEW + Common.isLoginId()
//                        + "/" + etSearch.getText().toString() + "/0";
                call = apiInterface.GET(url);
            }
        } else {
            if (Common.checkInternetConnection(activity())) {
//                String url = Constants.SearchConstants.SEARCH_CELEB + Common.isLoginId() + "/" + etSearch.getText().toString()
//                        + "/" + searchList.get(searchList.size() - 1).getCreatedAt() + "/" + LIMIT_COUNT;

                String url = Constants.SearchConstants.SEARCH_CELEB + Common.isLoginId() + "/" + etSearch.getText().toString()
                        + "/" + paginationDate;

//                String url = Constants.SearchConstants.SEARCH_CELEB_NEW + Common.isLoginId()
//                        + "/" + etSearch.getText().toString() + "/0";
                call = apiInterface.GET(url);
            } else {
                showSnackBar(Constants.PLEASE_CHECK_INTERNET, 5);
                return;
            }
        }
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, Constants.SearchConstants.SEARCH_CELEB, false, iApiListener, false));
        isLoadMoreApiRunning = isLoadmore;
    }


    @Override
    public void changeTitle(String title) {
        ((HelperActivity) activity()).changeTitle(title);
    }

    @Override
    public void setSkelltonView(RecyclerView recyclerView, boolean firstTime) {
        CommonRecycler.setSkelltonView(getActivity(), recyclerView, true, firstTime);
    }

    @Override
    public void nodataList(RecyclerView recyclerView, String title, String subTitle, int imageResource) {

    }

    @Override
    public boolean checkInterConnection(RecyclerView recyclerView) {
        if (Common.checkInternetConnection(getActivity())) {
            return true;
        } else {
            CommonRecycler.setNoInternetOrServerDown(getActivity(), recyclerView, false);
            return false;
        }
    }

    @Override
    public void showSnackBar(String snackBarText, int type) {
        UtilityNew.showSnackBar(activity(), coordinatorLayout, snackBarText, type);
    }

    @Override
    public void fetchDataFromServer(boolean firstTime) {
        recyclerCelebs.setVisibility(View.GONE);
        Call<ApiResponseModel> call = null;
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        setSkelltonView(recyclerhistoryCelebs, firstTime);
        Log.e("firstTime", firstTime + "");

        progressBar.setVisibility(View.VISIBLE);

        if (firstTime) {
            loadMore = false;
            if (checkInterConnection(recyclerhistoryCelebs)) {
                call = apiInterface.GET(Constants.SearchConstants.GET_ALL_HISTORY + Common.isLoginId() + "/0/" + LIMIT_COUNT);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        } else {
            loadMore = true;
            if (Common.checkInternetConnection(getActivity())) {
                String url = Constants.SearchConstants.GET_ALL_HISTORY + Common.isLoginId() + "/" +
                        searchCelebItemModel.get(searchCelebItemModel.size() - 1).createdAt
                        + "/" + LIMIT_COUNT;
                call = apiInterface.GET(url);
            } else {
                progressBar.setVisibility(View.GONE);
                showSnackBar(Constants.PLEASE_CHECK_INTERNET, 5);
            }
        }
        if (call != null) {
            Common.getInstance().callAPI(new ApiRequestModel().setModel(getActivity(), call, Constants.SearchConstants.GET_ALL_HISTORY,
                    false, iApiListener, false));
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public Activity activity() {
        return getActivity();
    }

    @Override
    public void navigateTOProfile(int position) {

        redirectProfilePage = true;

        if (searchCelebItemModel != null) {

            if (!SessionManager.getInstance().getKeyValue(SessionManager.KEY_PAUSE_SEARCH_HISTORY, false)) {
                SearchCelebItemModel singlesearchitem = new SearchCelebItemModel();

                if (searchList.get(position).getId() != null && !searchList.get(position).getId().isEmpty()) {
                    singlesearchitem.setCelebrityId(searchList.get(position).getId());
                    if (searchList.get(position).getFirstName() != null && !searchList.get(position).getFirstName().isEmpty()) {
                        singlesearchitem.setFirstName(searchList.get(position).getFirstName());
                    } else {
                        singlesearchitem.setFirstName("");
                    }

                    if (searchList.get(position).getLastName() != null && !searchList.get(position).getLastName().isEmpty()) {
                        singlesearchitem.setLastName(searchList.get(position).getLastName());
                    } else {
                        singlesearchitem.setLastName("");
                    }

                    if (searchList.get(position).getAvtarImgPath() != null && !searchList.get(position).getAvtarImgPath().isEmpty()) {
                        singlesearchitem.setAvtar_imgPath(searchList.get(position).getAvtarImgPath());
                    } else {
                        singlesearchitem.setAvtar_imgPath("");
                    }

                    if (searchList.get(position).getProfession() != null && !searchList.get(position).getProfession().isEmpty()) {
                        singlesearchitem.setProfession(searchList.get(position).getProfession());
                    } else {
                        singlesearchitem.setProfession("");
                    }

                    if (searchCelebItemModel.size() == 0) {
                        searchCelebItemModel.add(singlesearchitem);
                    } else {
                        boolean celebExist = false;

                        for (int i = 0; i < searchCelebItemModel.size(); i++) {
                            if (searchCelebItemModel.get(i).getCelebrityId().equals(searchList.get(position).getId())) {
                                Log.e("SameCeleb", true + "");
                                celebExist = true;
                                break;
                            } else {
                                Log.e("SameCeleb", false + "");
                                celebExist = false;
                            }
                        }

                        if (!celebExist) {
                            Log.e("AddList", true + "");
                            searchCelebItemModel.add(0, singlesearchitem);
                        }
                    }
                }
            }
        }
        Common.getInstance().openProfileScreen(activity(), searchList.get(position).getId());
    }

    @Override
    public void deleteIndividualSearch(int position) {

        if (etSearch.requestFocus()) {
            ((HelperActivity) getContext()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        }

        positionGlobal = position;
        if (searchCelebItemModel.get(position).celebrityId != null) {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<ApiResponseModel> call = apiInterface.DELETE(Constants.SearchConstants.DELETE_INDIVIDUAL_SEARCH
                    + Common.isLoginId() + "/" + searchCelebItemModel.get(position).celebrityId);
            Common.getInstance().callAPI(new ApiRequestModel().setModel(Utility.getContext(), call, Constants.SearchConstants.DELETE_INDIVIDUAL_SEARCH,
                    true, iApiListener, true));
        }

    }

    @Override
    public void saveSearchingCeleb(int position) {
        positionGlobal = position;
        if (SessionManager.getInstance().getKeyValue(SessionManager.KEY_PAUSE_SEARCH_HISTORY, false)) {
            navigateTOProfile(positionGlobal);
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("memberId", Common.isLoginId());
            jsonObject.put("celebrityId", searchList.get(position).getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        Call<ApiResponseModel> call = apiInterface.POST(Constants.SearchConstants.SAVE_SEARCH, requestBody);
        Common.getInstance().callAPI(new ApiRequestModel().setModel(getActivity(), call, Constants.SearchConstants.SAVE_SEARCH,
                true, iApiListener, true));
    }

    @Override
    public void clearAllCelebHistory() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.DELETE(Constants.SearchConstants.CLEAR_ALL_SEARCH + Common.isLoginId());
        Common.getInstance().callAPI(new ApiRequestModel().setModel(Utility.getContext(), call,
                Constants.SearchConstants.CLEAR_ALL_SEARCH, true, iApiListener, true));
    }

    @Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
        if (condition.equals(Constants.SearchConstants.SEARCH_CELEB)) {
            try {
                Type type = new TypeToken<SearchData>() {
                }.getType();

                recentLayout.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);

                SearchData searchData = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                if (searchData != null) {

                    List<Search> searchListComplete = searchData.celebSearchInfo;
                    paginationDate = searchData.paginationDate;

                    Log.e("listSize", searchListComplete.size() + "");
                    if (!isLoadMoreApiRunning) {

                        recyclerCelebs.setVisibility(View.VISIBLE);
                        recentLayout.setVisibility(View.GONE);

                        if (searchListComplete.size() > 0) {
                            if (etSearch.getText().toString().length() != 0) {

                                searchList = new ArrayList<>();
                                searchList = searchListComplete;

                                searchAdapter = new SearchAdapter(searchListComplete, activity(), RController.LOADED, iSearchAdapter);
                                recyclerCelebs.setAdapter(searchAdapter);
                            } else {
                                searchCelebEmpty(Constants.SEARCH_CELEB, "");
                            }
                        } else {
                            Log.e("nodataSearch", "yes");
                            searchCelebEmpty(Constants.SORRY, Constants.THERE_IS_NO_DATA);
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
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (condition.equals(Constants.SearchConstants.GET_ALL_HISTORY)) {
            try {
                Type type = new TypeToken<SearchCelebData>() {
                }.getType();
                if (searchCelebAdapter == null) {
                    progressBar.setVisibility(View.GONE);
                    SearchCelebData searchCelebData = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                    if (searchCelebData != null && searchCelebData.history.size() > 0) {

                        searchCelebItemModel.addAll(searchCelebData.history);

                        recyclerCelebs.setVisibility(View.GONE);
                        recentLayout.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);

                        Log.e("historySize", searchCelebData.history.size() + "");

                        recyclerhistoryCelebs.setAdapter(searchCelebAdapter = new SearchCelebAdapter(removeDuplicates(searchCelebItemModel), activity(), iSearchAdapter));
                    } else {
                        recentLayout.setVisibility(View.GONE);
                        recyclerCelebs.setVisibility(View.VISIBLE);
                    }
                } else {
                    progressBar.setVisibility(View.GONE);
                    SearchCelebData searchListTemp = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                    Log.e("historySizeMore", searchListTemp.history.size() + "");
                    if (searchListTemp.history.size() < LIMIT_COUNT) {
                        stopLoading = true;
                    }
                    if (searchListTemp.history.size() > 0) {
                        Log.e("historySizeMoreIn", searchListTemp.history.size() + "");
                        searchCelebItemModel.addAll(removeDuplicates(searchListTemp.history));
                        searchCelebAdapter.loadmore(removeDuplicates(searchCelebItemModel));
                    }


                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (condition.equals(Constants.SearchConstants.SAVE_SEARCH)) {
            navigateTOProfile(positionGlobal);
        } else if (condition.equals(Constants.SearchConstants.DELETE_INDIVIDUAL_SEARCH)) {
            if (apiResponseModel.message != null && !apiResponseModel.message.isEmpty()) {
                if (apiResponseModel.message.equals("Record has been successfully deleted")) {
                    searchCelebItemModel.remove(positionGlobal);
                    searchCelebAdapter.notifyDataSetChanged();

                    if (searchCelebItemModel.size() == 0) {
                        recentLayout.setVisibility(View.GONE);
                        recyclerCelebs.setVisibility(View.VISIBLE);
                        recyclerCelebs.setAdapter(new EmptyDataAdapter(activity(), Constants.SEARCH_CELEB,
                                "", R.drawable.ic_no_results_to_show, 6));
                    }
                }
            }
        } else if (condition.equals(Constants.SearchConstants.CLEAR_ALL_SEARCH)) {
            if (apiResponseModel.message != null && !apiResponseModel.message.isEmpty()) {
                if (apiResponseModel.message.equals("Records has been successfully deleted")) {
                    if (searchCelebItemModel != null && searchCelebItemModel.size() > 0) {
                        searchCelebItemModel.clear();
                        recyclerhistoryCelebs.removeAllViews();
                        searchCelebAdapter.notifyDataSetChanged();

                        recentLayout.setVisibility(View.GONE);

                        recyclerCelebs.setVisibility(View.VISIBLE);
                        recyclerCelebs.setAdapter(new EmptyDataAdapter(activity(), Constants.SEARCH_CELEB,
                                "", R.drawable.ic_no_results_to_show, 6));

                    }
                }
            }

        }
    }

    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
        if (condition.equals(Constants.SearchConstants.SEARCH_CELEB)) {
            isLoadMoreApiRunning = false;
            progressBar.setVisibility(View.GONE);
            recyclerCelebs.setAdapter(new EmptyDataAdapter(activity(), Constants.SORRY, Constants.SOMETHING_WENT_WRONG,
                    R.drawable.ic_no_results_to_show, 6));
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backimg:
                activity().finish();
                break;
            case R.id.iClearText:

                etSearch.setText("");

                searchCelebEmpty(Constants.SEARCH_CELEB, "");

                if (searchCelebItemModel.size() > 0) {
                    recyclerCelebs.setVisibility(View.GONE);
                    recentLayout.setVisibility(View.VISIBLE);
                    if (redirectProfilePage) {
                        recyclerhistoryCelebs.setAdapter(searchCelebAdapter = new SearchCelebAdapter(removeDuplicates(searchCelebItemModel), activity(), iSearchAdapter));
                    }
                }
                break;
            case R.id.clearAll:
                clearAllCelebHistory();
                break;
        }
    }


    private void searchCelebEmpty(String title, String description) {

        searchList = new ArrayList<>();
        if (searchAdapter != null) {
            searchAdapter.notifyDataSetChanged();
        }
        progressBar.setVisibility(View.GONE);
        recyclerCelebs.removeAllViews();
        recyclerCelebs.setVisibility(View.VISIBLE);
        recyclerCelebs.setAdapter(new EmptyDataAdapter(activity(), title, description, R.drawable.ic_no_results_to_show, 6));

//        if (SessionManager.getInstance().getKeyValue(SessionManager.KEY_PAUSE_SEARCH_HISTORY, false)) {
//            return;
//        } else {
        if (etSearch.getText().toString().length() == 0) {
            if (searchCelebItemModel.size() > 0) {
                recyclerCelebs.setVisibility(View.GONE);
                recentLayout.setVisibility(View.VISIBLE);
                if (redirectProfilePage) {
                    recyclerhistoryCelebs.setAdapter(searchCelebAdapter = new SearchCelebAdapter(removeDuplicates(searchCelebItemModel), activity(), iSearchAdapter));
                }
            }
        }
    }

    public List<SearchCelebItemModel> removeDuplicates(List<SearchCelebItemModel> list) {
        Log.e("beforeListsize", list.size() + "");
        searchCelebItemModel = new ArrayList<>();
        for (SearchCelebItemModel event : list) {
            boolean isFound = false;
            for (SearchCelebItemModel e : searchCelebItemModel) {
                if (e.getCelebrityId().equals(event.getCelebrityId()) || (e.equals(event))) {
                    isFound = true;
                    break;
                }
            }
            if (!isFound) searchCelebItemModel.add(event);
        }
        Log.e("afterListSize", searchCelebItemModel.size() + "");

        return searchCelebItemModel;
    }


}
