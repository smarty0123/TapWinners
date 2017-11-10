package kmitl.finalproject.nattapon58070036.tapwinner.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import kmitl.finalproject.nattapon58070036.tapwinner.R;

/**
 * Created by SMART on 23/10/2560.
 */

public class ChatHolder extends RecyclerView.ViewHolder {
    public ImageView chatProfileImage;
    public TextView tvPlayerName;
    public TextView tvMessage;
    public ChatHolder(View itemView) {
        super(itemView);
        chatProfileImage = itemView.findViewById(R.id.chatProfileImage);
        tvPlayerName = itemView.findViewById(R.id.tvPlayerName);
        tvMessage = itemView.findViewById(R.id.tvMessage);
    }
}
