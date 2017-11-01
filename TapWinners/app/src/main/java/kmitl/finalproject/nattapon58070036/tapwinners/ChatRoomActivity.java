package kmitl.finalproject.nattapon58070036.tapwinners;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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

import kmitl.finalproject.nattapon58070036.tapwinners.adapter.ChatAdapter;
import kmitl.finalproject.nattapon58070036.tapwinners.model.ChatModel;
import kmitl.finalproject.nattapon58070036.tapwinners.model.PlayerProfile;


public class ChatRoomActivity extends AppCompatActivity implements View.OnClickListener {
    private String username;
    private DatabaseReference child;
    private EditText et_message;
    private Button btn_send;
    private String key;
    private String child_username;
    private String child_value;
    private Uri playerPic;
    private String child_profilePic;
    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private PlayerProfile playerProfile;
    private ChatModel chatItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_chat_room);
        initInstances();
        onRequest_userName();
        setupRecyclerView();
        displayChatView();
        Log.i("eiei", "onCreate: ");
    }

    private void initInstances() {
        recyclerView = findViewById(R.id.listView);
        et_message = findViewById(R.id.editText);
        btn_send = findViewById(R.id.sendButton);
        btn_send.setOnClickListener(this);
        playerProfile = getIntent().getParcelableExtra("PlayerProfile");
        child = FirebaseDatabase.getInstance().getReference().child("chat");
        Toast.makeText(ChatRoomActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
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

    private void displayChatView(){
        child.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                onGetChild(dataSnapshot);
                Log.i("eeeee", "onChildAdded: ");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                onGetChild(dataSnapshot);
                Log.i("ddddd", "onChildChanged: ");

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
                Toast.makeText(ChatRoomActivity.this, "onCancelled", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void onGetChild(DataSnapshot dataSnapshot) {
        Iterator i = dataSnapshot.getChildren().iterator();
        while (i.hasNext()) {
            child_value = (String) ((DataSnapshot) i.next()).getValue();
            child_profilePic = (String) ((DataSnapshot) i.next()).getValue();
            child_username = (String) ((DataSnapshot) i.next()).getValue();
            chatItem = new ChatModel();
            playerPic = Uri.parse(child_profilePic);
            chatItem.setImgUri(playerPic);
            chatItem.setUsername(child_username);
            chatItem.setMessage(child_value);
            playerProfile.addChatList(chatItem);
        }
        int position = chatAdapter.getItemCount() - 1;
        if(position >= 0){
            recyclerView.smoothScrollToPosition(position);
        }
        chatAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.sendButton) {
            Map<String, Object> map = new HashMap<>();
            key = child.push().getKey();
            map.put(key, "");
            child.updateChildren(map);
            DatabaseReference message_key = child.child(key);
            Map<String, Object> map2 = new HashMap<>();
            playerPic = playerProfile.getPlayerImage();
            String pic = playerPic.toString();
            map2.put("pic", pic);
            map2.put("username", username);
            map2.put("msg", et_message.getText().toString());
            message_key.updateChildren(map2);
            Toast.makeText(ChatRoomActivity.this, "send", Toast.LENGTH_SHORT).show();
            et_message.setText("");
        }
    }
}
