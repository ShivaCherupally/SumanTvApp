package info.sumantv.flow.celebflow.Notifications;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.modelData.NotificationDeleteBottomModel;
import info.sumantv.flow.retrofitcall.ApiClient;
import info.sumantv.flow.userflow.Util.Common;

public class FragmentNotificationDeleteBottom extends BottomSheetDialogFragment {
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.llDelete)
    LinearLayout llDelete;
    @BindView(R.id.ivProfileImage)
    CircleImageView ivProfileImage;
    NotificationDeleteBottomModel notificationDeleteBottomModel;

    public FragmentNotificationDeleteBottom() {
        // Required empty public constructor
    }

    public void setData(NotificationDeleteBottomModel notificationDeleteBottomModel) {
        this.notificationDeleteBottomModel = notificationDeleteBottomModel;
    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification_delete_bottom, container, false);
        ButterKnife.bind(this, view);
        //
        return view;
    }*/

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        View contentView = View.inflate(getContext(), R.layout.fragment_notification_delete_bottom, null);
        dialog.setContentView(contentView);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();
        ((View) contentView.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        //
        ButterKnife.bind(this, contentView);
        if (notificationDeleteBottomModel.title != null) {
            tvTitle.setText(notificationDeleteBottomModel.title);
        }
        if (!Common.getInstance().IsNull(notificationDeleteBottomModel.profileImage)) {
            Common.getInstance().setGlideImage(activity(), ApiClient.BASE_URL + notificationDeleteBottomModel.profileImage, ivProfileImage);
        }
        llDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificationDeleteBottomModel.notificationsAdapter.deleteNotification(notificationDeleteBottomModel._idList,
                        notificationDeleteBottomModel.itemPosition,false, false, "");
                dismiss();
            }
        });
    }

    private Activity activity(){
        return getActivity();
    }
}
