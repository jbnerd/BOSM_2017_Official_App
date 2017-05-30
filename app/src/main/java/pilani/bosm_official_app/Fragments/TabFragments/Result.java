package pilani.bosm_official_app.Fragments.TabFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import pilani.bosm_official_app.R;
import pilani.bosm_official_app.SettingsActivity;
import pilani.bosm_official_app.result.Result_DataAdapter;

/**
 * Created by Prashant on 14-05-2017.
 */


public class Result extends Fragment {

    private ArrayList<Integer> countries;
    private ArrayList<String> time;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.result,container,false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.result_recycler_view);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        countries = new ArrayList<>();
        countries.add(R.drawable.athletics);
        countries.add(R.drawable.swimming);
        countries.add(R.drawable.taekwondo);
        countries.add(R.drawable.badminton);
        countries.add(R.drawable.basketball);
        countries.add(R.drawable.football);
        countries.add(R.drawable.hockey);
        countries.add(R.drawable.tennis);
        countries.add(R.drawable.table_tennis);
        countries.add(R.drawable.volleyball);
        countries.add(R.drawable.weightlifting);
        countries.add(R.drawable.cricket);
        countries.add(R.drawable.tennis);
        countries.add(R.drawable.carrom);
        countries.add(R.drawable.chessrio);
        countries.add(R.drawable.pool);
        countries.add(R.drawable.online);
        countries.add(R.drawable.misc);



        time = new ArrayList<>();
        time.add("Athletics");
        time.add("Swimming");
        time.add("Taekwondo");
        time.add("Badminton");
        time.add("Basketball");
        time.add("Football");
        time.add("Hockey");
        time.add("Tennis");
        time.add("Table Tennis");
        time.add("Volleyball");
        time.add("Weightlifting");
        time.add("Cricket");
        time.add("Squash");
        time.add("Carrom");
        time.add("Chess");
        time.add("Pool");
        time.add("Online");
        time.add("Misc");


        RecyclerView.Adapter adapter = new Result_DataAdapter(countries,time);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

            });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if (child != null && gestureDetector.onTouchEvent(e)) {
                    int position = rv.getChildAdapterPosition(child);
                    Intent in = new Intent(getActivity(),SettingsActivity.class);
                    in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    in.putExtra("bla",time.get(position));
                    startActivity(in);
                    Toast.makeText(getActivity(), time.get(position), Toast.LENGTH_SHORT).show();
                   // Toast.makeText(getContext(),"Link is not prepared yet!", Toast.LENGTH_SHORT).show();
                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });


        return rootView;
    }

}