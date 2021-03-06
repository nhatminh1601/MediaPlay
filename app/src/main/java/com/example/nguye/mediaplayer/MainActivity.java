package com.example.nguye.mediaplayer;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageButton btnnext, btnprevi, btnplay;
    SeekBar sksong;
    TextView txttitle,txttimebegin, txttimetong;
    Intent intents;
    GestureDetector gestureDetector;
   public ArrayList<ModelSong> mdata;
    public static int Position=0;
    public static int flagdd=0;
    public  static MediaPlayer mediaPlayer;
    Animation animation,animation1;
    ImageView disk;
    RelativeLayout main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Process();
        mdata=GetAllSong();
        Action();
        ProcessSeebar();
        animation= AnimationUtils.loadAnimation(this,R.anim.disk_rotate);
        animation1= AnimationUtils.loadAnimation(this,R.anim.stop);
        intents=getIntent();

        if(intents.getExtras()!=null){
            Position=intents.getExtras().getInt("position");
            khoitao();
            mediaPlayer.start();
            disk.startAnimation(animation);
            btnplay.setImageResource(R.drawable.ic_pause_black_24dp);

        }
        gestureDetector=new GestureDetector(this,new MyGesture());
        main=findViewById(R.id.mains);
        main.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });


    }
    class MyGesture extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(e1.getX()-e2.getX()>100 && Math.abs(velocityX)>100){
                Danhsachbh();
            }
            Log.d("test","e1: "+e1.getX()+ "  e2: "+ e2.getX() +" giatri: "+ velocityX);
            return super.onFling(e1, e2, velocityX, velocityY);
        }
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

    public ArrayList<ModelSong> GetAllSong() {
        ArrayList<ModelSong> mdata=new ArrayList<>();
        mdata.add(new ModelSong("Cùng anh","Unknow","pop",R.raw.cunganh));
        mdata.add(new ModelSong("Đừng xin lỗi nữa","Unknow","pop",R.raw.dungxinloinua));
        mdata.add(new ModelSong("Em mới là người anh yêu","Unknow","pop",R.raw.emmoilanguoiyeuanh));
        mdata.add(new ModelSong("Over You","Unknow","pop",R.raw.overyou));
        return mdata;
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
            case R.id.dsbh:
                Danhsachbh();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void Danhsachbh() {
        Intent intent=new Intent(this,ListSong.class);
        startActivity(intent);
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
