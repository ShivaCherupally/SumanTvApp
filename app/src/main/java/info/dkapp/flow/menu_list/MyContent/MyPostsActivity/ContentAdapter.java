package info.dkapp.flow.menu_list.MyContent.MyPostsActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import info.dkapp.flow.appmanagers.feed.models.Feed;
import info.dkapp.flow.bottommenu.activity.HelperActivity;
import info.dkapp.flow.bottommenu.constants.RController;
import info.dkapp.flow.bottommenu.viewholders.SkeletonViewHolder;
import info.dkapp.flow.databaseutil.appstart.AppController;

import info.dkapp.flow.R;
import info.dkapp.flow.celebflow.constants.Constants;
import info.dkapp.flow.retrofitcall.ApiClient;
import info.dkapp.flow.retrofitcall.ApiInterface;
import info.dkapp.flow.retrofitcall.ApiRequestModel;
import info.dkapp.flow.retrofitcall.ApiResponseModel;
import info.dkapp.flow.retrofitcall.EnumConstants;
import info.dkapp.flow.retrofitcall.IApiListener;
import info.dkapp.flow.retrofitcall.SessionManager;
import info.dkapp.flow.userflow.Util.Common;
import info.dkapp.flow.userflow.Util.DateUtil;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * Created by Uday Kumay Vurukonda on 29-Nov-19.
 * <p>
 * Mr.Psycho
 */
