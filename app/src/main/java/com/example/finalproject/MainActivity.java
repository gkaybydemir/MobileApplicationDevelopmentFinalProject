package com.example.finalproject;
// MainActivity.java
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements CityAdapter.OnItemClickListener {
    private static final int DELAY_TIME_MILLISECONDS = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("CampAnya");
        setContentView(R.layout.welcome_layout);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadLoginFragment();
                setContentView(R.layout.activity_main);
            }
        }, DELAY_TIME_MILLISECONDS);
    }

    private void loadLoginFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new LoginFragment())
                .commit();
    }

    @Override
    public void onItemClick(City city) {
        // Handle city item click, e.g., navigate to CampingListFragment
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_top, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.action_profile) {
            openProfileFragment();
            return true;
        } else if (itemId == R.id.action_settings) {
            openSettingsFragment();
            return true;
        } else if (itemId == R.id.action_back) {
            // Handle the "Back" action, for example, pop the back stack
            getSupportFragmentManager().popBackStack();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void openProfileFragment() {
        // Replace the current fragment with the ProfileFragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new ProfileFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void openSettingsFragment() {
        // Replace the current fragment with the SettingsFragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new SettingsFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}