package com.example.trial4;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    public static SQLiteDBHelper dbHelper;
    Person current= new Person();
    String username,password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new SQLiteDBHelper(this);

        final EditText txtUser= (EditText) findViewById(R.id.etUserName);
        final EditText txtPass= (EditText) findViewById(R.id.etPass);

        Button signBtn = (Button) findViewById(R.id.btnSignUp);
        Button accBtn = (Button) findViewById(R.id.btnAccount);

        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = txtUser.getText().toString();
                password = txtPass.getText().toString();

                Boolean insert = dbHelper.insert(username, password);


                /*what is this for what ??????
                try {
                    current = dbHelper.getContact(username, password);
                }catch(Exception e){
                */


                if(insert){

                    Intent intent = new Intent(MainActivity.this, accounts.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Account Successfully created!", Toast.LENGTH_SHORT).show();
                }
                else if(username==null||password==null){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("ALERT");
                    builder.setTitle("Incomplete or Missing Information");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog dialog2= builder.create();
                    dialog2.show();

                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("ALERT");
                    builder.setTitle("Incomplete or Missing Information");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog dialog2= builder.create();
                    dialog2.show();
                }
            }
        });



        accBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, accounts.class);
                startActivity(intent);
            }
        });
    }
}
