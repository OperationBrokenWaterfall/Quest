package teamb.cs262.calvin.edu.quest.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import teamb.cs262.calvin.edu.quest.R;

/**
 * LeaderBoardFragment is a singleton
 * To get an instance of this Fragment call LeaderBoardFragment.getInstance()
 * rather than new LeaderBoardFragment. This ensures there are duplicated Fragments
 */
public class LeaderBoardFragment extends Fragment {

    private TextView score;
    static int score_value;



    private static LeaderBoardFragment instance;  // singleton instance

    @SuppressLint("ValidFragment")
    private LeaderBoardFragment() {
        super();
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
            Toast.makeText(getContext(), bundle.getString("QR"), Toast.LENGTH_LONG).show();

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
