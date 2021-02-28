package com.duaempat.portofolio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressWarnings("SpellCheckingInspection")
public class activity_aboutme extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutme);

        TextView passData = findViewById(R.id.txtText);
        TextView passData2 = findViewById(R.id.txtText2);
        ImageView img = findViewById(R.id.imageView);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            String about = (String) bundle.get("moveabout");
            if(about.equals("wa")){
                passData.setText(R.string.abo1);
                passData2.setText(R.string.wa);
                img.setBackgroundResource(R.drawable.about1);
            }
            else if(about.equals("ig")){
                passData.setText(R.string.abo2);
                passData2.setText(R.string.ig);
                img.setBackgroundResource(R.drawable.about2);
            }
            else if(about.equals("tw")){
                passData.setText(R.string.abo3);
                passData2.setText(R.string.tw);
                img.setBackgroundResource(R.drawable.about3);
            }
            else if(about.equals("rd")){
                passData.setText(R.string.abo4);
                passData2.setText(R.string.rd);
                img.setBackgroundResource(R.drawable.about4);
            }
            else{
                passData.setText(R.string.abo5);
                passData2.setText(R.string.w3);
                img.setBackgroundResource(R.drawable.about5);
            }
        }
    }


    public void kembali(View view) {
        Intent intent = new Intent(this, activity_Profile.class);
        startActivity(intent);
        finish();
    }
}