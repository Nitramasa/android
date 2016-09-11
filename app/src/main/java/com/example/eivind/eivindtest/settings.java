package com.example.eivind.eivindtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class settings extends AppCompatActivity {
    Button btnDel;
    private Dinner dinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        init();
    }

    public void init(){
        btnDel = (Button) findViewById(R.id.btnDel);

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast tt  = Toast.makeText(getApplicationContext(),"Sletter.....", Toast.LENGTH_LONG);
                tt.show();
                tt.setGravity(Gravity.TOP|Gravity.CENTER, 0 ,500);
                dinner = new Dinner(getApplicationContext());
                dinner.deleteAllDinners();
                Toast t = Toast.makeText(getApplicationContext(),"Slettet! all data er fjernet", Toast.LENGTH_LONG);
                t.show();
                t.setGravity(Gravity.TOP|Gravity.CENTER, 0 ,500);
                main f = new main();
                f.clearArr();
            }
        });

    }
}
