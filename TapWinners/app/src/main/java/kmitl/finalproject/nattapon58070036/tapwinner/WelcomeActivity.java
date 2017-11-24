package kmitl.finalproject.nattapon58070036.tapwinner;


import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WelcomeActivity extends AppCompatActivity {
    @BindView(R.id.imageView)
    ImageView imageView;

    AnimationDrawable animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        if(imageView == null) throw new AssertionError();
        imageView.setBackgroundResource(R.drawable.animation_loading);
        animation = (AnimationDrawable)imageView.getBackground();
        animation.start();
        Thread thread = new Thread() {

            public void run() {
                try {
                    sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

        };
        thread.start();

    }
}
