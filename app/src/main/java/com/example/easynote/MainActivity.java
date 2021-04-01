package com.example.easynote;

import android.content.Intent;
import android.os.Bundle;

import com.example.easynote.data.DataBaseHandler;
import com.example.easynote.model.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private EditText editTextTitle;
    private EditText editTextNoteAdd;
    private Button button;
    private DataBaseHandler dataBaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dataBaseHandler = new DataBaseHandler(this);
        byPassActivity();

        List<Item> items = dataBaseHandler.getAllItem();
        for (Item item : items) {
            Log.d("Main", "Oncreate ;" + item.getDateItemAdded());
        }


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createPopupDialog();

            }
        });
    }

    private void byPassActivity() {
        if (dataBaseHandler.getItemsCount()>0){
            startActivity(new Intent(MainActivity.this,ListActivity.class));
            finish();
        }
    }

    private void saveNotes(View view) {

        Item item = new Item();
        String notesTitle = editTextTitle.getText().toString().trim();
        String notesAdd = editTextNoteAdd.getText().toString().trim();

        item.setNotesMain(notesTitle);
        item.setNotesAdd(notesAdd);

        dataBaseHandler.addNotes(item);


        Snackbar.make(view, "Заметка сохранена", Snackbar.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                startActivity(new Intent(MainActivity.this, ListActivity.class));
            }
        }, 1200);
    }

    private void createPopupDialog() {

        builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup, null);
        editTextTitle = view.findViewById(R.id.notes_layout);
        editTextNoteAdd = view.findViewById(R.id.notes_add);

        button = view.findViewById(R.id.buttonAddNote);
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


        builder.setView(view);
        dialog = builder.create();
        dialog.show();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}