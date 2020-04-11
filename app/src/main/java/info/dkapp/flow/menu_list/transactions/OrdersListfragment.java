package info.dkapp.flow.menu_list.transactions;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import info.dkapp.flow.bottommenu.generic.EmptyDataAdapter;
import info.dkapp.flow.bottommenu.generic.EmptyDataNewAdapter;
import info.dkapp.flow.bottommenu.interfaces.fragments.IFragment;

import info.dkapp.flow.R;
import info.dkapp.flow.celebflow.constants.Constants;
import info.dkapp.flow.menu_list.MyOrders.MyOrderAdapter;
import info.dkapp.flow.menu_list.MyOrders.MyOrderData;
import info.dkapp.flow.retrofitcall.*;
import info.dkapp.flow.userflow.Util.Common;
import info.dkapp.flow.utils.Utility;
import retrofit2.Call;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

public class OrdersListfragment extends Fragment implements IApiListener, IFragment {

    private RecyclerView mMyRecyList;
    SwipeRefreshLayout swipeRefreshLayout;
    ApiInterface apiInterface;
    private ArrayList<MyOrderData> myOrderdata;
    MyOrderAdapter myOrderAdapter;
    IApiListener iApiListener;
    Double creditsbalnce = 0.00;
    LinearLayout recycleLayout;
    Bundle arguments;
    boolean isLoadMoreApiRunning = false, stopLoading = false;
    int LIMIT_COUNT = 20;
    CoordinatorLayout coordinator_layout;

    public static OrdersListfragment newInstance(String param1, String param2) {
        OrdersListfragment fragment = new OrdersListfragment();
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        creditsbalnce = SessionManager.userLogin.mainCredits;
        iApiListener = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        arguments = getArguments();
        View root = inflater.inflate(R.layout.order_list_fragment, null);

        initializeViwesOrdersView(root);
        setSkelltonAdapter();
        getOrdersList(false);


        swipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayout);

        recycleLayout = root.findViewById(R.id.recycleLayout);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
//                creditLayout.setVisibility(View.VISIBLE);
            if (Common.checkInternetConnection(activity())) {
                myOrderAdapter = null;
                getOrdersList(false);
            } else {
                showSnackBar(Constants.PLEASE_CHECK_INTERNET, 5);
            }


        });
        mMyRecyList.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                    if (myOrderdata != null && myOrderdata.size() > 0 && !stopLoading) {
                        getOrdersList(true);
                    }
                }
            }
        });
        return root;
    }


    private void initializeViwesOrdersView(View root) {
        coordinator_layout = root.findViewById(R.id.coordinator_layout);
        mMyRecyList = (RecyclerView) root.findViewById(R.id.my_order_cart_recyclerView);
        setActions();
    }

    private void setSkelltonAdapter() {
        mMyRecyList.setAdapter(new MyOrderAdapter(Arrays.asList(null, null), true));
    }


    private void setActions() {

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mMyRecyList.setLayoutManager(mLayoutManager);

       /* DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mMyRecyList.getContext(), DividerItemDecoration.VERTICAL);
        mMyRecyList.addItemDecoration(dividerItemDecoration);*/


    }


    private void getOrdersList(Boolean isLoadmore) {
        if (isLoadMoreApiRunning) {
            return;
        }
        if (!Common.checkInternetConnection(getContext())) {
            recycleLayout.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
            mMyRecyList.removeAllViews();
            mMyRecyList.setAdapter(new EmptyDataAdapter(getActivity(), Constants.UH_OH, Constants.PLEASE_CHECK_INTERNET,
                    R.drawable.ic_network, 0));
            return;
        }
        IApiListener iApiListener = this;
        Call<ApiResponseModel> call = null;
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        if (!isLoadmore) {
            if (Common.getInstance().checkInterConnection(mMyRecyList, activity())) {
                String url = ApiClient.MY_ORDERS_LIST
                        + SessionManager.userLogin.userId + "/null/" + LIMIT_COUNT;
                call = apiInterface.GET(url);
            }
        } else {
            if (Common.checkInternetConnection(activity())) {
                String url = ApiClient.MY_ORDERS_LIST
                        + SessionManager.userLogin.userId + "/" + myOrderdata.get(myOrderdata.size() - 1).createdAt + "/" + LIMIT_COUNT;
                call = apiInterface.GET(url);
            } else {
                showSnackBar(Constants.PLEASE_CHECK_INTERNET, 5);
                return;
            }
        }
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, ApiClient.MY_ORDERS_LIST, false, iApiListener, false));
        isLoadMoreApiRunning = true;
    }


    @Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
        if (condition.equals(ApiClient.MY_ORDERS_LIST)) {
            isLoadMoreApiRunning = false;
            try {
                Type type = new TypeToken<ArrayList<MyOrderData>>() {
                }.getType();
                if (myOrderAdapter == null) {
                    myOrderdata = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                    if (myOrderdata.size() > 0) {
                        recycleLayout.setBackgroundColor(getActivity().getResources().getColor(R.color.managerBackgroundColor));
                        myOrderAdapter = new MyOrderAdapter(myOrderdata, false);
                        mMyRecyList.setAdapter(myOrderAdapter);
                    } else {
                        noOrderAvailable();
                    }
                } else {
                    ArrayList<MyOrderData> myOrderDataTemp = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                    if (myOrderDataTemp.size() < LIMIT_COUNT) {
                        stopLoading = true;
                    }
                    if (myOrderDataTemp.size() > 0) {
                        myOrderdata.addAll(myOrderDataTemp);
                        myOrderAdapter.loadmore(myOrderdata);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                mMyRecyList.setAdapter(new EmptyDataNewAdapter(activity(), Constants.SOMETHING_WENT_WRONG_SERVER, Constants.DRAG_TO_RETRY,
                        R.drawable.ic_no_upcoming_events));
            }
        }
    }

    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
        if (condition.equals(ApiClient.MY_ORDERS_LIST)) {
            isLoadMoreApiRunning = false;
            recycleLayout.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
            mMyRecyList.setAdapter(new EmptyDataNewAdapter(getActivity(),
                    Constants.SOMETHING_WENT_WRONG_SERVER,
                    Constants.DRAG_TO_RETRY,
                    R.drawable.ic_order_list));
        }
    }


    private void noOrderAvailable() {
        recycleLayout.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
        mMyRecyList.setAdapter(new EmptyDataNewAdapter(getActivity(),
                Constants.WHOOPS,
                Constants.THERE_NO_ORDERS,
                R.drawable.ic_order_list));
    }

    @Override
    public void changeTitle(String title) {

    }

    @Override
    public void showSnackBar(String snackBarText, int type) {
        Utility.showSnackBar(activity(), coordinator_layout, snackBarText, type);
    }

    @Override
    public Activity activity() {
        return getActivity();
    }
}
