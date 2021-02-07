package com.sumitkumar.taskmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SQLiteDatabase sqLiteDatabase;
    FloatingActionButton addNewTask;
    ArrayList<Task> tasks = new ArrayList<Task>();
    CustomAdapter customAdapter;
    private NotificationManager mNotificationManager;
    private static final int NOTIFICATION_ID = 0;
    private static final String PRIMARY_CHANNEL_ID =
            "alert_notification_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        sqLiteDatabase = getBaseContext().openOrCreateDatabase("tasks.sqlite", MODE_PRIVATE, null);
        addNewTask = findViewById(R.id.addNewTask);

        addNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), AddNewTask.class);
                startActivity(i);
            }
        });
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS tasks(task TEXT,description TEXT, time TEXT)");
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM tasks", null);
        if (cursor.moveToFirst())
        {
            do{
                String name = cursor.getString(0);
                String desc = cursor.getString(1);
                String time = cursor.getString(2);

                Task task = new Task(name, time, desc);
                tasks.add(task);
            } while (cursor.moveToNext());

            customAdapter = new CustomAdapter(MainActivity.this, tasks);
            recyclerView.setAdapter(customAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        } else
        {
            Toast.makeText(getBaseContext(), "No tasks", Toast.LENGTH_LONG).show();
        }

        Intent notifyIntent = new Intent(this, AlarmReciver.class);
        PendingIntent notifyPendingIntent = PendingIntent.getBroadcast
                (this, NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        createNotificationChannel();
    }

    public void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {

            // Create the NotificationChannel with all the parameters.
            NotificationChannel notificationChannel = new NotificationChannel
                    (PRIMARY_CHANNEL_ID,
                            "Stand up notification",
                            NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription
                    ("Notifies about any tasks that need to be done");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }
}