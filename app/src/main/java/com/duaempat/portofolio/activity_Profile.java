package com.duaempat.portofolio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class activity_Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView passData = findViewById(R.id.txtWelcome);
        TextView passData2 = findViewById(R.id.txtWelcome2);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            String nama = (String) bundle.get("moveUsername");
            String pass = (String) bundle.get("movePassword");
            passData.setText(getString(R.string.welcome_messages, nama));
            passData2.setText(getString(R.string.welcome_messages2, pass));
        }
    }

    public void keluar(View view) {
        finish();
    }

    public void aboutwa(View view) {
        Toast.makeText(this, "Tentang WhatsApp saya !", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, activity_aboutme.class);
        intent.putExtra("moveabout", "wa");
        startActivity(intent);
        finish();
    }

    public void aboutig(View view) {
        Toast.makeText(this, "Tentang Instagram saya !", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, activity_aboutme.class);
        intent.putExtra("moveabout", "ig");
        startActivity(intent);
        finish();
    }

    public void abouttw(View view) {
        Toast.makeText(this, "Tentang Twitter saya !", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, activity_aboutme.class);
        intent.putExtra("moveabout", "tw");
        startActivity(intent);
        finish();
    }

    public void aboutrd(View view) {
        Toast.makeText(this, "Tentang Sub Reddit saya !", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, activity_aboutme.class);
        intent.putExtra("moveabout", "rd");
        startActivity(intent);
        finish();
    }

    public void aboutw3(View view) {
        Toast.makeText(this, "Tentang Website saya !", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, activity_aboutme.class);
        intent.putExtra("moveabout", "w3");
        startActivity(intent);
        finish();
    }

    public void alertproject1(View view) {
        Toast.makeText(this, "Project pertama saya !", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, activity_Login.class);
        intent.putExtra("movepro", "pro1");
        startActivity(intent);
        finish();
    }

    public void alertproject2(View view) {
        Toast.makeText(this, "Project kedua saya !", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, activity_Profile.class);
        intent.putExtra("movepro", "pro2");
        startActivity(intent);
        finish();
    }

    public void alertproject3(View view) {
        Toast.makeText(this, "Project ketiga saya !", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, activity_InputUser.class);
        intent.putExtra("movepro", "pro3");
        startActivity(intent);
        finish();
    }

    public void alertproject4(View view) {
        Toast.makeText(this, "Project keempat saya !", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, activity_Project.class);
        intent.putExtra("movepro", "pro4");
        startActivity(intent);
        finish();
    }

    public void alertproject5(View view) {
        Toast.makeText(this, "Project kelima saya !", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, activity_InputUser.class);
        intent.putExtra("movepro", "pro5");
        startActivity(intent);
        finish();
    }

    public void alertproject6(View view) {
        Toast.makeText(this, "Project 2 sisipan saya !", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, activity_calculator.class);
        intent.putExtra("movepro", "pro6");
        startActivity(intent);
        finish();
    }

    public void logout(View view) {
        Intent intent = new Intent(this, activity_Login.class);
        startActivity(intent);
        finish();
    }

    public void alertproject7(View view) {
        Toast.makeText(this, "Project keenam saya !", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, activity_API.class);
        intent.putExtra("movepro", "pro6");
        startActivity(intent);
        finish();
    }
}