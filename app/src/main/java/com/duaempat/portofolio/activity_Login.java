package com.duaempat.portofolio;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class activity_Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void pindah(View view){
        EditText txtUsername = findViewById(R.id.txtUsername);
        EditText txtPassword = findViewById(R.id.txtPassword);
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();
        if(password.equals("123123") || password.equals("atha1234")){
            Toast.makeText(this, "Login Berhasil !", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, activity_Profile.class);
            intent.putExtra("moveUsername", username) ;intent.putExtra("movePassword", password);
            startActivity(intent);
            finish();
        }
        else{
            AlertDialog.Builder ad = new AlertDialog.Builder(this);
            ad.setMessage("Password Salah !").setNegativeButton("Coba Lagi", null).create().show();
        }
    }

    public void keluar(View view) {
        finish();
    }

    public void beta(View view) {
        Intent intent = new Intent(this, activity_InputUser.class);
        startActivity(intent);
        finish();
    }
}