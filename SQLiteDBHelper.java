package com.example.trial4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



class SQLiteDBHelper extends SQLiteOpenHelper {



    public String currentUser, currentPass;


    public SQLiteDBHelper (Context context) {
        super (context, "info.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("Create table profile(id integer primary key autoincrement, username text, password text, puzzle int)");
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVer, int newVer){
        db.execSQL("drop table if exists user");
    }

    public boolean insert (String username, String password){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put("username", username);
        val.put("password", password);
        val.put("puzzle", "1");



        long insert = db.insert("profile", null, val);
        return insert != -1;
    }

    public Boolean loginChecker (String username, String password){
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select *from profile where username =? and password =?", new String[]{username, password});

        currentUser= username;
        currentPass= password;

        return cursor.getCount() > 0;
    }


    public Person getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String [] columns = new String []{"id", "username", "password", "puzzle" };

        Cursor cursor = db.query("profile", columns, "id" + "=?",new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Person person = new Person (Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getInt(3) );


        return person;
    }


    /* seriosuly what is this for???

    public Person getContact(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String [] columns = new String []{KEY_ID,
                KEY_NAME, KEY_USERNAME, KEY_PASSWORD, KEY_CONTACT, KEY_EMAIL, KEY_GENDER};
        //

        Cursor cursor = db.query(TABLE_PERSON, columns, KEY_USERNAME + "=?"+" AND " + KEY_PASSWORD + "=?",new String[] { username,password }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Person person = new Person(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));

        // return contact
        return person;
    }

    */


    public void updateContact(int id, int numm) {
        SQLiteDatabase db2= this.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put("puzzle", numm);
        db2.update("profile", val, "id=" + id, null);

        SQLiteDatabase db = this.getReadableDatabase();
        String [] columns = new String []{"id", "username", "password", "puzzle" };

    }

    public int getContactsCount(){
        String countQuery = "SELECT  * FROM " + "profile";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count=cursor.getCount();
        cursor.close();

        // return count
        return count;
    }


}
