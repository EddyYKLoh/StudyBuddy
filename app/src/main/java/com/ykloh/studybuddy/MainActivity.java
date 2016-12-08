package com.ykloh.studybuddy;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences sharedPreferences = getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
        boolean loggedIn = sharedPreferences.getBoolean("loggedIn", false);

        if (!loggedIn) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }

        View hView = navigationView.getHeaderView(0);

        ImageView profilePicture = (ImageView) hView.findViewById(R.id.profilePictureNavImageView);
        final String profPicPath = sharedPreferences.getString("profPicPath", null);
        NavProfilePictureLoader profilePictureLoader = new NavProfilePictureLoader();
        profilePictureLoader.getImageView(profilePicture);
        profilePictureLoader.execute(profPicPath);

        TextView nameOnNavigationTextView = (TextView) hView.findViewById(R.id.nameOnNavigationTextView);
        final String username = sharedPreferences.getString("name", null);
        nameOnNavigationTextView.setText(username);

        TextView emailOnNavigationTextView = (TextView) hView.findViewById(R.id.emailOnNavigationTextView);
        final String email = sharedPreferences.getString("emailAddress", null);
        emailOnNavigationTextView.setText(email);

        String userID = sharedPreferences.getString("userID", null);
        CheckUnratedHelp checkUnratedHelp = new CheckUnratedHelp();
        checkUnratedHelp.checkUser(this, userID);

//        HomeFragment homeFragment = new HomeFragment();
//        android.app.FragmentManager fragmentManager = getFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(R.id.mainContentFrame, homeFragment )
//                .addToBackStack(null)
//                .commit();




    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (getFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStack();
        } else if (getFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            SharedPreferences sharedPreferences = getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.putBoolean("loggedIn", false);
            editor.commit();

            Intent toLogin = new Intent(MainActivity.this, LoginActivity.class);
            toLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(toLogin);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.navHome) {
            // Handle the action
            SharedPreferences sharedPreferences = getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
            String userID = sharedPreferences.getString("userID", null);
            CheckUnratedHelp checkUnratedHelp = new CheckUnratedHelp();
            checkUnratedHelp.checkUser(this, userID);

        }
        else if (id == R.id.navNotification) {
            NotificationFragment notificationFragment = new NotificationFragment();
            android.app.FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.mainContentFrame, notificationFragment)
                    .addToBackStack(null)
                    .commit();
        }
        else if (id == R.id.navRequest) {

            RequestFragment requestFragment = new RequestFragment();
            android.app.FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.mainContentFrame, requestFragment )
                    .addToBackStack(null)
                    .commit();


        }
//        else if (id == R.id.navMessage) {
//
//        } else if (id == R.id.navBookmark) {
//
//        }
        else if (id == R.id.navProfile) {

        } else if (id == R.id.navOwnRequest) {
            YourRequestFragment yourRequestFragment = new YourRequestFragment();
            android.app.FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.mainContentFrame, yourRequestFragment)
                    .addToBackStack(null)
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}