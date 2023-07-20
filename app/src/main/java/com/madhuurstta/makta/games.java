package com.madhuurstta.makta;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class games extends AppCompatActivity {

    private ImageView single;
    private ImageView jodi;
    private ImageView singlepatti;
    private ImageView doublepatti;
    private ImageView tripepatti;
    private ImageView halfsangam;
    private ImageView fullsangam;
    private ImageView crossing;
    TextView title, timing;
    ArrayList<String> number = new ArrayList<>();
    String market = "", is_open = "0", is_close = "0";
    private ImageView back;
    private ImageView oddEven;
    private ImageView redBracket;
    private ImageView spdptp;
    private ImageView spMotor;
    private ImageView dpMotor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);
        initViews();
        market = getIntent().getStringExtra("market");
        is_open = getIntent().getStringExtra("is_open");
        is_close = getIntent().getStringExtra("is_close");

        title.setText(market);
        timing.setText(getIntent().getStringExtra("timing"));

        findViewById(R.id.back).setOnClickListener(v -> finish());

        single.setOnClickListener(v -> {
            if (is_close.equals("1")) {
                number.clear();
                single();
                startActivity(new Intent(games.this, single_bet.class)
                        .putExtra("market", market)
                        .putExtra("game", "single")
                        .putExtra("list", number).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .putExtra("open_av", is_open)
                );
            } else {
                new AlertDialog.Builder(games.this)
                        .setTitle("Market Close")
                        .setMessage("This game is already closed for this market")
                        .setNegativeButton(android.R.string.no, null)
                        .show();
            }
        });

        oddEven.setOnClickListener(v -> {
            if (is_close.equals("1")) {
                number.clear();
                single();
                startActivity(new Intent(games.this, OddEven.class)
                        .putExtra("market", market)
                        .putExtra("game", "single")
                        .putExtra("list", number).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .putExtra("open_av", is_open)
                );
            } else {
                new AlertDialog.Builder(games.this)
                        .setTitle("Market Close")
                        .setMessage("This game is already closed for this market")
                        .setNegativeButton(android.R.string.no, null)
                        .show();
            }
        });


        redBracket.setOnClickListener(v -> {
            if (is_open.equals("1")) {
                number.clear();
                jodi();
                startActivity(new Intent(games.this, RedBracket.class)
                        .putExtra("market", market)
                        .putExtra("game", "jodi")
                        .putExtra("list", number).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .putExtra("open_av", is_open)
                );
            } else {
                new AlertDialog.Builder(games.this)
                        .setTitle("Market Close")
                        .setMessage("This game is already closed for this market")
                        .setNegativeButton(android.R.string.no, null)
                        .show();
            }
        });

        jodi.setOnClickListener(v -> {
            if (is_open.equals("1")) {
                number.clear();
                jodi();
                startActivity(new Intent(games.this, single_bet.class)
                        .putExtra("market", market)
                        .putExtra("game", "jodi")
                        .putExtra("list", number).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .putExtra("open_av", is_open)
                );
            } else {
                new AlertDialog.Builder(games.this)
                        .setTitle("Market Close")
                        .setMessage("This game is already closed for this market")
                        .setNegativeButton(android.R.string.no, null)
                        .show();
            }
        });

        singlepatti.setOnClickListener(v -> {
            if (is_close.equals("1")) {
                number.clear();
                singlepatti();
                startActivity(new Intent(games.this, single_bet.class)
                        .putExtra("market", market)
                        .putExtra("game", "singlepatti")
                        .putExtra("list", number).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .putExtra("open_av", is_open)
                );
            } else {
                new AlertDialog.Builder(games.this)
                        .setTitle("Market Close")
                        .setMessage("This game is already closed for this market")
                        .setNegativeButton(android.R.string.no, null)
                        .show();
            }
        });

        spMotor.setOnClickListener(v -> {
            if (is_close.equals("1")) {
                number.clear();
                singlepatti();
                startActivity(new Intent(games.this, SpMotor.class)
                        .putExtra("market", market)
                        .putExtra("game", "singlepatti")
                        .putExtra("list", number).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .putExtra("open_av", is_open)
                );
            } else {
                new AlertDialog.Builder(games.this)
                        .setTitle("Market Close")
                        .setMessage("This game is already closed for this market")
                        .setNegativeButton(android.R.string.no, null)
                        .show();
            }
        });


        dpMotor.setOnClickListener(v -> {
            if (is_close.equals("1")) {
                number.clear();
                doublepatti();
                startActivity(new Intent(games.this, SpMotor.class)
                        .putExtra("market", market)
                        .putExtra("game", "doublepatti")
                        .putExtra("list", number).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .putExtra("open_av", is_open)
                );
            } else {
                new AlertDialog.Builder(games.this)
                        .setTitle("Market Close")
                        .setMessage("This game is already closed for this market")
                        .setNegativeButton(android.R.string.no, null)
                        .show();
            }
        });

        spdptp.setOnClickListener(v -> {
            if (is_close.equals("1")) {
                number.clear();
                singlepatti();
                startActivity(new Intent(games.this, SpDpTp.class)
                        .putExtra("market", market)
                        .putExtra("game", "singlepatti")
                        .putExtra("list", number).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .putExtra("open_av", is_open)
                );
            } else {
                new AlertDialog.Builder(games.this)
                        .setTitle("Market Close")
                        .setMessage("This game is already closed for this market")
                        .setNegativeButton(android.R.string.no, null)
                        .show();
            }
        });

        doublepatti.setOnClickListener(v -> {
            if (is_close.equals("1")) {
                number.clear();
                doublepatti();
                startActivity(new Intent(games.this, single_bet.class)
                        .putExtra("market", market)
                        .putExtra("game", "doublepatti")
                        .putExtra("list", number).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .putExtra("open_av", is_open)
                );
            } else {
                new AlertDialog.Builder(games.this)
                        .setTitle("Market Close")
                        .setMessage("This game is already closed for this market")
                        .setNegativeButton(android.R.string.no, null)
                        .show();
            }
        });

        tripepatti.setOnClickListener(v -> {
            if (is_close.equals("1")) {
                number.clear();
                triplepatti();
                startActivity(new Intent(games.this, single_bet.class)
                        .putExtra("market", market)
                        .putExtra("game", "triplepatti")
                        .putExtra("list", number).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .putExtra("open_av", is_open)
                );
            } else {
                new AlertDialog.Builder(games.this)
                        .setTitle("Market Close")
                        .setMessage("This game is already closed for this market")
                        .setNegativeButton(android.R.string.no, null)
                        .show();
            }
        });


        halfsangam.setOnClickListener(v -> {
            if (is_open.equals("1")) {
                startActivity(new Intent(games.this, halfsangam.class)
                        .putExtra("market", market)
                        .putExtra("game", "halfsangam")
                        .putExtra("list", number)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                );

            } else {
                new AlertDialog.Builder(games.this)
                        .setTitle("Market Close")
                        .setMessage("This game is already closed for this market")
                        .setNegativeButton(android.R.string.no, null)
                        .show();
            }

        });

        fullsangam.setOnClickListener(v -> {
            if (is_open.equals("1")) {
                startActivity(new Intent(games.this, fullsangam.class)
                        .putExtra("market", market)
                        .putExtra("game", "fullsangam")
                        .putExtra("list", number)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                );

            } else {
                new AlertDialog.Builder(games.this)
                        .setTitle("Market Close")
                        .setMessage("This game is already closed for this market")
                        .setNegativeButton(android.R.string.no, null)
                        .show();
            }

        });


    }


    public void single() {
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

    public void jodi() {
        for (int i = 0; i < 100; i++) {
            String temp = String.format("%02d", i);
            number.add(temp);
        }


    }


    private void initViews() {
        single = findViewById(R.id.single);
        jodi = findViewById(R.id.jodi);
        singlepatti = findViewById(R.id.singlepatti);
        doublepatti = findViewById(R.id.doublepatti);
        tripepatti = findViewById(R.id.tripepatti);
        halfsangam = findViewById(R.id.halfsangam);
        fullsangam = findViewById(R.id.fullsangam);
        timing = findViewById(R.id.timing);
        title = findViewById(R.id.title);
        back = findViewById(R.id.back);
        oddEven = findViewById(R.id.odd_even);
        redBracket = findViewById(R.id.red_bracket);
        spdptp = findViewById(R.id.spdptp);
        spMotor = findViewById(R.id.sp_motor);
        dpMotor = findViewById(R.id.dp_motor);
    }
}