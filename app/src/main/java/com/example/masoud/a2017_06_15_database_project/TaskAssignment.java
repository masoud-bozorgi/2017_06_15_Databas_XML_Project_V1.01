package com.example.masoud.a2017_06_15_database_project;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.masoud.a2017_06_15_database_project.dataBase.DatabaseManager;

public class TaskAssignment extends AppCompatActivity implements View.OnClickListener{

    TextView textViewName, textViewPhone, textViewEmail;
    EditText editTextNewTasks;
    Button btnSave, btnReturn;


    //--------------------------------------- Database access
    DatabaseManager databaseManager;
    SQLiteDatabase sqLiteDatabase = null;

    long employee_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_assignment);

        initialize();
    }

    private void initialize() {

        textViewName = (TextView)findViewById(R.id.textViewNameAssign);
        textViewPhone = (TextView)findViewById(R.id.textViewPhoneAssign);
        textViewEmail = (TextView)findViewById(R.id.textViewEmailAssign);

        editTextNewTasks = (EditText)findViewById(R.id.editTextNewTasks);

        btnSave = (Button)findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);

        btnReturn = (Button)findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(this);


        // create database
        databaseManager = new DatabaseManager((this));
        sqLiteDatabase = databaseManager.createDatabase();


        employee_id = getIntent().getLongExtra("employee_id",1);

        //Fetch passed employee from database.
        String[] answer = databaseManager.fetchEmployeeInfo(employee_id);

        textViewName.setText(answer[0]);
        textViewPhone.setText(answer[1]);
        textViewEmail.setText(answer[2]);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnSave:

                // - get the text from textView
                //- create task for this id
                String taskNote = editTextNewTasks.getText().toString();
                databaseManager.createTask(taskNote,employee_id);
                break;

            case R.id.btnReturn:
                finish();
                break;
        }

    }
}
