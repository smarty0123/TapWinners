package kmitl.finalproject.nattapon58070036.tapwinners;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


import java.util.Iterator;


import kmitl.finalproject.nattapon58070036.tapwinners.adapter.ScoreboardAdapter;

import kmitl.finalproject.nattapon58070036.tapwinners.model.PlayerProfile;
import kmitl.finalproject.nattapon58070036.tapwinners.model.ScoreBoardModel;


public class ScoreBoardActivity extends AppCompatActivity {
    private TextView scoreText;
    private RecyclerView recyclerView;
    private int score;
    private Query child;
    private PlayerProfile playerProfile;
    private ScoreboardAdapter scoreAdapter;
    private String playerName;
    private String playerPic;
    private int playerHighScore;
    private ScoreBoardModel scoreItem;
    ChildEventListener childEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initInstances();
        setupRecyclerView();
        displayScoreboard();
    }

    private void initInstances() {
        scoreText = findViewById(R.id.scoreText);
        recyclerView = findViewById(R.id.recyclerView);
        playerProfile = getIntent().getParcelableExtra("PlayerProfile");
        score = getIntent().getIntExtra("score", 0);
        scoreText.setText("" + score);
        child = FirebaseDatabase.getInstance().getReference().child("Scoreboard").orderByChild("score").limitToFirst(100);
    }

    private void setupRecyclerView() {
        scoreAdapter = new ScoreboardAdapter(this);
        scoreAdapter.setData(playerProfile.getScoreList());
        Log.i("setup", "setupRecyclerView: ");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(scoreAdapter);
    }

    private void displayScoreboard() {
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                onGetChild(dataSnapshot);
            }

            @Override
            public void onChildChanged(final DataSnapshot dataSnapshot, String s) {
                onGetChild(dataSnapshot);
                Toast.makeText(ScoreBoardActivity.this, "onChildChange", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                onGetChild(dataSnapshot);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                onGetChild(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("databaseError", databaseError.toString());
                Toast.makeText(ScoreBoardActivity.this, "onCancelled", Toast.LENGTH_SHORT).show();
            }
        };
        child.addChildEventListener(childEventListener);
    }

    private void onGetChild(DataSnapshot dataSnapshot) {
        Iterator i = dataSnapshot.getChildren().iterator();
        while (i.hasNext()) {
            playerPic = (String) ((DataSnapshot) i.next()).getValue();
            playerName = (String) ((DataSnapshot) i.next()).getValue();
            playerHighScore = (int) (long) ((DataSnapshot) i.next()).getValue();
            scoreItem = new ScoreBoardModel();
            scoreItem.setImgUri(Uri.parse(playerPic));
            scoreItem.setPlayerName(playerName);
            scoreItem.setScore(playerHighScore);
            playerProfile.addScoreList(scoreItem);
        }

        scoreAdapter.notifyDataSetChanged();
    }



}
