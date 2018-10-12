package teamb.cs262.calvin.edu.quest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import teamb.cs262.calvin.edu.quest.fragments.LeaderBoardFragment;
import teamb.cs262.calvin.edu.quest.fragments.TaskListFragment;

public class TeamsActivity extends AppCompatActivity {


    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //set the action bar to the team name
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String team = bundle.getString("team_name");
        getSupportActionBar().setTitle("Team: " + team);

        //open into the TaskListFragment by default
        if (savedInstanceState == null) {
            navigation.setSelectedItemId(R.id.task_list_fragment); // change to whichever id should be default
        }
    }

    //listen for a click on the navigation
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            //Start the fragment that is clicked
            switch (item.getItemId()) {
                case R.id.qr_scanner_fragment:
                    break;
                case R.id.leaderboard_fragment:
                    fragment = new LeaderBoardFragment();
                    break;
                case R.id.task_list_fragment:
                    fragment = new TaskListFragment();
                    break;
            }
            return startFragment(fragment);
        }
    };

    //this method opens a fragment if it is not opened
    private boolean startFragment(Fragment fragment) {
        if(fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, fragment);
            transaction.commit();
            return true;
        }
        return false;
    }

    //disable the android back button
    @Override
    public void onBackPressed() { }

}
