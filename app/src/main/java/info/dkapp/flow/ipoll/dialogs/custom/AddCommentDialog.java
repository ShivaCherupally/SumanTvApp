package info.dkapp.flow.ipoll.dialogs.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.core.view.MotionEventCompat;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.dkapp.flow.appmanagers.feed.models.Feed;
import info.dkapp.flow.bottommenu.activity.BottomMenuActivity;
import info.dkapp.flow.bottommenu.activity.HelperActivity;
import info.dkapp.flow.databaseutil.appstart.AppController;

import info.dkapp.flow.R;
import info.dkapp.flow.celebflow.constants.Constants;
import info.dkapp.flow.ipoll.fragments.feeds.FeedsFragment;
import info.dkapp.flow.ipoll.fragments.feeds.KeyboardHeightObserver;
import info.dkapp.flow.ipoll.fragments.feeds.KeyboardHeightProvider;
import info.dkapp.flow.ipoll.fragments.feeds.comments.AddCommentParams;
import info.dkapp.flow.retrofitcall.ApiResponseModel;
import info.dkapp.flow.retrofitcall.EnumConstants;
import info.dkapp.flow.retrofitcall.IApiListener;
import info.dkapp.flow.userflow.Util.Common;
import info.dkapp.flow.utils.Utility;

public class AddCommentDialog extends BottomSheetDialogFragment implements IApiListener, KeyboardHeightObserver {
    @BindView(R.id.etAddComment)
    EditText etAddComment;
    @BindView(R.id.ibAddCommentSubmit)
    ImageButton ibAddCommentSubmit;
    @BindView(R.id.rlParent)
    RelativeLayout rlParent;

    Context context;
    private AddCommentParams addCommentParams;
    private IApiListener iApiListener;
    private KeyboardHeightProvider keyboardHeightProvider;
    BottomSheetBehavior mBottomSheetBehavior;

    public AddCommentDialog() {}

    public void setData(AddCommentParams addCommentParams) {
        this.addCommentParams = addCommentParams;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_comment_dialog, container, false);
        ButterKnife.bind(this, view);
        context = getContext();
        iApiListener = this;
        if(AppController.getInstance().getKeyboardHeight() <= 0){
            if(keyboardHeightProvider != null) {
                keyboardHeightProvider.close();
            }
            keyboardHeightProvider = new KeyboardHeightProvider(AppController.getInstance().getCurrentRegisteredActivity());
            rlParent.post(new Runnable() {
                public void run() {
                    keyboardHeightProvider.start();
                }
            });
        }
        //
        setup();
        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setup() {
        etAddComment.requestFocus();
        etAddComment.post(new Runnable() {
            @Override
            public void run() {
                InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(etAddComment, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        if(FeedsFragment.getInstance() != null){
            FeedsFragment.getInstance().moveToCommentSection(addCommentParams);
        }
        //
        /*mBottomSheetBehavior = BottomSheetBehavior.from(rlParent);
        mBottomSheetBehavior.setBottomSheetCallback(bottomSheetCallback);*/
        etAddComment.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_UP:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });
        etAddComment.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if(keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
                    dismiss();
                }
                return false;
            }
        });
        ibAddCommentSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String commentText = etAddComment.getText().toString().trim();
                if(commentText.isEmpty()){
                    Common.getInstance().cusToast(context, "Comment is empty");
                    return;
                }
                    if (Utility.isNetworkAvailable(AppController.getInstance().getCurrentRegisteredActivity())) {
                        addCommentParams.commentText = commentText;
                        Common.getInstance().insertFeedComment(context,addCommentParams, iApiListener);
                        dismiss();

                }
            }
        });
    }

    BottomSheetBehavior.BottomSheetCallback bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View view, int newState) {
            if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                if (mBottomSheetBehavior instanceof LockableBottomSheetBehavior) {
                    ((LockableBottomSheetBehavior) mBottomSheetBehavior).setLocked(true);
                }
            }
        }

        @Override
        public void onSlide(@NonNull View view, float slideOffset) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = MotionEventCompat.getActionMasked(event);
                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                            return false;
                        default:
                            return true;
                    }
                }
            });
        }
    };


    @Override
    public void onStart() {
        super.onStart();
        try {
            Window window = getDialog().getWindow();
            WindowManager.LayoutParams windowParams = window.getAttributes();
            windowParams.dimAmount = 0f;
            windowParams.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(windowParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Window window = getDialog().getWindow();
            if (window != null) {
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if(keyboardHeightProvider != null) {
            keyboardHeightProvider.close();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(keyboardHeightProvider != null) {
            keyboardHeightProvider.setKeyboardHeightObserver(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(keyboardHeightProvider != null) {
            keyboardHeightProvider.setKeyboardHeightObserver(null);
        }
    }

    @Override
    public void onKeyboardHeightChanged(int height, int orientation) {
        String orientationLabel = orientation == Configuration.ORIENTATION_PORTRAIT ? "portrait" : "landscape";
        Log.d("KeyboardHeight", "height" + height + " @ orientationLabel" + orientationLabel);
        AppController.getInstance().setKeyboardHeight(height);
        if(FeedsFragment.getInstance() != null){
            FeedsFragment.getInstance().moveToCommentSection(addCommentParams);
        }
    }

    @Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
        if (condition.equalsIgnoreCase(Constants.FeedConstants.URL_ADD_COMMENT)) {
            Feed feed = addCommentParams.feed;
            feed.numberOfComments = feed.numberOfComments+1;
            if (AppController.getInstance().getCurrentRegisteredActivity() instanceof BottomMenuActivity) {
                ((BottomMenuActivity) AppController.getInstance().getCurrentRegisteredActivity()).updateFeed(feed, addCommentParams.feedOrMediaPosition);
            } else if (AppController.getInstance().getCurrentRegisteredActivity() instanceof HelperActivity) {
                ((HelperActivity) AppController.getInstance().getCurrentRegisteredActivity()).updateFeed(feed, addCommentParams.feedOrMediaPosition);
            }
        }
    }

    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {
        if (condition.equalsIgnoreCase(Constants.FeedConstants.URL_ADD_COMMENT)) {
            if (AppController.getInstance().getCurrentRegisteredActivity() instanceof BottomMenuActivity) {
                ((BottomMenuActivity) AppController.getInstance().getCurrentRegisteredActivity()).showBlockError(apiResponseModel.message);
            } else if (AppController.getInstance().getCurrentRegisteredActivity() instanceof HelperActivity) {
                ((HelperActivity) AppController.getInstance().getCurrentRegisteredActivity()).showBlockError(apiResponseModel.message);
            }
        }
    }
}
