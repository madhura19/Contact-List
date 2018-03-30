package com.example.madhura.contactslist;

import java.util.ArrayList;

/**
 * Created by Madhura on 17-10-2017.
 */

//interface to communicate between different fragments
public interface Communicator {

    public void respondForProfile(Contact object);
    public void add(ArrayList<Contact> contactArrayList);
    public void addWithText(ArrayList<Contact> contactArrayList, String name, int phone);
    public void respondWithContactLand(Contact contact_item);
}
