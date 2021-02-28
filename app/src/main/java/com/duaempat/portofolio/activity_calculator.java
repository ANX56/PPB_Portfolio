package com.duaempat.portofolio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class activity_calculator extends AppCompatActivity {
    EditText txtAngka;
    TextView txtOperator;
    float angka1, angka2;
    boolean tambah, kurang, kali, bagi;
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        txtAngka = findViewById(R.id.txtAngka);
        txtOperator = findViewById(R.id.txtOperator);
    }

    private void startPlayer(int status){
        if(player == null){
            switch(status){
                case 1:
                    player = MediaPlayer.create(this, R.raw.num);
                    break;
                case 2:
                    player = MediaPlayer.create(this, R.raw.operator);
                    break;
                case 3:
                    player = MediaPlayer.create(this, R.raw.clear);
                    break;
                case 4:
                    player = MediaPlayer.create(this, R.raw.result);
                    break;
                default:
                    break;
            }
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stop();
                }
            });
            player.start();
        }
        else{
            stop();
        }
    }

    private void stop(){
        if(player != null){
            player.release();
            player = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stop();
    }

    private void setAngka(int angka){
        switch(angka){
            case 0 :
                txtAngka.setText(txtAngka.getText().toString().trim() + "0");
                break;
            case 1:
                txtAngka.setText(txtAngka.getText().toString().trim() + "1");
                break;
            case 2 :
                txtAngka.setText(txtAngka.getText().toString().trim() + "2");
                break;
            case 3 :
                txtAngka.setText(txtAngka.getText().toString().trim() + "3");
                break;
            case 4 :
                txtAngka.setText(txtAngka.getText().toString().trim() + "4");
                break;
            case 5 :
                txtAngka.setText(txtAngka.getText().toString().trim() + "5");
                break;
            case 6 :
                txtAngka.setText(txtAngka.getText().toString().trim() + "6");
                break;
            case 7 :
                txtAngka.setText(txtAngka.getText().toString().trim() + "7");
                break;
            case 8 :
                txtAngka.setText(txtAngka.getText().toString().trim() + "8");
                break;
            case 9 :
                txtAngka.setText(txtAngka.getText().toString().trim() + "9");
                break;
            default:
                break;
        }
        startPlayer(1);
    }

    private void setOperator(int op){
        if (txtAngka.getText().toString().trim().equals("")) {
            Toast.makeText(this, "isi angka !", Toast.LENGTH_SHORT).show();
        } else {
            angka1 = Float.parseFloat(txtAngka.getText().toString());
            txtAngka.setText("");
            switch(op){
                case 1:
                    tambah = true;
                    txtOperator.setText("+");
                    break;
                case 2:
                    kurang = true;
                    txtOperator.setText("-");
                    break;
                case 3:
                    kali = true;
                    txtOperator.setText("*");
                    break;
                case 4:
                    bagi = true;
                    txtOperator.setText("/");
                    break;
                default:
                    clean();
                    break;
            }
        }
        startPlayer(2);
    }

    private void hitung(int op){
        angka2 = Float.parseFloat(txtAngka.getText().toString().trim());
        switch(op){
            case 1:
                txtAngka.setText(String.valueOf(angka1 + angka2).trim());
                tambah = false;
                break;
            case 2:
                txtAngka.setText(String.valueOf(angka1 - angka2).trim());
                kurang = false;
                break;
            case 3:
                txtAngka.setText(String.valueOf(angka1 * angka2).trim());
                kali = false;
                break;
            case 4:
                txtAngka.setText(String.valueOf(angka1 / angka2).trim());
                bagi = false;
                break;
            default:
                clean();
                break;
        }
        startPlayer(4);
    }

    private void clean(){
        txtAngka.setText("");
        txtOperator.setText("");
        angka1 = 0;
        angka2 = 0;
        startPlayer(3);
    }

    public void satu(View view) {
        setAngka(1);
    }

    public void dua(View view) {
        setAngka(2);
    }

    public void tiga(View view) {
        setAngka(3);
    }

    public void empat(View view) {
        setAngka(4);
    }

    public void lima(View view) {
        setAngka(5);
    }

    public void enam(View view) {
        setAngka(6);
    }

    public void tujuh(View view) {
        setAngka(7);
    }

    public void delapan(View view) {
        setAngka(8);
    }

    public void sembilan(View view) {
        setAngka(9);
    }

    public void nol(View view) {
        setAngka(0);

    }

    public void tambah(View view) {
        setOperator(1);
    }

    public void kurang(View view) {
        setOperator(2);
    }

    public void kali(View view) {
        setOperator(3);
    }

    public void bagi(View view) {
        setOperator(4);
    }

    public void clear(View view) {
        clean();
    }

    public void hasil(View view) {
        if(tambah){
            hitung(1);
        }
        else if(kurang){
            hitung(2);
        }
        else if(kali){
            hitung(3);
        }
        else if(bagi){
            hitung(4);
        }
    }

    public void kembali(View view) {
        Intent intent = new Intent(this, activity_Profile.class);
        startActivity(intent);
        finish();
    }

    public void exit(View view) {
        finish();
    }
}