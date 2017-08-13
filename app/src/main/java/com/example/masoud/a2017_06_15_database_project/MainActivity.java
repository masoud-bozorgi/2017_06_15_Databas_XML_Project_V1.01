package com.example.masoud.a2017_06_15_database_project;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.masoud.a2017_06_15_database_project.dataBase.DatabaseManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextUserName, editTextPassword;
    Button btnSignIn, btnSignUp;

    DatabaseManager databaseManager;
    SQLiteDatabase sqLiteDatabase = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    private void initialize() {

        editTextUserName = (EditText) findViewById(R.id.editTextUserName);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(this);

        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(this);


        // create database
        databaseManager = new DatabaseManager((this));
        sqLiteDatabase = databaseManager.createDatabase();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnSignIn:

                String emailFromEditText = editTextUserName.getText().toString();

                if(editTextPassword.getText().toString().isEmpty() || editTextUserName.getText().toString().isEmpty()){
                    Toast.makeText(this,"Username or Password is empty.",Toast.LENGTH_SHORT).show();
                    break;
                }

                if (emailFromEditText.equals("admin") && editTextPassword.getText().toString().equals("admin")) {

                    Intent goToAvailability = new Intent(this, Availability.class);
                    startActivity(goToAvailability);
                    break;
                }

                if (databaseManager.fetchEmployeeInfo(emailFromEditText).length == 0 ) {

                    Toast.makeText(this, "There is no employee by this name", Toast.LENGTH_SHORT).show();
                    break;
                }

                //---------------------------------------------- Check database for user email ID
                String[] fetchedEmployeeInfoStringArray = databaseManager.fetchEmployeeInfo(emailFromEditText);

                //---------------------------------------------- Check user password
                if (fetchedEmployeeInfoStringArray[3].equals(editTextPassword.getText().toString())) {

                    Intent intent = new Intent(this, Registration.class);
                    intent.putExtra("signInOrUp", "in");

                    intent.putExtra("email", emailFromEditText);
                    startActivity(intent);

                } else {
                    Toast.makeText(this, "User name or password is not right", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btnSignUp:

                Intent intentFromSignUp = new Intent(this, Registration.class);
                intentFromSignUp.putExtra("signInOrUp", "up");
                startActivity(intentFromSignUp);
                break;
        }
    }
}
