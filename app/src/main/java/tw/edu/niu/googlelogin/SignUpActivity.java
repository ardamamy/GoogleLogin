package tw.edu.niu.googlelogin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import tw.edu.niu.googlelogin.model.User;

public class SignUpActivity extends AppCompatActivity {
    EditText ename,enumber,edepartment;
    Button eset;
    Spinner spgender,spcop;
    boolean error = false;
    String strgender,strcop;
    User user;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    String stringUID = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ename = findViewById(R.id.name);
        enumber = findViewById(R.id.number);
        edepartment = findViewById(R.id.department);
        eset = findViewById(R.id.button);
        spgender = findViewById(R.id.spinsex);
        spcop = findViewById(R.id.spinnerCop);


//        Intent mIntent2 = getIntent();
//        stringUID = mIntent2.getStringExtra("StringUID");
        spgender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strgender = parent.getSelectedItem().toString();
                Log.d("mytag","successful select gender item");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spcop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strcop = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        eset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = new User("","","","","","","","");

                try{
                    if(firebaseUser!=null){
                        user.setName(ename.getText().toString());
                        user.setNumber(enumber.getText().toString());
                        user.setCoperation(strcop);
                        user.setGender(strgender);
                        user.setDepartment(edepartment.getText().toString());
                        user.setUserId(firebaseUser.getUid().toString());

                    }

                }catch(Exception e){
                    error = true;
                }
                if(!error) {
                    db.collection("users").document(""+firebaseUser.getUid().toString())
                            .set(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("mytag", "DocumentSnapshot successfully written!");
                                    Toast.makeText(SignUpActivity.this, "設定成功", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(SignUpActivity.this, TeamSelectActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("mytag", "Error writing document", e);
                                }
                            });
//                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                              @Override
//                               public void onSuccess(DocumentReference documentReference) {
//                                   Log.d("mytag", "DocumentSnapshot added with ID: " + documentReference.getId());
//                                   Toast.makeText(SignUpActivity.this, "設定成功", Toast.LENGTH_LONG).show();
//                                   Intent intent = new Intent(SignUpActivity.this,TeamSelectActivity.class);
//                                   startActivity(intent);
//                                   finish();
//                               }
//                           })
//                           .addOnFailureListener(new OnFailureListener() {
//                               @Override
//                               public void onFailure(@NonNull Exception e) {
//                                    Log.w("mytag", "Error adding document", e);
//                             }
//                          });
                }
            }
        });
    }
}
