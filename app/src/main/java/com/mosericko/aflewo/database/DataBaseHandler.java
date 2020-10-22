package com.mosericko.aflewo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.mosericko.aflewo.member.User;

public class DataBaseHandler extends SQLiteOpenHelper {

    //Database Constants
    private static final String DATABASE_NAME = "Aflewo";
    private static final int DATABASE_VERSION = 1;

    //Database tables
    private static final String USER_TABLE = "userProfile";


    //table fields for userProfile
    private static final String USER_ID = "id";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String GENDER = "gender";
    private static final String EMAIL_ADDRESS = "emailAddress";
    private static final String PHONE_NUMBER = "phoneNumber";
    private static final String USER_TYPE = "userType";




    public DataBaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table using SQLite Statements
        //UserProfile SQLite Statements
        String PRIMARY_USER_ID = "primary_id";
        String userProfileSQl = "CREATE TABLE " + USER_TABLE + "(" + PRIMARY_USER_ID + " INTEGER PRIMARY KEY, " +
                USER_ID + " INTEGER, " +
                FIRST_NAME + " TEXT, " +
                LAST_NAME + " TEXT, " +
                GENDER + " TEXT, " +
                EMAIL_ADDRESS + " VARCHAR, " +
                PHONE_NUMBER + " VARCHAR, " +
                USER_TYPE + " VARCHAR " + ");";

        db.execSQL(userProfileSQl);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //if the tables already exist
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);

        // Create the table after dropping the existing ones
        onCreate(db);

    }



    //add user to database
    public void addUser(User user) {
        SQLiteDatabase myDb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(USER_ID, String.valueOf(user.getId()));
        cv.put(FIRST_NAME, user.getFirstname());
        cv.put(LAST_NAME, user.getLastname());
        cv.put(GENDER, user.getGender());
        cv.put(EMAIL_ADDRESS, user.getEmail());
        cv.put(PHONE_NUMBER, user.getPhonenumber());
        cv.put(USER_TYPE, user.getUsertype());

        //insert row in myDb
        myDb.insert(USER_TABLE, null, cv);
        myDb.close();
    }

    //get User Details From myDb using their id
    public User getUser(int id) {
        SQLiteDatabase myDb = this.getReadableDatabase();
        //Cursor userCursor= myDb.query(USER_TABLE,new String[]{FIRST_NAME,LAST_NAME,EMAIL_ADDRESS,PHONE_NUMBER},null,null,null,
        //null,null);

        String sql = "SELECT * FROM " + USER_TABLE + " WHERE " + USER_ID + " = '" + id + "'";
        Cursor userCursor = myDb.rawQuery(sql, null);

        if (userCursor != null)
            userCursor.moveToFirst();

        assert userCursor != null;
        User user = new User(
                userCursor.getInt(userCursor.getColumnIndex(USER_ID)),
                userCursor.getString(userCursor.getColumnIndex(FIRST_NAME)),
                userCursor.getString(userCursor.getColumnIndex(LAST_NAME)),
                userCursor.getString(userCursor.getColumnIndex(GENDER)),
                userCursor.getString(userCursor.getColumnIndex(EMAIL_ADDRESS)),
                userCursor.getString(userCursor.getColumnIndex(PHONE_NUMBER)),
                userCursor.getString(userCursor.getColumnIndex(USER_TYPE))

        );
        userCursor.close();
        myDb.close();

        return user;


    }

}

