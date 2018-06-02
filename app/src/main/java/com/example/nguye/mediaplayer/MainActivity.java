package com.example.nguye.mediaplayer;

import android.Manifest;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageButton btnnext, btnprevi, btnplay;
    SeekBar sksong;
    TextView txttitle,txttimebegin, txttimetong;
    ArrayList<ModelSong> mdata;
    int Position=0;
    MediaPlayer mediaPlayer;
    Animation animation,animation1;
    ImageView disk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Process();
        GetAllSong();
        Action();
        ProcessSeebar();
        animation= AnimationUtils.loadAnimation(this,R.anim.disk_rotate);
        animation1= AnimationUtils.loadAnimation(this,R.anim.stop);



    }

    private void ProcessSeebar() {
        sksong.setMax(mediaPlayer.getDuration());
        sksong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(sksong.getProgress());
            }
        });
    }

    private void GetAllSong() {
        mdata=new ArrayList<>();
        mdata.add(new ModelSong("Cùng anh","Unknow","pop",R.raw.cunganh));
        mdata.add(new ModelSong("Đừng xin lỗi nữa","Unknow","pop",R.raw.dungxinloinua));
        mdata.add(new ModelSong("Em mới là người anh yêu","Unknow","pop",R.raw.emmoilanguoiyeuanh));
        mdata.add(new ModelSong("Over You","Unknow","pop",R.raw.overyou));

    }

    private void Action() {
        // process play
        khoitao();
        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    btnplay.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    mediaPlayer.pause();
                    disk.startAnimation(animation1);
                }
                else {
                    mediaPlayer.start();
                    btnplay.setImageResource(R.drawable.ic_pause_black_24dp);
                    disk.startAnimation(animation);

                }

            }
        });
        // process next
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Position++;
                if(Position>(mdata.size()-1)){
                    Position=0;
                }
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
                else{
                    btnplay.setImageResource(R.drawable.ic_pause_black_24dp);
                }

                khoitao();
                mediaPlayer.start();
                disk.startAnimation(animation);
            }
        });
        // process previ
        btnprevi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Position--;
                if(Position<0){
                    Position=mdata.size()-1;
                }
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
                else{
                    btnplay.setImageResource(R.drawable.ic_pause_black_24dp);
                }
                khoitao();
                mediaPlayer.start();
                disk.startAnimation(animation);
            }
        });

    }

    private void Process() {
        toolbar=findViewById(R.id.my_toobar);
        toolbar.setTitle("Music");
        setSupportActionBar(toolbar);
        btnnext=findViewById(R.id.next);
        btnplay=findViewById(R.id.play);
        btnprevi=findViewById(R.id.previ);
        sksong=findViewById(R.id.seekBar);
        txttitle=findViewById(R.id.tenbh);
        txttimebegin=findViewById(R.id.timebegin);
        txttimetong=findViewById(R.id.timetong);
        disk=findViewById(R.id.disk);
    }
    public void stop(){
        mediaPlayer.stop();
        mediaPlayer.release();
        disk.startAnimation(animation1);
        btnplay.setImageResource(R.drawable.ic_play_arrow_black_24dp);

        khoitao();
    }

    private void khoitao() {
        mediaPlayer=MediaPlayer.create(MainActivity.this,mdata.get(Position).getUrl());
        txttitle.setText(mdata.get(Position).getTitle());
        SimpleDateFormat formatgio=new SimpleDateFormat("mm:ss");
        txttimetong.setText(formatgio.format(mediaPlayer.getDuration()));
        Updatetime();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nguon:
                stop();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void Updatetime(){
        final Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat formatgio=new SimpleDateFormat("mm:ss");
                txttimebegin.setText(formatgio.format(mediaPlayer.getCurrentPosition()));
                sksong.setProgress(mediaPlayer.getCurrentPosition());
                handler.postDelayed(this,100);
                // kiểm tra thời gian bài hat kết thuc ---> chuyển bài
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        Position++;
                        if(Position>(mdata.size()-1)){
                            Position=0;
                        }
                        if(mediaPlayer.isPlaying()){
                            mediaPlayer.stop();
                            mediaPlayer.release();
                        }
                        else{
                            btnplay.setImageResource(R.drawable.ic_pause_black_24dp);
                        }

                        khoitao();
                        mediaPlayer.start();
                    }
                });
            }
        },100);
    }
}
