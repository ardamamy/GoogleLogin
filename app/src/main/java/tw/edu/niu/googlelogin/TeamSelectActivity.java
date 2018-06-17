package tw.edu.niu.googlelogin;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Nullable;

public class TeamSelectActivity extends AppCompatActivity {
    EditText editTextTeamName;
    Button btnSet,btnCreate;
    ListView listView;
    ArrayList<String> teamSelectList = new ArrayList<String>();
    ArrayList<String> teamSelectListID = new ArrayList<String>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    String teamSelectID = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_select);
        editTextTeamName = findViewById(R.id.teamName);
        btnSet = findViewById(R.id.set);
        btnCreate = findViewById(R.id.teamCreate);
        listView = findViewById(R.id.listview);
        Query query = db.collection("teams").whereEqualTo("success","true");
        query.addSnapshotListener(TeamSelectActivity.this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e != null){
                    Log.e("mylog" , "Error", e);
                }else{
                    Log.e("mylog", queryDocumentSnapshots.size()+"");
                    if(queryDocumentSnapshots == null || queryDocumentSnapshots.isEmpty()){
                        //  找不到隊伍
                        AlertDialog.Builder bdr=new AlertDialog.Builder(TeamSelectActivity.this);
                        bdr.setMessage("如果您要創建隊伍，請按創建!");
                        bdr.setTitle("目前無任何隊伍");
                        bdr.setIcon(android.R.drawable.ic_dialog_info);
                    }else{
                        DocumentSnapshot documentSnapshot = null;
                        for(DocumentSnapshot snapshot : queryDocumentSnapshots){
                            documentSnapshot = snapshot;
                            teamSelectList.add(snapshot.get("name").toString());
                            teamSelectListID.add(snapshot.getId());

                       }

                    }
                }
            }
        });
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                teamSelectList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(onClickListView);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamSelectActivity.this,TeamCreateActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser(teamSelectID);
            }
        });

    }
    private AdapterView.OnItemClickListener onClickListView = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // Toast 快顯功能 第三個參數 Toast.LENGTH_SHORT 2秒  LENGTH_LONG 5秒

            Toast.makeText(TeamSelectActivity.this,"點選第 "+(position +1) +" 個 \n內容："+teamSelectList.get(position), Toast.LENGTH_SHORT).show();
            editTextTeamName.setText(teamSelectList.get(position));
            teamSelectID = teamSelectListID.get(position);

        }
    };
    private void updateUser(String teamId){
        DocumentReference documentReference = db.collection("users").document(firebaseUser.getUid());
        HashMap<String, Object> data = new HashMap<>();
        data.put("teamID", teamId);
        data.put("role", "member");
        documentReference.update(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("mytag", "DocumentSnapshot successfully updated!");
                        Toast.makeText(TeamSelectActivity.this, "加入成功", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(TeamSelectActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("mytag", "Error updating document", e);
                        // 無法更新使用者角色
                        AlertDialog.Builder bd=new AlertDialog.Builder(TeamSelectActivity.this);
                        bd.setTitle("角色更新失敗!");
                        bd.setIcon(android.R.drawable.ic_dialog_alert);
                    }
                });

    }
}
