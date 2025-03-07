package com.example.databinding_shared_preferences_sqllite.model;

import java.io.Serializable;

public class NotesModel implements Serializable {
    private int IdNote;
    private String NameNote;

    public NotesModel(int idNode, String nameNote){
        this.IdNote = idNode;
        this.NameNote = nameNote;
    }

    public int getIdNote() {
        return IdNote;
    }

    public void setIdNote(int idNote) {
        IdNote = idNote;
    }

    public String getNameNote() {
        return NameNote;
    }

    public void setNameNote(String nameNote) {
        NameNote = nameNote;
    }
}
