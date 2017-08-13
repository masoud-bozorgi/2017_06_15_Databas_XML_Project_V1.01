package com.example.masoud.a2017_06_15_database_project.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by seyedamirhosseinhashemi on 2017-06-25.
 */

public class DatabaseManager {

    private SQLiteDatabase sqLiteDatabase;
    private DatabaseOpenHelper databaseOpenHelper;

    public DatabaseManager(Context context) {
        this.databaseOpenHelper = new DatabaseOpenHelper(context);
    }


    public SQLiteDatabase createDatabase() {
        sqLiteDatabase = databaseOpenHelper.getWritableDatabase();

        Log.d("DATABASE", "Database is created.");
        return sqLiteDatabase;
    }


    public Long insertOneRowToEmployeeTable(String name, String phone, String email, String password) {

        ContentValues values = new ContentValues();
//        values.put(DatabaseSchema._ID, editTextID.getText().toString());
        values.put(DatabaseSchema.EMPLOYEE_NAME, name);
        values.put(DatabaseSchema.EMPLOYEE_PHONE, phone);
        values.put(DatabaseSchema.EMPLOYEE_EMAIL, email);
        values.put(DatabaseSchema.EMPLOYEE_PASSWORD, password);

        Long id = sqLiteDatabase.insert(DatabaseSchema.TABLE_EMPLOYEE, null, values);
        return id;
    }


    public String[] fetchEmployeeInfo(String email) {

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM employee", null);
        cursor.moveToFirst();
        String[] answer = new String[]{};

        do {
            int id = cursor.getInt(cursor.getColumnIndex(DatabaseSchema._ID));
            String curserName     = cursor.getString(cursor.getColumnIndex(DatabaseSchema.EMPLOYEE_NAME));
            String curserPhone    = cursor.getString(cursor.getColumnIndex(DatabaseSchema.EMPLOYEE_PHONE));
            String curserEmail    = cursor.getString(cursor.getColumnIndex(DatabaseSchema.EMPLOYEE_EMAIL));
            String curserPassword = cursor.getString(cursor.getColumnIndex(DatabaseSchema.EMPLOYEE_PASSWORD));

            if (curserEmail.equals(email)) {
                answer = new String[]{curserName, curserPhone, curserEmail, curserPassword, String.valueOf(id)};
                break;
            }
        } while (cursor.moveToNext());

        cursor.close();
        return answer;
    }


    public String[] fetchEmployeeInfo(Long id) {

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM employee", null);
        cursor.moveToFirst();
        String[] answer = new String[]{};

        do {
            int idCursor          = cursor.getInt(cursor.getColumnIndex(DatabaseSchema._ID));
            String curserName     = cursor.getString(cursor.getColumnIndex(DatabaseSchema.EMPLOYEE_NAME));
            String curserPhone    = cursor.getString(cursor.getColumnIndex(DatabaseSchema.EMPLOYEE_PHONE));
            String curserEmail    = cursor.getString(cursor.getColumnIndex(DatabaseSchema.EMPLOYEE_EMAIL));
            String curserPassword = cursor.getString(cursor.getColumnIndex(DatabaseSchema.EMPLOYEE_PASSWORD));

            if (idCursor == id) {
                answer = new String[]{curserName, curserPhone, curserEmail, curserPassword};
                break;
            }
        } while (cursor.moveToNext());

        cursor.close();
        return answer;
    }

    //------------------------------------------------------------------------------- Add a task to a employee
    public void createTask(String task, Long employee_id) {

        ContentValues values = new ContentValues();
        values.put(DatabaseSchema.TASK_NAME, task);

        //----------------------------- Just insert task to task column for target employee
        Long task_id = sqLiteDatabase.insert(DatabaseSchema.TABLE_TASK, null, values);
        createEmployeeTask(employee_id, task_id);
    }



    public long createEmployeeTask(long employee_id, long task_id) {

        ContentValues values = new ContentValues();
        values.put(DatabaseSchema.KEY_TASK_ID, task_id);
        values.put(DatabaseSchema.KEY_EMPLOYEE_ID, employee_id);

        long id = sqLiteDatabase.insert(DatabaseSchema.TABLE_EMPLOYEE_TASK, null, values);
        return id;
    }



    public Cursor readAllRows() {

        Cursor result = sqLiteDatabase.query(
                DatabaseSchema.TABLE_EMPLOYEE,
                DatabaseSchema.employee_columns,
                null, null, null, null, null);
        return result;
    }


