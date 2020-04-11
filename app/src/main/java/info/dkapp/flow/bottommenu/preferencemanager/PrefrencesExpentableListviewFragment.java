package info.dkapp.flow.bottommenu.preferencemanager;

import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import info.dkapp.flow.bottommenu.activity.HelperActivity;

import info.dkapp.flow.R;
import info.dkapp.flow.celebflow.constants.Constants;
import info.dkapp.flow.retrofitcall.*;
import info.dkapp.flow.userflow.Util.Common;
import info.dkapp.flow.utils.Logger;
import info.dkapp.flow.utils.UtilityNew;
import info.dkapp.flow.utils.expendableRecyclerviewNew.PreferenceNew;
import info.dkapp.flow.utils.expendableRecyclerviewNew.PreferenceNewCategory;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.json.JSONArray;
import org.json.JSONObject;
import retrofit2.Call;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PrefrencesExpentableListviewFragment extends Fragment implements IApiListener {
    ArrayList<PreferenceNewCategory> selectedListDataHeader = new ArrayList<>();
    ArrayList<String> categoryList = new ArrayList<>();
    ArrayList<PreferenceNewCategory> preferenceNewCategories = new ArrayList<PreferenceNewCategory>();
    ExpandableListAdapter_Preference preferenceAdapter;
    ExpandableListView expListView;
    DisplayMetrics metrics;
    int width;
    CoordinatorLayout coordinatorLayout;
    // ProgressDialog progressDialog;
    LinearLayout progressBarLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    ApiInterface apiInterface;
    IApiListener iApiListener;
    Button btnContinue;
    Boolean isFromRegister = false;
    RelativeLayout llEmpty;
    boolean isReachThree = false;
    ArrayList<Integer> forReachThreeList = new ArrayList<>();

    public PrefrencesExpentableListviewFragment() {
        // Required empty public constructor
    }

    public static PrefrencesExpentableListviewFragment newInstance(Boolean isFromRegister, String param2) {
        PrefrencesExpentableListviewFragment fragment = new PrefrencesExpentableListviewFragment();
        Bundle args = new Bundle();
        args.putBoolean("isFromRegister", isFromRegister);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iApiListener = this;
        if (getArguments() != null) {
            isFromRegister = getArguments().getBoolean("isFromRegister", false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_prefrences_expentable_listview, container, false);

        expListView = root.findViewById(R.id.lvExp);
//        expListView.setGroupIndicator(null);
        coordinatorLayout = root.findViewById(R.id.coordinatorLayout);
        progressBarLayout = root.findViewById(R.id.progressBarLayout);
        llEmpty = root.findViewById(R.id.llEmpty);
        llEmpty.setVisibility(View.GONE);
        btnContinue = root.findViewById(R.id.btnContinue);
        btnContinue.setVisibility(isFromRegister ? View.VISIBLE : View.GONE);
        //
        metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;
        expListView.setIndicatorBounds(width - GetDipsFromPixel(50), width - GetDipsFromPixel(10));
        //expListView.getExpandableListView().setGroupIndicator(null);
        preferenceNewCategories = new ArrayList<>();
        if (preferenceNewCategories == null) {
            preferenceNewCategories = new ArrayList<>();
        }
        getPreferenceData();
//        preferenceAdapter = new ExpandableListAdapter_Preference(getContext(), preferenceNewCategories, this);
//        expListView.setAdapter(preferenceAdapter);
        swipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                getPreferenceData();
            }
        });
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePreferences();
            }
        });
        return root;
    }

    public int GetDipsFromPixel(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }






    public void getPreferenceData() {
        progressBarLayout.setVisibility(View.VISIBLE);
        llEmpty.setVisibility(View.GONE);
        expListView.setVisibility(View.GONE);
        if (UtilityNew.isNetworkAvailable(getActivity())) {
            //   progressDialog = Common.showProgressDialog(getActivity(), progressDialog);
            getAllPerferences(SessionManager.userLogin.userId);
        } else {
            Snackbar.make(coordinatorLayout, Constants.PLEASE_CHECK_INTERNET, Snackbar.LENGTH_SHORT);
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getPreferenceData();
        }
    }

    public void getAllPerferences(String celebId) {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.getAllPreferences(ApiClient.GET_ALL_PREFERENCES + celebId);
        Common.getInstance().callAPI(new ApiRequestModel().setModel(getActivity(), call, ApiClient.GET_ALL_PREFERENCES, false, iApiListener, false));


    }

    public void getData(ArrayList<PreferenceNewCategory> list) {
        progressBarLayout.setVisibility(View.GONE);
        expListView.setVisibility(View.VISIBLE);
        preferenceNewCategories = new ArrayList<>();
        preferenceNewCategories = list;
        for (int i = 0; i < list.size(); i++) {
            PreferenceNew preferenceNew = new PreferenceNew("Select All");
            list.get(i).getCategories().add(0, preferenceNew);
        }
//        preferenceAdapter = new ExpandableListAdapter_Preference(getContext(), isFromRegister, preferenceNewCategories, this);
        expListView.setAdapter(preferenceAdapter);
    }


    public void savePreferences() {
        updateManager();
        procceedPreferenceInServer();

    }

    private void updateManager() {
        selectedListDataHeader.clear();
        categoryList.clear();
        getSelectedList();
        convertListToStringList();

    }

    public void getSelectedList() {
        for (PreferenceNewCategory industry : preferenceNewCategories) {
            PreferenceNewCategory selectedIndustry = new PreferenceNewCategory();

            selectedIndustry.getPreferenceName();
            selectedIndustry.setId(industry.getId());


            ArrayList<PreferenceNew> categoryArrayList = industry.getCategories();
            for (PreferenceNew category :
                    categoryArrayList) {

                if (category.getPreferenceName().equalsIgnoreCase("Select All")) {
                    categoryArrayList.remove(category.getPreferenceName());
                }

            }
            ArrayList<PreferenceNew> selectedCategoryArrayList = new ArrayList<>();
            for (PreferenceNew category : categoryArrayList) {
                if (category.getIsSelected() != null) {
                    if (category.getIsSelected()) {
                        PreferenceNew selectedCategory = new PreferenceNew();
                        selectedCategory.setPreferenceName(category.getPreferenceName());
                        selectedCategory.setId(category.getId());
                        selectedCategoryArrayList.add(selectedCategory);
                    }
                }

            }
            selectedIndustry.setCategories(selectedCategoryArrayList);
            selectedListDataHeader.add(selectedIndustry);
        }


    }

    public void convertListToStringList() {
        if (categoryList != null) {
            categoryList = new ArrayList<>();
        }
        for (PreferenceNewCategory industries : selectedListDataHeader) {

            Log.d("prefrenceSeleSize->", "" + industries.getCategories().size());

            for (PreferenceNew category : industries.getCategories()) {
                if (!category.getPreferenceName().equalsIgnoreCase("Select All") && category.getPreferenceName() != null) {
                    Logger.d("category name", category.getId());
                    categoryList.add(category.getId());
                }
            }
        }

    }

    private void procceedPreferenceInServer() {
        if (categoryList != null) {

            Log.e("duplicatebeforeremove", categoryList.size() + "");
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("userId", SessionManager.userLogin.userId);



                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < categoryList.size(); i++) {
                    Log.e("childSelectedData", categoryList.get(i));
                    jsonArray.put(categoryList.get(i));

                }

                jsonObject.put("preferences", jsonArray);
                if (categoryList.size() >= 3) {
                    jsonrequest(jsonObject);
                } else {
                    Toast.makeText(getContext(), "Selection of 3 or more preference categories is necessary.", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void jsonrequest(JSONObject jsonObject) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        Call<ApiResponseModel> call = apiInterface.POST(ApiClient.SET_MEMBER_PREFERENCE, requestBody);
        Common.getInstance().callAPI(new ApiRequestModel().setModel(getActivity(), call, ApiClient.SET_MEMBER_PREFERENCE, true, iApiListener, false));
    }

    @Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
        if (condition.equals(ApiClient.GET_ALL_PREFERENCES)) {
            try {
                Type type = new TypeToken<ArrayList<PreferenceNewCategory>>() {
                }.getType();
                ArrayList<PreferenceNewCategory> list = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                if (list != null && list.size() != 0) {
                    llEmpty.setVisibility(View.GONE);
                    getData(list);
                }else {
                    progressBarLayout.setVisibility(View.GONE);
                    llEmpty.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (condition.equals(ApiClient.SET_MEMBER_PREFERENCE)) {
            try {
                if (isFromRegister) {
                    Common.getInstance().navigateToFeedPageHome(getActivity());
                } else {
                    Toast.makeText(getContext(), apiResponseModel.message, Toast.LENGTH_SHORT).show();
                    getPreferenceData();
                    ((HelperActivity) getActivity()).updatePreferences();
                }
                SessionManager.getInstance().setKeyValue(SessionManager.KEY_IS_PREFERENCES_SELECTED,true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
        if (condition.equals(ApiClient.SET_MEMBER_PREFERENCE)) {
            Toast.makeText(getContext(), "Please check your network and try again", Toast.LENGTH_SHORT).show();
        }
    }
}
