package pilani.bosm_official_app.auth;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import pilani.bosm_official_app.Navigation;
import pilani.bosm_official_app.R;

public class OtherDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "OtherDetailsActivity";

    SharedPreferences sharedPreferences;
    String host;

    String Mobile;
    String Code;
    String myCode;

    private EditText mMobileEditText;
    private EditText mCodeEditText;

    TextInputLayout mobileInput;
    boolean isNow;
    String sport,id;
    TextInputLayout codeInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_details);

        sharedPreferences = getSharedPreferences(LoginActivity.SHARED_USER_DETAILS, Context.MODE_PRIVATE);
        host = getString(R.string.host_url);

        Intent intent = getIntent();
        if(intent.getStringExtra("now")!=null){
            isNow = true;
            sport=intent.getStringExtra("sport");
            id=intent.getStringExtra("id");
        }
        mobileInput = (TextInputLayout) findViewById(R.id.mobile_layout);
        codeInput = (TextInputLayout) findViewById(R.id.code_layout);
        mMobileEditText = (EditText) findViewById(R.id.mobile);
        mCodeEditText = (EditText) findViewById(R.id.code);

        Button bLogin = (Button) findViewById(R.id.bLogin);
        bLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.bLogin) {
            Mobile = mMobileEditText.getText().toString();
            Code = mCodeEditText.getText().toString().toUpperCase();

            mobileInput.setErrorEnabled(false);
            codeInput.setErrorEnabled(false);

            String errorMessage;
            if (!mobileValid(Mobile)) {
                errorMessage = getString(R.string.error_mobile);
                mobileInput.setErrorEnabled(true);
                mobileInput.setError(errorMessage);
                return;
            }
            if (!codeValid(Code)) {
                errorMessage = getString(R.string.error_code);
                codeInput.setErrorEnabled(true);
                codeInput.setError(errorMessage);
                return;
            }
            myCode = hashMobile(Mobile);
            getData();
        }
    }

    private void getData() {
        class GetData extends AsyncTask<Void, Void, String> {
            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(OtherDetailsActivity.this, "", "Please Wait...", true);
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
                    URL url = new URL(host + "auth/register.php");

                    Calendar calendar = Calendar.getInstance();
//                    calendar.add(android.icu.util.Calendar.HOUR, 8);

                    String urlParams = "mobile=" + Mobile +
                            "&code=" + Code +
                            "&my_code=" + myCode +
                            "&email=" + sharedPreferences.getString(LoginActivity.SP_EMAIL, "") +
                            "&name=" + sharedPreferences.getString(LoginActivity.SP_NAME, "") +
                            "&y=" + calendar.get(Calendar.YEAR) +
                            "&m=" + calendar.get(Calendar.MONTH)+1 +
                            "&d=" + calendar.get(Calendar.DATE) +
                            "&h=" + calendar.get(Calendar.HOUR_OF_DAY) +
                            "&i=" + calendar.get(Calendar.MINUTE) +
                            "&s=" + calendar.get(Calendar.SECOND);


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
            error = root.getBoolean("error");
            message = root.getString("message");

            if (!error) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(LoginActivity.SP_LOGGED_IN, true);
                editor.putString(LoginActivity.SP_MOBILE, Mobile);
                editor.putString(LoginActivity.SP_REF_CODE, myCode);
                editor.commit();

                if(isNow){
                    Intent intent = new Intent(this, Navigation.class);
                    intent.putExtra("now", "now");
                    intent.putExtra("sport", sport);
                    intent.putExtra("id", id);
                    startActivity(intent);
                }

                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }


        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Please Check your Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private static boolean mobileValid(String mobile) {
        String MOBILE_REGEX = "^[0-9]{10}$";
        return mobile.matches(MOBILE_REGEX);
    }

    private static boolean codeValid(String code) {
        return code.length() == 7 || code.isEmpty();
    }

    private String hashMobile(String mobile) {
        return Long.toString(Long.valueOf(mobile), 36).toUpperCase();
    }
}
