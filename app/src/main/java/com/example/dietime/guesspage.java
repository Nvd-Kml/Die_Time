package com.example.dietime;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class guesspage extends MainActivity {

   static int won,lost;
    int x,origage,age;

    TextView h;
    TextView g;
    EditText e;
    TextView t;
    LinearLayout l;
    TextView wins;
    TextView loss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guesspage);

        Bundle extras=getIntent().getExtras();
        x=extras.getInt("tryi");
        origage=extras.getInt("agei");
        

        h=findViewById(R.id.history);
        g=findViewById(R.id.guesses);
        e=findViewById(R.id.ageguess);
        t=findViewById(R.id.result);
        l=findViewById(R.id.lin);
        final Button b=findViewById(R.id.guessit);
        final Button r=findViewById(R.id.reset);
        final Button histy=findViewById(R.id.hist) ;
        wins=findViewById(R.id.won);
        loss=findViewById(R.id.lost);

        SpannableString cont =new SpannableString("GUESSES");
        cont.setSpan(new UnderlineSpan(),0,cont.length(),0);
        g.setText(cont);

        b.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        x--;
                        String s = e.getText().toString();
                        if(TextUtils.isEmpty(s)){
                            e.setError("Please enter age..☺");
                            e.requestFocus();
                            return;
                        }

                        age = Integer.parseInt(s);

                        if(age<1||age>100){
                            e.setError("Please enter in range[1-100]");
                            e.requestFocus();
                            return;
                        }

                        if(x>=0) {
                            if (origage> age) {
                                if ((origage - age) <= 5&&(origage-age)!=0)
                                    l.setBackgroundColor(Color.parseColor("#006400"));
                                else if ((origage - age > 5) && (origage - age <= 20))
                                    l.setBackgroundColor(Color.parseColor("#90EE90"));
                                else if ((origage - age > 20) && (origage - age <= 50))
                                    l.setBackgroundColor(Color.parseColor("#FF0000"));
                                else if ((origage - age > 50))
                                    l.setBackgroundColor(Color.parseColor("#8B0000"));
                            }
                            if (age > origage) {
                                if ((age - origage) <= 5&&(age-origage)!=0)
                                    l.setBackgroundColor(Color.parseColor("#006400"));
                                else if ((age - origage > 5) && (age - origage <= 20))
                                    l.setBackgroundColor(Color.parseColor("#90EE90"));
                                else if ((age - origage > 20) && (age - origage <= 50))
                                    l.setBackgroundColor(Color.parseColor("#FF0000"));
                                else if ((age - origage > 50))
                                    l.setBackgroundColor(Color.parseColor("#8B0000"));
                            }
                            if((origage==age))
                                l.setBackgroundColor(Color.parseColor("#87CEEB"));
                        }
                       String end=myresult(age);

                        if(x>=0) {
                            h.append(age + " ");
                        }
                        if(x>0) {
                            t.setText(end);
                        }
                        else if(x==0){
                            closekeyboard();
                            e.getText().clear();
                            l.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
                            t.setText(end);
                            if(origage!=age) {
                                t.append("\n\nGuesses over kidoo...Your tries are over..\uD83D\uDE13\nYou lost..\uD83D\uDC4E\uD83D\uDC4E\n" +"CORRECT AGE : "+origage+
                                         "\nPress RETRY to try again..\uD83D\uDE42\uD83D\uDE42");
                                t.requestFocus();
                                return;
                            }
                        }
                        else{
                            closekeyboard();
                            e.getText().clear();
                            t.setError("Guesses Over⚠️ ⚠️ ⚠️ ");
                            t.setText("Guesses Over⚠️ ⚠️ ⚠️ \nClick RETRY to Try Again\uD83D\uDE42\uD83D\uDE42" );
                            t.requestFocus();
                            return;
                        }
                        e.getText().clear();
                        closekeyboard();

                    }
                }
        );

        r.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(v==r){
                            startActivity(new Intent(guesspage.this,MainActivity.class));
                        }
                    }
                }
        );

        histy.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (age == origage) {
                            won++;
                        }
                        if (x == 0 && age != origage) {
                            lost++;
                        }
                        wins.setText("Correct Guesses : " + won);
                        loss.setText("Failed Guesses  : " + lost);
                        histy.setEnabled(false);
                    }
                }
        );

    }
    @Override
    protected  void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        String text=savedInstanceState.getString("text");
        String hist=savedInstanceState.getString("hist");
        int trie=savedInstanceState.getInt("try");
        int color=savedInstanceState.getInt("color");
        int agei=savedInstanceState.getInt("age");
        age=agei;
        l.setBackgroundColor(color);
        x=trie;
        h.setText(hist);
        t.setText(text);


    }
    @Override
    protected  void onSaveInstanceState(Bundle outstate){
        super.onSaveInstanceState(outstate);
        String text=t.getText().toString();
        String hist=h.getText().toString();
        int color=((ColorDrawable)l.getBackground()).getColor();
        int trie=x;
        int p=age;
        outstate.putInt("age",p);
        outstate.putInt("color",color);
        outstate.putInt("try",trie);
        outstate.putString("hist",hist);
        outstate.putString("text",text);
    }
    private void closekeyboard(){

        View view =this.getCurrentFocus();
        if(view!=null){
            InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
    public String myresult(int age) {

        if(x>1) {
            if (age < origage)
                return "Your guess is lower than actual age \uD83D\uDE1F\uD83D\uDE1F\uD83D\uDE1F\n\n" + x+ " tries left\uD83D\uDE08";
        }
        if(x==1){
            if (age < origage)
                return "Your guess is lower than actual age \uD83D\uDE1F\uD83D\uDE1F\uD83D\uDE1F\n\n" +x+ " try left\uD83D\uDE08";
        }

        if(x>1) {
            if(age>origage)
                return "Your guess is greater than actual age \uD83D\uDE1F\uD83D\uDE1F\n\n"+ x+ " tries left\uD83D\uDE08";
        }
        if(x==1){
            if(age>origage)
                return "Your guess is greater than actual age \uD83D\uDE1F\uD83D\uDE1F\n\n"+ x + " try left\uD83D\uDE08";
        }
        if(origage==age)
            return "Your guess is perfect..Keep it up..☺✌️✌️♥♥\n\nPress RETRY for more fun☺☺";

        return "";
    }


}
