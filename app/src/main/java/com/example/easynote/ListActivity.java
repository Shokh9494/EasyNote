package com.example.easynote;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.easynote.data.DataBaseHandler;
import com.example.easynote.model.Item;
import com.example.easynote.ui.RecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Item> itemList;
    private DataBaseHandler db;
    private FloatingActionButton fab;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;

    private AlertDialog dialog;
    private EditText editTextTitle;
    private EditText editTextNoteAdd;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        recyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.fab);
        db = new DataBaseHandler(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemList = new ArrayList<>();

        itemList = db.getAllItem();
        for (Item item : itemList) {
            Log.d("Main", "233" + item.getNotesAdd());
        }

        recyclerViewAdapter = new RecyclerViewAdapter(this, itemList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPoDialog();
            }
        });

    }

    private void createPoDialog() {
        builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup, null);
        editTextTitle = view.findViewById(R.id.notes_layout);
        editTextNoteAdd = view.findViewById(R.id.notes_add);
        button = view.findViewById(R.id.buttonAddNote);

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextNoteAdd.getText().toString().isEmpty() && !editTextTitle.getText().toString().isEmpty()) {
                    saveNotes(v);
                } else {
                    Snackbar.make(v, "Заполните поля", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void saveNotes(View view) {


        Item item = new Item();
        String notesTitle = editTextTitle.getText().toString().trim();
        String notesAdd = editTextNoteAdd.getText().toString().trim();

        item.setNotesMain(notesTitle);
        item.setNotesAdd(notesAdd);

        db.addNotes(item);


        Snackbar.make(view, "Заметка сохранена", Snackbar.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                alertDialog.dismiss();
                startActivity(new Intent(ListActivity.this, ListActivity.class));
                finish();
            }
        }, 1200);
    }

}
