package kmitl.finalproject.nattapon58070036.tapwinners.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by SMART on 23/10/2560.
 */
class Holder extends RecyclerView.ViewHolder {
    /*ImageView image;
    TextView textLike;
    TextView textComment;*/

    public Holder(View itemView) {
        super(itemView);
        /*image = itemView.findViewById(R.id.image);
        textLike = itemView.findViewById(R.id.textLike);
        textComment = itemView.findViewById(R.id.textComment);*/
    }
}
public class ChatAdapter extends RecyclerView.Adapter<Holder>{
    private final Context context;

    public ChatAdapter(Context context) {
        this.context = context;
        //data = new ArrayList<>();
    }
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
