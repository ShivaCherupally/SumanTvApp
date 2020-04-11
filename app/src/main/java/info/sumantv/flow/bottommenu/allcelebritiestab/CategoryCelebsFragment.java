//package info.celebkonnect.flow.bottommenu.allcelebritiestab;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.os.Parcelable;
//import androidx.annotation.Nullable;
//import com.google.android.material.snackbar.Snackbar;
//import androidx.fragment.app.Fragment;
//import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import android.util.DisplayMetrics;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import info.celebkonnect.flow.bottommenu.activity.BottomMenuActivity;
//import info.celebkonnect.flow.bottommenu.constants.RController;
//import info.celebkonnect.flow.bottommenu.generic.EmptyDataAdapter;
//import info.celebkonnect.flow.bottommenu.interfaces.fragments.IFragment;
//import info.celebkonnect.flow.celebflow.R;
//import info.celebkonnect.flow.celebflow.constants.Constants;
//import info.celebkonnect.flow.retrofitcall.*;
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class CategoryCelebsFragment extends Fragment implements IFragment, IApiListener {
//    RecyclerView mRecyclerView;
//    LinearLayout nodataLayout;
//    private SwipeRefreshLayout swipeRefreshLayout;
//    RelativeLayout rlParent;
//    CelebProfileAdapter celebProfileAdapter;
//    Button addpreferenceBtn;
//    LinearLayout llNoResultsFound;
//    TextView tvNote;
//    List<CelebProfileCompleteData> celebProfileCompleteDataa = new ArrayList<CelebProfileCompleteData>();
//    Bundle bundle = new Bundle();
//    Boolean isGrid = false;
//    String condition = null;
//    String query = null;
//    IApiListener iApiListener;
//
//    static CategoryCelebsFragment instance = null;
//
//    public static CategoryCelebsFragment getInstance() {
//        return instance;
//    }
//
//    public static CategoryCelebsFragment newInstance(String param2, List<CelebProfileCompleteData> celebProfileCompleteData) {
//        CategoryCelebsFragment fragment = new CategoryCelebsFragment();
//        Bundle args = new Bundle();
//        args.putString("CELEB_TYPE", param2);
//        args.putParcelableArrayList("CELEB_ALL_DATA", (ArrayList<? extends Parcelable>) celebProfileCompleteData);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.categoryceleb_fragment, container, false);
//        isGrid = false;
//        iApiListener = this;
//        setUp(view);
//        return view;
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        instance = this;
//    }
//
//    private void setUp(View view) {
//
//        rlParent = view.findViewById(R.id.rlParent);
//        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
//        mRecyclerView = (RecyclerView) view.findViewById(R.id.common_Recyclerview);
//        nodataLayout = (LinearLayout) view.findViewById(R.id.nodataLayout);
//        addpreferenceBtn = (Button) view.findViewById(R.id.addpreferenceBtn);
//        llNoResultsFound = view.findViewById(R.id.llNoResultsFound);
//        tvNote = (TextView) view.findViewById(R.id.tvNote);
//
//        celebProfileAdapter = new CelebProfileAdapter(RController.LOADING);
//        mRecyclerView.setAdapter(celebProfileAdapter);
//
//        bundle = getArguments();
//        try {
//            if (bundle.getParcelableArrayList("CELEB_ALL_DATA") != null &&
//                    bundle.getParcelableArrayList("CELEB_ALL_DATA").size() > 0) {
//                celebProfileCompleteDataa = bundle.getParcelableArrayList("CELEB_ALL_DATA");
//            } else {
//                //getAllCelebritiesListFormServerCeleb();
//            }
//            threeGridView("onRefresh");
//        } catch (Exception e) {
//
//        }
//        swipeRefreshLayout.setEnabled(false);
//        swipeRefreshLayout.setRefreshing(false);
//    }
//
//    public void setToRecylerView(final String celebType, final Boolean showLoading) {
//        @SuppressLint("StaticFieldLeak")
//        class setData extends AsyncTask<String, Void, String> {
//            private List<CelebProfileCompleteData> catogeryCelebList = new ArrayList<>();
//            private List<CelebProfileCompleteData> catogeryCelebListtemp = new ArrayList<>();
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                if (showLoading) {
//                    celebProfileAdapter = new CelebProfileAdapter(RController.LOADING);
//                    mRecyclerView.setAdapter(celebProfileAdapter);
//                }
//            }
//
//            @Override
//            protected String doInBackground(String... params) {
//                try {
//                   /* CelebrityBaseFragment celebrityBaseFragment = CelebrityBaseFragment.getInstance();
//
//                    if (celebrityBaseFragment != null) {
//                        celebProfileCompleteDataa = celebrityBaseFragment.getCelebProfileCompleteData();
//                    }
//                    catogeryCelebList.addAll(celebProfileCompleteDataa);
//                    if (celebType.equals(activity().getResources().getString(R.string.all_celeb_tab))) {
//                        for (int i = 0; i < catogeryCelebList.size(); i++) {
//                            if (!catogeryCelebList.get(i).isDeleted()) {
//                                catogeryCelebListtemp.add(catogeryCelebList.get(i));
//                            }
//                        }
//                    } else if (celebType.equals(activity().getResources().getString(R.string.trending_celeb))) {
//                        for (int i = 0; i < catogeryCelebList.size(); i++) {
//                            if (!catogeryCelebList.get(i).isDeleted() && catogeryCelebList.get(i).isTrending()) {
//                                catogeryCelebListtemp.add(catogeryCelebList.get(i));
//                            }
//                        }
//                    } else if (celebType.equals(activity().getResources().getString(R.string.editor_celeb))) {
//                        for (int i = 0; i < catogeryCelebList.size(); i++) {
//                            if (!catogeryCelebList.get(i).isDeleted()) {
//                                if (catogeryCelebList.get(i).isEditorChoice()) {
//                                    catogeryCelebListtemp.add(catogeryCelebList.get(i));
//                                }
//                            }
//                        }
//                    } else if (celebType.equals(activity().getResources().getString(R.string.recomended_celeb))) {
//                        for (int i = 0; i < catogeryCelebList.size(); i++) {
//                            if (!catogeryCelebList.get(i).isDeleted()) {
//                                if (catogeryCelebList.get(i).isPromoted()) {
//                                    catogeryCelebListtemp.add(catogeryCelebList.get(i));
//                                }
//                            }
//                        }
//                    }*/
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(String result) {
//                super.onPostExecute(result);
//                categoreyDataAdapter(catogeryCelebListtemp, celebType);
//            }
//        }
//        new setData().execute();
//    }
//
//    private void categoreyDataAdapter(List<CelebProfileCompleteData> celebProfileCompleteDataa, String celebType) {
//        if (celebProfileCompleteDataa.size() > 0) {
//            if (celebProfileAdapter != null && celebProfileAdapter.getRController() == RController.LOADED) {
//                celebProfileAdapter.offlineOrCategoryList = celebProfileCompleteDataa;
//                celebProfileAdapter.offlineOrCategoryListFilter = celebProfileCompleteDataa;
//                celebProfileAdapter.isGrid = isGrid;
//                celebProfileAdapter.notifyDataSetChanged();
//                if (query != null && query.trim().length() > 0) {
//                    celebProfileAdapter.getFilter().filter(query);
//                }
//            } else {
//                celebProfileAdapter = new CelebProfileAdapter(CategoryCelebsFragment.this, getContext(), celebProfileCompleteDataa, isGrid, RController.LOADED);
//                mRecyclerView.setNestedScrollingEnabled(true);
//                mRecyclerView.setAdapter(celebProfileAdapter);
//            }
//            addpreferenceBtn.setVisibility(View.GONE);
//        } else {
//            addpreferenceBtn.setVisibility(View.VISIBLE);
//            celebProfileAdapter = null;
//            mRecyclerView.setAdapter(new EmptyDataAdapter(activity(), Constants.NO_DATA_AVAILABLE, R.drawable.ic_nodata, 2));
//        }
//    }
//
//    public void updateAdapterFanStatus(Boolean fanStatus) {
//        if (celebProfileAdapter != null) {
//            celebProfileCompleteDataa.get(celebProfileAdapter.getClickedPosition()).isFan = fanStatus;
//            try {
//                Log.e("positionValue", celebProfileAdapter.getClickedPosition() + "_SHiva");
//                if (celebProfileAdapter.getClickedPosition() != -1) {
//                } else {
//                    celebProfileAdapter.updateItem(celebProfileCompleteDataa.get(celebProfileAdapter.getClickedPosition()));
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//    }
//
//    public void threeGridView(String condition) {
//        this.condition = condition;
//        try {
//            BottomMenuActivity bottomMenuActivity = (BottomMenuActivity) getParentFragment().getActivity();
//            if (bottomMenuActivity != null) {
//                //isGrid = bottomMenuActivity.getIsGrid();
//                if (condition != null && condition.equalsIgnoreCase("OnRefresh")) {
//                    isGrid = !isGrid;
//                } else {
//                    celebProfileAdapter = null;
//                }
//                isGrid = !isGrid;
//                DisplayMetrics displayMetrics = new DisplayMetrics();
//                activity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//                RecyclerView.LayoutManager layoutManager = isGrid ? new GridLayoutManager(getActivity(), 3) : new LinearLayoutManager(getActivity());
//                mRecyclerView.setLayoutManager(layoutManager);
//                mRecyclerView.setNestedScrollingEnabled(true);
//                mRecyclerView.getLayoutManager().setAutoMeasureEnabled(true);
//                mRecyclerView.setHasFixedSize(true);
//                setToRecylerView(bundle.getString("CELEB_TYPE", activity().getResources().getString(R.string.all_celeb_tab)), true);
//                //
//                bottomMenuActivity.changeListGridImage(isGrid);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void showNoResultsFound(List<CelebProfileCompleteData> celebProfileCompleteDataList) {
//        if (celebProfileCompleteDataList != null && celebProfileCompleteDataList.size() > 0) {
//            llNoResultsFound.setVisibility(View.GONE);
//        } else {
//            tvNote.setText(Constants.THERE_IS_NO_DATA);
//            llNoResultsFound.setVisibility(View.VISIBLE);
////            noSearchData();
//        }
//    }
//
//    public void sendSearchItem(String query) {
//        this.query = query;
//        if (query != null) {
//            if (celebProfileAdapter != null) {
//                celebProfileAdapter.getFilter().filter(query);
//            }
//        }
//    }
//
//    @Override
//    public void changeTitle(String title) {
//
//    }
//
//    @Override
//    public void showSnackBar(String snackBarText, int type) {
//        Snackbar.make(rlParent, snackBarText, Snackbar.LENGTH_SHORT);
//    }
//
//    @Override
//    public Activity activity() {
//        return getActivity();
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
//                    if (celebProfileCompleteDataList.size() > 0) {
//                        celebProfileCompleteDataa = new ArrayList<>();
//                        celebProfileCompleteDataa = celebProfileCompleteDataList;
//                        threeGridView("onRefresh");
//                    } else {
//                        addpreferenceBtn.setVisibility(View.VISIBLE);
//                    }
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
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        try {
//            if (isVisibleToUser) {
//                BottomMenuActivity bottomMenuActivity = null;
//                if (getParentFragment() != null) {
//                    bottomMenuActivity = (BottomMenuActivity) getParentFragment().getActivity();
//                }
//                if (bottomMenuActivity != null) {
//                    if (isGrid == bottomMenuActivity.getIsGrid()) {
//                        setToRecylerView(bundle.getString("CELEB_TYPE", activity().getResources().getString(R.string.all_celeb_tab)), false);
//                    } else {
//                        threeGridView(null);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
