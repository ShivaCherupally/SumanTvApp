package info.dkapp.flow.bottommenu.viewholders;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import info.dkapp.flow.R;

public class RetryViewHolder extends RecyclerView.ViewHolder
{

    public TextView tvError;
    public TextView tvRetry;
    public LinearLayout llParent;

    public RetryViewHolder(View itemView) {
        super(itemView);
        tvError = itemView.findViewById(R.id.tvError);
        tvRetry = itemView.findViewById(R.id.tvRetry);
        llParent = itemView.findViewById(R.id.llParent);
    }
}
