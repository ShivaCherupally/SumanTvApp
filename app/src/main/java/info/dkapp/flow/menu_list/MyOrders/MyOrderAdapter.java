package info.dkapp.flow.menu_list.MyOrders;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import info.dkapp.flow.bottommenu.viewholders.SkeletonViewHolder;

import info.dkapp.flow.R;
import info.dkapp.flow.retrofitcall.ApiClient;
import info.dkapp.flow.userflow.Util.Common;
import info.dkapp.flow.userflow.Util.DateUtil;

/**
 * Created by Praveen Lakkireddy on 16-03-2018.
 */

public class MyOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<MyOrderData> itemList;
    Context mContext;
    boolean isLoading;

    public MyOrderAdapter(List<MyOrderData> cartList, boolean isLoad) {
        this.itemList = cartList;
        this.isLoading = isLoad;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (isLoading) {
            int layout = R.layout.skeleton_order_item;
            return new SkeletonViewHolder(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
        } else {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_items,
                    parent, false);
            mContext = parent.getContext();
            return new ViewHolder(itemView);

        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            format.setTimeZone(TimeZone.getTimeZone("UTC"));
            SimpleDateFormat formatDate = new SimpleDateFormat("hh:mm a");

            try {
                if (itemList.get(position).slotId != null) {
                    viewHolder.tvTitle.setText(Common.getInstance().convertFirstLetterToCapital(itemList.get(position).serviceType) + " slot booked with " + Common.getInstance().convertFirstLetterToCapital(itemList.get(position).celebFirstName) + " " + itemList.get(position).celebLastName);
                    viewHolder.tvTimeDate.setText("From: "+formatDate.format(format.parse(itemList.get(position).startTime)) + " - " + formatDate.format(format.parse(itemList.get(position).endTime)) + " on " + DateUtil.getCompleteDate(itemList.get(position).startTime));
                    viewHolder.tvOrderTime.setText(formatDate.format(format.parse(itemList.get(position).createdAt)));
                    Glide.with(mContext)
                            .load(ApiClient.BASE_URL + itemList.get(position).celebProfilepic)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .error(R.drawable.ic_profile_square_pleace_holder)
                            .into(viewHolder.iVProfilePic);
                } else {
                    viewHolder.tvTitle.setText("Added credits to your wallet");
                    viewHolder.tvTimeDate.setText("From: " + itemList.get(position).paymentMode);
                    viewHolder.tvOrderTime.setText(formatDate.format(format.parse(itemList.get(position).createdAt)));
                    viewHolder.iVProfilePic.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_added_credits));

                }
                viewHolder.tvCredits.setText(itemList.get(position).credits + "");
                viewHolder.tvStatus.setText(itemList.get(position).ordersStatus);
                if (itemList.get(position).ordersStatus.equalsIgnoreCase("Success")) {
                    viewHolder.iVStatusImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_success));
                } else {
                    viewHolder.iVStatusImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_failed));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        if (isLoading) {
            return 10;
        }
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvTimeDate, tvOrderTime, tvStatus, tvCredits;
        ImageView iVProfilePic, iVStatusImage;

        public ViewHolder(View itemView) {
            super(itemView);
            tvOrderTime = (TextView) itemView.findViewById(R.id.tvOrderTime);
            tvTimeDate = (TextView) itemView.findViewById(R.id.tvTimeDate);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvStatus = (TextView) itemView.findViewById(R.id.tvStatus);
            tvCredits = (TextView) itemView.findViewById(R.id.tvCredits);
            iVProfilePic = (ImageView) itemView.findViewById(R.id.iVProfilePic);
            iVStatusImage = (ImageView) itemView.findViewById(R.id.iVStatusImage);
        }
    }

    public void loadmore(List<MyOrderData> celebrityList) {
        ArrayList<MyOrderData> appendList = (ArrayList<MyOrderData>) (List<?>) celebrityList;
        int positionStart = getItemCount() + 1;
        notifyItemRangeInserted(positionStart, appendList.size());
    }
}
