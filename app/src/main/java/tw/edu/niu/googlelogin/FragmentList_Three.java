package tw.edu.niu.googlelogin;

import android.app.AlertDialog;
import android.content.Intent;
import android.drm.DrmManagerClient;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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
import java.util.concurrent.Executor;

import javax.annotation.Nullable;

/**
 * Created by Solinari on 2016/12/31.
 */

public class FragmentList_Three extends Fragment {
    Button btnenter;
    ListView listView;
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<String> newtrains;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.train_menu, container, false);
        btnenter = (Button)view.findViewById(R.id.btnenter);
        listView = (ListView)view.findViewById(R.id.listview);
        newtrains = new ArrayList<String>();

        Query query =  db.collection("train").whereEqualTo("isnew",true);
        query.addSnapshotListener(getActivity(),new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e != null){
                    Log.e("mylog" , "Error", e);
                }else{
                    Log.e("mylog", queryDocumentSnapshots.size()+"");
                    if(queryDocumentSnapshots == null || queryDocumentSnapshots.isEmpty()){
                        Log.e("mylog" , "Error");
                    }else{
                        Log.e("mylog", "ENTER DocumentSnapshot");
                        DocumentSnapshot documentSnapshot = null;
                        for(DocumentSnapshot snapshot : queryDocumentSnapshots){
                            documentSnapshot = snapshot;
                            newtrains = (ArrayList<String>) snapshot.get("trainItem");
                        }
                        updatelistview(newtrains,listView);
                    }
                }
            }
        });

        btnenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("mylog","btnenter");
                Intent intent = new Intent(getActivity(),TrainCreateActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });


        return view;
    }
    private void updatelistview(ArrayList<String> newtrains, ListView listView){


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(getActivity(),
                android.R.layout.simple_list_item_1,newtrains);
        listView.setAdapter(arrayAdapter);
    }
}