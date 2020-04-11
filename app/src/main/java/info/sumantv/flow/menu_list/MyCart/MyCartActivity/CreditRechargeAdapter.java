package info.sumantv.flow.menu_list.MyCart.MyCartActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.sumantv.flow.bottommenu.mypaymentactionsfragment.CreditsModelDatas.CreditPackageInfo;
import info.sumantv.flow.bottommenu.mypaymentactionsfragment.CreditsModelDatas.CurrencyType;

import info.sumantv.flow.R;

/**
 * Created by Chenna on 12-04-2018.
 */

public class CreditRechargeAdapter extends RecyclerView.Adapter<CreditRechargeAdapter.MyViewHolder> {

    private int row_index = -1;
    private CreditPackage creditPackage;
    ArrayList<CreditPackageInfo> creditPageInfo = new ArrayList<>();
    CurrencyType currencyTypeObj;

    public CreditRechargeAdapter(ArrayList<CreditPackageInfo> creditPackageInfo, CurrencyType currencyTypeObj,
                                 CreditPackage mCreditPackage) {
        this.creditPageInfo = creditPackageInfo;
        this.currencyTypeObj = currencyTypeObj;
        this.creditPackage = mCreditPackage;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.creditrecharge_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.creditsCounttxt.setText(String.valueOf(creditPageInfo.get(position).credits));

        if (currencyTypeObj.currencySymbol != null && creditPageInfo.get(position).amount != null) {
            holder.creditAmounttxt.setText(currencyTypeObj.currencySymbol + String.valueOf(creditPageInfo.get(position).amount));
        }

        if (row_index == position) {
            holder.selecteimage.setVisibility(View.VISIBLE);
        } else {
            holder.selecteimage.setVisibility(View.GONE);
        }
        int padding = 15;
        holder.itemView.setPadding(padding, padding, padding, padding);

        holder.itemView.setOnClickListener(v -> refreshAdapter(position));

        changeCellColors(holder, position);
    }

    public void refreshAdapter(int position) {
        row_index = position;
        if (row_index != -1) {
            creditPackage.getSelectedPackagePos(position);
        }
        notifyDataSetChanged();
    }

    private void changeCellColors(MyViewHolder holder, int position) {

        Shader shader = new LinearGradient(0, 0, 0,
                holder.creditsCounttxt.getLineHeight(), Color.parseColor(creditPageInfo.get(position).startColor),
                Color.parseColor(creditPageInfo.get(position).endColor), Shader.TileMode.REPEAT);
        holder.creditsCounttxt.getPaint().setShader(shader);

        Shader shader2 = new LinearGradient(0, 0, 0, holder.creditAmounttxt.getLineHeight(), Color.parseColor(creditPageInfo.get(position).startColor),
                Color.parseColor(creditPageInfo.get(position).endColor), Shader.TileMode.REPEAT);
        holder.creditAmounttxt.getPaint().setShader(shader2);

        Shader shader3 = new LinearGradient(0, 0, 0, holder.creditlabel.getLineHeight(),
                Color.parseColor(creditPageInfo.get(position).startColor),
                Color.parseColor(creditPageInfo.get(position).endColor), Shader.TileMode.REPEAT);
        holder.creditlabel.getPaint().setShader(shader3);


        int colors[] = {Color.parseColor(creditPageInfo.get(position).startColor), Color.parseColor(creditPageInfo.get(position).endColor),
        };
        GradientDrawable gradientdrawable = new GradientDrawable();
        gradientdrawable.setColors(colors);
        gradientdrawable.setCornerRadius(25.0f);
        holder.selecteimage.setBackground(gradientdrawable);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.parentLayout.setBackgroundTintList(null);
            holder.dividerView.setBackground(null);
            holder.parentLayout.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(creditPageInfo.get(position).startColor)));
            holder.dividerView.setBackgroundColor(Color.parseColor(creditPageInfo.get(position).startColor));
        }


    }

    @Override
    public int getItemCount() {
        return creditPageInfo.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.creditlabel)
        TextView creditlabel;

        @BindView(R.id.creditsCounttxt)
        TextView creditsCounttxt;

        @BindView(R.id.selecteimage)
        TextView selecteimage;

        @BindView(R.id.creditAmounttxt)
        TextView creditAmounttxt;

        @BindView(R.id.dividerView)
        View dividerView;

        @BindView(R.id.parentLayout)
        RelativeLayout parentLayout;


        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);

        }
    }

}
