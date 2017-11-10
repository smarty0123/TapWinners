package kmitl.finalproject.nattapon58070036.tapwinner;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.Profile;
import com.facebook.login.LoginManager;

import kmitl.finalproject.nattapon58070036.tapwinner.model.PlayerProfile;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Profile profile;
    private TextView nameText;
    private ImageView profileImage;
    private Button btnLogout;
    private Button btnPlay;
    private Button btnChatRoom;
    private Button btnScoreboard;
    private PlayerProfile playerProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        if (AccessToken.getCurrentAccessToken() == null) {
            goLoginScreen();
        } else {
            initInstances();
            setPlayerProfile();
            displayProfile();
        }
    }

    private void goLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void initInstances(){
        btnLogout = findViewById(R.id.btnLogout);
        btnPlay = findViewById(R.id.btnPlay);
        btnChatRoom = findViewById(R.id.btnChatRoom);
        btnScoreboard = findViewById(R.id.btnScoreboard);
        btnLogout.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
        btnChatRoom.setOnClickListener(this);
        btnScoreboard.setOnClickListener(this);

    }

    private void displayProfile() {
        nameText = findViewById(R.id.nameText);
        nameText.setText("Player: " + playerProfile.getPlayerFirstName());
        profileImage = findViewById(R.id.profileImage);
        Glide.with(this).load(playerProfile.getPlayerImage()).into(profileImage);
    }

    private void setPlayerProfile(){
        playerProfile = new PlayerProfile();
        profile = Profile.getCurrentProfile();
        playerProfile.setPlayerFirstName(profile.getFirstName());
        playerProfile.setPlayerLastName(profile.getLastName());
        playerProfile.setPlayerId(profile.getId());
        playerProfile.setPlayerImage(profile.getProfilePictureUri(1000, 1000));
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnLogout){
            LoginManager.getInstance().logOut();
            goLoginScreen();
        }else if(view.getId() == R.id.btnPlay){
            Intent intent = new Intent(this, PlayActivity.class);
            intent.putExtra("PlayerProfile", playerProfile);
            startActivity(intent);
        }else if(view.getId() == R.id.btnChatRoom){
            Intent intent = new Intent(this, ChatRoomActivity.class);
            intent.putExtra("PlayerProfile", playerProfile);
            startActivity(intent);
        }else if(view.getId() == R.id.btnScoreboard){
            Intent intent = new Intent(this, ScoreBoardActivity.class);
            intent.putExtra("PlayerProfile", playerProfile);
            startActivity(intent);
        }
    }
}

