package com.example.nguye.mediaplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;

import java.util.ArrayList;

public class ListSong extends AppCompatActivity {

    ListSongAdapter listadapter;
    RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_song);
        MainActivity activity=new MainActivity();
        ArrayList<ModelSong> data=new ArrayList<>();
        data=activity.GetAllSong();
        listadapter=new ListSongAdapter(this,data);
        rv=findViewById(R.id.rvbh);
        rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rv.setAdapter(listadapter);



    }


}
