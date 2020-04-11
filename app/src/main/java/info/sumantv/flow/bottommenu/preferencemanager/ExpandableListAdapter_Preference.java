package info.sumantv.flow.bottommenu.preferencemanager;

import android.content.Context;
import android.graphics.Typeface;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import info.sumantv.flow.bottommenu.activity.HelperActivity;

import info.sumantv.flow.R;
import info.sumantv.flow.retrofitcall.ApiClient;
import info.sumantv.flow.utils.Utility;
import info.sumantv.flow.utils.expendableRecyclerviewNew.PreferenceNewCategory;

import java.util.ArrayList;

/**
 * Created by Uday Kumay Vurukonda on 12/1/2018.
 * <p>
 * Mr.Psycho
 */
public class ExpandableListAdapter_Preference extends BaseExpandableListAdapter {

    private Context _context;
    private ArrayList<PreferenceNewCategory> industriesList; // header titles
    ImageView imageViewarrow;
    RelativeLayout selectAllLayout;
    Boolean isFromRegister;


    public ExpandableListAdapter_Preference(Context context,Boolean isFromRegister, ArrayList<PreferenceNewCategory> industriesList) {
        this._context = context;
        this.isFromRegister = isFromRegister;
        this.industriesList = industriesList;

    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.industriesList.get(groupPosition).getCategories().get(childPosititon).getPreferenceName();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.exlistview_item_item_preference, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);
        RelativeLayout list_item_child = (RelativeLayout) convertView.findViewById(R.id.list_item_child);
        ImageView imageviewSelected = (ImageView) convertView.findViewById(R.id.imageviewSelected);
        View view = (View) convertView.findViewById(R.id.view);
        CheckBox cbItem = (CheckBox) convertView
                .findViewById(R.id.cbItem);
        txtListChild.setText(childText);
        cbItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheked) {
                //
            }
        });


        if (childPosition == 0) {
            view.setVisibility(View.GONE);
            boolean isActive = industriesList.get(groupPosition).getIsSelected();
            if (isActive) {
                txtListChild.setTypeface(Utility.getTypeface(9, _context));
                imageviewSelected.setImageDrawable(_context.getResources().getDrawable(R.drawable.ic_selected));
                cbItem.setChecked(true);

            } else {

                txtListChild.setTypeface(Utility.getTypeface(9, _context));
                imageviewSelected.setImageDrawable(_context.getResources().getDrawable(R.drawable.ic_uncheck));
                cbItem.setChecked(false);

            }
            list_item_child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        if(!isFromRegister){
                            ((HelperActivity) v.getContext()).updatePreferencesVi();
                        }
                        if (cbItem.isChecked()) {
                            cbItem.setChecked(false);
                            imageviewSelected.setImageDrawable(_context.getResources().getDrawable(R.drawable.ic_add_audition));
                        } else {
                            cbItem.setChecked(true);
                            imageviewSelected.setImageDrawable(_context.getResources().getDrawable(R.drawable.ic_selected));
                        }
                }
            });
//            ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) imageviewSelected.getLayoutParams();
//            marginParams.setMargins((int) _context.getResources().getDimension(R.dimen._30sdp), 0, 0, 0);

        } else {
            view.setVisibility(View.VISIBLE);
            txtListChild.setTypeface(Utility.getTypeface(11, _context));
            boolean isActive = industriesList.get(groupPosition).getCategories().get(childPosition).getIsSelected();
            if (isActive) {
                txtListChild.setTypeface(Utility.getTypeface(11, _context));
                imageviewSelected.setImageDrawable(_context.getResources().getDrawable(R.drawable.ic_selected));
                cbItem.setChecked(true);
            } else {
                txtListChild.setTypeface(Utility.getTypeface(11, _context));
                imageviewSelected.setImageDrawable(_context.getResources().getDrawable(R.drawable.ic_add_audition));
                cbItem.setChecked(false);
            }
            list_item_child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (CommonAccessPermissionOfCeleb.preferencePermissonAvailabilty(_context, false, false)) {
                        if(!isFromRegister){
                            ((HelperActivity) v.getContext()).updatePreferencesVi();
                        }
                        if (cbItem.isChecked()) {
                            imageviewSelected.setImageDrawable(_context.getResources().getDrawable(R.drawable.ic_add_audition));
//                            iCheckBoxListner.onCheckBoxClicked(groupPosition, childPosition, false);
                        } else {

                            imageviewSelected.setImageDrawable(_context.getResources().getDrawable(R.drawable.ic_selected));
//                            iCheckBoxListner.onCheckBoxClicked(groupPosition, childPosition, true);
                        }
//                    }
                }
            });
