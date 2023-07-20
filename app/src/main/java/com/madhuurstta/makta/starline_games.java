package com.madhuurstta.makta;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class starline_games extends AppCompatActivity {

    private ImageView back;
    private ImageView single;
    private ImageView singlepatti;
    private ImageView doublepatti;
    private ImageView tripepatti;

    ArrayList<String> number = new ArrayList<>();
    String market, timing;
    private latobold title;
    private ImageView oddEven;
    private ImageView spdptp;
    private ImageView spMotor;
    private ImageView dpMotor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starline_games);
        initViews();
        market = getIntent().getStringExtra("market");
        timing = getIntent().getStringExtra("timing");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        spdptp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                single();
                startActivity(new Intent(starline_games.this, SpDpTp.class).putExtra("market", market).putExtra("list", number).putExtra("game", "singlepatti").putExtra("timing", timing).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });



        spMotor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                single();
                startActivity(new Intent(starline_games.this, SpMotor.class).putExtra("market", market).putExtra("list", number).putExtra("game", "singlepatti").putExtra("timing", timing).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });


        dpMotor.setOnClickListener(v -> {
            single();
            startActivity(new Intent(starline_games.this, SpMotor.class).putExtra("market", market).putExtra("list", number).putExtra("game", "doublepatti").putExtra("timing", timing).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        });

        single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                single();
                startActivity(new Intent(starline_games.this, single_bet.class).putExtra("market", market).putExtra("list", number).putExtra("game", "single").putExtra("timing", timing).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

        singlepatti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singlepatti();
                startActivity(new Intent(starline_games.this, single_bet.class).putExtra("market", market).putExtra("list", number).putExtra("game", "singlepatti").putExtra("timing", timing).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

        doublepatti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doublepatti();
                startActivity(new Intent(starline_games.this, single_bet.class).putExtra("market", market).putExtra("list", number).putExtra("game", "doublepatti").putExtra("timing", timing).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

        tripepatti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                triplepatti();
                startActivity(new Intent(starline_games.this, single_bet.class).putExtra("market", market).putExtra("list", number).putExtra("game", "tripepatti").putExtra("timing", timing).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });


        oddEven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                single();
                startActivity(new Intent(starline_games.this, OddEven.class).putExtra("market", market).putExtra("list", number).putExtra("game", "single").putExtra("timing", timing).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });


    }

    private void initViews() {
        back = findViewById(R.id.back);
        single = findViewById(R.id.single);
        singlepatti = findViewById(R.id.singlepatti);
        doublepatti = findViewById(R.id.doublepatti);
        tripepatti = findViewById(R.id.tripepatti);
        title = findViewById(R.id.title);
        oddEven = findViewById(R.id.odd_even);
        spdptp = findViewById(R.id.spdptp);
        spMotor = findViewById(R.id.sp_motor);
        dpMotor = findViewById(R.id.dp_motor);
    }


    public void single() {
        number.clear();
        number.add("0");
        number.add("1");
        number.add("2");
        number.add("3");
        number.add("4");
        number.add("5");
        number.add("6");
        number.add("7");
        number.add("8");
        number.add("9");
    }

    public void doublepatti() {
        number.clear();
        number.add("100");
        number.add("119");
        number.add("155");
        number.add("227");
        number.add("335");
        number.add("344");
        number.add("399");
        number.add("588");
        number.add("669");
        number.add("200");
        number.add("110");
        number.add("228");
        number.add("255");
        number.add("336");
        number.add("499");
        number.add("660");
        number.add("688");
        number.add("778");
        number.add("300");
        number.add("166");
        number.add("229");
        number.add("337");
        number.add("355");
        number.add("445");
        number.add("599");
        number.add("779");
        number.add("788");
        number.add("400");
        number.add("112");
        number.add("220");
        number.add("266");
        number.add("338");
        number.add("446");
        number.add("455");
        number.add("699");
        number.add("770");
        number.add("500");
        number.add("113");
        number.add("122");
        number.add("177");
        number.add("339");
        number.add("366");
        number.add("447");
        number.add("799");
        number.add("889");
        number.add("600");
        number.add("114");
        number.add("277");
        number.add("330");
        number.add("448");
        number.add("466");
        number.add("556");
        number.add("880");
        number.add("899");
        number.add("700");
        number.add("115");
        number.add("133");
        number.add("188");
        number.add("223");
        number.add("377");
        number.add("449");
        number.add("557");
        number.add("566");
        number.add("800");
        number.add("116");
        number.add("224");
        number.add("233");
        number.add("288");
        number.add("440");
        number.add("477");
        number.add("558");
        number.add("990");
        number.add("900");
        number.add("117");
        number.add("144");
        number.add("199");
        number.add("225");
        number.add("388");
        number.add("559");
        number.add("577");
        number.add("667");
        number.add("550");
        number.add("668");
        number.add("244");
        number.add("299");
        number.add("226");
        number.add("488");
        number.add("677");
        number.add("118");
        number.add("334");
    }


    public void singlepatti() {
        number.clear();
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
        number.add("589");
    }


    public void triplepatti() {
        number.clear();
        number.add("000");
        number.add("111");
        number.add("222");
        number.add("333");
        number.add("444");
        number.add("555");
        number.add("666");
        number.add("777");
        number.add("888");
        number.add("999");
    }


}