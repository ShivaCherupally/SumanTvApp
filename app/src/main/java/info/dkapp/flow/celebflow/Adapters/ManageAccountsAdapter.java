package info.dkapp.flow.celebflow.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;

import info.dkapp.flow.R;
import info.dkapp.flow.celebflow.interfaces.IManageAccountsAdapter;
import info.dkapp.flow.celebflow.modelData.MultipleAccountModel;
import info.dkapp.flow.retrofitcall.ApiClient;
import info.dkapp.flow.userflow.Util.Common;

import java.util.ArrayList;

public class ManageAccountsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<MultipleAccountModel> arrayList;
    private Context context;
    private IManageAccountsAdapter iManageAccountsAdapter;

    public ManageAccountsAdapter(ArrayList<MultipleAccountModel> newChatDataModelArrayList, Context context, IManageAccountsAdapter iManageAccountsAdapter) {
        this.arrayList = newChatDataModelArrayList;
        this.context = context;
        this.iManageAccountsAdapter = iManageAccountsAdapter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = R.layout.item_manage_account;
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
            if (!Common.getInstance().IsNull(arrayList.get(position).image)) {
                Common.getInstance().setGlideImage(context, ApiClient.BASE_URL + arrayList.get(position).image,viewHolder.ivProfileImage);
            }
            viewHolder.btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        iManageAccountsAdapter.removeAccount(arrayList.get(position),position);
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

        @BindView(R.id.btnRemove)
        Button btnRemove;

        public AdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
