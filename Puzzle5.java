package com.example.trial4;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import static com.example.trial4.MainActivity.dbHelper;
import static com.example.trial4.menu.currentid;

public class Puzzle5 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle5);

        mContext = this;

        id = current.getId();

        init();
        scramble();
        setDimensions();
    }
    private static final int COLUMNS = 4;
    private static final int DIMENSIONS = COLUMNS * COLUMNS;

    private static String[] tileList;

    private static GestureDetectGridView5 mGridView;

    private static int mColumnWidth, mColumnHeight;

    public static final String UP ="up";
    public static final String DOWN ="down";
    public static final String LEFT ="left";
    public static final String RIGHT ="right";

    private static Context mContext;

    Person current= new Person();
    static int id;


    private void init(){
        mGridView = (GestureDetectGridView5) findViewById(R.id.grid);
        mGridView.setNumColumns(COLUMNS);

        tileList = new String[DIMENSIONS];
        for(int i = 0; i < DIMENSIONS; i++){
            tileList[i] = String.valueOf(i);
        }
    }

    private void scramble(){
        int index;
        String temp;
        Random random = new Random();

        for(int i = tileList.length-1; i > 0; i--){
            index = random.nextInt(i + 1);
            temp = tileList[index];
            tileList[index] = tileList[i];
            tileList[i] = temp;
        }
    }

    private static void display(Context context){
        ArrayList<Button> buttons = new ArrayList<>();
        Button button;

        for(int i = 0; i < tileList.length; i ++){
            button = new Button(context);

            if (tileList[i].equals("0")){
                button.setBackgroundResource(R.drawable.bop1);
            }
            else if (tileList[i].equals("1")){
                button.setBackgroundResource(R.drawable.bop2);
            }
            else if (tileList[i].equals("2")){
                button.setBackgroundResource(R.drawable.bop3);
            }
            else if (tileList[i].equals("3")){
                button.setBackgroundResource(R.drawable.bop4);
            }
            else if (tileList[i].equals("4")){
                button.setBackgroundResource(R.drawable.bop5);
            }
            else if (tileList[i].equals("5")){
                button.setBackgroundResource(R.drawable.bop6);
            }
            else if (tileList[i].equals("6")){
                button.setBackgroundResource(R.drawable.bop7);
            }
            else if (tileList[i].equals("7")){
                button.setBackgroundResource(R.drawable.bop8);
            }
            else if (tileList[i].equals("8")){
                button.setBackgroundResource(R.drawable.bop9);
            }
            else if (tileList[i].equals("9")){
                button.setBackgroundResource(R.drawable.bop10);
            }
            else if (tileList[i].equals("10")){
                button.setBackgroundResource(R.drawable.bop11);
            }
            else if (tileList[i].equals("11")){
                button.setBackgroundResource(R.drawable.bop12);
            }
            else if (tileList[i].equals("12")){
                button.setBackgroundResource(R.drawable.bop13);
            }
            else if (tileList[i].equals("13")){
                button.setBackgroundResource(R.drawable.bop14);
            }
            else if (tileList[i].equals("14")){
                button.setBackgroundResource(R.drawable.bop15);
            }
            else if (tileList[i].equals("15")){
                button.setBackgroundResource(R.drawable.bop16);
            }

            buttons.add(button);
        }

        mGridView.setAdapter(new CustomAdapter(buttons, mColumnWidth, mColumnHeight));
    }

    private void setDimensions(){
        ViewTreeObserver vto = mGridView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                mGridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int displayWidth = mGridView.getMeasuredWidth();
                int displayHeight = mGridView.getMeasuredHeight();

                int statusbarHeight = getStatusBarHeight(getApplicationContext());
                int requiredHeight = displayHeight - statusbarHeight;

                mColumnWidth = displayWidth / COLUMNS;
                mColumnHeight = requiredHeight / COLUMNS;

                display(getApplicationContext());


            }
        });
    }

    private int getStatusBarHeight(Context context){
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0){
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;

    }

    private static void swap(Context context, int currentPosition, int swap) {
        String newPosition = tileList[currentPosition + swap];
        tileList[currentPosition + swap] = tileList[currentPosition];
        tileList[currentPosition] = newPosition;
        display(context);

        if (isSolved()) {


            dbHelper.updateContact(currentid, 1);



            Toast.makeText(context, "YOU WIN!", Toast.LENGTH_LONG).show();

            final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Fun Fact");
            builder.setMessage("Do you want to logout ??");
            builder.setPositiveButton("Next Level", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Intent intent = new Intent(mContext, End.class);
                    mContext.startActivity(intent);


                }
            });
            builder.setNegativeButton("Go Home", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent2 = new Intent(mContext, menu.class);
                    mContext.startActivity( intent2);

                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    public static void moveTiles(Context context, String direction, int position) {

        // Upper-left-corner tile
        if (position == 0) {

            if (direction.equals("right")) swap(context, position, 1);
            else if (direction.equals("down")) swap(context, position, COLUMNS);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Upper-center tiles
        } else if (position > 0 && position < COLUMNS - 1) {
            if (direction.equals("left")) swap(context, position, -1);
            else if (direction.equals("down")) swap(context, position, COLUMNS);
            else if (direction.equals("right")) swap(context, position, 1);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Upper-right-corner tile
        } else if (position == COLUMNS - 1) {
            if (direction.equals("left")) swap(context, position, -1);
            else if (direction.equals("down")) swap(context, position, COLUMNS);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Left-side tiles
        } else if (position > COLUMNS - 1 && position < DIMENSIONS - COLUMNS &&
                position % COLUMNS == 0) {
            if (direction.equals("up")) swap(context, position, -COLUMNS);
            else if (direction.equals("right")) swap(context, position, 1);
            else if (direction.equals("down")) swap(context, position, COLUMNS);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Right-side AND bottom-right-corner tiles
        } else if (position == COLUMNS * 2 - 1 || position == COLUMNS * 3 - 1) {
            if (direction.equals("up")) swap(context, position, -COLUMNS);
            else if (direction.equals("left")) swap(context, position, -1);
            else if (direction.equals("down")) {

                // Tolerates only the right-side tiles to swap downwards as opposed to the bottom-
                // right-corner tile.
                if (position <= DIMENSIONS - COLUMNS - 1)
                    swap(context, position, COLUMNS);
            }

            // Bottom-left corner tile
        } else if (position == DIMENSIONS - COLUMNS) {
            if (direction.equals("up")) swap(context, position, -COLUMNS);
            else if (direction.equals("right")) swap(context, position, 1);

            // Bottom-center tiles
        } else if (position < DIMENSIONS - 1 && position > DIMENSIONS - COLUMNS) {
            if (direction.equals("up")) swap(context, position, -COLUMNS);
            else if (direction.equals("left")) swap(context, position, -1);
            else if (direction.equals("right")) swap(context, position, 1);

            // Center tiles
        } else {
            if (direction.equals("up")) swap(context, position, -COLUMNS);
            else if (direction.equals("left")) swap(context, position, -1);
            else if (direction.equals("right")) swap(context, position, 1);
            else swap(context, position, COLUMNS);
        }
    }

    private static boolean isSolved() {
        boolean solved = false;

        for (int i = 0; i < tileList.length; i++) {
            if (tileList[i].equals(String.valueOf(i))) {
                solved = true;
            } else {
                solved = false;
                break;
            }
        }

        return solved;
    }
}
