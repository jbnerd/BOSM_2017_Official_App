package pilani.bosm_official_app.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
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
import java.util.HashMap;
import java.util.List;

import pilani.bosm_official_app.R;
import pilani.bosm_official_app.auth.LoginActivity;
import pilani.bosm_official_app.auth.User;
import pilani.bosm_official_app.leaderboard.LeaderboardListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class Leaderboard extends Fragment {

Context context;

    public Leaderboard() {
        // Required empty public constructor
    }

    String host;
    SharedPreferences sharedPreferences;

    private TextView tvRank;
    ListView listView;

    int rank;
    List<User> users = new ArrayList<>();

    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_leaderboard, container, false);

        host = getString(R.string.host_url);
        sharedPreferences = getActivity().getSharedPreferences(LoginActivity.SHARED_USER_DETAILS, Context.MODE_PRIVATE);

        context = getContext();
        listView = (ListView) rootView.findViewById(R.id.listView);
        tvRank = (TextView) rootView.findViewById(R.id.tvRank);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);

        swipeRefreshLayout.setRefreshing(true);
        getData();


        swipeRefreshLayout.setOnRefreshListener(
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
                swipeRefreshLayout.setRefreshing(false);
                parseJSON(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                try {
                    URL url = new URL(host + "events/get_leaderboard.php");
                    String urlParams = "";

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
            JSONArray array = root.getJSONArray("leaderboard");
            error = root.getBoolean("error");
            message = root.getString("message");

            emails = new HashMap<>();

            if (!error) {
                users.clear();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject j = array.getJSONObject(i);

                    User user = new User();
                    user.setEmail(j.getString("email"));
                    user.setMobile(j.getString("mobile"));
                    user.setName(j.getString("name"));
                    user.setScore(j.getInt("score"));
                    if(j.getInt("betting_amount")==0) {
                        user.setBettingAmount(0);
                    }else
                        user.setBettingAmount(j.getInt("betting_amount"));
                    users.add(user);
                    emails.put(user.getEmail(), i+1);
                }
                showData();
            } else {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }


        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, "Please Check your Connection", Toast.LENGTH_SHORT).show();
        }
    }
    HashMap<String, Integer> emails;
    public void showData() {
        int rank = emails.get(sharedPreferences.getString(LoginActivity.SP_EMAIL, ""));

        ListAdapter listAdapter = new LeaderboardListAdapter(context, R.id.tvName, users, rank, getActivity());
        listView.setAdapter(listAdapter);

        tvRank.setText(String.valueOf(rank));

        tvRank.setText(String.valueOf(rank));
    }

  /*  @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
*/
}
