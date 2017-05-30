package pilani.bosm_official_app;

import android.app.FragmentBreadCrumbs;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.transition.Slide;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import pilani.bosm_official_app.Fragments.About;
import pilani.bosm_official_app.Fragments.Contact;
import pilani.bosm_official_app.Fragments.Developers;
import pilani.bosm_official_app.Fragments.FAQ;
import pilani.bosm_official_app.Fragments.Instructions;
import pilani.bosm_official_app.Fragments.Leaderboard;
import pilani.bosm_official_app.Fragments.Main;
import pilani.bosm_official_app.Fragments.Map;
import pilani.bosm_official_app.Fragments.Refer;
import pilani.bosm_official_app.Fragments.Roulette;
import pilani.bosm_official_app.Fragments.Schedule;
import pilani.bosm_official_app.Fragments.Sponsors;
import pilani.bosm_official_app.Fragments.TabFragments.Events;
import pilani.bosm_official_app.Fragments.TabFragments.Now;
import pilani.bosm_official_app.Fragments.TabFragments.Result;
import pilani.bosm_official_app.auth.LoginActivity;

public class Navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Sponsors sponsors;
    Main main;
    Schedule schedule;
    Map map;
    About about;
    Developers developers;
    FAQ faq;
    Contact contact;
    Roulette roulette;
    Refer refer;
    Instructions instructions;
    Leaderboard leaderboard;
    String sport="",id="";
    SharedPreferences sharedPreferences;
    static int current = 0;

    TextView textView;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));

        Intent i = getIntent();

        if(i.getStringExtra("sport")!=null){
            sport=i.getStringExtra("sport");
            id=i.getStringExtra("id");
            intializeFragments2(3);
        }else if(i.getStringExtra("now_login")!=null){
            intializeFragments2(1);
        }else {
            intializeFragments();
        }
        if(i.getStringExtra("lead")!=null){
            startNavFragment(leaderboard);
        }

        sharedPreferences = getSharedPreferences(LoginActivity.SHARED_USER_DETAILS, Context.MODE_PRIVATE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);

        textView = (TextView)header.findViewById(R.id.username);

        if(sharedPreferences.getBoolean(LoginActivity.SP_LOGGED_IN, false)==true){
            textView.setText(sharedPreferences.getString(LoginActivity.SP_NAME, ""));
        }else
            textView.setText("Guest");

    }

    private void intializeFragments(){
        sponsors =new Sponsors();
        main = new Main();
        schedule = new Schedule();
        map= new Map();
        about = new About();
        contact =new Contact();
        developers = new Developers();
        faq = new FAQ();
        roulette = new Roulette();
        leaderboard = new Leaderboard();
        refer = new Refer();
        instructions = new Instructions();

        setTitle("BOSM 2K17");
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame1,main,"main");
        fragmentTransaction.commit();
    }

    private void intializeFragments2(int flag){
        sponsors =new Sponsors();
        main = new Main();
        schedule = new Schedule();
        map= new Map();
        about = new About();
        contact =new Contact();
        developers = new Developers();
        faq = new FAQ();
        roulette = new Roulette();
        leaderboard = new Leaderboard();
        refer = new Refer();
        instructions = new Instructions();

        if(flag==3) {
            Bundle bundle = new Bundle();
            bundle.putString("now", "now");
            bundle.putString("sport", sport);
            bundle.putString("id", id);
            main.setArguments(bundle);
        }else {
            Bundle bundle = new Bundle();
            bundle.putInt("1",flag);
            main.setArguments(bundle);
        }
        setTitle("BOSM 2K17");
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame1,main,"main");
        fragmentTransaction.commit();
    }

        int count=0;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            setTitle("BOSM 2K17");
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame1,main,"main");
            navigationView.setCheckedItem(R.id.nav_main);
            fragmentTransaction.commit();
        }
       // Toast.makeText(this,Integer.toString(Main.value),Toast.LENGTH_LONG).show();
        if (Main.value==3){
            intializeFragments2(1);
        }else if(Main.value==0 && current==0){
           drawer.openDrawer(GravityCompat.START);
            if(count==1){
                Toast.makeText(this,"Press one more time to exit!",Toast.LENGTH_LONG).show();
            }
            if(count==2){
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
            count++;
        }else if(Main.value==1){

            intializeFragments2(0);
        }else if(Main.value==2){

            intializeFragments2(0);
        }else if(Main.value==3){

            intializeFragments2(1);
        }else if(Main.value==4){

            intializeFragments2(0);
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_sponsors) {
            current=1;
           // Toast.makeText(this,Integer.toString(current),Toast.LENGTH_SHORT).show();
            setTitle("Sponsors");
            startNavFragment(sponsors);

        } else if (id == R.id.nav_main) {
            current=0;
          //  Toast.makeText(this,Integer.toString(current),Toast.LENGTH_SHORT).show();
            setTitle("BOSM 2K17");
            startNavFragment(main);

        } else if (id == R.id.nav_schedule) {
            current=2;
           // Toast.makeText(this,Integer.toString(current),Toast.LENGTH_SHORT).show();

            setTitle("Schedule");
            startNavFragment(schedule);

        } else if (id == R.id.nav_map) {
            current=3;
            //Toast.makeText(this,Integer.toString(current),Toast.LENGTH_SHORT).show();

            setTitle("Map");
            startNavFragment(map);

        } else if (id == R.id.nav_dev) {
            current=4;
           // Toast.makeText(this,Integer.toString(current),Toast.LENGTH_SHORT).show();

            setTitle("Developers");
            startNavFragment(developers);

        } else if (id == R.id.nav_faq) {
            current=11;
            //Toast.makeText(this,Integer.toString(current),Toast.LENGTH_SHORT).show();

            setTitle("FAQ");
            startNavFragment(faq);

        }else if (id == R.id.nav_contact) {
            current=5;
           // Toast.makeText(this,Integer.toString(current),Toast.LENGTH_SHORT).show();
            setTitle("Contact Us");
            startNavFragment(contact);

        }else if (id == R.id.nav_about) {
            current=6;
           // Toast.makeText(this,Integer.toString(current),Toast.LENGTH_SHORT).show();

            setTitle("About");
            startNavFragment(about);

        }else if (id == R.id.nav_roulette) {
            current=7;
           // Toast.makeText(this,Integer.toString(current),Toast.LENGTH_SHORT).show();
          setTitle("BOSM Roulette");
            startNavFragment(roulette);

        }else if (id == R.id.nav_lead) {
            current=8;
           // Toast.makeText(this,Integer.toString(current),Toast.LENGTH_SHORT).show();
            setTitle("LeaderBoard");
            if(sharedPreferences.getBoolean(LoginActivity.SP_LOGGED_IN, false)==false){
                Intent i = new Intent(this,LoginActivity.class);
                i.putExtra("lead","lead");
                startActivity(i);
            }else
            startNavFragment(leaderboard);

        }else if (id == R.id.nav_refer) {
            current=9;
           // Toast.makeText(this,Integer.toString(current),Toast.LENGTH_SHORT).show();
            setTitle("Refer and Earn");
            startNavFragment(refer);

        }else if (id == R.id.nav_inst) {
            current=10;
          //  Toast.makeText(this,Integer.toString(current),Toast.LENGTH_SHORT).show();
            setTitle("Instructions");
            startNavFragment(instructions);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void startNavFragment (final Fragment fragment) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame1,fragment);
                fragmentTransaction.commit();
            }
        }, 350);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear(); //Clear view of previous menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation, menu);
        if(sharedPreferences.getBoolean(LoginActivity.SP_LOGGED_IN,false)==false)
            menu.getItem(0).setTitle("Login");

        return super.onPrepareOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout:
                if(sharedPreferences.getBoolean(LoginActivity.SP_LOGGED_IN,true)==true)
                signOut();
                else{
                    signIn();
                    item.setTitle("Login");
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void signOut() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(LoginActivity.SP_LOGGED_IN, false);
        editor.putString(LoginActivity.SP_NAME,"");
        editor.putString(LoginActivity.SP_EMAIL,"");
        editor.putString(LoginActivity.SP_MOBILE,"");
        editor.putString(LoginActivity.SP_REF_CODE,"");
        editor.commit();
        textView.setText("Guest");
        Toast.makeText(this,"Logged out Successfully!",Toast.LENGTH_LONG).show();
        intializeFragments();
    }

    private void signIn() {
        Intent intent = new Intent(this,LoginActivity.class);
        intent.putExtra("in","in");
        startActivity(intent);
    }
}
