package pilani.bosm_official_app.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.app.Fragment;

import android.transition.Explode;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import pilani.bosm_official_app.Fragments.TabFragments.Events;
import pilani.bosm_official_app.Fragments.TabFragments.Now;
import pilani.bosm_official_app.Fragments.TabFragments.Result;
import pilani.bosm_official_app.R;

/**
 * Created by Prashant on 14-05-2017.
 */


public class Main extends Fragment {

    private BottomNavigationView bottomNavigationView;
    String sport="";
    String id="";
    public static int value=0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment,container,false);

        Bundle bundle = this.getArguments();
        if(bundle!=null){
            if (bundle.getString("sport") != null ) {
                sport=bundle.getString("sport");
                id=bundle.getString("id");
                changeFragment(3);

            }else if(bundle.get("1") != null) {
                changeFragment(bundle.getInt("1"));
            }
        }else
        changeFragment(0);

        bottomNavigationView = (BottomNavigationView)rootView.findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.events) {
                    changeFragment(0);
                    return true;

                } else if (id == R.id.now) {

                    changeFragment(1);
                    return  true;

                } else if (id == R.id.result) {

                    changeFragment(2);
                    return  true;
                }
                return false;

        }

        });

        return rootView;
    }

    public  void changeFragment(int position) {

        Fragment newFragment = null;

        value=position;

        if (position == 0) {
            newFragment = new Events();

        } else if (position == 1 ) {
            newFragment = new Now();

        } else if (position == 2){
            newFragment = new Result();

        } else if (position == 3) {
            newFragment = new Bidding();

            Bundle bundle = new Bundle();
            bundle.putString("sport",sport);
            bundle.putString("id",id);
            newFragment.setArguments(bundle);

        }
        getFragmentManager().beginTransaction().replace(
                R.id.fragmentContainer, newFragment)
                .commit();
    }


}



