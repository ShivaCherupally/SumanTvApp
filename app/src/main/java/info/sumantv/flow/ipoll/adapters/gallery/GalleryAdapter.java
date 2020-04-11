package info.sumantv.flow.ipoll.adapters.gallery;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.sumantv.flow.appmanagers.feed.models.GalleryFile;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.constants.MediaType;
import info.sumantv.flow.ipoll.interfaces.gallery.IGalleryAdapter;
import info.sumantv.flow.utils.Utility;

/**
 * Created by User on 30-07-2018.
 **/

public class GalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<GalleryFile> list;
    Context context;
    IGalleryAdapter iGalleryAdapter;


    public GalleryAdapter(Context context, List<GalleryFile> list, IGalleryAdapter iGalleryAdapter) {
        this.list = list;
        this.context = context;
        this.iGalleryAdapter = iGalleryAdapter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GalleryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.ipoll_gallery_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,final int position) {
        if (holder instanceof GalleryViewHolder) {
            GalleryViewHolder galleryViewHolder = (GalleryViewHolder) holder;
            Glide
                    .with(context)
                    .load(list.get(position).uri)
                    .skipMemoryCache(true)
                    .centerCrop()
                    .into(galleryViewHolder.imgGallery);

            galleryViewHolder.imgTag.setVisibility(list.get(position).mediaType == MediaType.IMAGE ? View.GONE : View.VISIBLE);
            galleryViewHolder.rlSelect.setVisibility(list.get(position).isSelect?View.VISIBLE:View.GONE);

            galleryViewHolder.itemView.setOnClickListener(v ->
            {
                iGalleryAdapter.click(list.get(position),position);

            });

            galleryViewHolder.itemView.setOnLongClickListener(v -> {
//                    iGalleryAdapter.longPressed(list.get(position),position);
                    return false;
                });

        }


        holder.itemView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Utility.getScreenWidth(context) / 3));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class GalleryViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.imgTag)
        ImageView imgTag;

        @BindView(R.id.rlCover)
        RelativeLayout rlCover;

        @BindView(R.id.rlHeader)
        RelativeLayout rlHeader;

        @BindView(R.id.imgGallery)
        ImageView imgGallery;

        @BindView(R.id.rlSelect)
        RelativeLayout rlSelect;

        public GalleryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
