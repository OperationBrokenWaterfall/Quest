package teamb.cs262.calvin.edu.quest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


/**
 * This the main activity that Launches when the app opens.
 * Here one person from a team, that has an android phone will
 * enter the team name.
 */
public class MainActivity extends AppCompatActivity {

    private EditText TeamNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TeamNameText = findViewById(R.id.enter_team_name_field);

        getSupportActionBar().setTitle("Quest");
    }

    /**
     * This function is called when the LOGIN button is pressed.
     * This launches the TeamsActivity and passes on a bundle containing the team name.
     * @param view
     */
    public void switchToTeams(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("team_name", TeamNameText.getText().toString());
        Intent intent = new Intent(this, TeamsActivity.class);
        intent.putExtras(bundle); // pass on the team name
        startActivity(intent);
    }
}
