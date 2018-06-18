package tw.edu.niu.googlelogin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import static tw.edu.niu.googlelogin.R.drawable.animation;

public class DataActivity extends AppCompatActivity {
    ImageView imageView;

    AnimationDrawable animationDrawable;
    Button btnDataback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        btnDataback = findViewById(R.id.btnDataback);
        imageView  =  findViewById(R.id.imageView_animation);
        imageView.setBackgroundResource(R.drawable.ang);
        //mTextView.setBackground(mFrameAnimation);
        //mFrameAnimation.start();
        //imageView.setBackgroundResource(R.drawable.animation);
        animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();
        btnDataback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DataActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}
