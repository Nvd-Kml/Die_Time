package com.example.dietime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static int a;
    static int b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        final EditText ageg = findViewById(R.id.agefirst);
        final EditText trys = findViewById(R.id.tries);
        final Button nexty = findViewById(R.id.nextid);

        nexty.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String ageing = ageg.getText().toString();
                        String trying = trys.getText().toString();

                        if (TextUtils.isEmpty(ageing)) {
                            ageg.setError("Please Enter Age☺");
                            ageg.requestFocus();
                            return;
                        }
                        if (TextUtils.isEmpty(trying)) {
                            trys.setError("Please Enter no. of Tries☺");
                            trys.requestFocus();
                            return;
                        }
                        a = Integer.parseInt(ageing);
                        b = Integer.parseInt(trying);

                        closekeyboard();

                        if (a < 1 || a > 100) {

                            ageg.setError("Enter a value in range[1-100]");
                            ageg.requestFocus();
                            return;
                        }
                        if (b < 1) {
                            trys.setError("Value must be >1");
                            trys.requestFocus();
                            return;
                        }

                        Intent i = new Intent(MainActivity.this, guesspage.class);
                        i.putExtra("agei",a);
                        i.putExtra("tryi",b);
                        startActivity(i);

                    }
                }

        );

    }



    private void closekeyboard() {

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}

