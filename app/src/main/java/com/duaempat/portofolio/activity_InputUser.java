package com.duaempat.portofolio;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.FileProvider;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import static android.widget.Toast.LENGTH_LONG;

public class activity_InputUser extends AppCompatActivity {

    public final String APP_TAG = "portofolio";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    private SimpleDateFormat dateFormat;
    private EditText txtNama, dtpTglLahir, txtWriteSome;
    private ImageView imgView;
    private SwitchCompat swcEmail;
    private RadioButton rdbLaki, rdbPerempuan;
    private RadioGroup rdbKelamin;
    private CheckBox chkSekolah, chkRumah;
    private Integer SELECT_FILE=0;
    private String nama, writesome, tgllahir, jekel = "", email = "false", teman1 = "rumah",
            teman2 = "sekolah", picturePath= "", photoname="gambar.jpg";
    File photoFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_user);
        txtNama = findViewById(R.id.txtNama);
        swcEmail = findViewById(R.id.swcEmail);
        txtWriteSome = findViewById(R.id.txtWrite);
        rdbKelamin = findViewById(R.id.rdbKelamin);
        rdbLaki = findViewById(R.id.rdbLaki);
        rdbPerempuan = findViewById(R.id.rdbPerempuan);
        chkSekolah = findViewById(R.id.chkTmnSekolah);
        chkRumah = findViewById(R.id.chkTmnRumah);
        dtpTglLahir= findViewById(R.id.txtTglLahir);
        imgView = findViewById(R.id.imgPP);
        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    }

    public void selectImage(View view){
        final CharSequence[] items={"Camera","Gallery","Cancel"};
        AlertDialog.Builder builder=new AlertDialog.Builder(activity_InputUser.this);
        builder.setTitle("Change Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(items[i].equals("Camera")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    photoFile = getPhotoFileUri(photoname);
                    Uri fileProvider = FileProvider.getUriForFile(Objects.requireNonNull(getApplicationContext()),
                            BuildConfig.APPLICATION_ID + ".provider", photoFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                    }
                }
                else if(items[i].equals("Gallery"))
                {
                    Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent,SELECT_FILE);
                }
                else if(items[i].equals("Cancel"))
                {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

    public File getPhotoFileUri(String fileName) {
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(APP_TAG, "failed to create directory");
        }
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);
        picturePath = file.toString();
        return file;
    }

    public void showDateDialog(View view)
    {
        Calendar calendar= Calendar.getInstance();
        DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar newDate= Calendar.getInstance();
                newDate.set(year,month,day);
                dtpTglLahir.setText(dateFormat.format(newDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        try{
            if(requestCode==SELECT_FILE && resultCode == RESULT_OK)
            {
                assert data != null;
                Uri selectedImage =  data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                if (selectedImage != null) {
                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    if (cursor != null) {
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        picturePath = cursor.getString(columnIndex);
                        imgView.setImageDrawable(null);
                        imgView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                        cursor.close();
                    }
                }
            }
            else if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
                if (resultCode == RESULT_OK) {
                    Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                    Bitmap bMapScaled = Bitmap.createScaledBitmap(takenImage, 150, 150, true);
                    imgView.setImageDrawable(null);
                    imgView.setImageBitmap(bMapScaled);
                    picturePath = photoFile.getAbsolutePath();
                }
                else{
                    Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void save(View view) {
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        nama = txtNama.getText().toString();
        writesome = txtWriteSome.getText().toString();
        tgllahir = dtpTglLahir.getText().toString();
        if (nama.equals("")) {
            ad.setMessage("Isi Nama !").setNegativeButton("OK", null).create().show();
        } else if (tgllahir.equals("")) {
            ad.setMessage("Pilih Tanggal Lahir !").setNegativeButton("OK", null).create().show();
        } else if (writesome.equals("")) {
            ad.setMessage("Tuliskan sesuatu !").setNegativeButton("OK", null).create().show();
        }
        if (rdbLaki.isChecked()) {
            jekel = "Laki-Laki";
        } else if (rdbPerempuan.isChecked()) {
            jekel = "Perempuan";
        } else if (!rdbLaki.isChecked() && !rdbPerempuan.isChecked()) {
            ad.setMessage("Pilih salah satu jenis kelamin !").setNegativeButton("OK", null).create().show();
        }
        if (swcEmail.isChecked()) {
            email = "true";
        } else if (!swcEmail.isChecked()) {
            email = "false";
        }
        if (chkRumah.isChecked() && chkSekolah.isChecked()) {
            teman1 = "rumah";
            teman2 = "sekolah";
        }
        else if (chkRumah.isChecked()) {
            teman1 = "rumah";
            teman2 = "false";
        }
        else if (chkSekolah.isChecked()) {
            teman1 = "false";
            teman2 = "sekolah";
        }
        else if (!chkRumah.isChecked() && !chkSekolah.isChecked()) {
            ad.setMessage("Pilih salah satu atau kedua status teman !").setNegativeButton("OK", null).create().show();
        }
        File file = new File(this.getFilesDir(), "text");
        if (!file.exists()) {
            file.mkdir();
        } else
        {
            try {
                File gpxfile = new File(file, "data");
                FileWriter writer = new FileWriter(gpxfile);
                writer.append(nama);
                writer.append("\n");
                writer.append(writesome);
                writer.append("\n");
                writer.append(tgllahir);
                writer.append("\n");
                writer.append(jekel);
                writer.append("\n");
                writer.append(email);
                writer.append("\n");
                writer.append(picturePath);
                writer.append("\n");
                writer.append(teman1);
                writer.append("\t");
                writer.append(teman2);
                writer.append("\n");
                writer.flush();
                writer.close();
                Toast.makeText(this, "Data tersimpan di : " + getFilesDir() + "/" + file.getName(), LENGTH_LONG).show();
                clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void load(View view) {
        File file = new File(this.getFilesDir()+"/text/data");
        StringBuilder content = new StringBuilder();
        try {
            clear();
            if(file.exists() && file.length() != 0){
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                String[] array = new String[7];
                int i = 0;
                while ((line = br.readLine()) != null) {
                    content.append(line);
                    array[i] = content.toString();
                    i++;
                    content = new StringBuilder();
                }
                txtNama.setText(array[0]);
                txtWriteSome.setText(array[1]);
                dtpTglLahir.setText(array[2]);
                if(array[3].trim().equals("Laki-Laki")){
                    rdbLaki.setChecked(true);
                }
                else if(array[3].trim().equals("Perempuan")){
                    rdbPerempuan.setChecked(true);
                }
                if(array[4].trim().equals("true")){
                    swcEmail.setChecked(true);
                }
                else if(array[4].trim().equals("false")){
                    swcEmail.setChecked(false);
                }
                imgView.setImageDrawable(null);
                File imgFile = new File(array[5].trim());
                Bitmap takenImage = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                Bitmap bMapScaled = Bitmap.createScaledBitmap(takenImage, 100, 150, true);
                imgView.setImageBitmap(bMapScaled);
                String[] teman = array[6].trim().split("\t");
                if(teman[0].trim().equals("rumah") && teman[1].trim().equals("sekolah")){
                    chkRumah.setChecked(true);
                    chkSekolah.setChecked(true);
                }
                else if(teman[0].trim().equals("rumah")){
                    chkRumah.setChecked(true);
                }
                else if(teman[1].trim().equals("sekolah")){
                    chkSekolah.setChecked(true);
                }
                br.close();
            }
            else{
                Toast.makeText(this, "File Missing", LENGTH_LONG).show();
            }
        } catch (IOException e) {
            e.getSuppressed();
        }

    }

    public void clears(View view) {
        txtNama.getText().clear();
        txtWriteSome.getText().clear();
        dtpTglLahir.getText().clear();
        rdbKelamin.clearCheck();
//        if (jekel.equals("Laki-Laki")) {
//            rdbLaki.setChecked(false);
//        } else if (jekel.equals("Perempuan")) {
//            rdbPerempuan.setChecked(false);
//        }
        swcEmail.setChecked(false);
        chkRumah.setChecked(false);
        chkSekolah.setChecked(false);
        imgView.setImageResource(R.drawable.pp);
        nama = "";
        writesome = "";
        tgllahir = "";
        jekel = "";
        email = "false";
        teman1 = "";
        teman2 = "";
//        picturePath = "";
    }

    public void clear() {
        txtNama.getText().clear();
        txtWriteSome.getText().clear();
        dtpTglLahir.getText().clear();
        rdbKelamin.clearCheck();
//        if (jekel.equals("Laki-Laki")) {
//            rdbLaki.setChecked(false);
//        } else if (jekel.equals("Perempuan")) {
//            rdbPerempuan.setChecked(false);
//        }
        swcEmail.setChecked(false);
        chkRumah.setChecked(false);
        chkSekolah.setChecked(false);
        imgView.setImageDrawable(null);
        nama = "";
        writesome = "";
        tgllahir = "";
        jekel = "";
        email = "false";
        teman1 = "";
        teman2 = "";
//        picturePath = "";
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