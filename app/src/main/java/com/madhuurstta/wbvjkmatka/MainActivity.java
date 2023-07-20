package com.madhuurstta.wbvjkmatka;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    SharedPreferences preferences;
    String url;
    String is_gateway = "0";


    SliderView sliderView;
    private SliderAdapter adapter;
    private latonormal name;
    private latonormal mobile;
    private LinearLayout profile;
    private LinearLayout wallet;
    private LinearLayout gameHistory;
    private LinearLayout gameRate;
    private LinearLayout addPoints;
    private LinearLayout withdrawPoints;
    private LinearLayout bankDetails;
    private LinearLayout transferCoins;
    private LinearLayout howToPlay;
    private LinearLayout contactUs;
    private LinearLayout shareNow;
    private LinearLayout rateUs;
    private LinearLayout logout;
    private NavigationView navView;
    private ImageView loadingGif;
    private ImageView back;
    private RelativeLayout notIcon;
    private SwitchCompat resultNotification;
    private LinearLayout notView;
    private ImageView coin;
    private latobold balance;
    private LinearLayout walletView;
    private RelativeLayout toolbar;
    private SliderView imageSlider;
    private LinearLayout withdraw;
    private LinearLayout addMoney;
    private LinearLayout playStarline;
    private LinearLayout playDelhi;
    private LinearLayout callFigma;
    private RecyclerView recyclerview;
    private SwipeRefreshLayout swiperefresh;
    private DrawerLayout drawer;
    private LinearLayout whatsapp;
    private LinearLayout telegram,bid_history_2,winning_2,transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        initViews();
        url = constant.prefix + getString(R.string.home);

        bid_history_2 = findViewById(R.id.bid_history_2);
        winning_2 = findViewById(R.id.winning_2);
        transaction = findViewById(R.id.transaction);

        bid_history_2.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, played.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)));
        winning_2.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ledger.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)));
        transaction.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, transactions.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)));

        notIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultNotification.setChecked(!resultNotification.isChecked());
            }
        });



        callFigma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = getSharedPreferences(constant.prefs, Context.MODE_PRIVATE).getString("whatsapp", null);
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
            }
        });
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = constant.getWhatsapp(getApplicationContext());

                Uri uri = Uri.parse(url);
                Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(sendIntent);
            }
        });

        walletView.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Wallet.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)));

        wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Wallet.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
        addMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, deposit_money.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
        rateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, rate.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, withdraw.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
        withdrawPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, withdraw.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

        playDelhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DelhiJodiMarkets.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });


        playStarline.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, starline_timings.class).putExtra("market", "Madhur Satta")
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)));


        apicall();

        if (preferences.getString("wallet", null) != null) {
            balance.setText(preferences.getString("wallet", null));
        } else {
            balance.setText("Loading");
        }


    }


    private void apicall() {


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        final StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response", response);
                        try {

                            JSONObject jsonObject1 = new JSONObject(response);

                            SharedPreferences.Editor editor = preferences.edit();
                            if (jsonObject1.getString("active").equals("0")) {
                                Toast.makeText(MainActivity.this, "Your account temporarily disabled by admin", Toast.LENGTH_SHORT).show();

                                preferences.edit().clear().apply();
                                Intent in = new Intent(getApplicationContext(), login.class);
                                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(in);
                                finish();
                                return;
                            }

                            if (jsonObject1.getString("session").equals("0")) {
                                Toast.makeText(MainActivity.this, "Session expired ! Please login again", Toast.LENGTH_SHORT).show();

                                preferences.edit().clear().apply();
                                Intent in = new Intent(getApplicationContext(), login.class);
                                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(in);
                                finish();
                                return;
                            }

                            editor.putString("verify", jsonObject1.getString("verify")).apply();
                            balance.setText(jsonObject1.getString("wallet"));


                            ArrayList<String> namex = new ArrayList<>();
                            ArrayList<String> result = new ArrayList<>();

                            ArrayList<String> is_open = new ArrayList<>();
                            ArrayList<String> open_time = new ArrayList<>();
                            ArrayList<String> close_time = new ArrayList<>();
                            ArrayList<String> open_av = new ArrayList<>();

                            JSONArray jsonArray = jsonObject1.getJSONArray("result");
                            for (int a = 0; a < jsonArray.length(); a++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(a);

                                open_time.add(jsonObject.getString("open_time"));
                                close_time.add(jsonObject.getString("close_time"));
                                namex.add(jsonObject.getString("market"));
                                result.add(jsonObject.getString("result"));
                                is_open.add(jsonObject.getString("is_open"));
                                open_av.add(jsonObject.getString("is_close"));

                            }


                            adapter_result rc = new adapter_result(MainActivity.this, namex, result, is_open, open_time, close_time, open_av);
                            recyclerview.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));
                            recyclerview.setAdapter(rc);


                            if (jsonObject1.has("images")) {
                                adapter = new SliderAdapter(MainActivity.this);

                                JSONArray jsonArrayx = jsonObject1.getJSONArray("images");
                                for (int a = 0; a < jsonArrayx.length(); a++) {
                                    JSONObject jsonObject = jsonArrayx.getJSONObject(a);


                                    SliderItem sliderItem1 = new SliderItem();
                                    sliderItem1.setImageUrl(constant.project_root + "admin/" + jsonObject.getString("image"));
                                    sliderItem1.setData(jsonObject.getString("data"));
                                    sliderItem1.setRefer(jsonObject.getString("refer"));
//                                    sliderItem1.setScreen(jsonObject.getString("screen"));

                                    if (sliderItem1.getRefer().equals("market")) {
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put("market", jsonObject.getString("market"));
                                        params.put("is_open", jsonObject.getString("is_open"));
                                        params.put("is_close", jsonObject.getString("is_close"));
                                        params.put("open_time", jsonObject.getString("open_time"));
                                        params.put("close_time", jsonObject.getString("close_time"));
                                        sliderItem1.setParams(params);
                                    }
                                    adapter.addItem(sliderItem1);


                                }

                                sliderView.setSliderAdapter(adapter);
                            } else {
                                sliderView.setVisibility(View.GONE);
                            }

                            editor.putString("wallet", jsonObject1.getString("wallet")).apply();
                            editor.putString("homeline", jsonObject1.getString("homeline")).apply();
                            editor.putString("code", jsonObject1.getString("code")).apply();
                            editor.putString("is_gateway", jsonObject1.getString("gateway")).apply();
                            editor.putString("whatsapp", jsonObject1.getString("whatsapp")).apply();
                            editor.putString("transfer_points_status", jsonObject1.getString("transfer_points_status")).apply();
                            editor.putString("paytm", jsonObject1.getString("paytm")).apply();
                            is_gateway = jsonObject1.getString("gateway");

                            name.setText(jsonObject1.getString("name"));
                            mobile.setText(getSharedPreferences(constant.prefs, MODE_PRIVATE).getString("mobile", ""));

                            if (jsonObject1.getString("verify").equals("1")) {
                                // verifyMenu();
                            } else {
                                //   unVerifyMenu();
                                playStarline.setVisibility(View.GONE);
                                walletView.setVisibility(View.GONE);

                                wallet.setVisibility(View.GONE);
                                gameHistory.setVisibility(View.GONE);
                                gameRate.setVisibility(View.GONE);
                                addPoints.setVisibility(View.GONE);
                                withdrawPoints.setVisibility(View.GONE);
                                bankDetails.setVisibility(View.GONE);
                                bankDetails.setVisibility(View.GONE);
                                walletView.setVisibility(View.GONE);
                                addMoney.setVisibility(View.GONE);
                                transferCoins.setVisibility(View.GONE);
                                walletView.setVisibility(View.GONE);
                                addMoney.setVisibility(View.GONE);
                                rateUs.setVisibility(View.GONE);
                                withdraw.setVisibility(View.GONE);
                                playDelhi.setVisibility(View.GONE);
                                bid_history_2.setVisibility(View.GONE);
                                winning_2.setVisibility(View.GONE);
                                transaction.setVisibility(View.GONE);
                            }

                            if (swiperefresh.isRefreshing()) {
                                swiperefresh.setRefreshing(false);
                            }


                            if (swiperefresh.getVisibility() == View.GONE) {
                            //    Glide.with(MainActivity.this).load(R.drawable.logo).into(loadingGif);
                                loadingGif.setVisibility(View.GONE);
                                swiperefresh.setVisibility(View.VISIBLE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        error.printStackTrace();

                        Toast.makeText(MainActivity.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("app", "kalyanpro");
                params.put("mobile", preferences.getString("mobile", null));
                params.put("session", getSharedPreferences(constant.prefs, MODE_PRIVATE).getString("session", null));

                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
    }

    @Override
    protected void onResume() {
        apicall();
        super.onResume();
    }

    private void openWhatsApp() {
        String url = constant.getWhatsapp(getApplicationContext());

        Uri uri = Uri.parse(url);
        Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(sendIntent);
    }


    private void initMain() {
        preferences = getSharedPreferences(constant.prefs, MODE_PRIVATE);

        if (preferences.getString("result", null) != null) {
            resultNotification.setChecked(preferences.getString("result", null).equals("1"));
        } else {
            resultNotification.setChecked(false);
        }

        resultNotification.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                FirebaseMessaging.getInstance().subscribeToTopic("result")
                        .addOnCompleteListener(task2 -> {
                            preferences.edit().putString("result", "1").apply();
                        });
            } else {
                FirebaseMessaging.getInstance().unsubscribeFromTopic("result")
                        .addOnCompleteListener(task2 -> {
                            preferences.edit().putString("result", "0").apply();
                        });
            }
        });


        sliderView = findViewById(R.id.imageSlider);
        swiperefresh.setVisibility(View.GONE);
     //   Glide.with(MainActivity.this).load(R.drawable.loading_animation).into(loadingGif);
        loadingGif.setVisibility(View.VISIBLE);

        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                apicall();
            }
        });


        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

        addMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, deposit_money.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });


        addPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, deposit_money.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, withdraw.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });


        profile.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, profile.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)));
        wallet.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Wallet.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)));
        gameHistory.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, played.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)));
        gameRate.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, rate.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)));
        addPoints.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, deposit_money.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)));
        withdrawPoints.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, withdraw.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)));
        bankDetails.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, WithdrawDetails.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)));
        howToPlay.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, howot.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)));
        contactUs.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, howot.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)));
        transferCoins.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TransferCoin.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)));

        rateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = "market://details?id=";
                try {
                    // play market available
                    getPackageManager()
                            .getPackageInfo("com.android.vending", 0);
                    // not available
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                    // should use browser
                    link = "https://play.google.com/store/apps/details?id=";
                }
                // starts external action
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(link + getPackageName())));
            }
        });

        shareNow.setOnClickListener(v -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Download " + getString(R.string.app_name) + " and earn coins at home, Download link - " + constant.link);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        });

        logout.setOnClickListener(v -> {
            preferences.edit().clear().apply();
            Intent in = new Intent(getApplicationContext(), splash.class);
            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(in);
            finish();
        });
        navView.bringToFront();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawers();
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        if (preferences.getString("telegram", "0").equals("1")) {
            telegram.setVisibility(View.VISIBLE);
            telegram.setOnClickListener(v -> {
                String url = getSharedPreferences(constant.prefs, MODE_PRIVATE).getString("telegram_details", "");

                Uri uri = Uri.parse(url);
                Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(sendIntent);
            });
        } else {
            telegram.setVisibility(View.GONE);
        }

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }
        super.onBackPressed();
    }

    private void initViews() {
        name = findViewById(R.id.name);
        mobile = findViewById(R.id.mobile);
        profile = findViewById(R.id.profile);
        wallet = findViewById(R.id.wallet);
        gameHistory = findViewById(R.id.game_history);
        gameRate = findViewById(R.id.game_rate);
        addPoints = findViewById(R.id.add_points);
        withdrawPoints = findViewById(R.id.withdraw_points);
        bankDetails = findViewById(R.id.bank_details);
        transferCoins = findViewById(R.id.transfer_coins);
        howToPlay = findViewById(R.id.how_to_play);
        contactUs = findViewById(R.id.contact_us);
        shareNow = findViewById(R.id.share_now);
        rateUs = findViewById(R.id.rate_us);
        logout = findViewById(R.id.logout);
        navView = findViewById(R.id.navView);
        loadingGif = findViewById(R.id.loading_gif);
        back = findViewById(R.id.back);
        notIcon = findViewById(R.id.not_icon);
        resultNotification = findViewById(R.id.resultNotification);
        notView = findViewById(R.id.not_view);
        coin = findViewById(R.id.coin);
        balance = findViewById(R.id.balance);
        walletView = findViewById(R.id.wallet_view);
        toolbar = findViewById(R.id.toolbar);
        imageSlider = findViewById(R.id.imageSlider);
        withdraw = findViewById(R.id.withdraw);
        addMoney = findViewById(R.id.add_money);
        playStarline = findViewById(R.id.play_starline);
        playDelhi = findViewById(R.id.play_delhi);
        callFigma = findViewById(R.id.call_figma);
        recyclerview = findViewById(R.id.recyclerview);
        swiperefresh = findViewById(R.id.swiperefresh);
        drawer = findViewById(R.id.drawer);
        whatsapp = findViewById(R.id.whatsapp);
        telegram = findViewById(R.id.telegram);

        initMain();
    }
}
