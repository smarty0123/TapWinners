package kmitl.finalproject.nattapon58070036.tapwinners.model;

import android.net.Uri;

/**
 * Created by SMART on 23/10/2560.
 */

public class ChatModel {
    private String username;
    private String message;
    private Uri imgUri;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Uri getImgUri() {
        return imgUri;
    }

    public void setImgUri(Uri imgUri) {
        this.imgUri = imgUri;
    }
}
