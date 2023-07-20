package com.madhuurstta.wbvjkmatka;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TransferCoin extends AppCompatActivity {


    private ImageView back;
    private EditText mobile,password;
    private EditText amount;
    private latobold submit;
    private LinearLayout whatsapp;
    ViewDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_coin);
        initViews();

        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mobile.getText().toString().isEmpty() || mobile.getText().toString().length() != 10){
                    mobile.setError("Enter valid mobile number");
                } else if (amount.getText().toString().isEmpty() || amount.getText().toString().equals("0")){
                    amount.setError("Enter valid coins");
                } else if(Integer.parseInt(amount.getText().toString()) > Integer.parseInt(getSharedPreferences(constant.prefs, Context.MODE_PRIVATE).getString("wallet","0"))){
                    amount.setError("You don't have enough coin balance");
                } else {
                    apicall();
                }
            }
        });

    }


    private void apicall() {

        progressDialog = new ViewDialog(TransferCoin.this);
        progressDialog.showDialog();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        final StringRequest postRequest = new StringRequest(Request.Method.POST, constant.prefix+"transfer.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.hideDialog();
                        Log.e("edsa", "efsdc" + response);
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);

                            if (jsonObject1.getString("active").equals("0")) {
                                Toast.makeText(TransferCoin.this, "Password does not match, Please enter correct password", Toast.LENGTH_SHORT).show();
                            }

                            if (jsonObject1.getString("success").equalsIgnoreCase("1")) {
                                SharedPreferences.Editor editor = getSharedPreferences(constant.prefs,MODE_PRIVATE).edit();
                                editor.putString("wallet", jsonObject1.getString("wallet")).apply();

                                AlertDialog.Builder builder1 = new AlertDialog.Builder(TransferCoin.this);
                                builder1.setMessage(jsonObject1.getString("msg"));
                                builder1.setCancelable(true);
                                builder1.setNegativeButton(
                                        "Okay",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                                finish();
                                            }
                                        });

                                AlertDialog alert11 = builder1.create();
                                alert11.show();
                            } else {
                                Toast.makeText(getApplicationContext(), jsonObject1.getString("msg"), Toast.LENGTH_SHORT).show();
                            }

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
                        Toast.makeText(TransferCoin.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user", getSharedPreferences(constant.prefs,MODE_PRIVATE).getString("mobile",null));
                params.put("session",getSharedPreferences(constant.prefs, MODE_PRIVATE).getString("session", null));
                params.put("amount",amount.getText().toString());
                params.put("mobile",mobile.getText().toString());
                params.put("password",password.getText().toString());


                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
    }

    private void initViews() {
        back = findViewById(R.id.back);
        mobile = findViewById(R.id.mobile);
        amount = findViewById(R.id.amount);
        submit = findViewById(R.id.submit);
        whatsapp = findViewById(R.id.whatsapp);
        password = findViewById(R.id.password);
    }
}