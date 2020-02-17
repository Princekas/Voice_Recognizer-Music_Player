package com.prince.artificialintelligence_musicplayer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AboutMenu extends AppCompatActivity {
private Button facebookbutton;
private Button resumebutton;
private Button LinkdinButton;
    private Button MailButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_menu);
        facebookbutton=(Button)findViewById(R.id.button);
        resumebutton=(Button)findViewById(R.id.button2);
        LinkdinButton=(Button)findViewById(R.id.button3);
        MailButton=(Button)findViewById(R.id.button4);


        facebookbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent web =new Intent(getApplicationContext(), com.prince.artificialintelligence_musicplayer.web.class);
                startActivity(web);
            }
        });
        resumebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resume =new Intent(getApplicationContext(), com.prince.artificialintelligence_musicplayer.Resume.class);
                startActivity(resume);
            }
        });
        LinkdinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent linkdin =new Intent(getApplicationContext(), com.prince.artificialintelligence_musicplayer.linkdin.class);
                startActivity(linkdin);
            }
        });
        MailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mailIntent = new Intent(Intent.ACTION_VIEW);
                Uri data = Uri.parse("mailto:?subject=" + " Subject of Your Query...  " + "&body=" + " Please Write Your View to Developer... " + "&to=" + "pkasaudhan93@gmail.com");
                mailIntent.setData(data);
                startActivity(mailIntent);

            }
        });

    }
}
