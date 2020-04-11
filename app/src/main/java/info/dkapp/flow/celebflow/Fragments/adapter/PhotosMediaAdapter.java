package info.dkapp.flow.celebflow.Fragments.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.dkapp.flow.R;
import info.dkapp.flow.appmanagers.feed.models.Media;
import info.dkapp.flow.celebflow.constants.Constants;
import info.dkapp.flow.userflow.Util.Common;
import info.dkapp.flow.utils.Utility;

public class PhotosMediaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Media> media;
    private int gridType = 0;
    private boolean fromAuditionPage;
    private String celebId;

    public PhotosMediaAdapter(Context context, List<Media> media, int gridType, Boolean isSelfProfile,
                              Boolean isMember, boolean fromAuditionPage, String celebId) {
        this.context = context;
        this.media = media;
        this.gridType = gridType;
        this.fromAuditionPage = fromAuditionPage;
        this.celebId = celebId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = R.layout.photos_media_item;
        return new PhotosMediaAdapter.PhotosMediaViewHolder(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PhotosMediaViewHolder) {
            PhotosMediaViewHolder photosMediaViewHolder = (PhotosMediaViewHolder) holder;
            if (media.get(position).type.equals("image")) {
                photosMediaViewHolder.iVPlayButton.setVisibility(View.GONE);
            } else if (media.get(position).type.equals("video")) {
                photosMediaViewHolder.iVPlayButton.setVisibility(View.VISIBLE);
            } else if (media.get(position).type.equals(Constants.MEDIA_TYPE_GIF)) {
                photosMediaViewHolder.iVPlayButton.setVisibility(View.VISIBLE);
                photosMediaViewHolder.iVPlayButton.setImageResource(R.drawable.gif_icon);
            }

            int singleItemWidth = (Utility.getScreenWidth(context) / gridType) - 10;

           /* if (gridType > 1) {
                photosMediaViewHolder.itemView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, singleItemWidth));
            } else {
                photosMediaViewHolder.itemView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }*/

            photosMediaViewHolder.photoimage.setVisibility(View.VISIBLE);
            photosMediaViewHolder.ivImage.setVisibility(View.GONE);
            //
            Uri uri = Utility.addMediaThumbnailUrl(media.get(position));
            double ratio = 0.0;
            if (media.get(position).ratio != null & media.get(position).ratio != 0.0) {
                ratio = media.get(position).ratio;
            }

            //(int)((double)singleItemWidth*ratio)
            if (ratio == 0.0) {
                photosMediaViewHolder.itemView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            } else {
                photosMediaViewHolder.itemView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) ((double) singleItemWidth * ratio)));
            }
//           Common.getInstance().setGlideImage(context,media.get(position).source.urlthumbnail,photosMediaViewHolder.photoimage);
            photosMediaViewHolder.photoimage.setImageURI(uri);
            // Common.getInstance().setFrescoImage(uri,photosMediaViewHolder.photoimage,singleItemWidth, singleItemWidth, media.get(position).facePointList);
            //
//            photosMediaViewHolder.photoimage.setVisibility(View.GONE);
//            photosMediaViewHolder.ivImage.setVisibility(View.VISIBLE);
//            Common.getInstance().setGlideImageFaceDetector(context,uri.toString(),photosMediaViewHolder.ivImage,media.get(position).facePointList);
            //
            photosMediaViewHolder.itemView.setOnClickListener(v -> {
                if (media.get(position).type.equals("video")) {
                    if (media.get(position).source.videoUrl != null && !media.get(position).source.videoUrl.isEmpty()) {
                        String image_url = media.get(position).source.videoUrl;
                        Uri uriVideo = Utility.addVideoUrl(image_url);

                        Common.getInstance().openVideoPlayer(context, uriVideo, media.get(position).feedId, media.get(position)._id, -1, -1, false,"MediaSection");
                    }
                } else {
                    int startIndex, endIndex, newPosition, length = 10;
                    if (position > 10) {
                        startIndex = position - length;
                    } else {
                        startIndex = 0;
                    }
                    if (media.size() > length + position) {
                        endIndex = position + length;
                    } else {
                        endIndex = media.size();
                    }
                    List<Media> subMedia = new ArrayList<Media>(media.subList(startIndex, endIndex));
                    if (position > length) {
                        newPosition = length;
                    } else {
                        newPosition = position;
                    }

                }
            });
            int padding = 15;
            photosMediaViewHolder.itemView.setPadding(padding, padding, padding, padding);
        }
    }

    @Override
    public int getItemCount() {
        return media.size();
    }

    public class PhotosMediaViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.photoimage)
        SimpleDraweeView photoimage;

        @BindView(R.id.iVPlayButton)
        ImageView iVPlayButton;

        @BindView(R.id.ivImage)
        ImageView ivImage;

        public PhotosMediaViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void loadmore(List<Media> mediaListsub) {
        List<Media> appendList = (List<Media>) (List<?>) mediaListsub;
        int positionStart = getItemCount() + 1;
        notifyItemRangeInserted(positionStart, appendList.size());
    }
}
