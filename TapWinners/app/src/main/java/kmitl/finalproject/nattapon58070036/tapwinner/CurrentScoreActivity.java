package kmitl.finalproject.nattapon58070036.tapwinner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import kmitl.finalproject.nattapon58070036.tapwinner.model.PlayerProfile;

public class CurrentScoreActivity extends AppCompatActivity {
    @BindView(R.id.profileImage)
    CircleImageView profilePic;
    @BindView(R.id.tvCurrentScore)
    TextView tvCurrentScore;
    @BindView(R.id.btnPlayAgain)
    Button btnPlayAgain;
    private PlayerProfile playerProfile;
    private int score;

    private DatabaseReference notificationDB;
    private Query passedPlayer;
    private ChildEventListener childEventListener;
    private String playerPic;
    private String playerName;
    private int playerHighScore;
    private String tokenID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_score);
        ButterKnife.bind(this);
        initInstance();
        sendNotification();
        displayCurrentScore();
    }


    private void initInstance() {
        score = getIntent().getIntExtra("score", 0);

        playerProfile = getIntent().getParcelableExtra("PlayerProfile");

        notificationDB = FirebaseDatabase.getInstance().getReference().child("notifications");
        passedPlayer = FirebaseDatabase.getInstance().getReference().child("Scoreboard").orderByChild("score").endAt(score - 1);
    }

    private void displayCurrentScore() {
        Glide.with(this).load(playerProfile.getPlayerImage()).into(profilePic);
        tvCurrentScore.setText("YOUR SCORE\n" + score);
    }

    private void sendNotification() {
        childEventListener = new ChildEventListener() {
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
            playerPic = (String) ((DataSnapshot) i.next()).getValue();
            playerName = (String) ((DataSnapshot) i.next()).getValue();
            playerHighScore = (int) (long) ((DataSnapshot) i.next()).getValue();
            tokenID = (String) ((DataSnapshot) i.next()).getValue();
            HashMap<String, String> notificationData = new HashMap<>();
            notificationData.put("from", playerProfile.getPlayerFirstName());
            notificationDB.child(dataSnapshot.getKey()).push().setValue(notificationData).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                }
            });

        }
    }


    @OnClick(R.id.btnPlayAgain)
    public void onViewClicked() {
        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra("PlayerProfile", playerProfile);
        startActivity(intent);
        finish();
    }
}
