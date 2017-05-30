package pilani.bosm_official_app.Fragments.TabFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import pilani.bosm_official_app.Fragments.Main;
import pilani.bosm_official_app.Fragments.TabFragments.Events_Day.day_1;
import pilani.bosm_official_app.Fragments.TabFragments.Events_Day.day_2;
import pilani.bosm_official_app.Fragments.TabFragments.Events_Day.day_3;
import pilani.bosm_official_app.Fragments.TabFragments.Events_Day.day_4;
import pilani.bosm_official_app.R;

/**
 * Created by Prashant on 14-05-2017.
 */

public class Events extends Fragment {

    TextView day1,day2,day3,day4;
    FrameLayout layout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.events, container, false);
        initializeViews(rootView);

        day1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(1);
            }
        });
        day2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(2);
            }
        });
        day3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(3);
            }
        });
        day4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(4);
            }
        });

        return rootView;
    }


    private void initializeViews(View v){
        day1=(TextView)v.findViewById(R.id.day1);
        day2=(TextView)v.findViewById(R.id.day2);
        day3=(TextView)v.findViewById(R.id.day3);
        day4=(TextView)v.findViewById(R.id.day4);
        layout=(FrameLayout)v.findViewById(R.id.day_frame);
    }

    private void changeFragment(int position) {

        layout.setVisibility(View.VISIBLE);
        Fragment newFragment = null;

            Main.value=4;

        switch (position){
            case 1:
                newFragment = new day_1();
                break;
            case 2:
                newFragment = new day_2();
                break;
            case 3:
                newFragment = new day_3();
                break;
            case 4:
                newFragment = new day_4();
                break;
        }

        getFragmentManager().beginTransaction().replace(
                R.id.day_frame, newFragment)
                .commit();
    }
}