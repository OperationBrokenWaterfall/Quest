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
import teamb.cs262.calvin.edu.quest.fragments.TaskListFragment;
import teamb.cs262.calvin.edu.quest.fragments.TeamFragment;

public class TeamsActivity extends AppCompatActivity {


    private Fragment fragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    startFragment(TeamFragment.newInstance());
                    return true;
                case R.id.navigation_notifications:
                    startFragment(LeaderBoardFragment.newInstance());
                    return true;
                case R.id.navigation_task_list:
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
        startFragment(LeaderBoardFragment.newInstance());
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String team = bundle.getString("team_name");
        getSupportActionBar().setTitle(team);


    }

    private void startFragment(Fragment fragment) {
        if(fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, fragment);
            transaction.commit();

        }
    }

}
