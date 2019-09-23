package com.ftpdevelopers.knowlegeistreasure.DBHelpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ftpdevelopers.knowlegeistreasure.contracts.GroceryContract;
import com.ftpdevelopers.knowlegeistreasure.contracts.GroceryContract.*;

import androidx.annotation.Nullable;

public class GroceryDBHelper extends SQLiteOpenHelper {
    public static final String databaseName = "groceryList.db";
    public static final int version = 1;


    public GroceryDBHelper(@Nullable Context context) {
        super(context, databaseName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_GROCERYLIST_TABLE =
                "CREATE TABLE " + GroceryEntry.TABLE_NAME + " ("
               + GroceryEntry.COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
               + GroceryEntry.COLUMN_ITEM +" TEXT NOT NULL ,"
               + GroceryEntry.COLUMN_QUANTITY + " INTEGER NOT NULL , "
               + GroceryEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP "
               + ");" ;

        db.execSQL(CREATE_GROCERYLIST_TABLE);
        Log.e("database created - ", databaseName);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+GroceryEntry.TABLE_NAME);
        onCreate(db);
    }
}
