package com.example.madhura.contactslist;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Madhura on 15-10-2017.
 */

public class ContactList extends Fragment implements AdapterView.OnItemClickListener{

    ListView contact_list;
    Button add_button;
    Button delete_button;
    ArrayList<Contact> contact;
    CustomAdapter cadapter;
    //boolean itemClicked;
    //static boolean boxChecked;
    Communicator communicator;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_list, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //create arraylists to store relationaships
        ArrayList<Contact> relation_list1 = new ArrayList<>();
        ArrayList<Contact> relation_list2 = new ArrayList<>();
        ArrayList<Contact> relation_list3 = new ArrayList<>();
        ArrayList<Contact> relation_list4 = new ArrayList<>();

        //add some contacts
        Contact contact1, contact2, contact3, contact4;
        contact1 = new Contact("Madhura", 85955);
        contact2 = new Contact("Ddhvrg", 4886);
        contact3 = new Contact("Sfihw", 94462);
        contact4 = new Contact("hwieiw", 996133);

        //add the contacts to relationship arrays
        relation_list1.add(contact2);
        relation_list1.add(contact3);
        relation_list1.add(contact4);
        contact1.setRelations(relation_list1);

        relation_list2.add(contact3);
        relation_list2.add(contact4);
        contact2.setRelations(relation_list2);
        contact3.setRelations(relation_list2);
        contact4.setRelations(relation_list2);

        contact = new ArrayList<>();
        contact.add(contact1);
        contact.add(contact2);
        contact.add(contact3);

        contact_list = (ListView) getActivity().findViewById(R.id.contact_list);
        cadapter = new CustomAdapter(getActivity(), R.layout.item_list_view, contact);
        add_button = (Button) getActivity().findViewById(R.id.add_button);
        delete_button = (Button) getActivity().findViewById(R.id.delete_button);

        contact_list.setAdapter(cadapter);
        contact_list.setOnItemClickListener(this);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                communicator.add(contact);
            }
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if box is checked, delete whichever items are checked
                for (int i = 0; i< contact.size(); i++){
                    if (contact.get(i).isDelete_request() == true){
                        contact.remove(i);

                    }
                }
                contact_list.setAdapter(cadapter);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Contact contactItem = cadapter.getItem(position);
        communicator.respondForProfile(contactItem);
    }

    public void addNewItem(Contact contact_item){
        contact.add(contact_item);
        contact_list.setAdapter(cadapter);
    }

    /*public Communicator getCommunicator() {
        return communicator;
    }*/

    public void setCommunicator(Communicator c) {
        this.communicator = c;
    }

    @Override
    //when the view is destroyed, set all items as unchecked
    public void onDestroyView() {
        super.onDestroyView();

        //set all items as unchecked
        for (int i = 0; i < contact.size(); i++){
            contact.get(i).setDelete_request(false);
        }
    }
}
