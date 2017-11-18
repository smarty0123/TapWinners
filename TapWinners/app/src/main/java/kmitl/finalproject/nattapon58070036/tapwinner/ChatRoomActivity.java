package kmitl.finalproject.nattapon58070036.tapwinner;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kmitl.finalproject.nattapon58070036.tapwinner.adapter.ChatAdapter;
import kmitl.finalproject.nattapon58070036.tapwinner.model.ChatModel;
import kmitl.finalproject.nattapon58070036.tapwinner.model.PlayerProfile;


public class ChatRoomActivity extends AppCompatActivity {
    @BindView(R.id.et_message)
    EditText et_message;
    @BindView(R.id.sendButton)
    Button sendButton;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private PlayerProfile playerProfile;

    private String username;
    private DatabaseReference child;
    private Uri playerPic;
    private ChatAdapter chatAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_chat_room);
        initInstances();
        onRequest_userName();
        setupRecyclerView();
        displayChatView();
        Toast.makeText(ChatRoomActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
    }

    private void initInstances() {
        ButterKnife.bind(this);
        playerProfile = getIntent().getParcelableExtra("PlayerProfile");
        child = FirebaseDatabase.getInstance().getReference().child("chat");
    }

    private void setupRecyclerView() {
        chatAdapter = new ChatAdapter(this);
        chatAdapter.setData(playerProfile.getChatList());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(chatAdapter);
    }

    private void onRequest_userName() {
        username = playerProfile.getPlayerFirstName();
    }

    private void displayChatView() {
        child.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                onGetChild(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                onGetChild(dataSnapshot);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ChatRoomActivity.this, "onCancelled", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void onGetChild(DataSnapshot dataSnapshot) {
        Iterator i = dataSnapshot.getChildren().iterator();
        while (i.hasNext()) {
            String child_value = (String) ((DataSnapshot) i.next()).getValue();
            String child_profilePic = (String) ((DataSnapshot) i.next()).getValue();
            String child_username = (String) ((DataSnapshot) i.next()).getValue();
            ChatModel chatItem = new ChatModel();
            playerPic = Uri.parse(child_profilePic);
            chatItem.setImgUri(playerPic);
            chatItem.setUsername(child_username);
            chatItem.setMessage(child_value);
            playerProfile.addChatList(chatItem);
        }
        int position = chatAdapter.getItemCount() - 1;
        if (position >= 0) {
            recyclerView.smoothScrollToPosition(position);
        }
        chatAdapter.notifyDataSetChanged();
    }


    @OnClick(R.id.sendButton)
    public void onViewClicked() {
        String message = et_message.getText().toString().trim();
        if (!TextUtils.isEmpty(message)) {
            Map<String, Object> map = new HashMap<>();
            String key = child.push().getKey();
            map.put(key, "");
            child.updateChildren(map);
            DatabaseReference message_key = child.child(key);
            Map<String, Object> map2 = new HashMap<>();
            playerPic = playerProfile.getPlayerImage();
            String pic = playerPic.toString();
            map2.put("pic", pic);
            map2.put("username", username);
            map2.put("msg", message);
            message_key.updateChildren(map2);
            Toast.makeText(ChatRoomActivity.this, "send", Toast.LENGTH_SHORT).show();
            et_message.setText("");
        }
    }
}
