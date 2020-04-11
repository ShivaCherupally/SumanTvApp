package info.sumantv.flow.menu_list.MyFansFollowers.FollowersFragment;

import android.annotation.SuppressLint;
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
import com.google.gson.Gson;
import info.sumantv.flow.RecommededCelebProfiles.ProfileData;
import info.sumantv.flow.bottommenu.constants.RController;
import info.sumantv.flow.bottommenu.generic.EmptyDataAdapter;
import info.sumantv.flow.bottommenu.menuitemsmanager.fragments.MyFanFollowersFragment;

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


@SuppressLint("ValidFragment")
public class FollowersFragment extends Fragment implements IApiListener {
    private RecyclerView mFansRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    TextView nodata;
    private ImageView follow_image;
    SwipeRefreshLayout swipeRefreshLayout;
    RelativeLayout relativeLayout;
    boolean isFromCelebProfile;
    ArrayList<ProfileData> onlineprofileDataList;
    MyCelebAdapter onlineCelebAdapter;
    String fanId;
    IApiListener iApiListener;

    @SuppressLint("ValidFragment")
    public FollowersFragment(String fanId, boolean isFromCelebProfile) {
        this.fanId = fanId;
        this.isFromCelebProfile = isFromCelebProfile;
        Log.e("fanId", fanId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_user_following, container, false);
        iApiListener = this;

        intializeViews(view);
        setSkelltonAdapter();


        getAllFollowersByCeleb();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mFansRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        mFansRecyclerView.addItemDecoration(dividerItemDecoration);
//        }
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
            getAllFollowersByCeleb();
        });
        return view;

    }

    private void setSkelltonAdapter() {
        onlineCelebAdapter = new MyCelebAdapter(RController.LOADING);
        mFansRecyclerView.setAdapter(onlineCelebAdapter);
    }

    private void intializeViews(View view) {

        mFansRecyclerView = (RecyclerView) view.findViewById(R.id.fans_recyclerview);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        relativeLayout = view.findViewById(R.id.relativeLayout);
        nodata = (TextView) view.findViewById(R.id.nodata);
        nodata.setText("No followers for you!!");
        follow_image = (ImageView) view.findViewById(R.id.follow_image);
        layoutManager = new LinearLayoutManager(getActivity());
        mFansRecyclerView.setLayoutManager(layoutManager);

    }


    private void getAllFollowersByCeleb() {

        setSkelltonAdapter();

        if (!Common.checkInternetConnection(getContext())) {
            mFansRecyclerView.removeAllViews();
            mFansRecyclerView.setAdapter(new EmptyDataAdapter(getActivity(), Constants.UH_OH, Constants.PLEASE_CHECK_INTERNET,
                    R.drawable.ic_network, 0));
            return;
        }

        onlineprofileDataList = new ArrayList<>();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.GET(ApiClient.FOLLOWING_MEMBER_BY_CELB + fanId + "/");
        Common.getInstance().callAPI(new ApiRequestModel().setModel(getActivity(), call, ApiClient.FOLLOWING_MEMBER_BY_CELB, false, iApiListener, false));
    }


    public void deleteListItem(final int positionValue) {
        onlineprofileDataList.remove(positionValue);
        onlineCelebAdapter.notifyDataSetChanged();
        MyFanFollowersFragment.tabLayout.getTabAt(1).setText("Followers " + "("+ onlineprofileDataList.size() + ")");
    }

    @Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
        if (condition.equals(ApiClient.FOLLOWING_MEMBER_BY_CELB)) {
            try {
                onlineprofileDataList = new ArrayList<>();
                JSONArray response = new JSONArray(new Gson().toJson(apiResponseModel.data));
                if (response != null) {

                    if (response.length() != 0) {
                        String profession = "";
                        Log.v("Members_online", response.toString());
                        Log.v("Members_online_length", response.length() + "_data");
                        if (response.length() != 0) {
                            nodata.setVisibility(View.GONE);
                            follow_image.setVisibility(View.GONE);
                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    ProfileData _profileData = new ProfileData();
                                    JSONObject jsonObject = null;
                                    try {
                                        jsonObject = response.getJSONObject(i);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    JSONArray jsonObject1 = jsonObject.getJSONArray("memberProfile");
                                    if (jsonObject1 != null && jsonObject1.length() != 0) {
                                        for (int j = 0; j < jsonObject1.length(); j++) {
                                            JSONObject memberProfile = jsonObject1.getJSONObject(j);
                                            String _id = memberProfile.getString("_id");


                                            String name = "";

                                            if (memberProfile.getString("username") != null &&
                                                    !memberProfile.getString("username").isEmpty()) {
                                                name = memberProfile.getString("username");
                                            }

                                            if (memberProfile.getString("firstName") != null &&
                                                    !memberProfile.getString("firstName").isEmpty()) {
                                                name = memberProfile.getString("firstName");
                                            }


                                            if (memberProfile.has("profession")) {
                                                profession = memberProfile.getString("profession");
                                            } else {
                                                profession = "";
                                            }


                                            Boolean status = memberProfile.getBoolean("status");

                                            _profileData.set_id(_id);
                                            _profileData.setProfession(profession);
                                            _profileData.setName(name);
                                            _profileData.setStatus(status);

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
                                                if (memberProfile.getString("avtar_imgPath") != null && !memberProfile.getString("avtar_imgPath").isEmpty()) {
                                                    _profileData.setAvtar_imgPath(memberProfile.getString("avtar_imgPath"));
                                                } else {
                                                    _profileData.setAvtar_imgPath("");
                                                }
                                            } else {
                                                _profileData.setAvtar_imgPath("");
                                            }

                                            if (memberProfile.has("isCeleb")) {
                                                _profileData.setCeleb(memberProfile.getBoolean("isCeleb"));
                                            } else {
                                                _profileData.setCeleb(false);
                                            }

                                            if (memberProfile.has("aboutMe")) {
                                                if (memberProfile.getString("aboutMe") != null &&
                                                        !memberProfile.getString("aboutMe").isEmpty()) {
                                                    _profileData.setAboutMe(memberProfile.getString("aboutMe"));
                                                } else {
                                                    _profileData.setAboutMe("");
                                                }
                                            } else {
                                                _profileData.setAboutMe("");
                                            }

                                            if (memberProfile.has("lastName")) {
                                                if (memberProfile.getString("lastName") != null) {
                                                    _profileData.setLastName(memberProfile.getString("lastName"));
                                                } else {
                                                    _profileData.setLastName(null);
                                                }
                                            } else {
                                                _profileData.setLastName(null);
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

                                if (onlineprofileDataList.size() != 0) {
                                    MyFanFollowersFragment.tabLayout.getTabAt(1).setText("Followers " + "(" + onlineprofileDataList.size() + ")");
                                    onlineCelebAdapter = new MyCelebAdapter(FollowersFragment.this, getContext(),onlineprofileDataList, "FOLLOWER", isFromCelebProfile);
                                    mFansRecyclerView.setAdapter(onlineCelebAdapter);
                                } else {
                                    noFollowersAvailable();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            noFollowersAvailable();
                        }
                    } else {
                        noFollowersAvailable();
                    }


                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
        if (condition.equals(ApiClient.FOLLOWING_MEMBER_BY_CELB)) {
            mFansRecyclerView.setAdapter(new EmptyDataAdapter(getActivity(),
                    Constants.SOMETHING_WENT_WRONG, R.drawable.ic_nodata, 2));
        }
    }


    private void noFollowersAvailable() {
        mFansRecyclerView.setAdapter(new EmptyDataAdapter(getActivity(),
                Constants.NO_FOLLOWERS, "",
                R.drawable.ic_start_browsing, 5));
    }
}
