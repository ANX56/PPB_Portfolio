package com.duaempat.portofolio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class activity_Project extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        TextView passData = findViewById(R.id.txtText);
        TextView passData2 = findViewById(R.id.txtText2);
        ImageView img = findViewById(R.id.imageView);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            String about = (String) bundle.get("movepro");
            assert about != null;
            switch (about) {
                case "pro1":
                    passData.setText(R.string.pro1);
                    passData2.setText(R.string.keterangan1);
                    img.setBackgroundResource(R.drawable.project1);
                    break;
                case "pro2":
                    passData.setText(R.string.pro2);
                    passData2.setText(R.string.keterangan2);
                    img.setBackgroundResource(R.drawable.project2);
                    break;
                case "pro3":
                    passData.setText(R.string.pro3);
                    passData2.setText(R.string.keterangan3);
                    img.setBackgroundResource(R.drawable.project3);
                    break;
                case "pro4":
                    passData.setText(R.string.pro4);
                    passData2.setText(R.string.keterangan4);
                    img.setBackgroundResource(R.drawable.project4);
                    break;
                default:
                    passData.setText(R.string.pro5);
                    passData2.setText(R.string.keterangan5);
                    img.setBackgroundResource(R.drawable.project5);
                    break;
            }
        }
    }

    public void kembali(View view) {
        Intent intent = new Intent(this, activity_Profile.class);
        startActivity(intent);
        finish();
    }

}