package info.dkapp.flow.celebflow.countrydata;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import info.dkapp.flow.bottommenu.menuitemsmanager.fragments.EditProfileFragment;

import info.dkapp.flow.R;
import info.dkapp.flow.celebflow.SignUpFragment;
import info.dkapp.flow.celebflow.interfaces.ICountryAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shiva on 7/17/2018.
 */

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {
    private Context context;
    private List<CountryData> countryData;
    private List<CountryData> copyList;
    //  private ProgressDialog progressDialog;
    private SignUpFragment SignUpFragment;
    ICountryAdapter iCountryAdapter;
    EditProfileFragment editProfileFragment;

    public CountryAdapter(Context context, List<CountryData> list, SignUpFragment signUpFragment, ICountryAdapter iCountryAdapter) {
        this.context = context;
        this.countryData = list;
        copyList = new ArrayList<CountryData>();
        this.SignUpFragment = signUpFragment;
        this.iCountryAdapter = iCountryAdapter;
        copyList.addAll(countryData);
    }

    public CountryAdapter(Context context, List<CountryData> list, EditProfileFragment editProfileFragment, ICountryAdapter iCountryAdapter) {
        this.context = context;
        this.countryData = list;
        copyList = new ArrayList<CountryData>();
        this.editProfileFragment = editProfileFragment;
        this.iCountryAdapter = iCountryAdapter;
        copyList.addAll(countryData);
    }



    @Override
    public CountryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.country_list_item, parent, false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CountryViewHolder holder, final int position) {
        String name = countryData.get(position).getCountryName();
        holder.name.setText(name);
        holder.country_code.setText(countryData.get(position).getDial_code() + "");

        holder.relativeCoun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (editProfileTabFragment!= null){
                    for (int i = 0; i < countryData.size(); i++) {
                        if (countryData.get(i).getCountryName().equals(countryData.get(position).getCountryName())) {

                            editProfileTabFragment.searchCountryCode(countryData.get(position).getCountryCode() + " "
                                    + countryData.get(position).getDial_code(), countryData.get(position).getDial_code());
                            iCountryAdapter.onClickCountry(countryData.get(position),position);
                            break;
                        }
                    }
                }else*/
                if (editProfileFragment != null) {
                    for (int i = 0; i < countryData.size(); i++) {
                        if (countryData.get(i).getCountryName().equals(countryData.get(position).getCountryName())) {

                            editProfileFragment.searchCountryCode(countryData.get(position).getCountryCode() + " "
                                    + countryData.get(position).getDial_code(), countryData.get(position).getDial_code());
                            iCountryAdapter.onClickCountry(countryData.get(position), position);
                            break;
                        }
                    }
                } else {
                    //     progressDialog = new ProgressDialog(mobileNumberActivity, R.style.AppCompatAlertDialogStyle);
                    //    progressDialog = Common.showProgressDialog(mobileNumberActivity, progressDialog);
                    for (int i = 0; i < countryData.size(); i++) {
                        if (countryData.get(i).getCountryName().equals(countryData.get(position).getCountryName())) {

                            SignUpFragment.searchCountryCode(countryData.get(position).getCountryCode() + " "
                                    + countryData.get(position).getDial_code(), countryData.get(position).getDial_code());
                            iCountryAdapter.onClickCountry(countryData.get(position), position);
                            break;
                        }
                    }
                }


            }
        });
    }


    @Override
    public int getItemCount() {
        return countryData.size();
    }

    //***************************************************************************//
    //          FILETER LOGIC
    //***************************************************************************//

    public void filter(String queryText) {
        countryData.clear();
        if (queryText.isEmpty()) {
            countryData.addAll(copyList);
        } else {

            for (CountryData name : copyList) {
                if (name.getCountryName().toLowerCase().contains(queryText.toLowerCase())) {
                    countryData.add(name);
                }
            }

        }

        notifyDataSetChanged();
    }

    public class CountryViewHolder extends RecyclerView.ViewHolder {

        public TextView name, country_code;
        LinearLayout relativeCoun;

        public CountryViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.country_name);
            country_code = (TextView) itemView.findViewById(R.id.country_code);
            relativeCoun = (LinearLayout) itemView.findViewById(R.id.relativeCoun);
        }
    }

}
