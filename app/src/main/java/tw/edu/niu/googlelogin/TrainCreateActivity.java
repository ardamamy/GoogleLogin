package tw.edu.niu.googlelogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

public class TrainCreateActivity extends AppCompatActivity {
    Button backTofra3;
    android.support.v7.widget.Toolbar mtoolbar;
    ListView lvtrainmenu;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_settings:
                Toast.makeText(this,"action_settings",Toast.LENGTH_SHORT).show();
                break;
            case R.id.subitem1:
                Toast.makeText(this,"subitem1",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TrainCreateActivity.this,SetTrainMenuActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.subitem2:
                Toast.makeText(this,"subitem2",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_create);
        Log.d("mylog","enteractivity");
        backTofra3 = findViewById(R.id.button2);
        mtoolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(mtoolbar);

        backTofra3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainCreateActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
