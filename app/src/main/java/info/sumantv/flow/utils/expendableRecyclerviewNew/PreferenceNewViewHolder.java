package info.sumantv.flow.utils.expendableRecyclerviewNew;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import info.sumantv.flow.bottommenu.preferencemanager.newpreferene.ChildOnclikExpendableRecy;

import info.sumantv.flow.R;

public class PreferenceNewViewHolder extends ChildViewHolder  {

    private TextView mMoviesTextView;
    ImageView imageViewStatus;
    ChildOnclikExpendableRecy childOnclikExpendableRecy;
    LinearLayout linearChild;
    boolean isSelected = false;

    public PreferenceNewViewHolder(View itemView) {
        super(itemView);
        mMoviesTextView = (TextView) itemView.findViewById(R.id.tv_movies);
        imageViewStatus = (ImageView) itemView.findViewById(R.id.imageViewStatus);
        linearChild = (LinearLayout) itemView.findViewById(R.id.linearChild);
    }

    public void bind(PreferenceNew preferenceNew, ChildOnclikExpendableRecy iChildOnclikExpendableRecy, int position) {
        Context context = mMoviesTextView.getContext();
        mMoviesTextView.setText(preferenceNew.getPreferenceName());

        if (preferenceNew.getIsSelected()){
            isSelected = true;
            imageViewStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_selected));
        }else {
            isSelected = false;
            imageViewStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_add_audition));
        }
        linearChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSelected){
                    isSelected = false;
                    imageViewStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_add_audition));
                    //PreferenceFragmentNew.getInstance().removeData(preferenceNew.getId());
//                    getAdapterPosition();

//                  iChildOnclikExpendableRecy.onClick(preferenceNew,position);

                    //((BottomMenuActivity) context).updatePreferencesVi();
                }else {
                    isSelected = true;
                    imageViewStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_selected));
//                   iChildOnclikExpendableRecy.onClick(preferenceNew,position);

               //     PreferenceFragmentNew.getInstance().addData(preferenceNew.getId());
                    //((BottomMenuActivity) context).updatePreferencesVi();
                }

            }
        });
    }
}
