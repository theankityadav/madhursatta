package com.madhuurstta.makta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Random;

public class OTPVerification extends AppCompatActivity {

    private EditText otp1;
    private EditText otp2;
    private EditText otp3;
    private EditText otp4;
    private TextView verify;
    private TextView resendButton;

    String mobileNumber = "", otp = "";
    String otpId;
    ViewDialog progressDialog;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);
        context = this;
        initViews();
        mobileNumber = getIntent().getStringExtra("mobile");
        Random random = new Random();
        // otp = String.format("%04d", random.nextInt(10000));

        otp = String.format("%04d", random.nextInt(10000));

        otp1.addTextChangedListener(new GenericTextWatcher(otp1));
        otp2.addTextChangedListener(new GenericTextWatcher(otp2));
        otp3.addTextChangedListener(new GenericTextWatcher(otp3));
        otp4.addTextChangedListener(new GenericTextWatcher(otp4));

        apicall();


        verify.setOnClickListener(view -> {
            if (otp == null) return;
            if (getOtp().isEmpty() || getOtp().length() != 4){
                Toast.makeText(this, "Enter OTP", Toast.LENGTH_SHORT).show();
                return;
            }
            if (getOtp().equals(otp)){
                Intent intent=new Intent();
                intent.putExtra("verification","success");
                setResult(RESULT_OK,intent);
                finish();
            } else {
                Toast.makeText(context, "Invalid OTP", Toast.LENGTH_SHORT).show();
            }

        });


        resendButton.setOnClickListener(view -> {
            if (resendButton.getText().toString().equals(getString(R.string.resend_otp))) {
                apicall();
            } else {
                Toast.makeText(this, "Wait before resend", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setVerify() {

        progressDialog = new ViewDialog(OTPVerification.this);
        progressDialog.showDialog();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        final StringRequest postRequest = new StringRequest(Request.Method.POST, "url",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.hideDialog();
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);
                            if (jsonObject1.getString("success").equalsIgnoreCase("1")) {

                                Intent intent=new Intent();
                                intent.putExtra("verification","success");
                                setResult(RESULT_OK,intent);
                                finish();

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
                        Toast.makeText(context, "Check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("otp", getOtp());
                params.put("otp_id",otpId);

                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
    }

    private void apicall() {

        progressDialog = new ViewDialog(OTPVerification.this);
        progressDialog.showDialog();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        final StringRequest postRequest = new StringRequest(Request.Method.POST, constant.prefix+"send_otp.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response",response);
                        progressDialog.hideDialog();
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);
                            if (jsonObject1.getString("Status").equalsIgnoreCase("Success")) {

                                //   otpId = jsonObject1.getString("otp_id");


                                Toast.makeText(context, "OTP Sent", Toast.LENGTH_SHORT).show();


                                new CountDownTimer(60000, 1000) {

                                    public void onTick(long millisUntilFinished) {
                                        resendButton.setText("wait " + millisUntilFinished / 1000+" sec");
                                        //here you can have your logic to set text to edittext
                                    }

                                    public void onFinish() {
                                        resendButton.setText(getString(R.string.resend_otp));
                                    }

                                }.start();

                            } else {
                                Toast.makeText(getApplicationContext(), "OTP not sent, try again later", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(context, "Check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("mobile", mobileNumber);
                params.put("code","38ho3f3ws");
                params.put("otp", otp);

                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
    }




    private void initViews() {
        otp1 = findViewById(R.id.otp1);
        otp2 = findViewById(R.id.otp2);
        otp3 = findViewById(R.id.otp3);
        otp4 = findViewById(R.id.otp4);
        verify = findViewById(R.id.verify);
        resendButton = findViewById(R.id.resend_button);


    }

    public String getOtp(){
        return otp1.getText().toString()+otp2.getText().toString()+otp3.getText().toString()+otp4.getText().toString();
    }

    public class GenericTextWatcher implements TextWatcher {
        private View view;

        private GenericTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // TODO Auto-generated method stub
            String text = editable.toString();
            switch (view.getId()) {

                case R.id.otp1:
                    if (text.length() == 1)
                        otp2.requestFocus();
                    break;
                case R.id.otp2:
                    if (text.length() == 1)
                        otp3.requestFocus();
                    else if (text.length() == 0)
                        otp1.requestFocus();
                    break;
                case R.id.otp3:
                    if (text.length() == 1)
                        otp4.requestFocus();
                    else if (text.length() == 0)
                        otp2.requestFocus();
                    break;
                case R.id.otp4:
                    if (text.length() == 0)
                        otp3.requestFocus();
                    break;

            }
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            String text = arg0.toString();
            switch (view.getId()) {

                case R.id.otp1:
                    if (text.length() == 1)
                        otp2.requestFocus();
                    break;
                case R.id.otp2:
                    if (text.length() == 1)
                        otp3.requestFocus();
                    else if (text.length() == 0)
                        otp1.requestFocus();
                    break;
                case R.id.otp3:
                    if (text.length() == 1)
                        otp4.requestFocus();
                    else if (text.length() == 0)
                        otp2.requestFocus();
                    break;
                case R.id.otp4:
                    if (text.length() == 0)
                        otp3.requestFocus();
                    break;

            }
        }
    }
}