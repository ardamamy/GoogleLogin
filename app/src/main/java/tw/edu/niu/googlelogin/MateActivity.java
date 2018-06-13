package tw.edu.niu.googlelogin;

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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

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

import javax.annotation.Nullable;

import tw.edu.niu.googlelogin.model.User;

public class MateActivity extends AppCompatActivity  {
    ListView lvmember;
    Button btnback;
   // List<String> marrayList;
    ArrayList<String> mArrayList = new ArrayList<String>();
    ArrayList<String> mArrayListuserID = new ArrayList<String>();
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    String mateSelectID = "";
    String userTeamId = "";
    String checkCoperation = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mate);
        lvmember =(ListView) findViewById(R.id.lvmember);
        btnback = findViewById(R.id.btnback);
        //TODO 提醒使用者雙擊可更動繳費狀況
        db.collection("users").document(firebaseUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    Log.i("mylog","documentSnapshot.exists()");
                    userTeamId = documentSnapshot.get("teamID").toString();
                    Log.i("mylog",userTeamId);
                    Query query = db.collection("users").whereEqualTo("success",true).whereEqualTo("teamID",userTeamId);
                    query.addSnapshotListener(MateActivity.this, new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                            if(e != null){
                                Log.e("mylog" , "Error", e);
                            }else{
                                Log.e("mylog", queryDocumentSnapshots.size()+"");
                                if(queryDocumentSnapshots == null || queryDocumentSnapshots.isEmpty()){
                                    AlertDialog.Builder bdr=new AlertDialog.Builder(MateActivity.this);
                                    bdr.setMessage("無成員");
                                    bdr.setTitle("目前無任何成員");
                                    bdr.setIcon(android.R.drawable.ic_dialog_info);
                                }else{
                                    Log.e("mylog", "ENTER DocumentSnapshot");
                                    DocumentSnapshot documentSnapshot = null;
                                    for(DocumentSnapshot snapshot : queryDocumentSnapshots){
                                        documentSnapshot = snapshot;
                                        mArrayList.add(snapshot.get("name").toString());
                                        mArrayListuserID.add(snapshot.getId());
                                        Log.e("mylog", snapshot.get("name").toString());
                                    }
                                    ArrayAdapter adapter = new ArrayAdapter(MateActivity.this,
                                            android.R.layout.simple_list_item_1,
                                            mArrayList);
                                    lvmember.setAdapter(adapter);
                                    lvmember.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                            db.collection("users").document(firebaseUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                @Override
                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                    if(documentSnapshot.exists()){
                                                        checkCoperation = documentSnapshot.get("coperation").toString();
                                                    }
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    //TODO 錯誤:職稱查詢失敗
                                                }
                                            });
                                            if(checkCoperation.equals("球經")){
                                                //TODO 更改繳費狀況
                                                Toast.makeText(MateActivity.this,"點選第 "+(position +1) +" 個 \n內容："+mArrayList.get(position), Toast.LENGTH_SHORT).show();
                                                mateSelectID = mArrayListuserID.get(position);
                                                db.collection("users").document(mateSelectID).update("hasfee",true);
                                                //TODO 提醒使用者更新成功
                                                Toast.makeText(MateActivity.this,"隊費設定已繳", Toast.LENGTH_SHORT).show();
                                                lvmember.setAdapter(adapter);
                                            }else{
                                                //TODO 職稱不同無法操作
                                            }
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
                //TODO 隊伍比對失敗
            }
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(MateActivity.this,MainActivity.class);
                startActivity(mIntent);
                finish();
            }
        });
    }
}