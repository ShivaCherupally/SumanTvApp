package info.sumantv.flow.stories.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;


import info.sumantv.flow.R;
import info.sumantv.flow.imagepicker.model.Image;

/**
 * Created by Uday Kumay Vurukonda on 24-Oct-19.
 * <p>
 * Mr.Psycho
 */
public class MultipleImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public List<Image> list;
    IMultipleOnclick iMultipleOnclick;
    Context context;

    public MultipleImageAdapter(Context context, List<Image> list,IMultipleOnclick iMultipleOnclick) {
        this.list = list;
        this.context = context;
        this.iMultipleOnclick = iMultipleOnclick;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MultipleImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.multiple_image_item,
                parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MultipleImageViewHolder) {
            MultipleImageViewHolder multipleImageViewHolder = (MultipleImageViewHolder) holder;
            Glide.with(context).load(list.get(position).uri)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_profile_circle_pleace_holder)
                    .into(multipleImageViewHolder.imaegViewMultiple);
            multipleImageViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iMultipleOnclick.IMultipleOnclick(position);

                }
            });
            multipleImageViewHolder.iVDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iMultipleOnclick.IMultipleOnclickDelete(position);
                }
            });
        }
    }
    public void loadmore(List<Image> fragmentList) {
        ArrayList<Image> appendList = (ArrayList<Image>) (List<?>) fragmentList;
        int positionStart = getItemCount() + 1;
        notifyItemRangeInserted(positionStart, appendList.size());
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MultipleImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView imaegViewMultiple,iVDelete;
        RelativeLayout relatavieLayout;


        public MultipleImageViewHolder(View itemView) {
            super(itemView);
            imaegViewMultiple = itemView.findViewById(R.id.imaegViewMultiple);
            iVDelete = itemView.findViewById(R.id.iVDelete);
            relatavieLayout = itemView.findViewById(R.id.relatavieLayout);
        }
    }
}