public class ContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    List<Feed> feedList;
    ArrayList<String> dateAndTime = new ArrayList<>();
    public RController rController;

    public ContentAdapter(RController rController) {
        this.rController = rController;
    }

    public ContentAdapter(Context context, List<Feed> feedList) {
        this.context = context;
        this.feedList = feedList;
        this.rController = RController.LOADED;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        if (rController == RController.LOADING) {
            int layout = R.layout.my_post_list_item_skelton;
            return new SkeletonViewHolder(LayoutInflater.from(
                    parent.getContext()).inflate(layout, parent, false));
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.my_post_list_item, parent, false);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderGlobal, int position) {

        if (holderGlobal instanceof MyViewHolder) {

            MyViewHolder holder = (MyViewHolder) holderGlobal;

            if (feedList.get(position).mediaList != null && !feedList.get(position).mediaList.isEmpty()) {
                if (feedList.get(position).mediaList.get(0).source.url != null && !feedList.get(position).mediaList.get(0).source.url.isEmpty()) {
                    Glide.with(context)
                            .load(ApiClient.BASE_URL + feedList.get(position).mediaList.get(0).source.url)
                            .error(R.drawable.ic_only_text_feed)
                            .into(holder.user_image);
                }
            }

            if (feedList.get(position).feedTitle != null && !feedList.get(position).feedTitle.isEmpty()) {
                holder.posttitletxt.setText(Common.unescapeJava(Character.toUpperCase(feedList.get(position).feedTitle.charAt(0))
                        + feedList.get(position).feedTitle.substring(1)));
            } else {
                holder.posttitletxt.setText("");
            }
            if (feedList.get(position).createdDate != null && !feedList.get(position).createdDate.isEmpty()) {
                dateAndTime.add(DateUtil.getCompleteDate(feedList.get(position).createdDate) + " " +
                        DateUtil.serverSentDateConvertTimeInSchedule(feedList.get(position).createdDate));

                holder.dateandtime.setText(DateUtil.getCompleteDate(feedList.get(position).createdDate));
                holder.time.setText(DateUtil.serverSentDateConvertTimeInSchedule(feedList.get(position).createdDate));
            }
            setFeedLikeUnLike(holder,feedList.get(position));
            holder.likecounttxt.setText(String.valueOf(feedList.get(position).numberOfLikes));
            holder.commentcounttxt.setText(String.valueOf(feedList.get(position).numberOfComments));
            holder.user_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Common.getInstance().getSingleFeedData(feedList.get(position).id);
                }
            });
            holder.commentcounttxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Common.getInstance().openFeedComments(feedList.get(position), feedList.get(position).id, true, position, -1, false, false);
                }
            });
            holder.feed_commenticon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Common.getInstance().openFeedComments(feedList.get(position), feedList.get(position).id, true, position, -1, false, false);
                }
            });
            holder.likecounttxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((HelperActivity) AppController.getInstance().getCurrentRegisteredActivity()).openFeedLikes(feedList.get(position), feedList.get(position).id, true, position, -1, false);
                }
            });
            holder.feed_likeimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    likeAction(feedList.get(position),position,holder);
                }
            });
            holder.deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(context, holder.deletebtn);
                    //Inflating the Popup using xml file
                    popup.getMenuInflater().inflate(R.menu.options_menu, popup.getMenu());

                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.edit:
                                        ((HelperActivity) context).editFeed(feedList.get(position), position);
                                        notifyDataSetChanged();

                                    return true;
                                case R.id.delete:
                                    //handle menu2 click
                                        Common.getInstance().deleteFeed(feedList.get(position).id, position,0, "MyContent");
                                        notifyDataSetChanged();
                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });
                    popup.show();//showing popup menu
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return rController == RController.LOADING ?
                10 : feedList.size();
    }

    public void deleteItem(int position) {
        notifyDataSetChanged();
    }

    private void likeAction(Feed feed, int position, MyViewHolder myViewHolder) {
        if (!feedList.get(position).isBusyLike) {
            feedList.get(position).isBusyLike = true;
            showProgressFeedLikeUnLike(myViewHolder, feedList.get(position));
            //
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            JSONObject input = new JSONObject();
            try {
                String userid = null;
                if (SessionManager.userLogin.userId != null
                        && !SessionManager.userLogin.userId.isEmpty()) {
                    userid = SessionManager.userLogin.userId;
                }
                input.put("memberId", userid);
                input.put("celebId", feed.memberId);
                input.put("feedId", feed.id);
                input.put("isLike", Common.getInstance().getBooleanFromInt(feed.isLike) ? 0 : 1);

                input.put("activities", Constants.ACTION_TYPE_LIKE);
                input.put("status", "Active");
            } catch (Exception e) {
                e.printStackTrace();
            }
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), input.toString());
            Call<ApiResponseModel> call = apiInterface.POST(Constants.FeedConstants.URL_ADD_LIKE, requestBody);
            IApiListener iApiListener = new IApiListener() {
                @Override
                public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                    feedList.get(position).isBusyLike = false;
                    feedList.get(position).isLike = Common.getInstance().getBooleanFromInt(feed.isLike) ? 0 : 1;
                    feedList.get(position).numberOfLikes = Common.getInstance().getBooleanFromInt(feedList.get(position).isLike) ? feedList.get(position).numberOfLikes + 1 : feedList.get(position).numberOfLikes - 1;
                    setFeedLikeUnLike(myViewHolder, feedList.get(position));
                }

                @Override
                public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
                    feedList.get(position).isBusyLike = false;
                    showProgressFeedLikeUnLike(myViewHolder, feedList.get(position));
                }
            };
            Common.getInstance().callAPI(new ApiRequestModel().setModel(context, call, Constants.FeedConstants.URL_ADD_LIKE, false, iApiListener, false));
        }
    }

    private void showProgressFeedLikeUnLike(MyViewHolder myViewHolder, Feed feed) {
        myViewHolder.progressBar.setVisibility(feed.isBusyLike ? View.VISIBLE : View.GONE);
        myViewHolder.feed_likeimage.setVisibility(feed.isBusyLike ? View.GONE : View.VISIBLE);
    }

    private void setFeedLikeUnLike(MyViewHolder myViewHolder, Feed feed) {
        myViewHolder.feed_likeimage.setImageResource(Common.getInstance().getBooleanFromInt(feed.isLike) ? R.drawable.ic_like_fill : R.drawable.ic_like_stroke);
        myViewHolder.progressBar.setVisibility(feed.isBusyLike ? View.VISIBLE : View.GONE);
        myViewHolder.feed_likeimage.setVisibility(feed.isBusyLike ? View.GONE : View.VISIBLE);
        // feedViewHolder.imgLike.setColorFilter(ContextCompat.getColor(context, feed.isLike ? R.color.skyblueNew : R.color.hash_text_color));
        if (feed.numberOfLikes > 0) {
            if (feed.numberOfLikes == 1) {
//                    feedViewHolder.tvLikeCount.setText("" + feed.numberOfLikes + " " + Utility.getStringResource(context, R.string.text_like));
                myViewHolder.likecounttxt.setText(Common.getInstance().coolFormat(feed.numberOfLikes, 0));
            } else
//                    feedViewHolder.tvLikeCount.setText("" + feed.numberOfLikes + " " + Utility.getStringResource(context, R.string.text_likes));
                myViewHolder.likecounttxt.setText(Common.getInstance().coolFormat(feed.numberOfLikes, 0));
            myViewHolder.likecounttxt.setVisibility(View.VISIBLE);
        } else {
            myViewHolder.likecounttxt.setVisibility(View.VISIBLE);
            myViewHolder.likecounttxt.setText("");
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView user_image;
        public TextView posttitletxt, dateandtime, time,
                likecounttxt, commentcounttxt, sharecounttxt, viewscounttxt,viewcounttxt;
        ImageView deletebtn;
        LinearLayout creditLayout,content_linearlayout;
        ImageView feed_likeimage,feed_commenticon, feed_view;
        ProgressBar progressBar;

        public MyViewHolder(View itemView) {
            super(itemView);
            user_image = itemView.findViewById(R.id.star_image_profile);
            posttitletxt = itemView.findViewById(R.id.posttitletxt);
            dateandtime = itemView.findViewById(R.id.dateandtime);
            time = itemView.findViewById(R.id.time);
            viewcounttxt = itemView.findViewById(R.id.viewcounttxt);
            likecounttxt = itemView.findViewById(R.id.likecounttxt);
            commentcounttxt = itemView.findViewById(R.id.commentcounttxt);
            sharecounttxt = itemView.findViewById(R.id.sharecounttxt);
            viewscounttxt = itemView.findViewById(R.id.viewscount);
            deletebtn = itemView.findViewById(R.id.deletebtn);
            feed_likeimage = itemView.findViewById(R.id.feed_likeimage);
            feed_commenticon = itemView.findViewById(R.id.feed_commenticon);
            feed_view = itemView.findViewById(R.id.feed_view);
            creditLayout = itemView.findViewById(R.id.creditLayout);
            content_linearlayout = itemView.findViewById(R.id.content_linearlayout);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    public void loadmore(List<Feed> celebrityList) {
        ArrayList<Feed> appendList = (ArrayList<Feed>) (List<?>) celebrityList;
        int positionStart = getItemCount() + 1;
        notifyItemRangeInserted(positionStart, appendList.size());
    }
}

