package info.sumantv.flow.bottommenu.adapter;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;


import info.sumantv.flow.bottommenu.models.IPollOption;
import info.sumantv.flow.bottommenu.viewholders.OptionsViewHolder;

import info.sumantv.flow.R;
import info.sumantv.flow.ipoll.interfaces.other.IOptionsAdapter;
import info.sumantv.flow.utils.UtilityNew;

/**
 * Created by User on 31-07-2018.
 **/

public class OptionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<IPollOption> list;
    IOptionsAdapter iOptionsAdapter;
    int type;
    int width;

    public OptionAdapter(Context context, List<IPollOption> list, IOptionsAdapter iOptionsAdapter, int type) {
        this.context = context;
        this.list = list;
        this.iOptionsAdapter = iOptionsAdapter;
        this.type = type;

        width = UtilityNew.getScreenWidth(context) - UtilityNew.dpSize(context, 10);

        if (list.size() > 0)
            width = list.size() < 5 ? width / list.size() : UtilityNew.dpSize(context, 100);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OptionsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.option_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OptionsViewHolder) {
            OptionsViewHolder optionsViewHolder = (OptionsViewHolder) holder;
            optionsViewHolder.imgOption.setImageResource(list.get(position).image);
            optionsViewHolder.tvOption.setVisibility(type == 0 ? View.GONE : View.VISIBLE);
            optionsViewHolder.tvOption.setText(list.get(position).name);
            optionsViewHolder.itemView.setOnClickListener(v -> {
                iOptionsAdapter.optionClick(list.get(position), position);
            });
            if (type == 0) {
                optionsViewHolder.imgOption.setColorFilter(Color.parseColor("#3b68b1"));
                optionsViewHolder.imgOption.setBackgroundColor(ContextCompat.getColor(context, R.color.transparent));
                optionsViewHolder.imgOption.setPadding(UtilityNew.dpSize(context, 5), UtilityNew.dpSize(context, 5), UtilityNew.dpSize(context, 5), UtilityNew.dpSize(context, 5));
                optionsViewHolder.itemView.setLayoutParams(new LinearLayout.LayoutParams(UtilityNew.dpSize(context, 60), LinearLayout.LayoutParams.MATCH_PARENT));

            } else {
                optionsViewHolder.imgOption.setLayoutParams(new LinearLayout.LayoutParams(UtilityNew.dpSize(context, 35), UtilityNew.dpSize(context, 35)));
                optionsViewHolder.imgOption.setPadding(UtilityNew.dpSize(context, 10), UtilityNew.dpSize(context, 10), UtilityNew.dpSize(context, 10), UtilityNew.dpSize(context, 10));
                optionsViewHolder.tvOption.setTextColor(ContextCompat.getColor(context, R.color.dark_gray));
                optionsViewHolder.itemView.setLayoutParams(new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.MATCH_PARENT));
            }


        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
