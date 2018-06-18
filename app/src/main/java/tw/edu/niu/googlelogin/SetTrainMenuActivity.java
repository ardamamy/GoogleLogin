package tw.edu.niu.googlelogin;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import tw.edu.niu.googlelogin.model.TrainMenu;

public class SetTrainMenuActivity extends AppCompatActivity {
    EditText edtitle;
    EditText edtime;
    EditText editadditem;
    ListView trainitem;
    Button addTrainItem,setmenu;
    ArrayList<String> trainItemList;
    String strItemTrain = "";
    Calendar myCalendar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    String userteamid = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_train_menu);
        edtitle = findViewById(R.id.edtitle);
        edtime = findViewById(R.id.edtime);
        addTrainItem = findViewById(R.id.addTrainItem);
        editadditem = findViewById(R.id.editadditem);
        trainitem = findViewById(R.id.trainitem);
        setmenu = findViewById(R.id.setmenu);
        trainItemList = new ArrayList<String>();
        myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(edtime);
            }
        };
        edtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(SetTrainMenuActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        addTrainItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strItemTrain = editadditem.getText().toString();
                trainItemList.add(strItemTrain);
                Toast.makeText(SetTrainMenuActivity.this,"add success",Toast.LENGTH_LONG).show();
                editadditem.setText("");
                updateListView(trainItemList,trainitem);
            }
        });
        setmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    setUserTrain();
                    Intent intent = new Intent(SetTrainMenuActivity.this,TrainCreateActivity.class);
                    startActivity(intent);
                    finish();
                }catch (Exception e){
                    Log.d("mylog","error");
                }
            }
        });
    }
    void updateListView(ArrayList<String> trainItemList,ListView listView){
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                trainItemList);
        listView.setAdapter(arrayAdapter);
    }
    private void updateLabel(EditText edittext) {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edittext.setText(sdf.format(myCalendar.getTime()));
    }
    private  void setUserTrain(){
        db.collection("users").document(firebaseUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    userteamid = documentSnapshot.get("teamID").toString();
                    TrainMenu trainMenu = new TrainMenu(trainItemList,"","","","",null);
                    trainMenu.setTrainTime(edtime.getText().toString());
                    trainMenu.setTrainTitle(edtitle.getText().toString());
                    trainMenu.setTrainCreatedUserID(firebaseUser.getUid());
                    trainMenu.setTeamID(userteamid);
                    trainMenu.setIsnew(false);
                    db.collection("train").add(trainMenu).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(SetTrainMenuActivity.this,"設定成功",Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SetTrainMenuActivity.this,"設定失敗",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SetTrainMenuActivity.this,"設定失敗",Toast.LENGTH_LONG).show();
            }
        });
    }
}

