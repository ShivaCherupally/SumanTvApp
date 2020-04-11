package info.sumantv.flow.celebflow.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.interfaces.IMultipleAccountsAdapter;
import info.sumantv.flow.celebflow.modelData.MultipleAccountModel;
import info.sumantv.flow.retrofitcall.ApiClient;
import info.sumantv.flow.userflow.Util.Common;

import java.util.ArrayList;

public class MultipleAccountsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<MultipleAccountModel> arrayList;
    private Context context;
    private IMultipleAccountsAdapter iMultipleAccountsAdapter;
    private Integer screenWidth,screenHeight;

    public MultipleAccountsAdapter(ArrayList<MultipleAccountModel> newChatDataModelArrayList,Integer screenWidth,Integer screenHeight, Context context, IMultipleAccountsAdapter iMultipleAccountsAdapter) {
        this.arrayList = newChatDataModelArrayList;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.context = context;
        this.iMultipleAccountsAdapter = iMultipleAccountsAdapter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = R.layout.item_multiple_account;
        return new AdapterViewHolder(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AdapterViewHolder) {
            AdapterViewHolder viewHolder = (AdapterViewHolder) holder;
            if (!Common.getInstance().IsNull(arrayList.get(position).firstName)) {
                viewHolder.tvUserName.setText(arrayList.get(position).firstName + " " + arrayList.get(position).lastName);
            }
            Integer singleItemWidth = screenWidth/4;
            viewHolder.itemView.getLayoutParams().width = singleItemWidth;
            viewHolder.ivProfileImage.getLayoutParams().width = singleItemWidth;
            viewHolder.ivProfileImage.getLayoutParams().height = singleItemWidth;
            if (!Common.getInstance().IsNull(arrayList.get(position).image)) {
                Common.getInstance().setGlideImage(context, ApiClient.BASE_URL + arrayList.get(position).image,viewHolder.ivProfileImage);
            }
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        iMultipleAccountsAdapter.accountClick(arrayList.get(position));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvUserName)
        TextView tvUserName;

        @BindView(R.id.ivProfileImage)
        ImageView ivProfileImage;

        public AdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
