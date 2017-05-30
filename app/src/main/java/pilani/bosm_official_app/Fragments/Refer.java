package pilani.bosm_official_app.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import pilani.bosm_official_app.R;
import pilani.bosm_official_app.auth.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class Refer extends Fragment {


    TextView tvRefCode;
    Button bShare;
    SharedPreferences sharedPreferences;

    String refCode;

    public Refer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_refer, container, false);
        sharedPreferences = getActivity().getSharedPreferences(LoginActivity.SHARED_USER_DETAILS, Context.MODE_PRIVATE);

        //refCode="CODE";
        tvRefCode = (TextView)rootView.findViewById(R.id.tvRefCode);
        refCode=sharedPreferences.getString(LoginActivity.SP_REF_CODE, "");

        if(sharedPreferences.getBoolean(LoginActivity.SP_LOGGED_IN,false)==false){
            tvRefCode.setText("Login to get promo code");
            tvRefCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getContext(),LoginActivity.class));
                }
            });
        }else
            tvRefCode.setText(refCode);


       bShare = (Button)rootView.findViewById(R.id.bShare);
        bShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String shareBody = "Download the official BOSM 2016 app now using my referral code '"+
                       refCode+ "' and get additional $50 for the next bid.";
                Intent intent2 = new Intent();
                intent2.setAction(Intent.ACTION_SEND);
                intent2.setType("text/plain");
                intent2.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(intent2, "Share via"));
            }
        });


    return  rootView;
    }

}
