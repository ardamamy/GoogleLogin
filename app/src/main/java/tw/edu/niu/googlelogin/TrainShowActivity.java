package tw.edu.niu.googlelogin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import tw.edu.niu.googlelogin.model.TrainMenu;

public class TrainShowActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    TextView mtvShowTitle,tvShowUser;
    ListView lvShowItem;
    ArrayList<String> item;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_show);
        Bundle bundle = getIntent().getExtras();
        String menuID = "";
        menuID = bundle.getString("menulistID");
        Log.i("mylog",bundle.getString("menulistID"));
        mtvShowTitle = findViewById(R.id.mtvShowTitle);
        tvShowUser = findViewById(R.id.tvShowUser);
        lvShowItem = findViewById(R.id.lvShowItem);
        btnBack = findViewById(R.id.btnBack);
        db.collection("train").document(menuID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                   TrainMenu trainMenu = new TrainMenu((ArrayList<String>) documentSnapshot.get("trainItem"),"","","","",null);
                   trainMenu.setTrainTitle(documentSnapshot.get("trainTitle").toString());
                   trainMenu.setTrainItem((ArrayList<String>) documentSnapshot.get("trainItem"));
                   trainMenu.setTrainCreatedUserID((String) documentSnapshot.get("trainCreatedUserID"));
                   trainMenu.setTeamID((String) documentSnapshot.get("teamID"));
                   trainMenu.setTrainTime((String) documentSnapshot.get("trainTime"));

                    db.collection("users").document(trainMenu.getTrainCreatedUserID()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot userdocumentSnapshot) {
                            if(userdocumentSnapshot.exists()){
                                tvShowUser.setText(userdocumentSnapshot.get("name").toString());
                                mtvShowTitle.setText(trainMenu.getTrainTitle());
                                updateLv(trainMenu.getTrainItem(),lvShowItem);

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainShowActivity.this,TrainCreateActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private void updateLv(ArrayList<String> item,ListView lvShowItem){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(TrainShowActivity.this,android.R.layout.simple_list_item_1,
                item);
        lvShowItem.setAdapter(adapter);

    }
}
