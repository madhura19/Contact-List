package com.example.madhura.contactslist;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Madhura on 16-10-2017.
 */

public class AddContact extends Fragment implements Serializable {

    //declarations
    EditText contactname;
    EditText contactphone;
    ListView relationship;
    Button add_contact;

    Contact send_contact;
    Communicator communicator;
    ACCommunicator add_contact_communicator;
    RelationAdapter relationAdapter;
    //define a relationship list
    ArrayList<Contact> relationList;
    ArrayList<Contact> add_relation;

    //temporary variables for contact name and number
    String nameRec = "";
    int phoneRec = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //declarations and initializations
        View item_view = inflater.inflate(R.layout.contact_detail, container, false);
        contactname = (EditText)item_view.findViewById(R.id.contactname);
        contactphone = (EditText)item_view.findViewById(R.id.contactphone);
        relationship = (ListView)item_view.findViewById(R.id.relation_list);
        add_contact = (Button)item_view.findViewById(R.id.add_contact);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && getActivity().getClass().getSimpleName().toString().equals("MainActivity")){
            displayRelations();
        }
        return item_view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        add_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //declarations
                String name;
                String phone;
                int phonenum;

                name  = contactname.getText().toString();
                phone = contactphone.getText().toString();
                phonenum = Integer.parseInt(phone);
                send_contact = new Contact(name, phonenum);
                add_relation = new ArrayList<Contact>();

                for (int i = 0; i < relationList.size(); i++){
                    //check if the item is checked. If yes, add it to relationship list of the contact
                    if(relationList.get(i).isCheckbox()){
                        add_relation.add(relationList.get(i));
                        relationList.get(i).getRelations().add(send_contact);
                    }
                }
                send_contact.setRelations(add_relation);

                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                    add_contact_communicator.addNewContact(send_contact);
                }
                else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                    communicator.respondWithContactLand(send_contact);
                    contactname.setText("");
                    contactphone.setText("");
                    displayRelations();
                }

            }
        });
    }

    public void setTextView(String name, int phone){
        nameRec = name;
        phoneRec = phone;

    }

    //add the relations
    public void setRelations(ArrayList<Contact> contactList){
        AddContactActivity add_cont = (AddContactActivity) getActivity();
        add_cont.current_relationList = contactList;
        relationList = new ArrayList<>();
        //add items to relationship list
        for (int i = 0; i < contactList.size(); i++){
            relationList.add(contactList.get(i));
        }
    }

    //display the relations
    public void displayRelations(){
        contactname.setText(nameRec);
        contactphone.setText(Integer.toString(phoneRec));
        //if the relationship list is not empty
        if (relationList != null) {
            relationAdapter = new RelationAdapter(getActivity(), R.layout.item_list_view, relationList);
            relationship.setAdapter(relationAdapter);
            setOnClickListenerMethod();
        }
    }

    //public ACCommunicator getAddContact_communicator() {
    //    return add_contact_communicator;
    //}

    public void setAddContact_communicator(ACCommunicator c) {
        this.add_contact_communicator = c;
    }

    public void setLandCommunicator(Communicator c){
        this.communicator = c;
    }

    public void setOnClickListenerMethod(){
        relationship.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //portrait
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    Intent intent = new Intent(getActivity(), ContactProfileActivity.class);
                    intent.putExtra("contactItem", (Serializable) relationList.get(position));
                    intent.putExtra("uniqueID", "fromACActivity");
                    startActivity(intent);
                }
                //landscape
                else{
                    for (int i = 0; i < relationList.size(); i++){
                        relationList.get(i).setCheckbox(false);
                    }
                }

                communicator.respondForProfile(relationList.get(position));
            }
        });
    }

    @Override
    public void onDestroyView() {
        //Add Contact Activity
        if (getActivity().getClass().getSimpleName().toString().equals("AddContactActivity")){
            AddContactActivity add_cont = (AddContactActivity) getActivity();
            add_cont.current_name = contactname.getText().toString();
            add_cont.current_phone = Integer.parseInt(contactphone.getText().toString());
            add_cont.current_relationList = relationList;
        }

        //Main Activity
        if (getActivity().getClass().getSimpleName().toString().equals("MainActivity")){
            MainActivity add_cont = (MainActivity) getActivity();
            add_cont.current_nameDetails = contactname.getText().toString();
            add_cont.current_phoneDetails = Integer.parseInt(contactphone.getText().toString());
            add_cont.contactDetailList = relationList;
        }

        //if the list is not empty
        if (relationList != null){
            for (int i = 0; i < relationList.size(); i++){
                relationList.get(i).setCheckbox(false);
            }
        }
        super.onDestroyView();
    }

    //an interface to communicate Contact Details
    public interface ACCommunicator{
        public void addNewContact(Contact contact_item);
    }
}
