package com.example.madhura.contactslist;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Madhura on 15-10-2017.
 */

public class Contact implements Serializable {

    private String name;
    private int phone;
    private ArrayList<Contact> relations;
    private boolean checkbox;
    private boolean delete_request;

    public Contact(String name, int phone, ArrayList<Contact> relations) {
        this.name = name;
        this.phone = phone;
        this.relations = relations;
    }

    public Contact(String name, int phone) {
        this.name = name;
        this.phone = phone;
    }

    public Contact() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public ArrayList<Contact> getRelations() {
        return relations;
    }

    public void setRelations(ArrayList<Contact> relations) {
        this.relations = relations;
    }

    public boolean isCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }

    public boolean isDelete_request() {
        return delete_request;
    }

    public void setDelete_request(boolean delete_request) {
        this.delete_request = delete_request;
    }
}
