package tw.edu.niu.googlelogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class TrainCreateActivity extends AppCompatActivity {
    Button backTofra3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_create);
        Log.d("mylog","enteractivity");
        backTofra3 = findViewById(R.id.button2);
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
