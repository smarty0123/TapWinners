package kmitl.finalproject.nattapon58070036.tapwinner.playwithfriend;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kmitl.finalproject.nattapon58070036.tapwinner.R;

public class PlayWithFriendResultActivity extends AppCompatActivity {

    @BindView(R.id.tvWinner)
    TextView tvWinner;
    @BindView(R.id.btnPlayAgain)
    Button btnPlayAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_play_with_friend_result);
        ButterKnife.bind(this);
        displayWinner();
    }

    private void displayWinner() {
        int playerOneScore = getIntent().getIntExtra("playerOneScore", 0);
        int playerTwoScore = getIntent().getIntExtra("playerTwoScore", 0);
        String winner;
        if (playerOneScore > playerTwoScore) {
            winner = "Winner is Red\n" + playerOneScore;
        } else if (playerOneScore < playerTwoScore) {
            winner = "Winner is Blue\n" + playerTwoScore;
        } else {
            winner = "DRAW";
        }
        tvWinner.setText(winner);
    }

    @OnClick(R.id.btnPlayAgain)
    public void onViewClicked() {
        startActivity(new Intent(this, VersusFriendActivity.class));
        finish();
    }
}
