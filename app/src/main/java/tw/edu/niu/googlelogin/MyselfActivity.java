package tw.edu.niu.googlelogin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import tw.edu.niu.googlelogin.model.User;

public class MyselfActivity extends AppCompatActivity {
    EditText username;
    EditText usernumber;
    Button setup;

    boolean error = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myself);
        setup = findViewById(R.id.button);
        username =findViewById(R.id.name);
        usernumber =findViewById(R.id.number);
        setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                // Create a new user with a first and last name
                Map<String, Object> user = new HashMap<>();
                final User ardam = new User("","","","","","","","");
                try{
                    ardam.setName(username.getText().toString());
                    ardam.setNumber(usernumber.getText().toString());
                }catch (Exception e){
                    error = true;
                }
                if(!error) {
                    db.collection("users")
                            .add(ardam)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d("mytag", "DocumentSnapshot added with ID: " + documentReference.getId());
                                    Toast.makeText(MyselfActivity.this, "設定成功", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent();

                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("User ", ardam);
                                    intent.putExtra("ardam",bundle);
                                    intent.setClass(MyselfActivity.this,HomePageActivity.class);
                                    finish();
                                    startActivity(intent);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("mytag", "Error adding document", e);
                                }
                            });
                } else {
                }
            }
        });

// Add a new document with a generated ID

    }
}
