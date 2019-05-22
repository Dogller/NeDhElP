package com.example.trial4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class End extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        Button  btnExit= (Button) findViewById(R.id.btnExit);
        Button  btnReplay= (Button) findViewById(R.id.btnReplay);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in= new Intent(End.this, MainActivity.class);
                startActivity(in);

            }
        });

        btnReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent on= new Intent(End.this, menu.class);
                startActivity(on);
            }
        });


    }
}
