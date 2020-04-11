package info.dkapp.flow.ipoll.adapters.feeds.options;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.dkapp.flow.appmanagers.feed.models.Media;
import info.dkapp.flow.bottommenu.activity.HelperActivity;

import info.dkapp.flow.R;
import info.dkapp.flow.celebflow.constants.Constants;
import info.dkapp.flow.celebflow.constants.MediaType;
import info.dkapp.flow.ipoll.interfaces.feeds.options.IFeedEditAdapter;
import info.dkapp.flow.ipoll.viewholders.FeedEditViewHolder;
import info.dkapp.flow.userflow.Util.Common;
import info.dkapp.flow.utils.Logger;
import info.dkapp.flow.utils.Utility;
import info.dkapp.flow.imagepicker.model.Image;

/**
 * Created by User on 06-08-2018.
 **/

public class FeedEditAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<Image> list;
    Context context;
    IFeedEditAdapter iFeedEditAdapter;

    public FeedEditAdapter(List<Image> list, Context context, IFeedEditAdapter iFeedEditAdapter) {
        this.list = list;
        this.context = context;
        this.iFeedEditAdapter = iFeedEditAdapter;
    }

    @Override
    public int getItemViewType(int position) {
        return position == list.size() ? Constants.FOOTER_BOTTOM : position;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Constants.FOOTER_BOTTOM) {
            return new AddMoreMediaViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.ipoll_add_more_media, parent, false));
        } else
            return new FeedEditViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.ipoll_feed_edit_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        bind(holder, position);
    }

    private void bind(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof AddMoreMediaViewHolder) {
            AddMoreMediaViewHolder addMoreMediaViewHolder = (AddMoreMediaViewHolder) holder;
            addMoreMediaViewHolder.llAddMoreMedia.setOnClickListener(v -> {
                iFeedEditAdapter.addMoreMedia();
            });
        } else if (holder instanceof FeedEditViewHolder) {
            FeedEditViewHolder feedEditViewHolder = (FeedEditViewHolder) holder;
            feedEditViewHolder.imgFeed.setLayoutParams(Utility.getLayoutParams(1 / list.get(position).ratio, Utility.getScreenWidth(context)));
            //feedEditViewHolder.imgFeed.setImageURI(list.get(position).uri);
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(list.get(position).uri)
                    .setResizeOptions(Utility.getResizeOptions(list.get(position).ratio, context))
                    .build();

            PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                    .setOldController(feedEditViewHolder.imgFeed.getController())
                    .setImageRequest(request)
                    .build();
            feedEditViewHolder.imgFeed.setController(controller);
            Logger.d("MEDIA TYPE", "" + list.get(position).mediaType);
            feedEditViewHolder.imgMediaType.setVisibility(list.get(position).mediaType != null && list.get(position).mediaType == MediaType.VIDEO ? View.VISIBLE : View.GONE);
            if (list.get(position).mimeType != null && list.get(position).mimeType.contains(Constants.MEDIA_TYPE_GIF)) {
                feedEditViewHolder.imgMediaType.setImageResource(R.drawable.gif_icon);
                feedEditViewHolder.imgMediaType.setVisibility(View.VISIBLE);
            }

            feedEditViewHolder.etFeedCaption.setText(list.get(position).caption != null ? list.get(position).caption : "");


            feedEditViewHolder.etFeedCaption.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (list.get(position).caption != null && !list.get(position).caption.equals("" + s)) {
                        iFeedEditAdapter.addCaptionGalleryFile(list.get(position), "" + s, position);
                    }
                    list.get(position).caption = "" + s;
                }
            });

            feedEditViewHolder.imgClose.setOnClickListener(v -> {
                iFeedEditAdapter.removeGalleryFile(list.get(position), position);
            });

            feedEditViewHolder.imgCrop.setOnClickListener(v -> {
                iFeedEditAdapter.cropGalleryFile(list.get(position), position);
            });
            feedEditViewHolder.imgFeed.setOnClickListener(v -> {
                try {
                    if (list.get(position).mimeType != null && list.get(position).mimeType.contains(Constants.MEDIA_TYPE_VIDEO)) {
                        Common.getInstance().openVideoPlayer(context, list.get(position).uri, "","",-1, -1, true,"");

                    } else {
                        Media media = new Media();
                        media.uri = list.get(position).uri;
                        if (list.get(position).mimeType != null && list.get(position).mimeType.contains(Constants.MEDIA_TYPE_GIF)) {
                            media.type = Constants.MEDIA_TYPE_GIF;
                        } else {
                            media.type = list.get(position).mimeType;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } else {

        }
    }


    @Override
    public int getItemCount() {
        return list.size() + 1;
    }


    public class AddMoreMediaViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.llAddMoreMedia)
        LinearLayout llAddMoreMedia;

        @BindView(R.id.tvAddMoreMedia)
        TextView tvAddMoreMedia;

        public AddMoreMediaViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
