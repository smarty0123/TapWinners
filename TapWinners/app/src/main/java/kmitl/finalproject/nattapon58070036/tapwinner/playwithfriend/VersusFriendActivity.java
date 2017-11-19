package kmitl.finalproject.nattapon58070036.tapwinner.playwithfriend;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import kmitl.finalproject.nattapon58070036.tapwinner.R;
import kmitl.finalproject.nattapon58070036.tapwinner.playwithfriend.fragment.PlayerOneFragment;
import kmitl.finalproject.nattapon58070036.tapwinner.playwithfriend.fragment.PlayerTwoFragment;

public class VersusFriendActivity extends AppCompatActivity implements PlayerOneFragment.PlayerOneFragmentListener,
        PlayerTwoFragment.PlayerTwoFragmentListener {

    private PlayerOneFragment playerOneFragment;
    private PlayerTwoFragment playerTwoFragment;

    private boolean playerOnePlaying = false;
    private boolean playerTwoPlaying = false;

    private int playerOneScore = 0;
    private int playerTwoScore = 0;

    private int allPlayerFinish = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_versus_friend);
        initFragment();
    }

    private void initFragment() {
        Fragment fragment1 = getSupportFragmentManager().findFragmentById(R.id.player1Container);
        playerOneFragment = (PlayerOneFragment) fragment1;

        Fragment fragment2 = getSupportFragmentManager().findFragmentById(R.id.player2Container);
        playerTwoFragment = (PlayerTwoFragment) fragment2;

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
        allPlayerFinish++;
        playerOnePlaying = false;
        if (allPlayerFinish == 2) {
            goToPlayWithFriendResult();
        }

    }

    @Override
    public void onPlayerTwoPlaying() {
        playerTwoPlaying = true;
    }

    @Override
    public void onPlayerTwoFinished() {
        allPlayerFinish++;
        playerTwoPlaying = false;
        if (allPlayerFinish == 2) {
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
