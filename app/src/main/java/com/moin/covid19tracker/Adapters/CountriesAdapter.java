package com.moin.covid19tracker.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.moin.covid19tracker.Models.Country;
import com.moin.covid19tracker.R;
import com.moin.covid19tracker.Util;

import java.util.ArrayList;
import java.util.List;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.CountriesViewHolder> implements Filterable {

    private List<Country> countries;
    private List<Country> countriesFullList;
    private Context context;
    private OnCountryClickListener mOnCountryClickListener;


    public interface OnCountryClickListener {
        void onCountryClick(int position);
    }

    public void setOnCountryClickListener(OnCountryClickListener onCountryClickListener) {
        mOnCountryClickListener = onCountryClickListener;
    }

    public CountriesAdapter(List<Country> countries, Context context) {
        this.countries = countries;
        countriesFullList = new ArrayList<>(countries);
        this.context = context;
    }

    @NonNull
    @Override
    public CountriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.countries_list_item, parent, false);

        CountriesViewHolder holder = new CountriesViewHolder(view, mOnCountryClickListener);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CountriesViewHolder holder, int position) {
        Country country = countries.get(position);

        Glide.with(context)
                .load(country.getCountryInfo().getFlag())
                .fitCenter()
                .into(holder.flag);

        holder.country.setText(country.getCountry());
        holder.cases.setText(Util.addCommas(country.getCases()));
        holder.active.setText(Util.addCommas(country.getActive()));
        holder.deaths.setText(Util.addCommas(country.getDeaths()));
        holder.recovered.setText(Util.addCommas(country.getRecovered()));

    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    @Override
    public Filter getFilter() {
        return countriesFilter;
    }

    private Filter countriesFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Country> filteredCountries = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredCountries.addAll(countriesFullList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Country country : countriesFullList) {
                    if (country.getCountry().toLowerCase().contains(filterPattern)) {
                        filteredCountries.add(country);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredCountries;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            countries.clear();
            countries.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public static class CountriesViewHolder extends RecyclerView.ViewHolder {

        public ImageView flag;
        public TextView country;
        public TextView cases;
        public TextView active;
        public TextView deaths;
        public TextView recovered;

        public CountriesViewHolder(@NonNull View itemView, final OnCountryClickListener onCountryClickListener) {
            super(itemView);

            flag = (ImageView) itemView.findViewById(R.id.country_flag_image_view);
            country = (TextView) itemView.findViewById(R.id.country_name_text_view);
            cases = (TextView) itemView.findViewById(R.id.country_cases_text_view);
            active = (TextView) itemView.findViewById(R.id.country_active_text_view);
            deaths = (TextView) itemView.findViewById(R.id.country_deaths_text_view);
            recovered = (TextView) itemView.findViewById(R.id.country_recovered_text_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onCountryClickListener!=null) {
                        int position = getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION) {
                            onCountryClickListener.onCountryClick(position);
                        }
                    }
                }
            });
        }
    }

}
