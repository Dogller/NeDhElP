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

public class accounts extends AppCompatActivity {

    SQLiteDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);


        final EditText txtUName = (EditText) findViewById(R.id.editTextUname);
        final EditText txtPass= (EditText) findViewById(R.id.editTextPass);

        Button loginBtn= (Button) findViewById(R.id.btnLogIn);




        db = new SQLiteDBHelper(this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uname = txtUName.getText().toString();
                String pass= txtPass.getText().toString();

                Boolean checker = db.loginChecker(uname, pass);


                if (checker == true) {
                    Toast.makeText(getApplicationContext(), "Successfully Logged In.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(accounts.this, menu.class);
                    startActivity(intent);
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(accounts.this);
                    builder.setTitle("ALERT");
                    builder.setTitle("Username or Password is wrong.");
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
    }
}
