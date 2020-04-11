package info.sumantv.flow.bottommenu.menuitemsmanager.fragments.my_schedules;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import info.sumantv.flow.celebflow.MySchedules;

import info.sumantv.flow.R;

public class MySchedulesListFragment extends Fragment implements View.OnClickListener {
    RecyclerView recyclerView;
    MySchedulesViewAdapter mySchedulesAdapter;
    RelativeLayout relativeLayout;
    SwipeRefreshLayout swipeRefreshLayout;
    private List<MySchedules> mySchedulesList;
    TextView texttoday, textweek, textmonth;
    TextView nodata;

    private ProgressDialog progressDialog;
    String firtname = "", lastname = "";
    private LinearLayout no_schedules;

//    Bundle arguments;

    public static MySchedulesListFragment newInstance(String param1, String param2) {
        MySchedulesListFragment fragment = new MySchedulesListFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_my_schedule, container, false);
        nodata = root.findViewById(R.id.nodata);
        no_schedules = root.findViewById(R.id.no_schedules);
        swipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayout);
        relativeLayout = root.findViewById(R.id.relativeLayout);




        texttoday = root.findViewById(R.id.textView_today);
        texttoday.setTextColor(getResources().getColor(R.color.skyblue));
        textweek = root.findViewById(R.id.textView_thisWeek);
        textmonth = root.findViewById(R.id.textView_thisMonth);
        recyclerView = root.findViewById(R.id.recycler_view_my_schedule);

        texttoday.setPaintFlags(texttoday.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        textweek.setPaintFlags(textweek.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        textmonth.setPaintFlags(textmonth.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        texttoday.setOnClickListener(this);
        textweek.setOnClickListener(this);
        textmonth.setOnClickListener(this);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
        });
        return root;
    }


    private void nodataLayout() {
        nodata.setVisibility(View.VISIBLE);
        no_schedules.setVisibility(View.VISIBLE);
        texttoday.setVisibility(View.GONE);
        textweek.setVisibility(View.GONE);
        textmonth.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.textView_today) {
            texttoday.setTextColor(getResources().getColor(R.color.skyblue));
            textweek.setTextColor(getResources().getColor(R.color.black));
            textmonth.setTextColor(getResources().getColor(R.color.black));

        } else if (v.getId() == R.id.textView_thisWeek) {
            texttoday.setTextColor(getResources().getColor(R.color.black));
            textweek.setTextColor(getResources().getColor(R.color.skyblue));
            textmonth.setTextColor(getResources().getColor(R.color.black));

        } else if (v.getId() == R.id.textView_thisMonth) {
            texttoday.setTextColor(getResources().getColor(R.color.black));
            textweek.setTextColor(getResources().getColor(R.color.black));
            textmonth.setTextColor(getResources().getColor(R.color.skyblue));

        }
    }

    public boolean checkInternetConnection() {
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isWifiConn = networkInfo.isConnected();
        networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isMobileConn = networkInfo.isConnected();
        Log.v("TAG", "Wifi connected: " + isWifiConn);
        Log.v("TAG", "Mobile connected: " + isMobileConn);
        return connMgr.getActiveNetworkInfo() != null;
    }

}
