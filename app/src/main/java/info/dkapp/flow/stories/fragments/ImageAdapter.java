package info.dkapp.flow.stories.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;


import info.dkapp.flow.R;
import info.dkapp.flow.retrofitcall.ApiClient;

/**
 * Created by Ahmed Adel on 5/4/17.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private ArrayList<String > imageBitmaps;
    private LayoutInflater inflater;
    Context context;
    private OnImageClickListener onImageClickListener;

    public ImageAdapter(@NonNull Context context, @NonNull ArrayList<String> imageBitmaps) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.imageBitmaps = imageBitmaps;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_photo_edit_image_item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context)
                .load(ApiClient.BASE_URL + imageBitmaps.get(position))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_profile_square_pleace_holder)
                .into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onImageClickListener != null)
                    onImageClickListener.onImageClickListener(ApiClient.BASE_URL + imageBitmaps.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (imageBitmaps != null) {
            if (imageBitmaps.size() != 0) {
                return imageBitmaps.size();
            }
        }
        return 0;
    }

    public void setOnImageClickListener(OnImageClickListener onImageClickListener) {
        this.onImageClickListener = onImageClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.fragment_photo_edit_image_iv);

        }
    }

    public interface OnImageClickListener {
        void onImageClickListener(String image);
    }
}
