package info.sumantv.flow.ipoll.fragments.feeds.comments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import info.sumantv.flow.appmanagers.feed.models.*;
import info.sumantv.flow.bottommenu.activity.BottomMenuActivity;
import info.sumantv.flow.bottommenu.activity.HelperActivity;
import info.sumantv.flow.bottommenu.constants.RController;
import info.sumantv.flow.bottommenu.generic.EmptyDataAdapter;
import info.sumantv.flow.bottommenu.interfaces.fragments.IFragment;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.ipoll.adapters.feeds.comments.CommentsAdapter;
import info.sumantv.flow.ipoll.dialogs.custom.CommentsReportDialog;
import info.sumantv.flow.ipoll.interfaces.feeds.comments.ICommentsAdapter;
import info.sumantv.flow.retrofitcall.*;
import info.sumantv.flow.userflow.Util.Common;
import info.sumantv.flow.utils.Utility;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommentsFragment extends Fragment implements IFragment, ICommentsAdapter, IApiListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ApiInterface apiInterface;
    IApiListener iApiListener;


    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.imgClose)
    ImageView imgClose;
//
//    @BindView(R.id.swipeRefreshLayout)
//    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.etComment)
    EditText etComment;

    @BindView(R.id.imgUpdate)
    ImageButton imgUpdate;

    @BindView(R.id.imgSend)
    ImageButton imgSend;

    LinearLayoutManager linearLayoutManager;
    CommentsAdapter commentsAdapter;
    ICommentsAdapter iCommentsAdapter;
    List<Comment> commentList;
    List<CommentReportOption> commentReportOptionList;
    Boolean isLoadMoreBusy = false;
    String id;
    boolean isFeed, isDetails;
    int feedPosition, mediaPosition;
    Feed feed;

    Comment commentUpdate;
    CommentUpdate updateComment;
    int updatePosition = 0;
    private ProgressDialog progressDialog;
    Boolean isFromMediaDetailsFragment, isLoadMoreFinish = false;


    public CommentsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static CommentsFragment newInstance(Feed feed, String id, boolean isFeed, int feedPosition, int mediaPosition, boolean isDetails, Boolean isFromMediaDetailsFragment) {
        CommentsFragment fragment = new CommentsFragment();
        Bundle args = new Bundle();
        args.putParcelable("Feed", feed);
        args.putString("Id", id);
        args.putBoolean("IsFeed", isFeed);
        args.putInt("FeedPosition", feedPosition);
        args.putInt("MediaPosition", mediaPosition);
        args.putBoolean("IsDetails", isDetails);
        args.putBoolean("isFromMediaDetailsFragment", isFromMediaDetailsFragment);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iApiListener = this;
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.ipoll_fragment_comments, container, false);
        ButterKnife.bind(this, view);
        iCommentsAdapter = this;
        setUp();
        getCommentReportOptions(null, false, false);
        return view;
    }

    private void setUp() {
        if (getArguments() != null) {
            feed = getArguments().getParcelable("Feed");
            id = getArguments().getString("Id");
            isFeed = getArguments().getBoolean("IsFeed");
            feedPosition = getArguments().getInt("FeedPosition");
            mediaPosition = getArguments().getInt("MediaPosition");
            isDetails = getArguments().getBoolean("IsDetails");
            isFromMediaDetailsFragment = getArguments().getBoolean("isFromMediaDetailsFragment");
        } else {
            return;
        }
//        recyclerView.setLayoutManager(linearLayoutManager = new LinearLayoutManager(activity()));
        linearLayoutManager = new LinearLayoutManager(activity());
//        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

//        swipeRefreshLayout.setOnRefreshListener(() -> {
//            fetchFeedComments(true);
//        });
//        swipeRefreshLayout.setEnabled(false);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dx == 0 && dy == 0) {
                    return;
                }
                if (!recyclerView.canScrollVertically(1)) {
                    if (commentList != null && commentList.size() > 0 && !isLoadMoreFinish) {
                        loadMore();
                    }
                }
              /*  int visibleItemCount = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();
                int pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

                Logger.d("DATA " + firstVisibleItemPosition + " " + visibleItemCount + " " + totalItemCount + " " + pastVisibleItems);
                if (pastVisibleItems + visibleItemCount >= totalItemCount && !isLoadMoreFinish) {

                }*/
            }
        });

        imgClose.setOnClickListener(v ->
        {
            Utility.hideKeyboard(activity());
            activity().onBackPressed();
        });

        imgUpdate.setOnClickListener(v -> {

            if (etComment.getText().toString() != null && !etComment.getText().toString().isEmpty()) {

                if (commentUpdate.id != null && !commentUpdate.id.isEmpty()) {
                    if (Utility.isNetworkAvailable(activity())) {
                        Utility.hideKeyboard(activity());
                        updateComment(("" + etComment.getText()).trim());
                    } else {
                        showSnackBar(Constants.PLEASE_CHECK_INTERNET, 2);
                    }
                } else {
                    showSnackBar(Constants.SOMETHING_WENT_WRONG, 2);
                }
            } else {
                showSnackBar("Comment is empty", 2);
            }

        });

        etComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

                if (!etComment.getText().toString().trim().isEmpty() && etComment.getText().toString().trim().length() > 0) {
                    if (imgUpdate.getVisibility() == View.GONE) {
                        imgSend.setVisibility(View.VISIBLE);
                    }

                } else {
                    if (imgUpdate.getVisibility() == View.GONE) {
                        imgSend.setVisibility(View.INVISIBLE);
                    }

                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

                //   Toast.makeText(getActivity(), "before", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

        imgSend.setOnClickListener(v -> {
                Utility.hideKeyboard(activity());
                if (Utility.isNetworkAvailable(activity())) {
                    if (("" + etComment.getText()).trim().length() > 0) {
                        addComment(("" + etComment.getText()).trim());
                    } else {
                        showSnackBar("Comment is empty", 2);
                    }
                } else {
                    showSnackBar(Constants.PLEASE_CHECK_INTERNET, 2);
                }

        });


        fetchFeedComments(true);
    }


    private void fetchFeedComments(boolean reload) {
        isLoadMoreBusy = false;
        if (Utility.isNetworkAvailable(activity())) {
            if (reload) {
                recyclerView.setAdapter(commentsAdapter = new CommentsAdapter(feed, Arrays.asList(null, null),
                        activity(), RController.LOADING, this));
            }
            IApiListener iApiListener = new IApiListener() {
                @Override
                public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                    Type type = new TypeToken<List<Comment>>() {
                    }.getType();
                    commentList = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                    if (commentList != null && commentList.size() > 0) {
                        if (!reload) {
                            recyclerView.smoothScrollToPosition(0);
                        }
                        recyclerView.setAdapter(commentsAdapter = new CommentsAdapter(feed, commentList, activity(), RController.LOADED,
                                iCommentsAdapter));
                    } else {
                        recyclerView.setAdapter(new EmptyDataAdapter(activity(),
                                Constants.NO_COMMENTS, Constants.YOU_NO_COMMENTS, R.drawable.ic_no_comments, 5));
                    }
                }

                @Override
                public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
                    recyclerView.setAdapter(new EmptyDataAdapter(activity(), Constants.NO_COMMENTS, Constants.YOU_NO_COMMENTS, R.drawable.ic_no_comments, 5));
                    showSnackBar(Constants.CONNECTION_ERROR, 2);
                }
            };
            //
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            if (isFeed) {
                Log.e("CommentSec", id);
                Call<ApiResponseModel> call = apiInterface.GET(Constants.FeedConstants.URL_GET_FEED_COMMENTS + id + "/" + "0");
                Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, Constants.FeedConstants.URL_GET_FEED_COMMENTS,
                        false, iApiListener, false));
            } else {
                Log.e("CommentINnerID", id);
                Log.e("CommentINnerID2", feed.id);
                Call<ApiResponseModel> call = apiInterface.GET(Constants.FeedConstants.URL_GET_FEED_MEDIA_COMMENTS + id + "/"
                        + feed.id + "/" + "0");
                Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, Constants.FeedConstants.URL_GET_FEED_MEDIA_COMMENTS, false, iApiListener, false));
            }
        } else {
            recyclerView.setAdapter(new EmptyDataAdapter(activity(), Constants.PLEASE_CHECK_INTERNET, R.drawable.ic_network, 0));
        }
    }


    public void updateComment(String comment) {
        imgUpdate.setVisibility(View.GONE);
        JSONObject input = new JSONObject();
        try {
            input.put("celebId", feed.memberId);
            input.put("memberId", SessionManager.userLogin.userId);
            input.put("source", comment);
        } catch (Exception e) {
            e.printStackTrace();
        }
        IApiListener iApiListener = new IApiListener() {
            @Override
            public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                etComment.setText("");
                imgSend.setVisibility(View.VISIBLE);
                //imgSend.setColorFilter(ContextCompat.getColor(activity(), R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN);
                imgUpdate.setVisibility(View.GONE);
                commentList.get(updatePosition).comment = comment;
                commentsAdapter.notifyItemChanged(updatePosition);
            }

            @Override
            public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
                imgUpdate.setVisibility(View.VISIBLE);
                showSnackBar(Constants.CONNECTION_ERROR, 2);
            }
        };
        //
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.PUT(Constants.FeedConstants.UPDATE_COMMENT + commentUpdate.id, RequestBody.create(MediaType.parse("application/json; charset=utf-8"), input.toString()));
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, Constants.FeedConstants.UPDATE_COMMENT, false, iApiListener, false));
    }

    private void deleteComment(Comment isComment, int position) {
        if (!Utility.isNetworkAvailable(activity())) {
            showSnackBar(Constants.PLEASE_CHECK_INTERNET, 2);
            return;
        }
        commentList.get(position).isBusy = true;
        commentsAdapter.notifyDataSetChanged();
        IApiListener iApiListener = new IApiListener() {
            @Override
            public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                try {
                    commentList.remove(updatePosition);
                    updateFeedDetails(-1);
                    if (commentList == null || commentList.size() <= 0) {
                        recyclerView.setAdapter(new EmptyDataAdapter(activity(), Constants.NO_COMMENTS, Constants.YOU_NO_COMMENTS, R.drawable.ic_no_comments, 5));
                    }
                    commentsAdapter.notifyItemRemoved(updatePosition);
                    showSnackBar("Comment removed", 2);
                    Common.dismissProgressDialog(activity(), progressDialog);
                } catch (Exception e) {
                    e.printStackTrace();
                    Common.dismissProgressDialog(activity(), progressDialog);
                    isComment.isBusy = false;
                    commentsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
                isComment.isBusy = false;
                commentsAdapter.notifyDataSetChanged();
                showSnackBar(Constants.CONNECTION_ERROR, 2);
                Common.dismissProgressDialog(activity(), progressDialog);
            }
        };
        //
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.DELETE(Constants.FeedConstants.DELETE_COMMENT + commentUpdate.id);
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, Constants.FeedConstants.DELETE_COMMENT, false, iApiListener, false));
    }

    public void addComment(String comment) {
        imgSend.setVisibility(View.GONE);
        JSONObject input = new JSONObject();
        try {
            input.put("celebId", feed.memberId);
            input.put("memberId", SessionManager.userLogin.userId);
            input.put("feedId", feed.id);
            input.put(isFeed ? "feedId" : "mediaId", id);
            input.put("source", comment);
            input.put("activities", Constants.ACTION_TYPE_COMMENT);
            input.put("status", "Active");
        } catch (Exception e) {
            e.printStackTrace();
        }
        IApiListener iApiListener = new IApiListener() {
            @Override
            public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                etComment.setText("");
                imgSend.setVisibility(View.INVISIBLE);
                fetchFeedComments(false);
                updateFeedDetails(1);
            }

            @Override
            public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
                imgSend.setVisibility(View.VISIBLE);
                showSnackBar(Constants.CONNECTION_ERROR, 2);
            }
        };
        //
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.POST(Constants.FeedConstants.URL_ADD_COMMENT, RequestBody.create(MediaType.parse("application/json; charset=utf-8"), input.toString()));
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, Constants.FeedConstants.URL_ADD_COMMENT, false, iApiListener, false));
    }

    private void loadMore() {
        if (!isLoadMoreBusy && commentsAdapter.rController != RController.LOADING) {
            Comment comment = commentList.get(commentList.size() - 1);
            isLoadMoreBusy = true;
            if (Utility.isNetworkAvailable(activity())) {
                isLoadMoreBusy = true;
                IApiListener iApiListener = new IApiListener() {
                    @Override
                    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                        Type type = new TypeToken<List<Comment>>() {
                        }.getType();
                        List<Comment> appendList = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                        if (appendList != null && appendList.size() > 0) {
                            commentsAdapter.loadMore(new ArrayList<>(appendList));
                        }
                        if (appendList != null && appendList.size() < Constants.FEEDS_LOAD_MORE_COUNT) {
                            isLoadMoreFinish = true;
                        }
                    }

                    @Override
                    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
                        isLoadMoreBusy = false;
                    }
                };
                //
                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                if (isFeed) {
                    Call<ApiResponseModel> call = apiInterface.GET(Constants.FeedConstants.URL_GET_FEED_COMMENTS + id + "/" + comment.timeAgo);
                    Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, Constants.FeedConstants.URL_GET_FEED_COMMENTS, false, iApiListener, false));
                } else {
                    Call<ApiResponseModel> call = apiInterface.GET(Constants.FeedConstants.URL_GET_FEED_MEDIA_COMMENTS
                            + id + "/" + feed.id + "/" + comment.timeAgo);
                    Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, Constants.FeedConstants.URL_GET_FEED_MEDIA_COMMENTS, false, iApiListener, false));
                }
            } else {
                recyclerView.setAdapter(new EmptyDataAdapter(activity(), Constants.PLEASE_CHECK_INTERNET, R.drawable.ic_network, 0));

            }

        }
    }

    public Feed getUpdateFeed() {
        return feed;
    }

    public Integer getFeedPosition() {
        return feedPosition;
    }

    private void updateFeedDetails(int updateCount) {
        try {
            if (!isDetails || isFeed) {
                feed.numberOfComments = feed.numberOfComments + updateCount;
            } else {
                int position = isFromMediaDetailsFragment ? mediaPosition : mediaPosition - 1;
                feed.mediaList.get(position).numberOfComments = feed.mediaList.get(position).numberOfComments + updateCount;
            }
            if (activity() instanceof BottomMenuActivity) {
                ((BottomMenuActivity) activity()).updateFeed(feed, feedPosition);
                ((BottomMenuActivity) activity()).updateFeedDetails(feed);
            } else if (activity() instanceof HelperActivity) {
                ((HelperActivity) activity()).updateFeed(feed, feedPosition);
                ((HelperActivity) activity()).updateFeedDetails(feed);
            }
            if(Common.getInstance().getVideoPlayerFragment() != null){
                Common.getInstance().getVideoPlayerFragment().updateCommentCount(updateCount);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeTitle(String title) {

    }

    @Override
    public void showSnackBar(String snackBarText, int type) {
        Utility.showSnackBar(activity(), coordinatorLayout, snackBarText, type);
    }

    @Override
    public Activity activity() {
        return getActivity();
    }

    @Override
    public void onCommentClick(Comment comment, int position) {

    }

    @Override
    public void onProfileClick(Comment comment, int position) {

    }

    @Override
    public void selfProfile(Profile profile) {
        Common.getInstance().selfProfile(activity(), profile);
    }

    @Override
    public void celebUserProfile(Profile profile) {
        Common.getInstance().celebUserProfile(activity(), profile);
    }

    @Override
    public void retryLoadMore() {
        isLoadMoreBusy = false;
        //  loadMore();
    }

    @Override
    public void editComment(Comment comments, int position) {
            if (comments != null) {
                commentUpdate = comments;
                updatePosition = position;
            }
            if (comments.comment != null && !comments.comment.isEmpty()) {
                etComment.setText(comments.comment);
                etComment.setSelection(etComment.getText().toString().length());
                etComment.requestFocus();
                imgSend.setVisibility(View.GONE);
                imgUpdate.setVisibility(View.VISIBLE);
                Common.getInstance().showKeyboard(activity());
            }

    }

    @Override
    public void deleteComments(Comment comments, int position) {
            new AlertDialog.Builder(activity())
                    .setTitle("CelebKonect")
                    .setMessage("Comment deleted will be removed permanently. Are you sure you want to delete?")
                    .setCancelable(true)
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    })
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                            //
                            etComment.setText("");
                            imgSend.setVisibility(View.INVISIBLE);
                            imgUpdate.setVisibility(View.GONE);
                            if (comments != null) {
                                if (!Utility.isNetworkAvailable(activity())) {
                                    showSnackBar(Constants.PLEASE_CHECK_INTERNET, 2);
                                    return;
                                }
                                if (comments.isBusy) {
                                    return;
                                }
                                progressDialog = Common.showProgressDialog(activity(), progressDialog);
                                commentUpdate = comments;
                                updatePosition = position;
                                deleteComment(comments, position);
                            } else {
                                showSnackBar("Deleting Comment", 2);
                            }
                        }
                    })
                    .create()
                    .show();

    }

    @Override
    public void reportComments(Comment comments, int position) {
        if (commentReportOptionList != null && commentReportOptionList.size() > 0) {
            showReportOptionsDialog(comments);
        } else {
            getCommentReportOptions(comments, true, true);
        }
    }

    @Override
    public void reportSubmit(Comment comments, CommentReportOption commentReportOption, String remark) {
        CommentReportOptionPostParams commentReportOptionPostParams = new CommentReportOptionPostParams();
        commentReportOptionPostParams.commentId = comments.id;
        commentReportOptionPostParams.feedbackItemID = commentReportOption._id;
        commentReportOptionPostParams.remark = remark;
        commentReportOptionPostParams.feedbackPostedBy = Common.getInstance().IsNullReturnValue(SessionManager.userLogin.userId, "");
        //
        if (!Utility.isNetworkAvailable(activity())) {
            showSnackBar(Constants.PLEASE_CHECK_INTERNET, 2);
            return;
        }

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.postFeedbackOnComment(commentReportOptionPostParams);
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, Constants.FeedConstants.postFeedbackOnComment, true, iApiListener, true));


      /*  Call<JsonElement> call = apiInterface.postFeedbackOnComment(commentReportOptionPostParams);
        call.enqueue(new retrofit2.Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, retrofit2.Response<JsonElement> response) {
                Utility.closeProgressDialog(progressDialog);
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    if(jsonObject.length() > 0 && Common.getInstance().checkResponseStatus(jsonObject)){
                        showSnackBar(jsonObject.optString("message",""),2);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                showSnackBar(Constants.SOMETHING_WENT_WRONG, 2);
                Utility.closeProgressDialog(progressDialog);
            }
        });*/
    }

    private void getCommentReportOptions(Comment comments, Boolean showProgress, Boolean showBottomSheetDialog) {
        if (!Utility.isNetworkAvailable(activity())) {
            showSnackBar(Constants.PLEASE_CHECK_INTERNET, 2);
            return;
        }
       /* if(showProgress) {
            progressDialog = Utility.generateProgressDialog(activity());
        }
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonElement> call = apiInterface.getAllFeedbackItems(Constants.FeedConstants.getAllFeedbackItems);*/

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.getAllFeedbackItems(Constants.FeedConstants.getAllFeedbackItems);
        IApiListener iApiListenerC = new IApiListener() {
            @Override
            public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                if (condition.equals(Constants.FeedConstants.getAllFeedbackItems)) {
                    try {
                        Type type = new TypeToken<ArrayList<CommentReportOption>>() {
                        }.getType();
                        commentReportOptionList = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                        if (comments != null && showBottomSheetDialog) {
                            showReportOptionsDialog(comments);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {

            }
        };
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, Constants.FeedConstants.getAllFeedbackItems, false, iApiListenerC, true));


      /*  call.enqueue(new retrofit2.Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, retrofit2.Response<JsonElement> response) {
                Utility.closeProgressDialog(progressDialog);
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    if(jsonObject.length() > 0 && Common.getInstance().checkResponseStatus(jsonObject)){
                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<CommentReportOption>>() {}.getType();
                        commentReportOptionList = gson.fromJson(jsonObject.optString("allcommentFeedBack"), type);
                    }
                    if(comments != null && showBottomSheetDialog){
                        showReportOptionsDialog(comments);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                showSnackBar(Constants.SOMETHING_WENT_WRONG, 2);
                Utility.closeProgressDialog(progressDialog);
            }
        });*/
    }

    public void showReportOptionsDialog(Comment comments) {
        try {
            if (commentReportOptionList != null && commentReportOptionList.size() > 0) {
                CommentsReportDialog commentsReportDialog = new CommentsReportDialog();
                commentsReportDialog.setData(commentReportOptionList, comments, iCommentsAdapter);
                commentsReportDialog.show(getFragmentManager(), "CommentsReportDialog");
            } else {
                showSnackBar(Constants.NO_DATA_AVAILABLE, 2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
        if (condition.equals(Constants.FeedConstants.postFeedbackOnComment)) {
            try {
                showSnackBar(apiResponseModel.message, 2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {

    }
    /*@Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = -1;
        try {
            position = commentsAdapter.getPosition();
        } catch (Exception e) {
            return super.onContextItemSelected(item);
        }
        switch (item.getItemId()) {
            case 1:
                if(commentReportOptionList != null && commentReportOptionList.size() > 0) {
                    showReportOptionsDialog();
                } else {
                    getCommentReportOptions(null,true,true);
                }
                break;
            case 2:
                Common.getInstance().cusToast(activity(),"2");
                break;
        }
        return super.onContextItemSelected(item);
    }*/
}
