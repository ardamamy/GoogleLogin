package tw.edu.niu.googlelogin;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static android.R.id.text1;

public class MateActivity extends AppCompatActivity  {
    ListView member;

   // List<String> marrayList;
    ArrayList<HashMap<String, User>> mArrayList = new ArrayList<HashMap<String, User>>();
    final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mate);
        member = findViewById(R.id.member);
        new Thread(new Runnable() {
            @Override
            public void run() {
                db.collection("users").get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    Log.d("mytag", "task success");
                                    for (QueryDocumentSnapshot document : task.getResult()) {

                                        try{
                                            DocumentSnapshot documentSnapshot = null;
                                            User user = documentSnapshot.toObject(User.class);

                                            Log.d("mytag", document.getId() + " => " + user.getUid());
                                        }catch (Exception e){
                                            Log.d("mytag", "fail");
                                        }

                                    }
                                } else {
                                    Log.w("mytag", "Error getting documents.", task.getException());
                                }
                            }
                        });
            }
        }).start();

       // marrayList = new ArrayList<String>();

    }




    private void listviewupdate(){




    }
    private void getListItems() {


    }
}