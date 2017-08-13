package com.example.masoud.a2017_06_15_database_project.dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by masoud on 2017-06-14.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper{

    public static final String DB_NAME = DatabaseSchema.DB_Name;
    public static int version = DatabaseSchema.version;
    private Context context;


    public DatabaseOpenHelper(Context context) {
        super(context, DB_NAME, null, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DatabaseSchema.CREATE_CMD_EMPLOYEE);
        db.execSQL(DatabaseSchema.CREATE_CMD_TASK);
        db.execSQL(DatabaseSchema.CREATE_CMD_TABLE_TASK);
        db.execSQL(DatabaseSchema.CREATE_CMD_AVAILABILITY);
        db.execSQL(DatabaseSchema.CREATE_CMD_EMPLOYEE_AVAILABILITY);
        Log.d("DATABASE", "The database " + DB_NAME + "is created on" + db.getPath());
        db.getPath();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void deleteDatabase(){
        context.deleteDatabase(DB_NAME);
        Log.d("DATABASE", "The database " + DB_NAME + "is removed");
    }



}
