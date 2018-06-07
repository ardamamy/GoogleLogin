package tw.edu.niu.googlelogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class TeamSelectActivity extends AppCompatActivity {
    EditText editTextTeamName;
    Button btnSet,btnCreate;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_select);
        editTextTeamName = findViewById(R.id.teamName);
        btnSet = findViewById(R.id.set);
        btnCreate = findViewById(R.id.teamCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamSelectActivity.this,TeamCreateActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
