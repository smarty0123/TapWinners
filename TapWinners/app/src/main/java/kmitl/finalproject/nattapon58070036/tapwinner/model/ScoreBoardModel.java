package kmitl.finalproject.nattapon58070036.tapwinner.model;

import android.net.Uri;

/**
 * Created by SMART on 1/11/2560.
 */

public class ScoreBoardModel {
    private String playerName;
    private int Score;
    private Uri imgUri;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    public Uri getImgUri() {
        return imgUri;
    }

    public void setImgUri(Uri imgUri) {
        this.imgUri = imgUri;
    }
}
