package info.dkapp.flow.menu_list.MyCelebrities.MyCelebritiesActivity.CelebFollowingFragment;

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
import info.dkapp.flow.RecommededCelebProfiles.ProfileData;
import info.dkapp.flow.bottommenu.constants.RController;
import info.dkapp.flow.bottommenu.generic.EmptyDataAdapter;
import info.dkapp.flow.bottommenu.menuitemsmanager.fragments.MyCelebritiesfragment;

import info.dkapp.flow.R;
import info.dkapp.flow.celebflow.constants.Constants;
import info.dkapp.flow.menu_list.MyCelebrities.MyCelebAdapter;
import info.dkapp.flow.retrofitcall.*;
import info.dkapp.flow.userflow.Util.Common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;

import java.util.ArrayList;


public class CelebFollowingFragment extends Fragment implements IApiListener {
    private RecyclerView mFansRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    TextView nodata;
    private ImageView follow_image;
    RelativeLayout relativeLayout;
    ArrayList<ProfileData> onlineprofileDataList;
    MyCelebAdapter onlineCelebAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    IApiListener iApiListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_user_following, container, false);
        iApiListener = this;
        intializeViews(view);

        setSkelltonAdapter();

        getAllFollowersByCeleb();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mFansRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        mFansRecyclerView.addItemDecoration(dividerItemDecoration);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
            getAllFollowersByCeleb();
        });
        return view;
    }

    private void intializeViews(View view) {
        mFansRecyclerView = (RecyclerView) view.findViewById(R.id.fans_recyclerview);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        relativeLayout = view.findViewById(R.id.relativeLayout);
        nodata = (TextView) view.findViewById(R.id.nodata);
        follow_image = (ImageView) view.findViewById(R.id.follow_image);
        layoutManager = new LinearLayoutManager(getActivity());
        mFansRecyclerView.setLayoutManager(layoutManager);
    }


    private void getAllFollowersByCeleb() {
        setSkelltonAdapter();
        onlineprofileDataList = new ArrayList<>();

        if (!Common.checkInternetConnection(getContext())) {
            mFansRecyclerView.removeAllViews();
            mFansRecyclerView.setAdapter(new EmptyDataAdapter(getActivity(), Constants.UH_OH, Constants.PLEASE_CHECK_INTERNET,
                    R.drawable.ic_network, 0));
            return;
        }

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.GET(ApiClient.FOLLOWING_CELB_BY_MEMBER
                + SessionManager.userLogin.userId + "/");
        Common.getInstance().callAPI(new ApiRequestModel().setModel(getActivity(), call, ApiClient.FOLLOWING_CELB_BY_MEMBER,
                false, iApiListener, false));

    }


    public void deleteListItem(final int positionValue) {
        onlineprofileDataList.remove(positionValue);
        onlineCelebAdapter.notifyDataSetChanged();
        if (onlineprofileDataList.size() != 0) {
            MyCelebritiesfragment.tabLayout.getTabAt(1).setText("Following " + "( " + onlineprofileDataList.size() + " )");
        } else {
            MyCelebritiesfragment.tabLayout.getTabAt(1).setText("Following");
            noFollowersAvailable();
        }
    }

    private void setSkelltonAdapter() {
        onlineCelebAdapter = new MyCelebAdapter(RController.LOADING);
        mFansRecyclerView.setAdapter(onlineCelebAdapter);
    }


    @Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
        if (condition.equals(ApiClient.FOLLOWING_CELB_BY_MEMBER)) {
            try {
                onlineprofileDataList = new ArrayList<>();
                JSONArray response = new JSONArray(new Gson().toJson(apiResponseModel.data));
                if (response.length() != 0) {
                    Log.v("Members_online", response.toString());
                    Log.v("Members_online_length", response.length() + "_data");
//                            Common.dismissProgressDialog(getContext(), progressDialog);
                    if (response.length() != 0) {
                        follow_image.setVisibility(View.GONE);
                        nodata.setVisibility(View.GONE);
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                ProfileData _profileData = new ProfileData();
                                JSONObject jsonObject = null;
                                String profession = "";
                                Boolean status;
                                String _id = "";
                                String name = "";
                                try {
                                    jsonObject = response.getJSONObject(i);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                JSONArray jsonObject1 = jsonObject.getJSONArray("celebProfile");
                                if (jsonObject1 != null && jsonObject1.length() != 0) {
                                    for (int j = 0; j < jsonObject1.length(); j++) {
                                        JSONObject memberProfile = jsonObject1.getJSONObject(j);
                                        if (memberProfile.has("_id")) {
                                            _id = memberProfile.getString("_id");
                                        }


                                        if (memberProfile.getString("username") != null && !memberProfile.getString("username").isEmpty()) {
                                            name = memberProfile.getString("username");
                                        }

                                        if (memberProfile.has("firstName")) {
                                            name = memberProfile.getString("firstName");
                                        } else {
                                            name = "";
                                        }


                                        if (memberProfile.has("profession")) {
                                            profession = memberProfile.getString("profession");
                                        } else {
                                            profession = "";
                                        }

                                        if (memberProfile.has("status")) {
                                            status = memberProfile.getBoolean("status");
                                        } else {
                                            status = false;
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
                                            _profileData.setLastName(null);
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
                                            //avtar_imgPath
                                            if (memberProfile.getString("avtar_imgPath") != null &&
                                                    !memberProfile.getString("avtar_imgPath").isEmpty()) {
                                                _profileData.setAvtar_imgPath(memberProfile.getString("avtar_imgPath"));
                                            } else {
                                                _profileData.setAvtar_imgPath(null);
                                            }
                                        } else {
                                            _profileData.setAvtar_imgPath(null);
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


                                        if (memberProfile.has("isManager")) {
                                            _profileData.setManager(memberProfile.getBoolean("isManager"));
                                        } else {
                                            _profileData.setManager(false);
                                        }

                                    }
                                    onlineprofileDataList.add(_profileData);
                                }
                            }

                            if (onlineprofileDataList.size() != 0) {
                                MyCelebritiesfragment.tabLayout.getTabAt(1).setText("Following " + "( "
                                        + onlineprofileDataList.size() + " )");
                                onlineCelebAdapter = new MyCelebAdapter(CelebFollowingFragment.this, getContext(),
                                        onlineprofileDataList, "FOLLOWERS");
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void noFollowersAvailable() {
        mFansRecyclerView.setAdapter(new EmptyDataAdapter(getActivity(),
                Constants.YOU_NOT_FOLLOW,
                Constants.YOU_NOT_FOLLOW_CELEB, R.drawable.ic_start_browsing, 5));
    }

    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
        if (condition.equals(ApiClient.FOLLOWING_CELB_BY_MEMBER)) {
            try {
                mFansRecyclerView.setAdapter(new EmptyDataAdapter(getActivity(),
                        Constants.SOMETHING_WENT_WRONG, R.drawable.ic_nodata, 2));
            } catch (Exception e) {

            }
        }
    }
}
