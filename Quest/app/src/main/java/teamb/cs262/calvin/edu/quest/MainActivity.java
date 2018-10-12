package teamb.cs262.calvin.edu.quest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText TeamNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TeamNameText = findViewById(R.id.team_name_input);
    }

    //when the create a team button is clicked, start the TeamsActivity
    public void switchToTeams(View view) {
        //bundle to pass the team name on to the TeamsActivity
        Bundle bundle = new Bundle();
        bundle.putString("team_name", TeamNameText.getText().toString());
        Intent intent = new Intent(this, TeamsActivity.class);
        intent.putExtras(bundle);
        //start the TeamsActivity
        startActivity(intent);
    }
}
