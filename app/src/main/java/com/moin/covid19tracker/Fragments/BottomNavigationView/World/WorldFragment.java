package com.moin.covid19tracker.Fragments.BottomNavigationView.World;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.moin.covid19tracker.Adapters.CountriesAdapter;
import com.moin.covid19tracker.Models.Country;
import com.moin.covid19tracker.Network.WorldApiService;
import com.moin.covid19tracker.Network.WorldApiServiceProvider;
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
public class WorldFragment extends Fragment {

    public WorldFragment() {
        // Required empty public constructor
    }

    private View mView;
    private LinearLayoutManager mLinearLayoutManager;
    private CountriesAdapter mAdapter;

    private RecyclerView mRecyclerView;
    private SearchView mSearchView;
    private Spinner mSpinner;

    private List<Country> mCountries;
    private WorldApiService mApiService;

    private ProgressBar progressBar;
    private TextView emptyTextView;
    private ConstraintLayout cl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_world, container, false);
        initUI();
        getCountries();
        setHasOptionsMenu(true);
        return mView;
    }

    private void initUI() {
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.wfr_countries_list_recycler_view);

        mSpinner = mView.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.spinner_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mApiService = WorldApiServiceProvider.getWorldApiService();

        progressBar = mView.findViewById(R.id.loading_spinner);
        emptyTextView = mView.findViewById(R.id.empty_view);
        emptyTextView.setText("Loading...");
        cl = mView.findViewById(R.id.countries_fragment_container);
        cl.setVisibility(View.INVISIBLE);
    }

    private void getCountries() {
        if (Util.isAppOnLine(getContext())) {
            mApiService.getCountriesByCases().enqueue(new Callback<List<Country>>() {
                @Override
                public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                    mCountries = response.body();

                    mAdapter = new CountriesAdapter(mCountries, getContext());
                    mRecyclerView.setLayoutManager(mLinearLayoutManager);
                    mRecyclerView.setAdapter(mAdapter);
                    setOnCountryClickListener();

                    cl.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    emptyTextView.setVisibility(View.INVISIBLE);

                    Context context = getContext();
                    if (context!=null) {
                        Toast toast = Toast.makeText(getContext(),
                                "Click on Country for Stats", Toast.LENGTH_SHORT);
//                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                    }
                }

                @Override
                public void onFailure(Call<List<Country>> call, Throwable t) {
                    Toast.makeText(getContext(), "Request Failed !!", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !!", Toast.LENGTH_LONG).show();
        }
    }

    private void setOnCountryClickListener() {
        mAdapter.setOnCountryClickListener(position-> {
                Country country = mCountries.get(position);

                int cases = country.getCasesInt();
                int deaths = country.getDeathsInt();
                int recovered = country.getRecoveredInt();
                float fatality_rate_value = (float) deaths / cases;
                float recovery_rate_value = (float) recovered / cases;

                NavDirections navDirections = WorldFragmentDirections.actionWorldToCountry(
                        country.getCases(),
                        country.getActive(),
                        country.getRecovered(),
                        country.getDeaths(),
                        country.getTodayDeaths(),
                        country.getTodayCases(),
                        country.getCasesPerOneMillion(),
                        country.getDeathsPerOneMillion(),
                        country.getCountryInfo().getFlag(),
                        country.getCountry(),
                        country.getCountryInfo().getLat(),
                        country.getCountryInfo().getLong(),
                        fatality_rate_value * 100,
                        recovery_rate_value * 100
                );

                Navigation.findNavController(mView).navigate(navDirections);
        });

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        sortByCases();
                        break;
                    case 1:
                        sortByActive();
                        break;
                    case 2:
                        sortByRecovered();
                        break;
                    case 3:
                        sortByDeaths();
                        break;
                    default:
                        sortByCases();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void sortByCases() {
        Collections.sort(mCountries, (c1, c2) -> c2.getCasesInt() - c1.getCasesInt());
        mAdapter.notifyDataSetChanged();
    }

    private void sortByActive() {
        Collections.sort(mCountries, (c1, c2) -> c2.getActiveInt() - c1.getActiveInt());
        mAdapter.notifyDataSetChanged();
    }

    private void sortByRecovered() {
        Collections.sort(mCountries, (c1, c2) -> c2.getRecoveredInt() - c1.getRecoveredInt());
        mAdapter.notifyDataSetChanged();
    }

    private void sortByDeaths() {
        Collections.sort(mCountries, (c1, c2) -> c2.getDeathInt() - c1.getDeathInt());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) searchItem.getActionView();

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }
}
