package pilani.bosm_official_app;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class SettingsActivity extends AppCompatActivity {

    private WebView webView;
    private Firebase mRef;

    NavigationView navigationView;

    ProgressBar loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Intent intent = getIntent();

        Firebase.setAndroidContext(this);
        loading = (ProgressBar) findViewById(R.id.loading);
        loading.setVisibility(View.VISIBLE);

        String fName = intent.getStringExtra("bla");
        setTitle(fName);


        //Addition
        webView = (WebView) findViewById(R.id.webView1);
        mRef = new Firebase("https://bosm-1f680.firebaseio.com/Sports/"+fName);
        final String mimeType = "text/html";
        final String encoding = "UTF-8";

//        webView.loadDataWithBaseURL("", "<br /><br />Read the handouts please for tomorrow.<br /><br /><!--homework help homework\" +                 \"help help with homework homework assignments elementary school high school middle school\" +                 \"// --><font color='#60c000' size='4'><strong>Please!</strong></font>\" +                 \"<img src='http://www.homeworknow.com/hwnow/upload/images/tn_star300.gif'  />\"", mimeType, encoding, "");


        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String value = dataSnapshot.getValue(String.class);

                webView.loadDataWithBaseURL("", value, mimeType, encoding, "");
                loading.setVisibility(View.GONE);


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // navigationView.setCheckedItem(R.id.nav_settings);
    }

}
