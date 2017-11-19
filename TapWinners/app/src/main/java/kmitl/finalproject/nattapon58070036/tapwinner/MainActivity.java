package kmitl.finalproject.nattapon58070036.tapwinner;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.Profile;
import com.facebook.login.LoginManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import kmitl.finalproject.nattapon58070036.tapwinner.chatroom.ChatRoomActivity;
import kmitl.finalproject.nattapon58070036.tapwinner.model.PlayerProfile;
import kmitl.finalproject.nattapon58070036.tapwinner.playonline.PlayActivity;
import kmitl.finalproject.nattapon58070036.tapwinner.playonline.ScoreBoardActivity;
import kmitl.finalproject.nattapon58070036.tapwinner.playwithfriend.VersusFriendActivity;


public class MainActivity extends AppCompatActivity {
    @BindView(R.id.profileImage)
    CircleImageView profileImage;
    @BindView(R.id.nameText)
    TextView nameText;
    @BindView(R.id.btnLogout)
    Button btnLogout;
    @BindView(R.id.btnPlay)
    Button btnPlay;
    @BindView(R.id.btnChatRoom)
    Button btnChatRoom;
    @BindView(R.id.btnScoreboard)
    Button btnScoreboard;
    @BindView(R.id.btnVSFriend)
    Button btnVSFriend;

    private PlayerProfile playerProfile;
    private MediaPlayer mp;

    @Override
    protected void onPause() {
        super.onPause();
        mp.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mp = MediaPlayer.create(this, R.raw.cute);
        mp.setLooping(true);
        mp.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        if (AccessToken.getCurrentAccessToken() == null) {
            goLoginScreen();
        } else {
            ButterKnife.bind(this);
            setPlayerProfile();
            displayProfile();
        }
    }

    private void goLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void setPlayerProfile() {
        playerProfile = new PlayerProfile();
        Profile profile = Profile.getCurrentProfile();
        playerProfile.setPlayerFirstName(profile.getFirstName());
        playerProfile.setPlayerLastName(profile.getLastName());
        playerProfile.setPlayerId(profile.getId());
        playerProfile.setPlayerImage(profile.getProfilePictureUri(1000, 1000));
    }

    private void displayProfile() {
        nameText.setText("Player: " + playerProfile.getPlayerFirstName());
        Glide.with(this).load(playerProfile.getPlayerImage()).into(profileImage);
    }


    @OnClick(R.id.btnLogout)
    public void onBtnLogoutClicked() {
        LoginManager.getInstance().logOut();
        goLoginScreen();
    }

    @OnClick(R.id.btnPlay)
    public void onBtnPlayClicked() {
        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra("PlayerProfile", playerProfile);
        startActivity(intent);
    }

    @OnClick(R.id.btnChatRoom)
    public void onBtnChatRoomClicked() {
        Intent intent = new Intent(this, ChatRoomActivity.class);
        intent.putExtra("PlayerProfile", playerProfile);
        startActivity(intent);
    }

    @OnClick(R.id.btnScoreboard)
    public void onBtnScoreboardClicked() {
        Intent intent = new Intent(this, ScoreBoardActivity.class);
        intent.putExtra("PlayerProfile", playerProfile);
        startActivity(intent);
    }

    @OnClick(R.id.btnVSFriend)
    public void onViewClicked() {
        Intent intent = new Intent(this, VersusFriendActivity.class);
        startActivity(intent);
    }
}

