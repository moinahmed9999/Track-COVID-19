package com.moin.covid19tracker.Fragments.BottomNavigationView.World;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.moin.covid19tracker.Activities.CountryStatsActivity;
import com.moin.covid19tracker.Fragments.BottomNavigationView.World.CountryFragmentArgs;
import com.moin.covid19tracker.Models.CountryTimeline;
import com.moin.covid19tracker.Network.WorldApiService;
import com.moin.covid19tracker.Network.WorldApiServiceProvider;
import com.moin.covid19tracker.R;
import com.moin.covid19tracker.Util;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CountryFragment extends Fragment {

    public CountryFragment() {
        // Required empty public constructor
    }

    private View mView;

    private ImageView flag;
    private TextView country_name;
    private TextView cases;
    private TextView active;
    private TextView recovered;
    private TextView deaths;
    private TextView new_cases;
    private TextView new_deaths;
    private TextView cpm;
    private TextView dpm;
    private TextView fatality_rate;
    private TextView recovery_rate;
    private Button countryStatsButton;

    private WorldApiService mWorldApiService;

    private HashMap<String,String> confirmedMap;
    private HashMap<String,String> recoveredMap;
    private HashMap<String,String> deathsMap;

    private ProgressBar progressBar;
    private TextView emptyTextView;
    private ConstraintLayout cl;

    private CardView countryStatsButtonCV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_country, container, false);
        initUI();
        setValues();
        getCountryStats();
        setOnClickListener();
        return mView;
    }

    private void initUI() {
        flag = mView.findViewById(R.id.country_flag);
        country_name = mView.findViewById(R.id.country_name);
        cases = mView.findViewById(R.id.country_cases_value);
        active = mView.findViewById(R.id.country_active_value);
        recovered = mView.findViewById(R.id.country_recovered_value);
        deaths = mView.findViewById(R.id.country_deaths_value);
        new_cases = mView.findViewById(R.id.country_new_cases_value);
        new_deaths = mView.findViewById(R.id.country_new_deaths_value);
        cpm = mView.findViewById(R.id.country_cpm_value);
        dpm = mView.findViewById(R.id.country_dpm_value);
        fatality_rate = mView.findViewById(R.id.country_fatality_rate_value);
        recovery_rate = mView.findViewById(R.id.country_recovery_rate_value);
        countryStatsButton = mView.findViewById(R.id.country_stats_button);
        countryStatsButton.setText("Country Stats");
        countryStatsButtonCV = mView.findViewById(R.id.country_stats_button_cv);
        countryStatsButtonCV.setVisibility(View.INVISIBLE);

        mWorldApiService = WorldApiServiceProvider.getWorldApiService();

        progressBar = mView.findViewById(R.id.loading_spinner);
        emptyTextView = mView.findViewById(R.id.empty_view);
        emptyTextView.setText("Loading...");
        cl = mView.findViewById(R.id.constraint_ll);
        cl.setVisibility(View.INVISIBLE);
    }

    private void setValues() {
        String url = CountryFragmentArgs.fromBundle(getArguments()).getFlag();
        Glide.with(getContext())
                .load(url)
                .fitCenter()
                .into(flag);

        country_name.setText(CountryFragmentArgs.fromBundle(getArguments()).getCountryName());
        cases.setText(Util.addCommas(CountryFragmentArgs.fromBundle(getArguments()).getCases()));
        active.setText(Util.addCommas(CountryFragmentArgs.fromBundle(getArguments()).getActive()));
        recovered.setText(Util.addCommas(CountryFragmentArgs.fromBundle(getArguments()).getRecovered()));
        deaths.setText(Util.addCommas(CountryFragmentArgs.fromBundle(getArguments()).getDeaths()));
        new_cases.setText(Util.addCommas(CountryFragmentArgs.fromBundle(getArguments()).getNewCases()));
        new_deaths.setText(Util.addCommas(CountryFragmentArgs.fromBundle(getArguments()).getNewDeaths()));
        cpm.setText(Util.addCommas(CountryFragmentArgs.fromBundle(getArguments()).getCpm()));
        dpm.setText(Util.addCommas(CountryFragmentArgs.fromBundle(getArguments()).getDpm()));
        float fatality_rate_value = CountryFragmentArgs.fromBundle(getArguments()).getFatalityRate();
        float recovery_rate_value = CountryFragmentArgs.fromBundle(getArguments()).getRecoveryRate();

        fatality_rate.setText(String.format("%.2f", fatality_rate_value) + " %");
        recovery_rate.setText(String.format("%.2f", recovery_rate_value) + " %");
    }

    private void getCountryStats() {
        if (Util.isAppOnLine(getContext())) {
            mWorldApiService.getCountryStats(CountryFragmentArgs.fromBundle(getArguments()).getCountryName())
                    .enqueue(new Callback<CountryTimeline>() {
                @Override
                public void onResponse(Call<CountryTimeline> call, Response<CountryTimeline> response) {
                    CountryTimeline timeline = response.body();
                    if (timeline!=null) {
                        confirmedMap = timeline.getTimeline().getCases();
                        recoveredMap = timeline.getTimeline().getRecovered();
                        deathsMap = timeline.getTimeline().getDeaths();
                        countryStatsButtonCV.setVisibility(View.VISIBLE);
                    }

                    Util.setCountryConfirmedList(confirmedMap);
                    Util.setCountryRecoveredList(recoveredMap);
                    Util.setCountryDeathsList(deathsMap);
                    Util.setCountryName(CountryFragmentArgs.fromBundle(getArguments()).getCountryName());

                    cl.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    emptyTextView.setVisibility(View.INVISIBLE);

                }

                @Override
                public void onFailure(Call<CountryTimeline> call, Throwable t) {
                    Toast.makeText(getContext(),"Request Failed !!", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(getContext(),"No Internet Connection !!", Toast.LENGTH_LONG).show();
        }
    }

    private void setOnClickListener() {
        countryStatsButton.setOnClickListener(v-> {
                Intent intent = new Intent(getActivity(), CountryStatsActivity.class);
                startActivity(intent);
        });
    }
}
