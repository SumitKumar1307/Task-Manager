package com.sumitkumar.taskmanager;

public class Task {
    private String name;
    private String time;
    private String desc;

    Task(String name, String time, String desc)
    {
        this.name = name;
        this.time = time;

        if(desc.equals(""))
        {
            this.desc = "No description found";
        } else {
            this.desc = desc;
        }
    }

    public String getName(){
        return this.name;
    }

    public String getTime(){
        return this.time;
    }

    public String getDesc(){
        return this.desc;
    }
}
