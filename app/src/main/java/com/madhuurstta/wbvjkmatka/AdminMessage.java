package com.madhuurstta.wbvjkmatka;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminMessage extends AppCompatActivity {

    ViewDialog progressDialog;
    String url,url2;

    private RecyclerView recyclerview;
    private CircleImageView send;
    private RelativeLayout msgWindow;
    private EditText msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_message);
        url = constant.prefix + "getChat.php";
        url2 = constant.prefix + "setChat.php";
        initViews();
        apicall();

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        send.setOnClickListener(v -> {
            if (!msg.getText().toString().isEmpty()){
                sendMsg();
            }
        });
    }


    private void sendMsg() {

        progressDialog = new ViewDialog(AdminMessage.this);
        progressDialog.showDialog();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String response = null;

        final StringRequest postRequest = new StringRequest(Request.Method.POST, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("edsa", "efsdc" + response);
                        progressDialog.hideDialog();
                        msg.setText("");
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);

                            ArrayList<String> msg = new ArrayList<>();
                            ArrayList<String> time = new ArrayList<>();
                            ArrayList<String> sender = new ArrayList<>();

                            JSONArray jsonArray = jsonObject1.getJSONArray("data");
                            for (int a = 0; a < jsonArray.length(); a++) {



                                JSONObject jsonObject = jsonArray.getJSONObject(a);

                                msg.add(jsonObject.getString("message"));
                                time.add(jsonObject.getString("time"));
                                sender.add(jsonObject.getString("user"));

                                if (a == 0){
                                    msg.add(getSharedPreferences(constant.prefs,MODE_PRIVATE).getString("welcome_msg",""));
                                    time.add(time.get(0));
                                    sender.add("admin");
                                }
                            }


                            adapter_chat rc = new adapter_chat(AdminMessage.this, msg, time, sender);
                            recyclerview.setLayoutManager(new GridLayoutManager(AdminMessage.this, 1));
                            recyclerview.setAdapter(rc);


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
                        Toast.makeText(AdminMessage.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("mobile", getSharedPreferences(constant.prefs, MODE_PRIVATE).getString("mobile", null));
                params.put("message",msg.getText().toString());

                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
    }

    private void apicall() {

        progressDialog = new ViewDialog(AdminMessage.this);
        progressDialog.showDialog();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String response = null;

        final StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("edsa", "efsdc" + response);
                        progressDialog.hideDialog();
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);

                            ArrayList<String> msg = new ArrayList<>();
                            ArrayList<String> time = new ArrayList<>();
                            ArrayList<String> sender = new ArrayList<>();

                            JSONArray jsonArray = jsonObject1.getJSONArray("data");
                            for (int a = 0; a < jsonArray.length(); a++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(a);

                                msg.add(jsonObject.getString("message"));
                                time.add(jsonObject.getString("time"));
                                sender.add(jsonObject.getString("user"));

                                if (a == 0){
                                    msg.add(getSharedPreferences(constant.prefs,MODE_PRIVATE).getString("welcome_msg",""));
                                    time.add(time.get(0));
                                    sender.add("admin");
                                }

                            }

                            adapter_chat rc = new adapter_chat(AdminMessage.this, msg, time, sender);
                            recyclerview.setLayoutManager(new GridLayoutManager(AdminMessage.this, 1));
                            recyclerview.setAdapter(rc);


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
                        Toast.makeText(AdminMessage.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("mobile", getSharedPreferences(constant.prefs, MODE_PRIVATE).getString("mobile", null));

                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
    }


    private void initViews() {
        recyclerview = findViewById(R.id.recyclerview);
        send = findViewById(R.id.send);
        msgWindow = findViewById(R.id.msg_window);
        msg = findViewById(R.id.msg);
    }
}