package tw.edu.niu.googlelogin;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import tw.edu.niu.googlelogin.model.City;

public class MoneyActivity extends AppCompatActivity {
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    ListView listView;
    Button btntomain;
    ArrayList<String> mArrayList = new ArrayList<String>();
    ArrayList<String> mArrayListuserID = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);
        btntomain = findViewById(R.id.btntomain);
        listView = findViewById(R.id.listview);
        Query query = db.collection("users").whereEqualTo("hasfee",false);
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e != null){
                    Log.e("mylog" , "Error", e);
                }else{
                    Log.e("mylog", queryDocumentSnapshots.size()+"");
                    if(queryDocumentSnapshots == null || queryDocumentSnapshots.isEmpty()){
                        AlertDialog.Builder bdr=new AlertDialog.Builder(MoneyActivity.this);
                        bdr.setMessage("無成員");
                        bdr.setTitle("目前無任何成員");
                        bdr.setIcon(android.R.drawable.ic_dialog_info);
                    }else {
                        Log.e("mylog", "ENTER DocumentSnapshot");
                        DocumentSnapshot documentSnapshot = null;
                        for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                            documentSnapshot = snapshot;
                            mArrayList.add(snapshot.get("name").toString());
                            mArrayListuserID.add(snapshot.getId());
                            Log.e("mylog", snapshot.get("name").toString());
                        }
                        ArrayAdapter adapter = new ArrayAdapter(MoneyActivity.this,
                                android.R.layout.simple_list_item_1,
                                mArrayList);
                        listView.setAdapter(adapter);
                    }
                }
            }
        });

        btntomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoneyActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
