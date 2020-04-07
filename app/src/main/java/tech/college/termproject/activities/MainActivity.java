package tech.college.termproject.activities;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import tech.college.termproject.R;
import tech.college.termproject.adapters.TabAdapter;
import tech.college.termproject.fragments.AllItemFragment;
import tech.college.termproject.fragments.FavoriteListFragment;
import tech.college.termproject.fragments.WelcomeFragment;

public class MainActivity extends AppCompatActivity {

    public static String emailId;
    boolean doubleBackToExitPressedOnce = false;
    TextView ToolbarTxt;
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ToolbarTxt = findViewById(R.id.toolbar_txt);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new WelcomeFragment(), "Welcome");
        adapter.addFragment(new FavoriteListFragment(), "Favorite");
        adapter.addFragment(new AllItemFragment(), "All Item");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        emailId = getIntent().getStringExtra("Email_id");
        ToolbarTxt.setText("Welcome" + " " + emailId);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}
