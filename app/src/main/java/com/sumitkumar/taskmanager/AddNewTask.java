package com.sumitkumar.taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddNewTask extends AppCompatActivity {

    SQLiteDatabase sqLiteDatabase;
    Button addBTN;
    private static final String TAG = "AddNewTask";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);
        sqLiteDatabase = getBaseContext().openOrCreateDatabase("tasks.sqlite", MODE_PRIVATE, null);
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS tasks(task TEXT,description TEXT, time TEXT)");

        addBTN = findViewById(R.id.addTask);
        addBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText task_name = findViewById(R.id.task_name);
                EditText task_desc = findViewById(R.id.task_description);
                EditText task_time = findViewById(R.id.task_time);
                String task = task_name.getText().toString();
                String desc = task_desc.getText().toString();
                String time = task_time.getText().toString();
                if (!(task.equals("")) && !(time.equals(""))){
                    String sql = "INSERT INTO tasks VALUES('" + task + "', '" + desc + "', '" + time + "')";
                    Log.d(TAG, sql);
                    sqLiteDatabase.execSQL(sql);
                    Toast.makeText(getBaseContext(), "Task Successfully Added", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(i);
                } else {
                    if (task.equals("")){
                        Toast.makeText(getBaseContext(), "Task Name cannot be Empty!", Toast.LENGTH_LONG).show();
                    } else if (time.equals("")){
                        Toast.makeText(getBaseContext(), "Please Select A Time for this task", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}