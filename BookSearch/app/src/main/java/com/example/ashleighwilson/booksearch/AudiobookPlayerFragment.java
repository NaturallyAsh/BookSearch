package com.example.ashleighwilson.booksearch;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ashleighwilson.booksearch.data.BookDbHelper;
import com.example.ashleighwilson.booksearch.models.AudioBook;

import java.io.IOException;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AudiobookPlayerFragment extends Fragment {

    private static final String TAG = AudiobookPlayerFragment.class.getSimpleName();

    //sygi/AudiobookPlayer

    private AudiobookActivity audiobookActivity;
    public static final String PLAYER_ARG = "player_arg";
    private AudioBook audioBook;
    @BindView(R.id.audio_player_book_cover_IV)
    ImageView bookCover;
    @BindView(R.id.audio_player_book_title_text_view)
    TextView title;
    @BindView(R.id.audio_player_seekbar)
    SeekBar seekBar;
    @BindView(R.id.audio_player_start_text_view)
    TextView startTV;
    @BindView(R.id.audio_player_duration_text_view)
    TextView durationTV;
    @BindView(R.id.audio_player_forward_IV)
    ImageView forwardIV;
    @BindView(R.id.audio_player_rewind_IV)
    ImageView rewindIV;
    @BindView(R.id.audio_player_play_IV)
    ImageView playIV;
    @BindView(R.id.audio_player_stop_IV)
    ImageView stopIV;
    @BindView(R.id.audio_player_library_BT)
    Button libraryBT;
    private MediaPlayer mediaPlayer;
    private Handler handler;
    private boolean isPlaying = false;
    private boolean data_set = false;
    private boolean reverse_counter = false;
    private int currentPosition = 0;
    private BookDbHelper dbHelper;


    public AudiobookPlayerFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        audiobookActivity = (AudiobookActivity) getActivity();
        dbHelper = BookDbHelper.getInstance();

        Bundle bundle = getArguments();
        if (bundle.containsKey(PLAYER_ARG)) {
            audioBook = bundle.getParcelable(PLAYER_ARG);
            Log.i(TAG, "arg item: " + audioBook.getmName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.audiobook_player, container,
                false);
        ButterKnife.bind(this, rootView);

        audiobookActivity.getSupportActionBar().setTitle("Now Playing");
        audiobookActivity.getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });

        initMediaPlayer();


        return rootView;
    }

    private void goHome() {
        if (audiobookActivity != null && audiobookActivity.getSupportActionBar() != null) {
            if (currentPosition > 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Save your current position?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i(TAG, "position: " + currentPosition);
                        audioBook.setmCurrentPosition(currentPosition);
                        dbHelper.updateAudiobook(audioBook);
                        //dialog.dismiss();
                        audiobookActivity.switchToList();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if (dialog != null) {
                            //dialog.dismiss();
                            audiobookActivity.switchToList();
                        }
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            } else {
                audiobookActivity.switchToList();
            }
        }
    }

    private void initMediaPlayer() {
        handler = new Handler();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build());

        if (audioBook != null) {
            init();
        }
        initButtons();

    }

    @SuppressLint("SetTextI18n")
    private void init() {
        title.setText(audioBook.getmName());
        loadCoverImage(audioBook.getmImage());

        durationTV.setText("00:00:00");
        startTV.setText("00:00:00");

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                int length = mediaPlayer.getDuration() / 1000;
                seekBar.setProgress(length);
                durationTV.setText(getTime(length));
                stopPlayback();
            }
        });
        mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(MediaPlayer mp) {
                //Log.i(TAG, "seek complete called");
                int position = audioBook.getmCurrentPosition() / 1000;
                seekBar.setProgress(position);
                durationTV.setText(getTime(position));
                startTV.setText(getReverseTime(position));
            }
        });
        readUriFile();
        setThread();
        if (audioBook.getmCurrentPosition() > 0) {
            mediaPlayer.seekTo(audioBook.getmCurrentPosition(), MediaPlayer.SEEK_CLOSEST);

        }
    }

    private void loadCoverImage(String image) {
        if (image != null) {
            Glide.with(getContext())
                    .load(image)
                    .into(bookCover);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initButtons() {
        rewindIV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                seek(-10);
                return false;
            }
        });
        forwardIV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                seek(10);
                return false;
            }
        });
        playIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                    if (!isPlaying) {
                        startPlayback();

                    }
                    playIV.setImageResource(R.drawable.ic_pause_black_24dp);
                } else if (mediaPlayer != null && mediaPlayer.isPlaying()){
                    pausePlayback();
                    currentPosition = mediaPlayer.getCurrentPosition();
                } else if (mediaPlayer == null) {
                    Log.i(TAG, "read file called");
                    initMediaPlayer();
                }
            }
        });
        stopIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlayback();
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && data_set && fromUser) {
                    mediaPlayer.seekTo(progress * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private String getTime(int current) {
        if (reverse_counter) {
            current = mediaPlayer.getDuration() / 1000 - current;
        }
        int minutes = current / 60;
        int hours = minutes / 60;
        minutes %= 60;
        int seconds = current % 60;
        String current_time = String.format("%d:%02d:%02d", hours, minutes, seconds);
        if (reverse_counter) {
            return "-" + current_time;
        } else {
            return " " + current_time;
        }
    }

    private String getReverseTime(int current) {
        current = mediaPlayer.getDuration() / 1000 - current;

        int minutes = current / 60;
        int hours = minutes / 60;
        minutes %= 60;
        int seconds = current % 60;
        String current_time = String.format("%d:%02d:%02d", hours, minutes, seconds);

        return " " + current_time;
    }

    private void readUriFile() {
        String path = audioBook.getmFilePath();
        if (path != null) {
            Uri uri = Uri.parse(path);
            setFileToPlayBT(uri);
        }
    }

    private void setFileToPlayBT(Uri uri) {
        if (audioBook != null) {
            try {
                //Log.i(TAG, "path: " + uri);
                mediaPlayer.setDataSource(uri.getPath());
                mediaPlayer.prepare();
                data_set = true;
                seekBar.setMax(mediaPlayer.getDuration() / 1000);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setThread() {
        audiobookActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (data_set) {
                    int currentPosition = mediaPlayer.getCurrentPosition() / 1000;
                    seekBar.setProgress(currentPosition);
                    durationTV.setText(getTime(currentPosition));
                    startTV.setText(getReverseTime(currentPosition));
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    private void seek(int diff) {
        if (!data_set) {
            return;
        }
        int current = mediaPlayer.getCurrentPosition();
        mediaPlayer.seekTo(current + 1000 * diff);
    }

    private void pausePlayback() {
        if (!mediaPlayer.isPlaying()) {
            return;
        }
        mediaPlayer.pause();
        playIV.setImageResource(R.drawable.ic_play_arrow_black_24dp);
        isPlaying = false;
        Log.i(TAG, "media player paused");
    }

    private void startPlayback() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            Log.i(TAG, "media player start");
            mediaPlayer.start();
        } else if (data_set) {
            Log.i(TAG, "media player read file");
            readUriFile();
        }

        isPlaying = true;
    }

    @SuppressLint("SetTextI18n")
    private void stopPlayback() {
        Log.i(TAG, "media player stopped");
        if (mediaPlayer != null) {
            if (mediaPlayer.getCurrentPosition() != 0) {
                currentPosition = mediaPlayer.getCurrentPosition();
            }
            durationTV.setText("00:00:00");
            startTV.setText("00:00:00");
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
            playIV.setImageResource(R.drawable.ic_play_arrow_black_24dp);
        }
        isPlaying = false;
        data_set = false;
    }
}
