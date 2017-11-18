package kmitl.finalproject.nattapon58070036.tapwinner;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import kmitl.finalproject.nattapon58070036.tapwinner.adapter.ScoreboardAdapter;
import kmitl.finalproject.nattapon58070036.tapwinner.model.PlayerProfile;
import kmitl.finalproject.nattapon58070036.tapwinner.model.ScoreBoardModel;


public class ScoreBoardActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.imageView3)
    ImageView imageView3;

    private Query scoreboardDB;
    private PlayerProfile playerProfile;
    private ScoreboardAdapter scoreAdapter;

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
        ButterKnife.bind(this);
        playerProfile = getIntent().getParcelableExtra("PlayerProfile");
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
        ChildEventListener childEventListener = new ChildEventListener() {
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
            String playerPic = (String) ((DataSnapshot) i.next()).getValue();
            String playerName = (String) ((DataSnapshot) i.next()).getValue();
            int playerHighScore = (int) (long) ((DataSnapshot) i.next()).getValue();
            String tokenID = (String) ((DataSnapshot) i.next()).getValue();
            ScoreBoardModel scoreItem = new ScoreBoardModel();
            scoreItem.setImgUri(Uri.parse(playerPic));
            scoreItem.setPlayerName(playerName);
            scoreItem.setScore(playerHighScore);
            scoreItem.setTokenID(tokenID);

            playerProfile.addScoreList(scoreItem);
        }
        scoreAdapter.notifyDataSetChanged();
    }


}
