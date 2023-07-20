package com.madhuurstta.makta;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DelhiJodiMarkets extends AppCompatActivity {


    RecyclerView recyclerview;
    ViewDialog progressDialog;
    String url2;
    TextView title;
    private ImageView back;
    private RelativeLayout toolbar;
    private latonormal rate;
    private latonormal bidHistory;
    private latonormal resultHistory;
    private latonormal chart;
    private ImageView back2;
    private RecyclerView recyclerview2;
    private LinearLayout resultHistoryPop;
    private latonormal single;
    private latonormal jodi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delhi_jodi_markets);
        initViews();
        url2 = constant.prefix + getResources().getString(R.string.delhi_markets);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        resultHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultHistoryPop.setVisibility(View.VISIBLE);
            }
        });

        bidHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DelhiJodiMarkets.this, played.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

        chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DelhiJodiMarkets.this, chart_menu.class));
            }
        });

        apicall2();
    }

    @Override
    public void onBackPressed() {

        if (resultHistoryPop.getVisibility() == View.VISIBLE){
            resultHistoryPop.setVisibility(View.GONE);
            return;
        }

        super.onBackPressed();
    }

    private void apicall2() {

        progressDialog = new ViewDialog(DelhiJodiMarkets.this);
        progressDialog.showDialog();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        final StringRequest postRequest = new StringRequest(Request.Method.POST, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response", response);
                        progressDialog.hideDialog();
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);

                            JSONArray jsonArray = jsonObject1.getJSONArray("data");

                            rate.setText("Single Digit : " + jsonObject1.getString("single") + ", Single Pana : " + jsonObject1.getString("singlepatti") + ", Double Pana : " + jsonObject1.getString("doublepatti") + ", Triple Pana : " + jsonObject1.getString("triplepatti"));

                            single.setText(jsonObject1.getString("single"));
                            jodi.setText(jsonObject1.getString("jodi"));

                            ArrayList<String> name = new ArrayList<>();
                            ArrayList<String> is_open = new ArrayList<>();
                            ArrayList<String> time = new ArrayList<>();
                            ArrayList<String> result = new ArrayList<>();
                            ArrayList<String> market_name = new ArrayList<>();


                            for (int a = 0; a < jsonArray.length(); a++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(a);

                                name.add(jsonObject.getString("market"));
                                is_open.add(jsonObject.getString("is_open"));
                                time.add(jsonObject.getString("time"));
                                result.add(jsonObject.getString("result"));
                                market_name.add(jsonObject.getString("name2"));
                            }

                            adapter_delhi_markets rc = new adapter_delhi_markets(DelhiJodiMarkets.this, name, is_open, time, result, market_name);
                            recyclerview.setLayoutManager(new GridLayoutManager(DelhiJodiMarkets.this, 1));
                            recyclerview.setAdapter(rc);

                            adapter_starline_results rc2 = new adapter_starline_results(name, result);
                            recyclerview2.setLayoutManager(new GridLayoutManager(DelhiJodiMarkets.this, 1));
                            recyclerview2.setAdapter(rc2);


                        } catch (JSONException e) {
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
                        Toast.makeText(DelhiJodiMarkets.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("session", getSharedPreferences(constant.prefs, MODE_PRIVATE).getString("session", null));

                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
    }




    private void initViews() {
        recyclerview = findViewById(R.id.recyclerview);
        title = findViewById(R.id.title);
        back = findViewById(R.id.back);
        toolbar = findViewById(R.id.toolbar);
        rate = findViewById(R.id.rate);
        bidHistory = findViewById(R.id.bid_history);
        resultHistory = findViewById(R.id.result_history);
        chart = findViewById(R.id.chart);
        back2 = findViewById(R.id.back2);
        recyclerview2 = findViewById(R.id.recyclerview2);
        resultHistoryPop = findViewById(R.id.result_history_pop);
        single = findViewById(R.id.single);
        jodi = findViewById(R.id.jodi);
    }
}
