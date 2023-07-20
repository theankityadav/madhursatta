package com.madhuurstta.wbvjkmatka;

import static com.madhuurstta.wbvjkmatka.R.layout.activity_withdraw;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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

import lib.kingja.switchbutton.SwitchMultiButton;

public class withdraw extends AppCompatActivity {


    ViewDialog progressDialog;
    String url, withdraw_request;
    ArrayList<String> gateways = new ArrayList<>();
    String gateway = "";
    SwitchMultiButton mSwitchMultiButton;
    EditText amount;
    Spinner mode;

    ArrayList<String> payment_mode = new ArrayList<>();
    ArrayList<String> payment_info = new ArrayList<>();
    private ImageView back;
    private EditText paytm;
    private EditText phonepe;
    private EditText ac;
    private EditText ifsc;
    private EditText holder;
    private LinearLayout bankDetails;
    private latobold submit;
    private LinearLayout whatsapp;
    RecyclerView recycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_withdraw);
        initViews();
        url = constant.prefix + getString(R.string.withdraw_modes);
        withdraw_request = constant.prefix + getString(R.string.withdraw_request);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        amount = findViewById(R.id.amount);
        mode = findViewById(R.id.mode);

        ArrayList<String> modes = new ArrayList<>();
        modes.add("Paytm");
        modes.add("Phonepe");
        modes.add("Bank");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.simple_list_item_2, modes);
        mode.setAdapter(adapter);

        mode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    paytm.setVisibility(View.VISIBLE);
                    phonepe.setVisibility(View.GONE);
                    bankDetails.setVisibility(View.GONE);
                } else if (position == 1){
                    phonepe.setVisibility(View.VISIBLE);
                    paytm.setVisibility(View.GONE);
                    bankDetails.setVisibility(View.GONE);
                } else if (position == 2){
                    bankDetails.setVisibility(View.VISIBLE);
                    phonepe.setVisibility(View.GONE);
                    paytm.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Log.e("wall", getSharedPreferences(constant.prefs, Context.MODE_PRIVATE).getString("wallet", "0"));

        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amount.getText().toString().isEmpty() || amount.getText().toString().equals("0")) {
                    amount.setError("Enter valid coins");
                } else if (Integer.parseInt(amount.getText().toString()) < Integer.parseInt(getSharedPreferences(constant.prefs, Context.MODE_PRIVATE).getString("min_withdraw", "1000"))) {
                    amount.setError("coins must be more than " + getSharedPreferences(constant.prefs, Context.MODE_PRIVATE).getString("min_withdraw", constant.min_deposit + ""));
                } else if (Integer.parseInt(amount.getText().toString()) > Integer.parseInt(getSharedPreferences(constant.prefs, Context.MODE_PRIVATE).getString("wallet", "0"))) {
                    amount.setError("You don't have enough coin balance");
                } else {

                    if (mode.getSelectedItemPosition() == 0){
                        if (paytm.getText().toString().isEmpty()){
                            paytm.setError("Enter paytm number");
                            return;
                        }
                    } else if (mode.getSelectedItemPosition() == 1){
                        if (phonepe.getText().toString().isEmpty()){
                            phonepe.setError("Enter phonepe number");
                            return;
                        }
                    } else if (mode.getSelectedItemPosition() == 2){
                        if (ac.getText().toString().isEmpty()){
                            ac.setError("Enter account number");
                            return;
                        }
                        if (ifsc.getText().toString().isEmpty()){
                            ifsc.setError("Enter ifsc");
                            return;
                        }
                        if (holder.getText().toString().isEmpty()){
                            holder.setError("Enter account holder name");
                            return;
                        }
                    }

                    apicall();
                }
            }
        });

    }


    @Override
    protected void onResume() {
        getapi();
        super.onResume();
    }

    private void getapi() {

        progressDialog = new ViewDialog(withdraw.this);
        progressDialog.showDialog();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        final StringRequest postRequest = new StringRequest(Request.Method.POST, constant.prefix+"withdraw_reqs.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.hideDialog();
                        Log.e("edsa", "efsdc" + response);
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);

                            ArrayList<String> amount = new ArrayList<>();
                            ArrayList<String> details = new ArrayList<>();
                            ArrayList<String> mode = new ArrayList<>();
                            ArrayList<String> date = new ArrayList<>();
                            ArrayList<String> status = new ArrayList<>();

                            JSONArray jsonArray = jsonObject1.getJSONArray("data");

                            for (int a = 0; a < jsonArray.length(); a++){
                                JSONObject jsonObject = jsonArray.getJSONObject(a);

                                amount.add(jsonObject.getString("amount"));
                                mode.add(jsonObject.getString("mode"));
                                details.add(jsonObject.getString("details"));
                                date.add(jsonObject.getString("date"));
                                status.add(jsonObject.getString("status"));

                            }

                            adapter_withdraw_requests rc = new adapter_withdraw_requests(withdraw.this,amount,details,mode,date,status);
                            recycler.setLayoutManager(new GridLayoutManager(withdraw.this, 1));
                            recycler.setAdapter(rc);



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
                        Toast.makeText(withdraw.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
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


    private void apicall() {

        progressDialog = new ViewDialog(withdraw.this);
        progressDialog.showDialog();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        final StringRequest postRequest = new StringRequest(Request.Method.POST, withdraw_request,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.hideDialog();
                        Log.e("edsa", "efsdc" + response);
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);

                            if (jsonObject1.getString("active").equals("0")) {
                                Toast.makeText(withdraw.this, "Your account temporarily disabled by admin", Toast.LENGTH_SHORT).show();

                                getSharedPreferences(constant.prefs, MODE_PRIVATE).edit().clear().apply();
                                Intent in = new Intent(getApplicationContext(), login.class);
                                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(in);
                                finish();
                            }


                            if (jsonObject1.getString("success").equalsIgnoreCase("1")) {
                                SharedPreferences.Editor editor = getSharedPreferences(constant.prefs, MODE_PRIVATE).edit();
                                editor.putString("wallet", jsonObject1.getString("wallet")).apply();

                                AlertDialog.Builder builder1 = new AlertDialog.Builder(withdraw.this);
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
                        Toast.makeText(withdraw.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("mobile", getSharedPreferences(constant.prefs, MODE_PRIVATE).getString("mobile", null));
                params.put("session", getSharedPreferences(constant.prefs, MODE_PRIVATE).getString("session", null));
                params.put("amount", amount.getText().toString());
                params.put("mode", mode.getSelectedItem().toString());
                params.put("paytm", paytm.getText().toString());
                params.put("phonepe", phonepe.getText().toString());
                params.put("ac", ac.getText().toString());
                params.put("ifsc", ifsc.getText().toString());
                params.put("holder", holder.getText().toString());


                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
    }

    private void initViews() {
        back = findViewById(R.id.back);
        amount = findViewById(R.id.amount);
        mode = findViewById(R.id.mode);
        paytm = findViewById(R.id.paytm);
        phonepe = findViewById(R.id.phonepe);
        ac = findViewById(R.id.ac);
        ifsc = findViewById(R.id.ifsc);
        holder = findViewById(R.id.holder);
        bankDetails = findViewById(R.id.bank_details);
        submit = findViewById(R.id.submit);
        whatsapp = findViewById(R.id.whatsapp);
        recycler = findViewById(R.id.recycler);
    }
}