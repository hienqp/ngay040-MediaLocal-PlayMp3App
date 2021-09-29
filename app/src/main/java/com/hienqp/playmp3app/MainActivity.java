package com.hienqp.playmp3app;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView textViewDisplaySongName;
    TextView textViewCurrentSongTime;
    TextView textViewTotalSongTime;
    SeekBar seekBarControlSongTime;
    ImageButton imageButtonRewind;
    ImageButton imageButtonStop;
    ImageButton imageButtonPlay;
    ImageButton imageButtonNext;
    ImageView imageViewCdMusic;

    ArrayList<Song> songArrayList;

    int position = 0;

    MediaPlayer mediaPlayer;

    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        capturViewObjectFromLayout();

        addSong();
        createMediaPlayer();

        animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_rotate);

        imageButtonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                imageButtonPlay.setImageResource(R.drawable.play);
                createMediaPlayer();
            }
        });
        imageButtonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    // nếu MediaPlayer đang play -> pause MediaPlayer -> đổi imageButton sang play
                    mediaPlayer.pause();
                    imageButtonPlay.setImageResource(R.drawable.play);
                } else {
                    // nếu MediaPlayer đang pause -> play MediaPlayer -> đổi imageButton sang pause
                    mediaPlayer.start();
                    imageButtonPlay.setImageResource(R.drawable.pause);
                }
                imageViewCdMusic.startAnimation(animation);
            }
        });

        imageButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position++;
                if (position >= songArrayList.size()) {
                    position = 0;
                }
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                createMediaPlayer();
                mediaPlayer.start();
                imageButtonPlay.setImageResource(R.drawable.pause);
            }
        });

        imageButtonRewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position--;
                if (position < 0) {
                    position = (songArrayList.size() - 1);
                }
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                createMediaPlayer();
                mediaPlayer.start();
                imageButtonPlay.setImageResource(R.drawable.pause);
            }
        });

        seekBarControlSongTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBarControlSongTime.getProgress());
            }
        });
    }

    private void updateSongTime() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                int currentPositionSeekbar = mediaPlayer.getCurrentPosition();
                String currentTime = simpleDateFormat.format(currentPositionSeekbar);

                textViewCurrentSongTime.setText(currentTime);
                seekBarControlSongTime.setProgress(currentPositionSeekbar);

                handler.postDelayed(this, 500);

                // kiểm tra thời gian bài hát -> nếu kết thúc -> next
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        position++;
                        if (position >= songArrayList.size()) {
                            position = 0;
                        }
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.stop();
                        }
                        createMediaPlayer();
                        mediaPlayer.start();
                        imageButtonPlay.setImageResource(R.drawable.pause);
                    }
                });
            }
        }, 100);
    }

    private void setTotalSongTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        textViewTotalSongTime.setText(simpleDateFormat.format(mediaPlayer.getDuration()));

        // gán max seekBarControlSongTime = mediaPlayer.getDuration()
        seekBarControlSongTime.setMax(mediaPlayer.getDuration());

        updateSongTime();
    }

    private void createMediaPlayer() {
        textViewDisplaySongName.setText(songArrayList.get(position).getSongName());
        mediaPlayer = MediaPlayer.create(MainActivity.this, songArrayList.get(position).getSongId());
        setTotalSongTime();
    }

    private void addSong() {
        songArrayList = new ArrayList<>();
        songArrayList.add(new Song("Anh Không Tha Thứ Remix", R.raw.anh_khong_tha_thu_remix));
        songArrayList.add(new Song("Bánh Mì Không Remix", R.raw.banh_mi_khong_remix));
        songArrayList.add(new Song("Cafe Không Đường Remix", R.raw.cafe_khong_duong_remix));
        songArrayList.add(new Song("Cô Độc Vương Remix", R.raw.co_doc_vuong_remix));
        songArrayList.add(new Song("Cô Đơn Dành Cho Ai Remix", R.raw.co_don_danh_cho_ai_remix));
        songArrayList.add(new Song("Hẹn Em Kiếp Sau Remix", R.raw.hen_em_kiep_sau_remix));
        songArrayList.add(new Song("Hóa Tương Tư Remix", R.raw.hoa_tuong_tu_remix));
        songArrayList.add(new Song("Kẹo Bông Gòn Remix", R.raw.keo_bong_gon_remix));
        songArrayList.add(new Song("Phố Đã Lên Đèn Remix", R.raw.pho_da_len_den_remix));
        songArrayList.add(new Song("Sao Ta Ngược Lối Remix", R.raw.sao_ta_nguoc_loi_remix));
        songArrayList.add(new Song("Tình Bạn Diệu Kỳ Remix", R.raw.tinh_ban_dieu_ky_remix));
        songArrayList.add(new Song("Trên Tình Bạn Dưới Tình Yêu", R.raw.tren_tinh_ban_duoi_tinh_yeu));
        songArrayList.add(new Song("Tướng Quân Remix", R.raw.tuong_quan_remix));

    }

    private void capturViewObjectFromLayout() {
        textViewDisplaySongName = (TextView) findViewById(R.id.textView_display_song_name);
        textViewCurrentSongTime = (TextView) findViewById(R.id.textView_current_song_time);
        textViewTotalSongTime = (TextView) findViewById(R.id.textView_total_song_time);
        seekBarControlSongTime = (SeekBar) findViewById(R.id.seekBar_control_time);
        imageButtonRewind = (ImageButton) findViewById(R.id.imageButton_rewind);
        imageButtonStop = (ImageButton) findViewById(R.id.imageButton_stop);
        imageButtonPlay = (ImageButton) findViewById(R.id.imageButton_play);
        imageButtonNext = (ImageButton) findViewById(R.id.imageButton_next);
        imageViewCdMusic = (ImageView) findViewById(R.id.imageView_cd_music);
    }
}