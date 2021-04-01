package com.example.easynote.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easynote.R;
import com.example.easynote.data.DataBaseHandler;
import com.example.easynote.model.Item;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<Item> itemList;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private LayoutInflater inflater;


    public RecyclerViewAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);


        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {

        Item item = itemList.get(position);
        holder.textViewMain.setText("Тема заметки: " + item.getNotesMain());
        holder.textViewNotes.setText(item.getNotesAdd());
        holder.textViewDate.setText("Дата: " + item.getDateItemAdded());


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textViewMain;
        public TextView textViewNotes;
        public TextView textViewDate;
        public int id;
        public Button editButton;
        public Button deleteButton;


        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;

            textViewMain = itemView.findViewById(R.id.textViewMain);
            textViewNotes = itemView.findViewById(R.id.textViewNotes);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            editButton = itemView.findViewById(R.id.edit_button);
            deleteButton = itemView.findViewById(R.id.delete_button);

            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            position = getAdapterPosition();
            Item item = itemList.get(position);

            switch (v.getId()) {
                case R.id.edit_button:

                    editNotes(item);
                    break;

                case R.id.delete_button:
                    deleteNotes(item.getId());
                    break;
            }

        }

        private void editNotes(Item newItem) {

            //  final   Item item = itemList.get(getAdapterPosition());

            EditText editTextTitle;
            EditText editTextNoteAdd;
            Button button;
            TextView title;


            builder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.popup, null);
            editTextTitle = view.findViewById(R.id.notes_layout);
            editTextNoteAdd = view.findViewById(R.id.notes_add);
            button = view.findViewById(R.id.buttonAddNote);
            button.setText("Обновить");
            title = view.findViewById(R.id.title);
            title.setText("Редактировать заметку");

            editTextTitle.setText(newItem.getNotesMain());
            editTextNoteAdd.setText(newItem.getNotesAdd());


            builder.setView(view);
            dialog = builder.create();
            dialog.show();
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DataBaseHandler dataBaseHandler = new DataBaseHandler(context);

                    newItem.setNotesMain(editTextTitle.getText().toString());
                    newItem.setNotesAdd(editTextNoteAdd.getText().toString());
                    if (!editTextNoteAdd.getText().toString().isEmpty() && !editTextTitle.getText().toString().isEmpty()) {
                        dataBaseHandler.updateItem(newItem);
                        notifyDataSetChanged();
                    } else {
                        Snackbar.make(view, "Заполните поля!", Snackbar.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }
            });


        }

        private void deleteNotes(int id) {

            builder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.confirmation_popup, null);
            Button button = view.findViewById(R.id.conf_button);
            Button button1 = view.findViewById(R.id.conf_button1);

            builder.setView(view);
            dialog = builder.create();
            dialog.show();
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DataBaseHandler db = new DataBaseHandler(context);
                    db.deleteItem(id);
                    itemList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    dialog.dismiss();
                }
            });
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });


        }


    }
}
