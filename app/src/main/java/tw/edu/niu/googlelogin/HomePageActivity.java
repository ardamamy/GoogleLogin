package tw.edu.niu.googlelogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class HomePageActivity extends AppCompatActivity {
    GoogleApiClient mGoogleApiClient;
    Button signout;
    ImageButton calender_button;
    ImageButton data_button;
    ImageButton mate_button;
    ImageButton money_button;
    Button myself_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        signout = (Button)findViewById(R.id.signout);
        calender_button = (ImageButton)findViewById(R.id.calender_button);
        data_button = (ImageButton)findViewById(R.id.data_button);
        mate_button = (ImageButton)findViewById(R.id.mate_button);
        money_button = (ImageButton)findViewById(R.id.money_button);
        myself_button =(Button)findViewById(R.id.myself_button);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStart() {
        super.onStart();
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                // ...
                                Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(getApplicationContext(),LoginActivity.class);
                                finish();
                                startActivity(i);
                            }
                        });
            }
        });
        calender_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomePageActivity.this,"CalenderEnter",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(HomePageActivity.this,CalenderActivity.class);
                finish();
                startActivity(intent);
            }
        });
        data_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomePageActivity.this,"DataEnter",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(HomePageActivity.this,DataActivity.class);
                finish();
                startActivity(intent);
            }
        });
        mate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomePageActivity.this,"MateEnter",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(HomePageActivity.this,MateActivity.class);
                finish();
                startActivity(intent);
            }
        });
        money_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomePageActivity.this,"MoneyEnter",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(HomePageActivity.this,MoneyActivity.class);
                finish();
                startActivity(intent);
            }
        });
        myself_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomePageActivity.this,"MyselfEnter",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(HomePageActivity.this,MyselfActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }
}
