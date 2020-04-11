package info.sumantv.flow.bottommenu.generic;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.constants.Constants;

public class EmptyDataAdapter extends RecyclerView.Adapter<EmptyDataAdapter.EmptyViewHolder> {

    Context context;
    String content, note;
    int imageId;
    int type;


    public EmptyDataAdapter(Context context, String content, int imageId, int type) {
        this.context = context;
        this.content = content;
        this.imageId = imageId;
        this.type = type;
    }

    public EmptyDataAdapter(Context context, String content, String note, int imageId, int type) {
        this.context = context;
        this.content = content;
        this.imageId = imageId;
        this.type = type;
        this.note = note;
    }

    @Override
    public EmptyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_data_item_new, parent, false));
    }

    @Override
    public void onBindViewHolder(EmptyViewHolder holder, int position) {
//
//        holder.tvNote.setVisibility(type == 0 ? View.VISIBLE : View.GONE);
//        holder.tvNote.setVisibility(type == 5 ? View.VISIBLE : View.GONE);
//        holder.tvNote.setVisibility(type == 6 ? View.VISIBLE : View.GONE);

        if (type == 0) {
            holder.tvNote.setVisibility(View.VISIBLE);
            holder.tvError.setText(Constants.UH_OH);
            holder.imgError.setImageResource(R.drawable.ic_network);
            holder.tvNote.setText(Constants.SEEMS_LIKE_NO_INTRNET);
        } else if (type == 5) {
            holder.tvNote.setVisibility(View.VISIBLE);
            holder.tvError.setText(content);
            holder.imgError.setImageResource(imageId);
            holder.tvNote.setText(note);
        } else if (type == 6) {
            holder.tvNote.setVisibility(View.VISIBLE);
            holder.tvError.setText(content);
            holder.imgError.setImageResource(imageId);
            holder.tvNote.setText(note);
        } else {
            holder.tvNote.setVisibility(View.GONE);
            holder.tvError.setText(content);
            holder.imgError.setImageResource(imageId);
//            holder.tvNote.setText(note);
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class EmptyViewHolder extends RecyclerView.ViewHolder {

        //        LinearLayout llEmpty;
        TextView tvError, tvNote;
        ImageView imgError;

        @BindView(R.id.view_foreground)
        RelativeLayout viewForeground;

        public EmptyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
//            llEmpty = itemView.findViewById(R.id.llEmpty);
            tvError = itemView.findViewById(R.id.tvError);
            tvNote = itemView.findViewById(R.id.tvNote);
            imgError = itemView.findViewById(R.id.imgError);
        }
    }
}
