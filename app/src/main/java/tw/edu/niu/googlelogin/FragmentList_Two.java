package tw.edu.niu.googlelogin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import tw.edu.niu.googlelogin.model.User;

/**
 * Created by Solinari on 2016/12/31.
            */

    public class FragmentList_Two extends Fragment {
    private  TextView tvname;
    private TextView tvnumber;
    private TextView tvgender;
    private TextView tvdepartment;
    private TextView tvcoperation;
    private TextView tvteam;
    private Button btnname,btnnumber,btngender,btndepartment,btncoperation,btnteam;
    private boolean error = false;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.activity_myself, container, false);
            //TODO 個人資訊
        User user = new User("","","","","","","","");
        tvname = view.findViewById(R.id.tvname);
        tvnumber = view.findViewById(R.id.tvnumber);
        tvgender = view.findViewById(R.id.tvgender);
        tvdepartment = view.findViewById(R.id.tvdepartment);
        tvcoperation = view.findViewById(R.id.tvcoperation);
        tvteam = view.findViewById(R.id.tvteam);
        btnname = view.findViewById(R.id.btnname);
        btnnumber = view.findViewById(R.id.btnnumber);
        btngender = view.findViewById(R.id.btngender);
        btncoperation = view.findViewById(R.id.btncoperation);
        btnteam = view.findViewById(R.id.btnteam);
        btndepartment = view.findViewById(R.id.btndepartment);
        db.collection("users").document(firebaseUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.i("mylog","onSuccess");
                if(documentSnapshot.exists()){
                    Log.i("mylog","documentSnapshot.exists()");
                    tvname.setText(documentSnapshot.get("name").toString());
                    tvnumber.setText(documentSnapshot.get("number").toString());
                    tvgender.setText(documentSnapshot.get("gender").toString());
                    tvdepartment.setText(documentSnapshot.get("department").toString());
                    tvcoperation.setText(documentSnapshot.get("coperation").toString());
                    String teamId =documentSnapshot.get("teamID").toString();
                    db.collection("teams").document(teamId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot2) {
                            if(documentSnapshot2.exists()){
                                tvteam.setText(documentSnapshot2.get("name").toString());
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


//        btnname = view.findViewById(R.id.btnname);
////            setup = view.findViewById(R.id.button);
//            username =view.findViewById(R.id.name);
//            usernumber =view.findViewById(R.id.number);
//            userdepartment = view.findViewById(R.id.department);
//        setup.setOnClickListener(new View.OnClickListener() {
//            @Override示
//            public void onClick(View v) {
//
//                // Create a new user with a first and last name
//                Map<String, Object> user = new HashMap<>();
//                final User ardam = new User("","","","");
//                try{
//                    ardam.setName(username.getText().toString());
//                    ardam.setNumber(usernumber.getText().toString());
//                    ardam.setDepartment(userdepartment.getText().toString());
//                }catch (Exception e){
//                    error = true;
//                }
//                if(!error) {
//                    db.collection("users")
//                            .add(ardam)
//                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                                @Override
//                                public void onSuccess(DocumentReference documentReference) {
//                                    Log.d("mytag", "DocumentSnapshot added with ID: " + documentReference.getId());
//                                    Toast.makeText(getActivity(), "設定成功", Toast.LENGTH_LONG).show();
////                                    Intent intent = new Intent();
////
////                                    Bundle bundle = new Bundle();
////                                    bundle.putSerializable("User ", ardam);
////                                    intent.putExtra("ardam",bundle);
////                                    intent.setClass(getActivity(),HomePageActivity.class);
////                                    getActivity().finish();
////                                    startActivity(intent);
//                                }
//                            })
//                            .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Log.w("mytag", "Error adding document", e);
//                                }
//                            });
//                } else {
//                }
//            }
//        });
//
            return view;
      }
}