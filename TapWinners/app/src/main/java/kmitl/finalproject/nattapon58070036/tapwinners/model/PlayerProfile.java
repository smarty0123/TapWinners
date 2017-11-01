package kmitl.finalproject.nattapon58070036.tapwinners.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SMART on 23/10/2560.
 */

public class PlayerProfile implements Parcelable {
    private List<ChatModel> chatList = new ArrayList<>();
    private List<ScoreBoardModel> scoreList = new ArrayList<>();
    private String playerFirstName;
    private String playerLastName;
    private String playerId;
    private Uri playerImage;
    private int playerHighScore;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.chatList);
        dest.writeList(this.scoreList);
        dest.writeString(this.playerFirstName);
        dest.writeString(this.playerLastName);
        dest.writeString(this.playerId);
        dest.writeParcelable(this.playerImage, flags);
        dest.writeInt(this.playerHighScore);
    }

    public PlayerProfile() {
    }

    protected PlayerProfile(Parcel in) {
        this.chatList = new ArrayList<ChatModel>();
        in.readList(this.chatList, ChatModel.class.getClassLoader());
        this.scoreList = new ArrayList<ScoreBoardModel>();
        in.readList(this.scoreList, ScoreBoardModel.class.getClassLoader());
        this.playerFirstName = in.readString();
        this.playerLastName = in.readString();
        this.playerId = in.readString();
        this.playerImage = in.readParcelable(Uri.class.getClassLoader());
        this.playerHighScore = in.readInt();
    }

    public static final Parcelable.Creator<PlayerProfile> CREATOR = new Parcelable.Creator<PlayerProfile>() {
        @Override
        public PlayerProfile createFromParcel(Parcel source) {
            return new PlayerProfile(source);
        }

        @Override
        public PlayerProfile[] newArray(int size) {
            return new PlayerProfile[size];
        }
    };

    public void addChatList(ChatModel chatItem) {
        this.chatList.add(chatItem);
    }

    public void addScoreList(ScoreBoardModel scoreItem) {
        this.scoreList.add(scoreItem);
    }

    public List<ChatModel> getChatList() {
        return chatList;
    }

    public void setChatList(List<ChatModel> chatList) {
        this.chatList = chatList;
    }

    public List<ScoreBoardModel> getScoreList() {
        return scoreList;
    }

    public void setScoreList(List<ScoreBoardModel> scoreList) {
        this.scoreList = scoreList;
    }

    public String getPlayerFirstName() {
        return playerFirstName;
    }

    public void setPlayerFirstName(String playerFirstName) {
        this.playerFirstName = playerFirstName;
    }

    public String getPlayerLastName() {
        return playerLastName;
    }

    public void setPlayerLastName(String playerLastName) {
        this.playerLastName = playerLastName;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public Uri getPlayerImage() {
        return playerImage;
    }

    public void setPlayerImage(Uri playerImage) {
        this.playerImage = playerImage;
    }

    public int getPlayerHighScore() {
        return playerHighScore;
    }

    public void setPlayerHighScore(int playerHighScore) {
        this.playerHighScore = playerHighScore;
    }

    public static Creator<PlayerProfile> getCREATOR() {
        return CREATOR;
    }
}
