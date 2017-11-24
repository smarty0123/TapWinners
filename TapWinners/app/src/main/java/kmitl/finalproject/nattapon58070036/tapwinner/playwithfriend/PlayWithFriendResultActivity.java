package kmitl.finalproject.nattapon58070036.tapwinner.playwithfriend;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.thunderrise.animations.PulseAnimation;
import com.thunderrise.animations.ShakeAnimation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kmitl.finalproject.nattapon58070036.tapwinner.MainActivity;
import kmitl.finalproject.nattapon58070036.tapwinner.R;

public class PlayWithFriendResultActivity extends AppCompatActivity {

    @BindView(R.id.tvWinner)
    TextView tvWinner;
    @BindView(R.id.btnPlayAgain)
    Button btnPlayAgain;
    @BindView(R.id.btnBackToMain)
    Button btnBackToMain;

    private MediaPlayer mp;

    @Override
    protected void onPause() {
        super.onPause();
        mp.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mp = MediaPlayer.create(this, R.raw.popdance);
        mp.setLooping(true);
        mp.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_play_with_friend_result);
        ButterKnife.bind(this);
        displayWinner();
        setAnimationView();
    }

    private void displayWinner() {
        int playerOneScore = getIntent().getIntExtra("playerOneScore", 0);
        int playerTwoScore = getIntent().getIntExtra("playerTwoScore", 0);
        int blueColor = getResources().getColor(R.color.blue);
        int pinkColor = getResources().getColor(R.color.pink);
        int grayColor = Color.GRAY;
        String winner;
        String color = "#";
        if (playerOneScore > playerTwoScore) {
            winner = "Winner is Pink\n" + playerOneScore;
            color = color+Integer.toHexString(pinkColor);
        } else if (playerOneScore < playerTwoScore) {
            winner = "Winner is Blue\n" + playerTwoScore;
            color = color+Integer.toHexString(blueColor);
        } else {
            winner = "DRAW";
            color = color+Integer.toHexString(grayColor);
        }
        tvWinner.setText(winner);
        tvWinner.setTextColor(Color.parseColor(color));
    }

    private void setAnimationView() {
        PulseAnimation.create().with(btnPlayAgain)
                .setDuration(500)
                .setRepeatCount(PulseAnimation.INFINITE)
                .setRepeatMode(PulseAnimation.REVERSE)
                .start();

        PulseAnimation.create().with(btnBackToMain)
                .setDuration(500)
                .setRepeatCount(PulseAnimation.INFINITE)
                .setRepeatMode(PulseAnimation.REVERSE)
                .start();

        ShakeAnimation.create().with(tvWinner)
                .setDuration(10000)
                .setRepeatMode(ShakeAnimation.RESTART)
                .setRepeatCount(ShakeAnimation.INFINITE)
                .start();
    }

    @OnClick(R.id.btnPlayAgain)
    public void onBtnPlayAgainClicked() {
        Intent intent = new Intent(this, VersusFriendActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.btnBackToMain)
    public void onBtnBackToMainClicked() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }


}
