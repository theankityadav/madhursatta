package com.madhuurstta.wbvjkmatka;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import android.util.Log;

public class played extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener {

    protected RecyclerView recyclerview;
    ViewDialog progressDialog;
    String url ;
    ImageView datepicker;
    TextView date;
    String dateOpen = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_played);
        initView();
        url = constant.prefix + getString(R.string.games);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog  StartTime = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String month = monthOfYear+1+"";
                if (monthOfYear < 10){
                    month = "0"+month;
                }
                dateOpen = dayOfMonth+"/"+month+"/"+year;
                Log.e("dateOpen",dateOpen);
                apicall();
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override   public void onClick(View v) {
                StartTime.show();
            }});

        apicall();

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(mCalendar.getTime());
        dateOpen = selectedDate;
        date.setText(selectedDate);
        date.setVisibility(View.VISIBLE);
    }

    private void apicall() {

        progressDialog = new ViewDialog(played.this);
        progressDialog.showDialog();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        final StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("res",response);
                        progressDialog.hideDialog();
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);

                            ArrayList<String> date = new ArrayList<>();
                            ArrayList<String> bazar = new ArrayList<>();
                            ArrayList<String> amount = new ArrayList<>();
                            ArrayList<String> bet = new ArrayList<>();

                            if (jsonObject1.has("data")) {
                                JSONArray jsonArray = jsonObject1.getJSONArray("data");
                                for (int a = 0; jsonArray.length() > a; a++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(a);

                                    date.add(jsonObject.getString("date"));
                                    amount.add(jsonObject.getString("amount"));

                                    bazar.add(jsonObject.getString("bazar").replace("_", " "));

                                    bet.add(jsonObject.getString("number"));
                                }
                            }

                            adapterplayed rc = new adapterplayed(played.this,date,bazar,amount,bet);
                            recyclerview.setLayoutManager(new GridLayoutManager(played.this, 1));
                            recyclerview.setAdapter(rc);

                        } catch (JSONException e) {
                            ArrayList<String> date = new ArrayList<>();
                            ArrayList<String> bazar = new ArrayList<>();
                            ArrayList<String> amount = new ArrayList<>();
                            ArrayList<String> bet = new ArrayList<>();

                            adapterplayed rc = new adapterplayed(played.this,date,bazar,amount,bet);
                            recyclerview.setLayoutManager(new GridLayoutManager(played.this, 1));
                            recyclerview.setAdapter(rc);

                            e.printStackTrace();
                            progressDialog.hideDialog();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        error.printStackTrace();
                        progressDialog.hideDialog();
                        Toast.makeText(played.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("mobile", getSharedPreferences(constant.prefs,MODE_PRIVATE).getString("mobile",null));

                if (!dateOpen.equals("")){
                    params.put("date",dateOpen);
                }


                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
    }


    private void initView() {
        datepicker = findViewById(R.id.datepicker);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        date = findViewById(R.id.date);

        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}

