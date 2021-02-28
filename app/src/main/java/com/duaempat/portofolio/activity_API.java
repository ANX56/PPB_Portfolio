package com.duaempat.portofolio;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;

public class activity_API extends AppCompatActivity {

    public EditText editTextId, editTextNama, editTextEmail, editTextNohp;
    public Spinner spinnerKeterangan;
    public Button btnAddUpdate;
    public ListView listView;
    public static ProgressBar progressBar;
    public static final String CODE_POST_REQUEST = "POST";
    public static final String CODE_GET_REQUEST = "GET";
    ArrayList<biodata> dataList;
    boolean isUpdating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);

        editTextId = findViewById(R.id.txtID);
        editTextNama = findViewById(R.id.txtNama);
        editTextEmail = findViewById(R.id.txtEmail);
        editTextNohp = findViewById(R.id.txtNoHP);
        spinnerKeterangan = findViewById(R.id.keterangan);
        btnAddUpdate = findViewById(R.id.btnAdd);
        listView = findViewById(R.id.listView);
        progressBar = findViewById(R.id.progressBar);
        dataList = new ArrayList<>();

        readDataku();
        btnAddUpdate.setOnClickListener(v -> {
            if(isUpdating) {
                updateData();
                btnAddUpdate.setText("Add");
                editTextNama.setText("");
                editTextEmail.setText("");
                editTextNohp.setText("");
                spinnerKeterangan.setSelection(0);
                isUpdating = false;
            } else {
                buatData();
            }
        });
    }

    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {
        //the url where we need to send the request
        String url;
        //the parameters
        HashMap<String, String> params;
        //the request code to define whether it is a GET or POST
        String requestCode;

        //constructor to initialize values
        PerformNetworkRequest(String url, HashMap<String, String> params, String requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        //when the task started displaying a progressbar
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        //this method will give the response from the request
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(activity_API.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                    //refreshing the list after every operation
                    refreshDataku(object.getJSONArray("heroes"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //the network operation will be performed in background
        @Override
        protected String doInBackground(Void... voids) {
//            RequestHandler requestHandler = new RequestHandler();
            if (requestCode.equals(CODE_POST_REQUEST))
                return RequestHandler.sendPostRequest(url, params);
            if (requestCode.equals(CODE_GET_REQUEST))
                return RequestHandler.sendGetRequest(url);
            return null;
        }
    }

    class DatakuAdapter extends ArrayAdapter<biodata> {
        //our list
        List<biodata> dataList;
        //constructor to get the list
        public DatakuAdapter(List<biodata> dataList) {
            super(activity_API.this, R.layout.activity_layoutdatalist, dataList);
            this.dataList = dataList;
        }

        //method returning list item
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.activity_layoutdatalist, null, true);

            //getting the textview for displaying name
            TextView textViewName = listViewItem.findViewById(R.id.textViewName);

            //getting the imageview for displaying keterangan
            ImageView imgKeterangan = listViewItem.findViewById(R.id.imgKeterangan);

            //the update and delete textview
            TextView textViewUpdate = listViewItem.findViewById(R.id.textViewUpdate);
            TextView textViewDelete = listViewItem.findViewById(R.id.textViewDelete);

            final biodata dataku = dataList.get(position);
            if(dataku.getKeterangan().equals("teman")){
                imgKeterangan.setBackgroundResource(R.drawable.friends);
            } else if(dataku.getKeterangan().equals("biasa saja")){
                imgKeterangan.setBackgroundResource(R.drawable.neutral);
            } else if(dataku.getKeterangan().equals("bagaimana ya?")){
                imgKeterangan.setBackgroundResource(R.drawable.thinking);
            } else if(dataku.getKeterangan().equals("no comment")){
                imgKeterangan.setBackgroundResource(R.drawable.nocomment);
            }
            textViewName.setText(dataku.getNama());

            //attaching click listener to update
            textViewUpdate.setOnClickListener(view -> {
                isUpdating = true;

                //we will set the selected hero to the UI elements
                editTextId.setText(String.valueOf(dataku.getId()));
                editTextNama.setText(dataku.getNama());
                editTextEmail.setText(dataku.getEmail());
                editTextNohp.setText(dataku.getNohp());
                spinnerKeterangan.setSelection(((ArrayAdapter<String>) spinnerKeterangan.getAdapter()).getPosition(dataku.getKeterangan()));

                //we will also make the button text to Update
                btnAddUpdate.setText("Update");
            });

            //when the user selected delete
            textViewDelete.setOnClickListener(view -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity_API.this);

                builder.setTitle("Delete " + dataku.getNama())
                        .setMessage("Are you sure you want to delete it?")
                        .setPositiveButton(android.R.string.yes, (dialog, which) -> deleteData(dataku.getId()))
                        .setNegativeButton(android.R.string.no, (dialog, which) -> {
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            });

            return listViewItem;
        }
    }

    public void buatData() {
        String nama = editTextNama.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String nohp = editTextNohp.getText().toString().trim();
        String Keterangan = spinnerKeterangan.getSelectedItem().toString();

        //validating the inputs
        if (TextUtils.isEmpty(nama)) {
            editTextNama.setError("Masukkan Nama");
            editTextNama.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Masukkan email");
            editTextEmail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(nohp)) {
            editTextNohp.setError("Masukkan Nomor HP");
            editTextNohp.requestFocus();
            return;
        }

        //if validation passes
        HashMap<String, String> params = new HashMap<>();
        params.put("nama", nama);
        params.put("email", email);
        params.put("nohp", nohp);
        params.put("keterangan", Keterangan);

        //Calling the create API
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_CREATE, params, CODE_POST_REQUEST);
        request.execute();
    }

    private void readDataku() {
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_READ, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void refreshDataku(JSONArray dataarrayku) throws JSONException {
        //clearing previous data
        dataList.clear();

        //traversing through all the items in the json array
        //the json we got from the response
        for (int i = 0; i < dataarrayku.length(); i++) {
            //getting each hero object
            JSONObject obj = dataarrayku.getJSONObject(i);

            //adding to the list
            dataList.add(new biodata(
                    obj.getInt("id"),
                    obj.getString("nama"),
                    obj.getString("email"),
                    obj.getString("nohp"),
                    obj.getString("keterangan")
            ));
        }
        //creating the adapter and setting it to the listview
        DatakuAdapter adapter = new DatakuAdapter(dataList);
        listView.setAdapter(adapter);
    }

    private void updateData() {
        String id = editTextId.getText().toString();
        String nama = editTextNama.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String nohp = editTextNohp.getText().toString().trim();
        String keterangan = spinnerKeterangan.getSelectedItem().toString();

        if (TextUtils.isEmpty(nama)) {
            editTextNama.setError("Please enter nama");
            editTextNama.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Please enter e-mail");
            editTextEmail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            editTextNohp.setError("Please enter Nomor HP");
            editTextNohp.requestFocus();
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("nama", nama);
        params.put("email", email);
        params.put("nohp", nohp);
        params.put("keterangan", keterangan);

        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_UPDATE, params, CODE_POST_REQUEST);
        request.execute();

        btnAddUpdate.setText("Add");

        editTextNama.setText("");
        editTextEmail.setText("");
        editTextNohp.setText("");
        spinnerKeterangan.setSelection(0);

        isUpdating = false;
    }

    private void deleteData(int id) {
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_DELETE + id, null, CODE_GET_REQUEST);
        request.execute();
    }

    public void kembali(View view) {
        Intent intent = new Intent(this, activity_Profile.class);
        startActivity(intent);
        finish();
    }
}