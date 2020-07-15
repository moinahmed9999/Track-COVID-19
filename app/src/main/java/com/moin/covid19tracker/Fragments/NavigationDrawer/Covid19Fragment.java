package com.moin.covid19tracker.Fragments.NavigationDrawer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.moin.covid19tracker.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Covid19Fragment extends Fragment {

    public Covid19Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_covid19, container, false);

        ImageView symptoms = view.findViewById(R.id.symptoms_image);
        Glide.with(view)
                .load(R.drawable.covid19_symptom_copy)
                .fitCenter()
                .into(symptoms);

        ImageView prevention = view.findViewById(R.id.prevention_image);
        Glide.with(view)
                .load(R.drawable.stop_the_spread_of_germs_copy)
                .fitCenter()
                .into(prevention);

        return view;
    }
}
