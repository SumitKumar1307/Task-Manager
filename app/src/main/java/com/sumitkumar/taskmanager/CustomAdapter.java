package com.sumitkumar.taskmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>
{
    Context context;
    ArrayList<Task> tasks;

    CustomAdapter(Context context, ArrayList<Task> tasks){
        this.context = context;
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.task, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.task_name.setText(task.getName());
        holder.task_time.setText(task.getTime());
        holder.task_desc.setText(task.getDesc());
        holder.task_n.setText(String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView task_n, task_name, task_time, task_desc;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            task_n = itemView.findViewById(R.id.task_id);
            task_name = itemView.findViewById(R.id.task_name);
            task_time = itemView.findViewById(R.id.task_time);
            task_desc = itemView.findViewById(R.id.task_desc);
        }
    }
}
