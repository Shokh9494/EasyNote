package com.example.easynote.model;

public class Item {

    private String notesMain;
    private String notesAdd;
    private int id;
    private String dateItemAdded;

    public Item(String notesMain, String notesAdd, int id, String dateItemAdded) {
        this.notesMain = notesMain;
        this.notesAdd = notesAdd;
        this.id = id;
        this.dateItemAdded = dateItemAdded;
    }



    public Item() {
    }

    public Item(String notesMain, String notesAdd, String dateItemAdded) {
        this.notesMain = notesMain;
        this.notesAdd = notesAdd;
        this.dateItemAdded = dateItemAdded;
    }

    public String getNotesMain() {
        return notesMain;
    }

    public void setNotesMain(String notesMain) {
        this.notesMain = notesMain;
    }

    public String getNotesAdd() {
        return notesAdd;
    }

    public void setNotesAdd(String notesAdd) {
        this.notesAdd = notesAdd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateItemAdded() {
        return dateItemAdded;
    }

    public void setDateItemAdded(String dateItemAdded) {
        this.dateItemAdded = dateItemAdded;
    }
}
