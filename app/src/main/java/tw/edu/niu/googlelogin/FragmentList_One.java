package tw.edu.niu.googlelogin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

/**
 * Created by Solinari on 2016/12/31.
 */

public class FragmentList_One extends Fragment {
    GoogleApiClient mGoogleApiClient;
    Button signout;
    ImageButton calender_button;
    ImageButton data_button;
    ImageButton mate_button;
    ImageButton money_button;;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("mytag", "onAttach");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("mytag", "onStart");


    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("mytag", "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("mytag", "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("mytag", "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("mytag", "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("mytag", "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("mytag", "onDetach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("mytag", "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("mytag", "onCreateView");
        View view = inflater.inflate(R.layout.activity_home_page, container, false);
        signout = (Button) view.findViewById(R.id.signout);
        calender_button = (ImageButton) view.findViewById(R.id.calender_button);
        data_button = (ImageButton) view.findViewById(R.id.data_button);
        mate_button = (ImageButton)view.findViewById(R.id.mate_button);
        money_button = (ImageButton)view.findViewById(R.id.money_button);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                // ...getApplicationContext()
                                Toast.makeText(getActivity().getApplicationContext(),"Logged Out",Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(getActivity().getApplicationContext(),LoginActivity.class);
                                getActivity().finish();
                                startActivity(i);
                            }
                        });
            }
        });
        calender_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"CalenderEnter",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(),CalenderActivity.class);
                getActivity().finish();
                startActivity(intent);
            }
        });
        data_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"DataEnter",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(),DataActivity.class);
                startActivity(intent);
               getActivity().finish();
            }
        });
        mate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"MateEnter",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(),MateActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        money_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"MoneyEnter",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(),MoneyActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });



        return view;
    }
}
