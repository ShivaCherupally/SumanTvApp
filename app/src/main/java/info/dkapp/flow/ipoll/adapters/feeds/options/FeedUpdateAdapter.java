package info.dkapp.flow.ipoll.adapters.feeds.options;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.dkapp.flow.appmanagers.feed.models.Feed;

import info.dkapp.flow.R;
import info.dkapp.flow.celebflow.constants.Constants;
import info.dkapp.flow.ipoll.interfaces.feeds.options.IFeedUpdateAdapter;
import info.dkapp.flow.ipoll.viewholders.FeedEditViewHolder;
import info.dkapp.flow.retrofitcall.ApiClient;
import info.dkapp.flow.retrofitcall.SessionManager;
import info.dkapp.flow.userflow.Util.Common;
import info.dkapp.flow.utils.Utility;

/**
 * Created by User on 20-08-2018.
 **/

public class FeedUpdateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Feed feed;
    Context context;
    IFeedUpdateAdapter iFeedUpdateAdapter;


    public FeedUpdateAdapter(Feed feed, Context context, IFeedUpdateAdapter iFeedUpdateAdapter) {
        this.feed = feed;
        this.context = context;
        this.iFeedUpdateAdapter = iFeedUpdateAdapter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Constants.HEADER_VIEW) {
            return new FeedUpdateHeader(LayoutInflater.from(parent.getContext()).inflate(R.layout.ipoll_feed_update_header, parent, false));
        } else
            return new FeedEditViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.ipoll_feed_edit_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        bind(holder, position);
    }

    private void bind(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FeedUpdateHeader) {
            FeedUpdateHeader feedUpdateHeader = (FeedUpdateHeader) holder;

           /* if (SessionManager.userLogin.profilePic != null
                    && !SessionManager.userLogin.profilePic.isEmpty()) {
                feedUpdateHeader.imgUser.setImageURI(Uri.parse(SessionManager.userLogin.profilePic));
            } else {
//                feedHeaderViewHolder.imgUser.setImageURI(Uri.parse(R.drawable.user_profilee));
                Glide.with(context).load(R.drawable.appiconandroid).into(feedUpdateHeader.imgUser);
            }*/

            if (SessionManager.userLogin.profilePic != null
                    && !SessionManager.userLogin.profilePic.isEmpty()) {
                Glide.with(context).load(ApiClient.BASE_URL + SessionManager.userLogin.profilePic)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_grey_celebkonect_logo)
                        .into(feedUpdateHeader.imgUser);
            } else {
                feedUpdateHeader.imgUser.setImageResource(R.drawable.ic_grey_celebkonect_logo);
            }


            if (SessionManager.userLogin.firstName != null
                    && !SessionManager.userLogin.firstName.isEmpty()) {
                feedUpdateHeader.tvUserName.setText(SessionManager.userLogin.firstName);
            } else {
                feedUpdateHeader.tvUserName.setText("");
            }
//            feedUpdateHeader.imgUser.setImageURI(Constants.SAMPLE_USER_PROFILE_PIC);
            feedUpdateHeader.etTitle.setText(feed.feedTitle);
            feedUpdateHeader.etDescription.setText(feed.feedDescription);
            feedUpdateHeader.tvAddLocation.setText(feed.location != null ? feed.location : "Location");

            feedUpdateHeader.etTitle.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (feed.feedTitle != null && !feed.feedTitle.equals("" + s)) {
                        iFeedUpdateAdapter.addTitle("" + s);
                    }
                    feed.feedTitle = "" + s;
                }
            });

            feedUpdateHeader.etDescription.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    if (feed.feedDescription != null && !feed.feedDescription.equals("" + s)) {
                        iFeedUpdateAdapter.addDescription("" + s);
                    }
                    feed.feedDescription = "" + s;
                }
            });

            feedUpdateHeader.llLocation.setOnClickListener(v -> {
                iFeedUpdateAdapter.openPlacesSearch();
            });
        } else if (holder instanceof FeedEditViewHolder) {
            position = position - 1;
            FeedEditViewHolder feedEditViewHolder = (FeedEditViewHolder) holder;
            feedEditViewHolder.imgFeed.setLayoutParams(Utility.getLayoutParams(1 / feed.mediaList.get(position).ratio, Utility.getScreenWidth(context)));
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Utility.addMediaThumbnailUrl(feed.mediaList.get(position)))
                    .setResizeOptions(Utility.getResizeOptions(feed.mediaList.get(position).ratio, context))
                    .build();
            Common.getInstance().setGIFImageToFresco(feedEditViewHolder.imgFeed, request, feed.mediaList.get(position), true);

            //feedEditViewHolder.imgFeed.setImageURI(Utility.addMediaUrl(feed.mediaList.get(position)));
            feedEditViewHolder.etFeedCaption.setText(feed.mediaList.get(position).caption);
            feedEditViewHolder.imgMediaType.setVisibility(feed.mediaList.get(position).type != null && feed.mediaList.get(position).type.equals(Constants.MEDIA_TYPE_VIDEO) ? View.VISIBLE : View.GONE);
            if (feed.mediaList.get(position).type != null && feed.mediaList.get(position).type.equals(Constants.MEDIA_TYPE_GIF)) {
                feedEditViewHolder.imgMediaType.setImageResource(R.drawable.gif_icon);
                feedEditViewHolder.imgMediaType.setVisibility(View.VISIBLE);
            }


            final int mediaPosition = position;
            feedEditViewHolder.etFeedCaption.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (feed.mediaList.get(mediaPosition).caption != null && !feed.mediaList.get(mediaPosition).caption.equals("" + s)) {
                        iFeedUpdateAdapter.addMediaCaption(feed.mediaList.get(mediaPosition), "" + s, mediaPosition);
                    }
                    feed.mediaList.get(mediaPosition).caption = "" + s;
                }
            });

            feedEditViewHolder.imgClose.setOnClickListener(v -> {
                iFeedUpdateAdapter.removeMedia(feed.mediaList.get(mediaPosition), mediaPosition);
            });
            int finalPosition = position;
            feedEditViewHolder.imgFeed.setOnClickListener(v -> {
                try {
                    if (feed.mediaList.get(finalPosition).type.equals(Constants.MEDIA_TYPE_VIDEO)) {
                        Common.getInstance().openVideoPlayer(context, Utility.addVideoUrl(feed.mediaList.get(finalPosition)), "","", -1, -1, true,"");
                    } else {
                        Common.getInstance().openMediaDetailsFragment("", "", (Activity) context, null, feed.mediaList, finalPosition, false, false, -1, "", false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } else {

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return Constants.HEADER_VIEW;
        } else {
            return position;
        }
    }

    @Override
    public int getItemCount() {
        return (feed.mediaList == null ? 0 : feed.mediaList.size()) + 1;
    }

    public class FeedUpdateHeader extends RecyclerView.ViewHolder {

        @BindView(R.id.llLocation)
        LinearLayout llLocation;

        @BindView(R.id.imgOptions)
        ImageView imgOptions;

        @BindView(R.id.tvAddLocation)
        TextView tvAddLocation;

        @BindView(R.id.etTitle)
        EditText etTitle;

        @BindView(R.id.etDescription)
        EditText etDescription;

        @BindView(R.id.imgUser)
        SimpleDraweeView imgUser;

        @BindView(R.id.tvUserName)
        TextView tvUserName;


        public FeedUpdateHeader(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
