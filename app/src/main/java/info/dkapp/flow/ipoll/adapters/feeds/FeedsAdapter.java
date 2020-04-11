package info.dkapp.flow.ipoll.adapters.feeds;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.dkapp.flow.appmanagers.feed.models.Feed;
import info.dkapp.flow.appmanagers.feed.models.Media;
import info.dkapp.flow.appmanagers.feed.models.SuggestionsModel;
import info.dkapp.flow.bottommenu.activity.HelperActivity;
import info.dkapp.flow.bottommenu.constants.RController;
import info.dkapp.flow.bottommenu.generic.EmptyDataAdapter;
import info.dkapp.flow.bottommenu.viewholders.LoaderViewHolder;
import info.dkapp.flow.bottommenu.viewholders.RetryViewHolder;
import info.dkapp.flow.bottommenu.viewholders.SkeletonViewHolder;


import info.dkapp.flow.R;
import info.dkapp.flow.celebflow.constants.Constants;
import info.dkapp.flow.databaseutil.appstart.AppController;
import info.dkapp.flow.ipoll.adapters.feeds.celebrities.FeedSuggestionsAdapter;
import info.dkapp.flow.ipoll.adapters.feeds.celebrities.FeedSuggestionsOnWelcomeAdapter;
import info.dkapp.flow.ipoll.adapters.feeds.celebrities.OnlineCelebritiesAdapter;
import info.dkapp.flow.ipoll.adapters.feeds.celebrities.StoriesAdapter;
import info.dkapp.flow.ipoll.dialogs.custom.AddCommentDialog;
import info.dkapp.flow.ipoll.fragments.feeds.FeedsFragment;
import info.dkapp.flow.ipoll.fragments.feeds.comments.AddCommentParams;
import info.dkapp.flow.ipoll.interfaces.feeds.IFeedAdapter;
import info.dkapp.flow.ipoll.interfaces.recyclerview.IRecyclerViewAdapter;
import info.dkapp.flow.ipoll.viewholders.CreateStoriesViewHolder;
import info.dkapp.flow.ipoll.viewholders.FeedViewHolder;
import info.dkapp.flow.ipoll.viewholders.OnlineCelebritiesViewHolder;
import info.dkapp.flow.retrofitcall.ApiClient;
import info.dkapp.flow.retrofitcall.SessionManager;
import info.dkapp.flow.stories.models.StoryProfileInfo;
import info.dkapp.flow.userflow.Util.Common;
import info.dkapp.flow.utils.Utility;

import static info.dkapp.flow.bottommenu.activity.BottomMenuActivity.isCurvedBottomBar;

/**
 * Created by User on 11-07-2018.
 **/

public class FeedsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements IRecyclerViewAdapter {
    private List<Feed> list;
    private List<StoryProfileInfo> storyProfileInfoList;
    private Context context;
    public RController rController;
    private IFeedAdapter iFeedAdapter;
    public boolean isUpload = false;
    private Boolean hideHeaders;
    private FeedsAdapter feedsAdapter;
    private OnlineCelebritiesAdapter onlineCelebritiesAdapter;
    private CreateStoriesViewHolder createStoriesViewHolder;
    private OnlineCelebritiesViewHolder onlineCelebritiesViewHolder;

    public FeedsAdapter(List<Feed> list, List<StoryProfileInfo> storyProfileInfoList, Context context, RController rController, IFeedAdapter iFeedAdapter, Boolean hideHeaders) {
        if (context == null)
            context = Utility.getContext();
        if (list == null)
            list = new ArrayList<>();
        if (storyProfileInfoList == null)
            storyProfileInfoList = new ArrayList<>();
        //
        this.list = list;
        this.context = context;
        this.rController = rController;
        this.iFeedAdapter = iFeedAdapter;
        this.hideHeaders = hideHeaders;
        this.storyProfileInfoList = storyProfileInfoList;
        this.feedsAdapter = this;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return rController == RController.LOADING ? 10 : hideHeaders ? list.size() : getTotalListSize();
    }

