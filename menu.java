package com.example.trial4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.trial4.MainActivity.dbHelper;



public class menu extends AppCompatActivity{

    public static int currentid;

    SQLiteDBHelper databaseHelper = new SQLiteDBHelper(this);


    public static int personid=0;
    Person current= new Person();
    int puzz=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        if(personid==0) {
            personid = dbHelper.getContactsCount();
        }

        current = dbHelper.getContact(personid);


        TextView txtinfo=(TextView) findViewById(R.id.txtUsername);
        txtinfo.setText(current.getUsername());


        currentid=current.getId();

        Button btnPlay = (Button) findViewById(R.id.btnPlay);
        Button btnChange = (Button) findViewById(R.id.btnChangeUser);





        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current = dbHelper.getContact(personid);

                puzz=current.getPuzzle();

                if (puzz==1){
                    Intent puzzle1 = new Intent(menu.this, Puzzle1.class);
                    startActivity(puzzle1);
                }
                else if (puzz==2){
                    Intent puzzle2 = new Intent(menu.this, Puzzle2.class);
                    startActivity(puzzle2);
                }
                else if (puzz==3){
                    Intent puzzle1 = new Intent(menu.this, Puzzle3.class);
                    startActivity(puzzle1);
                }
                else if (puzz==4){
                    Intent puzzle1 = new Intent(menu.this, Puzzle4.class);
                    startActivity(puzzle1);
                }
                else if(puzz==5){
                    Intent puzzle1 = new Intent(menu.this, Puzzle5.class);
                    startActivity(puzzle1);

                }
            }
        });

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(menu.this, accounts.class);
                startActivity(i);
            }
        });
    }


}
