package info.sumantv.flow.ipoll.viewholders;

import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import de.hdodenhof.circleimageview.CircleImageView;

import info.sumantv.flow.R;

/**
 * Created by User on 28-08-2018.
 **/

public class CelebrityViewHolder extends RecyclerView.ViewHolder {
    public CircleImageView user_image, profileCheck;
    public SimpleDraweeView shadowuser_image;
    public TextView user_name;
    public LinearLayout cicleLayout;
    public ImageView online_profileCheck;
    public LinearLayout profileLayout;
    public FrameLayout circleFramelayout;
//    public ElevationImageView elevationIma;


    public CelebrityViewHolder(View itemView) {
        super(itemView);
        user_image = itemView.findViewById(R.id.user_image);
        user_name = itemView.findViewById(R.id.user_name);
        profileCheck = itemView.findViewById(R.id.profileCheck);
        cicleLayout = itemView.findViewById(R.id.cicleLayout);
        online_profileCheck = itemView.findViewById(R.id.online_profileCheck);
        profileLayout = itemView.findViewById(R.id.profileLayout);
        circleFramelayout = itemView.findViewById(R.id.circleFramelayout);
        shadowuser_image = itemView.findViewById(R.id.shadowuser_image);
//        elevationIma = itemView.findViewById(R.id.elevationIma);
    }
}
