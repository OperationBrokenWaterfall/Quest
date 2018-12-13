package teamb.cs262.calvin.edu.quest.fragments;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.text.Layout;
import android.util.LayoutDirection;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import teamb.cs262.calvin.edu.quest.R;
import teamb.cs262.calvin.edu.quest.Team;

/**
 * LeaderBoardFragment is a singleton
 * To get an instance of this Fragment call LeaderBoardFragment.getInstance()
 * rather than new LeaderBoardFragment. This ensures there are duplicated Fragments
 */
public class LeaderBoardFragment extends Fragment {

    private TextView score;
    static int score_value;

    private TableLayout layout;


    private static LeaderBoardFragment instance;  // singleton instance

    private List<Team> teams;

    @SuppressLint("ValidFragment")
    private LeaderBoardFragment() {
        super();
        teams = new ArrayList<Team>();

    }


    /**
     * This returns the singleton LeaderBoardFragment instance
     * @return LeaderBoardFragment instance
     */
    public static LeaderBoardFragment getInstance() {
        if(instance == null) {
            instance = newInstance();
        }
        return instance;
    }

    public void setTeams(final List<Team> teams) {
        this.teams = teams;
        Log.d("setTeams()", teams.toArray().toString());
        updateLeaderboard();

    }

    private void updateLeaderboard() {
        layout.removeViews(1, layout.getChildCount()-1);
        for (int i = 0; i < teams.size(); i++) {
            Team team = teams.get(i);
            layout.addView(createTableRow(i + 1, team.name, team.score));
        }
    }


    private TableRow createTableRow(int place, String team, int score) {
        final TableRow row = new TableRow(getContext());
        TextView placeText = createTextView(String.valueOf(place));
        TextView teamText = createTextView(team);
        TextView scoreText = createTextView(String.valueOf(score));
        row.addView(placeText);
        row.addView(teamText);
        row.addView(scoreText);
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        params.setMargins(1, 100, 1, 1);
        row.setLayoutParams(params);
        row.setPadding(10, 10,10,10);
        row.setBackgroundColor(Color.WHITE);
        row.setGravity(Gravity.CENTER);
        return row;

    }
    /*
    XML of styling

   <TextView
            android:layout_width="2dp"
            android:layout_height="wrap_content"
            android:layout_column="0"
            android:layout_margin="1dp"
            android:background="#FFFFFF"
            android:gravity="center"
            android:text="1"
            android:textAppearance="?android:attr/textAppearanceLarge" />
     */
    @SuppressLint({"ResourceAsColor", "ResourceType"})
    private TextView createTextView(String text) {
        TextView view = new TextView(getContext());
        view.setText(text);
        view.setTextAppearance(getContext(), android.R.style.TextAppearance_Large);
        view.setGravity(Gravity.CENTER);
        return view;
    }

    /**
     * Creates a new LeaderBoardFragment instance
     * @return fragment
     */
    private static LeaderBoardFragment newInstance() {
        LeaderBoardFragment fragment = new LeaderBoardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_leader_board, container, false);
        layout = (TableLayout) rootview.findViewById(R.id.leaderboardTableLayout);
        score = rootview.findViewById(R.id.team_score);

        return rootview;
    }

    /**
     * This is called when the LeaderBoardFragment is resumed.  This is called after a QR code is
     * scanned and provides the haptic feedback confirming the QR code was scanned.
     */
    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = getArguments();
        if(bundle != null && bundle.containsKey("QR")) {

            score_value += 1;
            score.setText(String.valueOf(score_value));

            System.out.println("Task List has received this deciphered QR Code: " + bundle.getString("QR"));
            if (bundle.containsKey("cheater")) {
                Toast.makeText(getContext(), "Invalid: No Cheating", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Location Confirmed + 1", Toast.LENGTH_LONG).show();
            }

            Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                v.vibrate(500);
            }
            bundle.remove("QR"); // this fixes score increment and vibrate on restart
        }
        score.setText(String.valueOf(score_value));
    }
}
