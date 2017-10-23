package kmitl.finalproject.nattapon58070036.tapwinners.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SMART on 23/10/2560.
 */

public class PlayerProfile {
    private List<ChatModel> chatList = new ArrayList<>();
    private String playerName;

    public List<ChatModel> getChatList() {
        return chatList;
    }

    public void addChatList(ChatModel chatItem) {
        this.chatList.add(chatItem);
    }

    public void setChatList(List<ChatModel> chatList) {
        this.chatList = chatList;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
