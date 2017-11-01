package kmitl.finalproject.nattapon58070036.tapwinners.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import kmitl.finalproject.nattapon58070036.tapwinners.R;

/**
 * Created by SMART on 1/11/2560.
 */

public class ScoreboardHolder extends RecyclerView.ViewHolder {
    public TextView rankText;
    public ImageView imageView;
    public TextView playerNameText;
    public TextView highScoreText;
    public ScoreboardHolder(View itemView) {
        super(itemView);
        rankText = itemView.findViewById(R.id.rankText);
        imageView = itemView.findViewById(R.id.imageView);
        playerNameText = itemView.findViewById(R.id.playerNameText);
        highScoreText = itemView.findViewById(R.id.highScoreText);
    }
}
