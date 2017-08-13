package com.example.masoud.a2017_06_15_database_project;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.masoud.a2017_06_15_database_project.dataBase.DatabaseManager;
import com.example.masoud.a2017_06_15_database_project.dataBase.DatabaseOpenHelper;
import com.example.masoud.a2017_06_15_database_project.dataBase.DatabaseSchema;

import java.util.ArrayList;

public class Registration extends AppCompatActivity implements View.OnClickListener{

    EditText editTextName, editTextPhone, editTextEmail, editTextPassword;
    CheckBox checkBox_9_17, checkBox_17_24, checkBox_24_09, checkBox_Weekend;
    Button btnLoad, btnRegister;

    DatabaseManager databaseManager;
    SQLiteDatabase sqLiteDatabase = null;

    private String idEmployee;
    private Cursor cursor;

    Long employeeIdSignUp;
    SimpleCursorAdapter employeeSimpleCursorAdapter;

    ListView taskListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initialize();
    }

    private void initialize() {

        editTextName = (EditText)findViewById(R.id.editTextName);
        editTextPhone = (EditText)findViewById(R.id.editTextPhone);
        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);


        checkBox_9_17 = (CheckBox)findViewById(R.id.checkBox_9_17);
        checkBox_17_24 = (CheckBox)findViewById(R.id.checkBox_17_24);
        checkBox_24_09 = (CheckBox)findViewById(R.id.checkBox_24_09);
        checkBox_Weekend = (CheckBox)findViewById(R.id.checkBox_Weekend);


        btnLoad = (Button)findViewById(R.id.btnLoad);
        btnLoad.setOnClickListener(this);

        btnRegister= (Button)findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);

        // create database
        databaseManager = new DatabaseManager((this));
        sqLiteDatabase = databaseManager.createDatabase();


        // if we are coming from sign in
        // - read data from database and select information of one employee by given name and password
        // - fill all Textfield  based on info

        String signInOrUp = getIntent().getStringExtra("signInOrUp");
        if (signInOrUp.equals("in")) {

            String[] answer = databaseManager.fetchEmployeeInfo(getIntent().getStringExtra("email"));

            editTextName.setText(answer[0]);
            editTextPhone.setText(answer[1]);
            editTextEmail.setText(answer[2]);
            editTextPassword.setText(answer[3]);
            idEmployee = answer[4];


            // - read employee
            ArrayList<Long> availabilityForThisEmployee = databaseManager.fetchAvailabilityIds(Long.valueOf(idEmployee));
            for(Long eachAvailability : availabilityForThisEmployee) {

                if (eachAvailability == Long.valueOf(1)) {
                    checkBox_9_17.setChecked(true);
                }
                if (eachAvailability == Long.valueOf(2)) {
                    checkBox_17_24.setChecked(true);
                }
                if (eachAvailability == Long.valueOf(3)) {
                    checkBox_24_09.setChecked(true);
                }
                if (eachAvailability == Long.valueOf(4)) {

                    checkBox_Weekend.setChecked(true);
                }
            }
        }

        // - insert 4  availability to table availability
        databaseManager.createAvailability("morning");
        databaseManager.createAvailability("evening");
        databaseManager.createAvailability("night");
        databaseManager.createAvailability("weekend");
    }



    // - get checkbox which has been checked
    // - add each checkbox(primary kry) and employee id to the avalibilityEmployee table



    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnLoad:
                // - fetch tasks for this employee

                ArrayList<Long> taskIds = null;
                try {
                    taskIds = databaseManager.fetchTaskIds(Long.valueOf(idEmployee));

                if(!taskIds.isEmpty()){
                    ArrayList<String> arr = databaseManager.fetchTasksForEmployee
                            (taskIds);
                    taskListView = (ListView)findViewById(R.id.taskListView);
                    ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,arr);
                    taskListView.setAdapter(arrayAdapter);
                }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.btnRegister:
                // - insert row to employee table
                 employeeIdSignUp =
                         databaseManager.insertOneRowToEmployeeTable(editTextName.getText().toString(),
                                 editTextPhone.getText().toString(),editTextEmail.getText().toString(),
                                 editTextPassword.getText().toString());
                show();
                break;
        }
    }

    private void show() {
        if (checkBox_9_17.isChecked()){
            databaseManager.createEmployeeAvailability(Long.valueOf(employeeIdSignUp),1);
        }
        if (checkBox_17_24.isChecked()){
            databaseManager.createEmployeeAvailability(Long.valueOf(employeeIdSignUp),2);
        }
        if (checkBox_24_09.isChecked()){
            databaseManager.createEmployeeAvailability(Long.valueOf(employeeIdSignUp),3);
        }
        if (checkBox_Weekend.isChecked()){
            databaseManager.createEmployeeAvailability(Long.valueOf(employeeIdSignUp),4);
        }


    }
}
