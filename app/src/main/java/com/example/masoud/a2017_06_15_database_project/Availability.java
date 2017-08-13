package com.example.masoud.a2017_06_15_database_project;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.masoud.a2017_06_15_database_project.Model.Person;
import com.example.masoud.a2017_06_15_database_project.dataBase.DatabaseManager;
import com.example.masoud.a2017_06_15_database_project.dataBase.DatabaseSchema;

import java.util.ArrayList;

public class Availability extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener{


    Button btnShowAvailability, btnReadFromXml;
    Cursor employeeCursor;
    ListView listViewEmployees;
    SimpleCursorAdapter employeeSimpleCursorAdapter;
    CheckBox checkBox_9_17, checkBox_17_24, checkBox_24_09, checkBox_Weekend;

    DatabaseManager databaseManager;
    SQLiteDatabase sqLiteDatabase = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availability);

        initialize();
    }

    private void initialize() {

        btnShowAvailability = (Button)findViewById(R.id.btnShowAvailability);
        btnShowAvailability.setOnClickListener(this);

        btnReadFromXml = (Button)findViewById(R.id.btnReadFromXml);
        btnReadFromXml.setOnClickListener(this);

        btnReadFromXml = (Button)findViewById(R.id.btnReadFromXml);
        btnReadFromXml.setOnClickListener(this);

        checkBox_9_17 = (CheckBox)findViewById(R.id.checkBox_9_17);
        checkBox_17_24 = (CheckBox)findViewById(R.id.checkBox_17_24);
        checkBox_24_09 = (CheckBox)findViewById(R.id.checkBox_24_09);
        checkBox_Weekend = (CheckBox)findViewById(R.id.checkBox_Weekend);
        databaseManager = new DatabaseManager((this));


        // create database
        sqLiteDatabase = databaseManager.createDatabase();
        employeeCursor = databaseManager.readAllRows();

        employeeSimpleCursorAdapter = new SimpleCursorAdapter(
                this,
                R.layout.one_element,
                employeeCursor,
                DatabaseSchema.employee_columns,
                new int[]{R.id.textViewIDOne,
                R.id.textViewNameOne});

        listViewEmployees = (ListView)findViewById(R.id.listViewEmployees);
        listViewEmployees.setAdapter(employeeSimpleCursorAdapter);
        listViewEmployees.setOnItemClickListener(this);

    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnShowAvailability:
                // - read rows for checked avalibilty
                // ( employee ids where availability is checked form availability_employee table)
                // - by employee ids read from employee table and return curser
                // - notify the adapter
                // - reload
                ArrayList<Long> selectedTimeSlot = new ArrayList<>();

                if (checkBox_9_17.isChecked()){
                    selectedTimeSlot.add(Long.valueOf(1));
                }
                if (checkBox_17_24.isChecked()){
                    selectedTimeSlot.add(Long.valueOf(2));
                }
                if (checkBox_24_09.isChecked()){
                    selectedTimeSlot.add(Long.valueOf(3));
                }
                if (checkBox_Weekend.isChecked()){
                    selectedTimeSlot.add(Long.valueOf(4));
                }

                Cursor myCursor = null;

                ArrayList<Long> employeeIds = databaseManager.fetchEmployeeIdsBasedOnAvailability(selectedTimeSlot);
                if (!employeeIds.isEmpty()) {
                     myCursor = databaseManager.fetchEmployeeInfoFromEmployeeIds(employeeIds);
                }

//                myCursor.moveToFirst();
//                ArrayList<Long> employee_ids = new ArrayList<>();
//                do{
//                    int idCursor = myCursor.getInt(myCursor.getColumnIndex(DatabaseSchema._ID));
//
//                } while (myCursor.moveToNext());
                employeeSimpleCursorAdapter.changeCursor(myCursor);

                employeeSimpleCursorAdapter.notifyDataSetChanged();
//                myCursor.close();
                break;

            case R.id.btnReadFromXml:

                // - read from xml file and return array of persons
                // - iterate throw array and one by one insert the person to the employee table
                // - and availibility of person to the table of availability
                XmlReader.processXMLFile(this,"employees.xml");
                for (Person currentPerson: XmlReader.personsArraylist) {
                    Long id = databaseManager.insertOneRowToEmployeeTable(currentPerson.getName(),
                            currentPerson.getPhone(),currentPerson.getEmail(), null);

                    if (currentPerson.getAvailabilityBooleanArrayList().get(0)){
                        databaseManager.createEmployeeAvailability(Long.valueOf(id),1);
                    }
                    if (currentPerson.getAvailabilityBooleanArrayList().get(1)){
                        databaseManager.createEmployeeAvailability(Long.valueOf(id),2);
                    }
                    if (currentPerson.getAvailabilityBooleanArrayList().get(2)){
                        databaseManager.createEmployeeAvailability(Long.valueOf(id),3);
                    }
                    if (currentPerson.getAvailabilityBooleanArrayList().get(3)){
                        databaseManager.createEmployeeAvailability(Long.valueOf(id),4);
                    }
                }
                Toast.makeText(this,"xml is added",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {

        // - go to TaskAssignment class
        // - send the id of employee which is chosen
        Intent intent = new Intent(this,TaskAssignment.class);
        intent.putExtra("employee_id",id);
        startActivity(intent);


    }
}
