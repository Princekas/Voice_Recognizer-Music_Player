//this activity read all the storage of the phone devices


package com.prince.artificialintelligence_musicplayer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity  {
private String[] itemAll;
private ListView msongList;
private MediaPlayer mymediaPlayer;
    private ImageView pauseplaybutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
     //--------------------------------------------

        mymediaPlayer=new MediaPlayer();

        msongList = findViewById(R.id.songlist);

pauseplaybutton=(ImageView)findViewById(R.id.play_pause_btn);
        checkAndRequestPermissions();
        displayAudioSongName();



    }


//----------------------------------------------------------------------------------------------------------------------------

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;


    private  boolean checkAndRequestPermissions() {
        int permissionRecord = ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO);
        int ReadPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (permissionRecord != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECORD_AUDIO);
        }
        if (ReadPermission != PackageManager.PERMISSION_GRANTED) {
            displayAudioSongName();
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);


        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }







    public ArrayList<File> readonlyAudioFile(File file)        // this method is created to read the audiofile
    {
        ArrayList<File> arrayList = new ArrayList<>();
        File[] allFile = file.listFiles();

        try {
            for (File individualFile : allFile) {


                if (individualFile.isDirectory() && !individualFile.isHidden() )
                {
                    arrayList.addAll(readonlyAudioFile(individualFile));


                }
                   else if (individualFile.getName().endsWith(".mp3") || individualFile.getName().endsWith(".aac") || individualFile.getName().endsWith(".wav") || individualFile.getName().endsWith(".wma"))
                   {
                    arrayList.addAll(readonlyAudioFile(individualFile));
                    arrayList.add(individualFile);
                   }
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;

    }
    //---------------------------------------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------------------------------------
    private void displayAudioSongName() {
        final ArrayList<File> audiosong = readonlyAudioFile(Environment.getExternalStorageDirectory());

        itemAll = new String[audiosong.size()];
        for (int songCounter = 0; songCounter <= audiosong.size(); songCounter++) {
            try {
                itemAll[songCounter] = audiosong.get(songCounter).getName();
            } catch (Exception e) {
                e.printStackTrace();
            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Main2Activity.this, android.R.layout.simple_list_item_1, itemAll);
            msongList.setAdapter(arrayAdapter);

//-------------------------------------------------------------------------------------------------------------------------------------
              msongList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                {
                    if(mymediaPlayer.isPlaying()) {
                       mymediaPlayer.stop();
                       mymediaPlayer.start();
                       mymediaPlayer.release();


                    }
                 String songName =msongList.getItemAtPosition(i).toString();
                 Intent intent =new Intent(getApplicationContext(),PlayerActivity.class);
                 intent.putExtra("song",audiosong);
                 intent.putExtra("name",songName);
                 intent.putExtra("Position",i);

                 startActivity(intent);
                }
            });
        }
    }
    //------------------------------------------------------------------------------------------------------------------------------
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();      // it inflate the menu item in the code and its xml file will be too inflated
        menuInflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.Help) {
            Intent mailIntent = new Intent(Intent.ACTION_VIEW);
            Uri data = Uri.parse("mailto:?subject=" + " Subject of Your Query...  " + "&body=" + " Please Write your Query... " + "&to=" + "pkasaudhan93@gmail.com");
            mailIntent.setData(data);
            startActivity(mailIntent);
            return true;
        } else if (item.getItemId() == R.id.About) {

            Intent intent = new Intent(getApplicationContext(), AboutMenu.class);
            startActivity(intent);

            return true;
        } else if (item.getItemId() == R.id.Share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Hey check out my app at: https://play.google.com/store/apps/details?id=com.google.android.apps.plus");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);

        }


        return false;
    }

}
