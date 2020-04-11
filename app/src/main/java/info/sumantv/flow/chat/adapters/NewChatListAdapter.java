package info.sumantv.flow.chat.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import info.sumantv.flow.bottommenu.constants.RController;
import info.sumantv.flow.bottommenu.viewholders.SkeletonViewHolder;

import info.sumantv.flow.R;
import info.sumantv.flow.chat.interfaces.INewChatListAdapter;
import info.sumantv.flow.chat.models.ChatDataConvertModel;
import info.sumantv.flow.retrofitcall.ApiClient;
import info.sumantv.flow.retrofitcall.SessionManager;
import info.sumantv.flow.userflow.Util.Common;

import java.util.ArrayList;

public class NewChatListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<ChatDataConvertModel> chatDataConvertModelArrayList;
    private Context context;
    private INewChatListAdapter iNewChatListAdapter;
    String userName = "",userEmail = "",userMemberId = "";
    private RController rController;

    public NewChatListAdapter(ArrayList<ChatDataConvertModel> newChatDataModelArrayList, Context context, INewChatListAdapter iNewChatListAdapter,RController rController) {
        this.rController = rController;
        this.chatDataConvertModelArrayList = newChatDataModelArrayList;
        this.context = context;
        this.iNewChatListAdapter = iNewChatListAdapter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (rController == RController.LOADING) {
            int layout = R.layout.skeleton_chat;
            return new SkeletonViewHolder(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
        } else {
            int layout = R.layout.item_new_chat_list;
            return new ChatDataModelViewAdapter(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        userName = Common.getInstance().IsNullReturnValue(SessionManager.userLogin.userName,"");
        userEmail = Common.getInstance().IsNullReturnValue(SessionManager.getInstance().getKeyValue(SessionManager.KEY_EMAIL_OR_MOBILE,""),"");
        userMemberId = Common.getInstance().IsNullReturnValue(SessionManager.userLogin.userId,"");
        if(userMemberId.isEmpty()){
            return;
        }
        if (holder instanceof ChatDataModelViewAdapter) {
            ChatDataModelViewAdapter viewHolder = (ChatDataModelViewAdapter) holder;
            if (!Common.getInstance().IsNull(chatDataConvertModelArrayList.get(position).firstName)) {
                viewHolder.tvUserName.setText(chatDataConvertModelArrayList.get(position).firstName + " " + chatDataConvertModelArrayList.get(position).lastName);
            }
            if (!Common.getInstance().IsNull(chatDataConvertModelArrayList.get(position).avtar_imgPath)) {
                Common.getInstance().setGlideImage(context,ApiClient.BASE_URL + chatDataConvertModelArrayList.get(position).avtar_imgPath,viewHolder.ivProfileImage);
            } else {
                viewHolder.ivProfileImage.setImageResource(R.drawable.appiconandroid);
            }
            if(chatDataConvertModelArrayList.get(position).isOnline != null && chatDataConvertModelArrayList.get(position).isOnline){
                viewHolder.ivOnline.setVisibility(View.VISIBLE);
            } else {
                viewHolder.ivOnline.setVisibility(View.GONE);
            }
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        iNewChatListAdapter.openSingleChatWindow(chatDataConvertModelArrayList.get(position),position);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            viewHolder.ivProfileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iNewChatListAdapter.openProfileDialog(chatDataConvertModelArrayList.get(position),position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return rController == RController.LOADING ? 10 : chatDataConvertModelArrayList.size();
    }

    public class ChatDataModelViewAdapter extends RecyclerView.ViewHolder {
        @BindView(R.id.tvUserName)
        TextView tvUserName;

        @BindView(R.id.ivProfileImage)
        CircleImageView ivProfileImage;

        @BindView(R.id.ivOnline)
        CircleImageView ivOnline;

        public ChatDataModelViewAdapter(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
