package com.moin.covid19tracker.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moin.covid19tracker.Models.DistrictData;
import com.moin.covid19tracker.R;
import com.moin.covid19tracker.Util;

import java.util.List;

public class IndianDistrictsAdapter extends RecyclerView.Adapter<IndianDistrictsAdapter.IndianDistrictsViewHolder>{

    private List<DistrictData> districts;
    private Context context;

    public IndianDistrictsAdapter(List<DistrictData> districts, Context context) {
        this.districts = districts;
        this.context = context;
    }

    @NonNull
    @Override
    public IndianDistrictsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.district_list_item, parent, false);
        return new IndianDistrictsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IndianDistrictsViewHolder holder, int position) {
        DistrictData districtData = districts.get(position);
        holder.district.setText(districtData.getDistrict());
        holder.confirmed.setText(Util.addCommas(districtData.getConfirmed()));
    }

    @Override
    public int getItemCount() {
        return districts.size();
    }

    public class IndianDistrictsViewHolder extends RecyclerView.ViewHolder {

        public TextView district;
        public TextView confirmed;

        public IndianDistrictsViewHolder(@NonNull View itemView) {
            super(itemView);

            district = (TextView) itemView.findViewById(R.id.dlst_district_name);
            confirmed = (TextView) itemView.findViewById(R.id.dlst_district_confirmed);
        }
    }
}
