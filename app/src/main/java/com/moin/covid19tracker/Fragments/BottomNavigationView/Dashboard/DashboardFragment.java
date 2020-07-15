package com.moin.covid19tracker.Fragments.BottomNavigationView.Dashboard;

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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.moin.covid19tracker.Activities.IndiaStatsActivity;
import com.moin.covid19tracker.Models.India;
import com.moin.covid19tracker.Models.IndiaDaily;
import com.moin.covid19tracker.Models.IndianState;
import com.moin.covid19tracker.Models.World;
import com.moin.covid19tracker.Models.WorldTimeline;
import com.moin.covid19tracker.Network.IndiaApiService;
import com.moin.covid19tracker.Network.IndiaApiServiceProvider;
import com.moin.covid19tracker.Network.WorldApiService;
import com.moin.covid19tracker.Network.WorldApiServiceProvider;
import com.moin.covid19tracker.R;
import com.moin.covid19tracker.Util;
import com.moin.covid19tracker.Activities.WorldStatsActivity;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {

    public DashboardFragment() {
        // Required empty public constructor
    }

    private View mView;
    private TextView worldCases;
    private TextView worldDeaths;
    private TextView worldRecovered;
    private TextView worldActive;
    private Button worldStatsButton;

    private TextView indiaCases;
    private TextView indiaDeaths;
    private TextView indiaRecovered;
    private TextView indiaActive;
    private Button indiaStatsButton;

    private World mWorld;
    private WorldApiService mWorldApiService;

    private List<IndianState> states;
    private List<IndiaDaily> timeline;
    private IndiaApiService mIndiaApiService;

    private HashMap<String,String> confirmedMap;
    private HashMap<String,String> recoveredMap;
    private HashMap<String,String> deathsMap;

    private ConstraintLayout worldCL;
    private ConstraintLayout indiaCL;
    private CardView worldBtn;
    private CardView indiaBtn;

    private ProgressBar progressBar1;
    private TextView emptyTextView1;
    private ProgressBar progressBar2;
    private TextView emptyTextView2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        initUI();
        getWorld();
        if (!Util.getIsWorldStatsDone()) {
            getWorldStats();
        }
        getIndia();
        setOnClickListener();
        return mView;
    }

    private void initUI() {
        worldCases = (TextView)mView.findViewById(R.id.dfr_world_cases_value_text_view);
        worldDeaths = (TextView)mView.findViewById(R.id.dfr_world_deaths_value_text_view);
        worldRecovered = (TextView)mView.findViewById(R.id.dfr_world_recovered_value_text_view);
        worldActive = (TextView)mView.findViewById(R.id.dfr_world_active_value_text_view);
        worldStatsButton = mView.findViewById(R.id.world_stats_button);
        worldStatsButton.setText("World Stats");

        indiaCases = (TextView)mView.findViewById(R.id.dfr_india_cases_value_text_view);
        indiaDeaths = (TextView)mView.findViewById(R.id.dfr_india_deaths_value_text_view);
        indiaRecovered = (TextView)mView.findViewById(R.id.dfr_india_recovered_value_text_view);
        indiaActive = (TextView)mView.findViewById(R.id.dfr_india_active_value_text_view);
        indiaStatsButton = mView.findViewById(R.id.india_stats_button);
        indiaStatsButton.setText("India Stats");

        mWorldApiService = WorldApiServiceProvider.getWorldApiService();
        mIndiaApiService = IndiaApiServiceProvider.getIndiaApiService();

        worldCL = mView.findViewById(R.id.world_cl);
        worldBtn = mView.findViewById(R.id.world_stats_button_cv);
        indiaCL = mView.findViewById(R.id.india_cl);
        indiaBtn = mView.findViewById(R.id.india_stats_button_cv);

        worldCL.setVisibility(View.INVISIBLE);
        indiaCL.setVisibility(View.INVISIBLE);
        worldBtn.setVisibility(View.INVISIBLE);
        indiaBtn.setVisibility(View.INVISIBLE);

        progressBar1 = mView.findViewById(R.id.loading_spinner1);
        emptyTextView1 = mView.findViewById(R.id.empty_view1);
        emptyTextView1.setText("Loading...");

        progressBar2 = mView.findViewById(R.id.loading_spinner2);
        emptyTextView2 = mView.findViewById(R.id.empty_view2);
        emptyTextView2.setText("Loading...");
    }

    private void getWorld() {
        if (Util.isAppOnLine(getContext())) {
            mWorldApiService.getWorld().enqueue(new Callback<World>() {
                @Override
                public void onResponse(Call<World> call, Response<World> response) {
                    mWorld = response.body();

                    assert mWorld != null;
                    worldCases.setText(Util.addCommas(mWorld.getCases()));
                    worldDeaths.setText(Util.addCommas(mWorld.getDeaths()));
                    worldRecovered.setText(Util.addCommas(mWorld.getRecovered()));
                    worldActive.setText(Util.addCommas(mWorld.getActive()));

                    if (Util.getIsWorldStatsDone()) {
                        worldCL.setVisibility(View.VISIBLE);
                        worldBtn.setVisibility(View.VISIBLE);

                        progressBar1.setVisibility(View.INVISIBLE);
                        emptyTextView1.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<World> call, Throwable t) {
                    worldCases.setText(R.string.server_issue);
                    worldDeaths.setText(R.string.server_issue);
                    worldRecovered.setText(R.string.server_issue);
                    worldActive.setText(R.string.server_issue);
                }
            });
        } else {
        }
    }

    private void getWorldStats() {
        if (Util.isAppOnLine(getContext())) {
            mWorldApiService.getWorldStats().enqueue(new Callback<WorldTimeline>() {
                @Override
                public void onResponse(Call<WorldTimeline> call, Response<WorldTimeline> response) {
                    confirmedMap = response.body().getCases();
                    recoveredMap = response.body().getRecovered();
                    deathsMap = response.body().getDeaths();

                    Util.setWorldConfirmedList(confirmedMap);
                    Util.setWorldRecoveredList(recoveredMap);
                    Util.setWorldDeathsList(deathsMap);

                    worldCL.setVisibility(View.VISIBLE);
                    worldBtn.setVisibility(View.VISIBLE);

                    progressBar1.setVisibility(View.INVISIBLE);
                    emptyTextView1.setVisibility(View.INVISIBLE);

                }

                @Override
                public void onFailure(Call<WorldTimeline> call, Throwable t) {
                    Toast.makeText(getContext(),"Request Failed !!", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(getContext(),"No Internet Connection !!", Toast.LENGTH_LONG).show();
        }
    }

    private void getIndia() {
        if (Util.isAppOnLine(getContext())) {
            mIndiaApiService.getNationalData().enqueue(new Callback<India>() {
                @Override
                public void onResponse(Call<India> call, Response<India> response) {
                    states = response.body().getStatewise();

                    timeline = response.body().getCases_time_series();
                    if (!Util.getIsWorldStatsDone()) {
                        indiaStats();
                        Util.setIsWorldStatsDone(true);
                    }

                    IndianState total = states.remove(0);
                    indiaCases.setText(Util.addCommas(total.getConfirmed()));
                    indiaActive.setText(Util.addCommas(total.getActive()));
                    indiaRecovered.setText(Util.addCommas(total.getRecovered()));
                    indiaDeaths.setText(Util.addCommas(total.getDeaths()));

                    if (Util.getIsWorldStatsDone()) {
                        indiaCL.setVisibility(View.VISIBLE);
                        indiaBtn.setVisibility(View.VISIBLE);

                        progressBar2.setVisibility(View.INVISIBLE);
                        emptyTextView2.setVisibility(View.INVISIBLE);
                    }

                }

                @Override
                public void onFailure(Call<India> call, Throwable t) {
                    Toast.makeText(getContext(),"Request Failed !!", Toast.LENGTH_LONG).show();

                    indiaCases.setText(R.string.server_issue);
                    indiaDeaths.setText(R.string.server_issue);
                    indiaRecovered.setText(R.string.server_issue);
                    indiaActive.setText(R.string.server_issue);
                }
            });
        } else {
            Toast.makeText(getContext(),"No Internet Connection !!", Toast.LENGTH_LONG).show();
        }
    }

    private void indiaStats() {
        for (int i=0; i<timeline.size();i++) {
            String[] str = new String[8];
            String confirmed = timeline.get(i).getTotalconfirmed();
            String recovered = timeline.get(i).getTotalrecovered();
            String deaths = timeline.get(i).getTotaldeceased();
            int active = Integer.parseInt(confirmed) - Integer.parseInt(recovered) - Integer.parseInt(deaths);
            str[0] = timeline.get(i).getDate();
            str[1] = confirmed;
            str[2] = recovered;
            str[3] = deaths;
            str[4] = Integer.toString(active);
            str[5] = timeline.get(i).getDailyconfirmed();
            str[6] = timeline.get(i).getDailyrecovered();
            str[7] = timeline.get(i).getDailydeceased();
            Util.getTimeSeries().add(str);
        }
    }

    private void setOnClickListener() {
        indiaStatsButton.setOnClickListener(v-> {
                Intent intent = new Intent(getActivity(), IndiaStatsActivity.class);
                startActivity(intent);
        });

        worldStatsButton.setOnClickListener(v-> {
                Intent intent = new Intent(getActivity(), WorldStatsActivity.class);
                startActivity(intent);
        });
    }

}
