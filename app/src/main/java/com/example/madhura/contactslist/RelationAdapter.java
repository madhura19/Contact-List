package com.example.madhura.contactslist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Madhura on 16-10-2017.
 */

public class RelationAdapter extends ArrayAdapter<Contact> {
    TextView contactItem;
    CheckBox checkbox;

    public RelationAdapter(Context context, int resource, ArrayList<Contact> contactList) {
        super(context, R.layout.item_list_view, contactList);
    }

    /*static class CustomHandler{
        TextView contactname;
        CheckBox checkbox;
    }*/

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        String name;
        //CustomHandler handler;
        //listitem = convertView;

        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View listitem = inflater.inflate(R.layout.item_list_view, parent, false);
        contactItem = (TextView)listitem.findViewById(R.id.list_item);
        checkbox = (CheckBox)listitem.findViewById(R.id.checkbox);

        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get the position of checked items
                int pos = position;
                Contact localcontact = getItem(position);

                //if checkbox is checked, set it as true; else set it as false
                if(!getItem(position).isCheckbox()){
                    getItem(position).setCheckbox(true);
                    System.out.println("7777  1");
                }
                else {
                    getItem(position).setCheckbox(false);
                    System.out.println("7777  2");
                }
            }
        });

        name = getItem(position).getName();
        contactItem.setText(name);
        return listitem;
    }

    public void checkFunc(){
        //when an item is checked
        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("3403 ---- list box clicked");
            }
        });
        //when a contact is clicked
        contactItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("3403 ---- list item clicked");
            }
        });
    }
}
