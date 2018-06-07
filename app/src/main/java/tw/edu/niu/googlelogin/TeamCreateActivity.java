package tw.edu.niu.googlelogin;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import tw.edu.niu.googlelogin.model.Team;
import tw.edu.niu.googlelogin.model.User;

public class TeamCreateActivity extends AppCompatActivity {
    Button buttonCreate;
    TextView teamName;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_create);
        buttonCreate = findViewById(R.id.buttonCreate);
        teamName = findViewById(R.id.teamName);
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(teamName.getText().toString().equals("")){
                    //TODO 使用者沒有輸入東西
                }else{
                    db.collection("teams")
                        .add(new Team(teamName.getText().toString(), firebaseUser.getUid()))
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                documentReference.getId();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                    });
                }
            }
        });
    }
}
