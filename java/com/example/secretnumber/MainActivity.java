package com.example.secretnumber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TableLayout tbLayout;
    private TextView tv;
    private Button btnNewGame;
    private int secretNumber;
    private ArrayList<Button> btnList;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        secretNumber = generateNum();

        init();

    }

    private void init() {
        tbLayout = (TableLayout) findViewById(R.id.tblayout);
        tv = (TextView) findViewById(R.id.tv);
        btnNewGame = (Button) findViewById(R.id.btn);
        btnList = new ArrayList<Button>();


        score = 10;

        tv.setText(getResources().getString(R.string.your_score) + " " + score);

        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain();
            }
        });

        writeTable();
    }

    public void writeTable() {
        int value =  0;

        View.OnClickListener btnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btnTemp = (Button) v;

                if(Integer.parseInt(btnTemp.getText().toString()) > secretNumber) {
                    score -= 1;
                    tv.setText(getResources().getString(R.string.your_score) + " " + score);
                    for(Button btn : btnList) {
                        if(Integer.parseInt(btn.getText().toString()) >= Integer.parseInt(btnTemp.getText().toString())) {
                            disableBTN(btn);
                        }
                    }
                }
                else if(Integer.parseInt(btnTemp.getText().toString()) < secretNumber) {
                    score -= 1;
                    tv.setText(getResources().getString(R.string.your_score) + " " + score);
                    for(Button btn : btnList) {
                        if(Integer.parseInt(btn.getText().toString()) <= Integer.parseInt(btnTemp.getText().toString())) {
                            disableBTN(btn);
                        }
                    }
                }
                else {
                    showToast("You win!");
                    for(Button btn : btnList) {
                        disableBTN(btn);
                    }
                    btnTemp.setTextColor(Color.parseColor("#F44336"));
                }

                if(score == 0) {
                    showToast("You lose!");
                    for(Button btn : btnList) {
                        disableBTN(btn);
                    }
                    btnList.get(secretNumber-1).setTextColor(Color.parseColor("#F44336"));
                }
            }
        };

        for(int i=0; i<8; i++) {
            TableRow tbRow = new TableRow(this);

            for(int j=0; j<5; j++) {
                Button smallBTN  = new Button(this);
                value++;
                smallBTN.setText(Integer.toString(value));
              //  smallBTN.setTextColor(Color.parseColor("#3F51B5"));
                smallBTN.setOnClickListener(btnClick);

                /*if(secretNumber == value) {
                    smallBTN.setTextColor(Color.parseColor("#F44336"));
                }*/


                btnList.add(smallBTN);


                tbRow.addView(smallBTN);
            }

            tbLayout.addView(tbRow);
        }
    }

    public void playAgain() {
        score = 10;

        tv.setText(getResources().getString(R.string.your_score) + " " + score);

        for(Button btn : btnList) {
            btn.setTextColor(Color.parseColor("#3F51B5"));
            btn.setClickable(true);
        }

        secretNumber = generateNum();
       /* btnList.get(secretNumber-1).setTextColor(Color.parseColor("#F44336"));*/
    }

    public int generateNum() {
        Random rand = new Random();

        return 1 + rand.nextInt(40);
    }


    public void disableBTN(Button btn) {
        btn.setClickable(false);
        btn.setTextColor(Color.parseColor("#D8D8D8"));
    }

    public void showKeyBoard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(this.getCurrentFocus(), InputMethodManager.SHOW_IMPLICIT);
    }

    public void hideKeyBoard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
