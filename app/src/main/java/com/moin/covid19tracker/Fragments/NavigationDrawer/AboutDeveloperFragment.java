package com.moin.covid19tracker.Fragments.NavigationDrawer;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.moin.covid19tracker.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutDeveloperFragment extends Fragment {

    public AboutDeveloperFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_developer, container, false);
        ImageView dp = view.findViewById(R.id.profile_image);
        Glide.with(view)
                .load(R.drawable.moin_dp_copy)
                .apply(RequestOptions.circleCropTransform())
                .into(dp);

        ImageView fb = view.findViewById(R.id.fb);
        Glide.with(view)
                .load(R.drawable.ic_facebook)
                .fitCenter()
                .into(fb);

        ImageView insta = view.findViewById(R.id.insta);
        Glide.with(view)
                .load(R.drawable.ic_instagram)
                .fitCenter()
                .into(insta);

        ImageView linkedin = view.findViewById(R.id.linkedin);
        Glide.with(view)
                .load(R.drawable.ic_linkedin)
                .fitCenter()
                .into(linkedin);

        ImageView gmail = view.findViewById(R.id.gmail);
        Glide.with(view)
                .load(R.drawable.ic_gmail)
                .fitCenter()
                .into(gmail);

        fb.setOnClickListener(v-> {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.facebook.com/m4moin")));
                } catch (ActivityNotFoundException e) {
                }
        });

        insta.setOnClickListener(v-> {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.instagram.com/_moinahmed/")));
                } catch (ActivityNotFoundException e) {
                }
        });

        linkedin.setOnClickListener(v-> {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.linkedin.com/in/moin-ahmed-082377154")));
                } catch (ActivityNotFoundException e) {
                }
        });

        gmail.setOnClickListener(v-> {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL,new String[] {"moinahmed_bt2k18@dtu.ac.in"});
                if (intent.resolveActivity(getActivity().getPackageManager())!=null)
                    startActivity(intent);
        });

        return view;
    }
}
