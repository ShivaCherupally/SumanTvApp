package info.dkapp.flow.ipoll.viewholders;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

import info.dkapp.flow.R;

/**
 * Created by Uday Kumay Vurukonda on 12/18/2018.
 * <p>
 * Mr.Psycho
 */
public class CelebrityViewHolderViewAll extends RecyclerView.ViewHolder
{
    public CircleImageView user_image;
    public TextView user_name,occupationtv;
    public Button online_profileCheck;

    public CelebrityViewHolderViewAll(View itemView) {
        super(itemView);
        user_image = itemView.findViewById(R.id.user_image);
        user_name = itemView.findViewById(R.id.starname_tv);
        occupationtv = itemView.findViewById(R.id.occupationtv);
        online_profileCheck = itemView.findViewById(R.id.cancelBtn);
    }
}
