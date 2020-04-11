package info.sumantv.flow.celebflow.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import info.sumantv.flow.bottommenu.generic.EmptyDataAdapterTop;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.constants.Constants;


/**
 * Created by Shiva on 12/13/2017.
 */

public class BrandsFragment extends Fragment {
    private RecyclerView brandsRecyclerView;
    private RecyclerView.LayoutManager layoutManager;

    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.brands_fragment, container, false);
        brandsRecyclerView = (RecyclerView) view.findViewById(R.id.brands_recycler_view);
        initViews();
        return view;
    }

    private void initViews() {
        brandsRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        brandsRecyclerView.setLayoutManager(layoutManager);
        brandsRecyclerView.setNestedScrollingEnabled(true);
        emptyDataList();
    }


    private void emptyDataList() {

        layoutManager = new LinearLayoutManager(getActivity());
        brandsRecyclerView.setLayoutManager(layoutManager);

        brandsRecyclerView.setAdapter(new EmptyDataAdapterTop(getActivity(), Constants.COMING_UP,
                "", R.drawable.ic_you_have_no_brands_to_see, 1));
    }


}
