package kmitl.finalproject.nattapon58070036.tapwinner;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


import java.util.HashMap;
import java.util.Iterator;

import kmitl.finalproject.nattapon58070036.tapwinner.adapter.ScoreboardAdapter;

import kmitl.finalproject.nattapon58070036.tapwinner.model.PlayerProfile;
import kmitl.finalproject.nattapon58070036.tapwinner.model.ScoreBoardModel;


public class ScoreBoardActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private int score;
    private DatabaseReference notificationDB;
    private Query scoreboardDB;
    private PlayerProfile playerProfile;
    private ScoreboardAdapter scoreAdapter;
    private String playerName;
    private String playerPic;
    private String tokenID;
    private int playerHighScore;
    private ScoreBoardModel scoreItem;
    private Query passedPlayer;
    private ChildEventListener childEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initInstances();
        sendNotification();
        setupRecyclerView();
        displayScoreboard();

    }

    private void initInstances() {
        recyclerView = findViewById(R.id.recyclerView);
        playerProfile = getIntent().getParcelableExtra("PlayerProfile");
        score = getIntent().getIntExtra("score", 0);
        notificationDB = FirebaseDatabase.getInstance().getReference().child("notifications");
        passedPlayer = FirebaseDatabase.getInstance().getReference().child("Scoreboard").orderByChild("score").endAt(score-1);
        scoreboardDB = FirebaseDatabase.getInstance().getReference().child("Scoreboard").orderByChild("score");
    }

    private void setupRecyclerView() {
        scoreAdapter = new ScoreboardAdapter(this);
        scoreAdapter.setData(playerProfile.getScoreList());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(scoreAdapter);
    }

    private void displayScoreboard() {
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                onGetScoreChild(dataSnapshot);
            }

            @Override
            public void onChildChanged(final DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("databaseError", databaseError.toString());
                Toast.makeText(ScoreBoardActivity.this, "onCancelled", Toast.LENGTH_SHORT).show();
            }
        };
        scoreboardDB.addChildEventListener(childEventListener);
    }

    private void onGetScoreChild(DataSnapshot dataSnapshot) {
        Iterator i = dataSnapshot.getChildren().iterator();
        while (i.hasNext()) {
            playerPic = (String) ((DataSnapshot) i.next()).getValue();
            playerName = (String) ((DataSnapshot) i.next()).getValue();
            playerHighScore = (int) (long) ((DataSnapshot) i.next()).getValue();
            tokenID = (String) ((DataSnapshot) i.next()).getValue();
            scoreItem = new ScoreBoardModel();
            scoreItem.setImgUri(Uri.parse(playerPic));
            scoreItem.setPlayerName(playerName);
            scoreItem.setScore(playerHighScore);

            playerProfile.addScoreList(scoreItem);
        }
        scoreAdapter.notifyDataSetChanged();
    }

    private void sendNotification(){
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                onGetNotification(dataSnapshot);
                Log.i("scoreboard", "onChildAdded: ");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.i("scoreboard", "onChildChange: ");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.i("scoreboard", "onChildRemove: ");
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.i("scoreboard", "onChildMove: ");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("scoreboard", "onChildCancel: ");
            }
        };
        passedPlayer.addChildEventListener(childEventListener);
    }

    private void onGetNotification(DataSnapshot dataSnapshot){
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
                    Log.i("sendnotification", "onSuccess: ");
                }
            });

        }

    }

}
