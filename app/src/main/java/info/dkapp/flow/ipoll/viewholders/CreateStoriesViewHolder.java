package info.dkapp.flow.ipoll.viewholders;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

import info.dkapp.flow.R;

/**
 * Created by User on 28-08-2018.
 **/

public class CreateStoriesViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.storiesRecylist)
    public RecyclerView storiesRecylist;

//    @BindView(R.id.rLCreateStore)
//    public RelativeLayout rLCreateStore;

    @BindView(R.id.llParent)
    public LinearLayout llParent;

//    @BindView(R.id.addImage)
//    public CircleImageView addImage;


    public CreateStoriesViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
