package com.example.annuairegsh.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class ListContact implements Serializable {
    private ArrayList<Contact> contacts;

    public ListContact(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }
}
