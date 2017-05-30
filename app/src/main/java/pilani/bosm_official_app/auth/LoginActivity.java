package pilani.bosm_official_app.auth;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import pilani.bosm_official_app.Fragments.TabFragments.Now;
import pilani.bosm_official_app.Navigation;
import pilani.bosm_official_app.R;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    private static final String TAG = "LoginActivity";

    boolean isNow=false,islead=false;    private static final int RC_OTHER_DETAILS = 1;
    private static final int RC_SIGN_IN = 2;


    SharedPreferences sharedPreferences;
    String host;

    String Email;
    String Name;

    private int mInterval = 2500;
    private Handler mHandler;

    //google
    private GoogleApiClient mGoogleApiClient;

    public static final String SHARED_USER_DETAILS = "UserDetails";
    public static final String SP_LOGGED_IN = "loggedIn";
    public static final String SP_MOBILE = "mobile";
    public static final String SP_EMAIL = "email";
    public static final String SP_NAME = "name";
    public static final String SP_REF_CODE = "ref_code";

    private Button googleButton;
/*
    ViewFlipper viewFlipper;
    ViewFlipper viewFlipperL;
    ViewFlipper viewFlipperR;

    Animation slide_in_left;
    Animation slide_out_left;
    Animation slide_in_right;
    Animation slide_out_right;
*/

    String sport,id;

    @Override
    public void onBackPressed() {
        if(isNow){
            Intent i = new Intent(this,Navigation.class);
            i.putExtra("now_login","now");
            startActivity(i);
        }else
         super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences(SHARED_USER_DETAILS, Context.MODE_PRIVATE);
        host = getString(R.string.host_url);

        Intent intent = getIntent();
        if(intent.getStringExtra("now")!=null){
            isNow = true;
            sport=intent.getStringExtra("sport");
            id=intent.getStringExtra("id");
        }

        googleButton = (Button) findViewById(R.id.google_button);
        googleButton.setOnClickListener(this);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));

        //Google

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addConnectionCallbacks(this)
                .build();


        mGoogleApiClient.connect();
//        if (mGoogleApiClient.isConnected()) {
//            Auth.GoogleSignInApi.signOut(mGoogleApiClient);
//        }

     /*   slide_in_left = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
        slide_in_right = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        slide_out_left = AnimationUtils.loadAnimation(this, R.anim.slide_out_left);
        slide_out_right = AnimationUtils.loadAnimation(this, R.anim.slide_out_right);

        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
//        viewFlipper.setFlipInterval(3000);
//        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(slide_in_right);
        viewFlipper.setOutAnimation(slide_out_left);

        viewFlipperL = (ViewFlipper) findViewById(R.id.viewFlipperL);
//        viewFlipperL.setFlipInterval(3000);
//        viewFlipperL.setAutoStart(true);
        viewFlipperL.setInAnimation(slide_in_right);
        viewFlipperL.setOutAnimation(slide_out_left);

        viewFlipperR = (ViewFlipper) findViewById(R.id.viewFlipperR);
//        viewFlipperR.setFlipInterval(3000);
//        viewFlipperR.setAutoStart(true);
        viewFlipperR.setInAnimation(slide_in_right);
        viewFlipperR.setOutAnimation(slide_out_left);

//        viewFlipper.showNext();
//        viewFlipperL.showNext();
//        viewFlipperR.showNext();
*/
     /*   mHandler = new Handler();
        startRepeatingTask();
*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       // stopRepeatingTask();
    }

   /* Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                updateStatus();
            } finally {
                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }

    void updateStatus() {
        viewFlipper.showNext();
        viewFlipperL.showNext();
        viewFlipperR.showNext();
    }
    */

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        String error = connectionResult.getErrorMessage();
        Toast.makeText(LoginActivity.this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.google_button) {
            googleSignIn();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        } else if (requestCode == RC_OTHER_DETAILS) {
            if (resultCode == RESULT_OK) {
                Intent intent = new Intent(this, Navigation.class);
                startActivity(intent);
                finish();
            }
        }
    }

    private void googleSignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(GoogleSignInResult result) {

        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();

            Email = acct.getEmail();
            Name = acct.getDisplayName();

            getData(Email);

        } else {
            String error = "error";
            Toast.makeText(LoginActivity.this, error, Toast.LENGTH_LONG).show();
        }
    }

    private void getData(final String email) {
        class GetData extends AsyncTask<Void, Void, String> {
            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog=new ProgressDialog(LoginActivity.this,R.style.MyAlertDialogStyle);
                progressDialog.setMessage("Please Wait...");
                progressDialog.show();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();
                parseJSON(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                BufferedReader bufferedReader = null;
                int tmp;
                String data = "";
                try {
                    URL url = new URL(host + "auth/login.php");
                    String urlParams = "email=" + email;

                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setDoOutput(true);
                    OutputStream os = httpURLConnection.getOutputStream();
                    os.write(urlParams.getBytes());
                    os.flush();
                    os.close();

                    InputStream is = httpURLConnection.getInputStream();
                    while ((tmp = is.read()) != -1) {
                        data += (char) tmp;
                    }

                    is.close();
                    httpURLConnection.disconnect();

                    return data;

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
        boolean exist;
        try {
            JSONObject root = new JSONObject(json);
            JSONObject j = root.getJSONObject("user_data");
            error = root.getBoolean("error");
            message = root.getString("message");
            exist = root.getBoolean("exist");

            if (!error) {
                if (exist) {
                    String refCode = j.getString("ref_code");
                    String mobile = j.getString("mobile");

                    Toast.makeText(this,"Logged in Successfully!",Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(SP_LOGGED_IN, true);
                    editor.putString(SP_EMAIL, Email);
                    editor.putString(SP_MOBILE, mobile);
                    editor.putString(SP_NAME, Name);
                    editor.putString(SP_REF_CODE, refCode);
                    editor.commit();

                    if(isNow){
                        Intent intent = new Intent(this, Navigation.class);
                        intent.putExtra("now", "now");
                        intent.putExtra("sport", sport);
                        intent.putExtra("id", id);
                        startActivity(intent);
                    }else if (islead) {
                        Intent intent = new Intent(this, Navigation.class);
                        intent.putExtra("done","done");
                        startActivity(intent);
                        finish();
                    }else {
                        Intent intent = new Intent(this, Navigation.class);
                        startActivity(intent);
                        finish();
                    }
                } else {


                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(SP_EMAIL, Email);
                    editor.putString(SP_NAME, Name);
                    editor.commit();

                    Intent intent = new Intent(this, OtherDetailsActivity.class);
                    if(isNow) {
                        intent.putExtra("now", "now");
                        intent.putExtra("sport", sport);
                        intent.putExtra("id", id);
                        startActivity(intent);
                    }
                    startActivityForResult(intent, RC_OTHER_DETAILS);
                }
            } else {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }


        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Please Check your Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