    public ArrayList<Long> fetchTaskIds(Long employee_id) throws Exception {

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM task_employee", null);
        cursor.moveToFirst();
        ArrayList<Long> task_ids = new ArrayList<>();

        do {
            int idCursor = cursor.getInt(cursor.getColumnIndex(DatabaseSchema._ID));
            Long employeeId = cursor.getLong(cursor.getColumnIndex(DatabaseSchema.KEY_EMPLOYEE_ID));
            Long taskId = cursor.getLong(cursor.getColumnIndex(DatabaseSchema.KEY_TASK_ID));
            if (employeeId == employee_id) {
                task_ids.add(taskId);
            }
        } while (cursor.moveToNext());

        cursor.close();
        return task_ids;
    }


    public ArrayList<String> fetchTasksForEmployee(ArrayList<Long> taskIds) {

        Cursor cursor = null;
        ArrayList<String> tasks = new ArrayList<>();

        for (Long taskId : taskIds) {
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM task", null);
            cursor.moveToFirst();

            do {
                int idCursor = cursor.getInt(cursor.getColumnIndex(DatabaseSchema._ID));
                String taskName = cursor.getString(cursor.getColumnIndex(DatabaseSchema.TASK_NAME));
                if (taskId == idCursor) {
                    tasks.add(taskName);
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        return tasks;
    }


    // add 4 availability to table avalability
    public void createAvailability(String availability) {

        ContentValues values = new ContentValues();
        values.put(DatabaseSchema.AVAILABILITY_NAME, availability);
        sqLiteDatabase.insert(DatabaseSchema.TABLE_AVAILABILITY, null, values);
    }


    public long createEmployeeAvailability(long employee_id, long availability_id) {

        ContentValues values = new ContentValues();
        values.put(DatabaseSchema.KEY_AVAILABILITY_ID, availability_id);
        values.put(DatabaseSchema.KEY_EMPLOYEE_ID, employee_id);

        long id = sqLiteDatabase.insert(DatabaseSchema.TABLE_EMPLOYEE_AVAILABILITY, null, values);
        return id;
    }


    public ArrayList<Long> fetchAvailabilityIds(Long employee_id) {

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM availability_employee", null);
        cursor.moveToFirst();
        ArrayList<Long> availability_ids = new ArrayList<>();

        do {
            int idCursor = cursor.getInt(cursor.getColumnIndex(DatabaseSchema._ID));
            Long employeeId = cursor.getLong(cursor.getColumnIndex(DatabaseSchema.KEY_EMPLOYEE_ID));
            Long availabilityId = cursor.getLong(cursor.getColumnIndex(DatabaseSchema.KEY_AVAILABILITY_ID));
            if (employeeId == employee_id) {
                availability_ids.add(availabilityId);
            }
        } while (cursor.moveToNext());

        cursor.close();
        return availability_ids;
    }

    //------------------------------------------------------------------------------ Select employee with target time slots
    public ArrayList<Long> fetchEmployeeIdsBasedOnAvailability(ArrayList<Long> target_Received_availability_ids) {

        Cursor cursor = null;
        ArrayList<Long> array_of_employee_ids_with_target_timeSlot = new ArrayList<>();

        //Do a loop on received target array of availability ids
        for (Long current_availability_id_from_received_target_array_ids : target_Received_availability_ids) {
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM availability_employee", null);
            cursor.moveToFirst();

            //fetch rows one by on from availability_employee table
            do {
                int idCursor = cursor.getInt(cursor.getColumnIndex(DatabaseSchema._ID));
                Long this_fetched_employee_Availability_ID = cursor.getLong(cursor.getColumnIndex(DatabaseSchema.KEY_AVAILABILITY_ID));
                Long employee_identity_ID = cursor.getLong(cursor.getColumnIndex(DatabaseSchema.KEY_EMPLOYEE_ID));

                //If in this current employee availability id is equal to current received availability id
                if (this_fetched_employee_Availability_ID == current_availability_id_from_received_target_array_ids) {

                    //And if this employee is not part of target array list, add it to the array
                    if (!array_of_employee_ids_with_target_timeSlot.contains(employee_identity_ID)) {
                        array_of_employee_ids_with_target_timeSlot.add(employee_identity_ID);
                    }
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        return array_of_employee_ids_with_target_timeSlot;
    }




    public Cursor fetchEmployeeInfoFromEmployeeIds(ArrayList<Long> employeeIds) {

        Cursor cursor = null;
        ArrayList<Cursor> cursorArrayList = new ArrayList<>();

        for (Long employeeId : employeeIds) {
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM employee WHERE _id = " + employeeId + ";", null);
            cursorArrayList.add(cursor);
        }

        Cursor[] cursorArray = new Cursor[cursorArrayList.size()];
        cursorArrayList.toArray(cursorArray);
        MergeCursor mergeCursor = new MergeCursor(cursorArray);
//        cursor.close();
//        mergeCursor.close();
        return mergeCursor;
    }
}
