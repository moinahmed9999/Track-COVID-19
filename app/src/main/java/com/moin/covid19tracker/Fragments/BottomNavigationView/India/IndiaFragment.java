package com.moin.covid19tracker.Fragments.BottomNavigationView.India;

import android.content.Context;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.moin.covid19tracker.Fragments.BottomNavigationView.India.IndiaFragmentDirections;
import com.moin.covid19tracker.Adapters.IndianStatesAdapter;
import com.moin.covid19tracker.Models.India;
import com.moin.covid19tracker.Models.IndianState;
import com.moin.covid19tracker.Network.IndiaApiService;
import com.moin.covid19tracker.Network.IndiaApiServiceProvider;
import com.moin.covid19tracker.R;
import com.moin.covid19tracker.Util;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndiaFragment extends Fragment {

    public IndiaFragment() {
        // Required empty public constructor
    }

    private View mView;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private IndianStatesAdapter mAdapter;

    private List<IndianState> states;
    private IndiaApiService mApiService;

    private TextView confirmed;
    private TextView active;
    private TextView recovered;
    private TextView deaths;

    private ProgressBar progressBar;
    private TextView emptyTextView;
    private ConstraintLayout cl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_india, container, false);

        initUI();
        getNationalData();

        return mView;
    }

    private void initUI() {
        mRecyclerView=(RecyclerView)mView.findViewById(R.id.indfr_india_list_recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mApiService = IndiaApiServiceProvider.getIndiaApiService();

        confirmed = (TextView) mView.findViewById(R.id.indfr_india_confirmed_value_text_view);
        active = (TextView) mView.findViewById(R.id.indfr_india_active_value_text_view);
        recovered = (TextView) mView.findViewById(R.id.indfr_india_recovered_value_text_view);
        deaths = (TextView) mView.findViewById(R.id.indfr_india_deaths_value_text_view);

        progressBar = mView.findViewById(R.id.loading_spinner);
        emptyTextView = mView.findViewById(R.id.empty_view);
        emptyTextView.setText("Loading...");
        cl = mView.findViewById(R.id.india_fragment_container);
        cl.setVisibility(View.INVISIBLE);
    }

    private void getNationalData() {
        if (Util.isAppOnLine(getContext())) {
            mApiService.getNationalData().enqueue(new Callback<India>() {
                @Override
                public void onResponse(Call<India> call, Response<India> response) {
                    states = response.body().getStatewise();

                    IndianState total = states.remove(0);
                    confirmed.setText(Util.addCommas(total.getConfirmed()));
                    active.setText(Util.addCommas(total.getActive()));
                    recovered.setText(Util.addCommas(total.getRecovered()));
                    deaths.setText(Util.addCommas(total.getDeaths()));

                    Collections.sort(states, Collections.reverseOrder());

                    int i= states.size() - 1;
                    while (states.get(i).getConfiredCases() == 0 && i>=0) {
                        states.remove(i);
                        i--;
                    }

                    mAdapter = new IndianStatesAdapter(states,getContext());
                    mRecyclerView.setLayoutManager(mLinearLayoutManager);
                    mRecyclerView.setAdapter(mAdapter);
                    setOnStateClickListener();

                    cl.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    emptyTextView.setVisibility(View.INVISIBLE);

                    Context context = getContext();
                    if (context!=null) {
                        Toast toast = Toast.makeText(getContext(),
                                "Click on State for District-wise Distribution", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }

                @Override
                public void onFailure(Call<India> call, Throwable t) {
                    Toast.makeText(getContext(),"Request Failed !!", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(getContext(),"No Internet Connection !!", Toast.LENGTH_LONG).show();
        }
    }

    private void setOnStateClickListener() {
        mAdapter.setOnStateClickListener(position-> {
                IndianState state = states.get(position);
                NavDirections navDirections = IndiaFragmentDirections.actionIndiaToState(
                        state.getState()
                );
                Navigation.findNavController(mView).navigate(navDirections);
        });
    }

}
