package com.example.masoud.a2017_06_15_database_project.dataBase;

/**
 * Created by masoud on 2017-06-14.
 */

public class DatabaseSchema {

    public static final String DB_Name = "db_employee";

    public static final String TABLE_EMPLOYEE = "employee";

    public static final String _ID = "_id";
    public static final String EMPLOYEE_NAME = "employee_name";
    public static final String EMPLOYEE_PHONE = "employee_phone";
    public static final String EMPLOYEE_EMAIL = "employee_email";
    public static final String EMPLOYEE_PASSWORD = "employee_password";


    public static final String[] employee_columns = {_ID, EMPLOYEE_NAME, EMPLOYEE_PHONE, EMPLOYEE_EMAIL, EMPLOYEE_PASSWORD};


    public static String CREATE_CMD_EMPLOYEE = "CREATE TABLE " + TABLE_EMPLOYEE + "(" +
            _ID + " INTEGER PRIMARY KEY," +
            EMPLOYEE_NAME + " TEXT NOT NULL," +
            EMPLOYEE_PHONE + " TEXT NOT NULL," +
            EMPLOYEE_EMAIL + " TEXT NOT NULL UNIQUE," +
            EMPLOYEE_PASSWORD + " TEXT" +
            ");";


    public static final String TABLE_TASK = "task";
    public static final String TASK_NAME = "task_name";
    public static final String[] task_columns = {_ID, TASK_NAME};

    public static String CREATE_CMD_TASK = "CREATE TABLE " + TABLE_TASK + "(" +
            _ID + " INTEGER PRIMARY KEY," +
            TASK_NAME + " TEXT NOT NULL" +
            ");";


    public static final String TABLE_EMPLOYEE_TASK = "task_employee";
    public static final String KEY_EMPLOYEE_ID = "table_id";
    public static final String KEY_TASK_ID = "task_id";

    public static final String[] table_task_columns = {_ID, KEY_EMPLOYEE_ID, KEY_TASK_ID};

    public static String CREATE_CMD_TABLE_TASK = "CREATE TABLE " + TABLE_EMPLOYEE_TASK + "(" +
            _ID + " INTEGER PRIMARY KEY," +
            KEY_EMPLOYEE_ID + " TEXT NOT NULL," +
            KEY_TASK_ID + " TEXT NOT NULL" +
            ");";

    public static final String TABLE_EMPLOYEE_AVAILABILITY = "availability_employee";

    //    public static final String KEY_EMPLOYEE_ID = "table_id";
    public static final String KEY_AVAILABILITY_ID = "availability_id";
    public static final String[] table_availability_columns = {_ID, KEY_EMPLOYEE_ID, KEY_AVAILABILITY_ID};

    public static String CREATE_CMD_EMPLOYEE_AVAILABILITY = "CREATE TABLE " + TABLE_EMPLOYEE_AVAILABILITY + "(" + _ID + " INTEGER PRIMARY KEY,"
            + KEY_EMPLOYEE_ID + " TEXT NOT NULL," + KEY_AVAILABILITY_ID + " TEXT NOT NULL" + ");";


    public static final String TABLE_AVAILABILITY = "availability";
    public static final String AVAILABILITY_NAME = "availability_name";

    public static final String[] availability_columns = {_ID, AVAILABILITY_NAME};

    public static String CREATE_CMD_AVAILABILITY = "CREATE TABLE " + TABLE_AVAILABILITY + "(" +
            _ID + " INTEGER PRIMARY KEY," +
            AVAILABILITY_NAME + " TEXT NOT NULL" +
            ");";

    public static int version = 1;

}
