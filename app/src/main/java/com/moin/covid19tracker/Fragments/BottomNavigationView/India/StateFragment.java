package com.moin.covid19tracker.Fragments.BottomNavigationView.India;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.moin.covid19tracker.Fragments.BottomNavigationView.India.StateFragmentArgs;
import com.moin.covid19tracker.Adapters.IndianDistrictsAdapter;
import com.moin.covid19tracker.Models.DistrictData;
import com.moin.covid19tracker.Models.IndianStateDistrictWise;
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
public class StateFragment extends Fragment {

    public StateFragment() {
        // Required empty public constructor
    }

    private View mView;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private IndianDistrictsAdapter mAdapter;

    private List<DistrictData> districts;
    private IndiaApiService mApiService;

    private String state;
    private TextView state_name;

    private ProgressBar progressBar;
    private TextView emptyTextView;
    private RelativeLayout rl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_state, container, false);

        initUI();
        setValues();
        getStateData();

        return mView;
    }

    private void initUI() {
        state_name = mView.findViewById(R.id.stfr_state_name);
        mRecyclerView = mView.findViewById(R.id.stfr_district_list_recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mApiService = IndiaApiServiceProvider.getIndiaApiService();

        progressBar = mView.findViewById(R.id.loading_spinner);
        emptyTextView = mView.findViewById(R.id.empty_view);
        emptyTextView.setText("Loading...");
        rl = mView.findViewById(R.id.rl);
        rl.setVisibility(View.INVISIBLE);
    }

    private void setValues() {
        state = StateFragmentArgs.fromBundle(getArguments()).getStateName();
        state_name.setText(state);
    }

    private void getStateData() {
        if (Util.isAppOnLine(getContext())) {
            mApiService.getStateData().enqueue(new Callback<List<IndianStateDistrictWise>>() {
                @Override
                public void onResponse(Call<List<IndianStateDistrictWise>> call, Response<List<IndianStateDistrictWise>> response) {
                    List<IndianStateDistrictWise> states = response.body();
                    for (int i=0;i<states.size();i++) {
                        if (states.get(i).getState().equals(state)) {
                            districts = states.get(i).getDistrictData();
                        }
                    }

                    if (districts!=null) {
                        Collections.sort(districts, Collections.reverseOrder());

                        for (int i=0;i<districts.size();i++) {
                            if (districts.get(i).getDistrict().equals("Unknown")) {
                                DistrictData district = districts.remove(i);
                                districts.add(district);
                                break;
                            }
                        }

                        mAdapter = new IndianDistrictsAdapter(districts, getContext());
                        mRecyclerView.setLayoutManager(mLinearLayoutManager);
                        mRecyclerView.setAdapter(mAdapter);

                        rl.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                        emptyTextView.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<List<IndianStateDistrictWise>> call, Throwable t) {
                    Toast.makeText(getContext(),"Request Failed !!", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(getContext(),"No Internet Connection !!", Toast.LENGTH_LONG).show();
        }

    }

}
