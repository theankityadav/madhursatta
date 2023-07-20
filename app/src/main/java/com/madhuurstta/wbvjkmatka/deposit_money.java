package com.madhuurstta.wbvjkmatka;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dev.shreyaspatil.easyupipayment.EasyUpiPayment;
import dev.shreyaspatil.easyupipayment.listener.PaymentStatusListener;
import dev.shreyaspatil.easyupipayment.model.PaymentApp;
import dev.shreyaspatil.easyupipayment.model.TransactionDetails;
import lib.kingja.switchbutton.SwitchMultiButton;

public class deposit_money extends AppCompatActivity implements PaymentStatusListener {
    private EasyUpiPayment easyUpiPayment;
    ViewDialog progressDialog;
    String url;
    ArrayList<String> gateways = new ArrayList<>();
    String gateway = "";
    SwitchMultiButton mSwitchMultiButton;
    EditText amount;

    String url3 = constant.prefix + "upi_payment.php";
    String url2 = constant.prefix + "get_payment.php";
    String _amount = "0";
    final int UPI_PAYMENT = 0;
    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();
    String hash, hashKey;
    String package_name = "";
    PaymentApp paymentApp;
    String selectedApp = "";
    private LinearLayout paytmIcon;
    private LinearLayout gpayIcon;
    private LinearLayout phonepeIcon;
    TextView wallet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_money);
        initViews();

        wallet = findViewById(R.id.wallet);
        wallet.setText(getSharedPreferences(constant.prefs,MODE_PRIVATE).getString("wallet","0"));

        url = constant.prefix + getString(R.string.get_gateway);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        amount = findViewById(R.id.amount);
        mSwitchMultiButton = findViewById(R.id.switchmultibutton);
        findViewById(R.id.whatsapp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = constant.getWhatsapp(getApplicationContext());

                Uri uri = Uri.parse(url);
                Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(sendIntent);
            }
        });

        //apicall();

        String[] tabTexts1 = new String[3];

        gateways.add("paytm");
        tabTexts1[0] = "PAYTM";
        gateways.add("gpay");
        tabTexts1[1] = "GPAY";
        gateways.add("phonepe");
        tabTexts1[2] = "PHONEPE";


        paytmIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getSharedPreferences(constant.prefs,MODE_PRIVATE).getString("paytm","0").equals("1")) {

                    if (amount.getText().toString().isEmpty() || amount.getText().toString().equals("0")){
                        amount.setError("Enter points");
                        return;
                    } else if (Integer.parseInt(amount.getText().toString()) < Integer.parseInt(getSharedPreferences(constant.prefs,MODE_PRIVATE).getString("min_deposit","10"))){
                        amount.setError("Enter points above "+getSharedPreferences(constant.prefs,MODE_PRIVATE).getString("min_deposit","10"));
                        return;
                    }

                    apicall3("paytm","paytm");
                    mSwitchMultiButton.setSelectedTab(0);
                } else {
                    new AlertDialog.Builder(deposit_money.this)
                            .setTitle("Request feature")
                            .setMessage("Please contact admin to enable this feature for your account")
                            .setNegativeButton(android.R.string.no, null)
                            .show();
                }

            }
        });

        gpayIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amount.getText().toString().isEmpty() || amount.getText().toString().equals("0")){
                    amount.setError("Enter points");
                    return;
                } else if (Integer.parseInt(amount.getText().toString()) < Integer.parseInt(getSharedPreferences(constant.prefs,MODE_PRIVATE).getString("min_deposit","10"))){
                    amount.setError("Enter points above "+getSharedPreferences(constant.prefs,MODE_PRIVATE).getString("min_deposit","10"));
                    return;
                }

                apicall3("gpay","gpay");
                mSwitchMultiButton.setSelectedTab(1);
            }
        });

        phonepeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (amount.getText().toString().isEmpty() || amount.getText().toString().equals("0")){
                    amount.setError("Enter points");
                    return;
                } else if (Integer.parseInt(amount.getText().toString()) < Integer.parseInt(getSharedPreferences(constant.prefs,MODE_PRIVATE).getString("min_deposit","10"))){
                    amount.setError("Enter points above "+getSharedPreferences(constant.prefs,MODE_PRIVATE).getString("min_deposit","10"));
                    return;
                }

                apicall3("phonepe","phonepe");
                mSwitchMultiButton.setSelectedTab(2);
            }
        });

        if (gateways.size() > 0) {
            mSwitchMultiButton.setText(tabTexts1);
            mSwitchMultiButton.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public void onTransactionCompleted(TransactionDetails transactionDetails) {
        // Transaction Completed
        Log.d("TransactionDetails", transactionDetails.toString());

        switch (transactionDetails.getTransactionStatus()) {
            case SUCCESS:
                apicall2();
                break;
            case FAILURE:
                onTransactionFailed();
                break;
            case SUBMITTED:
                onTransactionSubmitted();
                break;
        }
    }

    @Override
    public void onTransactionCancelled() {
        // Payment Cancelled by User
        toast("Cancelled by user");
    }


    private void onTransactionSubmitted() {
        // Payment Pending
        toast("Pending | Submitted");
        apicall2();
    }

    private void onTransactionFailed() {
        // Payment Failed
        toast("Failed");
    }

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    void payUsingUpi(String amount, String name, String note, String upiApp) {

        String upi_id = "", mcc = "";
//        Log.d("printa",getSharedPreferences(constant.prefs, MODE_PRIVATE).getString("upi_3", null));
//        Log.d("printa",getSharedPreferences(constant.prefs, MODE_PRIVATE).getString("upi", null));
//        Log.d("printa",getSharedPreferences(constant.prefs, MODE_PRIVATE).getString("upi_2", null));
        String description = "add fund";
        Log.d("printa",amount);
        selectedApp = upiApp;
        String payeeVpa = "";
        String payeeName = name;
        switch (upiApp) {
            case "gpay":
                paymentApp = PaymentApp.GOOGLE_PAY;
                payeeVpa = getSharedPreferences(constant.prefs, MODE_PRIVATE).getString("upi_3", null);
                mcc = getSharedPreferences(constant.prefs, MODE_PRIVATE).getString("merchant_3", null);
                description = "GOOGLE_PAY";
                break;
            case "paytm":
                paymentApp = PaymentApp.PAYTM;
                payeeVpa = getSharedPreferences(constant.prefs, MODE_PRIVATE).getString("upi", null);
                mcc = getSharedPreferences(constant.prefs, MODE_PRIVATE).getString("merchant", null);
                description = "PAYTM";

                break;
            case "phonepe":
                paymentApp = PaymentApp.PHONE_PE;
                payeeVpa = getSharedPreferences(constant.prefs, MODE_PRIVATE).getString("upi_2", null);
                mcc = getSharedPreferences(constant.prefs, MODE_PRIVATE).getString("merchant_2", null);
                description = "PHONE_PE";

                break;
            default:
                Toast.makeText(this, "We are unable to proceed your request, Please select any other gateway option if avaialble", Toast.LENGTH_SHORT).show();
                break;
        }


        String transactionId = "TID" + System.currentTimeMillis();
        String transactionRefId = transactionId+"_"+ System.currentTimeMillis();

        Log.d("sxadsdas",payeeVpa);
        Log.d("sxadsdas",payeeName);
        Log.d("sxadsdas",transactionId);
        Log.d("sxadsdas",transactionRefId);
        Log.d("sxadsdas","payeeMerchantCode");
        Log.d("sxadsdas",description);
        Log.d("sxadsdas",amount);

        // START PAYMENT INITIALIZATION
        EasyUpiPayment.Builder builder = new EasyUpiPayment.Builder(this)
                .with(paymentApp)
                .setPayeeVpa(payeeVpa)
                .setPayeeName(payeeName)
                .setTransactionId(transactionId)
                .setTransactionRefId(transactionRefId)
                .setPayeeMerchantCode("")
                .setDescription(description)
                .setAmount(amount);
        // END INITIALIZATION


        try {
            // Build instance
            easyUpiPayment = builder.build();

            // Register Listener for Events
            easyUpiPayment.setPaymentStatusListener(this);

            // Start payment / transaction
            easyUpiPayment.startPayment();
        } catch (Exception exception) {
            exception.printStackTrace();
            toast("Error: " + exception.getMessage());
        }


    }




    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }


    String randomString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }


    private void apicall3(String upiApp, String type) {

        progressDialog = new ViewDialog(deposit_money.this);
        progressDialog.showDialog();

        hashKey = randomString(10);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String response = null;

        final StringRequest postRequest = new StringRequest(Request.Method.POST, url3,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("edsa", "efsdc" + response);
                        progressDialog.hideDialog();
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);

                            if (jsonObject1.getString("success").equals("1")) {
                                hash = jsonObject1.getString("hash");
                                payUsingUpi(amount.getText().toString()+".00", getString(R.string.app_name), "Adding coins to wallet", type);
                            } else {
                                Toast.makeText(deposit_money.this, jsonObject1.getString("msg"), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(deposit_money.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("mobile", getSharedPreferences(constant.prefs, MODE_PRIVATE).getString("mobile", null));
                params.put("session", getSharedPreferences(constant.prefs, MODE_PRIVATE).getString("session", null));
                params.put("amount", amount.getText().toString());
                params.put("hash_key", hashKey);
                params.put("type", type);

                Log.e("api3param",params.toString());

                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
    }

    private void apicall2() {

        progressDialog = new ViewDialog(deposit_money.this);
        progressDialog.showDialog();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String response = null;

        final StringRequest postRequest = new StringRequest(Request.Method.POST, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("edsa", "efsdc" + response);
                        progressDialog.hideDialog();
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);

                            if (jsonObject1.getString("success").equals("0")) {
                                new AlertDialog.Builder(deposit_money.this)
                                        .setTitle("Payment Received")
                                        .setMessage("We receieved your payment successfully, We will update your wallet balance in sometime")
                                        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                startActivity(new Intent(getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                                finish();
                                            }
                                        })
                                        .show();

                            } else {
                                Toast.makeText(deposit_money.this, "Coins added to wallet", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                finish();
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
                        Toast.makeText(deposit_money.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("mobile", getSharedPreferences(constant.prefs, MODE_PRIVATE).getString("mobile", null));
                params.put("hash_key", hashKey);
                params.put("hash", hash);
                params.put("amount", amount.getText().toString());
                params.put("session", getSharedPreferences(constant.prefs, MODE_PRIVATE).getString("session", null));

                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
    }


    private void apicall() {

        progressDialog = new ViewDialog(deposit_money.this);
        progressDialog.showDialog();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        final StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("edsa", "efsdc" + response);
                        progressDialog.hideDialog();
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);

                            JSONArray jsonArray = jsonObject1.getJSONArray("data");

                            if (jsonArray.length() == 1) {

                                gateway = jsonArray.getJSONObject(0).getString("name").toLowerCase();
                                return;
                            }
                            String[] tabTexts1 = new String[jsonArray.length()];

                            for (int a = 0; jsonArray.length() > a; a++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(a);
                                gateways.add(jsonObject.getString("name").toLowerCase());
                                tabTexts1[a] = jsonObject.getString("name").toUpperCase();
                            }

                            if (gateways.size() > 0) {
                                mSwitchMultiButton.setText(tabTexts1);
                                mSwitchMultiButton.setVisibility(View.VISIBLE);
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
                        Toast.makeText(deposit_money.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
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
        amount = findViewById(R.id.amount);
        paytmIcon = findViewById(R.id.paytm_icon);
        gpayIcon = findViewById(R.id.gpay_icon);
        phonepeIcon = findViewById(R.id.phonepe_icon);
    }
}