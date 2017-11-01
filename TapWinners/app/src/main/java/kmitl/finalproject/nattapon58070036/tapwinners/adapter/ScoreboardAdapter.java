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
import kmitl.finalproject.nattapon58070036.tapwinners.adapter.holder.ScoreboardHolder;
import kmitl.finalproject.nattapon58070036.tapwinners.model.ScoreBoardModel;

/**
 * Created by SMART on 1/11/2560.
 */

public class ScoreboardAdapter extends RecyclerView.Adapter<ScoreboardHolder> {
    private Context context;
    private List<ScoreBoardModel> data;

    public ScoreboardAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }
    public void setData(List<ScoreBoardModel> data) {
        this.data = data;
    }

    @Override
    public ScoreboardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.scorelist_item, null, false);
        ScoreboardHolder holder = new ScoreboardHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(ScoreboardHolder holder, int position) {
        holder.rankText.setText(Integer.toString(position+1));
        Glide.with(context).load(data.get(position).getImgUri()).into(holder.imageView);
        holder.playerNameText.setText(data.get(position).getPlayerName());
        holder.highScoreText.setText(Integer.toString(data.get(position).getScore()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
