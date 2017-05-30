package pilani.bosm_official_app.Fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.List;

import pilani.bosm_official_app.R;
import pilani.bosm_official_app.Sport;
import pilani.bosm_official_app.SportsListAdapter;


public class Schedule extends Fragment {

    private WebView webView;
    private Firebase mRef;
    ProgressBar loading;

    public Schedule() {
        // Required empty public constructor
    }

    List<Sport> items;
    ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);


        listView = (ListView) rootView.findViewById(R.id.listView);
        new populateList().execute();

        return rootView;
    }


    class populateList extends AsyncTask<Object, Object, Void> {

        @Override
        protected void onPostExecute(Void aVoid) {
            ListAdapter listAdapter = new SportsListAdapter(getContext(), R.id.tvName, items, getActivity());
            listView.setAdapter(listAdapter);
        }

        @Override
        protected Void doInBackground(Object... params) {
            setupList();

            return null;
        }
    }

    private void setupList() {
        items = new ArrayList<>();
        items.add(new Sport("Athletics", R.drawable.athletics));
        items.add(new Sport("Swimming", R.drawable.swimming));
        items.add(new Sport("Taekwondo", R.drawable.taekwondo));
        items.add(new Sport("Badminton", R.drawable.badminton));
        items.add(new Sport("Basketball", R.drawable.basketball));
        items.add(new Sport("Football", R.drawable.football));
        items.add(new Sport("Hockey", R.drawable.hockey));
        items.add(new Sport("Tennis", R.drawable.tennis));
        items.add(new Sport("Table Tennis", R.drawable.table_tennis));
        items.add(new Sport("Volleyball", R.drawable.volleyball));
        items.add(new Sport("Weightlifting", R.drawable.weightlifting));
        items.add(new Sport("Cricket", R.drawable.cricket));
        items.add(new Sport("Squash", R.drawable.tennis));
        items.add(new Sport("Carrom", R.drawable.carrom));
        items.add(new Sport("Chess", R.drawable.chessrio));
        items.add(new Sport("Pool", R.drawable.pool));
        items.add(new Sport("Online", R.drawable.online));
        items.add(new Sport("Misc", R.drawable.misc));
    }

 /*   @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    */
}
