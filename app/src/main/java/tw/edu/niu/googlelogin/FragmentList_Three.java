package tw.edu.niu.googlelogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Created by Solinari on 2016/12/31.
 */

public class FragmentList_Three extends Fragment {
    Button btnenter;
    ListView listView;
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.train_menu, container, false);
        btnenter = (Button)view.findViewById(R.id.btnenter);
        listView = (ListView)view.findViewById(R.id.listview);

        btnenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),TrainCreateActivity.class);
                startActivity(intent);
                getActivity().finish();

            }
        });


        return view;
    }
}