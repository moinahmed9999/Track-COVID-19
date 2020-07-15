package com.moin.covid19tracker.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moin.covid19tracker.Models.IndianState;
import com.moin.covid19tracker.R;
import com.moin.covid19tracker.Util;

import java.util.List;

public class IndianStatesAdapter extends RecyclerView.Adapter<IndianStatesAdapter.IndianStatesViewHolder> {

    private List<IndianState> states;
    private Context context;
    private OnStateClickListener mOnStateClickListener;

    public interface OnStateClickListener {
        void onStateClick(int position);
    }

    public void setOnStateClickListener(OnStateClickListener onStateClickListener) {
        mOnStateClickListener = onStateClickListener;
    }

    public IndianStatesAdapter(List<IndianState> states, Context context) {
        this.states = states;
        this.context = context;
    }

    @NonNull
    @Override
    public IndianStatesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.state_list_item, parent, false);
        return new IndianStatesViewHolder(view, mOnStateClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull IndianStatesViewHolder holder, int position) {
        IndianState indianState = states.get(position);

        holder.state.setText(indianState.getState());
        holder.confirmed.setText(Util.addCommas(indianState.getConfirmed()));
        holder.active.setText(Util.addCommas(indianState.getActive()));
        holder.recovered.setText(Util.addCommas(indianState.getRecovered()));
        holder.deaths.setText(Util.addCommas(indianState.getDeaths()));
    }

    @Override
    public int getItemCount() {
        return states.size();
    }


    public class IndianStatesViewHolder extends RecyclerView.ViewHolder {

        public TextView state;
        public TextView confirmed;
        public TextView active;
        public TextView recovered;
        public TextView deaths;

        public IndianStatesViewHolder(@NonNull View itemView, final OnStateClickListener onStateClickListener) {
            super(itemView);

            state = (TextView) itemView.findViewById(R.id.indlst_states_text_view);
            confirmed = (TextView) itemView.findViewById(R.id.indlst_states_confirmed_text_view);
            active = (TextView) itemView.findViewById(R.id.indlst_states_active_text_view);
            recovered = (TextView) itemView.findViewById(R.id.indlst_states_recovered_text_view);
            deaths = (TextView) itemView.findViewById(R.id.indlst_states_deaths_text_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onStateClickListener!=null) {
                        int position = getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION) {
                            onStateClickListener.onStateClick(position);
                        }
                    }
                }
            });
        }
    }

}
