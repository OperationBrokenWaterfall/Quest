package teamb.cs262.calvin.edu.quest;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.util.Log;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import teamb.cs262.calvin.edu.quest.fragments.LeaderBoardFragment;
import teamb.cs262.calvin.edu.quest.fragments.QRCodeFragment;
import teamb.cs262.calvin.edu.quest.fragments.TaskListFragment;

/**
 * This activity is the hub of our UI, containing the three main fragments
 * which drive the scavenger hunt.
 */
public class TeamsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{

    private Fragment fragment;
    public static String TEAM_NAME;

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
        TEAM_NAME = team;
        getSupportActionBar().setTitle("Quest");
        getSupportActionBar().setSubtitle("Team: " + team);



        //open into the TaskListFragment by default
        if (savedInstanceState == null) {
            navigation.setSelectedItemId(R.id.task_list_fragment); // change to whichever id should be default
        }
        if(getSupportLoaderManager().getLoader(0)!=null){
            getSupportLoaderManager().initLoader(0,null,this);
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
                    updateLeaderboard();
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

    public void updateLeaderboard() {

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            Bundle queryBundle = new Bundle();
            getSupportLoaderManager().restartLoader(0, queryBundle,this);
            System.out.println("Loading");
        }
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new LeaderboardLoader(this, bundle.getString("queryString"));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String s) {
        List<Team> teams = new ArrayList<Team>();
        try {
            JSONObject jsonObject = new JSONObject(s);
            Log.d("Teams", jsonObject.toString());
            JSONArray itemsArray = jsonObject.getJSONArray("items");
            Log.d("Teams", itemsArray.toString());
            for(int i = 0; i<itemsArray.length(); i++){
                JSONObject item = itemsArray.getJSONObject(i);
                String team=null;
                String score=null;


                try {
                    team = item.getString("team");
                    score = item.getString("score");
                } catch (Exception e){
                    e.printStackTrace();
                }


                if (team != null && score != null){
                    System.out.println("Team: " + team + ", Score: " + score);
                    teams.add(new Team(team, Integer.valueOf(score)));
                }
            }
            if(!teams.isEmpty()) {
                LeaderBoardFragment.getInstance().setTeams(teams);
            }
            else {
                Log.d("Web Req Finish", "No teams found");
            }
        } catch (Exception e){
            System.out.println("None Found");
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.help, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.user_guide:
                Intent intent = new Intent(this, HelpActivity.class);
                startActivity(intent);
                return true;
            default:
                // Do nothing
        }
        return super.onOptionsItemSelected(item);
    }

}