package com.example.todo;



import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText editTextTask;
    private ListView listViewTasks;
    private ArrayList<String> tasksList;
    private TaskAdapter tasksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTask = findViewById(R.id.edit_text_task);
        listViewTasks = findViewById(R.id.list_view_tasks);

        tasksList = new ArrayList<>();
        tasksAdapter = new TaskAdapter();
        listViewTasks.setAdapter(tasksAdapter);

        Button btnAddTask = findViewById(R.id.btn_add_task);
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task = editTextTask.getText().toString().trim();
                if (!task.isEmpty()) {
                    tasksList.add(task);
                    tasksAdapter.notifyDataSetChanged();
                    editTextTask.setText("");
                }
            }
        });

        Button btnClearAll = findViewById(R.id.btn_clear_all);
        btnClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showClearAllConfirmationDialog();
            }
        });
    }

    // Custom Adapter to handle list item views
    private class TaskAdapter extends ArrayAdapter<String> {
        TaskAdapter() {
            super(MainActivity.this, R.layout.list_item_task, R.id.text_task_name, tasksList);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                LayoutInflater inflater = getLayoutInflater();
                itemView = inflater.inflate(R.layout.list_item_task, parent, false);
            }

            // Set the task name
            final TextView textTaskName = itemView.findViewById(R.id.text_task_name);
            textTaskName.setText(tasksList.get(position));

            // Handle the delete button click
            Button btnDeleteTask = itemView.findViewById(R.id.btn_delete_task);
            btnDeleteTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tasksList.remove(position);
                    tasksAdapter.notifyDataSetChanged();
                }
            });

            // Handle checkbox click to apply strikethrough
            CheckBox checkboxTask = itemView.findViewById(R.id.checkbox_task);
            checkboxTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    if (cb.isChecked()) {
                        textTaskName.setPaintFlags(textTaskName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    } else {
                        textTaskName.setPaintFlags(textTaskName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    }
                }
            });

            return itemView;
        }
    }

    private void showClearAllConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Clear All Tasks");
        builder.setMessage("Do you want to clear all the tasks?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tasksList.clear();
                tasksAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing, just dismiss the dialog
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
