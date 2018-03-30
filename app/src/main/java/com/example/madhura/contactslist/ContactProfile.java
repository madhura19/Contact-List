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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Madhura on 16-10-2017.
 */

public class ContactProfile extends Fragment {
    //declarations
    TextView namedesc;
    TextView phonedesc;
    ListView relationship;
    String local_name;
    int local_phone;
    ArrayList<Contact> local_relationList;

    ArrayList<String> disp_relation;
    ArrayAdapter<String> relationAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //declarations
        View list_view = inflater.inflate(R.layout.contact_profile, container, false);
        namedesc = (TextView)list_view.findViewById(R.id.name_desc);
        phonedesc = (TextView)list_view.findViewById(R.id.phone_desc);
        relationship = (ListView)list_view.findViewById(R.id.relation_list);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && getActivity().getClass().getSimpleName().toString().equals("MainActivity")) {
            displayLocalData();
        }

        return list_view;
    }

    public void setLocalData(Contact contactItem){
        //get name and phone no and store in a local variable
        local_name = contactItem.getName();
        local_phone = contactItem.getPhone();
        local_relationList = contactItem.getRelations();
    }

    public void displayLocalData(){

        disp_relation = new ArrayList<>();
        //if the relationship list is not empty, add it to display list
        if (local_relationList != null){
            for (int i = 0; i < local_relationList.size(); i++){
                disp_relation.add(local_relationList.get(i).getName());
            }
        }
        relationAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, disp_relation);
        relationship.setAdapter(relationAdapter);
        setOnClickListenerMethod();
        namedesc.setText(local_name);
        phonedesc.setText(String.valueOf(local_phone));
    }

    public void changeLocalData(Contact contactItem){

        //portrait to landscape
        if (getActivity().getClass().getSimpleName().toString().equals("ContactProfileActivity")) {
            ContactProfileActivity cp = (ContactProfileActivity) getActivity();
            cp.sendContact_main = contactItem;
        }

        disp_relation = new ArrayList<>();
        local_relationList = contactItem.getRelations();
        //display the relations
        for (int i = 0; i < local_relationList.size(); i++){
            disp_relation.add(local_relationList.get(i).getName());
        }

        relationAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, disp_relation);
        relationship.setAdapter(relationAdapter);
        setOnClickListenerMethod();
        local_name = contactItem.getName();
        local_phone = contactItem.getPhone();
        namedesc.setText(local_name);
        phonedesc.setText(String.valueOf(local_phone));
    }

    public void setOnClickListenerMethod() {
        relationship.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //portrait
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    Intent intent = new Intent(getActivity(), ContactProfileActivity.class);
                    intent.putExtra("contactItem", (Serializable) local_relationList.get(position));
                    intent.putExtra("uniqueID", "fromCPActivity");
                    startActivity(intent);
                }
                //landscape
                else {
                    Contact newContact = local_relationList.get(position);
                    setLocalData(newContact);
                    displayLocalData();

                }

            }
        });
    }

}
