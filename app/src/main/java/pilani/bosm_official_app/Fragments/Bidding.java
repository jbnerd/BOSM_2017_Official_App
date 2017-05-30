package pilani.bosm_official_app.Fragments;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import pilani.bosm_official_app.Config;
import pilani.bosm_official_app.Fragments.TabFragments.Event;

import pilani.bosm_official_app.R;
import pilani.bosm_official_app.auth.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class Bidding extends Fragment implements View.OnClickListener {


    private String host;
    private SharedPreferences sharedPreferences;

    private String id;
    private TextView tvDate;
    private TextView tvDay;
    private TextView tvTime;
    private TextView tvStatus;
    private TextView tvVenue;
    private TextView tvTeamA;
    private TextView tvTeamB;
    private LinearLayout container2;

    private ImageView ivBet;
    private TextView tvBidAmount;
    private ImageView ivPowBet;
    private TextView tvPowBidAmount;
    private TextView tvWallet;
    private TextView tvTimerPowBet;
    private TextView tvTimerSpin;
    private ImageView ivSpin;
    private TextView tvWon;

    SwipeRefreshLayout mySwipeRefreshLayout;

    private Event event;

    //user
    private int score;
    private Calendar powBidTime;
    private Calendar slotTime;
    private int luck;
    private int bettingAmount;

    //betting
    private boolean done;
    private String team;
    private int amount;
    private boolean powerBid;

    private CardView cvTeamA;
    private CardView cvTeamB;
    private CardView cardViewWon;
    private CardView cardViewWallet;
    private CardView cardViewBet;
    private CardView cardViewPowBet;
    private CardView cardViewSpin;

    private boolean cvBetEnabled = true;
    private boolean cvPowBetEnabled = true;
    private boolean cvSpinEnabled = true;

    CountDownTimer countDownTimer;
    CountDownTimer countDownTimer2;

    final HashMap<Integer, Integer> items = new HashMap<>();

    Context context;

    public Bidding() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_bidding, container, false);

        host = getString(R.string.host_url);
       //host=" http://bits-bosm.org/rouletteadmin/bosm/";
        sharedPreferences = getActivity().getSharedPreferences(LoginActivity.SHARED_USER_DETAILS, Context.MODE_PRIVATE);

        context = rootview.getContext();
        Bundle bundle=this.getArguments();
        String sport_id = bundle.getString("sport");
        id = bundle.getString("id");
       // Toast.makeText(context,id + sharedPreferences.getString(LoginActivity.SP_EMAIL, "") ,Toast.LENGTH_LONG).show();



        mySwipeRefreshLayout = (SwipeRefreshLayout) rootview.findViewById(R.id.swiperefresh);

       // tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvDate = (TextView) rootview.findViewById(R.id.tvDate);
        tvDay = (TextView) rootview.findViewById(R.id.tvDay);
        tvTime = (TextView) rootview.findViewById(R.id.tvTime);
        tvStatus = (TextView) rootview.findViewById(R.id.tvStatus);
        tvVenue = (TextView) rootview.findViewById(R.id.tvVenue);
        tvTeamA = (TextView) rootview.findViewById(R.id.tvTeamA);
        tvTeamB = (TextView) rootview.findViewById(R.id.tvTeamB);
        container2 = (LinearLayout) rootview.findViewById(R.id.container);
        tvWallet = (TextView) rootview.findViewById(R.id.tvWallet);
        tvWon = (TextView) rootview.findViewById(R.id.tvWon);
        cvTeamA = (CardView) rootview.findViewById(R.id.cvTeamA);
        cvTeamB = (CardView) rootview.findViewById(R.id.cvTeamB);
        cardViewWallet = (CardView) rootview.findViewById(R.id.card_view_wallet);
        cardViewBet = (CardView) rootview.findViewById(R.id.card_view_bid);
        cardViewPowBet = (CardView) rootview.findViewById(R.id.card_view_pow_bid);
        cardViewPowBet = (CardView) rootview.findViewById(R.id.card_view_pow_bid);
        cardViewSpin = (CardView) rootview.findViewById(R.id.card_view_spin);
        tvTimerPowBet = (TextView) rootview.findViewById(R.id.tvTimerPowBet);
        tvTimerSpin = (TextView) rootview.findViewById(R.id.tvTimerSpin);
        cardViewWon = (CardView) rootview.findViewById(R.id.cvWon);

        tvBidAmount = (TextView) rootview.findViewById(R.id.tvBidAmount);
        tvPowBidAmount = (TextView) rootview.findViewById(R.id.tvPowBidAmount);
        ivBet = (ImageView) rootview.findViewById(R.id.ivBet);
        ivPowBet = (ImageView) rootview.findViewById(R.id.ivPowBet);
        ivSpin = (ImageView) rootview.findViewById(R.id.ivSpin);

        cardViewWallet.setOnClickListener(this);
        cardViewBet.setOnClickListener(this);
        cardViewPowBet.setOnClickListener(this);
        cardViewSpin.setOnClickListener(this);

        mySwipeRefreshLayout.setRefreshing(true);
            //container.setVisibility(View.GONE);
            getData();

            mySwipeRefreshLayout.setOnRefreshListener(
                    new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                           getData();
                        }
                    }
            );

        items.put(0, 1);
        items.put(1, 2);
        items.put(2, 3);
        items.put(3, 4);
        items.put(4, 5);
        items.put(5, 6);
        items.put(6, 7);
        items.put(7, 8);
        items.put(8, 9);
        items.put(9, 11);
        items.put(10, 12);
        items.put(11, 13);
        items.put(12, 14);
        items.put(13, 15);
        items.put(14, 16);

        return rootview;
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
            }

            @Override
            protected String doInBackground(Void... params) {
                int tmp;
                String data = "";
                try {
                    URL url = new URL(host + "events/get_event.php");
                    String urlParams = "id=" + id +
                            "&email=" + sharedPreferences.getString(LoginActivity.SP_EMAIL, "");

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
        String message = "error!";
        try {
           // Toast.makeText(context,"1111",Toast.LENGTH_LONG).show();
            JSONObject root = new JSONObject(json);
            JSONObject j = root.getJSONObject("event");
            JSONObject user = root.getJSONObject("user");
            JSONObject bet = root.getJSONObject("bet");
            error = root.getBoolean("error");
            message = root.getString("message");

            if (!error) {
               // Toast.makeText(context,"22222211",Toast.LENGTH_LONG).show();
                //event
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
                int year = Integer.parseInt(dt.substring(0, 4));
                int month = Integer.parseInt(dt.substring(5, 7)) - 1;
                int day = Integer.parseInt(dt.substring(8, 10));
                int hourOfDay = Integer.parseInt(dt.substring(11, 13));
                int minute = Integer.parseInt(dt.substring(14, 16));
                int second = Integer.parseInt(dt.substring(17, 19));
                date_time.set(year, month, day, hourOfDay, minute, second);

                if (team_a.isEmpty()) {
                    event = new Event(id, sport, description, venue, date_time, status);
                } else {
                    event = new Event(id, sport, description, venue, date_time, status, team_a, team_b, winner);
                }

                //user

                score = user.getInt("score");
                luck = user.getInt("luck");
                bettingAmount = user.getInt("betting_amount");

                powBidTime = Calendar.getInstance();
                dt = user.getString("pow_bid_time");
                year = Integer.parseInt(dt.substring(0, 4));
                month = Integer.parseInt(dt.substring(5, 7)) - 1;
                day = Integer.parseInt(dt.substring(8, 10));
                hourOfDay = Integer.parseInt(dt.substring(11, 13));
                minute = Integer.parseInt(dt.substring(14, 16));
                second = Integer.parseInt(dt.substring(17, 19));
                powBidTime.set(year, month, day, hourOfDay, minute, second);

               // Toast.makeText(context,"33333",Toast.LENGTH_LONG).show();

                slotTime = Calendar.getInstance();
                dt = user.getString("slot_time");
                year = Integer.parseInt(dt.substring(0, 4));
                month = Integer.parseInt(dt.substring(5, 7)) - 1;
                day = Integer.parseInt(dt.substring(8, 10));
                hourOfDay = Integer.parseInt(dt.substring(11, 13));
                minute = Integer.parseInt(dt.substring(14, 16));
                second = Integer.parseInt(dt.substring(17, 19));
                slotTime.set(year, month, day, hourOfDay, minute, second);

               // Toast.makeText(context,"44444",Toast.LENGTH_LONG).show();
                //bet

                done = bet.getBoolean("done");
                if (done) {
                    team = bet.getString("team");
                    amount = bet.getInt("amount");
                    powerBid = bet.getInt("power_bid") == 1;
                } else {
                    team = "";
                }

                container2.setVisibility(View.VISIBLE);
               // Toast.makeText(context," vvvvvv",Toast.LENGTH_LONG).show();
                showData();
                //Toast.makeText(context,"dddddddd",Toast.LENGTH_LONG).show();
            } else {
                //Toast.makeText(context,"5555555",Toast.LENGTH_LONG).show();
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                container2.setVisibility(View.GONE);
            }


        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, "Please Check your Connection", Toast.LENGTH_SHORT).show();
            container2.setVisibility(View.GONE);
        }
    }

    @SuppressLint("SimpleDateFormat")
    public void showData() {

        //Toast.makeText(context,"999999",Toast.LENGTH_LONG).show();
        tvTeamA.setText(event.getTeam_a());
        tvTeamB.setText(event.getTeam_b());
        Calendar calendar = event.getDateTime();
        SimpleDateFormat f1 = new SimpleDateFormat("dd MMM yyyy");
        tvDate.setText(f1.format(calendar.getTime()));
        SimpleDateFormat f2 = new SimpleDateFormat("EEE");
        tvDay.setText(f2.format(calendar.getTime()));
        SimpleDateFormat f3 = new SimpleDateFormat("hh:mm a");
        tvTime.setText(f3.format(calendar.getTime()));

        tvVenue.setText(event.getVenue());
        tvWallet.setText(""+score);
       // Toast.makeText(context,"888888",Toast.LENGTH_LONG).show();
        refreshView();
    }

    private void changeTeamColor(String team, CardView cardView) {
        if (event.getWinner().equals(team)) {
            cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.bosm_roulette));
        } else {
            cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.color_red));
        }
    }

    private void refreshView() {
        if (event.getStatus() == -1) {
            tvStatus.setText(Config.getStatusName(-1));
            tvStatus.setTextColor(ContextCompat.getColor(context ,R.color.black));
            cardViewWon.setVisibility(View.GONE);
        } else if (event.getStatus() == 0) {
            tvStatus.setText(Config.getStatusName(0));
           tvStatus.setTextColor(ContextCompat.getColor(context, R.color.fav_green));
            cardViewWon.setVisibility(View.GONE);
        } else if (event.getStatus() == 1) {
            cardViewWon.setVisibility(View.VISIBLE);
            tvStatus.setText(Config.getStatusName(1));
            tvWon.setText(event.getWinner());
            tvStatus.setTextColor(ContextCompat.getColor(context, R.color.black));
        }

        if (event.getStatus() == 1 && done) {
            if (team.equals(event.getTeam_a())) {
                changeTeamColor(event.getTeam_a(), cvTeamA);
            } else {
                changeTeamColor(event.getTeam_b(), cvTeamB);
            }
        }

        if (score <= 20) {
            setWalletCritical();
        } else {
            removeWalletCritical();
        }

        if (done) {
            if (powerBid) {
                leftAlign(ivPowBet);
                tvPowBidAmount.setText("$"+amount);
                tvPowBidAmount.setVisibility(View.VISIBLE);
                freezeCard(cardViewPowBet);
                disableBet();
            } else {
                leftAlign(ivBet);
                tvBidAmount.setText("$"+amount);
                tvBidAmount.setVisibility(View.VISIBLE);
                freezeCard(cardViewBet);
                disablePowBet();
            }
        } else {
            if (event.getStatus() != -1) {
                disableBet();
                disablePowBet();
            }
            if (powBidTime.after(Calendar.getInstance())) {
                disablePowBet();
                ivPowBet.setVisibility(View.GONE);
                long end = powBidTime.getTimeInMillis();
                long start = Calendar.getInstance().getTimeInMillis();
                final long[] duration = {end - start};

                countDownTimer = new CountDownTimer(duration[0], 1000) {

                    public void onTick(long millisUntilFinished) {
                        long sec = TimeUnit.MILLISECONDS.toSeconds(Math.abs(duration[0])) % 60;
                        long min = TimeUnit.MILLISECONDS.toMinutes(Math.abs(duration[0])) % 60;
                        long hr = TimeUnit.MILLISECONDS.toHours(Math.abs(duration[0]));
                        tvTimerPowBet.setVisibility(View.VISIBLE);
                        tvTimerPowBet.setText(hr+":"+String.format("%02d", min)+":"+String.format("%02d", sec));
                        duration[0] -= 1000;
                    }

                    public void onFinish() {
                        tvTimerPowBet.setVisibility(View.GONE);
                        ivPowBet.setVisibility(View.VISIBLE);
                        enablePowBet();
                    }
                }.start();
            }
        }
        if (slotTime.after(Calendar.getInstance())) {
            disableSpin();
            ivSpin.setVisibility(View.GONE);
            long end = slotTime.getTimeInMillis();
            long start = Calendar.getInstance().getTimeInMillis();
            final long[] duration = {end - start};

            countDownTimer2 = new CountDownTimer(duration[0], 1000) {

                public void onTick(long millisUntilFinished) {
                    long sec = TimeUnit.MILLISECONDS.toSeconds(Math.abs(duration[0])) % 60;
                    long min = TimeUnit.MILLISECONDS.toMinutes(Math.abs(duration[0])) % 60;
                    long hr = TimeUnit.MILLISECONDS.toHours(Math.abs(duration[0]));
                    tvTimerSpin.setVisibility(View.VISIBLE);
                    tvTimerSpin.setText(hr+":"+String.format("%02d", min)+":"+String.format("%02d", sec));
                    duration[0] -= 1000;
                }

                public void onFinish() {
                    tvTimerSpin.setVisibility(View.GONE);
                    ivSpin.setVisibility(View.VISIBLE);
                    enableSpin();
                }
            }.start();
           // Toast.makeText(context,"10101010101001",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            countDownTimer.cancel();
            countDownTimer2.cancel();
        } catch (Exception e) {}
    }

    private void disableBet() {
        cvBetEnabled = false;
        cardViewBet.setCardBackgroundColor(ContextCompat.getColor(context, R.color.grey));
    }

    private void disablePowBet() {
        cvPowBetEnabled = false;
        cardViewPowBet.setCardBackgroundColor(ContextCompat.getColor(context, R.color.grey));
    }

    private void disableSpin() {
        cvSpinEnabled = false;
        cardViewSpin.setCardBackgroundColor(ContextCompat.getColor(context, R.color.grey));
    }

    private void removeWalletCritical() {
        cardViewWallet.setCardBackgroundColor(ContextCompat.getColor(context, R.color.bosm_roulette));
    }

    private void setWalletCritical() {
        cardViewWallet.setCardBackgroundColor(ContextCompat.getColor(context, R.color.errorColor));
    }

    private void enableBet() {
        cvBetEnabled = true;
        cardViewBet.setCardBackgroundColor(ContextCompat.getColor(context, R.color.bid));
    }

    private void enablePowBet() {
        cvPowBetEnabled = true;
        cardViewPowBet.setCardBackgroundColor(ContextCompat.getColor(context, R.color.power_bid_active));
    }

    private void enableSpin() {
        cvSpinEnabled = true;
        cardViewSpin.setCardBackgroundColor(ContextCompat.getColor(context, R.color.slot));
    }

    private void freezeCard(CardView cardView) {
        disableBet();
        disablePowBet();
        cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.blue));
    }

    private void leftAlign(View view) {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) view.getLayoutParams();
        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        view.setLayoutParams(lp);
    }

    private void centerAlign(View view) {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) view.getLayoutParams();
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        view.setLayoutParams(lp);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.card_view_wallet) {
            showWalletDialog();
        } else if (id == R.id.card_view_bid && cvBetEnabled) {
            showBetDialog(false);
        } else if (id == R.id.card_view_pow_bid && cvPowBetEnabled) {
            showBetDialog(true);
        } else if (id == R.id.card_view_spin && cvSpinEnabled) {
            showSpinDialog();
        }
    }

    private void showWalletDialog () {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_wallet);

        final TextView tvAmount = (TextView) dialog.findViewById(R.id.tvAmount);
        final TextView tvBet = (TextView) dialog.findViewById(R.id.tvBet);

        tvAmount.setText("$"+score);
        tvBet.setText("$"+bettingAmount);

        dialog.show();
    }

    private void showBetDialog (final boolean powerBid) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_bet);

        if (powerBid) {
            TextView tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);
            ImageView ivTitle = (ImageView) dialog.findViewById(R.id.ivTitle);
            tvTitle.setText("Power Bet");
            ivTitle.setImageResource(R.drawable.power_bid);
        }

        final RadioGroup rgTeam = (RadioGroup) dialog.findViewById(R.id.rgTeam);
        final RadioButton rbA = (RadioButton) dialog.findViewById(R.id.rbA);
        final RadioButton rbB = (RadioButton) dialog.findViewById(R.id.rbB);

        rbA.setText(event.getTeam_a());
        rbB.setText(event.getTeam_b());

        if (powerBid) {
            LinearLayout container5 = (LinearLayout) dialog.findViewById(R.id.container);
            container5.setBackgroundColor(ContextCompat.getColor(context, R.color.power_bid_active));
        }

        final EditText etAmount = (EditText) dialog.findViewById(R.id.etAmount);

        final Button bEdit = (Button) dialog.findViewById(R.id.bBid);
        bEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = etAmount.getText().toString();
                if (value.isEmpty() || Integer.parseInt(value) <= 0) {
                    Toast.makeText(context, "Enter an amount", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.parseInt(value) > score) {
                    Toast.makeText(context, "Cannot be more than your wallet", Toast.LENGTH_SHORT).show();
                    return;
                }
                int qty;
                try {
                    qty = Integer.parseInt(value);
                } catch (NumberFormatException e) {
                    Toast.makeText(context, "Not a valid number", Toast.LENGTH_SHORT).show();
                    return;
                }
                int powBid;
                if (powerBid) {
                    powBid = 1;
                } else {
                    powBid = 0;
                }

                String team;
                if (rgTeam.getCheckedRadioButtonId() == rbA.getId()) {
                    team = event.getTeam_a();
                } else {
                    team = event.getTeam_b();
                }

                placeBetOnline(powBid, team, qty, dialog);
            }
        });

        final Button bCancel = (Button) dialog.findViewById(R.id.bCancel);
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    ImageView left;
    ImageView mid;
    ImageView right;

    private void showSpinDialog() {

        spin_dialog = new Dialog(context);
        spin_dialog.setContentView(R.layout.dialog_spin_n_earn);

        left = (ImageView) spin_dialog.findViewById(R.id.left);
        mid = (ImageView) spin_dialog.findViewById(R.id.mid);
        right = (ImageView) spin_dialog.findViewById(R.id.right);

        final TextView tvWon = (TextView) spin_dialog.findViewById(R.id.tvWon);
        tvWon.setVisibility(View.GONE);

        final Button bSpin = (Button) spin_dialog.findViewById(R.id.bSpin);
        bSpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bSpin.setOnClickListener(null);
                counter = 0;
                int interval = 15/luck;
                values = new ArrayList<Integer>();
                int base = 0;
                for (int i = 0; i< luck; i++) {
                    values.add(new Random().nextInt(interval) + base);
                    base += interval;
                }
                final int final_n1 = new Random().nextInt(luck);
                final int final_n2 = new Random().nextInt(luck);
                final int final_n3 = new Random().nextInt(luck);


                Config config = new Config();

                boolean won = false;
                if (final_n1 == final_n2 && final_n1 == final_n3) {
                    won = true;
                }


                final boolean finalWon = won;

                coun = 12;
                CountDownTimer countDownTimer3 = new CountDownTimer(5000, 250) {

                    public void onTick(long millisUntilFinished) {
                        coun--;
                        if (coun > 0) {
                            Config config = new Config();
                            int n1 = new Random().nextInt(15);
                            int n2 = new Random().nextInt(15);
                            int n3 = new Random().nextInt(15);

                            left.setImageResource(config.items.get(items.get(n1)).getImage());
                            mid.setImageResource(config.items.get(items.get(n2)).getImage());
                            right.setImageResource(config.items.get(items.get(n3)).getImage());
                        } else {
                            bSpin.setVisibility(View.INVISIBLE);
                            tvWon.setVisibility(View.VISIBLE);
                            if (finalWon) {
                                tvWon.setText("WON");
                            } else {
                                tvWon.setText("LOST");
                            }

                            Config config = new Config();

                            left.setImageResource(config.items.get(items.get(values.get(final_n1))).getImage());
                            mid.setImageResource(config.items.get(items.get(values.get(final_n2))).getImage());
                            right.setImageResource(config.items.get(items.get(values.get(final_n3))).getImage());
                        }
                    }

                    public void onFinish() {
                        slotOnline(spin_dialog, finalWon);
                    }
                }.start();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(spin_dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        spin_dialog.show();

        spin_dialog.getWindow().setAttributes(lp);
    }

    int coun = 6;
    Dialog spin_dialog;
    boolean won;

    List<Integer> values;

    int counter = 0;

    private void placeBetOnline(final int _power_bid, final String _team, final int _amount, final Dialog dialog) {
        class GetData extends AsyncTask<Void, Void, String> {
            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(context, "", "Please Wait...", true);
            }

            @Override
            protected String doInBackground(Void... params) {
                try {
                    URL url = new URL(host + "bet/bet.php");

                    Calendar calendar = Calendar.getInstance();
                    if (_power_bid == 1) {
                        calendar.add(Calendar.HOUR, 4);
                    }

                    String urlParams = "id=" + id +
                            "&email=" + sharedPreferences.getString(LoginActivity.SP_EMAIL, "") +
                            "&team=" + _team +
                            "&amount=" + _amount +
                            "&power_bid=" + _power_bid +
                            "&y=" + calendar.get(Calendar.YEAR) +
                            "&m=" + (calendar.get(Calendar.MONTH)+1) +
                            "&d=" + calendar.get(Calendar.DATE) +
                            "&h=" + calendar.get(Calendar.HOUR_OF_DAY) +
                            "&i=" + calendar.get(Calendar.MINUTE) +
                            "&s=" + calendar.get(Calendar.SECOND);

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

            @Override
            protected void onPostExecute(String json) {
                super.onPostExecute(json);
                progressDialog.dismiss();
                boolean error = true;
                String message = "";
                try {
                    JSONObject root = new JSONObject(json);
                    JSONObject j = root.getJSONObject("event");
                    JSONObject user = root.getJSONObject("user");
                    JSONObject bet = root.getJSONObject("bet");
                    error = root.getBoolean("error");
                    message = root.getString("message");

                    if (!error) {
                        //event
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
                        int year = Integer.parseInt(dt.substring(0, 4));
                        int month = Integer.parseInt(dt.substring(5, 7)) - 1;
                        int day = Integer.parseInt(dt.substring(8, 10));
                        int hourOfDay = Integer.parseInt(dt.substring(11, 13));
                        int minute = Integer.parseInt(dt.substring(14, 16));
                        int second = Integer.parseInt(dt.substring(17, 19));
                        date_time.set(year, month, day, hourOfDay, minute, second);

                        if (team_a.isEmpty()) {
                            event = new Event(id, sport, description, venue, date_time, status);
                        } else {
                            event = new Event(id, sport, description, venue, date_time, status, team_a, team_b, winner);
                        }

                        //user

                        score = user.getInt("score");
                        luck = user.getInt("luck");
                        bettingAmount = user.getInt("betting_amount");

                        powBidTime = Calendar.getInstance();
                        dt = user.getString("pow_bid_time");
                        year = Integer.parseInt(dt.substring(0, 4));
                        month = Integer.parseInt(dt.substring(5, 7)) - 1;
                        day = Integer.parseInt(dt.substring(8, 10));
                        hourOfDay = Integer.parseInt(dt.substring(11, 13));
                        minute = Integer.parseInt(dt.substring(14, 16));
                        second = Integer.parseInt(dt.substring(17, 19));
                        powBidTime.set(year, month, day, hourOfDay, minute, second);

                        slotTime = Calendar.getInstance();
                        dt = user.getString("slot_time");
                        year = Integer.parseInt(dt.substring(0, 4));
                        month = Integer.parseInt(dt.substring(5, 7)) - 1;
                        day = Integer.parseInt(dt.substring(8, 10));
                        hourOfDay = Integer.parseInt(dt.substring(11, 13));
                        minute = Integer.parseInt(dt.substring(14, 16));
                        second = Integer.parseInt(dt.substring(17, 19));
                        slotTime.set(year, month, day, hourOfDay, minute, second);

                        //bet

                        done = bet.getBoolean("done");
                        if (done) {
                            team = bet.getString("team");
                            amount = bet.getInt("amount");
                            powerBid = bet.getInt("power_bid") == 1;
                        }

                        showData();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Please Check your Connection", Toast.LENGTH_SHORT).show();
                }
            }
        }
        GetData gd = new GetData();
        gd.execute();
    }

    private void slotOnline(final Dialog dialog, final boolean won) {
        class GetData extends AsyncTask<Void, Void, String> {
            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(context, "", "Please Wait...", true);
            }

            @Override
            protected String doInBackground(Void... params) {
                try {
                    URL url = new URL(host + "bet/spin.php");

                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.MINUTE, 15);


                    int won1;
                    if (won) {
                        won1 = 1;
                    } else {
                        won1 = 0;
                    }

                    String urlParams = "id=" + id +
                            "&email=" + sharedPreferences.getString(LoginActivity.SP_EMAIL, "") +
                            "&won=" + won1 +
                            "&y=" + calendar.get(Calendar.YEAR) +
                            "&m=" + (calendar.get(Calendar.MONTH)+1) +
                            "&d=" + calendar.get(Calendar.DATE) +
                            "&h=" + calendar.get(Calendar.HOUR_OF_DAY) +
                            "&i=" + calendar.get(Calendar.MINUTE) +
                            "&s=" + calendar.get(Calendar.SECOND);

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

            @Override
            protected void onPostExecute(String json) {
                super.onPostExecute(json);
                progressDialog.dismiss();
                boolean error = true;
                String message = "";
                try {
                    JSONObject root = new JSONObject(json);
                    JSONObject j = root.getJSONObject("event");
                    JSONObject user = root.getJSONObject("user");
                    JSONObject bet = root.getJSONObject("bet");
                    error = root.getBoolean("error");
                    message = root.getString("message");

                    if (!error) {
                        //event
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
                        int year = Integer.parseInt(dt.substring(0, 4));
                        int month = Integer.parseInt(dt.substring(5, 7)) - 1;
                        int day = Integer.parseInt(dt.substring(8, 10));
                        int hourOfDay = Integer.parseInt(dt.substring(11, 13));
                        int minute = Integer.parseInt(dt.substring(14, 16));
                        int second = Integer.parseInt(dt.substring(17, 19));
                        date_time.set(year, month, day, hourOfDay, minute, second);

                        if (team_a.isEmpty()) {
                            event = new Event(id, sport, description, venue, date_time, status);
                        } else {
                            event = new Event(id, sport, description, venue, date_time, status, team_a, team_b, winner);
                        }

                        //user

                        score = user.getInt("score");
                        luck = user.getInt("luck");
                        bettingAmount = user.getInt("betting_amount");

                        powBidTime = Calendar.getInstance();
                        dt = user.getString("pow_bid_time");
                        year = Integer.parseInt(dt.substring(0, 4));
                        month = Integer.parseInt(dt.substring(5, 7)) - 1;
                        day = Integer.parseInt(dt.substring(8, 10));
                        hourOfDay = Integer.parseInt(dt.substring(11, 13));
                        minute = Integer.parseInt(dt.substring(14, 16));
                        second = Integer.parseInt(dt.substring(17, 19));
                        powBidTime.set(year, month, day, hourOfDay, minute, second);

                        slotTime = Calendar.getInstance();
                        dt = user.getString("slot_time");
                        year = Integer.parseInt(dt.substring(0, 4));
                        month = Integer.parseInt(dt.substring(5, 7)) - 1;
                        day = Integer.parseInt(dt.substring(8, 10));
                        hourOfDay = Integer.parseInt(dt.substring(11, 13));
                        minute = Integer.parseInt(dt.substring(14, 16));
                        second = Integer.parseInt(dt.substring(17, 19));
                        slotTime.set(year, month, day, hourOfDay, minute, second);

                        //bet

                        done = bet.getBoolean("done");
                        if (done) {
                            team = bet.getString("team");
                            amount = bet.getInt("amount");
                            powerBid = bet.getInt("power_bid") == 1;
                        }

                        showData();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Please Check your Connection", Toast.LENGTH_SHORT).show();
                }
            }
        }
        GetData gd = new GetData();
        gd.execute();
    }


}
