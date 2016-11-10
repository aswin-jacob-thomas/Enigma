package com.example.aswin.hope;

/**
 * Created by aswin on 10/11/16.
 */

        import java.util.ArrayList;
        import java.util.List;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;
        import android.widget.Toast;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Securit.db";

    // Contacts table name
    private static final String TABLE_SECURITY = "security";

    // Contacts Table Columns names
    private static final String KEY_ACCX = "accx";
    private static final String KEY_ACCY = "accy";
    private static final String KEY_ACCZ = "accz";
    private static final String KEY_TIME = "timestamp";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SECURITY_TABLE = "CREATE TABLE " + TABLE_SECURITY + "("
                + KEY_TIME + " TEXT PRIMARY KEY,"
                + KEY_ACCX + " TEXT," + KEY_ACCY + " TEXT,"
                + KEY_ACCZ + " TEXT" + ")";
        db.execSQL(CREATE_SECURITY_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SECURITY);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    void addSecurity(Security security) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TIME,security.getTime());
        values.put(KEY_ACCX, security.getAccx());
        values.put(KEY_ACCY, security.getAccy());
        values.put(KEY_ACCZ, security.getAccz());
        Log.d("hey siri",security.getTime()+security.getAccy()+security.getAccz());
        // Inserting Row
        db.insert(TABLE_SECURITY, null, values);
        Log.d("adding","dbpart");
        db.close(); // Closing database connection
    }
/*
    // Getting single contact
    Security getSecurity(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SECURITY, new String[] { KEY_TIME,
                        KEY_ACCX, KEY_ACCY,KEY_ACCZ }, KEY_TIME + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Security security = new Security(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),cursor.getString(3));
        // return contact
        return security;
    }*/

    public Cursor getAllSecurity() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_SECURITY,null);
        return res;
    }
    // Getting All Contacts
//    public List<Security> getAllSecurity() {
//        List<Security> securityList = new ArrayList<Security>();
//        // Select All Query
//        String selectQuery = "SELECT  * FROM " + TABLE_SECURITY;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                Security security = new Security();
//                security.setTime(Integer.parseInt(cursor.getString(0)));
//                security.setAccx(cursor.getString(1));
//                security.setAccy(cursor.getString(2));
//                security.setAccz(cursor.getString(3));
//
//                Log.d("taging",cursor.getString(2));
//
//                // Adding contact to list
//                securityList.add(security);
//            } while (cursor.moveToNext());
//        }
//
//        // return contact list
//        return securityList;
//    }

   /* // Updating single contact
    public int updateSecurity(Security security) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ACCX, contact.getName());
        values.put(KEY_PH_NO, contact.getPhoneNumber());

        // updating row
        return db.update(TABLE_SECURITY, values, KEY_TIME + " = ?",
                new String[] { String.valueOf(security.getID()) });
    }**/

    // Deleting single contact
//    public void deleteSecurity(Security security) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_SECURITY, KEY_TIME + " = ?",
//                new String[] { String.valueOf(security.getTime()) });
//        db.close();
//    }


    // Getting contacts Count
    public int getSecurityCount() {
        String countQuery = "SELECT  * FROM " + TABLE_SECURITY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}