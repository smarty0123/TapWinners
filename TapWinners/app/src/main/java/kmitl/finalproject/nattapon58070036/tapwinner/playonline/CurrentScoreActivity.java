package kmitl.finalproject.nattapon58070036.tapwinner.playonline;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.CallbackManager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.thunderrise.animations.FlipAnimation;
import com.thunderrise.animations.PulseAnimation;
import com.thunderrise.animations.ShakeAnimation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import kmitl.finalproject.nattapon58070036.tapwinner.MainActivity;
import kmitl.finalproject.nattapon58070036.tapwinner.R;
import kmitl.finalproject.nattapon58070036.tapwinner.model.PlayerProfile;

import static java.security.AccessController.getContext;

public class CurrentScoreActivity extends AppCompatActivity {


    @BindView(R.id.btnScoreboard)
    ImageView btnScoreboard;
    @BindView(R.id.profileImage)
    CircleImageView profileImage;
    @BindView(R.id.tvCurrentScore)
    TextView tvCurrentScore;
    @BindView(R.id.btnPlayAgain)
    Button btnPlayAgain;
    @BindView(R.id.btnBackToMain)
    Button btnBackToMain;
    @BindView(R.id.btnShare)
    Button btnShare;
    @BindView(R.id.currentScoreView)
    ConstraintLayout currentScoreView;
    private PlayerProfile playerProfile;

    private int score;

    private DatabaseReference notificationDB;
    private Query passedPlayer;

    private MediaPlayer mp;

    private CallbackManager callbackManager;

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
        setContentView(R.layout.activity_current_score);
        initInstance();
        sendNotification();
        displayCurrentScore();
    }


    private void initInstance() {
        ButterKnife.bind(this);

        setAnimationView();

        score = getIntent().getIntExtra("score", 0);

        playerProfile = getIntent().getParcelableExtra("PlayerProfile");

        notificationDB = FirebaseDatabase.getInstance().getReference().child("notifications");
        passedPlayer = FirebaseDatabase.getInstance().getReference().child("Scoreboard").orderByChild("score").endAt(score - 1);

    }

    private void displayCurrentScore() {
        Glide.with(this).load(playerProfile.getPlayerImage()).into(profileImage);
        tvCurrentScore.setText("YOUR SCORE\n" + score);
    }

    private void sendNotification() {
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                onGetNotification(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        passedPlayer.addChildEventListener(childEventListener);
    }

    private void onGetNotification(DataSnapshot dataSnapshot) {
        Iterator i = dataSnapshot.getChildren().iterator();
        while (i.hasNext()) {
            String playerPic = (String) ((DataSnapshot) i.next()).getValue();
            String playerName = (String) ((DataSnapshot) i.next()).getValue();
            int playerHighScore = (int) (long) ((DataSnapshot) i.next()).getValue();
            String tokenID = (String) ((DataSnapshot) i.next()).getValue();
            notificationDB.child(dataSnapshot.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            if (child.getKey().toString().equals(playerProfile.getPlayerId())) {
                                notificationDB.child(child.getKey().toString()).child(dataSnapshot.getKey()).setValue("");
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            HashMap<String, String> notificationData = new HashMap<>();
            notificationData.put("from", playerProfile.getPlayerFirstName());
            notificationDB.child(dataSnapshot.getKey()).child(playerProfile.getPlayerId()).setValue(notificationData)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                        }
                    });

        }
    }

    private void setAnimationView() {
        FlipAnimation.create().with(profileImage)
                .setDuration(10000)
                .setRepeatCount(FlipAnimation.INFINITE)
                .start();

        PulseAnimation.create().with(btnPlayAgain)
                .setDuration(500)
                .setRepeatCount(PulseAnimation.INFINITE)
                .setRepeatMode(PulseAnimation.REVERSE)
                .start();

        ShakeAnimation.create().with(tvCurrentScore)
                .setDuration(10000)
                .setRepeatMode(ShakeAnimation.RESTART)
                .setRepeatCount(ShakeAnimation.INFINITE)
                .start();

        PulseAnimation.create().with(btnScoreboard)
                .setDuration(500)
                .setRepeatCount(PulseAnimation.INFINITE)
                .setRepeatMode(PulseAnimation.REVERSE)
                .start();

        PulseAnimation.create().with(btnBackToMain)
                .setDuration(500)
                .setRepeatCount(PulseAnimation.INFINITE)
                .setRepeatMode(PulseAnimation.REVERSE)
                .start();
    }

    private Bitmap createBitmapFromView(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);
        view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        view.draw(c);
        return bitmap;
    }

    private void saveBitmap(Bitmap bitmap) {
        try {
            File cachePath = new File(this.getCacheDir(), "images");
            cachePath.mkdirs();
            FileOutputStream stream = new FileOutputStream(cachePath + "/image.png");
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Uri getUriFile() {
        File imagePath = new File(this.getCacheDir(), "images");
        File newFile = new File(imagePath, "image.png");
        return FileProvider.getUriForFile(this, "kmitl.finalproject.nattapon58070036.tapwinner.fileprovider", newFile);
    }

    private void shareIntent(Uri contentUri) {
        if (contentUri != null) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            shareIntent.setDataAndType(contentUri, this.getContentResolver().getType(contentUri));
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            startActivity(Intent.createChooser(shareIntent, "Choose an app"));
        }
    }

    @OnClick(R.id.btnScoreboard)
    public void onBtnScoreboardClicked() {
        Intent intent = new Intent(this, ScoreBoardActivity.class);
        intent.putExtra("PlayerProfile", playerProfile);
        startActivity(intent);
    }

    @OnClick(R.id.btnPlayAgain)
    public void onBtnPlayAgainClicked() {
        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra("PlayerProfile", playerProfile);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.btnBackToMain)
    public void onBtnBackToMainClicked() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.btnShare)
    public void onBtnShareClicked() {
        Bitmap bitmap = createBitmapFromView(currentScoreView);
        saveBitmap(bitmap);
        Uri contentUri = getUriFile();
        shareIntent(contentUri);
    }
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
