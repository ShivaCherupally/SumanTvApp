package info.dkapp.flow.ipoll.viewholders;

import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import info.dkapp.flow.R;

/**
 * Created by User on 28-08-2018.
 **/

public class OnlineCelebritiesViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tvViewAll)
    public TextView tvViewAll;

    @BindView(R.id.recyclerView)
    public RecyclerView recyclerView;

    @BindView(R.id.tvOnline)
    public TextView tvOnline;

    @BindView(R.id.llParent)
    public LinearLayout llParent;


    @BindView(R.id.storiesCheck)
    public TextView storiesCheck;

    public OnlineCelebritiesViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
