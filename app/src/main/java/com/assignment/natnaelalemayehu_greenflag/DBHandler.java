package com.assignment.natnaelalemayehu_greenflag;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper implements LoginRepository {

    private final String DB_NAME = "GreenFlagDB";
    private final String TABLE_NAME = "AuthTable";
    private final int DB_VERSION = 1;
    private final String USER_EMAIL = "user_email";
    private final String USER_PASS = "user_pass";

    public DBHelper(Context context) {
        super(context, "GreenFlagDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE TABLE_NAME(USER_EMAIL TEXT PRIMARY KEY, USER_PASS TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @Override
    public User selectUser(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorUser = db.rawQuery("SELECT email FROM " + TABLE_NAME, null);
        User user = new User(cursorUser.getString(1), cursorUser.getString(2));
        return user;
    }

    @Override
    public boolean insertUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_EMAIL, email);
        values.put(USER_PASS, password);
        long result = db.insert(TABLE_NAME, null, values);
        db.close();

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean updateUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_PASS, password);
        long result = db.update(TABLE_NAME, values, USER_EMAIL + "=?", new String[]{email});
        db.close();

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean deleteUser(String UserEmail) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, USER_EMAIL +"=?", new String[]{UserEmail});
        db.close();

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
}
