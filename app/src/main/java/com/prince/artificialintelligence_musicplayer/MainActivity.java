package com.prince.artificialintelligence_musicplayer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
private Button forward;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
forward=(Button)findViewById(R.id.forward);
forward.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent =new Intent(getApplicationContext(),Main2Activity.class);
        startActivity(intent);
    }
});




        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Intent WelcomeActivity = new Intent(getApplicationContext(), Main2Activity.class);
                    startActivity(WelcomeActivity);
                }
            }

        };
        thread.start();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    //------------------------------------------------------------------------------------------------------------------------------------
}
