package tw.edu.niu.googlelogin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

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
                        .add(new Team(teamName.getText().toString(), firebaseUser.getUid(),true))
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                updateUserRole(documentReference.getId());
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                //TODO 無法創建系隊
                            }
                    });
                }
            }
        });
    }

    private void updateUserRole(String teamId){
        DocumentReference documentReference = db.collection("users").document(firebaseUser.getUid());

        HashMap<String, Object> data = new HashMap<>();
        data.put("teamID", teamId);
        data.put("role", "admin");

        documentReference.update(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("mytag", "DocumentSnapshot successfully updated!");
                        Toast.makeText(TeamCreateActivity.this, "創建成功", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(TeamCreateActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("mytag", "Error updating document", e);
                        //TODO 無法更新使用者角色
                    }
                });
    }
}