//            ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) imageviewSelected.getLayoutParams();
//            marginParams.setMargins((int) _context.getResources().getDimension(R.dimen._45sdp), 0, 0, 0);

        }
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.industriesList.get(groupPosition).getCategories().size();

    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.industriesList.get(groupPosition).getPreferenceName();
    }

    @Override
    public int getGroupCount() {
        return this.industriesList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.exlistview_item_group_prefence, null);
        }


        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        TextView sub = (TextView) convertView.findViewById(R.id.list_item_text_subscriptions);
        ImageView imageviewLogo = (ImageView) convertView.findViewById(R.id.imageviewLogo);
        ImageView imageviewSelectAll = (ImageView) convertView.findViewById(R.id.imageviewSelectAll);
        imageViewarrow = (ImageView) convertView.findViewById(R.id.imageViewarrow);
        selectAllLayout = convertView.findViewById(R.id.selectAllLayout);
        selectAllLayout.setVisibility(View.GONE);
        CheckBox cbItem_selectall = (CheckBox) convertView
                .findViewById(R.id.chechkboxSelectAll);

        cbItem_selectall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheked) {
                //
            }
        });
        boolean isActive = false;
        if(industriesList.get(groupPosition).getIsSelected() != null){
            isActive = industriesList.get(groupPosition).getIsSelected();
        }
        if (isActive) {
            imageviewSelectAll.setImageDrawable(_context.getResources().getDrawable(R.drawable.ic_uncheck));
            imageviewSelectAll.setColorFilter(ContextCompat.getColor(_context, R.color.skyblueNew), android.graphics.PorterDuff.Mode.MULTIPLY);
            cbItem_selectall.setChecked(true);
        } else {
            imageviewSelectAll.setImageDrawable(_context.getResources().getDrawable(R.drawable.ic_uncheck));
            imageviewSelectAll.setColorFilter(ContextCompat.getColor(_context, R.color.navagation_view_selected_item), android.graphics.PorterDuff.Mode.MULTIPLY);
            cbItem_selectall.setChecked(false);
        }
        imageviewSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(!isFromRegister){
                        ((HelperActivity) v.getContext()).updatePreferencesVi();
                    }
                    if (cbItem_selectall.isChecked()) {
                        cbItem_selectall.setChecked(false);
                        imageviewSelectAll.setImageDrawable(_context.getResources().getDrawable(R.drawable.ic_add_audition));
                    } else {
                        cbItem_selectall.setChecked(true);
                        imageviewSelectAll.setImageDrawable(_context.getResources().getDrawable(R.drawable.ic_selected));
                    }
            }
        });

        Glide.with(_context)
                .load(ApiClient.BASE_URL + industriesList.get(groupPosition).getLogoURL())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.logo_with_text)
                .into(imageviewLogo);
        if (industriesList.get(groupPosition).selection.size() > 0) {
            sub.setText(industriesList.get(groupPosition).selection.toString());
        } else {
            sub.setText("");
        }


        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void expendableActions(int position) {
        selectAllLayout.setVisibility(View.VISIBLE);
        notifyDataSetChanged();
    }
}

