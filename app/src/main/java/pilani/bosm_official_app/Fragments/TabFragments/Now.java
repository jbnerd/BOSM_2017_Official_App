package pilani.bosm_official_app.Fragments.TabFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import pilani.bosm_official_app.R;
import pilani.bosm_official_app.auth.LoginActivity;

/**
 * Created by Prashant on 14-05-2017.
 */

public class Now extends Fragment {

    String host;
    SharedPreferences sharedPreferences;

    public static List<Event> events;


    private RecyclerView recyclerView;

    SwipeRefreshLayout mySwipeRefreshLayout;

  //  private NowArrayAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.now,container,false);


        host = getString(R.string.host_url);
        sharedPreferences = getActivity().getSharedPreferences(LoginActivity.SHARED_USER_DETAILS, Context.MODE_PRIVATE);

        events=new ArrayList<>();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);


        mySwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);

        if (events.isEmpty()) {
            mySwipeRefreshLayout.setRefreshing(true);
            getData();
        } else {
            showData();
        }




        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        getData();
                    }
                }
        );

        return rootView;
    }


    private void getData() {
        class GetData extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mySwipeRefreshLayout.setRefreshing(false);
                parseJSON(s);
                showData();
            }

            @Override
            protected String doInBackground(Void... params) {
                try {
                    URL url = new URL(host + "events/get_events.php");
                    String urlParams = "email=" + sharedPreferences.getString(LoginActivity.SP_EMAIL, "");

                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setDoOutput(true);
                    StringBuilder sb = new StringBuilder();

                    OutputStream os = con.getOutputStream();
                    os.write(urlParams.getBytes());
                    os.flush();
                    os.close();

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    String s = sb.toString().trim();
                    return s;

                } catch (IOException e) {
                    e.printStackTrace();
                    return "error";
                }
            }
        }
        GetData gd = new GetData();
        gd.execute();
    }

    private void parseJSON(String json) {
        boolean error = true;
        String message = "";
        try {
            JSONObject root = new JSONObject(json);
            JSONArray array = root.getJSONArray("events");
            error = root.getBoolean("error");
            message = root.getString("message");
            JSONArray betting_array = root.getJSONArray("bet");

            List<Integer> n = new ArrayList<>();

            if (!error) {
                events.clear();
                for (int i = 0; i < betting_array.length(); i++) {
                    n.add(betting_array.getInt(i));
                }
                for (int i = 0; i < array.length(); i++) {
                    JSONObject j = array.getJSONObject(i);

                    int id = j.getInt("id");
                    int sport = j.getInt("sport");
                    String description = j.getString("description");
                    String venue = j.getString("venue");
                    String team_a = j.getString("team_a");
                    String team_b = j.getString("team_b");
                    String winner = j.getString("winner");
                    int status = j.getInt("status");

                    Calendar date_time = Calendar.getInstance();
                    String dt = j.getString("date_time");
                    int year = Integer.parseInt(dt.substring(0,4));
                    int month = Integer.parseInt(dt.substring(5,7)) - 1;
                    int day = Integer.parseInt(dt.substring(8,10));
                    int hourOfDay = Integer.parseInt(dt.substring(11,13));
                    int minute = Integer.parseInt(dt.substring(14,16));
                    int second = Integer.parseInt(dt.substring(17,19));
                    date_time.set(year, month, day, hourOfDay, minute, second);

                    Event event;
                    if (team_a.isEmpty()) {
                        event = new Event(id, sport, description, venue, date_time, status);
                    } else {
                        if (n.contains(id)) {
                            event = new Event(id, sport, description, venue, date_time, status, team_a, team_b, winner, true);
                        } else {
                            event = new Event(id, sport, description, venue, date_time, status, team_a, team_b, winner, false);
                        }
                    }
                    events.add(event);
                }
            } else {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }


        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Please Check your Connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void showData() {
        RecyclerView.Adapter adapter = new EventsAdapter(getActivity(), events);
        recyclerView.setAdapter(adapter);
    }
}
