package info.sumantv.flow.menu_list.MyCelebrities.MyCelebritiesActivity.FansOfFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import info.sumantv.flow.RecommededCelebProfiles.ProfileData;
import info.sumantv.flow.bottommenu.constants.RController;
import info.sumantv.flow.bottommenu.generic.EmptyDataAdapter;
import info.sumantv.flow.bottommenu.menuitemsmanager.fragments.MyCelebritiesfragment;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.menu_list.MyCelebrities.MyCelebAdapter;
import info.sumantv.flow.retrofitcall.*;
import info.sumantv.flow.userflow.Util.Common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;

import java.util.ArrayList;


public class FansOfFragment extends Fragment implements IApiListener {
    private RecyclerView mFansRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    TextView nodata;
    private ImageView follow_image;
    RelativeLayout relativeLayout;
    ArrayList<ProfileData> onlineprofileDataList;
    MyCelebAdapter onlineCelebAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    IApiListener iApiListener;

    static FansOfFragment instance;

    public static FansOfFragment getInstance() {
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_user_following, container, false);
        iApiListener = this;
        instance = this;
        intializeViews(view);


        getAllFansByCeleb();


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mFansRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        mFansRecyclerView.addItemDecoration(dividerItemDecoration);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
            getAllFansByCeleb();
        });
        return view;
    }

    private void setSkelltonAdapter() {
        onlineCelebAdapter = new MyCelebAdapter(RController.LOADING);
        mFansRecyclerView.setAdapter(onlineCelebAdapter);

    }

    private void intializeViews(View view) {
        mFansRecyclerView = (RecyclerView) view.findViewById(R.id.fans_recyclerview);
//        progressDialog = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        relativeLayout = view.findViewById(R.id.relativeLayout);
        nodata = (TextView) view.findViewById(R.id.nodata);
        follow_image = (ImageView) view.findViewById(R.id.follow_image);
        layoutManager = new LinearLayoutManager(getActivity());
        mFansRecyclerView.setLayoutManager(layoutManager);
    }


    private void getAllFansByCeleb() {
        setSkelltonAdapter();
        onlineprofileDataList = new ArrayList<>();

        if (!Common.checkInternetConnection(getContext())) {
            mFansRecyclerView.removeAllViews();
            mFansRecyclerView.setAdapter(new EmptyDataAdapter(getActivity(), Constants.UH_OH, Constants.PLEASE_CHECK_INTERNET,
                    R.drawable.ic_network, 0));
            return;
        }

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.GET(ApiClient.FAN_CELB_BY_MEMBER
                + SessionManager.userLogin.userId + "/");
        Common.getInstance().callAPI(new ApiRequestModel().setModel(getActivity(), call, ApiClient.FAN_CELB_BY_MEMBER,
                false, iApiListener, false));
    }

    public void deleteListItem() {
        if (onlineCelebAdapter != null) {
            if (onlineprofileDataList != null && onlineprofileDataList.size() > 0) {
                MyCelebritiesfragment.tabLayout.getTabAt(0).setText("Fan " + "(" + onlineprofileDataList.size() + ")");
            } else {
                MyCelebritiesfragment.tabLayout.getTabAt(0).setText("Fan");
                noFansAvailable();
            }
        }
    }


    @Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
        if (condition.equals(ApiClient.FAN_CELB_BY_MEMBER)) {

            onlineprofileDataList = new ArrayList<>();

            try {
                JSONArray response = new JSONArray(new Gson().toJson(apiResponseModel.data));
                if (response.length() != 0) {
                    String profession = "";
                    Boolean status = false;
                    String _id = "";
                    Log.v("Members_online", response.toString());
                    Log.v("Members_online_length", response.length() + "_data");
                    if (response.length() != 0) {
                        nodata.setVisibility(View.GONE);
                        follow_image.setVisibility(View.GONE);
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                ProfileData _profileData = new ProfileData();
                                JSONObject jsonObject = null;
                                jsonObject = response.getJSONObject(i);
                                if (jsonObject.has("celebProfile")) {
                                    JSONArray jsonObject1 = jsonObject.getJSONArray("celebProfile");
                                    if (jsonObject1 != null && jsonObject1.length() != 0) {
                                        for (int j = 0; j < jsonObject1.length(); j++) {
                                            String name = "";
                                            JSONObject memberProfile = jsonObject1.getJSONObject(j);
                                            if (memberProfile.has("_id")) {
                                                _id = memberProfile.getString("_id");
                                            }

                                            if (memberProfile.has("username")) {
                                                name = memberProfile.getString("username");
                                            }

                                            if (memberProfile.has("firstName")) {
                                                if (memberProfile.getString("firstName") != null &&
                                                        !memberProfile.getString("firstName").isEmpty()) {
                                                    name = memberProfile.getString("firstName");
                                                }
                                            }


                                            if (memberProfile.has("profession")) {
                                                profession = memberProfile.getString("profession");
                                            }
                                            if (memberProfile.has("status")) {
                                                status = memberProfile.getBoolean("status");
                                            }

                                            _profileData.set_id(_id);
                                            _profileData.setProfession(profession);
                                            _profileData.setName(name);
                                            _profileData.setStatus(status);

                                            if (memberProfile.has("lastName")) {
                                                if (memberProfile.getString("lastName") != null) {
                                                    _profileData.setLastName(memberProfile.getString("lastName"));
                                                } else {
                                                    _profileData.setLastName(null);
                                                }
                                            } else {
                                                _profileData.setLastName("");
                                            }

                                            if (memberProfile.has("cumulativeSpent")) {
                                                _profileData.setCumulativeCreditValue(memberProfile.getInt("cumulativeSpent"));
                                            } else {
                                                _profileData.setCumulativeCreditValue(0);
                                            }

                                            if (memberProfile.has("lastActivity")) {
                                                _profileData.setLastActivity(memberProfile.getString("lastActivity"));
                                            } else {
                                                _profileData.setLastActivity("");
                                            }

                                            if (memberProfile.has("avtar_imgPath")) {
                                                if (memberProfile.getString("avtar_imgPath") != null &&
                                                        !memberProfile.getString("avtar_imgPath").isEmpty()) {
                                                    _profileData.setAvtar_imgPath(memberProfile.getString("avtar_imgPath"));
                                                } else {
                                                    _profileData.setAvtar_imgPath("");
                                                }
                                            } else {
                                                _profileData.setAvtar_imgPath("");
                                            }

                                            if (memberProfile.has("liveStatus")) {
                                                _profileData.setLiveStatus(memberProfile.getString("liveStatus"));
                                            } else {
                                                _profileData.setLiveStatus("offline");
                                            }


                                            if (memberProfile.has("isCeleb")) {
                                                _profileData.setCeleb(memberProfile.getBoolean("isCeleb"));
                                            } else {
                                                _profileData.setCeleb(false);
//                                                        _profileData.setLiveStatus("offline");
                                            }

                                            //ProfileData
                                            if (memberProfile.has("aboutMe")) {
                                                if (memberProfile.getString("aboutMe") != null &&
                                                        !memberProfile.getString("aboutMe").isEmpty()) {
                                                    _profileData.setAboutMe(memberProfile.getString("aboutMe"));
                                                } else {
                                                    _profileData.setAboutMe("");
                                                }
                                            }

                                            if (memberProfile.has("isManager")) {
                                                _profileData.setManager(memberProfile.getBoolean("isManager"));
                                            } else {
                                                _profileData.setManager(false);
                                            }
                                        }
                                    }
                                    onlineprofileDataList.add(_profileData);
                                }
                            }
                            if (onlineprofileDataList.size() != 0) {
                                onlineCelebAdapter = new MyCelebAdapter(FansOfFragment.this,
                                        getContext(),
                                        onlineprofileDataList, "FANS_OF");
                                mFansRecyclerView.setAdapter(onlineCelebAdapter);
                                MyCelebritiesfragment.tabLayout.getTabAt(0).setText("Fan " + "("
                                        + onlineprofileDataList.size() + ")");
                            } else {
                                noFansAvailable();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Fail to get all field", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        noFansAvailable();
                    }
                } else {
                    noFansAvailable();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void noFansAvailable() {
        mFansRecyclerView.setAdapter(new EmptyDataAdapter(getActivity(),
                Constants.YOU_NOT_FAN, Constants.FAN_YOUR_CELEB,
                R.drawable.ic_fan_your_favourite_celebrity, 5));
    }


    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
        if (condition.equals(ApiClient.FAN_CELB_BY_MEMBER)) {
            mFansRecyclerView.setAdapter(new EmptyDataAdapter(getActivity(),
                    Constants.SOMETHING_WENT_WRONG, R.drawable.ic_nodata, 2));
        }
    }
}
