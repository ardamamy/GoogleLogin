package tw.edu.niu.googlelogin;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import static tw.edu.niu.googlelogin.R.drawable.animation;

public class DataActivity extends AppCompatActivity {
    ImageView imageView;
    AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        imageView  =  findViewById(R.id.imageView_animation);
        imageView.setBackgroundResource(animation);
        animationDrawable = (AnimationDrawable) imageView.getBackground();

        imageView.post(new Runnable() {
            @Override
            public void run()  {
                animationDrawable.start();
            }
        });
    }
}
