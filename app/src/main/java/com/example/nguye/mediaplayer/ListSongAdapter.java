package com.example.nguye.mediaplayer;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.nguye.mediaplayer.MainActivity.flagdd;
import static com.example.nguye.mediaplayer.MainActivity.mediaPlayer;

public class ListSongAdapter extends RecyclerView.Adapter<ListSongAdapter.SongList> {
    Context context;
    ArrayList<ModelSong> mData;

    public ListSongAdapter(Context context, ArrayList<ModelSong> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public SongList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        view=layoutInflater.inflate(R.layout.carview_item_bh,parent,false);
        return new SongList(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SongList holder, final int position) {
        holder.imgsong.setImageResource(R.drawable.h);
        holder.title.setText(mData.get(position).getTitle());
        holder.singer.setText(mData.get(position).getSinger());

        holder.carviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,MainActivity.class);
                intent.putExtra("position",position);
                mediaPlayer.stop();
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class SongList extends RecyclerView.ViewHolder {
        ImageView imgsong;
        TextView title, singer;
        CardView carviews;
       public SongList(View itemView) {
           super(itemView);
           imgsong=itemView.findViewById(R.id.imgsong);
           title=itemView.findViewById(R.id.namesong);
           singer=itemView.findViewById(R.id.singer);
           carviews=itemView.findViewById(R.id.cvitem);
       }
   }
}
