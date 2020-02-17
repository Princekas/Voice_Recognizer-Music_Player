package com.prince.artificialintelligence_musicplayer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.core.content.ContextCompat;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

public class PlayerActivity extends Main2Activity {


    private RelativeLayout parentRelativeLayout;
    private SpeechRecognizer speechRecognizer;
    private Intent speechRecognizerIntent;
    private String Keeper = "";
    private ImageView pauseplaybutton, previousbutton, nextbutton;
    private Button voiceenabledbutton;
    private TextView songNameTxt;
    private VideoView video;
    private RelativeLayout lowerrelativeLayout;
    private String mode = "OFF";
    private MediaPlayer mymediaPlayer;
    private int position;
    private ArrayList<File> mysongs;
    private String msongName;
    private TextView helpknowledge;
    private ListView msongList;
    private SeekBar seekBar;
    private Runnable runnable;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
//===================================================================================================================
        parentRelativeLayout = (RelativeLayout) findViewById(R.id.parentRelativeLayout);
        pauseplaybutton = findViewById(R.id.play_pause_btn);
        nextbutton = findViewById(R.id.next_btn);
        previousbutton = findViewById(R.id.previous_button);
        handler=new Handler();
        //-------------------------------------------------------------------
        seekBar=findViewById(R.id.seekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                 if(b){
                     mymediaPlayer.seekTo(i);
                 }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        //-------------------------------------------------------------------
//-------------------------------------------
        video = findViewById(R.id.videoview);
        String path = "android.resource://" + getPackageName() + "/" + R.raw.pleaseplay;
        video.setVideoURI(Uri.parse(path));

        video.start();

        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {   // this method is called to loop the video
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
           mediaPlayer.setLooping(true);            }
        });
//--------------------------------------------
        msongList = findViewById(R.id.songlist);
        mysongs=new ArrayList<>();
        lowerrelativeLayout = findViewById(R.id.lower);
        voiceenabledbutton = findViewById(R.id.voice_enabled_btn);
        songNameTxt = findViewById(R.id.songName);
        helpknowledge=findViewById(R.id.helpknowledge);
        seekBar=findViewById(R.id.seekBar);

        //================================================
// =================================================================
     if(mode.equals("ON"))
     {
         helpknowledge.setVisibility(View.VISIBLE);
     }
     else if (mode.equals("OFF"))
     {
         helpknowledge.setVisibility(View.INVISIBLE);
     }

//----------------------------------------------------------------------------------------------------------------------------



        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(PlayerActivity.this);     //SpeechRecognizer Intent is Created

        speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, Locale.getDefault());


//==================================================================================================================
        checkVoiceCommandPermisson();      // method called here
        validateRecieveValueAndStartPlaying();
        playNextSong();
        playPreviousSong();

//--------------------------------------------------------------------------------------------------------------------------



//========================================================================================================================
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {
                Toast.makeText(PlayerActivity.this, "Please Speak", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {
            }

            @Override
            public void onEndOfSpeech() {
            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {
                ArrayList<String> matchfound = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);


                if (matchfound != null) {
                    if (mode.equals("ON"))
                    {
                        lowerrelativeLayout.setVisibility(View.GONE);
                        Keeper = matchfound.get(0);


                        if (Keeper.equals("play")) {
                            pauseplaybutton.performClick();
                            Toast.makeText(PlayerActivity.this, "Sir , Command =" + Keeper, Toast.LENGTH_LONG).show();
                        } else if (Keeper.equals("wait")) {
                            pauseplaybutton.performClick();
                            Toast.makeText(PlayerActivity.this, "Sir , Command =" + Keeper, Toast.LENGTH_LONG).show();
                        } else if (Keeper.equals("next")) {
                            nextbutton.performClick();
                            Toast.makeText(PlayerActivity.this, "Sir , Command =" + Keeper, Toast.LENGTH_LONG).show();
                        } else if (Keeper.equals("back")) {
                            previousbutton.performClick();
                            Toast.makeText(PlayerActivity.this, "Sir , Command =" + Keeper, Toast.LENGTH_SHORT).show();
                        }

                    }
                    else if (mode.equals("OFF"))
                    {

                        speechRecognizer.stopListening();
                    }
                }
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });
//===================================================================================================================
        parentRelativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:        // when we keep pressing the screen.
                        speechRecognizer.startListening(speechRecognizerIntent);
                        Keeper = "";
                        break;
                    case MotionEvent.ACTION_UP:            //when we remove finger from the screen.
                        speechRecognizer.startListening(speechRecognizerIntent);
                        break;
                }
                return false;
            }
        });
