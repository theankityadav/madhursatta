package com.madhuurstta.makta;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class halfsangam extends AppCompatActivity {

    protected Spinner type;
    protected latobold submit;
    protected ScrollView scrollView;
    protected Spinner first;
    protected Spinner second;
    protected latonormal firstitle;
    protected latonormal secondtitle;
    protected EditText totalamount;

    ArrayList<String> typeof = new ArrayList<>();
    ArrayList<String> ank = new ArrayList<>();
    ArrayList<String> patti = new ArrayList<>();

    String market, game;

    SharedPreferences prefs;

    ViewDialog progressDialog;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_halfsangam);
        initView();
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        url = constant.prefix + getString(R.string.sangam);
        prefs = getSharedPreferences(constant.prefs, MODE_PRIVATE);

        game = getIntent().getStringExtra("game");
        market = getIntent().getStringExtra("market");

        ank.add("0");
        ank.add("1");
        ank.add("2");
        ank.add("3");
        ank.add("4");
        ank.add("5");
        ank.add("6");
        ank.add("7");
        ank.add("8");
        ank.add("9");

        patti.addAll(getpatti());

        typeof.add("Open Ank Close Patti");
        typeof.add("Open Patti Close Ank");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(halfsangam.this, R.layout.simple_list_item_2, typeof);
        type.setAdapter(arrayAdapter);

        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(halfsangam.this, R.layout.simple_list_item_2, ank);
        first.setAdapter(arrayAdapter2);

        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(halfsangam.this, R.layout.simple_list_item_2, patti);
        second.setAdapter(arrayAdapter3);

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(halfsangam.this, R.layout.simple_list_item_2, ank);
                    first.setAdapter(arrayAdapter2);

                    ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(halfsangam.this, R.layout.simple_list_item_2, patti);
                    second.setAdapter(arrayAdapter3);

                    firstitle.setText("Ank");
                    secondtitle.setText("Patti");
                } else {
                    ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(halfsangam.this, R.layout.simple_list_item_2, ank);
                    second.setAdapter(arrayAdapter2);

                    ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(halfsangam.this, R.layout.simple_list_item_2, patti);
                    first.setAdapter(arrayAdapter3);

                    firstitle.setText("Patti");
                    secondtitle.setText("Ank");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (first.getSelectedItem().toString().contains("Line") || second.getSelectedItem().toString().contains("Line"))
                {
                    Toast.makeText(halfsangam.this, "Please select A valid number", Toast.LENGTH_SHORT).show();
                }
                else if (Integer.parseInt(totalamount.getText().toString()) < Integer.parseInt(prefs.getString("wallet",null))) {
                    apicall();
                }
                else
                {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(halfsangam.this);
                    builder1.setMessage("You don't have enough wallet balance to place this bet, Recharge your wallet to play");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "Recharge",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    openWhatsApp();
                                    dialog.dismiss();
                                }
                            });

                    builder1.setNegativeButton(
                            "Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
            }
        });
    }


    private void apicall() {

        progressDialog = new ViewDialog(halfsangam.this);
        progressDialog.showDialog();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        final StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.hideDialog();
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);
                            if (jsonObject1.getString("success").equalsIgnoreCase("1")) {

                                Intent in = new Intent(getApplicationContext(), thankyou.class);
                                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(in);
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
                        Toast.makeText(halfsangam.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("number", first.getSelectedItem().toString()+" - "+second.getSelectedItem().toString());
                params.put("amount", totalamount.getText().toString());
                params.put("bazar", market);
                params.put("total", totalamount.getText().toString());
                params.put("game", game);
                params.put("mobile", prefs.getString("mobile", null));

                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
    }

    private void openWhatsApp() {
        String url = constant.getWhatsapp(getApplicationContext());

        Uri uri = Uri.parse(url);
        Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(sendIntent);
    }



    public ArrayList<String> getpatti() {

        ArrayList<String> number = new ArrayList<>();

        // 1
        number.add("Line of 1");
        number.add("100");
        number.add("119");
        number.add("155");
        number.add("227");
        number.add("335");
        number.add("344");
        number.add("399");
        number.add("588");
        number.add("669");
        number.add("128");
        number.add("137");
        number.add("146");
        number.add("236");
        number.add("245");
        number.add("290");
        number.add("380");
        number.add("470");
        number.add("489");
        number.add("560");
        number.add("678");
        number.add("579");

        //2
        number.add("Line of 2");
        number.add("200");
        number.add("110");
        number.add("228");
        number.add("255");
        number.add("336");
        number.add("499");
        number.add("660");
        number.add("688");
        number.add("778");
        number.add("129");
        number.add("138");
        number.add("147");
        number.add("156");
        number.add("237");
        number.add("246");
        number.add("345");
        number.add("390");
        number.add("480");
        number.add("570");
        number.add("679");
        number.add("589");

        // 3
        number.add("Line of 3");
        number.add("300");
        number.add("166");
        number.add("229");
        number.add("337");
        number.add("355");
        number.add("445");
        number.add("599");
        number.add("779");
        number.add("788");
        number.add("120");
        number.add("139");
        number.add("148");
        number.add("157");
        number.add("238");
        number.add("247");
        number.add("256");
        number.add("346");
        number.add("490");
        number.add("580");
        number.add("670");
        number.add("689");

        // 4
        number.add("Line of 4");
        number.add("400");
        number.add("112");
        number.add("220");
        number.add("266");
        number.add("338");
        number.add("446");
        number.add("455");
        number.add("699");
        number.add("770");
        number.add("130");
        number.add("149");
        number.add("158");
        number.add("167");
        number.add("239");
        number.add("248");
        number.add("257");
        number.add("347");
        number.add("356");
        number.add("590");
        number.add("680");
        number.add("789");

        // 5
        number.add("Line of 5");
        number.add("500");
        number.add("113");
        number.add("122");
        number.add("177");
        number.add("339");
        number.add("366");
        number.add("447");
        number.add("799");
        number.add("889");
        number.add("140");
        number.add("159");
        number.add("168");
        number.add("230");
        number.add("249");
        number.add("258");
        number.add("267");
        number.add("348");
        number.add("357");
        number.add("456");
        number.add("690");
        number.add("780");

        // 6
        number.add("Line of 6");
        number.add("600");
        number.add("114");
        number.add("277");
        number.add("330");
        number.add("448");
        number.add("466");
        number.add("556");
        number.add("880");
        number.add("899");
        number.add("123");
        number.add("150");
        number.add("169");
        number.add("178");
        number.add("240");
        number.add("259");
        number.add("268");
        number.add("349");
        number.add("358");
        number.add("457");
        number.add("367");
        number.add("790");

        // 7
        number.add("Line of 7");
        number.add("700");
        number.add("115");
        number.add("133");
        number.add("188");
        number.add("223");
        number.add("377");
        number.add("449");
        number.add("557");
        number.add("566");
        number.add("124");
        number.add("160");
        number.add("179");
        number.add("250");
        number.add("269");
        number.add("278");
        number.add("340");
        number.add("359");
        number.add("368");
        number.add("458");
        number.add("467");
        number.add("890");

        // 8
        number.add("Line of 8");
        number.add("800");
        number.add("116");
        number.add("224");
        number.add("233");
        number.add("288");
        number.add("440");
        number.add("477");
        number.add("558");
        number.add("990");
        number.add("125");
        number.add("134");
        number.add("170");
        number.add("189");
        number.add("260");
        number.add("279");
        number.add("350");
        number.add("369");
        number.add("378");
        number.add("459");
        number.add("567");
        number.add("468");

        // 9
        number.add("Line of 9");
        number.add("900");
        number.add("117");
        number.add("144");
        number.add("199");
        number.add("225");
        number.add("388");
        number.add("559");
        number.add("577");
        number.add("667");
        number.add("126");
        number.add("135");
        number.add("180");
        number.add("234");
        number.add("270");
        number.add("289");
        number.add("360");
        number.add("379");
        number.add("450");
        number.add("469");
        number.add("478");
        number.add("568");

        // 0
        number.add("Line of 0");
        number.add("550");
        number.add("668");
        number.add("244");
        number.add("299");
        number.add("226");
        number.add("488");
        number.add("677");
        number.add("118");
        number.add("334");
        number.add("127");
        number.add("136");
        number.add("145");
        number.add("190");
        number.add("235");
        number.add("280");
        number.add("370");
        number.add("479");
        number.add("460");
        number.add("569");
        number.add("389");
        number.add("578");


        return number;
    }




    private void initView() {
        type = (Spinner) findViewById(R.id.type);
        submit = (latobold) findViewById(R.id.submit);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        first = (Spinner) findViewById(R.id.first);
        second = (Spinner) findViewById(R.id.second);
        firstitle = (latonormal) findViewById(R.id.firstitle);
        secondtitle = (latonormal) findViewById(R.id.secondtitle);
        totalamount = (EditText) findViewById(R.id.totalamount);
    }
}
