package tw.edu.niu.googlelogin;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.client.Firebase;
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

import javax.annotation.Nullable;

public class WelcomeActivity extends AppCompatActivity {
    ImageView imageView;
    AnimationDrawable animationDrawable;
    private FirebaseAuth mAuth;
    private int delay = 3200;
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    String stringUID = "";
    @Override
    protected void onStart() {
        super.onStart();

        if(firebaseUser != null){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkRegister();
                }
            }, delay);
        }else{
            Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mAuth = FirebaseAuth.getInstance();
        imageView  =  findViewById(R.id.imageView);
        imageView.setBackgroundResource(R.drawable.img);
        animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();

    }

    private void checkRegister(){
        //Intent mIntent2 = getIntent();
        //stringUID = mIntent2.getStringExtra("StringUID");
        db.collection("users").document(firebaseUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.i("mylog","fuckyou");
                if(documentSnapshot.exists()){
                    Log.i("mylog","fuckyoubeach");
                    if(documentSnapshot.get("teamID") != null && !documentSnapshot.get("teamID").toString().equals("")){
                        //TODO 使用者已經有所屬球隊
                        Log.i("mylog","使用者已經有所屬球隊");
                        Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        // TODO 使用者沒有所屬球隊
                        Log.i("mylog","使用者沒有所屬球隊");
                        Intent intent = new Intent(WelcomeActivity.this, TeamSelectActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }else{
                    Intent intent = new Intent(WelcomeActivity.this,SignUpActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("mylog","fuckyoufather");
                Toast.makeText(WelcomeActivity.this, "發生錯誤", Toast.LENGTH_LONG).show();//TODO 顯示錯誤給使用者知道
            }
        });
//        Query query = db.collection("users").whereEqualTo("userId", firebaseUser.getUid());
//        //Query query = db.collection("users").document(""+firebaseUser.getUid());
//        query.addSnapshotListener(WelcomeActivity.this, new EventListener<QuerySnapshot>() {
//
//            @Override
//            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//
//                if(e != null){
//
//                    Log.e("Li-Xian Chen", "Error", e);
//                }else{
//                    Log.e("Li-Xian Chen", queryDocumentSnapshots.size()+"");
//                    if(queryDocumentSnapshots == null || queryDocumentSnapshots.isEmpty()){
//                        //TODO 使用者還沒註冊
//                            Intent intent = new Intent(WelcomeActivity.this,SignUpActivity.class);
//                            //intent.putExtra("StringUID",stringUID);
//                            startActivity(intent);
//                            finish();
//                    }else{
//                        //TODO 使用者已經註冊 判斷有沒有加入隊伍
//                        //取得使用者的document
//                        DocumentSnapshot documentSnapshot = null;
//                        for(DocumentSnapshot snapshot : queryDocumentSnapshots){
//                            documentSnapshot = snapshot;
//                        }
//                        Object teamObj = documentSnapshot.get("teamID");
//                        if(teamObj != null && !teamObj.toString().equals("")){
//                            //TODO 使用者已經有所屬球隊
//                            Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
//                            startActivity(intent);
//                            finish();
//                        }else{
//                            //TODO 使用者沒有所屬球隊
//                            Intent intent = new Intent(WelcomeActivity.this,TeamSelectActivity.class);
//                            startActivity(intent);
//                            finish();
//                        }
//                    }
//                }
//            }
//        });
    }
}
