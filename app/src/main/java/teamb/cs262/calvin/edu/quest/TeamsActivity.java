package teamb.cs262.calvin.edu.quest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import teamb.cs262.calvin.edu.quest.fragments.LeaderBoardFragment;
import teamb.cs262.calvin.edu.quest.fragments.QRCodeFragment;
import teamb.cs262.calvin.edu.quest.fragments.TaskListFragment;

/**
 * This activity is the hub of our UI, containing the three main fragments
 * which drive the scavenger hunt.
 */
public class TeamsActivity extends AppCompatActivity {

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_teams);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        startFragment(LeaderBoardFragment.getInstance());
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String team = bundle.getString("team_name");

        getSupportActionBar().setTitle("Quest");
        getSupportActionBar().setSubtitle("Team: " + team);

        //open into the TaskListFragment by default
        if (savedInstanceState == null) {
            navigation.setSelectedItemId(R.id.task_list_fragment); // change to whichever id should be default
        }
    }

    /**
     * This creates a listener for the bottom navigation menu and switches to the user
     * selected fragment.
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            //Start the fragment that is clicked
            switch (item.getItemId()) {
                case R.id.qr_scanner_fragment:
                    fragment = QRCodeFragment.getInstance();
                    break;
                case R.id.leaderboard_fragment:
                    fragment = LeaderBoardFragment.getInstance();
                    break;
                case R.id.task_list_fragment:
                    fragment = TaskListFragment.getInstance();
                    break;

            }
            return startFragment(fragment);
        }
    };

    /**
     * This function is called within onNavigationItemSelected() and actually switches the fragment
     * to whichever is tapped.
     * @param fragment
     * @return
     */
    private boolean startFragment(Fragment fragment) {
        if(fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, fragment);
            transaction.commit();
            return true;
        }
        return false;
    }

    /**
     * Disable the back button for the TeamsActivity page.
     * We don't want users going back after they login.
     */
    @Override
    public void onBackPressed() { }

}