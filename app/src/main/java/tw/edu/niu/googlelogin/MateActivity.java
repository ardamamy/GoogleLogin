package tw.edu.niu.googlelogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

import tw.edu.niu.googlelogin.model.User;

public class MateActivity extends AppCompatActivity  {
    ListView member;
    Button btnback;
   // List<String> marrayList;
    ArrayList<HashMap<String, User>> mArrayList = new ArrayList<HashMap<String, User>>();
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    Query query=db.collection("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mate);
        member = findViewById(R.id.member);
        btnback = findViewById(R.id.btnback);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(MateActivity.this,MainActivity.class);
                finish();
                startActivity(mIntent);
            }
        });
       // marrayList = new ArrayList<String>();

    }




    private void listviewupdate(){




    }
    private void getListItems() {


    }
}