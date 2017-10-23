package kmitl.finalproject.nattapon58070036.tapwinners.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import kmitl.finalproject.nattapon58070036.tapwinners.R;
import kmitl.finalproject.nattapon58070036.tapwinners.adapter.holder.ChatHolder;
import kmitl.finalproject.nattapon58070036.tapwinners.model.ChatModel;

/**
 * Created by SMART on 23/10/2560.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatHolder>{
    private Context context;
    private List<ChatModel> data;

    public ChatAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }

    public void setData(List<ChatModel> data) {
        this.data = data;
    }

    @Override
    public ChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.chatlist_item, null, false);
        ChatHolder holder = new ChatHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ChatHolder holder, int position) {
        Glide.with(context).load(data.get(position).getImgUri()).into(holder.chatProfileImage);
        holder.tvPlayerName.setText(data.get(position).getUsername());
        holder.tvMessage.setText(data.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
