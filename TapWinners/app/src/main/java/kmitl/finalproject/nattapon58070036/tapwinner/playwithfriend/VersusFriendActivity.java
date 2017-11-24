package kmitl.finalproject.nattapon58070036.tapwinner.playwithfriend;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import kmitl.finalproject.nattapon58070036.tapwinner.R;
import kmitl.finalproject.nattapon58070036.tapwinner.playwithfriend.fragment.PlayerOneFragment;
import kmitl.finalproject.nattapon58070036.tapwinner.playwithfriend.fragment.PlayerTwoFragment;

public class VersusFriendActivity extends AppCompatActivity implements PlayerOneFragment.PlayerOneFragmentListener,
        PlayerTwoFragment.PlayerTwoFragmentListener {


    private boolean playerOnePlaying = false;
    private boolean playerTwoPlaying = false;

    private int playerOneScore = 0;
    private int playerTwoScore = 0;

    private boolean playerOneFinished = false;
    private boolean playerTwoFinished = false;

    private MediaPlayer mp;

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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_versus_friend);
        initFragment();
    }

    private void initFragment() {
        getSupportFragmentManager().findFragmentById(R.id.player1Container);
        getSupportFragmentManager().findFragmentById(R.id.player2Container);

    }

    public void getPlayerOneScore(int playerOneScore) {
        this.playerOneScore = playerOneScore;
    }

    public void getPlayerTwoScore(int playerTwoScore) {
        this.playerTwoScore = playerTwoScore;
    }


    @Override
    public void onBackPressed() {
        if (!(playerOnePlaying || playerTwoPlaying)) {
            super.onBackPressed();
        }
    }

    @Override
    public void onPlayerOnePlaying() {
        playerOnePlaying = true;
    }

    @Override
    public void onPlayerOneFinished() {
        playerOneFinished = true;
        if (playerOneFinished && playerTwoFinished) {
            goToPlayWithFriendResult();
        }

    }

    @Override
    public void onPlayerTwoPlaying() {
        playerTwoPlaying = true;
    }

    @Override
    public void onPlayerTwoFinished() {
        playerTwoFinished = true;
        if (playerOneFinished && playerTwoFinished) {
            goToPlayWithFriendResult();
        }

    }

    private void goToPlayWithFriendResult() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(VersusFriendActivity.this, PlayWithFriendResultActivity.class);
                intent.putExtra("playerOneScore", playerOneScore);
                intent.putExtra("playerTwoScore", playerTwoScore);
                startActivity(intent);
                finish();
            }
        }, 2000);

    }


}
