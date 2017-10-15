package kmitl.finalproject.nattapon58070036.tapwinners;


import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class PlayActivity extends AppCompatActivity {
    private int score = 0;

    private TextView scoreText;
    private TextView tvTimer;
    private TextView tvStartGame;

    private ProgressBar pbTimer;
    private CountDownTimer cdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        scoreText = findViewById(R.id.scoreText);
        scoreText.setText("Score\n " + score);
        tvStartGame = findViewById(R.id.tvStartGame);
        tvTimer = findViewById(R.id.tvTimer);
        pbTimer = findViewById(R.id.pbTimer);
        pbTimer.setVisibility(View.INVISIBLE);
        tvTimer.setVisibility(View.INVISIBLE);
    }

    public void clicking(View view) {
        score++;
        if (score == 1) {
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
                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(PlayActivity.this);
                    builder.setMessage("รับขนมจีบซาลาเปาเพิ่มมั้ยครับ?");
                    builder.setCancelable(false);
                    builder.setPositiveButton("รับ", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(getApplicationContext(),
                                    "ขอบคุณครับ", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("ไม่รับ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //dialog.dismiss();
                        }
                    });
                    builder.show();
                    Toast.makeText(getApplicationContext(), "FINISH", Toast.LENGTH_LONG).show();
                    pbTimer.setVisibility(View.INVISIBLE);
                    tvTimer.setVisibility(View.INVISIBLE);
                }
            }.start();

        }
        scoreText.setText("Score\n" + score);
    }
}
