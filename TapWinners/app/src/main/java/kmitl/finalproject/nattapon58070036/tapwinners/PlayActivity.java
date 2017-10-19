package kmitl.finalproject.nattapon58070036.tapwinners;


import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener{
    private int score = 0;
    private ConstraintLayout playScreen;
    private TextView scoreText;
    private TextView tvTimer;
    private TextView tvStartGame;

    private ProgressBar pbTimer;
    private CountDownTimer cdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        initInstances();
    }

    private void initInstances(){
        scoreText = findViewById(R.id.scoreText);
        scoreText.setText("Score\n " + score);
        tvStartGame = findViewById(R.id.tvStartGame);
        tvTimer = findViewById(R.id.tvTimer);
        pbTimer = findViewById(R.id.pbTimer);
        pbTimer.setVisibility(View.INVISIBLE);
        tvTimer.setVisibility(View.INVISIBLE);
        playScreen = findViewById(R.id.playScreen);
        playScreen.setOnClickListener(this);
    }


    private void alertDialog(){
        AlertDialog.Builder builder =
                new AlertDialog.Builder(PlayActivity.this);
        builder.setMessage("เล่นใหม่อีกครั้ง?");
        builder.setCancelable(false);
        builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                score = 0;
                scoreText.setText("Score\n" + score);
                tvStartGame.setText("Tap To Start");
            }
        });
        builder.show();
        Toast.makeText(getApplicationContext(), "FINISH", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(final View view) {
        if(view.getId() == R.id.playScreen){
            score++;
            if (score == 1) { //player start tap
                tvStartGame.setText("TAP!!!!");
                pbTimer.setVisibility(View.VISIBLE);
                tvTimer.setVisibility(View.VISIBLE);
                cdt = new CountDownTimer(6000, 50) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        tvTimer.setText(String.valueOf(Long.toString((millisUntilFinished / 1000) + 1)));
                    }

                    @Override
                    public void onFinish() {
                        view.setOnClickListener(null);
                        pbTimer.setVisibility(View.INVISIBLE);
                        tvTimer.setVisibility(View.INVISIBLE);
                        tvStartGame.setText("Time Out!");
                        final Handler handler = new Handler();//start deley timing
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                alertDialog();
                            }
                        }, 500); //set delay as 500 ms
                    }
                }.start();
            }
            scoreText.setText("Score\n" + score);
        }
    }
}