    private int getTotalListSize() {
        if (hideHeaders) {
            return list.size();
        }
        return (list.size() + 1 + getOnlineCelebItemPosition());//+1 means adding online celebs position
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (rController == RController.LOADING) {
            return new SkeletonViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.skeleton_feed_item, parent, false));
        } /*else if (getSuggestionType(viewType) == Constants.FOOTER_LOADER) {
            return new LoaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.loader_view_holder, parent, false));
        } else if (getSuggestionType(viewType) == Constants.FOOTER_ERROR_RETRY) {
            return new RetryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.retry_view_holder, parent, false));
        }*/ else if (viewType == 0 && Common.isCelebStatus(context) && !hideHeaders) {
            return new FeedHeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.ipoll_feed_add_header, parent, false));
        } else if (showOnlineCelebs(viewType)) {
//            return new OnlineCelebritiesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.online_celebrites_viewholder, parent, false));
            return new CreateStoriesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.stories_viewholder, parent, false));
        } else if (getSuggestionType(viewType) == Constants.FeedConstants.suggestionsOnWelcomeFeed) {
            return new FeedSuggestionsOnWelcomeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_suggestions_on_welcome_layout, parent, false));
        } else if (getSuggestionType(viewType) == Constants.FeedConstants.suggestionsInFeed) {
            return new FeedSuggestionsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_suggestions_layout, parent, false));
        } else if (getSuggestionType(viewType) == Constants.FeedConstants.allCaughtUpFeed) {
            return new FeedAllCaughtUpViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_all_caught_up_layout, parent, false));
        } else if (getSuggestionType(viewType) == Constants.FeedConstants.AdvertisementFeed) {
            return new FeedAdvertisementViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_advertisement_layout, parent, false));
        } else {
            return new FeedViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.ipoll_feed_item, parent, false));
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int orgPosition) {
        LinearLayout llParentCommon = null;
        int position = orgPosition;
        if (holder instanceof FeedViewHolder) {
            position = getPositionWithoutHeaders(position);
            FeedViewHolder feedViewHolder = (FeedViewHolder) holder;
            llParentCommon = feedViewHolder.llParent;
            feedViewHolder.llParent.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            feedViewHolder.ivLikeAnimation.setVisibility(View.GONE);
            feedViewHolder.progressBar.setVisibility(View.GONE);
            if (list.get(position) != null) {
                feedViewHolder.rlFeed.setVisibility(list.get(position).isUpdating || list.get(position).isHide ? View.GONE : View.VISIBLE);
                feedViewHolder.llUpdate.setVisibility(list.get(position).isUpdating ? View.VISIBLE : View.GONE);
                feedViewHolder.llHidePost.setVisibility(list.get(position).isHide ? View.VISIBLE : View.GONE);
            }
            if (list.get(position) == null || list.get(position).feedMemberDetails == null) {
                try {
                    if (position != (list.size() - 1)) {
                        feedViewHolder.llParent.getLayoutParams().height = 1;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("feedMemberDetails", "null @@ " + position);
                return;
            }
            // header
            if (Common.getInstance().IsNullReturnValue(list.get(position).feedMemberDetails.avtar_imgPath, "").length() > 0) {
                feedViewHolder.imgUser.setImageURI(Uri.parse(Constants.MEDIA_BASE_URL + list.get(position).feedMemberDetails.avtar_imgPath));
            } else {
                Glide.with(context).load(R.drawable.ic_grey_celebkonect_logo).into(feedViewHolder.imgUser);
            }
            String userName = list.get(position).feedMemberDetails.firstName + " " + list.get(position).feedMemberDetails.lastName;
            if (!userName.isEmpty()) {
//                feedViewHolder.tvUserName.setText(list.get(position).userName);
                feedViewHolder.tvUserName.setText(Character.toUpperCase(userName.charAt(0)) + userName.substring(1));
            }
            if (list.get(position).feedMemberDetails.profession != null && !list.get(position).feedMemberDetails.profession.isEmpty()) {
                feedViewHolder.tvProfession.setText(Character.toUpperCase(list.get(position).feedMemberDetails.profession.charAt(0))
                        + list.get(position).feedMemberDetails.profession.substring(1));

                if (list.get(position).feedMemberDetails.category != null
                        && !list.get(position).feedMemberDetails.category.isEmpty()) {
                    String professionAndCategory = list.get(position).feedMemberDetails.category
                            + ", " + feedViewHolder.tvProfession.getText().toString();

                    feedViewHolder.tvProfession.setText(professionAndCategory);
                }
            }

            feedViewHolder.tvTimeAgo.setText(Utility.makeDateToAgo(list.get(position).createdDate));
            //feedViewHolder.tvLocation.setText(list.get(position).location);
            //feedViewHolder.tvLocation.setText(Constants.SAMPLE_LOCATION);
            // collage
            int itemPosition = position;
            if (list.get(itemPosition).mediaList != null && list.get(itemPosition).mediaList.size() > 0) {
                if (SessionManager.getInstance().getKeyValue(SessionManager.KEY_FEED_GRID_STYLE, false)) {
                    feedViewHolder.rlParentViewPager.setVisibility(View.VISIBLE);
                    feedViewHolder.collageView.setVisibility(View.GONE);
                    //
                    FeedViewPagerAdapter adapter = new FeedViewPagerAdapter(context, list.get(itemPosition).mediaList, itemPosition, feedsAdapter);
                    feedViewHolder.viewPager.setAdapter(adapter);
                    //feedViewHolder.viewPager.setOffscreenPageLimit(adapter.getCount()-1);
                    feedViewHolder.viewPager.getLayoutParams().height = Utility.getFeedViewPagerHeight(context);
                    //
                    if (list.get(itemPosition).mediaList.size() <= 1) {
                        feedViewHolder.tvPagerCount.setText("");
                        feedViewHolder.tvPagerCount.setVisibility(View.GONE);
                    } else {
                        feedViewHolder.tvPagerCount.setText(1 + "/" + list.get(itemPosition).mediaList.size());
                    }
                    feedViewHolder.viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        }

                        @Override
                        public void onPageSelected(int position) {
                            feedViewHolder.tvPagerCount.setText(position + 1 + "/" + list.get(itemPosition).mediaList.size());
                            //
                            if (FeedsFragment.getInstance() != null) {
                                FeedsFragment.getInstance().stopVideoPlayer(true);
                                if (SessionManager.getInstance().getKeyValue(SessionManager.KEY_AUTO_PLAY_VIDEOS, false) && list.get(itemPosition).mediaList.get(feedViewHolder.viewPager.getCurrentItem()).type.equals(Constants.MEDIA_TYPE_VIDEO)) {
                                    FeedsFragment.getInstance().startAutoPlayVideos(false);
                                }
                            }
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {
                        }
                    });
                } else {
                    feedViewHolder.rlParentViewPager.setVisibility(View.GONE);
                    feedViewHolder.collageView.setVisibility(View.VISIBLE);
                    try {
                        feedViewHolder.collageView.addMedia(list.get(itemPosition).mediaList, Utility.getIPollCollageController(list.get(itemPosition).mediaList), Utility.getScreenWidth(AppController.getInstance().getContext()), true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                feedViewHolder.rlParentViewPager.setVisibility(View.GONE);
                feedViewHolder.collageView.setVisibility(View.GONE);
            }
            // content
            if (list.get(position).mediaList != null && list.get(position).mediaList.size() > 0) {
                feedViewHolder.llMediaContentTextOnly.setVisibility(View.GONE);
                feedViewHolder.llMediaContent.setVisibility(View.VISIBLE);
                Common.getInstance().setFeedContent(feedViewHolder.tvFeedContent, feedViewHolder.tvFeedDescription, feedViewHolder.view_more, position, list.get(position), iFeedAdapter);
            } else {
                feedViewHolder.llMediaContentTextOnly.setVisibility(View.VISIBLE);
                feedViewHolder.llMediaContent.setVisibility(View.GONE);
                Common.getInstance().setFeedContent(feedViewHolder.tvFeedContentTextOnly, feedViewHolder.tvFeedDescriptionTextOnly, feedViewHolder.view_moreTextOnly, position, list.get(position), iFeedAdapter);
            }
            //  footer
            Common.getInstance().setFeedLikeUnLike(feedViewHolder, list.get(position), false);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(
                    0,
                    Utility.dpSize(context, position == 0 ? 0 : 10),
                    Utility.dpSize(context, 0),
                    Utility.dpSize(context, list.size() - 1 == position ? 10 : 0)
            );
            feedViewHolder.llLike.setOnClickListener(v -> {
                    iFeedAdapter.likeAction(list.get(itemPosition), itemPosition, feedViewHolder);
            });
            feedViewHolder.tvLikeCount.setOnClickListener(v -> {
                    iFeedAdapter.userLikes(list.get(itemPosition), itemPosition);
            });
            feedViewHolder.llComment.setOnClickListener(v -> {
                    iFeedAdapter.userComments(list.get(itemPosition), itemPosition);
            });
            feedViewHolder.tvCommentCount.setOnClickListener(v -> {
                    iFeedAdapter.userComments(list.get(itemPosition), itemPosition);
            });
            feedViewHolder.llShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        iFeedAdapter.shareToOther(list.get(itemPosition), itemPosition);
                }
            });
            feedViewHolder.collageView.setCollageItemClickListener(mediaPosition -> {
                collageViewClick(list.get(itemPosition).mediaList, itemPosition, mediaPosition);
            });
            if (list.get(itemPosition).memberId != null && !list.get(itemPosition).memberId.isEmpty()) {
                if (SessionManager.userLogin.userId != null && !SessionManager.userLogin.userId.isEmpty()) {
                    if (list.get(itemPosition).memberId.equals(SessionManager.userLogin.userId)) {
                        feedViewHolder.imgOptions.setVisibility(View.VISIBLE);
                        feedViewHolder.LLKonect.setVisibility(View.GONE);
                    } else {
//                        feedViewHolder.imgOptions.setVisibility(View.GONE);
                        feedViewHolder.imgOptions.setVisibility(View.VISIBLE);
                        feedViewHolder.LLKonect.setVisibility(View.VISIBLE);
                    }
                } else {
//                    feedViewHolder.imgOptions.setVisibility(View.GONE);
                    feedViewHolder.imgOptions.setVisibility(View.VISIBLE);
                    feedViewHolder.LLKonect.setVisibility(View.GONE);
                }
            } else {
                feedViewHolder.LLKonect.setVisibility(View.GONE);
//                feedViewHolder.imgOptions.setVisibility(View.GONE);

                feedViewHolder.imgOptions.setVisibility(View.VISIBLE);
            }

            feedViewHolder.imgOptions.setOnClickListener(v -> {
                iFeedAdapter.openOptionDailog(list.get(itemPosition), itemPosition);
            });

            feedViewHolder.llCelebProfile.setOnClickListener(v -> {
                boolean isSelf = false;
                if (list.get(itemPosition).memberId.equals(SessionManager.userLogin.userId)) {
                    isSelf = true;
                }
                iFeedAdapter.viewCelebDetails(list, itemPosition, isSelf);
            });
            feedViewHolder.LLKonect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iFeedAdapter.openKonect(list.get(itemPosition), itemPosition);
                }
            });
            feedViewHolder.undobtn.setOnClickListener(view -> {
//                ((BottomMenuActivity) context).hideFeed(list.get(itemPosition).id, itemPosition, false);
                if (FeedsFragment.getInstance() != null) {
                    FeedsFragment.getInstance().hidePostFromFeeds(list.get(itemPosition).id, itemPosition, false);
                }
            });
            feedViewHolder.tvAddComment.setOnClickListener(view -> {
                try {
                    AddCommentDialog addCommentDialog = new AddCommentDialog();
                    addCommentDialog.setData(new AddCommentParams().newObj(list.get(itemPosition), true, "", itemPosition, false, feedViewHolder));
                    addCommentDialog.show(AppController.getInstance().getFragmentManager(), "AddCommentDialog");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } else if (holder instanceof OnlineCelebritiesViewHolder) {
            onlineCelebritiesViewHolder = (OnlineCelebritiesViewHolder) holder;
            llParentCommon = onlineCelebritiesViewHolder.llParent;
            onlineCelebritiesViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            onlineCelebritiesViewHolder.tvViewAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, HelperActivity.class);
                    intent.putExtra(Constants.FRAGMENT_TITLE, "Now Online");
                    intent.putExtra(Constants.FRAGMENT_KEY, 8065);// MyCartfragmentOrder
                    intent.putParcelableArrayListExtra("storyProfileInfoList", (ArrayList<? extends Parcelable>) storyProfileInfoList);
                    context.startActivity(intent);
                }
            });
        } else if (holder instanceof CreateStoriesViewHolder) {

            createStoriesViewHolder = (CreateStoriesViewHolder) holder;
            llParentCommon = createStoriesViewHolder.llParent;
            createStoriesViewHolder.storiesRecylist.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

            setStoriesAdapter(storyProfileInfoList);

        } else if (holder instanceof FeedHeaderViewHolder) {

            FeedHeaderViewHolder feedHeaderViewHolder = (FeedHeaderViewHolder) holder;
            llParentCommon = feedHeaderViewHolder.llParent;

            if (SessionManager.userLogin.profilePic != null && !SessionManager.userLogin.profilePic.isEmpty()) {
                feedHeaderViewHolder.imgUser.setImageURI(Uri.parse(ApiClient.BASE_URL + SessionManager.userLogin.profilePic));
            } else {
//                Glide.with(context).load(R.drawable.ic_grey_celebkonect_logo).into(feedHeaderViewHolder.imgUser);
                feedHeaderViewHolder.imgUser.setImageResource(R.drawable.ic_grey_celebkonect_logo);
            }


            feedHeaderViewHolder.llParent.setOnClickListener(v -> {
                    iFeedAdapter.createFeed();
            });

            feedHeaderViewHolder.llMedia.setOnClickListener(v -> {
                    iFeedAdapter.addMedia();

            });
            feedHeaderViewHolder.imgUser.setOnClickListener(view -> {
                    navigatingToSelfProfile();
            });

            feedHeaderViewHolder.llFeedCreate.setVisibility(isUpload ? View.GONE : View.VISIBLE);
            feedHeaderViewHolder.llFeedProgress.setVisibility(isUpload ? View.VISIBLE : View.GONE);

        } else if (holder instanceof RetryViewHolder) {
            position = getPositionWithoutHeaders(position);
            RetryViewHolder retryViewHolder = (RetryViewHolder) holder;
            llParentCommon = retryViewHolder.llParent;
            retryViewHolder.llParent.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            retryViewHolder.tvRetry.setOnClickListener(v -> onRetry());
            try {
                if (position != (list.size() - 1)) {
                    retryViewHolder.llParent.getLayoutParams().height = 1;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (holder instanceof LoaderViewHolder) {
            position = getPositionWithoutHeaders(position);
            LoaderViewHolder loaderViewHolder = (LoaderViewHolder) holder;
            llParentCommon = loaderViewHolder.llParent;
            loaderViewHolder.llParent.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            try {
                if (position != (list.size() - 1)) {
                    loaderViewHolder.llParent.getLayoutParams().height = 1;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (holder instanceof FeedSuggestionsOnWelcomeViewHolder) {
            try {
                position = getPositionWithoutHeaders(position);
                FeedSuggestionsOnWelcomeViewHolder feedSuggestionsOnWelcomeViewHolder = (FeedSuggestionsOnWelcomeViewHolder) holder;
                llParentCommon = feedSuggestionsOnWelcomeViewHolder.llParent;
                String name = Common.getInstance().IsNullReturnValue(SessionManager.userLogin.firstName, "");
                if (!name.isEmpty()) {
                    feedSuggestionsOnWelcomeViewHolder.tvUserName.setText("Hi, " + Character.toUpperCase(name.charAt(0)) + name.substring(1));
                } else {
                    feedSuggestionsOnWelcomeViewHolder.tvUserName.setText("Hi, User");
                }
                List<SuggestionsModel> suggestionsModels = list.get(position).suggestions;
                if (suggestionsModels != null && suggestionsModels.size() > 0) {
                    //feedSuggestionsOnWelcomeViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                    //feedSuggestionsOnWelcomeViewHolder.recyclerView.setAdapter(new FeedSuggestionsOnWelcomeAdapter(context, suggestionsModels));
                    //
                    feedSuggestionsOnWelcomeViewHolder.svHorizontal.setOrientation(DSVOrientation.HORIZONTAL);
                    //InfiniteScrollAdapter infiniteAdapter = InfiniteScrollAdapter.wrap(new FeedSuggestionsOnWelcomeAdapter(context, suggestionsModels));
                    feedSuggestionsOnWelcomeViewHolder.svHorizontal.setAdapter(new FeedSuggestionsOnWelcomeAdapter(context, suggestionsModels));
                    feedSuggestionsOnWelcomeViewHolder.svHorizontal.getCurrentItem();
                    feedSuggestionsOnWelcomeViewHolder.svHorizontal.setItemTransitionTimeMillis(2400);
                    feedSuggestionsOnWelcomeViewHolder.svHorizontal.setSlideOnFling(true);
                    feedSuggestionsOnWelcomeViewHolder.svHorizontal.setSlideOnFlingThreshold(250);
                    feedSuggestionsOnWelcomeViewHolder.svHorizontal.setItemTransformer(new ScaleTransformer.Builder().setMinScale(0.8f).build());
                    feedSuggestionsOnWelcomeViewHolder.svHorizontal.setVisibility(View.VISIBLE);
                } else {
                    feedSuggestionsOnWelcomeViewHolder.svHorizontal.setVisibility(View.GONE);
                    feedSuggestionsOnWelcomeViewHolder.recyclerView.setVisibility(View.VISIBLE);
                    feedSuggestionsOnWelcomeViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                    feedSuggestionsOnWelcomeViewHolder.recyclerView.setAdapter(new EmptyDataAdapter(context, Constants.NO_DATA_AVAILABLE, R.drawable.ic_nodata, 1));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            rController = RController.DONE;
        } else if (holder instanceof FeedSuggestionsViewHolder) {
            try {
                position = getPositionWithoutHeaders(position);
                FeedSuggestionsViewHolder feedSuggestionsViewHolder = (FeedSuggestionsViewHolder) holder;
                llParentCommon = feedSuggestionsViewHolder.llParent;
                List<SuggestionsModel> suggestionsModels = list.get(position).suggestions;
                if (suggestionsModels != null && suggestionsModels.size() > 0) {
                    feedSuggestionsViewHolder.llParent.setVisibility(View.VISIBLE);
                    feedSuggestionsViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                    feedSuggestionsViewHolder.recyclerView.setAdapter(new FeedSuggestionsAdapter(context, suggestionsModels,
                            feedSuggestionsViewHolder.recyclerView));
                } else {
                    feedSuggestionsViewHolder.llParent.setVisibility(View.GONE);
                    feedSuggestionsViewHolder.llParent.getLayoutParams().height = 1;
                    feedSuggestionsViewHolder.recyclerView.setAdapter(new EmptyDataAdapter(context, Constants.NO_DATA_AVAILABLE, R.drawable.ic_nodata, 1));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (holder instanceof FeedAdvertisementViewHolder) {
            try {
                position = getPositionWithoutHeaders(position);
                FeedAdvertisementViewHolder feedAdvertisementViewHolder = (FeedAdvertisementViewHolder) holder;
                llParentCommon = feedAdvertisementViewHolder.llParent;
                String imagePath = Common.getInstance().IsNullReturnValue(list.get(position).advertisement.src.url, "");
                if (!imagePath.isEmpty()) {
                    Common.getInstance().setGlideImage(context, ApiClient.BASE_URL + imagePath, feedAdvertisementViewHolder.ivImage);
                }
                int finalPosition = position;
                feedAdvertisementViewHolder.llParent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Intent intent = new Intent(context, HelperActivity.class);
                            intent.putExtra(Constants.FRAGMENT_TITLE, list.get(finalPosition).advertisement.title);
                            intent.putExtra(Constants.FRAGMENT_KEY, 9041);
                            intent.putExtra("FeedAdvertisementURL", list.get(finalPosition).advertisement.appUrlAndroid);
                            context.startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (holder instanceof FeedAllCaughtUpViewHolder) {
            try {
                FeedAllCaughtUpViewHolder feedAllCaughtUpViewHolder = (FeedAllCaughtUpViewHolder) holder;
                llParentCommon = feedAllCaughtUpViewHolder.llParent;
                position = getPositionWithoutHeaders(position);
                feedAllCaughtUpViewHolder.llParent.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                if (position == 0) {
                    feedAllCaughtUpViewHolder.llParent.getLayoutParams().height = 1;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            if (isCurvedBottomBar && llParentCommon != null && rController == RController.DONE) {
                Log.d("isCurvedBottomBar", getItemCount() - 1 + " @ " + orgPosition);
                if (orgPosition == getItemCount() - 1) {
                    int paddingToBottom = context.getResources().getDimensionPixelSize(R.dimen._48sdp);
                    llParentCommon.setPadding(0, 0, 0, paddingToBottom);
                } else {
                    llParentCommon.setPadding(0, 0, 0, 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int[] getPositionInParent(ViewGroup parent, View view) {
        int relativePosition[] = {view.getLeft(), view.getTop()};
        ViewGroup currentParent = (ViewGroup) view.getParent();
        while (currentParent != parent) {
            relativePosition[0] += currentParent.getLeft();
            relativePosition[1] += currentParent.getTop();
            currentParent = (ViewGroup) currentParent.getParent();
        }
        return relativePosition;
    }

    private int getRelativeLeft(View myView) {
        if (myView == null) {
            return 0;
        }
        if (myView.getParent() == myView.getRootView()) {
            return myView.getLeft();
        } else {
            return myView.getLeft() + getRelativeLeft((View) myView.getParent());
        }
    }

    private int getRelativeTop(View myView) {
        if (myView == null) {
            return 0;
        }
        if (myView.getParent() == myView.getRootView()) {
            return myView.getTop();
        } else {
            return myView.getTop() + getRelativeTop((View) myView.getParent());
        }
    }

    public int getSuggestionType(int position) {
        try {
            position = getPositionWithoutHeaders(position);
            return list.get(position).type;
        } catch (Exception e) {
            return -1;
        }
    }

    private Boolean showOnlineCelebs(int position) {
        return position == getOnlineCelebItemPosition() && !hideHeaders;
    }

    private int getOnlineCelebItemPosition() {
        if (Common.isCelebStatus(context)) {
            return 1;
        } else {
            return 0;
        }
    }

    public int getPositionWithoutHeaders(int position) {
        return hideHeaders ? position : (position - 1) - getOnlineCelebItemPosition();//-1 means removing online celebs position
    }

    public int getAdapterPositionWithHeaders(int position) {
        return hideHeaders ? position : (position + 1) + getOnlineCelebItemPosition();//+1 means adding online celebs position
    }

    public void collageViewClick(List<Media> mediaList, int itemPosition, int mediaPosition) {
        if (mediaList != null && mediaList.size() < 2) {
            if (mediaList.get(mediaPosition).type.equals(Constants.MEDIA_TYPE_VIDEO)) {
                iFeedAdapter.openVideoPlayer(list.get(itemPosition), Utility.addVideoUrl(mediaList.get(mediaPosition)), mediaList.get(mediaPosition), itemPosition, mediaPosition);
            } else {
                if (mediaList.get(mediaPosition) != null && mediaList.get(mediaPosition).source != null)
                    Common.getInstance().openMediaDetailsFragment("", "", AppController.getInstance().getCurrentRegisteredActivity(), list.get(itemPosition), list.get(itemPosition).mediaList, mediaPosition, false, false, itemPosition, "", true);
                    //iFeedAdapter.openImage(list.get(itemPosition), mediaList.get(mediaPosition), itemPosition);
            }
        } else {
            iFeedAdapter.viewFeedDetails(list.get(itemPosition), itemPosition, mediaPosition);
        }
    }

    public void showProgressFeedLikeUnLike(FeedViewHolder feedViewHolder, Feed feed) {
        //feedViewHolder.progressBar.setVisibility(feed.isBusyLike ? View.VISIBLE : View.GONE);
        //feedViewHolder.imgLike.setVisibility(feed.isBusyLike ? View.GONE : View.VISIBLE);
    }

    private void navigatingToSelfProfile() {
        Intent intent = new Intent(context, HelperActivity.class);
        intent.putExtra(Constants.FRAGMENT_KEY, 8038);
        intent.putExtra(Constants.FRAGMENT_TITLE, "My Profile");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void setStoriesAdapter(List<StoryProfileInfo> storyProfileInfoList) {
        Log.e("setStoriesAdapter", storyProfileInfoList.size() + "");
        this.storyProfileInfoList = storyProfileInfoList;
        try {
            if ((storyProfileInfoList == null || storyProfileInfoList.size() <= 0) && !Common.isCelebStatus(context)) {
                createStoriesViewHolder.llParent.setVisibility(View.GONE);
                createStoriesViewHolder.llParent.getLayoutParams().height = 1;
            } else {
                createStoriesViewHolder.llParent.setVisibility(View.VISIBLE);
                createStoriesViewHolder.llParent.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;

                createStoriesViewHolder.storiesRecylist.setAdapter(new StoriesAdapter(context, storyProfileInfoList));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void loadMore(List<Object> subList) {
        try {
            if (rController == RController.LOADING || rController == RController.DONE || rController == RController.NETWORK_ERROR_RETRY) {
                return;
            }
            removeAllFooterOptions();
            rController = RController.LOADED;
            List<Feed> appendList = (List<Feed>) (List<?>) subList;
            int positionStart = getItemCount() + 1;
            list.addAll(appendList);
            notifyItemRangeInserted(positionStart, appendList.size());
            //notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addBottomLoader() {
        /*try {
            if (!checkLastFooterOption(Constants.FOOTER_LOADER)) {
                list.add(new Feed().getFeedWithType(Constants.FOOTER_LOADER));
                rController = RController.LOAD_MORE;
                notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void addRetryOption() {
       /* try {
            if (!checkLastFooterOption(Constants.FOOTER_ERROR_RETRY)) {
                list.add(new Feed().getFeedWithType(Constants.FOOTER_ERROR_RETRY));
                rController = RController.NETWORK_ERROR_RETRY;
                notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    public void removeAllFooterOptions() {
        try {
            removeFooterOption(Constants.FOOTER_LOADER);
            removeFooterOption(Constants.FOOTER_ERROR_RETRY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void removeFooterOption(int type) {
        try {
            int position = list.size() - 1;
            Feed feed = list.get(position);
            if (feed != null && feed.type != null && feed.type == type) {
                list.remove(position);
                rController = RController.LOADED;
                notifyItemRemoved(getAdapterPositionWithHeaders(position));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Boolean checkLastFooterOption(int type) {
        try {
            int position = list.size() - 1;
            Feed feed = list.get(position);
            if (feed != null && feed.type != null && feed.type == type) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onRetry() {
        try {
            if (checkLastFooterOption(Constants.FOOTER_ERROR_RETRY)) {
                removeFooterOption(Constants.FOOTER_ERROR_RETRY);
                iFeedAdapter.retryLoadMore();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*public void updateOnlineCelebrityList(List<Celebrity> celebrityListFinal) {
        this.storyProfileInfoList = celebrityListFinal;
        if (onlineCelebritiesViewHolder != null) {
            if (storyProfileInfoList != null && storyProfileInfoList.size() > 0) {
                onlineCelebritiesViewHolder.llParent.setVisibility(View.VISIBLE);
                onlineCelebritiesViewHolder.llParent.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                if (onlineCelebritiesAdapter == null) {
                    onlineCelebritiesViewHolder.recyclerView.setAdapter(onlineCelebritiesAdapter = new OnlineCelebritiesAdapter(context, storyProfileInfoList, iFeedAdapter, onlineCelebritiesViewHolder));
                }
                onlineCelebritiesViewHolder.tvOnline.setText("Now Online (" + storyProfileInfoList.size() + ")");
            } else {
                onlineCelebritiesViewHolder.llParent.setVisibility(View.GONE);
                onlineCelebritiesViewHolder.llParent.getLayoutParams().height = 1;
            }
            if (storyProfileInfoList != null && storyProfileInfoList.size() > 4) {
                onlineCelebritiesViewHolder.tvViewAll.setVisibility(View.VISIBLE);
            } else {
                onlineCelebritiesViewHolder.tvViewAll.setVisibility(View.GONE);
            }
            if (onlineCelebritiesAdapter != null) {
                onlineCelebritiesAdapter.updateData(storyProfileInfoList);
            }
            //notifyItemChanged(getOnlineCelebItemPosition());
        }
    }*/

    /*public void updateOnlineCelebrityList(List<Celebrity> celebrityListFinal) {
        this.storyProfileInfoList = celebrityListFinal;
        if (storiesAdapter != null) {
            storiesAdapter.updateData(storyProfileInfoList);
        }
        if (createStoriesViewHolder != null) {
            try {
                if (storyProfileInfoList == null || storyProfileInfoList.size() <= 0) {
                    createStoriesViewHolder.llParent.setVisibility(View.GONE);
                    createStoriesViewHolder.llParent.getLayoutParams().height = 1;
                } else {
                    createStoriesViewHolder.llParent.setVisibility(View.VISIBLE);
                    createStoriesViewHolder.llParent.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }*/

    public void donePagination() {
        rController = RController.DONE;
        if (getItemCount() > 0) {
            notifyItemChanged(getItemCount() - 1);
        }
    }

    public class FeedHeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgUser)
        SimpleDraweeView imgUser;

        @BindView(R.id.llFeedProgress)
        LinearLayout llFeedProgress;

        @BindView(R.id.llFeedCreate)
        LinearLayout llFeedCreate;

        @BindView(R.id.progressBar)
        ProgressBar progressBar;

        @BindView(R.id.llParent)
        LinearLayout llParent;

        @BindView(R.id.llMedia)
        LinearLayout llMedia;

        //
        public FeedHeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class FeedSuggestionsOnWelcomeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvUserName)
        public TextView tvUserName;

        @BindView(R.id.recyclerView)
        public RecyclerView recyclerView;

        @BindView(R.id.svHorizontal)
        public DiscreteScrollView svHorizontal;

        @BindView(R.id.llParent)
        public LinearLayout llParent;

        public FeedSuggestionsOnWelcomeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class FeedSuggestionsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recyclerView)
        public RecyclerView recyclerView;

        @BindView(R.id.llParent)
        public LinearLayout llParent;

        public FeedSuggestionsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class FeedAllCaughtUpViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvUserName)
        public TextView tvUserName;

        @BindView(R.id.llParent)
        public LinearLayout llParent;

        public FeedAllCaughtUpViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class FeedAdvertisementViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivImage)
        public ImageView ivImage;

        @BindView(R.id.llParent)
        public LinearLayout llParent;

        public FeedAdvertisementViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
