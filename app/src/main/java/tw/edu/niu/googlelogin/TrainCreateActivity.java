package tw.edu.niu.googlelogin;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

import static android.widget.Toast.LENGTH_SHORT;

public class TrainCreateActivity extends AppCompatActivity {
    Button backTofra3;
    android.support.v7.widget.Toolbar mtoolbar;
    ListView lvtrainmenu;
    ArrayList<HashMap<String,String>> menulist;
    ArrayList<String> menulistID;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    String userTeamID = "";
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_settings:
                Toast.makeText(this,"action_settings", LENGTH_SHORT).show();
                break;
            case R.id.subitem1:
                Toast.makeText(this,"subitem1", LENGTH_SHORT).show();
                Intent intent = new Intent(TrainCreateActivity.this,SetTrainMenuActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.subitem2:
                db.collection("users").document(firebaseUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            String role = documentSnapshot.get("role").toString();
                            if(role == "admin"){
                                Toast.makeText(TrainCreateActivity.this,"進入設定最新菜單",LENGTH_SHORT).show();//TODO 設定成員權限
                            }else{
                                Toast.makeText(TrainCreateActivity.this,"你沒有管理權限",LENGTH_SHORT).show();
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

                break;
            case R.id.action_help:
                Toast.makeText(this,"action_help", LENGTH_SHORT).show();
                //  跳出提示.
                AlertDialog.Builder bdr=new AlertDialog.Builder(TrainCreateActivity.this);
                bdr.setMessage("點擊一次菜單列表，可檢視該訓練菜單內容。\n點擊設定可新增菜單內容與最新菜單列表。");
                bdr.setTitle("提示:");
                bdr.setIcon(android.R.drawable.ic_dialog_info);


                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_create);
        Log.d("mylog","enteractivity");
        backTofra3 = findViewById(R.id.button2);
        mtoolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(mtoolbar);
        menulist = new ArrayList<HashMap<String,String>>();
        menulistID = new ArrayList<String>();
        lvtrainmenu = findViewById(R.id.lvtrainmenu);
        menulist.clear();
        menulistID.clear();
        db.collection("users").document(firebaseUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    userTeamID = documentSnapshot.get("teamID").toString();
                    Query query = db.collection("train").whereEqualTo("teamID",userTeamID);
                    query.addSnapshotListener(TrainCreateActivity.this, new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                            if (e != null) {
                                Log.e("mylog", "Error", e);
                            } else {
                                Log.e("mylog", queryDocumentSnapshots.size() + "");
                                if (queryDocumentSnapshots == null || queryDocumentSnapshots.isEmpty()) {
                                    AlertDialog.Builder bdr = new AlertDialog.Builder(TrainCreateActivity.this);
                                    bdr.setMessage("無");
                                    bdr.setTitle("目前無任何菜單");
                                    bdr.setIcon(android.R.drawable.ic_dialog_info);
                                } else {
                                    Log.e("mylog", "ENTER DocumentSnapshot");
                                    DocumentSnapshot documentSnapshot = null;
                                    for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                                        documentSnapshot = snapshot;
                                        HashMap map1 = new HashMap<String,String>();
                                        map1.put("trainTitle",snapshot.get("trainTitle").toString());
                                        map1.put("trainTime",snapshot.get("trainTime").toString());
                                        menulist.add(map1);
                                        menulistID.add(snapshot.getId().toString());
                                        Log.e("mylog", snapshot.get("trainTitle").toString());
                                    }
                                    updatelistview(menulist,lvtrainmenu);
                                    lvtrainmenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            String text = menulistID.get(position).toString();
                                            String result = "索引值: " + position + "\n" + "內容: " + text;
                                            Toast.makeText(TrainCreateActivity.this, result, Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent();
                                            intent.setClass(TrainCreateActivity.this,TrainShowActivity.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putString("menulistID",text);
                                            intent.putExtras(bundle);   // 記得put進去，不然資料不會帶過去哦
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                                }
                            }
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //TODO 搜尋失敗
                AlertDialog.Builder bd=new AlertDialog.Builder(TrainCreateActivity.this);
                bd.setTitle("搜尋錯誤!");
                bd.setIcon(android.R.drawable.ic_dialog_alert);
            }
        });

        backTofra3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainCreateActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void updatelistview(ArrayList<HashMap<String,String>> menulist,ListView lvtrainmenu){


        SimpleAdapter simpleAdapter = new SimpleAdapter(TrainCreateActivity.this,menulist,
                android.R.layout.simple_list_item_2,new String[] {"trainTitle","trainTime"},new int[] { android.R.id.text1, android.R.id.text2 }
        );
        lvtrainmenu.setAdapter(simpleAdapter);
    }
}
