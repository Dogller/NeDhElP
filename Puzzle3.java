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

public class Puzzle3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle3);

        mContext = this;

        id = current.getId();

        init();
        scramble();
        setDimensions();
    }
    private static final int COLUMNS = 3;
    private static final int DIMENSIONS = COLUMNS * COLUMNS;

    private static String[] tileList;

    private static GestureDetectGridView3 mGridView;

    private static int mColumnWidth, mColumnHeight;

    public static final String UP ="up";
    public static final String DOWN ="down";
    public static final String LEFT ="left";
    public static final String RIGHT ="right";

    private static Context mContext;

    Person current= new Person();
    static int id;


    private void init(){
        mGridView = (GestureDetectGridView3) findViewById(R.id.grid);
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
                button.setBackgroundResource(R.drawable.sakura1);
            }
            else if (tileList[i].equals("1")){
                button.setBackgroundResource(R.drawable.sakura2);
            }
            else if (tileList[i].equals("2")){
                button.setBackgroundResource(R.drawable.sakura3);
            }
            else if (tileList[i].equals("3")){
                button.setBackgroundResource(R.drawable.sakura4);
            }
            else if (tileList[i].equals("4")){
                button.setBackgroundResource(R.drawable.sakura5);
            }
            else if (tileList[i].equals("5")){
                button.setBackgroundResource(R.drawable.sakura6);
            }
            else if (tileList[i].equals("6")){
                button.setBackgroundResource(R.drawable.sakura7);
            }
            else if (tileList[i].equals("7")){
                button.setBackgroundResource(R.drawable.sakura8);
            }
            else if (tileList[i].equals("8")){
                button.setBackgroundResource(R.drawable.sakura9);
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



            dbHelper.updateContact(currentid, 4);

            Toast.makeText(context, "YOU WIN!", Toast.LENGTH_LONG).show();

            final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Fun Fact");
            builder.setMessage("Do you want to logout ??");
            builder.setPositiveButton("Next Level", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Intent intent = new Intent(mContext,Puzzle4.class);
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
