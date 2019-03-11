package dev.master;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import dev.master.model.location;


public class DbHelper extends SQLiteOpenHelper {


    // notes table
    public static final String DATABASE_NAME = "locations";
    public static final String TABLE_NAME = "places";
    public static final String C_ID = "_id";
    public static final String NAME = "name";

    public static final String LAT = "latitude";
    public static final String LONG = "longitude";

    public static final int VERSION = 1;




    // create locations sql query
    private final String CREATE_PLACES_TABLE = "create table if not exists " + TABLE_NAME + " ( "
    + C_ID + " integer primary key autoincrement, "
    + NAME + " text, "
    + LAT + " text, "

    + LONG + " text)";




    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_PLACES_TABLE);
        // create users table

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table " + TABLE_NAME);

    }



    public List<location> getsavedlocations() {
        List<location> locations = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " ORDER BY " +
                C_ID + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                location l = new location();
                l.setName(cursor.getString(cursor.getColumnIndex(NAME)));
                l.setLatitude(cursor.getString(cursor.getColumnIndex(LAT)));
                l.setLongitude(cursor.getString(cursor.getColumnIndex(LONG)));

                locations.add(l);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return locations;
    }

    public void addplace(String name, String latitude,String longitude ) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, name);
        values.put(LAT, latitude);
        values.put(LONG, longitude);

        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close();
    }





}
