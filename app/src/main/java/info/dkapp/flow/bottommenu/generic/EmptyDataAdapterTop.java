package info.dkapp.flow.bottommenu.generic;

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

import info.dkapp.flow.R;

public class EmptyDataAdapterTop extends RecyclerView.Adapter<EmptyDataAdapterTop.EmptyViewHolder> {

    Context context;
    String content, note;
    int imageId;
    int type;


    public EmptyDataAdapterTop(Context context, String content, int imageId, int type) {
        this.context = context;
        this.content = content;
        this.imageId = imageId;
        this.type = type;
    }

    public EmptyDataAdapterTop(Context context, String content, String note, int imageId, int type) {
        this.context = context;
        this.content = content;
        this.imageId = imageId;
        this.type = type;
        this.note = note;
    }

    @Override
    public EmptyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_data_item_top, parent, false));
    }

    @Override
    public void onBindViewHolder(EmptyViewHolder holder, int position) {
        holder.tvError.setText(content);
        holder.imgError.setImageResource(imageId);
        holder.tvNote.setText(note);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class EmptyViewHolder extends RecyclerView.ViewHolder {
        TextView tvError, tvNote;
        ImageView imgError;

        @BindView(R.id.view_foreground)
        RelativeLayout viewForeground;

        public EmptyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            tvError = itemView.findViewById(R.id.tvError);
            tvNote = itemView.findViewById(R.id.tvNote);
            imgError = itemView.findViewById(R.id.imgError);
        }
    }
}
