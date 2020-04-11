package info.dkapp.flow.utils.expendableRecyclerviewNew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import info.dkapp.flow.bottommenu.preferencemanager.newpreferene.ChildOnclikExpendableRecy;

import info.dkapp.flow.R;

public class PreferenceNewCategoryAdapter extends ExpandableRecyclerAdapter<PreferenceNewCategoryViewHolder, PreferenceNewViewHolder> {

    private LayoutInflater mInflator;
    ChildOnclikExpendableRecy iChildOnclikExpendableRecy;

    public PreferenceNewCategoryAdapter(Context context, List<? extends ParentListItem> parentItemList,ChildOnclikExpendableRecy iChildOnclikExpendableRecy) {
        super(parentItemList);
        mInflator = LayoutInflater.from(context);
        this.iChildOnclikExpendableRecy = iChildOnclikExpendableRecy;
    }

    @Override
    public PreferenceNewCategoryViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View movieCategoryView = mInflator.inflate(R.layout.preferences_category_view, parentViewGroup, false);
        return new PreferenceNewCategoryViewHolder(movieCategoryView);
    }

    @Override
    public PreferenceNewViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        View moviesView = mInflator.inflate(R.layout.preferences_view, childViewGroup, false);
        return new PreferenceNewViewHolder(moviesView);
    }

    @Override
    public void onBindParentViewHolder(PreferenceNewCategoryViewHolder preferenceNewCategoryViewHolder, int position, ParentListItem parentListItem) {
        PreferenceNewCategory preferenceNewCategory = (PreferenceNewCategory) parentListItem;
        preferenceNewCategoryViewHolder.bind(preferenceNewCategory);
    }

    @Override
    public void onBindChildViewHolder(PreferenceNewViewHolder preferenceNewViewHolder, int position, Object childListItem) {
        PreferenceNew preferenceNew = (PreferenceNew) childListItem;
        preferenceNewViewHolder.bind(preferenceNew, iChildOnclikExpendableRecy,position);
    }
}
