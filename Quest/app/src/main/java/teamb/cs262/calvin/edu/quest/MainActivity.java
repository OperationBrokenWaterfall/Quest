package teamb.cs262.calvin.edu.quest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText createTeamText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createTeamText = findViewById(R.id.enter_team_name_field);

    }

    public void switchToTeams(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("team_name", createTeamText.getText().toString());
        Intent intent = new Intent(this, TeamsActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
