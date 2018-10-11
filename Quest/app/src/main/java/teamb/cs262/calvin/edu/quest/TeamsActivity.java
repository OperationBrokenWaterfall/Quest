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

    //listen for a click on the navigation
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            //Start the fragment that is clicked
            switch (item.getItemId()) {
                case R.id.qr_scanner_fragment:
                    return true;
                case R.id.leaderboard_fragment:
                    startFragment(LeaderBoardFragment.newInstance());
                    return true;
                case R.id.task_list_fragment:
                    startFragment(TaskListFragment.newInstance());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //open into the TaskListFragment by default
        startFragment(TaskListFragment.newInstance());

        //set the action bar to the team name
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String team = bundle.getString("team_name");
        getSupportActionBar().setTitle("Team: " + team);
    }

    //this method opens a fragment if it is not opened
    private void startFragment(Fragment fragment) {
        if(fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, fragment);
            transaction.commit();
        }
    }

    //disable the android back button
    @Override
    public void onBackPressed() { }

}