//------------------------------------------------------------------------------------------------------------------------------------
        pauseplaybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playPausesong();
                if (mymediaPlayer.isPlaying()) {
                    pauseplaybutton.setImageResource(R.drawable.resume);
                    String path = "android.resource://" + getPackageName() + "/" + R.raw.pleaseplay;
                    video.setVideoURI(Uri.parse(path));


                    video.start();
                } else {
                    pauseplaybutton.setImageResource(R.drawable.play);

                    String path = "android.resource://" + getPackageName() + "/" + R.raw.donts;
                    video.setVideoURI(Uri.parse(path));
                    video.start();


                }

            }
        });

//-----------------------------------------------------------------------------------------------------------------------------------
        voiceenabledbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mode.equals("OFF")) {
                    mode = "ON";
                    voiceenabledbutton.setText("Voice Enabled Mode -ON");
                    lowerrelativeLayout.setVisibility(View.GONE);
                    helpknowledge.setVisibility(View.VISIBLE);
                } else {
                    mode = "OFF";
                    voiceenabledbutton.setText("Voice Enabled Mode -OFF");

                    lowerrelativeLayout.setVisibility(View.VISIBLE);
                    helpknowledge.setVisibility(View.INVISIBLE);
                }
            }
        });


    }

    //----------------------------------------------------------------------------------------------------------------------------
    private void validateRecieveValueAndStartPlaying() {
        if (mymediaPlayer != null) {
            mymediaPlayer.stop();
            mymediaPlayer.release();
        }
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        mysongs = (ArrayList) bundle.getParcelableArrayList("song");
        msongName = mysongs.get(position).getName();
        String songName = intent.getStringExtra("name");

        songNameTxt.setText(songName);
        songNameTxt.setSelected(true);

        position = bundle.getInt("Position", 0);
        Uri uri = Uri.parse(mysongs.get(position).toString());

        mymediaPlayer = MediaPlayer.create(PlayerActivity.this, uri);
        mymediaPlayer.start();


    }

//----------------------------------------------------------------------------------------------------------------------------------


    private void checkVoiceCommandPermisson() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(ContextCompat.checkSelfPermission(PlayerActivity.this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                finish();
            }

        }
    }

    //----------------------------------------------------------------------------------------------------------------------------
   public void playPausesong() {
        if (mymediaPlayer.isPlaying()) {

            mymediaPlayer.pause();
        } else {
            mymediaPlayer.start();

        }
//--------- ---------------------------------------------------------------------------------------------------------------------------   //-----------------------------------------------------------------------------------------------------------------------------
    }

    private void playNextSong() {
        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pauseplaybutton.setImageResource(R.drawable.play);
                mymediaPlayer.pause();
                mymediaPlayer.stop();
                mymediaPlayer.release();
                position = ((position + 1) % mysongs.size());
                Uri uri = Uri.parse(mysongs.get(position + 1).toString());

                mymediaPlayer = MediaPlayer.create(PlayerActivity.this, uri);
                msongName = mysongs.get(position + 1).toString();
                songNameTxt.setText(msongName);
                mymediaPlayer.start();


                if (mymediaPlayer.isPlaying()) {
                    pauseplaybutton.setImageResource(R.drawable.resume);
                    String path = "android.resource://" + getPackageName() + "/" + R.raw.pleaseplay;
                    video.setVideoURI(Uri.parse(path));

                    video.start();
                } else {
                    pauseplaybutton.setImageResource(R.drawable.play);


                }

            }


        });
    }

    //--------------------------------------------------------------------------------------------------------------------------------
    private void playPreviousSong() {

        previousbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                pauseplaybutton.setImageResource(R.drawable.resume);
                mymediaPlayer.pause();
                mymediaPlayer.stop();
                mymediaPlayer.release();
                position = ((position - 1) % mysongs.size());
                Uri uri = Uri.parse(mysongs.get(position - 1).toString());

                mymediaPlayer = MediaPlayer.create(PlayerActivity.this, uri);
                msongName = mysongs.get(position - 1).toString();
                songNameTxt.setText(msongName);
                mymediaPlayer.start();


                if (mymediaPlayer.isPlaying()) {
                    pauseplaybutton.setImageResource(R.drawable.resume);
                    String path = "android.resource://" + getPackageName() + "/" + R.raw.pleaseplay;
                    video.setVideoURI(Uri.parse(path));

                    video.start();
                } else {
                    pauseplaybutton.setImageResource(R.drawable.play);
                    String path = "android.resource://" + getPackageName() + "/" + R.raw.donts;
                    video.setVideoURI(Uri.parse(path));

                    video.start();

                }

            }
        });
    }
 //---------------------------------------------------------------------------------------------------------------------------------
private void changeSeekbar(){
        if(mymediaPlayer.isPlaying()){
            runnable =new Runnable() {
                @Override
                public void run() {
                    changeSeekbar();
                }
            };
            handler.postDelayed(runnable,1000);
        }
}
//-----------------------------------------------------------------------------------------------------------------------------------

}
