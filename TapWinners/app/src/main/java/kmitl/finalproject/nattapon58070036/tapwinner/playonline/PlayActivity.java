package kmitl.finalproject.nattapon58070036.tapwinner.playonline;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.thunderrise.animations.PulseAnimation;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kmitl.finalproject.nattapon58070036.tapwinner.R;
import kmitl.finalproject.nattapon58070036.tapwinner.model.PlayerProfile;


public class PlayActivity extends AppCompatActivity {
    @BindView(R.id.scoreText)
    TextView scoreText;
    @BindView(R.id.tvStartGame)
    TextView tvStartGame;
    @BindView(R.id.pbTimer)
    ProgressBar pbTimer;
    @BindView(R.id.tvTimer)
    TextView tvTimer;
    @BindView(R.id.playScreen)
    ConstraintLayout playScreen;
    private int score = 0;
    private PlayerProfile playerProfile;
    private DatabaseReference child;
    private String key;
    private boolean playing = false;
    private int highScore;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_play);
        initInstances();
        playerProfile = getIntent().getParcelableExtra("PlayerProfile");
        child = FirebaseDatabase.getInstance().getReference().child("Scoreboard");
    }

    @Override
    protected void onPause() {
        super.onPause();
        mp.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mp = MediaPlayer.create(this, R.raw.happyrock);
        mp.setLooping(true);
        mp.start();
        child.child(playerProfile.getPlayerId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    onGetChild(dataSnapshot);
                } else {
                    playerProfile.setPlayerHighScore(highScore);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void onGetChild(DataSnapshot dataSnapshot) {
        for (DataSnapshot child : dataSnapshot.getChildren()) {
            if (child.getKey().toString().equals("score")) {
                highScore = (int) (long) child.getValue();
                playerProfile.setPlayerHighScore(highScore);
            }

        }
    }

    private void initInstances() {
        ButterKnife.bind(this);
        scoreText.setText("Score\n " + score);
        pbTimer.setVisibility(View.INVISIBLE);
        tvTimer.setVisibility(View.INVISIBLE);
        setAnimationView(1000);
    }



    @Override
    public void onBackPressed() {
        if (!playing) {
            super.onBackPressed();
        }
    }

    @OnClick(R.id.playScreen)
    public void onViewClicked(final View view) {
        score++;
        if (score == 1) { //player start tap
            playing = true;
            tvStartGame.setText("MORE TAP\nMORE SCORE!!");
            tvStartGame.setTextSize(30);
            setAnimationView(100);
            pbTimer.setVisibility(View.VISIBLE);
            tvTimer.setVisibility(View.VISIBLE);

            CountDownTimer cdt = new CountDownTimer(6000, 50) {
                @Override
                public void onTick(long millisUntilFinished) {
                    tvTimer.setText(String.valueOf(Long.toString((millisUntilFinished / 1000) + 1)));
                    onBackPressed();
                }

                @Override
                public void onFinish() {
                    view.setOnClickListener(null);
                    pbTimer.setVisibility(View.INVISIBLE);
                    tvTimer.setVisibility(View.INVISIBLE);
                    tvStartGame.setTextSize(50);
                    tvStartGame.setText("Time Out!");
                    final Handler handler = new Handler();//start deley timing
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (playerProfile.getPlayerHighScore() < score) {
                                Map<String, Object> map = new HashMap<>();
                                key = playerProfile.getPlayerId();
                                map.put(key, "");
                                child.updateChildren(map);
                                DatabaseReference message_key = child.child(key);
                                Map<String, Object> map2 = new HashMap<>();
                                map2.put("token-id", FirebaseInstanceId.getInstance().getToken());
                                map2.put("player", playerProfile.getPlayerFirstName());
                                map2.put("score", score);
                                map2.put("pic", playerProfile.getPlayerImage().toString());
                                message_key.updateChildren(map2);

                            }
                            Intent intent = new Intent(PlayActivity.this, CurrentScoreActivity.class);
                            intent.putExtra("score", score);
                            intent.putExtra("PlayerProfile", playerProfile);
                            startActivity(intent);
                            finish();
                        }
                    }, 2000);
                }
            }.start();
        }
        scoreText.setText("Score\n" + score);

    }

    private void setAnimationView(int duration) {
        PulseAnimation.create().with(tvStartGame)
                .setDuration(duration)
                .setRepeatCount(PulseAnimation.INFINITE)
                .setRepeatMode(PulseAnimation.REVERSE)
                .start();
    }

}
